/**
 * Javascript object containing all common functions to handle
 * processing Question objects
 */


function createQuestion(event){
	var title = $("#questionTitle").val();
	var details = $("#questionDescription").val();
	var recipientId = $("#questionContactId").val();
	var tags = $("#tags").val();
	var url = event.data.url;
	
	$.getJSON(url+'/question/createQuestion.ajax', {title : title, body : details, recipientId : recipientId, tags : tags}, function(data) {
		disablePopup("#questionBackground", "#questionPopup");
		$("#questionTitle").val($("#questionTitle").attr('defaultVal'));
	    $("#questionTitle").css({color:'grey'});
	    $("#questionDescription").val($("#questionDescription").attr('defaultVal'));
	    $("#questionDescription").css({color:'grey'});
	    
	    $("#freeow").freeow("Question Created", "Question Asked..", {
			classes: ["gray", "pushpin"]
		});
	});
}

function postAnswer(event){
	var answer = $("#answer_question").val();
	var questionId = $("#answer_question").attr("questionId");
	$.getJSON(event.data.url+'/question/postAnswer.ajax', {answer : answer, questionId : questionId}, function(data) {
		$("#answer_question").val("");
		window.location.reload();
	});
}

function updateQuestion(questionId, completed, url){
	$.getJSON(url+'/question/updateQuestion.ajax', {id : questionId, completed: completed});
}