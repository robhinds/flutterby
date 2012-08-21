<%@ tag language="java" pageEncoding="ISO-8859-1" description="prototype dropdown. Requires ul / li style but that makes sense here anyway."%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name='dropdownId' required="true" %>
<%@ attribute name='title' required="true" description='title of the link. Pass as image if necessary' %>
<%@ attribute name='imageSource'  description='use if image desired in the title' %>
<%@ attribute name='altImageSource'  description='use if alternate image desired in the title' %>
<%@ attribute name='alt' description='use as alternate title' %>
<%@ attribute name='loadFunction' required="true" description='js ajax load function' %>

<c:if test="${!empty imageSource}">
	<c:url  var="urlVar" value='${imageSource}' />
	<c:url  var="altUrlVar" value='${altImageSource}' />
	<c:set var="title" value="<img id='${dropdownId}MenuIcon' src='${urlVar}' alt='${alt}' title='${title}'/>"/>
</c:if>

	<div class="dropdown">
	
		<a id="${dropdownId}" class='dropdownHref' href="<c:url value='#'/>"> ${title} </a>
		<ul id="ul${dropdownId}" class='dropdownUl' >
			<jsp:doBody/>
		</ul>
	</div>				




<script type="text/javascript">
	var url = "${pageContext.request.contextPath}";
	$(document).ready(function(){
		checkForNotifications("${dropdownId}", url, "${loadFunction}", "${altUrlVar}");
	});

	$('.dropdownUl').hide(); 

	$("#${dropdownId}").click(function() {
		// Change the behaviour of onclick states for links within the menu.
		var toggleId = "#ul${dropdownId}";
		// Hides all other menus depending on JQuery id assigned to them
		$(".dropdownUl").not(toggleId).hide();
		//Only toggles the menu we want since the menu could be showing and we want to hide it.
		$(toggleId).toggle();
		//Change the css class on the menu header to show the selected class.
		if ($(toggleId).css("display") == "none") {
			$(this).removeClass("selected");
		} else {
			$(this).addClass("selected");
		}
	});


	$(document).bind('click', function(e) {

		// Lets hide the menu when the page is clicked anywhere but the menu.
		var $clicked = $(e.target);
		if (!($clicked.parents().hasClass("dropdown"))) {
			$(".dropdownUl").hide();
			$(".dropdownUl").removeClass("selected");
		}
	});
</script>
