
class Person:
    def __init__(self):
        self.numberOfTickets=0
        self.ticketsExpected=0
        self.time=0              #time added when person gets off ride/gets belongings/ect


    def GetNumberOfTickets(self):
        return self.numberOfTickets

    def AddTickets(self,numOfTickets):
        self.numberOfTickets=numOfTickets

    def UseTickets(self,numberOfTicketsUsed):
        self.numberOfTickets-=numberOfTicketsUsed

    def SetNumberOfExpectedTickets(self,num):
        self.ticketsExpected=num

    def SetAddedTime(self,time):
        self.time+=time

    def DecrementAddedTime(self):
        self.time-=1