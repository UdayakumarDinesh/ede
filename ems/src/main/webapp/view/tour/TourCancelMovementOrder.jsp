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
 <jsp:include page="../static/LetterHead.jsp"></jsp:include>
 
<title>Tour Cancel Movement Order</title>
<%
	SimpleDateFormat time = new SimpleDateFormat("HH:mm");

	Object[] details = (Object[])request.getAttribute("tourdetails");
	List<Object[]> touronwarddetails = (List<Object[]>)request.getAttribute("Touronwarddetails");
	Object[] approvalemp = (Object[]) request.getAttribute("ApprovalEmp");
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
					content: "Emp No: <%=details[2]%>";
					
				}

			}
body
{
	font-size:14px !important;
}


</style>
<body>

  <div class="center"> 
    <div class="row">
        <div class="col-md-12"  style="margin-top: 10px; margin-left: 30px">
          <div class="row" style="margin-right: 48px">
            <div class="col-6" style="font-size: 20px;">Our Ref. No. <span style=" color: blue; font-size: 20px;"><%=details[1]%> </span> </div>
            <div class="col-6" style="text-align: right; font-weight: bold">Date : <span style="color: blue;"><%=DateTimeFormatUtil .fromDatabaseToActual( LocalDate.now().toString())%> </span>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
  		  </div>
          &nbsp;
          <div class="row">
             <div class="col">
             <div style="font-weight: bold;">Mr. / Ms. : <span style="color: blue;"><%=details[4]%>(<%=details[3]%>) </span> </div>
             <div style="font-weight: bold;">Emp. No. :<span style="color: blue;"> <%=details[2] %> </span></div>
          </div>
        </div>
             <h3 style="text-align: center;text-decoration: underline; margin-top: 50px">SUB : CANCELLATION OF TOUR PROGRAMME</h3>
          <p class="para" style="margin-top: 25px ; font-size: 18px">Kindly refer to the Movement order issued vide Ref:No &nbsp;<b style="color: #007bff;"><%=details[1]%></b> &nbsp; dated  &nbsp;  <span style="color: blue;"><%=DateTimeFormatUtil .fromDatabaseToActual( LocalDate.now().toString())%></span><br><br>
          As the tour has been cancelled, the Movement Order dated  <b style="color: #007bff;"><%=DateTimeFormatUtil .fromDatabaseToActual( LocalDate.now().toString())%>  </b>  Stands withdrawn.
          </p>
          
    </div>
    </div>
    
<!--   <p style="margin-top: 73px ; font-size: 18px; margin-left: 31px">You may draw TA / DA as per the Society rules.</p>
 -->  <b style="margin-left: 80px;"><span style="color: blue; margin-left: 65px;"> (<%=approvalemp[3]%>) </span></b><br>
  <b style="margin-left: 75px;"> Manager - P & A</b> <br><br>	
  <b style="margin-top: 73px ; font-size: 18px; margin-left: 31px"> To :F&A Dept: - for Information</b>
</div> 
</body>
</html>