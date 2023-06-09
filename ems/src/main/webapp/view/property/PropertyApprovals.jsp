<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
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
	List<Object[]> ConPendingList =(List<Object[]>)request.getAttribute("ConstructionPendingList");
	List<Object[]> ApprovedList =(List<Object[]>)request.getAttribute("ApprovedList");
	
	List<String[]> PandAs =(List<String[]>) request.getAttribute("PandAsEmpNos");
	List<String[]> SOs = (List<String[]>)request.getAttribute("SOEmpNos");
	
	Object[] empData=(Object[])request.getAttribute("EmpData");
	
	String fromdate = (String)request.getAttribute("fromdate");
	String todate   = (String)request.getAttribute("todate");	
	String tab   = (String)request.getAttribute("tab");
	String empNo = (String)session.getAttribute("EmpNo");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
%>


<div class="card-header page-top ">
	<div class="row">
		<div class="col-md-7">
				<h5>Property Approvals<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
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
				   		 <%if(PendingList.size()+ConPendingList.size()>99){ %>
				   			99+
				   		<%}else{ %>
				   			<%=PendingList.size()+ConPendingList.size() %>
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
					   <th style="width:5%">SN</th>
					 <!--  <th style="width:15%">EmpNo</th> -->
					  <th style="width:40%">Employee Name</th>
                       <th style="width:25%">Type</th>
                       <th style="width:25%">Action</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SN=0;
                          for(Object[] form:PendingList ){
                       %>
                        <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            <%-- <td style="text-align: center;"><%=form[1]%></td> --%>
                            <td ><%=form[2] %></td>
                            <td ><%=form[3]%></td>
                            <td style="text-align: center;">
                             <%if(form[3]!=null && form[3].toString().equalsIgnoreCase("Immovable Property") ){%>
                            	<button type="submit" class="btn btn-sm" name="immPropertyId" value="<%=form[4]%>" formaction="ImmovablePropPreview.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		<button type="submit" class="btn btn-sm" name="immPropertyId" value="<%=form[4]%>" formaction="ImmovablePropFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								   <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>
						 	<%}else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Movable Property") ){ %>  
						 	    <button type="submit" class="btn btn-sm" name="movPropertyId" value="<%=form[4]%>" formaction="MovablePropPreview.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		<button type="submit" class="btn btn-sm" name="movPropertyId" value="<%=form[4]%>" formaction="MovablePropFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								   <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>
						 	<%} %>                         
						 	</td>
                            
                        </tr>
                       <%} %>
                          <%  for(Object[] form: ConPendingList ){
                       %>
                        <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            <%-- <td style="text-align: center;"><%=form[1]%></td> --%>
                            <td ><%=form[2] %></td>
                            <td >
                            <%if(form[6]!=null){
                              if(form[6].toString().equalsIgnoreCase("C")){
                            %>
                            Permission for Construction
                            <%}else if(form[6].toString().equalsIgnoreCase("A")) {%>
                            Permission for Addition
                            <%} else{%>
                            Permission for Renovation
                            <%} }%>
                            </td>
                            <td style="text-align: center;">
                             <%if(form[3]!=null && form[3].toString().equalsIgnoreCase("Permission") ){%>
                            	<button type="submit" class="btn btn-sm" name="constructionId" value="<%=form[4]%>" formaction="ConstructionPreview.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		<button type="submit" class="btn btn-sm" name="constructionId" value="<%=form[4]%>" formaction="ConstructionFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								   <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>
							<%} %>	
						 	<%-- <%}else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Movable Property") ){ %>  
						 	    <button type="submit" class="btn btn-sm" name="movPropertyId" value="<%=form[4]%>" formaction="MovablePropPreview.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		<button type="submit" class="btn btn-sm" name="movPropertyId" value="<%=form[4]%>" formaction="MovablePropFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								   <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>
						 	<%} %>    --%>                      
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
		
		<form method="post" action="PropertyApprovals.htm" >
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
					  <th style="width:40%">Employee Name</th>
                       <th style="width:15%">Type</th>
                       <th style="width: ">Status</th>
                       <th style="width: ">Action</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SNA=0;
                          for(Object[] form:ApprovedList ){
                       %>
                        <tr>
                             
                            <td style="text-align: center;"><%=++SNA%></td>
                            <td ><%=form[2] %></td>
                            <td ><%=form[3]%>
                            <%if(form[10]!=null && form[3].toString().equalsIgnoreCase("Permission")){
                              if(form[10].toString().equalsIgnoreCase("C")){
                            %>
                            for Construction
                            <%}else if(form[10].toString().equalsIgnoreCase("A")  ) {%>
                             for Addition
                            <%} else if(form[10].toString().equalsIgnoreCase("R")){%>
                            for Renovation
                            <%} }%>
                            </td>
                            <td>
                            <%if(form[3]!=null && form[3].toString().equalsIgnoreCase("Immovable Property") ){%>
                            	<button type="submit" class="btn btn-sm btn-link w-100" formaction="ImmovablePropTransStatus.htm" value="<%=form[4]%>" name="immpropertyid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=form[8]%>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=form[7] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								</button>
						 	<%} else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Movable Property")){ %>
						 		<button type="submit" class="btn btn-sm btn-link w-100" formaction="MovablePropTransStatus.htm" value="<%=form[4]%>" name="movpropertyId"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=form[8]%>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=form[7] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								</button>						 	
						 	<%} else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Permission")){ %>
						 		<button type="submit" class="btn btn-sm btn-link w-100" formaction="ConstructionTransStatus.htm" value="<%=form[4]%>" name="constructionId"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=form[8]%>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=form[7] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								</button>						 	
						 	<%} %>
						 	</td>
						 	<td align="center">
                            <%
                            if(form[3]!=null && form[3].toString().equalsIgnoreCase("Immovable Property") ){%>
                            	<button type="submit" class="btn btn-sm" name="immPropertyId" value="<%=form[4]%>" formaction="ImmovablePropFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								   <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>
						 	<%} else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Movable Property")){ %>
						 		<button type="submit" class="btn btn-sm" name="movPropertyId" value="<%=form[4]%>" formaction="MovablePropFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
									<i style="color: #019267" class="fa-solid fa-download"></i>
							    </button>						 	
						 	<%} else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Permission")){ %>
						 		<button type="submit" class="btn btn-sm" name="constructionId" value="<%=form[4]%>" formaction="ConstructionFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
									<i style="color: #019267" class="fa-solid fa-download"></i>
							    </button>
							    <%if(form[5]!=null && form[5].toString().equalsIgnoreCase("A")) {
							      if(empNo.equalsIgnoreCase(form[11].toString()) || empNo.equalsIgnoreCase(form[12].toString())){
							    %>
							    <button type="submit" class="btn btn-sm" name="constructionId" value="<%=form[4]%>" formaction="ConstructionSanctionOrder.htm"  formmethod="GET" formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="Sanction Order">
									<i style="color: #5C469C;font-size:20px;" class="fa fa-envelope-open-text"></i>
								</button> 	
								<%} }%>					 	
						 	<%} %>
						 	</td>
                        </tr>
                       <%} %>
                          
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
<script type="text/javascript">
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