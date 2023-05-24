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

<title>Tour Proposal Form</title>
<%
	String LabLogo = (String) request.getAttribute("LabLogo");

	Object[] tourdetails = (Object[]) request.getAttribute("tourdetails");
	List<Object[]> list = (List<Object[]>)request.getAttribute("Touronwarddetails");
	Object[] ApprovalEmp=(Object[])request.getAttribute("ApprovalEmp"); 
	SimpleDateFormat time = new SimpleDateFormat("HH:mm");
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
	 <jsp:include page="../static/LetterHead.jsp"></jsp:include>

<div class="center">
								<table style="width:100%;margin-top:30px; border: 1px solid black; border-collapse: collapse; text-align: center;">
								
									<tr>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"> <b style="font-size: 2em;">STARC </b> <br> <b> BANGALORE </b></td>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;">
										<b>  PROPOSAL FOR TOUR <br>
											 PROGRAMME / REVISEDTOUR <br>
											 PROGRAMME </b> 
										</td>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"> <b>SITAR-BNG-P&A-037F <br> Date of Issue : <%=DateTimeFormatUtil.fromDatabaseToActual( LocalDate.now().toString())%> </b> </td>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><b>  Rev : 04 </b> </td>
									</tr>
								</table>
								
								<table style="border: 0px; width: 100%;margin-top: 5px; ">
									<tr>
										<td> <b>Department : </b>&nbsp; <%=tourdetails[3]%></td>
										<td><b> Phone No : </b> &nbsp; <%=tourdetails[6]%></td>
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
										<th colspan="3" style="text-align: left;">1) Earliest presence required at destination</th>
									</tr>
									<tr style="text-align: center;">
										<td>a)Time : <%=time.format(tourdetails[11])%></td>										
										<td>Date : <%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[10].toString())%></td>
										<td>Place :<%=tourdetails[12]%></td>
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
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[2]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=DateTimeFormatUtil.fromDatabaseToActual(obj[0].toString())%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=time.format(obj[1])%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[3]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[4]%></td>
										</tr>
										<%}}%>
									</tbody>
								</table>
								
								<table style="margin-top: 10px; width: 100%;">
									<tr>
										<td colspan="3" style="text-align: left;"><b>3)Period of stay :</b>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
										From : &nbsp;&nbsp; &nbsp;&nbsp; <%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[13].toString()) %>&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										
										To: &nbsp;&nbsp; &nbsp;&nbsp;<%=DateTimeFormatUtil.fromDatabaseToActual(tourdetails[14].toString()) %>
										</td>
									</tr>
									<tr>	
										<td style="text-align: left;"><b>4)Place of stay :</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										 <%=tourdetails[15]%>
										 </td>
									</tr>
									<tr >
										<td colspan="3"> <b>5)Justification for Air Travel of non-entitled employee (if applicable):</b> &nbsp; &nbsp;&nbsp;<%if(tourdetails[16]!=null){ %> <%=tourdetails[16]%> <%}else{%> ---<%}%></td>
									</tr>
									<tr >
										<td ><b>6)Amount of Travelling Advance proposed :&nbsp; &nbsp;&nbsp; </b><%if(tourdetails[17]!=null && tourdetails[17].toString().equalsIgnoreCase("Y")){%> Yes <%}else{%> No <%}%></td>
									</tr>
									</table>
									
									<table style="margin-top: 6px; width: 100%;" >
									<tr style="margin-top: 20px;">
										<td colspan="2"> 
											Signature of the employee <br>
											Name : &nbsp;&nbsp;<%=tourdetails[4]%>(<%=tourdetails[3]%>)
										 </td>
										
										<td style="text-align: right; margin-left: 10px;">  
											Signature of Dept. Incharge <br>
											Name : &nbsp;&nbsp;<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("VDH") || 
											tourdetails[19].toString().equalsIgnoreCase("ABD") || tourdetails[19].toString().equalsIgnoreCase("RDG") ||
										  	tourdetails[19].toString().equalsIgnoreCase("RBF") || tourdetails[19].toString().equalsIgnoreCase("ABF") || 
										    tourdetails[19].toString().equalsIgnoreCase("RBP") || tourdetails[19].toString().equalsIgnoreCase("ABP") ||
										    tourdetails[19].toString().equalsIgnoreCase("RBC") || tourdetails[19].toString().equalsIgnoreCase("ABC") ||
										    tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
											 <%=ApprovalEmp[0]%>
											<%}%>
										</td>
									</tr >
									<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABD") ||
										  	tourdetails[19].toString().equalsIgnoreCase("RBF") || tourdetails[19].toString().equalsIgnoreCase("ABF") || 
										    tourdetails[19].toString().equalsIgnoreCase("RBP") || tourdetails[19].toString().equalsIgnoreCase("ABP") ||
										    tourdetails[19].toString().equalsIgnoreCase("RBC") || tourdetails[19].toString().equalsIgnoreCase("ABC") ||
										    tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
									<tr class="break" style="margin-top: 40px;">
										<td colspan="3">
										Signature of DGM<br>
										Name : &nbsp;&nbsp;<%=ApprovalEmp[1] %><br>
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
										<td colspan="2"> <%if(tourdetails[20]!=null && tourdetails[20].toString().equalsIgnoreCase("Y")){%> <b style="color: green;"> Yes</b> <%}%></td>
									</tr>
									<tr>
										<td>&nbsp; &nbsp;&nbsp; &nbsp;Not Available :</td>
										<td colspan="2"> <%if(tourdetails[20]!=null && tourdetails[20].toString().equalsIgnoreCase("N")){%> <b style="color: Red ;"> No</b> <%}%></td>
									</tr>
									
									<tr> 
										<td colspan="3" style="text-align: right;">
											(<%=ApprovalEmp[2] %>)<br>
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
										<td colspan="3"> 1)Above tour program may be approved and sanction an advance amount Rs._______________________</td>
									</tr>
									<tr>
										<td colspan="3" > 2)Remarks, If any:</td>
									</tr>
									<tr >
										<td colspan="3" style="text-align: right;"> (<%=ApprovalEmp[3]%>)<br> Incharge - P & A</td>
									</tr>
									<%}%>
								<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("ABC") || tourdetails[19].toString().equalsIgnoreCase("REV") ){%>
									<tr>
											<td colspan="3" style="text-align: center;"><b> SANCTIONED / NOT SANCTIONED <br>CEO</b><br> <%=ApprovalEmp[4]%></td>
									</tr>	
								<%}%>
								</table>
											
							
							<%if(tourdetails[17]!=null && tourdetails[17].toString().equalsIgnoreCase("Y")){%>
							
							<!-- TOUR Advance -->
							<table style="width: 100%; margin-top: 5px; border: 1px solid black; border-collapse: collapse;">
							<tr>
								<td style="width:20%; text-align: center; border: 1px solid black; border-collapse: collapse;"> <b style="font-weight: 600;font-size: 1.5rem;">STARC</b> <br><b>BANGALORE</b></td>
								<td style="width:60%; text-align: center; border: 1px solid black; border-collapse: collapse;"> <b>APPLICATION FOR ADVANCE OF TRAVELLING ALLOWANCE </b></td>
								<td style="width:10%; text-align: center; border: 1px solid black; border-collapse: collapse;"><b>SITAR-BNG-P&A-001 <br> Date of issue :  </b></td>
								<td style="width:10%; text-align: center; border: 1px solid black; border-collapse: collapse;"> <b>REV. : 02  issue : 01 <br> Page 1 of 2 </b></td>
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
									<b>	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=obj[2]%>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;On&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%=DateTimeFormatUtil.fromDatabaseToActual(obj[0].toString())%>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 	at &nbsp;&nbsp;&nbsp;<%=time.format(obj[1])%></b>
								<% break;}}%> 
								
								</td>
							</tr>
							<tr>
								<td colspan="4" style="padding: 6px; border: 1px solid black; border-collapse: collapse;">
								 	<b style="padding-left: 500px;">Rs.____________P. &nbsp;&nbsp;&nbsp;</b><br>
							
									<b style="padding-left: 40px;"> 1. Air / Train /...................................../ Bus fare(to & from)</b>
									<p style="padding-left: 55px;">(Single fare @ Rs. .............................................)</p>
									<b style="padding-left: 40px;">2. Daily Allowance</b>
									<p style="padding-left: 55px;"> i) &nbsp;&nbsp;&nbsp;&nbsp;  Boarding Charges for .......................Days<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (@ Rs. ..........................per Day)<br>
									ii)&nbsp;&nbsp;&nbsp;&nbsp;Daily Allowance at Rs. ......................per Day<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for .............days from ...................to ..............</p>
									<b style="padding-left: 40px;"> 3.  Taxi / Auto / Bus fare from ............. to ................</b><br>
									<b style="padding-left: 500px;"> Total ______________ &nbsp;&nbsp;&nbsp;</b><br>
									
									<p style="margin-left: 10px;">I Undertake that:</p>
									  <p style="margin-left: 30px;">  1.  I have to render statement of expenses towards adjustment of the paid advance paid to me before  asking for payment of further advance and in any case within 15 days of completion of my tour.
									<br>2. I have to refund to the STARCany excess of TA advance amount immediately on completion of the tour.<br>
									    3. I have read / have been explained the TA rules applicable to me.</p><br><br>
									    
									<b style=" margin-left: 10px;">Date: </b><b style=" padding-left: 400px;">Signature of the employee</b>	    
								
									<hr style=" margin-top: 35px;">
								
									<b style=" margin-left: 10px;">Forwarded to P & A Dept. for necessary action</b>	<br><br>
							
									<b style=" margin-left: 10px;margin-top: 40px;"> Date :</b> <b style=" padding-left: 450px;">Sign. of DGM <br> Name :</b>
									<hr style=" margin-top: 50px;">
									<b style="text-decoration: underline; margin-left: 165px; margin-bottom: 10px"> FOR ADMINISTATION USE ONLY </b>
									<p>An advance of Rs. .........................(Ruppes....................................only) May be released to Dr. / Mr. / Ms. .............................Emp No................ 
									</p>
									<b style=" padding-left: 480px;">()<br>
									Incharge-P&A
									</b><br>
									<b>Forwarded to F&A Dept. for Necessary Action</b>  -->
								</td>
							</tr>
						</table>
						
						<%}%>				
					</div>
				</div>
</body>
</html>