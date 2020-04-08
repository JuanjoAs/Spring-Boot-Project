<!-- chart.jsp-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	window.onload = function() {

		var dps = [ [], [], [], [] ];
		var chart = new CanvasJS.Chart("chartContainer", {
			animationEnabled : true,
			title : {
				text : "Fixed Voice Call Volume - UK"
			},
			axisY : {
				includeZero : false,
				title : "Call Duration (in billion minutes)"
			},
			legend : {
				cursor : "pointer",
				itemclick : toggleDataSeries
			},
			toolTip : {
				shared : true
			},
			data : [ {
				type : "stackedBar",
				name : "Local Calls",
				showInLegend : true,
				xValueType : "dateTime",
				xValueFormatString : "YYYY",
				yValueFormatString : "#,##0.0 billion minutes",
				dataPoints : dps[0]
			}, {
				type : "stackedBar",
				name : "Call to Mobile",
				showInLegend : true,
				xValueType : "dateTime",
				xValueFormatString : "YYYY",
				yValueFormatString : "#,##0.0 billion minutes",
				dataPoints : dps[1]
			}, {
				type : "stackedBar",
				name : "International Call",
				showInLegend : true,
				xValueType : "dateTime",
				xValueFormatString : "YYYY",
				yValueFormatString : "#,##0.0 billion minutes",
				dataPoints : dps[2]
			}, {
				type : "stackedBar",
				name : "Other Calls",
				showInLegend : true,
				xValueType : "dateTime",
				xValueFormatString : "YYYY",
				yValueFormatString : "#,##0.0 billion minutes",
				dataPoints : dps[3]
			} ]
		});

		function toggleDataSeries(e) {
			if (typeof (e.dataSeries.visible) === "undefined"
					|| e.dataSeries.visible) {
				e.dataSeries.visible = false;
			} else {
				e.dataSeries.visible = true;
			}
			chart.render();
		}

		var xValue;
		var yValue;

		<c:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">
		<c:forEach items="${dataPoints}" var="dataPoint">
		xValue = parseInt("${dataPoint.x}");
		yValue = parseFloat("${dataPoint.y}");
		dps[parseInt("${loop.index}")].push({
			x : xValue,
			y : yValue
		});
		</c:forEach>
		</c:forEach>

		chart.render();

	}
</script>
</head>
<body>
	<nav class="navbar navbar-expand-md navbar-dark bg-primary navColor">
	<button class="navbar-toggler navbar-toggler-right" type="button"
		data-toggle="collapse" data-target="#navbarNavDropdown"
		aria-controls="navbarNavDropdown" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<a class="navbar-brand" href="#"> <img
		src="../../../static/images/logov21.png" width="130rem"
		class="d-inline-block align-top" alt="">
	</a>
	<div class="collapse navbar-collapse" id="navbarNavDropdown">
		<ul class="navbar-nav ml-auto">
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> ${userName}
			</a>
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="navbarDropdown">
					<a class="dropdown-item" href="#">Action</a> <a
						class="dropdown-item" href="#">Another action</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="/logout">Log out</a>
				</div></li>

			</li>

			<!-- This menu is hidden in bigger devices with d-sm-none. 
           The sidebar isn't proper for smaller screens imo, so this dropdown menu can keep all the useful sidebar itens exclusively for smaller screens  -->
			<li class="nav-item dropdown d-sm-block d-md-none"><a
				class="nav-link dropdown-toggle" href="#" id="smallerscreenmenu"
				data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					Menu </a>
				<div class="dropdown-menu" aria-labelledby="smallerscreenmenu">
					<a class="dropdown-item" href="#top">hjsahgjsa</a> <a
						class="dropdown-item" href="#top">Profile</a> <a
						class="dropdown-item" href="#top">Tasks</a> <a
						class="dropdown-item" href="#top">Etc ...</a>
				</div></li>
			<!-- Smaller devices menu END -->
		</ul>
	</div>
	</nav>
	<!-- NavBar END -->
	<!-- Bootstrap row -->
	<div class="row" id="body-row">
		<!-- Sidebar -->
		<div id="sidebar-container" class="sidebar-expanded d-none d-md-block">
			<!-- d-* hiddens the Sidebar in smaller devices. Its itens can be kept on the Navbar 'Menu' -->
			<!-- Bootstrap List Group -->
			<ul class="list-group">
				<!-- Separator with title -->
				<li
					class="list-group-item sidebar-separator-title text-muted d-flex align-items-center menu-collapsed">
					<small>MENU PRINCIPAL</small>
				</li>
				<!-- /END Separator -->
				<!-- Menu with submenu -->
				<a href="#submenu1" data-toggle="collapse" aria-expanded="false"
					class="bg-dark list-group-item list-group-item-action flex-column align-items-start">
					<div class="d-flex w-100 justify-content-start align-items-center">
						<span class="fa fa-dashboard fa-fw mr-3"></span> <span
							class="menu-collapsed">Inicio</span> <span
							class="submenu-icon ml-auto"></span>
					</div>
				</a>
				<!-- Submenu content -->
				<div id='submenu1' class="collapse sidebar-submenu">
					<a href="#"
						class="list-group-item list-group-item-action bg-dark text-white">
						<span class="menu-collapsed">Añadir reserva</span>
					</a> <a href="#"
						class="list-group-item list-group-item-action bg-dark text-white">
						<span class="menu-collapsed">Gestionar reserva</span>
					</a>
				</div>
				<a href="#submenu2" data-toggle="collapse" aria-expanded="false"
					class="bg-dark list-group-item list-group-item-action flex-column align-items-start">
					<div class="d-flex w-100 justify-content-start align-items-center">
						<span class="fa fa-user fa-fw mr-3"></span> <span
							class="menu-collapsed">Perfil</span> <span
							class="submenu-icon ml-auto"></span>
					</div>
				</a>
				<!-- Submenu content -->
				<div id='submenu2' class="collapse sidebar-submenu">
					<a href="#"
						class="list-group-item list-group-item-action bg-dark text-white">
						<span class="menu-collapsed">Configuración</span>
					</a> <a href="#"
						class="list-group-item list-group-item-action bg-dark text-white">
						<span class="menu-collapsed">Contraseña</span>
					</a>
				</div>
				<a href="#" class="bg-dark list-group-item list-group-item-action">
					<div class="d-flex w-100 justify-content-start align-items-center">
						<span class="fa fa-newspaper fa-fw mr-3"></span> <span
							class="menu-collapsed">Noticias</span>
					</div>
				</a>
				<!-- Separator with title -->
				<li
					class="list-group-item sidebar-separator-title text-muted d-flex align-items-center menu-collapsed">
					<small>OPTIONS</small>
				</li>
				<!-- /END Separator -->
				<a href="#" class="bg-dark list-group-item list-group-item-action">
					<div class="d-flex w-100 justify-content-start align-items-center">
						<span class="fa fa-calendar fa-fw mr-3"></span> <span
							class="menu-collapsed">Calendario</span>
					</div>
				</a>
				<a href="#" class="bg-dark list-group-item list-group-item-action">
					<div class="d-flex w-100 justify-content-start align-items-center">
						<span class="fa fa-envelope-o fa-fw mr-3"></span> <span
							class="menu-collapsed">Mensajes <span
							class="badge badge-pill badge-primary ml-2">5</span></span>
					</div>
				</a>
				<!-- Separator without title -->
				<li class="list-group-item sidebar-separator menu-collapsed"></li>
				<!-- /END Separator -->
				<a href="#" class="bg-dark list-group-item list-group-item-action">
					<div class="d-flex w-100 justify-content-start align-items-center">
						<span class="fa fa-question fa-fw mr-3"></span> <span
							class="menu-collapsed">Ayuda</span>
					</div>
				</a>
				<a href="#top" data-toggle="sidebar-colapse"
					class="bg-dark list-group-item list-group-item-action d-flex align-items-center">
					<div class="d-flex w-100 justify-content-start align-items-center">
						<span id="collapse-icon" class="fa fa-2x mr-3"></span> <span
							id="collapse-text" class="menu-collapsed">Collapse</span>
					</div>
				</a>
			</ul>
			<!-- List Group END-->
		</div>
		<!-- sidebar-container END -->



		<!-- MAIN -->
		<div class="col p-4">
			<main class="container">
			<div id="chartContainer" style="height: 370px; width: 100%;"></div>
			<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
			</main>
		</div>
		<!-- Main Col END -->
	</div>
	<!-- body-row END -->



	<!--  <div th:insert="fragments/footer :: footer"></div> -->
	<script src="../../../static/js/js.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
</body>
</html>