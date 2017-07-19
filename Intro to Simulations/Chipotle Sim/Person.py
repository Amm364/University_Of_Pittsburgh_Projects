
class Person:
    def __init__(self,time,food):
        self.totalTime = 0
        self.totalExpectedTransactionTime = time
        self.currentTransactionTime = 0
        self.isPaying = False
        self.timeSpentPaying = 0
        self.foodChoice=food

    def IncrementTotalTime(self):
        self.totalTime += 1

    def IncrementTransactionTime(self):
        self.currentTransactionTime += 1

    def IncrementPayTime(self):
        self.timeSpentPaying += 1
