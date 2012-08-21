<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<script type="text/JavaScript">
	var url = "${pageContext.request.contextPath}";
	$(document).ready(function(){
		buildLiveStatusFeed(url);
		$("#moreActivities").click(getMoreFeed);
	});

	$('.status_repeat').live('click', function () { 
		repeatStatus($(this).attr('id'));
	});
</script>

<tiles:insertTemplate template="/WEB-INF/views/quickAction.jsp" />

<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
	<table id="live_feed" class="table_alternate feed_table">	
		<thead>  
			<tr>  
				<th>Latest Activity</th>
			</tr>  
		</thead>
		<tbody id="items">
		</tbody>
	</table>
	
	
	<br/><br/>
	<div style="text-align:center;">
		<button id="moreActivities" class="moreFeedBtn">More..</button><br/>
	</div>
	
	<div class="cleaner"></div>
</div>