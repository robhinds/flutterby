<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
	<table id="live_feed" class="table_alternate feed_table">	
		<thead>  
			<tr>  
				<th>Team Members</th>
			</tr>  
		</thead>
		<tbody>
			<tr>
			<j:forEachJson items="${members}" var="member" counter="counter">
				
				<td class="person" id="<j:jsonGetString key="id" json="${member}" />" style="width: 10%;">
					<a href="<c:url value='/user/'/><j:jsonGetString key="name" json="${member}" />">
						<img src="<c:url value='/resources/images/userProfile.png'/>" alt="image" /><br />
			  			<j:jsonGetString key="name" json="${member}" /><br/>
			  			"<j:jsonGetString key="status" json="${member}" />"<br/>
			  		</a>
			  	</td>
				
				<c:if test="${(counter+1) % 3 == 0}">
					</tr><tr>
				</c:if>
				
			</j:forEachJson>
			</tr>
		</tbody>
	</table>
	
	
	<div class="cleaner"></div>
</div>
