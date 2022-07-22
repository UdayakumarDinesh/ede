<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>   
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CHSS</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">


input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

input[type=number]{
    -moz-appearance: textfield;
}


b{
	font-weight: 600;
}
p
{
	text-align: justify !important;
  	text-justify: inter-word;
}
p,td,th
{
  word-wrap: break-word;
  word-break: normal ;
}

th,td
{
	border : 1px solid black;
	padding: 5px;
}


.nobordertd
{
	border: 0px;
	margin: 0px;
	padding : 0px;
}

</style>
<meta charset="ISO-8859-1">
<title>CHSS FORM - 3</title>
</head>
<%

	String labcode=(String) session.getAttribute("LabCode");

	List<Object[]> ExcMemberDetails = (List<Object[]>)request.getAttribute("ExcMemberDetails");
	List<Object[]> FamilymemDropdown = (List<Object[]>)request.getAttribute("FamilymemDropdown");
	List<Object[]> relationtypes = (List<Object[]>)request.getAttribute("relationtypes");
	Object[] empdetails = (Object[])request.getAttribute("empdetails");
	Object[] employeeResAddr = (Object[])request.getAttribute("employeeResAddr");
	Object[] formdetails = (Object[])request.getAttribute("formdetails");
	
	String LabLogo = (String)request.getAttribute("LabLogo");
	
	String isapproval = (String)request.getAttribute("isApprooval");
	
	String formid="0";
	String empid=empdetails[0].toString();
	String status="C";
	if(formdetails!=null)
	{
		formid=formdetails[0].toString();
		status=formdetails[3].toString();
	}
%>
<body >
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>CHSS FORM -3</h5>
			</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<%if(isapproval.equalsIgnoreCase("N")){ %>
						<li class="breadcrumb-item " aria-current="page"><a href="FamIncExcFwdList.htm">Include / Exclude</a></li>
						<%}else if(isapproval.equalsIgnoreCase("Y")){ %>
						<li class="breadcrumb-item " aria-current="page"><a href="FamFormsApproveList.htm">CHSS Forms Approval</a></li>
						<%} %>
						<li class="breadcrumb-item active " aria-current="page">CHSS FORM -3</li>
					</ol>
				</div>
		</div>
	</div>

	<div align="center" style="margin-top: 15px;">
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
	<div class="card dashboard-card" >
		<div style="width: 100%;">
			<div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;float:left;" ><img style="width: 80px; height: 90px; margin: 5px;"   src="data:image/png;base64,<%=LabLogo%>"></div>
			<h3 style="text-decoration: underline; float: right;margin: 15px 15px 0px 0px;">CHSS FORM - 3</h3>
		</div>	
		<div id="pageborder" align="center" style="max-width:95%;margin-top: 5px;margin-left: 10px;" >
		
		<!-- 	<div align="right">
				<h3 style="text-decoration: underline;">CHSS FORM - 3</h3>
			</div> -->
			<div align="center">
				<h3>Contributory Health Service Scheme of SITAR Society</h3>
				<span style="text-decoration: underline; font-size: 15px;"><b>Application for exclusion of names of beneficiaries</b></span>
			</div>
			
			<div align="left">
				<h3>UNIT : <%=labcode.toUpperCase() %></h3>
			</div>
			<div align="left">
				<table style="width:100%; ">
					<tr>
						<th style="width:30% " >Name</th>
						<th style="width:15% " >Emp.No.</th>
						<th style="width:20% " >Designation</th>
						<th style="width: 10% " >Level In Pay Matrix</th>
						<th style="width: 25% " >Basic Pay</th>
					</tr>
					<tr>
						<td><%=empdetails[2] %></td>
						<td><%=empdetails[1] %></td>
						<td><%=empdetails[12] %></td>
						<td><%=empdetails[9] %></td>
						<td><%=empdetails[11] %></td>
					</tr>
				</table>
				Residential Address :
					<%if(employeeResAddr!=null){ %>
						<%=employeeResAddr[1] %>
					<%} %>
				<br>
				<br><br><br>
			</div>
			<div align="left">
				<b style="text-decoration: underline; ">Particulars of family members</b><br>
				
				<table>
					<tr>
						<th style="width:5%;text-align: center; " >SN</th>
						<th style="width:25% " >Name</th>
						<th style="width:10% " >Relationship With the Employee</th>
						<th style="width: 10% " >Effective Date of Exclusion</th>
						<th style="width: 20% " >Reason for Exclusion </th>
						<th style="width: 15% " >Attachment </th>
						<%if((status.equalsIgnoreCase("C") || status.equalsIgnoreCase("R"))){ %>
							<th style="width: 10% " >
								Action  
							</th>
						<%} %>
					</tr>
					
					<%int i=0;
					ArrayList<String> addedlist = new ArrayList<String>();
					for(;i<ExcMemberDetails.size();i++)
					{
						addedlist.add(ExcMemberDetails.get(i)[0].toString());
						
						%>
					
						<tr id="show-edit-<%=ExcMemberDetails.get(i)[0] %>" style="display: none;" >
							<td style="text-align: center;" ><form action="ExclusionFormMemberEdit.htm" method="POST" enctype="multipart/form-data" autocomplete="off" id="edit-form-<%=ExcMemberDetails.get(i)[0] %>" ><%=i+1%></form></td>
							<td>
								<%-- <select class="form-control selectpicker dropdown"  form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" id="familydetailsid-<%=ExcMemberDetails.get(i)[0] %>" name="familydetailsid" onchange="changeReletion('<%=ExcMemberDetails.get(i)[0] %>')" data-dropup-auto="false" data-live-search="true" data-container="body" data-show-subtext="false" >
									<option disabled="disabled" selected="selected" value="0">Choose..</option>
									<%for(Object[] member : FamilymemDropdown){ %>
										<option value="<%=member[0]%>" <%if(ExcMemberDetails.get(i)[0].toString().equalsIgnoreCase(member[0].toString())){ %> selected <%} %> 
										data-subtext="(<%=member[4]%>)" data-relation="<%=member[4]%>" ><%=member[2]%></option>
									<%} %>
								</select> --%>
								
								<%=ExcMemberDetails.get(i)[1] %>
								
							</td>
							<td>
								<span id="mem-rel-<%=ExcMemberDetails.get(i)[0]%>"><%=ExcMemberDetails.get(i)[2] %></span>															
							</td>
							<td>
								<input form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="text" class="form-control mem-exc-date-edit" name="mem-exc-date" value="<%=DateTimeFormatUtil.SqlToRegularDate(ExcMemberDetails.get(i)[17].toString()) %>" style="width:100%;"  maxlength="10" readonly required="required">
							</td>							
							<td>
								<input form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="text" class="form-control" name="mem-comment" <%if(ExcMemberDetails.get(i)[16]!=null ){ %>value="<%=ExcMemberDetails.get(i)[16] %>" <%}else{ %>value="" <%} %> maxlength="255" required >
							</td>
							<td>
								<input form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="file" class="form-control" name="mem-attach-edit"  >
							</td>
							<td>	
								<button form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="submit" class="btn btn-sm update-btn" name="familydetailsid" value="<%=ExcMemberDetails.get(i)[0] %>"  onclick="return confirm('Are You Sure to Update ?');" title="update">
									Update
								</button>
								<button form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="button" class="btn btn-sm "  onclick="hideEdit('<%=ExcMemberDetails.get(i)[0] %>')" title="Cancel"> 
									<i class="fa-solid fa-xmark"  style="color: red;"></i>
								</button>
								<input form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="hidden" name="formid" value="<%=formid%>"/>
								<input form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="hidden" name="formmemberid" value="<%=ExcMemberDetails.get(i)[15]%>"/>
								<input form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="hidden" name="Excformid" value="<%=ExcMemberDetails.get(i)[13]%>"/>
							</td>
							
						</tr>
					
						<tr id="show-view-<%=ExcMemberDetails.get(i)[0] %>"   >
						
							<td style="text-align: center;" ><%=i+1%></td>
							<td><%=ExcMemberDetails.get(i)[1] %></td>
							<td><%=ExcMemberDetails.get(i)[2] %></td>
							<td><%=DateTimeFormatUtil.SqlToRegularDate(ExcMemberDetails.get(i)[17].toString()) %></td>							
							<td>
								<%if(ExcMemberDetails.get(i)[16]!=null){ %>
									<%=ExcMemberDetails.get(i)[16] %>
								<%}else{ %>
									-
								<%} %>
							</td>
							<td style="text-align: center;">
								<%if(ExcMemberDetails.get(i)[18]!=null){ %>
									<button form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="submit" class="btn btn-sm" name="formmemberid" value="<%=ExcMemberDetails.get(i)[15] %>"  formaction="FamIncExcAttachDownload.htm" formtarget="_blank"  data-toggle="tooltip" data-placement="top" title="Download">
										<i style="color: #019267" class="fa-solid fa-download"></i>
									</button>
								<%}%>
								
							</td>
							<%if(status.equalsIgnoreCase("C") || status.equalsIgnoreCase("R")){ %>
								<td>
									<button type="button" class="btn btn-sm "  onclick="showEdit('<%=ExcMemberDetails.get(i)[0] %>')" title="Edit"> 
										<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
									</button> 
									<button form="edit-form-<%=ExcMemberDetails.get(i)[0] %>" type="submit" class="btn btn-sm" formnovalidate="formnovalidate" name="familydetailsid" value="<%=ExcMemberDetails.get(i)[0] %>" formaction="ExcFormRemoveMember.htm"  onclick="return confirm('Are You Sure to Remove ?');"  title="Remove">
										<i class="fa-solid fa-trash-can" style="color: red;"></i>
									</button>
								</td>
							<%} %>			
						</tr>
					
					<%} %>
					
					<%if(status.equalsIgnoreCase("C") || status.equalsIgnoreCase("R")){ %>
						
						<tr>
						<td style="text-align: center;" ><form method="post" action="DepExcusionFormMemAdd.htm" enctype="multipart/form-data" autocomplete="off" id="add-form" ><%=i+1%></form></td>
						<td>
							<select class="form-control selectpicker dropdown" form="add-form" id="familydetailsid-0" name="familydetailsid" onchange="changeReletion('0')" data-dropup-auto="false" data-live-search="true" data-container="body" data-show-subtext="false" required="required" >
								<option disabled="disabled" selected="selected" value="0">Choose..</option>
								<%for(Object[] member : FamilymemDropdown){
									if(!addedlist.contains(member[0].toString())){%>
									<option value="<%=member[0]%>" data-subtext="(<%=member[4]%>)" data-relation="<%=member[4]%>" ><%=member[2]%></option>
								<%} } %>
							</select>
						</td>
						<td >
							<span id="mem-rel-0"></span>
						</td>
						<td>
							<input form ="add-form" type="text" class="form-control mem-exc-date" name="mem-exc-date" value="" style="width:100%;"  maxlength="10" readonly required="required">
						</td>
						<td>
							<input form ="add-form" type="text" class="form-control" name="mem-comment" value="" maxlength="255" required >
						</td>
						<td>
							<input form ="add-form" type="file" class="form-control" name="mem-attach" >
						</td>
						<td>	
							<button form ="add-form" type="submit" class="btn btn-sm add-btn" onclick="return confirm('Are You Sure to Add?');">add</button>
							<input form ="add-form" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<input form ="add-form" type="hidden" name="formid" value="<%=formid%>"/>
						</td>
					</tr>
						
					<% } %>
					
				</table>
				<div class="row" align="left"> 
					<div class="col-md-12">
					<br><br><br><br>
					<table style="float: left;">
						<tr>
							<td class="nobordertd" ><b style="text-decoration: underline;">APPLIED BY</b></td>
						</tr>
						<tr>
							<td class="nobordertd" ><b><%=empdetails[2] %>,</b></td>
						</tr>
						<tr>
							<td class="nobordertd"  > <%=empdetails[12] %></td>
						</tr>
						<tr>
							<td class="nobordertd" >Date: &nbsp; <%if(formdetails!=null && formdetails[4]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(formdetails[4].toString()) %>.<%} %></td>
						</tr>
					</table>
					
					<%if(formdetails!=null && formdetails[3].toString().equals("A")){ %>
					<table style="float: right;">
						<tr >
							<td class="nobordertd" ><b style="text-decoration: underline;" >APPROVED BY</b></td>
						</tr>
						<tr>
							<td class="nobordertd" ><b><%=formdetails[8] %>,</b></td>
						</tr>
						<tr>
							<td class="nobordertd" > <%=formdetails[9] %></td>
						</tr>
						<tr>
							<td class="nobordertd" >Date: &nbsp; <%=DateTimeFormatUtil.SqlToRegularDate(formdetails[6].toString()) %>.</td>
						</tr>
					</table>
					<%} %>
					</div>
				</div>
				<br><br>
				<form action="FamilyFormForwardRet.htm" method="Post">	
					<div class="row">
						<div class="col-md-12" style="text-align: justify !important;   	text-justify: inter-word;">
							<%if(formdetails!=null && formdetails[10]!=null && !formdetails[10].toString().equalsIgnoreCase("")){ %> <b style="color: red;">Remark : </b> <%=formdetails[10] %> <%} %>
							<br>
						</div>
						<div class="col-md-12">
							<b>Remark :</b>
							<textarea rows="5" style="width: 100%;" name="remarks" maxlength="400" required="required"></textarea>
						</div>
					</div>
					<%if(ExcMemberDetails.size()>0 && (status.equalsIgnoreCase("C") || status.equalsIgnoreCase("R"))){ %>
						<div align="center">
							<button type="submit" class="btn btn-sm submit-btn" id="fwd-btn" name="action" formnovalidate="formnovalidate" value="F" onclick="return confirm('Are you Sure to Submit ?');" >Forward</button>
						</div>
					
					<% } %>
					
					<%if(status.equalsIgnoreCase("F") && isapproval!=null && isapproval.equalsIgnoreCase("Y")){ %>
						<div align="center">
							<button type="submit" class="btn btn-sm submit-btn" formaction="FamilyFormForwardRet.htm" name="action" value="A" formnovalidate="formnovalidate" onclick="return confirm('Are you Sure to Confirm ?');" >Confirm</button>
							<button type="submit" class="btn btn-sm delete-btn" formaction="FamilyFormForwardRet.htm" name="action" value="R" onclick="return confirm('Are you Sure to Return ?');" >Return</button>
						</div>
					
					<% } %>
					<input type="hidden" name="formid" value="<%=formid%>">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="hidden" name="empid" value="<%=empid %>">
				</form>
				<br><br><br><br><br>
			</div>
		</div>
	</div>



<script type="text/javascript">



function changeReletion(selectid)
{
	$('#mem-rel-'+selectid).html($('#familydetailsid-'+selectid+' option:selected').attr('data-relation'));
}
	
	$('.mem-exc-date').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"startDate" : new Date(),
					 
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
		
	$('.mem-exc-date-edit').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,					
							 
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
		
	function showEdit(famdetailid)
	{
		$('#show-edit-'+famdetailid).show();
		$('#show-view-'+famdetailid).hide();
		
		changeReletion(famdetailid);
	}
		
	function hideEdit(famdetailid)
	{
		$('#show-edit-'+famdetailid).hide();
		$('#show-view-'+famdetailid).show();
	}
				
</script>

</body>
</html>