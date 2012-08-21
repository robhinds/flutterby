<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<script type="text/JavaScript">
	//style the left panel accordion boxes
	$(function() {
		$("#user_menu_action").addClass("ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
		.find("h3")
	    .addClass("ui-accordion-header ui-state-active ui-helper-reset ui-state-default ui-corner-top ui-corner-bottom")
	    .next()
	    .addClass("ui-accordion-content ui-accordion-content-active ui-helper-reset ui-widget-content ui-corner-bottom");
	});	
</script>

<!--  User control menu section -->
<div id="user_menu_action_wrapper" class="column_menu">
	<div id="user_menu_action">
		
		<h3><a href="#"><%= (String)pageContext.getAttribute("username", pageContext.REQUEST_SCOPE)%>'s Profile</a></h3>
		<div>
	    	<div id="user_profile">
				<div id="prof_image" class="profile_image">
					<img src="<c:url value='/resources/images/userProfile.png'/>" alt="image" /><br />
				</div><br/>
				<b>Username: </b> <%= (String)pageContext.getAttribute("username", pageContext.REQUEST_SCOPE)%>
				<br /><br />
				<b>Team: </b> 
					<a href="<c:url value='/team/'/><%= (String)pageContext.getAttribute("teamname", pageContext.REQUEST_SCOPE)%>">
						<%= (String)pageContext.getAttribute("teamname", pageContext.REQUEST_SCOPE)%>
					</a><a href="<c:url value='/team/orgchart/'/><%= (String)pageContext.getAttribute("teamname", pageContext.REQUEST_SCOPE)%>">(org chart)</a>
				<br /><br />
			</div>	
			<br/>    
	    </div>
	    
	</div>
</div>