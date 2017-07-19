from Line import Line
from Person import Person

class CashBooth:
    def __init__(self):
        self.total=0
        self.ticketsCurrentlyBeingSold=0
        self.costOfTicketsBeingSold=0
        self.timeLeftWithCustomer=0
        self.timeNeededToBuyTickets=0
        self.costPerTicket=0.25
        self.line = Line()
        self.avgTransactionTime=120
        self.inProgress = False
        self.person = Person()

    def InitializeTransaction(self):
        if self.line.HasNext():
            self.ticketsCurrentlyBeingSold = 150
            self.costOfTicketsBeingSold=float(150 * 0.25)
            self.person = self.line.GetPerson()
            self.timeNeededToBuyTickets = (150 / 10) + self.avgTransactionTime
            self.timeLeftWithCustomer=self.timeNeededToBuyTickets
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
        self.total+=self.costOfTicketsBeingSold
        self.inProgress=False
        self.person.AddTickets(self.ticketsCurrentlyBeingSold)
        self.line.DecrementLineCount()
        return self.person

    def GetTotal(self):
        return self.total

    def AddToLine(self,person):
        self.line.AddPerson(person)