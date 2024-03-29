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
		<jsp:include page="../static/sidebar.jsp"></jsp:include>
	</head>

	<%	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
		String logintype   = (String)session.getAttribute("LoginType");
		String EmpName     = (String)session.getAttribute("EmpName");
		String fromdate =(String)request.getAttribute("FromDate");		
		String todate = (String)request.getAttribute("ToDate");
		List<Object[]> emplist=(List<Object[]>)request.getAttribute("Emplist");
		String EmpId=(String)request.getAttribute("EmpId");
		String empNo=(String)request.getAttribute("EmpNo");
		List<Object[]> attendlist=(List<Object[]>)request.getAttribute("attendlist");
		Object LastSyncDateTime=(Object)request.getAttribute("LastSyncDateTime");
		SimpleDateFormat stf = new SimpleDateFormat("hh:mm aa");
	%>
	
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
		<div class="row">
			<div class="col-md-3">
				<%if(!logintype.equalsIgnoreCase("CE")){ %>
				<h5 style="padding-top: 0.5rem;color: #009851;" >Dashboard : Punch Data </h5>
				<%}else{ %>
					<h5 style="padding-top: 0.5rem;color: #009851;" >Dashboard</h5>
				<%} %>
			</div>
			<div class="col-md-9">
				
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


				<%if(!logintype.equalsIgnoreCase("CE")){ %>
					<form action="AttendanceDashBoard.htm" method="POST" id="myform"> 
	             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	
					     				    
					 <table style="width: 100%" > 
					        <tr>					       
					         <td style="text-align: left;"><button type="submit"  class="btn btn-sm submit-btn"  formaction="AttendancePieChart.htm" formmethod="POST">Pie Chart</button></td>
					         <th style="text-align: center;" ><h6 style="font-weight: bold;">Last Sync Date And Time :&nbsp;</h6></th>
					         <td> <h6 style="font-weight: bold;color:blue;"> <%if(LastSyncDateTime!=null){ %><%=sdf.format(LastSyncDateTime)%>&nbsp;&nbsp;<%=stf.format(LastSyncDateTime)%><% }%></h6></td>
					        <th ><h6 style="font-weight: bold;" class="text-nowrap">&nbsp;&nbsp;&nbsp;Employee Name :&nbsp;</h6></th>
					        <td>
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
					            </td>
					            <td>&nbsp;&nbsp;&nbsp;&nbsp;<td>
					      <th ><h6 style="font-weight: bold;" class="text-nowrap">From Date : &nbsp;</h6> </th>
				         <td><input type="text" style="width:7rem;; background-color:white; text-align: left;"  class="form-control input-sm" <%if(fromdate!=null){%>value="<%=fromdate%>" <%}%>   readonly="readonly" id="fromdate" name="FromDate"  required="required">  </td>
				        <th><h6 style="font-weight: bold;" class="text-nowrap"> &nbsp; &nbsp; &nbsp;To Date : &nbsp;</h6></th>
				        <td> <input type="text" style="background-color:white;width: 7rem;" class="form-control input-sm mydate" readonly="readonly" <%if(todate!=null){ %>  value="<%=todate%>"<%} %>  id="todate" name="ToDate"  required="required"></td>
				        
				   </tr>
		    </table> 				         			             				                          	
      </form>	
                  	<br>      
				    <form action="AttendanceDashBoard.htm" method="POST">
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
							                         <td style="text-align: center;color:blue;"><%if(obj[4]!=null){%><%=stf.format(sdf.parse(obj[4].toString()))%><%}else {%>-<%} %></td>
							                         <td <%if(obj[5]!=null){if(some.before(time)){%> style="color:red;font-weight: bold;text-align: center;"<%}else{%> style="font-weight:bold;text-align:center;"<%}%>><%=obj[5]%><%}else {%>00:00<%} %></td>
							                   </tr>
							                   <%}} %>          			           
							           </tbody>									
							     </table>
							  </div> 
							</form>
				<%} %>
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