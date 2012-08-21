<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>

<li>
<j:jsonGetString key="teamName" json="${team}" />
<ul>
	<j:jsonGetArray key="members" json="${team}" var="teamMembers" />
	<j:forEachJson items="${teamMembers}" var="member" counter="counter">
		<li>
			<img src="<c:url value='/resources/images/anonPhoto.png'/>" /><br/>
			<a href="<c:url value='/user/'/><j:jsonGetString key="name" json="${member}" />" class="orgChart"> 
				<j:jsonGetString key="name" json="${member}" />
			</a>
		</li>
	</j:forEachJson>
	
	<j:jsonGetArray key="subTeams" json="${team}" var="subTeams" />
	<j:forEachJson items="${subTeams}" var="subTeam" counter="counter">
		<c:set var="team" value="${subTeam}" scope="request"/>
		<jsp:include page="node.jsp"/>
	</j:forEachJson>
	
</ul> 
</li>