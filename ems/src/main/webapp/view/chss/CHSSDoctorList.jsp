<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>CHSS Doctors List</title>
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
	List<Object[]> doctorlist = (List<Object[]> )request.getAttribute("doctorlist") ;

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
					    <a class="nav-link active" href="##" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; Doctors List</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link " href="Circulars.htm"   ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; CHSS Circulars</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link" href="LoginPage/Eligibility.htm" target="_blank" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; CHSS Policy</a>
					  </li> 
					    <li class="nav-item">
					    <a class="nav-link" href="LoginPage/EmpanneledHospital.htm" target="_blank" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; Empanelled  Hospitals</a>
					  </li> 
		</ul>
				
  		
	</header>

	<div align="center">
		
		<div class="card" style="width: 90%;margin: 1rem;border-radius: 20px; " align="left">
			
			<div class="card-header" style="height: 4rem" >
	           <div class="row ">
						<div class="col-md-6 "><span style=" color: #3498DB; text-shadow: 0px 0px 1px #3a3b3c; font-size: 30px;">Doctors</span></div>
					    <div class="col-md-6">
						 
						</div>

					</div>
				</div>

				<div class="card-body " >
				
					<form action="##" method="POST" id="empForm"   >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive"    >
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1" > 
								<thead>
									<tr>
										<th style=" text-align:center; width: 15%"> SlNo. </th>
										<th style="width: 50%"> Name </th>
										<th style="width: 35%"> Qualification </th>									
									</tr>
								</thead>
								<tbody>
									<%if(doctorlist!=null && doctorlist.size()>0){ 
										int slno=0;
										for(Object[] obj : doctorlist){
									%>
										<tr>
											<td style="text-align:center;  width: 15%;"> <%=++slno%>. </td>
											<td style="text-align:justify; width: 50%"><%=obj[1]%></td>
											<td style="text-align:justify; width: 35%"><%=obj[2]%></td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						
						<a href="login.jsp"   class="btn btn-sm  btn-info">HOME</a>
								
					    </div>						 
					</div>
					
			   </form>		
			  </div>
		   	 </div>				
	       
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
    "lengthMenu": [5,10,20, 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});

</script>
</html>