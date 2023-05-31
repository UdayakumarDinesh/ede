<%@page import="java.time.LocalDate"%>
<%@page import="java.util.stream.Collectors"%>
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
<style type="text/css">
 body{
  overflow-x: hidden;
  overflow-y: hidden;
} 
</style>
<title>Tour Proposal Form</title>

</head>
<body>

	<%	
	String empno = session.getAttribute("EmpNo").toString();
	String LabLogo = (String) request.getAttribute("LabLogo");

	Object[] tourdetails = (Object[]) request.getAttribute("tourdetails");
	Object[] touradvancedetails = (Object[])request.getAttribute("touradvancedetails");
	List<Object[]> list = (List<Object[]>)request.getAttribute("Touronwarddetails");
	List<Object[]> TourStatisDetails = (List<Object[]>)request.getAttribute("tourstatisdetails");
	List<Object[]> pandalist = (List<Object[]>)request.getAttribute("pandalist");
	Object[] divisiondgmpafadetails = (Object[]) request.getAttribute("DivisiondgmpafaDetails");
	
	SimpleDateFormat time = new SimpleDateFormat("HH:mm");
	long totaladvance=0l;
	if(touradvancedetails!=null){
		 int boadringdays = Integer.parseInt(touradvancedetails[2].toString());
		 int boardingperday = Integer.parseInt(touradvancedetails[3].toString());
		 int fareamout = new Double(touradvancedetails[1].toString()).intValue(); 
		 int allowanceperday = Integer.parseInt(touradvancedetails[4].toString());
		 int allowancedays = Integer.parseInt(touradvancedetails[5].toString());
		 int totalboardingcharges = boadringdays * boardingperday;
		 int totalallowance = allowanceperday * allowancedays;
		 totaladvance =  totalboardingcharges +  totalallowance + fareamout;
	}
	
	List<Object[]> Deptapprove = TourStatisDetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("VDH")).collect(Collectors.toList());      
	List<Object[]> dgmapprove  = TourStatisDetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("ABD")).collect(Collectors.toList());
	List<Object[]> FAapprove   = TourStatisDetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("ABF")).collect(Collectors.toList());
	List<Object[]> PAapprove   = TourStatisDetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("ABP")).collect(Collectors.toList());
	List<Object[]> CEOapprove  = TourStatisDetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("ABC")).collect(Collectors.toList());

	List<Object[]> PAIssueMoapprove  = TourStatisDetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("MOI")).collect(Collectors.toList());
	List<Object[]> FAAdvaapprove  = TourStatisDetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("ABC")).collect(Collectors.toList());

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
										<td> <b>Department : </b>&nbsp; <span style="color: blue;"><%=tourdetails[3]%></span></td>
										<td><b> Phone No : </b> &nbsp;  <span style="color: blue;"> <%=tourdetails[6]%></span> </td>
										<td><b> Date : </b> &nbsp;  <span style="color: blue;"><%=DateTimeFormatUtil.fromDatabaseToActual( LocalDate.now().toString())%></span></td>								
									</tr>
								</table>
								<br>
								<div align="left">
								<table>
									<tr>
									<td style="text-align: left;">
									<%if(tourdetails[26]!=null && Integer.parseInt(tourdetails[26].toString())>0 ){ %>
										
										The revised tour programme is proposed due to <%=tourdetails[9]%>
										<%}else{%>
										 The Following tour programme is proposed for the purpose of   <%=tourdetails[9]%>
										
										<%}%>
										</td>
									</tr>
									<tr>	
										<td> <b> Brief details of the proposed tour program :</b></td>
									</tr>
									
								</table>
								<div align="center"> 
									<table style="width: 90%; margin-top: 10px; margin-left: 10px;">
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
												<td style="border: 1px solid black; border-collapse: collapse; text-align: center; color: blue;"><%=tourdetails[4]%>(<%=tourdetails[3]%>)</td>
												<td style="border: 1px solid black; border-collapse: collapse; text-align: center; color: blue;"><%=tourdetails[2]%></td>
												<td style="border: 1px solid black; border-collapse: collapse; text-align: center; color: blue;"><%=DateTimeFormatUtil.fromDatabaseToActual( tourdetails[5].toString())%></td>
												<td style="border: 1px solid black; border-collapse: collapse; text-align: center; color: blue;"><%=tourdetails[8]%></td>
												<td style="border: 1px solid black; border-collapse: collapse; text-align: center; color: blue;"><%=tourdetails[6]%></td>
												<td style="border: 1px solid black; border-collapse: collapse; text-align: center; color: blue;"><%=tourdetails[7]%></td>
											</tr>
										</tbody>
									</table>
								</div>
								<table style="width: 100%;margin-top: 10px;">
									<tr>
										<th colspan="3">1) Earliest presence required at destination</th>
									</tr>
									<tr style="text-align: center;">
										<td>a)Time : <span style="color: blue;"><%=time.format(tourdetails[11])%> </span></td>
										<td>Date :   <span style="color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( tourdetails[10].toString())%> </span></td>
										<td>Place :  <span style="color: blue;"><%=tourdetails[12]%> </span></td>
									</tr>
									<tr >
											<th style="text-align: left;" colspan="3">2)Proposed journey details :</th>
									</tr>
								</table>
								<div align="center">
								<table style="width: 90%; margin-top: 10px;">
									<thead>
										
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
											<td style="border: 1px solid black; border-collapse: collapse; text-align: left;"><%=obj[2]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=DateTimeFormatUtil.fromDatabaseToActual(obj[0].toString())%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=time.format(obj[1])%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: left;"><%=obj[3]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[4]%></td>
										</tr>
										<%}}%>
									</tbody>
								</table>
								</div>
								
								<table style="margin-top: 10px;">
									<tr>
										<th>3)Period of stay :</th>
										<td>From : &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; <span style="color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[13].toString()) %> </span>&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</td>
										<td>To : &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;   <span style="color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[14].toString()) %> </span></td>
									</tr>
									<tr>	
										<th>4)Place of stay : </th>
										<td colspan="2"> <span style="color: blue;"> <%=tourdetails[15]%> </span></td>
									</tr>
									<tr>
										<td colspan="3"> <b>5)Justification for Air Travel of non-entitled employee (if applicable):</b> 
										<span style="color: blue;"> <%if(tourdetails[16]!=null){%> &nbsp; &nbsp;&nbsp;<%=tourdetails[16]%>  <%}else{%> -- <%}%> </span></td>
									</tr>
									<tr>
										<td ><b>6)Amount of Travelling Advance proposed :&nbsp; &nbsp;&nbsp; </b><span style="color: blue;"> <%if(tourdetails[17]!=null && tourdetails[17].toString().equalsIgnoreCase("Y")){%> Yes <%}else{%> No <%}%> </span> </td>
									</tr>
									</table>
									<table style="width: 100%; margin-top: 6px;">
									
									<tr>
										<td colspan="2"> 
											Signature of the employee <br>
											&nbsp; <span style="color: blue;"> <%=tourdetails[4]%>(<%=tourdetails[3]%>) </span>
										 </td>
										
										<td style="text-align: right;">  
										<%if(Deptapprove!=null && Deptapprove.size()>0){%>
											Signature of Dept. Incharge <br>
											 &nbsp;&nbsp;
											
												<span style="color: blue;"><%=Deptapprove.get(0)[2]%>(<%=Deptapprove.get(0)[3]%>) <br><%= DateTimeFormatUtil.SqlDatetimeToRegularDatetime(Deptapprove.get(0)[5].toString())%></span>
											 <%}%>
										</td>
									</tr>
									<%if(dgmapprove!=null && dgmapprove.size()>0){%>
									<tr>
										<td colspan="3">
										Signature of DGM<br>
										 &nbsp;<span style="color: blue;"> <%=dgmapprove.get(0)[2]%>(<%=dgmapprove.get(0)[3]%>) <br> <%=DateTimeFormatUtil.SqlDatetimeToRegularDatetime(dgmapprove.get(0)[5].toString()) %></span>
										</td>
									</tr>
									<%}%>
									<%if(FAapprove!=null && FAapprove.size()>0){ %>
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
											<span style="color: blue;"><%=FAapprove.get(0)[2]%> (<%=FAapprove.get(0)[3]%>) <br><%=DateTimeFormatUtil.SqlDatetimeToRegularDatetime( FAapprove.get(0)[5].toString())%> </span>
											<br>
											Incharge - F & A
										 </td>
									</tr>
									<%}%>
									<%if(PAapprove!=null && PAapprove.size()>0){ %>
									<tr>
										<td colspan="3" style="text-align:center;">
											<b style=" text-decoration: underline;">REMARKS OF ADMINISTRATION</b>
										</td>
									</tr>
									<tr>
										<td colspan="3"> 1)Above tour program may be approved and sanction an advance amount <span style="color: blue;"> <%if(touradvancedetails!=null){%><%=touradvancedetails[11]%> <%}%> </span></td>
									</tr>
									<tr>
										<td colspan="3" > 2)Remarks, If any: <span style="color: blue;"><%=tourdetails[21]%> </span></td>
									</tr>
									
									<tr>
										<td colspan="3" style="text-align: right;"> <span style="color: blue;"> <%=PAapprove.get(0)[2]%>(<%=PAapprove.get(0)[3]%>)<br> <%=DateTimeFormatUtil.SqlDatetimeToRegularDatetime(PAapprove.get(0)[5].toString()) %> </span>
											<br> Incharge - P & A</td>
									</tr>
									<%}%>
									<%if(CEOapprove!=null && CEOapprove.size()>0){%>
									<tr>
											<td colspan="3" style="text-align: center;"><b> SANCTIONED / NOT SANCTIONED <br>CEO</b><br> 
											<span style="color: blue;"> <%=CEOapprove.get(0)[2]%> (<%=CEOapprove.get(0)[3]%>) <br><%=DateTimeFormatUtil.SqlDatetimeToRegularDatetime(CEOapprove.get(0)[5].toString())%> </span>	
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
								<td colspan="2" style=" border: 1px solid black; border-collapse: collapse;">&nbsp;  Name  :- &nbsp;&nbsp;<span style="color: blue;"> <%=tourdetails[4]%>(<%=tourdetails[3]%>) </span></td>
								<td style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Emp No  :- &nbsp;&nbsp; <span style="color: blue;"> <%=tourdetails[2]%> </span></td>
								<td style=" border: 1px solid black; border-collapse: collapse;">&nbsp;  Grade   :-  &nbsp;&nbsp; <span style="color: blue;"> <%=tourdetails[24]%> </span></td>
							</tr>	
							<tr>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Basic Pay   :- <span style="color: blue;"> <%=tourdetails[8]%> </span></td>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Level in the Pay Matrix</td>
							</tr>	
							<tr>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Place of visit / Tour   :- <span style="color: blue;"> <%=tourdetails[12]%> </span></td>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Mobile No   :- <span style="color: blue;"> <%=tourdetails[6]%> </span></td>
							</tr>	
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Movement Order Ref . No :- <span style="color: blue;"> <%if(tourdetails[1]!=null){%>  <%=tourdetails[1]%> <%}else{%> ---<%}%> </span> </td>
							</tr>			
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Departure From 
								<span style="color: blue;">
								<% if(list!=null && list.size()>0){
										for(Object[] obj:list){%>
										<b style="margin-left: 155px;"><%=obj[2]%></b>  
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;On
										<b style="margin-left: 200px;"><%=DateTimeFormatUtil.fromDatabaseToActual(obj[0].toString())%></b>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 at	<b style="float: right;"><%=time.format(obj[1])%></b>
								<% break;}}%>
								</span>
								</td>
							</tr>
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">
									<b style="float: right; text-decoration: underline;">Rs. <span style="color: blue; text-decoration: underline;"> <%=touradvancedetails[11]%> </span></b> &nbsp;&nbsp;&nbsp;<br>
									<b style="padding-left: 40px;"> 1. Air / Train / &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[1] %></span> &nbsp;&nbsp;/ Bus fare(to & from)</b>
									<p style="padding-left: 55px;">(Single fare @ Rs. &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[1] %></span> &nbsp;&nbsp;)</p>
									<b style="padding-left: 40px;">2. Daily Allowance</b>
									<p style="padding-left: 55px;"> i) &nbsp;&nbsp;&nbsp;&nbsp;  Boarding Charges for  &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=AmountWordConveration.convert1(Long.parseLong(touradvancedetails[2].toString())) %></span> &nbsp;&nbsp; Days<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (@ Rs. &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[3] %>.00</span> &nbsp;&nbsp;per Day)<br>
									ii)&nbsp;&nbsp;&nbsp;&nbsp;Daily Allowance at Rs. &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[4] %>.00</span> &nbsp;&nbsp;per Day<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for  &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=AmountWordConveration.convert1(Long.parseLong( touradvancedetails[5].toString())) %></span> &nbsp;&nbsp;days from &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[6].toString())%></span> &nbsp;&nbsp; to  &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[7].toString()) %></span> &nbsp;&nbsp; </p>
									<b style="padding-left: 40px;"> 3.  Taxi / Auto / Bus fare from &nbsp;&nbsp; </b><span style="text-decoration:underline; color:blue; "> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[8].toString())%></span> &nbsp;&nbsp; <b>to</b>  &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[9].toString()) %></span> &nbsp;&nbsp;<br>
									<b style="float: right; text-decoration: underline;"> Total <span style="color: blue;text-decoration: underline;"> <%=totaladvance%>.00</span> </b> &nbsp;&nbsp;&nbsp;<br>
									
									<p style="margin-left: 10px;">I Undertake that:</p>
									  <p style="margin-left: 30px;">  1.  I have to render statement of expenses towards adjustment of the paid advance paid to me before  asking for payment of further advance and in any case within 15 days of completion of my tour.
									<br>2. I have to refund to the STARCany excess of TA advance amount immediately on completion of the tour.<br>
									    3. I have read / have been explained the TA rules applicable to me.</p><br><br>
									    
									<b style="float: left; margin-left: 10px;">Date: <span style="color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( tourdetails[22].toString())%> </span> </b><b style="float: right; margin-right: 10px;">Signature of the employee <br> &nbsp;&nbsp; <span style="color: blue;"> <%=tourdetails[4]%>(<%=tourdetails[3]%>) </span></b>	    
								
									
										
											<!-- <hr style=" margin-top: 55px;"> -->
											<b style="float: left; margin-left: 10px;">Forwarded to P & A Dept. for necessary action</b>	<br>
											<%if(dgmapprove!=null && dgmapprove.size()>0){ %>
											<b style="float: left; margin-left: 10px;margin-top: 40px;"> Date : <span style="color: blue;"> <%=DateTimeFormatUtil.SqlDatetimeToRegularDatetime(dgmapprove.get(0)[5].toString())%></span> </b> 
											<b style="float: right; margin-top: 40px; margin-right: -100px;">
											Sign. of DGM <br> <span style="color: blue;"> <%=dgmapprove.get(0)[2]%>(<%=dgmapprove.get(0)[3]%>) </span>
											</b>
												<%} %>
										
									<%if(PAapprove!=null && PAapprove.size()>0){ %>
									<hr style=" margin-top: 100px;">
									<b style="text-decoration: underline; margin-left: 386px; margin-bottom: 10px"> FOR ADMINISTATION USE ONLY </b>
									<p style="text-align: center;">An advance of Rs.<span style="color: blue;"><%=touradvancedetails[11]%> </span>(Ruppes <span style=" color: blue;"> <%=AmountWordConveration.convert1(new Double( touradvancedetails[11].toString()).longValue()) %> only</span>) 
									
									May be released to Dr. / Mr. / Ms. <span style="color: blue;"><%=tourdetails[4]%>(<%=tourdetails[3]%>)</span> Emp No  <span style="color: blue;"><%=tourdetails[2]%></span>
									
									</p>
									<b style="float: right; margin-right: 90px; ">Incharge-P&A <br>
									<span style="color: blue;">    <%=PAapprove.get(0)[2]%> (<%=PAapprove.get(0)[3]%>)<br><%=DateTimeFormatUtil.SqlDatetimeToRegularDatetime(PAapprove.get(0)[5].toString())%><br></span>
									 
									</b>
									<b>Forwarded to F&A Dept. for Necessary Action</b>
									<%}}%>
								</td>
							</tr>
						</table>
						
						<%-- <%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("INI") || tourdetails[19].toString().equalsIgnoreCase("RDH") || tourdetails[19].toString().equalsIgnoreCase("RDG") || tourdetails[19].toString().equalsIgnoreCase("RBF") || tourdetails[19].toString().equalsIgnoreCase("RBP")){%>
							<div class="card">  
								<div class="card-body">
								<div align="center" style="margin: 10px;">
								   <form action="TourApplyList.htm" method="post">
									<div class="row">
										<div class="col-md-6"></div>
										<div class="col-md-5">
										    <div class=" input-group">
												<textarea   name="rejmarks"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>												                         
											</div>
										</div>
									</div>
									<div style="margin-top: 10px;" align="center">
										<button style="margin-bottom: -10px; margin-left: 400px;" type="submit" class="btn btn-sm submit-btn"  name="Action" value="ForwardTour/<%=tourdetails[0]%>"  data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>										
									</div>
																	
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>								
									</form>
								</div>
							 </div>	
							</div>
						<%}%> 
						
						
						 <%if( !tourdetails[2].toString().equalsIgnoreCase(empno) && tourdetails[19]!=null && (tourdetails[19].toString().equalsIgnoreCase("FWD") || tourdetails[19].toString().equalsIgnoreCase("VDH") || tourdetails[19].toString().equalsIgnoreCase("ABF") || tourdetails[19].toString().equalsIgnoreCase("ABP"))){%>
						
						<div class="card">
						  <div class="card-body">  
								<div align="center" style="margin: 10px;">
								   <form action="DeptInchApproval.htm" method="post">
									<div class="row">
										<div class="col-md-6"></div>
										<div class="col-md-5">
										    <div class=" input-group">
												<textarea   name="<%=tourdetails[0]%>"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>												                         
											</div>
										</div>
									</div> 
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>								
									<div style="margin-top: 10px;" align="center">
										<%if(tourdetails[19].toString().equalsIgnoreCase("ABP")){%>
										
											<button style="margin-bottom: -10px; margin-left: 400px;" type="submit" class="btn btn-sm submit-btn"  name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  data-toggle="tooltip" data-placement="top" title="Forward"> Verify</button>
											<button style="margin-bottom: -10px; background-color: maroon;" type="submit" class="btn btn-sm submit-btn"  name="reject" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  data-toggle="tooltip" data-placement="top" title="Return"> DisApprove </button>												
																					
										<%}else{%>
										
											<button style="margin-bottom: -10px; margin-left: 400px;" type="submit" class="btn btn-sm submit-btn"  name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>										
											<button style="margin-bottom: -10px; background-color: maroon;" type="submit" class="btn btn-sm submit-btn"  name="reject" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  data-toggle="tooltip" data-placement="top" title="Return"> Return </button>											
										
										<%}%>
									</div>		
									</form>
								</div>
							 </div>
							</div> 	
						<%}%> --%>
						
						<%-- <%if(!tourdetails[2].toString().equalsIgnoreCase(empno) && tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABD")){%>

						<div class="card">
						   <div class="card-body">  
							<div align="center" style="margin: 10px;">
							   <form action="DeptInchApproval.htm" method="post">
								<div class="row">
									<div class="col-md-6"></div>
									<div class="col-md-2" style="margin-top: -10px;">
										<div class=" input-group">
											<label style="font-weight: 600;">Is Fund Available</label>
											<input name="Funds<%=tourdetails[0]%>" id="funds<%=tourdetails[0]%>" value="false" onchange="funds('<%=tourdetails[0]%>', this)" type="checkbox" data-toggle="toggle" data-style="ios" data-onstyle="primary" data-offstyle="danger" data-width="105" data-height="15" data-on="<i class='fa fa-check' aria-hidden='true'></i> Yes" data-off="<i class='fa fa-times' aria-hidden='true'></i> No" >
											
										</div>
									</div>
									<div class="col-md-4">
									    <div class=" input-group">
										</div>
									</div>
								</div> 
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>								
								<div style="margin-top: 10px;" align="center"  >
									<button style="margin-bottom: -10px; margin-left: 400px;" type="submit" class="btn btn-sm submit-btn"  name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>										
									<button style="margin-bottom: -10px; background-color: maroon;" type="submit" class="btn btn-sm submit-btn"  name="reject" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  data-toggle="tooltip" data-placement="top" title="Return"> Return </button>												
								</div>		
								</form>
						  </div>
						 </div> <!-- Card Body Close -->
						</div><!-- Card Close -->
						<%}%> --%>
						
						
						
						
						<div class="card">
						   <div class="card-body">  
							<div align="center" style="margin: 10px;">
								<div class="row">
									<div class="col-md-6" align="left" style="margin-top:1rem; padding:0px;border: 1px solid #992929;border-radius: 5px;">			
											
										<table style="margin: 3px;padding: 0px;">
										    <tr>
												<td style="border:none;padding: 0px">
													<h6 class="text-blue"style="text-decoration: underline; color: red;">Remarks :</h6> 
												</td>											
											</tr>
										   <%if(TourStatisDetails!=null && TourStatisDetails.size()>0){
											for(Object[] obj : TourStatisDetails){if(obj[9]!=null && !obj[9].toString().equalsIgnoreCase("INI")){ %>
											<tr>
												<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
													<%=obj[2]%><%-- (<%=obj[3]%>) --%>&nbsp;  : &nbsp; <span style="color: blue;"> <%if(obj[6]!=null){%> <%=obj[6]%> <%}else{%> -- <%}%> </span>
												</td>
											</tr>
										  <% }}}%>
										</table>
									</div>
									<div class="col-md-6" style="margin-top: -10px;">
									
						<%if(tourdetails[19]!=null &&
						(divisiondgmpafadetails[0].toString().equalsIgnoreCase(empno) && tourdetails[19].toString().equalsIgnoreCase("FWD")) ||							
						(divisiondgmpafadetails[1].toString().equalsIgnoreCase(empno) && tourdetails[19].toString().equalsIgnoreCase("VDH"))  ||				
						(divisiondgmpafadetails[3].toString().equalsIgnoreCase(empno) && tourdetails[19].toString().equalsIgnoreCase("ABF")) ||
						(!tourdetails[2].toString().equalsIgnoreCase(empno) && tourdetails[19].toString().equalsIgnoreCase("ABP"))){%>			
						 	 <form action="DeptInchApproval.htm" method="post">
						  			 <div class="row" style="margin-top: 10px;">
										<div class="col-md-4">
											    <label style="font-weight: 600;"> Remarks  :</label>
										</div>
										<div class="col-md-6">
												<textarea   name="<%=tourdetails[0]%>"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>												                         
										</div>
									  </div>
						  			 <div class="row" style="margin-top: 10px;">
											<div class="col-md-5">  <input type="hidden" name="empno"  value="<%=tourdetails[2]%>"></div>
												<%if(tourdetails[19].toString().equalsIgnoreCase("ABP")){%>
												<div class="col-md-2">
													<button style="margin-bottom: -10px; " type="submit" class="btn btn-sm submit-btn"  name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>" onclick="return confirm('Are you sure to Submit?')" data-toggle="tooltip" data-placement="top" title="Forward"> Approve</button>
												</div>
												<div class="col-md-2">
													<button style="margin-bottom: -10px; background-color: maroon;" type="submit" class="btn btn-sm submit-btn"  name="reject" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>" onclick="return confirm('Are you sure to Return?')" data-toggle="tooltip" data-placement="top" title="Return"> DisApprove </button>																								
												</div>
												<%}else{%>
												<div class="col-md-2">
													<button style="margin-bottom: -10px; " type="submit" class="btn btn-sm submit-btn"  name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>" onclick="return confirm('Are you sure to Forward?')"  data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>										
												</div>	
												<div class="col-md-2">
													<button style="margin-bottom: -10px; background-color: maroon;" type="submit" class="btn btn-sm submit-btn"  name="reject" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>" onclick="return confirm('Are you sure to Return?')"  data-toggle="tooltip" data-placement="top" title="Return"> Return </button>																					
												</div>
												<%}%>
											
									   </div>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>										
									</form>	
						 	
						 		
						<%}%>	
							
							
							
							
						
						<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("INI") || tourdetails[19].toString().equalsIgnoreCase("RDH") || tourdetails[19].toString().equalsIgnoreCase("RDG") || tourdetails[19].toString().equalsIgnoreCase("RBF") || tourdetails[19].toString().equalsIgnoreCase("RBP")){%>
							<!-- initialy forward and returned Forward -->
							<form action="TourApplyList.htm" method="post">
								    <div class="row" style="margin-top: 10px;">
										<div class="col-md-4">
											    <label style="font-weight: 600;"> Remarks  :</label>
										</div>
										<div class="col-md-6">
												<textarea   name="rejmarks"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>												                         
										</div>
									  </div>
									  <div class="row" style="margin-top: 10px;">
											<div class="col-md-5"> <input type="hidden" name="empno"  value="<%=tourdetails[2]%>"></div>
											 <div class="col-md-3">
												<button style="margin-bottom: -10px; " type="submit" class="btn btn-sm submit-btn"  name="Action" value="ForwardTour/<%=tourdetails[0]%>" onclick="return confirm('Are you sure to Forward?')" data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>										
											 </div>
									   </div>						
							          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>								
							</form>
						<%}%>

						
						
						<%if(divisiondgmpafadetails[2].toString().equalsIgnoreCase(empno) && tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABD")){%>
									
						 <form action="DeptInchApproval.htm" method="post">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>							   
							<div class="form-group">
									 <div class="row">
										<div class="col-md-4">
												<label style="font-weight: 600;">Is Fund Available</label>
										</div>
										<div class="col-md-8">
												<input name="Funds<%=tourdetails[0]%>" id="funds<%=tourdetails[0]%>" value="false" onchange="funds('<%=tourdetails[0]%>', this)" type="checkbox" data-toggle="toggle" data-style="ios" data-onstyle="primary" data-offstyle="danger" data-width="105" data-height="15" data-on="<i class='fa fa-check' aria-hidden='true'></i> Yes" data-off="<i class='fa fa-times' aria-hidden='true'></i> No" >
										</div>
								      </div>	
								      <div class="row" style="margin-top: 10px;">
										<div class="col-md-4">
											    <label style="font-weight: 600;"> Remarks  :</label>
										</div>
										<div class="col-md-6">
											<textarea   name="<%=tourdetails[0]%>"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>												                         
										</div>
									  </div>
									  
									  <div class="row" style="margin-top: 10px;">
											  <div class="col-md-5"><input type="hidden" name="empno"  value="<%=tourdetails[2]%>"></div>
											  <div class="col-md-2">
											    <button style="margin-bottom: -10px; " type="submit" class="btn btn-sm submit-btn"  name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  onclick="return confirm('Are you sure to Forward?')" data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>										
											  </div>
											  <div class="col-md-2">
												<button style="margin-bottom: -10px; background-color: maroon;" type="submit" class="btn btn-sm submit-btn"  name="reject" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  onclick="return confirm('Are you sure to Return?')" data-toggle="tooltip" data-placement="top" title="Return"> Return </button>
											  </div>
									  </div>
							</div>			
						</form>
						<%}%>	
									
							<%if(divisiondgmpafadetails[3].toString().equalsIgnoreCase(empno) && tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABC")){%>								
									 <form action="IssueMOFromPA.htm" method="post">
							   			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>							   
								
									    <div class="form-group">
												<div class="row">
													<div class="col-md-4">
														<label>Issued By :<span class="mandatory">*</span></label>
													</div>
													<div class="col-md-8">
														<select class="form-control input-sm selectpicker" name="issuedby" required="required" data-live-search="true">
															<%for(Object[] emp: pandalist){ %>
																<option value="<%=emp[1]%>" ><%=emp[2]%>(<%=emp[3]%>)</option>
						                        			<%}%>
						                        		 </select>
												 	</div>
											    </div>
											    
												<div class="row" style="margin-top: 10px;">
													<div class="col-md-4">
														<label>Issued On :<span class="mandatory">*</span></label>
													</div>
													<div class="col-md-5">
														<div class=" input-group">
															<input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="issueddate" name="issueddate"  required="required"  > 
															<label class="input-group-addon btn" for="testdate"></label>                    
														</div>
												 	</div>
												 	<div class="col-md-2"></div>
											    </div>
											    
											    <div class="row" style="margin-top: 10px;">
											    	<div class="col-md-4">
											    		<label> Remarks  :</label>
											    	 </div>
											    	 <div class="col-md-6">
											    	 	<textarea style="width: 140%;" name="remarks"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>
											    	 </div>
											    </div>
											    
											    <div class="row" style="margin-top: 10px;">
											    	<div class="col-md-5"></div>
											    	<div class="col-md-3">
											    		  <%if(tourdetails[26]!=null && Integer.parseInt(tourdetails[26].toString())>0 ){ %>
												    	  <input type="text" name="tourpreviousid" value="tourdetails[26]">
												    	  <%}%>
											    		  <input type="hidden" name="empno"  value="<%=tourdetails[2]%>">		    	
											    		  <input type="hidden" name="tourapply" id="TOURAPPLY" value="<%=tourdetails[0]%>">
							      						  <button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are you sure to Submit?')">Submit</button>
											    	</div>
											    </div>
											</div>
									</form>
									<%}%>
									
									<%if(!tourdetails[2].toString().equalsIgnoreCase(empno) && tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("MOI")){%>
										<form action="TourAdvanceRelsed.htm" method="post">
										<div class="form-group">
												<div class="row" style="margin-top: 10px;">
													<div class="col-md-4">
														<label style="font-weight: 600;">Tour Advance :<span class="mandatory">*</span></label>
													</div>
													<div class="col-md-5">
														<div class=" input-group">
															<input type="text" class="form-control input-sm "  placeholder=" Enter Tour Advance ..." id="tourAdvance"   name="tourAdvance"  required="required"  > 
														</div>
												 	</div>
												 	<div class="col-md-2"></div>
											    </div>
											    
											    <div class="row" style="margin-top: 10px;">
											    	<div class="col-md-4">
											    		<label style="margin-left: -30px; font-weight: 600;"> Remarks  :</label>
											    	 </div>
											    	 <div class="col-md-6">
											    	 	<textarea style="width: 140%;" name="remarks"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>
											    	 </div>
											    </div>
											    
											    <div class="row" style="margin-top: 10px;">
											    	<div class="col-md-5"></div>
											    	<div class="col-md-3">
											    		<%if(tourdetails[26]!=null && Integer.parseInt(tourdetails[26].toString())>0 ){ %>
											    		<input type="text" name="tourpreviousid" value="tourdetails[26]">
											    		<%}%>
											    		<input type="hidden" name="tourapply" id="TOURAPPLY" value="<%=tourdetails[0]%>">
											    		<input type="hidden" name="empno"  value="<%=tourdetails[2]%>">
														<button style="margin-bottom: -10px; " type="submit" class="btn btn-sm submit-btn"  onclick="return confirm('Are you sure to Submit?')" name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  data-toggle="tooltip" data-placement="top" title="Approve"> Approve</button>											    	</div>
											    </div>
										</div>
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	
						  			 </form>
									<%}%>
									</div>
								</div> 
								
						  </div>
						 </div> <!-- Card Body Close -->
						</div><!-- Card Close -->
						
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
setPatternFilter($("#tourAdvance"), /^-?\d*$/);
function setPatternFilter(obj, pattern) {
	  setInputFilter(obj, function(value) { return pattern.test(value); });
	}

function setInputFilter(obj, inputFilter) {
	  obj.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
	    if (inputFilter(this.value)) {
	      this.oldValue = this.value;
	      this.oldSelectionStart = this.selectionStart;
	      this.oldSelectionEnd = this.selectionEnd;
	    } else if (this.hasOwnProperty("oldValue")) {
	      this.value = this.oldValue;
	      this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	    }
	  });
	}


	function funds(val, val1){
		$("#funds"+val).val($(val1).prop('checked'));
	}
	
	$('#issueddate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		//"minDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
</script>
</html>