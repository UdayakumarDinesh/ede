<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.* ,java.time.LocalDate"%>
    <%@ page import="java.time.format.DateTimeFormatter" %>
    <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Reply To Request Message</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%

List<Object[]> Reqlist = (List<Object[]>)request.getAttribute("msglist");
List<Object[]> emplists = (List<Object[]>) request.getAttribute("emplist");
String empid = (String)request.getAttribute("emp");
String fromdate = (String)request.getAttribute("fromdate");
String todate  =  (String)request.getAttribute("todate");

%>

<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
				<h5>Employee Request Message</h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>					
						<li class="breadcrumb-item active " aria-current="page"> Request Message</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	
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
			<form action="RequestMessagelist.htm" method="POST">
					<div class="nav navbar bg-light dashboard-margin custom-navbar">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="col-md-1"></div>
					 <label style=" font-weight: 800">Employee:<span class="mandatory">*</span></label>
					<select name="employee" id="employee" class="form-control select2"  style="width: 30%;" >
											<%if(emplists!=null){  for( Object[] obj: emplists){ %>
											<%if(empid!=null){ %>
												<option value="<%=obj[0]%>" <%if(empid.equalsIgnoreCase(obj[0].toString())){ %>selected="selected" <%}%>><%=obj[1]%></option>
												<%}else{%>
												<option value="<%=obj[0]%>" ><%=obj[1]%></option>
												<%}%>
											<%} }%>
						                </select>
					
					<label style=" font-weight: 800">From Date : </label>
					<input  class="form-control form-control date"  data-date-format="dd-mm-yyyy"  id="fromdate" name="fromdate" onchange="this.form.submit()" required="required"  style="width: 120px;"	 <%if(fromdate!=null){%> value="<%=fromdate%>" <%}%>   >
								  
				
					<label style="font-weight: 800">To Date : </label>
					<input  class="form-control form-control" data-date-format="dd-mm-yyyy"  id="todate" name="todate" onchange="this.form.submit()"  style="width: 120px;" <%if(todate!=null){%> value="<%=todate%>"<%}%>>
										
			      <div class="col-md-1"></div>
			      
				    </div>
			</form>
			
					<div class="card-body main-card">
        			
        		  <form action="AdminReplyToReqMsg.htm" method="POST" name="myfrm1" id="myfrm1"  > 	 						
               	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
                                          
                        <div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th style="width: 5%;">  SlNo </th>
										<th style="width: 10%;"> Request Date  &  Time </th>
										<th style="width: 10%;"> Request From     </th>
										<th style="width: 40%;"> Request Message  </th>	
										<th style="width: 10%;"> Response Date  &   Time </th>									
										<th style="width: 40%;"> Response Message  </th>
										<th style="width: 10%;"> Action            </th>
									</tr>
								</thead>
								<tbody>	
								<%if(Reqlist!=null){ int slno=0; for(Object[] obj:Reqlist){ 
								String respose = "response"+obj[0];
								String employeeid = "employeeid"+obj[0];
								%>							
										<tr>
											<td style="text-align: center;"><%=++slno%></td>
											<td align="center"> <%if(obj[5]!=null && obj[6]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(obj[5].toString()) %><br><%=obj[6]%><%}else{%>--<%}%></td>
											<td><input type="hidden" id="employeeid" name="<%=employeeid%>" value="<%=obj[4]%>"> <%=obj[1] %></td>
											<td align="left"><%=obj[2]%></td>		
											<td align="center"><%if(obj[7]!=null && obj[7]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(obj[7].toString()) %><br><%=obj[8]%><%}else{%>--<%}%></td>
											<td> <textarea class="form-control"  name="<%=respose%>"  id="<%=respose%>" rows="5" cols="60"><%if(obj[3]!=null){%><%=obj[3]%><%}%></textarea></td>
											<td><%if(obj[3]==null){ %> <button type="submit" class="btn btn-sm submit-btn" style="margin-top: 70%;" name="action" value="<%=obj[0]%>" onclick="return checkEmptyReply('<%=respose%>')">Submit</button><%}%></td>		
										</tr>	
										
								<%}} %>							
								</tbody>
							</table>					
						</div>		
					
						 </form>     
	             </div>
		   	 </div>				
	        </div>
	    

</body>

<script type="text/javascript">



function checkEmptyReply(inputid)
{
	var reply = $('#'+inputid).val();
	console.log(reply);
	
	if(reply.trim()===''){
		alert('Please Enter a Valid Reply to  Submit');
		return false;
	}else
	{
		return confirm('Are You Sure to Submit?');
	}
}






	$('#fromdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
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
		"minDate" :$("#fromdate").val(),   
		/* "startDate" : new Date(), */
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
	

</script>
</html>