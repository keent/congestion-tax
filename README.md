# Congestion Tax Solution for Volvo

I wrote 3 Solutions for the problem in Java and Typescript to 
demonstrate I'm comfortable with both Imperative and Declarative languages.
The other Java solution implements the bonus part.

## My strategy : 
1. Understand the problem and write/simulate types and test cases
2. Add the HTTP entry point and integrate the existing code
3. Write Unit tests to debug the issues and fix them
4. Write Curl/API/System tests - The same set of curl command tests applies for all the solution
5. Fix the logic issues, typos, incorrect code with spec, etc.
6. Rinse and repeat steps 3 to 5

## Some notes:
1. I don't have questions at the moment
2. I limited the scope to 2013
3. This is a good test - not narrow, just enough to test real world skills and aptitude

## How to RUN
1. Using Intellij, Simply run the Main class or the Tests in the IDE.
2. For the Typescript solution simply type in 'npm start' after 'npm install'. Type in 'npm test' to run the tests.

## The API Tests using curl 
Must use the curl commands below in a *nix terminal.

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     -d "vehicleType=Car&dates=2013-11-15%2009:00 \
                                ,2013-11-15%2010:15 \
                                ,2013-11-15%2011:30 \
                                ,2013-11-15%2012:45 \
                                ,2013-11-15%2014:00 \
                                ,2013-11-15%2015:15 \
                                ,2013-11-15%2016:30 \
                                ,2013-11-15%2017:45 \
                                ,2013-11-15%2019:00 \
                                ,2013-11-15%2020:15" \
     http://localhost:8080/calculateTax

// Answer 60 in both 


curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     -d "vehicleType=Car&dates=2013-11-15%2009:00 \
                                ,2013-11-15%2010:15 \
                                ,2013-11-15%2011:30" \
     http://localhost:8080/calculateTax

// Answer 24 in Gothenburg (without Bonus), 48 in Stockholm (with Bonus)


curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     -d "vehicleType=Car&dates=2013-11-15%2012:45 \
                                ,2013-11-15%2014:00 \
                                ,2013-11-15%2015:15" \
     http://localhost:8080/calculateTax

// Answer 29 in Gothenburg (without Bonus), 58 in Stockholm (with Bonus) 


curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     -d "vehicleType=Car&dates=2013-11-15%2016:30 \
                                ,2013-11-15%2017:45 \
                                ,2013-11-15%2019:00 \
                                ,2013-11-15%2020:15" \
     http://localhost:8080/calculateTax

// Answer 31 in Gothenburg (without Bonus), 60 in Stockholm (with Bonus)


curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     -d "vehicleType=Car&dates=2023-11-15%2016:30 \
                                ,2033-11-15%2017:45 \
                                ,2043-11-15%2019:00 \
                                ,2023-11-15%2020:15" \
     http://localhost:8080/calculateTax

// Answer 0

```
