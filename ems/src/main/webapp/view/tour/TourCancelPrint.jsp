<%@page import="java.util.stream.Collectors"%>
<%@page import="java.time.LocalDate"%>
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
	
	<%
	String LabLogo = (String) request.getAttribute("LabLogo");

	Object[] tourdetails = (Object[]) request.getAttribute("tourdetails");
	SimpleDateFormat time = new SimpleDateFormat("HH:mm");
	List<Object[]> statusdetails = (List<Object[]>)request.getAttribute("cancelstatustrack");

	List<Object[]> Deptapprove = statusdetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("CAA")).collect(Collectors.toList());      
	List<Object[]> dgmapprove  = statusdetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("CAG")).collect(Collectors.toList());
	List<Object[]> FAapprove   = statusdetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("CAF")).collect(Collectors.toList());
	List<Object[]> PAapprove   = statusdetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("CAP")).collect(Collectors.toList());
	List<Object[]> CEOapprove  = statusdetails.stream().filter(e-> e[9].toString().equalsIgnoreCase("CAC")).collect(Collectors.toList());

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
<title>Tour Cancel Form</title>

</head>
<body>
	 <jsp:include page="../static/LetterHead.jsp"></jsp:include>

<div class="center">
								<table style="width:100%;margin-top:10px; border: 1px solid black; border-collapse: collapse; text-align: center;">
								
									<tr>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"> <b style="font-size: 2em;">STARC </b> <br> <b> BANGALORE </b></td>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;">
										<b>	FORM FOR CANCELLATION OF TOUR <br> PROGRAMME</b>
										</td>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"> <b>SITAR-BNG-P&A-037F <br> Date of Issue : <%=DateTimeFormatUtil.fromDatabaseToActual( LocalDate.now().toString())%> </b> </td>
										<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><b>  Rev : 04 </b> </td>
									</tr>
								</table>
								
								<br>
								
								<table style="border: 0px; width: 100%">
									<tr>
										<td><b>Department :  </b>&nbsp; <span style="color: blue;"><%=tourdetails[3]%>  </span></td>
										<td><b> Phone No :   </b>&nbsp; <span style="color: blue;"><%=tourdetails[6]%>  </span></td>
										<td><b> Date :       </b>&nbsp; <span style="color: blue;"><%=DateTimeFormatUtil.fromDatabaseToActual( LocalDate.now().toString())%></span></td>
									</tr>
								</table>
								<br>
								<div align="left">
								<p> A tour which was approved / planed to visit <span style="color: blue; font-weight: 600; text-decoration: underline;"> <%=tourdetails[12]%> </span> 
								by the following official for the purpose of <span style="color: blue; font-weight: 600; text-decoration: underline;"> <%=tourdetails[9]%> </span>
								 may be cancelled / has been re-scheduled due to <span style="color: blue; font-weight: 600; text-decoration: underline;"> <%=tourdetails[25]%> </span>
								 </p>
								 <p> Movement Order Ref. no ________________ dated _______________(if issued)</p>
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
								<p> THe above is put up for incurring cancellation charges of Rs. __________ / reimbursement of 
								Rs.___________ to the undersigned </p>
								<p> The Invoice / cancelled ticket is enclosed.  <br>
								 Put up for Expenditure Sanction.</p>
								<b style="margin-left: 470px;"> Signature of employee &nbsp;&nbsp; </b><span style="color: blue;margin-left: 470px;"><%=tourdetails[4]%>(<%=tourdetails[3]%>) </span><br><br>
								<%if(Deptapprove!=null && Deptapprove.size()>0){ %>
								<b style="margin-right: 10px;">Dept. Incharge. </b> <br>
									<span style="color: blue; margin-right: 10px;"><%=Deptapprove.get(0)[2]%>(<%=Deptapprove.get(0)[3]%>) <br><%=Deptapprove.get(0)[5]%></span>
								<br><%}%>
								
								<%if(FAapprove!=null && FAapprove.size()>0){%> 
								    <b style="margin-left: 480px;">Incharge-F & A &nbsp;&nbsp;&nbsp;&nbsp;</b>		
									<span style="color: blue; margin-left: 480px;"><%=FAapprove.get(0)[2]%>(<%=FAapprove.get(0)[3]%>) <br><%=FAapprove.get(0)[5]%></span>
								<br><%}%>
								  
								<%if(dgmapprove!=null && dgmapprove.size()>0){ %>
								 <b>DGM</b><br>
									<span style="color: blue;"><%=dgmapprove.get(0)[2]%>(<%=dgmapprove.get(0)[3]%>) <br><%=dgmapprove.get(0)[5]%></span>						
								<br><%}%>
								
								
								<%if(CEOapprove!=null && CEOapprove.size()>0){ %>
									<b style="margin-left: 240px;"> SANCTIONED / NOT SANCTIONED </b>
									<b style="margin-left: 340px;">CEO</b><br>
									<span style="color: blue; margin-left: 330px;"><%=CEOapprove.get(0)[2]%>(<%=CEOapprove.get(0)[3]%>) <br><%=CEOapprove.get(0)[5]%></span>							
								<br><%}%>
								<b>P&A Dept. for issue of cancellation of movement orders and onward transmission to F&A Department for necessary action.</b>	
													
								</div>
							</div>
</body>
</html>