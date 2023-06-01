<%@page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<style type="text/css">

.trup
		{
			padding:5px 10px 0px 10px ;			
			border-top-left-radius : 5px; 
			border-top-right-radius: 5px;
			font-size: 14px;
			font-weight: 600;
			
			
		}
		
		.trdown
		{
			padding:0px 10px 5px 10px ;			
			border-bottom-left-radius : 5px; 
			border-bottom-right-radius: 5px;
			font-size: 14px;
			font-weight: 600;
		}

body{

 overflow-x: hidden;
  overflow-y: hidden;

}

</style>
</head>
<body>

 <%	
	
    SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
    Object[] CeoName = (Object[])request.getAttribute("CeoName");
    Object[] PandAEmpName = (Object[])request.getAttribute("PandAEmpName");
	Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
	Object[] DivisionHeadName = (Object[])request.getAttribute("DivisionHeadName");
	Object[] GroupHeadName = (Object[])request.getAttribute("GroupHeadName");
	
	String CEO = (String)request.getAttribute("CEOEmpNos");
	List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
	List<String> DHs = (List<String>)request.getAttribute("DivisionHeadEmpNos");
	List<String> GHs = (List<String>)request.getAttribute("GroupHeadEmpNos");
	
	Object[] empData=(Object[])request.getAttribute("EmpData");
	
	List<Object[]> ExamIntimationList=(List<Object[]>)request.getAttribute("ExamIntimationDetails");
	
	Employee emp=(Employee)request.getAttribute("EmployeeD");
	
	List<String> toUserStatus  = Arrays.asList("INI","RDG","RPA");
	
   %>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-5">
				<h5>Intimation For Exam <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active" aria-current="page">Intimation For Exam
						</li>
				</ol>
			</div>

		</div>
		
		</div>
	
	
	<!-- <div class="page card dashboard-card"> -->
		<div align="center">
		<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){ %>
		
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
		</div>
		

   <div class="card">					
		<div class="card-body">
		<!-- <div class="card-body main-card"> -->

			<form action="#" method="POST" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				 <div class="table-responsive"> 
					 	<table class="table table-bordered table-hover table-striped table-condensed"  id="table"> 
						<thead>
							<tr>
								<th style="width: 6%">SN</th>
								<th style="width: 10%">Exam Name</th>
								<th style="width: 10%">Probable Date </th>
							    <th style="width: 10%">Initiated Date</th> 
								<th style="width: 15%">Status</th>
								<th style="width: 8%">Action</th>
							</tr>
						</thead>
						<tbody>
						
						<% 
						int count=0;
						
						for(Object[] obj: ExamIntimationList) {%>
						
						<tr>
								<td style="text-align: center;"><%=++count%></td>
								<td style="text-align: center;"><%=obj[1] %></td>
								<td style="text-align: center;"><%=rdf.format(sdf.parse(obj[2].toString())) %></td>
								<td style="text-align: center;"><%=rdf.format(sdf.parse(obj[3].toString())) %></td> 
								<td style="text-align: center;">
									<%if(obj[5]!=null){%>
								  
								 	<%if(obj[7]!=null && obj[7].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="IntimationTransactionStatus.htm" formmethod="GET"  name="ExamId" value="<%=obj[0] %>"   data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: blue; font-weight: 600;" formtarget="_blank">
								    		&nbsp;  Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
								    	
							    	<%} 
							    	else{%>
							    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="IntimationTransactionStatus.htm" value="<%=obj[0] %>" name="ExamId"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: purple; font-weight: 600;" formtarget="_blank">
								    		&nbsp;<%=obj[4] %><i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    		
							    	
							    	<%} %>  
								 
								<%} %>
								
								
								</td>
								<td style="text-align: center;">
								 
								 <input type="hidden" name="ExamName<%=obj[0]%>"   id="ExamName<%=obj[0]%>"   value="<%=obj[1] %>" >
								<input type="hidden" name="probabledate<%=obj[0]%>"   id="probabledate<%=obj[0]%>"  value="<%=rdf.format(sdf.parse(obj[2].toString())) %>" >  
								
								<%if(toUserStatus.contains(obj[8].toString()) ){ %> 
								
							   <button type="button" class="btn btn-sm " name="ExamId" value="<%=obj[0]%>" onclick="openeditmodal('<%=obj[0] %>')" data-toggle="tooltip" data-placement="top" data-original-title="Edit">
								 <i class="fa fa-edit" style="color:#FC4F00;"></i></button>
								 
								 <%} %>
								  <button type="submit" class="btn btn-sm " name="ExamId" value="<%=obj[0]%>"   formaction="ExamIntimationPreview.htm" formmethod="POST" data-toggle="tooltip" title="" data-original-title="View Details">
								    <i class="fa fa-eye " style="color: black;"></i>
							    </button>
							    
							    <button type="submit" class="btn btn-sm" name="ExamId" value="<%=obj[0] %>"  formaction="ExamIntimationLetterDownload.htm"  formmethod="GET" formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="Download">
								  <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>  
								 
							    </td>
						</tr>
						<%} %>	
					
                       </tbody>
					</table>

					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					
					
				 </div>

				<div class="row text-center">
					<div class="col-md-12">
						
						<button type="button" class="btn btn-sm add-btn" onclick="openmodal()">ADD</button>
						
					</div>
				</div>

			</form>
			
			<hr>
			
             <div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow For Intimation of Exam</b></div>
		 	</div>
		 	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               			<tr>
	               		<%if(!CEO.equalsIgnoreCase(emp.getEmpNo()) ) {%>
	                		<td class="trup" style="background: #E8E46E;">
	                			User - <%=emp.getEmpName() %>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                	<%} %>
	               		
	               		<%if(DGMEmpName!=null && !DGMs.contains(emp.getEmpNo()) && !PandAs.contains(emp.getEmpNo()) && !CEO.contains(emp.getEmpNo()) ){ %>
	                		<td class="trup" style="background: #FBC7F7;">
	                			DGM -  <%=DGMEmpName[1] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<% if(PandAEmpName!=null  && !PandAs.contains(emp.getEmpNo()) && !CEO.contains(emp.getEmpNo()) ){ %>
	                		<td class="trup" style="background: #BCAAA3;" >
	                			P&A - <%=PandAEmpName[1] %>
	                		</td>
	                		
	                	<%} %>
	               	</tr>		   
		                	
	              </table>			             
			  </div>


		</div>

	</div>
	
	
	 <div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="my-add-modal">
		   <div class="modal-dialog modal-lg" role="document" style="width:40%;height: 60% ;">
			<div class="modal-content">
					    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Intimation For Exam Add</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				<div class="modal-body" align="center" style="margin-top:-4px;">
					<form action="ExamDetailsAddSubmit.htm" method="post" autocomplete="off"  >
						<table style="width: 70%;">
							<tr>
								<th style="padding: 5px;" >Exam Name : <span class="mandatory"	style="color: red;">*</span></th>
								<td style="padding: 5px;" >
								<input type="text" class="form-control" name="ExamName"  style="width:100%;" required>
								</td>
							</tr>
							
							 <tr>
								<th style="padding: 5px;" >Probable Date : <span class="mandatory"	style="color: red;">*</span></th>
								<td style="padding: 5px;" >
								<input type="text" class="form-control" name="ProbableDate" id="dateadd" style="width:50%;">
								</td>
							</tr>
						</table>
						
						<div align="center" style="margin-top:10px;">
								<button type="submit"  class="btn btn-sm submit-btn"  name="action"  value="submit"  onclick="return confirm ('Are You Sure To Submit')">Submit</button>
						</div>
						
						 <input type="hidden"  name="" id="" value=""> 
						        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ---------------------------------------------------------------------------------------------------------------------------------- -->
	
	 <div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="my-edit-modal">
		   <div class="modal-dialog modal-lg" role="document" style="width:40%;height: 60% ;">
			<div class="modal-content">
					    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Intimation For Exam Edit</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				<div class="modal-body" align="center" style="margin-top:-4px;">
					<form action="ExamDetailsEditSubmit.htm" method="post" autocomplete="off"  >
						<table style="width: 70%;">
							<tr>
								<th style="padding: 5px;" >Exam Name : <span class="mandatory"	style="color: red;">*</span></th>
								<td style="padding: 5px;" >
								<input type="text" class="form-control" name="ExamName" id="examname" style="width:100%;">
								</td>
							</tr>
							
							 <tr>
								<th style="padding: 5px;" >"Probable Date : <span class="mandatory"	style="color: red;">*</span></th>
								<td style="padding: 5px;" >
								<input type="text" class="form-control" name="ProbableDate" id="dateedit" style="width:50%;">
								</td>
							</tr>
						</table>
						
						<div align="center" style="margin-top:10px;">
								<button type="submit"  class="btn btn-sm submit-btn"  name="action"  value="submit"  onclick="return confirm ('Are You Sure To Submit')" >Submit</button>
						</div>
						
						 <input type="hidden"  name="ExamId" id="ExamId" value=""> 
						        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
				</div>
			</div>
		</div>
	</div>
		
<!-- </div> -->



			       
<script type="text/javascript">
	

	   	   
	   



function openmodal(){
	
	$('#my-add-modal').modal('toggle'); 
	
	
}

function openeditmodal(ExamId){
	
	
	 $("#ExamId").val(ExamId);
	 
	 console.log("id---"+ExamId);
	
	 $('#examname').val($('#ExamName'+ExamId).val())

	   $('#dateedit').val($('#probabledate'+ExamId).val())
	 
	
	$('#my-edit-modal').modal('toggle'); 
	
}


</script>

<script type="text/javascript">
$("#table").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});


$( document ).ready(function() {
$('#dateadd').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
})

$( document ).ready(function() {
$('#dateedit').daterangepicker({
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

</body>
</html>