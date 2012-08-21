<%@ tag language="java" pageEncoding="ISO-8859-1" description="display a dropdown populated with contacts (teams/people)"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>


<%@ attribute name='searchContext' required="true" description='Search context' %>

<div id="searchBox" class = "rounded_menu txt_lightgrey">
	<form action="<c:url value='/search/${searchContext}'/>" method="get">
		<input type='text' id="searchBox" name="searchTerm" defaultVal="search.." class="clearText" />
		<input type="submit" id="search" value="Go">
	</form> 
</div>
