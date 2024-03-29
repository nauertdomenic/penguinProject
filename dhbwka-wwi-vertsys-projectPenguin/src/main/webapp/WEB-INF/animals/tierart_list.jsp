<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- JSP zum Anzeigen der Tierarten und Suchen von Tierarten nach verschiedenen Parametern --%>

<template:base>
    <jsp:attribute name="title">
        Liste der Tierarten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/tierart_list.css"/>" />
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
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input id="test" type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select id="test" name="search_spezies">
                <option value="">Alle Spezies</option>

                <c:forEach items="${spezien}" var="spezies">
                    <option value="${spezies.id}" ${param.search_spezies == spezies.id ? 'selected' : ''}>
                        <c:out value="${spezies.name}" />
                    </option>
                </c:forEach>
            </select>
            
            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button id="test" class="icon-search" type="submit">
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
                <jsp:useBean id="utils" class="projectanimal.common.web.WebUtils"/>

                <table>
                    <thead>
                        <tr id="ueberschrift">
                            <th>Bezeichnung</th>
                            <th>Spezies</th>
                            <th>Status</th>
                            <th>Lebensdauer</th>
                            <th>Gewicht</th>
                            <th>Schmeckt</th>
                            <th>Eigentümer</th>
                            <th>Änderungsdatum</th>
                            <th>Änderungszeit</th>
                        </tr>
                    </thead>
                    <c:forEach items="${tierarten}" var="tierart">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/tierarten/tierart/${tierart.id}/"/>">
                                    <c:out value="${tierart.tierartname}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${tierart.spezies.name}"/>
                            </td>
                            <td>
                                <c:out value="${tierart.status.label}"/>
                            </td>
                            <c:choose>
                                <c:when test='${tierart.lebensdauer == 0.0}'>
                                    <td>
                                        <c:out value="n/a"/>
                                    </td>
                                </c:when>    
                                <c:otherwise>
                                    <td>
                                        <c:out value="${tierart.lebensdauer}"/>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                            
                            <c:choose>
                                <c:when test='${tierart.gewicht == 0.0}'>
                                    <td>
                                        <c:out value="n/a"/>
                                    </td>
                                </c:when>    
                                <c:otherwise>
                                    <td>
                                        <c:out value="${tierart.gewicht}"/>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                            
                            <c:choose>
                                <c:when test='${tierart.schmeckt}'>
                                    <td>
                                        <c:out value="lecker!"/>
                                    </td>
                                </c:when>    
                                <c:otherwise>
                                    <td>
                                        <c:out value="igitt!"/>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                            
                            <td>
                                <c:out value="${tierart.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${tierart.date}"/>
                            </td>
                            <td>
                                <c:out value="${tierart.time}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>