<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>

<script type="text/JavaScript">
	$(document).ready(function(){
		$("#org").jOrgChart({
			chartElement : '#chart'
		});
	});
</script>

	<ul id="org" style="display:none">
		<c:set var="team" value="${team}" scope="request"/>
		<jsp:include page="orgChart/node.jsp"/>
	</ul>
	
	<div id="chart" class="orgChart"></div>
	
	<div class="cleaner"></div>