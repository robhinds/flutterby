<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

	<div id='emailContent'>			
			<input id="subject" type="text" defaultVal="Subject..." class="autoWidth clearText">
			<textarea id="msgBody" rows="12" cols="2" defaultVal="Compose Message.." class="clearText"></textarea>
			Send To:
			<tags:contactDropDown identifier="email" ></tags:contactDropDown>
			<br/><br/>
			<input type="submit" id="sendMessage" value="Send Message" />
		<div class="cleaner">
		</div>
	</div>

<script type="text/JavaScript">
	$(document).ready(function(){
		$("#sendMessage").click(sendEmail);
	});
</script>