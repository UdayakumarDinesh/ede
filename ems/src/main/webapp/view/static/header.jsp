<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">

<title>EMS</title>
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



</style>

</head>

<body>
            <% String Username = (String)session.getAttribute("Username");
            String logintype   = (String)session.getAttribute("LoginType"); 
            String EmpName=(String)session.getAttribute("EmpName");%>


                <div class="wrapper">

                    <div id="sidebar-container" class="sidebar-expanded  ">
						<div class="navbar-brand ml-3 w-100" style="margin: 2.5px 0px;">
							<div style="">
								<span  style="font-size: 1.7rem; color: #ffff">
									<img src="view/images/works.png" width="30" height="30" alt="">
									<span class="menu-collapsed" style="font-size: 22px;" >CHSS</span>
								</span>
							</div>
						</div>
						<ul class="list-group" id="module">
					<%--  <%if("A".equalsIgnoreCase(logintype)){%>
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
								
								 --%>
							</ul>
						</div>
							

                    <div id="content" >
                        <nav class="navbar navbar-expand-lg navbar-light  ">
                          
                            	
                           <button type="button" id="sidebarCollapse" class="btn btn-sm sidebar-colapse" >
                           <i class="fa fa-align-left"></i>
                           </button>
                             
                            <%--  <div class="mr-auto" style="margin-left: 20px; color: white;">
                                <span style="font-size: 20px"><%=EmpName %></span> 
                             </div>
                                
                             <div class=" navbar-collapse" id="navbarSupportedContent">
							    <ul class="navbar-nav ml-auto">
									        
							        <li class="nav-item dropdown">
							            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
							                aria-haspopup="true" aria-expanded="false" style="color: white !important;">
						                <%=Username %>&nbsp;
							            </a>
							            <div class="dropdown-menu dropdown-menu-right" style="margin-top: 8px;" aria-labelledby="navbarDropdown">
							                <form action="${pageContext.request.contextPath}/logout" method="post">
											<a class="dropdown-item  dd-item" onclick="parentNode.submit();" href="#">
												<i class="fa-solid fa-right-from-bracket mr-3"></i>
												Logout
											</a>
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
										</form>
							            </div>
							        </li>
								       
							    </ul>
								
							</div> --%>
							
							
							<div class="collapse navbar-collapse" id="navbarSupportedContent">
							 
							     <div class="mr-auto" style="margin-left: 20px; color: white;">
	                                <span style="font-size: 20px"><%=EmpName %></span> 
	                             </div> 
							    <form action="#" class="form-inline my-2 my-lg-0">
							        <div class=" search rounded rounded-pill shadow-sm ">
							            <div class="input-group">
							                <input type="search" placeholder="Search" aria-describedby="button-addon1"
							                    class="form-control border-0 ">
							                <div class="input-group-append" style="background-color: #ffffff;border-bottom-right-radius: 4px;border-top-right-radius: 4px;">
							                    <button id="button-addon1" type="submit" class="btn btn-link text-primary">
							                    <i class="fa fa-search" style="color: blue"></i></button>
							                </div>
							            </div>
							        </div>
							    </form>
							    <div class="btn-group ">
							        <a class="nav-link  onclickbell" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							            <img alt="logo" src="view/images/alarm.png" >
							            <span class="badge badge-danger badge-counter" id="NotificationCount"></span>
							            <i class="fa fa-caret-down " aria-hidden="true" style="padding-left:5px;color: #ffffff"></i>
							        </a>
							        <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in custombell"
							            aria-labelledby="alertsDropdown">
							            <h6 class="dropdown-header">Notifications</h6>
							            <div id="Notification"></div>
							            <a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
							        </div>
							    </div>
							    <div class="btn-group">
							        <button type="button" class="btn btn-link btn-responsive"
							            style="text-decoration: none !important" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							            <img alt="logo" src="view/images/myacc.png" style="">
							            <span style="font-weight: 700;color: white;">&nbsp;<%=Username %></span><i class="fa fa-caret-down "
							                aria-hidden="true" style="padding-left:5px;color: #ffffff"></i>
							        </button>
							
							        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in logout" aria-labelledby="userDropdown">
							            <a class="dropdown-item" href="#"><i class="fa fa-user-circle-o" aria-hidden="true" style="color: green"></i> &nbsp;&nbsp;Hi <%=Username%>  <i class="fas fa-grin-hearts"></i> !! </a>
							
							
							
							            <div class="dropdown-divider"></div>
							 <a class="dropdown-item" href="AuditStamping.htm" target="blank"> <i class="fa fa-history" aria-hidden="true"></i> Audit Stamping </a>     
							            <div class="dropdown-divider"></div>
							 <a class="dropdown-item" href="PasswordChange.htm" ><i class="fa fa-key" aria-hidden="true"></i> Change Password </a>
							            <div class="dropdown-divider"></div>
							   <a class="dropdown-item" href="#" target="blank"> <i class="fa fa-cog" aria-hidden="true" style="color: #142850"></i> HELP </a>
							                    
							                
							               
							
							            <div class="dropdown-divider"></div>
								<input type="hidden" value="<%=logintype %>" name="logintype" id="logintype">
							            <form id="logoutForm" method="POST" action="${pageContext.request.contextPath}/logout">
							                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							                <button class="dropdown-item " href="#" data-target="#logoutModal">
							                    <i class="fa fa-sign-out fa-1.5x" aria-hidden="true" style="color: red"></i>
							                    &nbsp;&nbsp;Logout
							                </button>
							            </form>
							        </div>
							    </div>
							
							</div>

                        </nav>


<script type="text/javascript">

   /*  $(document).ready(function() {
            $.ajax({
                type: "GET",
                url: "NotificationList.htm",

                datatype: 'json',
                success: function(result) {

                    var result = JSON.parse(result);
                    var values = Object.keys(result).map(function(e) {
                        return result[e]
                    });
                    var module = "";
                    for (i = 0; i < values.length; i++) {

                        module += "<a class='dropdown-item d-flex align-items-center' href='" + values[i][4] + "' style=' font-family:'Quicksand', sans-serif; '> <div> <i class='fa fa-arrow-right' aria-hidden='true' style='color:green'></i></div> <div style='margin-left:20px'> " + values[i][3] + " </div> </a>";
                        if (i > 4) {
                            break;
                        }

                    }

                    $('#Notification').html(module);
                    $('#NotificationCount').html(values.length);

                }
            });
    }); */
    
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
                console.log('kjfbasjdfb');
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
				
				console.log(values.length);
				for(i=0; i<values.length;i++){

					var name=values[i][1].replace(/ /g,'');

					
					module+='<li>  <a href="'+values[i][2]+'?formroleid='+values[i][0]+'" class="bg-dark list-group-item list-group-item-action" > <span class="d-flex w-100 justify-content-start align-items-center">  <span class="menu-collapsed" > </span></span><i class="'+values[i][4]+'"></i> '+name+'</a></li>';				
					
				}
			
				$('#module').html(module); 
				
			}	
		})	
	});
    
    
    </script>
        </body>

        </html>