#Alex Mitro Amm364 Assignment1 CS1656

import math
import nltk
import json


def findweight(key, document):  # finds the weight of keyword in the document
    weight = float((1 + math.log(dictionary[key][document], 2)) * math.log(
        (dictionary["totalDocs"]["total"] / float(dictionary[key]["numberOfDocs"])), 2))
    return weight

dictionary = {}
f = open("inverted-index.json")
dictionary = json.load(f)  # Load in the dictionary
f.close()
f = open("keywords.txt")
keyword = f.readline()
while keyword:  # loop through each line of the keywords file
    rank = 1
    previousFilePrinted = {}
    previousKey = ""
    previousFileRank = 0
    docWeights = {}
    stemArray = []
    print "Keywords = " + keyword
    keyword = keyword.lower()
    punctuation = ['\'', '.', ',', ';', ':', '!', '?', '$', '#', '@', '$', '%', '&', '(', ')', '-', '_', '\n']
    for j in range(0,10):  # get rid of numbers in the keywords
        keyword = keyword.replace(str(j), '')
        int(j)
    for j in range(0,18):  # get rid of punctuation
        keyword = keyword.replace(punctuation[j], '')
    tk = nltk.word_tokenize(keyword)
    stemmer = nltk.PorterStemmer()
    for k in tk:  # stem the keywords and place back into the tk array
        stemmedWord = stemmer.stem(k)
        stemArray.append(stemmedWord)
    sentence = ""
    for x in stemArray:
        sentence += x + " "
    tk = nltk.word_tokenize(sentence)
    index = []
    numberOfFiles = 0
    for i in range(1,10):  # loop through the documents
        doc = "doc" + str(i) + ".txt"
        sumVal = 0
        for key in tk:  # loop through the keywords. Add their weights together
            try:
                sumVal += findweight(key, doc)
            except KeyError:  # if keyword not found, weight is 0
                sumVal += 0
        if sumVal != 0:  # if the weight is 0, then keywords dont exist in the file
            docWeights[doc] = sumVal
            index.append(doc)   #The index array holds the docs who have some weight with the keyword
            numberOfFiles += 1
        int(i)
    for count in range(0,numberOfFiles):        #beginning of the format code
        currentFile = {}
        currentKey = ""
        valueOfBestFile = 0
        bestCurrentFile = {}
        indexOfBestKey = 0
        for c in range(0, numberOfFiles):   #loop through the index array and look for the highest weight
            try:
                d = index[c];
                currentFile[d] = docWeights[d]
                if currentFile[d] > valueOfBestFile:
                    bestCurrentFile[d] = currentFile[d]
                    valueOfBestFile = currentFile[d]
                    currentKey = d
                    indexOfBestKey = c
            except KeyError:
                pass
        try:
            if bestCurrentFile[currentKey] == previousFilePrinted[previousKey]: # if the new weight is the same as the previous file's weight
                print "[" + str(previousFileRank) + "] file=" + currentKey + " score=" + str(  #print the rank of the previous file, then the rest of the info
                    bestCurrentFile[currentKey])
                for t in tk:
                    try:
                        print "    weight(" + t + ")=" + str(findweight(t, currentKey))
                    except KeyError:
                        print "    weight(" + t + ")=" + str(0.000000)
                int(bestCurrentFile[currentKey])
                int(previousFileRank)
                rank += 1 #increment rank
            else: # if the weights are not the same, print out the next rank in order
                print "[" + str(rank) + "] file=" + currentKey + " score=" + str(bestCurrentFile[currentKey])
                for t in tk:
                    try:
                        print "    weight(" + t + ")=" + str(findweight(t, currentKey))
                    except KeyError:
                        print "    weight(" + t + ")=" + str(0.000000)
                int(bestCurrentFile[currentKey])
                int(rank)
                previousFileRank = rank
                rank += 1
        except KeyError: #Can't access previous file weight because this is the first rank to print out
            print "[" + str(rank) + "] file=" + currentKey + " score=" + str(bestCurrentFile[currentKey])
            for t in tk:
                try:
                    print "    weight(" + t + ")=" + str(findweight(t, currentKey))
                except KeyError:
                    print "    weight(" + t + ")=" + str(0.000000)
            int(bestCurrentFile[currentKey])
            int(rank)
            previousFileRank = rank
            rank += 1
        print "\n"
        previousFilePrinted = currentFile   #update variables so we can access previous rank and weight to check for ties
        previousKey = currentKey
        index[indexOfBestKey] = ""
        count += 1
    keyword = f.readline()  #read next line in keyword file
    print ""