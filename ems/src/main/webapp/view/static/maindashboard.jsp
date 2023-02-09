<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="org.bouncycastle.util.Arrays"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<jsp:include page="../static/header.jsp"></jsp:include>
	
	</head>

	<body  >
	<%	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
		List<Object[]> emplogintypelist     = (List<Object[]>)request.getAttribute("logintypeslist");
		String logintype   = (String)session.getAttribute("LoginType");
		String EmpName     = (String)session.getAttribute("EmpName");
		String fromdate =(String)request.getAttribute("FromDate");		
		String todate = (String)request.getAttribute("ToDate");
		List<Object[]> emplist=(List<Object[]>)request.getAttribute("Emplist");
		String EmpId=(String)request.getAttribute("EmpId");
		String empNo=(String)request.getAttribute("EmpNo");
		List<Object[]> attendlist=(List<Object[]>)request.getAttribute("attendlist");
		SimpleDateFormat stf = new SimpleDateFormat("hh:mm aa");
	%>
	
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
		<div class="row">
			<div class="col-md-3">
				<h5 style="padding-top: 0.5rem;color: #009851;" >Attendance </h5>
			</div>
			<div class="col-md-9">
					<form action="EmpLogitypeChange.htm" method="post" style="float: right;">
							<div class="form-inline">
								<b>Login As : &nbsp;</b> 
								<select class="form-control select2" name="logintype" onchange="this.form.submit();" style="margin-top: -5px;width: 200px;align:right;">
									<%for(Object[] login:emplogintypelist){ %>
										<option value="<%=login[0]%>" <%if(logintype.equalsIgnoreCase(login[0].toString())){ %>selected <%} %>><%=login[1]%></option>
									<%} %>
								</select>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							</div>
					</form> 
				
			</div>
		</div>
	</div>	


	<div class="card dashboard-card" >
		<div class="card-body " >
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
					<form action="MainDashBoard.htm" method="POST" id="myform"> 
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	
					      <div class="row justify-content-right">
					     
					      <div class="col-4" > <label for="EmpName" style="margin-left: 306px; font-weight: bold" >Employee Name</label></div>
					      <div class="col-3" >	
					        
                              	<%                             		 
                              	if(logintype.equals("A")||logintype.equals("P")||logintype.equals("Z")) {
                              	%>
                              	<Select class="form-control select2" onchange="this.form.submit();" name="empNo" style="width:280px;" id="EmpName">
                              		<%  if(emplist!=null){
					        	   for(Object[] obj:emplist){
					        	   %>	      
					           <option value="<%=obj[0]%>"<%if(obj[0].equals(empNo)) {%>selected="selected"<%} %>><%=obj[1]%></option>
					          <%}}%>
					          </Select>	
					         <% }else{ %>
                              		 <Select class="form-control select" onchange="this.form.submit();" name="empNo" style="width:280px;" id="EmpName" readonly="readonly">
					              <% if(emplist!=null){
					        	   for(Object[] obj:emplist){
					        	  if(obj[0].equals(empNo)){ %>	      
					           <option value="<%=obj[0]%>"<%if(obj[0].equals(empNo)) {%>selected="selected" <%} %>><%=obj[1]%></option>
					          <%}}}%>
					       
					        	 </Select>
					            <%}%>
					      </div>
					 
						        <div class="col-2" style="margin-left: 5%;margin-top:8px; font-color:black!important;font-weight: bold!important;"><h6 style="font-weight: bold" >From Date :  &nbsp;</h6></div>
					            <div class="col-1" style="margin-left: -10%"> 
						             <input type="text" style="width: 165%; background-color:white; text-align: left;"  class="form-control input-sm s" <%if(fromdate!=null){%>value="<%=fromdate%>" <%}%>   readonly="readonly" id="fromdate" name="FromDate"  required="required"> 
							         <label class="input-group-addon btn" for="testdate"  ></label>              
						        </div>							 						
						        <div class="col-2"  style="margin-left: 4%;margin-top:8px;font-color:black;"><h6 style="font-weight: bold">To Date : &nbsp;</h6></div>
							    <div class="col-1" style="margin-left: -12%">						
							         <input type="text" style="width: 165%; background-color:white;" class="form-control input-sm mydate" readonly="readonly" <%if(todate!=null){ %>  value="<%=todate%>"<%} %>  id="todate" name="ToDate"  required="required"> 							
							         <label class="input-group-addon btn" for="testdate"></label>    
							    </div>
				          </div>      
				                           	
                  </form>	      
    <form action="MainDashBoard.htm" method="POST">
	     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	               
	          <div class="table-responsive" >
	                <table  class="table table-bordered table-hover table-striped table-condensed" id="myTable"  >
			           <thead>
			                  
			                  <tr>
			                       <th>SN</th>
			                       <th>Emp No</th>
			                       <!-- <th>Status</th> -->
			                       <th>Attendance Date</th>
			                       <th>In Date</th>
			                       <th>In Time</th>
			                       <th>Out Date</th>		                       
			                       <th>Out Time</th>
			                       <th>Duration </th>
			                  </tr>	
			                  	           			           
			           </thead>
			           <tbody>
			                    <%if(attendlist!=null){ int sn=0;
			                  for(Object[] obj:attendlist){ 
			                  
			                 SimpleDateFormat st=new SimpleDateFormat("HH:mm");
			                 Date time=st.parse("7:50");
			                 Date some=st.parse(obj[5].toString());
			                
			                  %>
			                   <tr> 
			                         <td style="text-align: center;"><%=++sn%></td>
			                         <td  style="text-align: center;"><%=obj[0] %></td>	
			                        <%--   <td <% if((obj[1].toString()).equalsIgnoreCase("present")){%>style="color:green;font-weight: bold;text-align: center;"<%}else {%>style="color:red;font-weight: bold;text-align: center;"<% }%>><%=obj[1] %></td>		                          --%>
			                         <td style="text-align: center;"><%=sdf.format(obj[2])%></td>			                        
			                         <td style="text-align: center;"><%if(obj[3]!=null){%><%=sdf.format(obj[3])%><%}else {%>-<%} %></td>
			                         <td style="text-align: center;color:blue;"><%if(obj[3]!=null){%><%=stf.format(obj[3])%><%}else {%>-<%} %></td>
			                         <td style="text-align: center;"><%if(obj[4]!=null){%><%=sdf.format(obj[4])%><%}else {%>-<%} %></td>
			                         <td style="text-align: center;color:blue;"><%if(obj[4]!=null){%><%=stf.format(obj[4])%><%}else {%>-<%} %></td>
			                         <td <%if(obj[5]!=null){if(some.before(time)){%> style="color:red;font-weight: bold;text-align: center;"<%}else{%> style="color:green;font-weight:bold;text-align:center;"<%}%>><%=obj[5]%><%}else {%>00:00<%} %></td>
			                   </tr>
			                   <%}} %>          			           
			           </tbody>									
			     </table>
			  </div> 
			</form>
		</div>
	
	</div>
<script>

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