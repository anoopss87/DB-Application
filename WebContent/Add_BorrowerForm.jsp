<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Borrower</title>

<style>
pre {
    color:red;
    font-size:170%;
}
h1 {
    color:MidnightBlue;
    text-align:center;
    font-size:200%;
}  
</style>
</head>

<body style="background-color:LightSteelBlue ">
<body>

<h1><ins>BORROWER MANAGEMENT FORM</ins></h1>
<form name="AddBorrowerform" action=Add_Borrower method="post">
<pre>
<b>Enter the first name       : <input type="text" name="fname" size="40"></b><br>
<b>Enter the last name        : <input type="text" name="lname" size="40"></b><br>
<b>Enter the email address    : <input type="text" name="email" size="40"></b><br>
<b>Enter the street address   : <input type="text" name="address" size="40"></b><br>
<b>Enter the city             : <input type="text" name="city" size="40"></b><br>
<b>Enter the state            : <input type="text" name="state" size="40"></b><br>
<b>Enter the phone number     : <input type="text" name="phone" size="40"></b><br>
</pre>
<pre><input type="submit" value="Add" style="height:36px; width:80px; position: relative; left: 300px; top: -28px; font-size:110%;color:magenta;"></pre><br>
</form>

<a href=default.html style="font-size:190%; position:absolute; left:1150px; top:5px"><ins>Home</ins></a>
</body>
</html>