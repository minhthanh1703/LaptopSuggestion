<%-- 
    Document   : search
    Created on : Mar 18, 2020, 3:56:44 PM
    Author     : minht
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
        <style>
            .card {
                float: left;
                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
                width: 270px;
                text-align: center;
                font-family: arial;
                border-radius: 5%;
                padding: 10px;
                color: black;
            }

            .price {
                color: grey;
                font-size: 22px;
            }

            .card button {
                border: none;
                outline: 0;
                padding: 12px;
                color: white;
                background-color: #365899;
                text-align: center;
                cursor: pointer;
                width: 100%;
                font-size: 18px;
            }

            .card button:hover {
                opacity: 0.7;
            }

            .pagination{
                text-align: center;
            }

            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
            }

            .pagination a.active {
                background-color: #365899;
                color: white;
            }

            .pagination a:hover:not(.active) {background-color: #ddd;}

            #top {
                width: 1200px;
                margin: auto;
                height: 50px;
                background-image: url(game.jpg);
                color: black;
                text-align: center;
            }

            #menu {
                width: 1200px;
                margin: auto;
                height: 40px;
                background-color: #365899;
                text-align: center;
            }

            #menu ul {
                list-style-type: none;
                padding: 0px;

            }

            #menu ul li {
                float: left;
                width: 239px;
                border-left: 1px solid white;
                padding-top: 10px;
                position: relative;
            }

            #menu ul li a {
                text-decoration: none;
                color: white;
            }

            #menu ul li a:hover {
                background: #34495E;
            }

            #submenu {
                display: none;
                position: absolute;
                z-index: 90;
                background: #365899;
            }

            #menu li:hover #submenu {
                display: block;
            }

            #submenu li {
                border-right: 1px solid white;
                border-bottom: 1px dashed white;
            }

            #wrapper {
                width: 1200px;
                margin: auto;
                height: auto;
                background: #365899;
                margin-top: 10px;
            }
            #banner{
                text-align: center;
            }

            #main {
                width: 1200px;
                height: auto;
                background: white;
                float: left;
                color: white;

            }

            #bottom {
                width: 1200px;
                margin: auto;
                margin-top: 10px;
                height: 300px;
                background: pink;
                float: left;
                border: 2px solid #365899;
            }

            #bottomlist {
                width: 400px;
                height: 150px;
                float: left;
                background: white;

            }

        </style>
        <script>
            var i = 0;
            var images = [];
            var time = 3000;

            images[0] = 'img/hinh1.jpg';
            images[1] = 'img/hinh2.jpg';
            images[2] = 'img/hinh3.jpg';

            function changeImage() {

                document.slide.src = images[i];

                if (i < images.length - 1) {
                    i++;
                } else {
                    i = 0;
                }
                setTimeout("changeImage()", time);
            }

            window.onload = changeImage;

            document.addEventListener("DOMContentLoaded", function () {
                var lazyloadImages = document.querySelectorAll("img.lazy");
                var lazyloadThrottleTimeout;

                function lazyload() {
                    if (lazyloadThrottleTimeout) {
                        clearTimeout(lazyloadThrottleTimeout);
                    }

                    lazyloadThrottleTimeout = setTimeout(function () {
                        var scrollTop = window.pageYOffset;
                        lazyloadImages.forEach(function (img) {
                            if (img.offsetTop < (window.innerHeight + scrollTop)) {
                                img.src = img.dataset.src;
                                img.classList.remove('lazy');
                            }
                        });
                        if (lazyloadImages.length == 0) {
                            document.removeEventListener("scroll", lazyload);
                            window.removeEventListener("resize", lazyload);
                            window.removeEventListener("orientationChange", lazyload);
                        }
                    }, 20);
                }

                document.addEventListener("scroll", lazyload);
                window.addEventListener("resize", lazyload);
                window.addEventListener("orientationChange", lazyload);
            });

        </script>
    </head>
    <body>
        <div id="top">
            <h1>Tư vấn laptop</h1>
        </div>
        <div id="menu">
            <ul>
                <li style="border-left: none;"><a href="hometestXML.jsp">Làm lại quiz</a></li>
                <li><a href="#">Hãng</a>

                    <ul id="submenu">
                        <c:if test="${requestScope.BRANDS != null}">
                            <c:forEach items="${requestScope.BRANDS}" varStatus="counter" var="dto">
                                <li><a href="/final_v2/DispatcherServlet?action=Search&brandId=${dto.id}">${dto.brandName}</a></li>
                                </c:forEach>
                            </c:if>
                    </ul>
                </li>
                <li><a href="#">Laptop theo nhu cầu</a>
                    <ul id="submenu">
                        <c:if test="${requestScope.CATEGORYS != null}">
                            <c:forEach items="${requestScope.CATEGORYS}" varStatus="counter" var="dto">
                                <li><a href="/final_v2/DispatcherServlet?action=Search&categoryId=${dto.id}">${dto.categoryName}</a></li>
                                </c:forEach>
                            </c:if>
                    </ul>
                </li>
                <li><a href="#">Thông tin</a>
                    <ul id="submenu">
                        <li><a href="#">Thông tin về shop</a></li>
                        <li><a href="#">Liên hệ với shop</a></li>
                    </ul>
                </li>
                <li><a href="#">Liên hệ</a></li>
            </ul>
        </div>

        <div id="banner">
            <img name="slide" width="1200px" height="350px">
        </div>

        <div id="wrapper">
            <div id="main">
                <c:if test="${requestScope.RESULTLIST != null}">
                    <c:if test="${not empty requestScope.RESULTLIST}">
                        <div id="sanpham">
                            <font color="black" size="10">Sản phẩm</font>
                        </div>
                        <c:forEach items="${requestScope.RESULTLIST}" varStatus="counter" var="dto">
                            <form action="DispatcherServlet">
                                <div class="card">
                                    <img class="lazy" src="img/giphy.gif" data-src="${dto.img}"
                                         alt="${dto.name}" width="250px" height="250px" >
                                    <h3>${dto.name}</h3>
                                    <p class="price">${dto.price}</p>
                                    <input type="hidden" name="productId" value="${dto.id}"/>
                                    <p><button type="submit" name="action" value="Details">Chi tiết</button></p>
                                </div>
                            </form>
                        </c:forEach>

                    </c:if>
                    <c:if test="${empty requestScope.RESULTLIST}">
                        <div id="sanpham">
                            <font color="black" size="10">Không có sản phẩm phù hợp với bạn</font>
                        </div>
                    </c:if>
                </c:if>
            </div></br>
            <div class="pagination">
                <c:set var="url" value="${sessionScope.QUERYSTRING}"/>
                <c:if test="${requestScope.RESULTLIST != null}">

                    <c:forEach var="i" begin="1" end="${requestScope.MAXPAGE}">
                        <c:if test="${requestScope.PAGENUMER == i}">
                            <a class="active" href="${url}&pageNumber=${i}">${i}</a>
                        </c:if>
                        <c:if test="${requestScope.PAGENUMER != i}">
                            <a href="${url}&pageNumber=${i}">${i}</a>
                        </c:if>  
                    </c:forEach>

                </c:if>
            </div
            </br>
            <div id="bottom">
                <div id="bottomlist"><a href="#">
                        <h4 align="left">Thông tin</h4>
                        <p align="left">Giới thiệu về shop</p>
                        <p align="left">Chính sách bảo mật</p>
                </div></a>

                <div id="bottomlist"><a href="#"><a href="#">
                            <h4 align="left">Chăm sóc khách hàng</h4>
                            <p align="left">Liên hệ</p>
                            <p align="left">Sơ đồ trang</p>
                            <p align="left">Chế độ bảo hành</p>
                        </a>
                </div>

                <div id="bottomlist"><a href="#">
                        <h4 align="left">Liên hệ</h4>
                        <p align="left"><b>Địa chỉ: </b>Dĩ An, Bình Dương</p>
                        <p align="left"><b>Số điện thoại: </b> 0904403502</p>
                    </a>
                </div>

                <div id="bottomlist" align="left"><br><br><img src="img/dathongbao.png"></div>
                <div id="bottomlist" align="center"><br><br>© Copyright 2018 Minh Thành</div>
                <div id="bottomlist"><br><br><img src="img/facebook.png">&emsp;<img src="img/google_plus.png">&emsp;<img
                        src="img/youtube.png"></div>
            </div>

        </div>
    </body>
</html>
