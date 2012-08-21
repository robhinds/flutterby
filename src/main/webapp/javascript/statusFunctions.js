/**
 * Javascript object containing all common functions to handle
 * processing Status objects
 */

var globalFeedLimit=20;
var pageNumber=0;
var counter=0;

function moreFeed(){
	globalFeedLimit = globalFeedLimit +20;
}

function updateStatus(){
	var updates = $("#status").val();
	$.getJSON('status/updateStatus.ajax', {status : updates}, function(data) {
		$("#status_text").html("\"" + data.status + "\"");
		$("#status").val('');
		$( "#user_menu_action" ).accordion("activate", 0);
	});
}

function repeatStatus(statusId){
	$.getJSON('status/repeatStatus.ajax', {status : statusId}, function(data) {
		$("#status_text").html("\"" + data.status + "\"");
		$( "#user_menu_action" ).accordion("activate", 0);
		$("#freeow").freeow("Status Re-Shared", "Message shared with you connections", {
			classes: ["gray", "pushpin"]
		});
	});
}


function searchStatus(){
	var keyword = $("#search").val();
	$.getJSON('status/searchStatus.ajax', {status : keyword}, function(data) {
		//console.log(data.results);
	});
}


function buildLiveStatusFeed(url){
	var latestUpdateId;	
	getLiveStatusFeed();	//load initial data
	var refreshId = setInterval(getLiveStatusFeed, 30000);	//interval set at 30 seconds
	function getLiveStatusFeed(){
		$.getJSON(url+'/activity/latestActivity.ajax', {latestId : latestUpdateId}, function(data) {
			var updates = $.parseJSON(data.statuses);
			if (!jQuery.isEmptyObject(updates)){
				latestUpdateId = updates.latestId;
				$.each(updates.statuses, function() {
					if (counter>globalFeedLimit-1) removeItem();
					var rowClass = counter % 2 == 1 ? '' : 'odd';
					var newRow = createRow(url, counter, rowClass, this);
					$('#items').prepend(newRow);
	     		    $('#item' + counter).fadeIn();
	      		    counter += 1;
				});
			}

			//we want to limit the list to a certain length, so remove items as they get too long
			function removeItem() {
				$('#item' + (counter-(globalFeedLimit))).hide();
			}					
		});
	}
}


function getMoreFeed(){
	moreFeed();
	pageNumber = pageNumber+1;
	$.getJSON(url+'/activity/latestActivity.ajax', {pageNum:pageNumber}, function(data) {
		var updates = $.parseJSON(data.statuses);
		if (!jQuery.isEmptyObject(updates)){
			latestUpdateId = updates.latestId;
			$.each(updates.statuses, function() {
				var rowClass = counter % 2 == 0 ? '' : 'odd';
				var newRow = createRow(url, counter, rowClass, this);
				$('#items').append(newRow);
				
     		    $('#item' + counter).fadeIn();
      		    counter += 1;
			});
		}					
	});
}

	
function createRow(url, counter, rowClass, objectProcessed, hideButtons){
	var activityDescripton = objectProcessed.createdBy;
	var activityType = '';
	var btnDef = "";
	if (objectProcessed.objectType == "STATUS"){
		activityType = "<span class=\"activityHighlight activityStatus\">Status</span>";
		if(objectProcessed.isOwner){
			activityDescripton = "you posted this update "+ objectProcessed.displayDate;
		}else{
			activityDescripton = "status posted by <a href=\"" + url + "/user/" + activityDescripton + "\">" + activityDescripton + "</a> "+ objectProcessed.displayDate;
		}
		
		btnDef = "<a href=\"#\"><img class=\"status_repeat\" src=\"" + url + "/resources/images/refresh.png\" alt=\"resend\" id=\"" + objectProcessed.id + "\" title=\"Repeat Status\" /></a>";
	}
	else if (objectProcessed.objectType == "TODO"){
		activityType = "<span class=\"activityHighlight activityTodo\">To-Do</span>";
		activityDescripton = "You created this To-Do " + objectProcessed.displayDate;
//		btnDef = "<a href=\"#\" ><img id=\"deleteTodo\" src=\"" + url + "/resources/images/deleteSmall.png\""+ 
//				"alt=\"Permanently delete To-Do\" title=\"Permanently delete To-Do\"/></a>";
//		
//		if (objectProcessed.completed){
//			btnDef = btnDef + "<a href=\"#\" ><img id=\"markAsComplete\" src=\"" + url + "/resources/images/correctSmall.png\" checked=\"true\" alt=\"Mark To-Do item as not complete\" title=\"Mark To-Do item as not complete\"/></a>";
//		}
//		else
//		{
//			btnDef = btnDef + "<a href=\"#\" ><img id=\"markAsComplete\" src=\"" + url + "/resources/images/notCompletedSmall.png\" checked=\"false\" alt=\"Mark To-Do item as complete\" title=\"Mark To-Do item as complete\"/></a>";
//		}
		btnDef = btnDef+"<a href=\"" + url + "/todo/detail/" + objectProcessed.id + "\"><img src=\"" + url + "/resources/images/view.png\" alt=\"view\" title=\"View Ticket\" /></a>";
	}
	else if (objectProcessed.objectType == "QUESTION"){
		activityType = "<span class=\"activityHighlight activityQuestion\">Question</span>";
		if(objectProcessed.isOwner){
			activityDescripton = "You asked this question " + objectProcessed.displayDate;
		}else{
			activityDescripton = "question asked by <a href=\"" + url + "/user/" + activityDescripton + "\">" + activityDescripton + "</a> "+ objectProcessed.displayDate;
		}
		btnDef = //"<a href=\"#\"><img src=\"" + url + "/resources/images/reply.png\" alt=\"reply\" title=\"Reply to Status\" /></a>"+
				"<a href=\"" + url + "/question/detail/" + objectProcessed.id + "\"><img src=\"" + url + "/resources/images/view.png\" alt=\"view\" title=\"View Ticket\" /></a>";
	}
	else if (objectProcessed.objectType == "ANSWER"){
		activityType = "<span class=\"activityHighlight activityAnswer\">Answer</span>";
		if(objectProcessed.isOwner){
			activityDescripton = "You answered this question " + objectProcessed.displayDate;
		}else{
			activityDescripton = activityDescripton + " answered the question \"" + objectProcessed.questionTitle + "\" " + objectProcessed.displayDate;
		}
		btnDef = "<a href=\"" + url + "/question/detail/" + objectProcessed.questionId + "\"><img src=\"" + url + "/resources/images/view.png\" alt=\"view\" title=\"View Ticket\" /></a>";
	}
	else if (objectProcessed.objectType == "PRIVATEMESSAGE"){
		activityType = "<span class=\"activityHighlight activityMessage\">Message</span>";
		if(objectProcessed.isOwner){
			activityDescripton = "You sent an private message " + objectProcessed.displayDate;
		}else{
			activityDescripton = "message recieved from <a href=\"" + url + "/user/" + activityDescripton + "\">" + activityDescripton + "</a> "+ objectProcessed.displayDate;
		}
		btnDef = "<a href=\"#\"><img src=\"" + url + "/resources/images/reply.png\" alt=\"reply\" title=\"Reply to Message\" /></a>";
	}
	else if (objectProcessed.objectType == "TRACKER"){
		activityType = "<span class=\"activityHighlight activityTracker\">Work Ticket</span>";
		if(objectProcessed.isOwner){
			activityDescripton = "You raised this Work Ticket " + objectProcessed.displayDate;
		}else{
			activityDescripton = "Work ticket raised by <a href=\"" + url + "/user/" + activityDescripton + "\">" + activityDescripton + "</a> "+ objectProcessed.displayDate;
		}
		btnDef = "<a href=\"" + url + "/worktask/detail/" + objectProcessed.id + "\"><img src=\"" + url + "/resources/images/view.png\" alt=\"view\" title=\"View Ticket\" /></a>";
	}
	else if (objectProcessed.objectType == "TICKETUPDATE"){
		activityType = "<span class=\"activityHighlight activityTracker\">Ticket Update</span>";
		if(objectProcessed.isOwner){
			activityDescripton = "You updated this Work Ticket " + objectProcessed.displayDate;
		}else{
			activityDescripton = "Work ticket updated by <a href=\"" + url + "/user/" + activityDescripton + "\">" + activityDescripton + "</a> "+ objectProcessed.displayDate;
		}
		btnDef = "<a href=\"" + url + "/worktask/detail/" + objectProcessed.trackerId + "\"><img src=\"" + url + "/resources/images/view.png\" alt=\"view\" title=\"View Original Ticket\" /></a>";
	}
	
	if(hideButtons){
		btnDef = "";
	}
	var newRow = "<tr objectId='" + objectProcessed.id + "' id='item" + counter + "' class=\"" + rowClass + "\" >"
	+ "<td class=\"feed_details\">"
	+ activityType
	+ objectProcessed.title
	+ "<br/>"
	+ "<span class=\"small_text\">"
	+ activityDescripton
	+ "</span>"
	+ "</td>"
	+ "<td class=\"feed_actions\">"
	+ btnDef
		+ "</td></tr>";
	
	return newRow;
}



function buildPublicUserStatusFeed(url, username){
	var latestUpdateId;	
	getLiveStatusFeed();	//load initial data
	function getLiveStatusFeed(){
		$.getJSON(url+'/activity/latestUserActivity.ajax', {latestId : latestUpdateId, userName:username}, function(data) {
			var updates = $.parseJSON(data.statuses);
			if (!jQuery.isEmptyObject(updates)){
				latestUpdateId = updates.latestId;
				$.each(updates.statuses, function() {
					if (counter>globalFeedLimit-1) removeItem();
					var rowClass = counter % 2 == 1 ? '' : 'odd';
					var newRow = createRow(url, counter, rowClass, this, true);
					$('#items').prepend(newRow);
	     		    $('#item' + counter).fadeIn();
	      		    counter += 1;
				});
			}

			//we want to limit the list to a certain length, so remove items as they get too long
			function removeItem() {
				$('#item' + (counter-(globalFeedLimit))).hide();
			}					
		});
	}
}