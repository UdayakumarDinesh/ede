<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.time.LocalDate"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">

<title>SEIP</title>
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

.custom-button{
	background-color: #0E6FB6;;
	color: white !important;
	font-weight: 100 !important;
    

}

.bg-transparent{
	   
    background-color: transparent;
    text-transform: capitalize;
    color: white;
    font-weight: 100;
    padding: 4px 7px;
}

.onclickbell{
	    margin: 0px 0px 0px 10px;
}

.logoutdivider{
	margin: 0px !important
}

.logout a{
	font-size: 14px !important;
	font-weight: 800 !important;
}



</style>

<style type="text/css">
	
	@media (min-width: 992px){
	.dropdown-menu .dropdown-toggle:after{
		border-top: .3em solid transparent;
	    border-right: 0;
	    border-bottom: .3em solid transparent;
	    border-left: .3em solid;
	}
	.dropdown-menu .dropdown-menu{
		margin-left:0;
		margin-right: 0;
	}
	.dropdown-menu li{
		position: relative;
	}
	.nav-item .submenu{ 
		display: none;
		position: absolute;
		left:100%; 
		top:-7px;
	}
	.nav-item .submenu-left{ 
		right:100%;
		left:auto;
	}
	.dropdown-menu > li:hover{ background-color: #f1f1f1 }
	.dropdown-menu > li:hover > .submenu{
		display: block;
	}
}
</style>


</head>

<body>
         
	<% String Username = (String)session.getAttribute("Username");
       String logintype   = (String)session.getAttribute("LoginType"); 
       String EmpName     = (String)session.getAttribute("EmpName");
    	
       
    %>
      
	<div class="wrapper">


		<div id="content" >
        	<nav class="navbar navbar-expand-lg navbar-light  " style="padding: 0.1rem 0.5rem !important;">              
            	<div class="container-fluid">
					<button class=" btn btn-sm" id = "header-sidebar-menu-btn"  type="button" style="display: none; margin-right: 5px;background-color: #143F6B;color: #FFFFFF;" onclick="SideBarToggle()"  data-toggle="tooltip" data-placement="right" title="Sidebar Menu">
						<i class="fa-solid fa-bars"></i>
					</button>
					<a class="navbar-brand" href="MainDashBoard.htm" id="brandname"	style=" margin-left: 5px ;margin-right: 5px;font-family: 'Montserrat', sans-serif; color: white;text-align: initial;width:6% ">
						<img class="" style="width: 28%;" src="view/images/lablogoui.png" alt=""><b style="font-family: Montserrat, sans-serif;font-size: 19px"> &nbsp; SEIP &nbsp;&nbsp;</b>
					</a>
						<span style="margin-bottom: -10px;">
							<span id="p1" style="font-family:Lato, sans-serif;font-size: 12px;font-weight: 700; color: orange;"></span>
							<span style="font-family: Lato, sans-serif;font-size: 12px;padding: 0px 0px 0px 5px;text-transform: capitalize !important;color: white;"><%=LocalDate.now().getMonth().toString().substring(0,3) %> &nbsp; <%=LocalDate.now().getYear() %> </span>
						</span>
						<span>
							
						</span>

					<div class="collapse navbar-collapse" id="navbarSupportedContent">
	                    <!-- Search Bar -->
							<ul class="navbar-nav ml-auto ">
								
								<li class="nav-item active">
									<a class=" btn bg-transparent custom-button " href="MainDashBoard.htm" type="button" aria-haspopup="true" aria-expanded="false" style="background-color: transparent" data-toggle="tooltip" data-placement="bottom" title="Home"><i class="fa fa-home"	aria-hidden="true" ></i></a> 	
								</li>

								<li class="nav-item dropdown">
									<ul class="navbar-nav" id="module">
										
				              		</ul>
								</li>		
							</ul>
							
							
							
						
					        <%if(!logintype.equalsIgnoreCase("CE")){ %>
					        <div class="btn-group ">
								<a class="nav-link  onclickbell" style="padding: 0.25rem 0.5rem;" href="EMSTodaysNotices.htm" id="NoticeButton" target="blank" data-toggle="tooltip" data-placement="top" title="Notices">
						            <img alt="logo" src="view/images/notice.png" >
							            <span class="badge badge-danger badge-counter" id="NoticeCount"></span>
						        </a>
						  	</div>
							    <%} %>
						<div class="btn-group ">
	
							<a class="nav-link  onclickbell" style="padding: 0.25rem 0.5rem;"  href="" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
					            <img alt="logo" src="view/images/alarm.png" >
						            <span class="badge badge-danger badge-counter" id="NotificationCount"></span>
						            <i class="fa fa-caret-down " aria-hidden="true" style="color: #ffffff"></i>
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
								    
								 	<a class="dropdown-item" href="AuditStamping.htm"> &nbsp;&nbsp;<i class="fa fa-history" aria-hidden="true" style="color: purple"></i> &nbsp;&nbsp;Audit Stamping </a>     
								    <div class="dropdown-divider logoutdivider"></div>
								 
								 	<a class="dropdown-item" href="PasswordChange.htm" >&nbsp;<i class="fa fa-key" aria-hidden="true" style="color: cornflowerblue"></i> &nbsp;&nbsp;Change Password </a>
								    <div class="dropdown-divider logoutdivider"></div>
								    
								<!--    <a class="dropdown-item" href="EMSForms.htm" ><img src="view/images/forms.png"/> &nbsp;&nbsp;&nbsp;&nbsp;Forms </a>
								    <div class="dropdown-divider logoutdivider"></div>
								    
								    <a class="dropdown-item" href="EMSNotices.htm" ><img src="view/images/notice.png"/> &nbsp;&nbsp;&nbsp;&nbsp;Notice </a>
								    <div class="dropdown-divider logoutdivider"></div> -->
								    
								   <a class="dropdown-item" href="UserManualDoc.htm" target="blank"><img src="view/images/handbook.png"/> &nbsp;&nbsp;Manual </a>
								    <div class="dropdown-divider logoutdivider"></div>
								    
								    <a class="dropdown-item" href="WorkFlow.htm" target="blank" ><img src="view/images/work.png"/>&nbsp;&nbsp; Work Flow</a>
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

function SideBarToggle(){
	$('#sidebar').toggle();
}


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
			url : "HeaderOptionList.htm",
			
			datatype : 'json',
			success :  function(result){
				
				var result = JSON.parse(result);
				var values = Object.keys(result).map(function(e){
					return result[e]
				})
				var module= "";
				var logintype= $('#logintype').val();
				
				for(i=0; i<values.length;i++)
				{
					var name=values[i][1].replace(/ /g,'');
					/* module+='<li class="nav-item dropdown " >  <a href="'+values[i][2]+'" class=" btn bg-transparent custom-button" >'+name+'</a></li>'; */
					
					module+="<li class='nav-item dropdown uppernav ' style='padding: 0rem 0.25rem'>"
					module+= "<button type='button' class='btn dropdown-toggle custom-button'  value='"+values[i][0]+"_"+values[i][2]+"' id='header-btn-"+values[i][0]+"'  data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' onclick='checkme("+values[i][0]+")' >"+values[i][1]+"</button> <div class='dropdown-menu dropdown-menu-right' id='scheduledropdown"+values[i][0]+"' style='width:13rem'> </div></li>";
					
				}
				$('#module').html(module); 
				
			}	
		})	
	});
    
	
	function checkme(value){

		  var $formOptionid =  $("#header-btn-"+value).val(); 
				
	      var $logintype = $('#logintype').val(); 
	      	      
						$.ajax({

							type : "GET",
							url :  "HeaderModuleDropDownList.htm" ,
							data : {
								formOptionId : $formOptionid
							},
							datatype : 'json',
							success : function(result) {

								var result = JSON.parse(result);
							
								
								var values = Object.keys(result).map(function(e) {
								  return result[e]
								})
								
								var s = '';
								for (i = 0; i < values.length; i++) {
									
									s += '<a class="dropdown-item" href="'+values[i][1]+'">' +values[i][0]+ '</a>';
									
                                   }
								
								$('#scheduledropdown'+value).html(s);
				
							}
						});
		
		}
	
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

	
	$(document).ready(function(){
		
		$.ajax({
			type : "GET",
			url : "AllNoticeCount.htm",
			
			datatype : 'json',
			success : function(result) {
				
				var result = JSON.parse(result);
		
				$('#NoticeCount').html(result); 
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