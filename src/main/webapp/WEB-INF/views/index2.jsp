<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<tiles:insertAttribute name="header" />
</head>
<body onload="prettyPrint()">
	<tiles:insertAttribute name="siteHeader" />
	<div id="freeow" class="freeow freeow-top-right"></div>
	<br />
	<div id="container">

		<div id="left_col">
			<tiles:insertAttribute name="leftColumn" ignore="true" />
		</div>

		<div id="right_col">
			<div id="right_col_1">
				<tiles:insertAttribute name="rightColumn1" ignore="true" />
			</div>

			<div id="right_col_2">
				<tiles:insertAttribute name="rightColumn2" ignore="true" />
			</div>
		</div>


		<div id="page_content">
			<tiles:insertAttribute name="mainContent" />
		</div>

		<!-- Marker to seperate footer to ensure it is always docked -->
		<div class="clearfooter"></div>

	</div>

	<tiles:insertAttribute name="footer" />

</body>
</html>