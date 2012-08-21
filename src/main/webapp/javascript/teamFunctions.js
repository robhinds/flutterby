function createTeam(event){
	var teamName = $("#teamName").val();
	var teamDesc = $("#teamDesc").val();
	var parentTeam = $("#parentTeam").val();
	var url = event.data.url;
	
	$.getJSON(url+'/team/createTeam.ajax', {teamName : teamName, parentTeam : parentTeam, teamDesc : teamDesc}, function(data) {
		$("#teamName").val($("#teamName").attr('defaultVal'));
	    $("#teamName").css({color:'grey'});
	    $("#teamDesc").val($("#teamDesc").attr('defaultVal'));
	    $("#teamDesc").css({color:'grey'});
	    $("#parentTeam").val();
	    
	    $("#freeow").freeow("Success", "Team Created", {
			classes: ["gray", "pushpin"]
		});
	});
}

function updateTeam(event){
	var parentTeam = $("#parentTeamUpdate").val();
	var selectedTeam = $("#teamSelect").val();
	var assignSubmitArgs = []; 
	var assigned = $('#assigned').children('.drag');
	$.each(assigned,function(index,value)
			{
				assignSubmitArgs[index] = $(value).attr('id'); 
			})
	var url = event.data.url;
	var teamArgs = assignSubmitArgs.join(',');
	$.getJSON(url+'/team/updateTeam.ajax', {selectedTeam:selectedTeam, parentTeam : parentTeam, team:teamArgs}, function(data) {
		$("#freeow").freeow("Success", "Team Updated", {
			classes: ["gray", "pushpin"]
		});
		
		$("#parentTeamUpdate").val();
		$("parentTeamUpdate option:selected").removeAttr("selected");

		$("#teamLeadUpdate").val();
		$("#teamSelect").val();
	});
}


function updateTeamConfig(event){
	var selectedTeam = $("#teamSelect").val();
	$.getJSON(url+'/team/getSettings.ajax', {selectedTeam : selectedTeam}, function(data) {
		var updates = $.parseJSON(data.updates);
		if (!jQuery.isEmptyObject(updates)){

			//update parent team
			//$("#parentTeamUpdate").val(updates.pTeam.pTeamId).attr('selected', 'selected');
			$("#parentTeamUpdate").val(updates.pTeam.pTeamId);

			var assigned = $('#assigned').children('.drag');
			$.each(assigned,function(index,value)
					{
						$(value).appendTo('#unassigned');
					});
			
			//update selected members
			$.each(updates.members, function() {
				$('#'+this.id).appendTo($('#assigned'));

			});
		}
	});
	
}