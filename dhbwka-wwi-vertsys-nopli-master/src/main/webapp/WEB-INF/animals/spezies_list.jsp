<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Kategorien bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/spezies_list.css"/>" />
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
        <form method="post" class="stacked">
            <%-- CSRF-Token --%>
            <input type="hidden" name="csrf_token" value="${csrf_token}">

            <%-- Feld zum Anlegen einer neuen Spezies --%>
            <div class="column margin">
                <label for="j_username">Neue Spezies:</label>
                <input type="text" name="name" value="${spezien_form.values["name"][0]}">

                <br>

                <button type="submit" name="action" value="create" class="icon-pencil">
                    Anlegen
                </button>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty spezien_form.errors}">
                <ul class="errors margin">
                    <c:forEach items="${spezien_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>

            <%-- Vorhandene Spezies --%>
            <c:choose>
                <c:when test="${empty spezien}">
                    <p>
                        Es ist noch keine Spezies vorhanden. 🐏
                    </p>
                </c:when>
                <c:otherwise>
                    <div>
                        <div class="margin">
                            <c:forEach items="${spezien}" var="spezies">
                                <input type="checkbox" name="spezies" id="${'spezies-'.concat(spezies.id)}" value="${spezies.id}" />
                                <label for="${'spezies-'.concat(spezies.id)}">
                                    <c:out value="${spezies.name}"/>
                                </label>
                                <br />
                            </c:forEach>
                        </div>

                        <button type="submit" name="action" value="delete" class="icon-trash">
                            Markierte löschen
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </jsp:attribute>
</template:base>