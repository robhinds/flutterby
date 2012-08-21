<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<script type="text/JavaScript">
	var url = "${pageContext.request.contextPath}";
	$('#viewComments').live('click',function(){
		if ($(this).next().is(":visible")){
			$(this).next().hide("fast");
		}
		else {
			$(this).next().show("fast");
		}
	});

	$(document).ready(function(){
		$('#updateTicket').bind("click", { url: url}, updateWorkTicket);
	});
</script>


<j:jsonGetVariable key="assignedTo" json="${task}" var="assignedTo" />
<j:jsonGetVariable key="status" json="${task}" var="status" />
<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
	<div class="page_header page_body"><j:jsonGetString key="ticketNumber" json="${task}" /> : <j:jsonGetString key="title" json="${task}" /></div>
	<div class="page_body">
		<div id="taskDetails">
			<j:jsonGetString key="body" json="${task}" />
			<br/><br/>
		</div>
		<br/>
		<br/><br/>
		<div id="postedDate" class="small_text">
			Raised by <j:jsonGetString key="createdBy" json="${task}" /> <j:jsonGetString key="displayDate" json="${task}" />
		</div>
		<br/>
		
		Status:
		<select name="state" id="state" size="1" class="autoWidth">
			<j:jsonGetVariable key="state" json="${task}" var="state" />
			<option></option>
	  		<option value="ASSIGNED" ${state == 'ASSIGNED' ? 'selected="selected"' : ''}>Assigned</option>
	  		<option value="ONHOLD" ${state == 'ONHOLD' ? 'selected="selected"' : ''}>On Hold</option>
			<option value="CLOSED" ${state == 'CLOSED' ? 'selected="selected"' : ''}>Closed</option>
		</select>
		
		Priority:
		<select name="priority" id="priorityId" size="1" class="autoWidth">
			<j:jsonGetVariable key="priority" json="${task}" var="priority" />
			<option></option>
	  		<option value="LOW" ${priority == 'LOW' ? 'selected="selected"' : ''}>Low</option>
	  		<option value="MEDIUM" ${priority == 'MEDIUM' ? 'selected="selected"' : ''}>Medium</option>
			<option value="HIGH" ${priority == 'HIGH' ? 'selected="selected"' : ''}>High</option>
		</select>
		
		Assigned To:
		<select name="ticketAssignedTo" id="ticketAssignedToId" size="1" class="autoWidth">
	  		<option></option>
			<optgroup label="Teams">
				<j:forEachJson items="${contactsTeams}" var="contact">
					<j:jsonGetVariable key="name" json="${contact}" var="userName" />
					<c:if test="${assignedTo==userName}">
						<option value="<j:jsonGetString key="id" json="${contact}" />" selected="selected" >
							<j:jsonGetString key="name" json="${contact}" />
						</option>
					</c:if>
					<c:if test="${assignedTo!=userName}">
						<option value="<j:jsonGetString key="id" json="${contact}" />">
							<j:jsonGetString key="name" json="${contact}" />
						</option>
					</c:if>
				</j:forEachJson>
			</optgroup>
			<optgroup label="People">
				<j:forEachJson items="${contactsPersons}" var="contact">
					<j:jsonGetVariable key="name" json="${contact}" var="userName" />
					<c:if test="${assignedTo==userName}">
						<option value="<j:jsonGetString key="id" json="${contact}" />" selected="selected" >
							<j:jsonGetString key="name" json="${contact}" />
						</option>
					</c:if>
					<c:if test="${assignedTo!=userName}">
						<option value="<j:jsonGetString key="id" json="${contact}" />">
							<j:jsonGetString key="name" json="${contact}" />
						</option>
					</c:if>
				</j:forEachJson>
			</optgroup>
		</select> 
		
		<br/><br/>
		<textarea id="newTaskUpdate" rows="5" cols="1" defaultVal="Add Comment.." class="clearText ninetyNinePercent"  taskId="<j:jsonGetString key="id" json="${task}" />"></textarea>
		<input type="submit" id="updateTicket" value="Update Ticket" />
		
		<br/><br/><br/><br/>
		<div id="viewComments" style="cursor:pointer;"><b>View All Comments(<j:jsonGetString key="numUpdates" json="${task}" />):</b></div>
		<div style="display:none;">
			<hr/>
			<j:jsonGetArray key="updates" json="${task}" var="updates"/><br/>
			<j:forEachJson items="${updates}" var="update">
				<j:jsonGetString key="body" json="${update}" />
				<br/><br/>
				<div id="updateDate" class="small_text">
					Update posted by <j:jsonGetString key="createdBy" json="${update}" /> <j:jsonGetString key="displayDate" json="${update}" />
				</div>
				<hr/>
			</j:forEachJson>
		</div>
		
	</div>
	
	<div class="cleaner"></div>
</div>