<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.6.0/css/bootstrap.min.css">
    <script type="text/javascript" src="webjars/jquery/3.6.0/jquery.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/4.6.0/js/bootstrap.min.js"></script>
    <c:url value="/css/main.css" var="jstlCss"/>
    <link href="${jstlCss}" rel="stylesheet"/>
</head>
<script type="text/javascript">
    // Prevent closing from click inside dropdown
    $(document).on('click', '.dropdown-menu', function (e) {
        e.stopPropagation();
    });

    // make it as accordion for smaller screens
    if ($(window).width() < 992) {
        $('.dropdown-menu a').click(function (e) {
            e.preventDefault();
            if ($(this).next('.submenu').length) {
                $(this).next('.submenu').toggle();
            }
            $('.dropdown').on('hide.bs.dropdown', function () {
                $(this).find('.submenu').hide();
            })
        });
    }
</script>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"><fmt:message key="application.name"/></a>
        </div>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main_nav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="main_nav">
            <ul class="navbar-nav">
                <li class="nav-item active"><a class="nav-link" href="#"><fmt:message key="menu.home"/></a></li>
                <li class="nav-item"><a class="nav-link" href="#"><fmt:message key="menu.about"/></a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"><fmt:message
                            key="menu.management"/></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="dropdown-item" href="#"><fmt:message key="menu.users"/> &raquo;</a>
                            <ul class="submenu dropdown-menu">
                                <li><a class="dropdown-item" href=""><fmt:message key="menu.users.list"/></a></li>
                                <li><a class="dropdown-item" href=""><fmt:message key="menu.users.create"/></a></li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>

    </div>
</nav>
</body>