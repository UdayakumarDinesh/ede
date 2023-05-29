<%@page import="java.util.stream.Collectors"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style type="text/css">
body{
  overflow-x: hidden;
  overflow-y: hidden;
}
</style>
<title>Tour Proposal Form</title>
<%
	String LabLogo = (String) request.getAttribute("LabLogo");

	Object[] tourdetails = (Object[]) request.getAttribute("tourdetails");
	Object[] touradvancedetails = (Object[])request.getAttribute("touradvancedetails");
	List<Object[]> list = (List<Object[]>)request.getAttribute("Touronwarddetails");
	Object[] ApprovalEmp=(Object[])request.getAttribute("ApprovalEmp"); 
	List<Object[]> TourStatisDetails = (List<Object[]>)request.getAttribute("tourstatisdetails");
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
	%>
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
				size: 790px 950px;
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

				@top-left {
					margin-top: 30px;
					margin-left: 10px;
					content: "Emp No: <%=tourdetails[2]%>";
					
				}

			}
body
{
	font-size:14px !important;
}


</style>
</head>
<body>
<div class="center">
								<table style="width:100%;margin-top:10px; border: 1px solid black; border-collapse: collapse; text-align: center;">
								
									<tr>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"> <b style="font-size: 2em;">STARC </b> <br> <b> BANGALORE </b></td>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;">
										<b>  PROPOSAL FOR TOUR <br>
											 PROGRAMME / REVISEDTOUR <br>
											 PROGRAMME </b> 
										</td>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"> <b>SITAR-BNG-P&A-037F <br> Date of Issue : <%=DateTimeFormatUtil.fromDatabaseToActual( LocalDate.now().toString())%> </b> </td>
									</tr>
								</table>
								
								<table style="border: 0px; width: 100%;margin-top: 5px; ">
									<tr>
										<td> <b>Department : </b>&nbsp; <span style="color: blue;"> <%=tourdetails[3]%> </span> </td>
										<td><b> Phone No : </b> &nbsp;  <span style="color: blue;"> <%=tourdetails[6]%> </span> </td>
										<td><b> Date : </b></td>
										
									</tr>
								</table>
								
								<div align="left">
								<table style="border: 0px; width: 100%;margin-top: 5px; ">
									<tr>
										<td style="text-align: left;">
										 The following tour program is proposed for the purpose of   <%=tourdetails[9]%>
										</td>
									</tr>
									<tr>	
										<td> <b> Brief details of the proposed tour program :</b></td>
									</tr>
									
								</table>
								
										<table style="width: 100%; margin-top: 10px; border: 1px solid black; border-collapse: collapse;">
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
										
								<table style="width: 100%;margin-top: 10px;">
									<tr>
										<th colspan="3" style="text-align: left;">1) Earliest presence required at destination</th>
									</tr>
									<tr style="text-align: center;">
										<td>a)Time :<span style="color: blue;"> <%=time.format(tourdetails[11])%> </span></td>										
										<td>Date : <span style="color: blue;"><%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[10].toString())%></span></td>
										<td>Place :<span style="color: blue;"><%=tourdetails[12]%></span></td>
									</tr>
									<tr >
											<th colspan="3" style="text-align: left;">2)Proposed journey details :</th>
										</tr>
								</table>
								
								<table style="width: 100%; margin-top: 5px; border: 1px solid black; border-collapse: collapse;">
									
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
								<table style="margin-top: 10px; width: 100%;">
									<tr>
										<td colspan="3" style="text-align: left;"><b>3)Period of stay :</b>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
										From : &nbsp;&nbsp; &nbsp;&nbsp; <span style="color: blue;"><%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[13].toString()) %> </span> &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										
										To: &nbsp;&nbsp; &nbsp;&nbsp; <span style="color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[14].toString()) %></span>
										</td>
									</tr>
									<tr>	
										<td style="text-align: left;"><b>4)Place of stay :</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<span style="color: blue;"> <%=tourdetails[15]%></span>
										 </td>
									</tr>
									<tr >
										<td colspan="3"> <b>5)Justification for Air Travel of non-entitled employee (if applicable):</b> &nbsp; &nbsp;&nbsp;<span style="color: blue;"><%if(tourdetails[16]!=null){ %> <%=tourdetails[16]%> <%}else{%> ---<%}%> </span></td>
									</tr>
									<tr >
										<td ><b>6)Amount of Travelling Advance proposed :&nbsp; &nbsp;&nbsp; </b> <span style="color: blue;"> <%if(tourdetails[17]!=null && tourdetails[17].toString().equalsIgnoreCase("Y")){%> Yes <%}else{%> No <%}%> </span></td>
									</tr>
									</table>
									
									<table style="margin-top: 6px; width: 100%;" >
									<tr style="margin-top: 20px;">
										<td colspan="2"> 
											Signature of the employee <br>
											Name : &nbsp;&nbsp; <span style="color: blue;"> <%=tourdetails[4]%>(<%=tourdetails[3]%>)</span>
										 </td>
										
										<td style="text-align: right; margin-left: 10px;">  
											<%if(Deptapprove!=null && Deptapprove.size()>0){%>
											Signature of Dept. Incharge <br>&nbsp;&nbsp;
												<span style="color: blue;"><%=Deptapprove.get(0)[2]%>(<%=Deptapprove.get(0)[3]%>) <br><%=Deptapprove.get(0)[5]%></span>
											 <%}%>
										</td>
									</tr >
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
										<th colspan="3" style="text-align: left;"> Availability of funds :</th>
									</tr>
									
									<tr>
										<td> &nbsp; &nbsp;&nbsp; &nbsp;Available :</td>
										<td colspan="2"> <span style="color: blue;"> <%if(tourdetails[20]!=null && tourdetails[20].toString().equalsIgnoreCase("Y")){%> Yes <%}%> </span></td>
									</tr>
									<tr>
										<td>&nbsp; &nbsp;&nbsp; &nbsp;Not Available :</td>
										<td colspan="2"> <span style="color: blue;"><%if(tourdetails[20]!=null && tourdetails[20].toString().equalsIgnoreCase("N")){%> No <%}%> </span></td>
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
											
							
							<%if(tourdetails[17]!=null && tourdetails[17].toString().equalsIgnoreCase("Y")){%>
							<div style="page-break-after: always;"></div>
							<!-- TOUR Advance -->
							<table style="width: 100%; margin-top: 5px; border: 1px solid black; border-collapse: collapse;">
							<tr>
								<td style="width:20%; text-align: center; border: 1px solid black; border-collapse: collapse;"> <b style="font-weight: 600;font-size: 1.5rem;">STARC</b> <br><b>BANGALORE</b></td>
								<td style="width:60%; text-align: center; border: 1px solid black; border-collapse: collapse;"> <b>APPLICATION FOR ADVANCE OF TRAVELLING ALLOWANCE </b></td>
								<td style="width:10%; text-align: center; border: 1px solid black; border-collapse: collapse;"><b>SITAR-BNG-P&A-001 <br> Date of issue :  </b></td>
								<td style="width:10%; text-align: center; border: 1px solid black; border-collapse: collapse;"> <b>REV. : 02  issue : 01 <br> Page 1 of 2 </b></td>
							</tr>
							<tr>
								<td colspan="2" style=" border: 1px solid black; border-collapse: collapse;">&nbsp;  Name  :- &nbsp;&nbsp;<span style="color: blue;"><%=tourdetails[4]%>(<%=tourdetails[3]%>)</span></td>
								<td style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Emp No  :- &nbsp;&nbsp;<span style="color: blue;"> <%=tourdetails[2]%></span></td>
								<td style=" border: 1px solid black; border-collapse: collapse;">&nbsp;  Grade   :-  &nbsp;&nbsp; <span style="color: blue;"><%=tourdetails[24]%></span></td>
							</tr>	
							<tr>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Basic Pay   :- <span style="color: blue;"><%=tourdetails[8]%></span></td>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Level in the Pay Matrix</td>
							</tr>	
							<tr>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Place of visit / Tour   :- <span style="color: blue;"><%=tourdetails[12]%></span></td>
								<td colspan="2" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Mobile No   :-  <span style="color: blue;"><%=tourdetails[6]%></span></td>
							</tr>	
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Movement Order Ref . No :- <span style="color: blue;"> <%if(tourdetails[1]!=null){%>  <%=tourdetails[1]%> <%}else{%> ---<%}%></span></td>
							</tr>			
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">  Departure From 
								 <span style="color: blue;">
								 <% if(list!=null && list.size()>0){
										for(Object[] obj:list){%>
									<b>	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=obj[2]%>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;On&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%=DateTimeFormatUtil.fromDatabaseToActual(obj[0].toString())%>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 	at &nbsp;&nbsp;&nbsp;<%=time.format(obj[1])%></b>
								<% break;}}%> 
								</span>
								</td>
							</tr>
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">
								 	<b style="padding-left: 490px; text-decoration: underline;">Rs. <span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[11]%> </span>  </b> &nbsp;&nbsp;&nbsp;<br>
									<b style="padding-left: 40px;"> 1. Air / Train / <span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[1] %></span> / Bus fare(to & from)</b>
									<p style="padding-left: 55px;">(Single fare @ Rs. <span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[1] %></span> )</p>
									<b style="padding-left: 40px;">2. Daily Allowance</b>
									<p style="padding-left: 55px;"> i) &nbsp;&nbsp;&nbsp;&nbsp;  Boarding Charges for  &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=AmountWordConveration.convert1(Long.parseLong(touradvancedetails[2].toString())) %></span> &nbsp;&nbsp; Days<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (@ Rs. &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[3] %>.00</span> &nbsp;&nbsp;per Day)<br>
									ii)&nbsp;&nbsp;&nbsp;&nbsp;Daily Allowance at Rs. &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=touradvancedetails[4] %>.00</span> &nbsp;&nbsp;per Day<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for  &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=AmountWordConveration.convert1(Long.parseLong( touradvancedetails[5].toString())) %></span> &nbsp;&nbsp;days from &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[6].toString())%></span> &nbsp;&nbsp; to  &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[7].toString()) %></span> &nbsp;&nbsp; </p>
									<b style="padding-left: 40px;"> 3.  Taxi / Auto / Bus fare from &nbsp;</b><span style="text-decoration:underline; color:blue; "> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[8].toString())%></span> &nbsp;&nbsp;<b> to</b> &nbsp;&nbsp;<span style="text-decoration:underline; color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( touradvancedetails[9].toString()) %></span> &nbsp;&nbsp;<br>
									<b style="padding-left: 490px; text-decoration: underline;"> Total <span style="text-decoration: underline; color: blue;"><%=totaladvance%>.00 </span> </b> &nbsp;&nbsp;&nbsp;<br>
									
									<p style="margin-left: 10px;">I Undertake that:</p>
									  <p style="margin-left: 30px;">  1.  I have to render statement of expenses towards adjustment of the paid advance paid to me before  asking for payment of further advance and in any case within 15 days of completion of my tour.
									<br>2. I have to refund to the STARCany excess of TA advance amount immediately on completion of the tour.<br>
									    3. I have read / have been explained the TA rules applicable to me.</p><br><br>
									    
									<b style=" margin-left: 10px;">Date: <span style="color: blue;"> <%=DateTimeFormatUtil.fromDatabaseToActual( tourdetails[22].toString())%> </span> </b><br><b style=" padding-left: 370px;">Signature of the employee <br>Name : &nbsp;&nbsp; <span style="color: blue;"> <%=tourdetails[4]%>(<%=tourdetails[3]%>) </span></b>	    	    
								
									<hr style=" margin-top: 35px;">
								
									<b style=" margin-left: 10px;">Forwarded to P & A Dept. for necessary action</b>	<br><br>
									<%if(dgmapprove!=null && dgmapprove.size()>0){ %>
									<b style=" margin-left: 10px;margin-top: 40px;"> Date : <span style="color: blue;"> <%=DateTimeFormatUtil.SqlDatetimeToRegularDatetime(dgmapprove.get(0)[5].toString())%></span></b> <b style=" padding-left: 450px;">Sign. of DGM <br><span style="color: blue; padding-left: 500px;"> <%=dgmapprove.get(0)[2]%>(<%=dgmapprove.get(0)[3]%>)</span> </b>
									<%}%>
									<%if(PAapprove!=null && PAapprove.size()>0){ %>
									
									<b style="margin-top: 200px; text-decoration: underline; margin-left: 200px; margin-bottom: 10px"> FOR ADMINISTATION USE ONLY </b>
									<p style="text-align: center;">An advance of Rs.<span style="color: blue;"><%=touradvancedetails[11]%> </span>(Ruppes <span style=" color: blue;"> <%=AmountWordConveration.convert1(new Double( touradvancedetails[11].toString()).longValue()) %> only</span>) 
									
									May be released to Dr. / Mr. / Ms. <span style="color: blue;"><%=tourdetails[4]%>(<%=tourdetails[3]%>)</span> Emp No  <span style="color: blue;"><%=tourdetails[2]%></span>
									
									</p>
									<b style=" margin-left: 430px; ">Incharge-P&A <br></b>
									  <span style="color: blue; margin-left: 425px;"><%=PAapprove.get(0)[2]%> (<%=PAapprove.get(0)[3]%>)</span> <br>
									  <span style="color: blue; margin-left: 425px;"><%=DateTimeFormatUtil.SqlDatetimeToRegularDatetime(PAapprove.get(0)[5].toString())%> </span><br>									 
									
									<b>Forwarded to F&A Dept. for Necessary Action</b>
									<%}%>
								</td>
							</tr>
						</table>
						
						<%}%>				
					</div>
				</div>
</body>
</html>