<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Home</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" th:href="@{styles/style.css}" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/33a002ef8f.js"
	crossorigin="anonymous"></script>
<script type="text/javascript">
window.onload = function() {
 
var dps = [[], [], [], []];
var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	title: {
		text: "Fixed Voice Call Volume - UK"
	},
	axisY: {
		includeZero: false,
		title: "Call Duration (in billion minutes)"
	},
	legend:{
		cursor: "pointer",
		itemclick: toggleDataSeries
	},
	toolTip: {
		shared: true
	},
	data: [{
		type: "stackedBar",
		name: "Local Calls",
		showInLegend: true,
		xValueType: "dateTime",
		xValueFormatString: "YYYY",
		yValueFormatString: "#,##0.0 billion minutes",
		dataPoints: dps[0]
	},{
		type: "stackedBar",
		name: "Call to Mobile",
		showInLegend: true,
		xValueType: "dateTime",
		xValueFormatString: "YYYY",
		yValueFormatString: "#,##0.0 billion minutes",
		dataPoints: dps[1]
	},{
		type: "stackedBar",
		name: "International Call",
		showInLegend: true,
		xValueType: "dateTime",
		xValueFormatString: "YYYY",
		yValueFormatString: "#,##0.0 billion minutes",
		dataPoints: dps[2]
	},{
		type: "stackedBar",
		name: "Other Calls",
		showInLegend: true,
		xValueType: "dateTime",
		xValueFormatString: "YYYY",
		yValueFormatString: "#,##0.0 billion minutes",
		dataPoints: dps[3]
	}]
});
 
function toggleDataSeries(e){
	if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	}
	else{
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
	<div id="chartContainer" style="height: 370px; width: 100%;"></div>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html>