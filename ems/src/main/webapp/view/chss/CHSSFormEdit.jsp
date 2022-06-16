<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
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
 
</style>


</head>
<body>

	
<%
	Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
	List<Object[]> chssbillslist = (List<Object[]>)request.getAttribute("chssbillslist");
	List<Object[]> ConsultDataList = (List<Object[]>)request.getAttribute("ConsultDataList");
	List<Object[]> TestsDataList = (List<Object[]>)request.getAttribute("TestsDataList");
	List<Object[]> MedicineDataList = (List<Object[]>)request.getAttribute("MedicineDataList");
	List<Object[]> OtherDataList = (List<Object[]>)request.getAttribute("OtherDataList");
	List<Object[]> MiscDataList = (List<Object[]>)request.getAttribute("MiscDataList");
	List<Object[]> ClaimapprovedPOVO = (List<Object[]>)request.getAttribute("ClaimapprovedPOVO");
	
	
	Object[] employee = (Object[])request.getAttribute("employee");
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	AmountWordConveration awc = new AmountWordConveration();
	IndianRupeeFormat nfc=new IndianRupeeFormat();
	
	String isapproval = (String)request.getAttribute("isapproval");
	String showedit = (String)request.getAttribute("show-edit");
	String logintype = (String)request.getAttribute("logintype");
	
	int chssstatusid = Integer.parseInt(chssapplydata[9].toString());
	String LabLogo = (String)request.getAttribute("LabLogo");
	String onlyview=(String)request.getAttribute("onlyview");
	
	
	
	
	boolean showhistorybtn = showedit.equalsIgnoreCase("Y") && isapproval.equalsIgnoreCase("Y");
	
	List<Object[]> ClaimRemarksHistory = (List<Object[]>)request.getAttribute("ClaimRemarksHistory");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<%if(chssstatusid==3 || chssstatusid==1){ %>
						<!-- <li class="breadcrumb-item "><a href="CHSSAppliedList.htm">CHSS List</a></li> -->
						<%}else if(chssstatusid==2 || chssstatusid==4 || chssstatusid==5  ){ %>
						<li class="breadcrumb-item "><a href="CHSSApprovalsList.htm">CHSS Approval List</a></li>
						<%}else if(chssstatusid==6 ||   chssstatusid==9 ||   chssstatusid==10 ||   chssstatusid==13){ %>
						<li class="breadcrumb-item "><a href="ContingentApprovals.htm">CHSS Contingent List</a></li>
						<%}else if(chssstatusid==7 ||   chssstatusid==8 ||   chssstatusid==11 ||   chssstatusid==12){ %>
						<li class="breadcrumb-item "><a href="ContingentApprovals.htm">CHSS Contingent List</a></li>
						<%} %>
						<li class="breadcrumb-item active " aria-current="page">Claim</li>
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
				
			<div class="card" style="padding-top:0px;margin-top: -15px;">
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"  align="center">
							
								<table style="border: 0px; width: 100%">
									<tr>
										<td style="width: 20%; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
										<td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h3> MEDICAL CLAIM - OPD </h3> </td>
										<td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;">No.of ENCL : &nbsp;<span class="text-blue"><%=chssapplydata[8] %></span></span> </td>
									</tr>
								</table> 
							
								<table style="margin-top: 5px;">	
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
											<td class="text-blue" ><%=employee[9] %></td>

										</tr>
									</tbody>
								</table>
								<table>	
									<tbody>
										<tr>
											<th>Patient Name</th>						
											<th>Ailment</th>
											<th>Treatment Type</th>
											<th>Submitted On</th>
											
										</tr>
										<tr>
											<td class="text-blue" style="text-transform: capitalize;"><%=chssapplydata[12] %> &nbsp;(<%=chssapplydata[14] %>)</td>
											<td class="text-blue" ><%=chssapplydata[17] %></td>
											<td class="text-blue" ><%=chssapplydata[10] %></td>
											<td class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(chssapplydata[15].toString()) %></td>
										</tr>
										
									</tbody>
								</table>
								<table style="margin-bottom: 0px;">	
									<tbody>
										<tr>
											<td><b>Basic Pay : </b> &#8377;<span class="text-blue" ><%=employee[4] %></span>  </td>
											<td colspan="2"><b>Level in The Pay Matrix : </b> <span class="text-blue" ><%=employee[9] %></span></td>
											<td colspan="2"><b>Ph.No. : </b> <span class="text-blue" ><%=employee[8]%></span></td>
										</tr>
									</tbody>
								</table>
								<table style="margin-top: 0px;">	
									<tbody>
										<tr>
											<th class="center">SN</th>
											<th>Hospital / Medical / Diagnostics Centre Name</th>
											<th>Bill / Receipt No.</th>
											<th class="center">Date</th>
											<th style="text-align: right;">Amt (&#8377;)</th>
											<th style="text-align: right;">Discount (&#8377;)</th>
											<th style="text-align: right;">Total (&#8377;)</th>
										</tr>
										<% double billstotal=0 ,discount=0 /*,  GST=0 */;
											for(int i=0;i<chssbillslist.size();i++)
											{
												billstotal +=Double.parseDouble(chssbillslist.get(i)[7].toString());
												if(Double.parseDouble(chssbillslist.get(i)[8].toString())>0)
												{
													/* GST +=Double.parseDouble(chssbillslist.get(i)[5].toString()); */
													discount +=Double.parseDouble(chssbillslist.get(i)[6].toString());
												}
										%>
											<tr>
												<td class="center text-blue"><%=i+1 %></td>
												<td class="text-blue"><%=chssbillslist.get(i)[3] %></td>
												<td class="text-blue"><%=chssbillslist.get(i)[2] %></td>
												<td class="center text-blue" ><%=rdf.format(sdf.parse(chssbillslist.get(i)[4].toString())) %></td>
												<td class="text-blue" style="text-align: right;"><%=chssbillslist.get(i)[9] %></td>
												<td class="text-blue" style="text-align: right;"><%=chssbillslist.get(i)[6] %></td>
												<td class="text-blue" style="text-align: right;"><%=chssbillslist.get(i)[7] %></td>
											</tr>
										<%} %>
										<%if(chssbillslist.size()>0){ %>
											<%-- <tr>
												<td colspan="5"></td>
												<td style="text-align: right;"><b>Total GST (+)</b></td>
												<td class="text-blue"  style="text-align: right;"><%=GST %></td>
											</tr>
											<tr>
												<td colspan="5"></td>
												<td style="text-align: right;"><b>Total Discount (-)</b></td>
												<td class="text-blue"  style="text-align: right;"><%=discount %></td>
											</tr> --%>
											<tr>
												<td colspan="5"></td>
												<td style="text-align: right;"><b>Rounded Total </b></td>
												<td class="text-blue"  style="text-align: right;"><%=nfc.rupeeFormat(String.valueOf(Math.round(billstotal))) %></td>
											</tr>
										<%}else{ %>
											<tr>
												<td colspan="7" class="center" >Bills Not Added</td>
											</tr>
										<% } %>
									</tbody>
								</table>
								
								<div style="margin-left: 10px;">
									<p>											
										I do state that the member(s) of my family for whom reimbursement of medical expenses claimed in this bill are
										dependent upon me and eligible for reimbursement under CHSS Rules and declare in particular:
										<br>
										<%if(new ArrayList<String>( Arrays.asList("3", "4", "15","16")).contains(chssapplydata[13].toString().trim())){ %>
											&#8226; That my Parents / Parents-in-Law Shri / Smt. <span style="text-transform: capitalize;"><%=chssapplydata[12] %></span> are wholly dependent
											upon me and reside with me and that his / her total monthly incomes does not exceed Rs. 10,000/- per
											month.
											<br>
											
											&#8226; That no claim has been / will be made in respect of my parents as I opt to claim reimbursement in respect of
											my parents-in-law (applicable in case of female employees only).
											<br>
										<%} %>
										
											&#8226; That the patient Shri / Smt.<span style="text-transform: capitalize;"> <%=chssapplydata[12] %> </span> is not covered by the ESI Scheme / any
											other medical facility.
											<br>
											
										<%if(new ArrayList<String>( Arrays.asList("6","7")).contains(chssapplydata[13].toString().trim())){ %>
											&#8226; That the claim does not relate to my married daughter(s) / son(s) above the age of 25 years. In case of my
											son(s) and unmarried daughters(s), I declare that they are not gainfully employed and are dependent upon
											me.
											<br>
										<%} %>
										
										<%if(new ArrayList<String>( Arrays.asList("5","8")).contains(chssapplydata[13].toString().trim())){ %>
											&#8226; That my wife / husband is an employee of (Orgn) ................................ and we have furnished a joint
											declaration in writing that I shall prefer the claim in respect of our family except for
											......................................... which shall be claimed by my spouse.
											<br>
											&#8226; That my wife/ husband is employed in (Orgn) .........................................and is certified that medical benefit
											claimed in this bill has not been preferred / shall not be preferred from any other source.
											<br>
											&#8226; That my wife / husband Shri / Smt.<span style="text-transform: capitalize;"> <%=chssapplydata[12] %> </span> is an employee in STARC and that she / he is
											covered by ESI Scheme / ............................ Scheme and I certify that no claim for her / him for any medical
											benefit has been preferred / will be preferred, for such benefit received in respect of ineligible
											dependant(s) for whom the claim has been made against ESI Corporation / ............................... (Orgn).
											<br>
										<%} %>
										
										&#8226; That the bills attached herewith and the statements made in this claim are true and correct and I may be
										held liable, if anything is found to be incorrect later on.
										<br>
										&#8226; This bill is submitted on <b class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(chssapplydata[15].toString())%></b> which is within 3 months of treatment / hospitalization.
										<br>
										&#8226; I am not claiming the consultation fees within 7 days of preceding consultation for the same illness.
										<br>
										&#8226; It is certified that the reimbursement claimed in this form is genuine and not availed from any sources.
									</p>


							</div>
							<div class="break"></div>
							<div align="center" >
							
								<form action="ConsultRemAmountEdit.htm" method="post" autocomplete="off">
									<table>
										<tbody>
											
												<tr><td colspan="7" style="text-align: center;padding: 0;"><h4>MEDICAL REIMBURSEMENT DETAILS</h4></td></tr> 
												<!-- --------------- consultation -------------------- -->
												<tr>
													<th class="center" colspan="4" style="width: 60%;">Particulars</th>
													<th class="right" style="width: 7%;">Amount Claimed (&#8377;)</th>
													<th class="right" style="width: 5%;">Reimbursable under CHSS (&#8377;)</th>
													<th class="center" style="width: 25%;">Comments</th>
												</tr>
												<%double itemstotal=0, totalremamount=0;
												int i=1;
												for(Object[] consult :ConsultDataList)
												{ %>
													<%if(i==1){ %>
														<tr>
															<td colspan="4" style="text-align: center;">
																<b>Consultation Charges </b>
																<%if(showhistorybtn){ %>
																<button type="button" class="btn btn-sm btn-history"  onclick ="ShowHistory(1)" data-toggle="tooltip" data-placement="top" title="History">       
																	<i class="fa-solid fa-clock-rotate-left"></i>
																</button>
																<%} %>
															</td>
															<td class="right"> 
																<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
																<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
															</td>
															<td ></td>
															<td ></td>
														</tr>
														<tr>
															<th>Bill No</th>
															<th>Doctor</th>
															<th style="width:10%;">Type</th>
															<th style="width:15%;" class="center">Date</th>
															<th></th>
															<th></th>
															<th></th>
														</tr>			
													<% } %>
													<tr>
														<td  class="text-blue" ><%=consult[8] %><%-- &nbsp;(<%=rdf.format(sdf.parse(consult[9] .toString()))%>) --%></td>
														<td class="text-blue" ><%=consult[3] %></td>
														<td class="text-blue" ><%=consult[2] %></td>
														<td class="center text-blue"><%=rdf.format(sdf.parse(consult[5].toString()))%></td>
														<td class="right text-blue"><%=consult[6] %></td>
														
													<%if((showedit.equalsIgnoreCase("N") && isapproval.equalsIgnoreCase("Y")) || chssstatusid==14){ %>
														<td class="right text-green">	
															<%=consult[7]%>		
														</td>	
														<td class="text-green">
															<%if(consult[10]!=null){ %>
																<%=consult[10]%>
															<%} %>
														</td>
													<%}else if(showedit.equalsIgnoreCase("Y") && isapproval.equalsIgnoreCase("Y") ){ %>
														<td class="right">	
															<input type="number" class="numberonly" style="width: 100%;text-align: right; " name="consultremamount-<%=consult[0]%>" style="text-align: right;" value="<%=consult[7]%>">
														</td>
														<td >
															<input type="text" maxlength="255"  style="width: 85%;word-break: break-word;" placeholder="Comments" name="consultcomment-<%=consult[0]%>" style="text-align: right;" <%if(consult[10]!=null){ %> value="<%=consult[10] %>" <%}else{ %> value="" <%} %> >
															
															<button type="submit" class="btn btn-sm editbtn" formaction="ConsultRemAmountEdit.htm" name="consultationid" value="<%=consult[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update"> 
																<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
															</button>	
														</td>
													<% }else {%>
														<td class="">
														<td class="">		
													<%} %>	
														
																											
													</tr>					
												<%	i++;
													itemstotal += Double.parseDouble(consult[6].toString());
													totalremamount +=Double.parseDouble(consult[7].toString());
												} %>
										
											<% i=1;
											for(Object[] test :TestsDataList)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Tests / Procedures</b> 
															<%if(showhistorybtn){ %>
																<button type="button" class="btn btn-sm btn-history"  onclick ="ShowHistory(2)" data-toggle="tooltip" data-placement="top" title="History">
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
															 <%} %>
														</td>
														<td class="right">
															<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
															<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
														</td>
														<td ></td>
														<td ></td>
													</tr>
													<tr>
														<th>Bill No</th>
														<th colspan="3">Test</th>
														<th></th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td class="text-blue" ><%=test[8] %><%-- &nbsp;(<%=rdf.format(sdf.parse(test[9] .toString()))%>) --%></td>
													<td  class="text-blue" colspan="3"><%=test[6] %>(<%=test[10] %>)</td>
													<td class="right text-blue"><%=test[4] %></td>
												
													
													<%if((showedit.equalsIgnoreCase("N") && isapproval.equalsIgnoreCase("Y")) || chssstatusid==14){ %>
														<td class="right text-green">	
															<%=test[7]%>	
														</td>	
														<td class="text-green">
															<%if(test[11]!=null){ %>
																<%=test[11]%>
															<%} %>
														</td>
													<%}else if(showedit.equalsIgnoreCase("Y") && isapproval.equalsIgnoreCase("Y") ){ %>
														<td class="right">	
															<input type="number" class="numberonly" style="width: 100%;text-align: right;" name="testremamount-<%=test[0]%>" style="text-align: right;" value="<%=test[7]%>">
														</td>
														<td >
														<input type="text" maxlength="255" style="width: 85%;word-break: break-word;" placeholder="Comments" name="testcomment-<%=test[0]%>" style="text-align: right;" <%if(test[11]!=null){ %> value="<%=test[11] %>" <%}else{ %> value="" <%} %> >
															
														<button type="submit" class="btn btn-sm editbtn" formaction="TestRemAmountEdit.htm"  name="testid" value="<%=test[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>
														</td>
													<% }else {%>
														<td class="">
														<td class="">		
													<%} %>											
												</tr>					
											<%i++;
											itemstotal += Double.parseDouble(test[4].toString());
											totalremamount +=Double.parseDouble(test[7].toString());
											} %>
																	
											<% i=1;
											for(Object[] medicine : MedicineDataList)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Medicines</b>
															<%if(showhistorybtn){ %>
																<button type="button" class="btn btn-sm btn-history"  onclick ="ShowHistory(3)" data-toggle="tooltip" data-placement="top" title="History">
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
															 <%} %>
														</td>
														<td class="right">
															<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
															<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
														</td>
														<td ></td>
														<td ></td>
													</tr>
													<tr>
														<th>Bill No</th>
														<th>Medicine Name</th>
														<th style="width:10%;text-align: center;">Rx<br> Qty.</th>
														<th style="width:15%;text-align: center;">Pur<br> Qty.</th>
														<th></th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td  class="text-blue" ><%=medicine[7] %><%-- &nbsp;(<%=rdf.format(sdf.parse(medicine[8] .toString()))%>) --%></td>
													<td  class="text-blue" >	
														<%=medicine[2] %>
														<% if(showhistorybtn && chssapplydata[7].toString().equals("1")){ %>
														   <button type="button" class="btn btn-sm" style="float: right;background-color: #FFD24C" onclick="showsimilarmeds('<%=medicine[2]%>');" data-toggle="tooltip" data-placement="top" title="Similar Medicines List" ><i class="fa-solid fa-list-ul"></i></button> 
														<%} %>
													</td>
													<td  class="text-blue" style="text-align: center;"><%=medicine[5] %></td>
													<td class="text-blue"  style="text-align: center;" ><%=medicine[4] %></td> 
													<td  class="text-blue right"><%=medicine[3] %></td>
												
													
													<%if((showedit.equalsIgnoreCase("N") && isapproval.equalsIgnoreCase("Y")) || chssstatusid==14){ %>
														<td class="right text-green">	
															<%=medicine[6]%>
														</td>	
														<td class="text-green">
															<%if(medicine[9]!=null){ %>
																<%=medicine[9]%>
															<%} %>
															
														</td>
													<%}else if(showedit.equalsIgnoreCase("Y") && isapproval.equalsIgnoreCase("Y") ){ %>
														<td class="right">	
															<input type="number" class="numberonly" style="width: 100%;text-align: right;" name="medicineremamount-<%=medicine[0]%>" style="text-align: right;" value="<%=medicine[6]%>">
														</td>
														<td >
															<input type="text" maxlength="255" style="width: 85%;word-break: break-word;" placeholder="Comments" name="medscomment-<%=medicine[0]%>" style="text-align: right;" <%if(medicine[9]!=null){ %> value="<%=medicine[9] %>" <%}else{ %> value="" <%} %> >
																
															<button type="submit" class="btn btn-sm editbtn" formaction="MedRemAmountEdit.htm" name="medicineid" value="<%=medicine[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
																<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
															</button>
														</td>
													<%} else {%>
														<td class="">
														<td class="">
													<%} %>
														
												</tr>					
											<%i++;
											itemstotal += Double.parseDouble(medicine[3].toString());
											totalremamount +=Double.parseDouble(medicine[6].toString());
											}%>
											
											<% i=1;
											for(Object[] other : OtherDataList)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Others</b>
															<%if(showhistorybtn){ %>
																<button type="button" class="btn btn-sm btn-history"  onclick ="ShowHistory(4)" data-toggle="tooltip" data-placement="top" title="History">
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
															 <%} %>
														</td>
														<td class="right">
															<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
															<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
														</td>
														<td ></td>
														<td ></td>
													</tr>
													<tr>
														<th>Bill No</th>
														<th colspan="3">Other Items</th>
														<th></th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td  class="text-blue" ><%=other[6] %><%-- &nbsp;(<%=rdf.format(sdf.parse(other[7] .toString()))%>) --%></td>
													<td  class="text-blue" colspan="3"><%=other[4] %></td>
													<td class="right text-blue"><%=other[3] %></td>
													
													<%if((showedit.equalsIgnoreCase("N") && isapproval.equalsIgnoreCase("Y")) || chssstatusid==14){ %>
														<td class="right text-green">	
															<%=other[5]%>
														</td>	
														<td class="text-green">
															<%if(other[8]!=null){ %>
																<%=other[8]%>
															<%} %>
														</td>
													<%}else if(showedit.equalsIgnoreCase("Y") && isapproval.equalsIgnoreCase("Y") ){ %>
														<td class="right">	
															<input type="number" class="numberonly" style="width: 100%;text-align: right;" name="otherremamount-<%=other[0]%>" style="text-align: right;" value="<%=other[5]%>">
														</td>
														<td >
															<input type="text"maxlength="255" style="width: 85%;word-break: break-word;" placeholder="Comments" name="otherscomment-<%=other[0]%>" style="text-align: right;" <%if(other[8]!=null){ %> value="<%=other[8] %>" <%}else{ %> value="" <%} %>>
															<button type="submit" class="btn btn-sm editbtn" formaction="OtherRemAmountEdit.htm" name="otherid" value="<%=other[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>
														</td>
													<%} else {%>
														<td class="">
														<td class="">
													<%} %>
														
													
												</tr>					
											<%i++;
											itemstotal += Double.parseDouble(other[3].toString());
											totalremamount +=Double.parseDouble(other[5].toString());
											} %>
											<% i=1;
											for(Object[] misc : MiscDataList)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Miscellaneous</b>
															<%if(showhistorybtn){ %>
																<button type="button" class="btn btn-sm btn-history"  onclick ="ShowHistory(5)" data-toggle="tooltip" data-placement="top" title="History">
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
															 <%} %>
														</td>
														<td class="right">
															<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
															<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
														</td>
														<td ></td>
														<td ></td>
													</tr>
													<tr>
														<th>Bill No</th>
														<th colspan="2">Item</th>
														<th style="text-align: center;">Qty</th>
														<th></th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td class="text-blue" ><%=misc[5] %><%-- &nbsp;(<%=rdf.format(sdf.parse(misc[6] .toString()))%>) --%></td>
													<td  class="text-blue" colspan="2"><%=misc[2] %></td>
													<td class="text-blue"  style="text-align: center;"><%if(misc[8]!=null){ %><%=misc[8] %><%} %></td>
													<td class="right text-blue"><%=misc[3] %></td>
													
																										
													<% if((showedit.equalsIgnoreCase("N") && isapproval.equalsIgnoreCase("Y")) || chssstatusid==14){ %>
														<td class="right text-green">	
															<%=misc[4]%>
														</td>	
														<td class="text-green">
															<%if(misc[7]!=null){ %>
																<%=misc[7]%>
															<%} %>
														</td>
													<%}else if(showedit.equalsIgnoreCase("Y") && isapproval.equalsIgnoreCase("Y") ){ %>
														<td class="right">	
															<input type="number" class="numberonly" style="width: 100%;text-align: right;" name="miscremamount-<%=misc[0]%>" value="<%=misc[4]%>">
														</td>
														<td >
															<input type="text" maxlength="255" style="width: 85%;word-break: break-word;word-break: break-word;" placeholder="Comments" name="miscomment-<%=misc[0]%>" style="text-align: right;" <%if(misc[7]!=null){ %> value="<%=misc[7] %>" <%}else{ %> value="" <%} %> >
															<button type="submit" class="btn btn-sm editbtn" formaction="MiscRemAmountEdit.htm" name="miscid" value="<%=misc[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
																<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
															</button>
														</td>
													<%} else { %>
														<td class="">
														<td class="">
													<%} %>
													
												</tr>					
											<%i++;
											itemstotal += Double.parseDouble(misc[3].toString());
											totalremamount +=Double.parseDouble(misc[4].toString());
											}%>
										
										
										<tr>
											<td colspan="4" class="right"><b>Total</b></td>
											<td class="right text-blue"><b> <%=itemstotal %></b></td>
											<td class="right text-green">
												<%if(isapproval.equalsIgnoreCase("Y") || chssstatusid==14){ %>	 
												&#8377; <b><%=nfc.rupeeFormat(String.valueOf(Math.round(totalremamount))) %></b>
												<%} %>
											</td>
											<td ></td>
										</tr>
															
									<%-- 	<tr>
											<td colspan="4" class="right"><b>Total GST (+)</b></td>
											<td class="right text-blue"><b><%=GST %></b></td>
											<td class="right text-green"></td>
											<td ></td>
										</tr>  --%>
										<tr>
											<td colspan="4" class="right"><b>Total Discount (-)</b></td>
											<td class="right text-blue"><b><%=discount %></b></td>
											<td class="right text-green"></td>
											<td ></td>
										</tr>
															
										<tr>
											<td colspan="4" class="right"><b>Rounded Total</b></td>
											<td class="right text-blue"><b><%=nfc.rupeeFormat(String.valueOf(Math.round(itemstotal -discount))) %></b></td>
															
											<td class="right text-green">
												<%if(isapproval.equalsIgnoreCase("Y") || chssstatusid==14){ %>	 
												&#8377; <b><%=nfc.rupeeFormat(String.valueOf(Math.round(totalremamount))) %></b>
												<%} %>
											</td>
											<td ></td>
										</tr>
																				
										<tr>
											<td colspan="7" class="text-blue">(In words Rupees <%=awc.convert1(Math.round(itemstotal)) %> Only)</td> 
										</tr>
										
										<tr>
											<td colspan="7" class="center"><span style="text-decoration: underline;"><b>FOR OFFICE USE ONLY</b></span></td>
										</tr>
										
										<tr>
											<td colspan="7" class="text-green">Admitted to Rs.
												<%if(isapproval.equalsIgnoreCase("Y") || chssstatusid==14){ %>
												<%= nfc.rupeeFormat(String.valueOf(Math.round(totalremamount))) %> (Rupees  <%=awc.convert1(Math.round(totalremamount)) %> Only)
												<%}else{ %>
													&#8377;  ............................. (Rupees ...........................................................................................Only)
												<%} %>
											</td> 
										</tr>
										
										<tr>
											<td colspan="7" style="text-align:center; ;border-bottom : 0;text-decoration: underline;"><b>Finance and Accounts Department</b></td>
										<tr>
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
					
				<%if(onlyview==null || !onlyview.equalsIgnoreCase("Y")){ %>	
					<form action="CHSSUserForward.htm" method="post" id="fwdform">
						<div class="row">
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
					
						
							
							<div class="col-md-6" align="center" style="margin-top: 5px;">
							
							
							
							<%if(chssstatusid!=8 ){ %>
								<div class="col-md-12" align="left" style="margin-bottom: 5px;">
									Remarks : <br>
									<textarea class="w-100 form-control" rows="4" cols="100" id="remarks" name="remarks" maxlength="500"></textarea>
								</div>
							<%} %>
							
							<%if(chssstatusid==2 || chssstatusid==4 ||  chssstatusid==5 ){ %>
								
								<button type="submit" class="btn btn-sm submit-btn" name="claimaction" value="F" onclick="return remarkRequired('F'); " formnovalidate="formnovalidate">Verify</button>
								<button type="submit" class="btn btn-sm delete-btn" name="claimaction" value="R" onclick="return remarkRequired('R'); " formnovalidate="formnovalidate">Return</button>
							
							<%}else if(chssstatusid==1 || chssstatusid==3 ||  chssstatusid==7){ %>
							
								<button type="button" class="btn btn-sm submit-btn" name="claimaction" value="F"   <%if(chssstatusid==3){ %> onclick="remarkscheck();" <%}else{%>data-toggle="modal" data-target=".my-encl-modal" <%} %> >
									<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for processing	
								</button>
								<button type="submit" class="btn btn-sm edit-btn" name="action" value="edit"  formaction="CHSSConsultMainData.htm" formnovalidate="formnovalidate" data-toggle="tooltip" data-placement="top" title="Edit">
									Edit
								</button>
								<input type="hidden" name="claimaction" value="F" >	
									
							<%}else if(chssstatusid==6 ||chssstatusid==9 || chssstatusid==11 ||  chssstatusid==13){ %>
							
								<button type="submit" class="btn btn-sm delete-btn"  name="claimaction" value="R" onclick="return remarkRequired('R'); " >Return</button>
								
							<%} %>
								
							</div>
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
				<%} %>

				</div>
			</div>		
			
		</div>
	
	 </div>
	 
	 
 <div class="modal fade my-history-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" style="min-width: 85% !important;min-height: 80% !important; ">
		<div class="modal-content" >
			<div class="modal-header" style="background: #F5C6A5 ">
				<div> <h4 id="m-header"></h4> </div>
			    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			    	<i class="fa-solid fa-xmark" aria-hidden="true" ></i>
			    </button>
		    </div>
			<div class="modal-body" style="min-height: 30rem;">
				<div class="row">
					<div class="col-md-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-condensed  info shadow-nohover" id="modal-history-table" style="max-width: 99% !important;">
								<thead><tr><td></td></tr></thead>
								<tbody><tr><td></td></tr></tbody>								
							</table>
						</div>
					</div>
				</div>
			</div>
		      
		</div>
	</div>
</div>
	 
	
<%if(isapproval!=null && isapproval.equalsIgnoreCase("Y") && logintype.equals("K") && chssapplydata[20].toString().equals("0") ){ %>

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
$("#modal-history-table").DataTable({
    "lengthMenu": [10, 25, 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});
var $chssapplyid = <%=chssapplydata[0]%>;
function ShowHistory(itemid)
{
	
	if(itemid==1)
	{
		$('#m-header').html('Consultations History');
		$.ajax({

			type : "GET",
			url : "ConsultationHistoryAjax.htm",
			data : {
					
				chssapplyid : $chssapplyid,
				
			},
			datatype : 'json',
			success : function(result) {
				var result = JSON.parse(result);
				var $consulthistorylist = Object.keys(result).map(function(e){
					return result[e]
				})	
				var $TblStr = '';

				$TblStr+=	'<thead><tr>';
				$TblStr+=		'<th>Claim No</th>';
				$TblStr+=		'<th>Type</th>';
				$TblStr+=		'<th>Doctor Name</th>';
				/* $TblStr+=		'<th>Qualification</th>'; */
				$TblStr+=		'<th style="text-align:right;width: 10%; ">Claimed (&#8377;)</th>';
				$TblStr+=		'<th style="text-align:right;width: 10%; ">Admitted (&#8377;)</th>';
				$TblStr+=	'</tr></thead>';
				$TblStr+=	'<tbody>';
				
				for(var cons=0;cons< $consulthistorylist.length;cons++)
				{
					$TblStr+=	'<tr>';
					$TblStr+=		'<td style="width:15%;">'+$consulthistorylist[cons][1]+'</td>';
					$TblStr+=		'<td  style="width:10%;" >'+$consulthistorylist[cons][3]+'</td>';
					$TblStr+=		'<td>'+$consulthistorylist[cons][4]+'</td>';
					/* $TblStr+=		'<td>'+$consulthistorylist[i][7]+'</td>'; */
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$consulthistorylist[cons][7]+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$consulthistorylist[cons][8]+'</td>';
					$TblStr+=	'</tr>';
				}
				
				
				$TblStr+=	'</tbody>';
				$("#modal-history-table").DataTable().clear().destroy();
				$('#modal-history-table').html($TblStr);
				$("#modal-history-table").DataTable({
			        "lengthMenu": [10, 25, 50, 75, 100],
			        "pagingType": "simple",
			        "language": {
					      "emptyTable": "No Record Found"
					    }

			    });
				$('.my-history-modal').modal('toggle');
			}
		});
		
		
	}
	else if(itemid==2)
	{
		$('#m-header').html('Tests History');	
		
		$.ajax({

			type : "GET",
			url : "TestsHistoryAjax.htm",
			data : {
					
				chssapplyid : $chssapplyid,
				
			},
			datatype : 'json',
			success : function(result) {
				var result = JSON.parse(result);
				var $testshistorylist = Object.keys(result).map(function(e){
					return result[e]
				})	
				var $TblStr = '';

				$TblStr+=	'<thead><tr>';
				$TblStr+=		'<th>Claim No</th>';
				$TblStr+=		'<th>Test</th>';
				$TblStr+=		'<th style="text-align:right;">Claimed (&#8377;)</th>';
				$TblStr+=		'<th style="text-align:right;">Admitted (&#8377;)</th>';
				$TblStr+=	'</tr></thead>';
				$TblStr+=	'<tbody>';
				
				for(var t=0;t< $testshistorylist.length;t++)
				{
					$TblStr+=	'<tr>';
					$TblStr+=		'<td style="width:15%;">'+$testshistorylist[t][1]+'</td>';
					$TblStr+=		'<td>'+$testshistorylist[t][4]+'  ('+$testshistorylist[t][5]+')'+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$testshistorylist[t][6]+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$testshistorylist[t][7]+'</td>';
					$TblStr+=	'</tr>';
				}
				
				
				$TblStr+=	'</tbody>';
				$("#modal-history-table").DataTable().destroy();
				$('#modal-history-table').html($TblStr);
				$("#modal-history-table").DataTable({
			        "lengthMenu": [10, 25, 50, 75, 100],
			        "pagingType": "simple",
			        "language": {
					      "emptyTable": "No Record Found"
					    }

			    });
				$('.my-history-modal').modal('toggle');
			}
		});
		
		
		
		
	}else if(itemid==3){
		$('#m-header').html('Medicines History');		
		
		$.ajax({

			type : "GET",
			url : "MedicinesHistoryAjax.htm",
			data : {
					
				chssapplyid : $chssapplyid,
				
			},
			datatype : 'json',
			success : function(result) {
				var result = JSON.parse(result);
				var $medshistorylist = Object.keys(result).map(function(e){
					return result[e]
				})	
				var $TblStr = '';

				$TblStr+=	'<thead><tr>';
				$TblStr+=		'<th>Claim No</th>';
				$TblStr+=		'<th>Bill Date</th>';
				$TblStr+=		'<th>Medicine Name</th>';
				$TblStr+=		'<th>Rx Qty.</th>';
				$TblStr+=		'<th>Pur Qty.</th>';
				$TblStr+=		'<th style="text-align:right;">Claimed (&#8377;)</th>';
				$TblStr+=		'<th style="text-align:right;">Admitted (&#8377;)</th>';
				$TblStr+=	'</tr></thead>';
				$TblStr+=	'<tbody>';
				
				
				for(var m=0;m< $medshistorylist.length;m++)
				{
					$TblStr+=	'<tr>';
					$TblStr+=		'<td style="width:15%;">'+$medshistorylist[m][1]+'</td>';
					
					let now = new Date($medshistorylist[m][8]);
					var dateString = moment(now).format('DD-MM-YYYY');
					
					$TblStr+=		'<td style="width:15%;">'+dateString+'</td>';
					$TblStr+=		'<td>'+$medshistorylist[m][3]+'</td>';
					$TblStr+=		'<td>'+$medshistorylist[m][4]+'</td>';
					$TblStr+=		'<td>'+$medshistorylist[m][5]+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$medshistorylist[m][6]+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$medshistorylist[m][7]+'</td>';
					$TblStr+=	'</tr>';
				}
				
				
				$TblStr+=	'</tbody>';
				$("#modal-history-table").DataTable().destroy();
				$('#modal-history-table').html($TblStr);
				$("#modal-history-table").DataTable({
			        "lengthMenu": [10, 25, 50, 75, 100],
			        "pagingType": "simple",
			        "language": {
					      "emptyTable": "No Record Found"
					    }

			    });
				$('.my-history-modal').modal('toggle');
			}
		});
		
		
		
		
	}else if(itemid==4)
	{
		
		$('#m-header').html('Other Items History');			
		
		$.ajax({

			type : "GET",
			url : "OthersHistoryAjax.htm",
			data : {
					
				chssapplyid : $chssapplyid,
				
			},
			datatype : 'json',
			success : function(result) {
				var result = JSON.parse(result);
				var $othershistorylist = Object.keys(result).map(function(e){
					return result[e]
				})	
				var $TblStr = '';

				$TblStr+=	'<thead><tr>';
				$TblStr+=		'<th>Claim No</th>';
				$TblStr+=		'<th>Other Item Name</th>';
				$TblStr+=		'<th style="text-align:right;">Claimed (&#8377;)</th>';
				$TblStr+=		'<th style="text-align:right;">Admitted (&#8377;)</th>';
				$TblStr+=	'</tr></thead>';
				$TblStr+=	'<tbody>';
				
				for(var m=0;m< $othershistorylist.length;m++)
				{
					$TblStr+=	'<tr>';
					$TblStr+=		'<td style="width:15%;">'+$othershistorylist[m][1]+'</td>';
					$TblStr+=		'<td>'+$othershistorylist[m][4]+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$othershistorylist[m][5]+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$othershistorylist[m][6]+'</td>';
					$TblStr+=	'</tr>';
				}
				
				
				$TblStr+=	'</tbody>';
				$("#modal-history-table").DataTable().destroy();
				$('#modal-history-table').html($TblStr);
				$("#modal-history-table").DataTable({
			        "lengthMenu": [10, 25, 50, 75, 100],
			        "pagingType": "simple",
			        "language": {
					      "emptyTable": "No Record Found"
					    }

			    });
				$('.my-history-modal').modal('toggle');
			}
		});
		
		
	}else if(itemid==5)
	{
		
		$('#m-header').html('Miscellaneous Items History');					
		
		$.ajax({

			type : "GET",
			url : "MiscItemsHistoryAjax.htm",
			data : {
					
				chssapplyid : $chssapplyid,
				
			},
			datatype : 'json',
			success : function(result) {
				var result = JSON.parse(result);
				var $mischistorylist = Object.keys(result).map(function(e){
					return result[e]
				})	
				var $TblStr = '';

				$TblStr+=	'<thead><tr>';
				$TblStr+=		'<th>Claim No</th>';
				$TblStr+=		'<th>Miscellaneous Item</th>';
				$TblStr+=		'<th style="text-align:center";>Qty</th>';
				$TblStr+=		'<th style="text-align:right;width: 15%;">Claimed (&#8377;)</th>';
				$TblStr+=		'<th style="text-align:right;width: 15%;">Admitted (&#8377;)</th>';
				$TblStr+=	'</tr></thead>';
				$TblStr+=	'<tbody>';
				
				for(var m=0;m< $mischistorylist.length;m++)
				{
					$TblStr+=	'<tr>';
					$TblStr+=		'<td style="width:15%;">'+$mischistorylist[m][1]+'</td>';
					$TblStr+=		'<td style="text-align:center"; >'+$mischistorylist[m][6]+'</td>';
					$TblStr+=		'<td>'+$mischistorylist[m][3]+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$mischistorylist[m][4]+'</td>';
					$TblStr+=		'<td style="text-align:right;width: 15%;">'+$mischistorylist[m][5]+'</td>';
					$TblStr+=	'</tr>';
				}
				
				
				$TblStr+=	'</tbody>';
				$("#modal-history-table").DataTable().destroy();
				$('#modal-history-table').html($TblStr);
				$("#modal-history-table").DataTable({
			        "lengthMenu": [10, 25, 50, 75, 100],
			        "pagingType": "simple",
			        "language": {
					      "emptyTable": "No Record Found"
					    }

			    });
				$('.my-history-modal').modal('toggle');
			}
		});
		
		
	}
		
	
}


</script>
	 
	 
	 
<script type="text/javascript">

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
					
			 if(Number(result[2])===1)
			{
				alert('Sum of Items Cost in Bill \''+result[3]+'\' does not Tally with Amount Paid.');
			}
			 else if(Number(result[1])===1)
			{
				alert('Please Enter Atleast One Item in All the bills');
			}
			else if(Number(result[0])===1)
			{
				alert('Total claim amount should not be zero !');
			}
			else
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
			}
		
		}
	});
	
}

function auto_grow(element) {
    element.style.height = "5px";
    element.style.height = (element.scrollHeight)+"px";
}



function showsimilarmeds($medname)
{
	$('#m-header').html('Medicine List');
	$.ajax({

		type : "GET",
		url : "MedsNameListAjax.htm",
		data : {
				
			medname : $medname,
		},
		datatype : 'json',
		success : function(result) {
			var result = JSON.parse(result);
			var $medslist = Object.keys(result).map(function(e){
				return result[e]
			})	
			var $TblStr = '';

			$TblStr+=	'<thead><tr>';
			$TblStr+=		'<th>SN</th>';
			$TblStr+=		'<th>Medicine No</th>';
			$TblStr+=		'<th>Medicine Name</th>';
			$TblStr+=		'<th>Admissible</th>';
			$TblStr+=	'</tr></thead>';
			$TblStr+=	'<tbody>';
			
			for(var med=0;med< $medslist.length;med++)
			{
				$TblStr+=	'<tr>';
				$TblStr+=		'<td style="width:15%;">'+(med+1)+'</td>';
				$TblStr+=		'<td  style="width:10%;" >'+$medslist[med][1]+'</td>';
				$TblStr+=		'<td>'+$medslist[med][4]+'</td>';
				if($medslist[med][5]==='N'){
					$TblStr+=		'<td style="color:red" ><b>InAdmissible</b></td>';
				}else{
					$TblStr+=		'<td style="color:green" ><b>Admissible</b></td>';
				}	
				$TblStr+=	'</tr>';
			}
			
			
			$TblStr+=	'</tbody>';
			$("#modal-history-table").DataTable().clear().destroy();
			$('#modal-history-table').html($TblStr);
			$("#modal-history-table").DataTable({
		        "lengthMenu": [10, 25, 50, 75, 100],
		        "pagingType": "simple",
		        "language": {
				      "emptyTable": "No Record Found"
				    }

		    });
			$('.my-history-modal').modal('toggle');
		}
	});
	
	
	
	
}



</script>

</body>

</html>