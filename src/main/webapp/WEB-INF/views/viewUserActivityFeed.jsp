<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<script type="text/JavaScript">
	var url = "${pageContext.request.contextPath}";
	$(document).ready(function(){
		buildPublicUserStatusFeed(url, "${userName}");
	});

</script>
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
	
	
	<div class="cleaner"></div>
</div>