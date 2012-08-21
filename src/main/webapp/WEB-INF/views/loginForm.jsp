<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<div id="mainContentBubble" class = "rounded_menu txt_lightgrey">
	<div class="content_header_text"><h1>Welcome to Butterfly</h1></div>
	<div id="user_menu" class="rounded_menu light_grey login_menu">
		<form name="f" action="loginProcess" method="POST">
		       Username: <input type='text' name='j_username' align="RIGHT"/>
		       <br />
		       Password: <input type='password' name='j_password' align="RIGHT" />
		       <br /><br />
		        <input name="submit" type="submit" value='Login'>
		        <input name="reset" type="reset">
			<br />
		</form>
		<br/>
		<i>Not a member yet? <a href="<c:url value='/register'/>">Register Now</a></i>
	</div>
</div>