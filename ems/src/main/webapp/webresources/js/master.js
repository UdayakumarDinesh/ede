$(document).ready(function() {
	$("#myTable").DataTable({
		"lengthMenu": [5, 10, 25, 50, 75, 100],
		"pagingType": "simple"

	});
	
	$('[data-toggle="tooltip"]').tooltip()
	
	
	 $('.select2').select2();
	
	
});


