<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<title>EMS</title>
	
	<spring:url value="/resources/css/dashboard.css" var="dashboardCss" />
	<link href="${dashboardCss}" rel="stylesheet" /> 
	<jsp:include page="dependancy.jsp"></jsp:include>
	
</head>

<body>


  	<!-- Bootstrap NavBar -->
	<nav class="navbar navbar-expand-md navbar-dark ">
	  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	  <a class="navbar-brand" href="#">
	    <img src="view/images/works.png"  width="30" height="30" class="d-inline-block align-top" alt="">
	    <span class="menu-collapsed">EMS</span>
	  </a>
	  <div class="collapse navbar-collapse" id="navbarNavDropdown">
	    <ul class="navbar-nav">
	    
	      <li class="nav-item">
	        <a href="#" data-toggle="sidebar-colapse" class="nav-link" href="#">
		        <!-- <span id="collapse-icon" class="fa fa-2x mr-3"></span>
		        <span id="collapse-text" class="fa fa-2x mr-3"></span> -->
		        <i id="collapse-icon" class="fa-solid fa-bars"></i>
	      	</a>
	      </li>
	      <li class="nav-item active">
	        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#">Features</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#">Pricing</a>
	      </li>
	      
	      <!-- This menu is hidden in bigger devices with d-sm-none. 
	           The sidebar isn't proper for smaller screens imo, so this dropdown menu can keep all the useful sidebar itens exclusively for smaller screens  -->
	      <li class="nav-item dropdown d-sm-block d-md-none">
	        <a class="nav-link dropdown-toggle" href="#" id="smallerscreenmenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          Menu
	        </a>
	        <div class="dropdown-menu" aria-labelledby="smallerscreenmenu">
	            <a class="dropdown-item" href="#">Dashboard</a>
	            <a class="dropdown-item" href="#">Profile</a>
	            <a class="dropdown-item" href="#">Tasks</a>
	            <a class="dropdown-item" href="#">Etc ...</a>
	        </div>
	      </li><!-- Smaller devices menu END -->
	      
	    </ul>
	  </div>
	</nav>
<!-- NavBar END -->

	<div class="row" id="body-row">
	    <!-- Sidebar -->
	    <div id="sidebar-container" class="sidebar-expanded d-none d-md-block"><!-- d-* hiddens the Sidebar in smaller devices. Its itens can be kept on the Navbar 'Menu' -->
	        <!-- Bootstrap List Group -->
	        <ul class="list-group">
	           
	            <!-- Menu with submenu -->
	            <a href="#submenu1" data-toggle="collapse" aria-expanded="false" class="bg-dark list-group-item list-group-item-action flex-column align-items-start">
	                <div class="d-flex w-100 justify-content-start align-items-center">
	                    <span class="fa fa-dashboard fa-fw mr-3"></span> 
	                    <span class="menu-collapsed">Dashboard</span>
	                    <span class="submenu-icon ml-auto"></span>
	                </div>
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
	            <a href="#submenu2" data-toggle="collapse" aria-expanded="false" class="bg-dark list-group-item list-group-item-action flex-column align-items-start">
	                <div class="d-flex w-100 justify-content-start align-items-center">
	                    <span class="fa fa-user fa-fw mr-3"></span>
	                    <span class="menu-collapsed">Profile</span>
	                    <span class="submenu-icon ml-auto"></span>
	                </div>
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
	            <a href="#" class="bg-dark list-group-item list-group-item-action">
	                <div class="d-flex w-100 justify-content-start align-items-center">
	                    <span class="fa fa-tasks fa-fw mr-3"></span>
	                    <span class="menu-collapsed">Tasks</span>    
	                </div>
	            </a>
	            
	            <!-- Separator with title -->
	            <!-- <li class="list-group-item sidebar-separator-title text-muted d-flex align-items-center menu-collapsed">
	                <small>OPTIONS</small>
	            </li> -->
	            <!-- /END Separator -->
	            
	            <a href="#" class="bg-dark list-group-item list-group-item-action">
	                <div class="d-flex w-100 justify-content-start align-items-center">
	                    <span class="fa fa-calendar fa-fw mr-3"></span>
	                    <span class="menu-collapsed">Calendar</span>
	                </div>
	            </a>
	            <a href="#" class="bg-dark list-group-item list-group-item-action">
	                <div class="d-flex w-100 justify-content-start align-items-center">
	                    <span class="fa fa-calendar fa-fw mr-3"></span>
	                    <span class="menu-collapsed">Messages <span class="badge badge-pill badge-primary ml-2">5</span></span>
	                </div>
	            </a>
	            
	            <a href="#" class="bg-dark list-group-item list-group-item-action">
	                <div class="d-flex w-100 justify-content-start align-items-center">
	                    <span class="fa fa-question fa-fw mr-3"></span>
	                    <span class="menu-collapsed">Help</span>
	                </div>
	            </a>
	         
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



</body>




</html>