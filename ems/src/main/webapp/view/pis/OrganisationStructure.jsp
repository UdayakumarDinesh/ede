<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Organisation Structure</title>

<style type="text/css">

 .organisation-body{
    white-space: nowrap;
    overflow-y: hidden;
    padding: 40px;
    min-height: 400px;
    padding-top: 10px;
    text-align: center;
}
.organisation-tree{
  display: inline-block;
} 
.organisation-tree ul {
    padding-top: 20px; 
    position: relative;
    padding-left: 0px;
    display: flex;
    justify-content: center;
}
.organisation-tree li {
    float: left; text-align: center;
    list-style-type: none;
    position: relative;
    padding: 20px 26px 0 12px;
}
.organisation-tree li::before, .organisation-tree li::after{
    content: '';
    position: absolute; 
    top: 0; 
    right: 50%;
    border-top: 2px solid #ccc;
    width: 50%; 
    height: 18px;
}
.organisation-tree li::after{
    right: auto; left: 50%;
    border-left: 2px solid #ccc;
}
.organisation-tree li:only-child::after, .organisation-tree li:only-child::before {
    display: none;
}
.organisation-tree li:only-child{ 
    padding-top: 0;
}
.organisation-tree li:first-child::before, .organisation-tree li:last-child::after{
    border: 0 none;
}
.organisation-tree li:last-child::before{
     border-right: 2px solid #ccc;
     border-radius: 0 5px 0 0;
    -webkit-border-radius: 0 5px 0 0;
    -moz-border-radius: 0 5px 0 0;
}
.organisation-tree li:first-child::after{
    border-radius: 5px 0 0 0;
    -webkit-border-radius: 5px 0 0 0;
    -moz-border-radius: 5px 0 0 0;
}
.organisation-tree-tree ul ul::before{
    content: '';
    position: absolute; top: 0; left: 50%;
    border-left: 2px solid #ccc;
    width: 0; height: 15px;
}
.organisation-tree li .action-view-box{
     text-decoration: none;
     /* font-family: arial, verdana, tahoma; */
     font-size: 11px;
     display: inline-block;
     border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
}

.organisation-tree li a:hover+ul li::after, 
.organisation-tree li a:hover+ul li::before, 
.organisation-tree li a:hover+ul::before, 
.organisation-tree li a:hover+ul ul::before{
    border-color:  #fbba00;
}

/*--------------memeber-card-design----------*/
 .member-view-box{
    padding:0px 5px;
    text-align: center;
    border-radius: 4px;
    position: relative;
}
 

</style>
 <!-- ------------------------------- tree css ------------------------------- -->


<style type="text/css">
.action-box
{
    width: fit-content ;
    height: fit-content;
    min-width:120px;
    
    border: 1px solid black;
    border-radius: 8px;
}


.action-box-header
{
	border-top-right-radius : 7px;
	border-top-left-radius: 7px;
 	padding:2px;
 	text-align: center;
 	background-color: #428bca;
	color: #FFFFFF;
}



.action-box-body
{
	padding:0px;
	text-align: center;
	background-color:#FFFFFF ;
	display: block;
    flex-wrap: wrap;
    border-bottom-right-radius : 9px;
	border-bottom-left-radius: 7px;
}



.card-body-table
{
	width:100%;
}

.card-body-table  td
{
	border:0px;
	text-align: left;
}	
	
.card-body-table th
{
	border:0px;
}


th
{
 	text-align: left;
 	overflow-wrap: break-word;
}

td
{
 	text-align: justify;
  	text-justify: inter-word;
    
}

.tabledata
{
 	white-space: -o-pre-wrap; 
    word-wrap: break-word;
    white-space: pre-wrap; 
    white-space: -moz-pre-wrap; 
    white-space: -pre-wrap; 
}

</style>
</head>
<body style="background-color:#FFFFFF;overflow-y:auto " class="body organisation-body organisation-scroll">
<%
List<Object[]> grouplist=(List<Object[]>)request.getAttribute("grouplist");
List<Object[]> divisionlist=(List<Object[]>)request.getAttribute("divisionlist"); 
Object[] Director=(Object[])request.getAttribute("Director"); 
%>
  <div align="center">
		<h2 >SITAR ORGANISATION STRUCTURE </h2>
		<hr style="width:33%; margin-top:-7px;" >
	</div>
     <div>
       <div class="organisation-tree">
         <ul>
             <li>
             
                   <div class="member-view-box action-view-box">
			                    
			             <div class=" action-box" style="border:0px;" > 
			                         	
			              <div class="action-box-body" align="center" style="cursor: pointer;background-color:#428bca;color: #FFFFFF;font-size: 1.6em;
			                     border-top-right-radius: 5px;border-top-left-radius: 5px;border-bottom-right-radius: 5px; border-bottom-left-radius: 5px;">
			                      <span style="font-weight: 600;"> 
							                          									                 		
									<%=Director[2]%> <br> <%= Director[0]%>
														
							    </span >			                          													    
			                </div>
			            </div>
			        </div>
    <!-- ************************************* Group level ******************************************************************** -->
              <ul class="active">
              <%for(Object[] group: grouplist){ %>
                <li>
                 <div class="member-view-box action-view-box">												
						<div class=" action-box" >
						<div  class="action-box-header" >
												
						  <span style="font-size: 1.5em;cursor:pointer;"> 
						    
			                          			 
			                   <%=group[1] %> <% if( grouplist!=null && grouplist.size()>0 ){ %>  <i class="fa fa-info-circle " style="font-size: 1.1rem;cursor:pointer; position:absolute; right:21px;top:11px;" aria-hidden="true"></i> <%} %></span>
			                          			 
						</div>
						<div class="action-box-body"  align="center" style="cursor: pointer ;" >
													<div style="margin-top:10px;"></div>
														<table class="card-body-table">
					                          			<tr>
					                          				<th style="">GH :</th>
					                          				<td style="font-size: 12px;" >&nbsp; <%if(grouplist!=null  && grouplist.size()>0){ %><%= group[3]%><%} else{ %>-<%} %> </td>
					                          			</tr>
												</table>
												
												<div style="margin-top:10px;"></div>
					</div>
					</div>	
					</div>
		<!-- *****************************************  Division Level Start ************************************************************** -->
		 <ul class="active">
              <%for(Object[] division: divisionlist){
            	 if(group[0].toString().equals(division[5].toString())){
            	  %>
                <li>
                 <div class="member-view-box action-view-box">												
						<div class=" action-box" >
						<div  class="action-box-header" >
												
						  <span style="font-size: 1.5em;cursor:pointer;"  <%if(division[0]!=null) {%>onclick="DeptEmpList('<%=division[0] %>')"<%} %>  data-toggle="modal" data-target=".employee">
			                          			 
			                   <%=division[1] %> <% if( divisionlist!=null && divisionlist.size()>0 ){ %>  <i class="fa fa-info-circle " style="font-size: 1.1rem;cursor:pointer; position:absolute; right:21px;top:11px;" aria-hidden="true"></i> <%} %></span>
			                          			 
						</div>
						<div class="action-box-body"  align="center" style="cursor: pointer ;" >
													<div style="margin-top:10px;"></div>
														<table class="card-body-table">
					                          			<tr>
					                          				<th style="">DH Name :</th>
					                          				<td style="font-size: 12px;" >&nbsp; <%if(divisionlist!=null  && divisionlist.size()>0){ %><%= division[3]%><%} else{ %>-<%} %> </td>
					                          			</tr>
												</table>
												
										
													
					</div>
					</div>	
					</div>
					
					</li>
				<%}} %>						         
             </ul>
	<!-- *************************************   Division Level End  ******************************************************-->
				</li>
				<%} %>						         
             </ul>
              <!-- GroupLevel End  -->
               </li>
           </ul>
       </div>  
     </div>
     <!--*******************************************************  Modal For EmpList   -->
   
     <div class="modal employee" id="emplist" role="dialog" >
           <div class="modal-dialog modal-lg">
            <div class="modal-content">
             <div class="modal-header"><h5 class="modal-litle" >LIST OF EMPLOYEES  </h5>
                 <button  type="button"  class="close" data-dismiss="modal">&times;</button>
                </div>
     
   <div class="modal-body">
             
         <div class="row">
              
          <div class="col-md-12" >
              <div class="row" style="margin-top: 15px;">
    
     <div class="col-md-12">

	    <div class="table-responsive">
	  <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1">
   	
    <thead>
    	
	             <tr style="color: #000080;">
	               	
	                 <th style="font-size: 12px;">EMP Name</th>
	        	     <th style="font-size: 12px;">Designation</th>
	        	     <th style="font-size: 12px;">Emp No</th>
	                   
	               </tr>
	         </thead>
	      <tbody id="modal_progress_table">
	     
	     </tbody>
	  </table>
	  </div>
	  </div>
	  </div>
	  </div>
	  </div>
	  </div>
	  </div>
	  </div>
	  </div>
	 
    
<script type="text/javascript">

 /*  $(function () {
	    $('.organisation-tree ul').hide();
	    $('.organisation-tree>ul').show();
	    $('.organisation-tree ul.active').show();
	    
	    $('.organisation-tree li .action-box-body').on('click', function (e) {
			
	        var children = $(this).parent().parent().parent().find('> ul');
	        if (children.is(":visible")) children.hide('fast').removeClass('active');
	        else children.show('fast').addClass('active');
	        e.stopPropagation();
	    });
	});
 */
</script> 
<script type="text/javascript">
function DeptEmpList(divisionId){
	$("#modal_progress_table").DataTable().destroy();
	$.ajax({
		type:"GET",
		url:"DeptEmpListAjax.htm",
		data:	{
		divisionId:	divisionId
		},
		datatype:'json',
		success:function(result){
		var  result=JSON.parse(result);
		document.getElementById("empname").innerHTML =result[0];
		}
	})
	
	
	
}


</script>
</body>
</html>