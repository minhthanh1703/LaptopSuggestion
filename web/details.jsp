<%-- 
    Document   : details
    Created on : Mar 21, 2020, 7:54:28 AM
    Author     : minht
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết sản phẩm</title>
        <style>
		.container {
			display: inline-block;

		}

		.left-container {
			float: left;
			position: relative;
			width: 596px;
			height: 600px;
			background-color: white;
			
		}

		.img-container {
			width: 300px;
			height: 300px;
			margin: 10px 150px;
			background-color: white;

		}

		.content-container {
			width: 597px;
			height: 277px;
			background-color: white;
			

		}

		.right-container {
			float: left;
			position: relative;
			width: 595px;
			height: 600px;
			background-color: white;
			
		}


		.card {
			float: left;
			box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
			max-width: 280px;
			text-align: center;
			font-family: arial;
			border-radius: 5%;
			padding: 10px;
			color: black
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
			margin-top: 10px;
		}

		#banner {
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
					<li><a href="#">ACER</a></li>
					<li><a href="#">APPLE</a></li>
					<li><a href="#">ASUS</a></li>
					<li><a href="#">DELL</a></li>
					<li><a href="#">FUJITSU</a></li>
					<li><a href="#">HP</a></li>
					<li><a href="#">LG</a></li>
					<li><a href="#">LENOVO</a></li>
					<li><a href="#">MICROSOFT</a></li>
					<li><a href="#">MSI</a></li>
				</ul>
			</li>
			<li><a href="#">Laptop theo nhu cầu</a>
				<ul id="submenu">
					<li><a href="#">LAPTOP ĐỒ HỌA - KỸ THUẬT</a></li>
					<li><a href="#">LAPTOP GAMING</a></li>
					<li><a href="#">LAPTOP WORKSTATION</a></li>
					<li><a href="#">LAPTOP CAO CẤP</a></li>
					<li><a href="#">LAPTOP HỌC TẬP - VĂN PHÒNG</a></li>
					<li><a href="#">LAPTOP DOANH NGHIỆP</a></li>
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

        <c:set var="dto" value="${requestScope.PRODUCTDETAILS}"/>
	<div id="wrapper">
		<div class="container">

			<div class="left-container">
				<div class="img-container">
					<img src="${dto.img}"
						alt="${dto.name}" width="300px" height="300px">
				</div>
				<div class="content-container">
                                        <p>Category: ${dto.category}</p>
                                        <p>Hãng: ${dto.brand}</p>
					<p>CPU: ${dto.cpu}</p>
					<p>Ram: ${dto.ram}</p>
					<p>Card màn hình: ${dto.vga}</p>
					<p>Màn hình: ${dto.display}</p>
					<p>Ổ cứng: ${dto.hardDisk}</p>
				</div>
			</div>
			<div class="right-container">
				<h1>${dto.name}</h1>
				<font color="red" size="6">Giá tham khảo: ${dto.price}</font>
			</div>
		</div>
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
