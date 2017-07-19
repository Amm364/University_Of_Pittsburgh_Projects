from Line import Line

class MerryGoRound:

    def __init__(self,capacity):
        self.ticketCost=8
        self.capacity=capacity
        self.totalRuntime=240
        self.numberOfOperators=1
        self.line=Line()
        self.currentRuntime=0
        self.setupTime=0
        self.running=False
        self.numberOfPeopleGettingOnRide=0
        self.mgr = []
        self.totalTicketsReceived=0
        self.postRide = False
        self.postRideTime = 30

    def StartSetup(self):
        if self.line.GetNumberOfPeople()>=self.capacity:
            self.numberOfPeopleGettingOnRide=self.capacity
            self.setupTime = 120
        else:
            self.numberOfPeopleGettingOnRide=self.line.GetNumberOfPeople()
            self.setupTime = 120
        self.running=True

    def CheckIfReady(self):
        self.setupTime-=1
        if self.setupTime==0:
            self.mgr=[]
            for i in range(0,self.numberOfPeopleGettingOnRide):
                person=self.line.GetPerson()
                self.line.DecrementLineCount()
                person.UseTickets(8)
                self.totalTicketsReceived+=8
                self.mgr.append(person)
            return True
        else:
            return False

    def CheckIfDone(self):
        self.currentRuntime+=1
        if self.currentRuntime==self.totalRuntime:
            self.running=False
            self.currentRuntime=0
            self.postRide=True
            return True
        else:
            return False

    def UpdatePostRiders(self):     #riders gather their stuff. 30 seconds
        self.postRideTime-=1
        if self.postRideTime==0:
            self.postRideTime=30
            self.postRide=False
            return True
        else:
            return False

    def GetCurrentWaitTime(self):
        numberOfRides = 0
        if int(self.line.GetNumberOfPeople() / self.capacity) == self.line.GetNumberOfPeople() / self.capacity:
            numberOfRides = int(self.line.GetNumberOfPeople() / self.capacity)
        else:
            numberOfRides = int(self.line.GetNumberOfPeople() / self.capacity) + 1
        merryGoRoundWaitTime = (numberOfRides * 360)
        if self.running:
            merryGoRoundWaitTime += (240 - self.currentRuntime)
        else:
            merryGoRoundWaitTime += 240 + self.setupTime
        return merryGoRoundWaitTime