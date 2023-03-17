<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
    <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MT List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">

</style>
</head>
<body>
<% List<Object[]> Mtapplylist=(List<Object[]>)request.getAttribute("Mtapplylist") ;
List<Object[]> Mtapplysanclist=(List<Object[]>)request.getAttribute("Mtapplysanclist") ;
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String empname = (String)request.getAttribute("empname");
 %>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>MT List <small> <%if(empname!=null){%> <%=empname%> <%}%></small>  </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT List</li>
					</ol>
				</div>
			</div>
</div>


 <%String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%><center>
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></center>
	<%}if(ses!=null){ %>
	<center>
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div></center>
                    <%} %>

        <div class="card" >
			
				<div class="card-body " >
               <!-- edit and Delete -->
        		
             	<div class="table-responsive">

			<table class="table table-bordered table-hover table-striped table-condensed"  > 
					  <thead>
					  			 <tr> 
                                    <td  colspan="4" style=" text-align:center; background-color: white; color:#005C97; ">
                                    <h5 style="margin-left: 145px; margin-top: -2px; ">List of Applied Trip   <button style="font-size:x-small;  text-align: right;float: right; " class="btn  btn-info btn-sm " data-toggle="modal" data-target="#statusFullForm">Click Me To See Full Form Of Status</button></h5>
                                   
                                    </td>                                  
                                 </tr>
								  <tr>
									  <th >Date</th>
								      <th>Source</th>
								      <th>Destination</th>
									  <th>Edit &amp; Delete</th>
								  </tr>
					  </thead>
	                  <tbody>
	  
							 <%if(Mtapplylist!=null&&Mtapplylist.size()!=0){
						                            	   int count=0;
                               for(Object[] hlo :Mtapplylist){
                            	   if("A".equalsIgnoreCase(hlo[15]+"")){
                            	   %>
	                             <tr>
									  <td align="center"><%=DateTimeFormatUtil.SqlToRegularDate(hlo[3]+"") %> To <%=DateTimeFormatUtil.SqlToRegularDate(hlo[4]+"") %></td>
									  <td><%=hlo[7]+"" %></td>
									  <td><%=hlo[8]+"" %></td>
                                      <td align="center">
		                                	<form class="lv-action" action="MtUserApplyC.htm" method="GET">
		                                		
		                                		<button type="submit" class="btn btn-sm" name="sub"  value="edit"   data-toggle="tooltip"  data-placement="top" title="Edit">
												            <i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
											    </button>
											    
		                                		<button type="submit"  class="btn btn-sm"   Onclick="return confirm('Are You Sure To Delete?');" name="sub"  value="delete"  data-toggle="tooltip" data-placement="top" title="Delete Bill">
																<i class="fa-solid fa-trash-can" style="color: red;"></i>
												</button>
		                                 		
		                                		<input type="hidden" name="MtApplyId" value="<%=hlo[0]+""%>"> 
		                                		
		                                	</form>
                                    </td>
                               </tr> 
                             <%count++;}if(count==2)break;}%>                        
	                             <tr style="font-size: 12px; line-height: 0px;">
	                           		<td colspan="4" align="center"><a class="badge badge-info" href="" data-toggle="modal" data-target="#pending" style="font-size: 12px;">See More</a></td>
	                           </tr>  
                             <%}else{%>  
                               <tr>
                                     <td colspan="4" align="center"><span class="badge badge-warning">No ApplyData Present For Modify And Delete </span></td>
                              </tr> 
                            <%}%>
                              
	                   </tbody>
	   </table>
</div> 
                   
             
                    <div class="table-responsive">                 
                     <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                           <tr> 
                               <td colspan="6" style="text-align:center; font-size:16px; background-color: white; color:#005C97;" ><h5 style="margin-bottom: -2px; margin-top: -2px;">List of Sanction Trip </h5></td>
                           </tr>
                            <tr >
                                <th>Date</th>
                                <th>Source</th>
                                <th>Destination</th>
                                <th>Start Time</th>
                                <th>Driver</th>
                                <th>Vehicle</th>
                            </tr>
                        </thead>
                        <tbody>  
                         <%if(Mtapplysanclist!=null&&Mtapplysanclist.size()!=0){
                               int count=0;
                               for(Object[] hlo :Mtapplysanclist){%>
						   <tr style="font-size: 12px; ">	 
								  <td><%=DateTimeFormatUtil.SqlToRegularDate(hlo[0]+"") %> <br> To <br> <%=DateTimeFormatUtil.SqlToRegularDate(hlo[6]+"") %> </td>
								  <td><%=hlo[1]%></td>
								  <td><%=hlo[2] %></td>
								  <td><%=hlo[3] %></td>
								  <td><%=hlo[4]%></td>
								  <td><%=hlo[5] %></td>
	                       </tr> 
                             <%count++;if(count==2)break;}%>
                           <tr style="font-size: 12px; line-height: 0px;">
                                 <td colspan="6" align="center"><a class="badge badge-info" href="" data-toggle="modal" data-target="#editdelete1" style="font-size: 12px;">See More</a></td>
                           </tr> 
                              <%}else{%>  
                            <tr style="font-size: 12px; line-height: 0px;">
                                 <td colspan="6" align="center"><span class="badge badge-warning">No Sanction Trip Present </span></td>
                            </tr>
                            <%} %>    
                        </tbody>
                      </table> 	
                      </div> 
                     
                             
            
                <div class="table-responsive">
	             <table class="table table-bordered table-hover table-striped table-condensed " id="myTable1" >  
						  <thead>
						          <tr> 
						              <td colspan="4" style="text-align:center; font-size:16px; background-color: white; color:#005C97;"><h5 style="margin-bottom: -2px; margin-top: -2px;"> List of Pending/Cancelled Trip </h5></td>
						         </tr>
								  <tr>
									  <th>Date</th>
								      <th>Source</th>
								      <th>Destination</th>
									  <th>Status</th>
								  </tr>
						  </thead>
	                      <tbody>
	                         <%if(Mtapplylist!=null&&Mtapplylist.size()!=0){ int count=0;
                               for(Object[] hlo :Mtapplylist){
                            if("M".equalsIgnoreCase(hlo[15]+"")||"V".equalsIgnoreCase(hlo[15]+"")||"N".equalsIgnoreCase(hlo[15]+"")){%>
								  <tr>
								  	
									  <td align="center"><%=DateTimeFormatUtil.SqlToRegularDate(hlo[3]+"") %> To <%=DateTimeFormatUtil.SqlToRegularDate(hlo[4]+"") %></td>
									  <td><%=hlo[7]+"" %></td>
									  <td><%=hlo[8]+"" %></td>
		                              <td>
		                                	<%if("V".equalsIgnoreCase(hlo[15]+"")){%>Recommended<%}%>
			      		                    <%if("N".equalsIgnoreCase(hlo[15]+"")){%>Vehicle Not Available<%}%>
			      	 	                    <%if("M".equalsIgnoreCase(hlo[15]+"")){%>Trip Not Available<%}%>
                                     </td>
                                </tr> 
                             <%count++;}if(count==2) break;} if(count>0){%>
                             <tr style="font-size: 12px; line-height: 0px;">
                                    <td colspan="4" align="center"><a class="badge badge-info" href="" data-toggle="modal" data-target="#pending1" style="font-size: 12px;">See More</a></td>
                             </tr> 
                             <%}else{%>
								<tr>
								   <td colspan="4" align="center"><span class="badge badge-warning">No ApplyData Present For Modify And Delete</span></td>
								</tr>
                             <%}}else{%>  
                             <tr>
                                    <td colspan="4" align="center"><span class="badge badge-warning">No ApplyData Present For Modify And Delete</span></td>
                             </tr>
                            <%}%>
                              
	                     </tbody>
	               </table>   
	            </div> 
	                      
    </div>
    </div>
                
                       
              
   <div class="modal fade" id="pending" role="dialog" >
           <div class="modal-dialog modal-lg" >
              <div class="modal-content">
                   <div class="modal-header">
                       <!-- <h4 class="modal-litle">List of MT Pending</h4> -->
                       <button  type="button" class="close" data-dismiss="modal">&times;</button>
                   </div> 
              
              <div class="modal-body">
            <div class="table-responsive">
               
	   <table class="table table-bordered table-hover table-striped table-condensed " id="myTable1" >  
				  <thead>
				          <tr> 
						      <td colspan="4" style="text-align:center; font-size:16px; background-color: white; color:#005C97;"> <h5 style="margin-bottom: -2px; margin-top: -2px;">List of Applied Trip</h5></td>
						   </tr>
						  <tr>						
							  <th>Date</th>
						      <th>Source</th>
						      <th>Destination</th>
							  <th>Edit &amp; Delete</th>
						  </tr>
				  </thead>
				  <tbody>
				  
				 <%if(Mtapplylist!=null&&Mtapplylist.size()!=0){
					
			         for(Object[] hlo :Mtapplylist){
			        	 if("A".equalsIgnoreCase(hlo[15]+"")){
			        	 %>
				  <tr>
				 
				  <td><%=DateTimeFormatUtil.SqlToRegularDate(hlo[3]+"") %> To <%=DateTimeFormatUtil.SqlToRegularDate(hlo[4]+"") %></td>
				  <td><%=hlo[7]+""%></td>
				  <td><%=hlo[8]+"" %></td>
	              <td>
                                	<form class="lv-action" action="MtUserApplyC.htm" method="GET">
		                                		
		                                		<button type="submit" class="btn btn-sm" name="sub"  value="edit"   data-toggle="tooltip"  data-placement="top" title="Edit">
												            <i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
											    </button>
											    
		                                		<button type="submit"  class="btn btn-sm"   Onclick="return confirm('Are You Sure To Delete?');" name="sub"  value="delete"  data-toggle="tooltip" data-placement="top" title="Delete Bill">
																<i class="fa-solid fa-trash-can" style="color: red;"></i>
												</button>
		                                 		
		                                		<input type="hidden" name="MtApplyId" value="<%=hlo[0]+""%>"> 
		                                		
		                                	</form>
                 </td>
                 </tr> 
                  <%}}}else{%>  
                  <tr>
                      <td colspan="6" align="center"><span class="label label-warning">No ApplyData Present For Modify And Delete</span></td>
                  </tr>
                   <%}%>                             
	                </tbody>
	             </table>	   
					</div> 
				</div> 
				 <div class="modal-footer"> </div>     
           </div>
      </div>     
</div>
<div class="modal fade" id="editdelete1" role="dialog">
		<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-litle">List Mt Request</h4>
			<button type="button" class="close" data-dismiss="modal">&times;</button>
		</div>

		<div class="modal-body">
			<div><span class="" style="font-size: 16px; background-color: white; color:#119bd8;">List of Sanction Trip</span></div>
			<div class="card-body main-card">
				<div class="table-responsive">
					<table	class="table table-bordered table-hover table-striped table-condensed" id="">
						<thead>
							<tr style="font-size: 12px; line-height: 0px;">
								<th>Date</th>
								<th>Source</th>
								<th>Destination</th>
								<th>Start Time</th>
								<th>Driver</th>
								<th>Vehicle</th>
							</tr>
						</thead>
						<tbody>

							<%if (Mtapplysanclist != null && Mtapplysanclist.size() != 0) {
								int count = 0;
								for (Object[] hlo : Mtapplysanclist) {%>
							<tr style="font-size: 12px;">

								<td><%=sdf.format(hlo[0])%> <br> To <br> <%=sdf.format(hlo[6])%>
								</td>
								<td><%=hlo[1]%></td>
								<td><%=hlo[2]%></td>
								<td><%=hlo[3]%></td>
								<td><%=hlo[4]%></td>
								<td><%=hlo[5]%></td>
							</tr>

							<%count++;}%>
							<!-- <tr style="font-size: 12px; line-height: 0px;">
								<td colspan="6" align="center"><a class="badge badge-info" href=""	data-toggle="modal" data-target="#editdelete1"	style="font-size: 12px;">See More</a></td>
							</tr> -->
							<%}else{%>
							<tr style="font-size: 12px; line-height: 0px;">
								<td colspan="6" align="center"><span class="badge badge-warning">No Sanction Trip Present </span></td>
							</tr>
							<%}%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div> 
</div>
</div>


	<div class="modal fade" id="pending1" role="dialog">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-litle">List of MT Pending</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body">
					<div class="table-responsive">
						<table class="table table-bordered table-hover table-striped table-condensed "	id="myTable1">
							<thead>
								<tr> 
							      <td colspan="4" style="text-align:center; font-size:16px; background-color: white; color:#119bd8;"> <h5> List of Pending/Cancelled Trip </h5></td>
							   </tr>
								<tr>
									<th>Date</th>
									<th>Source</th>
									<th>Destination</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<%if (Mtapplylist != null && Mtapplylist.size() != 0) {
									int count = 0;
									for (Object[] hlo : Mtapplylist) {
										if ("M".equalsIgnoreCase(hlo[15] + "") || "V".equalsIgnoreCase(hlo[15] + "")
										|| "N".equalsIgnoreCase(hlo[15] + "")) {
								%>
								<tr>
									
									<td align="center"><%=DateTimeFormatUtil.SqlToRegularDate(hlo[3] + "")%> To <%=DateTimeFormatUtil.SqlToRegularDate(hlo[4] + "")%></td>
									<td><%=hlo[7] + ""%></td>
									<td><%=hlo[8] + ""%></td>
									<td>
										<%if ("V".equalsIgnoreCase(hlo[15] + "")) {%>Recommended<%}%>
										<%if ("N".equalsIgnoreCase(hlo[15] + "")) {%>Vehicle Not Available<%}%> 
										<%if ("M".equalsIgnoreCase(hlo[15] + "")) {%>Trip Not Available<%}%>
									</td>
								</tr>
								<%count++;}}%>
								
								<!--  <tr style="font-size: 12px; line-height: 0px;">
									<td colspan="4" align="center"><a class="badge badge-info" href=""	data-toggle="modal" data-target="#pending" style="font-size: 12px;">See More</a></td>
								</tr>  -->
								
								
								<%}else{%>
								<tr>
									<td colspan="4" align="center"><span class="label label-warning">No ApplyData Present For Modify And Delete</span></td>
								</tr>
								<%}%>
							</tbody>
						</table>
					</div>
				</div>	
			</div>
		</div>
	</div>

	<div class="row" style="margin-top: 10px;">
	  
	 
				    <!-- Status Full Form Button -->
				    <!-- Status Full Form Modal -->
				    
		<div class="modal fade" id="statusFullForm" role="dialog" >
           <div class="modal-dialog modal-lg">
            <div class="modal-content">
             <div class="modal-header"><h4 class="modal-litle">Full Form Of Status</h4>
                 <button  type="button" class="close" data-dismiss="modal">&times;</button>
             </div>
      
              
       <div class="modal-body">            
         <div class="row">             
          <div class="col-md-6" >              
              <table class="table table-hover  table-striped table-condensed table-bordered">          
             <thead>
                   <tr style="background-color:#FDF5E6 ;">
	                   <th style="color: black;">Status</th> 
	                   <th style="color: black;">Full Form of Status</th> 
                   </tr>
             </thead>
             <tbody>
                <tr><td>A</td><td>Applied By User </td></tr> 
                <tr><td>F</td><td>Forward By Accounts</td></tr>              
                <tr><td>R</td><td>Recommended By Recommending Officer</td></tr>               
                <tr><td>S</td><td>Sanctioned By Sanctioning Officer</td></tr>             
            </tbody>
           </table>
        </div> 

           <div class="col-md-6" >
             <table class="table table-hover  table-striped table-condensed table-bordered">
               <thead>
                 <tr style="background-color:#FDF5E6 ;">
                      <th style="color: black;">Status</th>
                      <th style="color: black;">Full Form of Status</th>
                 </tr>
            </thead>
            <tbody>
               <tr><td>V</td><td>Verified By Admin</td></tr>
               <tr><td>X</td><td>Cancel By User</td></tr>
               <tr><td>Y</td><td>Cancel Approve By Recommending Officcer</td></tr>              
               <tr><td>Z</td><td>Cancel Approve By Sanctioning Officer</td> </tr>
             </tbody>
          </table>
          </div>             
              </div><!--//row -->
             </div><!--//modal body -->
             <div class="modal-footer"> </div>          
            </div>
        </div>
     </div>
     </div>
				    
				    <!-- //Status Full Form Modal -->
</body>
</html>