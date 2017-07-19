from Person import Person
from Chipotle import Chipotle
import numpy

def main():
    trials = int(input("How many trials would you like to run? "))
    rate = int(input("What is the rate of arrival? "))
    numFood = int(input("How many people on each food line?(1 - 5) "))
    numGrill = int(input("How many people on the grill?(1 or 2) "))
    numLines = int(input("How many lines?(1 or 2) "))
    split=False
    totalAvgProfit=0
    totalAvgTime=0
    totalAvgLineLength=0
    if numLines == 2:
        split = input("Would you like to partition the lines?(Y/N) ")
        if split.upper() == "Y":
            split = True
        elif split.upper() == "N":
            split = False
    for trial in range(0,trials):
        lineLengthAvg = 0
        denominator = 0
        chipotle = Chipotle(numLines,numFood,numGrill, split)
        arrivalRate = -1
        if len(chipotle.foodLine) == 2:
            while chipotle.clock != 7200:
                if arrivalRate == -1:
                    arrivalRate = numpy.random.poisson(rate) + chipotle.clock
                arrivalRate = chipotle.CheckForArrivals(arrivalRate)
                chipotle.UpdateWaitLine(0)
                chipotle.UpdateWaitLine(1)
                chipotle.UpdateFood()
                chipotle.UpdateFoodLine(0)
                chipotle.UpdateFoodLine(1)
                chipotle.CheckForCompletion(0)
                chipotle.CheckForCompletion(1)
                chipotle.IncrementClock()
                if chipotle.clock % 60 == 0:
                    for i in range(0,numLines):
                        lineLengthAvg += len(chipotle.waitLine[i])
                        denominator += 1
        else:
            while chipotle.clock != 7200:
                if arrivalRate == -1:
                    arrivalRate = numpy.random.poisson(rate) + chipotle.clock
                arrivalRate = chipotle.CheckForArrivals(arrivalRate)
                chipotle.UpdateWaitLine(0)
                chipotle.UpdateFood()
                chipotle.UpdateFoodLine(0)
                chipotle.CheckForCompletion(0)
                chipotle.IncrementClock()
                if chipotle.clock % 60 == 0:
                    for i in range(0,numLines):
                        lineLengthAvg += len(chipotle.waitLine[i])
                        denominator += 1
        wageCost = chipotle.numberOfWorkersTotal*20
        profit = chipotle.profit - wageCost
        totalAvgProfit += profit
        totalTime=chipotle.totalTime
        avgTime=int(totalTime/chipotle.totalCustomersCompleted)
        totalAvgTime += avgTime
        lineLengthAvg = int(lineLengthAvg / denominator)
        totalAvgLineLength += lineLengthAvg
    totalAvgLineLength = int(totalAvgLineLength/trials)
    totalAvgTime = int(totalAvgTime/trials)
    totalAvgProfit = int(totalAvgProfit/trials)
    print("Average Line Length: " + str(totalAvgLineLength))
    print("Average Wait Time: " + str(totalAvgTime))
    print("Average Profit: $" + str(totalAvgProfit))

main()