<%@page import="java.time.LocalDate"%>
<%@page import="com.vts.ems.chss.model.CHSSConsultMain"%>
<%@page import="com.vts.ems.chss.model.CHSSApply"%>
<%@page import="com.vts.ems.chss.model.CHSSApproveAuthority"%>
<%@page import="com.vts.ems.chss.model.CHSSMedicinesList"%>
<%@page import="com.vts.ems.chss.model.CHSSTestSub"%>
<%@page import="com.vts.ems.master.model.CHSSDoctorRates"%>
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

.nav-link-btn
{	
	padding :10px 3px;
	margin : 3px 0px;
}

.nav-link-btn:hover {
	background-color: #F5C6A5;
	
}


.nav-pills .nav-link.show, .nav-pills .show>.nav-link 
{
	background-color: #750550;
}

</style>
</head>
<body>

<%
	
	Object[] employee = (Object[] )request.getAttribute("employee") ;
	Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
	String isself = chssapplydata[3].toString();
	List<Object[]> chssbillslist=(List<Object[]>)request.getAttribute("chssbillslist");	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();	
	List<CHSSTestSub> testmainlist = (List<CHSSTestSub>)request.getAttribute("testmainlist");	
	List<CHSSOtherItems> otheritemslist = (List<CHSSOtherItems>)request.getAttribute("otheritemslist");
	List<CHSSDoctorRates> doctorrates = (List<CHSSDoctorRates>)request.getAttribute("doctorrates");
	List<CHSSMedicinesList> allowedmeds =(List<CHSSMedicinesList>)request.getAttribute("allowedmed");
	
	CHSSConsultMain consultmain =(CHSSConsultMain) request.getAttribute("consultmain");
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
	String consultmainid =(String)request.getAttribute("consultmainid");
	String tab =(String)request.getAttribute("tab");
	
	LocalDate minbilldate = LocalDate.now().minusMonths(3);
	
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Apply - <%=chssapplydata[6] %></h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Data</li>
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
				<div class="card-body main-card " >
					<div class="card" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
						
						<div class="row">
								
							<%if(isself.equalsIgnoreCase("N")){
								Object[] familyMemberData = (Object[])request.getAttribute("familyMemberData") ; %>
								<div class="col-3" >
									<b>Patient Name : &nbsp;</b> <%=familyMemberData[1] %>
								</div>
								<div class="col-3">
									<b>Relation : &nbsp;</b><%=familyMemberData[7] %>
									
								</div>
							<% 
								if(familyMemberData[8]!=null && LocalDate.parse(familyMemberData[8].toString()).isAfter(minbilldate)){
									minbilldate = LocalDate.parse(familyMemberData[8].toString());
								}
							
							}else{ %>
								<div class="col-3">
									<b> Patient Name : &nbsp;</b><%=employee[2] %>
								</div>
								<div class="col-3">
									<b>Relation : &nbsp;</b>SELF
								</div>
							<%} %>
							<div class="col-3">
								<b>Treatment Type : </b> <%=chssapplydata[10] %>																	
							</div>
							<div class="col-3">
								<b>Ailment/Disease/Accident : </b><%=chssapplydata[17] %>
							</div>
						</div>
						<br>
						<div class="row">
								<div class="col-3">
									<b>Claim Id : </b><%=chssapplydata[16] %>
								</div>
								<div class="col-3">
									<b>Consulted Doctor : </b><%=consultmain.getDocName() %>
								</div>
								<div class="col-3">
									<%-- <b>Consulted Date: </b><%=DateTimeFormatUtil.SqlToRegularDate(consultmain.getConsultDate() ) %> --%>
								</div>
							</div>
						</div>
							<form method="post" action="#" autocomplete="off"  >
									<div class="table-responsive">
									<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:20%;" >Hospital / Medical / Diagnostics Center Name</th>
												<th style="width:10%;" >Bill / Receipt No.</th>
												<th style="width:10%;" >Bill Date</th>
												<th style="width:10%; text-align: right;">Paid Amt (&#8377;)</th>
												<th style="width:10%; text-align: right;">Discount (&#8377;)</th>
												<th style="width:10%; text-align: right;">Discount (%)</th>
												<th style="width:10%;" >Action  </th>
											</tr>
										</thead>
										<tbody >
										<%	int sno=0;
										for(Object[] obj : chssbillslist){
											sno++;%>											
											<tr class="" >
												<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno %></span> </td>
												<td> <input type="text" class="form-control items" name="centername-<%=obj[0]%>" value="<%=obj[3] %>" style="width:100%; "  maxlength="500" required="required"></td>
												<td> <input type="text" class="form-control items" name="billno-<%=obj[0]%>" value="<%=obj[2] %>" style="width:100%;"   maxlength="25" required="required"></td>
												<td> <input type="text" class="form-control billdate" name="billdate-<%=obj[0]%>" value="<%=rdf.format(sdf.parse(obj[4].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
												<td> <input type="number" class="form-control items cost-only " step=".01" name="finalbillamount-<%=obj[0]%>" id="finalbillamount-<%=obj[0]%>"  onkeyup="enableDiscount('<%=obj[0]%>')" value="<%=obj[7]%>" style="width:100%;text-align: right; " min="1" max="9999999" required="required"></td>
												<td> <input type="number" class="form-control items cost-only " step=".01" name="Discount-<%=obj[0]%>" id="DiscountAmt-<%=obj[0]%>" onkeyup ="calculateDiscountPer('<%=obj[0]%>');" value="<%=obj[6] %>" style="width:100%;text-align: right; " min="0" max="9999999" required="required" ></td>
												<td> <input type="number" class="form-control items cost-only " step=".1" name="DiscountPer-<%=obj[0]%>" id="DiscountPer-<%=obj[0]%>" readonly="readonly" value="<%=Double.parseDouble(obj[8].toString()) %>" style="width:100%;text-align: right; " min="0" max="100" required="required" ></td>
												<td>
													<%-- <%if(Double.parseDouble(obj[9].toString())==0){ %> --%>
													<button type="submit"  class="btn btn-sm update-btn" formaction="CHSSBillEdit.htm" Onclick="return confirm('Are You Sure To Update?');" name="billid" value="<%=obj[0]%>" > <!-- data-toggle="tooltip" data-placement="top" title="Update Bill" -->														
														update
													</button>
													<%-- <%} %> --%>
													<button type="button"  class="btn btn-sm" style="background-color: #34B3F1;color:#ffffff;"  Onclick="showBillDetails('<%=obj[0]%>')" name="billid" value="<%=obj[0]%>"  > <!-- data-toggle="tooltip"  data-placement="top" title="Bill Details" -->
														details
													</button>	
													<button type="submit"  class="btn btn-sm" formaction="CHSSBillDelete.htm" Onclick="return confirm('Are You Sure To Delete?');" name="billid" value="<%=obj[0]%>" >  <!-- data-toggle="tooltip" data-placement="top" title="Delete Bill" -->
														<i class="fa-solid fa-trash-can" style="color: red;"></i>
													</button>
												</td>										
											</tr>											
											<%} %>
											<%if(sno==0){ %>
												<tr>
													<td colspan="9" style="text-align: center ;">
														Bills Not Added
													</td>
												</tr>
											<%} %>
										</tbody>							
										
									</table>
								</div>
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
							</form>
						
						
							<form method="post" action="CHSSBillAdd.htm" autocomplete="off" >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
								<div class="table-responsive">
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
									<tbody>
										<tr class="" >
											<td style="width:5%;text-align: center;"><span class="sno" id="sno"><%=++sno %></span> </td>
											<td style="width:20%;" ><input type="text" class="form-control items" name="centername"  value="" style="width:100%; "  maxlength="500" required="required"></td>
											<td style="width:10%;" ><input type="text" class="form-control items" name="billno"  value="" style="width:100%;"   maxlength="25" required="required"></td>
											<td style="width:10%;" ><input type="text" class="form-control billdate" name="billdate"  value="" style="width:100%; "  maxlength="10" readonly required="required"></td>
											<td style="width:10%;" > <input type="number" class="form-control items cost-only" step=".01"  name="finalbillamount"  id="finalbillamount-" Onclick="this.select();" onkeyup="enableDiscount('')" value="0.00" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
											<td style="width:10%;" > <input type="number" class="form-control items cost-only" step=".01" name="DiscountAmt" id="DiscountAmt-" Onclick="this.select();"  onkeyup="calculateDiscountPer('');" onchange="calculateDiscountPer('');" value="0.00" style="width:100%;text-align: right; " min="0" max="9999999" readonly="readonly" required="required" ></td>
											<td style="width:10%;" > <input type="number" class="form-control items cost-only" step=".1" name="DiscountPer" id="DiscountPer-" value="0.0" style="width:100%;text-align: right; " min="0" max="9999999" required="required" readonly="readonly" ></td>
											<td style="width:10%;" >
												<button type="submit"  class="btn btn-sm add-btn "  name="action" value="add" >Add</button> <!-- Onclick="return confirm('Are You Sure To Add ?');" -->
											</td>										
										</tr>
									</tbody>	
								</table>
								</div>
							</form>
					
						<form action="CHSSFormEdit.htm" method="post" id="form2">
							<div class="row">
								
								<div class="col-md-12" align="center" style="margin-top: 5px;">
									<button type="submit" class="btn btn-sm back-btn" formaction="CHSSConsultMainData.htm" formnovalidate="formnovalidate" style="background-color: #541690;color: white;" name="claimaction" >
										Back	
									</button>
									<%if(chssbillslist.size()>0){ %>
									<button type="submit" class="btn btn-sm preview-btn" name="claimaction" value="F"  data-toggle="tooltip" data-placement="top" title="Preview and Forward">
										<i class="fa-solid fa-forward" style="color: #084594"></i> Preview	
									</button>
									<%} %>									
									<input type="hidden" name="isapproval" value="N">
									<input type="hidden" name="show-edit" value="N">
									<input type="hidden" name="view_mode" value="UF">
								</div>
							</div>
		
							<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
							<input type="hidden" name="claimaction" value="F">
							<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
					
					<%-- <form action="CHSSForm.htm" method="post" id="previewform" target="_blank">	
						<input type="hidden" name="claimaction" value="F">	
						<input type="hidden" name="isapproval" value="N">
						<input type="hidden" name="show-edit" value="N">
						<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
						<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form> --%>
					
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
				    	<b><span>Bill No : <span id="modal-billno" style="color: blue;" ></span></span> </b>
				    </div>
				    <div class="col-3">
				    	<b><span>Medical Center : <span id="modal-centername" style="color: blue;" ></span></span></b>
				    </div>
				    <div class="col-3">
				    	<b><span>Bill Date : <span id="modal-billdate" style="color: blue;" ></span></span></b>
				    </div>
				    <div class="col-3">
				    	<b><span>Bill Amount : &#8377;  <span id="modal-billamount" style="color: blue;" ></span></span></b>
				    </div>
			    </div>
			    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			    	<i class="fa-solid fa-xmark" aria-hidden="true" ></i>
			    </button>
		    </div>
			<div class="modal-body" style="min-height: 30rem;">
		
			   <div class="row" >
			   		<div class="col-2" style="padding: 0px 5px 0px 5px;">
				    	<div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" style="box-shadow: 5px 6px 10px rgba(0, 0, 0, 0.25);min-height: 28rem;border-radius: 5px;" >
				    		<a class="nav-link nav-link-btn btn co"  data-toggle="tab" id="nav-consultation-tab" href="#nav-consultation" role="tab" aria-controls="nav-consultation"  Onclick="getConsultdata();"  >Consultation</a>
				    		<a class="nav-link nav-link-btn btn te"  data-toggle="tab" id="nav-tests-tab" href="#nav-tests" role="tab" aria-controls="nav-tests"   Onclick="getTestsData();"  >Tests / Procedures</a>
				    		<a class="nav-link nav-link-btn btn me"  data-toggle="tab" id="nav-medicines-tab" href="#nav-medicines" role="tab" aria-controls="nav-medicines" Onclick="getMedicinesData();"  >Medicines</a>
				    		<a class="nav-link nav-link-btn btn ot"  data-toggle="tab" id="nav-others-tab" href="#nav-others" role="tab" aria-controls="nav-others" Onclick="getOthersDetails()" >Others</a>
				    		<a class="nav-link nav-link-btn btn mi" data-toggle="tab" id="nav-misc-tab" href="#nav-misc" role="tab" aria-controls="nav-misc" Onclick="getmiscData()" >Miscellaneous</a>
				    	</div>
				    				    	
				    </div>
			   <div class="col-10" style="padding:0px;  " >
				   <div class="tab-content card-body " id="nav-tabContent" style="padding: 0px;" >
	<!-- ------------------------------------------------------- consultation --------------------------------------------------- -->		
						<div align="center">
							<%if(ses1!=null){ %>
								<div class="alert alert-danger" role="alert">
									<%=ses1 %>
								</div>
								
							<%}if(ses!=null){ %>
								
								<div class="alert alert-success" role="alert">
									<%=ses %>
								</div>
							<%} %>
						</div>   
				   		<div class="tab-pane fade show " id="nav-consultation" role="tabpanel" aria-labelledby="nav-consultation-tab">
				   			
					   		<div class="col-md-12" >
					    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
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
									<input type="hidden" name="consultmainid-old" value="<%=consultmain.getCHSSConsultMainId()%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
					   		<div class="col-md-12" id="consult-add-form" >
					    		<form action="ConsultationBillAdd.htm"  method="post" autocomplete="off" style="width: 100%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:30%;">Name of the Doctor</th>
												<th style="width:20%;">Qualification</th>
												<th style="width:15%;">Date</th>
												<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											</tr>
										</thead>
										<tbody>
											<tr class="tr_clone_cons" >
												<td><input type="text" class="form-control items" name="doc-name" id="doc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
												<td>
													<select class="form-control w-100" name="doc-qualification-view" id="doc-qualification" disabled="disabled" >
														<%int docqual=0;
														for(CHSSDoctorRates rate:doctorrates ){ %>
															<option value="<%=rate.getDocRateId() %>"><%=rate.getDocQualification() %></option>
														<%} %>
													</select>
													<input type="hidden" name="doc-qualification" value=""  id="doc-qualification-val" >
												</td>
												<td><input type="text" class="form-control cons-date" name="cons-date" id="cons-date" value="" style="width:100%;"  maxlength="10" readonly required="required"></td>
												<td><input type="number" class="form-control items cost-only co-cost"  step=".01"  name="cons-charge" id="cons-charge" value="0" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
											</tr>
										</tbody>							
										
									</table>
									<div align="center">
										<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return itemAddEligibleCheck('co')" >Add</button>  <!-- Onclick="return confirm('Are You Sure To Submit?');"  -->	
									</div>
									<input type="hidden" class="billid" name="billid" value="">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid-old" value="<%=consultmain.getCHSSConsultMainId()%>">
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
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
												<th style="width:35%;">Test / Procedure</th>
												<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:10%;" > Action </th>
											</tr>
										</thead>
										<tbody id="tests-list-table">
											
	
										</tbody>
									</table>
									<input type="hidden" class="billid" name="billid" value="">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
					   		<div class="col-md-12" >
					    		<form action="TestsBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:75%;">Test / Procedure</th>
												<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:5%;" > <button type="button" class="btn btn-sm tbl-row-add-tests"  data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button>  </th>
											</tr>
										</thead>
										<tbody>
											<%for(int i=0;i<2;i++){ %>
											<tr class="tr_clone_tests"  id="tr_clone_tests">
												<td style="max-width:35% !important;">
													<select class="form-control test-type  selectpicker " id="test-type_1" style="width: 100%" data-size="auto" name="test-id"  data-live-search="true" data-container="body" >
													
														<option value="" selected="selected" disabled="disabled">Choose..</option>
														<%for(CHSSTestSub testsub : testmainlist){ %>
															<option value="<%= testsub.getTestMainId()%>_<%= testsub.getTestSubId() %>"><%=testsub.getTestName()%></option>
														<% } %>
													</select>
												</td>
												<td><input type="number" class="form-control items cost-only te-cost"  step=".01"  name="tests-cost"  value="" style="width:100%;text-align: right; " min="1" max="9999999"  ></td>
												<td><button type="button" class="btn btn-sm tbl-row-rem_tests"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
											</tr>
											<%} %>
										</tbody>							
										
									</table>
									<div align="center">
										<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return itemAddEligibleCheck('te')">Add</button>	<!--  Onclick="confirm('Are You Sure To Submit?');" -->
									</div>
									<input type="hidden" class="billid" name="billid" value="">
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
					    	</div>
				   		</div>
	<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->
	<!-- ------------------------------------------------------- medicines --------------------------------------------------- -->			   		
				   		<div class="tab-pane fade " id="nav-medicines" role="tabpanel" aria-labelledby="nav-medicines-tab" >
				   		
					   		<div class="col-md-12" >
					   			<div class="row" align="center" style="margin-bottom: 10px;margin-top:5px; ">
					   				<%if(Long.parseLong(chssapplydata[0].toString())!=consultmain.getCHSSApplyId()){ %>
					   				<div class="col-12" align="left">
					   					<span class="btn " style="background-color: #F806CC; color: white;">
					   					<b>Get Medicines From Previous Consultation of this Doctor:</b> 
					   					<input type="checkbox" name="" id="old-consult" value="<%=consultmain.getCHSSConsultMainId() %>" Onclick="FillMedsList()" >
					   				</span>
					   				</div>
					   				<%} %>
					   				
					   			</div>
					   		
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
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
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
										<tbody  id="meds-add-tbody">
										
										</tbody>
										<tbody>
											<%for(int i=0;i<4;i++){ %>
											<tr class="tr_clone_meds" >
												<td>
													<%if(Integer.parseInt(chssapplydata[7].toString())==1){ %>
													<input type="text" class="form-control items" name="meds-name" id="meds-name" value="" style="width:100%; "  maxlength="255" >
													<%}else{ %>
														<select class="form-control selectpicker " name="meds-name"  style="width: 100%" data-live-search="true"  >
															<option value="" selected="selected" disabled="disabled">Choose..</option>
															<%for(CHSSMedicinesList medicine : allowedmeds ){ %>
																<option value="<%=medicine.getMedicineName()%>"><%=medicine.getMedicineName() %></option>
															<%} %>
														</select>
													<%} %>
												</td>
												<td><input type="number" class="form-control items numberonly med-qty" name="meds-presquantity" id="meds-quantity" value="0" Onclick="this.select();" style="width:100%;" min="0" max="9999999"  ></td>
												<td><input type="number" class="form-control items numberonly med-qty" name="meds-quantity" id="meds-quantity" value="0" Onclick="this.select();" style="width:100%;" min="0" max="9999999"  ></td>
												<td><input type="number" class="form-control items cost-only me-cost"  step=".01"  name="meds-cost" id="meds-cost" value="" style="width:100%;text-align: right; "  max="9999999"  ></td>
												<td><button type="button" class="btn btn-sm tbl-row-rem_meds"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
											</tr>
											<%} %>
										</tbody>							
										
									</table>
									<div align="center">
										<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return itemAddEligibleCheck('me')" >Add</button>	 <!--  Onclick="return confirm('Are You Sure To Submit?');"  -->
									</div>
									<input type="hidden" class="billid" name="billid" value="">
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
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
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
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
														<option value="" selected="selected" disabled="disabled">Choose..</option>
														<%for(int k=0 ;k<otheritemslist.size();k++){ %>
															<option value="<%=otheritemslist.get(k).getOtherItemId() %>"><%=otheritemslist.get(k).getOtherItemName() %></option>
														<%} %>
													</select>
												</td>
												<td><input type="number" class="form-control items cost-only ot-cost"  step=".01" name="otheritemcost" value="" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td><button type="button" class="btn btn-sm tbl-row-rem_other"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
											</tr>
										</tbody>							
										
									</table>
									<div align="center">
										<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return itemAddEligibleCheck('ot')" >Add</button>	<!--  Onclick="return confirm('Are You Sure To Submit?');"  -->
									</div>
									<input type="hidden" class="billid" name="billid" value="">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
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
												<th style="width:55%;"> Item Name </th>
												<th style="width:10%; text-align: center; ;">Qty</th>
												<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:10%;" > Action </th>
											</tr>
										</thead>
										<tbody id="misc-list-table">
											
	
										</tbody>
									</table>
									<input type="hidden" class="billid" name="billid" value="">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
					   		<div class="col-md-12" >
					    		<form action="MiscBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:60%;"> Item Name </th>
												<th style="width:10%; text-align: center; ;">Qty</th> 
												<th style="width:20%; text-align: right;">Amount  (&#8377;)</th>
												<th style="width:10%;" > <button type="button" class="btn btn-sm tbl-row-add-misc" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
											</tr>
										</thead>
										<tbody class="tr_other_add">
											<tr class="tr_clone_misc" >
												<td><input type="text" class="form-control items" name="misc-name" id="misc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
												<td><input type="number" class="form-control items numberonly" name="misc-count" id="misc-count" value="0" style="width:100%;" min="0" max="999999" required="required" ></td>
												<td><input type="number" class="form-control items cost-only mi-cost"  step=".01"  name="misc-cost" id="misc-cost" value="" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td><button type="button" class="btn btn-sm tbl-row-rem_misc"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
											</tr>
										</tbody>			
									</table>
									<div align="center">
										<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return itemAddEligibleCheck('mi')" >Add</button>	 <!--  Onclick="return confirm('Are You Sure To Submit?');" -->
									</div>
									<input type="hidden" class="billid" name="billid" value="">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
					    	</div>
				   			
				   		</div>
	<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->			   		
					 
					   </div>
					
			  </div>
			  		
			  
			  </div>
			</div>
		      
		    <div class="modal-header  w-100">
		    	<div class="row w-100" >
		    		<div class="col-md-9">
		    			<form action="CHSSFormEdit.htm" method="post" id="form2">
							<button type="button" class=" btn btn-sm " data-dismiss="modal" aria-label="Close" style="background-color: #541690;color: white;" name="claimaction" >
								Back	
							</button>
							<button type="submit" class="btn btn-sm preview-btn" name="claimaction" value="F" data-toggle="tooltip" data-placement="top" title="Preview and Forward">
								<i class="fa-solid fa-forward" style="color: #084594"></i> Preview	
							</button>
							<input type="hidden" name="view_mode" value="UF">
							<input type="hidden" name="isapproval" value="N">
							<input type="hidden" name="show-edit" value="N">
							<input type="hidden" name="consultmainid" value="<%=consultmainid%>">
							<input type="hidden" name="claimaction" value="F">
							<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
		    		
		    		</div>
		    		<div class="col-md-3" style="float: right;">
			    		<b>Total :&nbsp;&nbsp;</b>&#8377;&nbsp;<span id="modal-items-total"></span>
		    		</div>
		    	</div>
		    </div>
		</div>
	</div>
</div>


<input type="hidden" name="treattype" id="treattypeid" value="<%=chssapplydata[7]%>">

<script type="text/javascript">

var threeMonthsAgo = new Date('<%=minbilldate%>');


function calculateDiscountPer($id)
{
	var disAmt = Number($('#DiscountAmt-'+$id).val());
	var billAmt = Number($('#finalbillamount-'+$id).val());
	
	if(billAmt===0 ){
		$('#DiscountPer-'+$id).val("0.00");
		$('#DiscountAmt-'+$id).val("0.00");
	}else{
		var discPer = (100*disAmt)/(billAmt+disAmt);
		$('#DiscountPer-'+$id).val(parseFloat(discPer.toFixed(6)));
		
		$('#DiscountAmt-'+$id).attr('max',billAmt);
		$('#GSTAmt-'+$id).attr('max',billAmt);
	}
}

function enableDiscount($id)
{
	var billAmt = $('#finalbillamount-'+$id).val();
	if(Number(billAmt)>0){
		$('#DiscountAmt-'+$id).prop("readonly", false);
		$('#DiscountAmt-'+$id).attr('max',billAmt);
		calculateDiscountPer($id);
	}else
	{
		$('#DiscountAmt-'+$id).prop("readonly", true);
		
	}
	calculateDiscountPer($id);
}


</script>


 
<!-- -------------------------------------------------------modal script --------------------------------------------------- -->

<script type="text/javascript">

var itemstotal = 0;
var billamount = 0;

var $billdate=new Date();
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
						
			$('#modal-items-total').html(result[8]);
			itemstotal = Number(result[8]);
			billamount = result[5]+result[7];
			$billdate =new Date(result[4]); 
			if(result[5]!=null){
				$('#modal-billamount').html(parseFloat((Number(result[5]) + Number(result[7])).toFixed(2)));
			
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

function itemAddEligibleCheck(itemtype)
{ 
	var total = 0;
	$('.'+itemtype+'-cost').each(function(i, obj) {		    
	    total += Number(obj.value);
	});
	
	
	if(Math.round(itemstotal+total)<=Math.round(billamount)){
		return true;
	}else
	{
		alert('Items Total Cost is Exceeding Bill Amount.\nPlease Check all Details in the Bill');
		return false;	
	}
}



function itemEditEligibleCheck(itemtype,itemid)
{ 	
	var itemoldcost=Number( $('#'+itemtype+'-cost-org-'+itemid).val());
	var itemnewcost=Number( $('#'+itemtype+'-cost-'+itemid).val());
	
	
	if(Math.round(itemstotal+itemnewcost-itemoldcost)<=Math.round(billamount)){
		return confirm ('Are You Sure To Update ?');
	}else
	{
		alert('Items Total Cost is Exceeding Bill Amount.\nPlease Check all Details in the Bill');
		return false;	
	}
	return false;
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

function  onlyNumbers() {    
    	
	
	  $('.cost-only').on("click", function() {
          $(this).select();
      });
	  
	
	 	$('.numberonly').keypress(function (e) {    

        var charCode = (e.which) ? e.which : event.keyCode    

        if (String.fromCharCode(charCode).match(/[^0-9]/g))    

            return false;                        

  		});
    
	 $('.cost-only').keypress( function (evt) {

	    if (evt.which > 31 &&  (evt.which < 48 || evt.which > 57) && evt.which!=46 )
	    {
	        evt.preventDefault();
	    } 
		
	    
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


$(document).ready( function() 
		{
	onlyNumbers();
	getdoctorrates(<%=chssapplydata[7]%>);
	MedsAllowedList();
	<%if(billid!=null && !billid.equalsIgnoreCase("null") && Long.parseLong(billid)>0 ){%>
		showBillDetails(<%=billid%>);
	<%}%> 
	
});   


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
		
		if(consultVals.length>0){
			var consult = consultVals[0];
						
			consultHTMLStr +=	'<tr> ';
			consultHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ 1 +'.</span> </td> ';
			/* consultHTMLStr +=	'	<td> ';
			consultHTMLStr +=	'		<select class="form-control w-100" name="consult-type-'+consult.ConsultationId+'"  required="required" > ';
																		
										if(consult.ConsultType.toUpperCase() === 'FRESH'){
			consultHTMLStr +=	'			<option value="Fresh" selected >Fresh</option> ';
			consultHTMLStr +=	'			<option value="FollowUp">FollowUp</option> ';
										}else{
			consultHTMLStr +=	'			<option value="Fresh">Fresh</option> ';
			consultHTMLStr +=	'			<option value="FollowUp" selected >FollowUp</option> ';
										}				
			
			consultHTMLStr +=	'		</select> ';
			consultHTMLStr +=	'	</td> '; */
			consultHTMLStr +=	' 	<td><input type="text" class="form-control items" name="doc-name-'+consult.ConsultationId+'" value="'+consult.DocName+'" style="width:100%; " readonly maxlength="255" required="required"></td> ';
			consultHTMLStr +=	'	<td>';
			
			
			consultHTMLStr +=	'		<select class="form-control w-100" name="doc-qualification-'+consult.ConsultationId+'" required="required" disabled > ';
			for(var u=0;u<$docrateslist.length;u++)
			{				
				if(Number(consult.DocQualification) === $docrateslist[u].DocRateId)
				{
					consultHTMLStr +=	'			<option value="'+$docrateslist[u].DocRateId+'" selected >'+$docrateslist[u].DocQualification+'</option>';
				}else{
					consultHTMLStr +=	'			<option value="'+$docrateslist[u].DocRateId+'"  >'+$docrateslist[u].DocQualification+'</option>';										
				}				
			}
			
			consultHTMLStr +=	'		</select> ';
			
			consultHTMLStr +=	'	</td>';
			
			/* let now = new Date(consult.ConsultDate);
			var dateString = moment(now).format('DD-MM-YYYY'); */
			
			/* consultHTMLStr +=	'	<td><input type="text" class="form-control cons-date " name="cons-date-'+consult.ConsultationId+'"  value="'+dateString+'" style="width:100%;"  maxlength="10" readonly required="required"></td> '; */
			consultHTMLStr +=	'	<td><input type="text" class="form-control cons-date " name="cons-date-'+consult.ConsultationId+'" style="width:100%;"  maxlength="10" readonly required="required"></td> ';
			
			consultHTMLStr +=	'	<td>';
			consultHTMLStr +=	'		<input type="number" class="form-control items cost-only "  step=".01"  name="cons-charge-'+consult.ConsultationId+'" id="co-cost-'+consult.ConsultationId+'" value="'+consult.ConsultCharge+'" style="width:100%;text-align: right; " min="1" max="9999999" required="required" >';
			consultHTMLStr +=	'		<input type="hidden" id="co-cost-org-'+consult.ConsultationId+'" value="'+consult.ConsultCharge+'" >';
			consultHTMLStr +=	'	</td> ';
			consultHTMLStr +=	'	<td>';
			consultHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="consultationid" value="'+consult.ConsultationId+'" formaction="ConsultationBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update" Onclick="return itemEditEligibleCheck(\'co\',\''+consult.ConsultationId+'\')"  ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; /* Onclick="return confirm(\'Are You Sure To Update ?\');" */ 
			consultHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="consultationid" value="'+consult.ConsultationId+'" formaction="ConsultationBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
			consultHTMLStr +=	'	</td> ';
			consultHTMLStr +=	'</tr> ';
			
			$('#consult-list-table').html(consultHTMLStr);
			
			$('.cons-date').daterangepicker({
				"singleDatePicker" : true,
				"linkedCalendars" : false,
				"showCustomRangeLabel" : true,
				"startDate" : new Date(consult.ConsultDate),
				
				"minDate":$billdate, 
				"cancelClass" : "btn-default",
				showDropdowns : true,
				locale : {
					format : 'DD-MM-YYYY'
				}
			});
			
			$('#consult-add-form').hide();
		
		}
		if(consultVals.length==0){
			
			consultHTMLStr +=	'<tr><td colspan="7" style="text-align: center;"> No Record Found</td></tr> ';
			$('#consult-list-table').html(consultHTMLStr);			
			$('#consult-add-form').show();
			
			
			/* ------------------------------------------------------- */				
				$.ajax({
			
						type : "GET",
						url : "ConsultMainDataAjax.htm",
						data : {
												
							consultmainid : <%=consultmainid%>,
							chssapplyid : <%=chssapplydata[0]%>,
						},
						datatype : 'json',
						success : function(result) {
							var result = JSON.parse(result);
							$('#doc-name').val(result.DocName);
							$('#doc-name').prop('readonly','readonly');
							
							$('#doc-qualification-val').val(result.DocQualification);
							$('#doc-qualification').val(result.DocQualification);
							
							
							
							/* let consdate = new Date($billdate);
							var dateString = moment(consdate).format('DD-MM-YYYY');
												
							$('#cons-date').val(dateString);  */
							
							/* 					
								$('.te').prop('disabled', true);
								$('.me').prop('disabled', true);
								$('.ot').prop('disabled', true);
								$('.mi').prop('disabled', true); 
							*/
							$('.cons-date').daterangepicker({
								"singleDatePicker" : true,
								"linkedCalendars" : false,
								"showCustomRangeLabel" : true,
								"startDate" : $billdate,
								
								"minDate":$billdate, 
								"cancelClass" : "btn-default",
								showDropdowns : true,
								locale : {
									format : 'DD-MM-YYYY'
								}
							});
										
						}
				});
			/* ------------------------------------------------------- */				
			 
			
			
			
		}
				
		setTooltip();
		
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
			treattypeid : <%=chssapplydata[7]%>,
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
			
			
			testsHTMLStr +=	'	<td>';
			testsHTMLStr +=	'		<input type="number" class="form-control items cost-only"  step=".01"  name="test-cost-'+test.CHSSTestId+'" id="te-cost-'+test.CHSSTestId+'" value="'+test.TestCost+'" style="width:100%;text-align: right; " min="1" max="9999999" required="required" > ';
			testsHTMLStr +=	'		<input type="hidden" id="te-cost-org-'+test.CHSSTestId+'"  value="'+test.TestCost+'" > ';
			testsHTMLStr +=	'	</td>';
			testsHTMLStr +=	'	<td>';
			testsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="testid" value="'+test.CHSSTestId+'" formaction="TestBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update" Onclick="return itemEditEligibleCheck(\'te\',\''+test.CHSSTestId+'\')"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
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
		
		
		
		onlyNumbers();
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
   	$clone.find(".med-qty").val("0").end();
    $clone.find('.bootstrap-select').replaceWith(function() { return $('select', this); })  ;  
    $clone.find('.selectpicker').selectpicker('render'); 
  	setTooltip();
  	onlyNumbers();
  
});


$("table").on('click','.tbl-row-rem_meds' ,function() {
var cl=$('.tr_clone_meds').length;
if(cl>1){
          
   var $tr = $(this).closest('.tr_clone_meds');
   var $clone = $tr.remove();
   $tr.after($clone);
  
}
  
});

$("table").on('click','.tbl-row-rem_meds_old' ,function() {
	
	var cl=$('.tr_clone_meds').length;
	if(cl>1){
	          
	   var $tr = $(this).closest('.tr_clone_meds_old');
	   var $clone = $tr.remove();
	   $tr.after($clone);
	  
	}
	  
});


var medsVals;
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
		medsVals= Object.keys(result).map(function(e){
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
				medsHTMLStr +=	'	<td><select class="form-control w-100 selectpicker added-meds" name="meds-name-'+meds.CHSSMedicineId+'"  required="required" data-live-search="true"   > ';
				medsHTMLStr +=	'		<option value="" disabled selected>Choose...</option> ';
							for(var ml=0;ml<$medsAllowedList.length;ml++)
							{								
								if(meds.MedicineName.toUpperCase().trim() === $medsAllowedList[ml].MedicineName.toUpperCase().trim()){
				medsHTMLStr +=	'		<option value="'+$medsAllowedList[ml].MedicineName+'" selected >'+$medsAllowedList[ml].MedicineName+'</option> ';
								}else{
				medsHTMLStr +=	'		<option value="'+$medsAllowedList[ml].MedicineName+'"  >'+$medsAllowedList[ml].MedicineName+'</option> ';
								}
							}			
				medsHTMLStr +=	'	</select> </td>';
			}
			
			medsHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="meds-presquantity-'+meds.CHSSMedicineId+'" id="meds-presquantity" value="'+meds.PresQuantity+'" style="width:100%;" min="1" max="9999999" required="required" ></td> ';
			medsHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="meds-quantity-'+meds.CHSSMedicineId+'" id="meds-quantity" value="'+meds.MedQuantity+'" style="width:100%;" min="1" max="9999999" required="required" ></td> ';
			
			medsHTMLStr +=	'	<td>';
			medsHTMLStr +=	'		<input type="number" class="form-control items cost-only"  step=".01"  name="meds-cost-'+meds.CHSSMedicineId+'" id="me-cost-'+meds.CHSSMedicineId+'" value="'+meds.MedicineCost+'" style="width:100%;text-align: right; " min="1" max="9999999" required="required" > ';
			medsHTMLStr +=	'		<input type="hidden" id="me-cost-org-'+meds.CHSSMedicineId+'" value="'+meds.MedicineCost+'" > ';
			medsHTMLStr +=	'	</td>';
			medsHTMLStr +=	'	<td>';
			medsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="medicineid" value="'+meds.CHSSMedicineId+'" formaction="MedicineBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update" Onclick="return itemEditEligibleCheck(\'me\',\''+meds.CHSSMedicineId+'\')" ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
			medsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="medicineid" value="'+meds.CHSSMedicineId+'" formaction="MedicineBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
			medsHTMLStr +=	'	</td> ';
			medsHTMLStr +=	'</tr> ';
			
		}
		
		if(medsVals.length==0){
			
			medsHTMLStr +=	'<tr><td colspan="6" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		$('#meds-list-table').html(medsHTMLStr);
		
		setTooltip();
		onlyNumbers();
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



function FillMedsList()
{
	var oldconsultid=0;
	if($('#old-consult').prop('checked'))
	{
		oldconsultid=$('#old-consult').val();
	}else
	{
		oldconsultid=0;
	}
		$.ajax({
		
			type : "GET",
			url : "OldConsultMedsListAjax.htm",
			data : {
									
				consultmainid : oldconsultid,
				chssapplyid : <%=chssapplydata[0]%>,
			},
			datatype : 'json',
			success : function(result) 
			{
				var result = JSON.parse(result);
							
				console.log(medsVals);
				
				var medsHTMLStr = '';
				for(var m=0;m<result.length;m++)
				{
					var meds = result[m];
					var allowThisMed = 0;
					for(var a=0;a<medsVals.length;a++)
					{
						if(meds[3].toUpperCase().trim() === medsVals[a].MedicineName.toUpperCase().trim())
						{
							allowThisMed=1;
						}
					}
					if(allowThisMed==1)
					{
						continue;
					}
					
					medsHTMLStr +=	'<tr class="tr_clone_meds_old tr_clone_meds" > ';
					
					medsHTMLStr +=	' 	<td><input type="text" class="form-control items" name="meds-name" id="med-name" readonly value="'+meds[3]+'" style="width:100%; "  maxlength="255" required="required"></td> ';
					
					medsHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="meds-presquantity" readonly id="meds-presquantity" value="'+meds[4]+'" style="width:100%;" min="1" max="9999999" required="required" ></td> ';
					medsHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="meds-quantity" id="meds-quantity" value="" style="width:100%;" min="1" max="9999999"  ></td> ';
					
					medsHTMLStr +=	'	<td><input type="number" class="form-control items cost-only"  step=".01"  name="meds-cost" id="meds-cost" value="" style="width:100%;text-align: right; " min="1" max="9999999" ></td> ';  
					medsHTMLStr +=	'	<td>';
					medsHTMLStr +=	'		<button type="button" class="btn btn-sm tbl-row-rem_meds_old"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> ';
					medsHTMLStr +=	'	</td> ';
					medsHTMLStr +=	'</tr> ';
					
				}
				
				
				$('#meds-add-tbody').html(medsHTMLStr);
				setTooltip();
				onlyNumbers();
							
			}
		});
}

</script>
<!-- ------------------------------------------------------- medicines script --------------------------------------------------- -->

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
		  	onlyNumbers();
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
			
			otherHTMLStr +=	'	<td> ';
			otherHTMLStr +=	'		<input type="number" class="form-control items cost-only"  step=".01" name="otheritemcost-'+other.CHSSOtherId+'" id="ot-cost-'+other.CHSSOtherId+'" value="'+other.OtherItemCost+'" style="width:100%;text-align: right; " min="1" max="9999999" required="required" > ';
			otherHTMLStr +=	'		<input type="hidden" id="ot-cost-org-'+other.CHSSOtherId+'" value="'+other.OtherItemCost+'"  > ';
			otherHTMLStr +=	'	<td>';
			otherHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="chssotherid" value="'+other.CHSSOtherId+'" formaction="OtherBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return itemEditEligibleCheck(\'ot\',\''+other.CHSSOtherId+'\')"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
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

<!-- ------------------------------------------------------- Miscellaneous script--------------------------------------------------- -->
<script type="text/javascript">


$("table").on('click','.tbl-row-add-misc' ,function() 
{
   	var $tr = $('.tr_clone_misc').last('.tr_clone_misc');
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	$clone.find(".items").val("").end();

	
   	setTooltip();
   	onlyNumbers();
  
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
				
				miscHTMLStr +=	'	<td><input type="number" class="form-control items numberonly" name="misc-count-'+misc.ChssMiscId+'"  value="'+misc.MiscCount+'" style="width:100%;" min="0" max="9999999" required="required" ></td> ';
				miscHTMLStr +=	'	<td>';
				miscHTMLStr +=	'		<input type="number" class="form-control items cost-only"  step=".01"  name="misc-cost-'+misc.ChssMiscId+'" id="mi-cost-'+misc.ChssMiscId+'" value="'+misc.MiscItemCost+'" style="width:100%;text-align: right; " min="1" max="9999999" required="required" >';
				miscHTMLStr +=	'		<input type="hidden" id="mi-cost-org-'+misc.ChssMiscId+'"  value="'+misc.MiscItemCost+'"  > ';
				miscHTMLStr +=	'	<td>';
				miscHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="chssmiscid" value="'+misc.ChssMiscId+'" formaction="MiscBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return itemEditEligibleCheck(\'mi\',\''+misc.ChssMiscId+'\')" ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
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



</body>
</html>