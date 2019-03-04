<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Tiere
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/animals/animal/new/"/>">Tiere anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/animals/species/"/>">Species bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Tierart"/>

            <select name="search_species">
                <option value="">Alle Kategorien</option>

                <c:forEach items="${species}" var="species">
                    <option value="${species.id}" ${param.search_species == species.id ? 'selected' : ''}>
                        <c:out value="${species.name}" />
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty animals}">
                <p>
                    Es wurden keine Aufgaben gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Tierart</th>
                            <th>Species</th>
                            <th>Eigentümer</th>
                            <th>Fällig am</th>
                        </tr>
                    </thead>
                    <c:forEach items="${animals}" var="animal">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/animals/animal/${animal.id}/"/>">
                                    <c:out value="${animals.kind}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${animals.species.name}"/>
                            </td>
                            <td>
                                <c:out value="${animals.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(animals.dueDate)}"/>
                                <c:out value="${utils.formatTime(animals.dueTime)}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>