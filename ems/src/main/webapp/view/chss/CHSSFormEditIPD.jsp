<%@page import="java.util.Arrays"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.time.LocalTime"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.vts.ems.chss.model.CHSSIPDClaimsInfo"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.chss.model.CHSSBillImplants"%>
<%@page import="com.vts.ems.chss.model.CHSSBillEquipment"%>
<%@page import="com.vts.ems.chss.model.CHSSBillMisc"%>
<%@page import="com.vts.ems.chss.model.CHSSBillTests"%>
<%@page import="com.vts.ems.chss.model.CHSSBillConsultation"%>
<%@page import="com.vts.ems.master.model.CHSSDoctorRates"%>
<%@page import="com.vts.ems.chss.model.CHSSTestSub"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
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


p {
	text-align: justify;
	text-justify: inter-word;
}


table{
	align: left;
	width: 100% !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	margin-left:10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
	
}

.center{

	text-align: center;
}

.right
{
	text-align: right;
}
.btn-history
{
	float: right;
	background-color: #FFD24C;
}
.text-blue
{
	color: blue;
}

.text-green
{
	color: #008005;
}
 
 
 .float{
	position:fixed;
	width:50px;
	height:50px;
	bottom:40px;
	right:40px;
	color:#FFF;
	border-radius:50px;
	text-align:center;
	box-shadow: 2px 2px 3px #999;
}

.my-float{
	margin:0px;
	font-size: 25px;
}

.systemgen
{
	color: #008005;
}

.processed
{
	color: #A300B5;
}

.verified
{
	color: #0CB5ED;
}

.legend { list-style: none; }
.legend li { float: left; margin-right: 10px; }
.legend span { border: 1px solid #ccc; float: left; width: 12px; height: 12px; margin: 6px 5px 2px 2px; }
/* your colors */

</style>
</head>

<%


SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();	
AmountWordConveration awc = new AmountWordConveration();
IndianRupeeFormat nfc=new IndianRupeeFormat();

Object[] employee = (Object[] )request.getAttribute("employee") ;	

Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
CHSSIPDClaimsInfo ipdbasicinfo = (CHSSIPDClaimsInfo)request.getAttribute("ipdbasicinfo") ;
String LabLogo = (String)request.getAttribute("LabLogo");


List<Object[]> chssbill=(List<Object[]>)request.getAttribute("chssbill");
List<Object[]> ClaimPackages=(List<Object[]>)request.getAttribute("ClaimPackages");
List<Object[]> ClaimPkgItems=(List<Object[]>)request.getAttribute("ClaimPkgItems");

List<Object[]> NonPackageItems=(List<Object[]>)request.getAttribute("NonPackageItems");

List<Object[]> consultations=(List<Object[]>)request.getAttribute("consultations");
List<Object[]> billtests=(List<Object[]>)request.getAttribute("billtests");
List<Object[]> miscitems =(List<Object[]>)request.getAttribute("miscitems"); 
List<Object[]> equipments =(List<Object[]>)request.getAttribute("equipments");
List<Object[]>implants =(List<Object[]>)request.getAttribute("implants");
List<Object[]> ClaimRemarksHistory = (List<Object[]>)request.getAttribute("ClaimRemarksHistory");
List<Object[]> ClaimAttachDeclare = (List<Object[]>)request.getAttribute("ClaimAttachDeclare");


List<Object[]> ClaimapprovedPOVO = (List<Object[]>)request.getAttribute("ClaimapprovedPOVO");

String tab = (String)request.getAttribute("tab") ;
String isself = chssapplydata[3].toString();


long billid = 0;
if(chssbill.size()>0){
	billid = Long.parseLong(chssbill.get(0)[0].toString());
}

String chssapplyid=chssapplydata[0].toString();
int chssstatusid = Integer.parseInt(chssapplydata[9].toString());

	
String view_mode=(String)request.getAttribute("view_mode");

boolean showRemAmt =  ((view_mode.equalsIgnoreCase("U") || view_mode.equalsIgnoreCase("UF") )&& chssstatusid==15) || view_mode.equalsIgnoreCase("V") ||  view_mode.equalsIgnoreCase("A") || ( view_mode.equalsIgnoreCase("E") && (chssstatusid==15 || chssstatusid==10 || chssstatusid==12)) ;
boolean allowEdit =   view_mode.equalsIgnoreCase("E") && (chssstatusid==2 || chssstatusid==4 || chssstatusid==5 || chssstatusid==6 ||chssstatusid==8 || chssstatusid==9 || chssstatusid==11 || chssstatusid==13  );
boolean historyBtn =  view_mode.equalsIgnoreCase("V") ||  view_mode.equalsIgnoreCase("E")||  view_mode.equalsIgnoreCase("A")  ;

historyBtn = historyBtn || allowEdit;
String logintype = (String)request.getAttribute("logintype");

String SidebarActive = (String)session.getAttribute("SidebarActive");	

String ActivateDisp=(String)request.getAttribute("ActivateDisp");
String dispReplyEnable = (String)request.getAttribute("dispReplyEnable");

Object[] ClaimDisputeData = (Object[])request.getAttribute("ClaimDisputeData");

%>


<body>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Preview - IPD</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<%if(dispReplyEnable!=null && dispReplyEnable.equalsIgnoreCase("Y")){ %>
						<li class="breadcrumb-item "><a href="ClaimDisputeList.htm">Claim Disputes</a></li>
						<%}else if(SidebarActive.equalsIgnoreCase("CHSSDashboard_htm")) {%>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<%}else if(SidebarActive.equalsIgnoreCase("CHSSApprovalsList_htm")) {%>
						<li class="breadcrumb-item "><a href="CHSSIPDApprovalsList.htm">Claims Pending</a></li>
						<%} else if(SidebarActive.equalsIgnoreCase("ClaimsList_htm")) {%>
						<li class="breadcrumb-item "><a href="ClaimsList.htm">Claims List</a></li>
						<%}else if(SidebarActive.equalsIgnoreCase("ApprovedBills_htm")) {%>
						<li class="breadcrumb-item "><a href="ApprovedBills.htm">Approved Bills List</a></li>
						<%} %>
						<li class="breadcrumb-item active " aria-current="page">Claim</li>
					</ol>S
				</div>
			</div>
	</div>	
	
	
	
	<div class="page card dashboard-card">
		
			<div align="center">
					<%String ses=(String)request.getParameter("result"); 
					String ses1=(String)request.getParameter("resultfail");
					if(ses1!=null){ %>
						<div class="alert alert-danger" role="alert"  style="margin-top: 5px;">
							<%=ses1 %>
						</div>
						
					<%}if(ses!=null){ %>
						
						<div class="alert alert-success" role="alert"  style="margin-top: 5px;">
							<%=ses %>
						</div>
					<%} %>
				</div>
			<div class="card-body" >
				<div class="card" style="padding-top:0px;margin-top: -15px;">
					<div class="card-body main-card " style="padding-top:0px;margin-top: -10px;"  >
					
						
						<div class="" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
							<div class="row">
								<b> &nbsp; Claim Id : </b><%=chssapplydata[16] %>
							</div>
							<div class="row">
								<table style="border: 0px; width: 100%">
									<tr>
										<td style="width: 20%; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
										<td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h3> MEDICAL CLAIM - IPD </h3> </td>
										<td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;">No.of ENCL : &nbsp;<span class="text-blue"><%=chssapplydata[8] %></span></span> </td>
									</tr>
								</table> 
							
								<table style="margin-top: 5px;margin-bottom: 0px;">	
									<tbody>
										<tr>
											<th colspan="3" style="border: 0px"> Claim No : <%=chssapplydata[16] %></th>
										</tr>
										<tr>
											<th>Name</th>
											<th>Emp No</th>
											<th>Grade</th>
										</tr>
										<tr>
											<td class="text-blue" style="text-transform: capitalize;"><%=employee[2] %></td>
											<td class="text-blue" ><%=employee[1] %></td>
											<td class="text-blue" ><%=employee[13] %></td>
										</tr>
									</tbody>
								</table>
		
								
								<table style="margin-top: 0px;">	
									<tbody>
										<tr>
											<td><b>Basic Pay : </b> &#8377;<span class="text-blue" ><%=employee[4] %></span>  </td>
											<td colspan="2"><b>Level in The Pay Matrix : </b> <span class="text-blue" ><%=employee[9] %></span></td>
											<td colspan="2"><b>Ph.No. : </b> <span class="text-blue" ><%=employee[8]%></span></td>
											<td colspan="2"><b>Ext.No. : </b> <span class="text-blue" ><%=employee[14]%></span></td>
										</tr>
									</tbody>
								</table>
								<table >
									<tr>
												
									<%if(isself.equalsIgnoreCase("N")){
										Object[] familyMemberData = (Object[])request.getAttribute("familyMemberData") ; %>
										<td >	
											<b>Patient Name : &nbsp;</b> <%=familyMemberData[1] %>
										</td>
										<td>
										
											<b>Relation : &nbsp;</b><%=familyMemberData[7] %>
										</td>
										<td>
											<b> Gender: &nbsp;</b><%if(familyMemberData[9].toString().equalsIgnoreCase("M")){ %> Male <%}else if(familyMemberData[9].toString().equalsIgnoreCase("F")){ %> Female  <%}else{ %>Other<%} %>
										</td>
										<td>
											<b>DOB : &nbsp;</b><%=DateTimeFormatUtil.SqlToRegularDate(familyMemberData[3].toString()) %>
										</td>
										
									<%}else{ %>
										
										<td>
											<b> Patient Name : &nbsp;</b><%=employee[2] %>
										</td>
										<td>
											<b>Relation : &nbsp;</b>SELF
										</td>
										<td>
											<b> Gender: &nbsp;</b><%if(employee[5].toString().equalsIgnoreCase("M")){ %> Male <%}else if(employee[5].toString().equalsIgnoreCase("F")){ %> Female <%}else{ %>Other<%} %>
										</td>
										<td>
											<b>DOB : &nbsp;</b><%=DateTimeFormatUtil.SqlToRegularDate(employee[10].toString()) %>
										</td>
									
									<% } %>
									</tr>
									<tr>
										<td colspan="2">	
											<b>Treatment Type : &nbsp;&nbsp;</b><%=chssapplydata[10] %><br>
										</td>
										<td colspan="2">	
											<b>Ailment/Disease/Accident : &nbsp;&nbsp;</b><%=chssapplydata[17] %><br>
										</td>
									</tr>
								</table>			
							
							</div>
						
						<!-- -------------------------------------------------- Basic Details ------------------------------------------------------ -->						
						<div class="row">
							<div class="col-md-12" align="center">
								<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;"> Basic Details</span>
							</div>
							<table>
								<tr>
									<td colspan="4">
										<b>Hospital :&nbsp;&nbsp;</b><%=ipdbasicinfo.getHospitalName()%>
									</td>
									<%-- <td>
										<b>Room Type :&nbsp;&nbsp;</b><%=ipdbasicinfo.getRoomType()%>
									</td> --%>
								</tr>
								<tr>
									<td>
										<b>Admitted Date :&nbsp;&nbsp;</b><%=DateTimeFormatUtil.SqlToRegularDate(ipdbasicinfo.getAdmissionDate())%>
									</td>											
									<td>
										<b>Time :&nbsp;&nbsp;</b><%=LocalTime.parse(ipdbasicinfo.getAdmissionTime()) %>
									</td>
									<td>
										<b>Discharged Date :&nbsp;&nbsp;</b><%=DateTimeFormatUtil.SqlToRegularDate(ipdbasicinfo.getDischargeDate())%>
									</td>
									<td>
										<b>Time :&nbsp;&nbsp;</b><%=LocalTime.parse(ipdbasicinfo.getDischargeTime())%>
									</td>
								</tr>
							
								<tr>
									<td colspan="4">
										<span style="font-weight: 600; font-size: 15px;color: #CA4E79;text-decoration: underline;"> Claim For :</span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<b>Domiciliary Hospitalization :</b>
										&nbsp;&nbsp; <%if(ipdbasicinfo.getDomiciliaryHosp()==1){ %> YES <%}else { %>NO <%} %>
									</td>
									<td>
										<b>Day Care :</b>
										&nbsp;&nbsp; <%if(ipdbasicinfo.getDayCare()==1){ %> YES <%}else { %>NO <%} %>
									</td>
									<td>
										<b>Extended Care / Inpatient Rehabiliatation :</b>
										&nbsp;&nbsp;  <%if( ipdbasicinfo.getExtCareRehab()==1){ %>YES <%}else { %>NO <%} %>
									</td>
								</tr>
							</table>
								
							</div>
						
				
			<!-- -------------------------------------------------- Basic Details ------------------------------------------------------ -->
						
			<!-- -------------------------------------------------- Bill Details ------------------------------------------------------ -->
					
								<table style="margin-top: 0px;">	
									<tbody>
										<tr>
											<th class="center" style="width: 5%;" >SN</th>
											<th style="width: 30%; max-width: 30% !important" >Hospital / Medical / Diagnostics Center Name</th>
											<th style="width: 17%;" >Bill / Receipt No.</th>
											<th class="center" style="width: 10%;" >Date</th>
											<th style="text-align: right;width: 13%;">Total (&#8377;)</th>
										</tr>
										<% 	BigDecimal billstotal = new BigDecimal(0);
											BigDecimal discount = new BigDecimal(0);/*,  GST=0 */;
											for(int i=0;i<chssbill.size();i++)
											{
												billstotal =billstotal.add (new BigDecimal(chssbill.get(i)[7].toString()).setScale(0, BigDecimal.ROUND_HALF_UP));
												if(Double.parseDouble(chssbill.get(i)[8].toString())>0)
												{
													discount =discount.add (new BigDecimal(chssbill.get(i)[6].toString()));
												}
											%>
												<tr>
													<td class="center text-blue"><%=i+1 %></td>
													<td class="text-blue"><%=chssbill.get(i)[3] %></td>
													<td class="text-blue"><%=chssbill.get(i)[2] %></td>
													<td class="center text-blue" ><%=rdf.format(sdf.parse(chssbill.get(i)[4].toString())) %></td>
													<td class="text-blue" style="text-align: right;"><%=chssbill.get(i)[7] %></td>
												</tr>
											<%} %>
											<%if(chssbill.size()==0){ %>
												<tr>
													<td colspan="6" class="center" >Bills Not Added</td>
												</tr>
											<% } %>
									</tbody>
								</table>
			
			
			<%if(billid>0){ %>
			
						<div class="row" id="tab-scroll-sh" align="center">
							<div class="col-md-12" >
				   				<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;"> Bill Breakup</span>
						   	</div>
							<div class="col-md-12">
								<hr>
								<div class="row" style="margin-top: 5px;font-weight: bold;"    >
										<div class="col-md-5"></div>
										<div class="col-md-7" >
											
											<ul class="legend" style="float:right; ">
												<li> Comments By :</li>	
											    <li><span style=" background-color: #008005;" ></span> System Generated</li>
											    <li><span style=" background-color: #A300B5;" ></span> Processing Officer</li>
											    <li><span style=" background-color: #0CB5ED;" ></span> Verifying Officer </li>
											</ul>
											
										</div>
									</div>
								<form action="ConsultRemAmountEdit.htm" method="post" autocomplete="off">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<table>
										<tbody>
												
											<tr>
												<td colspan="7" style="text-align: center;padding: 0;">
													<h4><span>MEDICAL REIMBURSEMENT DETAILS</span>  </h4>
												</td>
											</tr> 
											<tr>
												<th class="center" colspan="4" style="width: 55%;">Particulars</th>
												<th class="right" style="width: 7%;">Amount Claimed (&#8377;)</th>
												<th class="right" style="width: 10%;">Reimbursable under CHSS (&#8377;)</th>
												<th class="center" style="width: 25%;">Comments</th>
											</tr>
											
							<!-- ----------------------------- package ---------------------------------- -->			
											<%
												BigDecimal itemstotal=new BigDecimal("0.0");
												BigDecimal totalremamount=new BigDecimal("0.0"); 
											%>
												
											<% 
													int i=1;
													for(Object[] pkg :ClaimPackages)	
													{ %>
													
														<%if(i==1){ %>
															<tr>
																<td colspan="4" style="text-align: center;">
																	<b>Package(s) </b>
																</td>
																<td></td>
																<td ></td>
																<td ></td>
															</tr>
														<% } %>
														<tr>
															<td class="text-blue" colspan="4"><%=pkg[3] %>&nbsp; (<%=pkg[9] %>)</td>
															<td class="text-blue right" ><%=pkg[4] %></td>									
														<%if(showRemAmt){ %>
															<td class="right ">
																<%if(pkg[7]== null   || Long.parseLong(pkg[7].toString())==0){ %>	 
																	<span class="systemgen" ><%=pkg[5]%></span>		
																<%}else if(pkg[8].toString().equalsIgnoreCase("K") || pkg[8].toString().equalsIgnoreCase("B")){ %>
																	<span class="processed" ><%=pkg[5]%></span>		
																<%}else if(pkg[8].toString().equalsIgnoreCase("V")){ %>
																	<span class="verified" ><%=pkg[5]%></span>		
																<%} %>
															</td>
															<td>
																<%if(pkg[6]!=null){ %>
																	<%if(pkg[7]== null   || Long.parseLong(pkg[7].toString())==0){ %>	  
																		<span class="systemgen" ><%=pkg[6]%></span>		
																	<%}else if(pkg[8].toString().equalsIgnoreCase("K") || pkg[8].toString().equalsIgnoreCase("B")){ %>
																		<span class="processed" ><%=pkg[6]%></span>		
																	<%}else if(pkg[8].toString().equalsIgnoreCase("V")){ %>
																		<span class="verified" ><%=pkg[6]%></span>		
																	<%} %>
																<%} %>
															</td>
														<%}else if(allowEdit){ %>
															<td class="right">	
																<input type="number" class="cost-only" step=".01" style="width: 100%;text-align: right; " max="<%=pkg[4] %>" name="pkg_remamount-<%=pkg[0]%>" style="text-align: right;" value="<%=pkg[5]%>">
															</td>
															<td >
																<input type="text" maxlength="255"  style="width: 85%;word-break: break-word;" placeholder="Comments" name="pkg_comment-<%=pkg[0]%>" style="text-align: right;" <%if(pkg[6]!=null){ %> value="<%=pkg[6] %>" <%}else{ %> value="" <%} %> >
																
																<button type="submit" class="btn btn-sm editbtn" formaction="IPDPkgRemAmountEdit.htm" name="chssbillpkgid" value="<%=pkg[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update"> 
																	<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
																</button>	
															</td>
														<% }else {%>
															<td></td>
															<td></td>	
														<%} %>	
														</tr>
														
														
														<%
														int count=1;
														for(Object[] pkgitem : ClaimPkgItems)
														{
															if(Integer.parseInt(pkgitem[2].toString()) == Integer.parseInt(pkg[0].toString()) )
															{
														%>
															<tr>
																<td><%=count++%></td>
																<td colspan="2"><%=pkgitem[3] %></td>
																<td class="right" style="width: 10%;"><%=pkgitem[4] %></td>
																<td ></td>
																<td ></td>
																<td ></td>
															</tr>														
														<%	} %>
														<%} %>
													<% 
													i++;
													itemstotal =itemstotal.add (new BigDecimal(pkg[4].toString()));
													
													totalremamount =totalremamount.add (new BigDecimal(pkg[5].toString()));
													
													}%>
											
								<!-- ----------------------------- package ---------------------------------- -->
										
								<!-- ----------------------------- non package ---------------------------------- -->
												<%  i=1;
													for(Object[] pkgIten :NonPackageItems)	
													{ %>
													<%if( pkgIten[4] !=null && Double.parseDouble(pkgIten[4].toString())>0){  %>
													
														<%if(i==1){ %>
															<tr>
																<td colspan="4" style="text-align: center;">
																	<b>Bill Items </b>
																</td>
																<td></td>
																<td ></td>
																<td ></td>
															</tr>
															<tr>
																<th colspan="4">Bill Head</th>
																<th ></th>
																<th></th>
																<th></th>
															</tr>			
														<% } %>
														<tr>
															<td class="text-blue" colspan="4"><%=pkgIten[1] %></td>
															<td class="text-blue right" ><%=pkgIten[4] %></td>									
														<%if(showRemAmt){ %>
															<td class="right ">
																<%if(pkgIten[7]== null   || Long.parseLong(pkgIten[7].toString())==0){ %>	 
																	<span class="systemgen" ><%=pkgIten[5]%></span>		
																<%}else if(pkgIten[8].toString().equalsIgnoreCase("K") || pkgIten[8].toString().equalsIgnoreCase("B")){ %>
																	<span class="processed" ><%=pkgIten[5]%></span>		
																<%}else if(pkgIten[8].toString().equalsIgnoreCase("V")){ %>
																	<span class="verified" ><%=pkgIten[5]%></span>		
																<%} %>
															</td>
															<td>
																<%if(pkgIten[6]!=null){ %>
																	<%if(pkgIten[7]== null   || Long.parseLong(pkgIten[7].toString())==0){ %>	  
																		<span class="systemgen" ><%=pkgIten[6]%></span>		
																	<%}else if(pkgIten[8].toString().equalsIgnoreCase("K") || pkgIten[8].toString().equalsIgnoreCase("B")){ %>
																		<span class="processed" ><%=pkgIten[6]%></span>		
																	<%}else if(pkgIten[8].toString().equalsIgnoreCase("V")){ %>
																		<span class="verified" ><%=pkgIten[6]%></span>		
																	<%} %>
																<%} %>
															</td>
														<%}else if(allowEdit){ %>
															<td class="right">	
																<input type="number" class="cost-only"   step=".01" style="width: 100%;text-align: right; "  name="otherremamount-<%=pkgIten[2]%>"  <%if(Integer.parseInt(pkgIten[0].toString())!=1){ %> max="<%=pkgIten[4] %>"  <%} %>  style="text-align: right;" value="<%=pkgIten[5]%>">
															</td>
															<td >
																<input type="text" maxlength="255"  style="width: 85%;word-break: break-word;" placeholder="Comments" name="othercomment-<%=pkgIten[2]%>" style="text-align: right;" <%if(pkgIten[6]!=null){ %> value="<%=pkgIten[6] %>" <%}else{ %> value="" <%} %> >
																
																<button type="submit" class="btn btn-sm editbtn" formaction="IPDBillheadRemAmountEdit.htm" name="chssotherid" value="<%=pkgIten[2]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update"> 
																	<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
																</button>	
															</td>
														<% }else {%>
															<td></td>
															<td></td>	
														<%} %>	
														</tr>
														
													<% 
													i++;
													itemstotal =itemstotal.add (new BigDecimal(pkgIten[4].toString()));
													totalremamount =totalremamount.add (new BigDecimal(pkgIten[5].toString()));
													
													} } %>
											
																						
								<!-- ----------------------------- non package  ---------------------------------- -->
								
								<!-- ----------------------------- consultation ---------------------------------- -->		
												<% 
													 i=1;
													for(Object[] consult :consultations)	
													{ %>
														<%if(i==1){ %>
															<tr>
																<td colspan="4" style="text-align: center;">
																	<b>Consultation Charges </b>
																</td>
																<td></td>
																<td ></td>
																<td ></td>
															</tr>
															<tr>
																<th colspan="3">Doctor</th>
																<!-- <th style="width:10%;">Type</th> -->
																<th style="width:10%;" class="center">Date</th>
																<th></th>
																<th></th>
																<th></th>
															</tr>			
														<% } %>
														<tr>
															<td class="text-blue" colspan="3" ><%=consult[3] %>&nbsp;(<%=consult[11] %>)</td>
															<%-- <td class="text-blue" >
																<% if(allowEdit){ %>
																	<select name="consulttype-<%=consult[0]%>"  class="form-control"  >
																		<option value="Fresh" <%if(consult[2].toString().trim().equalsIgnoreCase("Fresh")){ %> selected <%} %> >Fresh</option>
																		<option value="FollowUp" <%if(consult[2].toString().trim().equalsIgnoreCase("FollowUp")){ %> selected <%} %> >FollowUp</option>
																	</select>
																<%}else{ %>
																	<%=consult[2] %>
																<% } %>
															</td> --%>
															<td class="center text-blue"><%=rdf.format(sdf.parse(consult[5].toString()))%></td>
															<td class="right text-blue"><%=consult[6] %></td>
															
														<%if(showRemAmt){ %>
															<td class="right ">
																
																<%if(consult[12]== null   || Long.parseLong(consult[12].toString())==0){ %>	 
																	<span class="systemgen" ><%=consult[7]%></span>		
																<%}else if(consult[13].toString().equalsIgnoreCase("K") || consult[13].toString().equalsIgnoreCase("B")){ %>
																	<span class="processed" ><%=consult[7]%></span>		
																<%}else if(consult[13].toString().equalsIgnoreCase("V")){ %>
																	<span class="verified" ><%=consult[7]%></span>		
																<%} %>
																
																
															</td>
															<td>
																<%if(consult[10]!=null){ %>
																	<%if(consult[12]== null || Long.parseLong(consult[12].toString())==0){ %>	 
																		<span class="systemgen" ><%=consult[10]%></span>		
																	<%}else if(consult[13].toString().equalsIgnoreCase("K") || consult[13].toString().equalsIgnoreCase("B")){ %>
																		<span class="processed" ><%=consult[10]%></span>		
																	<%}else if(consult[13].toString().equalsIgnoreCase("V")){ %>
																		<span class="verified" ><%=consult[10]%></span>		
																	<%} %>
																<%} %>
															</td>
														<%}else if(allowEdit){ %>
															<td class="right">	
																<input type="number" class="cost-only" step=".01" style="width: 100%;text-align: right; " max="<%=consult[6] %>" name="consultremamount-<%=consult[0]%>" style="text-align: right;" value="<%=consult[7]%>">
															</td>
															<td >
																<input type="text" maxlength="255"  style="width: 85%;word-break: break-word;" placeholder="Comments" name="consultcomment-<%=consult[0]%>" style="text-align: right;" <%if(consult[10]!=null){ %> value="<%=consult[10] %>" <%}else{ %> value="" <%} %> >
																
																<button type="submit" class="btn btn-sm editbtn" formaction="IPDConsultRemAmountEdit.htm" name="consultationid" value="<%=consult[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update"> 
																	<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
																</button>	
															</td>
														<% }else {%>
															<td></td>
															<td></td>		
														<%} %>	
														</tr>
													<%	i++;
													itemstotal =itemstotal.add (new BigDecimal(consult[6].toString()));
													totalremamount =totalremamount.add (new BigDecimal(consult[7].toString()));
													} %>
									<!-- ----------------------------- consultation ---------------------------------- -->
									
									<!-- ----------------------------- Tests ---------------------------------- -->
															
													<% i=1;
											for(Object[] test :billtests)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Test / Investigations</b> 
														</td>
														<td></td>
														<td ></td>
														<td ></td>
													</tr>
													<tr>
														<th colspan="4">Test</th>
														<th></th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td  class="text-blue" colspan="4"><%=test[6] %>(<%=test[10] %>)</td>
													<td class="right text-blue"><%=test[4] %></td>
												
													
													<%if(showRemAmt){ %>
														<td class="right ">	
															<%if(test[12]== null  || Long.parseLong(test[12].toString())==0){ %>	 
																<span class="systemgen" ><%=test[7]%>	</span>		
															<%}else if(test[13].toString().equalsIgnoreCase("K") || test[13].toString().equalsIgnoreCase("B")){ %>
																<span class="processed" ><%=test[7]%>	</span>		
															<%}else if(test[13].toString().equalsIgnoreCase("V")){ %>
																<span class="verified" ><%=test[7]%>	</span>		
															<%} %>
														</td>	
														<td>
															<%if(test[11]!=null){ %>
																<%if(test[12]== null || Long.parseLong(test[12].toString())==0){ %>	 
																	<span class="systemgen" ><%=test[11]%>	</span>		
																<%}else if(test[13].toString().equalsIgnoreCase("K") || test[13].toString().equalsIgnoreCase("B")){ %>
																	<span class="processed" ><%=test[11]%>	</span>		
																<%}else if(test[13].toString().equalsIgnoreCase("V")){ %>
																	<span class="verified" ><%=test[11]%>	</span>		
																<%} %>
															<%} %>
														</td>
													<%}else if(allowEdit){ %>
														<td class="right">	
															<input type="number" class="cost-only" step=".01" style="width: 100%;text-align: right;" name="testremamount-<%=test[0]%>" max="<%=test[4] %>" style="text-align: right;" value="<%=test[7]%>">
														</td>
														<td >
														<input type="text" maxlength="255" style="width: 85%;word-break: break-word;" placeholder="Comments" name="testcomment-<%=test[0]%>" style="text-align: right;" <%if(test[11]!=null){ %> value="<%=test[11] %>" <%}else{ %> value="" <%} %> >
															
														<button type="submit" class="btn btn-sm editbtn" formaction="IPDTestRemAmountEdit.htm"  name="testid" value="<%=test[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>
														</td>
													<% }else {%>
														<td></td>
														<td></td>		
													<%} %>											
												</tr>					
											<%i++;
																						
											itemstotal =itemstotal.add (new BigDecimal(test[4].toString()));
											totalremamount =totalremamount.add (new BigDecimal(test[7].toString()));
											
											} %>
							<!-- ----------------------------- Tests ---------------------------------- -->
							
							<!-- ----------------------------- Equipment ---------------------------------- -->
															
													<% i=1;
											for(Object[] equipment :equipments)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Equipment Charges</b> 
														</td>
														<td></td>
														<td ></td>
														<td ></td>
													</tr>
													<tr>
														<th colspan="4">Equipment Name</th>
														<th></th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td  class="text-blue" colspan="4"><%=equipment[2] %></td>
													<td class="right text-blue"><%=equipment[3] %></td>
												
													
													<%if(showRemAmt){ %>
														<td class="right ">	
															<%if(equipment[8]== null  || Long.parseLong(equipment[8].toString())==0){ %>	 
																<span class="systemgen" ><%=equipment[4]%>	</span>		
															<%}else if(equipment[9].toString().equalsIgnoreCase("K") || equipment[9].toString().equalsIgnoreCase("B")){ %>
																<span class="processed" ><%=equipment[4]%>	</span>		
															<%}else if(equipment[9].toString().equalsIgnoreCase("V")){ %>
																<span class="verified" ><%=equipment[4]%>	</span>		
															<%} %>
														</td>	
														<td>
															<%if(equipment[7]!=null){ %>
																<%if(equipment[8]== null  || Long.parseLong(equipment[8].toString())==0){ %>		 
																	<span class="systemgen" ><%=equipment[7]%>	</span>		
																<%}else if(equipment[9].toString().equalsIgnoreCase("K") || equipment[9].toString().equalsIgnoreCase("B")){ %>
																	<span class="processed" ><%=equipment[7]%>	</span>		
																<%}else if(equipment[9].toString().equalsIgnoreCase("V")){ %>
																	<span class="verified" ><%=equipment[7]%>	</span>		
																<%} %>
															<%} %>
														</td>
													<% }else if(allowEdit){ %>
														<td class="right">	
															<input type="number" class="cost-only" step=".01" style="width: 100%;text-align: right;" name="equipremamount-<%=equipment[0]%>" max="<%=equipment[3] %>" style="text-align: right;" value="<%=equipment[4]%>">
														</td>
														<td >
														<input type="text" maxlength="255" style="width: 85%;word-break: break-word;" placeholder="Comments" name="equipcomment-<%=equipment[0]%>" style="text-align: right;" <%if(equipment[7]!=null){ %> value="<%=equipment[7] %>" <%}else{ %> value="" <%} %> >
															
														<button type="submit" class="btn btn-sm editbtn" formaction="IPDEquipRemEdit.htm"  name="equipmentid" value="<%=equipment[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>
														</td>
													<% }else {%>
														<td></td>
														<td></td>	
													<% } %>											
												</tr>					
											<%i++;
																						
											itemstotal =itemstotal.add (new BigDecimal(equipment[3].toString()));
											totalremamount =totalremamount.add (new BigDecimal(equipment[4].toString()));
											
											} %>
							<!-- ----------------------------- Equipment ---------------------------------- -->	

							<!-- ----------------------------- Implant ---------------------------------- -->
															
											<% i=1;
											for(Object[] implant :implants)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Implant(s)</b> 
														</td>
														<td></td>
														<td ></td>
														<td ></td>
													</tr>
													<tr>
														<th colspan="4">Implant Name</th>
														<th></th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td  class="text-blue" colspan="4"><%=implant[2] %></td>
													<td class="right text-blue"><%=implant[3] %></td>
												
													
													<%if(showRemAmt){ %>
														<td class="right ">	
															<%if(implant[8]== null  || Long.parseLong(implant[8].toString())==0){ %>	 
																<span class="systemgen" ><%=implant[4]%>	</span>		
															<%}else if(implant[9].toString().equalsIgnoreCase("K") || implant[9].toString().equalsIgnoreCase("B")){ %>
																<span class="processed" ><%=implant[4]%>	</span>		
															<%}else if(implant[9].toString().equalsIgnoreCase("V")){ %>
																<span class="verified" ><%=implant[4]%>	</span>		
															<%} %>
														</td>	
														<td>
															<%if(implant[7]!=null){ %>
																<%if(implant[8]== null  || Long.parseLong(implant[8].toString())==0){ %>		 
																	<span class="systemgen" ><%=implant[7]%>	</span>		
																<%}else if(implant[9].toString().equalsIgnoreCase("K") || implant[9].toString().equalsIgnoreCase("B")){ %>
																	<span class="processed" ><%=implant[7]%>	</span>		
																<%}else if(implant[9].toString().equalsIgnoreCase("V")){ %>
																	<span class="verified" ><%=implant[7]%>	</span>		
																<%} %>
															<%} %>
														</td>
													<% }else if(allowEdit){ %>
														<td class="right">	
															<input type="number" class="cost-only" step=".01" style="width: 100%;text-align: right;" name="implantremamount-<%=implant[0]%>" max="<%=implant[3] %>" style="text-align: right;" value="<%=implant[4]%>">
														</td>
														<td >
														<input type="text" maxlength="255" style="width: 85%;word-break: break-word;" placeholder="Comments" name="implantcomment-<%=implant[0]%>" style="text-align: right;" <%if(implant[7]!=null){ %> value="<%=implant[7] %>" <%}else{ %> value="" <%} %> >
															
														<button type="submit" class="btn btn-sm editbtn" formaction="IPDImplantRemEdit.htm"  name="implantid" value="<%=implant[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>
														</td>
													<% }else {%>
														<td></td>
														<td></td>		
													<% } %>											
												</tr>					
											<%i++;
																						
											itemstotal =itemstotal.add (new BigDecimal(implant[3].toString()));
											totalremamount =totalremamount.add (new BigDecimal(implant[4].toString()));
											
											} %>
							<!-- ----------------------------- Implant ---------------------------------- -->	

							<!-- ----------------------------- miscellaneous ---------------------------------- -->
											<% i=1;
											for(Object[] misc : miscitems)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Miscellaneous</b>
														</td>
														<td></td>
														<td ></td>
														<td ></td>
													</tr>
													<tr>
														<th colspan="3">Item</th>
														<th style="text-align: center;">Qty</th>
														<th></th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td  class="text-blue" colspan="3"><%=misc[2] %></td>
													<td class="text-blue"  style="text-align: center;"><%if(misc[8]!=null){ %><%=misc[8] %><%} %></td>
													<td class="right text-blue"><%=misc[3] %></td>
													
																										
													<%if(showRemAmt){ %>
														<td class="right">	
															<%if(misc[9]== null  || Long.parseLong(misc[9].toString())==0){ %>	 
																<span class="systemgen" > <%=misc[4]%>	</span>		
															<%}else if(misc[10].toString().equalsIgnoreCase("K") || misc[10].toString().equalsIgnoreCase("B")){ %>
																<span class="processed" > <%=misc[4]%></span>		
															<%}else if(misc[10].toString().equalsIgnoreCase("V")){ %>
																<span class="verified" > <%=misc[4]%>	</span>		
															<%} %>
														</td>	
														<td>
															<%if(misc[7]!=null){ %>
																<%if(misc[9]== null  || Long.parseLong(misc[9].toString())==0){ %>	 
																	<span class="systemgen" > <%=misc[7]%>	</span>		
																<%}else if(misc[10].toString().equalsIgnoreCase("K") || misc[10].toString().equalsIgnoreCase("B")){ %>
																	<span class="processed" > <%=misc[7]%></span>		
																<%}else if(misc[10].toString().equalsIgnoreCase("V")){ %>
																	<span class="verified" > <%=misc[7]%>	</span>		
																<%} %>
															<%} %>
														</td>
													<%}else if(allowEdit){ %>
														<td class="right">	
															<input type="number" class="cost-only" step=".01" style="width: 100%;text-align: right;" name="miscremamount-<%=misc[0]%>" max="<%=misc[3] %>" value="<%=misc[4]%>">
														</td>
														<td >
															<input type="text" maxlength="255" style="width: 85%;word-break: break-word;" placeholder="Comments" name="miscomment-<%=misc[0]%>" style="text-align: right;" <%if(misc[7]!=null){ %> value="<%=misc[7] %>" <%}else{ %> value="" <%} %> >
															<button type="submit" class="btn btn-sm editbtn" formaction="IPDMiscRemAmountEdit.htm" name="miscid" value="<%=misc[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
																<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
															</button>
														</td>
													<%} else { %>
														<td></td>
														<td></td>
													<%} %>
													
												</tr>					
											<%i++;
											itemstotal =itemstotal.add (new BigDecimal(misc[3].toString()));
											totalremamount =totalremamount.add (new BigDecimal(misc[4].toString()));
											}%>
										
							<!-- ----------------------------- miscellaneous ---------------------------------- -->
										<tr>
											<td colspan="4" class="right"><b>Total</b></td>
											<td class="right text-blue"><b><%=nfc.rupeeFormat(String.valueOf(itemstotal.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())) %></b></td>
															
											<td class="right text-green">
												
												<%if(showRemAmt || allowEdit){ %>	 
												 <b><%=nfc.rupeeFormat(String.valueOf(totalremamount.setScale(0, BigDecimal.ROUND_HALF_UP))) %></b>
												<%} %>
											</td>
											<td ></td>
										</tr>
																				
										<tr>
											<td colspan="7" class="text-blue">(In words Rupees <%=awc.convert1(itemstotal.setScale(0, BigDecimal.ROUND_HALF_UP).longValue()) %> Only)</td> 
										</tr>
										
										<tr>
											<td colspan="7" class="center"><span style="text-decoration: underline;"><b>FOR OFFICE USE ONLY</b></span></td>
										</tr>
										
										<tr>
											<td colspan="7" class="text-green">Admitted to Rs.
												<%if(showRemAmt || allowEdit){%>
												<%= nfc.rupeeFormat(String.valueOf(totalremamount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())) %> (Rupees  <%=awc.convert1(totalremamount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue()) %> Only)
												<%}else{ %>
													&#8377;  ............................. (Rupees ...........................................................................................Only)
												<%} %>
											</td> 
										</tr>
										
										<tr>
											<th colspan="7" style="text-align: center;">Attachments </th>
										</tr>
										
										<tr>
											<th style="width:5%;text-align:center;  " >SN</th>
											<th colspan="5" style="width:50%;" >Type of Document(s)	</th>
											<th style="width:20%;" >Is Attached</th>
										</tr>
									
									<%for(Object[]  ClaimAttach: ClaimAttachDeclare){ %>
										<tr>
											<td style="text-align:center;"><%=ClaimAttachDeclare.indexOf(ClaimAttach)+1 %></td>
											<td colspan="5" ><%=ClaimAttach[1] %>	</td>
											<td style="text-align: center;">
												<% if(ClaimAttach[2]!=null && ClaimAttach[4].toString().equalsIgnoreCase("Y")){ %>
													Yes
												<%}else{ %>
													No
												<%} %>
											</td>
										</tr>
									<%} %>
									
										<tr>
											<td colspan="7" style="text-align:center; ;border-bottom : 0;text-decoration: underline;"><b>Finance and Accounts Department</b></td>
										</tr>
										<tr>
											<td colspan="4" style="border-top: 0;border-right : 0;">
											
												<ul style="list-style-type: none;margin:10px 5px -25px -35px;">
													<%for(Object[] obj:ClaimapprovedPOVO){
														if(obj[1].toString().equalsIgnoreCase("PO")){%>
														<li style="text-transform: capitalize;"><%=obj[2] %>,</li>
														<li style="text-transform: capitalize;"><%=obj[4] %> </li>
													<% } } %>
												</ul>
											
											</td>
											<td colspan="4" style="border-top: 0;border-left : 0;height: 80px;" >
											
												<ul style="float: right;list-style-type: none; margin:10px 5px -25px 0px; ">
													<%for(Object[] obj:ClaimapprovedPOVO){
														if(obj[1].toString().equalsIgnoreCase("VO")){%>
														<li style="text-transform: capitalize;"><%=obj[2] %>,</li>
														<li style="text-transform: capitalize;"><%=obj[4] %></li>
													<% } } %>
												</ul>					
											</td>
										</tr>
																				
										</tbody>						
									</table>
								
								</form>
							
						</div>
					</div>
					<%-- <div class="row" id="tab-scroll-at" align="center">
				   			<div class="col-md-12" >
				   				<div align="center"><b>Attachments</b></div>
				   			</div>
				   			<div class="col-md-12" >
				   				
				   				<table style="width: 70%;">
									<thead>
										<tr>
											<th style="width:5%;text-align:center;  " >SN</th>
											<th style="width:50%;" >Type of Document(s)	</th>
											<th style="width:20%;" >Is Attached</th>
										</tr>
									</thead>
									<tbody>
									<%for(Object[]  ClaimAttach: ClaimAttachDeclare){ %>
										<tr>
											<td style="text-align:center;"><%=ClaimAttachDeclare.indexOf(ClaimAttach)+1 %></td>
											<td><%=ClaimAttach[1] %>	</td>
											<td style="text-align: center;">
												<% if(ClaimAttach[2]!=null && ClaimAttach[4].toString().equalsIgnoreCase("Y")){ %>
													Yes
												<%}else{ %>
													No
												<%} %>
											</td>
										</tr>
									<%} %>
									</tbody>
				   				</table>				   			
				   			</div>
				   		</div> --%>
				
				
					<form action="CHSSUserIPDForward.htm" method="post" id="fwdform">
						<div class="row">
						<%if(ClaimRemarksHistory.size()>0){ %>
							<div class="col-md-5" align="center" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
								<%if(ClaimRemarksHistory.size()>0){ %>
									<table style="margin: 3px;padding: 0px">
										<tr>
											<td style="border:none;padding: 0px">
												<h6 style="text-decoration: underline;">Remarks :</h6> 
											</td>											
										</tr>
										<%for(Object[] obj : ClaimRemarksHistory){%>
										<tr>
											<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
												<%=obj[3] %>&nbsp; :
												<span style="border:none;" class="text-blue" >	<%=obj[1] %></span>
											</td>
										</tr>
										<%} %>
									</table>
								<%} %>
							</div>
							<%}else { %>
								<div class="col-md-5" ></div>
							<%} %>
							<%if(Arrays.asList("UF","E","A").contains(view_mode)){ %>	
							<div class="col-md-6" align="center" style="margin-top: 5px;">
							
							<%if(chssstatusid!=8 ){ %>
								<div class="col-md-12" align="left" style="margin-bottom: 5px;">
									Remarks : <br>
									<textarea class="w-100 form-control" rows="4" cols="100" id="remarks" name="remarks" maxlength="500"></textarea>
								</div>
							<%} %>
							
							<%if(chssstatusid==2 ||  chssstatusid==5 ){ %>
								
								<button type="submit" class="btn btn-sm submit-btn" name="claimaction" value="F" onclick="return remarkRequired('F'); " formnovalidate="formnovalidate">Process</button>
								<button type="submit" class="btn btn-sm delete-btn" name="claimaction" value="R" onclick="return remarkRequired('R'); " formnovalidate="formnovalidate">Return</button>
							
							<%}else if( chssstatusid==4 ){ %>
								
								<button type="submit" class="btn btn-sm submit-btn" name="claimaction" value="F" onclick="return remarkRequired('F'); " formnovalidate="formnovalidate">Verify</button>
								<button type="submit" class="btn btn-sm delete-btn" name="claimaction" value="R" onclick="return remarkRequired('R'); " formnovalidate="formnovalidate">Return</button>
							
							<%}else if(chssstatusid==1 || chssstatusid==3 ||  chssstatusid==7){ %>
							
								<button type="button" class="btn btn-sm submit-btn" name="claimaction" value="F"   <%if(chssstatusid==3){ %> onclick="remarkscheck();" <%}else{%>data-toggle="modal" data-target=".my-encl-modal" <%} %> >
									<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for processing	
								</button>
								<button type="submit" class="btn btn-sm edit-btn" name="action" value="edit"  formaction="CHSSIPDApply.htm" formnovalidate="formnovalidate" data-toggle="tooltip" data-placement="top" title="Edit">
									Edit
								</button>
								<input type="hidden" name="claimaction" value="F" >	
									
							<%}else if(chssstatusid==6 ||chssstatusid==9 || chssstatusid==11 ||  chssstatusid==13){ %>
								<button type="submit" class="btn btn-sm delete-btn"  name="claimaction" value="R" onclick="return remarkRequired('R'); " >Return</button>
							<%} %>
							</div>
							<%}else if(chssstatusid ==15 && ClaimDisputeData !=null) { %>
							<div class="col-md-6" align="center" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
							<%
							if(ClaimDisputeData !=null){ %>
								<table style="margin: 3px;padding: 0px">
									<tr>
										<td style="border:none;padding: 0px">
											<h6 style="text-decoration: underline;">Dispute :</h6> 
										</td>											
									</tr>
									
									<tr>
										<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
											<%=employee[2] %>&nbsp; :
											<span style="border:none; color: red;" >	<%=ClaimDisputeData[2] %></span>
										</td>
									</tr>
									<%if(ClaimDisputeData[8]!=null){ %>
										<tr>
											<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
												Response By&nbsp;<%=ClaimDisputeData[8] %>&nbsp; :
												<span style="border:none;" class="text-blue" >	<%=ClaimDisputeData[3] %></span>
											</td>
										</tr>
									
									<% } %>
									
								</table>
							<%} %>
						</div>
						<% } %>
						</div>
						<input type="hidden" name="chssapplyidcb" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
							<div class="modal my-encl-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" align="center" style="left: 15%;">
								<div class="modal-dialog  modal-dialog-centered"  >
									<div class="modal-content" style="width: 50%;">
										<div class="modal-header">											
											 <button type="button" class="close" data-dismiss="modal" aria-label="Close">
										    	<i class="fa-solid fa-xmark" aria-hidden="true" ></i>
										    </button>
										</div>
										<div class="modal-body" >
									          <div class="row">
											    <div class="col-12">
											    	<b>No of Enclosures : </b><br>
													<input type="number" class="form-control numberonly1 w-100" name="enclosurecount" id="enclosurecount" value="<%=chssapplydata[8] %>" min="1" max="" maxlength="2" required="required" >
												</div>												
												 <div class="col-12 w-100" align="center">
												 <br>
												<button type="button" class="btn btn-sm submit-btn" name="claimaction" value="F"  onclick="return CheckClaimAmount(<%=chssapplydata[0]%>)" >
													submit
												</button>
												</div>
											</div>
										</div>
										
									</div>
								</div>	
							</div>
							
					</form>
					
					
					<%if(chssstatusid==15 && ActivateDisp!=null && ActivateDisp.equalsIgnoreCase("Y") && ClaimDisputeData==null){ %>
						
						<form action="ClaimDisputeSubmit.htm" method="post">
							<div class="row">
								<div class="col-md-12 w-100" align="left">
									<br><b>Raise Dispute:</b><br>
									<textarea rows="5" style="width: 100%;" maxlength="1000" name="disputemsg" id="disputemsg" required="required"></textarea>
								</div>
								<div class="col-md-12 w-100" align="center">
									<button type="submit" class="btn btn-sm submit-btn"  onclick="return checkDisputeMsg('disputemsg')" >submit dispute</button>
								</div>
								
							</div>
							<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
					<% }else if( dispReplyEnable!=null && dispReplyEnable.equalsIgnoreCase("Y") && ClaimDisputeData!=null && ClaimDisputeData[8]==null){ %>
						<form action="ClaimDisputeResponceSubmit.htm" method="post">
							<div class="row">
								<div class="col-md-12 w-100" align="left">
									<br><b>Response For Dispute Raised by  : </b><br>
									<textarea rows="5" style="width: 100%;" maxlength="1000" name="Responcemsg" id="Responcemsg" required="required"></textarea>
								</div>
								<div class="col-md-12 w-100" align="center">
									<button type="submit" class="btn btn-sm submit-btn" onclick="return checkDisputeMsg('Responcemsg')">submit Response</button>
								</div>
								
							</div>
							<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
						
					<% } %>				
				
			<%} %>
			
				</div>
			</div>
		</div>
		</div>
	</div>


<%if(allowEdit && (logintype.equals("K") || logintype.equals("B")) && chssapplydata[20].toString().equals("0") ){ %>

<div class="modal fade" id="my_acknowledge_model" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
	  <div class="modal-header" style="">
	  	<h5 class="modal-title" id="exampleModalLabel">Confirm</h5>
	  </div>
      <div class="modal-body">
      	
      	<b><span style="color: red">Did You Receive The Physical Copy of the Claim?</span></b>&nbsp;&nbsp;&nbsp;&nbsp;
     
        <button type="button" class="btn btn-sm btn-primary" data-dismiss="modal"  onclick="acknowledgeFunction(true)">Yes</button>
        <button type="button" class="btn btn-sm  btn-secondary"  onclick="acknowledgeFunction(false)">No</button>
      </div>
    </div>
  </div>
</div>



	
<script type="text/javascript">	 

	$('#my_acknowledge_model').modal('show')
 	  function acknowledgeFunction(yesorno){
		
		 if(yesorno)
		 {
			 $.ajax({

					type : "GET",
					url : "POAcknowledgeUpdateAjax.htm",
					data : {
							
						chssapplyid : $chssapplyid,
					},
					datatype : 'json',
					success : function(result) {
	
					}
				}); 
		}else
		{
			window.location = "CHSSApprovalsList.htm";
		}
	 	
	 
	 }
	 
	 
</script>
<%} %>	 
	 





<script type="text/javascript">

var $chssapplyid = <%=chssapplydata[0]%>;

function  onlyNumbers() {    

    $('.numberonly').keypress( function (evt) {

	    if (evt.which > 31 &&  (evt.which < 48 || evt.which > 57) && evt.which!=46 )
	    {
	        evt.preventDefault();
	    } 
	});

}


$('.numberonly1').keypress(function (e) {    

    var charCode = (e.which) ? e.which : event.keyCode    

    if (String.fromCharCode(charCode).match(/[^0-9]/g))    

        return false;                        

		});


$(document).ready( function() {
	onlyNumbers();
});   


function remarkscheck()
{
	event.preventDefault();
	$('#remarks').attr('required', true);
	if($('#remarks').val().trim()===''){
		alert('Please Fill Remarks to Submit! ');
	}else{
		$('.my-encl-modal').modal('show');
	}
}


function remarkRequired(action)
{
	if(action === 'R'){
		$('#remarks').attr('required', true);
		if($('#remarks').val().trim()===''){
			alert('Please Fill Remarks to Return! ');
			return false;
		}else{
				return confirm('Are You Sure To Return?');
		}
		
	}else{
		$('#remarks').attr('required', false);
		return confirm('Are You Sure To Verify?');
	}
	
}

function remarkscheck()
{
	event.preventDefault();
	$('#remarks').attr('required', true);
	if($('#remarks').val().trim()===''){
		alert('Please Fill Remarks to Submit! ');
	}else{
		$('.my-encl-modal').modal('show');
	}
}



</script>

<script type="text/javascript">

function CheckClaimAmount($chssapplyid)
{
	$.ajax({

		type : "GET",
		url : "CHSSIPDClaimFwdApproveAjax.htm",
		data : {
				
			chssapplyid : $chssapplyid,
		},
		datatype : 'json',
		success : function(result) {
		
			if(result==="")
			{
				
				if(Number($('#enclosurecount').val())>0  )
				{
					if(Number($('#enclosurecount').val())>99)
					{
						alert(' No of Encloseres Should be less than 100 !');
					}
					else if(confirm("Are You Sure To Submit the bill for processing ?\nOnce submitted, data can't be changed"))
					{
						$('#fwdform').submit();
					}
				}
				else
				{
					alert('Please Enter No of Encloseres.');	
				}
				
			}else
			{
				alert(result);	
			}
		
		}
	});
	
}



</script>

</body>
</html>