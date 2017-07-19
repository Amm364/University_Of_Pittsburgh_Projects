from Line import Line
from Person import Person

class ExpressBooth:

    def __init__(self):
        self.inProgress=False
        self.total=0
        self.ticketsCurrentlyBeingSold=200
        self.costOfTicketsBeingSold=50.00
        self.timeToBuyTickets=15
        self.timeLeftWithCustomer=0
        self.line = Line()
        self.person = Person()

    def InitializeTransaction(self):
        if self.line.HasNext():
            self.person = self.line.GetPerson()
            self.timeLeftWithCustomer=self.timeToBuyTickets
            self.inProgress=True
            return True
        else:
            return False

    def GetProgress(self):
        self.timeLeftWithCustomer-=1
        if self.timeLeftWithCustomer==0:
            return True
        else:
            return False

    def FinishTransaction(self):
        self.total+=50.00
        self.inProgress=False
        self.person.AddTickets(200)
        self.line.DecrementLineCount()
        return self.person

    def GetTotal(self):
        return self.total

    def AddToLine(self,person):
        self.line.AddPerson(person)