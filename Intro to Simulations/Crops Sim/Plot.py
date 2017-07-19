import csv
import random


class Plot:                 #Creates the plot that holds all of the coordinates
    def __init__(self):
        self.xLimit = 0
        self.yLimit = 0
        self.arrayPlot = []
        self.plotDictionary = {}
        self.cropCoordinates = []

    def RetrievePlot(self, filename,walls):     #Reads in the plot from the file and store its data in lists
        with open(filename, 'r') as fp:  # Open the file and determine its max X and Y coordinates
            reader = csv.reader(fp)
            initialize = False
            for row in reader:
                if initialize != True:      #Find the limits of the file
                    for i in row:
                        self.xLimit += 1
                    initialize = True
                self.yLimit += 1
        fp.close()
        with open(filename, 'r') as fp:
            reader = csv.reader(fp)
            currentX = 0
            currentY = self.yLimit - 1
            for row in reader:
                for unit in row:
                    self.arrayPlot.append((currentX, currentY))
                    if unit == "#":
                        found=False
                        for w in walls:
                            if currentX==0 or currentX==25 or currentY==0 or currentY==29:
                                found=True
                                break
                            if ((currentX+1,currentY) in w) or ((currentX-1,currentY) in w) or ((currentX+1,currentY+1) in w) or ((currentX-1,currentY-1) in w) or ((currentX,currentY+1) in w) or ((currentX,currentY-1) in w) or ((currentX+1,currentY-1) in w) or ((currentX-1,currentY+1) in w):
                                w.append((currentX,currentY))
                                found=True
                                break
                        if found==False:
                            walls.append([(currentX, currentY)])
                        self.plotDictionary[(currentX, currentY)] = [str(unit), int(0)]
                    else:
                        self.plotDictionary[(currentX, currentY)] = [float(unit), int(0)]
                        if float(unit)>0:
                            self.cropCoordinates.append((currentX,currentY))
                    currentX += 1
                currentY -= 1
                currentX = 0
        fp.close()
        return walls

    def UpdatePlot(self):           #Update the contents of the plot. (Chance to grow/decrement the refresh timer)
        for plot in self.cropCoordinates:
            if self.plotDictionary[plot][1] == 0:  # Probably dont need first comparison but just in case.
                rand = random.random()
                if rand <= float(self.plotDictionary[plot][0]):
                    self.plotDictionary[plot][1] = "!"  # ! represents a crop
            elif self.plotDictionary[plot][0] != "#" and self.plotDictionary[plot][1]!="!" and self.plotDictionary[plot][1] > 0:
                self.plotDictionary[plot][1]-=1

    def RemoveCrop(self,x,y):       #Remove the crop from the dictionary. Crop represented by !
        self.plotDictionary[((x,y))][1]=int(((self.plotDictionary[((x,y))][0])**2)*100)