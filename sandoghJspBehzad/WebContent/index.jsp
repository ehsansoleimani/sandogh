<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%
 String db_driver="org.postgresql.Driver";
 String db_url="jdbc:postgresql://localhost:5432/sandogh";
 String db_user="admin";
 String db_password="behzadjava";

try {
Class.forName(db_driver);
} catch (ClassNotFoundException e) {
e.printStackTrace();
}

Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users Managment</title>
</head>
<body style="align-items: center;background-color: olive;">
<h2 align="center"style="color: white;"><font><strong>Retrieve data from database in jsp</strong></font></h2>
<table align="center" cellpadding="5" cellspacing="5" border="3">
<tr>

</tr>
<tr bgcolor=" #A52A2A">
<td><b>id</b></td>
<td><b>username</b></td>

<td><b>Phonenumber</b></td>
<td><b>Email</b></td>
<td><b>Update</b></td>
<td><b>Delete</b></td>
</tr>
<%
try{ 
connection = DriverManager.getConnection(db_url,db_user,db_password);
statement=connection.createStatement();
String sql ="SELECT * FROM users";

resultSet = statement.executeQuery(sql);
while(resultSet.next()){
%>
<tr bgcolor="#DEB887">

<td><%=resultSet.getString("id") %></td>
<td><%=resultSet.getString("username") %></td>

<td><%=resultSet.getString("phonenumber") %></td>
<td><%=resultSet.getString("email") %></td>
<td><a href="update.jsp?id=<%=resultSet.getString("id")%>">update</a></td>
<td><a href="delete.jsp?id=<%=resultSet.getString("id") %>"><button type="button" class="delete">Delete</button></a></td>

</tr>

<% 
}
connection.close();
} catch (Exception e) {
e.printStackTrace();
}
%>
</table>
</body>
</html>