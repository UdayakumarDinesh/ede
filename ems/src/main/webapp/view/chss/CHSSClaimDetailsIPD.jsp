<%@page import="com.vts.ems.chss.model.CHSSIPDPkgItems"%>
<%@page import="com.vts.ems.chss.model.CHSSBillImplants"%>
<%@page import="com.vts.ems.chss.model.CHSSBillEquipment"%>
<%@page import="com.vts.ems.chss.model.CHSSBillMisc"%>
<%@page import="com.vts.ems.chss.model.CHSSBillTests"%>
<%@page import="com.vts.ems.chss.model.CHSSBillConsultation"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.vts.ems.chss.model.CHSSIPDClaimsInfo"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.util.ArrayList"%>
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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

input[type="radio"] {
  -webkit-appearance: checkbox; /* Chrome, Safari, Opera */
  -moz-appearance: checkbox;    /* Firefox */
  -ms-appearance: checkbox;     /* not currently supported */
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

table.roundedCorners { 
  border-radius: 8px; 
  border-spacing: 0;
  }
table.roundedCorners tr:last-child > td {
  border-bottom: none;
}


table th:first-child{
  border-radius:8px 0 0 0;
}

table th:last-child{
  border-radius:0 8px 0 0;
}

</style>
</head>
<body>

<%
	String tab = (String)request.getAttribute("tab") ;
	Object[] employee = (Object[] )request.getAttribute("employee") ;	
	List<CHSSTreatType> treattypelist=(List<CHSSTreatType>)request.getAttribute("treattypelist");

	Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
	List<Object[]> chssbill=(List<Object[]>)request.getAttribute("chssbill");
	List<Object[]> NonPackageItems=(List<Object[]>)request.getAttribute("NonPackageItems");
	
	List<CHSSTestSub> packageslist = (List<CHSSTestSub>)request.getAttribute("testmainlist");
	List<CHSSIPDPkgItems> pkgSubItems = (List<CHSSIPDPkgItems>)request.getAttribute("pkgSubItems");
	
	List<Object[]> ClaimPackages = (List<Object[]>)request.getAttribute("ClaimPackages");
	
	List<CHSSTestSub> testmainlist = (List<CHSSTestSub>)request.getAttribute("testmainlist");
	List<CHSSDoctorRates> doctorrates = (List<CHSSDoctorRates>)request.getAttribute("doctorrates");
	List<Object[]> ClaimAttachDeclare = (List<Object[]>)request.getAttribute("ClaimAttachDeclare");
	
	List<CHSSBillConsultation> consultations=(List<CHSSBillConsultation>)request.getAttribute("consultations");
	List<CHSSBillTests> billtests=(List<CHSSBillTests>)request.getAttribute("billtests");
	List<CHSSBillMisc> miscitems =(List<CHSSBillMisc>)request.getAttribute("miscitems"); 
	List<CHSSBillEquipment> equipments =(List<CHSSBillEquipment>)request.getAttribute("equipments");
	List<CHSSBillImplants> implants =(List<CHSSBillImplants>)request.getAttribute("implants");
	
	String isself = chssapplydata[3].toString();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();	
	
	CHSSIPDClaimsInfo ipdbasicinfo = (CHSSIPDClaimsInfo)request.getAttribute("ipdbasicinfo") ;
	LocalDate minbilldate = LocalDate.now().minusMonths(3);
	
	long billid = 0;
	if(chssbill.size()>0){
		billid = Long.parseLong(chssbill.get(0)[0].toString());
	}
	
	String chssapplyid=chssapplydata[0].toString();
	
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
						<li class="breadcrumb-item active " aria-current="page">Claim Data</li>
					</ol>
				</div>
			</div>
	</div>	
		<div align="center" >
				<%String ses=(String)request.getParameter("result"); 
				String ses1=(String)request.getParameter("resultfail");
				if(ses1!=null){ %>
					<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
						<%=ses1 %>
					</div>
				<%}if(ses!=null){ %>
					<div class="alert alert-success" role="alert" style="margin-top: 5px;">
						<%=ses %>
					</div>
				<%} %>
			</div>
	<div class="page card dashboard-card">
		
		<div class="card-body" >
		  
			<div class="card" >
				<div class="card-body main-card " >
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
							
							<b> &nbsp; Claim Id : </b><%=chssapplydata[16] %>
									
						</div>
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
										<b> Patient Name : &nbsp;</b><%=employee[2] %>
									</div>
											
									<div class="row" style="padding:5px; ">
										<b>Relation : &nbsp;</b>SELF
										<input type="hidden" name="patientid" value="<%=employee[0]%>">
										<input type="hidden" name="relationid" value="0">
									</div>
								</div>
							<% } %>
							<div class="col-2">
								<b>Treatment Type : </b><br>
								<select class="form-control select2 " name="treatmenttype" id="treatmenttype" required="required" data-live-search="true" >
									<option value="" selected="selected" disabled="disabled">Choose..</option>
									<%for(CHSSTreatType treattype : treattypelist ){ %>
										<option value="<%=treattype.getTreatTypeId()%>" <%if(Integer.parseInt(chssapplydata[7].toString()) == treattype.getTreatTypeId()){ %>selected<%} %> ><%=treattype.getTreatmentName() %></option>
									<%} %>								
								</select>
							</div>
							<div class="col-2">
								<b>Claim Type : </b> 
								<br>
								<select class="form-control " name="chsstype" >
									<option value="OPD" id="claim_type_IPD" <%if(chssapplydata[6].toString().equalsIgnoreCase("OPD")){ %>Selected<%} %> >OPD</option>
									<option value="IPD" id="claim_type_OPD" <%if(chssapplydata[6].toString().equalsIgnoreCase("IPD")){ %>Selected<%} %> >IPD</option>
								</select>
							</div>
							<div class="col-3">
								<b>Ailment/Disease/Accident : </b><br>
								<input type="text" class="form-control w-100" name="ailment" value="<%=chssapplydata[17] %>" required="required" maxlength="255" >
							</div>
							
									
							<div class="col-1">
								<button type="submit" class="btn btn-sm update-btn " style="margin-top: 20px;"  data-toggle="tooltip" data-placement="top" title="Update"> Update <!-- <i class="fa-solid fa-pen-to-square" style="color: #FF7800;"> --></i></button>  <!-- Onclick="return confirm ('Are You Sure To Update?');" --> 
							</div>
						</div>
					</div>
					<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
		<!-- -------------------------------------------------- Basic Details ------------------------------------------------------ -->						
					<div class="row">
						<div class="col-md-12" align="center">
							<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;"> Basic Details</span>
							<br>
						</div>
						
						<form <%if(ipdbasicinfo!=null){ %> action="CHSSIPDBasicInfoEdit.htm" <%}else{ %> action="CHSSIPDBasicInfoAdd.htm" <%} %>method="post" autocomplete="off"  >
						
						
							<div class="col-md-12">
								<div class="row">
									<div class="col-md-3">
										<b>Hospital :</b>
										<br>
										<input type="text" class="form-control" name="hospitalname" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getHospitalName()%>"  <%}else{ %>  value="" <%} %> maxlength="150" required>
									</div>
									<div class="col-md-2">
										<b>Admitted Date :</b>
										<br>
										<input type="text" class="form-control"  data-date-format="dd/mm/yyyy" name="admitted-date" id="admitted-date" value="" readonly="readonly" required>
									</div>
									<div class="col-md-2">
										<b>Time :</b>
										<br>
										<input type="text" class="form-control" style="width: 50%" name="admitted-time" id="admitted-time" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getAdmissionTime()%>"  <%}else{ %> value="<%=LocalTime.now()%>" <%} %>  readonly="readonly" required>
									</div>
									<div class="col-md-2">
										<b>Discharged Date :</b>
										<br>
										<input type="text" class="form-control" data-date-format="dd/mm/yyyy" name="discharged-date" id="discharged-date" value="" readonly="readonly"  required>
									</div>
									<div class="col-md-2">
										<b>Time :</b>
										<br>
										<input type="text" class="form-control" style="width: 50%" name="discharged-time" id="discharged-time" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getDischargeTime()%>"  <%}else{ %> value="<%=LocalTime.now()%>" <%} %>  readonly="readonly" required>
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row">
									<div class="col-md-4">
									<span style="font-weight: 600; font-size: 15px;color: #CA4E79;text-decoration: underline;"> Claim For :</span>
									</div>
								</div>
								<div class="row">
									<div class="col-md-4">
										<b>Domiciliary Hospitalization :</b>
										&nbsp;&nbsp;
										<input type="radio"  name="DomicHospi" value="1" <%if(ipdbasicinfo!=null && ipdbasicinfo.getDomiciliaryHosp()==1){ %> checked="checked" <%} %> required> &nbsp; YES &nbsp;&nbsp;
										<input type="radio"  name="DomicHospi" value="0" <%if(ipdbasicinfo!=null && ipdbasicinfo.getDomiciliaryHosp()==0){ %> checked="checked" <%} %> >&nbsp; NO
									</div>
									<div class="col-md-3">
										<b>Day Care :</b>
										&nbsp;&nbsp;
										<input type="radio"  name="DayCare" value="1" <%if(ipdbasicinfo!=null && ipdbasicinfo.getDayCare()==1){ %> checked="checked" <%} %> required> &nbsp; YES &nbsp;&nbsp;
										<input type="radio"  name="DayCare" value="0" <%if(ipdbasicinfo!=null && ipdbasicinfo.getDayCare()==0){ %> checked="checked" <%} %> >&nbsp; NO
									</div>
									<div class="col-md-5">
										<b>Extended Care / Inpatient Rehabiliatation :</b>
										&nbsp;&nbsp;
										<input type="radio"  name="ExtCareRehab" value="1"  <%if(ipdbasicinfo!=null && ipdbasicinfo.getExtCareRehab()==1){ %> checked="checked" <%} %> required> &nbsp; YES &nbsp;&nbsp;
										<input type="radio"  name="ExtCareRehab" value="0"  <%if(ipdbasicinfo!=null && ipdbasicinfo.getExtCareRehab()==0){ %> checked="checked" <%} %> >&nbsp; NO
									</div>
								</div>
							
							</div>
							<div align="center">	
								<br>
								<%if(ipdbasicinfo!=null){ %>
								<button type="submit" class="btn btn-sm update-btn" onclick="return confirm('Are You Sure to Update ?');">update</button>
								<input type="hidden" name="ipdclaiminfoid" value="<%=ipdbasicinfo.getIPDClaimInfoId()%>">
								<%}else{ %>
								<button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure to Submit ?');">submit</button>
								<%} %>
							</div>
							
							
							<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	
							
						</form>
						
					</div>
			
		<!-- -------------------------------------------------- Basic Details ------------------------------------------------------ -->
		
		<!-- -------------------------------------------------- Bill Details ------------------------------------------------------ -->			
				<%if(ipdbasicinfo!=null){ %>
					<div class="row">
						<div class="col-md-12"><br>
							<%if(chssbill.size()>0){ %>
								
								<form method="post" action="#" autocomplete="off"  >
										<div class="table-responsive">
										<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
											<thead>
												<tr style="background-color: #003E6C" >
													<th style="width:5%;" >SN</th>
													<th style="width:20%;" >Hospital Name</th>
													<th style="width:10%;" >Bill / Receipt No.</th>
													<th style="width:10%;" >Bill Date</th>
													<th style="width:10%; text-align: right;">Paid Amt (&#8377;)</th>
													<th style="width:10%;" >Action  </th>
												</tr>
											</thead>
											<tbody >
											<%	int sno=0;
											for(Object[] obj : chssbill){
												sno++;%>											
												<tr class="" >
													<td  style="text-align: center;" > <span class="sno" ><%=sno %></span> </td>
													<td> <input type="text" class="form-control items" name="centername-<%=obj[0]%>" value="<%=obj[3] %>" style="width:100%; "  maxlength="500" required="required"></td>
													<td> <input type="text" class="form-control items" name="billno-<%=obj[0]%>" value="<%=obj[2] %>" style="width:100%;"   maxlength="25" required="required"></td>
													<td> <input type="text" class="form-control billdate_edit" name="billdate-<%=obj[0]%>" value="<%=rdf.format(sdf.parse(obj[4].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
													<td> 
														<input type="number" class="form-control items cost-only " step=".01" name="finalbillamount-<%=obj[0]%>" id="finalbillamount-<%=obj[0]%>"   value="<%=obj[7]%>" style="width:100%;text-align: right; " min="1" max="9999999" required="required">
														<input type="hidden" name="Discount-<%=obj[0]%>" value="<%=chssapplydata[0]%>">
														<input type="hidden" name="DiscountPer-<%=obj[0]%>" value="<%=chssapplydata[0]%>">	
													</td>
													<td>
														<button type="submit"  class="btn btn-sm update-btn" formaction="CHSSIPDBillEdit.htm" Onclick="return confirm('Are You Sure To Update?');" name="billid" value="<%=obj[0]%>" > <!-- data-toggle="tooltip" data-placement="top" title="Update Bill" -->														
															update
														</button>
														<button type="submit"  class="btn btn-sm" formaction="CHSSBillDelete.htm" Onclick="return confirm('Are You Sure To Delete?');" name="billid" value="<%=obj[0]%>" >  <!-- data-toggle="tooltip" data-placement="top" title="Delete Bill" -->
															<i class="fa-solid fa-trash-can" style="color: red;"></i>
														</button>
													</td>										
												</tr>											
												<%} %>
											</tbody>							
										</table>
									</div>
									
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="0">
								</form>
								<% }
								else if(chssbill.size()==0){ %>
								
								<form method="post" action="CHSSIPDBillAdd.htm" autocomplete="off" >
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="0">
									<div class="table-responsive">
									<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:20%;" >Hospital Name</th>
												<th style="width:10%;" >Bill / Receipt No.</th>
												<th style="width:10%;" >Bill Date</th>
												<th style="width:10%; text-align: right;">Paid Amt (&#8377;)</th>
												<th style="width:10%;" >Action  </th>
											</tr>
										</thead>
									<tbody>
										<tr class="" >
											<td style="width:5%; text-align: center;"><span class="sno" >1</span> </td>
											<td style="width:20%;" ><input type="text" class="form-control items" name="centername" value="<%=ipdbasicinfo.getHospitalName()%>" style="width:100%; "  maxlength="500" required="required"></td>
											<td style="width:10%;" ><input type="text" class="form-control items" name="billno"  value="" style="width:100%;"   maxlength="25" required="required"></td>
											<td style="width:10%;" ><input type="text" class="form-control billdate" name="billdate"  value="" style="width:100%; "  maxlength="10" readonly required="required"></td>
											<td style="width:10%;" > 
												<input type="number" class="form-control items  decimal" step=".01"  name="finalbillamount"  id="finalbillamount-"   value="" style="width:100%;text-align: right; " min="1" max="9999999" required="required" >
												<input type="hidden" name="DiscountAmt" value="0.00">
												<input type="hidden" name="DiscountPer" value="0.00">	
											</td>
											<td style="width:10%;" >
												<button type="submit"  class="btn btn-sm add-btn "  name="action" value="add" >Add</button> <!-- Onclick="return confirm('Are You Sure To Add ?');" -->
											</td>										
										</tr>
									</tbody>	
								</table>
								</div>
								</form>
								<%} %>
							</div>
						</div>
						
				<%} %>
						
		<!-- -------------------------------------------------- Bill Details ------------------------------------------------------ -->
		<%if(billid>0){ %>
		
		<!-- -------------------------------------------------- package ------------------------------------------------------ -->
					
					<div class="row" id="tab-scroll-pk" align="center" >
						<div class="col-md-12" >
			   				<div align="center"><b>Bill Breakup</b></div>
					   	</div>
						<div class="col-md-12">
							
						  	<form action="#" method="post" autocomplete="off" >
								<div class="table-responsive">
									<table class="table table-bordered table-hover table-condensed  info shadow-nohover" style="width: 70%;">
										<thead>
											<tr>
												<th style="width:5%;text-align: center; ">SN</th>
												<th style="width:60% ">Package</th>
												<th style="width:25% ">Amount  (&#8377;)</th>
												<th style="width:10% ">Action</th>
											</tr>
										</thead>
										<% int sn=0; %>
										<tbody>
											<%for(Object[] pkgs : ClaimPackages){ %>
												<tr>
													<td style="width:5% !important; text-align: center;"><%=++sn %></td>
													<td style="width:60%;"><%=pkgs[3] %></td>
													<td style="width:25%;"><%=pkgs[4] %></td>
													<td style="width:10%;">
														<button type="button" class="btn btn-sm"  ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  data-toggle="tooltip" data-placement="top" title="Update"  Onclick="setModalPackageIdValue(<%=pkgs[0] %>,<%=pkgs[2] %>);" ></i></button>
														<button type="submit" class="btn btn-sm" name="bill_pkg_id" value="<%=pkgs[0] %>" formaction="CHSSIPDPackageDelete.htm" Onclick="return confirm('Are You Sure To Delete ?');"  data-toggle="tooltip" data-placement="top" title="Delete"  ><i class="fa-solid fa-trash-can" style="color: red;"></i></button>	
													</td>
												<tr>
											<%} %>
									
											<tr>
												<td colspan="4" align="center" ><button type="button" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return setModalPackageIdValue(0,0)">Add Package</button></td>
											</tr>
												
										</tbody>							
												
									</table>
								</div>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
					</div>
					
						
					
						
						
		<!-- -------------------------------------------------- package ------------------------------------------------------ -->
		
		<!-- -------------------------------------------------- Single billheads Details ------------------------------------------------------ -->
					
					
					<div class="row" id="tab-scroll-sh" align="center">
						<div class="col-md-12">
							<div class="table-responsive">
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover" style="width: 70%;">
									<thead>
										<tr>
											<th style="width:5%;text-align: center; ">SN</th>
											<th style="width:60% ">Bill Item</th>
											<th style="width:10% ">Amount  (&#8377;)</th>
										</tr>
									</thead>
									<% sn=0; %>
									<tbody>
										<%for(Object[] pkgitem : NonPackageItems){ %>
											<tr>
												<td style="text-align: center;"><%=++sn %></td>
												<td><%=pkgitem[1] %></td>
												<td style="padding: 0px;">
													<input type="number" class="form-control cost-only decimal" step=".01" min="1" 
													 name="billheadcost" <%if(pkgitem[4]!=null && Double.parseDouble(pkgitem[4].toString())>0){ %> value="<%=pkgitem[4] %>"<%}else{ %>value=""<%} %>  
													 style="margin: 0px;text-align: right;"  onchange="updateBillheads('<%=billid %>','<%=pkgitem[0]%>',this);" >
												</td>
											</tr>
										<%} %>
									</tbody>
								</table>
							</div>
						</div>	
					</div>
						
						
		<!-- -------------------------------------------------- single billheads Details ------------------------------------------------------ -->
		
		<!-- -------------------------------------------------- multiple billheads Details ------------------------------------------------------ -->
		
			<!-- ------------------------------------------------------- consultation --------------------------------------------------- -->		
					
				   		<%-- <div class="row" id="tab-scroll-co" align="center">
					   		<div class="col-md-12" >
					    		<form action="#" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:30%;">Doctor's Name</th>
												<th style="width:15%;">Qualification</th>
												<th style="width:15%;">Consult Date</th>
												<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:10%;" > Action </th>
											</tr>
										</thead>
										<tbody >
											<% sn=0;
											for(CHSSBillConsultation consult :consultations){ %>
												<tr>
													<td style="text-align:center; " ><%=++sn %></td>
													<td> <input type="text" class="form-control" name="doc-name-<%=consult.getConsultationId() %>" value="<%=consult.getDocName() %>"> </td>
													<td>
														<select class="form-control w-100" name="doc-qualification-<%=consult.getConsultationId() %>"  >
															<% for(CHSSDoctorRates rate:doctorrates ){ %>
																<option value="<%=rate.getDocRateId() %>" <%if(rate.getDocRateId() == consult.getDocQualification() ){ %>selected<%} %>><%=rate.getDocQualification() %></option>
															<%} %>
														</select>
													</td>
													
													<td><input type="text" class="form-control cons-date-edit" name="cons-date-<%=consult.getConsultationId() %>"  value="<%=DateTimeFormatUtil.SqlToRegularDate(consult.getConsultDate()) %>" style="width:100%;"  maxlength="10" readonly required="required"></td>
													<td><input type="number" class="form-control items cost-only co-cost"  step=".01"  name="cons-charge-<%=consult.getConsultationId() %>" value="<%=consult.getConsultCharge() %>" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
													<td>
														<button type="submit" class="btn btn-sm" name="consultationid" value="<%=consult.getConsultationId() %>" formaction="IPDConsultEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"   ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  Onclick="return confirm('Are You Sure To Update ?');"  ></i></button>
														<button type="submit" class="btn btn-sm" name="consultationid" value="<%=consult.getConsultationId() %>" formaction="IPDConsultationDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm('Are You Sure To Delete ?');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> 										
													</td>
												</tr>
											<% } %>	
											<%if(sn==0){ %>
												<tr><td colspan="6" style="text-align: center;" > No Records Found </td></tr>
											<%} %>
										</tbody>
									</table>
									<input type="hidden" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
					   		<div class="col-md-12" >
					    		<form action="IPDConsultAdd.htm"  method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<tbody>
											<tr class="tr_clone_cons" >
												<td  style="width:5%;text-align:center; "><%=++sn %></td>
												<td  style="width:30%;"><input type="text" class="form-control items" name="doc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
												<td  style="width:15%;">
													<select class="form-control w-100" name="doc-qualification">
														<%
														for(CHSSDoctorRates rate:doctorrates ){ %>
															<option value="<%=rate.getDocRateId() %>"><%=rate.getDocQualification() %></option>
														<% } %>
													</select>
												</td >
												<td  style="width:15%;"><input type="text" class="form-control cons-date" name="cons-date"  value="" style="width:100%;"  maxlength="10" readonly required="required"></td>
												<td  style="width:15%;"><input type="number" class="form-control items cost-only co-cost"  step=".01"  name="cons-charge" value="0" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td  style="width:10%;">
													<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return itemAddEligibleCheck('co')" >Add</button>  <!-- Onclick="return confirm('Are You Sure To Submit?');"  -->		
												</td>
											</tr>
										</tbody>							
										
									</table>
									<input type="hidden" class="billid" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid-old" value="0">
									<input type="hidden" name="consultmainid" value="0">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
					    	</div>
			   			</div> --%>
		
			<!-- ------------------------------------------------------- consultation --------------------------------------------------- -->
			<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->			   	
				   		<div class="row" id="tab-scroll-te" align="center">
				   			<div class="col-md-12" >
					    		<form action="#" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5% !important;" >SN</th>
												<th style="width:55%;">Test / Investigations</th>
												<th style="width:25%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:10%;" > Action </th>
											</tr>
										</thead>
										<tbody>
											<%	sn=0;
												for(CHSSBillTests tests :billtests){ %>
												<tr>
													<td style="text-align:center; "><%=++sn %></td>
													<td>
														<select class="form-control test-type  select2 "  data-size="auto" name="test-subid-<%=tests.getCHSSTestId() %>"  data-live-search="true" data-container="body" data-dropup-auto="true" data-size="8" required="required" >
															<option value="" selected="selected" disabled="disabled">Choose..</option>
															<%for(CHSSTestSub testsub : testmainlist){ %>
																<option value="<%= testsub.getTestMainId()%>_<%= testsub.getTestSubId() %>" <%if(tests.getTestSubId() == testsub.getTestSubId()){ %>selected <%} %> ><%=testsub.getTestName()%></option>
															<% } %>
														</select>
													</td>
													<td><input type="number" class="form-control items cost-only te-cost"  step=".01"  name="test-cost-<%=tests.getCHSSTestId() %>"  value="<%=tests.getTestCost() %>" style="width:100%;text-align: right; " min="1" max="9999999" required="required"  ></td>
													<td>
														<button type="submit" class="btn btn-sm " name="testid" value="<%=tests.getCHSSTestId() %>" formaction="IPDTestBillEdit.htm" ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  data-toggle="tooltip" data-placement="top" title="Update"   Onclick="return confirm('Are You Sure To Update ?');" ></i></button>
														<button type="submit" class="btn btn-sm" name="testid" value="<%=tests.getCHSSTestId() %>" formaction="IPDTestBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm('Are You Sure To Delete ?');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button>	
													</td>
												</tr>	
											<%} %>
											<%if(sn==0){ %>
												<tr><td colspan="4" style="text-align: center;" > No Records Found </td></tr>
											<%} %>
										</tbody>
									</table>
									<input type="hidden" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
					   		<div class="col-md-12" >
					    		<form action="IPDTestsBillAdd.htm" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<tbody>
											<tr>
												<td style="width:5% !important; text-align: center;"><%=++sn %></td>
												<td style="width:55%;">
													<select class="form-control test-type  select2 "   data-size="auto" name="test-id"  data-live-search="true" data-container="body" data-dropup-auto="true" data-size="8" required="required" >
														<option value="" selected="selected" disabled="disabled">Choose..</option>
														<%for(CHSSTestSub testsub : testmainlist){ %>
															<option value="<%= testsub.getTestMainId()%>_<%= testsub.getTestSubId() %>"><%=testsub.getTestName()%></option>
														<% } %>
													</select>
												</td>
												<td style="width:25%;"><input type="number" class="form-control items cost-only te-cost"  step=".01"  name="tests-cost"  value="" style="width:100%;text-align: right; " min="1" max="9999999"  required="required"  ></td>
												<td style="width:10%;"><button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return itemAddEligibleCheck('te')">Add</button></td>
											</tr>
											
										</tbody>							
										
									</table>
									<input type="hidden" class="billid" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="0">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
					    	</div>
				   		</div>
			<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->
		
			
			<!-- ------------------------------------------------------- Equipments --------------------------------------------------- -->	
				   		<div class="row" id="tab-scroll-eq" align="center">
				   		
				   			<div class="col-md-12" >
					    		<form action="#" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:55%;">Equipment </th>
												<th style="width:25%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:10%;" > Action </th>
											</tr>
										</thead>
										<tbody>
											<% sn=0;
											for(CHSSBillEquipment equip : equipments){ %>
											<tr>
												<td style="width:5%;text-align: center;"><%=++sn %></td>
												<td style="width:55%;"><input type="text" class="form-control items" name="equip_name_<%=equip.getCHSSEquipmentId() %>" value="<%=equip.getEquipmentName() %>" style="width:100%; "  maxlength="255" required="required"></td>
												<td style="width:25%;"><input type="number" class="form-control items cost-only equip_cost"  step=".01"  name="equip_cost_<%=equip.getCHSSEquipmentId() %>"  value="<%=equip.getEquipmentCost()%>" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td style="width:10%;">
													<button type="submit" class="btn btn-sm " name="chssequipid" value="<%=equip.getCHSSEquipmentId() %>" formaction="EquipmentBillEdit.htm" ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  data-toggle="tooltip" data-placement="top" title="Update" Onclick="return confirm('Are You Sure To Update ?');" ></i></button>
													<button type="submit" class="btn btn-sm" name="chssequipid" value="<%=equip.getCHSSEquipmentId() %>" formaction="EquipmentBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm('Are You Sure To Delete ?');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button>
												</td>
											</tr>	
											<%} %>
											<%if(sn==0){ %>
												<tr><td colspan="5" style="text-align: center;" > No Records Found </td></tr>
											<%} %>
										</tbody>
									</table>
									<input type="hidden" class="billid" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="0">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
					   		<div class="col-md-12" >
					    		<form action="EquipmentItemAdd.htm" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<tbody class="tr_other_add">
											<tr class="tr_clone_misc" >
												<td style="width:5%;text-align: center;"><%=++sn %></td>
												<td style="width:55%;"><input type="text" class="form-control items" name="equip_name" value="" style="width:100%; "  maxlength="255" required="required"></td>
												<td style="width:25%;"><input type="number" class="form-control items cost-only equip-cost"  step=".01"  name="equip_cost" value="" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td style="width:10%;">
													<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" >Add</button>	 
												</td>
											</tr>
										</tbody>			
									</table>
									
									<input type="hidden" class="billid" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="0">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
					    	</div>
				   			
				   		</div>
			<!-- ------------------------------------------------------- Equipments --------------------------------------------------- -->
			
			<!-- ------------------------------------------------------- Implant --------------------------------------------------- -->	
				   		<div class="row" id="tab-scroll-im" align="center">
				   		
				   			<div class="col-md-12" >
					    		<form action="#" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:55%;">Implant </th>
												<th style="width:25%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:10%;" > Action </th>
											</tr>
										</thead>
										<tbody>
											<% sn=0;
											for(CHSSBillImplants implant : implants){ %>
											<tr>
												<td style="width:5%;text-align: center;"><%=++sn %></td>
												<td style="width:55%;"><input type="text" class="form-control items" name="impl_name_<%=implant.getCHSSImplantId() %>" value="<%=implant.getImplantName() %>" style="width:100%; "  maxlength="255" required="required"></td>
												<td style="width:25%;"><input type="number" class="form-control items cost-only equip_cost"  step=".01"  name="impl_cost_<%=implant.getCHSSImplantId() %>"  value="<%=implant.getImplantCost()%>" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td style="width:10%;">
													<button type="submit" class="btn btn-sm " name="chssimplantid" value="<%=implant.getCHSSImplantId() %>" formaction="ImplantBillEdit.htm" ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  data-toggle="tooltip" data-placement="top" title="Update" Onclick="return confirm('Are You Sure To Update ?');" ></i></button>
													<button type="submit" class="btn btn-sm" name="chssimplantid" value="<%=implant.getCHSSImplantId() %>" formaction="ImplantBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm('Are You Sure To Delete ?');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button>
												</td>
											</tr>	
											<%} %>
											<%if(sn==0){ %>
												<tr><td colspan="5" style="text-align: center;" > No Records Found </td></tr>
											<%} %>
										</tbody>
									</table>
									<input type="hidden" class="billid" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="0">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
					   		<div class="col-md-12" >
					    		<form action="ImplantItemAdd.htm" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<tbody class="tr_other_add">
											<tr class="tr_clone_misc" >
												<td style="width:5%;text-align: center;"><%=++sn %></td>
												<td style="width:55%;"><input type="text" class="form-control items" name="impl_name"  value="" style="width:100%; "  maxlength="255" required="required"></td>
												<td style="width:25%;"><input type="number" class="form-control items cost-only implant-cost"  step=".01"  name="impl_cost" value="" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td style="width:10%;">
													<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" >Add</button>	 
												</td>
											</tr>
										</tbody>			
									</table>
									
									<input type="hidden" class="billid" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="consultmainid" value="0">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
					    	</div>
				   			
				   		</div>
			<!-- ------------------------------------------------------- Implant --------------------------------------------------- -->
			
			<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->	
			
				   		<div class="row" id="tab-scroll-mi" align="center">
				   		
				   			<div class="col-md-12" >
					    		<form action="#" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:55%;">Miscellaneous Item </th>
												<th style="width:10%; text-align: center; ;">Qty</th>
												<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:10%;" > Action </th>
											</tr>
										</thead>
										<tbody>
											<% sn=0;
											for(CHSSBillMisc misc :miscitems){ %>
											<tr>
												<td style="width:5%;text-align: center;"><%=++sn %></td>
												<td style="width:55%;"><input type="text" class="form-control items" name="misc-name-<%=misc.getChssMiscId() %>" value="<%=misc.getMiscItemName() %>" style="width:100%; "  maxlength="255" required="required"></td>
												<td style="width:10%;"><input type="number" class="form-control items numberonly" name="misc-count-<%=misc.getChssMiscId() %>"  value="<%=misc.getMiscCount()%>" style="width:100%;" min="0" max="999999" required="required" ></td>
												<td style="width:20%;"><input type="number" class="form-control items cost-only mi-cost"  step=".01"  name="misc-cost-<%=misc.getChssMiscId() %>"  value="<%=misc.getMiscItemCost()%>" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td style="width:10%;">
													<button type="submit" class="btn btn-sm " name="chssmiscid" value="<%=misc.getChssMiscId() %>" formaction="IPDMiscBillEdit.htm" ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  data-toggle="tooltip" data-placement="top" title="Update" Onclick="return confirm('Are You Sure To Update ?');" ></i></button>
													<button type="submit" class="btn btn-sm" name="chssmiscid" value="<%=misc.getChssMiscId() %>" formaction="IPDMiscBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm('Are You Sure To Delete ?');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button>
												</td>
											</tr>	
											<%} %>
											<%if(sn==0){ %>
												<tr><td colspan="5" style="text-align: center;" > No Records Found </td></tr>
											<%} %>
										</tbody>
									</table>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
					   		<div class="col-md-12" >
					    		<form action="IPDMiscBillAdd.htm" method="post" autocomplete="off" style="width: 70%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<tbody class="tr_other_add">
											<tr class="tr_clone_misc" >
												<td style="width:5%;text-align: center;"><%=++sn %></td>
												<td style="width:55%;"><input type="text" class="form-control items" name="misc-name"  value="" style="width:100%; "  maxlength="255" required="required"></td>
												<td style="width:10%;"><input type="number" class="form-control items numberonly" name="misc-count"  value="0" style="width:100%;" min="1" max="999999" required="required" ></td>
												<td style="width:20%;"><input type="number" class="form-control items cost-only mi-cost"  step=".01"  name="misc-cost"  value="" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td style="width:10%;">
													<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" >Add</button>	 <!--  Onclick="return confirm('Are You Sure To Submit?');" --> 
												</td>
											</tr>
										</tbody>			
									</table>
									<input type="hidden" class="billid" name="billid" value="<%=billid%>">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
					    	</div>
				   			
				   		</div>
			
		<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->
			
		<!-- -------------------------------------------------- multiple billheads Details ------------------------------------------------------ -->
	
		<!-- -------------------------------------------------- Attachments ------------------------------------------------------ -->
						<div class="row" id="tab-scroll-at" align="center">
				   			<div class="col-md-12" >
				   				<div align="center"><b>Attachments</b></div>
				   			</div>
				   			<div class="col-md-12" >
				   				
				   				<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" style="width: 70%;">
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:50%;" >Type of Document(s)	</th>
											<th style="width:20%;" >Is Attached</th>
										</tr>
									</thead>
									<tbody>
									<%for(Object[]  ClaimAttach: ClaimAttachDeclare){ %>
										<tr>
											<td><%=ClaimAttachDeclare.indexOf(ClaimAttach)+1 %></td>
											<td><%=ClaimAttach[1] %>	</td>
											<td style="text-align: center;">
												<input type="radio" class="attach_radio" name="attachment_<%=ClaimAttach[0] %>" <%if(ClaimAttach[2]!=null && ClaimAttach[4].toString().equalsIgnoreCase("Y")){ %> checked <%} %> value="Y" onclick="updateAttachments('<%=chssapplyid %>','<%=ClaimAttach[0] %>','Y');" > &nbsp; Yes &nbsp;
												<input type="radio" class="attach_radio" name="attachment_<%=ClaimAttach[0] %>" <%if(ClaimAttach[2]==null || ClaimAttach[4].toString().equalsIgnoreCase("N")){ %> checked <%} %> value="N" onclick="updateAttachments('<%=chssapplyid %>','<%=ClaimAttach[0] %>','N');" > &nbsp; No
											</td>
										</tr>
									<%} %>
									</tbody>
				   				</table>				   			
				   			</div>
				   		</div>
				
	<!-- -------------------------------------------------- Attachments ------------------------------------------------------ -->
						<form action="CHSSIPDFormEdit.htm">
							<div class="row" align="center">
								<div class="col-md-12">
									<button type="submit" class="btn btn-sm preview-btn" name="chssapplyid" value="<%=chssapplydata[0]%>">preview</button>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="isapproval" value="N">
									<input type="hidden" name="show-edit" value="N">
									
									<input type="hidden" name="view_mode" value="UF">
												
								</div>	
							</div>
						</form>
						
					<%} %>
			
			</div>
		</div>		
				
	</div>
		
</div>


					<div class="modal my-pkg-modal" id="my-pkg-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">

						<div class="modal-dialog modal-lg modal-dialog-centered" style="min-width: 70% !important;min-height:70% !important; ">
							<div class="modal-content" >
							
								<div class="modal-header" style="background: #F5C6A5;padding: 11px 20px 2px 25px;">
									<div class="row" style="width: 100%;">
									    <div class="col-12"  >
									    	<h5 style="font-weight: 800;font-size: 1.5rem;font-family: Poppins;color: #005C97;">Enter Package Breakup details Here</h5>
									    </div>
								    </div>
								    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
								    	<i class="fa-solid fa-xmark" aria-hidden="true" ></i>
								    </button>
							    </div>
							    
								<div class="modal-body" style="min-height: 30rem;" align="center">
									<form action="#" method="post" autocomplete="off" id="package_form" style="width: 80%;">
									<div class="row" style="width: 100%;">
									    <div class="col-12" >
											<table  class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" style="width: 80%">
												<tr>
													<td style="min-width:25%;width:25%;"><span style="font-size: 20px;font-weight: 600;">Package : </span> </td>
													<td style="width:75%">
														<select class="form-control pkg-type  select2 "  style="width: 500px !important;"  data-size="auto" name="pkg_id"  id="modal_pkg_id" data-live-search="true" data-container="body" data-dropup-auto="true" data-size="8" required="required" >
															<option value="" selected="selected" disabled="disabled">Choose..</option>
																<%for(CHSSTestSub pkg : packageslist){ %>
																	<option value="<%= pkg.getTestSubId() %>"><%=pkg .getTestName()%></option>
																<% } %>
														</select>
													</td>
												</tr>
											</table>
										</div>
									    
								    </div>
								    <br>
								    
						    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
											<thead>
												<tr>
													<th style="text-align:center; ">SN</th>
													<th>Package Item</th>
													<th>Amount  (&#8377;)</th>
												</tr>
											</thead>
											<tbody>
												<% int sno=1;
												for(CHSSIPDPkgItems pkgitem : pkgSubItems){ %>
													<tr>
														<td style="text-align:center; " ><%=sno++ %></td>
														<td><%=pkgitem.getPkgItemName() %></td>
														<td style="width:15%; ">
															<input type="number" class="form-control items decimal cost-only model-pkgsub-cost"  name="pkgitem_cost" id="model-pkgitem-cost-<%=pkgitem.getIPDPkgItemId() %>"  onchange="calculatePkgTotal();"  value="" style="width:100%;text-align: right; " min="0"    step=".01">
															<input type="hidden" name="pkgitem_id" value="<%=pkgitem.getIPDPkgItemId() %>"> 
														</td>
														
													</tr>
												<% } %>
												<tr>
													<td colspan="2" style="text-align: right;"><b>Total</b></td>
													<td style="width:15%;text-align: right; ">
														<input type="number" class="form-control" name="pkg_total_cost" id="model-pkgsub-cost-total" style="width:100%;text-align: right; " required="required" min="1" readonly="readonly" value="0" >
													</td>
												</tr>
											</tbody>
										</table>
										<div class="row">
									   		<div class="col-12">
									   			<button type="button" class="btn btn-sm submit-btn" name="action" value="add" id="pkg_add_btn" formaction="CHSSIPDPackageAdd.htm"   onclick="return checkPkgTotal('A');" >submit</button>
									   			<button type="button" class="btn btn-sm update-btn" name="action" value="add" id="pkg_edit_btn" formaction="CHSSIPDPackageEdit.htm"  onclick="return checkPkgTotal('E');" >update</button>
									   		</div>
									   	</div>
										
										<input type="hidden" name="bill_pkg_id" id="modal_bill_pkg_id" value="0">
										<!-- <input type="hidden" name="pkg_id" id="modal_pkg_id" value=""> -->
										<input type="hidden" id="modal_billid" name="billid" value="<%=billid%>">
										<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									
									</form>
								</div>
								
							</div>
						</div>
					</div>



<script type="text/javascript">


function setModalPackageIdValue(billPkgid,testsubid)
{
	if(billPkgid==0){
		var pkg_id = $('#pkg_id_add').val();
		
		$('#modal_bill_pkg_id').val(0);
		$('#modal-pkg-name').html($('#pkg_id_add option:selected' ).text());
		$("#modal_pkg_id").val('').trigger("change");
		
	
		$('#pkg_add_btn').attr('disabled',false).show();
		$('#pkg_edit_btn').attr('disabled',true).hide();
		
		clearPkgBreakupvalues();
		$('#my-pkg-modal').modal('toggle');
		
	}else {
		$('#modal_pkg_id').val(testsubid);
		$('#modal_bill_pkg_id').val(billPkgid);
		$("#modal_pkg_id").val(testsubid).trigger("change");
		
		$.ajax({

			type : "GET",
			url : "ClaimPkgItemsListAjax.htm",
			data : {
					
				billid : $('#modal_billid').val() ,
				billpkgid : billPkgid ,
				
			},
			datatype : 'json',
			success : function(result) {
				var result = JSON.parse(result);
				
				console.log(result);
				var totalamt=0;
				for(var i=0;i<result.length;i++)
				{
					$('#model-pkgitem-cost-'+result[i].IPDPkgItemId).val(result[i].PkgItemCost);
					totalamt = totalamt+result[i].PkgItemCost;
				}
				
				$('#model-pkgsub-cost-total').val(totalamt);
				
				$('#pkg_add_btn').attr('disabled',true).hide();
				$('#pkg_edit_btn').attr('disabled',false).show();
							
				$('#my-pkg-modal').modal('toggle');
			
			
			}
		});
		
		
		
	}
}

function checkPkgTotal(addedit)
{
	if(Number($('#modal_pkg_id').val())>0){
	
		var pkgtotalamt = $('#model-pkgsub-cost-total').val();
		if(pkgtotalamt==='' || Number(pkgtotalamt)==0 )
		{
			alert('Please Enter Valid Item Cost');
			return false;
		}else if(addedit==='A')
		{
			if(confirm('Are You Sure to Submit ?'))
			{
				$('#package_form').attr('action', 'CHSSIPDPackageAdd.htm').submit();;
			}
		}else if(Number(pkgtotalamt)>0 && addedit==='E')
		{
			if(confirm('Are You Sure to Update ?'))
			{
				$('#package_form').attr('action', 'CHSSIPDPackageEdit.htm').submit();;
			}
		}
	}else
	{
		alert('Plaese Select a Package!');
	}
}
function clearPkgBreakupvalues()
{
	$('.model-pkgsub-cost').each(function(i, obj) {	   
	    $(obj).val('');
	});
	$('#model-pkgsub-cost-total').val(0);
}

function calculatePkgTotal()
{

	var packageItemTotalCost = 0;
	$('.model-pkgsub-cost').each(function(i, obj) {	   
	    packageItemTotalCost += Number(obj.value);
	});
	$('#model-pkgsub-cost-total').val(packageItemTotalCost);
}


</script>
						
						
						
						


<script type="text/javascript">



function updateBillheads(billid,billheadid,elem)
{
	var chssapplyid = '<%=chssapplyid%>';
	$.ajax({

		type : "GET",
		url : "IPDSingleBillHeadsAjax.htm",
		data : {
				
			chssapplyid : chssapplyid ,
			billid : billid ,
			billheadid : billheadid ,
			billheadcost : $(elem).val(),
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		

		}
	});
}


</script>



<script type="text/javascript">

var DecimalClassOldValue;
$('.decimal').keypress(function (e) {
	
	DecimalClassOldValue = $(this).val();
	
});

$('.decimal').keyup(function (e) {
					 
	var newValue = $(this).val();
	if(hasDecimalPlace(newValue,3)){
		 e.preventDefault();
		 $(this).val(DecimalClassOldValue);
		 return false;
	}
	  
});
function hasDecimalPlace(value, x) {
	    var pointIndex = value.indexOf('.');
	    return  pointIndex >= 0 && pointIndex < value.length - x;
} 
						

</script>

<script type="text/javascript">

function updateAttachments(chssapplyid,attachtypeid,value)
{

	
	$.ajax({

		type : "GET",
		url : "IPDAttachmentsUpdateAjax.htm",
		data : {
				
			chssapplyid : chssapplyid ,
			attachtypeid : attachtypeid ,
			value : value,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		

		}
	});
}


</script>


<script type="text/javascript">

$('.cons-date').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	<%if(ipdbasicinfo!=null){ %> "startDate" : new Date('<%=ipdbasicinfo.getAdmissionDate()%>'),   <%}else{ %> "startDate" : new Date(), <%} %>
	"maxDate" :new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});	


$('.cons-date-edit').daterangepicker({
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

</script>


<script type="text/javascript">


var threeMonthsAgo = new Date('<%=minbilldate%>');
$(document).ready( function() {
	
	onlyNumbers();
	setTooltip();
	
	<%if(tab!=null){ %>
		    
		document
	    .getElementById("tab-scroll-<%=tab%>")
	    .scrollIntoView();
		 
	<%}%>
		


$('.billdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(), 
	"minDate":threeMonthsAgo,
	<%if(ipdbasicinfo!=null){ %> "startDate" : new Date('<%=ipdbasicinfo.getDischargeDate()%>'),   <%}else{ %> "startDate" : new Date(), <%} %>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('.billdate_edit').daterangepicker({
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


function  onlyNumbers() {    
	
 	$('.numberonly').keypress(function (e) {    

	    var charCode = (e.which) ? e.which : event.keyCode    
	
	    if (String.fromCharCode(charCode).match(/[^0-9]/g))  
	    {
	        return false;
	    }
	    
	});

 	$('.cost-only').keypress( function (evt) {

	    if (evt.which > 31 &&  (evt.which < 48 || evt.which > 57) && evt.which!=46 )
	    {
	        evt.preventDefault();
	    } 
	});

 	
 	
 
}

 function setTooltip()
 {
 	$('[data-toggle="tooltip"]').tooltip({
 		 trigger : 'hover',
 	});
 	$('[data-toggle="tooltip"]').on('click', function () {
 		$(this).tooltip('hide');
 	});
 }



});

</script>


	
<script type="text/javascript">   

$(document).ready( function() {
	onlyNumbers();
	
	<%if(ipdbasicinfo!=null){%>
		$('#treatmenttype').prop('disabled', true);
		$('#claim_type_<%=chssapplydata[6]%>').prop('disabled', true);
	<%}%> 
});   


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



$('#admitted-date').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(),
	<%if(ipdbasicinfo!=null){ %> "startDate" : new Date('<%=ipdbasicinfo.getAdmissionDate()%>'),   <%}else{ %> "startDate" : new Date(), <%} %>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


	$('#admitted-time').daterangepicker({
		timePicker : true,
		singleDatePicker:true,
		timePicker24Hour : true,
		timePickerIncrement : 1,
		timePickerSeconds : false,
		locale : {
			format : 'HH:mm'
		}
	}).on('show.daterangepicker', function(ev, picker) {
		picker.container.find(".calendar-table").hide();
	});

 

	 $('#discharged-date').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :$('#admitted-date').val(),
		"maxDate" :new Date(),
		<%if(ipdbasicinfo!=null){ %> "startDate" : new Date('<%=ipdbasicinfo.getDischargeDate()%>'),   <%} %>
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});

	$(function() {
		$('#discharged-time').daterangepicker({
			timePicker : true,
			singleDatePicker:true,
			timePicker24Hour : true,
			timePickerIncrement : 1,
			timePickerSeconds : false,
			locale : {
				format : 'HH:mm'
			}
		}).on('show.daterangepicker', function(ev, picker) {
			picker.container.find(".calendar-table").hide();
		});
	})


$('#admitted-date').change(function()
{
 $('#discharged-date').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(),
	"minDate" :$('#admitted-date').val(),
	/* "startDate" : result, */
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$(function() {
	$('#discharged-time').daterangepicker({
		timePicker : true,
		singleDatePicker:true,
		timePicker24Hour : true,
		timePickerIncrement : 1,
		timePickerSeconds : false,
		locale : {
			format : 'HH:mm'
		}
	}).on('show.daterangepicker', function(ev, picker) {
		picker.container.find(".calendar-table").hide();
	});
})

});
	

</script>



</body>
</html>