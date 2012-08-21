<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<script type="text/JavaScript">
	$(document).ready(function(){	
		$("#submitStatus").click(updateStatus);
	});
</script>

<div id="quickActionDiv">
	<div class="box">
		
		<ul id="tabMenu">
			<li class="statusTab selected"></li>
		</ul>
		<br /><br />
		
		<div class="boxBody rounded_menu txt_lightgrey">
		
			<div id="quickStatus" class="show parent">
			<ul>
				<input type='text' id="status" name="status" defaultVal="What are you working on?" class="clearText ninetyFivePercent"/>
				<input type="submit" id="submitStatus" value="Update">
			</ul>
			</div>
			
		</div>
	</div>
</div>