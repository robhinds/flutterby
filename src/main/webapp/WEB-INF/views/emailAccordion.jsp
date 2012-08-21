<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<script type="text/JavaScript">
	$(function() {
		$( "#email_menu_action" ).accordion({ collapsible: true, active:0, autoHeight: false });
	});
	
</script>

<!--  email control menu section -->
<div id="user_menu_action_wrapper" class="column_menu">
	<div id="email_menu_action">
		<h3><a href="#">Folder Navigation</a></h3>
		<div>
	    	<tiles:insertTemplate template="/WEB-INF/views/emailNavigation.jsp" />
	    </div>
	</div>
</div>