/**
 * Javascript object containing all common functions to handle
 * processing People - primarily for the popup window
 */

//0 = disabled; 1 = enabled;
var popupStatus = 0;
var dataLoaded=false;

function loadPeopleFinder(url){
	$.getJSON(url+'/people/loadDirectory.ajax', function(data) {
		var json = $.parseJSON(data.directory);
		if (!jQuery.isEmptyObject(json)){
			$.each(json, function(index, teamObj) {
				if (!jQuery.isEmptyObject(this.members)){
					//create table header
					var html = "<table class=\"table_alternate people_table\">"
						+"<thead><tr><th scope=\"col\">" + teamObj.name + "</th></tr></thead><tbody>";
					
					//iterate through individuals of the team:
					$.each(teamObj.members, function(i, membObj) {
						var rowClass = i % 2 == 0 ? '' : 'odd';
						html +="<tr class=\"" + rowClass + "\"><td>"
						+ "<a href=\"" +url+ "/user/" + membObj.name + "\">"
						+ membObj.name 
						+ "</a></td><td class=\"feed_actions\">"
						+ "<a href=\"#\"><img class=\"connect\" src=\"" + url 
						+ "/resources/images/connect.png\" alt=\"connect\" title=\"Connect\"" 
						+ " id=\"" + membObj.id +  "\" /></a>"
						+ "</td></tr>";
					});
					
					//end table
					html += "</tbody></table>";
					$('#peopleDirectory').append(html);
				}
			});
		}
		dataLoaded = true;
	});
}


$('.connect').live('click', function () { 
	var connectId = $(this).attr('id');
	
	$.getJSON('people/connect.ajax', {connectId : connectId}, function(data) {
		disablePopup("#peopleBackground", "#peoplePopup");
	});
});