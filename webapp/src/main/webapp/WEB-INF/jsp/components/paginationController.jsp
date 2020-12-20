<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="collection" scope="request" type="ar.edu.itba.paw.models.PaginatedCollection"/>
<jsp:useBean id="baseURL" scope="request" type="java.lang.String"/>
<jsp:useBean id="numberOfInputs" scope="request" type="java.lang.Long"/>

<%-- Requirements:
-- When using this component there needs to be a form surrounding it with the apropiate URL.
-- Include paginationController.js
--%>

<div class="uk-flex uk-margin-small-top uk-flex-baseline">
    <div class="uk-width-auto uk-flex uk-flex-wrap uk-flex-baseline">
        <label for="pagination-page-size" class="uk-form-label uk-margin-small-right uk-width-auto">
            <spring:message code="paginationController.pageSize.message"/>
        </label>
        <select id="pagination-page-size" name="pageSize" class="uk-select uk-form-blank uk-width-expand">
            <option <c:out value="${ collection.pageSize == 2 ? 'selected' : ''}"/> label="2" value="2"><c:out value="2"/></option>
            <option <c:out value="${ collection.pageSize == 5 ? 'selected' : ''}"/> label="5" value="5"><spring:message code="paginationController.pageSize.five"/></option>
            <option <c:out value="${ collection.pageSize == 10 ? 'selected' : ''}"/> label="10" value="10"><spring:message code="paginationController.pageSize.ten"/></option>
            <option <c:out value="${ collection.pageSize == 25 ? 'selected' : ''}"/> label="25" value="25"><spring:message code="paginationController.pageSize.twentyfive"/></option>
            <option <c:out value="${ collection.pageSize == 35 ? 'selected' : ''}"/> label="35" value="35"><c:out value="35"/></option>
        </select>
    </div>
    <ul id="pagination-page-selector" class="uk-width-expand uk-pagination uk-flex-center" uk-margin>
        <c:if test="${collection.pageNumber > 0}">
            <c:url value = "${baseURL}" var = "pageURL">
                <c:param name = "pageNumber" value = "${collection.pageNumber - 1}"/>
                <c:param name = "pageSize" value = "${collection.pageSize}"/>
            </c:url>
            <li><a href="${pageURL}">
                <span uk-pagination-previous></span>
            </a></li>
        </c:if>

        <c:set value="${collection.pageNumber - numberOfInputs   >= 0 ? collection.pageNumber - numberOfInputs : 0}" var="firstPage"/>
        <c:forEach begin="0" end="${numberOfInputs * 2}" var="index">
            <c:if test="${firstPage + index <= collection.lastPageNumber}">
                <c:url value = "${baseURL}" var = "pageURL">
                    <c:param name = "pageNumber" value = "${firstPage + index}"/>
                    <c:param name = "pageSize" value = "${collection.pageSize}"/>
                </c:url>
                <li class="${ firstPage + index == collection.pageNumber ? 'uk-active' : ''}">
                    <a href="${pageURL}" class="${ firstPage + index == collection.pageNumber ? 'uk-disabled' : ''}"><c:out value="${firstPage + index + 1}"/></a>
                </li>
            </c:if>
        </c:forEach>

        <c:if test="${collection.pageNumber < collection.lastPageNumber }">
            <c:url value = "${baseURL}" var = "pageURL">
                <c:param name = "pageNumber" value = "${collection.pageNumber + 1}"/>
                <c:param name = "pageSize" value = "${collection.pageSize}"/>
            </c:url>
            <li><a href="${pageURL}"><span uk-pagination-next></span></a></li>
        </c:if>
    </ul>
</div>