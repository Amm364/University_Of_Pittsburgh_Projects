from MerryGoRound import MerryGoRound
from Person import Person
from Line import Line
from ExpressBooth import ExpressBooth

eb = ExpressBooth()
for i in range(0,100):
    eb.AddToLine(Person())
    eb.line.AddWaitTime(120,True)
print(eb.line.lineWaitTime)
j = 0
check=True
while eb.line.GetNumberOfPeople()>0 or eb.inProgress:
    eb.line.DecrementWaitTime()
    if j%16==5 and check:
        eb.line.AddPerson(Person())
        print("Added person")
        eb.line.AddWaitTime(120,True)
        check=False
    if not eb.inProgress:
        eb.InitializeTransaction()
    else:
        if eb.GetProgress():
            person = eb.FinishTransaction()
    j+=1

print(eb.line.lineWaitTime)
count=0
