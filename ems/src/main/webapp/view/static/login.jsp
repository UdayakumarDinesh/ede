<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="dependancy.jsp"></jsp:include>
<title>LogIn</title>
</head>
<body>

<form action="abc.htm">

<button type="submit" class="btn btn-sm btn-primary" style="color: black" >
button

</button>
</form>
<input type="text" class="form-control" id="startdate" style="width: 10%;" readonly="readonly" >

<script type="text/javascript">
$('#startdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :new Date(), */
	"startDate" : new Date(),

	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


</script>

</body>
</html>