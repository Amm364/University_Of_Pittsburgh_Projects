class Robot:
    def __init__(self):         #Initialize robot
        self.numberOfCrops=0
        self.stepsTaken=0
        self.x=1
        self.y=1
        self.currentDirection=""

    def CheckForCrop(self,plot):            #Checks for crops and increments crop number
        if plot[(self.x,self.y)][1]=="!":
            self.numberOfCrops+=1
            return True
        else:
            return False

    def GetLocation(self):          #Returns the robots current location in a tuple
        return (self.x,self.y)

    def MoveNorth(self):
        self.y+=1
        self.stepsTaken+=1

    def MoveNorthEast(self):
        self.y+=1
        self.x+=1
        self.stepsTaken += 1

    def MoveEast(self):
        self.x+=1
        self.stepsTaken += 1

    def MoveSouthEast(self):
        self.x+=1
        self.y-=1
        self.stepsTaken += 1

    def MoveSouth(self):
        self.y-=1
        self.stepsTaken += 1

    def MoveSouthWest(self):
        self.y-=1
        self.x-=1
        self.stepsTaken += 1

    def MoveWest(self):
        self.x-=1
        self.stepsTaken += 1

    def MoveNorthWest(self):
        self.x-=1
        self.y+=1
        self.stepsTaken += 1


