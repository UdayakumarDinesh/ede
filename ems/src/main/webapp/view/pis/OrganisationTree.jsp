<%@page import="org.apache.logging.log4j.core.pattern.EqualsIgnoreCaseReplacementConverter"%>
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
<title>Organization Tree</title>
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
	background-color: #428bca;
	color: #FFFFFF;
}

.Q2
{
	background-color: #EA5455;
	color: #FFFFFF;
}

.Q3
{
	background-color: #E86A33;
	color: #000000;
}

.Q4
{
	background-color: #643A6B;
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

.h3{
font-size:2rem;
}

.btn1{
	border-top-left-radius: 5px !important;
	border-bottom-left-radius: 5px !important;
}

.btn2{
	
    border-left: 1px solid black;
}
.btn4{
	margin: 0px 10px;
	color:green !important;
}
</style>

</head>

  <%
  
Object[] Director=(Object[])request.getAttribute("Director"); 
List<Object[]> dgmlist=(List<Object[]>)request.getAttribute("DGMLIST");
List<Object[]> divisionlist=(List<Object[]>)request.getAttribute("divisionlist"); 
List<Object[]> grouplist=(List<Object[]>)request.getAttribute("grouplist"); 
List<Object[]> EmpModalList=(List<Object[]>)request.getAttribute("EmpModalList");
List<Object[]> DGMModalList=(List<Object[]>)request.getAttribute("DGMModalList");
List<Object[]> DivReportCeo=(List<Object[]>)request.getAttribute("DivReportCeo");

   

 %>

<body style="background-color:#FFFFFF;overflow-y:auto ;" class="body genealogy-body genealogy-scroll">

	<div>
		<h3>SITAR ORGANISATION STRUCTURE</h3>
		<!--  <hr style="width:30%; margin-top:-7px;" >  -->
	</div>
<div align="right">
   <div style="float: right;padding:0px;margin-top:-48px;">
		  	 <div class="btn-group "> 
		  	 	
	            <button class="btn btn1" id="submit"><i class="fa fa-chevron-circle-left" style="font-size:24px"></i></button>
		        <button class="btn btn2" ><i class="fa fa-chevron-circle-right" style="font-size:24px" ></i></button>
		       
		      </div>
		  </div>	
		  
		<div class="" style="display: block" id="colorcode">
			<div style="font-weight: bold; ">
				<span  style="margin:0px 0px 10px  10px;">CEO :&ensp; 	<span class="Q1" style="padding: 0px 15px;border-radius: 3px;"></span></span>
				<span  style="margin:0px 0px 10px  15px;">DGM :&ensp; 	<span class="Q2" style="padding: 0px 15px;border-radius: 3px;"></span></span>
				<span  style="margin:0px 0px 10px  15px;">Division:&ensp; 	<span class="Q3" style="padding: 0px 15px;border-radius: 3px;"></span></span>
				<span  style="margin:0px 0px 10px  15px;">Group :&ensp; <span class="Q4" style="padding: 0px 15px;border-radius: 3px;"></span></span>
				
			</div>
		</div>
		
		<div class="" style="display: none" id=colorcode1>
			<div style="font-weight: bold; ">
				<span  style="margin:0px 0px 10px  10px;">CEO :&ensp; 	<span class="Q1" style="padding: 0px 15px;border-radius: 3px;"></span></span>
				<span  style="margin:0px 0px 10px  15px;">Division:&ensp; 	<span class="Q3" style="padding: 0px 15px;border-radius: 3px;"></span></span>
				<span  style="margin:0px 0px 10px  15px;">Group :&ensp; <span class="Q4" style="padding: 0px 15px;border-radius: 3px;"></span></span>
				
			</div>
		</div>
		
	</div>
		<div style="display: block"  id="tree" >
	    <div class="genealogy-tree"  >
	        
	  		<ul>
				<li>      
	
						 <div class="member-view-box action-view-box">
			                    
			                         <div class=" action-box" style="border:-1px;" > 
			                         	
			                         	<div  class="action-box-header" >
			                         	
			                         	 <span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" 
			                          			                onclick="EmpDetails('-1','CEO','','','','yes')">
			                          			                 CEO  <i class="fa fa-plus-circle fa-lg " style="font-size: 1.2rem;background-color:#643A6B;cursor:pointer; position:absolute; 
			                          			                         right:24px;top:21px;" aria-hidden="true"></i>
			                          		 </span>
			                         	
											
			                          			 
			                          			 
										</div>
			                       <div class="action-box-body" style="cursor: pointer;background-color:#FFFF;color: #FFFFFF;font-size: 1.0em;">
			                          	
			                          	<span style="font-weight: 600;color:black;"> 
							                <%=Director[0] %><br>
							                [<%=Director[1] %>]	
							                		
									    </span > 
			                          	<% if(dgmlist!=null && dgmlist.size()>0) {%>
			                          	 <div style="margin-top:-2px;"><i class="fa fa-caret-down" aria-hidden="true" style="font-size: 1.2rem;color:#428bca;;padding-top:0px;padding-bottom:2px;cursor: pointer ;"></i></div>
			                          	 <%}%>	
			                          	 
			                   </div>
			                         
			                    </div>
			                    </div>
			               
			        <!-- --------------------------------------------------------   LEVEL 1 ---------------------------------------------------- -->
			                <ul class="active">	                
			                <% for(Object[] dgm :dgmlist ){%>
			                	<li>			    
									           
											<div class="member-view-box action-view-box">
												<div class=" action-box" >
												<% List<Object[]> list= EmpModalList.stream().filter(e-> dgm[0].toString().equalsIgnoreCase(e[2].toString())).collect(Collectors.toList());%>
												<div  class="action-box-header"style="background-color:#EA5455;" >
												
										  <span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" 
										      <% if(list!=null  && list.size()>0){ %> 
			                          			                onclick="EmpDetails('<%=dgm[0]%>','<%=dgm[1] %>','<%=dgm[1] %>','','','yes')" <%} %>>
			                          			                   <%=dgm[1]  %>   <% if(list!=null  && list.size()>0){ %>  <i class="fa fa-plus-circle fa-lg " style="font-size: 1.2rem;background-color:#643A6B;cursor:pointer; position:absolute; 
			                          			                         right:24px;top:21px;" aria-hidden="true"></i><%} %>
			                          		 </span>
												
											   </div>
													
													<% List<Object[]> level1 =divisionlist.stream().filter(e-> dgm[0].toString().equalsIgnoreCase(e[4].toString()) ).collect(Collectors.toList()); 
                                                     %>
													
													<div  <% if(level1!=null && level1.size()>0){%> class="action-box-body" <%} %>  align="center" style="cursor: pointer ;" >
													
													<span style="font-weight: 600;color:black;" ><%=dgm[3] %> <br>[<%=dgm[4]%>]</span>
													
													<% if(level1!=null && level1.size()>0) {%>
												       <div style="margin-top:-2px;"><i class="fa fa-caret-up " aria-hidden="true" style="font-size: 1.2rem;color:#FF6347;;padding-top:0px;padding-bottom:2px;cursor: pointer ;"></i></div>
			                          	            <%} 
													else{%><div style="margin-top:22px;" ></div><%} %>
			                          	            
														
													
													</div>
													
												</div>
											</div>
	
									    
								<!-- --------------------------------------------------------   LEVEL 2 ---------------------------------------------------- -->
						                <ul class="">	
						                 <% for(Object[] division : divisionlist){
						                	if(dgm[0].toString().equalsIgnoreCase(division[4].toString())){ 
						                  %>
							             
												<li>			    
												           
													<div class="member-view-box action-view-box">
															<div class=" action-box" >
															
															<% List<Object[]> list1= EmpModalList.stream().filter(e-> division[0].toString().equalsIgnoreCase(e[3].toString())).collect(Collectors.toList()); %>
															
															<div class="action-box-header" style="background-color: #E86A33;">
														
															<span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" 
			                          			                              <% if(list1!=null  && list1.size()>0 ){ %> onclick="EmpDetails('<%=division[0]%>','<%=division[1] %>','<%=dgm[1] %>','<%=division[1] %>' , '','no')" <%} %>>
			                          			                                 <%=division[1]  %> <%if(list1!=null  && list1.size()>0 ){ %> <i class="fa fa-plus-circle fa-lg " style="font-size: 1.2rem;background-color:#643A6B;cursor:pointer; position:absolute; 
			                          			                                 right:24px;top:21px;" aria-hidden="true"></i> <%} %>
			                          			            </span>
												             
			                          			             </div>
													              <% List<Object[]> level2 =grouplist.stream().filter(e-> division[0].toString().equalsIgnoreCase(e[6].toString()) ).collect(Collectors.toList()); %>
																<div    <% if(level2!=null && level2.size()>0){%> class="action-box-body" <%} %> align="center" style="cursor: pointer ;">
																
																	<span style="font-weight: 600;color:black;" ><%=division[3] %><br>[<%=division[5] %>]</span>
																	
																	<% if(level2!=null && level2.size()>0){%>
									                          			  <div style="margin-top:-2px;"  style="cursor: pointer ;"><i class="fa fa-caret-up" aria-hidden="true" style="font-size: 1.2rem;color:#E86A33;"></i></div>
									                               <%} else{%>
																	
																	<div style="margin-top:22px;" ></div>
																	<%} %>
																	
																  </div>
																			
																					
																</div>
															</div>
												    
												    <!-- --------------------------------------------------------   LEVEL 3 ---------------------------------------------------- -->
										                <ul class="">	                
										                	<% for(Object[] group : grouplist){
										                	if(group[6].toString().equalsIgnoreCase(division[0].toString())){
										                	  %>	    
																  <li>         
																	<div class="member-view-box action-view-box">
																		<div class=" action-box" >
																		<% List<Object[]> list2= EmpModalList.stream().filter(e-> group[0].toString().equalsIgnoreCase(e[4].toString())).collect(Collectors.toList()); %>
																		<div class="action-box-header" style="background-color: #643A6B;">
																		
																		
																		<span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" 
			                          			                              <% if(list2!=null  && list2.size()>0 ){ %> onclick="EmpDetails('<%=group[0]%>','<%=group[1] %>','<%=dgm[1] %>','<%=division[1] %>','<%=group[1] %>' ,'no')" <%} %>>
			                          			                                 <%=group[1]  %> <%if(list2!=null  && list2.size()>0 ){ %> <i class="fa fa-plus-circle fa-lg " style="font-size: 1.2rem;background-color:#643A6B;cursor:pointer; position:absolute; 
			                          			                                 right:24px;top:21px;" aria-hidden="true"></i> <%} %>
			                          			                          </span>
												                                              			
													                        </div>
													                       
																				<div class="action-box-body"  align="center" style="cursor: pointer ;">
																				
																					<span style="font-weight: 600;color:black;" ><%=group[4] %><br>[<%=group[5] %>]</span>
																					
																				 </div>
																			                     
																			</div>
																	</div>
																    														    
																    
																
										                		</li>
															
														    <% } %>
														<% } %>
										                </ul>
										                
										        <!-- --------------------------------------------------------   LEVEL 3 ---------------------------------------------------- -->  
												
												</li>
												
											<% } %>
								         <% } %> 
										
						                </ul>
						                
						        <!-- --------------------------------------------------------   LEVEL 2 ---------------------------------------------------- -->    
								    
			                		</li>
			                		
			                		<% } %>
							    
			                </ul>
			                
			        <!-- --------------------------------------------------------   LEVEL 1 ---------------------------------------------------- -->        
			           		
						
	        		</li>
		        </ul>
	
	    </div>
	</div>
	
	<!-- ---------------------------------------------------- tree------------------------------------------------------------ -->
	
	
	<div style="display: none"  id="tree1" >
	    <div class="genealogy-tree"  >
	        
	  		<ul>
				<li>      
	
						 <div class="member-view-box action-view-box">
			                    
			                         <div class=" action-box" style="border:-1px;" > 
			                         	
			                         	<div  class="action-box-header" >
			                         	
			                         	 <span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" 
			                          			                onclick="EmpDetails('-1','CEO' ,'','','','yes')">
			                          			                 CEO  <i class="fa fa-plus-circle fa-lg " style="font-size: 1.2rem;background-color:#643A6B;cursor:pointer; position:absolute; 
			                          			                         right:24px;top:21px;" aria-hidden="true"></i>
			                          		 </span>
			                         	
											
			                          			 
			                          			 
										</div>
			                       <div class="action-box-body" style="cursor: pointer;background-color:#FFFF;color: #FFFFFF;font-size: 1.0em;">
			                          	
			                          	<span style="font-weight: 600;color:black;"> 
							                <%=Director[0] %><br>
							                [<%=Director[1] %>]	
							                		
									    </span > 
			                          	<% if(dgmlist!=null && dgmlist.size()>0) {%>
			                          	 <div style="margin-top:-2px;"><i class="fa fa-caret-down" aria-hidden="true" style="font-size: 1.2rem;color:#428bca;;padding-top:0px;padding-bottom:2px;cursor: pointer ;"></i></div>
			                          	 <%}%>	
			                          	 
			                   </div>
			                         
			                    </div>
			                    </div>
			               
			        <!-- --------------------------------------------------------   LEVEL 1 ---------------------------------------------------- -->
			               <%--  <ul class="active">	                
			                <% for(Object[] dgm :dgmlist ){ %>
			                	<li>			    
									           
											<div class="member-view-box action-view-box">
												<div class=" action-box" >
												<% List<Object[]> list= EmpModalList.stream().filter(e-> dgm[0].toString().equalsIgnoreCase(e[2].toString())).collect(Collectors.toList());%>
												<div  class="action-box-header"style="background-color:#EA5455;" >
												
										  <span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" 
										      <% if(list!=null  && list.size()>0){ %> 
			                          			                onclick="EmpDetails('<%=dgm[0]%>','<%=dgm[1] %>','<%=dgm[1] %>')" <%} %>>
			                          			                   <%=dgm[1]  %>   <% if(list!=null  && list.size()>0){ %>  <i class="fa fa-plus-circle fa-lg " style="font-size: 1.2rem;background-color:#643A6B;cursor:pointer; position:absolute; 
			                          			                         right:24px;top:21px;" aria-hidden="true"></i><%} %>
			                          		 </span>
												
											   </div>
													
													<% List<Object[]> level1 =divisionlist.stream().filter(e-> dgm[0].toString().equalsIgnoreCase(e[4].toString()) ).collect(Collectors.toList()); 
                                                     %>
													
													<div  <% if(level1!=null && level1.size()>0){%> class="action-box-body" <%} %>  align="center" style="cursor: pointer ;" >
													
													<span style="font-weight: 600;color:black;" ><%=dgm[3] %> <br>[<%=dgm[4]%>]</span>
													
													<% if(level1!=null && level1.size()>0) {%>
												       <div style="margin-top:-2px;"><i class="fa fa-caret-up " aria-hidden="true" style="font-size: 1.2rem;color:#FF6347;;padding-top:0px;padding-bottom:2px;cursor: pointer ;"></i></div>
			                          	            <%} 
													else{%><div style="margin-top:22px;" ></div><%} %>
			                          	            
														
													
													</div>
													
												</div>
											</div> --%>
	
									    
								<!-- --------------------------------------------------------   LEVEL 2 ---------------------------------------------------- -->
						                <ul class="active">	
						                 <% for(Object[] div : DivReportCeo){
						                	
						                  %>
							             
												<li>			    
												           
													<div class="member-view-box action-view-box">
															<div class=" action-box" >
															
															
															
															<div class="action-box-header" style="background-color: #E86A33;">
														
															<span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" 
			                          			                               onclick="EmpDetails('<%=div[0]%>','<%=div[1] %>','','<%=div[1]  %>','')">
			                          			                                 <%=div[1]  %>  <i class="fa fa-plus-circle fa-lg " style="font-size: 1.2rem;background-color:#643A6B;cursor:pointer; position:absolute; 
			                          			                                 right:24px;top:21px;" aria-hidden="true"></i> 
			                          			            </span>
												             
			                          			             </div>
													              <% List<Object[]> level2 =grouplist.stream().filter(e-> div[0].toString().equalsIgnoreCase(e[6].toString()) ).collect(Collectors.toList()); %>
																<div    <% if(level2!=null && level2.size()>0){%> class="action-box-body" <%} %> align="center" style="cursor: pointer ;">
																
																	<span style="font-weight: 600;color:black;" ><%=div[3] %><br>[<%=div[4] %>]</span>
																	
																	<% if(level2!=null && level2.size()>0){%>
									                          			  <div style="margin-top:-2px;"  style="cursor: pointer ;"><i class="fa fa-caret-up" aria-hidden="true" style="font-size: 1.2rem;color:#E86A33;"></i></div>
									                               <%} else{%>
																	
																	<div style="margin-top:22px;" ></div>
																	<%} %>
																	
																  </div>
																			
																					
																</div>
															</div>
												    
												    <!-- --------------------------------------------------------   LEVEL 3 ---------------------------------------------------- -->
										               <ul class="">
										               	                
										                	<% for(Object[] group : grouplist){
										                	if(group[6].toString().equalsIgnoreCase(div[0].toString())){
										                	  %>	    
																  <li>         
																	<div class="member-view-box action-view-box">
																		<div class=" action-box" >
																		<% List<Object[]> list2= EmpModalList.stream().filter(e-> group[0].toString().equalsIgnoreCase(e[4].toString())).collect(Collectors.toList()); %>
																		<div class="action-box-header" style="background-color: #643A6B;">
																		
																		
																		<span style="cursor:pointer;font-weight: 600;font-size: 1.7em;" 
			                          			                              onclick="EmpDetails('<%=group[0]%>','<%=group[1] %>','','<%=div[1] %>','<%=group[1] %>','no')" >
			                          			                                 <%=group[1]  %>  <i class="fa fa-plus-circle fa-lg " style="font-size: 1.2rem;background-color:#643A6B;cursor:pointer; position:absolute; 
			                          			                                 right:24px;top:21px;" aria-hidden="true"></i>
			                          			                          </span>
												                                              			
													                        </div>
													                       
																				<div  class="action-box-body"  align="center" style="cursor: pointer ;">
																				
																					<span style="font-weight: 600;color:black;" ><%=group[4] %><br>[<%=group[5] %>]</span>
																					
																				 </div>
																			                     
																			</div>
																	</div>
																    														    
																    
																
										                		</li>
															
														    <% } %>
														<% } %>
										                </ul>
										                
										        <!-- --------------------------------------------------------   LEVEL 3 ---------------------------------------------------- -->  
												
												</li>
												
											<% } %>
								         
										
						                </ul>
						                
						        <!-- --------------------------------------------------------   LEVEL 2 ---------------------------------------------------- -->    
							</li>
	        		
		        </ul>
		    
			                	
			        <!-- --------------------------------------------------------   LEVEL 1 ---------------------------------------------------- -->        
			           		
						
	        		
	    </div>
	</div>
	<!-- ------------------------------------------------------tree1---------------------------------------------------------------- -->

<!-- ----------------------------------------------------------List box ---------------------------------------------->

	<div class=" modal bd-example-modal-lg" tabindex="-1" role="dialog" id="table_modal">
		<div class="modal-dialog modal-lg" role="document" style=" margin-left: 30%;">
			<div class="modal-content" style="width:80% !important;">
				<div class="modal-header" style="background-color: #F2E3DB; ">
					<div class="row w-100"  >
						<div class="col-md-12" >
						  <div class="row w-100" style="margin-left:0%;">
						  
						  <div   id="modal_ceo" style="font-weight:700;margin-left:0%; font-size:1.7em;color: #428bca;"></div>
						  <div   id="modal_dgmcode" style="font-weight:700; font-size:1.7em;color: #EA5455;"></div>
						   <div  id="modal_divcode" style="font-weight:700; font-size:1.7em;color: #E86A33;"></div>&nbsp;
						   <div  id="modal_groupcode" style="font-weight:700; font-size:1.7em;color: #643A6B;"></div>
						    
                          
						
					    </div>
					</div>
				</div>
					
					 <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body" align="center" style=" max-height: 25rem; overflow-y:auto;">
						<form action="#" method="get">
						
						<table class="table table-bordered table-hover table-striped table-condensed " id="" style="width: 100%">
							<thead> 
								<tr style="background-color: #055C9D; color: white;">
									<th style="text-align: center;width:5% !important;">SN</th>
									<th style="text-align: center;width:20% !important;">Name</th>
									<th style="text-align: center;width:15% !important;">Designation</th>
									<th id="div" style="text-align: center;width:20% !important;">Division</th>
									
								</tr>
							</thead>
							<tbody id="modal_table_body">
								
							</tbody>
						</table>
						
						
						</form>
					      
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
			
	    	 /* var children = $(this).parent().parent().parent().find('> ul');
		        if (children.is(":visible")) children.hide('fast').removeClass('active');
		        else children.show('fast').addClass('active');
		        e.stopPropagation(); */
	        
	     var children = $(this).parent().parent().parent().find('> ul');
	        if (children.is(":visible")) {
	        	children.hide('fast').removeClass('active');
	        	$(this).find('i').removeClass('fa fa-caret-down');
	        	$(this).find('i').addClass('fa fa-caret-up');
	        } else {
	        	children.show('fast').addClass('active');
	        	$(this).find('i').removeClass('fa fa-caret-up');
	        	$(this).find('i').addClass('fa fa-caret-down');
	    	}
	        e.stopPropagation(); 
	    });
	});

</script> 
<script type="text/javascript">
	 function EmpDetails(id,code,dgmcode,divcode,groupcode,YN)
	{
		

	$("#modal_progress_table").DataTable().destroy();
	$.ajax({		
	type : "GET",
	url : "EmpListAjax.htm",
	data : {
		id : id,
		code :code
	},
	datatype : 'json',
	success : function(result) {
	var result = JSON.parse(result);
	
	$('#modal_ceo').html('CEO');

	if(dgmcode!==""){
	$('#modal_dgmcode').html('<i class="fa fa-long-arrow-right" aria-hidden="true" style="color:#4D4D4D;font-size:25px;margin-top:8px;"></i>'+dgmcode);
	}
	else{
		$('#modal_dgmcode').html("");
	}
	if(divcode!==""){
		$('#modal_divcode').html('<i class="fa fa-long-arrow-right" aria-hidden="true" style="color:#4D4D4D;font-size:25px;margin-top:8px;"></i>'+divcode);
	}
	else{
		$('#modal_divcode').html("");
	}
	if(groupcode!==""){
		$('#modal_groupcode').html('<i class="fa fa-long-arrow-right" aria-hidden="true" style="color:#4D4D4D;font-size:25px;margin-top:8px;"></i>'+groupcode);
	}
	else{
		$('#modal_groupcode').html("");
	}

	
	var htmlStr='';
	
	
	
	if(result.length>0){
		
	for(var v=0;v<result.length;v++)
    {
	
	
	htmlStr += '<tr>';
	
	htmlStr += '<td class="tabledata" style="text-align: center;" >'+ (v+1) +  '</td>';
	htmlStr += '<td class="tabledata" style="text-align: left;" >'+ result[v][1] + '</td>';
	htmlStr += '<td class="tabledata" style="text-align: left;" >'+ result[v][2] + ' </td>';
	if(YN=='yes'){
    htmlStr += '<td class="tabledata" style="text-align: center;" >'+ result[v][3] + ' </td>';
    $('#div').show();
	}
	else{
		  $('#div').hide();
	}
	
	
	htmlStr += '</tr>';
	}

	
	}
	else
	{
		
	htmlStr += '<tr>';
	
	htmlStr += '<td colspan="4" style="text-align: center;"> No Record Found </td>';
	
	htmlStr += '</tr>';
	
	}
	setModalDataTable();
	$('#modal_table_body').html(htmlStr);
	
	$('#table_modal').modal('toggle');
	
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
<script>


window.onload = function() {
	  var button = document.getElementById('submit');
	  
	  $('.btn1').css('background-color','green');
	  
		$('.btn1').css('color','white');
		
	 
};
	
$('.btn1').click(function(){

	$('.btn1').css('background-color','green');
	$('.btn1').css('color','white');
	
	$('.btn2').css('background-color','white');
	$('.btn2').css('color','black');
	
	
	$('#tree').css("display","block");
	$('#tree1').css("display","none");
	$('#colorcode').css("display","block");
	$('#colorcode1').css("display","none");
	
})

$('.btn2').click(function(){
	
	$('.btn2').css('background-color','green');
	$('.btn2').css('color','white');
	
	$('.btn1').css('background-color','white');
	$('.btn1').css('color','black');
	
	$('#tree').css("display","none");
	$('#tree1').css("display","block");
	$('#colorcode1').css("display","block");
	$('#colorcode').css("display","none");
	
	
})


</script>


</body>
</html>