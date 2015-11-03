<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check Fine</title>

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

<h1><ins>FINE CHECK FORM</ins></h1>
<form name="FineForm" action=Fine_Process method="post">
<pre>
<b>Enter the borrower's first name        : <input type="text" name="fname" size="40"></b><br>
<b>Enter the borrower's last name         : <input type="text" name="lname" size="40"></b><br>
<b>Enter the card number                  : <input type="text" name="cardnum" size="40"></b><br>
</pre>
<pre><input type="submit" name ="fine" value="Paid" style="height:36px; width:140px; position: relative; left: 380px; top: -28px; font-size:110%;color:OrangeRed;"></pre><br>
<pre><input type="submit" name ="fine" value="UnPaid" style="height:36px; width:140px; position: absolute; left: 570px; top: 231px; font-size:110%;color:OrangeRed;"></pre><br>
<pre><input type="submit" name ="fine" value="All" style="height:36px; width:140px; position: absolute; left: 760px; top: 231px; font-size:110%;color:OrangeRed;"></pre><br>
</form>

<form name="FineRefresh" action=Fine_Refresh method="post">

<pre><input type="submit" name ="fine" value="Refresh Fine" style="height:36px; width:190px; position: absolute; left: 900px; top: 120px; font-size:110%;color:OrangeRed;"></pre><br>
</form>

<a href=default.html style="font-size:190%; position:absolute; left:1150px; top:5px"><ins>Home</ins></a>
</body>
</html>