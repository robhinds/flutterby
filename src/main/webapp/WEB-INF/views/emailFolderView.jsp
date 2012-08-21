<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>

<script type="text/JavaScript">	
</script>

<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
	<table id="email_folder" class="table_alternate feed_table">
	<!-- Table header -->  
		<thead>  
			<tr>  
				<th scope="col" class="email_from">From</th>
				<th scope="col" class="email_subj">Subject</th>
				<th scope="col" class="email_date">Date</th>
			</tr>  
		</thead>
		<tbody>
			<j:forEachJson items="${model.emails}" var="pm" counter="counter">
				<tr id='pm_id_<j:jsonGetString key="id" json="${pm}" />' class="${counter % 2 == 0 ? 'even' : 'odd'}">
			  		<td class="email_from"><j:jsonGetString key="createdBy" json="${pm}" /></td>
			  		<td class="email_subj"><j:jsonGetString key="title" json="${pm}" /></td>
			  		<td class="email_date"><j:jsonGetString key="displayDate" json="${pm}" /></td>
				</tr>
			</j:forEachJson>
		</tbody>
	</table>
	
	
	<div class="cleaner"></div>
</div>