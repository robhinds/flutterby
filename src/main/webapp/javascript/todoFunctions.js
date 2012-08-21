/**
 * Javascript object containing all common functions to handle
 * processing Status objects
 */


function createTodo(event){
	var title = $("#todoTitle").val();
	var details = $("#todoDescription").val();
	var url = event.data.url;
	
	$.getJSON(url+'/todo/createTodo.ajax', {title : title, body : details}, function(data) {
		disablePopup("#todoBackground", "#todoPopup");
		$("#todoTitle").val($("#todoTitle").attr('defaultVal'));
	    $("#todoTitle").css({color:'grey'});
	    $("#todoDescription").val($("#todoDescription").attr('defaultVal'));
	    $("#todoDescription").css({color:'grey'});
	    
	    var newRow = "<tr id='" + data.id + "' class='even'><td class='feed_details'><div id='listTodoTitle' class='activityTitle'>" +
	    		"<a href='#'>" + data.title + "</a></div><div id='todoDetails' style='display:none;'><br/>" + data.body + "<br/><br/>" +
	    				"</div><div id='todoDate' class='small_text'>Created " + data.displayDate + "</div></td>" +
	    						
	    				"<td class='feed_actions'>" +
	    				"<a href='#' ><img id='markAsComplete' src=\"" + event.data.url +"/resources/images/notCompleted.png\" checked='false' " +
	    				"alt='Mark To-Do item as complete' title='Mark To-Do item as complete'/></a><a href='#' >" +
	    				"<img id='deleteTodo' src=\"" + event.data.url +"/resources/images/delete.png\" alt='Permanently delete To-Do' " +
	    				"title='Permanently delete To-Do'/></a></td></tr>";
	    
	    $('#todo_list tr:last').after(newRow);
	    
	    $("#freeow").freeow("ToDo Created", "ToDo added to your list..", {
			classes: ["gray", "pushpin"]
		});
	});
}


function updateTodo(todoId, completed, url){
	$.getJSON(url+'/todo/updateTodo.ajax', {id : todoId, completed: completed});
}

function deleteTodo(todoId, url){
	$.getJSON(url+'/todo/deleteTodo.ajax', {id : todoId}, function(){
		$('#'+todoId).remove();
	});
}