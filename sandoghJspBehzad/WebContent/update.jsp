<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%
String id = request.getParameter("id");
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
<%
try{
connection = DriverManager.getConnection(db_url,db_user,db_password);
statement=connection.createStatement();
String sql ="select * from users where id="+id;
resultSet = statement.executeQuery(sql);
while(resultSet.next()){
%>
<!DOCTYPE html>
<html>
<body style="background-color: olive;">
<h1 align="center" style="color: white;">Update data from database in jsp</h1>
<form method="post" action="update-process.jsp">
<input type="hidden" name="id" value="<%=resultSet.getString("id") %>">
Id:<br>
<input type="text" name="id" value="<%=resultSet.getString("id") %>">
<br>
Username:<br>
<input type="text" name="username" value="<%=resultSet.getString("username") %>">
<br>
password:<br>
<input type="text" name="password" value="<%=resultSet.getString("password") %>">
<br>
phonenumber:<br>
<input type="text" name="phonenumber" value="<%=resultSet.getString("phonenumber") %>">
<br>
Emailid:<br>
<input type="email" name="email" value="<%=resultSet.getString("email") %>">
<br><br>
<input type="submit" value="submit">
</form>
<%
}
connection.close();
} catch (Exception e) {
e.printStackTrace();
}
%>
</body>
</html>