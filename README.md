# cybersecuritybase-project

LINK: https://github.com/thomsva/cybersecuritybase-project
This project was made as a part of the Cyber Security Base course. The assignment was to add five different security flaws to a project. 

## FLAW 1:
### Description of flaw 1: A1:2017 Injection
When adding a new signup using the html form, a query is being built as a string based on user input. A malicious user can easily execute sql statements on the server by adding sql code to the name or address field. As an example, the following input in the address field would add an extra user with the name xxx and address xxx: 

‘); insert into Signup (name,address) values ('xxx','xxx

Sql-injection can potentially lead to severe problems. It would for example be very easy to build a query to remove entries or even delete all data. Fortunately, the fix is fairly easy. 

### How to fix it: 
The problem is in the way the sql query is built as a string.

String query = "insert into Signup (name,address) values ('" + name + "','" + address + "')"; 

The inputs (name and address) need to be cleaned up before using them. One could for example remove all special signs and allow only letters and numbers. But leaving this to the developer to do manually is not a good solution. In this case a good alternative would to use a different method of adding the data to the database.  The easiest way is simply to use built in Spring methods for saving a new entry: 

signupRepository.save(new Signup(name, address));

If custom queries are needed these can be built with a query builder tool instead of string-concatenation, to automatically clean up any malicious content. 

## FLAW 2: 
### Description of flaw 2: A7:2017 Cross Site Scripting (XSS)
If a user tries to upload a Javascript command on the form instead of a name and address using the html form, he will most likely succeed, and the script will be stored in the database. With the current XSS flaw in place, the script will then be executed by any user who enters the site and loads the current listing of signups. This can be demonstrated by entering the following in the name fields: 

<script>window.alert("!")</script>

The next time a user loads the page, the script will be executed showing an alert window. This vulnerability can be used to run any javascript, or to load scrips from external sources. The admin page will also be affected by this problem, meaning that a CSRF attack (Cross-Site Request Forgery) could be possible.  
### How to fix it: 
The inputs should be sanitized as a part of an input validation process. This way anything like the <script> tags would not be inserted into the database. 
Another layer of security can be added to process of showing the listing to the user. When loading any page with a list of signups, the data is retrieved from the database and displayed to the user using a Thymeleaf template. The individual data fields are printed using the thymeleaf tag th:utext. By using th:text instead any script in the data will be converted to a displayable string and not executed. 

## FLAW 3:
### Description of flaw 3: A6:2017 Security Misconfiguration
A simple rest api has been added to the application. By sending a get-request to /api/signups, a json response with all signups including both name and addresses will be retrieved. This should not be possible without admin credentials, but no login is required to get the data. 
### How to fix it
The application uses Spring security, but the configuration is wrong. The configuration should be updated in SecurityConfiguration.java. Currently all requests made to /api/* are permitted. 

## FLAW 4: 
### Description of flaw 4: A2:2017 Broken Authentication
There is a default admin account with a username admin and password admin. Even a simple dictionary-based attack using common username-password combination could find this, opening up access to the admin page. 
### How to fix it
Remove the current default account and build a new process for user management. If a system for user management can’t be built, at least the password of the default account should be changed to something that can’t be found in any password lists. 

## FLAW 5:
### Description of flaw 5: A9:2017 Using Components with Known Vulnerabilities
The application uses an old version of the H2 Database Engine. This version is known to be vulnerable to arbitrary code execution.
### How to fix it
Open pom.xml. Locate the dependency H2 Database Engine. Remove the reference <version>1.4.197</version>, or replace the version number with a version that is more current and known to be safe. 
For keeping an application safe in the future too, the dependencies should be checked regularly for vulnerabilities and updated as needed. There are tools available for automating this. These tools do not guarantee anything, but having automated checks for the latest known vulnerabilities is most likely a big gain. 

