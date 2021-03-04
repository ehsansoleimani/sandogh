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
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="background-color: olive;">

<h1 align="center" style="color: white;">User Managment</h1>
<table align="center" cellpadding="5" cellspacing="5" border="3" >
<tr>
</tr>
<tr bgcolor="#A52B2A">
<td>Id</td>
<td>Username</td>
<td>Phonenumber</td>
<td>Email</td>
<td>Update</td>
<td>Delete</td>
</tr>
<%
try{
	Class.forName(db_driver);
	Connection conn=DriverManager.getConnection(db_url,db_user,db_password);
	Statement st=conn.createStatement();
	String sql="select * from users";
	ResultSet resultSet=st.executeQuery(sql);
	while(resultSet.next()){
		%>
		<tr bgcolor="#DEB887">
		<td><%=resultSet.getString("id") %></td>
		<td><%=resultSet.getString("username") %></td>
		<td><%=resultSet.getString("phonenumber") %></td>
		<td><%=resultSet.getString("email") %></td>
		<td><a href="update.jsp?id=<%=resultSet.getString("id")%>">update</a></td>
        <td><a href="delete.jsp?id=<%=resultSet.getString("id") %>">
        <button type="button" class="delete">Delete</button></a></td>
		
		
		</tr>
		<% 
		conn.close();
	}
	
}catch(Exception e){
	System.out.print(e);
	
	
}
%>
</table>

</body>
</html>