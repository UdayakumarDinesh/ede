<%@page import="com.vts.ems.utils.AmountWordConveration"%>
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
	Object[] touradvancedetails = (Object[])request.getAttribute("touradvancedetails");
	List<Object[]> list = (List<Object[]>)request.getAttribute("Touronwarddetails");
	List<Object[]> TourStatisDetails = (List<Object[]>)request.getAttribute("tourstatisdetails");
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
						<form action="TourApplyList.htm" method="post">
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
									
								</table>
								<table style="width: 100%; margin-top: 10px; ">
									<thead>
										<tr>
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
										<th colspan="3">1) Earliest presence required at destination</th>
									</tr>
									<tr style="text-align: center;">
										<td>a)Time : <%=time.format(tourdetails[11])%></td>
										<td>Date : <%=DateTimeFormatUtil.fromDatabaseToActual( tourdetails[10].toString())%></td>
										<td>Place : <%=tourdetails[12]%></td>
									</tr>
								</table>
								
								<table style="width: 100%; margin-top: 10px;">
									
									<thead>
										<tr >
											<th style="text-align: left;">2)Proposed journey details :</th>
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
										<th>3)Period of stay :</th>
										<td>From : &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; <%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[13].toString()) %>&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</td>
										<td>To : &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;<%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[14].toString()) %></td>
									</tr>
									<tr>	
										<th>4)Place of stay : </th>
										<td colspan="2"> <%=tourdetails[15]%></td>
									</tr>
									<tr>
										<td colspan="3"> <b>5)Justification for Air Travel of non-entitled employee (if applicable):</b><%if(tourdetails[16]!=null){%> &nbsp; &nbsp;&nbsp;<%=tourdetails[16]%>  <%}else{%> -- <%}%></td>
									</tr>
									<tr>
										<td ><b>6)Amount of Travelling Advance proposed :&nbsp; &nbsp;&nbsp; </b><%if(tourdetails[17]!=null && tourdetails[17].toString().equalsIgnoreCase("Y")){%> Yes <%}else{%> No <%}%></td>
									</tr>
									</table>
									<table style="width: 100%; margin-top: 6px;">
									
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
											 <%for(Object[] obj:TourStatisDetails){ 
												if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("VDH")){%>
											 <%=obj[2]%> (<%=obj[3]%>)
											<% break;}}%>
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
										
										<%for(Object[] obj:TourStatisDetails){ 
												if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("ABD")){%>
											 <%=obj[2]%> (<%=obj[3]%>)
											<% break;}}%>
											
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
											<%for(Object[] obj:TourStatisDetails){ 
												if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("ABF")){%>
											 <%=obj[2]%> (<%=obj[3]%>)
											<% break;}}%><br>
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
										<td colspan="3"> 1)Above tour program may be approved and sanction an advance amount <%=tourdetails[17]%></td>
									</tr>
									<tr>
										<td colspan="3" > 2)Remarks, If any:  <%=tourdetails[21]%></td>
									</tr>
									
									<tr>
										<td colspan="3" style="text-align: right;"> (<%for(Object[] obj:TourStatisDetails){ 
												if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("ABP")){%>
											 <%=obj[2]%> (<%=obj[3]%>)
											<% break;}}%>)<br> Incharge - P & A</td>
									</tr>
									<%}%>
									<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABC") || tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
									<tr>
											<td colspan="3" style="text-align: center;"><b> SANCTIONED / NOT SANCTIONED <br>CEO</b><br> 
												<%for(Object[] obj:TourStatisDetails){ 
												if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("ABC")){%>
													 <%=obj[2]%> (<%=obj[3]%>)
												<% break;}}%>
											</td>
									</tr>	
								<%}%>
								</table>							
								</div>
							</div>
							<%if(tourdetails[17]!=null && tourdetails[17].toString().equalsIgnoreCase("Y")){%>
							<!-- TOUR Advance -->
							<table>
							<tr>
								<td style="text-align: center; border: 1px solid black; border-collapse: collapse;"> <b style="font-weight: 600;font-size: 1.5rem;">STARC</b> <br><b>BANGALORE</b></td>
								<td style="text-align: center; border: 1px solid black; border-collapse: collapse;"> <b>APPLICATION FOR ADVANCE OF TRAVELLING ALLOWANCE </b></td>
								<td style="text-align: center; border: 1px solid black; border-collapse: collapse;"><b>SITAR-BNG-P&A-001 <br> Date of issue :  </b></td>
								<td style="text-align: center; border: 1px solid black; border-collapse: collapse;"> <b>REV. : 02  issue : 01 <br> Page 1 of 2 </b></td>
							</tr>
							<tr>
								<td colspan="2" style=" border: 1px solid black; border-collapse: collapse;">&nbsp;  Name  :- &nbsp;&nbsp;<%=tourdetails[4]%>(<%=tourdetails[3]%>)</td>
								<td style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Emp No  :- &nbsp;&nbsp; <%=tourdetails[2]%></td>
								<td style=" border: 1px solid black; border-collapse: collapse;">&nbsp;  Grade   :-  &nbsp;&nbsp; <%=tourdetails[24]%></td>
							</tr>	
							<tr>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Basic Pay   :- <%=tourdetails[8]%></td>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Level in the Pay Matrix</td>
							</tr>	
							<tr>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Place of visit / Tour   :- <%=tourdetails[12]%></td>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Mobile No   :-  <%=tourdetails[6]%></td>
							</tr>	
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Movement Order Ref . No :- <%if(tourdetails[1]!=null){%>  <%=tourdetails[1]%> <%}else{%> ---<%}%></td>
							</tr>			
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Departure From 
								<% if(list!=null && list.size()>0){
										for(Object[] obj:list){%>
										<b style="margin-left: 155px;"><%=obj[2]%></b>  
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;On
										<b style="margin-left: 200px;"><%=DateTimeFormatUtil.fromDatabaseToActual(obj[0].toString())%></b>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 at	<b style="float: right;"><%=time.format(obj[1])%></b>
								<% break;}}%>
								
								</td>
							</tr>
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">
									<b style="float: right;">Rs.____________P. &nbsp;&nbsp;&nbsp;</b><br>
									<b style="padding-left: 40px;"> 1. Air / Train / &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=touradvancedetails[1] %></span> &nbsp;&nbsp;/ Bus fare(to & from)</b>
									<p style="padding-left: 55px;">(Single fare @ Rs. &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=touradvancedetails[1] %></span> &nbsp;&nbsp;)</p>
									<b style="padding-left: 40px;">2. Daily Allowance</b>
									<p style="padding-left: 55px;"> i) &nbsp;&nbsp;&nbsp;&nbsp;  Boarding Charges for  &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=AmountWordConveration.convert1(Long.parseLong(touradvancedetails[2].toString())) %></span> &nbsp;&nbsp; Days<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (@ Rs. &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=touradvancedetails[3] %></span> &nbsp;&nbsp;per Day)<br>
									ii)&nbsp;&nbsp;&nbsp;&nbsp;Daily Allowance at Rs. &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=touradvancedetails[4] %></span> &nbsp;&nbsp;per Day<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for  &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=AmountWordConveration.convert1(Long.parseLong( touradvancedetails[5].toString())) %></span> &nbsp;&nbsp;days from &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[6].toString())%></span> &nbsp;&nbsp; to  &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[7].toString()) %></span> &nbsp;&nbsp; </p>
									<b style="padding-left: 40px;"> 3.  Taxi / Auto / Bus fare from &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[8].toString())%></span> &nbsp;&nbsp; to  &nbsp;&nbsp;<span style="text-decoration:underline;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[9].toString()) %></span> &nbsp;&nbsp;</b><br>
									<b style="float: right;"> Total ______________ &nbsp;&nbsp;&nbsp;</b><br>
									
									<p style="margin-left: 10px;">I Undertake that:</p>
									  <p style="margin-left: 30px;">  1.  I have to render statement of expenses towards adjustment of the paid advance paid to me before  asking for payment of further advance and in any case within 15 days of completion of my tour.
									<br>2. I have to refund to the STARCany excess of TA advance amount immediately on completion of the tour.<br>
									    3. I have read / have been explained the TA rules applicable to me.</p><br><br>
									    
									<b style="float: left; margin-left: 10px;">Date: <%=DateTimeFormatUtil.fromDatabaseToActual( tourdetails[22].toString())%> </b><b style="float: right; margin-right: 10px;">Signature of the employee <br>Name : &nbsp;&nbsp;<%=tourdetails[4]%>(<%=tourdetails[3]%>)</b>	    
								
									
										<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABD") ||
											  	tourdetails[19].toString().equalsIgnoreCase("RBF") || tourdetails[19].toString().equalsIgnoreCase("ABF") || 
											    tourdetails[19].toString().equalsIgnoreCase("RBP") || tourdetails[19].toString().equalsIgnoreCase("ABP") ||
											    tourdetails[19].toString().equalsIgnoreCase("RBC") || tourdetails[19].toString().equalsIgnoreCase("ABC") ||
											    tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
											<%for(Object[] obj:TourStatisDetails){ 
													if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("ABD")){%>
											<hr style=" margin-top: 55px;">
											<b style="float: left; margin-left: 10px;">Forwarded to P & A Dept. for necessary action</b>	<br>
											<b style="float: left; margin-left: 10px;margin-top: 40px;"> Date :</b> 
											<b style="float: right; margin-top: 40px; margin-right: 145px;">Sign. of DGM <br> Name : 
												 <%=obj[2]%> (<%=obj[3]%>)
												 </b>
												<% break;}}%>
										<%}%>
									
									<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABC") || tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
									<hr style=" margin-top: 100px;">
									<b style="text-decoration: underline; margin-left: 386px; margin-bottom: 10px"> FOR ADMINISTATION USE ONLY </b>
									<p>An advance of Rs. .........................(Ruppes....................................only) May be released to Dr. / Mr. / Ms. .............................Emp No................ 
									</p>
									<b style="float: right; margin-right: 90px;">
									(
									<%for(Object[] obj:TourStatisDetails){ 
												if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("ABC")){%>
													 <%=obj[2]%> (<%=obj[3]%>)
									<% break;}}%>
									)<br>
									Incharge-P&A
									</b><br>
									<b>Forwarded to F&A Dept. for Necessary Action</b>
									<%}%>
								</td>
							</tr>
						</table>
						<%}%>
						<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("INI")){ %>
							<div align="center" style="margin: 10px;">
								<button type="submit" class="btn btn-sm submit-btn"  name="Action" value="ForwardTour/<%=tourdetails[0]%>"  data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>
							</div>
						<%}%>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>