<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<c:set var="contPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>CHSS Login</title>

<jsp:include page="../static/dependancy.jsp"></jsp:include>
<spring:url value="/webresources/css/LoginPage.css" var="loginPageCss" />
<link href="${loginPageCss}" rel="stylesheet" />

<style>

.fa-file-text{
	color:rgba(255,222,0,1);
}

.scroll {
    max-height: 390px;
    overflow-y: auto;
}

.scrollpolicy{
 	max-height: 515px;
    overflow-y: auto;
}
</style>


</head>

<body class="home" >
<%List<Object[]> circularlist = (List<Object[]>)request.getAttribute("circularlist");
List<Object[]> circular =  (List<Object[]>)request.getAttribute("circular");
List<Object[]> doctorlist = (List<Object[]> )request.getAttribute("doctorlist") ;
List<Object[]> Empanelled = (List<Object[]> )request.getAttribute("Empanelled") ;
String chss_policy_pdf=(String)request.getAttribute("chss_policy_pdf");
String path=(String)request.getAttribute("path"); 
%>

<!--  Login Page  -->  
  
<section class="loginpage">
  
	<header id="header" class="clearfix">
 
	
  		<div class="btmhead clearfix">
    		<div class="widget-guide clearfix">
      			<div class="header-right clearfix">
        			<div class="float-element">
        				<a class="" href="" target="_blank">
        					<img  class ="drdologo " src="view/images/lablogoui.png" alt=""></a>
        			</div>
      			</div>
     			<div class="logo">
     				<a href="#" title="PMS"><span class="c"  style="margin-top: 2% !important;font-size: 113% !important;">CONTRIBUTORY HEALTH SERVICE SCHEME</span></a>
     			</div>
     		
     			
    		</div>

  		</div>
  		
  		
  		<ul class="nav nav-tabs justify-content-end ">
					  <li class="nav-item">
						   <!--  <a class="nav-link active" href="#"><i class="fa fa-home" aria-hidden="true"></i>&nbsp;Home</a> -->
						 <a class="nav-link active" data-toggle="tab" href="#tab-1" role="tab" ><i class="fa fa-home" aria-hidden="true"></i>&nbsp;Home</a>
					  </li> 
					  
					   <li class="nav-item">
					    <a class="nav-link" data-toggle="tab" href="#tab-5" role="tab"><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; CHSS Policy</a>				   
					  </li>
					  
					  <li class="nav-item">		   
					  	<a class="nav-link" data-toggle="tab" href="#tab-2" role="tab" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; CHSS Circulars</a>
					  </li>
					  
					  <li class="nav-item">					   
					   <a class="nav-link" data-toggle="tab" href="#tab-3"  role="tab"><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; Doctors List</a>
					  </li>

					    <li class="nav-item">					    
					    <a class="nav-link" data-toggle="tab" href="#tab-4" role="tab" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; Empanelled  Hospitals</a>
					  </li> 
		</ul>
				
  		
	</header>
 </section> 
<div class="tab-content">

<!-- -----------------------------------Home--------------------------------------------- -->
<div class="tab-pane active " id="tab-1" role="tabpanel" style=" max-height: 360px; ">
<!-- Login Page Content -->

 <div class="container" style="margin-top: 20px; ">
		<div class="row">
			<div class="col-md-12">
			
				<div class="login-container justify-content-center">
					<div class="row align-item-center">
						
						<div class="col-md-6">
							<div >
								
								<div>
									<p class="quote">Simplifying Medical Claims</p>
									
	
								 <h4 class="quote2" style="font-family: 'Lato', sans-serif;font-weight: 400" >Claims  &nbsp;|&nbsp; Coverages  &nbsp;|&nbsp;  Empowerment</h4>
									
									
								</div>
								
								<div class="product-banner-container" style="margin-top:35px">
									<img class="img-fluid img-responsive" src="view/images/LoginImg4.png" style="">
								</div>
								
							</div>
						</div>
						
						
						<div class="col-md-6">
					
							<div class="row justify-content-end login-main-container" style="margin-left:7rem;margin-top: 4rem" >
	
								<div class="col-md-12">
								
									<div align="center"><h5 style="color:maroon;font-family: 'Lato', sans-serif;display: block;" class="welcome">Welcome !</h5></div> 
									
									<div class="login-form-wrapper " style="padding-right: 43px;padding-left: 43px; padding-bottom: 43px; padding-top: 8px " >
										
										<div class="login-info-container">
											<h3 style="font-family: 'Lato', sans-serif;font-weight: 800" >Login</h3><br>
										</div>
										
										<div class="login-form-container">
										
											   <form action="${contPath}/login" autocomplete="off" method="post" >
											   
									
												<div class="form-row">
													
													<div class="form-group col-12 position-relative ${error != null ? 'has-error' : ''}">
														<input type="text" name="username" placeholder="Username" class="form-control" required>
														<i class="fa fa-user fa-lg position-absolute"></i>
													</div>
													
													<div class="form-group col-12 position-relative">
														<input name="password" type="password" placeholder="Password"  class="form-control">
														<i class="fa fa-lock fa-lg position-absolute"></i>	
													</div>
														
													<%-- <span style="font-family: 'Lato', sans-serif;font-size: 15px;color:red;margin-bottom: 10px;" id="error-alert">${error}</span>
													<span style="font-family: 'Lato', sans-serif;font-size: 15px;color:green;margin-bottom: 10px;" id="success-alert">${success}</span> --%>
													
															<span style="font-family: 'Lato', sans-serif;font-size: 15px;color:red;margin-bottom: 10px;" id="success-alert">${error}</span>
																		
												</div>
												
											
												
												<div class="form-submit">
													<div class="row align-items-center mb-5">
														<div class="col-md-5">
															<div class="form-submit-button">
																<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
																<button type="submit" class="btn btn-block btn-success" style="font-family: 'Montserrat', sans-serif;" >Login</button>
																<button type="submit" class="btn btn-link" formaction="fpwd/ForgotPassword.htm" > Forgot Password?</button>
															</div>
														</div>
													</div>
													
													
												</div>
												
												
											</form>
											
										</div>
										
										<div class="credentials-info-container" style="margin-top: -35px;margin-bottom: -6px">
											<div class="row">
												<div class="col-md-12">
													
													<div class="info-container text-md-left">
														<p class="text-secondary" style="font-family: 'Lato', sans-serif;font-size: 15px;">* Do not share credentials with anyone</p>
													</div>
													
												</div>
											</div>
										</div>
										
									</div>
								</div>
							</div>
						</div>
						
					</div>
					
				</div>
			
			</div>
			
		</div>
	</div>	
	</div>
	
	<!-- -----------------------------------Circular--------------------------------------------- -->
	<div class="tab-pane scroll" id="tab-2" role="tabpanel" >
		<div align="center"  >
		
		<div class="card " style="width: 90%;margin: 1rem;border-radius: 20px; " align="left">
			
			<div class="card-header" style="height: 4rem" >
			
					<form action="Circulars.htm" method="POST" >
					
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="row ">
						<div class="col-md-6 "><span style=" color: #3498DB; text-shadow: 0px 0px 1px #3a3b3c; font-size: 30px;">Circulars</span></div>
					    <div class="col-md-6">
						 <%--    <table>
						    	<tr>
						    		<td><h6>From Date :&nbsp;&nbsp;</h6></td>
									<td>							    	
										<input type="text" style="width: 100%;"  class="form-control input-sm mydate"  onchange="this.form.submit()" readonly="readonly" <%if(fromdate!=null){%> value="<%=fromdate%>" <%}%>   id="fromdate" name="fromdate"  required="required"  >
									</td>
									<td><h6>&nbsp;&nbsp;To Date :&nbsp;&nbsp;</h6></td>
									<td>		
										<input type="text" style="width: 100%;"  class="form-control input-sm mydate" onchange="this.form.submit()" readonly="readonly" onchange="this.form.submit()" <%if(todate!=null){%>value="<%=todate%>" <%}%>   id="todate" name="todate"  required="required"  > 							
									</td>
								</tr>    
							</table> --%>
						</div>

					</div>
							 
				   </form>
				
				</div>

				<div class="card-body main-card " >
				
					<form action="##" method="POST" id="empForm1"   >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive "    >
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1" > 
								<thead>
									<tr>
										<th style="width: 10%">SlNo.</th>
										<th style="width: 10%">Reference No</th>
										<th style="width: 50%" >Description </th>
										<th style="width: 20%" >Circular Date</th>
										<th style="width: 20%"> Download</th>
									</tr>
								</thead>
								<tbody>
									<%if(circular!=null && circular.size()>0){ 
										int slno=0;
										for(Object[] obj : circular){
									%>
										<tr>
											<td style="text-align:center;  width: 5%;"> <%=++slno%>. </td>
											<td style="text-align:justify; width: 10%;"><%=obj[6]%></td>
											<td style="text-align:justify; width: 70%;"><%=obj[1]%></td>
											<td style="text-align:justify; width: 10%;"><%if(obj[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[3].toString())%> <%} %></td>
											<td style="text-align:center;  width: 5%;"> <%if(obj[2]!=null){ %> 
											<button type="submit" class="btn btn-sm" name="path" value="<%=obj[2]%>//<%=obj[4] %>" formaction="download-CircularFile-attachment" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
											  <i style="color: #019267" class="fa-solid fa-download"></i>
										    </button>
											<%}else{%>--<%}%>
											</td>
										</tr>
										
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<!-- <div class="row text-center">
						<div class="col-md-12">
						
						<a href="login.jsp"   class="btn btn-sm  btn-info">HOME</a>
								
					    </div>						 
					</div> -->
					
			   </form>		
			  </div>
		   	 </div>				
	       
	</div> 
	</div>

	<!-- -----------------------------------Doctor--------------------------------------------- -->
	<div class="tab-pane scroll" id="tab-3" role="tabpanel">
		<div align="center">
		
		<div class="card" style="width: 90%;margin: 1rem;border-radius: 20px; " align="left">
			
			<div class="card-header" style="height: 4rem" >
	           <div class="row ">
						<div class="col-md-6 "><span style=" color: #3498DB; text-shadow: 0px 0px 1px #3a3b3c; font-size: 30px;">Doctors</span></div>
					    <div class="col-md-6">
						 
						</div>

					</div>
				</div>

				<div class="card-body main-card " >
				
					<form action="##" method="POST" id="empForm2"   >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive"    >
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1" > 
								<thead>
									<tr>
										<th style=" text-align:center; width: 15%;"> SlNo. </th>
										<th style="width: 15%;"> Name </th>
										<th style="width: 50%;"> Address </th>
										<th style="width: 20%;"> Contact Details </th>									
									</tr>
								</thead>
								<tbody>
									<%if(doctorlist!=null && doctorlist.size()>0){ 
										int slno=0;
										for(Object[] obj : doctorlist){
									%>
										<tr>
											<td style="text-align:center;  width: 10%;"> <%=++slno%>. </td>
											<td style="text-align:justify; width: 20%;"><%=obj[1]%> (<%=obj[2]%>)</td>
									
											<td style="text-align:justify; width: 50%;"><%=obj[3]%></td>
											<td style="text-align:justify; width: 20%;"><%=obj[4]%></td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
				<!-- 	<div class="row text-center">
						<div class="col-md-12">
						
						<a href="login.jsp"   class="btn btn-sm  btn-info">HOME</a>
								
					    </div>						 
					</div> -->
					
			   </form>		
			  </div>
		   	 </div>				
	       
	</div> 
	</div>

	<!-- -----------------------------------Empanelled Hospital--------------------------------------------- -->
<div class="tab-pane scroll" id="tab-4" role="tabpanel">
	
	<div align="center">
		
		<div class="card" style="width: 90%;margin: 1rem;border-radius: 20px; " align="left">
			
			<div class="card-header" style="height: 4rem" >
	           <div class="row ">
						<div class="col-md-6 "><span style=" color: #3498DB; text-shadow: 0px 0px 1px #3a3b3c; font-size: 30px;">Empanelled Hospital</span></div>
					    <div class="col-md-6">
						 
						</div>

					</div>
				</div>

				<div class="card-body main-card " >
				
					<form action="##" method="POST" id="empForm3"   >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive"    >
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1" > 
								<thead>
									<tr>
										<th style=" text-align:center; width: 15%"> SlNo. </th>
										<th style="width: 50%">Hospital Name </th>
										<th style="width: 50%">Hospital Address</th>
																
									</tr>
								</thead>
								<tbody>
									<%if(Empanelled!=null && Empanelled.size()>0){ 
										int slno=0;
										for(Object[] obj : Empanelled){
									%>
										<tr>
											<td style="text-align:center;  width: 15%;"> <%=++slno%>. </td>
											<td style="text-align:justify; width: 30%"><%=obj[1]%></td>
											<td style="text-align:justify; width: 45%"><%=obj[2]%></td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<!-- <div class="row text-center">
						<div class="col-md-12">
						
						<a href="login.jsp"   class="btn btn-sm  btn-info">HOME</a>
								
					    </div>						 
					</div> -->
					
			   </form>		
			  </div>
		   	 </div>				
	       
	</div> 
	</div>

	
	
<div class="tab-pane" id="tab-5" role="tabpanel">
    <div align="center" style="min-height: 29rem !important;">
        	<div class="card scrollpolicy">
        		<div class="card-body main-card" >
                    <div class="row" align="center">
                    	<div class="col-md-1"></div>
                        <div class="col-md-10" align="left">

                        </div>
                    </div>

                    <div class="row" align="left">
                    	<div class="col-md-1"></div>
                        <div class="col-md-10" >
                            <iframe  title="dsfbds.pdf" name="application.pdf" width="100%" height="600" src="data:application/pdf;base64,<%=chss_policy_pdf%>" style="margin:15px;"> </iframe>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>	
</div>	

 
<!-- Footer -->

 <!--    <footer id="footer" class="clearfix">
  		<div class="widget-guide clearfix">
       		<div class="footr-rt">
            	<div class="copyright-content"> 
            		<p>Website maintained by Vedant Tech Solutions<br>Site best viewed at 1360 x 768 resolution in I.E 11+, Mozilla 70+, Google Chrome 79+	</p> 
            	</div>
    		</div>
  	</div>
</footer> -->

    <!--/#footer-->
    
	<footer class="footer" id="footer">
	    <section id="fontSize" class="clearfix" style="font-size: 100%;margin-bottom: -1%;">
		  <section id="page" class="body-wrapper clearfix" style="">
		    	<!-- Blue Border for Login Page -->  
		    <div class="support-row clearfix" id="swapper-border" style="">
		      		  <div class="widget-guide clearfix">
		                	    <%if(circularlist!=null  && circularlist.size()>0) {%>
								<marquee direction="left" scrollamount="3" style="background: rgba(6,127,208,1);"> <p style="font-size: 1rem;margin-bottom: 0px !important"><%int i =0; for(Object[] obj:circularlist){ ++i;%><%=obj[1]%>  &emsp; <%if(circularlist.size()!=i){ %> || <%}%> &emsp; <% }%></p></marquee>
								<%}%> 
		              </div>
		    	</div> 
		    	
		  </section>  
		</section> 
		<div class="widget-guide clearfix">
       		<div class="footr-rt">
            	<div class="copyright-content"> 
            		<p>Website maintained by Vedant Tech Solutions<br>Site best viewed at 1360 x 768 resolution in I.E 11+, Mozilla 70+, Google Chrome 79+	</p> 
            	</div>
    		</div>
  		</div>
	</footer>

</body>

<script type="text/javascript">
$("#success-alert") .fadeTo(3000, 1000).slideUp(1000, function ( ) {
    $("#success-alert").slideUp(1000);
});

$("#error-alert") .fadeTo(3000, 1000).slideUp(1000, function ( ) {
    $("#error-alert").slideUp(1000);
});

</script>

<script>
    $(document).ready(function() {
        setInterval(function() {
            var docHeight = $(window).height();
            var footerHeight = $('#footer').height();
            var footerTop = $('#footer').position().top + footerHeight;
            var marginTop = (docHeight - footerTop + 10);

            if (footerTop < docHeight)
                $('#footer').css('margin-top', marginTop + 'px'); // padding of 30 on footer
            else
                $('#footer').css('margin-top', '0px');
            // console.log("docheight: " + docHeight + "\n" + "footerheight: " + footerHeight + "\n" + "footertop: " + footerTop + "\n" + "new docheight: " + $(window).height() + "\n" + "margintop: " + marginTop);
        }, 250);
    });
</script>
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