<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Check-Out</title>

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

<h1><ins>BOOK CHECK-OUT FORM</ins></h1>
<form name="BookLoanform" action=Book_Loan method="post">
<pre>
<b>Enter the book id          : <input type="text" name="bookid" size="40"></b><br>
<b>Enter the branch id        : <input type="text" name="branchid" size="40"></b><br>
<b>Enter the card number      : <input type="text" name="cardnum" size="40"></b><br>
</pre>
<pre><input type="submit" value="Check-Out" style="height:36px; width:160px; position: relative; left: 290px; top: -28px; font-size:110%;color:blue"></pre><br>
</form>

<a href=default.html style="font-size:190%; position:absolute; left:1150px; top:5px"><ins>Home</ins></a>
</body>
</html>