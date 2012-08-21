/**
 * Javascript object containing all common functions to handle
 * processing Private Message objects
 */


function sendEmail(){
	var msgBody = $("#msgBody").val();
	var recipient = $("#emailContactId").val();
	
	$.getJSON('email/sendEmail.ajax', {msg : msgBody, recipient : recipient}, function(data) {
		disablePopup("#msgBackground", "#msgPopup");
		
		$("#freeow").freeow("Message Sent", "Message Sent..", {
			classes: ["gray", "pushpin"]
		});
	});
}