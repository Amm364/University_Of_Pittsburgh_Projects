from Line import Line

class RollerCoaster:

    def __init__(self):
        self.ticketCost=16
        self.trainCarLimit=60
        self.totalRuntime=180
        self.numberOfOperators=1
        self.line=Line()
        self.currentRuntime=0
        self.setupTime=0
        self.running=False
        self.numberOfPeopleGettingOnRide=0
        self.car = []
        self.totalTicketsReceived=0
        self.postRide = False
        self.postRideTime = 30

    def StartSetup(self):
        if self.line.GetNumberOfPeople()>=60:
            self.numberOfPeopleGettingOnRide=60
            self.setupTime = 60
        else:
            self.numberOfPeopleGettingOnRide=self.line.GetNumberOfPeople()
            self.setupTime = 60
        self.running=True

    def CheckIfReady(self):
        self.setupTime-=1
        if self.setupTime==0:
            self.car=[]
            for i in range(0,self.numberOfPeopleGettingOnRide):
                person=self.line.GetPerson()
                self.line.DecrementLineCount()
                person.UseTickets(16)
                self.totalTicketsReceived+=16
                self.car.append(person)
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
        numberOfCars = 0
        if int(self.line.GetNumberOfPeople() / self.trainCarLimit) == self.line.GetNumberOfPeople() / self.trainCarLimit:
            numberOfCars = int(self.line.GetNumberOfPeople() / self.trainCarLimit)
        else:
            numberOfCars = int(self.line.GetNumberOfPeople() / self.trainCarLimit) + 1
        rollerCoasterWaitTime = (numberOfCars * 240)
        if self.running:
            rollerCoasterWaitTime += (180 - self.currentRuntime)
        else:
            rollerCoasterWaitTime += 180 + self.setupTime
        return rollerCoasterWaitTime