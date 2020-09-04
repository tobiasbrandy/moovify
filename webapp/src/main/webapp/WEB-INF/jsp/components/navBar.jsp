<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link href="<c:url value="/resources/css/navBar.css" />" rel="stylesheet">
</head>
<header id="navbar" uk-sticky="sel-target: .uk-navbar-container; cls-active: uk-navbar-sticky; bottom: #transparent-sticky-navbar">
    <nav class="uk-navbar-container" uk-navbar>
        <a class="uk-logo" href="<c:url value="/" />"><img id="nav-logo" src="<c:url value="/resources/images/logo.png"/>" alt="Moovify"></a>

        <div class="uk-navbar-right">
            <ul class="uk-navbar-nav">
                <li class="uk-visible@s">
                    <div>
                        <a class="uk-navbar-toggle" data-uk-search-icon ></a>
                        <div class="uk-drop" data-uk-drop="mode: click; pos: right-center; offset: 0; boundary: #navbar">
<%--                            TODO: change form action attribute --%>
                            <form action="#" class="uk-search uk-search-navbar uk-width-1-1">
                                <label for="nav-search"></label>
                                <input id="nav-search" name="searchBar" class="uk-search-input" type="search" placeholder="Search...">
                            </form>
                        </div>
                    </div>
                </li>
<%--                <li class="nav-item"><a class="uk-light" href="<c:url value="/" />">Home</a></li>--%>
                <li class="uk-navbar-item"><a href="<c:url value="/post/create" />">Create Post</a></li>
            </ul>

        </div>
    </nav>
</header>