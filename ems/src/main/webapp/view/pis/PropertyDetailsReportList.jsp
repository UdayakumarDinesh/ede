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
           List<Object[]> list=(List<Object[]>)request.getAttribute("list"); %>
           <input type="hidden" id="List" value="<%=list %>">
          
     <div class="card-header page-top">
		<div class="row">
			<div class="col-md-4">
			      <h5> Property Details List</h5>
			</div>
			   <div class="col-md-8 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item active " aria-current="page">  Property Details List </li>
					</ol>
				</div>
			</div>
		 </div>
		   <%String result=(String)request.getAttribute("result");
          String resultFail=(String)request.getAttribute("resultFail");
          if(result!=null){
          %>
             <center id="alrt">
            <div class="alert alert-success" style="width:50%; role:alert ;margin-top: 20px;">
             <%=result %>
          </div></center>
           <%} %>
             <%if(resultFail!=null) {%> 
           <center id="alrt">
        <div class="alert alert-danger"role="alert" style="width:50%;margin-top:20px;">
       <%=resultFail%>
         </div></center>
            <%} %>
		 <div style="margin-top:20px;">
              <form action="PropertyReport.htm" method="GET" id="frm"  style="margin-left: 32%;"> 
              <select class="form-control select2" name="PropertyYear" id="PropertyYear" required="required"  style="width:280px; margin-left:180px;"  data-container="body" data-live-search="true" onchange="this.form.submit()">
                         <!-- <option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option> -->
                         <option value="2022"<%if((year).equalsIgnoreCase("2022")){ %>selected="selected"<%} %>>2022</option>
                         <option value="2021"<%if((year).equalsIgnoreCase("2021")){ %>selected="selected"<%} %>>2021</option>
                         <option value="2020"<%if((year).equalsIgnoreCase("2020")){ %>selected="selected"<%} %>>2020</option>
                         <option value="2019"<%if((year).equalsIgnoreCase("2019")){ %>selected="selected"<%} %>>2019</option>
                         <option value="2018"<%if((year).equalsIgnoreCase("2018")){ %>selected="selected"<%} %>>2018</option>
                         <option value="2017"<%if((year).equalsIgnoreCase("2017")){ %>selected="selected"<%} %>>2017</option>
                        <option value="2016"<%if((year).equalsIgnoreCase("2016")){ %>selected="selected"<%} %>>2016</option>
                        <option value="2015"<%if((year).equalsIgnoreCase("2015")){ %>selected="selected"<%} %>>2015</option>
                           	        	
            </select>
            </form>
          </div>
		  <div class="page card dashboard-card" style="min-height: 650px;max-height: 660px;">	
	    	<div class="card-body" >
	    	<div class="card">
	    	
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
	              <button type="submit" id="delete"class="btn btn-sm delete-btn" name="action" value="DELETE" <%if(list==null||list.isEmpty()||Integer.parseInt(year)!=LocalDate.now().minusYears(1).getYear()){ %>style="display:none;"<%} %>>delete</button>
	               <button type="submit" id="print" class="btn btn-sm print-btn" formaction="PrintPropertyReport.htm" formtarget="_target"  formmethod="post" name="year" value="<%=year%>" <%if(list==null||list.isEmpty()){ %> style="display:none;" <%} else%>style="margin-left: 57%;margin-top:-57px;"
	                      formnovalidate="formnovalidate">PRINT</button> 
	                       
	</div>
	</form>            
	 </div>
	 </div>
	 </div>
	 </div>
	 
</body>
</html>