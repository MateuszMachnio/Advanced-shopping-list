<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edycja ilości składniku</title>
    <%@include file="../../../constantParts/head.jspf" %>
</head>
<body>
<div class="container">
    <%@include file="../../../constantParts/loggedHeader.jspf"%>

    <div id="content">
        <div id="topOfContent" style="margin-top: 10px">
        </div>

        <div id="middleOfContent">
            <%@include file="../../../constantParts/loggedSidebar.jsp"%>

            <div id="text" style="text-align: center">
                <h1>Edycja ilości składniku</h1>

                <form:form modelAttribute="ingredientWithQuantity" action="/logged-user/recipe/editing-ingredient" method="post">
                    <form:hidden path="id"/>
                    <table>
                        <tr>
                            <td><form:label path="ingredient">Nazwa: </form:label></td>
                            <form:hidden path="ingredient" value="${ingredientId}" />
                            <td><input type="text" value="${ingredientName}" readonly></td>
                            <td><form:errors path="ingredient" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td><form:label path="quantity">Ilość: </form:label></td>
                            <td><form:input path="quantity" />gram</td>
                            <td><form:errors path="quantity" cssClass="error"/></td>
                        </tr>
                    </table>
                    <br />

                    <input type="hidden" name="setId" value="${setId}">
                    <input type="hidden" name="recipeId" value="${recipeId}">
                    <input type="submit" value="Zapisz">
                    <%--                    <form:button><a href="<c:url value="/logged_user/dashboard"/>">Gotowe</a></form:button>--%>
                </form:form>
            </div>
        </div>
        <!-- koniec środka zawartości -->
        <div id="bottomOfContent">
        </div>
    </div>
    <!--  koniec divContent  -->
    <%@include file="../../../constantParts/footer.jspf"%>
</div>
</body>
</html>
