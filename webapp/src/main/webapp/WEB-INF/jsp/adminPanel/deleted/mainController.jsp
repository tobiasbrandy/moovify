<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="currentSelection" scope="request" type="java.lang.String"/>
<jsp:useBean id="query" scope="request" type="java.lang.String"/>
<jsp:useBean id="selectedView" scope="request" type="java.lang.String"/>

<%--
-- mainController.js required.
-- surrounding with form required
--%>
<div class="uk-margin-auto uk-margin-top">
    <h2 class="uk-article-title uk-margin-auto uk-text-center uk-text-primary">
        <spring:message code="admin.deleted.title" arguments="${selectedView}"/>
    </h2>
    <hr class="uk-divider-icon">
</div>

<div class="uk-flex uk-flex-middle">
    <div class="uk-search uk-search-large uk-width-expand">
        <span uk-search-icon></span>
        <input class="uk-search-input search-query-input" type="search" id="query-input" value="${query}" name="query" placeholder="<spring:message code="navbar.searchDots" />">
    </div>
    <div class="uk-width-small">
        <button class="uk-button uk-button-default uk-border-rounded search-button extended-button" type="submit"><spring:message code="navbar.search"/></button>
    </div>
</div>
<p class="uk-text-meta">
    <spring:message code="admin.deleted.searchResults"/>
</p>
<div class="uk-margin-medium-top">
    <ul class="uk-child-width-expand uk-tab">
        <li class="${currentSelection == 0 ? 'uk-active' : ''}">
            <c:url value="/admin/deleted/posts" var="postsURL">
                <c:param name="query" value="${query}"/>
            </c:url>
            <a href="${currentSelection == 0 ? '' : postsURL}"><spring:message code="admin.deleted.posts"/></a>
        </li>
        <li class="${currentSelection == 1 ? 'uk-active' : ''}">
            <c:url value="/admin/deleted/comments" var="commentsURL">
                <c:param name="query" value="${query}"/>
            </c:url>
            <a href="${currentSelection == 1 ? '' : commentsURL}"><spring:message code="admin.deleted.comments"/></a>
        </li>
        <li class="${currentSelection == 2 ? 'uk-active' : ''}">
            <c:url value="/admin/deleted/users" var="usersURL">
                <c:param name="query" value="${query}"/>
            </c:url>
            <a href="${currentSelection == 2 ? '' : usersURL}"><spring:message code="admin.deleted.users"/></a>
        </li>
    </ul>
</div>
