$(document).ready(function() {
    $("#myTable").DataTable({
        "lengthMenu": [5, 10, 25, 50, 75, 100],
        "pagingType": "simple"

    });

   $('[data-toggle="tooltip"]').tooltip({
		 trigger : 'hover',
	});
	$('[data-toggle="tooltip"]').on('click', function () {
		$(this).tooltip('hide');
	});


    $('.select2').select2();

    window.setTimeout(function() {
        $(".alert").fadeTo(500, 0).slideUp(500, function() {
            $(this).remove();
        });
    }, 4000);


});