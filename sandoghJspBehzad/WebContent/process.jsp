<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*"%>

<%
String username=request.getParameter("username");
String password=request.getParameter("password");
String phonenumber=request.getParameter("phonenumber");
String emailid=request.getParameter("emailid");

try
{
Class.forName("org.postgresql.Driver");
Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sandogh", "admin", "behzadjava");
Statement st=conn.createStatement();

int i=st.executeUpdate("insert into users(username,password,phonenumber,email)values('"+username+"','"+password+"','"+phonenumber+"','"+emailid+"')");
out.println("Data is successfully inserted!");
}
catch(Exception e)
{
System.out.print(e);
e.printStackTrace();
}
%>