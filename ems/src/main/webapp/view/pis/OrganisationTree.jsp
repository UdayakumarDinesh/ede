<%@page import="java.math.BigDecimal"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List , java.util.stream.Collectors, java.text.DecimalFormat,java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Item Tree</title>
<jsp:include page="../static/dependancy.jsp"></jsp:include>
 

<!-- ------------------------------- tree css ------------------------------- -->
<style type="text/css">


/*----------------genealogy-scroll----------*/

/* .genealogy-scroll::-webkit-scrollbar {
    width: 10px;
    height: 10px;
}
.genealogy-scroll::-webkit-scrollbar-track {
    border-radius: 5px;
    background-color: #e4e4e4;
}
.genealogy-scroll::-webkit-scrollbar-thumb {
    background: #212121;
    border-radius: 5px;
    transition: 0.5s;
}
.genealogy-scroll::-webkit-scrollbar-thumb:hover {
    background: #d5b14c;
    transition: 0.5s;
}
 */

/*----------------genealogy-tree----------*/
 .genealogy-body{
    white-space: nowrap;
    overflow-y: hidden;
    padding: 50px;
    min-height: 500px;
    padding-top: 10px;
    text-align: center;
}
.genealogy-tree{
  display: inline-block;
} 
.genealogy-tree ul {
    padding-top: 20px; 
    position: relative;
    padding-left: 0px;
    display: flex;
    justify-content: center;
}
.genealogy-tree li {
    float: left; text-align: center;
    list-style-type: none;
    position: relative;
    padding: 20px 26px 0 12px;
}
.genealogy-tree li::before, .genealogy-tree li::after{
    content: '';
    position: absolute; 
    top: 0; 
    right: 50%;
    border-top: 2px solid #ccc;
    width: 50%; 
    height: 18px;
}
.genealogy-tree li::after{
    right: auto; left: 50%;
    border-left: 2px solid #ccc;
}
.genealogy-tree li:only-child::after, .genealogy-tree li:only-child::before {
    display: none;
}
.genealogy-tree li:only-child{ 
    padding-top: 0;
}
.genealogy-tree li:first-child::before, .genealogy-tree li:last-child::after{
    border: 0 none;
}
.genealogy-tree li:last-child::before{
     border-right: 2px solid #ccc;
     border-radius: 0 5px 0 0;
    -webkit-border-radius: 0 5px 0 0;
    -moz-border-radius: 0 5px 0 0;
}
.genealogy-tree li:first-child::after{
    border-radius: 5px 0 0 0;
    -webkit-border-radius: 5px 0 0 0;
    -moz-border-radius: 5px 0 0 0;
}
.genealogy-tree ul ul::before{
    content: '';
    position: absolute; top: 0; left: 50%;
    border-left: 2px solid #ccc;
    width: 0; height: 20px;
}
.genealogy-tree li .action-view-box{
     text-decoration: none;
     /* font-family: arial, verdana, tahoma; */
     font-size: 11px;
     display: inline-block;
     border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
}

.genealogy-tree li a:hover+ul li::after, 
.genealogy-tree li a:hover+ul li::before, 
.genealogy-tree li a:hover+ul::before, 
.genealogy-tree li a:hover+ul ul::before{
    border-color:  #fbba00;
}

/*--------------memeber-card-design----------*/
.member-view-box{
    padding:0px 15px;
    text-align: center;
    border-radius: 4px;
    position: relative;
}
.member-image{
    width: 60px;
    position: relative;
}
.member-image img{
    width: 60px;
    height: 60px;
    border-radius: 6px;
    background-color :#000;
    z-index: 1;
}


</style>
 <!-- ------------------------------- tree css ------------------------------- -->


<style type="text/css">
.action-box
{
    width: fit-content ;
    height: fit-content;
    min-width:220px;
    
    border: 1px solid black;
    border-radius: 8px;
}


.action-box-header
{
	border-top-right-radius : 7px;
	border-top-left-radius: 7px;
 	padding:3px;
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

.Q1
{
	background-color: #DC3535;
	color: #FFFFFF;
}

.Q2
{
	background-color: #FF7000;
	color: #FFFFFF;
}

.Q3
{
	background-color: #FED049;
	color: #000000;
}

.Q4
{
	background-color: #5F8D4E;
	color: #FFFFFF;
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

<%
  
      
   
%>


        

<body style="background-color:#FFFFFF;overflow-y:auto " class="body genealogy-body genealogy-scroll">

	<div>
		<h3>SITAR ORGANISATION STRUCTURE</h3>
		<hr style="width:19%; margin-top:-7px;" >
	</div>
	
		<div>
	    <div class="genealogy-tree" >
	        
	  		<ul>
				<li>      
	
						 <div class="member-view-box action-view-box">
			                    
			                         <div class=" action-box" style="border:0px;" > 
			                         	
			                         	<div  class="action-box-header" >
												
												<span style="font-size: 1.7em;cursor:pointer;" >CEO</span>
			                          			 
			                          			 
										</div>
			                       <div class="action-box-body" align="center" style="cursor: pointer;background-color:#428bca;color: #FFFFFF;font-size: 1.6em;
			                          	border-top-right-radius: 5px;border-top-left-radius: 5px;border-bottom-right-radius: 5px; border-bottom-left-radius: 5px;">
			                          	
			                          	<span style="font-weight: 600;"> 
							                          									                 		
														
										</span >
			                          		
												    
			                 </div>
			                         
			                    </div>
			                    </div>
			               
			        <!-- --------------------------------------------------------   LEVEL 1 ---------------------------------------------------- -->
			                <ul class="active">	                
			               
									<li>			    
									           
											<div class="member-view-box action-view-box">
												<div class=" action-box" >
												
												<div  class="action-box-header" >
												
												<span style="font-size: 1.7em;cursor:pointer;" 
			                          			 ></span>
			                          			 
													</div>
													
													<div class="action-box-body"  align="center" style="cursor: pointer ;" >
													
														<div style="margin-top:-6px;"><i class="fa fa-caret-down whiteiconcolor" aria-hidden="true" style="font-size: 1.2rem;color:#FF6347;;padding-top:0px;padding-bottom:2px;cursor: pointer ;"></i></div>
													
													<div style="margin-top:12px;"></div>
													
													
			                          		       </div>
													
												</div>
											</div>
	
									    
								<!-- --------------------------------------------------------   LEVEL 2 ---------------------------------------------------- -->
						                <ul class="">	
						                
							             
												<li>			    
												           
													<div class="member-view-box action-view-box">
															<div class=" action-box" >
															<div class="action-box-header">
															
												               <span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" >
			                          			                </span>
			                          			
													               </div>
													              
																<div class="action-box-body"  align="center" style="cursor: pointer ;">
																
																	
									                          			       
																					  <div style="margin-top:-5px;"  style="cursor: pointer ;"><i class="fa fa-caret-down whiteiconcolor" aria-hidden="true" style="font-size: 1.2rem;color:#FF6347;"></i></div>
									
									                          			       <div style="margin-top:12px;" ></div>
									                          			   
																					
																			</div>
																			
																					
																		</div>
																	</div>
												    
												    <!-- --------------------------------------------------------   LEVEL 3 ---------------------------------------------------- -->
										                <ul class="">	                
										                		    
																           
																	<div class="member-view-box action-view-box">
																		<div class=" action-box" >
																		<div class="action-box-header">
																		
												                            <span style="cursor:pointer;font-weight: 600;font-size: 1.7em;"> 
			                          			                             </span>
			                          			
													                        </div>
													                       
																				<div class="action-box-body"  align="center" style="cursor: pointer ;">
																				
																					
																											  <div style="margin-top:-5px;" style="cursor: pointer ;"><i class="fa fa-caret-down whiteiconcolor" aria-hidden="true" style="font-size:1.2rem;color:#FF6347;"></i></div>
																											
														                          		            <div style="margin-top:12px;" ></div>
														                          		         
																											
																											  
																			                     </div>
																			                     
																							</div>
																						</div>
																    														    
																    
																    <!-- --------------------------------------------------------   LEVEL 4 ---------------------------------------------------- -->
															                <ul class="">	                
															                
																					<li>			    
																					           
																						<div class="member-view-box action-view-box">
																								<div class=" action-box" >
																								<div class="action-box-header">
																								
												                                                      <span style="cursor:pointer;font-weight: 600;font-size: 1.7em;"> 
			                          			                                               </span>
			                          			
													                                                 </div>
																									
																									<div class="action-box-body"  align="center" style="cursor: pointer ;">
																										
																										
																	                          		          
																											  <div style="margin-top:-5px;"  style="cursor: pointer ;"><i class="fa fa-caret-down whiteiconcolor" aria-hidden="true" style="font-size: 1.2rem;color:#FF6347;"></i></div>
																											
																	                          		         else<div style="margin-top:12px;" ></div>
																	                          		          
																											</div>
																											
																										</div>
																									</div>
																					<!-- -------------------------------------------------------------LEVEL-5 ---------------------------------------------->		
																					    
																					         <ul class="">	                
															             			   
																					<li>			    
																					           
																						<div class="member-view-box action-view-box">
																								<div class=" action-box" >
																									<div class="action-box-header">
																									
												                                                      <span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" >
			                          			                                                        </span>
													                                                 </div>
													                                                
																									
																									<div style="cursor: pointer ;">
																									
																											<div style="margin-top:-5px;" style="cursor: pointer ;"><i class="fa fa-caret-down whiteiconcolor" aria-hidden="true" style="font-size: 1.2rem;color:#FF6347;"></i></div>
																											<div style="margin-top:12px;" ></div>
															                          		          
																										
																									</div>
																									
																								</div>
																							</div>
																																	    
																					    
															                		</li>
																				
																			
															                </ul> 
															              <!-- --------------------------------------------------------   LEVEL 5 ---------------------------------------------------- -->      
    
															                		</li>
																				
																			
															                </ul>
															                
															        <!-- --------------------------------------------------------   LEVEL 4 ---------------------------------------------------- --> 
																    
										                		</li>
															
														
										                </ul>
										                
										        <!-- --------------------------------------------------------   LEVEL 3 ---------------------------------------------------- -->  
												
												</li>
											
										
						                </ul>
						                
						        <!-- --------------------------------------------------------   LEVEL 2 ---------------------------------------------------- -->    
								    
			                		</li>
			                		
								
							
			                </ul>
			                
			        <!-- --------------------------------------------------------   LEVEL 1 ---------------------------------------------------- -->        
			           		
						
	        		</li>
		        </ul>
	
	    </div>
	</div>

<!-- ----------------------------------------------------------List box ---------------------------------------------->

	<div class=" modal bd-example-modal-lg" tabindex="-1" role="dialog" id="item_modal">
		<div class="modal-dialog modal-lg" role="document" style=" margin-left: 15%;">
			<div class="modal-content" style="width:120% !important;">
				<div class="modal-header" style="background-color: #FFE0AD; ">
					<div class="row w-100"  >
						<div class="col-md-12" >
							<h5 class="modal-title" id="modal_item_name" style="font-weight:700; font-size:1.7em;color: #A30808;"></h5>
						</div>
					</div>
					
					 <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body" align="center" style=" max-height: 25rem; overflow-y:auto;">
						<form action="#" method="get">
						
						<table class="table table-bordered table-hover table-striped table-condensed " id="" style="width: 100%">
							<thead> 
								<tr style="background-color: #055C9D; color: white;">
									<th style="text-align: center;width:5% !important;">SN</th>
									<th style="text-align: center;width:20% !important;">Description </th>
									<th style="text-align: center;width:15% !important;">Make</th>
									<th style="text-align: center;width:5% !important;">Unit</th>
									<th style="text-align: center;width:5% !important;">Quantity</th>
									<th style="text-align: center;width:5% !important;">Rate</th>
									<th style="text-align:center;width:5% !important;">Cost(&#8377; In lakhs)</th>
								</tr>
							</thead>
							<tbody id="modal_progress_table_body">
								
							</tbody>
						</table>
						
						
						</form>
					      
				</div>
				<div class="modal-footer" align="center"  >
					       	  
							<div class="col-md-12" >
							<h5 class="modal-title" style="font-weight:700; color: #A30808;">Total Cost(&#8377; In lakhs): <span id="totalCost1"></span> </h5>
							</div>	
								
							
				 </div>
						
				
			</div>
		</div>
	</div>
	
	
	
	


<!-- ------------------------------- tree script ------------------------------- -->
  <script type="text/javascript">

  $(function () {
	    $('.genealogy-tree ul').hide();
	    $('.genealogy-tree>ul').show();
	    $('.genealogy-tree ul.active').show();
	    
	    $('.genealogy-tree li .action-box-body').on('click', function (e) {
			
	        var children = $(this).parent().parent().parent().find('> ul');
	        if (children.is(":visible")) children.hide('fast').removeClass('active');
	        else children.show('fast').addClass('active');
	        e.stopPropagation();
	    });
	});

</script> 
<script type="text/javascript">
	function ItemDetails(sbrmasterid,itemname)
	{
		
		
	$("#modal_progress_table").DataTable().destroy();
	$.ajax({		
	type : "GET",
	url : "itemModelListAjax.htm",
	data : {
		sbrmasterid : sbrmasterid
	},
	datatype : 'json',
	success : function(result) {
	var result = JSON.parse(result);
	console.log("res     :"+result);
	
	$('#modal_item_name').html(itemname);
	
	
	var htmlStr='';
	
	if(result.length> 0){
		var totalcost=0;
	for(var v=0;v<result.length;v++)
    {
	var cost=(result[v][6]/100000);
	var total= (new Intl.NumberFormat('en-IN').format(result[v][6]/100000));
	totalcost=totalcost + cost ;
	var totalcost1=(new Intl.NumberFormat('en-IN').format(totalcost));
	htmlStr += '<tr>';
	
	htmlStr += '<td class="tabledata" style="text-align: center;" >'+ (v+1) + '</td>';
	htmlStr += '<td class="tabledata" style="text-align: left;" >'+ result[v][1] + '</td>';
	htmlStr += '<td class="tabledata" style="text-align: left;" >'+ result[v][4] + ' </td>';
	htmlStr += '<td class="tabledata" style="text-align: left;" >'+ result[v][2] + ' </td>';
	htmlStr += '<td class="tabledata" style="text-align: right;" >'+ result[v][3] + ' </td>';
	htmlStr += '<td class="tabledata" style="text-align: right;" >'+ result[v][5] + ' </td>';
	htmlStr +=  '<td class="tabledata" style="text-align: right;" >'+ total + ' </td>';
	htmlStr += '</tr>';
	}

	$('#totalCost1').html(totalcost1);
	
	}
	else
	{
		var totalcost=0;
	htmlStr += '<tr>';
	
	htmlStr += '<td colspan="7" style="text-align: center;"> Item Data Unavailable </td>';
	
	htmlStr += '</tr>';
	$('#totalCost1').html(totalcost);
	}
	setModalDataTable();
	$('#modal_progress_table_body').html(htmlStr);
	
	$('#item_modal').modal('toggle');
	
	}
	});
	
	
	}
	setModalDataTable();
	function setModalDataTable()
	{
		$("#modal_progress_table").DataTable({
			"lengthMenu": [ 5, 10,25, 50, 75, 100 ],
			"pagingType": "simple",
			"pageLength": 5
		});
	}
</script>



</body>
</html>