<%@page import="com.sun.xml.bind.v2.schemagen.xmlschema.Import"%>
<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.lang.Math"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Re-imbursement bill</title>
<style type="text/css">
@media print {
	#printPageButton {
		display: none;
	}
}

* {
	font-family: Calibri, sans-serif;
}

table {
	border-collapse: collapse;
	width: 620px;
	font-weight: bold;
	font-size: 14px;
}

table tr, th, td {
	padding: 5px;
}

.table1 {
	border: 2px solid black;
	height: 100px;
	text-align: center;
}

.infoDiv {
	margin-top: 15px;
	font-size: 16px;
}

.table2 {
	border: 1px solid black;
}

.table2 td:first-child:not(.exclude) {
	width: 5%;
}

.table2 td:nth-child(2):not(.exclude) {
	width: 38%;
}

#infoTable tr, th, td {
	border: 1px solid black;
	border-top: none;
	height: 15px;
}

.textNsign {
	width: 620px;
	margin-top: 10px;
	text-align: justify;
}

.sign {
	margin-top: 30px;
	display: flex;
	justify-content: space-between;
	font-weight: bold;
	padding-right: 60px;
}

.note {
	margin-top: 20px;
}

.accDPT {
	width: 620px;
	margin-top: 30px;
}

.accDPT table {
	width: 620px;
	border: 1px solid black;
	margin-top: 20px;
	font-weight: normal;
}

.accDPT table td:first-child:not(.exclude) {
	width: 45%;
}

.FASign {
	width: 620px;
	text-align: right;
	margin-top: 45px;
}

.spouse{
	font-weight: bold;
	text-decoration: underline;
}

</style>
</head>
<body>
	<%
AmountWordConveration nw=new AmountWordConveration();
List<Object[]> TelephoneUserPrintSingleData=(List)request.getAttribute("TelephoneUserPrintSingleData");
List<Object[]> TelephoneUserPrintMultiData=(List)request.getAttribute("TelephoneUserPrintMultiData");

LabMaster LabDetails =(LabMaster)request.getAttribute("LabDetails");

SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
Date today=new Date();

String name="Not Available";
String designation="Not Available";
String SbiAcc="Not Available";
String OtherAcc="Not Available";
String GPFNO="Not Available";

String empNo="Not Available";
String payLevel="Not Available";
String payGrade="Not Available";
String spouse="Not Available";

if(request.getAttribute("TelephoneUserPrintSingleData")!=null){
    for(Object ls[]:TelephoneUserPrintSingleData){
    	name=ls[0].toString();
    	designation=ls[3].toString();
    	SbiAcc=ls[1].toString();
    	if(ls[12]!=null){
   	    OtherAcc=ls[12].toString();
    	}
   	    GPFNO=ls[2].toString();
		empNo=ls[13].toString();
		payLevel=ls[14].toString();
		payGrade=ls[15].toString();
		if(ls[16]!=null){
		spouse=ls[16].toString();
		}else{
			spouse="NA";
		}
		
     break;  
    }}

%>

	<%int count=1;
                     double TotalPayable=0.00;
                      
                     if(TelephoneUserPrintSingleData!=null&&TelephoneUserPrintSingleData.size()>0){ 
            		 for(Object ls[]:TelephoneUserPrintSingleData){
            		 String TeleId=ls[4].toString();
            		 TotalPayable=TotalPayable+Double.parseDouble(ls[9].toString());
            		 %>

	<div align="center">
		<div>
			<table class="table1">
				<tr>
					<td rowspan="2"><span style="font-size: 28px;">STARC</span> <br>
						BANGALORE</td>
					<td rowspan="2">CLAIM FORM FOR REIMBURSEMENT OF<br>RESIDENTIAL
						TELEPHONE EXPENSES FOR<br>EXECUTIVES (MOBILE & / OR LANDLINE)
					</td>
					<td>STARC-BNG- <br> P&A-028
					</td>
					<td>Rev.: 00</td>
				</tr>
				<tr>
					<td>Date of Issue:<br><%=dateFormat.format(today) %>
					</td>
					<td>Total Pages-1</td>
				</tr>
			</table>
		</div>
		<div class="infoDiv">
			<table class="table2">
				<tr>
					<td>1.</td>
					<td>Name of the Employee</td>
					<td colspan="2"><%=name %></td>
				</tr>
				<tr>
					<td>2.</td>
					<td>Emp. No.</td>
					<td colspan="2"><%=empNo %></td>
				</tr>
				<tr>
					<td>3.</td>
					<td>Designation</td>
					<td colspan="2"><%=designation%></td>
				</tr>
				<tr>
					<td>4.</td>
					<td>Grade & Level in the Pay Matrix</td>
					<td><%=payGrade %></td>
					<td><%=payLevel %></td>
				</tr>
				<tr>
					<td>5.</td>
					<td>Function (for E-2 & E-3 only)</td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td>6.</td>
					<td>State whether broadband facility is available or not</td>
					<td colspan="2">
						<%if("Y".equalsIgnoreCase(ls[11].toString())){%><%="Yes"%> <%}else{%><%="No"%>
						<%} %>
					</td>
				</tr>
				<tr>
					<td>7.</td>
					<td colspan="3">Details of the claim are as under:</td>
				</tr>
			</table>
		</div>
		<div>
			<table id="infoTable">
				<tr>
					<th rowspan="2">Type & <br>Number
					</th>
					<th colspan="2">Period of claim</th>
					<th rowspan="2">Bill No. & <br>Date
					</th>
					<!-- <th rowspan="2">Receipt No.<br> & Date
					</th> -->
					<th colspan="2">Details of claim <br>(Rs.)
					</th>
				</tr>
				<tr>
					<th>From</th>
					<th>To</th>
					<th width="60">Rental & <br>Usage
					</th>
					<th>Taxes</th>
				</tr>





				<% int c=0;
				for(Object ls3[]:TelephoneUserPrintMultiData){ 
                             if(TeleId.equalsIgnoreCase(ls3[0].toString())){%>
				<tr>
					<td><%=ls3[9]%>&nbsp; & <br><%=ls3[7]%><br></td>
					<td><%=DateTimeFormatUtil.fromDatabaseToActual_inNumericShortForm(ls3[1].toString())%><br></td>
					<td><%=DateTimeFormatUtil.fromDatabaseToActual_inNumericShortForm(ls3[2].toString())%><br></td>
					<td><%=ls3[5]%>&nbsp; & <br><%=DateTimeFormatUtil.fromDatabaseToActual_inNumericShortForm(ls3[6].toString())%><br></td>
					<td><%=ls3[3]%><br></td>
					<td style="text-align:right;"><%=ls3[10]%><br></td>
					<%--<%if(c==0){ %>
					 <td rowspan="<%=c %>" style="text-align: right;"><%=ls[8]%></td><%}%> --%>
					 <%c++; }}%>	

				</tr>

			

			</table>
		</div>
		<div class="textNsign">
			Certified that my spouse  <span class="spouse"><%=spouse %></span>  is unemployed / employed
			and he / she is not availing reimbursement of the telephone bills
			from his / her department. (Strike off whichever is not applicable).
			It is also certified that there is no ISD facility on my Landline.

			<div class="sign">
				<div>Date:</div>
				<div>
					Signature of Employee <br>STARC Intercom No:
				</div>
			</div>
			<div class="note">
				Note: 1.The bill for reimbursement should be claimed on or before
				25th of the subsequent &nbsp; &nbsp; &nbsp; &nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;
				month of payment of bills. <br> &nbsp; &nbsp; &nbsp; &nbsp;
				&nbsp;&nbsp;&nbsp;2.The original bill and payment receipt should be
				enclosed
			</div>
		</div>
		<div class="accDPT">
			<div
				style="text-decoration: underline; font-weight: bold; margin-bottom: 20px;">FOR
				ACCOUNTS DEPARTMENT USE</div>
			<table>
				<tr>
					<td>Claimed Amount</td>
					<td>Rs. <%=ls[7]%></td>
				</tr>
				<tr>
					<td>Eligible Amount</td>
					<td>Rs. <%=ls[8]%></td>
				</tr>
				<tr>
					<td>Amount passed for payment</td>
					<td>Rs. <%=ls[9]%></td>
				</tr>
			</table>

		</div>

		<div class="FASign">
			<b>F&A Dept.</b>
		</div>
	</div>
<%}} %>
</body>
</html>