<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<script type="text/JavaScript">
	var url = "${pageContext.request.contextPath}";
	$("#markAsCorrect").live('click', function() {
		if ($(this).attr("checked") != "true") {
			$(this).attr("checked", "true");
			$(this).attr("src", "<c:url value='/resources/images/correct.png'/>");
			$(this).parent().parent().parent().addClass("strikeThrough");
			updateQuestion($(this).parent().parent().parent().attr('id'), true, url);
		} else {
			$(this).attr("checked", false);
			$(this).attr("src", "<c:url value='/resources/images/notCompleted.png'/>");
			$(this).parent().parent().parent().removeClass("strikeThrough");
			updateQuestion($(this).parent().parent().parent().attr('id'), false, url);
		}
	});

	$(document).ready(function() {
		$("#answer_question").markItUp(mySettings).css('width', '93%');
		$("#postAnswer").bind('click', { url: url }, postAnswer);
		
	});
</script>

<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
	<div class="page_header page_body"><j:jsonGetString key="title" json="${question}" /></div>
	<div class="page_body">
		<div id="questionDetails">
			<j:jsonGetString key="body" json="${question}" />
			<br/><br/>
		</div>
		<br/>
		<div id="questionTags">
			<j:forEachJson items="${tags}" var="tag" counter="counter">
				<span class="tagHighlight"><j:jsonGetString key="title" json="${tag}" /></span>  
			</j:forEachJson>
		</div>
		<div id="postedDate" class="small_text">
			Asked by <j:jsonGetString key="createdBy" json="${question}" /> <j:jsonGetString key="displayDate" json="${question}" />
		</div>
	</div>
	
	<br/><br/><br/><br/>
	<div class="page_header page_body">${numAnswers} Answer(s)</div>
	<div class="page_body feed_table">
	<table id="answer_list" class="table_alternate feed_table">
		<tbody>
			<j:forEachJson items="${answers}" var="answer" counter="counter">
				<j:jsonGetVariable key="isCorrect" json="${answer}" var="isCorrect" />
				<tr id='<j:jsonGetString key="id" json="${answer}"/>' class="even">
				
			  		<td class="answer_actions">
			  			<c:if test="${isCorrect=='true'}">
			  				<a href="#" ><img id="markAsCorrect" src="<c:url value='/resources/images/correct.png'/>" 
			  					checked="true" alt="Mark as incorrect" title="Mark as not correct"/></a>
			  			</c:if>
			  			<c:if test="${isCorrect!='true'}">
			  				<a href="#" ><img id="markAsCorrect" src="<c:url value='/resources/images/notCompleted.png'/>" 
			  					checked="false" alt="Mark as correct" title="Mark as corrects"/></a>
			  			</c:if>
			  		</td>			
				
			  		<td class="feed_details">
			  			<div id="answerDetails" class="autoWidth">
			  				<j:jsonGetString key="body" json="${answer}" />
			  				<br/><br/>
			  			</div>
			  			<div id="todoDate" class="small_text">
			  			Answered by <j:jsonGetString key="createdBy" json="${answer}" /> <j:jsonGetString key="displayDate" json="${answer}" />
			  			</div>
			  		</td>
				</tr>
			</j:forEachJson>
		</tbody>
	</table>
	</div>
	<br/><br/><br/>
	
	<h2>Answer this Question</h2>
	<div>
		<textarea id="answer_question" questionId="<j:jsonGetString key="id" json="${question}" />"></textarea>
		<input type="submit" id="postAnswer" value="Submit Answer" />
	</div>
	<div class="cleaner"></div>
</div>