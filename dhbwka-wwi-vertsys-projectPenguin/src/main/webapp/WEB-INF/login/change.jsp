<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Daten ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/dashboard.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tierarten/list/"/>">Liste</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tierarten/tierart/new/"/>">Tierart anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tierarten/spezies/"/>">Spezies bearbeiten</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/change/"/>">Daten ändern</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="change_vorname">
                        Vorname:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="change_vorname" placeholder="${current_user.vorname}">
                    </div>

                    <label for="change_nachname">
                        Nachname:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="change_nachname" placeholder="${current_user.nachname}"> 
                    </div>

                    <label for="change_old_password">
                        Altes Passwort:
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="change_old_password" placeholder="altes Passwort">
                    </div>

                    <label for="change_new_password">
                        Neues Passwort:
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="change_new_passwort1" placeholder="neues Passwort">
                    </div>

                    <label for="change_new1_password">
                        Neues Passwort (wdh.):
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="change_new_passwort2" placeholder="wiederholen">
                    </div>

                    <br>

                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Ändern
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty change_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${change_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>