<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@  page import="java.util.*" %>
  <%@ page import=" java.time.LocalDate" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title> Property Details List</title>
<style>
#edit,#delete,#print{

margin-left: 10px;
}
#add{
margin-left: 38%;
}
#print{
margin-left: 42%;
}
</style>


</head>
<body>
         <script>   
	      document.getElementById('alrt');
          setTimeout(function(){document.getElementById('alrt').innerHTML='';},2000);
      </script>
          
           <% String year=(String)request.getAttribute("year");;
           List<Object[]> list=(List<Object[]>)request.getAttribute("list");
           Object[] empData=(Object[])request.getAttribute("Empdata");
           %>
           
           <input type="hidden" id="List" value="<%=list %>">
          
     <div class="card-header page-top">
		<div class="row">
		<div class="col-md-7">
				<h5>Annual Property Return List<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[0]%> (<%=empData[1]%>)<%}%>
						</b></small></h5>
			</div>
			   <div class="col-md-5">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item"><a href="PropertyDashBoard.htm">Property</a></li>
						<li class="breadcrumb-item active " aria-current="page">  Property Details List </li>
					</ol>
				</div>
			</div>
		 </div>
		<%String result=(String)request.getAttribute("result");
          String resultFail=(String)request.getAttribute("resultFail");
          if(resultFail!=null){
          %>
        <div align="center">
		<div class="alert alert-danger" role="alert">
        	<%=resultFail %>
        </div>
   	</div>
	<%}if(result!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
        	<%=result %>
        </div>
    </div>
            <%} %>		 
		  <div class="page card dashboard-card" style="min-height: 650px;max-height: 660px;">	
	    	<div class="card-body" >
	    	<div class="card">
	    	<form action="PropertyReport.htm" method="GET" id="frm"  style="margin-top: 1%; margin-left: 62%;">
               <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	    	 <table>
                   <tr>
                       <th ><label for="PropertyYear">Property Details for the Year &nbsp;&nbsp;</label></th>
                       <td><input type="text" id="PropertyYear" name="PropertyYear" class="form-control input-sm date" onchange="this.form.submit()" value="<%=year%>"style="width:195px;"></td>
                   </tr>
            
            </table>
            </form>
	           <div class="card-body">
	             <form action="PropertyReport.htm" method="POST" id="myform"> 
	               <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	               
	               <div class="table-responsive" >
	                <table  class="table table-bordered table-hover table-striped table-condensed" id="myTable"  >
	                     <thead>
	                           <tr> 
	                                <th>Select</th>
	                                <th>Property and Address</th>
	                                <th>Present Property value</th>
	                                <th>Partnership and Relationship</th>
	                                <th>Mode of Property Acquired</th>
	                                <th>Annual Income from property</th>
	                                <th>Remarks</th>
	                            </tr>
	                       </thead>     
	                   <tbody>
	                         
	                          <%if(list!=null){for(Object[] obj:list) {%>
	                           <tr>
	                              <td align="center"><input type="radio" value="<%=obj[0] %>" name="PropertyId" required="required">
	                              <td><%=obj[2] %> <br>Address: <%=obj[3] %></td>
	                              <td align="right"><%=obj[4] %></td>
	                              <td><%=obj[5] %></td>
	                              <td><%=obj[6] %></td>
	                              <td align="right"><%=obj[7] %></td>
	                              <td><%=obj[8] %></td> 
	                           </tr>
	                           <%}}else {%>
	                        	 <tr><td>  <p>No data found</p></td></tr>
	                          <% }%> 
	                       </tbody>
	               
	               </table>	  
	                <button type="submit" id="add" class="btn btn-sm add-btn" value="ADD" name="action" <%if(Integer.parseInt(year)!=LocalDate.now().minusYears(1).getYear()){ %>style="margin-left: 43%;display:none;" <%} %> formnovalidate="formnovalidate">ADD</button>    
	             <button type="submit" id="edit" class="btn btn-sm edit-btn" value="EDIT" name="action" <%if(list==null||list.isEmpty()||Integer.parseInt(year)!=LocalDate.now().minusYears(1).getYear()){ %>style="display:none;" <%} %>>EDIT</button>
	              <button type="submit" id="delete"class="btn btn-sm delete-btn" name="action" onclick="return confirm('Are You Sure Want to Delete?')"  value="DELETE" <%if(list==null||list.isEmpty()||Integer.parseInt(year)!=LocalDate.now().minusYears(1).getYear()){ %>style="display:none;"<%} %>>delete</button>
	               <button type="submit" id="print" class="btn btn-sm print-btn" formaction="PrintPropertyReport.htm" formtarget="_target"  formmethod="post" name="year" value="<%=year%>" <%if(list==null||list.isEmpty()){ %> style="display:none;" <%} else%>style="margin-left: 56%;margin-top:-57px;"
	                      formnovalidate="formnovalidate">PRINT</button> 
	                       
	       </div>
	     </form>            
       </div>
	 </div>
  </div>
</div>
<script>
$('.date').datepicker({
    format: "yyyy",
    orientation: "bottom",
    minViewMode: "years",   
    autoclose:"true"
});
</script>
</body>
</html>