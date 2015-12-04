# Database
1.	User Guide
Library Management System is a software to manage day to day library activities. This includes book search, book check out, book check in, borrower management and fine check management for the late submission of the book.
The home page has a text link like “Book Search”, “Check Fine” etc. as shown below:
 
On clicking the text link, it navigates to the new page where it performs the intended activity.
a.	Book Search
 
Book can be searched by either book id or title name or author name. The result will be shown based on the search field results as shown below:
 
b.	Book Check-Out
Book can be checked out by entering book details which includes book id, branch id and card number of the borrower as shown:
 
If all the details are correct then it shows the message as shown:
 
c.	Book Check-In
 
The book which needs to be checked in can be searched by entering fields like book id, borrower’s name and card number. The “Find” will search all related books as shown:
 
The required book can be checked in by selecting the desired row, entering the date in value and clicking “Checkin” button.
On successful check in, it displays the message as shown:
 
d.	Borrower Management
Borrower can be added by entering the details in the form as shown below:
 
On clicking “Add” button, the message is displayed as shown:
 
e.	Check Fine
 
Fine can be checked by entering either borrower’s name or card number. The results will be displayed based on the search field results as shown:
 
Fine can be paid by selected the desired row and clicking “Pay Fine” button. On success, the message is displayed as below:
 

2 Architecture
Library Management System uses Java Server Page as the front end (client side) and Java servlets at the back end (server). Apache Tomcat is a servlet container that implements the Java Servlet and the Java Server Pages (JSP) specifications, and provides a "pure Java" HTTP web server environment for Java code to run in. The servlet can Java Server Page can be mapped in multiple ways like servlet mapping in web.xml, form action etc. In the Library Management System, the mapping is done through form action i.e. each java server page will have a form which will have an action field where the servlet is linked as shown:
<form name="AddBorrowerform" action=Add_Borrower method="post">
Here Add_Borrower is the Java Servlet which handles the request from Java Server Page AddBorrowerform.
The lifecycle of a typical servlet running on Tomcat might look something like this:
1.	Tomcat receives a request from a client through one of its connectors.
2.	Tomcat maps this request to the appropriate Engine for processing.  These Engines are contained within other elements, such as Hosts and Servers, which limit the scope of Tomcat's search for the correct Engine.
3.	Once the request has been mapped to the appropriate servlet, Tomcat checks to see if that servlet class has been loaded.  If it has not, Tomcat compiles the servlet into Java bytecode, which is executable by the JVM, and creates an instance of the servlet.
4.	Tomcat initializes the servlet by calling its init method.  The servlet includes code that is able to read Tomcat configuration files and act accordingly, as well as declare any resources it might need, so that Tomcat can create them in an orderly, managed fashion.
5.	Once the servlet has been initialized, Tomcat can call the servlet's service method to process the request, which will be returned as a response.
6.	During the servlet's lifecycle, Tomcat and the servlet can communicate through the use of listener classes, which monitor the servlet for a variety of state changes.  Tomcat can retrieve and store these state changes in a variety of ways, and allow other servlets access to them, allowing state to be maintained and accessed by various components of a given context across the span of a single or multiple user sessions.  An example of this functionality in action is an e-commerce application that remembers what the user has added to their cart and is able to pass this data to a checkout process.
7.	Tomcat calls the servlet's destroy method to smoothly remove the servlet.  This action is triggered either by a state change that is being listened for, or by an external command delivered to Tomcat to un deploy the servlet's Context or shut down the server.
JSP and Servlet interacts using http request – http response model as shown:
   



Design Justification:
1)	Utilizing its implementation of the Java Servlet and JSP APIs, Tomcat is able to receive requests from a client, dynamically compile a container-managed Java class to handle the request as specified in the relevant application Context, and return the result to the client.  This method of generating dynamic content enables extremely fast, threaded, platform independent processing of requests.
2)	The Java Servlet specification is designed for interoperability with all other major Java web technologies, a servlet hosted on a Tomcat server is able to leverage any resource that Tomcat makes available to it.  Tomcat's nested hierarchical XML configuration files allow for extremely fine-grained resource access control, while maintaining loose coupling, ease of deployment, and logical, human-readable architecture descriptions.
3)	The servlet code itself will never listen for requests on a certain port, nor will it communicate directly with a client, nor is it responsible for managing its access to resources.  Rather, these things are managed by Tomcat, the servlet container. This allows servlets to be re-used in a wide variety of environments, or for components to be developed asynchronously from one another - a connector can be re-factored for improved efficiency without any changes to the servlet code itself, as long as no major changes are made.
4)	Since Java is platform independent, the application can be deployed at any client machine using WAR (Web application archive) file. 
WAR File Generation:
The War file is generated using Apache Maven which converts Java project to Maven Project using the eclipse plug in m2e-eclipse which generates POM xml file. POM xml file should be updated as below:
<packaging>war</packaging>
<artifactId>maven-war-plugin</artifactId>
Also dependencies like Servlet jar, mysql connector jar should be added in POM file.
Finally the project should be run as Maven Build which will generate a WAR file at the target directory. The generated war file can be deployed at any client machine which runs Apache Tomcat Server.
Technical Dependencies:
The client machine should have Apache Tomcat Server running which in turn requires a Java compiler JDK. Apache Tomcat Server version of 8.0 or above and JDL version of 1.8 or above is needed. 
During application development, servlet-api with version 8.0and mysql-connector with version 5.1 jar files was needed to compile the project with java servlets. 
Deployment:
The WAR file should be copied to webapps folder inside installed Apache Tomcat folder. Apache Server should be started at some port (default port is 8080). In the web browser, the following address is typed at the address bar which will display the home page of the Library Management System. Also, the property file conf.properties should be copied inside WEB_INF/Class folder of war_file_name folder. 
http://localhost:8081/war_file_name
Here, war_file_name is LibraryManager-0.0.1-SNAPSHOT.  

