<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*" %>
	<!DOCTYPE html>
<html>
<%Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata"); %>
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
					content: "";
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
div, table{
	width: 650px !important;
}

table{
	align: left;
	width: 650px !important;
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
		<meta charset="ISO-8859-1">
		<title>Form</title>
	</head>

<body>

	
<%
	
	List<Object[]> chssbillslist = (List<Object[]>)request.getAttribute("chssbillslist");
	List<Object[]> ConsultDataList = (List<Object[]>)request.getAttribute("ConsultDataList");
	List<Object[]> TestsDataList = (List<Object[]>)request.getAttribute("TestsDataList");
	List<Object[]> MedicineDataList = (List<Object[]>)request.getAttribute("MedicineDataList");
	List<Object[]> OtherDataList = (List<Object[]>)request.getAttribute("OtherDataList");
	List<Object[]> MiscDataList = (List<Object[]>)request.getAttribute("MiscDataList");
	
	
	Employee employee = (Employee)request.getAttribute("employee");
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	IndianRupeeFormat nfc=new IndianRupeeFormat();
	AmountWordConveration awc = new AmountWordConveration();
	int chssstatusid = Integer.parseInt(chssapplydata[9].toString());
	String isapproval = (String)request.getAttribute("isapproval");
	boolean show = false;
	if(isapproval!=null && isapproval.equalsIgnoreCase("Y") || chssstatusid==14 ){
		show = true;
	} 
	
	String LabLogo = (String)request.getAttribute("LabLogo");
	
%>

<div align="center">
	<div align="center">
		<div>
			<div>
				<%-- <span><img style="width: 2cm; height: 2cm"  src="data:image/png;base64,<%=LabLogo%>"></span> --%>
				
			</div>
			<div style="text-align:right; "><span><h3 style="text-align:center; " >MEDICAL CLAIM</h3> </span>  No.of ENCL : &nbsp;<%=chssapplydata[8] %></div>
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
					<tr><td colspan="7" style="text-align: center;padding: 0;"><h4>MEDICAL REIMBURSEMENT DETAILS</h4></td></tr>  <!-- --------------- consultation -------------------- -->
					<tr>
						<th class="center" colspan="4" style="width: 70%;">Particulars</th>
						<th class="right" style="width: 5%;">Amount Claimed (&#8377;)</th>
						<th class="right" style="width: 5%;">Reimbursable under CHSS  (&#8377;)</th>
						<th class="center" style="width: 25%;">Comments</th>
					</tr>
					<%long itemstotal=0,totalremamount=0; %>
					<% int i=1;
					for(Object[] consult :ConsultDataList)
					{%>
						<%if(i==1){ %>
							<tr>
								<td colspan="4" style="text-align: center;"><b>Consultation charges</b></td>
								<td class="right"></td>
								<td class="right"></td>
								<td class="right"></td>
							</tr>
							<tr>
								<th style="width: 14% !important">Bill No</th>
								<th>Doctor</th>
								<th style="width:10%;">Type</th>
								<th style="width:15%;" class="center">Date</th>
								<th></th>
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
							<%if(show){ %>
								<td class="right">	
									<%=consult[7]%>		
								</td>	
								<td class="">
									<%if(consult[10]!=null){ %>
										<%=consult[10]%>
									<%} %>
								</td>
							<%}else{ %>
								<td></td>
								<td></td>
							<%} %>
							
						</tr>					
					<%	i++;
						itemstotal += Integer.parseInt(consult[6].toString());
						totalremamount +=Integer.parseInt(consult[7].toString());
					} %>
					
					
					
					<% i=1;
					for(Object[] test :TestsDataList)
					{%>
						<%if(i==1){ %>
							<tr>
								<td colspan="4" style="text-align: center;"><b>Pathological/Investigations Test</b></td>
								<td class="right"></td>
								<td class="right"></td>
								<td class="right"></td>
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
							<td><%=test[8] %></td>
							<td colspan="3"><%=test[6] %>(<%=test[10] %>)</td>
							<td class="right"><%=test[4] %></td>
							
							<%if(show){ %>
								<td class="right">	
									<%=test[7]%>		
								</td>	
								<td class="">
									<%if(test[11]!=null){ %>
										<%=test[11]%>
									<%} %>
								</td>
							<%}else{ %>
								<td></td>
								<td></td>
							<%} %>
							
						</tr>					
					<%i++;
					itemstotal += Integer.parseInt(test[4].toString());
					totalremamount +=Integer.parseInt(test[7].toString());
					} %>
					
					<% i=1;
					for(Object[] medicine : MedicineDataList)
					{ %>
						<%if(i==1){ %>
							<tr>
								<td colspan="4" style="text-align: center;"><b>Medicines</b></td>
								<td class="right"></td>
								<td class="right"></td>
								<td class="right"></td>
							</tr>
							<tr>
								<th>Bill No</th>
								<th>Medicine Name</th>
								<th style="width:10%;">Rx Qty.</th>
								<th style="width:15%;">Pur Qty.</th>
								<th></th>
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
							
							<%if(show){ %>
								<td class="right">	
									<%=medicine[6]%>		
								</td>	
								<td class="">
									<%if(medicine[9]!=null){ %>
										<%=medicine[9]%>
									<%} %>
								</td>
							<%}else{ %>
								<td></td>
								<td></td>
							<%} %>

						</tr>					
					<%i++;
					itemstotal += Integer.parseInt(medicine[3].toString());
					totalremamount +=Integer.parseInt(medicine[6].toString());
					}%>
					
					
					<% i=1;
					for(Object[] other : OtherDataList)
					{%>
						<%if(i==1){ %>
							<tr>
								<td colspan="4" style="text-align: center;"><b>Others</b></td>
								<td class="right"></td>
								<td class="right"></td>
								<td class="right"></td>
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
							<td><%=other[6] %></td>
							<td colspan="3"><%=other[4] %></td>
							<td class="right"><%=other[3] %></td>
							
							<%if(show){ %>
								<td class="right">	
									<%=other[5]%>		
								</td>	
								<td class="">
									<%if(other[8]!=null){ %>
										<%=other[8]%>
									<%} %>
								</td>
							<%}else{ %>
								<td></td>
								<td></td>
							<%} %>
							
						</tr>					
					<%i++;
					itemstotal += Integer.parseInt(other[3].toString());
					totalremamount +=Integer.parseInt(other[5].toString());
					} %>
					
					
					<% i=1;
					for(Object[] misc : MiscDataList)
					{%>
						<%if(i==1){ %>
							<tr>
								<td colspan="4" style="text-align: center;"><b>Miscellaneous</b></td>
								<td class="right"></td>
								<td class="right"></td>
								<td class=""></td>
							</tr>
							<tr>
								<th>Bill No</th>
								<th colspan="3">Item</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>			
						<%} %>
						<tr>
							<td><%=misc[5] %></td>
							<td colspan="3"><%=misc[2] %></td>
							<td class="right"><%=misc[3] %></td>
								<%if(show){ %>
									<td class="right">	
										<%=misc[4]%>		
									</td>	
									<td class="">
										<%if(misc[7]!=null){ %>
											<%=misc[7]%>
										<%} %>
									</td>
								<%}else{ %>
									<td></td>
									<td></td>
								<%} %>
						</tr>					
					<%i++;
					itemstotal += Integer.parseInt(misc[3].toString());
					totalremamount +=Integer.parseInt(misc[4].toString());
					}%>
					<tr>
						<td colspan="4" class="right">Total</td>
						<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(itemstotal)) %></td>
						<td class="right">
							<%if(show){ %>
								&#8377; <%=nfc.rupeeFormat(String.valueOf(totalremamount)) %>
							<%} %>						
						</td>	
						<td class="right">
						</td>
					</tr>
					
					<tr>
						<td colspan="7">(In words Rupees <%=awc.convert1(itemstotal) %> Only)</td>
					</tr>
					
					<tr>
						<td colspan="7" class="center"><span style="text-decoration: underline;"><b>FOR OFFICE USE ONLY</b></span></td>
					</tr>
					
					<tr>
						<%if(show){ %>
								<td colspan="7">Admitted to &#8377;  <%=nfc.rupeeFormat(String.valueOf(totalremamount)) %> (Rupees  <%=awc.convert1(totalremamount) %> Only)</td>
						<%}else{ %>
							<td colspan="7">Admitted to &#8377;  ............................. (Rupees ...........................................................................................Only)</td>
						<%} %>
						
					</tr>
					
					<tr>
						<td colspan="7" style="height: 50px;vertical-align:bottom">Finance & Accounts Dept.</td>
					</tr>
				</tbody>				
			</table>
		</div>
	</div>
</div>
</body>

</html>