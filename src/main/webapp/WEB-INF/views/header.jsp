<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Social Tracker</title>

<!-- jQuery Library -->
<link rel="Stylesheet" type="text/css" href="<c:url value='/resources/styles/blue/jquery-ui-1.8.14.custom.css' />" />	
<script type="text/javascript" src="<c:url value='/resources/libs/jquery/jquery-1.7.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/jquery/jquery-ui-1.8.14.custom.min.js' />"></script>

<!-- Org Chart JQuery Plugin -->
<script type="text/javascript" src="<c:url value='/resources/libs/jquery/plugins/jquery.jOrgChart.js' />"></script>
<link rel="stylesheet" href="<c:url value='/resources/libs/jquery/style/jquery.jOrgChart.css' />"/>

<!-- Freeow alerts JQuery Plugin -->
<script type="text/javascript" src="<c:url value='/resources/libs/jquery/plugins/jquery.freeow.min.js' />"></script>
<link rel="stylesheet" href="<c:url value='/resources/libs/jquery/style/freeow.css' />"/>


<!-- MarkItUp Javascript -->
<script type="text/javascript" src="<c:url value='/resources/libs/markitup/jquery.markitup.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/markitup/sets/bbcode/set.js' />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/libs/markitup/skins/markitup/style.css' />" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/libs/markitup/sets/bbcode/style.css' />" />

<!-- Code Prettify -->
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/libs/google-code-prettify/prettify.css' />" />
<script type="text/javascript" src="<c:url value='/resources/libs/google-code-prettify/prettify.js' />"></script>

<!-- Dropdown css -->
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/styles/dropdown.css' />" />

<!-- Register css -->
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/styles/registerForm.css' />" />

<!-- Quick Action css -->
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/styles/quickActions.css' />" />

<!--  Required Javascript files  -->
<script type="text/javascript" src="<c:url value='/resources/javascript/statusFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/notificationFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/popupFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/emailFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/todoFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/workTicketFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/questionFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/fieldUtil.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/peopleFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/teamFunctions.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/toolTipBubble.js' />"></script>

<script type="text/javascript" src="<c:url value='/resources/javascript/html5utils.js' />"> </script>
<script type="text/javascript" src="<c:url value='/resources/javascript/teamAssignment.js' />"> </script>

<!-- Core StyleSheet -->
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/styles/main_style.css' />"/>

<script language="javascript" type="text/javascript">
function clearText(field){

    if (field.defaultValue == field.value) field.value = '';
    else if (field.value == '') field.value = field.defaultValue;

}
</script>