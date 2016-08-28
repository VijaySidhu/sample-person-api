UW Sample Person API
========

Prequsite
---------

Java 8
Maven 3.x
Postman Rest client to test api


How to run API
---------
Go to project directory and execute command : mvn spring-boot:run
 
Resources Accessible
---------------


- **POST /people** to upload a CSV file with a Content-Type of multipart/form-data  
                   1. http://localhost:8080/people/upload
                   2. Select Body tab
                   3. Choose form-data radio button
                   4. type file in Key and click on choose file button and select people.csv file
                   5. Hit on SEND button to post file
				   
	Output Status 201 Created
    Message in Body File Uploaded Successfully	
  				   
- **POST /people** to subject a JSON representation of a person with a Content-Type of application/json  
                   1. http://localhost:8080/people
                   2. Select raw and JSON(application/json)
				   3.  Enter below data and hit on SEND button to POST new person    
								   {
					  "personId": 100022,
					  "firstName": "Perkins",
					  "lastName": "Grace",
					  "affiliations": [
						"student",
						"staff"
					  ],
					  "birthDate": "02-Jan-82",
					  "lastEnrolledTerm": "Spring",
					  "lastEnrolledYear": 2009,
					  "hireDate": "02-Feb-14",
					  "separationDate": "09-Mar-15",
					  "address": {
						"line1": "19 Maple Ln",
						"line2": null,
						"city": " Seattle",
						"state": "WA",
						"zip": 98100
					  }
					}
	Output 201 Created
    Location →http://localhost:8080/people/100022	


- **GET /people** with zero or more query parameters as shown below to retrieve a JSON representation of search results

                   1. Selet GET operation
				   2. Enter http://localhost:8080/people/100027
				   3. Hit on Send Button to execute operation
	Output 
						{
			  "personId": 100027,
			  "firstName": "Perkins",
			  "lastName": "Grace",
			  "affiliations": [
				"student",
				"staff"
			  ],
			  "birthDate": "02-Jan-82",
			  "lastEnrolledTerm": "Spring",
			  "lastEnrolledYear": 2009,
			  "hireDate": "02-Feb-14",
			  "separationDate": "09-Mar-15",
			  "address": null
			}	
				   

#### Search for people

To retrieve a list of people filtered to a single affiliation.If a person has more than one affiliation, it shows all affiliations, e.g. [ "student", "staff" ].

1. Enter Below Url
2. http://localhost:8080/people?affiliation=student&zip=98100 


To filter to only return people who were active on a specific date, 
1. Enter Below Url
2. http://localhost:8080/people?activeOn=02-Feb-14

Output
									{
									  "people": [
										{
										  "personId": 100001,
										  "firstName": "Perkins",
										  "lastName": "Grace",
										  "affiliations": [
											"student",
											"staff"
										  ],
										  "birthDate": "02-Jan-82",
										  "lastEnrolledTerm": "Spring",
										  "lastEnrolledYear": 2009,
										  "hireDate": "02-Feb-14",
										  "separationDate": "09-Mar-15",
										  "address": {
											"line1": "19 Maple Ln",
											"line2": null,
											"city": " Seattle",
											"state": "WA",
											"zip": 98100
										  }
										},
										{
										  "personId": 100027,
										  "firstName": "Perkins",
										  "lastName": "Grace",
										  "affiliations": [
											"student",
											"staff"
										  ],
										  "birthDate": "02-Jan-82",
										  "lastEnrolledTerm": "Spring",
										  "lastEnrolledYear": 2009,
										  "hireDate": "02-Feb-14",
										  "separationDate": "09-Mar-15",
										  "address": null
										}
									  ],
									  "total": 2,
									  "page": 1,
									  "size": 20
									}


To be filtered by a zip/postal code. 
1. Enter below Url
2. http://localhost:8080/people?zip=98100

Output
					{
					  "people": [
						{
						  "personId": 100001,
						  "firstName": "Perkins",
						  "lastName": "Grace",
						  "affiliations": [
							"staff",
							"student"
						  ],
						  "birthDate": "02-Jan-82",
						  "lastEnrolledTerm": "Spring",
						  "lastEnrolledYear": 2009,
						  "hireDate": "02-Feb-14",
						  "separationDate": "09-Mar-15",
						  "address": {
							"line1": "19 Maple Ln",
							"line2": null,
							"city": " Seattle",
							"state": "WA",
							"zip": 98100
						  }
						},
						{
						  "personId": 200035,
						  "firstName": "Alarcon",
						  "lastName": "Shauna",
						  "affiliations": [
							"student"
						  ],
						  "birthDate": "03-Jan-97",
						  "lastEnrolledTerm": "Fall",
						  "lastEnrolledYear": 2016,
						  "hireDate": null,
						  "separationDate": null,
						  "address": {
							"line1": "7 Weathervane Ave",
							"line2": " Apt 2",
							"city": " Seattle",
							"state": "WA",
							"zip": 98100
						  }
						},
						{
						  "personId": 583452,
						  "firstName": "Exley",
						  "lastName": "Hugh",
						  "affiliations": [
							"student"
						  ],
						  "birthDate": "02-Sep-93",
						  "lastEnrolledTerm": "Fall",
						  "lastEnrolledYear": 2015,
						  "hireDate": null,
						  "separationDate": null,
						  "address": {
							"line1": "19 Maple Ln",
							"line2": null,
							"city": " Seattle",
							"state": "WA",
							"zip": 98100
						  }
						}
					  ],
					  "total": 3,
					  "page": 1,
					  "size": 20
					}

_Below are parameters that we can pass_

- **affiliation**: a single value of "student", "faculty", or "staff"
- **activeOn**: an ISO 8601 date that is the date on which a person was active in the system
- **zip**: the ZIP or postal code for the address of a person
- **page**: the page of the search results to provide
- **size**: the size of page to return the search results

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
    
  
