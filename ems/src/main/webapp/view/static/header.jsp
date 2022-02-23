<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0 " />
	<meta name="viewport" content="width=device-height, initial-scale=1.0 " />
	<title>EMS</title>
	
	<spring:url value="/webresources/css/dashboard.css" var="dashboardCss" />
	<link href="${dashboardCss}" rel="stylesheet" /> 
	
	<jsp:include page="dependancy.jsp"></jsp:include>
</head>
<body>
<% 	
	String Username =(String)session.getAttribute("Username");  
 	String EmpName =(String)session.getAttribute("EmpName");  
%>

  	<!-- Bootstrap NavBar -->
  	
<nav class="navbar navbar-expand-md navbar-dark ">
	<span class="navbar-brand" >
		<img src="view/images/works.png"  width="30" height="30" class="d-inline-block align-top" alt="">
		<span class="menu-collapsed" style="margin-right: 60px !important ;">EMS</span>
	</span>
	
	<div class="collapse navbar-collapse" id="navbarNavDropdown">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item">
				<span data-toggle="sidebar-colapse" class="nav-link" >
					<i id="collapse-icon" class="fa-solid fa-bars" style="color: white "></i>
				</span>
			</li>
			<li class="nav-item active">
				<a class="nav-link" href="MainDashBoard.htm">Home <span class="sr-only">(current)</span></a>
			</li>
		</ul>
		<div class="navbar-nav ml-auto">
			<div class="navbar-nav mr-auto">
				<div class="nav-item">
					<div class="dropdown">
					  <a class="btn dropdown-toggle nav-link" style="color: white;"  type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					    <%=Username %>
					  </a>
					  <div class="dropdown-menu dropdown-menu-right" style="margin-top: 10px;" aria-labelledby="dropdownMenuButton">
					    <a class="dropdown-item" href="#">Action</a>
					    <a class="dropdown-item" href="#">Another action</a>
					    <a class="dropdown-item" href="#">Something else here </a>
					  </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</nav>
<!-- NavBar END -->

	<div class="row" id="body-row">
	    <!-- Sidebar -->
	    <div id="sidebar-container" class="sidebar-expanded d-none d-md-block"><!-- d-* hiddens the Sidebar in smaller devices. Its itens can be kept on the Navbar 'Menu' -->
	        <!-- Bootstrap List Group -->
	        <ul class="list-group">
	            <!-- Menu with submenu -->
	            <li>
		            <a href="#submenu1" data-toggle="collapse" aria-expanded="false" class="bg-dark list-group-item list-group-item-action flex-column align-items-start">
		                <span class="d-flex w-100 justify-content-start align-items-center">
		                    <span class="fa fa-dashboard fa-fw mr-3"></span> 
		                    <span class="menu-collapsed">Dashboard</span>
		                    <span class="submenu-icon ml-auto"></span>
		                </span>
		            </a>
	           		<!-- Submenu content -->
		            <div id='submenu1' class="collapse sidebar-submenu">
		                <a href="#" class="list-group-item list-group-item-action bg-dark text-white">
		                    <span class="menu-collapsed">Charts</span>
		                </a>
		                <a href="#" class="list-group-item list-group-item-action bg-dark text-white">
		                    <span class="menu-collapsed">Reports</span>
		                </a>
		                <a href="#" class="list-group-item list-group-item-action bg-dark text-white">
		                    <span class="menu-collapsed">Tables</span>
		                </a>
		            </div>
	            </li>
	            <li>
	            <a href="#submenu2" data-toggle="collapse" aria-expanded="false" class="bg-dark list-group-item list-group-item-action flex-column align-items-start">
	                <span class="d-flex w-100 justify-content-start align-items-center">
	                    <span class="fa fa-user fa-fw mr-3"></span>
	                    <span class="menu-collapsed">Profile</span>
	                    <span class="submenu-icon ml-auto"></span>
	                </span>
	            </a>
	            <!-- Submenu content -->
		            <div id='submenu2' class="collapse sidebar-submenu">
		                <a href="#" class="list-group-item list-group-item-action bg-dark text-white">
		                    <span class="menu-collapsed">Settings</span>
		                </a>
		                <a href="#" class="list-group-item list-group-item-action bg-dark text-white">
		                    <span class="menu-collapsed">Password</span>
		                </a>
		            </div>            
	            </li>
	            <li>
		            <a href="#" class="bg-dark list-group-item list-group-item-action">
		                <span class="d-flex w-100 justify-content-start align-items-center">
		                    <span class="fa fa-tasks fa-fw mr-3"></span>
		                    <span class="menu-collapsed">Tasks</span>    
		                </span>
		            </a>
	            </li>
	            <!-- Separator with title -->
	            <!-- <li class="list-group-item sidebar-separator-title text-muted d-flex align-items-center menu-collapsed">
	                <small>OPTIONS</small>
	            </li> -->
	            <!-- /END Separator -->
	            <li>
		            <a href="#" class="bg-dark list-group-item list-group-item-action">
		                <span class="d-flex w-100 justify-content-start align-items-center">
		                    <span class="fa fa-calendar fa-fw mr-3"></span>
		                    <span class="menu-collapsed">Calendar</span>
		                </span>
		            </a>
	            </li>
	            <li>
		            <a href="#" class="bg-dark list-group-item list-group-item-action">
		                <span class="d-flex w-100 justify-content-start align-items-center">
		                    <i class="fa-solid fa-message mr-3"></i>
		                    <span class="menu-collapsed">Messages <!-- <span class="badge badge-pill badge-primary ml-2">5</span> --></span>
		                </span>
		            </a>
	            </li>
	            
	            <li>
	            	<form action="${pageContext.request.contextPath}/logout" method="post">
	            		<a href="javascript:;" class="bg-dark list-group-item list-group-item-action" onclick="parentNode.submit();">
			                <span class="d-flex w-100 justify-content-start align-items-center" >
			                   <i class="fa-solid fa-right-from-bracket mr-3"></i>
			                    <span class="menu-collapsed">Logout</span>
			                </span>
			            </a>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			        </form>
	         	</li>
	       
	        
	        
	        	<li>
		            <a href="#" class="bg-dark list-group-item list-group-item-action">
		                <span class="d-flex w-100 justify-content-start align-items-center">
		                    <span class="fa fa-question fa-fw mr-3"></span>
		                    <span class="menu-collapsed">Help</span>
		                </span>
		            </a>
	            </li>
	       </ul><!-- List Group END-->
	    </div><!-- sidebar-container END -->
	

	
	



<script>
//Hide submenus
$('#body-row .collapse').collapse('hide'); 

// Collapse/Expand icon
$('#collapse-icon').addClass('fa-angle-double-left'); 

// Collapse click
$('[data-toggle=sidebar-colapse]').click(function() {
    SidebarCollapse();
});

function SidebarCollapse () {
    $('.menu-collapsed').toggleClass('d-none');
    $('.sidebar-submenu').toggleClass('d-none');
    $('.submenu-icon').toggleClass('d-none');
    $('#sidebar-container').toggleClass('sidebar-expanded sidebar-collapsed');
    
    // Treating d-flex/d-none on separators with title
    var SeparatorTitle = $('.sidebar-separator-title');
    if ( SeparatorTitle.hasClass('d-flex') ) {
        SeparatorTitle.removeClass('d-flex');
    } else {
        SeparatorTitle.addClass('d-flex');
    }
    
    // Collapse/Expand icon
    $('#collapse-icon').toggleClass('fa-angle-double-left fa-angle-double-right');
}
</script>
<script type="text/javascript">




</script>


</body>
</html>