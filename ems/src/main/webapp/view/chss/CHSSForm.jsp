<%@page import="java.math.MathContext"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*" %>
	<!DOCTYPE html>
<html>
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

<body>

	
<%
	
	List<Object[]> chssbillslist = (List<Object[]>)request.getAttribute("chssbillslist");
	List<Object[]> ConsultDataList = (List<Object[]>)request.getAttribute("ConsultDataList");
	List<Object[]> TestsDataList = (List<Object[]>)request.getAttribute("TestsDataList");
	List<Object[]> MedicineDataList = (List<Object[]>)request.getAttribute("MedicineDataList");
	List<Object[]> OtherDataList = (List<Object[]>)request.getAttribute("OtherDataList");
	List<Object[]> MiscDataList = (List<Object[]>)request.getAttribute("MiscDataList");
	/* List<Object[]> consultmainlist = (List<Object[]>)request.getAttribute("consultmainlist"); */
	
	
	
	SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yy");
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	IndianRupeeFormat nfc=new IndianRupeeFormat();
	AmountWordConveration awc = new AmountWordConveration();
	int chssstatusid = Integer.parseInt(chssapplydata[9].toString());
	
	String view_mode=(String)request.getAttribute("view_mode");
	
	
	boolean show = false;
	if(view_mode!=null && ((view_mode.equalsIgnoreCase("U") || view_mode.equalsIgnoreCase("UF") )&& chssstatusid==15) || view_mode.equalsIgnoreCase("E") || view_mode.equalsIgnoreCase("V") ||  view_mode.equalsIgnoreCase("A")){
		show = true;
	} 
	
	String LabLogo = (String)request.getAttribute("LabLogo");
	List<Object[]> ClaimapprovedPOVO = (List<Object[]>)request.getAttribute("ClaimapprovedPOVO");
	
%>

<%
	BigDecimal billstotal = new BigDecimal(0);
	BigDecimal discount = new BigDecimal(0);
	
	for(int i=0;i<chssbillslist.size();i++)
	{
		billstotal =billstotal.add (new BigDecimal(chssbillslist.get(i)[7].toString()));
		if(Double.parseDouble(chssbillslist.get(i)[8].toString())>0)
		{
			discount =discount.add (new BigDecimal(chssbillslist.get(i)[6].toString()));
		}
	}
%>




	<div align="center">
		
			<div style="width: 100%;float:left;">
				<div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
				<div style="margin-left:auto; margin-right:auto;"><h3 ><span style="margin-left: -85px; ">MEDICAL CLAIM - OPD</span></h3> <section style="float: right;"><span>No.of ENCL : &nbsp;<span class="text-blue"><%=chssapplydata[8] %></span></span> </section> </div>
			</div>
			
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
						<td class="text-blue" style="text-transform: capitalize;" ><%=employee[2] %></td>
						<td class="text-blue" ><%=employee[1] %></td>
						<td class="text-blue" ><%=employee[13] %></td>

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
						<td class="text-blue"  style="text-transform: capitalize;" ><%=chssapplydata[12] %> &nbsp;(<%=chssapplydata[14] %>)</td>
						<td class="text-blue" ><%=chssapplydata[17] %></td>
						<td class="text-blue" ><%=chssapplydata[10] %></td>
						<td class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(chssapplydata[15].toString()) %></td>
					</tr>
					
				</tbody>
			</table>
			<table style="margin-bottom: 0px;">	
				<tbody>
					<tr>
						<td><b>Basic Pay : </b> &#8377; <%=employee[4] %> </td>
						<td colspan="2"><b>Level in The Pay Matrix : </b> <%=employee[9] %></td>
						<td colspan="2"><b>Ph.No. : </b> <span class="text-blue" ><%=employee[8]%></span></td>
						<td colspan="2"><b>Ext.No. : </b> <span class="text-blue" ><%=employee[14]%></span></td>
					</tr>
				</tbody>
			</table>
			
			<table style="margin-top: 0px;">	
				<tbody>
					<tr>
						<th class="center" style="width: 5%;" >SN</th>
						<th style="width: 30%;" >Hospital / Medical / Diagnostics Center Name</th>
						<th style="width: 17%;" >Bill / Receipt No.</th>
						<th class="center" style="width: 10%;" >Date</th>
						<th style="text-align: right;width: 11%;">MRP (&#8377;)</th>
						<th style="text-align: right;width: 14%;">Discount (&#8377;)</th>
						<th style="text-align: right;width: 13%;">Total (&#8377;)</th>
					</tr>
					<%	
					for(int i=0;i<chssbillslist.size();i++)
					{
					%>
						<tr>
							<td class="center text-blue" ><%=i+1 %></td>
							<td class="text-blue" style=" white-space: pre-wrap;word-break: break-all; "><%=chssbillslist.get(i)[3] %></td>
							<td class="text-blue" style=""><%=chssbillslist.get(i)[2] %></td>
							<td class="center text-blue" style="" ><%=rdf.format(sdf.parse(chssbillslist.get(i)[4].toString())) %></td>
							<td class="text-blue" style="text-align: right;"><%=new BigDecimal(chssbillslist.get(i)[6].toString()).add(new BigDecimal(chssbillslist.get(i)[7].toString())) %></td>
							<td class="text-blue" style="text-align: right;"><%=chssbillslist.get(i)[6] %></td>
							<td class="text-blue" style="text-align: right;"><%=chssbillslist.get(i)[7] %></td>
						</tr>
					<%} %>
					<%if(chssbillslist.size()>0){ %>
					<%-- 	<tr>
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
							<td style="text-align: right;"><b>Total </b></td>
							<td class="text-blue"  style="text-align: right;"><%=nfc.rupeeFormat(String.valueOf(billstotal.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())) %></td>
						</tr>
					<%}else{ %>
						<tr>
							<td colspan="5" class="center" >Bills Not Added</td>
						</tr>
					<%} %>
				</tbody>
			</table>
			<%-- <table style="margin-bottom: 0px;">	
									<thead>
										<tr>
											<th style="text-align: center;width: 5%">SN</th>
											<th style="width: 50%">Doctor</th>
											<th style="width: 30%">Qualification</th>
											<th style="text-align: center;width: 15%">Consultation Date</th>
										</tr>
									</thead>
									<tbody>
										<%for(Object[] consultmain : consultmainlist){ %>
										<tr>
											<td style="text-align: center;" ><%=consultmainlist.indexOf(consultmain)+1 %></td>
											<td><%=consultmain[1] %></td>
											<td><%=consultmain[4] %></td>
											<td style="text-align: center;" ><%=DateTimeFormatUtil.SqlToRegularDate(consultmain[3].toString()) %></td>		
										</tr>
										<%} %>
									</tbody>
								</table> --%>
								
						<div style="margin-left: 10px;font-size: 14px;" align="left">
										I do state that the member(s) of my family for whom reimbursement of medical expenses claimed in this bill are
										dependent upon me and eligible for reimbursement under CHSS Rules and declare in particular:
										<br>
										
										<% int numbering=0;
											if(new ArrayList<String>( Arrays.asList("3", "4", "15","16")).contains(chssapplydata[13].toString().trim())){ %>
											&#9675; <span>That my Parents / Parents-in-Law  <span class="text-blue" style="text-transform: capitalize;">Shri / Smt. <%=chssapplydata[12] %>  </span> are wholly dependent
											upon me and reside with me and that his / her total monthly incomes does not exceed Rs. 10,000/- per
											month.</span> 
											<br>
											<%if(new ArrayList<String>( Arrays.asList("15","16")).contains(chssapplydata[13].toString().trim())){ %>
											&#9675;  <span>That no claim has been / will be made in respect of my parents as I opt to claim reimbursement in respect of
											my parents-in-law (applicable in  case of female employees only).</span>  
											<br>
											<%} %>
										<%} %>
										
											&#9675; <span>That the patient <span class="text-blue" style="text-transform: capitalize;">Shri / Smt. <%=chssapplydata[12] %>  </span> is not covered by the ESI Scheme / any
											other medical facility.</span> 
											<br>
											
										<%if(new ArrayList<String>( Arrays.asList("6","7")).contains(chssapplydata[13].toString().trim())){ %>
											&#9675; <span>  That the claim does not relate to my married daughter(s) / son(s) above the age of 25 years. In case of my
											son(s) and unmarried daughters(s), I declare that they are not gainfully employed and are dependent upon
											me.</span>
											<br>
										<%} %>
										
										<%if(new ArrayList<String>( Arrays.asList("5","8")).contains(chssapplydata[13].toString().trim())){ %>
											&#9675; <span> That my wife / husband is an employee of (Orgn) ................................ and we have furnished a joint
											declaration in writing that I shall prefer the claim in respect of our family except for
											......................................... which shall be claimed by my spouse.</span>
											<br>
											&#9675;  <span>  That my wife/ husband is employed in (Orgn) .........................................and is certified that medical benefit
											claimed in this bill has not been preferred / shall not be preferred from any other source.</span>
											<br>
										<%} %>
										
										&#9675; <span> That the bills attached herewith and the statements made in this claim are true and correct and I may be
										held liable, if anything is found to be incorrect later on.</span>
										<br>
										&#9675;  <span> This bill is submitted on <b class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(chssapplydata[15].toString())%></b> which is within 3 months of treatment / hospitalization.</span>
										<br>
										&#9675;  <span> I am not claiming the consultation fees within 7 days of preceding consultation for the same illness.</span> 
										<br>
										&#9675;  <span> It is certified that the reimbursement claimed in this form is genuine and not availed from any sources.</span>
									
				<div style="max-width: 650px;padding-top:45px ;" align="left">
					
						
					<!-- Date : 							<span style="float: right;">(Signature of Employee) </span> -->
					
					<table style="border:0px;border-collapse: collapse;width: 100%; "  >
						<tr>
							<td style="border:0px;width: 50%;"> Date : </td>
							<td style="border:0px;width: 50%; text-align: right;"> (Signature of Employee) </td>
						</tr>
						<tr>
							<td style="border:0px;width: 50%;"> </td>
							<td style="border:0px;width: 50%; text-align: right;"> 
								<%=employee[2] %>,<br>
								<span style="font-size:10px; ">[Forwarded On:&nbsp; <%=DateTimeFormatUtil.SqlToRegularDate(chssapplydata[15].toString()) %>]</span>
							</td>
						</tr>
					</table>
					
					
					
					
					
				</div>
				<%-- <%if(ClaimRemarksHistory.size()>0){ %>
					<table style="max-width: 650px;margin-left:0px;">
						<tr>
							<td style="border:none;">
								<h4 style="text-decoration: underline;">Remarks :</h4> 
							</td>
							
						</tr>
						<%for(Object[] obj : ClaimRemarksHistory){%>
						<tr>
							<td style="border:none;width: 25%;">
								<%=obj[3] %>&nbsp; :
							
							<span style="border:none;" class="text-blue" >
								<%=obj[1] %>
							</span>
							</td>
						</tr>
						<%} %>
					</table>
				<%} %> --%>
				
			</div>
		<div class="break"></div>
		<div align="center">
			<table>
				<tbody>
					<tr><td colspan="7" style="text-align: center;padding: 0;"><h4>MEDICAL REIMBURSEMENT DETAILS</h4></td></tr>  <!-- --------------- consultation -------------------- -->
					<tr>
						<th class="center" colspan="4" style="width: 70%;">Particulars</th>
						<th class="right" style="width: 5%;">Amount Claimed (&#8377;)</th>
						<th class="right" style="width: 5%;">Reimbursable under CHSS  (&#8377;)</th>
						<th class="center" style="width: 25%;">Comments</th>
					</tr>
					<%	BigDecimal itemstotal=new BigDecimal("0.0");
					BigDecimal totalremamount=new BigDecimal("0.0");  %>
					<% int i=1;
					for(Object[] consult :ConsultDataList)
					{%>
						<%if(i==1){ %>
							<tr>
								<td colspan="4" style="text-align: center;"><b>Consultation Charges</b></td>
								<td class="right"></td>
								<td class="right"></td>
								<td class="right"></td>
							</tr>
							<tr>
								<th style="width: 15% !important">Bill No</th>
								<th>Doctor</th>
								<th style="width:10%;">Type</th>
								<th style="width:15%;" class="center">Date</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>			
						<%} %>
						<tr>
							<td  class="text-blue" ><%=consult[8] %><%-- <br>(<%=rdf.format(sdf.parse(consult[9] .toString()))%>) --%></td>
							<td class="text-blue" ><%=consult[3] %>&nbsp;(<%=consult[11] %>)</td>
							<td class="text-blue" ><%=consult[2] %></td>
							<td class="center text-blue"><%=rdf.format(sdf.parse(consult[5].toString()))%></td>
							<td class="right text-blue"><%=consult[6] %></td>
							<%if(show){ %>
								<td class="right text-green">	
										<%if(consult[12]== null   || Long.parseLong(consult[12].toString())==0){ %>	 
											<span class="systemgen"><%=consult[7]%></span>		
										<%}else if(consult[13].toString().equalsIgnoreCase("K")){ %>
											<span class="processed"><%=consult[7]%></span>		
										<%}else if(consult[13].toString().equalsIgnoreCase("V")){ %>
											<span class="verified"><%=consult[7]%></span>		
										<%} %>	
								</td>	
								<td class="text-green">	
									<%if(consult[10]!=null){ %>
										<%if(consult[12]== null   || Long.parseLong(consult[12].toString())==0){ %>	 
											<span class="systemgen"><%=consult[10]%></span>		
										<%}else if(consult[13].toString().equalsIgnoreCase("K")){ %>
											<span class="processed"><%=consult[10]%></span>		
										<%}else if(consult[13].toString().equalsIgnoreCase("V")){ %>
											<span class="verified"><%=consult[10]%></span>		
										<%} %>	
									<%} %>
								</td>
							<%}else{ %>
								<td></td>
								<td></td>
							<%} %>
							
						</tr>					
					<%	i++;
					itemstotal =itemstotal.add (new BigDecimal(consult[6].toString()));
					totalremamount =totalremamount.add (new BigDecimal(consult[7].toString()));
					} %>
					
					<% i=1;
					for(Object[] test :TestsDataList)
					{%>
						<%if(i==1){ %>
							<tr>
								<td colspan="4" style="text-align: center;"><b>Tests / Procedures</b></td>
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
							<td class="text-blue" ><%=test[8] %><%-- <br>(<%=rdf.format(sdf.parse(test[9] .toString()))%>) --%></td>
							<td  class="text-blue" colspan="3"><%=test[6] %>(<%=test[10] %>)</td>
							<td class="right text-blue"><%=test[4] %></td>
							
							<%if(show){ %>
								<td class="right text-green">	
									<%if(test[12]== null  || Long.parseLong(test[12].toString())==0){ %>	 
										<span class="systemgen"><%=test[7]%>	</span>		
									<%}else if(test[13].toString().equalsIgnoreCase("K")){ %>
										<span class="processed"><%=test[7]%>	</span>		
									<%}else if(test[13].toString().equalsIgnoreCase("V")){ %>
										<span class="verified"><%=test[7]%>	</span>		
									<%} %>	
								</td>	
								<td class="text-green">
									<%if(test[11]!=null){ %>
										<%if(test[12]== null  || Long.parseLong(test[12].toString())==0){ %>	 
											<span class="systemgen"><%=test[11]%>	</span>		
										<%}else if(test[13].toString().equalsIgnoreCase("K")){ %>
											<span class="processed"><%=test[11]%>	</span>		
										<%}else if(test[13].toString().equalsIgnoreCase("V")){ %>
											<span class="verified"><%=test[11]%>	</span>		
										<%} %>	
									<%} %>
								</td>
							<%}else{ %>
								<td></td>
								<td></td>
							<%} %>
							
						</tr>					
					<%i++;
					itemstotal =itemstotal.add (new BigDecimal(test[4].toString()));
					totalremamount =totalremamount.add (new BigDecimal(test[7].toString()));
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
								<th style="width:10%;text-align: center;">Rx<br>Qty.</th>
								<th style="width:10%;text-align: center;">Pur<br> Qty.</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>			
						<%} %>
						<tr>
							<td  class="text-blue" ><%=medicine[7] %><%-- <br>(<%=rdf.format(sdf.parse(medicine[8] .toString()))%>) --%></td>
							<td  class="text-blue" ><%=medicine[2] %></td>														
							<td  class="text-blue" style="text-align: center;"><%=medicine[5] %></td>
							<td class="text-blue"  style="text-align: center;" ><%=medicine[4] %></td> 
							<td  class="text-blue right"><%=medicine[3] %></td>
							
							<%if(show){ %>
								<td class="right text-green">	
									<%if(medicine[10]== null  || Long.parseLong(medicine[10].toString())==0){ %>	 
										<span class="systemgen"> <%=medicine[6]%>	</span>		
									<%}else if(medicine[11].toString().equalsIgnoreCase("K")){ %>
										<span class="processed"> <%=medicine[6]%>	</span>		
									<%}else if(medicine[11].toString().equalsIgnoreCase("V")){ %>
										<span class="verified"> <%=medicine[6]%>	</span>		
									<%} %>		
								</td>	
								<td class="text-green">
									<%if(medicine[9]!=null){ %>
										<%if(medicine[10]== null  || Long.parseLong(medicine[10].toString())==0){ %>	 
											<span class="systemgen"> <%=medicine[9]%>	</span>		
										<%}else if(medicine[11].toString().equalsIgnoreCase("K")){ %>
											<span class="processed"> <%=medicine[9]%>	</span>		
										<%}else if(medicine[11].toString().equalsIgnoreCase("V")){ %>
											<span class="verified"> <%=medicine[9]%>	</span>		
										<%} %>	
									<%} %>
								</td>
							<%}else{ %>
								<td></td>
								<td></td>
							<%} %>

						</tr>					
					<%i++;
					itemstotal =itemstotal.add (new BigDecimal(medicine[3].toString()));
					totalremamount =totalremamount.add (new BigDecimal(medicine[6].toString()));
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
							<td  class="text-blue" ><%=other[6] %><%-- <br>(<%=rdf.format(sdf.parse(other[7] .toString()))%>) --%></td>
							<td  class="text-blue" colspan="3"><%=other[4] %></td>
							<td class="right text-blue"><%=other[3] %></td>
							
							<%if(show){ %>
								<td class="right text-green">	
									<%if(other[9]== null  || Long.parseLong(other[9].toString())==0){ %>	 
										<span class="systemgen"> <%=other[5]%>	</span>		
									<%}else if(other[10].toString().equalsIgnoreCase("K")){ %>
										<span class="processed"> <%=other[5]%>	</span>		
									<%}else if(other[10].toString().equalsIgnoreCase("V")){ %>
										<span class="verified"> <%=other[5]%>	</span>		
									<%} %>	
								</td>	
								<td class="text-green">
									<%if(other[8]!=null){ %>
										<%if(other[9]== null  || Long.parseLong(other[9].toString())==0){ %>	 
											<span class="systemgen"> <%=other[8]%>	</span>		
										<%}else if(other[10].toString().equalsIgnoreCase("K")){ %>
											<span class="processed"> <%=other[8]%>	</span>		
										<%}else if(other[10].toString().equalsIgnoreCase("V")){ %>
											<span class="verified"> <%=other[8]%>	</span>		
										<%} %>	
									<%} %>
								</td>
							<%}else{ %>
								<td></td>
								<td></td>
							<%} %>
							
						</tr>					
					<%i++;
					itemstotal =itemstotal.add (new BigDecimal(other[3].toString()));
					totalremamount =totalremamount.add (new BigDecimal(other[5].toString()));
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
								<th colspan="2">Item</th>
								<th style="text-align: center;">Qty</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>			
						<%} %>
						<tr>
							<td class="text-blue" ><%=misc[5] %><%-- <br>(<%=rdf.format(sdf.parse(misc[6] .toString()))%>) --%></td>
							<td  class="text-blue" colspan="2"><%=misc[2] %></td>
							<td class="text-blue"  style="text-align: center;"><%if(misc[8]!=null){ %><%=misc[8] %><%} %></td>
							<td class="right text-blue"><%=misc[3] %></td>
								<%if(show){ %>
									<td class="right text-green">	
										<%if(misc[9]== null  || Long.parseLong(misc[9].toString())==0){ %>	 
											<span class="systemgen"> <%=misc[4]%>	</span>		
										<%}else if(misc[10].toString().equalsIgnoreCase("K")){ %>
											<span class="processed"> <%=misc[4]%></span>		
										<%}else if(misc[10].toString().equalsIgnoreCase("V")){ %>
											<span class="verified"> <%=misc[4]%>	</span>		
										<%} %>	
									</td>	
									<td class="text-green">
										<%if(misc[7]!=null){ %>
											<%if(misc[9]== null  || Long.parseLong(misc[9].toString())==0){ %>	 
												<span class="systemgen"> <%=misc[7]%>	</span>		
											<%}else if(misc[10].toString().equalsIgnoreCase("K")){ %>
												<span class="processed"> <%=misc[7]%></span>		
											<%}else if(misc[10].toString().equalsIgnoreCase("V")){ %>
												<span class="verified"> <%=misc[7]%>	</span>		
											<%} %>	
										<%} %>
									</td>
								<%}else{ %>
									<td></td>
									<td></td>
								<%} %>
						</tr>					
					<%i++;
					itemstotal =itemstotal.add (new BigDecimal(misc[3].toString()));
					totalremamount =totalremamount.add (new BigDecimal(misc[4].toString()));
					}%>
					
					<tr>
						<td colspan="4" class="right"><b>Total</b></td>
						<td class="right text-blue"><b> <%=itemstotal %></b></td>
						<td class="right text-green">
							<%if(show){ %>	 
								&#8377; <b><%=totalremamount %></b>
							<%} %>
						</td>
						<td ></td>
					</tr>
										
					<%-- <tr>
						<td colspan="4" class="right"><b>Total GST (+)</b></td>
						<td class="right text-blue"><b><%=GST %></b></td>
						<td class="right text-green"></td>
						<td ></td>
					</tr> --%>
					<tr>
						<td colspan="4" class="right"><b>Total Discount (-)</b></td>
						<td class="right text-blue"><b><%=discount %></b></td>
						<td class="right text-green"></td>
						<td ></td>
					</tr>
										
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
						<td colspan="7" class="text-blue">(In words Rupees <%=awc.convert1(itemstotal.subtract(discount).setScale(0, BigDecimal.ROUND_HALF_UP).longValue()) %> Only)</td>
					</tr>
					
					<tr>
						<td colspan="7" class="center"><span style="text-decoration: underline;"><b>FOR OFFICE USE ONLY</b></span></td>
					</tr>
					
					<tr>
						<%if(show){%>
								<td colspan="7" class="text-green">Admitted to &#8377;  
								<%= nfc.rupeeFormat(String.valueOf(totalremamount.setScale(0, BigDecimal.ROUND_HALF_UP))) %> (Rupees  <%=awc.convert1(totalremamount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue()) %> Only)</td>
						<%}else{ %>
							<td colspan="7">Admitted to &#8377;  ............................. (Rupees ...........................................................................................Only)</td>
						<%} %>
						
					</tr>
					<tr>
						<td colspan="7" style="text-align:center; border-bottom : 0px;text-decoration: underline;"><b>Finance and Accounts Department</b></td>
					</tr>
					<tr>
						<td  colspan="3" style="border-top: 0px;border-right : 0px;height:120px;padding: 0px;margin:0px;">
							<ul style="list-style-type: none;margin:10px 5px -35px -35px;">
								<% int flag=0;
								for(Object[] obj:ClaimapprovedPOVO){
									if(obj[1].toString().equalsIgnoreCase("PO")){%>
									<li style="text-transform: capitalize;"><%=obj[2] %>,</li>
									<li style="text-transform: capitalize;"><%=obj[4] %> </li>
									<li><span style="font-size:10px; ">[Processed On :&nbsp; <%=DateTimeFormatUtil.SqlToRegularDate(obj[6].toString().substring(0, 10))  +" "+obj[6].toString().substring(11,19) %>]</span></li>
								<%flag=1;
								break;} } %>
								
								<%if(flag==0){ %>
								
									<%for(Object[] obj:ClaimapprovedPOVO){
										if(obj[1].toString().equalsIgnoreCase("authority") &&  obj[5].toString().equalsIgnoreCase("K")){%>
										<li style="text-transform: capitalize;"><%=obj[2] %>,</li>
										<li style="text-transform: capitalize;"><%=obj[4] %> </li>
									<%
									break;} } %>								
								<%} %>
								
								
							</ul>
						</td>				
						<td colspan="4" style="border-top: 0px;border-left : 0px;padding: 0px;margin:0px;height:120px;">
							<ul style="float: right;list-style-type: none; margin:10px 5px -35px 0px; ">
								<%  flag=0;
								for(Object[] obj:ClaimapprovedPOVO){
									if(obj[1].toString().equalsIgnoreCase("VO")){%>
									<li style="text-transform: capitalize;"><%=obj[2] %>,</li>
									<li style="text-transform: capitalize;"><%=obj[4] %> </li>
									<li><span style="font-size:10px; ">[Verified On:&nbsp; <%=DateTimeFormatUtil.SqlToRegularDate(obj[6].toString().substring(0, 10))  +" "+obj[6].toString().substring(11,19) %>]</span></li>
								<%flag=1;
								break;} } %>
								
								<%if(flag==0){ %>
								
									<%for(Object[] obj:ClaimapprovedPOVO){
										if(obj[1].toString().equalsIgnoreCase("authority") &&  obj[5].toString().equalsIgnoreCase("V")){%>
										<li style="text-transform: capitalize;"><%=obj[2] %>,</li>
										<li style="text-transform: capitalize;"><%=obj[4] %> </li>
									<%
									break;} } %>								
								<%} %>
							</ul>
							
						</td>	
					</tr>
					
				</tbody>			
				
					
			</table>
		</div>
	</div>

</body>

</html>