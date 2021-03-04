<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users</title>
</head>
<body style="align-items: center;background-color: olive;">
<h2 align="center" style="color:white;"><font>User management</font></h2>
<form method="post" action="process.jsp">
<a style="font-size: 13px;margin-left: 20px">username:</a><br>
<input type="text" name="username" placeholder="username"style="margin-bottom: 5px;margin-left: 20px">
<br>
<a style="font-size: 13px;margin-left: 20px">password:</a><br>
<input type="text" name="password"placeholder="password"style="margin-bottom: 5px;margin-left: 20px">
<br>
<a style="font-size: 13px;margin-left: 20px">phonenumber:</a><br>
<input type="text" name="phonenumber"placeholder="phonenumber"style="margin-bottom: 5px;margin-left: 20px">
<br>
<a style="font-size: 13px;margin-left: 20px">Emailid:</a><br>
<input type="email" name="emailid"placeholder="email"style="margin-bottom: 5px;margin-left: 20px">
<br><br>
<input type="submit" value="Add User " style="padding: 10px 24px;border-radius: 6px;border-color: gray;box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);margin-left: 35px">
</form><br>
<form action="index2.jsp">
<input type="submit"  value="Users List" style="padding: 10px 24px;border-radius: 6px;border-color: gray;box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);margin-left: 35px"/>
</form>

</body>
</html>
