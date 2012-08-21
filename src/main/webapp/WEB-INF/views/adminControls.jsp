<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='j'
	uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray'%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/JavaScript">
	$('#controlPanel').live('click', function() {
		if ($(this).next().is(":visible")) {
			$(this).next().hide("fast");
		} else {
			$(this).next().show("fast");
		}
	});

	var url = "${pageContext.request.contextPath}";
	$(document).ready(function() {
		$('#createTeam').bind("click", {
			url : url
		}, createTeam);
		$('#teamSelect').bind("change", {
			url : url
		}, updateTeamConfig);
		$('#updateTeam').bind("click", {
			url : url
		}, updateTeam);
	});
</script>

<div id="mainContentBubble" class="rounded_menu txt_lightgrey">

	<H1>Admin Console</H1>


	<div id="controlPanel" class="rounded_menu light_grey mousePointer">
		Create Teams</div>
	<div id="teamCreation" style="display: none; padding-left: 15px;">

		Enter Team Name<br /> <input id="teamName" type="text"
			defaultVal="Team Name.." class="ninetyFivePercent clearText">

		<br /> <br /> Enter Team Description<br /> <input id="teamDesc"
			type="text" defaultVal="Team Description.."
			class="ninetyFivePercent clearText"> <br /> <br /> Add
		Parent Team <select id="parentTeam" class="ninetyFivePercent">
			<option></option>
			<j:forEachJson items="${contactsTeams}" var="contact">
				<option value="<j:jsonGetString key="id" json="${contact}" />">
					<j:jsonGetString key="name" json="${contact}" />
				</option>
			</j:forEachJson>
		</select> <br /> <br /> <input type="submit" id="createTeam"
			value="Create Team" />

	</div>


	<div id="controlPanel" class="rounded_menu light_grey mousePointer">
		Manage Teams</div>
	<div id="teamManagement" style="display: none; padding-left: 15px;">

		Select Team<br /> <select id="teamSelect" class="ninetyFivePercent">
			<option></option>
			<j:forEachJson items="${contactsTeams}" var="contact">
				<option value="<j:jsonGetString key="id" json="${contact}" />">
					<j:jsonGetString key="name" json="${contact}" />
				</option>
			</j:forEachJson>
		</select> <br /> <br /> Change Parent Team <select id="parentTeamUpdate"
			class="ninetyFivePercent">
			<option></option>
			<j:forEachJson items="${contactsTeams}" var="contact">
				<option value="<j:jsonGetString key="id" json="${contact}" />">
					<j:jsonGetString key="name" json="${contact}" />
				</option>
			</j:forEachJson>
		</select> <br /> <br /> <br /> Assign people to team
		<div id="teamAssignmentDiv">
			<div id="unassigned" class="rounded_menu txt_lightgrey">
				Unassigned (drag contacts to assign) : <br /> <br />
				<j:forEachJson items="${contactsPersons}" var="contact">
					<c:set var="teamName">
						<j:jsonGetString key="teamName" json="${contact}" />
					</c:set>
					<c:if test="${empty teamName}">
						<span class="bubbleInfo drag" draggable="true"
							id="<j:jsonGetString key='id' json='${contact}' />"> <a
							href="<c:url value='/user/'/><j:jsonGetString key="name" json="${contact}" />">
								<span class="connectionHighlight trigger"><j:jsonGetString
										key="name" json="${contact}" /></span>
						</a> <span class="popup rounded_menu"> <!-- your information content -->
								<img src="<c:url value='/resources/images/anonPhoto.png'/>" />
								<br /> <strong>Team:</strong> <j:jsonGetString key="teamName"
									json="${contact}" /><br /> <strong>Status:</strong> "<j:jsonGetString
									key="status" json="${contact}" />"
						</span>
						</span>

					</c:if>
				</j:forEachJson>
			</div>
			<div id="assigned" class="rounded_menu txt_lightgrey">
				Assigned : <br /> <br />
				<j:forEachJson items="${contactsPersons}" var="contact">
					<c:set var="teamName">
						<j:jsonGetString key="teamName" json="${contact}" />
					</c:set>
					<c:if test="${not empty teamName}">
						<span class="bubbleInfo drag" draggable="true"
							id="<j:jsonGetString key='id' json='${contact}'/>"> <a
							href="<c:url value='/user/'/><j:jsonGetString key="name" json="${contact}" />">
								<span class="connectionHighlight trigger"><j:jsonGetString
										key="name" json="${contact}" /></span>
						</a> <span class="popup rounded_menu"> <!-- your information content -->
								<img src="<c:url value='/resources/images/anonPhoto.png'/>" />
								<br /> <strong>Team:</strong> <j:jsonGetString key="teamName"
									json="${contact}" /><br /> <strong>Status:</strong> "<j:jsonGetString
									key="status" json="${contact}" />"
						</span>
						</span>

					</c:if>

				</j:forEachJson>

			</div>
		</div>

		<br /> <input type="submit" id="updateTeam" value="Save Changes" />

	</div>



	<div class="cleaner"></div>
</div>

<script type="text/javascript">
	teamAssignmentDragAssign();
</script>

