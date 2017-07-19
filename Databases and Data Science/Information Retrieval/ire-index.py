#Alex Mitro Amm364 Assignment1 CS1656

import nltk
import json

dictionary={"totalDocs":{"total":0}}
for count in range(1,10):
    file="doc"+str(count) + ".txt"
    entireFile= "input/" + file
    with open(entireFile, "r") as fp:
        sentence = fp.readline()
        original = nltk.word_tokenize(sentence)
        sentence=sentence.lower()
        punctuation=['\'','.',',',';',':','!','?','$','#','@','$','%','&','(',')','-','_','\n']
        for j in range(0,10):
            sentence = sentence.replace(str(j), '')
            int(j)
        j=0
        for j in range(0,18):
            sentence = sentence.replace(punctuation[j], '')
        stemmer = nltk.PorterStemmer()
        tk = nltk.word_tokenize(sentence)
        for word in tk:
            word=stemmer.stem(word)
            if dictionary.has_key(word):
                if dictionary[word].has_key(file):
                    dictionary[word][file]+=1
                else:
                    dictionary[word][file]=1
                    dictionary[word]["numberOfDocs"]+=1
            else:
                dictionary[word]={file:1}
                dictionary[word]["numberOfDocs"]=1
        dictionary["totalDocs"]["total"]+=1
f = open('inverted-index.json','wb')
json.dump(dictionary,f)
f.close()