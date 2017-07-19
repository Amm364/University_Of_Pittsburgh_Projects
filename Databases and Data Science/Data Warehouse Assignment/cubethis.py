import itertools
import sys
import csv

#command: what is to be rolled up
#cube: cube to be rolled up

def rollup(command, cube):      #rolls up the cube on the attribute 'command'
    updatedCube = {}
    for key in cube:            #go through the cube's keys
        attr = key.split(" ")
        found = False
        for i in attr:
            if i in attributeDictionary[command]:       #if the key contains the attribute found==true
                found = True
                break
        if found:                                       #if we found a key with the rolled up attribute, skip it
            continue
        else:                                           #if we found a key without the rolled up attribute, add it to the updated cube
            updatedCube[key] = cube[key]
    return updatedCube


#command: which attribute to drilldown
#updatedCube: holds the updated cube
#cube: the original cube with all of the original data
#status: holds a list of the attribute names to see if it is currently in the cube or not(rolled up/drilled down)
#formatList: the format order for the output

def drilldown(command, updatedCube, cube, status, formatList, attributeDictionary):          #if we are drilling up on an attribute
    temp=[]
    for k in updatedCube:       #hold the keys of the current cube in temp
        temp.append(k)
    for k in temp:              #loop through temp as we drill down
        arr = k.split(" ")      #list of keys in updatedCube
        for key in cube:        #loop through the cube with all of the data
            attr = key.split(" ")   #list of keys in the original cube
            if key in attributeDictionary[command]:
                updatedCube[key] = cube[key]
                continue
            if set(arr).issubset(attr)==False:      #if the key in the updated cube is not a subset of the current key, go to the next one
                continue
            found = False          #if it makes it here, we can check this key to see if it contains the drilled down attribute
            for i in attr:
                if i in attributeDictionary[command]:       #if we find the attribute, we can add it to the list by marking found as true
                    found = True
                else:
                    for attribute in formatList:
                        if i in attributeDictionary[attribute] and status[attribute] == False:      # if this key contains a key that is currently drilled down, ignore it
                            found = False
                            break
            if found:           #update the cube if found
                updatedCube[key] = cube[key]
    return updatedCube

#condition: condition for slicing the cube
#updatedCube: cube to be rolled up

def sliceCube(condition, updatedCube):      #handles the slicing of the cube
    slicedCube = {}
    for key in updatedCube:     #go through the cube
        attr = key.split(" ")
        found = False
        for i in attr:      #loop through until the attribute matches the condition
            if i in attributeDictionary[condition[0]] and i == condition[1]: #check if the attribute matches the condition
                found = True
                break
        if found:   #add to the sliced cube
            slicedCube[key] = updatedCube[key]
    return slicedCube

#updatedCube: the cube of data
#formatList: holds the format of the output ex: year,state,product,sales
#numberOfAttrs: holds the number of attributes
#attributeDictionary: Dictionary that holds all of the attributes

#formatTheCube will take the cube and create a list of lists. Each list is going to be in the specified format
#It also substitutes 'all'
def formatTheCube(updatedCube,formatList,numberOfAttrs,attributeDictionary):
    formattedLists=[]
    for i in updatedCube:
        outputList = []     #Output list holds the list in the correct format for each key
        listSize = 0
        count = 0
        for j in formatList:        #go through the format list and create the formatted keys. This will format the current key (i) correctly
            if j == "sales":        #if were on the sales attribute, append it to the list and then go to the next attribute
                outputList.append(updatedCube[i])
                continue
            found = False
            keys = i.split(" ")     #split up the data that was in i
            for k in keys:          #go through the data
                if k in attributeDictionary[j]:     #If the k is a part of the attribute j (ie: if k is 2014 and j is year) then add it to the list.
                    outputList.append(k)
                    listSize += 1                   #count of how big my list is so far
                    found = True                    #we found data that matched the attribute
                    count += 1
                    break
            if found == False:                      #if we couldnt find an attribute (like year)
                outputList.append("all")            #put an 'all' in its place
                listSize += 1
                count += 1
        while listSize != numberOfAttrs:            #if we finish looping through the formatlist but we have data missing, place an 'all' in the remaining places
            outputList.append("all")
            listSize += 1
        formattedLists.append(outputList)           #add the formatted list to main list
    formattedLists = sorted(formattedLists, key=lambda x: x[0], reverse=True)              #sort the main list based on the first index
    return formattedLists   #return formatted lists in sorted order

def save(updatedCube,formatList,numberOfAttrs,attributeDictionary,fileName):    #Saves the current cube into the file.
    formattedCube=formatTheCube(updatedCube, formatList, numberOfAttrs, attributeDictionary) #format the cube
    with open(fileName, "w") as output:
        writer = csv.writer(output, lineterminator='\n')
        writer.writerows(formattedCube)

attributeDictionary = {}
statusDictionary = {}
keyList = []
cube = {}
cube[""] = 0
commands = sys.argv[1]
listOfCommands = []
fp1 = open(commands, "r")
commandLines = fp1.readline()
inputFile = ""
format = ""
while commandLines:                         #This iterates throught the command text file and parses the information
    commandLines.replace("\n", "")
    if commandLines[0] == "#":              #ignore the lines beginning with #
        commandLines = fp1.readline()
        continue
    elif commandLines == "\n":              #ignore \n
        commandLines = fp1.readline()
        continue
    else:                                   #everything else is put into the list of commands
        listOfCommands.append(commandLines.replace("\n", ""))
    commandLines = fp1.readline()
for sentence in listOfCommands:             #find the format list and the load command so we can load in the cube
    token = sentence.split(" ")
    if token[0] == "format":
        format = token[1]
    elif token[0] == "load":
        inputFile = token[1]
del listOfCommands[0]           #get rid of the first two commands
del listOfCommands[0]
fp = open(inputFile, "r")                   #open the data file
line = fp.readline()
formatList = format.split(",")
numberOfAttrs = 0
for attr in formatList:                     #create an attribute dictionary. key is attr name, value is list of values that are of that attr
    attributeDictionary[attr] = []
    statusDictionary[attr] = True
    numberOfAttrs += 1
numberOfAttrs -= 1
while line:                                 #while there is data to read from the file
    data = []
    line = line.replace("\n", "")           #replace the \n in the line of data
    if line == "": continue
    values = line.split(",")
    for i in values:                        #put the data into a list. #THIS WILL ONLY WORK ASSUMING THE DATA IS READ INTO THE PROGRAM IN THE SAME FORMAT ie. year,state,product
        data.append(i)
    if data[0] not in attributeDictionary["year"]:      #if its in the first index, it belongs in the year list if its not already
        attributeDictionary["year"].append(data[0])
    if data[1] not in attributeDictionary["state"]:     #if its in the second index, it belongs in the state list if its not already
        attributeDictionary["state"].append(data[1])
    if data[2] not in attributeDictionary["product"]:   #if its in the third index, it belongs in the product list if its not already
        attributeDictionary["product"].append(data[2])
    salesCount = int(data[3])                           #hold the sales count in a variable
    del data[3]
    for count in range(1, numberOfAttrs + 1):
        combinations = itertools.combinations(data, count)  #go through every combination of data possible
        for key in combinations:
            stringKey = ""
            for value in key:                       #format the combinations into a string seperated by a space to use as the key to the dictionary
                if stringKey == "":
                    stringKey = value
                else:
                    stringKey += " " + value
            if stringKey in keyList:                #the key already exists in the dictionary, so add the sales to the previous value
                cube[stringKey] += salesCount
                if count==3:                        #handles the case that all attributes are rolled up
                    cube[""]+=salesCount
            else:                                   #add the key to the cube(dictionary)
                cube[stringKey] = salesCount
                if count==3:
                    cube[""]+=salesCount
                keyList.append(stringKey)
    line = fp.readline()
updatedCube = cube
for commands in listOfCommands:                     #got through the list of commands. When no more commands, the program stops
    commandIndex = commands.split(" ")
    if commandIndex[0] == "rollup":                 #if its roll up
        if statusDictionary[commandIndex[1]] == False:            # if the attribute is already rolled up
            print "ERROR. This attribute has already been rolled up!"
            continue
        statusDictionary[commandIndex[1]] = False   #update status dictionary false=rolled up true=in the cube still
        updatedCube = rollup(commandIndex[1], updatedCube)      #roll up the cube
    elif commandIndex[0] == "drilldown":                #if drill down
        if statusDictionary[commandIndex[1]] == True:
            print "ERROR. This attribute has already been drilled down!"
            continue
        statusDictionary[commandIndex[1]] = True        #drill down
        updatedCube = drilldown(commandIndex[1], updatedCube, cube, statusDictionary, formatList,attributeDictionary)
    elif commandIndex[0] == "slice":        #if it says slice, then slice the current cube using the condition
        condition = commandIndex[1].split("=")
        if attributeDictionary[condition[0]]==False:
            print "ERROR: Can't preform this slicing action. The attribute is rolled up"
        updatedCube = sliceCube(condition, updatedCube)
    elif commandIndex[0] == "save":         #call the save function if we are asked to save
        save(updatedCube,formatList,numberOfAttrs,attributeDictionary,commandIndex[1])
    else:
        print "Error: Can't recognize the command '" + commandIndex[0] + "'"