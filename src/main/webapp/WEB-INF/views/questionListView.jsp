<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>

<script type="text/JavaScript">
$(document).ready(function() { 
  $('#tabMenu li').click(function(){
    if (!$(this).hasClass('selected')) { 
        $('#tabMenu li').removeClass('selected');
        $(this).addClass('selected');
        $('.boxBody div.parent').slideUp('1500');
        $('.boxBody div.parent:eq(' + $('#tabMenu > li').index(this) + ')').slideDown('1500');
     }
  }).mouseover(function() {   
    $(this).addClass('mouseover');
    $(this).removeClass('mouseout');  
  }).mouseout(function() {
    $(this).addClass('mouseout');
    $(this).removeClass('mouseover');        
  });

  $('.boxBody #category li').mouseover(function() {
    $(this).css('backgroundColor','#888');
    $(this).children().animate({paddingLeft:"20px"}, {queue:false, duration:300});
  }).mouseout(function() {
    $(this).css('backgroundColor','');
    $(this).children().animate({paddingLeft:"0"}, {queue:false, duration:300});
  }); 
     
  $('.boxBody li').click(function(){
    window.location = $(this).find("a").attr("href");
  }).mouseover(function() {
    $(this).css('backgroundColor','#888');
  }).mouseout(function() {
    $(this).css('backgroundColor','');
  });  
     
});

</script>
<div id="quickActionDiv">
	<div class="box">
		
		<ul id="tabMenu">
			<li class="questionsAskedToYou selected"></li>
			<li class="questionsAskedByYou"></li>
		</ul>
		<br /><br />
		
		<div class="boxBody rounded_menu txt_lightgrey">
		
			<div id="askedByYou" class="show parent">
				<div id="question_tabs" class="column_menu feed_table">
					<table id="asked_questions_list" class="table_alternate autoWidth">
						<tbody>
							<j:forEachJson items="${questionsAssigned}" var="questionItem" counter="counter">
								<j:jsonGetVariable key="isCorrect" json="${questionItem}" var="isCorrect" />
					
								<tr id='<j:jsonGetString key="id" json="${questionItem}"/>' 
									class="${counter % 2 == 0 ? 'even' : 'odd'}">
					
							  		<td class="feed_details">
					
							  			<div id="questionTitle"  class="activityTitle">
							  				<a href="<c:url value='/question/detail/'/><j:jsonGetString key="id" json="${questionItem}" />" title='<j:jsonGetString key="tooltip" json="${questionItem}" />'>
							  					<j:jsonGetString key="title" json="${questionItem}" />
							  				</a>
							  			</div>
					
							  			<div id="questionAskedDate" class="small_text">
							  			Asked <j:jsonGetString key="displayDate" json="${questionItem}" /> by <j:jsonGetString key="createdBy" json="${questionItem}" />
							  			</div>
							  			
							  		</td>
							  		
							  		<td class="feed_actions">
							  			<c:if test="${isCorrect=='true'}">
							  				<img src="<c:url value='/resources/images/correct.png'/>" alt="Correct Answer Provided" title="Correct Answer Provided"/>
							  			</c:if>
							  			<c:if test="${isCorrect!='true'}">
							  				<img src="<c:url value='/resources/images/noAnswer.png'/>" alt="Correct Answer Not Provided" title="Correct Answer Not Provided"/>
							  			</c:if>
							  			<br/>
							  			<div id="questionInfo" class="small_text">
							  				<j:jsonGetString key="numAnswers" json="${questionItem}" /> answers
							  			</div>
							  		</td>
								</tr>
							</j:forEachJson>
						</tbody>
					</table>
					<br/><br/>
				</div>
			</div>
			
			<div id="askedToYou" class="parent">
				<div id="question_tabs" class="column_menu feed_table">
					<table id="assigned_questions_list" class="table_alternate autoWidth">
						<tbody>
							<j:forEachJson items="${questionsAsked}" var="questionItem" counter="counter">
								<j:jsonGetVariable key="isCorrect" json="${questionItem}" var="isCorrect" />
					
								<tr id='<j:jsonGetString key="id" json="${questionItem}"/>' 
									class="${counter % 2 == 0 ? 'even' : 'odd'}">
					
							  		<td class="feed_details">
					
							  			<div id="questionTitle"  class="activityTitle">
							  				<a href="<c:url value='/question/detail/'/><j:jsonGetString key="id" json="${questionItem}" />" title='<j:jsonGetString key="tooltip" json="${questionItem}" />'>
							  					<j:jsonGetString key="title" json="${questionItem}" />
							  				</a>
							  			</div>
					
							  			<div id="questionAskedDate" class="small_text">
							  			Asked <j:jsonGetString key="displayDate" json="${questionItem}" /> by <j:jsonGetString key="createdBy" json="${questionItem}" />
							  			</div>
							  			
							  		</td>
							  		
							  		<td class="feed_actions">
							  			<c:if test="${isCorrect=='true'}">
							  				<img src="<c:url value='/resources/images/correct.png'/>" alt="Correct Answer Provided" title="Correct Answer Provided"/>
							  			</c:if>
							  			<c:if test="${isCorrect!='true'}">
							  				<img src="<c:url value='/resources/images/noAnswer.png'/>" alt="Correct Answer Not Provided" title="Correct Answer Not Provided"/>
							  			</c:if>
							  			<br/>
							  			<div id="questionInfo" class="small_text">
							  				<j:jsonGetString key="numAnswers" json="${questionItem}" /> answers
							  			</div>
							  		</td>
								</tr>
							</j:forEachJson>
						</tbody>
					</table>
					<br/><br/>
				</div>
			</div>
		</div>
	</div>
</div>