<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>

<script type="text/JavaScript">
$('#listTodoTitle').live('click',function(){
	if ($(this).next().is(":visible")){
		$(this).next().hide("fast");
	}
	else {
		$(this).next().show("fast");
	}
});


$("#deleteTodo").live('click', function() {
	deleteTodo($(this).parent().parent().parent().attr('objectId'), "${pageContext.request.contextPath}");
});

$("#markAsComplete").live('click', function() {
	if ($(this).attr("checked") != "true") {
		$(this).attr("checked", "true");
		$(this).attr("src", "<c:url value='/resources/images/correct.png'/>");
		$(this).parent().parent().parent().addClass("strikeThrough");
		updateTodo($(this).parent().parent().parent().attr('objectId'), true, "${pageContext.request.contextPath}");
	} else {
		$(this).attr("checked", false);
		$(this).attr("src", "<c:url value='/resources/images/notCompleted.png'/>");
		$(this).parent().parent().parent().removeClass("strikeThrough");
		updateTodo($(this).parent().parent().parent().attr('objectId'), false, "${pageContext.request.contextPath}");
	}
});
</script>
<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
	<table id="todo_list" class="table_alternate feed_table">
		<thead>
			<tr>
				<th scope="row">Your To-Do List</th>
			</tr>
		</thead>
		<tbody>
			<j:forEachJson items="${todos}" var="todoItem" counter="counter">
				<j:jsonGetVariable key="completed" json="${todoItem}" var="completed" />
				<j:jsonGetVariable key="display" json="${todoItem}" var="display" />
	
				<tr objectId='<j:jsonGetString key="id" json="${todoItem}"/>'
					id='<j:jsonGetString key="id" json="${todoItem}"/>'  
					class="${counter % 2 == 0 ? 'even' : 'odd'} ${completed == 'true' ? 'strikeThrough' : ''}">
			  		
			  		<td class="feed_details">
			  			
			  			<div id="listTodoTitle" class="activityTitle">
			  				<a href="#"><j:jsonGetString key="title" json="${todoItem}" /></a>
			  			</div>
			  			
			  			<div id="todoDetails" ${display == 'true' ? '' : 'style="display:none;"'}>
			  				<br/>
			  				<j:jsonGetString key="body" json="${todoItem}" />
			  				<br/><br/>
			  			</div>
			  			
			  			<div id="todoDate" class="small_text">
			  			Created <j:jsonGetString key="displayDate" json="${todoItem}" />
			  			</div>
			  			
			  		</td>
			  		
			  		<td class="feed_actions">
			  			<c:if test="${completed=='true'}">
			  				<a href="#" ><img id="markAsComplete" src="<c:url value='/resources/images/correct.png'/>" 
			  					checked="true" alt="Mark To-Do item as not complete" title="Mark To-Do item as not complete"/></a>
			  			</c:if>
			  			<c:if test="${completed!='true'}">
			  				<a href="#" ><img id="markAsComplete" src="<c:url value='/resources/images/notCompleted.png'/>" 
			  					checked="false" alt="Mark To-Do item as complete" title="Mark To-Do item as complete"/></a>
			  			</c:if>
			  			
			  			<a href="#" >
			  				<img id="deleteTodo" src="<c:url value='/resources/images/delete.png'/>" 
			  					alt="Permanently delete To-Do" title="Permanently delete To-Do"/>
			  			</a>
			  			
			  		</td>
				</tr>
			</j:forEachJson>
		</tbody>
	</table>
	
	<div class="cleaner"></div>
</div>