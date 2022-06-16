<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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





</style>


</head>

<body class="home" >
<%List<Object[]> circularlist = (List<Object[]>)request.getAttribute("circularlist");


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
			    <a class="nav-link active" href="#"><i class="fa fa-home" aria-hidden="true"></i>&nbsp;Home</a>
			 </li> 
					   <li class="nav-item">
					    <a class="nav-link" href="CHSSPolicy.htm" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; CHSS Policy</a>				   
					  </li>
					  <li class="nav-item">
					    <a class="nav-link" href="Circulars.htm"  ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; CHSS Circulars</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link" href="DoctorsList.htm" ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; Doctors List</a>
					  </li> 
					    <li class="nav-item">
					    <a class="nav-link" href="EmpanneledHospital.htm"  ><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp; Empanelled  Hospitals</a>
					  </li> 
		</ul>
				
  		
	</header>


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
 </section> 
 
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



</html>