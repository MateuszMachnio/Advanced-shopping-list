<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dodawanie przepisu</title>
    <%@include file="../../constantParts/head.jspf" %>
</head>
<body>
<div class="container">
    <%@include file="../../constantParts/loggedHeader.jspf"%>

    <div id="content">
        <div id="topOfContent" style="margin-top: 10px">
        </div>

        <div id="middleOfContent">
            <%@include file="../../constantParts/loggedSidebar.jsp"%>

            <div id="text" style="text-align: center">
                <h1>Dodawanie składniku do przepisu</h1>
                <c:if test="${setWithIngredients != null}">
                    <table>
                        <tr>
                            <th>składnik</th>
                            <th>ilość</th>
                        </tr>

                        <c:forEach items="${setWithIngredients.ingredientsWithQuantities}" var="ingredientWithQuantity">
                            <tr>
                                <td>${ingredientWithQuantity.ingredient.name}</td>
                                <td>${ingredientWithQuantity.quantity}</td>
                            </tr>
                        </c:forEach>
                    </table>

                </c:if>



                <form:form modelAttribute="ingredientsWithQuantity">
                    <table>
                        <tr>
                            <td><form:label path="ingredient">Składnik: </form:label></td>
                            <td>
                                <form:select path="ingredient">
                                <form:option value="0" label="--select ingredient--"/>
                                <form:options items="${ingredients}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </td>
                            <td><form:errors path="ingredient" cssClass="error"/></td>
                        </tr>

                        <tr>
                            <td><form:label path="quantity">Ilość: </form:label></td>
                            <td><form:input path="quantity"/>gram</td>
                            <td><form:errors path="quantity" cssClass="error"/></td>
                        </tr>
                    </table>
                    <br />

                    <input type="hidden" name="setId" value="${setWithIngredients.id}">

                    <input type="submit" <c:if test="${setWithIngredients != null}"> formaction="/logged-user/add-next-ingredient-to-set" formmethod="post" </c:if> value="Dodaj składnik">
                    <form:button><a href="<c:url value="/logged_user/dashboard"/>">Powrót</a></form:button>

                </form:form>

            </div>
        </div>
        <!-- koniec środka zawartości -->
        <div id="bottomOfContent">
        </div>
    </div>
    <!--  koniec divContent  -->
    <%@include file="../../constantParts/footer.jspf"%>
</div>
</body>
</html>
