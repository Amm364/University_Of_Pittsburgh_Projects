import numpy
import random
from Person import Person


class Chipotle:

    def __init__(self,numberOfLines,numberOfWorkersOnLine,numberOfWorkersOnGrill, split):
        self.numberOfLines=numberOfLines
        self.numberOfWorkersOnLine=numberOfWorkersOnLine
        self.numberOfWorkersTotal=(numberOfLines*numberOfWorkersOnLine)+numberOfWorkersOnGrill+1
        self.numberOfWorkersOnGrill = numberOfWorkersOnGrill
        self.rateOfArrival=100
        self.rateOfTransaction=0
        self.waitLine=[]
        self.foodLine = []
        self.totalTime = 0
        self.clock=0
        self.profit=0
        self.cashTime=7
        self.bufferTime=0
        self.waitForFood = False
        self.totalCustomersCompleted = 0
        self.endBuffer = 0
        self.splitLines = split
        for i in range(0,numberOfLines):
            self.waitLine.append([])
            self.foodLine.append([])
        self.cookingTime=0
        self.servingsAvailable=50
        if self.numberOfWorkersOnLine==5:
            self.rateOfTransaction=40
            self.endBuffer = int(self.rateOfTransaction/5)
        elif self.numberOfWorkersOnLine==4:
            self.rateOfTransaction=45
            self.endBuffer = int(self.rateOfTransaction / 4)
        elif self.numberOfWorkersOnLine==3:
            self.rateOfTransaction=50
            self.endBuffer = int(self.rateOfTransaction / 3)
        elif self.numberOfWorkersOnLine==2:
            self.rateOfTransaction=55
            self.endBuffer = int(self.rateOfTransaction / 2)
        else:
            self.rateOfTransaction = 60
            self.endBuffer = int(self.rateOfTransaction)


    def UpdateWaitLine(self, line):
        currentWaitLine=self.waitLine[line]
        if len(currentWaitLine) == 0:
            return
        for i in range(0,len(currentWaitLine)):
            currentWaitLine[i].IncrementTotalTime()

    def UpdateFoodLine(self, line):
        currentLine = self.foodLine[line]
        currentWaitLine = self.waitLine[line]
        self.UpdateFoodLineTimes(line)
        if self.isFoodEmpty() and not self.isFoodReady() and self.waitForFood == False:  # If there is no food ready
            self.waitForFood = True
        elif self.isFoodReady():  # Add food to the line
            self.servingsAvailable += 50
            self.cookingTime = 0
            self.waitForFood = False  # If the food was ready, we dont need to wait anymore
        if not self.waitForFood:
            if (len(currentLine)<5 and self.bufferTime>=self.endBuffer) or len(currentLine) == 0:  # Customer buffer
                if len(currentWaitLine) > 0:
                    self.servingsAvailable -= 1
                    self.bufferTime=0
                    currentLine.append(currentWaitLine.pop(0))
                    #print("There are now {} people in the food line.".format(len(currentLine)))


    def isFoodEmpty(self):
        if self.servingsAvailable == 0:
            return True
        else:
            return False

    def isFoodReady(self):
        if self.numberOfWorkersOnGrill == 1:
            if self.cookingTime >= 1020:
                return True
            else:
                return False
        else:
            if self.cookingTime >= 900:
                return True
            else:
                return False


    def UpdateFoodLineTimes(self,line):
        currentLine = self.foodLine[line]
        self.bufferTime += 1
        for i in range(0,len(currentLine)):
            currentLine[i].IncrementTransactionTime()
            currentLine[i].IncrementTotalTime()

    def CheckForCompletion(self,line):
        currentLine=self.foodLine[line]
        if len(currentLine)>0:
            if currentLine[0].currentTransactionTime>=currentLine[0].totalExpectedTransactionTime:
                if currentLine[0].isPaying:
                    if currentLine[0].timeSpentPaying == 7:
                        #print("Total Time in Food Line " + str(line) + ": " + str(currentLine[0].currentTransactionTime) + " Expected: " + str(currentLine[0].totalExpectedTransactionTime))
                        person=currentLine.pop(0)
                        self.Pay()
                        self.totalCustomersCompleted+=1
                        self.totalTime+=person.totalTime
                    else:
                        currentLine[0].timeSpentPaying += 1
                else:
                    currentLine[0].isPaying = True
                    currentLine[0].timeSpentPaying += 1

    def Pay(self):
        self.profit+=7.00

    def UpdateFood(self):
        self.cookingTime+=1

    def IncrementClock(self):
        self.clock+=1

    def CheckForArrivals(self, arrivalRate):
        if self.clock == arrivalRate:
            if random.random() * 100 <= 64:
                person = Person(int(numpy.random.normal(self.rateOfTransaction, 13)),"bowl")
                self.putInLine(True, self.numberOfLines, person, self.splitLines)
            else:
                person = Person(int(numpy.random.normal(self.rateOfTransaction, 13)), "burrito")
                self.putInLine(False, self.numberOfLines, person, self.splitLines)
            return -1
        return arrivalRate

    def putInLine(self, isBowl, numbOfLines, person, splitLines):
        if isBowl:  # The customer wants a bowl
            if numbOfLines == 1:
                self.waitLine[0].append(person)
            elif numbOfLines == 2 and splitLines:
                self.waitLine[0].append(person)
            else:
                r = int(random.random() * 100)
                if r >= 50:
                    self.waitLine[1].append(person)
                else:
                    self.waitLine[0].append(person)
        else:  # The customer wants a burrito
            if numbOfLines == 1:
                self.waitLine[0].append(person)  # The burrito customers can go in line 1 if there isnt a 2nd line
            elif numbOfLines == 2 and splitLines:
                self.waitLine[1].append(person)  # The burrito customers will go to the 2nd line if there are 2 lines
            else:
                r = int(random.random() * 100)
                if r >= 50:
                    self.waitLine[1].append(person)
                else:
                    self.waitLine[0].append(person)


#64% bowls
#36% burritos