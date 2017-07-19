from Person import Person

class GameStalls:

    def __init__(self,nos):
        self.numberOfStalls=nos
        self.stalls=nos
        self.capacity=nos*5
        self.numberOfOperators=nos
        self.postGameTime=30
        self.currentNumberOfCustomers=0
        self.customers=[]
        self.totalTicketsReceived=0

    def CheckCapacity(self):
        if self.currentNumberOfCustomers!=self.capacity:
            return True
        else:
            return False

    def AddCustomer(self,person):
        person.time=30
        self.currentNumberOfCustomers+=1
        self.customers.append(person)

    def UpdateCustomerTimes(self):
        finished = []
        for person in self.customers:
            person.time-=1
            if person.time==0:
                person.UseTickets(1)
                self.totalTicketsReceived+=1
                self.customers.remove(person)
                self.currentNumberOfCustomers-=1
                finished.append(person)
        return finished