<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
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
	//String fromdate = (String)request.getAttribute("fromdate");
	//String todate = (String)request.getAttribute("todate");
	List<Object[]> SearchList=(List<Object[]>)request.getAttribute("SearchList");
	//List<Object[]> DepTypeList=(List<Object[]>)request.getAttribute("DepTypeList");
	
	String Search = (String)request.getAttribute("Search");
%>


<div class="card-header page-top ">
	<div class="row">
		<div class="col-md-3">
			<h5>Circular Dashboard</h5>
		</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					
				</ol>
			</div>
		</div>
</div>	
<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
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

  		<div class="card" >
	        <div class="card-header">
	              <form action="CircularDashBoard.htm" method="POST"> 
	                   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					   <div class="row" >
					      		
						<div class="col-md-4" ></div>
								
						<div class="col-md-4">
                        	<input type="text" placeholder="Search Circular" name="search" value="<%if(Search!=null) {%><%=Search %><%} %>" autocomplete="off" style=" width: 100%;padding: 5px;border-left: none;border-top: none;border-right: none;border-down: black;">
                        </div>
                        <div class="col-md-1" >
                        	<button type="submit" id="button" ><i class="fa fa-search"></i></button>
                        </div>
                                
				          </div>       	
                  </form>
                </div>
            </div>  
            
   <div class="card-body main-card">
        <form action="CircularDashBoard.htm" method="POST" id="circularForm">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:5%">SN</th>
					  <th style="width:15%">Department</th>
					  <th style="width:15%">Circular No</th>
                       <th style="width:10%">Date</th>
                       <th style="width:50%">Subject</th>
                       <th style="width:5%">Download</th>
                  	</tr>
				</thead>
                 <tbody>
                       <%if (SearchList!=null){ int SN=0;
                                  for(Object[] ls:SearchList ){
                                %>
                        <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            <td><%=ls[5]%></td>
                            <td ><%=ls[1] %></td>
                            <td style="text-align: center;"><%= ls[2]%></td>
                            <td><%=ls[3] %></td>
                            <td style="text-align: center;">
                            	<button type="submit" class="btn btn-sm" name="CircularId" value="<%=ls[0] %>" formaction="DepCircularDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download" formtarget="_blank">
						 			<i class="fa-solid fa-download " style="color: green;"></i>
						 		</button>
						 	</td>
                            
                        </tr>
                       <%}} %>
                          
                 </tbody>
   
            </table>
          </div>
         </form>
        </div>
 	

  
<script>


	   

</script>

</body>
</html>