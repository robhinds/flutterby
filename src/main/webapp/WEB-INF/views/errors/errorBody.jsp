<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
	<div id="error_msg">
	<br/>
	<h1>Oh Dear! Looks like there's been an Error..</h1><br/><br/>
	<%= (String)pageContext.getAttribute("errorMessage", pageContext.REQUEST_SCOPE)%>
	</div>
</div>