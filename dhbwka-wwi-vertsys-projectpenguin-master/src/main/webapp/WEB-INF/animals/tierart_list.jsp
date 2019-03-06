<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Tierarten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/task/new/"/>">Tierart anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/categories/"/>">Spezies bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_category">
                <option value="">Alle Spezies</option>

                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}" ${param.search_category == category.id ? 'selected' : ''}>
                        <c:out value="${category.name}" />
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Tierarten --%>
        <c:choose>
            <c:when test="${empty tierarten}">
                <p>
                    Es wurden keine Tierarten gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.projectanimal.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Spezies</th>
                            <th>Eigentümer</th>
                        </tr>
                    </thead>
                    <c:forEach items="${tierarten}" var="tierart">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/tasks/task/${tierart.id}/"/>">
                                    <c:out value="${tierart.tierartname}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${tierart.category.name}"/>
                            </td>
                            <td>
                                <c:out value="${tierart.owner.username}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>