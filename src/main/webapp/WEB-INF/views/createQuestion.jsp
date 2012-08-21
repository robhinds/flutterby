<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script type="text/JavaScript">

	var url = "${pageContext.request.contextPath}";
	$(document).ready(function(){
		$('#createQuestion').bind("click", { url: url}, createQuestion);
	});
</script>


<div class="content_header_text">
</div>
	<input id="questionTitle" type="text" defaultVal="Question.." class="autoWidth clearText">
	<textarea id="questionDescription" rows="9" cols="2" defaultVal="Description.." class="clearText"></textarea>
	Assign To:
	<tags:contactDropDown identifier="question" ></tags:contactDropDown>
	Enter tags (comma seperated):
	<input id="tags" type="text" class="autoWidth">
	<br/><br/>
	<input type="submit" id="createQuestion" value="Ask Question" />

<div class="cleaner"></div>
