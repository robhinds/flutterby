<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script type="text/JavaScript">

	var url = "${pageContext.request.contextPath}";
	$(document).ready(function(){
		$('#createWorkTicket').bind("click", { url: url}, createWorkTicket);
	});
</script>


<div class="content_header_text">
</div>
	<input id="workTicketTitle" type="text" defaultVal="Heading.." class="autoWidth clearText">
	<textarea id="workTicketDescription" rows="9" cols="2" defaultVal="Details.." class="clearText"></textarea>
	Assign To:
	<tags:contactDropDown identifier="ticket" ></tags:contactDropDown>
	<br/> 
	Priority:
	<select name="priority" id="priority" size="1" class="autoWidth">
  		<option></option>
		<option value="LOW">Low</option>
		<option value="MEDIUM">Medium</option>
		<option value="HIGH">High</option>
	</select> 
	<br/><br/>
	<input type="submit" id="createWorkTicket" value="Submit Ticket" />

<div class="cleaner"></div>
