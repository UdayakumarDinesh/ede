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
	
	List<Object[]> ApprovalList=(List<Object[]>) request.getAttribute("ApprovalList");
	List<Object[]> DGMAllBanks=(List<Object[]>) request.getAttribute("DGMAllBanks");
	Object[] empNameAndDesi=(Object[]) request.getAttribute("empNameAndDesi");

%>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Bank Details <small><b>&nbsp; - &nbsp;<%if(empNameAndDesi !=null) %> <%=empNameAndDesi[0] %> (<%=empNameAndDesi[1] %>)</b></small></h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="BankDetails.htm">
							Bank Details </a></li>		
					<li class="breadcrumb-item active " aria-current="page">
							Bank Details Approval </a></li>
					
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

  		
            
   	<div class="card-body main-card">
   		
   		  <form action="CircularDashBoard.htm" method="POST" id="circularForm">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:5%">SN</th>
					  <th style="width:15%">EmpNo</th>
					  <th style="width:40%">Employee Name</th>
                      <!--  <th style="width:15%">Type</th> -->
                       <th style="width:25%">Action</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SN=0;
                          for(Object[] form:DGMAllBanks ){
                       %>
                        <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            <td style="text-align: center;"><%=form[1]%></td>
                            <%-- <td ><%=form[2] %></td> --%>
                            <td ><%=form[3]%></td>
                            <td style="text-align: center;">
						 		<button type="submit" class="btn btn-sm" name="bankId" value="<%=form[0] %>" formaction="GetBankDetailForm.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 	</td>
                            
                        </tr>
                       <%} %>
                          
                 </tbody>
   
            </table>
          </div>
          <input type="hidden" name="isApproval" value="Y">
         </form>
     
     
	</div>
 	

  
<script>


</script>

</body>
</html>