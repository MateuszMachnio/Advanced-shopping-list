<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dodawanie planu</title>
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
                <h1>Dodawanie planu</h1>

                <form:form modelAttribute="plan">
                    <table>
                        <tr>
                            <td><form:label path="name">Nazwa planu: </form:label></td>
                            <td><form:input path="name"/></td>
                            <td><form:errors path="name" cssClass="error"/></td>
                        </tr>

                        <tr>
                            <td><form:label path="description">Opis planu: </form:label></td>
                            <td><form:textarea cols="20" rows="5" path="description"/></td>
                            <td><form:errors path="description" cssClass="error"/></td>
                        </tr>
                    </table>
                    <br />

                    <input type="submit" value="Dodaj plan">
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
