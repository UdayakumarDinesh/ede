<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<%-- <jsp:include page="../static/sidebar.jsp"></jsp:include> --%>
<style>

.nav-pills-custom .nav-link {
    color: #717488;
    background: #dddde8;
    position: relative;
}

.nav-pills-custom .nav-link.active {
    color: white;
    background: #055C9D;
    /* border:2px solid #45b649; */
}

.nav-pills{
	    background-color: #e9ecef;
    height: 600px;
    padding: 11px;
    border-radius: 6px;
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
        border-color: transparent transparent transparent #055C9D;
        
    }
}

.nav-pills-custom .nav-link.active::before {
    opacity: 1;
}

.nav-link{
	font-family: 'Muli',sans-serif;
}

.p-3{
	padding: 2rem !important;
}

/* inside tabs */

p {
    margin: 0px 0px 20px 0px
}

p:last-child {
    margin: 0px
}

a {
    color: #71748d
}

a:hover {
    color: #ff407b;
    text-decoration: none
}

a:active,
a:hover {
    outline: 0;
    text-decoration: none
}

.btn-secondary {
    color: #fff;
    background-color: #ff407b;
    border-color: #ff407b
}

.btn {
    font-size: 14px;
    padding: 9px 16px;
    border-radius: 2px
}

.tab-vertical .nav.nav-tabs {
    float: left;
    display: block;
    margin-right: 0px;
    border-bottom: 0
}

.tab-vertical .nav.nav-tabs .nav-item {
    margin-bottom: 3px
}

.tab-vertical .nav-tabs .nav-link {
    border: 1px solid transparent;
    border-top-left-radius: .25rem;
    border-top-right-radius: .25rem;
    background: #fff;
    padding: 6px 13px;
    color: #71748d;
    background-color: #dddde8;
    -webkit-border-radius: 4px 0px 0px 4px;
    -moz-border-radius: 4px 0px 0px 4px;
    border-radius: 4px 0px 0px 4px
}

.tab-vertical .nav-tabs .nav-link.active {
    color: white;
    background-color: #055C9D !important;
    border-color: transparent !important
}

.tab-vertical .nav-tabs .nav-link {
    border: 1px solid transparent;
    border-top-left-radius: 4px !important;
    border-top-right-radius: 0px !important
}

.tab-vertical .tab-content {
    overflow: auto;
    -webkit-border-radius: 0px 4px 4px 4px;
    -moz-border-radius: 0px 4px 4px 4px;
    border-radius: 0px 4px 4px 4px;
    background: aliceblue;
    padding: 30px;
    font-size: 17px;
}

.tab-pane{
	box-shadow: 4px 7px 5px rgb(0 0 0 / 10%);
	overflow-y: auto; 
}

</style>

</head>
<body>
<%List<Object[]> list = (List<Object[]>)request.getAttribute("dashboard"); %>
 <div class="col page">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>LEAVE DASHBOARD</h5>
			</div>
			<div class="col-md-9 " >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page"><i class=" fa-solid fa-user-doctor  mr-3"></i> Leave</li>
				    <!-- <li class="breadcrumb-item"><a href="#">Library</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Data</li> -->
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	

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
		
		<form action="#" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="card dashboard-card" >
				<div class="card-body " >
					<div class="row" > 
					
						<%if(list!=null){ for(Object[] o:list){%>
						
						<div class="col-md-3" style="margin-bottom: 20px;">
							<button type="submit" class=" db-button w-100" formaction="<%=o[1] %>" ><%=o[0] %></button>
						</div>
						
						<%}}%>
					</div>
				</div>
			</div>		
		</form>
		
	
	<%-- 	<div class="container-fluid dashboard-container">
	
		 <div class="row">
            <div class="col-md-2" >
                <!-- Tabs nav -->
                <div class="nav flex-column nav-pills nav-pills-custom" id="v-pills-tab" role="tablist" aria-orientation="vertical">

					 <a class="nav-link mb-3 shadow custom_width" id="v-pills-authority-tab" data-toggle="pill" href="#v-pills-authority" role="tab" aria-controls="v-pills-authority" aria-selected="false">
                        <i class="fa fa-id-badge mr-2" ></i>
                        <span class="font-weight-bold large ">Authority</span></a>

					<%if(list!=null){ for(Object[] o:list){%>
						
						<a class="nav-link mb-3 shadow custom_width" id="v-pills-authority-tab" data-toggle="pill" href="#v-pills-authority" role="tab" aria-controls="v-pills-authority" aria-selected="false">
                        		<i class="fa fa-id-badge mr-2" ></i>
                        <span class="font-weight-bold large "><%=o[0] %></span></a>
						
						<%}}%>



                    <!--< a class="nav-link mb-3 shadow custom_width" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab" aria-controls="v-pills-profile" aria-selected="false">
                        <i class="fa fa-check mr-2"></i>
                        <span class="font-weight-bold large ">Details</span></a>

                    <a class="nav-link mb-3 shadow custom_width" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab" aria-controls="v-pills-messages" aria-selected="false">
                        <i class="fa fa-star mr-2"></i>
                        <span class="font-weight-bold large ">Cost</span></a>

                    <a class="nav-link mb-3 shadow custom_width" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false">
                        <i class="fa fa-calendar-minus-o mr-2"></i>
                        <span class="font-weight-bold large ">Schedule</span></a>
                     
                    <a class="nav-link mb-3 shadow custom_width" id="v-pills-attachment-tab" data-toggle="pill" href="#v-pills-attachment" role="tab" aria-controls="v-pills-attachment" aria-selected="false">
                        <i class="fa fa-paperclip mr-2"></i>
                        <span class="font-weight-bold large ">Attachment</span></a>    
                        
                     <a class="nav-link mb-3 shadow custom_width" id="v-pills-prints-tab" data-toggle="pill" href="#v-pills-prints" role="tab" aria-controls="v-pills-prints" aria-selected="false">
                        <i class="fa fa-print mr-2" aria-hidden="true"></i>
                        <span class="font-weight-bold large ">Prints</span></a>   --> 

            	</div>
            </div>


            <div class="col-md-10" style="padding-left: 0px !important">
                <!-- Tabs content -->
                <div class="tab-content" id="v-pills-tabContent">
                
				<!-- ********************AUTHORITY*********************************************** -->               
                    
                 
                <div class="tab-pane fade shadow rounded bg-white p-3" id="v-pills-authority" role="tabpanel" aria-labelledby="v-pills-authority-tab">
                	<div class="row" >			 			
	
	 				</div>
      			</div>   
                         
                    
                    
				<!--  **** DETAILS *********************     -->
                    
                <div class="tab-pane fade shadow rounded bg-white p-2" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">
                	<div class="container " style="margin: 0px !important;padding: 0px !important">
						<div class="col">
							<div class="tab-vertical">
						            <ul class="nav nav-tabs" id="myTab3" role="tablist">
						                <li class="nav-item"> <a class="nav-link active" id="req-vertical-tab" data-toggle="tab" href="#req-vertical" role="tab" aria-controls="home" aria-selected="true">Requirement</a> </li>
						                <li class="nav-item"> <a class="nav-link" id="obj-vertical-tab" data-toggle="tab" href="#obj-vertical" role="tab" aria-controls="profile" aria-selected="false">Objective</a> </li>
						                <li class="nav-item"> <a class="nav-link" id="scope-vertical-tab" data-toggle="tab" href="#scope-vertical" role="tab" aria-controls="contact" aria-selected="false">Scope</a> </li>
						            	<li class="nav-item"> <a class="nav-link" id="multilab-vertical-tab" data-toggle="tab" href="#multilab-vertical" role="tab" aria-controls="contact" aria-selected="false">Multi Lab Work Share</a> </li>
						            	<li class="nav-item"> <a class="nav-link" id="earlierwork-vertical-tab" data-toggle="tab" href="#earlierwork-vertical" role="tab" aria-controls="contact" aria-selected="false">Earlier Work</a> </li>
						            	<li class="nav-item"> <a class="nav-link" id="competency-vertical-tab" data-toggle="tab" href="#competency-vertical" role="tab" aria-controls="contact" aria-selected="false">Competency Established</a> </li>
						            	<li class="nav-item"> <a class="nav-link" id="needofprj-vertical-tab" data-toggle="tab" href="#needofprj-vertical" role="tab" aria-controls="contact" aria-selected="false">Need Of Project</a> </li>
						            	<li class="nav-item"> <a class="nav-link" id="technology-vertical-tab" data-toggle="tab" href="#technology-vertical" role="tab" aria-controls="contact" aria-selected="false">Technology Challenges</a> </li>
						            	<li class="nav-item"> <a class="nav-link" id="risk-vertical-tab" data-toggle="tab" href="#risk-vertical" role="tab" aria-controls="contact" aria-selected="false">Risk Mitigation</a> </li>
						            	<li class="nav-item"> <a class="nav-link" id="proposal-vertical-tab" data-toggle="tab" href="#proposal-vertical" role="tab" aria-controls="contact" aria-selected="false">Proposal</a> </li>
						            	<li class="nav-item"> <a class="nav-link" id="realization-vertical-tab" data-toggle="tab" href="#realization-vertical" role="tab" aria-controls="contact" aria-selected="false">Realization Plan</a> </li> 
						            </ul>
						            
						           
						            
						        </div>
						        
						    </div>
						</div>    
                    </div>
                    
                    
 					<!--  **** COST *********************     -->                   
                    
                    <div class="tab-pane fade shadow rounded bg-white p-3" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">
                        
                    	<div class="row">
				        	<div class="col-12">
				            	<div class="card">
				               
					                <div class="table-responsive">
					                    <table class="table">
					                        <thead class="thead" style="color:white!important;background-color: #055C9D">
					                            <tr>
						                            <th scope="col" >Budget Head</th>
					                                <th scope="col">Head of Accounts</th>
					                                <th scope="col">Item Detail</th>
					                                <th scope="col">Item Cost (In Lakhs)</th>
					                                
					                            </tr>
					                        </thead>
					                        <tbody class="customtable">
					                        
					                        	 	
					                           
					                        </tbody>
					                    </table>
					                </div>
				            	</div>
				        	</div>
				    	</div>

                    </div>
                    
                    
     				<!--  **** SCHEDULE *********************     -->           
                    
                    <div class="tab-pane fade shadow rounded bg-white p-3" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">

                        <div class="row">
					        <div class="col-12">
					            <div class="card">
					               
					                <div class="table-responsive">
					                    <table class="table">
					                        <thead class="thead" style="color:white!important;background-color: #055C9D">
					                            <tr>
						                            <th scope="col" class="center">Milestone No</th>
					                                <th scope="col">Milestone Activity</th>
					                                <th scope="col" class="center">Milestone Month</th>
					                                
					                            </tr>
					                        </thead>
					                        <tbody class="customtable">
					                        
					                        	 	
					                           
					                        </tbody>
					                    </table>
					                </div>
					            </div>
					        </div>
				    	</div>
                        
                        
                    </div>
                    
         
         			<!-- ********* ATTACHMENT *****************  -->           
                    
                    <div class="tab-pane fade shadow rounded bg-white p-3" id="v-pills-attachment" role="tabpanel" aria-labelledby="v-pills-attachment-tab">                        
                        <div class="row">
				        <div class="col-12">
				            <div class="card">
				               
				                <div class="table-responsive">
				                    <table class="table">
				                        <thead class="thead" style="color:white!important;background-color: #055C9D">
				                            <tr>
				                            <th scope="col" >File Name</th>
				                            <th scope="col" >Created By</th>
				                            <th scope="col" >Created Date</th>
				                            <th class="center" scope="col">Download</th>
				                            
				                                
				                            </tr>
				                        </thead>
				                        <tbody class="customtable">
				                        
				                        	
				                        </tbody>
				                    </table>
				                </div>
				               
				            </div>
		
				        </div>
				    </div>
                        
                    </div>
                    
                   
                     <!-- ********* PRINTS *****************  -->  
                    
                    
                    <div class="tab-pane fade shadow rounded bg-white p-3" id="v-pills-prints" role="tabpanel" aria-labelledby="v-pills-print-tab">

                        <div class="row">
				        <div class="col-12">
				         
				                <form action="" method="POST" name="myfrm" id="myfrm">
				                
				                	<button type="submit" class="btn btn-warning btn-sm prints" formaction="PfmsPrint.htm" formtarget="_blank"   >Print Executive Summary</button>&nbsp;&nbsp;
							 
							 		<button type="submit" class="btn btn-warning btn-sm prints" formaction="PfmsPrint2.htm" formtarget="_blank"  >Print Project Proposal</button>&nbsp;&nbsp;
				                				                	
				                	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
				                	
				                </form>
		
				        </div>
				    </div>
                        
                        
                    </div>
                    
                    
                    
                    
                    
                    
                </div>
            </div>
        </div>	
	
		</div>
	 --%>

	
	
	

 </div>

</body>
<script>

$(document).ready(function(){

	var height = $(window).height();
	
	$('.tab-pane').css('min-height', (height-150) +'px' )
	$('.tab-pane').css('max-height', (height-150) +'px' )
	$('.nav-pills').css('height', (height-150) +'px' )
	
})


</script>

</html>