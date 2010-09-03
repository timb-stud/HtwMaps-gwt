var maps;
var points;
var pointCount;
var polyline;
var markerStart;
var markerZiel;

function init() {
	maps = new Mapstraction('map', 'openstreetmap');
	points = [];
	pointCount = 0;
	var myPoint = new LatLonPoint(51.1632400, 10.4451736);
	maps.setCenterAndZoom(myPoint, 6);
	maps.enableScrollWheelZoom();
	maps.addControls({
		pan : true,
		zoom : 'large',
		map_type : true
	});
}

function addPoint(lat, lon) {
	points[pointCount++] = new LatLonPoint(lat, lon);
}

function drawPolyLine() {
	polyline = new Polyline(points);
	polyline.setColor("#FF0000");
	polyline.setOpacity(0.5);
	polyline.setWidth(5);
	maps.addPolyline(polyline);
	points = [];
	pointCount = 0;
}

function addMarker(lat, lon, text) {
	marker = new Marker(new LatLonPoint(lat, lon));
	maps.addMarkerWithData(marker, {
		infoBubble : text
	});
}

function removePolylineAndMarker() {
	maps.removeAllPolylines();
	maps.removeAllMarkers();
	var myPoint = new LatLonPoint(51.1632400, 10.4451736);
	maps.setCenterAndZoom(myPoint, 6);
}

function removePolyline() {
	maps.removeAllPolylines();
}

function autoCenterAndZoom() {
	maps.autoCenterAndZoom();
}

function loadImageOn() {
	document.getElementById("htwMapsLoad").style.visibility = "visible";
}

function loadImageOff() {
	document.getElementById("htwMapsLoad").style.visibility = "hidden";
}