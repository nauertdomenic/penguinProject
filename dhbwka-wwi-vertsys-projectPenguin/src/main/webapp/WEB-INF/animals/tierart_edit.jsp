<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Tierart bearbeiten
            </c:when>
            <c:otherwise>
                Tierart anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/tierart_edit.css"/>" />
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
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="tierart_owner">Eigentümer:</label>
                <div class="side-by-side">
                    <input type="text" name="tierart_owner" value="${tierart_form.values["tierart_owner"][0]}" readonly="readonly">
                </div>

                <label for="tierart_spezies">Spezies:</label>
                <div class="side-by-side">
                    <select name="tierart_spezies">
                        <option value="">Keine Spezies</option>

                        <c:forEach items="${spezien}" var="spezies">
                            <option value="${spezies.id}" ${tierart_form.values["tierart_spezies"][0] == spezies.id.toString() ? 'selected' : ''}>
                                <c:out value="${spezies.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="tierart_tierartname">
                    Bezeichnung:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="tierart_tierartname" value="${tierart_form.values["tierart_tierartname"][0]}">
                </div>
                
                <label for="tierart_status">
                    Status:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="tierart_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${tierart_form.values["tierart_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <br>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty tierart_form.errors}">
                <ul class="errors">
                    <c:forEach items="${tierart_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>