<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
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

</style>
</head>
<body>
<%	
    List<Object[]> PendingList =(List<Object[]>)request.getAttribute("PendingList");
    List<Object[]> ApprovedList =(List<Object[]>)request.getAttribute("ApprovedList");
	Object[] empData=(Object[])request.getAttribute("EmpData");
	
	String fromdate = (String)request.getAttribute("fromdate");
	String todate   = (String)request.getAttribute("todate");
	
	String tab   = (String)request.getAttribute("tab");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
%>


<div class="card-header page-top ">
	<div class="row">
		<div class="col-md-7">
				<h5>Visitor Pass Approvals<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-5">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					
				</ol>
			</div>
		</div>
</div>	
<div class="page card dashboard-card">
 <div class="card-body" >	
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
	
	<!-- Pending List -->
	<div class="card">					
		<div class="card-body">
		<div class="container-fluid" >
           <div class="tab-content" id="pills-tabContent">
            <div class="tab-pane fade show active" id="pills-mov-property" role="tabpanel" aria-labelledby="pills-mov-property-tab">
		    <form action="" method="POST" id="circularForm">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width: ">SN</th>
					   <th style="width: ">Intimation By</th>
					   <th style="width: ">Visitor/s</th>
					   <th style="width: ">Company</th>
					   <th style="width: ">Special Permission</th>
                       <th style="width: ">Action</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SN=0;
                       if(PendingList!=null){
                          for(Object[] form: PendingList ){
                       %>
                        <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            <td ><%=form[2] %></td>                            
                            <td ><%=form[8]%></td>
                             <td ><%=form[6]%></td>
                              <td ><%=form[5]%></td>
                            <td style="text-align: center;">
                            <input type="hidden" name="intimationId" value="<%=form[4]%>">
					         <button type="submit" class="btn btn-sm" name="intimationId" value="<%=form[4] %>" formaction="VisitorPassPreview.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
								<%-- 
								<button type="submit" class="btn btn-sm submit-btn" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" data-toggle="tooltip" data-placement="top" title="Verify">
							     Verify
						       </button>	
						       
						       <button type="button" class="btn btn-sm delete-btn" name="Action" value="R" formaction="VpIntimationApprovalSubmit.htm" onclick="return validateTextBox(<%=form[4]%>);" formmethod="post" data-toggle="tooltip" data-placement="top" title="Retrun">
								 Return	
								</button>	 --%>			 	
						 	</td>
                            
                        </tr>
                       <%} }%>                          
                 </tbody>   
            </table>
          </div>
        
          <input type="hidden" name="isApproval" value="Y">
         </form>
				
			  </div>
 
	<!-- Approved List -->	
		<div class="tab-pane fade" id="pills-imm-property" role="tabpanel" aria-labelledby="pills-imm-property-tab">	
		<div class="card-body main-card " >	
		
		<form method="post" action="VpApprovals.htm" >
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
		      <form action="" method="POST" id="circularForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <div class="table-responsive">
              <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable1">
				<thead>
					<tr>
					  <th style="width:5%">SN</th>
					  <th style="width:40%">Intimation By</th>
					  <th style="width: ">Visitor</th>					 
					  <th style="width: ">Company</th>	
					  <th style="width: ">Special Permission</th>	
					  <th>Status</th>	
					  <th>Action</th> 	  
                  	</tr>
				</thead>
                 <tbody>
                       <% int SNA=0;
                        if(ApprovedList!=null){
                          for(Object[] form:ApprovedList ){
                       %>
                        <tr>
                             
                            <td style="text-align: center;"><%=++SNA%></td>
                            <td ><%=form[2] %></td>
                             <td ><%=form[8]%></td>                           
                            <td ><%=form[6]%></td>
                            <td ><%=form[5]%></td>
                            <td>
                              <button type="submit" class="btn btn-sm btn-link w-100" formaction="VpIntimationTransStatus.htm" value="<%=form[4]%>" name="intimationid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=form[12] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=form[11] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
                            </td> 
                            <td align="center">
                            <% if(form[14]!=null && "G".equalsIgnoreCase(form[9].toString()) ) {%> 
								       <button type="submit" class="btn btn-sm" formaction="passPrint" name="passAction" value="<%=form[14]%>#<%=form[4]%>" formtarget="blank" formmethod="GET" data-toggle="tooltip" data-placement="top" title="Download">
								          <i style="color: #019267" class="fa-solid fa-download"></i>
								       </button>
								       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								       <input type="hidden" value="Y" name="Download">								      
								       <%}else {%>-<%} %>
                            </td>
                        </tr>
                       <%}} %>
                          
                   </tbody>
   
                 </table>
                </div>       
               </form>
		
               </div>
               </div>
               </div>				
			  </div>
		   </div>
		</div>
     </div>
</div>
</div>
</div>	
<script>

function validateTextBox(intimationId) {
	
	document.getElementById("intimationId").value=intimationId;
	$('#myModal').modal('show');
	return true; 
   
}

$("#myTable1").DataTable({
    "lengthMenu": [ 50, 75, 100],
    "pagingType": "simple"

});

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


	
	<%if(tab!=null && tab.equals("closed")){%>
	
		$('#pills-imm-property-tab').click();
	
	<%}%>
	
</script>

</body>
</html>