<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.time.LocalDate ,java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Insert title here</title>
</head>
<body>
<%String empname = (String)request.getAttribute("empname"); %>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>MT Link Trip <small> <%if(empname!=null){%> <%=empname%> <%}%> </small> </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Link Trip</li>	
					</ol>
				</div>
			</div>
</div>
<% 
String comp=(String)request.getAttribute("comp"); 
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
List<Object[]> appllist=(List<Object[]>)request.getAttribute("AllListApp");
List<Object[]> triplist=(List<Object[]>)request.getAttribute("triplist");
%>
 <%String ses=(String)request.getParameter("result"); 
	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){%>
	<center><div class="alert alert-danger" role="alert"><%=ses1 %></div></center>
	<%}if(ses!=null){%>
	<center><div class="alert alert-success" role="alert" ><%=ses %> </div></center>
    <%}%>
    
    
 <div class=" page card dashboard-card">
		
		<div class="card-body" >
			<div class="row">
	<div class="col-12">

      <div class="card" >
			
     <div class="card-body"  >
   <%if(comp.equalsIgnoreCase("link")){ %>
   
				<% if(!(request.getParameter("message")==null)){ %>
				<div class="alert alert-success"><strong><%= request.getParameter("message") %></strong> </div>
				 <%} %>
  
  
  
   <%if(appllist!=null&&appllist.size()!=0){%>
 
  <% for(Object[] list:appllist){ %>
  <form  action="MtTripLink.htm" method="POST">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="table-responsive">
		  <table class="table table-bordered table-hover table-striped table-condensed"  > 
   
      <thead >
      		<tr >
	            <th style="text-align: left; font-size: 15px; " >For Req# <%=list[0]%>- <%=list[2] %>-<%=list[3] %> Request From <%=list [7]%> To <%=list[8] %> on <%=sdf.format(list[4]) %> To <%=sdf.format(list[16]) %> At <%=list[5] %></th>
			    <th style="text-align: left; font-size: 12px; ">Comment</th>
			    <th style="text-align: left; font-size: 12px; ">Link</th>
	    	</tr>
	    </thead>
      <tbody>
  
	   <tr>
		    <td  style="text-align: left;">
				    <input type="hidden" name="appid" value="<%=list[12] %>">
				    <input type="hidden" name="EmpId" value="<%=list[1] %>">
		    

					<select class="form-control select2"  style="width:100%" name="tripid" id="tripid" data-container="body" data-live-search="true"  required="required" >
						<option value="" disabled="disabled" selected="selected" hidden="true" >--Select--</option>
						
					    <%for(Object[] trip:triplist){
							if(sdf.format(trip[5]).equalsIgnoreCase(sdf.format(list[4]))){ %>
					     <option value="<%=trip[1]%>"><b>Trip#&nbsp; <%=trip[2]%> :&nbsp; <%=sdf.format(trip[5]) %> To <%=sdf.format(trip[9]) %> AT&nbsp; <%=trip[6]%>&nbsp;Vehicle &nbsp;&nbsp;<%=trip[7]%>&nbsp;&nbsp;(<%=trip[8]%>) &nbsp;by&nbsp; <%=trip[0]%>&nbsp;Covering &nbsp;&nbsp;<%=trip[4]%>  </b></option> 
					     <%}}%>
					     <option value="0">Vehicle not available</option>
					     <option value="-1">CMTD not available</option>
				    </select>
		    </td>
		    <td>
		        <textarea  name="comment" style="height: 35px; "></textarea> 
		    </td>
		    <td>
		          <button type="submit" class="btn btn-sm " style="background-color: #128260; color: white;"  onclick="return confirm('Are you sure To Link?')" >LINK</button>
		    </td>
	    </tr> 
     </tbody> 
    </table>
    
   </div>  
   </form>
    <%}%>

   <% }else{%>
   
    	<center><b style="color: red;">No Data Found</b></center>
    	<% }} %>  
			</div>
		</div>
		</div>
		</div>
		</div>
</div>	

</body>

</html>