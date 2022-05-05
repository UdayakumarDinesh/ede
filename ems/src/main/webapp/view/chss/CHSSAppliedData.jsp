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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	List<Object[]> chssbillslist=(List<Object[]>)request.getAttribute("chssbillslist");	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();	
	List<CHSSTestSub> testmainlist = (List<CHSSTestSub>)request.getAttribute("testmainlist");	
	List<CHSSOtherItems> otheritemslist = (List<CHSSOtherItems>)request.getAttribute("otheritemslist");
	List<CHSSDoctorRates> doctorrates = (List<CHSSDoctorRates>)request.getAttribute("doctorrates");
	List<CHSSMedicinesList> allowedmeds =(List<CHSSMedicinesList>)request.getAttribute("allowedmed");
	
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
	
	String billid =(String)request.getAttribute("billid");
	String tab =(String)request.getAttribute("tab");
%>

 <div class="col page card">
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
						<li class="breadcrumb-item active " aria-current="page">CHSS Data</li>
					</ol>
				</div>
			</div>
	</div>	
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
							<%} %>
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
						
							<form method="post" action="#" autocomplete="off"  >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<div class="table-responsive">
									<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:35%;" >Hospital / Medical / Diagnostics Centre Name</th>
												<th style="width:20%;" >Bill / Receipt No.</th>
												<th style="width:10%;" >Bill Date</th>
												<th style="width:15%; text-align: right;">Amount (&#8377;)</th>
												<th style="width:10%;" >Action  </th>
											</tr>
										</thead>
										<tbody >
										<%	int sno=0;
										for(Object[] obj : chssbillslist){
											sno++;%>
											
											<tr class="" >
											
												<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno %></span> </td>
												<td> <input type="text" class="form-control items" name="centername-<%=obj[0]%>"  id="centername-<%=obj[0]%>"  value="<%=obj[3] %>" style="width:100%; "  maxlength="500" required="required"></td>
												<td> <input type="text" class="form-control items" name="billno-<%=obj[0]%>" id="billno-<%=obj[0]%>"  value="<%=obj[2] %>" style="width:100%;"   maxlength="100" required="required"></td>
												<td> <input type="text" class="form-control billdate" name="billdate-<%=obj[0]%>"  id="billdate-<%=obj[0]%>" value="<%=rdf.format(sdf.parse(obj[4].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
												<%if(obj[5]!=null){ %>
												<td> <input type="number" class="form-control items numberonly " name="billamount-<%=obj[0]%>" id="billamount-<%=obj[0]%>"  value="<%=obj[5] %>" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" readonly="readonly"></td>
												<%}else{ %>
												<td> <input type="number" class="form-control items numberonly" name="billamount-<%=obj[0]%>" id="billamount-<%=obj[0]%>"  value="0" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" readonly="readonly"></td>
												<%} %>
												<td>
													<button type="submit"  class="btn btn-sm" formaction="CHSSBillEdit.htm" Onclick="return confirm('Are You Sure To Update?');" name="billid" value="<%=obj[0]%>" data-toggle="tooltip" data-placement="top" title="Update Bill">														
														<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
													</button>
													<button type="submit"  class="btn btn-sm" formaction="CHSSBillDelete.htm" Onclick="return confirm('Are You Sure To Delete?');" name="billid" value="<%=obj[0]%>" data-toggle="tooltip" data-placement="top" title="Delete Bill">
														<i class="fa-solid fa-trash-can" style="color: red;"></i>
													</button>													
													<button type="button"  class="btn btn-sm" formaction="CHSSBillDelete.htm"  Onclick="showBillDetails('<%=obj[0]%>')" name="billid" value="<%=obj[0]%>" data-toggle="tooltip"  data-placement="top" title="Bill Details" >
														<i class="fa-solid fa-file-lines"></i>
													</button>													
												</td>										
											
											</tr>
											
											<%} %>
											<%if(sno==0){ %>
												<tr>
													<td colspan="6" style="text-align: center ;">
														Bills Not Added
													</td>
												</tr>
											
											<%} %>
										</tbody>							
										
									</table>
								</div>
								
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							</form>
							<form method="post" action="CHSSBillAdd.htm" autocomplete="off" >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<div class="table-responsive">
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
									<tbody>
										<tr class="" >
										
											<td style="width:5%;text-align: center;"><span class="sno" id="sno"><%=++sno %></span> </td>
											<td style="width:35%;" ><input type="text" class="form-control items " name="centername"  value="" style="width:100%; "  maxlength="500" required="required"></td>
											<td style="width:20%;" ><input type="text" class="form-control items " name="billno"  value="" style="width:100%;"   maxlength="100" required="required"></td>
											<td style="width:10%;" ><input type="text" class="form-control billdate " name="billdate"  value="" style="width:100%; "    maxlength="10" readonly required="required"></td>
											<td style="width:25%;" >
												<button type="submit"  class="btn btn-sm add-btn" Onclick="return confirm('Are You Sure To Add ?');" name="action" value="add" >ADD</button>
											</td>										
										</tr>
									</tbody>	
								</table>
							</div>
						</form>
						
						<form action="CHSSUserPreview.htm" method="post" id="form2">
							<div class="row">
								
								<div class="col-md-12" align="center" style="margin-top: 5px;">
									<button type="button" class="btn btn-sm" name="chssapplyid" value="<%=chssapplydata[0] %>" onclick="$('#previewform').submit();" formaction="CHSSForm.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Preview">
										<i class="fa-solid fa-eye"></i>
									</button>
									<button type="button" class="btn btn-sm submit-btn" name="claimaction" value="F"  data-toggle="modal" data-target=".my-encl-modal">
										<i class="fa-solid fa-forward" style="color: #A63EC5"></i> Preview	
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
	 
<div class="modal fade my-bill-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" style="min-width: 85% !important;min-height: 80% !important; ">
		<div class="modal-content" >
			<div class="modal-header" style="background: #F5C6A5 ">
		          <div class="row" style="width: 100%;">
				    <div class="col-3">
				    	<span><b>BillNo : </b><span id="modal-billno" ></span></span> 
				    </div>
				    <div class="col-3">
				    	<span><b>Medical Center : </b><span id="modal-centername" ></span></span>
				    </div>
				    <div class="col-3">
				    	<span><b>Bill Date : </b><span id="modal-billdate" ></span></span>
				    </div>
				    <div class="col-3">
				    	<span><b>Bill Amount : &#8377;  </b><span id="modal-billamount" ></span></span>
				    </div>
			    </div>
			    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			    	<i class="fa-solid fa-xmark" aria-hidden="true" ></i>
			    </button>
		    </div>
			<div class="modal-body" style="min-height: 30rem;">
		
			    <div class="row" >
			    	<div class="col-12">
				    	<div class="nav nav-pills nav-justified" id="nav-tabs">
				    		<a class="nav-item nav-link co" data-toggle="tab" id="nav-consultation-tab" href="#nav-consultation" role="tab" aria-controls="nav-consultation"  Onclick="getConsultdata();"  >Consultation</a>
				    		<a class="nav-item nav-link te" data-toggle="tab" id="nav-tests-tab" href="#nav-tests" role="tab" aria-controls="nav-tests"   Onclick="getTestsData();"  >Tests</a>
				    		<a class="nav-item nav-link me" data-toggle="tab" id="nav-medicines-tab" href="#nav-medicines" role="tab" aria-controls="nav-medicines" Onclick="getMedicinesData();"  >Medicines</a>
				    		<a class="nav-item nav-link ot" data-toggle="tab" id="nav-others-tab" href="#nav-others" role="tab" aria-controls="nav-others" onclick="getOthersDetails()" >Others</a>
				    		<a class="nav-item nav-link mi" data-toggle="tab" id="nav-misc-tab" href="#nav-misc" role="tab" aria-controls="nav-misc" onclick="getmiscData()" >Miscellaneous</a>
				    	</div>
			    	</div>
			    </div>
			   <div class="tab-content " id="nav-tabContent" style="margin-top: 10px;">
			   
<!-- ------------------------------------------------------- consultation --------------------------------------------------- -->		   
			   		<div class="tab-pane fade show active" id="nav-consultation" role="tabpanel" aria-labelledby="nav-consultation-tab">
			   		
				   		<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:12%;"> Consultation </th>
											<th style="width:30%;">Doctor's Name</th>
											<th style="width:15%;">Qualification</th>
											<th style="width:15%;">Consult Date</th>
											<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:8%;" > Action </th>
										</tr>
									</thead>
									<tbody id="consult-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="ConsultationBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:15%;"> Consultation </th>
											<th style="width:30%;">Name of the Doctor</th>
											<th style="width:20%;">Qualification</th>
											<th style="width:15%;">Date</th>
											<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:5%;" > <button type="button" class="btn btn-sm tbl-row-add-cons" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_cons" >
											<td>
												<select class="form-control w-100" name="consult-type" required="required" >
													<option value="Fresh">Fresh</option>
													<option value="FollowUp">FollowUp</option>
												</select>
											</td>
											<td><input type="text" class="form-control items" name="doc-name" id="doc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
											<td>
												<select class="form-control w-100" name="doc-qualification" required="required" >
													<%for(CHSSDoctorRates rate:doctorrates ){ %>
														<option value="<%=rate.getDocRateId() %>"><%=rate.getDocQualification() %></option>
													<%} %>
												</select>
											</td>
											<td><input type="text" class="form-control cons-date" name="cons-date" id="cons-date" value="" style="width:100%;"  maxlength="10" readonly required="required"></td>
											<td><input type="number" class="form-control items numberonly" name="cons-charge" id="cons-charge" value="" style="width:100%;direction: rtl;" min="0" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_cons"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit"  onclick="return confirm('Are You Sure To Submit?');" >ADD</button>	
								</div>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
				    		
			   		</div>
<!-- ------------------------------------------------------- consultation --------------------------------------------------- -->
<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->			   	
			   		<div class="tab-pane fade " id="nav-tests" role="tabpanel" aria-labelledby="nav-tests-tab">
			   			<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:35%;">Test Name</th>
											<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > Action </th>
										</tr>
									</thead>
									<tbody id="tests-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="TestsBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:75%;">Test Name</th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:5%;" > <button type="button" class="btn btn-sm tbl-row-add-tests"  data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button>  </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_tests"  id="tr_clone_tests">
											<td style="max-width:35% !important;">
												<select class="form-control test-type  selectpicker " id="test-type_1" style="width: 100%" data-size="auto" name="test-id" required="required" data-live-search="true" data-container="body" >
												
													<option value="" selected="selected" disabled="disabled">Choose..</option>
													<%for(CHSSTestSub testmain : testmainlist){ %>
														<option value="<%= testmain.getTestMainId()%>_<%= testmain.getTestSubId() %>"><%=testmain.getTestName()%></option>
													<% } %>
												</select>
											</td>
											<td><input type="number" class="form-control items numberonly" name="tests-cost"  value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_tests"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit" onclick="confirm('Are You Sure To Submit?');">Add</button>	
								</div>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
			   		</div>
<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->
<!-- ------------------------------------------------------- medicines --------------------------------------------------- -->			   		
			   		<div class="tab-pane fade " id="nav-medicines" role="tabpanel" aria-labelledby="nav-medicines-tab">
			   		
				   		<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:50%;"> Medicine Name </th>
											<th style="width:10%;">Rx Qty.</th>
											<th style="width:10%;">Pur Qty.</th>
											<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > Action </th>
										</tr>
									</thead>
									<tbody id="meds-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="MedicinesBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:50%;"> Medicine Name </th>
											<th style="width:10%;">Rx Qty.</th>
											<th style="width:10%;">Pur Qty.</th>
											<th style="width:20%; text-align: right;">Amount (&#8377;)</th> 
											<th style="width:10%;" > <button type="button" class="btn btn-sm tbl-row-add-meds" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_meds" >
											<td>
												<%if(Integer.parseInt(chssapplydata[7].toString())==1){ %>
												<input type="text" class="form-control items" name="meds-name" id="meds-name" value="" style="width:100%; "  maxlength="255" required="required">
												<%}else{ %>
													<select class="form-control selectpicker " name="meds-name" required="required" style="width: 100%" data-live-search="true"  >
														<%for(CHSSMedicinesList medicine : allowedmeds ){ %>
															<option value="<%=medicine.getMedicineName()%>"><%=medicine.getMedicineName() %></option>
														<%} %>
													</select>
												<%} %>
											</td>
											<td><input type="number" class="form-control items numberonly" name="meds-presquantity" id="meds-quantity" value="" style="width:100%;" min="1" max="9999999" required="required" ></td>
											<td><input type="number" class="form-control items numberonly" name="meds-quantity" id="meds-quantity" value="" style="width:100%;" min="1" max="9999999" required="required" ></td>
											<td><input type="number" class="form-control items numberonly" name="meds-cost" id="meds-cost" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_meds"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit"  onclick="return confirm('Are You Sure To Submit?');" >Add</button>	
								</div>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
			   			
			   		</div>
<!-- ------------------------------------------------------- medicines --------------------------------------------------- -->
<!-- ------------------------------------------------------- Others --------------------------------------------------- -->	
			   		<div class="tab-pane fade " id="nav-others" role="tabpanel" aria-labelledby="nav-others-tab">
			   		
			   			<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:65%;"> Item Name </th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > Action </th>
										</tr>
									</thead>
									<tbody id="other-list-table">
										
									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="OtherBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:70%;"> Item</th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > <button type="button" class="btn btn-sm tbl-row-add-other" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_other" >
											<td>
												<select class="form-control selectpicker " name="otheritemid" required="required" style="width: 100%" data-live-search="true"  >
													<%for(int k=0 ;k<otheritemslist.size();k++){ %>
														<option value="<%=otheritemslist.get(k).getOtherItemId() %>"><%=otheritemslist.get(k).getOtherItemName() %></option>
													<%} %>
												</select>
											</td>
											<td><input type="number" class="form-control items numberonly" name="otheritemcost" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_misc"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit" onclick="return confirm('Are You Sure To Submit?');" >Add</button>	
								</div>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
			   			
			   		</div>
<!-- ------------------------------------------------------- Others --------------------------------------------------- -->	
<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->	
			   		<div class="tab-pane fade " id="nav-misc" role="tabpanel" aria-labelledby="nav-misc-tab">
			   		
			   			<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:65%;"> Item Name </th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > Action </th>
										</tr>
									</thead>
									<tbody id="misc-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="MiscBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:70%;"> Item Name </th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > <button type="button" class="btn btn-sm tbl-row-add-misc" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody class="tr_other_add">
										<tr class="tr_clone_misc" >
											<td><input type="text" class="form-control items" name="misc-name" id="misc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
											<td><input type="number" class="form-control items numberonly" name="misc-cost" id="misc-cost" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_misc"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>			
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit" onclick="return confirm('Are You Sure To Submit?');" >Add</button>	
								</div>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
			   			
			   		</div>
<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->			   		
			   </div>
			</div>
		      
		</div>
	</div>
</div>

<!-- -------------------------------------------------------modal script --------------------------------------------------- -->

<input type="hidden" name="treattype" id="treattypeid" value="<%=chssapplydata[7]%>">

<script type="text/javascript">

var tab = '<%=tab%>';

function showBillDetails($billid)
{
	$(".billid").val($billid);
	
	
	
	var $billid = $('.billid').val();
	$.ajax({

		type : "GET",
		url : "GetBillDataAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		
			$('#modal-billno').html(result[2]);
			$('#modal-centername').html(result[3]);
			$('#modal-billdate').html(result[4]);
			if(result[5]!=null){
				$('#modal-billamount').html(result[5]);
			
			}else{
				$('#modal-billamount').html('0');
			}
			
			if(tab=='null'){
				$('.co').click();
			}else
			{
				$('.'+tab).click();
				tab= 'null';
			}
			
			$('.my-bill-modal').modal('toggle');
			
		}
	});
	
}

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
	<%if(billid!=null && !billid.equalsIgnoreCase("null") && Long.parseLong(billid)>0 ){%>
		showBillDetails(<%=billid%>);
	<%}%> 
	
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

$('.billdate').daterangepicker({
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

<!-- -------------------------------------------------------consultation script --------------------------------------------------- -->

<script type="text/javascript">


var $docrateslist;
function getdoctorrates(treattype)
{
	$.ajax({

		type : "GET",
		url : "CHSSDoctorRatesAjax.htm",
		data : {
				
			treattypeid : treattype,
		},
		datatype : 'json',
		success : function(result) {
			var result = JSON.parse(result);
			$docrateslist= Object.keys(result).map(function(e){
				return result[e]
			})
		}
	});

}



$("table").on('click','.tbl-row-add-cons' ,function() 
{
   	var $tr = $('.tr_clone_cons').last('.tr_clone_cons');
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	$clone.find(".items").val("").end();
	
  	$clone.find('.cons-date').daterangepicker({
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
  	
  	setTooltip();
  
});


$("table").on('click','.tbl-row-rem_cons' ,function() {
var cl=$('.tr_clone_cons').length;
if(cl>1){
          
   var $tr = $(this).closest('.tr_clone_cons');
   var $clone = $tr.remove();
   $tr.after($clone);
  
}
  
});

$('.cons-date').daterangepicker({
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



function getConsultdata()
{
	var $billid = $('.billid').val();
	
	$.ajax({

		type : "GET",
		url : "ChssConsultationListAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var consultVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var consultHTMLStr = '';
		for(var c=0;c<consultVals.length;c++)
		{
			var consult = consultVals[c];
			consultHTMLStr +=	'<tr> ';
			consultHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (c+1) +'.</span> </td> ';
			consultHTMLStr +=	'	<td> ';
			consultHTMLStr +=	'		<select class="form-control w-100" name="consult-type-'+consult.ConsultationId+'"  required="required" > ';
																		
										if(consult.ConsultType.toUpperCase() === 'FRESH'){
			consultHTMLStr +=	'			<option value="Fresh" selected >Fresh</option> ';
			consultHTMLStr +=	'			<option value="FollowUp">FollowUp</option> ';
										}else{
			consultHTMLStr +=	'			<option value="Fresh">Fresh</option> ';
			consultHTMLStr +=	'			<option value="FollowUp" selected >FollowUp</option> ';
										}				
			
			consultHTMLStr +=	'		</select> ';
			consultHTMLStr +=	'	</td> ';
			consultHTMLStr +=	' 	<td><input type="text" class="form-control items" name="doc-name-'+consult.ConsultationId+'" id="doc-name" value="'+consult.DocName+'" style="width:100%; "  maxlength="255" required="required"></td> ';
			consultHTMLStr +=	'	<td>';
			
			
			consultHTMLStr +=	'		<select class="form-control w-100" name="doc-qualification-'+consult.ConsultationId+'" id="doc-qualification"  required="required" > ';
			for(var u=0;u<$docrateslist.length;u++){
				
				if(Number(consult.DocQualification) === $docrateslist[u].DocRateId)
				{
					consultHTMLStr +=	'			<option value="'+$docrateslist[u].DocRateId+'" selected >'+$docrateslist[u].DocQualification+'</option>';
				}else{
					consultHTMLStr +=	'			<option value="'+$docrateslist[u].DocRateId+'"  >'+$docrateslist[u].DocQualification+'</option>';										
				}
				
			}
			
			consultHTMLStr +=	'		</select> ';
			
			
			
			
			consultHTMLStr +=	'	</td>';
			
			let now = new Date(consult.ConsultDate);
			var dateString = moment(now).format('DD-MM-YYYY');
			
			consultHTMLStr +=	'	<td><input type="text" class="form-control cons-date" name="cons-date-'+consult.ConsultationId+'" id="cons-date" value="'+dateString+'" style="width:100%;"  maxlength="10" readonly required="required"></td> ';
			
			consultHTMLStr +=	'	<td><input type="number" class="form-control items numberonly " name="cons-charge-'+consult.ConsultationId+'" id="cons-charge" value="'+consult.ConsultCharge+'" style="width:100%;direction: rtl;" min="0" max="9999999" required="req uired" ></td> ';
			consultHTMLStr +=	'	<td>';
			consultHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="consultationid" value="'+consult.ConsultationId+'" formaction="ConsultationBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
			consultHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="consultationid" value="'+consult.ConsultationId+'" formaction="ConsultationBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
			consultHTMLStr +=	'	</td> ';
			consultHTMLStr +=	'</tr> ';
			
		}
		
		if(consultVals.length==0){
			
			consultHTMLStr +=	'<tr><td colspan="7" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		$('#consult-list-table').html(consultHTMLStr);
		
		setTooltip();
		$('.cons-date').daterangepicker({
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
			
		onlyNumbers();
		
		
		
		
		}
	});
	/* $('#nav-consultation-tab').click(); */
}


</script>

<!-- ------------------------------------------------------- consultation script --------------------------------------------------- -->

<!-- ------------------------------------------------------- tests script --------------------------------------------------- -->

<script type="text/javascript">
$("table").on('click','.tbl-row-add-tests' ,function() 
		{
			
		   	var $tr = $('.tr_clone_tests').last('.tr_clone_tests');
		   	var $clone = $tr.clone();
		    $tr.after($clone);
		    $clone.find('.bootstrap-select').replaceWith(function() { return $('select', this); })  ;  
		    $clone.find('.selectpicker').selectpicker('render'); 
		   			   			   	
		   	$clone.find(".items").val("").end();  
			
		  	setTooltip();
		  
		});


		$("table").on('click','.tbl-row-rem_tests' ,function() {
		var cl=$('.tr_clone_tests').length;
		if(cl>1){
		          
		   var $tr = $(this).closest('.tr_clone_tests');
		   var $clone = $tr.remove();
		   $tr.after($clone);
		  
		}
		  
		});




function getTestSubAdd(testrowid)
{
	var testmainid = $('#test-type_'+testrowid).val();
	$.ajax({

		type : "GET",
		url : "GetTestSubListAjax.htm",
		data : {
				
			testmainid : testmainid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var testsubs= Object.keys(result).map(function(e){
			return result[e]
		})
		var subtestsHTML = '<option value="" selected="selected" disabled="disabled">Choose..</option>';
		for(var st=0;st<testsubs.length;st++)
		{
			var subtest = testsubs[st];			
			subtestsHTML += '<option value="'+subtest.TestSubId+'" >';
			subtestsHTML += subtest.TestName ;
			subtestsHTML += '</option>';
		}
		
		if(testsubs.length==0){
			
			 subtestsHTML +='<option value="" selected="selected" disabled="disabled">No Data</option> ';
		}
		
		$('#test-id_'+testrowid).html(subtestsHTML);
		
		setTooltip();
		onlyNumbers();
		}
	});
	
	
}

function getTestSubEdit(testrowid)
{
	var testmainid = $('#test-maintype-'+testrowid).val();
	$.ajax({

		type : "GET",
		url : "GetTestSubListAjax.htm",
		data : {
				
			testmainid : testmainid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var testsubs= Object.keys(result).map(function(e){
			return result[e]
		})
		var subtestsHTML = '<option value="" selected="selected" disabled="disabled">Choose..</option>';
		for(var st=0;st<testsubs.length;st++)
		{
			var subtest = testsubs[st];			
			subtestsHTML += '<option value="'+subtest.TestSubId+'" >';
			subtestsHTML += subtest.TestName ;
			subtestsHTML += '</option>';
		}
		
		if(testsubs.length==0){
			
			 subtestsHTML +='<option value="" selected="selected" disabled="disabled">No Data</option> ';
		}
		
		$('#test-subid-'+testrowid).html(subtestsHTML);
		
		setTooltip();
		onlyNumbers();
		}
	});
}


var $testslist = null;
function getTestsList()
{
	$.ajax({

		type : "GET",
		url : "GetTestsListAjax.htm",
		data : {
			
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		$testslist= Object.keys(result).map(function(e){
			return result[e]
		})
		}
	});	
	
}
getTestsList();

function getTestsData()
{
	var $billid = $('.billid').val();
	 $('#tests-list-table').html('');
	$.ajax({

		type : "GET",
		url : "ChssTestsListAjax.htm",
		async : false,
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var testVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var testsHTMLStr = '';
		testscount = testVals.length;
		for(var t=0;t<testVals.length;t++)
		{
			var test = testVals[t];
					
			testsHTMLStr +=	'<tr> ';
			testsHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (t+1); +'.</span> </td> ';
			
			testsHTMLStr +=	'<td>';
			testsHTMLStr +=	'	<select class="form-control selectpicker" style="width: 100%" name="test-subid-'+test.CHSSTestId+'" id="test-subid-'+test.CHSSTestId+'" required="required" " data-live-search="true" >';
								for(var i=0;i<$testslist.length;i++){
									if($testslist[i].TestSubId === test.TestSubId){
			testsHTMLStr +=	'		<option value="'+$testslist[i].TestMainId+'_'+$testslist[i].TestSubId+'" selected >'+$testslist[i].TestName+'</option>';
									}else
									{
			testsHTMLStr +=	'		<option value="'+$testslist[i].TestMainId+'_'+$testslist[i].TestSubId+'">'+$testslist[i].TestName+'</option>';	
									}
								}
			testsHTMLStr +=	'	</select>';
			testsHTMLStr +=	'</td>';
			
			
			testsHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="test-cost-'+test.CHSSTestId+'"  value="'+test.TestCost+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="req uired" ></td> ';
			testsHTMLStr +=	'	<td>';
			testsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="testid" value="'+test.CHSSTestId+'" formaction="TestBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
			testsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="testid" value="'+test.CHSSTestId+'" formaction="TestBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
			testsHTMLStr +=	'	</td> ';
			testsHTMLStr +=	'</tr> ';
			
			
			
				
		}
		
		$('#tests-list-table').html(testsHTMLStr);
		$('.selectpicker').selectpicker('render'); 
		onlyNumbers();
		
		if(testVals.length==0){
			
			testsHTMLStr +=	'<tr><td colspan="5" style="text-align: center;"> No Record Found</td></tr> ';
			 $('#tests-list-table').html(testsHTMLStr);
		}
		
		
		
		
		setTooltip();
		$('.meds-date').daterangepicker({
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
		


		}
	});
} 

</script>
<!-- ------------------------------------------------------- tests script --------------------------------------------------- -->

<!-- ------------------------------------------------------- medicines script--------------------------------------------------- -->
<script type="text/javascript">


$("table").on('click','.tbl-row-add-meds' ,function() 
{
   	var $tr = $('.tr_clone_meds').last('.tr_clone_meds');
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	$clone.find(".items").val("").end();

  	setTooltip();
  
});


$("table").on('click','.tbl-row-rem_meds' ,function() {
var cl=$('.tr_clone_meds').length;
if(cl>1){
          
   var $tr = $(this).closest('.tr_clone_meds');
   var $clone = $tr.remove();
   $tr.after($clone);
  
}
  
});

function getMedicinesData(){
	
	var $billid = $('.billid').val();
	
	var $treattype = $('#treattypeid').val();
	
	$.ajax({

		type : "GET",
		url : "ChssMedicinesListAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var medsVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var medsHTMLStr = '';
		for(var m=0;m<medsVals.length;m++)
		{
			var meds = medsVals[m];
			medsHTMLStr +=	'<tr> ';
			medsHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (m+1) +'.</span> </td> ';
			
			if(Number($treattype)==1)
			{
				medsHTMLStr +=	' 	<td><input type="text" class="form-control items" name="meds-name-'+meds.CHSSMedicineId+'" id="med-name" value="'+meds.MedicineName+'" style="width:100%; "  maxlength="255" required="required"></td> ';
			}
			else if(Number($treattype)>1)
			{
				medsHTMLStr +=	'	<td><select class="form-control w-100 selectpicker" name="meds-name-'+meds.CHSSMedicineId+'"  required="required" data-live-search="true"   > ';
				medsHTMLStr +=	'		<option value="" disabled selected>Choose...</option> ';
							for(var ml=0;ml<$medsAllowedList.length;ml++)
							{								
								if(meds.MedicineName === $medsAllowedList[ml].MedicineName){
				medsHTMLStr +=	'		<option value="'+$medsAllowedList[ml].MedicineName+'" selected >'+$medsAllowedList[ml].MedicineName+'</option> ';
								}else{
				medsHTMLStr +=	'		<option value="'+$medsAllowedList[ml].MedicineName+'"  >'+$medsAllowedList[ml].MedicineName+'</option> ';
								}
							}			
				medsHTMLStr +=	'	</select> </td>';
			}
			
			medsHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="meds-presquantity-'+meds.CHSSMedicineId+'" id="meds-presquantity" value="'+meds.PresQuantity+'" style="width:100%;" min="1" max="9999999" required="required" ></td> ';
			medsHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="meds-quantity-'+meds.CHSSMedicineId+'" id="meds-quantity" value="'+meds.MedQuantity+'" style="width:100%;" min="1" max="9999999" required="required" ></td> ';
			
			medsHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="meds-cost-'+meds.CHSSMedicineId+'" id="meds-cost" value="'+meds.MedicineCost+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td> ';
			medsHTMLStr +=	'	<td>';
			medsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="medicineid" value="'+meds.CHSSMedicineId+'" formaction="MedicineBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
			medsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="medicineid" value="'+meds.CHSSMedicineId+'" formaction="MedicineBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
			medsHTMLStr +=	'	</td> ';
			medsHTMLStr +=	'</tr> ';
			
		}
		
		if(medsVals.length==0){
			
			medsHTMLStr +=	'<tr><td colspan="6" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		$('#meds-list-table').html(medsHTMLStr);
		
		setTooltip();
		$('.meds-date').daterangepicker({
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
		
		onlyNumbers();
		$('.selectpicker').selectpicker('render'); 
		}
	});
}

</script>
<!-- ------------------------------------------------------- medicines script --------------------------------------------------- -->

<!-- ------------------------------------------------------- Miscellaneous script--------------------------------------------------- -->
<script type="text/javascript">


$("table").on('click','.tbl-row-add-misc' ,function() 
{
   	var $tr = $('.tr_clone_misc').last('.tr_clone_misc');
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	$clone.find(".items").val("").end();

	
   	setTooltip();
  
});


$("table").on('click','.tbl-row-rem_misc' ,function() {
var cl=$('.tr_clone_misc').length;
if(cl>1){
          
   var $tr = $(this).closest('.tr_clone_misc');
   var $clone = $tr.remove();
   $tr.after($clone);
  
}
  
});

function getmiscData(){
	
	var $billid = $('.billid').val();
	
	$.ajax({

		type : "GET",
		url : "ChssMiscListAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var miscVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var miscHTMLStr = '';
		for(var m=0;m<miscVals.length;m++)
		{
			var misc = miscVals[m];
			
			
				miscHTMLStr +=	'<tr> ';
				miscHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (m+1) +'.</span> </td> ';
				
				miscHTMLStr +=	' 	<td><input type="text" class="form-control items" name="misc-name-'+misc.ChssMiscId+'"  value="'+misc.MiscItemName+'" style="width:100%; "  maxlength="255" required="required"></td> ';
				
				let now = new Date(misc.MedicineDate);
				var dateString = moment(now).format('DD-MM-YYYY');
				
				miscHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="misc-cost-'+misc.ChssMiscId+'"  value="'+misc.MiscItemCost+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="req uired" ></td> ';
				miscHTMLStr +=	'	<td>';
				miscHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="chssmiscid" value="'+misc.ChssMiscId+'" formaction="MiscBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
				miscHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="chssmiscid" value="'+misc.ChssMiscId+'" formaction="MiscBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
				miscHTMLStr +=	'	</td> ';
				miscHTMLStr +=	'</tr> ';
			
			
		}
		
		if(miscVals.length==0){
			
			miscHTMLStr +=	'<tr><td colspan="4" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		$('#misc-list-table').html(miscHTMLStr);
		
		setTooltip();
		onlyNumbers();

		}
	});
}

</script>
<!-- ------------------------------------------------------- Miscellaneous script --------------------------------------------------- -->

<!-- ------------------------------------------------------- Others script --------------------------------------------------- -->

<script type="text/javascript">

$("table").on('click','.tbl-row-add-other' ,function() 
		{
			
		   	var $tr = $('.tr_clone_other').last('.tr_clone_other');
		   	var $clone = $tr.clone();
		    $tr.after($clone);
		    $clone.find('.bootstrap-select').replaceWith(function() { return $('select', this); })  ;  
		    $clone.find('.selectpicker').selectpicker('render'); 
		   			   			   	
		   	$clone.find(".items").val("").end();  
		 
		  	setTooltip();
		  
		});


		$("table").on('click','.tbl-row-rem_other' ,function() {
		var cl=$('.tr_clone_other').length;
		if(cl>1){
		          
		   var $tr = $(this).closest('.tr_clone_other');
		   var $clone = $tr.remove();
		   $tr.after($clone);
		  
		}
		  
		});

$OtherItemsList = null;
function getOtherItemsList()
{
	$.ajax({
	
		type : "GET",
		url : "GetOtherItemsListAjax.htm",
		data : {
				
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		$OtherItemsList= Object.keys(result).map(function(e){
			return result[e]
		})
		}
	});
}
getOtherItemsList();
function getOthersDetails()
{
	
	var $billid=$(".billid").val();
	
	$.ajax({

		type : "GET",
		url : "ChssOtherListAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var otherVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var otherHTMLStr = '';
		for(var c=0;c<otherVals.length;c++)
		{
			var other = otherVals[c];
			otherHTMLStr +=	'<tr> ';
			otherHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (c+1) +'.</span> </td> ';
			otherHTMLStr +=	'	<td> ';
			otherHTMLStr +=	'		<select class="form-control w-100 selectpicker" name="otheritemid-'+other.CHSSOtherId+'"  required="required" data-live-search="true"   > ';
										for(var oi=0;oi<$OtherItemsList.length;oi++)
										{								
											if(other.OtherItemId === $OtherItemsList[oi].OtherItemId){
			otherHTMLStr +=	'					<option value="'+$OtherItemsList[oi].OtherItemId+'" selected >'+$OtherItemsList[oi].OtherItemName+'</option> ';
											}else{
			otherHTMLStr +=	'					<option value="'+$OtherItemsList[oi].OtherItemId+'"  >'+$OtherItemsList[oi].OtherItemName+'</option> ';
											}
										}
			
			otherHTMLStr +=	'		</select> ';
			otherHTMLStr +=	'	</td> ';
			
			otherHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="otheritemcost-'+other.CHSSOtherId+'" value="'+other.OtherItemCost+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="req uired" ></td> ';
			otherHTMLStr +=	'	<td>';
			otherHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="chssotherid" value="'+other.CHSSOtherId+'" formaction="OtherBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
			otherHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="chssotherid" value="'+other.CHSSOtherId+'" formaction="OtherBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
			otherHTMLStr +=	'	</td> ';
			otherHTMLStr +=	'</tr> ';
			
		}
		
		if(otherVals.length==0){
			
			otherHTMLStr =	'<tr><td colspan="4" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		$('#other-list-table').html(otherHTMLStr);
		$('.selectpicker').selectpicker('render'); 
		setTooltip(); 
		onlyNumbers();

		}
	});
	
	
	
}

</script>

<!-- ------------------------------------------------------- Others script --------------------------------------------------- -->




</body>
</html>