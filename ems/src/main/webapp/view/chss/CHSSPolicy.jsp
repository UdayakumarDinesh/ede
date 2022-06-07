<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>CHSS Policy List</title>
<jsp:include page="../static/dependancy.jsp"></jsp:include>
<spring:url value="/webresources/css/LoginPage.css" var="loginPageCss" />
<link href="${loginPageCss}" rel="stylesheet" />

<style type="text/css">
.group
{
    text-align: center;
    color: #3498DB;
    text-shadow: 0px 0px 1px #3A3B3C;
    text-decoration: underline;
}

</style>

</head>
<body>
<%

%>

<section class="loginpage">
  
	<header id="header" class="clearfix">
 
	
  		<div class="btmhead clearfix">
    		<div class="widget-guide clearfix">
      			<div class="header-right clearfix">
        			<div class="float-element">
        				<a class="" href="" target="_blank">
        					<img  class ="drdologo" src="view/images/lablogoui.png" alt=""></a>
        			</div>
      			</div>
     			<div class="logo">
     				<a href="#" title="PMS"><span class="title"  style="margin-top: 2% !important;font-size: 113% !important;">CONTRIBUTORY HEALTH SERVICE SCHEME</span></a>
     			</div>
     		
     			
    		</div>

  		</div>
  		
  		
  		<ul class="nav nav-tabs justify-content-end ">
			<li class="nav-item">
			    <a class="nav-link " href="login.jsp"><i class="fa fa-home" aria-hidden="true"></i>&nbsp;Home</a>
			 </li> 
					   <li class="nav-item">
					    <a class="nav-link active" href="##" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; CHSS Policy</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link " href="Circulars.htm"   ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; CHSS Circulars</a>
					  </li>
					  <li class="nav-item">
					   
					     <a class="nav-link " href="DoctorsList.htm" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; Doctors List</a>
					  </li> 
					    <li class="nav-item">
					    <a class="nav-link" href="EmpanneledHospital.htm"  ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; Empanelled  Hospitals</a>
					  </li> 
		</ul>
				
  		
	</header>

	<div align="center" style="height: 100%;">
		
					
	       
	</div> 
	</section>
	
<div class="wrapper" id="skipCont"></div>
<!--/#skipCont-->



<section id="fontSize" class="clearfix" style="font-size: 100%;">
  <section id="page" class="body-wrapper clearfix">
    	
 
<!-- Blue Border for Login Page -->  
    <div class="support-row clearfix" id="swapper-border" style="">
      		<div class="widget-guide clearfix">
                	<%-- <%if(circularlist!=null  && circularlist.size()>0) {
						
						%>
						<marquee direction="left"  style="background: rgba(6,127,208,1);"> <b><%int i =0; for(Object[] obj:circularlist){ ++i;%><%=obj[1]%>  &emsp; <%if(circularlist.size()!=i){ %> || <%}%> &emsp; <% }%></b></marquee>
						<%  }%>  --%>
              </div>
    	</div> 
    	
  </section>  <!--/#page--> 
  

</section> 


<!-- Footer -->

    <footer id="footer" class="clearfix">
  		<div class="widget-guide clearfix">
       		<div class="footr-rt">
            	<div class="copyright-content"> 
            		<p>Website maintained by Vedant Tech Solutions<br>Site best viewed at 1360 x 768 resolution in I.E 11+, Mozilla 70+, Google Chrome 79+	</p> 
            	</div>
    		</div>
  	</div>
</footer>

    <!--/#footer-->
	
</body>

<script type="text/javascript">

$("#myTable1").DataTable({
    "lengthMenu": [10,20, 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});

</script>
</html>