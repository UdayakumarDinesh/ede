<%@page import="java.time.LocalDate"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/png" href="view/images/lablogoui.png">
<jsp:include page="../../view/static/dependancy.jsp"></jsp:include>
        
<spring:url value="/webresources/css/Header.css" var="Header" />
<title>Tour Report</title>
<style type="text/css">


</style>
<body>
<%
	SimpleDateFormat time = new SimpleDateFormat("HH:mm");

	Object[] details = (Object[])request.getAttribute("tourdetails");
	List<Object[]> touronwarddetails = (List<Object[]>)request.getAttribute("Touronwarddetails");
	Object[] approvalemp = (Object[]) request.getAttribute("ApprovalEmp");
%>
  <div class="center"> 
    <div class="row">
        <div class="col-md-12"  style="margin-top: 50px; margin-left: 30px">
          <div class="row" style="margin-right: 48px">
            <div class="col-6" style="font-size: 20px">Our Ref. No. STARC/P & A/1601 </div>
            <div class="col-6" style="text-align: right; font-weight: bold">Date : <%=DateTimeFormatUtil .fromDatabaseToActual( LocalDate.now().toString())%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
  		  </div>
          &nbsp;
          <div class="row">
             <div class="col">
             <div style="font-weight: bold;">Mr. / Ms. : <%=details[4]%>(<%=details[3]%>)</div>
             <div style="font-weight: bold;">Emp. No. <%=details[2] %></div>
          </div>
        </div>
             <h3 style="text-align: center;text-decoration: underline; margin-top: 70px">SUB : MOVEMENT ORDER</h3>
          <P class="para" style="margin-top: 25px ; font-size: 18px">You are directed to proceed to <%=details[12]%> for <%=details[9]%> from <%= DateTimeFormatUtil .fromDatabaseToActual(details[13].toString())%> to <%= DateTimeFormatUtil .fromDatabaseToActual(details[14].toString())%></P>
          
          <!-- <table class="table1">
            
                <tr>
                  <th style="width: 63%">Particulars</th>
                  <th style="width: 30%">Date of Journey</th>
                  <th style="width: 20%">Mode</th>
                </tr>
                <tr>
                  <td>Depature from --------------- to -------------</td>
                  <td>------------- forenoon/afternoon</td>
                  <td>Air</td>
                </tr>
                 <tr>
                  <td>Depature from --------------- to -------------</td>
                  <td>------------- forenoon/afternoon</td>
                  <td>Air</td>
                </tr>
             
          </table> -->
          <table style="width: 90%; margin-top: 10px;">
									
									<thead>
										<tr >
											<th style="text-align: left;">Proposed journey details :</th>
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
									<% if(touronwarddetails!=null && touronwarddetails.size()>0){
										for(Object[] obj:touronwarddetails){%>
										<tr>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[2]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=DateTimeFormatUtil .fromDatabaseToActual(obj[0].toString())%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=time.format(obj[1])%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[3]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;"><%=obj[4]%></td>
										</tr>
										<%}}%>
									</tbody>
								</table>
          
    </div>
    </div>
    
  <p style="margin-top: 73px ; font-size: 18px; margin-left: 31px">You may draw TA / DA as per the Society rules.</p>
  <b style="margin-left: 75px;"> (<%=approvalemp[3]%>) </b><br>
  <b style="margin-left: 75px;"> Manager - P & A</b>
</div> 
</body>
</html>