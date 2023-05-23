<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
#button {
   float: left;
   width: 80%;
   padding: 5px;
   background: #dcdfe3;
   color: black;
   font-size: 17px;
   border:none;
   border-left: none;
   cursor: pointer;
}


body{

  overflow-x: hidden;
  overflow-y: hidden;

}

</style>
</head>
<body>
<%	
	
	List<Object[]> PendingList=(List<Object[]>)request.getAttribute("ApprovalList");
	String tab   = (String)request.getAttribute("tab");
	String fromdate = (String)request.getAttribute("fromdate");
	String todate   = (String)request.getAttribute("todate");
	
%>


<div class="card-header page-top ">
	<div class="row">
		<div class="col-md-5">
			<h5>NOC Approval List</h5>
		</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active" aria-current="page">NOC Approval</li>
						
				</ol>
			</div>
		</div>
</div>	

  <%	
    String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){ %>
	
	<div align="center">
		<div class="alert alert-danger" role="alert">
        	<%=ses1 %>
        </div>
   	</div>
	<%}if(ses!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
        	<%=ses %>
        </div>
    </div>
	<%} %>
<br>
  		
   <%-- 	<div class="card-body main-card">
   		
   		  <form action="" method="POST" >
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:5%">SN</th>
					   <th style="width:40%">Employee Name</th>
                       <th style="width:15%">Type</th>
                       <th style="width:25%">Action</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SN=0;
                         for(Object[] obj:ApprovalList){ %>
                        	 
                      <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            
                            <td ><%=obj[2]%></td>
                            <td style="text-align: center;"><%=obj[3]%></td>
                            <td style="text-align: center;">
                             <%if(form[3]!=null && form[3].toString().equalsIgnoreCase("Residential Address") ){%>
                            	<button type="submit" class="btn btn-sm" name="resaddressId" value="<%=form[4] %>" formaction="PersonalIntimation.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 	<%} else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Permanent Address")){ %>
						 		<button type="submit" class="btn btn-sm" name="peraddressId" value="<%=form[4] %>" formaction="PersonalIntimation.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 	<%} else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Mobile Number")){ %>
						 		<button type="submit" class="btn btn-sm" name="mobileNumberId" value="<%=form[4] %>" formaction="MobileNumberPreview.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 	<% if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("Passport")){ %>
						 		<button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4] %>" formaction="PassportPreview.htm"   formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		 <button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4]%>" formaction="PassportNOCPrint.htm"  formmethod="GET" data-toggle="tooltip" data-placement="top" data-original-title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
								</button> 
						 	<%} else if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("Proceeding Abroad")){ %> 
						 	
						 		<button type="submit" class="btn btn-sm" name="ProcAbrId" value="<%=obj[4] %>" formaction="ProcAbroadPreview.htm"   formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		
						 		 <button type="submit" class="btn btn-sm" name="ProcAbrId" value="<%=obj[4]%>" formaction="ProcAbroadPrint.htm"  formmethod="GET" data-toggle="tooltip" data-placement="top" data-original-title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
								</button> 
						 		
						 	<%} %>	
						 
						 	<% if(obj[5].toString().equalsIgnoreCase("APR")){ %>
						 	     <button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4]%>" formaction="PassportNOCCertificate.htm"  formmethod="GET" formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="Certificate">
												<i style="color: #5C469C" class="fa fa-envelope"></i>
								</button> 
						 	   
						 	
						 	<%} %>
						 	</td>
                            
                        </tr>
                      <%} %>
                          
                 </tbody>
   
            </table>
          </div>
          <input type="hidden" name="isApproval" value="Y">
         </form>
     
     
	</div> --%>
	
	<div class="row w-100" style="margin-bottom: 10px;">
		<div class="col-12">
         <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist" style="background-color: #E1E5E8;padding:0px;">
		  <li class="nav-item" style="width: 50%;"  >
		    <div class="nav-link active" style="text-align: center;" id="pills-mov-property-tab" data-toggle="pill" data-target="#pills-mov-property" role="tab" aria-controls="pills-mov-property" aria-selected="true">
			   <span>Pending</span> 
				<span class="badge badge-danger badge-counter count-badge" style="margin-left: 0px;">
				   		 <%if(PendingList.size()>99){ %>
				   			99+
				   		<%}else{ %>
				   			<%=PendingList.size() %>
						<%} %>			   			
				  </span> 
		    </div>
		  </li>
		  <li class="nav-item"  style="width: 50%;">
		    <div class="nav-link" style="text-align: center;" id="pills-imm-property-tab" data-toggle="pill" data-target="#pills-imm-property" role="tab" aria-controls="pills-imm-property" aria-selected="false">
		    	 <span>Approved</span> 
		    </div>
		  </li>
		</ul>
	   </div>
	</div>
	
	
	
	<div class="card">					
		<div class="card-body">
		<div class="container-fluid" >
           <div class="tab-content" id="pills-tabContent">
            <div class="tab-pane fade show active" id="pills-mov-property" role="tabpanel" aria-labelledby="pills-mov-property-tab">
		    <form action="" method="POST" id="">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <div class="table-responsive">
              <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:5%">SN</th>
					   <th style="width:40%">Employee Name</th>
                       <th style="width:15%">Type</th>
                       <th style="width:25%">Action</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SN=0;
                       for(Object[] obj:PendingList){ %>
                      
                        <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            
                            <td ><%=obj[2]%></td>
                            <td ><%=obj[3]%></td>
                            <td style="text-align: center;">
                            
						 	<% if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("Passport")){ %>
						 		<button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4] %>" formaction="PassportPreview.htm"   formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		 <button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4]%>" formaction="PassportNOCPrint.htm"  formmethod="GET"  formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
								</button> 
						 	<%} else if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("Proceeding Abroad")){ %> 
						 	
						 		<button type="submit" class="btn btn-sm" name="ProcAbrId" value="<%=obj[4] %>" formaction="ProcAbroadPreview.htm"   formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		
						 		 <button type="submit" class="btn btn-sm" name="ProcAbrId" value="<%=obj[4]%>" formaction="ProcAbroadPrint.htm"  formmethod="GET"  formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
								</button> 
						 		
						 	<%} %>	
						 
						 	<% if(obj[5].toString().equalsIgnoreCase("APR")){ %>
						 	     <button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4]%>" formaction="PassportNOCCertificate.htm"  formmethod="GET" formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="Certificate">
												<i style="color: #5C469C" class="fa fa-envelope"></i>
								</button> 
						 	 <%} %>
						 	</td>
                            
                        </tr>
                       <%} %>
                          
                 </tbody>
   
            </table>
          </div>
          <input type="hidden" name="isApproval" value="Y">
         </form>
				
			  </div>
 
	<!-- Approved List -->	
	
		<div class="tab-pane fade" id="pills-imm-property" role="tabpanel" aria-labelledby="pills-imm-property-tab">	
		<div class="card-body main-card " >	
		
		 <form method="post" action="IntimationApprovals.htm" >
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="hidden" name="tab" value="closed"/>
					<div class="row w-100" style="margin-top: 10px;margin-bottom: 10px;">
						<div class="col-md-12" style="float: right;">
							<table style="float: right;">
								<tr>
									<td> From Date :&nbsp; </td>
							        <td> 
										<input type="text" class="form-control input-sm mydate" onchange="this.form.submit()"  readonly="readonly"  <%if(fromdate!=null){%>
								        value="<%=DateTimeFormatUtil.SqlToRegularDate(fromdate)%>" <%}%> value=""  id="fromdate" name="fromdate"  required="required"   > 
									</td>
									<td></td>
									<td >To Date :&nbsp;</td>
									<td>					
										<input type="text"  class="form-control input-sm mydate" onchange="this.form.submit()"  readonly="readonly" <%if(todate!=null){%>
								         value="<%=DateTimeFormatUtil.SqlToRegularDate(todate)%>" <%}%>  value=""  id="todate" name="todate"  required="required"  > 							
									</td>
									<!-- <td>					
										<button type="submit" class="btn btn-sm submit-btn" > Submit</button> 							
									</td> -->
								</tr>
							</table>
					 	</div>
					 </div>
		</form> 
		
		
		<div class="row" >
		 <div class="col-md-12">
		
		<form action="" method="POST" id="">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <!--  <div class="table-responsive"> -->
              <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable1">
				<thead>
					<tr>
					   
					   <th style="width:0%">SN</th>
					   <th style="width:50%">Employee Name</th>
                       <th style="width:15%">Type</th>
                       <th style="width:25%">Action</th>
					  
                  	</tr>
				</thead>
                 <tbody>
                       
                        <tr>
                             
                            <td style="text-align: center;"></td>
                            <td ></td>
                            <td ></td>
                            <td ></td>
                        </tr>
                       
                          
                   </tbody>
   
                 </table>
                <!-- </div>  -->      
               </form>
               </div>
               </div>
               </div>				
			  </div>
		   </div>
		</div>
     </div>
</div>
 	
<script>

$("#myTable1").DataTable({
    "lengthMenu": [ 50, 75, 100],
    "pagingType": "simple"

});


	<%if(tab!=null && tab.equals("closed")){%>
	
		$('#pills-imm-property-tab').click();
	
	<%}%>
	
	$('#fromdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		/* "minDate" :datearray,   */
		 "startDate" : new Date('<%=fromdate%>'), 
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
		
		
		$('#todate').daterangepicker({
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,
			"startDate" : new Date('<%=todate%>'), 
			"minDate" :$("#fromdate").val(),  
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

		 $(document).ready(function(){
			   $('#fromdate, #todate').change(function(){
			       $('#myform').submit();
			    });
			}); 
	
	
	
	
 </script>

</body>


</html>