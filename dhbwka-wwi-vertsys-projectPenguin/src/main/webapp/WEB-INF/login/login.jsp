<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- JSP zum Anmelden auf der Website --%>

<template:base>
    <jsp:attribute name="title">
        Login
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/signup/"/>">Registrieren</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form action="j_security_check" method="post" class="stacked">
                <div class="column">
                    <%-- Eingabefelder --%>
                    <label for="j_username">
                        Benutzername:
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="j_username">

                    <label for="j_password">
                        Passwort:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="j_password">

                    <br>
                    <h3> </h3>
                    <%-- Button zum Abschicken --%>
                    <button class="icon-login" type="submit">
                        Einloggen
                    </button>
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>