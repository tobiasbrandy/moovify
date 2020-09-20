<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<body>
<c:url value="/post/create" var="action"/>
<form:form modelAttribute="postCreateForm" class="uk-form-stacked uk-margin-auto uk-padding-large uk-padding-remove-vertical" method="post" action='${action}' id="new-post-form">
<%--TODO: mostrar los errores dentro del modal sin que este se cierre, podria ser mas hci--%>
    <form:errors path="movies" element="p" cssClass="error"/>
    <form:errors path="tags" element="p" cssClass="error"/>
    <spring:message code="post.create.titlePlaceholder" var="titlePlaceholder"/>
    <div class="uk-margin-left uk-margin-bottom" >
        <form:label path="title" class="uk-form-label uk-text-secondary uk-margin-auto" for="title-text" ><spring:message code="post.create.newPostTitle"/>
            <form:input path="title" class="uk-input uk-form-small" id="title-text" type="text" placeholder="${titlePlaceholder}"/>
        </form:label>
        <form:errors path="title" element="p" cssClass="error" cssStyle="color: red;" />
    </div>

    <div class="uk-margin-left uk-margin-bottom">
        <form:label path="category" class="uk-form-label uk-text-secondary uk-margin-auto" for="category-select" ><spring:message code="post.create.newPostCategory"/></form:label>
        <div class="uk-form-controls">
            <form:select path="category"  class="uk-select" id="category-select">
                <c:forEach items="${categories}" var="category">
                    <option hidden disabled selected value> <spring:message code="post.create.selectCategory"/> </option>
                    <option value="${category.id}"><spring:message code="${category.name}"/></option>
                </c:forEach>
            </form:select>
            <form:errors path="category" element="p" cssClass="error" cssStyle="color: red;"/>
        </div>

    <div class="uk-margin-auto">
        <form:errors path="body" element="p" cssClass="error" cssStyle="color: red;"/>
        <form:label path="body" for="create-post-data"><spring:message code="post.create.body"/>
            <form:textarea path="body" id="create-post-data"/>
        </form:label>
    </div>
</form:form>
</body>
</html>
