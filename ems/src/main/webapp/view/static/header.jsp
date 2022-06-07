<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.time.LocalDate"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">

<title>EMS</title>
<link rel="shortcut icon" type="image/png" href="view/images/lablogoui.png">
<jsp:include page="dependancy.jsp"></jsp:include>
        
<spring:url value="/webresources/css/Header.css" var="Header" />
<link href="${Header}" rel="stylesheet" />

<style type="text/css">

.badge-counter {
    position: absolute;
    transform: scale(.8);
    transform-origin: top right;
   	margin-left: -1.0rem;
    margin-top: -.25rem;
    background: red;
   	font-family:'Lato', sans-serif;  
   	
}

.custom-button:hover{
	color: white !important;
	
}

.custom-button{
	color: white !important;
	
}

.bg-transparent{
	    margin: 7px;
    background-color: transparent;
    text-transform: capitalize;
    color: white;
    font-weight: 100;
    padding: 4px 7px;
}

.onclickbell{
	    margin: 0px 0px 0px 35px;
}

.logoutdivider{
	margin: 0px !important
}

.logout a{
	font-size: 14px !important;
	font-weight: 800 !important;
}
</style>

</head>

<body>
         
	<% String Username = (String)session.getAttribute("Username");
       String logintype   = (String)session.getAttribute("LoginType"); 
       String EmpName     = (String)session.getAttribute("EmpName");
    	
       
    %>
      
	<div class="wrapper">

                  <%--  <div id="sidebar-container" class="sidebar-expanded  ">
						<div class="navbar-brand ml-3 w-100" style="margin: 2.5px 0px;">
							<div style="">
								<span  style="font-size: 1.7rem; color: #ffff">
									<img src="view/images/works.png" width="30" height="30" alt="">
									<span class="menu-collapsed" style="font-size: 22px;" >CHSS</span>
								</span>
							</div>
						</div>
						<ul class="list-group" id="module">
					 <%if("A".equalsIgnoreCase(logintype)){%>
							   <li>
									<a href="PisAdminDashboard.htm"
										class="bg-dark list-group-item list-group-item-action">
										<span class="d-flex w-100 justify-content-start align-items-center">
											<i class="fa-solid  fa-address-card mr-3"></i>
											<span class="menu-collapsed" >Admin</span>
										</span>
									</a>
								</li>
								<%}else{%>
								<li>
								
									<a href="EmployeeDetails.htm"
										class="bg-dark list-group-item list-group-item-action">
										<span class="d-flex w-100 justify-content-start align-items-center">
											<i class="fa-solid  fa-address-card mr-3"></i>
											<span class="menu-collapsed" >Profile</span>
										</span>
									</a>
								</li>
								<%}%>
								<li>
									<a href="CHSSDashboard.htm"
										class="bg-dark list-group-item list-group-item-action">
										<span class="d-flex w-100 justify-content-start align-items-center">
											<i class="fa-solid fa-hospital-user  mr-3"></i>
											<span class="menu-collapsed" >CHSS</span>
										</span>
									</a>
								</li> 
								
								
							</ul>
						</div>  --%>
							

		<div id="content" >
        	<nav class="navbar navbar-expand-lg navbar-light  " style="padding: 0.1rem 1rem !important;">              
            	<div class="container-fluid">

					<a class="navbar-brand" href="MainDashBoard.htm" id="brandname"	style=" font-family: 'Montserrat', sans-serif; color: white;text-align: initial;width:40% ">
						<img class="headerlogo" src="view/images/lablogoui.png" alt=""><b style="font-family: Montserrat, sans-serif;font-size: 19px"> &nbsp; CHSS &nbsp;&nbsp;</b>
						<span id="p1" style="font-family:Lato, sans-serif;font-size: 19px;font-weight: 700; color: orange;"></span>
						<span style="font-family: Lato, sans-serif;font-size: 15px;padding: 0px 16px 0px 10px;text-transform: capitalize !important;"><%=LocalDate.now().getMonth() %> &nbsp; <%=LocalDate.now().getYear() %> </span>
					</a>

					


					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						
						<div class="mr-auto" style="margin-left: 20px; color: white;">
	                               <%--  <span style="font-size: 20px"><%=EmpName %></span>  --%>
	                    </div> 
	                    
	                    <!-- Search Bar -->
	                    
						
							
							<ul class="navbar-nav ml-auto ">
								
								<li class="nav-item active">
									<a class=" btn bg-transparent custom-button " href="MainDashBoard.htm" type="button" aria-haspopup="true" aria-expanded="false" style="background-color: transparent" ><i class="fa fa-home"	aria-hidden="true" ></i> Home</a> 	
								</li>

								<li class="nav-item dropdown">
									<ul class="navbar-nav" id="module">
										
				              		</ul>
								</li>		
							</ul>
							
							
						<div class="btn-group ">
						<%-- <%if(request.getRequestURI().equalsIgnoreCase("/ems/view/static/maindashboard.jsp")){ %>
							<form action="EmpLogitypeChange.htm" class="form-inline my-2 my-lg-0" method="post">
								<div class=" search rounded rounded-pill shadow-sm ">
									<div class="input-group">
										<select class="form-control select2" name="logintype" onchange="this.form.submit();">
											<%for(Object[] login:emplogintypelist){ %>
												<option value="<%=login[0]%>" <%if(logintype.equalsIgnoreCase(login[0].toString())){ %>selected <%} %>><%=login[1]%></option>
											<%} %>
										</select>
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									</div>
								</div>
							</form> 
						<%} %> --%>
	
							<a class="nav-link  onclickbell" href="" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					            <img alt="logo" src="view/images/alarm.png" >
						            <span class="badge badge-danger badge-counter" id="NotificationCount"></span>
						            <i class="fa fa-caret-down " aria-hidden="true" style="padding-left:5px;color: #ffffff"></i>
					        </a>
					        <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in custombell" aria-labelledby="alertsDropdown" style="width:400px;padding: 0px;margin-top: 6px; ">
								<span class="dropdown-header" style="background-color: #faa51e;font-size: 16px;color: #145374; margin-top: -1px;border-top-left-radius: 3px;border-top-right-radius: 3px;font-weight: 700"><i class="fa-solid fa-bell"></i>&nbsp;&nbsp;&nbsp;&nbsp;Notifications</span>


						        <div id="Notification">
						        </div>
						        
						            <a class="dropdown-item text-center small text-gray-500 showall" href="AllNotificationList.htm" style="height: 30px;font-size: 13px;color: black;" >Show All Alerts</a>
						        </div>
						    </div>
						    

							<div class="btn-group">
						        <button type="button" class="btn btn-link btn-responsive" style="text-decoration: none !important" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							    	<img alt="logo" src="view/images/myacc.png" style="">
							            <span style="font-weight: 700;color: white;">&nbsp;<%=Username %></span>
							            <i class="fa fa-caret-down " aria-hidden="true" style="padding-left:5px;color: #ffffff"></i>
							  	</button>
							
							    <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in logout" aria-labelledby="userDropdown">
							    	
								    <a class="dropdown-item" href="#"><i class="fa-solid fa-user"></i> &nbsp;&nbsp;Hi <%=EmpName%>!! </a>
								    <div class="dropdown-divider logoutdivider"></div>
								    
								 	<a class="dropdown-item" href="AuditStamping.htm" target="blank"> &nbsp;&nbsp;<i class="fa fa-history" aria-hidden="true" style="color: purple"></i> &nbsp;&nbsp;Audit Stamping </a>     
								    <div class="dropdown-divider logoutdivider"></div>
								 
								 	<a class="dropdown-item" href="PasswordChange.htm" >&nbsp;<i class="fa fa-key" aria-hidden="true" style="color: cornflowerblue"></i> &nbsp;&nbsp;Change Password </a>
								    <div class="dropdown-divider logoutdivider"></div>
								   
								   <a class="dropdown-item" href="UserManualDoc.htm" target="blank"><img src="view/images/handbook.png"/> &nbsp;&nbsp;Manual </a>
								    <div class="dropdown-divider logoutdivider"></div>
								    
								    <a class="dropdown-item" href="WorkFlow.htm" target="blank" ><img src="view/images/work.png"/>&nbsp;&nbsp; Work Flow</a>
								    <div class="dropdown-divider logoutdivider"></div>
						
								   	<a class="dropdown-item" href="#" target="blank"> &nbsp;<i class="fa fa-cog" aria-hidden="true" style="color: green"></i> &nbsp;&nbsp;HELP </a>
								  	<div class="dropdown-divider logoutdivider"></div>
									
									<input type="hidden" value="<%=logintype %>" name="logintype" id="logintype">
								            
								    	<form id="logoutForm" method="POST" action="${pageContext.request.contextPath}/logout">
									        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								                <button class="dropdown-item " href="#" data-target="#logoutModal" style="font-weight: 700">
								                   &nbsp; <i class="fa fa-sign-out fa-1.5x" aria-hidden="true" style="color: #B20600"></i>
								                    &nbsp;&nbsp;Logout
								                </button>
								        </form>
							        
							    </div>
							</div>
						</div>
					</div>
			 </nav>


<script type="text/javascript">

$(document).ready(function() {
	
	function english_ordinal_suffix(dt)
	{
	  return dt.getDate()+(dt.getDate() % 10 == 1 && dt.getDate() != 11 ? 'st' : (dt.getDate() % 10 == 2 && dt.getDate() != 12 ? 'nd' : (dt.getDate() % 10 == 3 && dt.getDate() != 13 ? 'rd' : 'th'))); 
	}
	
	dt= new Date();
	document.getElementById("p1").innerHTML = english_ordinal_suffix(dt);
	
});

    $('#NotificationCount').html(0);
            //Hide submenus
            $('#body-row .collapse').collapse('hide');

            // Collapse/Expand icon
            $('#collapse-icon').addClass(
                'fa-angle-double-left');

            // Collapse click
            $('.sidebar-colapse').click(
                function() {
                    SidebarCollapse();
                });

            function SidebarCollapse() {

                $('.menu-collapsed').toggleClass('d-none');
                $('.sidebar-submenu').toggleClass('d-none');
                $('.submenu-icon').toggleClass('d-none');
                $('.brand-name').toggleClass('d-none');
                $('#sidebar-container')
                    .toggleClass('sidebar-expanded sidebar-collapsed');

                // Treating d-flex/d-none on separators with title
                var SeparatorTitle = $('.sidebar-separator-title');
                if (SeparatorTitle.hasClass('d-flex')) {
                    SeparatorTitle.removeClass('d-flex');
                } else {
                    SeparatorTitle.addClass('d-flex');
                }

                // Collapse/Expand icon
                $('#collapse-icon').toggleClass('fa-angle-double-left fa-angle-double-right');
            } 
    </script> 
    
    
    
    <script type="text/javascript">
    
	$(document).ready(function() {

		$('.selectdee').select2();
		
		$.ajax({
			type : "GET",
			url : "HeaderModuleList.htm",
			
			datatype : 'json',
			success :  function(result){
				
				var result = JSON.parse(result);
				var values = Object.keys(result).map(function(e){
					return result[e]
				})
				var module= "";
				var logintype= $('#logintype').val();
				var chsscount=0;
				for(i=0; i<values.length;i++)
				{
					var name=values[i][1].replace(/ /g,'');
					module+='<li class="nav-item dropdown " >  <a href="'+values[i][2]+'" class=" btn bg-transparent custom-button" >'+name+'</a></li>';
					if(name.trim()==='CHSS'){
						chsscount=1;
					}
				}
				if(chsscount==0){
				module+='<li class="nav-item dropdown " >  <a href="CHSSDashboard.htm" class=" btn bg-transparent custom-button" >CHSS</a></li>';
				}
				$('#module').html(module); 
				
			}	
		})	
	});
    
	
	$(document).ready(function(){
		
		$.ajax({
			type : "GET",
			url : "NotificationList.htm",
			
			datatype : 'json',
			success : function(result) {
				
				var result = JSON.parse(result);
				var values = Object.keys(result).map(function(e) {
					  return result[e]
					});
				var module = "";
				for (i = 0; i < values.length; i++) {
					
					
				
					module+="<a class='dropdown-item d-flex align-items-center' id='"+values[i].NotificationId+"'  onclick='RemNotification("+values[i].NotificationId+")' href='"+values[i].NotificationUrl+"'  style=' font-family:'Quicksand', sans-serif; '> <div> <i class='fa fa-arrow-right' aria-hidden='true' style='color:green'></i></div> <div style='margin-left:20px'> " +values[i].NotificationMessage+" </div> </a> <div class='dropdown-divider logoutdivider'></div>";
					if(i>4){
						break;
					}
			   
				}
			
				if(values.length==0){
					
					var info="No Notifications !";
					var empty="";
					empty+="<a class='dropdown-item d-flex align-items-center' href=# style=' font-family:'Quicksand', sans-serif; '> <div> <i class='fa fa-comment-o' aria-hidden='true' style='color:green;font-weight:800'></i></div> <div style='margin-left:20px'>" +info+" </div> </a>";

					$('#Notification').html(empty); 
					$('.showall').hide();
					$('#NotificationCount').addClass('badge-success');
				}
				
				if(values.length>0){
	 			
					$('#Notification').html(module);
					$('.showall').show();
					
				
				}
				
				
				
				$('#NotificationCount').html(values.length); 
			}
		});
		
	});

	function RemNotification(notifyid){
		
		var notificationid=notifyid;
		
		$.ajax({
			type : "GET",
			url : "NotificationUpdate.htm",
			data : {
					notificationid : notificationid,
					
				},
			datatype : 'json',
			success : function(result) {
				
			}
		});
		
		
	}


    
    </script>
        </body>

        </html>