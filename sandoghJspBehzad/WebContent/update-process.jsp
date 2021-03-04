<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%! String db_driver="org.postgresql.Driver";%>
<%!String db_url="jdbc:postgresql://localhost:5432/sandogh";%>
<%!String db_user="admin";%>
<%!String db_password="behzadjava";%>
<%
String id = request.getParameter("id");
String username=request.getParameter("username");
String password=request.getParameter("password");
String phonenumber=request.getParameter("phonenumber");
String email=request.getParameter("email");
if(id != null)
{
Connection con = null;
PreparedStatement ps = null;
int personID = Integer.parseInt(id);
try
{
Class.forName(db_driver);
con = DriverManager.getConnection(db_url,db_user,db_password);
String sql="Update users set id=?,username=?,password=?,phonenumber=?,email=? where id="+id;
ps = con.prepareStatement(sql);
ps.setInt(1, personID);
ps.setString(2, username);
ps.setString(3, password);
ps.setString(4, phonenumber);
ps.setString(5, email);
int i = ps.executeUpdate();
if (i > 0) {
	out.print("Record Updated Successfully");
} else {
	out.print("There is a problem in updating Record.");
}
} catch (SQLException sql) {
request.setAttribute("error", sql);
out.println(sql);
}
}
%>