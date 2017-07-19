from ExpressBooth import ExpressBooth
from CashBooth import CashBooth
from Person import Person
from RollerCoaster import RollerCoaster
from GameStalls import GameStalls
from MerryGoRound import MerryGoRound
import sys
import numpy
import random

numberOfStalls = int(sys.argv[1])
capacityOfMerryGoRound = int(sys.argv[2])
numberOfCashStalls = int(sys.argv[3])
numberOfExpressStalls = int(sys.argv[4])

totalAvgExpressLineWaitTime=0
totalAvgCashLineWaitTime=0
totalAvgRollerCoasterWaitTime=0
totalAvgMerryGoRoundWaitTime=0
totalAvgPeopleInFair=0
totalAvgWastedTickets=0
totalAvgLengths=[0,0,0,0]
profit=0
if numberOfStalls>10:
    num=numberOfStalls-10
    num=500*num
    profit-=num
elif numberOfStalls<10:
    num=10-numberOfStalls
    num=num*200
    profit+=num
for day in range(0,100):
    print(day)
    cashBooth=[]
    expressBooth=[]
    avgCashLineWaitTime=0
    avgExpressLineWaitTime=0
    avgRollerCoasterWaitTime=0
    avgMerryGoRoundWaitTime=0
    avgPeopleInFair=0
    denominator=0
    numberOfPeopleAtFair=0
    avgLengths=[0,0,0,0]
    totalTickets=0
    for i in range(0,numberOfCashStalls):
            cashBooth.append(CashBooth())

    for j in range(0,numberOfExpressStalls):
            expressBooth.append(ExpressBooth())

    rollerCoaster = RollerCoaster()
    stallsAndGames = GameStalls(numberOfStalls)
    merryGoRound = MerryGoRound(capacityOfMerryGoRound)
    totalCustomers = 0
    dailyCost = 0
    cashIterator=1
    currentCash=0
    currentExpress=0
    expressIterator=1
    early = []
    poissonCustomers = []
    numberOfEarly = int(numpy.random.normal(200, 10))
    def RandomizeLocation(person,num):
        rand = random.random() * 100
        if person.GetNumberOfTickets() >= 16:
            if rand <= 40:
                rollerCoaster.line.AddPerson(person)
                return num
            elif 75 >= rand > 40:
                if stallsAndGames.CheckCapacity():
                    stallsAndGames.AddCustomer(person)
                    return num
                else:
                    return RandomizeLocation(person,num)
            else:
                merryGoRound.line.AddPerson(person)
                return num
        elif person.GetNumberOfTickets() >= 8:
            if rand <= 58:
                if stallsAndGames.CheckCapacity():
                    stallsAndGames.AddCustomer(person)
                    return num
                else:
                    return RandomizeLocation(person,num)
            else:
                merryGoRound.line.AddPerson(person)
                return num
        elif person.GetNumberOfTickets() >= 1:
            if stallsAndGames.CheckCapacity():
                stallsAndGames.AddCustomer(person)
                return num
            else:
                num-=1
                return num
        else:
            num-=1
            return num

    for k in range(0,numberOfEarly):
        early.append(int(numpy.random.normal(1800, 900)))
        k += 1
    hold = True
    poisson = False

    for clock in range(0, 30600):
        if clock < 3600:
            if clock in early:
                p = Person()
                if random.random() * 100 <= 80:
                    expressBooth[currentExpress].line.AddPerson(p)
                    totalCustomers += 1
                    expressBooth[currentExpress].line.AddWaitTime(int(p.ticketsExpected / 10), True)
                    currentExpress += expressIterator
                    if currentExpress==numberOfExpressStalls:
                        expressIterator=-1
                        currentExpress+=expressIterator
                    elif currentExpress==-1:
                        expressIterator=1
                        currentExpress+=expressIterator

                else:
                    if cashBooth[currentCash].line.lineWaitTime >= 1200 and expressBooth[currentExpress].line.GetNumberOfPeople()*2<cashBooth[currentCash].line.GetNumberOfPeople():
                        expressBooth[currentExpress].line.AddPerson(p)
                        totalCustomers += 1
                        expressBooth[currentExpress].line.AddWaitTime(int(p.ticketsExpected / 10), True)
                        currentExpress += expressIterator
                        if currentExpress == numberOfExpressStalls:
                            expressIterator = -1
                            currentExpress += expressIterator
                        elif currentExpress == -1:
                            expressIterator = 1
                            currentExpress += expressIterator
                    else:
                        p.SetNumberOfExpectedTickets(numpy.random.normal(100, 30))
                        cashBooth[currentCash].AddToLine(p)
                        totalCustomers += 1
                        cashBooth[currentCash].line.AddWaitTime(int(p.ticketsExpected / 10), False)
                        currentCash+=cashIterator
                        if currentCash == numberOfCashStalls:
                            cashIterator = -1
                            currentCash += cashIterator
                        elif currentCash == -1:
                            cashIterator = 1
                            currentCash += cashIterator
        elif clock>=3600 and clock<23400:
            if not poisson:
                poisson = True
                poissonCustomers.append(clock + numpy.random.poisson(240))
            else:
                if clock in poissonCustomers:
                    p = Person()
                    if random.random() * 100 <= 10:
                        smallestLineSize=0
                        for i in range(0,numberOfExpressStalls):
                            if expressBooth[i].line.GetNumberOfPeople()<smallestLineSize or smallestLineSize==0:
                                currentExpress=i
                        expressBooth[currentExpress].AddToLine(p)
                        totalCustomers += 1
                        expressBooth[currentExpress].line.AddWaitTime(int(p.ticketsExpected / 10), True)
                    else:
                        for i in range(0,numberOfCashStalls):
                            switch=False
                            for j in range(0,numberOfExpressStalls):
                                if cashBooth[i].line.lineWaitTime >= 1200 and cashBooth[i].line.GetNumberOfPeople() >= expressBooth[j].line.GetNumberOfPeople() * 2:
                                    p.SetNumberOfExpectedTickets(200)
                                    expressBooth[j].AddToLine(p)
                                    totalCustomers += 1
                                    expressBooth[j].line.AddWaitTime(int(p.ticketsExpected / 10), True)
                                    switch=True
                                    break
                            if not switch:
                                p.SetNumberOfExpectedTickets(numpy.random.normal(100, 30))
                                cashBooth[i].AddToLine(p)
                                totalCustomers += 1
                                cashBooth[i].line.AddWaitTime(int(p.ticketsExpected / 10), False)

                    poisson = False

        if clock >= 1800:
            for i in range(0,numberOfExpressStalls):
                if expressBooth[i].line.lineWaitTime != 0:
                    expressBooth[i].line.DecrementWaitTime()

            for i in range(0,numberOfCashStalls):
                if cashBooth[i].line.lineWaitTime != 0:
                    cashBooth[i].line.DecrementWaitTime()

            arr = stallsAndGames.UpdateCustomerTimes()
            for person in arr:
                numberOfPeopleAtFair=RandomizeLocation(person,numberOfPeopleAtFair)
            if hold:
                hold = False
            for i in range(0,numberOfExpressStalls):
                if not expressBooth[i].inProgress:
                    expressBooth[i].InitializeTransaction()
                else:
                    if expressBooth[i].GetProgress():
                        person = expressBooth[i].FinishTransaction()
                        totalTickets+=person.GetNumberOfTickets()
                        numberOfPeopleAtFair += 1
                        numberOfPeopleAtFair=RandomizeLocation(person,numberOfPeopleAtFair)
            for i in range(0,numberOfCashStalls):
                if not cashBooth[i].inProgress:
                    cashBooth[i].InitializeTransaction()
                else:
                    if cashBooth[i].GetProgress():
                        person = cashBooth[i].FinishTransaction()
                        totalTickets += person.GetNumberOfTickets()
                        numberOfPeopleAtFair += 1
                        numberOfPeopleAtFair=RandomizeLocation(person,numberOfPeopleAtFair)

            if not rollerCoaster.running:
                if rollerCoaster.line.GetNumberOfPeople()>0:
                    rollerCoaster.StartSetup()
            else:
                if rollerCoaster.CheckIfReady() or rollerCoaster.currentRuntime > 0:
                    rollerCoaster.CheckIfDone()
            if rollerCoaster.postRide:
                if rollerCoaster.UpdatePostRiders():
                    for person in rollerCoaster.car:
                        numberOfPeopleAtFair=RandomizeLocation(person,numberOfPeopleAtFair)

            if not merryGoRound.running:
                if merryGoRound.line.GetNumberOfPeople()>0:
                    merryGoRound.StartSetup()
            else:
                if merryGoRound.CheckIfReady() or merryGoRound.currentRuntime > 0:
                    merryGoRound.CheckIfDone()
            if merryGoRound.postRide:

                if merryGoRound.UpdatePostRiders():
                    for person in merryGoRound.mgr:
                        numberOfPeopleAtFair=RandomizeLocation(person,numberOfPeopleAtFair)
            if clock % 300 == 0 and clock < 23400:
                denominator+=1
                for i in range(0,numberOfExpressStalls):
                    avgExpressLineWaitTime+=expressBooth[i].line.lineWaitTime
                    avgLengths[0]+=expressBooth[i].line.GetNumberOfPeople()
                for i in range(0,numberOfCashStalls):
                    avgCashLineWaitTime+=cashBooth[i].line.lineWaitTime
                    avgLengths[1] += cashBooth[i].line.GetNumberOfPeople()
                avgLengths[2]+=rollerCoaster.line.GetNumberOfPeople()
                avgLengths[3]+=merryGoRound.line.GetNumberOfPeople()
                avgRollerCoasterWaitTime+=rollerCoaster.GetCurrentWaitTime()
                avgMerryGoRoundWaitTime+=merryGoRound.GetCurrentWaitTime()
                avgPeopleInFair+=numberOfPeopleAtFair

    for i in range(0,4):
        avgLengths[i]=int(avgLengths[i]/denominator)
        totalAvgLengths[i]+=avgLengths[i]
    totalAvgExpressLineWaitTime+=int(avgExpressLineWaitTime/(numberOfExpressStalls*denominator))
    totalAvgCashLineWaitTime+=int(avgCashLineWaitTime/(numberOfCashStalls*denominator))
    totalAvgRollerCoasterWaitTime+=int(avgRollerCoasterWaitTime/denominator)
    totalAvgMerryGoRoundWaitTime+=int(avgMerryGoRoundWaitTime/denominator)
    totalAvgPeopleInFair+=int(avgPeopleInFair/denominator)
    totalAvgWastedTickets+=(totalTickets-rollerCoaster.totalTicketsReceived-merryGoRound.totalTicketsReceived-stallsAndGames.totalTicketsReceived)
    expenses=(rollerCoaster.numberOfOperators+merryGoRound.numberOfOperators+stallsAndGames.numberOfOperators)*80
    expenses+=(numberOfCashStalls*70)
    if merryGoRound.capacity==30:
        expenses+=5000
    elif merryGoRound==15:
        expenses-=2000
    profit -= expenses
    for i in range(0,numberOfExpressStalls):
        profit+=expressBooth[i].total
    for i in range(0,numberOfCashStalls):
        profit+=cashBooth[i].total
print("avg line for express: " + str(int(totalAvgLengths[0]/100)))
print("avg line for cash: " + str(int(totalAvgLengths[1]/100)))
print("avg line for coaster: " + str(int(totalAvgLengths[2]/100)))
print("avg line for merry: " + str(int(totalAvgLengths[3]/100)))
print("avg people in fair: " + str(int(totalAvgPeopleInFair/100)))
print("avg express line wait time: " + str(int(totalAvgExpressLineWaitTime/100)))
print("avg cash line wait time: " + str(int(totalAvgCashLineWaitTime/100)))
print("avg roller coaster wait time: " + str(int(totalAvgRollerCoasterWaitTime/100)))
print("avg merry go round wait time: " + str(int(totalAvgMerryGoRoundWaitTime/100)))
print("Tickets wasted: " + str(int(totalAvgWastedTickets/100)))
print("profit: " + str(profit))
