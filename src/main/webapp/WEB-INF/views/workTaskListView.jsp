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
			<li class="ticketsAssignedToYou selected"></li>
			<li class="ticketsRaisedByYou"></li>
		</ul>
		<br /><br />
		
		<div class="boxBody rounded_menu txt_lightgrey">
		
			<div id="assignedToYou" class="show parent">
				<div id="tasks_tab" class="column_menu feed_table">
					<table id="raised_ticket_list" class="table_alternate autoWidth">
						<tbody>
							<j:forEachJson items="${tasksAssigned}" var="task" counter="counter">
								<tr id='<j:jsonGetString key="id" json="${task}"/>' class="${counter % 2 == 0 ? 'even' : 'odd'}">
							  		<td class="feed_details">
					
							  			<div id="taskTitle"  class="activityTitle">
							  				<a href="<c:url value='/worktask/detail/'/><j:jsonGetString key="id" json="${task}" />" title='<j:jsonGetString key="tooltip" json="${task}" />'>
							  					<j:jsonGetString key="ticketNumber" json="${task}" /> : <j:jsonGetString key="title" json="${task}" />
							  				</a>
							  			</div>
					
							  			<div id="taskRaisedDate" class="small_text">
							  			Raised <j:jsonGetString key="displayDate" json="${task}" /> by <j:jsonGetString key="createdBy" json="${task}" />
							  			</div>
							  			
							  		</td>
							  		<td class="feed_actions">
							  		</td>
								</tr>
							</j:forEachJson>
						</tbody>
					</table>
					<br/><br/>
				</div>
			</div>
			
			<div id="raisedByYou" class="parent">
				<div id="task_tabs" class="column_menu feed_table">
					<table id="assigned_task_list" class="table_alternate autoWidth">
						<tbody>
							<j:forEachJson items="${tasksRaised}" var="task" counter="counter">
								<tr id='<j:jsonGetString key="id" json="${task}"/>' class="${counter % 2 == 0 ? 'even' : 'odd'}">
							  		<td class="feed_details">
							  			
							  			<div id="taskTitle"  class="activityTitle">
							  				<a href="<c:url value='/worktask/detail/'/><j:jsonGetString key="id" json="${task}" />" title='<j:jsonGetString key="tooltip" json="${task}" />'>
							  					<j:jsonGetString key="title" json="${task}" />
							  				</a>
							  			</div>
					
							  			<div id="taskRaisedDate" class="small_text">
							  			Raised <j:jsonGetString key="displayDate" json="${task}" /> by <j:jsonGetString key="createdBy" json="${task}" />
							  			</div>
							  			
							  		</td>
							  		
							  		<td class="feed_actions">
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