/**
 * Javascript object containing all common functions to handle
 * processing WorkTicket objects
 */


function createWorkTicket(event){
	var title = $("#workTicketTitle").val();
	var details = $("#workTicketDescription").val();
	var recipientId = $("#ticketContactId").val();
	var priority = $("#priority").val();
	var url = event.data.url;
	
	$.getJSON(url+'/worktask/createWorkTask.ajax', {title : title, body : details, recipientId : recipientId, priority : priority}, function(data) {
		disablePopup("#workTicketBackground", "#workTicketPopup");
		$("#workTicketTitle").val($("#workTicketTitle").attr('defaultVal'));
	    $("#workTicketTitle").css({color:'grey'});
	    $("#workTicketDescription").val($("#workTicketDescription").attr('defaultVal'));
	    $("#workTicketDescription").css({color:'grey'});
	    
	    $("#freeow").freeow("Work Ticket Created", "Work Ticket Created..", {
			classes: ["gray", "pushpin"]
		});
	});
}

function updateWorkTicket(event){
	var state = $("#state").val();
	var recipientId = $("#ticketAssignedToId").val();
	var priority = $("#priorityId").val();
	var comment = $("#newTaskUpdate").val();
	var url = event.data.url;
	var taskId = $("#newTaskUpdate").attr("taskId");
	
	$.getJSON(url+'/worktask/updateWorkTask.ajax', {comment:comment, recipientId:recipientId, priority:priority, state:state, taskId:taskId}, function(data) {
		window.location.reload();
	});
}