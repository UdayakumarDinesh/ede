<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CHSS</title>
<jsp:include page="../static/header.jsp"></jsp:include>

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
</style>
<meta charset="ISO-8859-1">
<title>CHSS FORM - 2</title>
</head>
<%
	List<Object[]> FwdMemberDetails = (List<Object[]>)request.getAttribute("FwdMemberDetails");
	List<Object[]> relationtypes = (List<Object[]>)request.getAttribute("relationtypes");
	Object[] empdetails = (Object[])request.getAttribute("empdetails");
	Object[] employeeResAddr = (Object[])request.getAttribute("employeeResAddr");
	Object[] formdetails = (Object[])request.getAttribute("formdetails");
	
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
				<h5>CHSS FORM -2</h5>
			</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item " aria-current="page"><a href="EmployeeDetails.htm">Profile</a></li>
						<%if(isapproval.equalsIgnoreCase("N")){ %>
						<li class="breadcrumb-item " aria-current="page"><a href="FamIncExcFwdList.htm">Include / Exclude</a></li>
						<%}else if(isapproval.equalsIgnoreCase("Y")){ %>
						<li class="breadcrumb-item " aria-current="page"><a href="FamFormsApproveList.htm">CHSS Forms Approval</a></li>
						<%} %>
						<li class="breadcrumb-item active " aria-current="page">CHSS FORM -2</li>
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
	<div align="center">	
		<div id="pageborder" align="center" style="max-width:95%;margin-top: 5px;margin-left: 10px;" >
			<div align="right">
				<h3 style="text-decoration: underline;">CHSS FORM - 2</h3>
			</div>
			<div align="center">
				<h3>Contributory Health Service Scheme of SITAR Society</h3>
				<span style="text-decoration: underline; font-size: 15px;"><b>Application for addition of names of beneficiaries</b></span>
			</div>
			<div align="left">
				<h3>UNIT : STARC</h3>
			</div>
			<div align="left">
				<table style="width:100%; ">
					<tr>
						<th style="width:30% " >Name</th>
						<th style="width:15% " >Emp.No.</th>
						<th style="width:20% " >Designation</th>
						<th style="width: 10% " >Level In Pay Matrix</th>
						<th style="width: 25% " >Basic Pay</th>
						<!-- <th style="width: 12% " >Date of Birth</th>
						<th style="width: 12% " >Date of Appointment</th> -->
					</tr>
					<tr>
						<td><%=empdetails[2] %></td>
						<td><%=empdetails[1] %></td>
						<td><%=empdetails[12] %></td>
						<td><%=empdetails[9] %></td>
						<td><%=empdetails[11] %></td>
						<!-- <td></td>
						<td></td> -->
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
						<th style="width:3%;text-align: center; " >SN</th>
						<th style="width:15% " >Name</th>
						<th style="width:12% " >Relationship With the Employee</th>
						<th style="width: 10% " >Date of Birth</th>
						<th style="width: 12% " >Occupation if Any</th>
						<th style="width: 8% " >Income, if any, per month (Rs) </th>
						<th style="width: 15% " >Comments </th>
						<th style="width: 15% " >Attachment </th>
						<%if((status.equalsIgnoreCase("C") || status.equalsIgnoreCase("R"))){ %>
						<th style="width: 10% " >
							Action  
						</th>
						<%} %>
					</tr>
					<%int i=0;
					for(;i<FwdMemberDetails.size();i++)
					{ %>
					
					<%-- <%if(status.equalsIgnoreCase("C")){ %> --%>
						<tr id="show-edit-<%=FwdMemberDetails.get(i)[0] %>" style="display: none;" >
							<td style="text-align: center;" ><form action="DepMemEditSubmit.htm" method="POST" enctype="multipart/form-data" autocomplete="off" id="edit-form-<%=FwdMemberDetails.get(i)[0] %>" ><%=i+1%></form></td>
							<td>
								<input type="text" form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" class="form-control" name="mem-name" value="<%=FwdMemberDetails.get(i)[1] %>" maxlength="255" required >
							</td>
							<td>
								<select form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" class="form-control select2 " style="width: 100%" name="mem-relation" data-live-search="true" data-size="6" required>
									<%for(Object[] relation : relationtypes){ %>
										<option value="<%=relation[0]%>" <%if(FwdMemberDetails.get(i)[14].toString().equalsIgnoreCase(relation[0].toString())){ %> selected <%} %> ><%=relation[1]%></option>
									<%} %>
								</select>							
							</td>
							<td>
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="text" class="form-control mem-dob-edit" name="mem-dob" value="<%=DateTimeFormatUtil.SqlToRegularDate(FwdMemberDetails.get(i)[3].toString()) %>" style="width:100%;"  maxlength="10" readonly required="required">
							</td>
							<td>
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="text" class="form-control" name="mem-occupation" id="mem-occupation-<%=FwdMemberDetails.get(i)[0] %>" <%if(FwdMemberDetails.get(i)[7]!=null){ %>value="<%=FwdMemberDetails.get(i)[7] %>" <%}else{ %>value="" <%} %> maxlength="255" onchange = "IncomeRequired('<%=FwdMemberDetails.get(i)[0] %>');" >
							</td>
							<td>
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="number" class="form-control numberonly" name="mem-income" id="mem-income-<%=FwdMemberDetails.get(i)[0] %>"  <%if(FwdMemberDetails.get(i)[8]!=null && !FwdMemberDetails.get(i)[8].toString().equals("0")){ %>value="<%=FwdMemberDetails.get(i)[8] %>" <%}else{ %>value="" <%} %>min="0"  >
							</td>
							<td>
								
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="text" class="form-control" name="mem-comment" <%if(FwdMemberDetails.get(i)[9]!=null ){ %>value="<%=FwdMemberDetails.get(i)[9] %>" <%}else{ %>value="" <%} %> maxlength="255" required >
								
							</td>
							<td>
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="file" class="form-control" name="mem-attach-edit"  >
							</td>
							<td>	
								<button form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="submit" class="btn btn-sm update-btn" name="familydetailsid" value="<%=FwdMemberDetails.get(i)[0] %>"  onclick="return confirm('Are You Sure to Update ?');" title="update">
									Update
								</button>
								<button form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="button" class="btn btn-sm "  onclick="hideEdit('<%=FwdMemberDetails.get(i)[0] %>')" title="Cancel"> 
									<i class="fa-solid fa-xmark"  style="color: red;"></i>
								</button>
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="hidden" name="formid" value="<%=formid%>"/>
							</td>
							
						</tr>
					<%-- <%} %> --%>
						<tr id="show-view-<%=FwdMemberDetails.get(i)[0] %>"   >
						
							<td style="text-align: center;" ><%=i+1%></td>
							<td><%=FwdMemberDetails.get(i)[1] %></td>
							<td><%=FwdMemberDetails.get(i)[2] %></td>
							<td><%=DateTimeFormatUtil.SqlToRegularDate(FwdMemberDetails.get(i)[3].toString()) %></td>
							<td>
								<%if(FwdMemberDetails.get(i)[7]!=null){ %>
									<%=FwdMemberDetails.get(i)[7] %>
								<%}else{ %>
									-
								<% } %>
							</td>
							<td>
								<%if(FwdMemberDetails.get(i)[8]!=null && !FwdMemberDetails.get(i)[8].toString().equals("0")){ %>
								<%=FwdMemberDetails.get(i)[8] %>
								<%}else{ %>
									-
								<%} %>
							</td>
							<td>
								<%if(FwdMemberDetails.get(i)[9]!=null){ %>
									<%=FwdMemberDetails.get(i)[9] %>
								<%}else{ %>
									-
								<%} %>
							</td>
							<td style="text-align: center;">
								<%if(FwdMemberDetails.get(i)[10]!=null){ %>
									<button form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="submit" class="btn btn-sm" name="familydetailid" value="<%=FwdMemberDetails.get(i)[0] %>" formaction="FamIncExcAttachDownload.htm" formtarget="_blank"  data-toggle="tooltip" data-placement="top" title="Download">
										<i style="color: #019267" class="fa-solid fa-download"></i>
									</button>
								<%}%>
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="hidden" name="FileFor" value="I"/>		
							</td>
							<%if(status.equalsIgnoreCase("C") || status.equalsIgnoreCase("R")){ %>
								<td>
									<button type="button" class="btn btn-sm "  onclick="showEdit('<%=FwdMemberDetails.get(i)[0] %>')" title="Edit"> 
										<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
									</button> 
									<button form="edit-form-<%=FwdMemberDetails.get(i)[0] %>" type="submit" class="btn btn-sm" formnovalidate="formnovalidate" name="familydetailsid" value="<%=FwdMemberDetails.get(i)[0] %>" formaction="FamilyMemberDelete.htm"  onclick="return confirm('Are You Sure to Delete ?');" title="Delete">
										<i class="fa-solid fa-trash-can" style="color: red;"></i>
									</button>
								</td>
							<%} %>			
						</tr>
					
					
					<%} %>
					<%if(status.equalsIgnoreCase("C") || status.equalsIgnoreCase("R")){ %>
					
						<tr>
							<td style="text-align: center;" ><form method="post" action="DepMemAddSubmit.htm" enctype="multipart/form-data" autocomplete="off" id="add-form" ><%=i+1%></form></td>
							<td>
								<input form ="add-form" type="text" class="form-control" name="mem-name" value="" maxlength="255" required >
							</td>
							<td>
								<select form ="add-form" class="form-control select2 w-100" name="mem-relation" data-live-search="true" data-size="6" required>
									<option  disabled="disabled" selected value="">Choose..</option>
									<%for(Object[] relation : relationtypes){ %>
										<option value="<%=relation[0]%>"><%=relation[1]%></option>
									<% } %>
								</select>							
							</td>
							<td>
								<input form ="add-form" type="text" class="form-control mem-dob" name="mem-dob" value="" style="width:100%;"  maxlength="10" readonly required="required">
							</td>
							<td>
								<input form ="add-form" type="text" class="form-control" name="mem-occupation" id="mem-occupation-0"  value="" maxlength="255" onchange="IncomeRequired(0);"  >
							</td>
							<td>
								<input form ="add-form" type="number" class="form-control numberonly" name="mem-income" id="mem-income-0" value="" min="0"  >
							</td>
							<td>
								<input form ="add-form" type="text" class="form-control" name="mem-comment" value="" maxlength="255" required >
							</td>
							<td>
								<input form ="add-form" type="file" class="form-control" name="mem-attach"  required="required">
							</td>
							<td>	
								<button form ="add-form" type="submit" class="btn btn-sm add-btn" onclick="return confirm('Are You Sure to Add?');">add</button>
								<input form ="add-form" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<%if(FwdMemberDetails.size()>0){ %>
								<input form ="add-form" type="hidden" name="formid" value="<%=formid%>"/>
								<%}else{ %>
								<input form ="add-form" type="hidden" name="formid" value="0"/>
								<%} %>
							</td>
						</tr>
					<%} %>
				</table>

				<p style="text-indent: 50px;">
					<input type="checkbox"  class="TCBox" > &nbsp;
					I hereby undertake to declare at the beginning of each calendar year and as soon as necessary
					thereafter about the eligibility or otherwise to the CHSS benefits of myself and my parents/ family
					members whose names are mentioned above. It shall be my responsibility to notify the Unit when any
					person referred to above becomes ineligible to the CHSS benefits. I realise that the onus of proving
					eligibility of the members mentioned above to the benefits of the scheme rests on me.
				</p>
				
				<h1 class="break"></h1>
				
				<div align="left">
				<div align="center"> <h4 style="text-decoration: underline; ">Instructions for the employee</h4></div>
					<ol class="list-group">
						<li class="list-group-item">
							<p><input type="checkbox"  class="TCBox" > &nbsp; 
							<b > The term Parents for the purpose of CHSS benefits does not include 'Step Parents'. Parent
							should have actually resided at least for 60 days with the employee before they are proposed for
							inclusion under the CHSS, and should continue to reside with the employee and be mainly
							dependant on him / her.</b> If the total income of the parents from all sources does not exceed thE ay
							of the employee, subject to the maximum income of the parents being Rs. 18,000/- per month, such
							parents may be treated as mainly dependant on the employee. Income from land holdings, houses,
							fixed deposits, dividends, securities, deposits, employment, pension etc., should be taken into account
							for the purpose of total income of both the parents. If the parents of an employee move out for more
							than 60 days continuously, the Unit should be notified by the employee for suspension / cancellation
							of CHSS facilities.</p>
						</li>
						<li class="list-group-item">
							<p><input type="checkbox"  class="TCBox" > &nbsp;
								Married / divorced or legally separated daughters of employees, though dependant on the
								employee, are not eligible for medical benefits under CHSS. In the case of adopted children, only
								legally adopted sons and daughters are eligible for the benefits of the CHSS.
							</p>
						</li>
						<li class="list-group-item">
							<p><input type="checkbox"  class="TCBox" > &nbsp;
								If any of the family members / dependants for whom the registration is sought is eligible to
								receive medical aid/ facility, cash subsidy, cash allowances or reimbursement for medical care from
								sources other than the CHSS of SITAR Society, particulars of such benefits should be furnished on a
								separate sheet.
							</p>
						</li>
						<li class="list-group-item">
							<p><input type="checkbox"  class="TCBox" > &nbsp;
								If any of the members of the family proposed for registration is engaged in trade/business or is employed outside SITAR on part / full time basis, full particulars of such occupation should be
								furnished on a separate sheet duly supported by documentary evidence so that their eligibility for
								CHSS beneficiaries could be determined.
							</p>
						</li>
						<li class="list-group-item">
							<p><input type="checkbox"  class="TCBox" > &nbsp;
								Employees giving false or misleading information will be liable for disciplinary action.
							</p>
						</li>
					</ol>
				
				</div>
				
				<div align="center" ><b style="text-decoration: underline; text-align: center;" >Certificate in respect of dependant </b> </div> 
				<p style="text-indent: 21px; "><input type="checkbox"  class="TCBox" > &nbsp;
				I certify that the family members whose names are mentioned above are mainly dependant on and
					residing with me.
				</p>
				
				<div class="row" align="left"> 
					<div class="col-md-12">
					<%if(formdetails!=null && formdetails[3].toString().equals("A")){ %>
					<table style="float: left;">
						<tr>
							<td style="border: 0"><b><%=formdetails[8] %>,</b></td>
						</tr>
						<tr>
							<td style="border: 0"> <%=formdetails[9] %></td>
						</tr>
					</table>
					<%} %>
					<table style="float: right;">
						<tr>
							<td style="border: 0"><b><%=empdetails[2] %>,</b></td>
						</tr>
						<tr>
							<td style="border: 0"> <%=empdetails[12] %></td>
						</tr>
					</table>
					</div>
				</div>
				
				<br>
				<form action="FamilyMembersForward.htm" method="Post">	
					<div class="row">
						<div class="col-md-12" style="text-align: justify !important;   	text-justify: inter-word;">
							<%if(formdetails[10]!=null || !formdetails[10].toString().equalsIgnoreCase("")){ %> <b style="color: red;">Remark : </b> <%=formdetails[10] %> <%} %>
							<br>
						</div>
						<div class="col-md-12">
							<textarea rows="5" style="width: 100%;" name="remarks" maxlength="400" required="required"></textarea>
						</div>
					</div>
					<%if(FwdMemberDetails.size()>0 && (status.equalsIgnoreCase("C") || status.equalsIgnoreCase("R"))){ %>
						<div align="center">
							<button type="submit" class="btn btn-sm submit-btn" id="fwd-btn" name="formid" formnovalidate="formnovalidate" value="<%=formid%>" onclick="return confirm('Are you Sure to Submit ?');" disabled="disabled">Forward</button>
						</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="hidden" name="empid" value="<%=empid %>">
					
					<% } %>
					
					<%if(status.equalsIgnoreCase("F") && isapproval!=null && isapproval.equalsIgnoreCase("Y")){ %>
						<div align="center">
							<button type="submit" class="btn btn-sm submit-btn" formaction="FamilyMembersFormApprove.htm" name="familyformid" formnovalidate="formnovalidate" value="<%=formid%>" onclick="return confirm('Are you Sure to Confirm ?');" >Confirm</button>
							<button type="submit" class="btn btn-sm delete-btn" formaction="FamilyIncFormReturn.htm" name="formid" value="<%=formid%>" onclick="return confirm('Are you Sure to Return ?');" >Return</button>
						</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="hidden" name="empid" value="<%=empid %>">
					
					<% } %>
				</form>
				<br><br><br><br><br>
			</div>
		</div>
	</div>


<script type="text/javascript">
	function IncomeRequired(detailid)
	{
		if($('#mem-occupation-'+detailid).val().trim() === '')
		{
			$('#mem-income-'+detailid).prop('required',false);
		}
		else
		{
			$('#mem-income-'+detailid).prop('required',true);
		}
	}			
</script>
<script type="text/javascript">


$(document).ready(function(){

    
    $('.TCBox').on('click',function(){
        if($('.TCBox:checked').length == $('.TCBox').length)
        {
        	  $('#fwd-btn').prop('disabled',false);
        }
        else{
        	  $('#fwd-btn').prop('disabled',true);
        }
    });
})


</script>
<script type="text/javascript">
		
	$('.mem-dob').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"startDate" : new Date(),
		"maxDate" :new Date(),					 
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
		
	$('.mem-dob-edit').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,					
		"maxDate" :new Date(),					 
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
		
	$('.numberonly').keypress(function (e) {    
        var charCode = (e.which) ? e.which : event.keyCode    
        if (String.fromCharCode(charCode).match(/[^0-9]/g))    
        return false;                        
	});
	
			
				
	function showEdit(famdetailid)
	{
		$('#show-edit-'+famdetailid).show();
		$('#show-view-'+famdetailid).hide();
	}
		
	function hideEdit(famdetailid)
	{
		$('#show-edit-'+famdetailid).hide();
		$('#show-view-'+famdetailid).show();
	}
				
</script>

</body>
</html>

