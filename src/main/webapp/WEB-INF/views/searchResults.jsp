<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>

		
<div class="rounded_menu txt_lightgrey">
	<div id="searchResults" class="column_menu feed_table">
		<table id="asked_questions_list" class="table_alternate autoWidth">
			<thead>
				<tr>
					<th scope="row">Search Results</th>
				</tr>
			</thead>
			<tbody>
				<j:forEachJson items="${results}" var="result" counter="counter">
					<tr id='<j:jsonGetString key="id" json="${result}"/>' 
						class="${counter % 2 == 0 ? 'even' : 'odd'}">
		
				  		<td class="feed_details">
				  			<div id="resultTitle"  class="activityTitle">
				  				<span class="activityHighlight <j:jsonGetString key="styleClass" json="${result}" />"><j:jsonGetString key="context" json="${result}" /></span>"
				  				<a href="<c:url value='/'/><j:jsonGetString key="context" json="${result}" />/detail/<j:jsonGetString key="id" json="${result}" />" title='<j:jsonGetString key="tooltip" json="${result}" />'>
				  					<j:jsonGetString key="title" json="${result}" />
				  				</a>
				  			</div>
				  		</td>
					</tr>
				</j:forEachJson>
			</tbody>
		</table>
		<br/><br/>
	</div>
</div>