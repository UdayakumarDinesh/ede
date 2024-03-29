<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.time.LocalDate"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<spring:url value="/webresources/css/master.css" var="MasterCss" />     
<link href="${MasterCss}" rel="stylesheet" />

	
<style type="text/css">

/* ---------------------------------------------------
    SIDEBAR STYLE
----------------------------------------------------- */

.wrapper {
    display: flex;
    width: 100%;
    align-items: stretch;
}

#sidebar {
    min-width: 215px;
    max-width: 250px;
    /* background: #7386D5; */
    color: #fff;
    transition: all 0.3s;
    margin:5px;
    
}

#sidebar.active {
    toggle-collapse handle margin-left: -250px; 
}


/* ---------------------------------------------------
    CONTENT STYLE
----------------------------------------------------- */

#content {
    width: 100%;
    min-height: 90vh;
    transition: all 0.3s;
}

/* ---------------------------------------------------
    MEDIAQUERIES
----------------------------------------------------- */

@media (max-width: 768px) {
    #sidebar {
        margin-left: -250px;
    }
    #sidebar.active {
        margin-left: 0;
    }
    #sidebarCollapse span {
        display: none;
    }
}

/* ---------------------------------------------------
   NEW SIDEBAR
----------------------------------------------------- */

.nav-pills-custom .nav-link {
    color: white;
    background: #055C9D;
    padding: 0.8rem;
    border-bottom :1px solid #FFFFFF;
   
}

.nav-pills-custom .nav-link.active {
    
    background: #143F6B;
    /* border:2px solid #45b649; */
}

.nav-pills{
	    background-color: #0e6fb6;
    /* height: 600px; */
    padding: 0px;
    border-radius: 5px;
}

/* Add indicator arrow for the active tab */
@media (min-width: 992px) {
    .nav-pills-custom .nav-link::before {
        content: '';
        display: block;
        border-top: 8px solid transparent;
        border-left: 10px solid #fff;
        border-bottom: 8px solid transparent;
        position: absolute;
        top: 50%;
        right: -10px;
        transform: translateY(-50%);
        opacity: 0;
        border-color: transparent transparent transparent #143F6B;
        
    }
}

.nav-pills-custom .nav-link.active::before {
    opacity: 1;
}

.nav-link{
	font-family: 'Muli',sans-serif !important;
}

/* .custom_width{
	padding: 0.5rem 0.5rem !important;
}
 */
.sidebar-container{
	overflow-y:auto !important; 
}

#sidebarmodule .nav-link span{
	font-size: 14px !important;
}

</style>
</head>
<body>

	<%
	  String formmoduleid =(String)session.getAttribute("formmoduleid"); 
	  String activeid =(String)session.getAttribute("SidebarActive");
	%>

  <div class="wrapper">
  
          <!-- Sidebar  -->
          <nav id="sidebar">
          
          		<div class="nav flex-column nav-pills nav-pills-custom sidebar-container" id="v-pills-tab" role="tablist" aria-orientation="vertical">
		
					<div id="sidebarmodule">
		
                     </div>

            	</div>
     
          </nav> 

          <!-- Page Content  -->
          <div id="content">

			 <!-- <button type="button" id="sidebarCollapse" class="btn btn-info">
                          <i class="fas fa-align-left"></i>
                          <span>Toggle Sidebar</span>
                      </button> -->


     

       <!--    </div>
      </div> -->


<script>
$(document).ready(function () {
   /*  $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    }); */
  
    var activeid= '<%=activeid%>';
    var formmoduleid = <%=formmoduleid%>;
    $.ajax({
		type : "GET",
		url : "SidebarModuleList.htm",
		data : {
			formmoduleid:formmoduleid,				
		},
		datatype : 'json',
		success : function(result) {
			
			var result = JSON.parse(result);
			var values = Object.keys(result).map(function(e) {
				  return result[e];
				});
			var sidebarmodule = "";
			for (i = 0; i < values.length; i++) {
				
				sidebarmodule+="<a class='nav-link  shadow custom_width' id='"+values[i][1].replace('.','_') +"'  href='"+values[i][1]+"'><i class='fa-solid fa-star'></i>&nbsp; <span class=' large ' >"+values[i][0]+"</span></a>";
 
			}
			$('#sidebarmodule').html(sidebarmodule); 
			/* $('#'+ window.location.href.split('/')[4].replace('.','_')).addClass('active') */
			$('#'+ activeid).addClass('active');
		}
	});
    
   
    
});

$('#header-sidebar-menu-btn').show();
</script>
</body>
</html>