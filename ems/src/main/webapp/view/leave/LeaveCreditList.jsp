<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
 #dis{
 text-align: center;
 }
 
.btn-group1 button {
  background-color: #344b8a; /* Green background */
  color: white; /* White text */
  padding: 10px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  float: left; /* Float the buttons side by side */
}

/* Clear floats (clearfix hack) */
.btn-group1:after {
  content: "";
  clear: both;
  display: table;
}

.btn-group1 button:not(:last-child) {
  border-right:thin;
}

/* Add a background color on hover */
.btn-group1 button:hover {
  background-color: #3e8e41;
}
.dataTables_info, .dataTables_length{
  text-align: left !important;
}
.modal-dialog {
    max-width:750px;
    margin: 2rem auto;
}

.page-top {
   margin: 7px 6px 6px 1px !important;
    border-radius: 5px;
}

.dashboard-card{
	    margin: 7px 6px 6px 1px !important;
}

</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Credit List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Credit List</li>
					</ol>
				</div>
			</div>
	</div>	
<%
String month=(String)request.getAttribute("month");
String year=(String)request.getAttribute("year");
String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%><center>
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></center>
	<%}if(ses!=null){ %>
	<center>
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div></center>
                    <%} %>

 <%
    List<Object[]> clist=(List<Object[]>)request.getAttribute("CreditList");
    List<Employee> emplist=(List<Employee>)request.getAttribute("EmpList");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>

<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >
   
   	<div class="row">
   		
   		<div class="col-md-8">
   			<form action="CreditPreview.htm" method="POST" id="preview">
			    <div class="row">
				    
				    <div class="col-md-2" style="margin-top: 4px;font-weight: bold;">
				             Credit :
				    </div>
				    <div class="col-md-4">
					    <select class="form-control  selectpicker" required="required" name="empNo" title="Select Employee" data-live-search="ture" id="empNo">
					    <option value="A">All</option>
					    <%for(Employee emp:emplist){ %>
					    <option value="<%=emp.getEmpNo() %>"><%=emp.getEmpName() %></option>
					    <%} %>
					    </select>
				    </div>
			    
				    <div class="col-md-2">
					    <div class="group-form">
						    <select class="form-control  selectpicker" required="required" name="month"  id="month1" title="Select Type">
						    <option value="LOB">Opening</option>
						    <option value="January">Jan</option>
						    <option value="July">Jul</option>
						    </select>
					    </div>
				    </div>
				    <div class="col-md-2">
					    <div class="group-form">
					 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					    <input class="form-control  form-control" type="text" id="year1"  name="Year">
					    </div>
				    </div>
				    <div class="col-md-2">
					    <input type="button" value="Preview Credit" class="btn  btn-sm misc1-btn" onclick="leaveCredit()" style="margin-top: 3px;">
					</div>
				</div>
    		</form>
   		</div>
   		
   		<div class="col-md-4">
   			<form action="LeaveCredit.htm" method="POST" name="myfrm">
			    <div class="row justify-content-end">

				    <div class="col-md-3">
					    <div class="group-form">
					    <select class="form-control  selectpicker" required="required" name="month"  title="Select" data-live-search="true">
					    <option value="January" <%if("January".equalsIgnoreCase(month)){ %> selected="selected" <%} %>>Jan</option>
					    <option value="July" <%if("July".equalsIgnoreCase(month)){ %> selected="selected" <%} %>>Jul</option>
					    </select>
					    
					    </div>
				    </div>
				    <div class="col-md-3">
					    <div class="group-form">
						 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						    <input class="form-control  form-control" type="text" id="year"  value="<%=year %>" name="Year">
					    </div>
				    </div>
				    <div class="col-md-	1">
				    	<input type="submit" value="SUBMIT" class="btn btn-success btn-sm submit-btn" style="margin-top: 3px;">
				    </div>
			    </div>
		   </form>
   		</div>
   	
   	</div>
   
   
 <%--   <form action="CreditPreview.htm" method="POST" id="preview">
	    <div class="row" style="margin-top: 8px;">
		    <div class="col-md-2">
		    
		    </div>
		    <div class="col-md-1" style="margin-top: 4px;font-weight: bold;">
		             Leave Credit :
		    </div>
		    <div class="col-md-4">
			    <select class="form-control  selectpicker" required="required" name="empNo" title="Select Employee" data-live-search="ture" id="empNo">
			    <option value="A">All</option>
			    <%for(Employee emp:emplist){ %>
			    <option value="<%=emp.getEmpNo() %>"><%=emp.getEmpName() %></option>
			    <%} %>
			    </select>
		    </div>
	    
		    <div class="col-md-1">
			    <div class="group-form">
				    <select class="form-control  selectpicker" required="required" name="month"  id="month1" title="Select Type">
				    <option value="LOB">Opening</option>
				    <option value="January">Jan</option>
				    <option value="July">Jul</option>
				    </select>
			    </div>
		    </div>
		    <div class="col-md-1">
			    <div class="group-form">
			 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			    <input class="form-control  form-control" type="text" id="year1"  name="Year">
			    </div>
		    </div>
		    <div class="col-md-1">
			    <input type="button" value="Preview Credit" class="btn btn-success btn-sm" onclick="leaveCredit()" style="margin-top: 3px;">
			</div>
		</div>
    </form> --%>
   
   
   
   
   
   
   
   
	    <div class="row" style="margin-top:7px;"> 
	    <div class="col-md-12">
	 <div class="table-responsive">
		<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
	  <thead>
	  <tr>
	  <th>SN</th>
	  <th>Employee</th>
	  <th>CL</th>
	  <th>EL</th>
	  <th>HPL</th>
	  <th>CML</th>
	  <th>RH</th>
	  <th>Month</th>
	  <th>Year</th>
	  <th>Action</th>
	  
	  </tr>
	  </thead>
	  <tbody>
	  <%int count=1;
	  for(Object[] hlo :clist){ %>
	  <tr>
	  <td style="width: 100px;text-align: center;"><%=count %></td> 
	  <td style="width: 30%;text-align: center;"><%=hlo[1] %>,<%=hlo[2] %></td>
	  <td style="text-align: center;width: 100px;"><%=hlo[3] %></td>
	  <td style="text-align: center;width: 100px;"><%=hlo[4] %></td>
	   <td style="text-align: center;width: 100px;"><%=hlo[5] %></td>
	    <td style="text-align: center;width: 100px;"><%=hlo[6] %></td>
	     <td style="text-align: center;width: 100px;"><%=hlo[7] %></td>
	      <td style="text-align: center;width: 100px;"><%=hlo[8] %></td>
	       <td style="text-align: center;width: 100px;"><%=hlo[9] %></td>
	        <td style="text-align: center;width: 100px;">
	             
	         	 <button type="button" class="btn edit-btn btn-sm" name="sub" value="edit" onclick="leaveCreditEdit('<%=hlo[0] %>')"  >EDIT</button>
	        
	         </td>
	  
	  
	  
	  </tr>
	  <%count++;} %>
	  </tbody>
	   </table>
	   
	   </div>
	  


	   </div>
	   </div>
	<%-- <form action="CreditPreview.htm" method="POST" id="preview">
	    <div class="row" style="margin-top: 8px;">
		    <div class="col-md-2">
		    
		    </div>
		    <div class="col-md-1" style="margin-top: 4px;font-weight: bold;">
		             Leave Credit :
		    </div>
		    <div class="col-md-4">
			    <select class="form-control  selectpicker" required="required" name="empNo" title="Select Employee" data-live-search="ture" id="empNo">
			    <option value="A">All</option>
			    <%for(Employee emp:emplist){ %>
			    <option value="<%=emp.getEmpNo() %>"><%=emp.getEmpName() %></option>
			    <%} %>
			    </select>
		    </div>
	    
		    <div class="col-md-1">
			    <div class="group-form">
				    <select class="form-control  selectpicker" required="required" name="month"  id="month1" title="Select Type">
				    <option value="LOB">Opening</option>
				    <option value="January">Jan</option>
				    <option value="July">Jul</option>
				    </select>
			    </div>
		    </div>
		    <div class="col-md-1">
			    <div class="group-form">
			 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			    <input class="form-control  form-control" type="text" id="year1"  name="Year">
			    </div>
		    </div>
		    <div class="col-md-1">
			    <input type="button" value="Preview Credit" class="btn btn-success btn-sm" onclick="leaveCredit()" style="margin-top: 3px;">
			</div>
		</div>
    </form> --%>
    
   </div>
   </div>

 
 <div class="modal fade my-modal" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
 	  <div class="modal-dialog modal-dialog-centered" role="document">
 	    <div class="modal-content">
 	      <div class="modal-header">
 	        <h5 class="modal-title" id="exampleModalLongTitle"></h5>
  	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
  	          <span aria-hidden="true">&times;</span>
  	        </button>
  	      </div>
  	      <div class="modal-body">
  	      
  	      	<form action="AddUpdateCredit.htm" method="POST">
  	      		<div class="row">
  	      		    <div class="col-md-1">
  	      		    </div>
  	      		    <div class="col-md-2">
  	      		    CL:<br>
  	      		    <input class="form-control" type="number" name="cl" id="cl"  required="required"  min="0"  max="12" step='0.1'>
  	      		    </div>
  	      			<div class="col-md-2">
                      EL:<br>
  	      		    <input class="form-control" type="number" name="el"  id="el"  required="required"  min="0"  max="300">
  	      		    </div>
  	      		    <div class="col-md-2">
                      HPL:<br>
  	      		    <input class="form-control" type="number" name="hpl" id="hpl"   required="required"  min="0"  max="999">
  	      		    </div>
  	      		    <div class="col-md-2">
                      CML:<br>
  	      		    <input class="form-control" type="number" name="cml"  id="cml"  required="required"  min="0"  max="499">
  	      		    </div>
  	      		    <div class="col-md-2">
                      RH:<br>
  	      		    <input class="form-control" type="number" name="rh" id="rh"  required="required"  min="0"  max="2">
  	      		    </div>
  	      			</div>
  	      			<br>
  	      			<div class="row">
  	      		    <div class="col-md-2" id="div">
  	      		    </div>
  	      		    <div class="col-md-2" id="CCL">
  	      		    CCL:<br>
  	      		    <input class="form-control" type="number" name="ccl" id="ccl"  required="required"  min="0" value="0"  max="730">
  	      		    </div>
  	      			<div class="col-md-2">
                      SL:<br>
  	      		    <input class="form-control" type="number" name="sl"  id="sl"  required="required"  min="0" value="0"  max="730">
  	      		    </div>
  	      		    <div class="col-md-2" id="F">
                    ML:<br>
  	      		    <input class="form-control" type="number" name="ml"  id="ml"  required="required"  min="0" value="0"  max="180">
  	      		    </div>
  	      		    <div class="col-md-2" id="M">
  	      		    PL:<br>
  	      		    <input class="form-control" type="number" name="pl" id="pl"  required="required"  min="0" value="0"  max="30">
  	      		    </div>
  	      		    </div>
  	      			
  	      		<br>
  	      		<div align="center">
  	      			<input type="submit" class="btn btn-primary btn-sm submit " id="sub" value="Submit" name="sub"  onclick="return confirm('Are You Sure To Submit ?')" > 
  	      		</div>
  	      		<br>


  	      		<input type="hidden" name="type" value="" id="type"/>
                <input type="hidden" name="EmpId" value="" id="EmpId"/>
                <input type="hidden" name="RegId" value="" id="RegId"/>
                <input type="hidden" name="mnth" value="" id="mnth"/>
                <input type="hidden" name="yr" value="" id="yr"/>
  	      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  	      	</form>
  	      		
  	      		
  	      </div>
  	      <div class="modal-footer">
  	      </div>
  	    </div>
  	  </div>
	</div>


<script type="text/javascript">
function leaveCredit(){
	    event.preventDefault();
	    var  year=$('#year1').val();
	    var  month=$('#month1').val();
	    var  id=$('#empNo').val();
	    if(year!=''&&month!='' &&'A'!=id&&id!=''){    	
	    	$('#exampleModalCenter').modal('toggle');

	    	$.ajax({
	  			
	  			type : "GET",
	  			url : "GetCreditDetails.htm",
	  			data : {
	  				
	  				month: month,
	  				year:year,
	  				empNo:id
	  				
	  			},
	  			datatype : 'json',
	  			success : function(result) {
	  			var result = JSON.parse(result);
	  			var consultVals= Object.keys(result).map(function(e){
	 			return result[e]
	 			})
	 			

	  			if('Y'== consultVals[0][9]){
	  			$("#cl").val(consultVals[0][8]);
	  			}else if('N'== consultVals[0][9]){
		  			$("#cl").val(consultVals[0][3]);
		  			}
	  			$("#el").val(consultVals[0][4]);
	  			$("#hpl").val(consultVals[0][5]);
	  			$("#cml").val(consultVals[0][6]);
	  			$("#rh").val(consultVals[0][7]);
	  			$("#ccl").val(consultVals[0][10]);
	  			$("#sl").val(consultVals[0][11]);
	  			$("#mnth").val(month);
	  			$("#yr").val(year);
	  			if('C'== consultVals[0][12]){
		  			$("#sub").val('Credit');
		 			$("#exampleModalLongTitle").html(consultVals[0][1]+', '+consultVals[0][2]+'   Leave Credit For '+month+' '+year);
		 			$("#EmpId").val(consultVals[0][0]);
					$("#type").val('C');
		  		}else if('U'== consultVals[0][12]){
			  	    $("#sub").val('Update');
		 			$("#exampleModalLongTitle").html(consultVals[0][1]+', '+consultVals[0][2]+'  Already   Leave  Credited For '+month+' '+year);
		 			$("#RegId").val(consultVals[0][13]);
		 			$("#EmpId").val(consultVals[0][0]);
					$("#type").val('U');
			  }else if('OC'== consultVals[0][12]){
		  			$("#sub").val('Credit');
		 			$("#exampleModalLongTitle").html(consultVals[0][1]+', '+consultVals[0][2]+'   Leave Credit For Leave Opening Balance '+year);
		 			$("#EmpId").val(consultVals[0][0]);
					$("#type").val('C');
		  		}else if('OU'== consultVals[0][12]){
			  	    $("#sub").val('Update');
		 			$("#exampleModalLongTitle").html(consultVals[0][1]+', '+consultVals[0][2]+'  Already   Leave  Credited For Leave Opening Balance '+month+' '+year);
		 			$("#RegId").val(consultVals[0][13]);
		 			$("#EmpId").val(consultVals[0][0]);
					$("#type").val('U');
			  }
	  			
	  			if('M'== consultVals[0][14]){
		  			$("#CCL").hide();
		  			$("#F").hide();
		  			$("#M").show();
		 			$("#pl").val(consultVals[0][16]);
		 			$("#ml").val(consultVals[0][15]);
		 			 $('#div').removeClass().addClass('col-md-4');
		  		}else if('F'== consultVals[0][14]){
		  			$("#CCL").show();
		  			$("#F").show();
		  			$("#M").hide();
		 			$("#pl").val(consultVals[0][16]);
		 			$("#ml").val(consultVals[0][15]);
		 			$('#div').removeClass().addClass('col-md-3');
			    }
	 			$('[data-toggle="tooltip"]').tooltip()
	 				
	 				
	  			}
	  		});
	    }else if('A'==id && 'LOB'!=month && year!=''){
	    		$("#preview").submit();
	   	
	    }else if('A'==id && 'LOB'==month){
	    	alert("You Can't Credit Opening balance For All, Please Select One Employee.");
	    }else{	
	    	alert("Please Select Options.");
	    }
		
		
}		
function leaveCreditEdit(id){
	$('#exampleModalCenter').modal('toggle');
	$.ajax({
			
			type : "GET",
			url : "GetCreditByEmp.htm",
			data : {
			
				registerid:id
				
			},
			datatype : 'json',
			success : function(result) {
			var result = JSON.parse(result);
			var consultVals= Object.keys(result).map(function(e){
			return result[e]
			})
			


  			$("#cl").val(consultVals[0][3]);
			$("#el").val(consultVals[0][4]);
			$("#hpl").val(consultVals[0][5]);
			$("#cml").val(consultVals[0][6]);
			$("#rh").val(consultVals[0][7]);
			$("#ccl").val(consultVals[0][10]);
			$("#sl").val(consultVals[0][11]);
			$("#RegId").val(id);
			$("#EmpId").val(consultVals[0][0]);
			$("#mnth").val(consultVals[0][12]);
  			$("#yr").val(consultVals[0][13]);
			$("#type").val('U');
	  	    $("#sub").val('Update');
 			$("#exampleModalLongTitle").html(consultVals[0][1]+', '+consultVals[0][2]+'     Leave  Credited For '+consultVals[0][12]+' '+consultVals[0][13]);
 			if('M'== consultVals[0][14]){
	  			$("#CCL").hide();
	  			$("#F").hide();
	  			$("#M").show();
	 			$("#pl").val(consultVals[0][16]);
	 			$("#ml").val(consultVals[0][15]);
	 			 $('#div').removeClass().addClass('col-md-4');
	  		}else if('F'== consultVals[0][14]){
	  			$("#CCL").show();
	  			$("#F").show();
	  			$("#M").hide();
	 			$("#pl").val(consultVals[0][16]);
	 			$("#ml").val(consultVals[0][15]);
	 			$('#div').removeClass().addClass('col-md-3');
		    }
			$('[data-toggle="tooltip"]').tooltip()
				
				
			}
		});
}

function Edit(myfrm){
	
	 var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	}
	 
	var cnf=confirm("Are U Sure To Edit!");
	  if(cnf){
	
		 
	
		  return true;
	  }
	  
	  
	  else{
		  event.preventDefault();
			return false;
			}
			
	}
function Delete(myfrm){
	

	var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	
	}
	  var cnf=confirm("Are U Sure To Cancel!");
	  if(cnf){
	
	return true;
	
	}
	  else{
		  event.preventDefault();
			return false;
			}
	
	}

$('#year').datepicker({
	 format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    autoclose: true,
	    todayHighlight: true
});
$('#year1').datepicker({
	 format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    autoclose: true,
	    todayHighlight: true
});
$('#year1').datepicker("setDate", new Date());
</script>
</body>
</html>