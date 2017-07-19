# Repository: 2016-09.template.project-3
# Assignment #3: Data Warehousing  

> Course: **[CS 1656 - Introduction to Data Science](http://cs1656.org)** (CS 2056) -- Fall 2016    
> Instructor: [Alexandros Labrinidis](http://labrinidis.cs.pitt.edu)  
> 
> Assignment: #3
> Released: October 11, 2016  
> **Due:      October 30, 2016**

### Description
This is the **third assignment** for the CS 1656 -- Introduction to Data Science (CS 2056) class, for the Fall 2016 semester.

### Goal
The goal of this assignment is for you to gain familiarity with data warehousing and (in the process) to also advance your Python skills further.

---

### What to do -- cubethis.py

In this assignment you are asked to implement a simple data warehousing system in Python, called `cubethis.py`, that loads data from a CSV file, creates a data cube from it, and supports the following operations:  
* **format**, to specify what is the expected format of the input file, e.g.,
```
format year,state,product,sales
```
In the above example, year, state, and product are the *dimension* attributes, whereas sales is the *measure* attribute. For simplicity, attribute names consist of either numbers (0-9) or characters (a-zA-z) or combinations of numbers and characters. No spaces or punctation characters are allowed in attribute names. You are required to make your program general enough to **handle up to 5 dimension attributes and 1 measure attribute**. The last attribute will always be the single measure attribute (e.g., sales in the above example).

* **load**, the operation to load the data from the specified csv file and create a data cube in memory, e.g., 
```
load input.csv
```
For simplicity, you can expect that the format in the input file always matches the format specified with the format command. Also, values for dimension attributes will follow the same rules as for attribute names (i.e., consisting of only numbers and characters), whereas values for measure attributes will be just integers (that are greater or equal to 0). Finally, the CSV files may or may not contain whitespace between values.

After parsing the CSV file, you need to construct a full data cube, that essentially contains the subtotals of all possible combinations of dimension attribute values. For example, for 3 dimension attributes A, B, C, you need to have computed and stored in a data structure the following subtotal types (for all value combinations of the different attributes):
* A, B, C (this should be values from the input file)
* A, B
* B, C
* A, C
* A
* B
* C
* total

Note that there may be some value combinations that do not have any corresponding input. For those cases you should create the subtotal, but put 0 as the sum of the measure attribute.

* **roll up**, eliminates one dimension from the **current cube** through summarization. So if you had a full cube with all the subtotals for A, B, C and did a roll up on dimension A, you would only get subtotals for B, C, plus for B, plus for C (i.e., all the information for A should be aggregated and could not say anything for any specific value of attribute A). For example,
```
rollup product
```
would "collapse" the product dimension from the original cube. If this is followed by another roll up operation, e.g.,
```
rollup state
```
then you would need to "collapse" the state dimension from the current state, i.e., from the cube that has the state dimension already "collapsed". 

* **drill down**, is the opposite operation from roll up, where a dimension is added to the **current cube** and detailed breakdowns are available in that dimension. For example,
```
drilldown state
```
would bring back all the information for the state attribute, in the cube that has product and state dimensions previously "collapsed".

* **slice**, is picking a portion of the **current cube** that has a specific value in one dimension. For example,
```
slice state=PA
```
would filter out all information about state (i.e., all subtotals) except for those that have PA as the value of the state attribute. 

* **save**, saves the current state of the cube into the specified file. Here’s an example output file (`output1.csv`):
```
2012,all,all,330
2013,all,all,2776
2014,all,all,1710
2015,all,all,3300
```
Note that the `all` value corresponds to a subtotal with that dimension rolled up. So if were to roll up on the year attribute over the above cube, we will get:
```
all, all, all, 8116
```

### Implementation Notes
The `commands.txt` may have comments; a comment is a line that starts with a `#` symbol. Additionally, empty lines (i.e., a line with just white space characters) should be ignored.

Your program may print additional messages; these will not be considered (unless they are error messages). You are encouraged to use this mechanism for debugging or progress reporting purposes. Only the results contained in the output files, in the specified format, will be considered.  

The repository contains one command file (`commands.txt`), one input file (`input.csv`), four output files (`output1.csv`, ..., `output4.csv`) which are the result of calling the `save` command in different points of the `commands.txt` file. 

Although the command files that we will use for testing will not deviate from the above specification, you should have a way to capture an error if your program does not recognize a command, since this will be really crucial in debugging. 

Also, you **are** required to report an error message if a certain command cannot be executed because the current state of the cube does not permit it. For example, if a slice command follows a roll up command for the same dimension. In that case, your program should terminate with an error message explaining the cause (e.g., `ERROR: cannot execute command slice product=bread; dimension not available in current cube`).

For more details on data cube construction, as well as the roll up, drill down, and slice operations, you should refer to the class material: [cs1656.org/notes.html#07](http://cs1656.org/notes.html#07).

---

### Important notes about grading
It is absolutely imperative that your python program:  
* runs without any syntax or other errors (using Python 2.7) -- we will run it using the following command:  
`python cubethis.py command.txt`  
* strictly adheres to the format specifications for input and output, as explained above.     

Failure in any of the above will result in **severe** point loss. 


### Allowed Python Libraries
You are allowed to use the following Python libraries:
```
argparse
collections
csv
glob
itertools
math 
os
pandas
re
string
sys
```
If you would like to use any other libraries, you must ask permission by Sunday, October 16, 2016, using [piazza](http://piazza.cs1656.org).

---

### How to submit your assignment
For this assignment, you must use the repository that was created for you after visiting the classroom link. You need to update the repository to include file `apriori.py` as described above, and other files that are needed for running your program. You need to make sure to commit your code to the repository provided. We will clone all repositories shortly after midnight:  
* the day of the deadline **Sunday, October 30th, 2016 (i.e., at 12:15am, Monday, October 31, 2016)**  
* 24 hours later (for submissions that are one day late / -5 points), and  
* 48 hours after the first deadline (for submissions that are two days late / -15 points). 

Our assumption is that everybody will submit on the first deadline. If you want us to consider a late submission, you need to email us at `cs1656-staff@cs.pitt.edu`


### About your github account
It is very important that:  
* Your github account can do **private** repositories. If this is not already enabled, you can do it by visiting <https://education.github.com/>  
* You use the same github account for the duration of the course.  
* You use the github account that you specified during the test assignment.    
