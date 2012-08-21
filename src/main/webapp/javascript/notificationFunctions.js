function checkForNotifications(dropdownId, url, ajaxUrl, altImageSrc){
	$('#ul'+dropdownId).prepend("<li id=\"header"+dropdownId+"\" style=\"font-weight:bold;\"><a href=\""+ url + "/"+dropdownId+"/list\">View All Activity...</a></li>");
	var latestUpdateId;
	var messagesWaiting = false;
	loadMessageNotifications();
	var refreshId = setInterval(loadMessageNotifications, 30000);	//interval set at 30 seconds

	function loadMessageNotifications(){
		var result= $.getJSON(url+ajaxUrl, {latestUpdateId:latestUpdateId}, function(data) {
			var updates = $.parseJSON(data.messageNots);
			if (!jQuery.isEmptyObject(updates)){

				latestUpdateId = updates.latestId;
				$.each(updates.data, function() {
					messagesWaiting=true;
					var styles = '';
					if (this.read == false){
						styles = '<span class=\"activityHighlight newActivityHighlight\">new</span>'; 
					}
					if (dropdownId == "answer"){
						dropdownId = "question";
					}
					var newRow = "<li><a href=\""+url+"/"+dropdownId+"/detail/" + this.activityId + "\">"
									+ styles
									+ this.body
									+ "<br/><span class=\"small_text\">"
									+ this.from
									+ "</span></a></li>";
					$('#ul'+dropdownId).prepend(newRow);
				});
			}
			
			if (messagesWaiting){
				var imgId = "#" + dropdownId + "MenuIcon";
				$(imgId).attr("src",altImageSrc);
			}
		});
			return result; 
	}
}


