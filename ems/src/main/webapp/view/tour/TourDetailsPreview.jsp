<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Tour Proposal Form</title>

</head>
<body>

	<%
	String LabLogo = (String) request.getAttribute("LabLogo");

	Object[] tourdetails = (Object[]) request.getAttribute("tourdetails");
	List<Object[]> list = (List<Object[]>)request.getAttribute("Touronwarddetails");
	Object[] ApprovalEmp=(Object[])request.getAttribute("ApprovalEmp"); 
	SimpleDateFormat time = new SimpleDateFormat("HH:mm");
	%>
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Tour Proposal Form</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TourApplyList.htm">Tour List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Tour Program Form</li>
					</ol>
			</div>
		</div>
	</div>


	<div class="page card dashboard-card">
		<div class="card-body" align="center">
			<div class="row">



				<div class="card-body">
					<div class="card"
						style="padding-top: 0px;  width: 85%;">
						<form action="VehicleParkFormSubmitAndPrint.htm" method="post">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="card-body main-card" style="padding-top: 0px; " align="center">
								<table style="border: 0px; width: 100%">
									<tr>
										<td
											style="width: 20%; height: 75px; border: 0; margin-bottom: 10px;"><img
											style="width: 80px; height: 90px; margin: 5px;" align="left"
											src="data:image/png;base64,<%=LabLogo%>"></td>
										<td
											style="width: 60%; height: 75px; border: 0; text-align: center; vertical-align: bottom;"><h4>
												FORM FOR TOUR PROGRAM <br>
											</h4></td>
										<td
											style="width: 20%; height: 75px; border: 0; vertical-align: bottom;">
											<span style="float: right;"> &nbsp;<span
												class="text-blue"></span></span>
										</td>
									</tr>
								</table>
								
								<br>
								
								<table style="border: 0px; width: 100%">
									<tr>
										<td> <b>Department : </b>&nbsp; <%=tourdetails[3]%></td>
										<td><b> Phone No : </b> &nbsp; <%=tourdetails[6]%></td>
										<td><b> Date : </b></td>
										
									</tr>
								</table>
								<br>
								<div align="left">
								<table>
									<tr>
										<td style="text-align: left;">
										 The Following tour Program is proposed for the purpose of   <%=tourdetails[9]%>
										</td>
									</tr>
									<tr>	
										<td> <b> Brief details of the proposed tour program :</b></td>
									</tr>
									<tr>	
										<th> 1) Employee proposed to be deputed:</th>
									</tr>
								</table>
								<table style="width: 100%; margin-top: 10px; ">
									<thead>
										<tr>
											<th style="border: 1px solid black; border-collapse: collapse;"> SN </th>
											<th style="border: 1px solid black; border-collapse: collapse;"> Name </th>
											<th style="border: 1px solid black; border-collapse: collapse;">Emp No</th>
											<th style="border: 1px solid black; border-collapse: collapse;"> DOB</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Pay Level</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Mobile No</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Email</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;">1</td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=tourdetails[4]%>(<%=tourdetails[3]%>)</td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=tourdetails[2]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=DateTimeFormatUtil.fromDatabaseToActual( tourdetails[5].toString())%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=tourdetails[8]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=tourdetails[6]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=tourdetails[7]%></td>
										</tr>
									</tbody>
								</table>
								<table style="width: 100%;margin-top: 10px;">
									<tr>
										<th colspan="3">2) Earliest presence required at destination</th>
									</tr>
									<tr style="text-align: center;">
										<td>a)Time : <%=time.format(tourdetails[11])%></td>
										<td>Date : <%=tourdetails[10]%></td>
										<td>Place :<%=tourdetails[12]%></td>
									</tr>
								</table>
								
								<table style="width: 100%; margin-top: 10px;">
									
									<thead>
										<tr >
											<th style="text-align: left;">3)Proposed journey details :</th>
										</tr>
										<tr>
											<th style="border: 1px solid black; border-collapse: collapse;">Departure Place</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Date</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Time</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Arrival Place</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Mode of Journey</th>
										</tr>
									</thead>
									<tbody>
									<% if(list!=null && list.size()>0){
										for(Object[] obj:list){%>
										<tr>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[2]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=DateTimeFormatUtil.fromDatabaseToActual(obj[0].toString())%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=time.format(obj[1])%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[3]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[4]%></td>
										</tr>
										<%}}%>
									</tbody>
								</table>
								
								<table style="margin-top: 10px;">
									<tr>
										<th>4)Period of stay :</th>
										<td>Form : &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; <%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[13].toString()) %>&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</td>
										<td>To: &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;<%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[14].toString()) %></td>
									</tr>
									<tr>	
										<th>5)Place of stay : </th>
										<td colspan="2"> <%=tourdetails[15]%></td>
									</tr>
									<tr>
										<td colspan="3"> <b>6)Justification for Air Travel of non-entitled employee (if applicable):</b> &nbsp; &nbsp;&nbsp;<%=tourdetails[16]%> </td>
									</tr>
									<tr>
										<td ><b>7)Amount of Travelling Advance proposed :&nbsp; &nbsp;&nbsp; Rs.</b><%=tourdetails[17]%></td>
									</tr>
									</table>
									<table style="width: 100%; margin-top: 6px;">
									<tr> 
										<th style="border: 1px solid black; border-collapse: collapse;"> SN</th>
										<th style="border: 1px solid black; border-collapse: collapse;">Particulars</th>
										<th style="border: 1px solid black; border-collapse: collapse;">Amount</th>
									</tr>
									<tr>
										<td style="border: 1px solid black; border-collapse: collapse;">1</td>
										<td style="border: 1px solid black; border-collapse: collapse;">Air/Train/Bus fare(to & from)<br>(single fate @ Rs. )</td>
										<td style="border: 1px solid black; border-collapse: collapse;">---</td>
									</tr>
									<tr>
										<td style="border: 1px solid black; border-collapse: collapse;">2</td>
										<td style="border: 1px solid black; border-collapse: collapse;">
											<b style="text-decoration: underline;">Daily Allowance </b><br>
											Boarding Charges for ...................Days<br>
											(@Rs. ...............................per day)<br>
											Daily Allowance at Rs. ................per day <br>
											for ..............days from .............. to .................
											
										</td>
										<td style="border: 1px solid black; border-collapse: collapse;"></td>
									</tr>
									<tr>
									 	<td colspan="2" style="text-align:right; border: 1px solid black; border-collapse: collapse;"><b>Total :</b></td>
									 	<td style="border: 1px solid black; border-collapse: collapse;"></td>
									</tr>
									
									<tr>
										<td colspan="3">The above proposal is put up for approval I Undertake that:</td>
									</tr>
									
									<tr>
										<td colspan="3"> &nbsp; &nbsp;&nbsp;1. I have to render statement of expenses towards adjustment of the advance paid to me before asking for payment of further advance and in any case within 15 days of completion of my tour.</td>
									</tr>
									<tr>
										<td colspan="3"> &nbsp; &nbsp; 	2. I have to refund to the STARC any excess of TA advance amount immediately on completion of the tour.</td>
									</tr>
									<tr>
										<td colspan="3">&nbsp; &nbsp; 	3. I have read / have been explained the TA rules applicable to me. <br><br></td>
									</tr>
									
									<tr>
										<td colspan="2"> 
											Signature of the employee <br>
											Name : &nbsp;&nbsp;<%=tourdetails[4]%>(<%=tourdetails[3]%>)
										
										 </td>
										
										<td style="text-align: right;">  
											Signature of Dept. Incharge <br>
											Name : &nbsp;&nbsp;
											
											<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("VDH") || 
											tourdetails[19].toString().equalsIgnoreCase("ABD") || tourdetails[19].toString().equalsIgnoreCase("RDG") ||
										  	tourdetails[19].toString().equalsIgnoreCase("RBF") || tourdetails[19].toString().equalsIgnoreCase("ABF") || 
										    tourdetails[19].toString().equalsIgnoreCase("RBP") || tourdetails[19].toString().equalsIgnoreCase("ABP") ||
										    tourdetails[19].toString().equalsIgnoreCase("RBC") || tourdetails[19].toString().equalsIgnoreCase("ABC") ||
										    tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
											 <%=ApprovalEmp[0]%>
											<%}%>
										</td>
									</tr>
									<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABD") ||
										  	tourdetails[19].toString().equalsIgnoreCase("RBF") || tourdetails[19].toString().equalsIgnoreCase("ABF") || 
										    tourdetails[19].toString().equalsIgnoreCase("RBP") || tourdetails[19].toString().equalsIgnoreCase("ABP") ||
										    tourdetails[19].toString().equalsIgnoreCase("RBC") || tourdetails[19].toString().equalsIgnoreCase("ABC") ||
										    tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
									<tr>
										<td colspan="3">
										Signature of DGM<br>
										Name : &nbsp;&nbsp;
										
												<%=ApprovalEmp[1]%>
											
										</td>
									</tr>
									<%}%>
									<%if(tourdetails[19]!=null &&  tourdetails[19].toString().equalsIgnoreCase("ABF") || 
										    tourdetails[19].toString().equalsIgnoreCase("RBP") || tourdetails[19].toString().equalsIgnoreCase("ABP") ||
										    tourdetails[19].toString().equalsIgnoreCase("RBC") || tourdetails[19].toString().equalsIgnoreCase("ABC") ||
										    tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
									<tr> 
										<td colspan="3" style="text-align: center;"><b style="text-decoration: underline;">FOR USE BY FINANCE DEPARTMENT </b> </td>			
									</tr>
									
									<tr>
										<th colspan="3"> Availability of funds :</th>
									</tr>
									
									<tr>
										<td> &nbsp; &nbsp;&nbsp; &nbsp;Available :</td>
										<td colspan="2"> <%if(tourdetails[20]!=null && tourdetails[20].toString().equalsIgnoreCase("Y")){%> <i class="fa fa-check" aria-hidden="true" style="color: green;"></i> <%}%></td>
									</tr>
									<tr>
										<td>&nbsp; &nbsp;&nbsp; &nbsp;Not Available :</td>
										<td colspan="2"> <%if(tourdetails[20]!=null && tourdetails[20].toString().equalsIgnoreCase("N")){%> <i class="fa fa-times" aria-hidden="true" style="color: red ;"></i> <%}%></td>
									</tr>
									
									<tr> 
										<td colspan="3" style="text-align: right;">
											(<%=ApprovalEmp[2]%>)<br>
											Incharge - F & A
										 </td>
									</tr>
									<%}%>
									<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABP") ||
										    tourdetails[19].toString().equalsIgnoreCase("RBC") || tourdetails[19].toString().equalsIgnoreCase("ABC") ||
										    tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
									
									<tr>
										<td colspan="3" style="text-align:center;">
											<b style=" text-decoration: underline;">REMARKS OF ADMINISTRATION</b>
										</td>
									</tr>
									<tr>
										<td colspan="3"> 1)Above tour program may be approved and sanction an advance amount Rs.<%=tourdetails[17]%></td>
									</tr>
									<tr>
										<td colspan="3" > 2)Remarks, If any:  <%=tourdetails[21]%></td>
									</tr>
									
									<tr>
										<td colspan="3" style="text-align: right;"> (<%=ApprovalEmp[3]%>)<br> Incharge - P & A</td>
									</tr>
									<%}%>
									<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABC") || tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
									<tr>
											<td colspan="3" style="text-align: center;"><b> SANCTIONED / NOT SANCTIONED <br>CEO</b><br> <%=ApprovalEmp[4]%></td>
									</tr>	
										<%}%>
								</table>
																	
								</div>
								
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>