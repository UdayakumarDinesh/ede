<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="org.bouncycastle.util.Arrays"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<jsp:include page="../static/header.jsp"></jsp:include>
<style>
.fa-file-text{
	color:rgba(255,222,0,1);
}

	.title h5 {
	font-size: 18px;
	margin: 8% 0
}
.news {
	width: 100%;
	
	padding: 0 2%;
}


.news marquee {
	font-size: 18px;
	margin-top: 12px
}

.news-content p {
	margin-right: 41px;
	display: inline
}

.footer {
    position: fixed;
    bottom: 0;
    width: 100%;
}
	</style>

	</head>

	<body  >
	<%	
		List<Object[]> emplogintypelist     = (List<Object[]>)request.getAttribute("logintypeslist");
		String logintype   = (String)session.getAttribute("LoginType");
		String EmpName     = (String)session.getAttribute("EmpName");
	%>
	<% List<Object[]> marqueList = (List<Object[]>)request.getAttribute("alerts-marquee"); %>
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
		<div class="row">
			<div class="col-md-3">
				<h5 style="padding-top: 0.5rem">DASHBOARD </h5>
			</div>
			<div class="col-md-9">
					<form action="EmpLogitypeChange.htm" method="post" style="float: right;">
							
								<b>Login As : &nbsp;</b> 
								<select class="form-control select2" name="logintype" onchange="this.form.submit();" style="margin-top: -5px;width: 200px;">
									<%for(Object[] login:emplogintypelist){ %>
										<option value="<%=login[0]%>" <%if(logintype.equalsIgnoreCase(login[0].toString())){ %>selected <%} %>><%=login[1]%></option>
									<%} %>
								</select>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							
					</form> 
				
			</div>
		</div>
	</div>	


	<div class="card dashboard-card" >
		<div class="card-body " >
		<div align="center">
						<%String ses=(String)request.getParameter("result"); 
						String ses1=(String)request.getParameter("resultfail");
						if(ses1!=null){ %>
							<div class="alert alert-danger" role="alert">
								<%=ses1 %>
							</div>
							
						<%}if(ses!=null){ %>
							
							<div class="alert alert-success" role="alert">
								<%=ses %>
							</div>
						<%} %>
					</div>
			<div class="container-fluid">
				
				<div class="row">
					<div class="col-md-12" align="center">
						<h1>WELCOME <%=EmpName.toUpperCase() %></h1>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12" align="center">
						<h1 style="font-weight: 900 !important;  font-size: 90px;  color: #1c6abd;">SEIP</h1>
					</div>
				</div>
			  
			</div>
			
			<div class="footer" >
				    <section id="fontSize" class="clearfix" style="margin-bottom: 1%;">
					  <section id="page" class="body-wrapper clearfix" style="">
					    	<!-- Blue Border for Login Page -->  
					    <div class="support-row clearfix" id="swapper-border" style="padding: 15px 0px;">
					  		
					  		<!-- <div class="container-fluid" style="margin-top:280px;margin-bottom: 0px;"> -->
					  		<div  class="card-footer page-bottom" style="bottom: 0;">
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
					    	<!-- </div> --> 
					    	
					  </section>  
					</section> 
			</div>
			</div>
		</div>
	
</body>

</html>