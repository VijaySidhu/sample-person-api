UW Sample Person API
========

Objective
---------

Develop a RESTful API in Java using the existing Spring dependencies (Web, JPA) provided in the Maven pom.xml file included in the quickstart. 

Feel free to add additional dependencies as necessary, so long as they are available in the standard maven repository. 

The purpose of the API is to serve data about people. These people may be students, faculty, or staff members of a University. It should allow an end user to upload a comma separated values (CSV) file -- as included under data/People.csv -- that will populate a simple in memory or file-based relational database such as HSQL that can then be queried to search for people by certain fields.

To run the API, you'll use the following command. More information about testing and running Spring Boot applications can be found in the Spring documentation online.

    mvn spring-boot:run   



Resources
---------------

The API should minimally include the following resources:

- **POST /people** to upload a CSV file with a Content-Type of multipart/form-data
- **POST /people** to subject a JSON representation of a person with a Content-Type of application/json
- **GET /people** with zero or more query parameters as shown below to retrieve a JSON representation of search results


#### Upload csv file

This is the primary mechanism by which data is added to the data store. 

    POST /people
    Content-Type: multipart/form-data
    
The content of the multipart request would be a file formatted as shown by the data/People.csv file provided. 
    

#### Add a single person in JSON format

This resource should create a new person and assign a unique identifier on each POST. The example below shows what this would look like if the representation for
 a single person object was passed, but feel free to adjust the request body to be a JSON list of people if you prefer, though you may need to adjust the response code and
 body to reflect that change. 

    POST /people
    { 
        "firstName": "Tim",
        "lastName": "Tester",
        "affiliations": [ "faculty", "staff", "student"],
        "birthDate": "1988-09-20",
        "lastEnrolledTerm": "Spring",
        "lastEnrolledYear": "2015",
        "hireDate": "2002-07-31",
        "separationDate": "",
        "address": {
            "line1": "1 Testington Ln",
            "line2": "",
            "city": "Seattle",
            "state": "WA",
            "zip": "98103"
        }
    }
    
    201 Created
    Location: http://localhost:8080/people/583453


#### Search for people

This resource should allow the end user to retrieve a list of people filtered to a single affiliation (note that people can have more than one affiliation). 
If a person has more than one affiliation, then show all affiliations, e.g. [ "student", "staff" ]. 

This resource should also allow the end user to filter to only return people who were active on a specific date, as in the example below, based on the following
rules:

- Students are active for 4 quarters after the quarter in which they were last enrolled, as included in the CSV column LastEnrolled. 
- Faculty and staff are active between their HireDate (inclusive) and their SeparationDate (exclusive). 
- If a person is active for either reason, that person should be returned in the search results

In other words, if a faculty member has separated from the University on 9/8/2014 but then was enrolled in Summer 2016, she would still be active on Aug 22, 2016. 

A staff member who separated on 9/8/2014 would _not_ be active on 2014-09-08, but would be active on 2014-09-07. 

Finally, the resource should allow the search results to be filtered by a zip/postal code. 

_Accepted query parameters_

- **affiliation**: a single value of "student", "faculty", or "staff" (if this is left blank or not provided then people with all affiliations should be returned)
- **activeOn**: an ISO 8601 date that is the date on which a person was active in the system, i.e. a current student or a staff/faculty members who has not separated from the University
- **zip**: the ZIP or postal code for the address of a person (if a zip is provided, then only people with that particular zip should be returned in the search results)
- **page**: the page of the search results to provide (optional, should default to page 1)
- **size**: the size of page to return the search results in (optional, should default to 100)

The response body should provide an indication of the total count that matches the query parameters, independent of the page size returned.  

_Example search_

    GET /people?affiliation=student&activeOn=2016-09-08&zip=98100&page=1&size=20

    200 OK
    {
        "people": [
            {
                "id": "200035",
                "firstName": "Shauna", 
                "lastName": "Alarcon",
                "affiliations": [ "student" ],
                "birthDate": "1997-01-03",
                "lastEnrolledTerm": "Fall",
                "lastEnrolledYear": "2014"
                "address": {
                    "line1": "7 Weathervane Ave",
                    "line2": "Apt 2",
                    "city": "Seattle",
                    "state": "WA",
                    "zip": "98100"
                }
            }
        ],
        "total": 1
        "page": 1
        "size": 20
    }
    
    
The representations retrieved in the search should vary based on the affiliations, so that a student who is not a faculty or staff member should not include a 
hire date or separation date, but should include the following fields:

#### Student Representation
- id
- firstName
- lastName
- affiliations  (list of strings)
- birthDate (in ISO 8601 date format) 
- lastEnrolledTerm
- lastEnrolledYear
- address

#### Faculty Representation
- id
- firstName
- lastName
- affiliations (list of strings)
- hireDate (in ISO 8601 date format)

#### Staff Representation
- id
- firstName
- lastName
- affiliations (list of strings)
- address
    
    
If a person has more than one affiliation, then their representation should include the union of the fields of each affiliation. 
So, for example, a Faculty/Staff member should include both hireDate and address. 


-------------------
#### Expectations

The basic expectation of this assignment is that you will use Spring Boot and Spring Web to build a RESTful API -- you may want to take a look at this guide if you haven't used the Spring Java Config annotations to build web services in the past: https://spring.io/guides/gs/rest-service/

1. The API should use a relational database, such as HSQL 
2. It should take advantage of Spring Boot's embedded servlet container to run and test the resources
3. It does not need to provide security or authentication
4. It should use Maven for dependency and build management
5. It should follow common Java best practices in terms of documentation, style, and use of collections and design patterns
6. It should use JDK 1.8
7. Some exception handling should be provided, including use of standard HTTP status codes to indicate when a query parameter is unacceptable, for example


--------------------
#### General Advice

If you feel that any information is missing or unclear, then please make a choice that makes sense for you and provide the reason of your choice, either in the code or in an attached document.

    
    
    