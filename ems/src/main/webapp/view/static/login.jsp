<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<c:set var="contPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

<jsp:include page="../static/dependancy.jsp"></jsp:include>
<spring:url value="/webresources/css/LoginPage.css" var="loginPageCss" />
<link href="${loginPageCss}" rel="stylesheet" />
<link rel="shortcut icon" type="image/png" href="view/images/lablogoui.png">
<title>SEIP</title>
<style>

.fa-file-text{
	color:rgba(255,222,0,1);
}

.scroll {
    max-height: 390px;
    overflow-y: auto;
}

.scrollpolicy{
 	/* max-height: 515px; */
    overflow-y: auto !important;
}
</style>


<style type="text/css">
.ticker {
	display: flex;
	flex-wrap: wrap;
	width: 100%;
	height: 50px;
	margin: 0 auto
}

.news {
	width: 100%;
	/* background: #cc4444; */
	padding: 0 2%
}

.title {
	width: 100%;
	text-align: center;
	/* background: #c81c1c; */
	position: relative
}

/* .title:after {
	position: absolute;
	content: "";
	right: -22%;
	border-left: 20px solid #c81c1c;
	border-top: 28px solid transparent;
	border-right: 20px solid transparent;
	border-bottom: 21px solid transparent;
	top: 0px
 */}

.title h5 {
	font-size: 18px;
	margin: 8% 0
}

.news marquee {
	font-size: 18px;
	margin-top: 12px
}

.news-content p {
	margin-right: 41px;
	display: inline
}
</style>


</head>
<% List<Object[]> marqueList = (List<Object[]>)request.getAttribute("alerts-marquee"); %>
<body class="home" >

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
     				<a href="#" title="PMS"><span class="c"  style="margin-top: 2% !important;font-size: 113% !important;">STARC EMPLOYEE INFORMATION PORTAL</span></a>
     			</div>
     		
     			
    		</div>

  		</div>
  		
  		
  				<ul class="nav nav-tabs justify-content-center ">
  				
  				 	  <li class="nav-item" >
  				 	  	&nbsp;&nbsp;&nbsp;&nbsp;
					  </li> 
					  <li class="nav-item"  onclick="$('#footer').show();">
						 <a class="nav-link active" data-toggle="tab" href="#tab-1" role="tab" ><i class="fa fa-home" aria-hidden="true"></i>&nbsp;Home</a>
					  </li> 
					  
					  <li class="nav-item">
					    <a class="nav-link" href="EMSForms.htm" target="blank"><i class="fa fa-file-text" aria-hidden="true" ></i>&nbsp; Forms</a>				   
					  </li>
					  
					  <li class="nav-item">
					    <a class="nav-link" href="GovtOrdersList.htm" target="blank"><i class="fa fa-file-text" aria-hidden="true" ></i>&nbsp; Govt. Orders</a>				   
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
									<p class="quote"><!-- Simplifying Medical Claims --></p>
									
	
								 <h4 class="quote2" style="font-family: 'Lato', sans-serif;font-weight: 400" ><!-- Claims  &nbsp;|&nbsp; Coverages  &nbsp;|&nbsp;  Empowerment --></h4>
									
									
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
														
													<span style="font-family: 'Lato', sans-serif;font-size: 15px;color:red;margin-bottom: 10px;" id="success-alert">${error}</span>
													<span style="font-family: 'Lato', sans-serif;font-size: 15px;color:green;margin-bottom: 10px;" id="success-alert">${message}</span>
												</div>
												
											
												
												<div class="form-submit">
													<div class="row align-items-center mb-5">
														<div class="col-md-5">
															<div class="form-submit-button">
																<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
																<button type="submit" class="btn btn-block btn-success" style="font-family: 'Montserrat', sans-serif;" >Login</button>
																<!-- <button type="submit" class="btn btn-link" formaction="fpwd/ForgotPassword.htm" > Forgot Password?</button> -->
																
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
														<p class="text-secondary btn btn-link">Please Contact IT Admin for Resetting Password.</p> <br>
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


   <div id="footer">
	<footer class="footer"  >
	    <section id="fontSize" class="clearfix" style="font-size: 100%;margin-bottom: -1%;">
		  <section id="page" class="body-wrapper clearfix" style="">
		    	<!-- Blue Border for Login Page -->  
		    <div class="support-row clearfix" id="swapper-border" style="padding: 15px 0px;">
		  		
		  		<div class="container" style="margin-top:0px;margin-bottom: 0px">
					<div style="text-align: center;position: relative;"><h5 style="font-weight: 900;" >Recent Updates</h5></div>
				         <div class="news"> 
				             <marquee class="news-content"> 
				                 <% String alertType=""; 
				                 for(Object[] alert : marqueList){ %>
				                 	<span>
				                 		<% if(!alertType.equals(alert[0].toString())){ %>
					                 		<%if(alert[0].toString().equalsIgnoreCase("C")){ %>
					                 			<span style="font-weight: 600;color:#000000; "> Circular(s) : </span>
					                 		<%}else if(alert[0].toString().equalsIgnoreCase("N")){ %>
					                 			<span style="font-weight: 600;color:#000000;">Notice(s) : </span>
					                 		<%}else if(alert[0].toString().equalsIgnoreCase("O")){ %>
					                 			<span style="font-weight: 600;color:#000000;" >Office Order(s) :</span>
					                 		<%}else if(alert[0].toString().equalsIgnoreCase("G")){ %>
					                 			<span style="font-weight: 600;color:#000000;">Govt. Order(s) : </span>
					                 		<%} %>
					                 		<%alertType = alert[0].toString(); %>
					                 	<%} %>
				                 	
				                 		<span><%=alert[2] %></span> : &nbsp; <%=alert[4] %>
										<%if(! (marqueList.indexOf(alert)-1 == marqueList.size())){ %>				                 		
				                 			 &nbsp;&nbsp;||&nbsp;&nbsp;
				                 		<%} %>
				                 		
				                 		
				                 	</span>
				                 <%} %>
				            </marquee>
				        </div>
					</div>
		    	</div> 
		    	
		  </section>  
		</section> 
		<div class="widget-guide clearfix">
       		<div class="footr-rt">
            	<div class="copyright-content"> 
            		<p>Website maintained by Vedant Tech Solutions<br><b>Site best viewed at 1360 x 768 resolution in I.E 11+, Mozilla 70+, Google Chrome 79+</b>	</p> 
            	</div>
    		</div>
  		</div>
	</footer>
	</div> 
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

            $('.scrollpolicy').css('max-height', docHeight-155+ 'px' )
            
            if (footerTop < docHeight)
                $('#footer').css('margin-top', marginTop + 'px'); // padding of 30 on footer
            else
                $('#footer').css('margin-top', '0px');
            // console.log("docheight: " + docHeight + "\n" + "footerheight: " + footerHeight + "\n" + "footertop: " + footerTop + "\n" + "new docheight: " + $(window).height() + "\n" + "margintop: " + marginTop);
        }, 250);
    }); 
</script>
<script type="text/javascript">
$("#myTable1,#myTable2,#myTable3").DataTable({
    "lengthMenu": [10,20, 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});
</script>

</html>