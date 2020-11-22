<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="../constantParts/head.jspf" %>
</head>
<body>
<div class="container">
    <div id="topOfContainer">
        <div id="main-logo">
            <a href="/" id="logo">Zaplanuj <span>Jedzonko</span></a>
        </div>
        <div id="topMenu">
            <ul id="topMenuList">
                <li><a href="http://videokurs.pl"><span class="topMenuSpan">Coś tam</span></a></li>
                <li><a href="#"><span class="topMenuSpan">coś tam innego</span></a></li>
                <li><a href="#"><span class="topMenuSpan">Zakładka</span></a></li>
                <li><a href="#"><span class="topMenuSpan">Jeszcze</span></a></li>
                <li><a href="#"><span class="topMenuSpan">nie</span></a></li>
                <li><a href="#"><span class="topMenuSpan">Internet</span></a></li>
            </ul>
        </div>
        <div id="contactBox">
            <table>
                <tr><th>Kontakt</th></tr>
                <tr><td>Mateusz Machnio</td></tr>
                <tr><td>Tetmajera 103</td></tr>
                <tr><td>42-610 Miasteczko Śląskie</td></tr>
                <tr><td>Tel.: 792 772 131</td></tr>
                <tr><td>Mail: <a href="mailto:mateusz.machnio.9@gmial.com">mateusz.machnio.9@gmail.com</a></td></tr>
            </table>
        </div>
        <!-- koniec div topMenuList-->
    </div>
    <!-- koniec div "topOfContainer" -->

    <div id="content">
        <div id="topOfContent">

        </div>
        <div id="middleOfContent">

            <div id="text">
                <h1>Strona Główna</h1>
            </div>
        </div>
        <!-- koniec środka zawartości -->
        <div id="bottomOfContent">

        </div>
    </div>
    <!--  koniec divContent  -->
    <div id="footer">
        <img id="newsletterIcon" src="<c:url value="/static/css/images/newsletter_icon.gif"/>" alt="Newsletter icon"/>
        <div id="newsletterText">
            <div id="newsletterHeader">Zapisz się do Newslettera i bądź na bieżąco z najlepszymi przepisami

            </div>
            <div id="newsletterForm">
                <form action="#" method="post">
                    <div>
                        <label for="email">Twój e-mail:
                            <input type="text" id="email" class="inputText" name="email" />
                        </label>
                        <input class="submitButton" type="submit" value="Zapisuję się*" />

                        <input type="checkbox" name="email" id="checkbox"/>
                        <label for="checkbox" id="checkboxLabel">Akceptuję zasady prywatności.</label>
                    </div>
                </form>
            </div>
            <div id="newsletterInfo">
                *Będziemy Ciebie informować o nowych ciekawych <em>przepisach</em>. W każdym momencie, możesz wypisać się z Newsletera wysyłając do nas e-mail'a. Przyciskając button "Zapisuje się*" akceptujesz nasze <a href="#">zasady prywatności</a>.
            </div>
        </div>
    </div>
    <!--  koniec footer  -->
    <div id="copyright">
        &copy; 2020, created by <strong><em>Mateusz Machnio</em></strong>
    </div>
</div>
</body>
</html>