<%@page import="java.util.List"%>
<%@ page import="java.util.*, java.text.DateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.text.SimpleDateFormat"%> 
<%@page import="java.time.LocalDate"%>
<%@ page import="java.util.Date" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

	  <% String ValidTill =(String)request.getAttribute("ValidTill");		
	
	  String CurrentDate =(String)request.getAttribute("CurrentDate");
	  
	  SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	List<Object[]> EmployeeList =(List<Object[]>)request.getAttribute("EmployeeList");
	List<Object[]> ContractEmployeeList =(List<Object[]>)request.getAttribute("ContractEmployeeList");
	List<Object[]> HelpDeskEmpList =(List<Object[]>)request.getAttribute("HelpDeskEmployeeList");
	
	 
		
	
	%>
	
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5></h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
							<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active" aria-current="page">Employee Employee</li>
				</ol>
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
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
		<%} %>
		
		<div class="row">	
		<div class="card" style="width:48%;margin-left:2%"; >
				 <div class="card-body main-card "  > 
					<form action="EmployeeAddSubmits.htm" method="post" autocomplete="off" id="" >
					
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="row">
							<div class="col-6" >
									<b>Employee Name :</b><span class="mandatory" style="color: red;">*</span>
							        <select class="form-control select2"  name="Employee" required="required" id="">
							        <option value="" disabled="disabled" selected="selected"
					                     hidden="true">Select</option>
								
								    <%for(Object[] emp :EmployeeList) {%>
								    
								    <option value="<%=emp[0]%>"><%=emp[1] %></option>
								    <%} %>
								    	
							        
								
							</select>
							
								</div>
								
								<div class="col-4">
									<b>Valid Till :</b><span class="mandatory"	style="color: red;">*</span>
							<input type=text  class="form-control" name="ValidTill" id="validtill" <%if(ValidTill!=null){%>
								value="<%=ValidTill%>" <%} %>>
								
								  
							
								</div>
						</div>
						<br>
						<div class="row justify-content-center" style="margin-right:-15px;margin-top:-14px;">
							<button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Submit ?')" >SUBMIT</button>  
						</div>
					</form>
				 </div>
			 </div>
				
			
			<div class="card" style="width:48%;" >
				  <div class="card-body main-card " > 
				<form action="ContractEmployeeAddSubmit.htm" method="post" autocomplete="off" id="" >
					
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				 <div class="row" > 
							<div class="col-6"  style="margin-left:0px;margin-top:0px;">
									<b>ContractEmployee Name :</b><span class="mandatory" style="color: red;">*</span>
							        <select class="form-control select2"  name="Caseworker" required="required" id="">
							        <option value="" disabled="disabled" selected="selected"
					                     hidden="true">Select</option>
								
								    <%for(Object[] cw :ContractEmployeeList) {%>
								    
								    <option value="<%=cw[0]%>"><%=cw[1] %></option>
								    <%} %>
								    	
							        
								
							</select>
							
								</div>
								
								<div class="col-4" style="margin-left:0px;margin-top:0px;">
									<b>Valid Till :</b><span class="mandatory"	style="color: red;">*</span>
							<input type=text  class="form-control" name="ValidTill" id="validtill1" <%if(ValidTill!=null){%>
								value="<%=ValidTill%>"<%} %>>
								
								  
							
								</div>
						 </div> 
						<br>
						<div class="row justify-content-center " style="margin-right:-15px;margin-top:-12px;">
							<button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Submit ?')" >SUBMIT</button>  
						</div>
						
					</form>
			
		</div> 
		</div>
		</div>
		
		<div class="card-body main-card" style=" max-height: 30rem; overflow-y:auto;" >

			<form action="#" method="POST" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="table-responsive"      >
					<table
						class="table table-hover  table-striped table-condensed table-bordered table-fixed"
						id="myTable" style="width:100% ;" >
						<thead>
							<tr>
								<th style="width: 4%">SN&nbsp;</th>
								<th style="width: 7%">Employee Type</th>
								<th style="width: 10%">Employee Name</th>
								<th style="width: 10%">Valid Till</th>
								<th style="width: 10%">Action</th>
							</tr>
						</thead>
						<tbody>
						   <%int count=0;
							 String Permanent="Permanent";
							String Contract="Contract";
							for(Object[] obj:HelpDeskEmpList){
								Date date1=sdf.parse(sdf.format(obj[4]));
								Date date2=sdf.parse(sdf.format(new Date()));%>
							<tr>
							    <td style="text-align: center;"><%=++count %></td>
								<td style="text-align: left;"><% if(obj[2].toString().equalsIgnoreCase("P")){%><%=Permanent%><%} else if(obj[2].toString().equalsIgnoreCase("C")){%><%=Contract %><%} %></td>
								<td style="text-align: left;"><%=obj[3]%></td>
								<td style="text-align: center;color:red;"><%if(date1.compareTo(date2)< 0){%><%=rdf.format(sdf.parse(obj[4].toString()))%><%} else{%><span style="color:black"><%=rdf.format(sdf.parse(obj[4].toString()))%></span><%} %></td> 
								<td style="text-align: center;">
								
								<input type="hidden" name="Empname<%=obj[0]%>"  id="Empname<%=obj[0]%>"   value="<%=obj[3] %>" >
								<input type="hidden" name="Emptype<%=obj[0]%>"   id="Emptype<%=obj[0]%>"   value="<%=obj[2] %>" >
								<input type="hidden" name="ValidTill<%=obj[0]%>"   id="ValidTill<%=obj[0]%>"  value="<%=rdf.format(sdf.parse(obj[4].toString())) %>" >
								<button type="button" class="btn btn-sm edit-btn" name="HelpDeskEmpId" value="<%=obj[0]%>" onclick="openeditmodal('<%=obj[0] %>')" data-toggle="tooltip" data-placement="top" data-original-title="Edit">
								 <i class="fa fa-pencil"></i></button>
								<button type="submit" class="btn btn-sm " name="HelpDeskEmpId" value="<%=obj[0]%>" formaction="EmployeeDelete.htm" onclick="return confirm('Are You Sure to Delete?');"  formmethod="post" data-toggle="tooltip" data-placement="top" data-original-title="Delete">
								<i class="fa-solid fa-trash-can " style="color: red"></i></button>
												
								</td>
							</tr>
							<%} %>
						</tbody>
					</table>
                       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</div>
          </form>
      </div>
</div>
           <div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="my-edit-modal">
		   <div class="modal-dialog modal-lg" role="document" style="width: 42% ;height: 60% ;">
			<div class="modal-content">
					    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Employee Edit</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				<div class="modal-body" align="center" style="margin-top:-4px;">
					<form action="EmployeeUpdate.htm" method="post" autocomplete="off"  >
						<table style="width: 100%;">
							<tr>
								<th style="padding: 5px;" >Employee Name :</th>
								<td style="padding: 5px;" id="empname"></td>
								<th style="padding: 5px">Employee Type :</th>
								<td style="padding: 5px;" id="emptype"></td>
							</tr>
							<tr>
								<th style="padding: 5px;" >Valid Till: <span class="mandatory"	style="color: red;">*</span></th>
								<td style="padding: 5px;" >
								<input type="text" class="form-control" name="ValidTill" id="validupto" style="width:50%;">
								</td>
							</tr>
						</table>
						<div align="center" style="margin-top:10px;">
								<button type="submit"  class="btn btn-sm submit-btn"  name="action"  value="submit" >UPDATE</button>
						</div>
						 <input type="hidden"  name="HelpDeskEmpId" id="HelpDeskEmpId" value=""> 
						        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
				</div>
			</div>
		</div>
	</div>	
</body>

<script type="text/javascript">
$('#validtill').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	 
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

	$('#validtill1').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
	
function openeditmodal(HelpDeskEmpId){

   $("#HelpDeskEmpId").val(HelpDeskEmpId);

   $('#empname').html($('#Empname'+HelpDeskEmpId).val())
   
   var type=$('#Emptype'+HelpDeskEmpId).val()
   
   if(type=="P")
    {
	   P="Permanent"
	   $('#emptype').html(P);
	}
   if(type=="C")
   {
	   C="Contract"
	   $('#emptype').html(C);
   }
   
   $('#validupto').val($('#ValidTill'+HelpDeskEmpId).val())
 
		console.log();
   $('#my-edit-modal').modal('toggle'); 
	}

</script>
<script type="text/javascript">
$( document ).ready(function() {
$('#validupto').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
})

</script>
</html>