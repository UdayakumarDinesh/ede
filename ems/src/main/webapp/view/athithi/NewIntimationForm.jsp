<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<spring:url value="/webresources/css/multiselect.css" var="MultiselectCss" />     
<link href="${MultiselectCss}" rel="stylesheet" />
 
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

 <spring:url value="/webresources/js/multiselect.js" var="multiselectjs" />  
<script src="${multiselectjs}"></script> 

<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js"></script> -->
<style type="text/css">
.wave {
	position: fixed;
	bottom: 0;
	left: 0;
	height: 100%;
	z-index: -1;
}

#wrapper {
	background-image: url("vtsfolder/images/background.png") !important;
	background-repeat: no-repeat;
	background-size: 100%;
	width: 100%;
}

label {
	font-size: 18px;
	font-weight: 600;
	color: #264e86;
}

.table  tr th {
	background-color: aliceblue;
	text-align: left;
}

.table thead tr td ,.table thead tr th{
	color: black;
}


.spermission {
  width: 100%;
  height: 34px;
  padding: 6px 90px;
  font-size: 14px;
  line-height: 1.42857143;
  color: #555555;
  background-color: #ffffff;
  border: 1px solid #cccccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}
 .spermission option {
  font-size: 14px;
  line-height: 1.42857143;
  color: #555555;
  background-color: #ffffff;
  background-image: none;
  border: 1px solid #cccccc;
  border-radius: 4px;
  padding: 5px 10px;
  margin: 5px 0;
}

option:checked {
  background-color: #337ab7;
  color: #fff;
}
.multiselect{
padding: 4px 90px;
background-color:#E0E0E0;
}
</style>

</head>
<body>

<div class="card-header page-top ">
	<div class="row">
		<div class="col-md-3">
			<h5>Visitors Intimation </h5>
		</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item ml-auto"><a	href="IntimationList.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Intimation List</a></li>
					<li class="breadcrumb-item active" aria-current="page">Intimation</li>
				</ol>
			</div>
		</div>
</div>	
<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
	<div align="center">
		<div class="alert alert-danger" role="alert">
        	<%=ses1 %>
        </div>
   	</div>
	<%}if(ses!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
        	<%=ses %>
        </div>
    </div>
	<%} %>

  		<!-- <div class="card" >
	        <div class="card-header">
	           
                </div>
            </div>  --> 
<%
List<Object[]> compnyList =(List<Object[]>)request.getAttribute("compnyList"); 
List<Object[]> officerList=(List<Object[]>)request.getAttribute("officer");
%>        
	<div class="card-body main-card">
   		<!-- <div class="container-fluid"> -->
		<div class="row">
			<div class="col-sm-12" style="top: 10px;">
				<div class="card shadow-nohover">
				<!-- 	<div class="card-header">
						<h5>
							<b>Add New Intimation </b>
						</h5>
					</div> -->
					<div class="card-body">
						<form action="newIntimationSubmit" method="post"  onSubmit="remove(); return confirm('Do you want to submit?'); " >
							<div class="form-row">
								<div class="form-group col-md-1">
									<label >Company <span class="mandatory" style="color: red;">*</span> </label>
								</div>
								<div class="col-md-5">
								    <select class="form-control form-control CompanyID" id="CompanyID"  name="company" required>
						         	  <option></option>
								    <%for(Object[] data:compnyList){ %>
								    <option value="<%=data[0]%>"><%=data[1]%></option>
								    <%} %>
				                    <option value="QQ">Add New Company</option> 
								         
								    </select>
								</div>
								   
                         		<div class="form-group col-md-1">
									<label >Visitors <span class="mandatory" style="color: red;">*</span> </label>
								
								</div>
								<div class="col-md-4">
								    <select class="form-control CompanyID"  multiple="multiple"  id="EmppIDs"  name="visitors" required>
								   
				                    <option value="QQ"style="font-weight: 5px;">Add New Employee</option> 
								         
								    </select>
								</div>
								<div class="form-check" >
									<input class="form-check-input" style="height: 26px; width: 26px;" type="checkbox" required="required" value="" id="ConfirmCheck"> 
					                 &nbsp;&nbsp; <label >Confirm<span class="mandatory"  style="color: red;">*</span> </label>
								</div>
								
                           
							</div>
							<!-- New Company Form start -->
							<div class="border" id="compyForm" style="margin-bottom: 10px; background-color: #d6f8ff ">
								<div class="form-row ">
								</div>
								<div class="form-row" style="margin-top: 10px;">
									<div class="form-group col-md-4">
									
										<input	type="text" style="margin-left: .4rem" class="form-control" name="newCompanyName" id="newCompanyName" placeholder="Enter New Company Name">
									</div>
									<div class="form-group col-md-3">
									
										<input type="text" class="form-control" id="newCompanyCity" name="newCompanyCity" placeholder="Enter Company City">
									</div>
								
								</div>
								<table class="table  table-bordered table-hover table-striped table-condensed  info shadow-nohover" id="myTable20">
								<thead>  
									<tr id="">
										<th> Employee Name </th>
										<th>Designation</th>
										<th>Mobile No</th>
										<th> <button type="button" class="tr_clone_addbtn btn " name="add"> <i class="btn btn-sm fa fa-plus" style="color: green; padding: 0px  0px  0px  0px;"></i></button></th>	
									</tr>
																			
									<tr class="tr_clone">
									
										<td width="30%"><input type="text" name="empName[]" class="form-control item_name child empName"  maxlength="100" /></td>
										<td width="40%">				
								         	<input type="text" name="designation[]" class="form-control item_name child " maxlength="100" />
										</td>								
						         		<td  width="30%"><input type="text" name="mobile[]" class="form-control item_name child mobileClass" maxlength="10"/></td>      
																			
										<td><!-- <i class="btn btn-sm fa fa-minus" style="color: red;" id="MemberMinus0" onclick="Memberremove(this)" ></i> -->
											<button type="button" class="tr_clone_sub btn " name="sub" ><i class="btn btn-sm fa fa-minus" style="color: red;"></i></button>
										</td>								
									</tr>
								</thead>
						
							</table>
							<div align="center">
							<input type="button" class="btn btn-primary" style="margin-bottom: .8rem;" value="Submit" onclick="AddCompany();">
							</div>
									
							</div>
				        <!-- New Company Form start -->
				        <!--new emp from start   -->
				        
				        	<div class="border" id="empForm" style="margin-bottom: 10px; background-color: #d6f8ff ">
								
								
							<table class="table  table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
								<thead>  
									<tr>
										<th> Employee Name </th>
										<th>Designation</th>
										<th>Mobile No</th>
										<th> <button type="button" class="tr_clone_addbtn1 btn " name="add"> <i class="btn btn-sm fa fa-plus" style="color: green; padding: 0px  0px  0px  0px;"></i></button></th>	
									</tr>
								</thead>
								<tbody>									
									<tr class="tr_clone1">
									
										<td width="30%"><input type="text" name="empName1[]" class="form-control item_name child empName" maxlength="100" /></td>
										<td width="40%">				
								         	<input type="text" name="designation1[]" class="form-control item_name child" maxlength="100" />
										</td>								
						         		<td  width="30%"><input type="text" name="mobile1[]" class="form-control item_name child mobileClass"  maxlength="10"/></td>      					
										<td><!-- <i class="btn btn-sm fa fa-minus" style="color: red;" id="MemberMinus0" onclick="Memberremove(this)" ></i> -->
											<button type="button" class="tr_clone_sub1 btn " name="sub" ><i class="btn btn-sm fa fa-minus" style="color: red;"></i></button>
										</td>								
									</tr>
								</tbody>
							</table>
							<div align="center">
							<input type="button" class="btn btn-primary" style="margin-bottom: .8rem;" value="Submit" onclick="AddEmp();">
							</div>
								
							</div>
                     <!-- new emp from start   -->
						<div class="otherDetails" id="otherDetailsId">
							<div class="form-row" style="margin-top: 10px;">
					
							    <div class="form-group col-md-1">
									<label class="label" >Dates <span class="mandatory"  style="color: red;">*</span> </label>
									
								</div>
								<div class="col-md-4">
								<div class="form-row">
								<div class="col-md-4">
								<input class="form-control" type="text" id="fdate" name="fdate" required="required" readonly="readonly">
								 
								</div>
								<label> - </label>
								<div class="col-md-4">
								<input class="form-control" type="text" id="tdate" name="tdate" required="required"  readonly="readonly" >
								</div>
								</div>
								</div>
								<div class="form-group col-md-1" >
									<label style="margin-left:-115%;">Duration<span class="mandatory" style="color: red;">*</span> </label>
								</div>
						        <div class="col-md-1" style="margin-left:-8.5%;">
								 <input class="form-control" type="number" id="duration" name="duration" placeholder="In hours" required="required" min="1" max="24">
								</div>
								<div class="form-group col-md-2" >
									<label >Visit Expected Time<span class="mandatory" style="color: red;">*</span> </label>
								</div>
						        <div class="col-lg-3">
								 <input class="form-control" type="time" value="09:30" id="expectedTime" name="expectedTime" required="required">
								</div>
									
							</div>
							<div class="form-row" style="margin-top: 10px;">
							 <div class="form-group col-md-1" >
									<label > Officer  <span class="mandatory" style="color: red;">*</span> </label>
								</div>
						        <div class="col-md-5"  >
								<select class="form-control col-md-5  CompanyID"  style="width: 33rem;"   name="officer" required>
								  <option></option>
								    <%for(Object[] data:officerList){ %>
								    <option value="<%=data[0]%>"><%=data[1]%>,&nbsp;<%=data[2]%> </option>
								    <%} %>
								    </select>
								    
								</div>
								<div class="form-group col-md-2">
									<label>Special Permission</label>
									
								</div>
								
								 <div class="col-md-3">			
								<!--  <input class="form-control" name="spermission"  id="specialPermission"  maxlength="255" placeholder="Enter max 255 characters" > -->
								 <select class="form-control col-lg-12 spermission" id="options" name="spermission" multiple required>
								    <option value="Not Applicable">Not Applicable</option>
								    <option value="Laptop">Laptop</option>
								    <option value="Mobile">Mobile</option>
								    <option value="Pendrive">Pendrive</option>
								    <option value="CD/DVD">CD/DVD</option>
								    <option value="Vehicle">Vehicle</option>
								    </select> 
								    								    
								</div> 
							</div>
							<div class="form-row" style="margin-top: 10px;">
								<div class="form-group col-md-1">
									<label>Purpose<span class="mandatory"  style="color: red;">*</span> </label>
									
								</div>
								<div class="col-md-5">
								 <input class="form-control" name="purpose"  id="purposeId"  maxlength="255" placeholder="Enter max 255 characters" required>
								</div>
								
							
								
							
							</div>
									<div align="center" style="margin-top: 20px;">
										<button type="submit" class="btn btn-success">Submit</button>
									</div>
							</div>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- </div> -->
 	
	<script type="text/javascript">
	
	 $("input[name = 'empName[]']").keypress(function(event){
	        var inputValue = event.which;
	        // allow letters and whitespaces only.
	        if(inputValue==8){
	        }
	        else if(!((inputValue > 64 && inputValue < 91) || (inputValue > 96 && inputValue  < 123)) && (inputValue != 32 && inputValue != 0)) { 
	            event.preventDefault(); 
	        }
	    });
	
	</script>
	
	<script type="text/javascript">
	
	$('.mobileClass').keyup(function () { 

	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	
	

	
	</script>

<script type="text/javascript">

$(function() {
    $('#EmppIDs').change(function(e) {
        var selected = $(e.target).val();
        var y2 = selected.toString(); 
    if(y2.includes('QQ'))  {
    	  $("#empForm").show(); 
    	  $("#ConfirmCheck").attr("disabled", true);
    }else{
    	  $("#empForm").hide(); 
    	  $("#ConfirmCheck").removeAttr("disabled");
    }
    }); 
});
	
</script>


<script type="text/javascript">

$(document).ready(function(){
	 $("#compyForm").hide();
	 $("#empForm").hide();
	 $("#otherDetailsId").hide();
/* 	   $("#otherDetailsId").hide(); */
	$(".CompanyID").select2({
		placeholder: "---Choose---",	
		"language": {
		       "noResults": function() {
		           return '<button class='+'btn'+' id="no-results-btn" onclick="noResultsButtonClicked()">Add New</a>';
		       },
		   },
		    escapeMarkup: function (markup) {
		        return markup;
		    }

	});
  
    })
</script>

<script type="text/javascript">

/* 	
function noResultsButtonClicked() {
	console.log('dfvsvsd');
	 $("#empForm").show();
	} */

</script>

<script type="text/javascript">
$('#CompanyID').change(function() {
    if ($(this).val() == 'QQ')
    {
    	 $("#compyForm").show();
    	 $("#EmppIDs").attr('disabled','disabled');
    }else{
    	 $("#compyForm").hide();
    	 $("#EmppIDs").removeAttr('disabled');
    	
    }
});
</script>


<script type="text/javascript">
function AddCompany() {
	 event.preventDefault();
	 var name=$("#newCompanyName").val();
	 var city=$("#newCompanyCity").val();
	 var emps = [];
	 $("input[name='empName[]']").each(function() {
		 emps.push($(this).val());
	
	 });
	  var y = emps.toString();
	  
	  var desig = [];
		 $("input[name='designation[]']").each(function() {
			 desig.push($(this).val());
		
		 });
	   var z = desig.toString();
	   
	   var mobil = [];
		 $("input[name='mobile[]']").each(function() {
			 mobil.push($(this).val());
		
		 });
	   var m = mobil.toString();

	 if(name.length<=0){
		 alert ("Fill Company Name");
		 return false;
	 } 
	 if(city.length<=0){
		 alert ("Fill Company City Name");
		 return false;
	 }
		var x='';
		
		$.ajax({
			type : "GET",
			url : "addNewCompany",
			data : {
				project : name,
				project1: city,
				project2:y,
				project3:z,
				project4:m
			},
			datatype : 'json',
		    beforeSend: function() {
		    	if(!confirm('Do you want to Add new Company?')){
		    		$('#CompanyID').prop('selectedIndex',0);
		    		return false;
		    	}
	            },
			success : function(result) {
				
				var values = JSON.parse(result);
				for(i = 0; i < values.length; i++){
					if(i==values.length-1){
						 x=x+"<option value="+values[i][0]+" selected='selected' >"+ values[i][1]+ "</option>"; 
					}else{
					 x=x+"<option value="+values[i][0]+">"
					+ values[i][1]
					+ "</option>"; 
				    }
				}
				x=x+'<option value="QQ">Add New Company</option>'
				$("#CompanyID").html(x)
				  x='';
				 var companyId=$("#CompanyID").val();
				$.ajax({
					type : "GET",
					url : "getCompEmp",
					data : {
						project : companyId
					},
					datatype : 'json',
					success : function(result) {
						var values = JSON.parse(result);

						for(i = 0; i < values.length; i++){
							 x=x+"<option value="+values[i][0]+" selected='selected' >"
							+ values[i][1]
							+ "</option>"; 
						}
						x=x+'<option value="QQ" style="font-size:20px;">Add New Employee </option>'
						
						 $("#EmppIDs").html(x);
					   	 $("#EmppIDs").removeAttr('disabled');
					}
					});	
			}
			});	
		
		
		 var cl=$('.tr_clone').length;
		 $('.tr_clone').each(function() {
			 if(cl==1){
				 return false;
			    }
			 var $tr = $(this).closest('.tr_clone');
			   var $clone = $tr.remove();
			 
			    cl--;
			    
			});
		 $("input[name='empName[]']").val("");
		 $("input[name='designation[]']").val("");
		 $("input[name='mobile[]']").val("");
		 $("#newCompanyName").val("");
	     $("#newCompanyCity").val("");
  	    $("#compyForm").hide();
	}
</script>


<script type="text/javascript">
$('#CompanyID').change(function() {
	var x='';
	 var companyId=$("#CompanyID").val();
	 
		$.ajax({
			type : "GET",
			url : "getCompEmp",
			data : {
				project : companyId
			},
			datatype : 'json',
			success : function(result) {
				var values = JSON.parse(result);

				for(i = 0; i < values.length; i++){
					 x=x+"<option value="+values[i][0]+">"
					+ values[i][1]
					+ "</option>"; 
				}
				x=x+'<option value="QQ" style="font-size:20px;">Add New Employee </option>'
				
				$("#EmppIDs").html(x);
			}
			});	
});
</script>
<script type="text/javascript">


$("table").on('click','.tr_clone_addbtn1' ,function() {
      
   var $tr = $('.tr_clone1').last('.tr_clone1');
   var $clone = $tr.clone();
   $tr.after($clone);
   $clone.find("input").val("").end(); 
 
});

$("table").on('click','.tr_clone_sub1' ,function() {
	
var cl=$('.tr_clone1').length;
	
if(cl>1){     
   var $tr = $(this).closest('.tr_clone1');
   var $clone = $tr.remove();
   $tr.after($clone);

}
   
});
</script>
<script type="text/javascript">


$("table").on('click','.tr_clone_addbtn' ,function() {
      
   var $tr = $('.tr_clone').last('.tr_clone');
   var $clone = $tr.clone();
   $tr.after($clone);
   $clone.find("input").val("").end(); 
 
});

$("table").on('click','.tr_clone_sub' ,function() {
	
var cl=$('.tr_clone').length;
	
if(cl>1){     
   var $tr = $(this).closest('.tr_clone');
   var $clone = $tr.remove();
   $tr.after($clone);

}
   
});
</script>
<script type="text/javascript">
function AddEmp() {

	 var selectedEmp = $('#EmppIDs').val(); 
	 selectedEmp=selectedEmp.toString();

	event.preventDefault();
	
	
	 var emps1 = [];
	 $("input[name='empName1[]']").each(function() {
		
		 emps1.push($(this).val());
	      
	 });
	  var y3 = emps1.toString();
	  
	  var desig1 = [];
		 $("input[name='designation1[]']").each(function() {
			 desig1.push($(this).val());
		
		 });
	   var z3 = desig1.toString();
	   
	   var mobil1 = [];
		 $("input[name='mobile1[]']").each(function() {
			 mobil1.push($(this).val());
		
		 });
	   var m3 = mobil1.toString();
	   var companyId=$("#CompanyID").val();
		$.ajax({
			type : "GET",
			url : "addCompEmp",
			data : {
				project : companyId,
				empName : y3,
				design : z3,
				mobilee : m3,
				selected : selectedEmp
				
			},
			datatype : 'json',
			success : function(result) {
				var prev_new = JSON.parse(result);
				var xx='';
				$.ajax({
					type : "GET",
					url : "getCompEmp",
					data : {
						project : companyId
					},
					datatype : 'json',
					success : function(result) {
						var values = JSON.parse(result);

						for(i = 0; i < values.length; i++){
							
							if(prev_new.includes(""+values[i][0])){
								 xx=xx+"<option value="+values[i][0]+" selected='selected' >"
									+ values[i][1]
									+ "</option>"; 
							}else{
							
							 xx=xx+"<option value="+values[i][0]+">"
							+ values[i][1]
							+ "</option>"; 
							}
						}
						xx=xx+'<option value="QQ" style="font-size:20px;">Add New Employee </option>'
						
						 $("#EmppIDs").html(xx);
						  $("#ConfirmCheck").removeAttr("disabled");
					}
					});	
				
			}
			});	
		 var cl1=$('.tr_clone1').length;
		 $('.tr_clone1').each(function() {
			 if(cl1==1){
				 return false;
			    }
			 var $tr = $(this).closest('.tr_clone1');
			   var $clone = $tr.remove();
			 
			    cl1--;
			    
			});
		 
		 $("input[name='empName1[]']").val("");
		 $("input[name='designation1[]']").val("");
		 $("input[name='mobile1[]']").val("");
		
  	    $("#empForm").hide();
	   
	
}
</script>

<script type="text/javascript">

$('#ConfirmCheck').change(function() {
	 var name=$("#CompanyID").val();
	 var city=$("#EmppIDs").val();
	
	if(name.length<=0||city.length<=0){
		alert("Please select company and visitor ");
		$('#ConfirmCheck').prop('checked', false);
		return false;
	}
	
    if(this.checked) {
 	$("#CompanyID").select2({disabled:'readonly'});
 	$("#EmppIDs").select2({disabled:'readonly'});
    	  $("#otherDetailsId").show(); 
 

    }else{
    	   $("#otherDetailsId").hide();
    	   $("#CompanyID").removeAttr('disabled');
    	   $("#EmppIDs").removeAttr('disabled');
    }
 
});

function remove() {
	$("#CompanyID").removeAttr('disabled');
    $("#EmppIDs").removeAttr('disabled');
}

</script>



<script>

$( "#fdate" ).change( function(){
    
	$( "#tdate" ).daterangepicker({
		"singleDatePicker" : true,
	    "linkedCalendars" : false,
	    "showCustomRangeLabel" : true,
	    "minDate" :$("#fdate").val(),  
	    "cancelClass" : "btn-default",
	    showDropdowns : true,
	    	locale : {
	    	format : 'DD-MM-YYYY'
	    } 
	});

	
});

$( "#fdate" ).daterangepicker({
    "singleDatePicker" : true,
    "linkedCalendars" : false,
    "showCustomRangeLabel" : true,
    "minDate" :new Date(),  
    "cancelClass" : "btn-default",
    showDropdowns : true,
    locale : {
    	format : 'DD-MM-YYYY'
    }
});

        
$( "#tdate" ).daterangepicker({
	"singleDatePicker" : true,
    "linkedCalendars" : false,
    "showCustomRangeLabel" : true,
    "minDate" :$("#fdate").val(),  
    "startDate" : $("#fdate").val(),
    "cancelClass" : "btn-default",
    showDropdowns : true,
    	locale : {
    	format : 'DD-MM-YYYY'
    } 
});




</script>
  <script type="text/javascript">
 /*  $(document).ready(function() {
	  $("#dropdown-submit").click(function() {
	    var selectedOptions = [];
	    $("input[type=checkbox]:checked").each(function() {
	      selectedOptions.push($(this).val());
	    });
	    console.log(selectedOptions); // or use the selected options for further processing
	  });
	});
 */
 
 $(document).ready(function() {
	  $("#dropdown-submit").click(function() {
	    var selectedOptions = [];
	    $("input[type=checkbox]:checked").each(function() {
	      selectedOptions.push($(this).val());
	    });
	    if (selectedOptions.length > 0) {
	      $("#dropdownMenuButton").text(selectedOptions.join(", "));
	    } else {
	      $("#dropdownMenuButton").text("Select options");
	    }
	  });
	});

  </script>

<script type="text/javascript">

$(document).ready(function() {
$('#options').multiselect({
includeSelectAllOption: true,
enableFiltering: true,
maxHeight: 200,
buttonWidth: '100%',
onChange: function(option, checked) {
  if(checked) {
    // Option selected
    console.log('Option selected: ' + $(option).val());
  } else {
    // Option deselected
    console.log('Option deselected: ' + $(option).val());
  }
}
});
});

</script>
</body>
</html>