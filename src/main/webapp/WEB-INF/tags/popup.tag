<%@ tag language="java" description="will pop a message center screen"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name='popupId' required="true" %>
<%@ attribute name='loadFunction' %>
<%@ attribute name='loadArgs' %>
<%@ attribute name='handler' required='true'%>

<c:if test="${empty loadFunction}">
  <c:set var="loadFunction" value="false"/>
</c:if>

<c:if test="${empty loadArgs}">
  <c:set var="loadArgs" value="false"/>
</c:if>


<div id="${popupId}Popup" class="popupWindow">  
    <a id="${popupId}PopupClose" class="popupWindowClose">x</a>  
    	<jsp:doBody/>
</div>  
<div id="${popupId}Background" class="popupBackground"></div> 

<script type="text/JavaScript">
	var url = "${pageContext.request.contextPath}";
	
	
	$("#${handler}").click(function(){
		console.log("lanching people directory");
		var loadFunc = ${loadFunction};
		var loadArgs = ${loadArgs};
		centerPopup("#${popupId}Background", "#${popupId}Popup");
		loadPopup(loadFunc,'#${popupId}Background','#${popupId}Popup',loadArgs);
	});
	$("#${popupId}PopupClose").click(function(){
		disablePopup("#${popupId}Background", "#${popupId}Popup");
	});
	$("#${popupId}Background").click(function(){
		disablePopup("#${popupId}Background", "#${popupId}Popup");
	});
	
	$(document).keypress(function(e){
		if(e.keyCode==27){
			disablePopup("#${popupId}Background", "#${popupId}Popup");
		}
	});	
</script>