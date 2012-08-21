<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<!--  insert search box here  -->
<tags:searchBox searchContext="all"></tags:searchBox>


<!-- insert connections here  -->
<div id="connectionsDiv" class = "rounded_menu txt_lightgrey">
		<br/>
		Following People..
		<br/><br/>
		<j:forEachJson items="${connections}" var="connection" counter="counter">
			<span class="bubbleInfo">
				<a href="<c:url value='/user/'/><j:jsonGetString key="name" json="${connection}" />">
					<span class="connectionHighlight trigger"><j:jsonGetString key="name" json="${connection}" /></span>
				</a>
				<span class="popup rounded_menu">
					<!-- your information content -->
					<img src="<c:url value='/resources/images/anonPhoto.png'/>" />
					<br/><strong>Team:</strong> <j:jsonGetString key="teamName" json="${connection}" /><br/>
					<strong>Status:</strong> "<j:jsonGetString key="status" json="${connection}" />"
				</span>
			</span>  
		</j:forEachJson>
		<br/><br/>
</div>