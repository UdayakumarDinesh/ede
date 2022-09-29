<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
 .table thead tr{
	background-color: white;
	color: black;
	font-size: 13px !important;
}
</style>
</head>
<body >

<%List<Object[]> UploadMcFc=(List<Object[]>)request.getAttribute("UploadMcFc");
  String year=(String)request.getAttribute("yr");
  SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Upload MC-FC</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Upload MC-FC</li>
					</ol>
				</div>
			</div>
	</div>	
    
<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >
   
   	<div class="row">
   		
   		<div class="col-md-12">
   			<form action="UploadMcFc.htm" method="POST" id="preview" style="background-color:#0e6fb6;  border-radius: 4px;">
			    <div class="row" >			    
				    <div class="col-md-11" > 
				     <span class="text-warning" style="font-size: 16px;font-weight: bolder;margin-top:5px;">Upload Your Medical/Fitness Certificate</span>     
				    </div>
				    <div class="col-md-1">
					    <div class="group-form">
					 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					    <input class="form-control  form-control" type="text" id="year" value="<%=year %>" onchange="this.form.submit()" name="yr">
					    </div>
				    </div>
				</div>
    		</form>
   		</div>
   		</div>
   		
   		<div class="row" style="margin-top: 7px;">
   		 <div class="col-md-12">
   		  <form action="UploadMcFc.htm" method="post" enctype="multipart/form-data">
                      
   		   <table class="table table-hover  table-striped table-condensed table-bordered">
				        <thead>
					          <tr>
                                   <th>Leave Type</th>
                                   <th>Leave Period</th>
                                   <th>Select Certificate</th>
                                   <th>Select File</th>
                                   <th>Action</th>
                                   <th>View PDF</th>
                                   <th>Status</th>
                          	</tr>
				       </thead>
				<tbody>
				        <% 
				         if(UploadMcFc!=null&&UploadMcFc.size()>0){
                         for(Object[] ls:UploadMcFc){ 
                        %> 
                         
                           <tr>
                           <td><%=ls[4]%></td>
                                <td>From <%=sdf.format(ls[1])%> -To <%=sdf.format(ls[2])%></td>
                                
                                <%if(ls[6].toString().equals("0")||ls[5].toString().equals("0")){%>
                                                              
                                <td>

                                <%if(ls[5].toString().equals("0")){%>
                                MC&nbsp;&nbsp;<input type="radio" name="mcFc"  value="M" required>	
					             &nbsp;&nbsp;
					             <%}if(ls[6].toString().equals("0")){%>
					             FC&nbsp;&nbsp;<input type="radio" name="mcFc" value="F" required>	
					             <%} %>					
				                </td>
                                <td><input class="btn btn-default btn-sm form-control" type="file" name="document" accept="application/pdf" required="required"/></td>
                                <td><button class="btn btn-success btn-sm" type="submit" name="applid" value="<%=ls[0]%>">Upload</button></td>

                                <%}else{%>
                                <td>MC-FC</td>
                                 <td></td>
                                  <td>Uploaded</td>
                                <%} %>
                                <td>

                                <%if(!ls[5].toString().equals("0")){%>
                                <button class="btn btn-link btn-sm btn-outline" formaction="downloadMcFc.htm" formnovalidate="formnovalidate" name="MC" value="<%=ls[0]%>" >
	                            MC
	                            </button>
                                <%}%>
                                <%if(!ls[6].toString().equals("0")){%>
                                                              
	                           <button class="btn btn-link btn-sm btn-outline" formaction="downloadMcFc.htm"  formnovalidate="formnovalidate" name="FC" value="<%=ls[0]%>" >
	                            FC
	                            </button>

                                 <%}%>

                                </td>
                                <td>

	                           <button class="btn btn-link btn-sm btn-outline" formaction="leavestatus.htm"  name="applId" formnovalidate="formnovalidate" value="<%=ls[0]%>" >
	                             <%=ls[3]%> 
	                            </button>

                               </td>
                              </tr>

                            <%}}else{%>
                            <tr><td  colspan="7"><span class="label label-warning">No Record Found</span></td></tr>
                            <%}%>
                        
			    </tbody>
					</table>
   		                                  	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
                                    </form>
   		 </div>
   		</div>
   		</div>
   		</div>
<script type="text/javascript">
$('#year').datepicker({
	 format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    autoclose: true,
	    todayHighlight: true
});

</script>
</body>
</html>