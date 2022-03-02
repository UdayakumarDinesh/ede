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


</head>

<body>
            <% String Username=(String)session.getAttribute("Username"); String
                EmpName=(String)session.getAttribute("EmpName"); %>


                <div class="wrapper">

                    <div id="sidebar-container" class="sidebar-expanded  ">
						<div class="navbar-brand ml-3 w-100" style="margin: 2.5px 0px;">
							<div style="">
								<span  style="font-size: 1.7rem; color: #ffff">
									<img src="view/images/works.png" width="30" height="30" alt="">
									<span class="menu-collapsed" style="font-size: 22px;" >EMS</span>
								</span>
							</div>
						</div>
						<ul class="list-group">
							<li>
									<a href="PisAdminDashboard.htm"
										class="bg-dark list-group-item list-group-item-action">
										<span class="d-flex w-100 justify-content-start align-items-center">
											<i class="fa-solid fa-fw fa-address-card mr-3"></i>
											<span class="menu-collapsed" >Admin</span>
										</span>
									</a>
								</li>
							</ul>
						</div>
							

                    <div id="content" >
                        <nav class="navbar navbar-expand-lg navbar-light  ">
                          
                            	
                             <button type="button" id="sidebarCollapse" class="btn btn-sm sidebar-colapse" >
                              	<i class="fa fa-align-left"></i>
                             </button>
                             <div class="mr-auto" style="margin-left: 20px; color: white;">
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
								
							</div>

                        </nav>



                        <script>
                            //Hide submenus
                            $('#body-row .collapse').collapse('hide');

                            // Collapse/Expand icon
                            $('#collapse-icon').addClass(
                                'fa-angle-double-left');

                            // Collapse click
                            $('.sidebar-colapse').click(
                                function () {
                                    SidebarCollapse();
                                });

                            function SidebarCollapse() {
                                console.log('kjfbasjdfb');
                                $('.menu-collapsed').toggleClass('d-none');
                                $('.sidebar-submenu').toggleClass('d-none');
                                $('.submenu-icon').toggleClass('d-none');
                                $('.brand-name').toggleClass('d-none');
                                $('#sidebar-container')
                                    .toggleClass(
                                        'sidebar-expanded sidebar-collapsed');

                                // Treating d-flex/d-none on separators with title
                                var SeparatorTitle = $('.sidebar-separator-title');
                                if (SeparatorTitle.hasClass('d-flex')) {
                                    SeparatorTitle.removeClass('d-flex');
                                } else {
                                    SeparatorTitle.addClass('d-flex');
                                }

                                // Collapse/Expand icon
                                $('#collapse-icon')
                                    .toggleClass(
                                        'fa-angle-double-left fa-angle-double-right');
                            }
                        </script>
                        <script type="text/javascript">




                        </script>
        </body>

        </html>