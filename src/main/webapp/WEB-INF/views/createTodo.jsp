<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<script type="text/JavaScript">

	var url = "${pageContext.request.contextPath}";

	$(document).ready(function(){
		$('#createTodo').bind("click", { url: url}, createTodo);
	});

	$(function() {
		$( "#deadlineDate" ).datepicker();
	});
</script>


<div class="content_header_text">
</div>
	<input id="todoTitle" type="text" defaultVal="Title.." class="autoWidth clearText">
	<textarea id="todoDescription" rows="14" cols="2" defaultVal="Description.." class="clearText"></textarea>
	<input id="deadlineDate" type="text" defaultVal="Deadline.." class="autoWidth clearText"><br/> 
	<input type="submit" id="createTodo" value="Create ToDo" />

<div class="cleaner"></div>
