<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<script type="text/JavaScript">
	$(document).ready(function(){

		$(function() {
			$( "#todobutton", ".quickAction" ).click(function() {
				centerPopup("#todoBackground", "#todoPopup");
				loadPopup(null, "#todoBackground", "#todoPopup");
			});
		});
		$(function() {
			$( "#questionbutton", ".quickAction" ).click(function() {
				centerPopup("#questionBackground", "#questionPopup");
				loadPopup(null, "#questionBackground", "#questionPopup");
			});
		});
		$(function() {
			$( "#workticketbutton", ".quickAction" ).click(function() {
				centerPopup("#workTicketBackground", "#workTicketPopup");
				loadPopup(null, "#workTicketBackground", "#workTicketPopup");
			});
		});
		$(function() {
			$( "#msgbutton", ".quickAction" ).click(function() {
				centerPopup("#msgBackground", "#msgPopup");
				loadPopup(null, "#msgBackground", "#msgPopup");
			});
		});

		$("#todoPopupClose").click(function(){
			disablePopup("#todoBackground", "#todoPopup");
		});
		$("#todoBackground").click(function(){
			disablePopup("#todoBackground", "#todoPopup");
		});
		$("#questionPopupClose").click(function(){
			disablePopup("#questionBackground", "#questionPopup");
		});
		$("#questionBackground").click(function(){
			disablePopup("#questionBackground", "#questionPopup");
		});
		$("#workTicketPopupClose").click(function(){
			disablePopup("#workTicketBackground", "#workTicketPopup");
		});
		$("#workTicketBackground").click(function(){
			disablePopup("#workTicketBackground", "#workTicketPopup");
		});
		$("#msgPopupClose").click(function(){
			disablePopup("#msgBackground", "#msgPopup");
		});
		$("#msgBackground").click(function(){
			disablePopup("#msgBackground", "#msgPopup");
		});
	});

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
		
		<h3><a href="#">Your Profile</a></h3>
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
				
				<b>Latest Status: </b>
					<span class="status_text" id="status_text">
						"<%= (String)pageContext.getAttribute("currentStatus", pageContext.REQUEST_SCOPE)%>"
					</span>
					<br/><br/>
				
				<!-- <a href="<c:url value='/profile/edit'/>"> Edit Profile</a> -->
			</div>	
			<br/>    
	    </div>
	    
	    <h3><a href="#">Quick Actions</a></h3>
	    <div class="quickAction">
	    	<a href="#"><img id="workticketbutton" src="<c:url value='/resources/images/createTicketBtn.png'/>" alt="Raise Work Ticket" /></a><br/>
	    	<a href="#"><img id="questionbutton" src="<c:url value='/resources/images/createQuestionBtn.png'/>" alt="Ask Question" /></a><br/>
	    	<a href="#"><img id="todobutton" src="<c:url value='/resources/images/createTodoBtn.png'/>" alt="Create To Do" /></a><br/>
	    	<a href="#"><img id="msgbutton" src="<c:url value='/resources/images/createMessageBtn.png'/>" alt="Send Message" /></a><br/>
	    </div>
	</div>
</div>