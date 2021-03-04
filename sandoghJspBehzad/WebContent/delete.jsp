
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*"%>
<%
String db_driver="org.postgresql.Driver";
String db_url="jdbc:postgresql://localhost:5432/sandogh";
String db_user="admin";
String db_password="behzadjava";
String id=request.getParameter("id");
try
{
Class.forName(db_driver);
Connection conn = DriverManager.getConnection(db_url,db_user,db_password);
Statement st=conn.createStatement();
int i=st.executeUpdate("DELETE FROM users WHERE id="+id);
out.println("Data Deleted Successfully!");
}
catch(Exception e)
{
System.out.print(e);
e.printStackTrace();
}
%>
