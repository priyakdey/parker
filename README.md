# Parker - a CLI-based parking lot management system

`parker` is a CLI-based parking lot management system.

This was an exercise, provided to me years ago, where I was given a problem
statement and asked to provide the solution for the same. I needed to build a
Parking Lot Management tool (cli based).

Tbf, this was a problem asked to solve over the weekend, but this time I will
take my sweet time to complete it !

I will refer back to the (perhaps over-engineered) solution I developed in the
past to observe how I have evolved as an engineer over the last 4-5 years.

### Table of Content

- [Problem Statement](#problem-statement)
- [Setup](#setup)
- [Example](#example)
- [Rules](#rules)
- [Assumptions](#assumptions)
- [LICENSE](#license)

### Problem Statement

I own a parking lot that can hold up to `n` cars at any given point in time.
Each slot is given a number starting at 1 increasing with increasing distance
from the entry point in steps of one. I want to create an automated ticketing
system that allows my customers to use my parking lot without human
intervention.

- When a car enters my parking lot, I want to have a ticket issued to the
  driver. The ticket issuing process includes us documenting the registration
  number (number plate) and the colour of the car and allocating an available
  parking slot to the car before actually handing over a ticket to the driver
  (we assume that our customers are nice enough to always park in the slots
  allocated to them).
- The customer should be allocated a parking slot which is nearest to the entry.
- At the exit the customer returns the ticket with the time the car was parked
  in the lot, which then marks the slot they were using as being available.
- Total parking charge should be calculated as per the parking time. Charge
  applicable is &#x24;10 for first 2 hours and &#x24;10 for every additional
  hour.

We interact with the system via a simple set of commands which produce a
specific output. Please take a look at the example below, which includes all the
commands you need to support - they're self explanatory. The system should
accept a filename as a parameter at the command prompt and read the commands
from that file.

##### Commands:

- Create parking lot of size n : `create_parking_lot {capacity}`
- Park a car : `park {car_number}`
- Remove(Unpark) car from : `leave {car_number} {hours}`
- Print status of parking slot : `status`

### Setup

Later.... !!!!

### Example

- ##### Input (contents of a file):

```text
create_parking_lot 6
park KA-01-HH-1234
park KA-01-HH-9999
park KA-01-BB-0001
park KA-01-HH-7777
park KA-01-HH-2701
park KA-01-HH-3141
leave KA-01-HH-3141 4
status
park KA-01-P-333
park DL-12-AA-9999
leave KA-01-HH-1234 4
leave KA-01-BB-0001 6
leave DL-12-AA-9999 2
park KA-09-HH-0987
park CA-09-IO-1111
park KA-09-HH-0123
status
```

- ##### Output (to STDOUT):

```text
Created parking lot with 6 slots
Allocated slot number: 1
Allocated slot number: 2
Allocated slot number: 3
Allocated slot number: 4
Allocated slot number: 5
Allocated slot number: 6
Registration number KA-01-HH 3141 with Slot Number 6 is free with Charge 30
Slot No. Registration No.
1 KA-01-HH-1234
2 KA-01-HH-9999
3 KA-01-BB-0001
4 KA-01-HH-7777
5 KA-01-HH-2701
Allocated slot number: 6
Sorry, parking lot is full
Registration number KA-01-HH1234 with Slot Number 1 is free with Charge 30 
Registration number KA-01-BB0001 with Slot Number 3 is free with Charge 50 
Registration number DL-12-AA-9999 not found 
Allocated slot number: 1
Allocated slot number: 3
Sorry, parking lot is full
Slot No. Registration No.
1 KA-09-HH-0987
2 KA-01-HH-9999
3 CA-09-IO-1111
```

### Rules

Here are some notable rules mentioned, among others.

- We want to understand how you make assumptions when building software. If a
  particular workflow or boundary condition is not defined in the problem
  statement below, what you do is your choice.
- You have to solve the problem in any object oriented or functional language
  without using any external libraries to the core language except for a testing
  library for TDD. Your solution must build+run on Linux. If you don't have
  access to a Linux dev machine, you can easily set one up using Docker.
- Please write comprehensive unit tests/specs.
- Your codebase should have the same level of structure and organization as any
  mature open source project. This includes coding conventions, directory
  structure, and build approach (like make, gradle, etc.). Also, ensure there's
  a README.md with clear instructions.
- Please create the Unix executable scripts `bin/setup` and `bin/parking_lot` in
  the bin directory of the project root. `bin/setup` should install dependencies
  and/or compile the code and then run your unit test suite. `bin/parking_lot`
  runs the program itself. It takes an input file as an argument and prints the
  output on STDOUT. Please see the [example](#example) above. Please note that
  these files are Unix executable files and should run on Unix.
- Use design patterns, write clean code, write extensible code, and blah blah.
  The **crux of engineering** is to write something today so that it can still
  be extensible 3 years later, even though you gonna scrap the project next year
  and put it in maintenance mode, while you rewrite the whole thing cause
  someone screamed - microservices !! 😆 (My rant !!)

**DISCLAIMER** - Well, since this is not a test anymore, I might and will change
some of the things written here, according to my wish and convenience.

### Assumptions:

Since this is a offline test, and mentioned so, I need to make some assumptions
to move forward. Back in the days, I would have choosen the hard way of doing
things to show off and impress, and probably mess it up at times. With age, the
validation requirement is fading away.

When given an option, always choose the easiest path!

<u>Below are the assumptions I made when working the design</u>:

- Parking lot has one entry and one exit only.
- Since this is cli based, commands are executed in sync. But lets assume two
  gates - one for entry and one for exit. Right when a user exits from spot 1,
  another car came in and actually got spot 2, though spot 1 technically is the
  nearest. We are going to ignore this for now as this does not impact much of
  functionality - and I think this sync is completely trivial.
- Parking lot is a single level parking lot.
- All parking spaces are same, there are no special/reserved space.
- When parking space is full, there is no queue, just print - `Full` and move on
  !!

### LICENSE

This project is under MIT LICENSE, a copy of which can be found [here](LICENSE).
