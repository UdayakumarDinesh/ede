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
	List<Object[]> PackageItems=(List<Object[]>)request.getAttribute("PackageItems");
	List<Object[]> NonPackageItems=(List<Object[]>)request.getAttribute("NonPackageItems");
	
	List<CHSSTestSub> testmainlist = (List<CHSSTestSub>)request.getAttribute("testmainlist");
	List<CHSSDoctorRates> doctorrates = (List<CHSSDoctorRates>)request.getAttribute("doctorrates");
	
	
	List<CHSSBillConsultation> consultations=(List<CHSSBillConsultation>)request.getAttribute("consultations");
	List<CHSSBillTests> billtests=(List<CHSSBillTests>)request.getAttribute("billtests");
	List<CHSSBillMisc> miscitems =(List<CHSSBillMisc>)request.getAttribute("miscitems");
	
	String isself = chssapplydata[3].toString();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();	
	
	CHSSIPDClaimsInfo ipdbasicinfo = (CHSSIPDClaimsInfo)request.getAttribute("ipdbasicinfo") ;
	LocalDate minbilldate = LocalDate.now().minusMonths(3);
	
	long billid = 0;
	if(chssbill.size()>0){
		billid = Long.parseLong(chssbill.get(0)[0].toString());
	}
	
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
								<select class="form-control select2 w-100" name="treatmenttype" id="treatmenttype" required="required" data-live-search="true" >
									<option value="" selected="selected" disabled="disabled">Choose..</option>
									<%for(CHSSTreatType treattype : treattypelist ){ %>
										<option value="<%=treattype.getTreatTypeId()%>" <%if(Integer.parseInt(chssapplydata[7].toString()) == treattype.getTreatTypeId()){ %>selected<%} %> ><%=treattype.getTreatmentName() %></option>
									<%} %>								
								</select>
							</div>
							<div class="col-2">
								<b>Claim Type : </b> 
								<br>
								<select class="form-control" name="chsstype" id="chsstype">
									<option value="OPD" <%if(chssapplydata[6].toString().equalsIgnoreCase("OPD")){ %>Selected<%} %> >OPD</option>
									<option value="IPD" <%if(chssapplydata[6].toString().equalsIgnoreCase("IPD")){ %>Selected<%} %> >IPD</option>
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
								<div class="col-md-3">
									<b>Room Type :</b>
									<br>
									<input type="text" class="form-control" name="room-type" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getRoomType()%>"  <%}else{ %>  value="" <%} %>  required>
								</div>
								<div class="col-md-2">
									<b>Admitted Date :</b>
									<br>
									<input type="text" class="form-control"  data-date-format="dd/mm/yyyy" name="admitted-date" id="admitted-date" value="" readonly="readonly" required>
								</div>
								<div class="col-md-1">
									<b>Time :</b>
									<br>
									<input type="text" class="form-control" name="admitted-time" id="admitted-time" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getAdmissionTime()%>"  <%}else{ %> value="<%=LocalTime.now()%>" <%} %>  readonly="readonly" required>
								</div>
								<div class="col-md-2">
									<b>Discharged Date :</b>
									<br>
									<input type="text" class="form-control" data-date-format="dd/mm/yyyy" name="discharged-date" id="discharged-date" value="" readonly="readonly"  required>
								</div>
								<div class="col-md-1">
									<b>Time :</b>
									<br>
									<input type="text" class="form-control" name="discharged-time" id="discharged-time" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getDischargeTime()%>"  <%}else{ %> value="<%=LocalTime.now()%>" <%} %>  readonly="readonly" required>
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
				<div class="row">
					<div class="col-md-12"><br>
						<%if(chssbill.size()>0){ %>
							
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
												<!-- <th style="width:10%; text-align: right;">Discount (&#8377;)</th>
												<th style="width:10%; text-align: right;">Discount (%)</th> -->
												<th style="width:10%;" >Action  </th>
											</tr>
										</thead>
										<tbody >
										<%	int sno=0;
										for(Object[] obj : chssbill){
											sno++;%>											
											<tr class="" >
												<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno %></span> </td>
												<td> <input type="text" class="form-control items" name="centername-<%=obj[0]%>" value="<%=obj[3] %>" style="width:100%; "  maxlength="500" required="required"></td>
												<td> <input type="text" class="form-control items" name="billno-<%=obj[0]%>" value="<%=obj[2] %>" style="width:100%;"   maxlength="25" required="required"></td>
												<td> <input type="text" class="form-control billdate" name="billdate-<%=obj[0]%>" value="<%=rdf.format(sdf.parse(obj[4].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
												<td> 
													<input type="number" class="form-control items cost-only " step=".01" name="finalbillamount-<%=obj[0]%>" id="finalbillamount-<%=obj[0]%>"  onkeyup="enableDiscount('<%=obj[0]%>')" value="<%=obj[7]%>" style="width:100%;text-align: right; " min="1" max="9999999" required="required">
													<input type="hidden" name="Discount-<%=obj[0]%>" value="<%=chssapplydata[0]%>">
													<input type="hidden" name="DiscountPer-<%=obj[0]%>" value="<%=chssapplydata[0]%>">	
												</td>
												<%-- <td> <input type="number" class="form-control items cost-only " step=".01" name="Discount-<%=obj[0]%>" id="DiscountAmt-<%=obj[0]%>" onkeyup ="calculateDiscountPer('<%=obj[0]%>');" value="<%=obj[6] %>" style="width:100%;text-align: right; " min="0" max="9999999" required="required" ></td>
												<td> <input type="number" class="form-control items cost-only " step=".1" name="DiscountPer-<%=obj[0]%>" id="DiscountPer-<%=obj[0]%>" readonly="readonly" value="<%=obj[8] %>" style="width:100%;text-align: right; " min="0" max="100" required="required" ></td> --%>
												<td>
													
													<%if(Double.parseDouble(obj[9].toString())==0){ %>
													<button type="submit"  class="btn btn-sm update-btn" formaction="CHSSBillEdit.htm" Onclick="return confirm('Are You Sure To Update?');" name="billid" value="<%=obj[0]%>" > <!-- data-toggle="tooltip" data-placement="top" title="Update Bill" -->														
														update
													</button>
													<%} %>														
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
							<%}
							else if(chssbill.size()==0)
														{ %>
							<form method="post" action="CHSSBillAdd.htm" autocomplete="off" >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="consultmainid" value="0">
								<div class="table-responsive">
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:20%;" >Hospital / Medical / Diagnostics Center Name</th>
											<th style="width:10%;" >Bill / Receipt No.</th>
											<th style="width:10%;" >Bill Date</th>
											<th style="width:10%; text-align: right;">Paid Amt (&#8377;)</th>
											<!-- <th style="width:10%; text-align: right;">Discount (&#8377;)</th>
											<th style="width:10%; text-align: right;">Discount (%)</th> -->
											<th style="width:10%;" >Action  </th>
										</tr>
									</thead>
								<tbody>
									<tr class="" >
										<td style="width:5%;text-align: center;"><span class="sno" id="sno">1</span> </td>
										<td style="width:20%;" ><input type="text" class="form-control items" name="centername"  value="" style="width:100%; "  maxlength="500" required="required"></td>
										<td style="width:10%;" ><input type="text" class="form-control items" name="billno"  value="" style="width:100%;"   maxlength="25" required="required"></td>
										<td style="width:10%;" ><input type="text" class="form-control billdate" name="billdate"  value="" style="width:100%; "  maxlength="10" readonly required="required"></td>
										<td style="width:10%;" > 
											<input type="number" class="form-control items cost-only" step=".01"  name="finalbillamount"  id="finalbillamount-" Onclick="this.select();"  value="0.00" style="width:100%;text-align: right; " min="1" max="9999999" required="required" >
											<input type="hidden" name="DiscountAmt" value="0.00">
											<input type="hidden" name="DiscountPer" value="0.00">	
										</td>
										<!-- <td style="width:10%;" > <input type="number" class="form-control items cost-only" step=".01" name="DiscountAmt" id="DiscountAmt-" Onclick="this.select();"  onkeyup="calculateDiscountPer('');" onchange="calculateDiscountPer('');" value="0.00" style="width:100%;text-align: right; " min="0" max="9999999" readonly="readonly" required="required" ></td>
										<td style="width:10%;" > <input type="number" class="form-control items cost-only" step=".1" name="DiscountPer" id="DiscountPer-" value="0.0" style="width:100%;text-align: right; " min="0" max="9999999" required="required" readonly="readonly" ></td> -->
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
	<!-- -------------------------------------------------- Bill Details ------------------------------------------------------ -->
	<!-- -------------------------------------------------- single billheads Details ------------------------------------------------------ -->
				<%if(billid>0){ %>
				
				<div class="row" id="tab-scroll-sh">
					<div class="col-md-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
								<thead>
									<tr>
										<th style="width:5%;text-align: center; ">SN</th>
										<th style="width:60% ">Bill Item</th>
										<th style="width:10% ">Amount</th>
										<th style="width:25% ">Action</th>
									</tr>
								</thead>
								<tbody>
									<% int sn=0;
									for(Object[] pkgitem : PackageItems){ %>
										<tr>
											<td style="text-align: center;"><form action="IPDBillHeadDataAdd.htm" method="post" id="billheads-<%=pkgitem[0]%>" > <%=++sn %></form></td>
											<td><%=pkgitem[1] %></td>
											<td style="padding: 0px;">
												<input type="number" class="form-control cost-only" step=".01" min="1"  name="billheadcost" onclick="this.select();" <%if(pkgitem[7]!=null){ %> value="<%=pkgitem[7] %>"<%}else{ %>value="0"<%} %>  form="billheads-<%=pkgitem[0]%>"  style="margin: 0px;text-align: right;" >
												<input type="hidden" name="billid" value="<%=billid%>" form="billheads-<%=pkgitem[0]%>" >
												<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>" form="billheads-<%=pkgitem[0]%>" >
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" form="billheads-<%=pkgitem[0]%>" />
											</td>
											<td style="padding: 0px;">
												<button type="submit" class="btn btn-sm submit-btn" name="billheadid" style="margin: 0px;" value="<%=pkgitem[0]%>" form="billheads-<%=pkgitem[0]%>" onclick="return confirm('Are You Sure to Save ? ');" >save</button>
												
												<%-- <button type="submit" class="btn btn-sm submit-btn" name="billheadid" value="<%=pkgitem[0]%>" form="billheads-<%=pkgitem[0]%>" onclick="return confirm('Are You Sure to Save ? ');" >save</button> --%>
											</td>
										</tr>
									<%} %>
									<%for(Object[] pkgitem : NonPackageItems){ %>
										<tr>
											<td style="text-align: center;"><form action="IPDBillHeadDataAdd.htm" method="post" id="billheads-<%=pkgitem[0]%>" > <%=++sn %></form></td>
											<td><%=pkgitem[1] %></td>
											<td style="padding: 0px;">
												<input type="number" class="form-control cost-only" step=".01" min="1"  name="billheadcost" onclick="this.select();" <%if(pkgitem[7]!=null){ %> value="<%=pkgitem[7] %>"<%}else{ %>value="0"<%} %>  form="billheads-<%=pkgitem[0]%>"  style="margin: 0px;text-align: right;" >
												<input type="hidden" name="billid" value="<%=billid%>" form="billheads-<%=pkgitem[0]%>" >
												<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>" form="billheads-<%=pkgitem[0]%>" >
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" form="billheads-<%=pkgitem[0]%>" />
											</td>
											<td>
												<button type="submit" class="btn btn-sm submit-btn" name="billheadid" value="<%=pkgitem[0]%>" form="billheads-<%=pkgitem[0]%>" onclick="return confirm('Are You Sure to Save ? ');" >save</button>
												
												<%-- <button type="submit" class="btn btn-sm submit-btn" name="billheadid" value="<%=pkgitem[0]%>" form="billheads-<%=pkgitem[0]%>" onclick="return confirm('Are You Sure to Save ? ');" >save</button> --%>
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
					
				   		<div class="row" id="tab-scroll-co" >
				   			<div class="col-md-12" >
				   				<div align="center"><b>Consultation Details</b></div>
				   			</div>
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
													
													<td><input type="text" class="form-control cons-date" name="cons-date-<%=consult.getConsultationId() %>"  value="<%=DateTimeFormatUtil.SqlToRegularDate(consult.getConsultDate()) %>" style="width:100%;"  maxlength="10" readonly required="required"></td>
													<td><input type="number" class="form-control items cost-only co-cost"  step=".01"  name="cons-charge-<%=consult.getConsultationId() %>" value="<%=consult.getConsultCharge() %>" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
													<td>
														<button type="submit" class="btn btn-sm" name="consultationid" value="<%=consult.getConsultationId() %>" formaction="IPDConsultEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"   ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  Onclick="return confirm('Are You Sure To Update ?');"  ></i></button>
														<button type="submit" class="btn btn-sm" name="consultationid" value="<%=consult.getConsultationId() %>" formaction="ConsultationBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm('Are You Sure To Delete ?');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> 										
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
					   		<div class="col-md-12" id="consult-add-form" >
					    		<form action="IPDConsultAdd.htm"  method="post" autocomplete="off" style="width: 100%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<tbody>
											<tr class="tr_clone_cons" >
												<td  style="width:5%;text-align:center; "><%=++sn %></td>
												<td  style="width:30%;"><input type="text" class="form-control items" name="doc-name" id="doc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
												<td  style="width:15%;">
													<select class="form-control w-100" name="doc-qualification" id="doc-qualification" >
														<%
														for(CHSSDoctorRates rate:doctorrates ){ %>
															<option value="<%=rate.getDocRateId() %>"><%=rate.getDocQualification() %></option>
														<% } %>
													</select>
												</td >
												<td  style="width:15%;"><input type="text" class="form-control cons-date" name="cons-date"  value="" style="width:100%;"  maxlength="10" readonly required="required"></td>
												<td  style="width:15%;"><input type="number" class="form-control items cost-only co-cost"  step=".01"  name="cons-charge" id="cons-charge" value="0" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td  style="width:8%;">
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
			   			</div>
		
		<!-- ------------------------------------------------------- consultation --------------------------------------------------- -->
		<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->			   	
				   		<div class="row" id="tab-scroll-te">
				   			<div class="col-md-12" >
					    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<thead>
											<tr>
												<th style="width:5% !important;" >SN</th>
												<th style="width:55%;">Test / Investigations</th>
												<th style="width:30%; text-align: right;">Amount  (&#8377;)</th> 
												<th style="width:10%;" > Action </th>
											</tr>
										</thead>
										<tbody>
											<%	sn=0;
												for(CHSSBillTests tests :billtests){ %>
												<tr>
													<td style="text-align:center; "><%=++sn %></td>
													<td>
														<select class="form-control test-type  selectpicker " style="width: 100%" data-size="auto" name="test-subid-<%=tests.getCHSSTestId() %>"  data-live-search="true" data-container="body" data-dropup-auto="true" data-size="8">
															<option value="" selected="selected" disabled="disabled">Choose..</option>
															<%for(CHSSTestSub testsub : testmainlist){ %>
																<option value="<%= testsub.getTestMainId()%>_<%= testsub.getTestSubId() %>" <%if(tests.getTestSubId() == testsub.getTestSubId()){ %>selected <%} %> ><%=testsub.getTestName()%></option>
															<% } %>
														</select>
													</td>
													<td><input type="number" class="form-control items cost-only te-cost"  step=".01"  name="test-cost-<%=tests.getCHSSTestId() %>"  value="<%=tests.getTestCost() %>" style="width:100%;text-align: right; " min="1" max="9999999" onclick="this.select();"  ></td>
													<td>
														<button type="submit" class="btn btn-sm " name="testid" value="<%=tests.getCHSSTestId() %>" formaction="TestBillEdit.htm" ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  data-toggle="tooltip" data-placement="top" title="Update"   Onclick="return confirm('Are You Sure To Update ?');" ></i></button>
														<button type="submit" class="btn btn-sm" name="testid" value="<%=tests.getCHSSTestId() %>" formaction="TestBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm('Are You Sure To Delete ?');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button>	
													</td>
												</tr>	
											<%} %>
											<%if(sn==0){ %>
												<tr><td colspan="4" style="text-align: center;" > No Records Found </td></tr>
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
					    		<form action="TestsBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<tbody>
											<tr>
												<td style="width:5% !important; text-align: center;"><%=++sn %></td>
												<td style="width:55%;">
													<select class="form-control test-type  selectpicker " id="test-type_1" style="width: 100%" data-size="auto" name="test-id"  data-live-search="true" data-container="body" data-dropup-auto="true" data-size="8">
														<option value="" selected="selected" disabled="disabled">Choose..</option>
														<%for(CHSSTestSub testsub : testmainlist){ %>
															<option value="<%= testsub.getTestMainId()%>_<%= testsub.getTestSubId() %>"><%=testsub.getTestName()%></option>
														<% } %>
													</select>
												</td>
												<td style="width:30%;"><input type="number" class="form-control items cost-only te-cost"  step=".01"  name="tests-cost"  value="" style="width:100%;text-align: right; " min="1" max="9999999"  ></td>
												<td style="width:10%;"><button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" Onclick="return itemAddEligibleCheck('te')">Add</button></td>
											</tr>
											
										</tbody>							
										
									</table>
									
									<input type="hidden" class="billid" name="billid" value="<%=billid%>">
									<input type="hidden" name="consultmainid" value="0">
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
					    	</div>
				   		</div>
		<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->
		<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->	
				   		<div class="row" id="tab-scroll-mi">
				   		
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
										<tbody>
											<% sn=0;
											for(CHSSBillMisc misc :miscitems){ %>
											<tr>
												<td style="width:5%;"><%=++sn %></td>
												<td style="width:55%;"><input type="text" class="form-control items" name="misc-name-<%=misc.getChssMiscId() %>" value="<%=misc.getMiscItemName() %>" style="width:100%; "  maxlength="255" required="required"></td>
												<td style="width:10%;"><input type="number" class="form-control items numberonly" name="misc-count-<%=misc.getChssMiscId() %>"  value="<%=misc.getMiscCount()%>" style="width:100%;" min="0" max="999999" required="required" ></td>
												<td style="width:20%;"><input type="number" class="form-control items cost-only mi-cost"  step=".01"  name="misc-cost-<%=misc.getChssMiscId() %>"  value="<%=misc.getMiscItemCost()%>" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td style="width:10%;">
													<button type="submit" class="btn btn-sm " name="chssmiscid" value="<%=misc.getChssMiscId() %>" formaction="MiscBillEdit.htm" ><i class="fa-solid fa-pen-to-square" style="color: #FF7800;"  data-toggle="tooltip" data-placement="top" title="Update" Onclick="return confirm('Are You Sure To Update ?');" ></i></button>
													<button type="submit" class="btn btn-sm" name="chssmiscid" value="<%=misc.getChssMiscId() %>" formaction="MiscBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm('Are You Sure To Delete ?');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button>
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
					    		<form action="MiscBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
					    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
										<tbody class="tr_other_add">
											<tr class="tr_clone_misc" >
												<td style="width:5%;"><%=++sn %></td>
												<td style="width:55%;"><input type="text" class="form-control items" name="misc-name" id="misc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
												<td style="width:10%;"><input type="number" class="form-control items numberonly" name="misc-count" id="misc-count" value="0" style="width:100%;" min="0" max="999999" required="required" ></td>
												<td style="width:20%;"><input type="number" class="form-control items cost-only mi-cost"  step=".01"  name="misc-cost" id="misc-cost" value="" style="width:100%;text-align: right; " min="1" max="9999999" required="required" ></td>
												<td style="width:10%;">
													<button type="submit" class="btn btn-sm add-btn new-item-add-btn" name="action" value="submit" >Add</button>	 <!--  Onclick="return confirm('Are You Sure To Submit?');" --> 
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
		<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->
		
		
					<%} %>
		
	
	<!-- -------------------------------------------------- multiple billheads Details ------------------------------------------------------ -->
			
			</div>
		</div>		
				
	</div>
		
</div>

<script type="text/javascript">

$('.cons-date').daterangepicker({
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
		    
		<%-- $('html, body').animate({
		     scrollTop: $('#tab-scroll-<%=tab%>').offset().top
		}, 1000); --%>
		
		document
	    .getElementById("tab-scroll-<%=tab%>")
	    .scrollIntoView({ behavior: "smooth" });
		 
	<%}%>
		


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

/* function calculateDiscountPer($id)
{
	var disAmt = Number($('#DiscountAmt-'+$id).val());
	var billAmt = Number($('#finalbillamount-'+$id).val());
	
	if(billAmt===0 ){
		$('#DiscountPer-'+$id).val("0.00");
		$('#DiscountAmt-'+$id).val("0.00");
	}else{
		var discPer = (100*disAmt)/(billAmt+disAmt);
		$('#DiscountPer-'+$id).val(discPer.toFixed(1));
		
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
 */
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