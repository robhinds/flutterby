/**
 * Javascript object containing all common functions to handle
 * processing Status objects
 */


function search(event){
	var searchTerm = $("#searchBox").val();
	var url = event.data.url;
	var searchContext = event.data.searchContext;
	
	$.getJSON(url+'/search/results.ajax', {searchContext:searchContext, searchTerm:searchTerm}, function(data) {
		window.location.reload();
	});
}