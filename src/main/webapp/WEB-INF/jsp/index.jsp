<%--
  User: ehsan
  Date: 07.03.21
  Time: 12:09
--%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>


</head>
<body>
    <div id="navigation-bar">
        <jsp:include page="navigationBar.jsp"/>
    </div>
    <h1>
        <fmt:message key="homePage.welcome"/>
    </h1>
</body>
</html>
