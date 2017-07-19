from queue import *

class Line:
    def __init__(self):
        self.queue = Queue(50)
        self.numberOfPeopleInLine=0
        self.lineWaitTime=0

    def AddPerson(self,person):
        self.queue.put(person)
        self.numberOfPeopleInLine+=1

    def GetPerson(self):
        return self.queue.get()

    def HasNext(self):
        if self.queue.empty():
            return False
        else:
            return True

    def GetNumberOfPeople(self):
        return self.numberOfPeopleInLine

    def DecrementLineCount(self):
        self.numberOfPeopleInLine-=1

    def AddWaitTime(self,num):
        self.lineWaitTime+=num

    def DecrementWaitTime(self):
        self.lineWaitTime-=1