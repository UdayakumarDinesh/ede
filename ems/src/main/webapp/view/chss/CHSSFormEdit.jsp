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

	Employee employee = (Employee)request.getAttribute("employee");
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	AmountWordConveration awc = new AmountWordConveration();
	IndianRupeeFormat nfc=new IndianRupeeFormat();
	
	String isapproval = (String)request.getAttribute("isapproval");
	
	int chssstatusid = Integer.parseInt(chssapplydata[9].toString());
%>
 
 <div class="col page card">
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
						<li class="breadcrumb-item "><a href="ContingentApprovals.htm">CHSS Approval List</a></li>
						<%}else if(chssstatusid==7 ||   chssstatusid==8 ||   chssstatusid==11 ||   chssstatusid==12){ %>
						<li class="breadcrumb-item "><a href="CHSSBatchList.htm">CHSS Contingent List</a></li>
						<%} %>
						<li class="breadcrumb-item active " aria-current="page">Claim</li>
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
					<div class="row">
						<%if(chssstatusid>=2 && chssapplydata[19]!=null && !chssapplydata[19].toString().trim().equalsIgnoreCase("")){ %>
						<div class="col-md-12">
							<span style="font-weight: 600;"> Remark : </span>
							<%if(chssstatusid == 3 || chssstatusid == 5 || chssstatusid == 7  ){ %>
								<span style="font-weight: 600; color: #D82148;"> <%=chssapplydata[19] %> </span>					
							<%}else{ %>    
								<span style="font-weight: 600; color: #035397;"> <%=chssapplydata[19] %> </span>			
							<%} %>
						</div>
						<%} %>
					</div>
				
					<div align="center">
						<div align="center">
							<div>
								<div>
									<h3 style="font-weight: 600;">MEDICAL CLAIM</h3>
									<div align="left"> <span>Claim No : <b><%=chssapplydata[16] %> </b></span><span style="float: right;">No.of ENCL : &nbsp;<%=chssapplydata[8] %></span></div>
								</div>
							
								<table>	
									<tbody>
										<tr>
											<th>Name</th>
											<th>Emp No</th>
											<th>Grade</th>
										</tr>
										<tr>
											<td><%=employee.getEmpName() %></td>
											<td><%=employee.getEmpNo() %></td>
											<td><%=employee.getPayLevelId() %></td>
										</tr>
									</tbody>
								</table>
								<table>	
									<tbody>
										<tr>
											<th class="center">SN</th>
											<th>Patient Name</th>
											<th>Relation</th>
										</tr>
										<tr>
											<td class="center">1</td>
											<td><%=chssapplydata[12] %></td>
											<td><%=chssapplydata[14] %></td>
										</tr>
									</tbody>
								</table>
								<table style="margin-bottom: 0px;">	
									<tbody>
										<tr>
											<td><b>Basic Pay : </b> &#8377; <%=employee.getBasicPay() %> </td>
											<td colspan="2"><b>Level in The Pay Matrix : </b> <%=employee.getPayLevelId() %></td>
											<td colspan="2"><b>Ph.No. : </b> <%=employee.getPhoneNo()%></td>
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
											<th style="text-align: right;">Amount &nbsp;(&#8377;)</th>
										</tr>
										<% long billstotal=0;
											for(int i=0;i<chssbillslist.size();i++)
											{
												billstotal +=Long.parseLong(chssbillslist.get(i)[5].toString());
												%>
											<tr>
												<td class="center"><%=i+1 %></td>
												<td><%=chssbillslist.get(i)[3] %></td>
												<td><%=chssbillslist.get(i)[2] %></td>
												<td class="center" ><%=rdf.format(sdf.parse(chssbillslist.get(i)[4].toString())) %></td>
												<td style="text-align: right;"><%=chssbillslist.get(i)[5] %></td>
											</tr>
										<%} %>
										<%if(chssbillslist.size()>0){ %>
											<tr>
												<td colspan="3"></td>
												<td style="text-align: right;"><b>Total </b></td>
												<td style="text-align: right;"><%=billstotal %></td>
											</tr>
										<%}else{ %>
											<tr>
												<td colspan="5" class="center" >Nil</td>
											</tr>
										<%} %>
									</tbody>
								</table>
								
								<div style="margin-left: 10px;">
									<p>
											
										I do state that the member(s) of my family for whom reimbursement of medical expenses claimed in this bill are
										dependent upon me and eligible for reimbursement under CHSS Rules and declare in particular:
										<br>
										<%if(new ArrayList<String>( Arrays.asList("3", "4", "15","16")).contains(chssapplydata[13].toString().trim())){ %>
											&#8226; That my Parents / Parents-in-Law Shri / Smt. <%=chssapplydata[12] %> are wholly dependent
											upon me and reside with me and that his / her total monthly incomes does not exceed Rs. 10,000/- per
											month.
											<br>
											
											&#8226; That no claim has been / will be made in respect of my parents as I opt to claim reimbursement in respect of
											my parents-in-law (applicable in case of female employees only).
											<br>
										<%} %>
										
											&#8226; That the patient Shri / Smt. <%=chssapplydata[12] %> is not covered by the ESI Scheme / any
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
											&#8226; That my wife / husband Shri / Smt. <%=chssapplydata[12] %> is an employee in STARC and that she / he is
											covered by ESI Scheme / ............................ Scheme and I certify that no claim for her / him for any medical
											benefit has been preferred / will be preferred, for such benefit received in respect of ineligible
											dependant(s) for whom the claim has been made against ESI Corporation / ............................... (Orgn).
											<br>
										<%} %>
										
										&#8226; That the bills attached herewith and the statements made in this claim are true and correct and I may be
										held liable, if anything is found to be incorrect later on.
										<br>
										&#8226; This bill is submitted on ................................. which is within 3 months of treatment / hospitalization.
										<br>
										&#8226; I am not claiming the consultation fees within 7 days of preceding consultation for the same illness.
										<br>
										&#8226; It is certified that the reimbursement claimed in this form is genuine and not availed from any sources.
										(Strike out whichever is not applicable)
								
									</p>
								
								</div>
								
							</div>
							<div class="break"></div>
							<div align="center" >
							
								
									<table>
										<tbody>
											<form action="ConsultRemAmountEdit.htm" method="post">
												<tr><td colspan="6" style="text-align: center;padding: 0;"><h4>MEDICAL REIMBURSEMENT DETAILS</h4></td></tr> 
												<!-- --------------- consultation -------------------- -->
												<tr>
													<th class="center" colspan="4" style="width: 60%;">Particulars</th>
													<th class="right" style="width: 15%;">Amount Claimed (&#8377;)</th>
													<th class="right" style="width: 25%;">Reimbursable under CHSS (&#8377;)</th>
												</tr>
												<%long itemstotal=0, totalremamount=0; %>
												<% int i=1;
												for(Object[] consult :ConsultDataList)
												{%>
													<%if(i==1){ %>
														<tr>
															<td colspan="4" style="text-align: center;">
																<b>Consultation charges </b>
																<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
																<button type="button" class="btn btn-sm btn-history" style="float: right;" onclick ="ShowHistory(1)" data-toggle="tooltip" data-placement="top" title="History">       
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
																 <%} %>
															</td>
															<td class="right"> 
																<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
																<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
															</td>
															<td class="right"></td>
														</tr>
														<tr>
															<th>Bill No</th>
															<th>Doctor</th>
															<th style="width:10%;">Type</th>
															<th style="width:15%;" class="center">Date</th>
															<th></th>
															<th></th>
														</tr>			
													<%} %>
													<tr>
														<td><%=consult[8] %></td>
														<td><%=consult[3] %></td>
														<td><%=consult[2] %></td>
														<td class="center"><%=rdf.format(sdf.parse(consult[5].toString()))%></td>
														<td class="right"><%=consult[6] %></td>
														<td class="right">
														<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
															<input type="text" class="w-100" maxlength="255" style="padding: 3px;" placeholder="Comments" name="consultcomment-<%=consult[0]%>" style="direction: rtl;" <%if(consult[10]!=null){ %> value="<%=consult[10] %>" <%}else{ %> value="" <%} %> >	 
															<input type="number" class="numberonly" style="width: 85%;direction: rtl;margin-top: 3px;" name="consultremamount-<%=consult[0]%>" style="direction: rtl;" value="<%=consult[7]%>">
															<button type="submit" class="btn btn-sm editbtn" name="consultationid" value="<%=consult[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update"> 
																<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
															</button>				
														<%}else if((chssstatusid>=6)){ %>
															<%=consult[7]%>			
														<%} %>
														</td>
																											
													</tr>					
												<%	i++;
													itemstotal += Integer.parseInt(consult[6].toString());
													totalremamount +=Integer.parseInt(consult[7].toString());
												} %>
													
												
										</form>
										<form action="TestRemAmountEdit.htm" method="post">
											<% i=1;
											for(Object[] test :TestsDataList)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Pathological/Investigations Test</b> 
															<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
																<button type="button" class="btn btn-sm btn-history" style="float: right;" onclick ="ShowHistory(2)" data-toggle="tooltip" data-placement="top" title="History">
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
															 <%} %>
														</td>
														<td class="right">
															<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
															<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
														</td>
														<td class="right"></td>
													</tr>
													<tr>
														<th>Bill No</th>
														<th colspan="3">Test</th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td><%=test[8] %></td>
													<td colspan="3"><%=test[6] %>(<%=test[10] %>)</td>
													<td class="right"><%=test[4] %></td>
													<td class="right">
													<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>	 
														<input type="text" class="w-100" maxlength="255" style="padding: 3px;" placeholder="Comments" name="testcomment-<%=test[0]%>" style="direction: rtl;" <%if(test[11]!=null){ %> value="<%=test[11] %>" <%}else{ %> value="" <%} %> >
														<input type="number" class="numberonly" style="width: 85%;direction: rtl;margin-top: 3px;" name="testremamount-<%=test[0]%>" style="direction: rtl;" value="<%=test[7]%>">
														<button type="submit" class="btn btn-sm editbtn"  name="testid" value="<%=test[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>												
													
													<%}else if((chssstatusid>=6)){ %>
															<%=test[7]%>			
													<%} %>
													</td>
												</tr>					
											<%i++;
											itemstotal += Integer.parseInt(test[4].toString());
											totalremamount +=Integer.parseInt(test[7].toString());
											} %>
												
										</form>
										<form action="MedRemAmountEdit.htm" method="post">
											
											<% i=1;
											for(Object[] medicine : MedicineDataList)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Medicines</b>
															<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
																<button type="button" class="btn btn-sm btn-history" style="float: right;" onclick ="ShowHistory(3)" data-toggle="tooltip" data-placement="top" title="History">
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
															 <%} %>
														</td>
														<td class="right">
															<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
															<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
														</td>
														<td class="right"></td>
													</tr>
													<tr>
														<th>Bill No</th>
														<th>Medicine Name</th>
														<th style="width:10%;">Rx Qty.</th>
														<th style="width:15%;">Pur Qty.</th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td><%=medicine[7] %></td>
													<td><%=medicine[2] %></td>
													<td><%=medicine[5] %></td>
													<td><%=medicine[4] %></td> 
													<td class="right"><%=medicine[3] %></td>
													<td class="right">
													<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9 ){ %>	 
														<input type="text" class="w-100" maxlength="255" style="padding: 3px;" placeholder="Comments" name="medscomment-<%=medicine[0]%>" style="direction: rtl;" <%if(medicine[9]!=null){ %> value="<%=medicine[9] %>" <%}else{ %> value="" <%} %> >
														<input type="number" class="numberonly" style="width: 85%;direction: rtl;margin-top: 3px;" name="medicineremamount-<%=medicine[0]%>" style="direction: rtl;" value="<%=medicine[6]%>">
														<button type="submit" class="btn btn-sm editbtn"  name="medicineid" value="<%=medicine[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>
													
													<%}else if((chssstatusid>=6)){ %>
															<%=medicine[6]%>			
													<%} %>
													</td>
												</tr>					
											<%i++;
											itemstotal += Integer.parseInt(medicine[3].toString());
											totalremamount +=Integer.parseInt(medicine[6].toString());
											}%>
											
									</form>
									<form action="OtherRemAmountEdit.htm" method="post">
											
											<% i=1;
											for(Object[] other : OtherDataList)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Others</b>
															<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
																<button type="button" class="btn btn-sm btn-history" style="float: right;" onclick ="ShowHistory(4)" data-toggle="tooltip" data-placement="top" title="History">
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
															 <%} %>
														</td>
														<td class="right">
															<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
															<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
														</td>
														<td class="right"></td>
													</tr>
													<tr>
														<th>Bill No</th>
														<th colspan="3">Other Items</th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td><%=other[6] %></td>
													<td colspan="3"><%=other[4] %></td>
													<td class="right"><%=other[3] %></td>
													<td class="right">
													<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>	 
														<input type="text" class="w-100" maxlength="255" style="padding: 3px;" placeholder="Comments" name="otherscomment-<%=other[0]%>" style="direction: rtl;" <%if(other[8]!=null){ %> value="<%=other[8] %>" <%}else{ %> value="" <%} %>>
														<input type="number" class="numberonly" style="width: 85%;direction: rtl;margin-top: 3px;" name="otherremamount-<%=other[0]%>" style="direction: rtl;" value="<%=other[5]%>">
														<button type="submit" class="btn btn-sm editbtn"  name="otherid" value="<%=other[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>	
													<%}else if((chssstatusid>=6)){ %>
															<%=other[5]%>			
														
													<%} %>
													</td>
												</tr>					
											<%i++;
											itemstotal += Integer.parseInt(other[3].toString());
											totalremamount +=Integer.parseInt(other[5].toString());
											} %>
												
									</form>
								
									<form action="MiscRemAmountEdit.htm" method="post">
											
											<% i=1;
											for(Object[] misc : MiscDataList)
											{%>
												<%if(i==1){ %>
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>Miscellaneous</b>
															<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
																<button type="button" class="btn btn-sm btn-history" style="float: right;" onclick ="ShowHistory(5)" data-toggle="tooltip" data-placement="top" title="History">
																	<i class="fa-solid fa-clock-rotate-left"></i>
																 </button>
															 <%} %>
														</td>
														<td class="right">
															<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
															<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
														</td>
														<td class="right"></td>
													</tr>
													<tr>
														<th>Bill No</th>
														<th colspan="3">Item</th>
														<th></th>
														<th></th>
													</tr>			
												<%} %>
												<tr>
													<td><%=misc[5] %></td>
													<td colspan="3"><%=misc[2] %></td>
													<td class="right"><%=misc[3] %></td>
													<td class="right">
													<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
														<input type="text" class="w-100" maxlength="255" style="padding: 3px;" placeholder="Comments" name="miscomment-<%=misc[0]%>" style="direction: rtl;" <%if(misc[7]!=null){ %> value="<%=misc[7] %>" <%}else{ %> value="" <%} %> >
														<input type="number" class="numberonly" style="width: 85%;direction: rtl;margin-top: 3px;" name="miscremamount-<%=misc[0]%>" value="<%=misc[4]%>">
														<button type="submit" class="btn btn-sm editbtn"  name="miscid" value="<%=misc[0]%>" onclick="return  confirm('Are You Sure To Update?')" data-toggle="tooltip" data-placement="top" title="Update" >
															<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
														</button>
													<%}else if((chssstatusid>=6)){ %>
														
															<%=misc[4]%>			
														
													<%} %>
													</td>
												</tr>					
											<%i++;
											itemstotal += Integer.parseInt(misc[3].toString());
											totalremamount +=Integer.parseInt(misc[4].toString());
											}%>
										
										<tr>
											<td colspan="3"></td>
											<td class="right">Total</td>
											<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(itemstotal)) %></td>
											
											<td class="right">
											<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>	 
											&#8377; <%=nfc.rupeeFormat(String.valueOf(totalremamount)) %>
											<%} %>
											</td>
										</tr>
												
								</form>
								
									
										<tr>
											<td colspan="6">(In words Rupees <%=awc.convert1(itemstotal) %> Only)</td>
										</tr>
										
										<tr>
											<td colspan="6" class="center"><span style="text-decoration: underline;"><b>FOR OFFICE USE ONLY</b></span></td>
										</tr>
										
										<tr>
											<td colspan="6">Admitted to Rs.
											<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
											 <%=nfc.rupeeFormat(String.valueOf(totalremamount)) %> (Rupees  <%=awc.convert1(totalremamount) %> Only)</td>
											 <%} %>
										</tr>
										
										<tr>
											<td colspan="6" style="height: 70px;vertical-align:bottom">Finance & Accounts Dept.</td>
										</tr>
									</tbody>				
								</table>
							</div>
						</div>
					</div>
					<form action="CHSSUserForward.htm" method="post" id="fwdform">
						<div class="row">
							<div class="col-md-12">
								Remarks : <br>
								<textarea class="w-100 form-control" rows="4" cols="100" id="remarks" name="remarks" maxlength="500"></textarea>
							</div>
							<div class="col-md-12" align="center" style="margin-top: 5px;">
								<%if(chssstatusid==2 || chssstatusid==4 || chssstatusid==7 || chssstatusid==5 ||  chssstatusid==9){ %>
									<%if( chssstatusid!=9){ %>
									<button type="submit" class="btn btn-sm submit-btn" name="claimaction" value="F" onclick="return remarkRequired('F'); " >Approve</button>
									<%} %>
									<button type="submit" class="btn btn-sm delete-btn" name="claimaction" value="R" onclick="return remarkRequired('R'); " >Return</button>
								<%} else if(chssstatusid==1){ %>
									<button type="button" class="btn btn-sm submit-btn" name="claimaction" value="F" onclick="return CheckClaimAmount(<%=chssapplydata[0]%>);" >
										<i class="fa-solid fa-forward" style="color: #A63EC5"></i> Submit for processing	
									</button>
									<button type="Submit" class="btn btn-sm edit-btn" name="action" value="edit" formaction="CHSSAppliedDetails.htm">
										Edit
									</button>
									<input type="hidden" name="claimaction" value="F" >
								<%} else if(chssstatusid==3){ %>
									<button type="button" class="btn btn-sm submit-btn" name="claimaction" value="F" onclick="return CheckClaimAmount(<%=chssapplydata[0]%>);" >
										<i class="fa-solid fa-forward" style="color: #A63EC5"></i> Submit for processing	
									</button>
									<button type="Submit" class="btn btn-sm edit-btn" name="action" value="edit" formaction="CHSSAppliedDetails.htm">
										Edit
									</button>
									<input type="hidden" name="claimaction" value="F" >
								<%}else if(chssstatusid==6){ %>
									<button type="submit" class="btn btn-sm delete-btn"  name="claimaction" value="R" onclick="return remarkRequired('R'); " >Return</button>
								<%} %>
							</div>
						</div>
						<input type="hidden" name="chssapplyidcb" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
					</form>


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
		
		
		
		
	}else if(itemid==2){
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
				$TblStr+=		'<th style="text-align:right;">Claimed (&#8377;)</th>';
				$TblStr+=		'<th style="text-align:right;">Admitted (&#8377;)</th>';
				$TblStr+=	'</tr></thead>';
				$TblStr+=	'<tbody>';
				
				for(var m=0;m< $mischistorylist.length;m++)
				{
					$TblStr+=	'<tr>';
					$TblStr+=		'<td style="width:15%;">'+$mischistorylist[m][1]+'</td>';
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
    
    $('.numberonly').keypress(function (e) {    

        var charCode = (e.which) ? e.which : event.keyCode    

        if (String.fromCharCode(charCode).match(/[^0-9]/g))    

            return false;                        

    });    

}
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
		return confirm('Are You Sure To Approve?');
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
						
			if(result===1)
			{
				if(confirm("Are You Sure To Submit the bill for processing ?\nOnce submitted, data can't be changed"))
				{
					<%if(chssstatusid==3){ %>
						$('#remarks').attr('required', true);
						if($('#remarks').val().trim()===''){
							alert('Please Fill Remarks to Submit! ');
						}else{
							$('#fwdform').submit();
						}
					<%}else{%>
							$('#fwdform').submit();
					<%}%>
				}
			}else if(result===-1){
				alert('Please Add Atleast one Consultation details.');
			}else if(result===0){
				alert('Total claim amount should not be zero !');
			}
		
		}
	});
	
}


</script>

</body>

</html>