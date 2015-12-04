<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Check-In</title>

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

<h1><ins>BOOK CHECK-IN FORM</ins></h1>
<form name="BookCheckInform" action=Book_CheckIn method="post">
<pre>
<b>Enter the book id                      : <input type="text" name="bookid" size="40"></b><br>
<b>Enter the borrower's first name        : <input type="text" name="fname" size="40"></b><br>
<b>Enter the borrower's last name         : <input type="text" name="lname" size="40"></b><br>
<b>Enter the card number                  : <input type="text" name="cardnum" size="40"></b><br>
</pre>
<pre><input type="submit" value="Find" style="height:36px; width:140px; position: relative; left: 380px; top: -28px; font-size:110%;color:OrangeRed;"></pre><br>
</form>

<a href=default.html style="font-size:190%; position:absolute; left:1150px; top:5px"><ins>Home</ins></a>
</body>
</html>