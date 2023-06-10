<%@page import="com.vts.ems.condone.model.SEIPCondoneAttach"%>
<%@page import="com.vts.ems.condone.model.SEIPCondoneType"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.vts.ems.master.model.LabMaster"%>
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

.subject-input {
  width : 100%;
  border: 0;
  outline: 0;
  margin-top : 15px;
  background: transparent;
  border-bottom: 1px solid black;
}

</style>
</head>
<body>


<div class="card-header page-top " >
		<div class="row">
			<div class="col-md-6">
				<h5>Condonation Edit</h5>
			</div>
				<div class="col-md-6 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="EmpCondonation.htm"> Condonation List </a></li>
						<li class="breadcrumb-item active " aria-current="page">Condonation Add</li>
					</ol>
				</div>
			</div>
	</div>	

<% String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
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
<%
List<SEIPCondoneType> CondoneTypesList=(List<SEIPCondoneType>)request.getAttribute("CondoneTypesList");
String LabLogo = (String)request.getAttribute("LabLogo");
String EmpNo = (String)request.getAttribute("EmpNo");

LabMaster labMaster=(LabMaster)request.getAttribute("LabMaster");
Object[] EmployeeInfo=(Object[])request.getAttribute("EmployeeInfo");

SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

Object[] condoneData = (Object[])request.getAttribute("condoneData");
List<SEIPCondoneAttach> CondoneAttachList=(List<SEIPCondoneAttach>)request.getAttribute("CondoneAttachList");
%>

<div class="page card dashboard-card" style="background: transparent;">
   <div class="card-body" >
		<%List<String> Statuses = List.of("INI", "SDG", "SCE"); %>
          	
          	<div class="card" style="padding-top:0px;margin-top: -15px;">
				<div class="card-body main-card " style="padding-top:0px;"  align="center">
					<form action="#" method="POST" id="CondoneForm" >
						<table style="border: 0px; width: 100%">
							<tr>
								<td style="width: 20%; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
								<td style="width: 50%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h3> CONDONATION FORM </h3> </td>
								<td style="width: 30%; height: 75px;border: 0;vertical-align: bottom;">
									<b>Type : </b>
									<select class="form-control" name="condone_type" style="width: 80%; float: right;">
										<%for(SEIPCondoneType type : CondoneTypesList){ %>
											<option value="<%=type.getCondoneTypeId()%>" <%if(Integer.parseInt(condoneData[3].toString())==type.getCondoneTypeId()	){ %> selected<%} %>><%=type.getCondoneType() %></option>
										<%} %>
									</select>
								</td>
							</tr>
						</table> 
						<hr>
						<table style="border: 0px; width: 100%">
							<tr>
								<td>TO,</td>
							</tr>
							<tr>
								<td>The CEO,</td>
							</tr>
							<tr>
								<td><%=labMaster.getLabName() %>,</td>
							</tr>
							<tr>
								<td><%=labMaster.getLabCity() %>.</td>
							</tr>
						</table>
						<br><br>
						<table style="border: 0px; width: 100%">
							<tr>
								<th>Subject : </th>
							</tr>
							<tr>
								<td>
									<input type="text" class="subject-input" name="condone_subject" value="<%=condoneData[4] %>" maxlength="255">
								</td>
							</tr>
							<tr>
								<th><br>Body : </th>
							</tr>
							<tr>
								<td colspan="2">
									<textarea class="form-control" name="condone_content" maxlength="5000" style="width: 100%;" rows="20"><%=condoneData[5] %></textarea>
								</td>
							</tr>
						</table>	
						<br><br>
						<table style="border: 0px; width: 100%">
							<tr>
								<td><%=EmployeeInfo[2] %>,</td>
							</tr>
							<tr>
								<td><%=EmployeeInfo[6] %>,</td>
							</tr>
							<tr>
								<td><%=sdf.format(ldf.parse(LocalDate.now().toString())) %></td>
							</tr>
						</table>
						
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="hidden" name="CondoneId" value="<%=condoneData[0] %>" >
			          	<div class="row" align="center">
			            	<div class="col-md-12">
			            		<button type="submit" class="btn btn-sm edit-btn" formaction="CondoneEditSubmit.htm"  formmethod="post" onclick="return confirm('Are You sure to Update');">UPDATE</button>
			            	</div>
						</div>
					</form>
					
					<hr>
					<%-- 
					<div class="row" align="left">
						<div class="col-md-6">
							<form action="#" method="POST" enctype="multipart/form-data">
								<table class="table" >
									<thead>
										<tr>
											<th style="width: 90%;">Add New Attachments</th>
											<th style="width: 10%;"><button type="button" class="btn btn-sm tbl-row-add" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone">
											<td><input type="file" class="form-control items" name="condone_attach"></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>
								</table>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="CondoneId" value="<%=condoneData[0] %>" >
					          	<div class="row" align="center">
					            	<div class="col-md-12">
					            		<button type="submit" class="btn btn-sm add-btn" formaction="CondoneAttachSubmit.htm"  formmethod="post" onclick="return confirm('Are You sure to Submit');">ADD</button>
					            	</div>
								</div>
							</form>
							
						</div>

						<div class="col-md-6">
							<form action="#" method="POST" enctype="multipart/form-data">
								<table class="table" style="table-layout: fixed;">
									<thead>
										<tr>
											<th style="width: 80%;">Attachments</th>
											<th style="width: 20%;"><button type="button" class="btn btn-sm " data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<%for(SEIPCondoneAttach attach : CondoneAttachList){ %>
											<tr>
												<td><%=attach.getOriginalFileName() %></td>
												<td >
													<button type="submit" class="btn btn-sm" name="condone_attach_id" value="<%=attach.getCondoneAttachId() %>" formaction="CondoneAttachDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download" >
														<i style="color: #019267" class="fa-solid fa-download"></i>
													</button>
													<button type="submit" class="btn btn-sm" name="condone_attach_id" value="<%=attach.getCondoneAttachId() %>" formaction="CondoneAttachDelete.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Delete" onclick="return confirm('Are You sure to Delete');">
														<i class="fa-solid fa-trash-can " style="color: red"></i>
													</button>
												</td>
											</tr>
										<%} %>
									</tbody>
								</table>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="CondoneId" value="<%=condoneData[0] %>" >
							</form>
							
						</div>
					</div>
					
					 --%>
				</div>
			</div>
            
   </div>
</div>

<script type="text/javascript">

var count=1;

$("table").on('click','.tbl-row-add' ,function() 
{
   	var $tr = $('.tr_clone').last('.tr_clone');
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	$clone.find(".items").val("").end();

	count++;

  $('[data-toggle="tooltip"]').tooltip('dispose');
  $('[data-toggle="tooltip"]').tooltip({
		 trigger : 'hover',
		 html : true,
		 boundary: 'window'
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

</script>

</body>
</html>