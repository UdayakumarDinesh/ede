<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

<style type="text/css">

.emp-card{
	transition: top ease 0.5s;
	width:10rem;
	height:14rem;
}

.emp-card .card-img-top{
	height: 8rem;
	width: 8rem;
	margin: 1rem 1rem 0rem 1rem;
}


</style>

</head>
<body>

<%
	
	Employee employee = (Employee )request.getAttribute("employee") ;
	String isself = (String )request.getAttribute("isself") ;
	List<CHSSTreatType> treattypelist=(List<CHSSTreatType>)request.getAttribute("treattypelist");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Apply</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<!-- <li class="breadcrumb-item "><a href="CHSSApply.htm">CHSS Apply</a></li> -->
						<li class="breadcrumb-item active " aria-current="page">CHSS Details</li>
					</ol>
				</div>
			</div>
	</div>	
	
	 <div class="page card dashboard-card">
	
	<div class="card-body" >
	
	
	
	<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
		if(ses1!=null){ %>
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
			
		<%}if(ses!=null){ %>
			
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		<%} %>
	</div>
	
	
				
			<div class="card" >
				<div class="card-body " >
					<form action="CHSSApplySubmit.htm" method="post" autocomplete="off">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="row">
							
							<%if(isself.equalsIgnoreCase("N")){
								Object[] familyMemberData = (Object[])request.getAttribute("familyMemberData") ; %>
								
								<div class="col-3">
									<b>Patient Name :</b> <br><%=familyMemberData[1] %>
								</div>
								
								<div class="col-2">
									<b>Relation : </b><br><%=familyMemberData[7] %>
									<input type="hidden" name="patientid" value="<%=familyMemberData[0]%>">
									<input type="hidden" name="relationid" value="<%=familyMemberData[2]%>">
								</div>
								
							<%}else{ %>
							
								<div class="col-3">
									<b> Patient Name : </b><br><%=employee.getEmpName() %>
								</div>
								
								<div class="col-2">
									<b>Relation : </b><br>SELF
									<input type="hidden" name="patientid" value="<%=employee.getEmpId()%>">
									<input type="hidden" name="relationid" value="0">
								</div>
								
							<%} %>
							<div class="col-2">
								<b>Treatment Type : </b><br>
								<select class="form-control select2 w-100" name="treatmenttype" required="required" >
									<option value="" selected="selected" disabled="disabled">Choose..</option>
									<%for(CHSSTreatType treattype : treattypelist ){ %>
										<option value="<%=treattype.getTreatTypeId()%>"><%=treattype.getTreatmentName() %></option>
									<%} %>								
								</select>
							</div>
							<div class="col-3">
								<b>Ailment/Disease/Accident : </b><br>
								<input type="text" class="form-control w-100" name="ailment" value="" required="required" maxlength="255" >
							</div>
							<!-- <div class="col-2">
								<b>No of ENCL : </b> <br><input type="number" class="form-control w-100" name="enclosurecount" value="" min="1" required="required" >
							</div> -->
							<input type="hidden" name="enclosurecount" value="0">
						</div>
						<br>
						<div class="row">
						
							<table class="table table-bordered table-hover table-condensed  info shadow-nohover" >
								<thead>
									<tr>
										<th style="width:5%;" >SNo.</th>
										<th style="width:35%;" >Hospital / Medical / Diagnostics Centre Name</th>
										<th style="width:25%;" >Bill / Receipt No.</th>
										<th style="width:10%;" >Bill Date</th>
										<th style="width:5%;" > <button type="button" class="btn tbl-row-add" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
									</tr>
								</thead>
								<tbody >
									<tr class="tr_clone" >
										<td><span class="sno" id="sno">1</span> </td>
										<td><input type="text" class="form-control items" name="centername" id="centername" value="" style="width:100%; "  maxlength="500" required="required"></td>
										<td><input type="text" class="form-control items" name="billno" id="billno" value="" style="width:100%; "   maxlength="100" required="required"></td>
										<td><input type="text" class="form-control billdate" name="billdate" id="billdate" value="" style="width:100%; "    maxlength="10" readonly required="required"></td>
										<td><button type="button" class="btn tbl-row-rem"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
									</tr>
								</tbody>							
								
							</table>
						</div>
						
						<div class="row justify-content-center">
							<button type="submit" class="btn btn-sm submit-btn" name="action" value="add" Onclick="return confirm('Are You Sure To Submit?')" >ENTER BILL DETAILS</button>						
						</div>
					</form>
				</div>
			</div>		
			
		</div>
	
	 </div>

<script type="text/javascript">

var threeMonthsAgo = moment().subtract(3, 'months');


var count=1;

	$("table").on('click','.tbl-row-add' ,function() 
	{
	   	var $tr = $('.tr_clone').last('.tr_clone');
	   	var $clone = $tr.clone();
	   	$tr.after($clone);
	   	$clone.find(".items").val("").end();
	
		count++;
		
		$clone.find(".sno").html(count).end(); 
		 
	  $clone.find('.billdate').daterangepicker({
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,
			"maxDate" :new Date(),
			"minDate" : threeMonthsAgo,
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
		}
		});
	  $('[data-toggle="tooltip"]').tooltip({
			 trigger : 'hover',
		});
	  
	});



	$("table").on('click','.tbl-row-rem' ,function() {
	var cl=$('.tr_clone').length;
	if(cl>1){
	          
	   var $tr = $(this).closest('.tr_clone');
	   var $clone = $tr.remove();
	   $tr.after($clone);
	   
	}
	   
	});

	$('.billdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"maxDate" :new Date(), 
		"minDate" : threeMonthsAgo,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});




</script>


</body>
</html>