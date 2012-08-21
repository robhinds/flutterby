<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<tiles:insertAttribute name="header" />
</head>
<body onload="prettyPrint()">
<tiles:insertAttribute name="siteHeader" />

<br />
<div id="container">

	<div id="widescreen_page_content">
		<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
			<tiles:insertAttribute name="mainContent" />
		</div>
	</div>	
	
	<!-- Marker to seperate footer to ensure it is always docked -->
	<div class="clearfooter"></div>
</div>

<tiles:insertAttribute name="footer" />

</body>
</html>