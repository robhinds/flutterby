<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<!--  insert search box here  -->
<tags:searchBox searchContext="question"></tags:searchBox>

<!-- insert connections here  -->
<div id="questionTagDiv" class = "rounded_menu txt_lightgrey">
	<H3>Similar Questions</H3>
	<div id="similarQuestions">
		<j:forEachJson items="${similar}" var="sQ" counter="counter">
			<j:jsonGetVariable key="id" json="${sQ}" var="id" />
			<a href="<c:url value='/question/detail/${id}'/>"><j:jsonGetString key="title" json="${sQ}" /></a><br/>  
		</j:forEachJson>
	</div>
	<br/><br/>
</div>