from Plot import *
from Robot import *
import sys
import math

filename=sys.argv[1]
algorithmChoice=int(sys.argv[2])
runs=1
totalRuns=1
try:            #Handles having just 2 arguments
    totalRuns=int(sys.argv[3])
except IndexError:
    print "Number of runs was not specified. Setting totalRuns to default.(1)"

averageCrops=0
#will run totalRuns times
while runs<=totalRuns:
    p = Plot()      #create plot
    robot = Robot()         #create robot
    walls = []
    walls.append([])
    walls = p.RetrievePlot(filename, walls)
    runs+=1
    if algorithmChoice == 1 or algorithmChoice == 5:            #These algorithms are similar. They run the same code but altered slightly.
        highestPercentLocation=(0,0)
        highestPercent=0
        bestCrop=(0,0)
        if algorithmChoice == 5:            #Algorithm 5 finds the best coordinate for crops that have highest probability of growing the most
            bestCrop = (0, 0)
            bestTime = 999
            for crop in p.cropCoordinates:              #algorithm for calculating the location
                percent = p.plotDictionary[crop][0]
                avg = math.ceil(1 / percent)
                refresh = (percent ** 2) * 100
                total = avg + refresh
                if total < bestTime:
                    bestTime = total
                    bestCrop = crop
        elif algorithmChoice == 1:      #If algorithm 1, find the highest percentage crop location
            highestPercent=0
            highestPercentLocation=(0,0)
            for j in p.cropCoordinates:
                if highestPercent < p.plotDictionary[j][0]:
                    highestPercentLocation=j
                    highestPercent=p.plotDictionary[j][0]
        count = 0
        time = (p.xLimit * p.yLimit) * .25          #time unit for this algorithm. If needed, comment this and uncomment the next to test different times
        #time=500
        visitedCoordinates = []         #Holds the coordinates we have already been to so we dont go over them more than once.
        wallCoordinates = []            #Holds the coordinates of the walls. Not counting the outer walls.
        traverse = []                   #traverse holds the coordinates to help manuever around walls
        wallFound = False               #Holds whether or not we have run into a wall.
        found = False                   #If the location is found, we will make this true.
        while count <= time:
            if found != True:
                wallFound = False
                visitedCoordinates = [robot.GetLocation()]
                if algorithmChoice == 5:
                    currentEndpoint = bestCrop
                elif algorithmChoice == 1:
                    currentEndpoint = highestPercentLocation
                prevCoord = (0, 0)         #Holds previous coordinate in order to check for
                while robot.GetLocation() != currentEndpoint and count<=time:       #move the robot until you hit the location or time runs out
                    if prevCoord == robot.GetLocation():
                        visitedCoordinates = [robot.GetLocation()]
                    prevCoord = robot.GetLocation()
                    p.UpdatePlot()      #Update the plot

                    #The following if statements check for walls in each direction and determines if it has been visited or not.
                    #This will determine which direction the robot will move until it hits a wall.

                    if robot.GetLocation()[0] < currentEndpoint[0] and robot.GetLocation()[1] < currentEndpoint[1] and \
                                    p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1)][
                                        0] != "#" and (
                                robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1) not in visitedCoordinates:
                        robot.MoveNorthEast()
                    elif robot.GetLocation()[0] < currentEndpoint[0] and robot.GetLocation()[1] > currentEndpoint[1] and \
                                    p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1)][
                                        0] != "#" and (
                                robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1) not in visitedCoordinates:
                        robot.MoveSouthEast()
                    elif robot.GetLocation()[0] > currentEndpoint[0] and robot.GetLocation()[1] > currentEndpoint[1] and \
                                    p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1)][
                                        0] != "#" and (
                                robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1) not in visitedCoordinates:
                        robot.MoveSouthWest()
                    elif robot.GetLocation()[0] > currentEndpoint[0] and robot.GetLocation()[1] < currentEndpoint[1] and \
                                    p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1)][
                                        0] != "#" and (
                                robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1) not in visitedCoordinates:
                        robot.MoveNorthWest()
                    elif robot.GetLocation()[0] < currentEndpoint[0] and \
                                    p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1])][
                                        0] != "#" and (
                                robot.GetLocation()[0] + 1, robot.GetLocation()[1]) not in visitedCoordinates:
                        robot.MoveEast()
                    elif robot.GetLocation()[0] > currentEndpoint[0] and \
                                    p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1])][
                                        0] != "#" and (
                                robot.GetLocation()[0] - 1, robot.GetLocation()[1]) not in visitedCoordinates:
                        robot.MoveWest()
                    elif robot.GetLocation()[1] < currentEndpoint[1] and \
                                    p.plotDictionary[(robot.GetLocation()[0], robot.GetLocation()[1] + 1)][
                                        0] != "#" and (
                            robot.GetLocation()[0], robot.GetLocation()[1] + 1) not in visitedCoordinates:
                        robot.MoveNorth()
                    elif robot.GetLocation()[1] > currentEndpoint[1] and \
                                    p.plotDictionary[(robot.GetLocation()[0], robot.GetLocation()[1] - 1)][
                                        0] != "#" and (
                            robot.GetLocation()[0], robot.GetLocation()[1] - 1) not in visitedCoordinates:
                        robot.MoveSouth()
                    else:                       #If it reaches here, a wall has been reached
                        if wallFound == False:
                            wallFound = True
                            x = robot.GetLocation()[0]
                            y = robot.GetLocation()[1]
                            coord = (0, 0)
                            if p.plotDictionary[(x + 1, y)][0] == "#":
                                coord = (x + 1, y)
                            elif p.plotDictionary[(x - 1, y)][0] == "#":
                                coord = (x - 1, y)
                            elif p.plotDictionary[(x, y + 1)][0] == "#":
                                coord = (x, y + 1)
                            elif p.plotDictionary[(x, y - 1)][0] == "#":
                                coord = (x, y - 1)
                            for w in walls:     #find the coordinate of the wall and find the list of coordinates of the wall in the wall list
                                if coord in w:
                                    wallCoordinates = w

                            #We populate the traverse list in order to move the robot around the wall

                            for c in wallCoordinates:
                                if p.plotDictionary[(c[0] + 1, c[1])][0] != "#" and (
                                    c[0] + 1, c[1]) not in traverse and (
                                            c[0] + 1, c[1]) not in wallCoordinates:
                                    traverse.append((c[0] + 1, c[1]))
                                if p.plotDictionary[(c[0] - 1, c[1])][0] != "#" and (
                                    c[0] - 1, c[1]) not in traverse and (
                                            c[0] - 1, c[1]) not in wallCoordinates:
                                    traverse.append((c[0] - 1, c[1]))
                                if p.plotDictionary[(c[0], c[1] + 1)][0] != "#" and (
                                c[0], c[1] + 1) not in traverse and (
                                        c[0], c[1] + 1) not in wallCoordinates:
                                    traverse.append((c[0], c[1] + 1))
                                if p.plotDictionary[(c[0], c[1] - 1)][0] != "#" and (
                                c[0], c[1] - 1) not in traverse and (
                                        c[0], c[1] - 1) not in wallCoordinates:
                                    traverse.append((c[0], c[1] - 1))
                                if p.plotDictionary[(c[0] + 1, c[1] + 1)][0] != "#" and (
                                            c[0] + 1, c[1] + 1) not in traverse and (
                                            c[0] + 1, c[1] + 1) not in wallCoordinates:
                                    traverse.append((c[0] + 1, c[1] + 1))
                                if p.plotDictionary[(c[0] - 1, c[1] + 1)][0] != "#" and (
                                            c[0] - 1, c[1] + 1) not in traverse and (
                                            c[0] - 1, c[1] + 1) not in wallCoordinates:
                                    traverse.append((c[0] - 1, c[1] + 1))
                                if p.plotDictionary[(c[0] + 1, c[1] - 1)][0] != "#" and (
                                            c[0] + 1, c[1] - 1) not in traverse and (
                                            c[0] + 1, c[1] - 1) not in wallCoordinates:
                                    traverse.append((c[0] + 1, c[1] - 1))
                                if p.plotDictionary[(c[0] - 1, c[1] - 1)][0] != "#" and (
                                            c[0] - 1, c[1] - 1) not in traverse and (
                                            c[0] - 1, c[1] - 1) not in wallCoordinates:
                                    traverse.append((c[0] - 1, c[1] - 1))

                        #Use same traversal code but this time check to also see if its in the traverse list. We dont move in relation to the endpoint.

                        if p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1)][0] != "#" and (
                                    robot.GetLocation()[0] + 1,
                                    robot.GetLocation()[1] + 1) not in visitedCoordinates and (
                                    robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1) in traverse:
                            robot.MoveNorthEast()
                        elif p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1)][0] != "#" and (
                                    robot.GetLocation()[0] + 1,
                                    robot.GetLocation()[1] - 1) not in visitedCoordinates and (
                                    robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1) in traverse:
                            robot.MoveSouthEast()
                        elif p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1)][0] != "#" and (
                                    robot.GetLocation()[0] - 1,
                                    robot.GetLocation()[1] - 1) not in visitedCoordinates and (
                                    robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1) in traverse:
                            robot.MoveSouthWest()
                        elif p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1)][0] != "#" and (
                                    robot.GetLocation()[0] - 1,
                                    robot.GetLocation()[1] + 1) not in visitedCoordinates and (
                                    robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1) in traverse:
                            robot.MoveNorthWest()
                        elif p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1])][0] != "#" and (
                                    robot.GetLocation()[0] + 1, robot.GetLocation()[1]) not in visitedCoordinates and (
                                    robot.GetLocation()[0] + 1, robot.GetLocation()[1]) in traverse:
                            robot.MoveEast()
                        elif p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1])][0] != "#" and (
                                    robot.GetLocation()[0] - 1, robot.GetLocation()[1]) not in visitedCoordinates and (
                                    robot.GetLocation()[0] - 1, robot.GetLocation()[1]) in traverse:
                            robot.MoveWest()
                        elif p.plotDictionary[(robot.GetLocation()[0], robot.GetLocation()[1] + 1)][0] != "#" and (
                                robot.GetLocation()[0], robot.GetLocation()[1] + 1) not in visitedCoordinates and (
                                robot.GetLocation()[0], robot.GetLocation()[1] + 1) in traverse:
                            robot.MoveNorth()
                        elif p.plotDictionary[(robot.GetLocation()[0], robot.GetLocation()[1] - 1)][0] != "#" and (
                                robot.GetLocation()[0], robot.GetLocation()[1] - 1) not in visitedCoordinates and (
                                robot.GetLocation()[0], robot.GetLocation()[1] - 1) in traverse:
                            robot.MoveSouth()
                    count += 1
                    visitedCoordinates.append(robot.GetLocation())
                    if robot.CheckForCrop(p.plotDictionary):        #check for a crop
                        p.RemoveCrop(robot.x, robot.y)
                found = True
            else:
                if algorithmChoice == 1:        #if algorithm 1 just sit there and collect. If 5, follow the collection algorithm below.
                    while count <= time:
                        p.UpdatePlot()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x,robot.y)
                        count+=1
                elif algorithmChoice == 5:      #Ran out of time to fix. Only works if current location has all 8 surrounding tiles as plots and not walls
                    iter = 0
                    while count <= time:
                        p.UpdatePlot()
                        if iter == 0:
                            robot.MoveNorth()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter += 1
                            count += 1
                        elif iter == 1:
                            robot.MoveEast()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter += 1
                            count += 1
                        elif iter == 2:
                            robot.MoveSouth()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter += 1
                            count += 1
                        elif iter == 3:
                            robot.MoveSouth()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter += 1
                            count += 1
                        elif iter == 4:
                            robot.MoveWest()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter += 1
                            count += 1
                        elif iter == 5:
                            robot.MoveWest()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter += 1
                            count += 1
                        elif iter == 6:
                            robot.MoveNorth()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter += 1
                            count += 1
                        elif iter == 7:
                            robot.MoveNorth()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter += 1
                            count += 1
                        elif iter == 8:
                            robot.MoveSouthEast()
                            if robot.CheckForCrop(p.plotDictionary):
                                p.RemoveCrop(robot.x, robot.y)
                            iter = 0
                            count += 1
        #print "Number of crops found = " + str(robot.numberOfCrops)            #Uncomment this to see total per run
        averageCrops+=robot.numberOfCrops   #add so we can average the values at the end.
    elif algorithmChoice == 2:                  #Algorithm 2 begins running here
        time = (p.xLimit * p.yLimit) * .25
        #time=500
        count = 0
        while count != time:
            rand = random.random()          #Get a random number to determine direction
            rand *= 100
            if rand < 12.5:                 #rand will determine direction.
                while count != time:        #Go until time runs out or hit a wall
                    p.UpdatePlot()
                    current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                    current[1] += 1
                    check = (current[0], current[1])
                    if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                        robot.MoveNorth()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x, robot.y)
                        count += 1
                    else:
                        break
            elif rand >= 12.5 and rand < 25:        #Same as above comment, just different direction
                while count != time:
                    p.UpdatePlot()
                    current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                    current[1] += 1
                    current[0] += 1
                    check = (current[0], current[1])
                    if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                        robot.MoveNorthEast()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x, robot.y)
                        count += 1
                    else:
                        break
            elif rand >= 25 and rand < 37.5:
                while count != time:
                    p.UpdatePlot()
                    current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                    current[0] += 1
                    check = (current[0], current[1])
                    if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                        robot.MoveEast()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x, robot.y)
                        count += 1
                    else:
                        break
            elif rand >= 37.5 and rand < 50:
                while count != time:
                    p.UpdatePlot()
                    current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                    current[1] -= 1
                    current[0] += 1
                    check = (current[0], current[1])
                    if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                        robot.MoveSouthEast()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x, robot.y)
                        count += 1
                    else:
                        break
            elif rand >= 50 and rand < 62.5:
                while count != time:
                    p.UpdatePlot()
                    current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                    current[1] -= 1
                    check = (current[0], current[1])
                    if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                        robot.MoveSouth()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x, robot.y)
                        count += 1
                    else:
                        break
            elif rand >= 62.5 and rand < 75:
                while count != time:
                    p.UpdatePlot()
                    current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                    current[1] -= 1
                    current[0] -= 1
                    check = (current[0], current[1])
                    if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                        robot.MoveSouthWest()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x, robot.y)
                        count += 1
                    else:
                        break
            elif rand >= 75 and rand < 87.5:
                while count != time:
                    p.UpdatePlot()
                    current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                    current[0] -= 1
                    check = (current[0], current[1])
                    if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                        robot.MoveWest()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x, robot.y)
                        count += 1
                    else:
                        break
            elif rand >= 87.5:
                while count != time:
                    p.UpdatePlot()
                    current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                    current[1] += 1
                    current[0] -= 1
                    check = (current[0], current[1])
                    if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                        robot.MoveNorthWest()
                        if robot.CheckForCrop(p.plotDictionary):
                            p.RemoveCrop(robot.x, robot.y)
                        count += 1
                    else:
                        break
        #print "Number of crops found = " + str(robot.numberOfCrops)
        averageCrops+=robot.numberOfCrops
    elif algorithmChoice == 3:                  #Choose random direction every unit of time
        time = (p.xLimit * p.yLimit) * .25
        #time=500
        count = 0
        while count != time:            #Similar to above but instead of while inside of if statements, its outside.
            p.UpdatePlot()              #Update plot and randomize direction every turn
            rand = random.random()
            rand *= 100
            if rand < 12.5:
                current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                current[1] += 1
                check = (current[0], current[1])
                if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                    robot.MoveNorth()
                    if robot.CheckForCrop(p.plotDictionary):
                        p.RemoveCrop(robot.x, robot.y)
            elif rand >= 12.5 and rand < 25:
                current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                current[1] += 1
                current[0] += 1
                check = (current[0], current[1])
                if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                    robot.MoveNorthEast()
                    if robot.CheckForCrop(p.plotDictionary):
                        p.RemoveCrop(robot.x, robot.y)
            elif rand >= 25 and rand < 37.5:
                current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                current[0] += 1
                check = (current[0], current[1])
                if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                    robot.MoveEast()
                    if robot.CheckForCrop(p.plotDictionary):
                        p.RemoveCrop(robot.x, robot.y)
            elif rand >= 37.5 and rand < 50:
                current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                current[1] -= 1
                current[0] += 1
                check = (current[0], current[1])
                if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                    robot.MoveSouthEast()
                    if robot.CheckForCrop(p.plotDictionary):
                        p.RemoveCrop(robot.x, robot.y)
            elif rand >= 50 and rand < 62.5:
                current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                current[1] -= 1
                check = (current[0], current[1])
                if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                    robot.MoveSouth()
                    if robot.CheckForCrop(p.plotDictionary):
                        p.RemoveCrop(robot.x, robot.y)
            elif rand >= 62.5 and rand < 75:
                current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                current[1] -= 1
                current[0] -= 1
                check = (current[0], current[1])
                if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                    robot.MoveSouthWest()
                    if robot.CheckForCrop(p.plotDictionary):
                        p.RemoveCrop(robot.x, robot.y)
            elif rand >= 75 and rand < 87.5:
                current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                current[0] -= 1
                check = (current[0], current[1])
                if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                    robot.MoveWest()
                    if robot.CheckForCrop(p.plotDictionary):
                        p.RemoveCrop(robot.x, robot.y)
            else:
                current = [robot.GetLocation()[0], robot.GetLocation()[1]]
                current[1] += 1
                current[0] -= 1
                check = (current[0], current[1])
                if p.plotDictionary[check][0] != "#" and check[0] != 0 and check[1] != 0:
                    robot.MoveNorthWest()
                    if robot.CheckForCrop(p.plotDictionary):
                        p.RemoveCrop(robot.x, robot.y)
            count += 1
        #print "Number of crops found = " + str(robot.numberOfCrops)
        averageCrops+=robot.numberOfCrops
    elif algorithmChoice == 4:              #This algorithm finds a location and goes to said location. Uses same traverse technique as 1 and 5
        count = 0
        time = (p.xLimit * p.yLimit) * .25
        #time = 500
        visitedCoordinates = []         #similar variables as algorithm 1,5
        wallCoordinates = []
        traverse = []
        wallFound = False
        while count != time:
            wallFound = False
            visitedCoordinates = [robot.GetLocation()]
            arrayLength = len(p.cropCoordinates)
            rand = random.random()
            rand *= 100
            rand = int(rand % arrayLength)
            currentEndpoint = p.cropCoordinates[rand]           #Get a random coordinate containing a crop
            prevCoord = (0, 0)

            # Same as algorithm 1,5. Determine which direction to go until hit a wall
            # However, after reaching the random location, we choose another coordinate to go to, which is why we use the while loop

            while robot.GetLocation() != currentEndpoint and count != time:
                if prevCoord == robot.GetLocation():
                    visitedCoordinates = [robot.GetLocation()]
                prevCoord = robot.GetLocation()
                p.UpdatePlot()
                if robot.GetLocation()[0] < currentEndpoint[0] and robot.GetLocation()[1] < currentEndpoint[1] and \
                                p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1)][
                                    0] != "#" and (
                    robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1) not in visitedCoordinates:
                    robot.MoveNorthEast()
                elif robot.GetLocation()[0] < currentEndpoint[0] and robot.GetLocation()[1] > currentEndpoint[1] and \
                                p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1)][
                                    0] != "#" and (
                    robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1) not in visitedCoordinates:
                    robot.MoveSouthEast()
                elif robot.GetLocation()[0] > currentEndpoint[0] and robot.GetLocation()[1] > currentEndpoint[1] and \
                                p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1)][
                                    0] != "#" and (
                    robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1) not in visitedCoordinates:
                    robot.MoveSouthWest()
                elif robot.GetLocation()[0] > currentEndpoint[0] and robot.GetLocation()[1] < currentEndpoint[1] and \
                                p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1)][
                                    0] != "#" and (
                    robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1) not in visitedCoordinates:
                    robot.MoveNorthWest()
                elif robot.GetLocation()[0] < currentEndpoint[0] and \
                                p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1])][0] != "#" and (
                    robot.GetLocation()[0] + 1, robot.GetLocation()[1]) not in visitedCoordinates:
                    robot.MoveEast()
                elif robot.GetLocation()[0] > currentEndpoint[0] and \
                                p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1])][0] != "#" and (
                    robot.GetLocation()[0] - 1, robot.GetLocation()[1]) not in visitedCoordinates:
                    robot.MoveWest()
                elif robot.GetLocation()[1] < currentEndpoint[1] and \
                                p.plotDictionary[(robot.GetLocation()[0], robot.GetLocation()[1] + 1)][0] != "#" and (
                robot.GetLocation()[0], robot.GetLocation()[1] + 1) not in visitedCoordinates:
                    robot.MoveNorth()
                elif robot.GetLocation()[1] > currentEndpoint[1] and \
                                p.plotDictionary[(robot.GetLocation()[0], robot.GetLocation()[1] - 1)][0] != "#" and (
                robot.GetLocation()[0], robot.GetLocation()[1] - 1) not in visitedCoordinates:
                    robot.MoveSouth()
                else:                           #if running into the wall, make traverse wall and continue
                    if wallFound == False:
                        wallFound = True
                        x = robot.GetLocation()[0]
                        y = robot.GetLocation()[1]
                        coord = (0, 0)
                        if p.plotDictionary[(x + 1, y)][0] == "#":
                            coord = (x + 1, y)
                        elif p.plotDictionary[(x - 1, y)][0] == "#":
                            coord = (x - 1, y)
                        elif p.plotDictionary[(x, y + 1)][0] == "#":
                            coord = (x, y + 1)
                        elif p.plotDictionary[(x, y - 1)][0] == "#":
                            coord = (x, y - 1)
                        for w in walls:
                            if coord in w:
                                wallCoordinates = w
                        for c in wallCoordinates:
                            if p.plotDictionary[(c[0] + 1, c[1])][0] != "#" and (c[0] + 1, c[1]) not in traverse and (
                                c[0] + 1, c[1]) not in wallCoordinates:
                                traverse.append((c[0] + 1, c[1]))
                            if p.plotDictionary[(c[0] - 1, c[1])][0] != "#" and (c[0] - 1, c[1]) not in traverse and (
                                c[0] - 1, c[1]) not in wallCoordinates:
                                traverse.append((c[0] - 1, c[1]))
                            if p.plotDictionary[(c[0], c[1] + 1)][0] != "#" and (c[0], c[1] + 1) not in traverse and (
                            c[0], c[1] + 1) not in wallCoordinates:
                                traverse.append((c[0], c[1] + 1))
                            if p.plotDictionary[(c[0], c[1] - 1)][0] != "#" and (c[0], c[1] - 1) not in traverse and (
                            c[0], c[1] - 1) not in wallCoordinates:
                                traverse.append((c[0], c[1] - 1))
                            if p.plotDictionary[(c[0] + 1, c[1] + 1)][0] != "#" and (
                                c[0] + 1, c[1] + 1) not in traverse and (c[0] + 1, c[1] + 1) not in wallCoordinates:
                                traverse.append((c[0] + 1, c[1] + 1))
                            if p.plotDictionary[(c[0] - 1, c[1] + 1)][0] != "#" and (
                                c[0] - 1, c[1] + 1) not in traverse and (c[0] - 1, c[1] + 1) not in wallCoordinates:
                                traverse.append((c[0] - 1, c[1] + 1))
                            if p.plotDictionary[(c[0] + 1, c[1] - 1)][0] != "#" and (
                                c[0] + 1, c[1] - 1) not in traverse and (c[0] + 1, c[1] - 1) not in wallCoordinates:
                                traverse.append((c[0] + 1, c[1] - 1))
                            if p.plotDictionary[(c[0] - 1, c[1] - 1)][0] != "#" and (
                                c[0] - 1, c[1] - 1) not in traverse and (c[0] - 1, c[1] - 1) not in wallCoordinates:
                                traverse.append((c[0] - 1, c[1] - 1))
                    if p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1)][0] != "#" and (
                        robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1) not in visitedCoordinates and (
                        robot.GetLocation()[0] + 1, robot.GetLocation()[1] + 1) in traverse:
                        robot.MoveNorthEast()
                    elif p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1)][0] != "#" and (
                        robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1) not in visitedCoordinates and (
                        robot.GetLocation()[0] + 1, robot.GetLocation()[1] - 1) in traverse:
                        robot.MoveSouthEast()
                    elif p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1)][0] != "#" and (
                        robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1) not in visitedCoordinates and (
                        robot.GetLocation()[0] - 1, robot.GetLocation()[1] - 1) in traverse:
                        robot.MoveSouthWest()
                    elif p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1)][0] != "#" and (
                        robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1) not in visitedCoordinates and (
                        robot.GetLocation()[0] - 1, robot.GetLocation()[1] + 1) in traverse:
                        robot.MoveNorthWest()
                    elif p.plotDictionary[(robot.GetLocation()[0] + 1, robot.GetLocation()[1])][0] != "#" and (
                        robot.GetLocation()[0] + 1, robot.GetLocation()[1]) not in visitedCoordinates and (
                        robot.GetLocation()[0] + 1, robot.GetLocation()[1]) in traverse:
                        robot.MoveEast()
                    elif p.plotDictionary[(robot.GetLocation()[0] - 1, robot.GetLocation()[1])][0] != "#" and (
                        robot.GetLocation()[0] - 1, robot.GetLocation()[1]) not in visitedCoordinates and (
                        robot.GetLocation()[0] - 1, robot.GetLocation()[1]) in traverse:
                        robot.MoveWest()
                    elif p.plotDictionary[(robot.GetLocation()[0], robot.GetLocation()[1] + 1)][0] != "#" and (
                    robot.GetLocation()[0], robot.GetLocation()[1] + 1) not in visitedCoordinates and (
                    robot.GetLocation()[0], robot.GetLocation()[1] + 1) in traverse:
                        robot.MoveNorth()
                    elif p.plotDictionary[(robot.GetLocation()[0], robot.GetLocation()[1] - 1)][0] != "#" and (
                    robot.GetLocation()[0], robot.GetLocation()[1] - 1) not in visitedCoordinates and (
                    robot.GetLocation()[0], robot.GetLocation()[1] - 1) in traverse:
                        robot.MoveSouth()
                count += 1
                visitedCoordinates.append(robot.GetLocation())
                if robot.CheckForCrop(p.plotDictionary):
                    p.RemoveCrop(robot.x, robot.y)
        #print "Number of crops found = " + str(robot.numberOfCrops)
        averageCrops+=robot.numberOfCrops
    else:
        print "Algorithm not found."
averageCrops=float(float(averageCrops)/float(totalRuns))
print "Average number of crops found per run = " + str(averageCrops)