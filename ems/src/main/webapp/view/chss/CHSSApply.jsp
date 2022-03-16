<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

<style type="text/css">


.card-img-top{
	height: 6rem;
    width: 6rem;
	margin: 0rem 1rem 0rem 1rem;
}

/* CARDS */

.cards {

    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    
   
}

.card-emp {
    margin: 0px;
    padding: 10px;
  	width:100%;
	height:14rem;
    display: grid;
    grid-template-rows: 20px 50px 1fr 50px;
    border-radius: 10px;
    box-shadow: 0px 6px 10px rgba(0, 0, 0, 0.25);
    transition: all 0.2s;
    overflow:hidden;
    
}

.card-emp:hover {
    box-shadow: 0px 6px 10px rgba(0, 0, 0, 0.4);
    transform: scale(1.01);
}

.card__link,
.card__exit,
.card__icon {
    position: relative;
    font-size: 1.2vw;
    text-decoration: none;
    height: 100%;
    
    
}

.card__link::after {
    position: absolute;
    top: 25px;
    left: 0;
    content: "";
    width: 0%;
    height:auto;
    background-color: rgba(255, 255, 255, 0.6);
    transition: all 0.5s;
}

.card__link:hover::after {
    width: 100%;
    color: #ffffff;
}


.card__title {
    grid-row: 1/4;
    font-weight: 100;
    color: #ffffff;
}

.card__apply {
    position:relative;
    align-self: center;
    height: 100%;
    bottom:0;
    right:0;
}


/* CARD BACKGROUNDS */

.card-1 {
  background: radial-gradient(#1fe4f5, #3fbafe);
}

.card-2 {
  background: radial-gradient(#fbc1cc, #fa99b2);
}

.card-3 {
  background: radial-gradient(#76b2fe, #b69efe);
}

.card-4 {
  background: radial-gradient(#60efbc, #58d5c9);
}

.card-5 {
  background: radial-gradient(#f588d8, #c0a3e5);
}

/* RESPONSIVE */


.button-mem, .button-mem:hover, .button-mem:focus 
{
 outline: none;
 margin:0px !important;
 padding: 0rem !important;
 background: transparent;
 border: 0px !important;
 width:100% !important;
 height: inherit !important;
 cursor: pointer;
 
}

.card__link{
color: black;
}

</style>

</head>
<body>

<%
	List<Object[]> empfamilylist = (List<Object[]> )request.getAttribute("empfamilylist") ;
	Employee employee = (Employee )request.getAttribute("employee") ;
%>

 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Apply</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Apply</li>
					</ol>
				</div>
			</div>
	</div>	
	<div class="card-body" >			
	
	
	
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
	
			<div class="card" >
				<div class="card-body " >
					<div class="row">
						<div class="col-12" align="center">
							<h4> Select Member</h4>
						</div>						
					</div>
					<div class="row" align="center">
							
							<div class="col-3">
								<form action="CHSSApplyDetails.htm" method="post" style="padding: 1rem">
									<button type="submit" name="patientid" value="<%=employee.getEmpId() %>"  class="button-mem">
										<div class="cards">
									        <div class="card-emp card-1 " >
									       
									        <h2 class="card__title">
									        	<img class="card-img-top" src="view/images/user.png"  alt="USER" >
									       	</h2>
									        
									            <span class="card__apply">
									               <span class="card__link" style=""><%=employee.getEmpName() %></span>
									            </span>
									             <span>(SELF)</span>
									            
									        </div>
										</div>
										
									</button>
									<input type="hidden" name="isself" value="Y">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
							 
							
							 
							<%for(Object[] obj : empfamilylist){ %>
							<div class="col-3">
								<form action="CHSSApplyDetails.htm" method="post" style="padding: 1rem">
									<button type="submit"  name="patientid" value="<%=obj[0]%>" class="button-mem" >
										<div class="cards">
									        <div class="card-emp card-4 " >
									        <h2 class="card__title">
									        	<img class="card-img-top" src="view/images/user.png"  alt="USER" >
									        </h2>
									            <div class="card__apply">
									                <span class="card__link"><h5><%=obj[1] %></h5></span>
									            </div>
									            <span >(<%=obj[7] %>)</span>
									        </div>
										</div>				 
									</button>
									<input type="hidden" name="isself" value="N">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							 </div>
							 <%} %>
						
					</div>	
			</div>
		</div>		
		
	</div>

</div>




</body>
</html>