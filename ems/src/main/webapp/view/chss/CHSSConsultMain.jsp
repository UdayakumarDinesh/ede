<%@page import="java.util.ArrayList"%>
<%@page import="com.vts.ems.chss.model.CHSSConsultMain"%>
<%@page import="com.vts.ems.chss.model.CHSSApply"%>
<%@page import="com.vts.ems.chss.model.CHSSApproveAuthority"%>
<%@page import="com.vts.ems.chss.model.CHSSMedicinesList"%>
<%@page import="com.vts.ems.chss.model.CHSSTestSub"%>
<%@page import="com.vts.ems.chss.model.CHSSDoctorRates"%>
<%@page import="com.vts.ems.chss.model.CHSSOtherItems"%>
<%@page import="com.vts.ems.chss.model.CHSSTestMain"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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

.nav-pills .nav-link.active, .nav-pills .show>.nav-link 
{
	background-color: #750550;
}
.table thead{
    background: #9AD0EC;
}

.filter-option-inner, .bootstrap-select .dropdown-menu
{
   max-width: 100%!important;  
   font-weight: 500 !important;
}

.filter-option-inner-inner
{
	max-width: 100%!important;
}

option {
  max-width: 80% !important;
}

p {
	text-align: justify;
	text-justify: inter-word;
}

</style>
</head>
<body>

<%
	
	Employee employee = (Employee )request.getAttribute("employee") ;
	Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
	String isself = chssapplydata[3].toString();
	List<CHSSTreatType> treattypelist=(List<CHSSTreatType>)request.getAttribute("treattypelist");
	/* List<Object[]> chssbillslist=(List<Object[]>)request.getAttribute("chssbillslist"); */	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();	
/* 	List<CHSSTestSub> testmainlist1 = (List<CHSSTestSub>)request.getAttribute("testmainlist");	
	List<CHSSOtherItems> otheritemslist1 = (List<CHSSOtherItems>)request.getAttribute("otheritemslist");
	List<CHSSDoctorRates> doctorrates1 = (List<CHSSDoctorRates>)request.getAttribute("doctorrates");
	List<CHSSMedicinesList> allowedmeds1 =(List<CHSSMedicinesList>)request.getAttribute("allowedmed");
	 */
	List<Object[]> consultmainlist =(List<Object[]>)request.getAttribute("consultmainlist");
	 List<Object[]> consulthistory=(List<Object[]>)request.getAttribute("consulthistory");
	
	Object[] consultcount = (Object[])request.getAttribute("consultcount");
	Object[] medicinecount = (Object[])request.getAttribute("medicinecount");
	
	int mcount= 0, ccount=0;
	
	if(consultcount!=null)
	{
		ccount = Integer.parseInt(consultcount[0].toString());
	}
	
	if(medicinecount!=null)
	{
		mcount = Integer.parseInt(medicinecount[0].toString());
	}
	
/* 	String billid =(String)request.getAttribute("billid");
	String tab =(String)request.getAttribute("tab"); */
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
						<!-- <li class="breadcrumb-item "><a href="CHSSAppliedList.htm">CHSS List</a></li> -->
						<li class="breadcrumb-item active " aria-current="page">Claim Data</li>
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
				<%if(Integer.parseInt(chssapplydata[9].toString())>1){ %>
					<div class="row">
						<div class="col-md-12">
							<p>Remark : 
							<%=chssapplydata[19] %></p>
						</div>
					</div>
				<%} %>
				
					<form action="CHSSApplyEdit.htm" method="post" autocomplete="off"  >
						
						<div class="card" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
						<div class="row">
							
							<%if(isself.equalsIgnoreCase("N")){
								Object[] familyMemberData = (Object[])request.getAttribute("familyMemberData") ; %>
								
								<div class="col-4" >
								<div class="row" style="padding:5px; ">
									<b>Patient Name : &nbsp;</b> <%=familyMemberData[1] %>
								</div>
								
								<div class="row" style="padding:5px; ">
									<b>Relation : &nbsp;</b><%=familyMemberData[7] %>
									<input type="hidden" name="patientid" value="<%=familyMemberData[0]%>">
									<input type="hidden" name="relationid" value="<%=familyMemberData[2]%>">
								</div>
								</div>
							<%}else{ %>
								<div class="col-4">
								<div class="row" style="padding:5px; ">
									<b> Patient Name : &nbsp;</b><%=employee.getEmpName() %>
								</div>
								
								<div class="row" style="padding:5px; ">
									<b>Relation : &nbsp;</b>SELF
									<input type="hidden" name="patientid" value="<%=employee.getEmpId()%>">
									<input type="hidden" name="relationid" value="0">
								</div>
								</div>
							<% } %>
							<div class="col-2">
								<b>Treatment Type : </b><br>
								<select class="form-control select2 w-100" name="treatmenttype" id="treatmenttype" required="required" data-live-search="true" >
									<option value="" selected="selected" disabled="disabled">Choose..</option>
									<%for(CHSSTreatType treattype : treattypelist ){ %>
										<option value="<%=treattype.getTreatTypeId()%>" <%if(Integer.parseInt(chssapplydata[7].toString())==treattype.getTreatTypeId()){ %>selected<%} %> ><%=treattype.getTreatmentName() %></option>
									<%} %>								
								</select>
							</div>
							<div class="col-3">
								<b>Ailment/Disease/Accident : </b><br>
								<input type="text" class="form-control w-100" name="ailment" value="<%=chssapplydata[17] %>" required="required" maxlength="255" >
							</div>
							
							<div class="col-1">
								<button type="submit" class="btn btn-sm " style="margin-top: 20px;" Onclick="return confirm ('Are You Sure To Update?');" data-toggle="tooltip" data-placement="top" title="Update"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i></button> 
							</div>
						</div>
						</div>
						<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
					
					<div class="row">
						<div class="col-md-6">
						
							<form method="post" action="#" autocomplete="off"  >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<div class="table-responsive">
									<table class="table table-bordered table-hover table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:50%;" >Doctor Name</th>
												<th style="width:20%;" > Consultation Date </th>
												<th style="width:25%;" >Action  </th>
											</tr>
										</thead>
										<tbody >
										<%
										
										ArrayList<String> claimconsultids = new ArrayList<String>();
										int sno=0;
										for(Object[] cmain : consultmainlist){
											sno++;											
										%>
											
											<tr class="" >
												<%if(chssapplydata[0].toString().equalsIgnoreCase(cmain[1].toString())){ %>
													<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno %></span> </td>
													<td> <input type="text" class="form-control items" name="docname-<%=cmain[0]%>"   value="<%=cmain[2] %>" style="width:100%; "  maxlength="500" required="required"></td>
													<td> <input type="text" class="form-control consultdate" name="consultdate-<%=cmain[0]%>"   value="<%=rdf.format(sdf.parse(cmain[3].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
												
													<td>
														
														<button type="submit"  class="btn btn-sm" formaction="CHSSConsultMainEdit.htm" Onclick="return confirm('Are You Sure To Update?');" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip" data-placement="top" title="Update Bill">														
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>
														<button type="submit"  class="btn btn-sm" formaction="CHSSConsultMainDelete.htm" Onclick="return confirm('Are You Sure To Delete?');" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip" data-placement="top" title="Delete Bill">
															<i class="fa-solid fa-trash-can" style="color: red;"></i>
														</button>
																										
														<button type="submit"  class="btn btn-sm details-icon" formaction="CHSSConsultBills.htm" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip"  data-placement="top" title="Add Bills" >
															<i class="fa-solid fa-receipt"></i>
														</button>												
													</td>										
												<%}else{
													claimconsultids.add(cmain[0].toString());
													%>	
													<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno %></span> </td>
													<td> <input type="text" class="form-control items" name="docname-<%=cmain[0]%>"  readonly="readonly" value="<%=cmain[2] %>" style="width:100%; "  maxlength="500" required="required"></td>
													<td> <input type="text" class="form-control consultdate" name="consultdate-<%=cmain[0]%>"   value="<%=rdf.format(sdf.parse(cmain[3].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
													<td>
														<button type="submit"  class="btn btn-sm details-icon" formaction="CHSSConsultBills.htm" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip"  data-placement="top" title="Add Bills" >
															<i class="fa-solid fa-receipt"></i>
														</button>												
													</td>				
												
												
												
												<%} %>
											</tr>
											
											<%} %>
											<%if(sno==0){ %>
												<tr>
													<td colspan="6" style="text-align: center ;">
														Consultations Not Added
													</td>
												</tr>
											
											<%} %>
										</tbody>							
										
									</table>
								</div>
								
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							</form>
							
							<form method="post" action="CHSSConsultMainAdd.htm" autocomplete="off" >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<div class="table-responsive">
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
									<tbody>
										<tr class="" >
											<td style="width:5%;text-align: center;"><span class="sno" id="sno"><%=++sno %></span> </td>
											<td style="width:45%;" ><input type="text" class="form-control items" name="docname"  value="" style="width:100%; "  maxlength="500" required="required"></td>
											<td style="width:25%;" ><input type="text" class="form-control consultdate " name="consultdate"  value="" style="width:100%; "    maxlength="10" readonly required="required"></td>
											<td style="width:25%;" >
												<button type="submit"  class="btn btn-sm add-btn" Onclick="return confirm('Are You Sure To Add ?');" name="action" value="add" >ADD</button>
											</td>										
										</tr>
									</tbody>	
								</table>
							</div>
						</form>
						</div>
				
				
						<div class="col-md-6">
							
							<form method="post" action="#" autocomplete="off"  >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<div class="table-responsive">
									<table class="table table-bordered table-hover table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:50%;" >Doctor Name</th>
												<th style="width:20%;" > Consultation Date </th>
												<th style="width:25%;" >Action  </th>
											</tr>
										</thead>
										<tbody >
										<%	int sno1=0;
										
										for(Object[] consult : consulthistory)
										{
											if(!claimconsultids.contains(consult[0].toString())){
											sno1++;	
										
										%>
											
											<tr class="" >
											
												<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno1 %></span> </td>
												<td> <%=consult[3]%></td>
												<td> <%=rdf.format(sdf.parse(consult[2].toString())) %></td>
											
												<td>
													<button type="submit"  class="btn btn-sm details-icon" formaction="CHSSConsultBills.htm" name="consultmainid" value="<%=consult[0]%>" data-toggle="tooltip"  data-placement="top" title="Add Bills" >
														<i class="fa-solid fa-receipt"></i>
													</button>												
												</td>										
											
											</tr>
											
											<%} 
											}%>
											<%if(sno1==0){ %>
												<tr>
													<td colspan="6" style="text-align: center ;">
														Previous Consultations Not Found
													</td>
												</tr>
											
											<%} %>
										</tbody>							
										
									</table>
								</div>
								
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							</form>
						
						</div>			
				
					</div>	
						<form action="CHSSUserPreview.htm" method="post" id="form2">
							<div class="row">
								
								<div class="col-md-12" align="center" style="margin-top: 5px;">
									<button type="button" class="btn btn-sm view-icon" name="chssapplyid" value="<%=chssapplydata[0] %>" onclick="$('#previewform').submit();" formaction="CHSSForm.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Preview">
										<i class="fa-solid fa-eye"></i>
									</button>
									<button type="button" class="btn btn-sm misc1-btn" name="claimaction" value="F"  data-toggle="modal" data-target=".my-encl-modal">
										<i class="fa-solid fa-forward" style="color: #084594"></i> Preview	
									</button>
									
								</div>
							</div>
							
							
							
							<div class="modal my-encl-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
								<div class="modal-dialog  modal-dialog-centered" >
									<div class="modal-content" >
										<div class="modal-header">
											
											 <button type="button" class="close" data-dismiss="modal" aria-label="Close">
										    	<i class="fa-solid fa-xmark" aria-hidden="true" ></i>
										    </button>
										</div>
										<div class="modal-body" >
									          <div class="row">
											    <div class="col-12">
											    	<b>No of Enclosures : </b><br>
													<input type="number" class="form-control numberonly w-100" name="enclosurecount" id="enclosurecount" value="<%=chssapplydata[8] %>" min="1" required="required" >
												</div>
												
												 <div class="col-12 w-100" align="center">
												 <br>
												<button type="button" class="btn btn-sm submit-btn" name="claimaction" value="F"  onclick="return CheckClaimAmount (<%=chssapplydata[0]%>)"  data-toggle="modal" data-target=".my-encl-modal">
													Save
												</button>
												</div>
											</div>
										</div>
										
									</div>
								</div>	
							</div>
							
							
							<input type="hidden" name="claimaction" value="F">
							<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
					</form>
					
					<form action="CHSSForm.htm" method="post" id="previewform" target="_blank">	
						<input type="hidden" name="claimaction" value="F">
						<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
					
				</div>
			</div>		
			
		</div>
	
	 </div>
	 

<script type="text/javascript">



var $medsAllowedList;

function MedsAllowedList()
{
	
	var $treattype = $('#treattypeid').val();
	
	if(Number($treattype)>1){
		$.ajax({
	
			type : "GET",
			url : "CHSSMedicinesListAjax.htm",
			data : {
					
				treattypeid : $treattype,
			},
			datatype : 'json',
			success : function(result) {
				var result = JSON.parse(result);
				
				$medsAllowedList = Object.keys(result).map(function(e){
					return result[e]
				})
				
				
			}
		});
	}
	
}

$(document).ready( function() {
	onlyNumbers();
	getdoctorrates(<%=chssapplydata[7]%>);
	MedsAllowedList();

	<%if(mcount>0 || ccount>0 ){%>
		$('#treatmenttype').prop('disabled', true);
	<%}%> 

});   




function CheckClaimAmount($chssapplyid)
{
	$.ajax({

		type : "GET",
		url : "CHSSClaimFwdApproveAjax.htm",
		data : {
				
			chssapplyid : $chssapplyid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
						
			if(result===1){
				
						if(Number($('#enclosurecount').val())<1){
							alert('Total No. of Enclosures should not be zero !');
						}else{
							
							$('#form2').submit();
						}	
						
			}else if(result===-1){
				alert('Please Add Atleast one Consultation details.');
				return false;	
			}else if(result===0){
				alert('Total claim amount should not be zero !');
				return false;	
			}
		
		}
	});
	
}


var threeMonthsAgo = moment().subtract(3, 'months');

function  onlyNumbers() {    
    
    $('.numberonly').keypress(function (e) {    

        var charCode = (e.which) ? e.which : event.keyCode    

        if (String.fromCharCode(charCode).match(/[^0-9]/g))    

            return false;                        

    });    

}

$('.consultdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(), 
	"minDate":threeMonthsAgo, 
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

function setTooltip()
{
	$('[data-toggle="tooltip"]').tooltip({
		 trigger : 'hover',
	});
	$('[data-toggle="tooltip"]').on('click', function () {
		$(this).tooltip('hide');
	});
}
</script>

<!-- -------------------------------------------------------modal script --------------------------------------------------- -->
<script type="text/javascript">


$(document).ready( function() {

	
	<%if(mcount>0 || ccount>0 ){%>
		$('#treatmenttype').prop('disabled', true);
	<%}%> 

});   




</script>

</body>
</html>