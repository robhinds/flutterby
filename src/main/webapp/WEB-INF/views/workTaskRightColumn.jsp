<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<!--  insert search box here  -->
<tags:searchBox searchContext="worktask"></tags:searchBox>

<!-- insert connections here  -->
<div id="questionTagDiv" class = "rounded_menu txt_lightgrey">
	<H3>Latest Work Tasks</H3>
	<div id="latestTasks">
		<j:forEachJson items="${latestTasks}" var="wt" counter="counter">
			<j:jsonGetVariable key="id" json="${wt}" var="id" />
			<a href="<c:url value='/question/detail/${id}'/>"><j:jsonGetString key="title" json="${wt}" /></a><br/>  
		</j:forEachJson>
	</div>
	<br/><br/>
</div>