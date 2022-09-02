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
<%	
	Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
	Object[] employee = (Object[])request.getAttribute("employee");
%>
	<head>
<style type="text/css">
		
			.break {
				page-break-after: always;
			}

			#pageborder {
				position: fixed;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
				border: 2px solid black;
			}

			@page {
				size: 790px 1120px;
				margin-top: 49px;
				margin-left: 72px;
				margin-right: 39px;
				margin-buttom: 49px;
				border: 2px solid black;

				@bottom-right {
					counter-increment: page;
  					counter-reset: page 2;
					content: "Page "counter(page) " of "counter(pages);
					margin-bottom: 30px;
					margin-right: 10px;
				}

				@top-right {
					content: "Claim No: <%=chssapplydata[16]%>";
					margin-top: 30px;
					margin-right: 10px;
				}

				@top-left {
					margin-top: 30px;
					margin-left: 10px;
					content: "Emp No: <%=employee[1] %>";
				}

				@top-center {
					margin-top: 30px;
					content: "";

				}

				@bottom-center {
					margin-bottom: 30px;
					content: "";
				}
				
				

			}

p {
	text-align: justify;
	text-justify: inter-word;
}
body
{
	font-size:14px !important;
}

div
{
	width: 650px !important;
}
table{
	align: left;
	width: 650px !important;
	max-width: 650px !important;
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
	
	 -ms-word-break: break-all;
     word-break: break-all;

     /* Non standard for WebKit */
     word-break: break-word;

-webkit-hyphens: auto;
   -moz-hyphens: auto;
        hyphens: auto;
	
}
.center{

	text-align: center;
}

.right
{
	text-align: right;
}
			
.text-blue
{
	color: blue;
}

.text-green
{
	color:  #008005;
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

 
			
</style>
<meta charset="ISO-8859-1">
		
</head>

<%


SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();	
AmountWordConveration awc = new AmountWordConveration();
IndianRupeeFormat nfc=new IndianRupeeFormat();


CHSSIPDClaimsInfo ipdbasicinfo = (CHSSIPDClaimsInfo)request.getAttribute("ipdbasicinfo") ;
String LabLogo = (String)request.getAttribute("LabLogo");

List<Object[]> ClaimPackages=(List<Object[]>)request.getAttribute("ClaimPackages");
List<Object[]> ClaimPkgItems=(List<Object[]>)request.getAttribute("ClaimPkgItems");

List<Object[]> chssbill=(List<Object[]>)request.getAttribute("chssbill");
/* List<Object[]> PackageItems=(List<Object[]>)request.getAttribute("PackageItems"); */
List<Object[]> NonPackageItems=(List<Object[]>)request.getAttribute("NonPackageItems");

List<Object[]> consultations=(List<Object[]>)request.getAttribute("consultations");
List<Object[]> billtests=(List<Object[]>)request.getAttribute("billtests");
List<Object[]> miscitems =(List<Object[]>)request.getAttribute("miscitems"); 
List<Object[]> equipments =(List<Object[]>)request.getAttribute("equipments");
List<Object[]> implants =(List<Object[]>)request.getAttribute("implants");
List<Object[]> ClaimRemarksHistory = (List<Object[]>)request.getAttribute("ClaimRemarksHistory");
List<Object[]> ClaimAttachDeclare = (List<Object[]>)request.getAttribute("ClaimAttachDeclare");

List<Object[]> ClaimapprovedPOVO = (List<Object[]>)request.getAttribute("ClaimapprovedPOVO");

int chssstatusid = Integer.parseInt(chssapplydata[9].toString());

String tab = (String)request.getAttribute("tab") ;
String isself = chssapplydata[3].toString();


long billid = 0;
if(chssbill.size()>0){
	billid = Long.parseLong(chssbill.get(0)[0].toString());
}
	
String view_mode=(String)request.getAttribute("view_mode");

boolean show = false;
if(view_mode!=null && ((view_mode.equalsIgnoreCase("U") || view_mode.equalsIgnoreCase("UF") )&& chssstatusid==15) || view_mode.equalsIgnoreCase("E") || view_mode.equalsIgnoreCase("V") ||  view_mode.equalsIgnoreCase("A")){
	show = true;
} 


%>

<body>
	
							<div align="center">
							
								<div style="width: 100%;float:left;">
									<div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
									<div style="margin-left:auto; margin-right:auto;"><h3 ><span style="margin-left: -85px; ">MEDICAL CLAIM - IPD</span></h3> <section style="float: right;"><span>No.of ENCL : &nbsp;<span class="text-blue"><%=chssapplydata[8] %></span></span> </section> </div>
								</div>
								
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
							<table style="margin-bottom: 0px;">
								<tr>
									<td colspan="4">
										<b>Hospital :&nbsp;&nbsp;</b><%=ipdbasicinfo.getHospitalName()%>
									</td>
									<%-- <td>
										<b>Room Type :&nbsp;&nbsp;</b><%=ipdbasicinfo.getRoomType()%>
									</td> --%>
								</tr>
								<tr>
									<td style="width:30% ">
										<b>Admitted Date :&nbsp;&nbsp;</b><%=DateTimeFormatUtil.SqlToRegularDate(ipdbasicinfo.getAdmissionDate())%>
									</td>											
									<td style="width:20% ">
										<b>Time :&nbsp;&nbsp;</b><%=LocalTime.parse(ipdbasicinfo.getAdmissionTime()) %>
									</td>
									<td style="width:30% ">
										<b>Discharged Date :&nbsp;&nbsp;</b><%=DateTimeFormatUtil.SqlToRegularDate(ipdbasicinfo.getDischargeDate())%>
									</td>
									<td style="width:20% ">
										<b>Time :&nbsp;&nbsp;</b><%=LocalTime.parse(ipdbasicinfo.getDischargeTime())%>
									</td>
								</tr>
							</table>
							<table style="margin-top: 0px;">
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
											<%if(chssbill.size()>0){ %>
												<tr>
													<td colspan="3"></td>
													<td style="text-align: right;"><b>Total </b></td>
													<td class="text-blue"  style="text-align: right;"><%=nfc.rupeeFormat(String.valueOf(billstotal.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())) %></td>
												</tr>
											<%}else{ %>
												<tr>
													<td colspan="6" class="center" >Bills Not Added</td>
												</tr>
											<% } %>
									</tbody>
								</table>
			
			
			<%if(billid>0){ %>
			
						<div class="row" id="tab-scroll-sh" align="center">
							<div class="col-md-12" >
				   				<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;"> Bill Details</span>
						   	</div>
							<div class="col-md-12">
						
									<table>
										<tbody>
												
											<tr>
												<td colspan="7" style="text-align: center;padding: 0;">
													<h4><span>MEDICAL REIMBURSEMENT DETAILS</span>  </h4>
												</td>
											</tr> 
											<tr>
												<th class="center" colspan="4" style="width: 54%;">Particulars</th>
												<th class="right" style="width: 12%;">Amount Claimed (&#8377;)</th>
												<th class="right" style="width: 12%;">Reimbursable under CHSS (&#8377;)</th>
												<th class="center" style="width: 22%;">Comments</th>
											</tr>
											
								<!-- ----------------------------- billheads ---------------------------------- -->			
											<%
												BigDecimal itemstotal=new BigDecimal("0.0");
												BigDecimal totalremamount=new BigDecimal("0.0"); 
											%>
											
								<!-- ----------------------------- package ---------------------------------- -->			
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
<!-- 														<tr>
																<th colspan="4">Package</th>
																<th ></th>
																<th></th>
																<th></th>
															</tr>			
 -->														<% } %>
														<tr>
															<td class="text-blue" colspan="4"><%=pkg[3] %>&nbsp; (<%=pkg[9] %>)</td>
															<td class="text-blue right" ><%=pkg[4] %></td>									
														<%if(show){ %>
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
														<%}else {%>
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
																<td style="width: 5%;"><%=count++%></td>
																<td  colspan="2"><%=pkgitem[3] %></td>
																<td class="text-blue right" style="width: 10%;" ><%=pkgitem[4] %></td>
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
											<%  
													i=1;
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
														<%if(show){ %>
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
																	<%if(pkgIten[7]== null || Long.parseLong(pkgIten[7].toString())==0){ %>	  
																		<span class="systemgen" ><%=pkgIten[6]%></span>		
																	<%}else if(pkgIten[8].toString().equalsIgnoreCase("K") || pkgIten[8].toString().equalsIgnoreCase("B")){ %>
																		<span class="processed" ><%=pkgIten[6]%></span>		
																	<%}else if(pkgIten[8].toString().equalsIgnoreCase("V")){ %>
																		<span class="verified" ><%=pkgIten[6]%></span>		
																	<%} %>
																<%} %>
															</td>
														<%}else {%>
															<td></td>
															<td></td>
														<%} %>	
														</tr>
													<% 
													i++;
													itemstotal =itemstotal.add (new BigDecimal(pkgIten[4].toString()));
													totalremamount =totalremamount.add (new BigDecimal(pkgIten[5].toString()));
													
													} } %>
											
																						
								<!-- ----------------------------- billheads  ---------------------------------- -->
								
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
																<th colspan="2">Doctor</th>
																<th style="width:10%;">Type</th>
																<th style="width:15%;" class="center">Date</th>
																<th></th>
																<th></th>
																<th></th>
															</tr>			
														<% } %>
														<tr>
															<td class="text-blue" colspan="2" ><%=consult[3] %>&nbsp;(<%=consult[11] %>)</td>
															<td class="text-blue" ><%=consult[2] %></td>
															<td class="center text-blue"><%=rdf.format(sdf.parse(consult[5].toString()))%></td>
															<td class="right text-blue"><%=consult[6] %></td>
															
														<%if(show){ %>
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
														<%}else {%>
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
												
													
													<%if(show){ %>
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
													<%}else {%>
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
												
													
													<%if(show){ %>
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
												
													
													<%if(show){ %>
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
													
																										
													<%if(show){ %>
														<td class="right ">	
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
													<%}else { %>
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
											<td class="right text-blue"><b><%=nfc.rupeeFormat(String.valueOf(itemstotal.subtract(discount).setScale(0, BigDecimal.ROUND_HALF_UP).longValue())) %></b></td>
															
											<td class="right text-green">
												<%if(show){ %>	 
												&#8377; <b><%=nfc.rupeeFormat(String.valueOf(totalremamount.setScale(0, BigDecimal.ROUND_HALF_UP))) %></b>
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
											<%if(show){ %>
													<td colspan="7" class="text-green">Admitted to &#8377;  
													<%= nfc.rupeeFormat(String.valueOf(totalremamount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())) %> (Rupees  <%=awc.convert1(totalremamount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue()) %> Only)</td>
											<%}else{ %>
												<td colspan="7">Admitted to &#8377;  ............................. (Rupees ...........................................................................................Only)</td>
											<%} %>
											
										</tr>
										
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
													<% break;} } %>
												</ul>
											</td>
											<td colspan="4" style="border-top: 0;border-left : 0;height: 80px;" >
												<ul style="float: right;list-style-type: none; margin:10px 5px -25px 0px; ">
													<%for(Object[] obj:ClaimapprovedPOVO){
														if(obj[1].toString().equalsIgnoreCase("VO")){%>
														<li style="text-transform: capitalize;"><%=obj[2] %>,</li>
														<li style="text-transform: capitalize;"><%=obj[4] %></li>
													<% break;} } %>
												</ul>					
											</td>
										</tr>
																				
										</tbody>						
									</table>
								
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
					</div>
					
						
						
					
				
				<%} %>
			
	

</body>
</html>