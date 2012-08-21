function cancel(e) {
  if (e.preventDefault) {
    e.preventDefault();
  }
  return false;
}

function teamAssignmentDragAssign(){
	var dragItems = document.querySelectorAll('[draggable=true]');

	for (var i = 0; i < dragItems.length; i++) {
	  addEvent(dragItems[i], 'dragstart', function (event) {
	    // store the element, and collect it on the drop later on
	    event.dataTransfer.setData('Text', this.id);
	  });
	}

	var drop = document.querySelector('#assigned');
	addEvent(drop, 'dragover', cancel);
	addEvent(drop, 'dragenter', cancel);
	addEvent(drop, 'drop', function (e) {
	  if (e.preventDefault) e.preventDefault(); // stops the browser from redirecting off to the text.
	  $('#'+e.dataTransfer.getData('Text')).appendTo($('#assigned')); 
	  return false;
	});

	var dropUnassigned = document.querySelector('#unassigned');
	addEvent(dropUnassigned, 'dragover', cancel);
	addEvent(dropUnassigned, 'dragenter', cancel);
	addEvent(dropUnassigned, 'drop', function (e) {
		  if (e.preventDefault) e.preventDefault(); // stops the browser from redirecting off to the text.
		  $('#'+e.dataTransfer.getData('Text')).appendTo($('#unassigned')); 
		  return false;
		});
}
