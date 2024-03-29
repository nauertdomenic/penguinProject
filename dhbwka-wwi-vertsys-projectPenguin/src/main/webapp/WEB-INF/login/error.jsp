<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- JSP zum Anzeigen der Fehlermeldungen beim Anmeldevorgang --%>

<template:base>
    <jsp:attribute name="title">
        Anmeldung nicht möglich
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/logout/"/>">Nochmal versuchen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <p>
            Das hat leider nicht geklappt. 🐻
        </p>
    </jsp:attribute>
</template:base>