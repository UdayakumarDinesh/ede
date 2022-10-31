<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@ page import="com.vts.ems.circularorder.model.EMSCircular" %>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Circular Add</title>

<style>
.card{


}
</style>
</head>
<body>
<% EMSCircular CirEditDetails = (EMSCircular)request.getAttribute("circularDetails");   %>



<div class="card-header page-top">
  <div class="row">
  
     	<div class="col-md-3">
     	<%if(CirEditDetails==null){  %>
     	<h5>Circular Add</h5>
     	<%}else{%>
		<h5>Circular Edit</h5>
		<%	}  %> 
     	</div>
     	
     	<div class="col-md-9">
			<ol class="breadcrumb">
				<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>

				<li class="breadcrumb-item "><a href="CircularList.htm"> Circular List </a></li>
						<%if(CirEditDetails==null){  %>
                        <li class="breadcrumb-item active " aria-current="page">Circular Add</li>
                         <%}else{%>
                          <li class="breadcrumb-item active " aria-current="page">Circular Edit</li>
                        <%}%> 
			</ol>
		</div>  
 </div>
</div>


<div class="page card dashboard-card">
 <div class="card-body" >
	<div class="card" >
	  <div class="card-body main-card  " align="center" >
	<%if(CirEditDetails!=null){ %> 
		<form name="myfrm" action="CircularEditSubmit.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data" >
	<%}else{ %>	
       <form name="myfrm" action="CircularAddSubmit.htm" method="POST" id="myfrm1" autocomplete="off"  enctype="multipart/form-data">		
    <%}%>	
	  <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	  
	  
	   <div class="row" align="center"> 
	   
	        <div class="col-md-3" style="margin-left: 20px;" >
			   <div class="form-group">
				  <label><b>Circular No</b><span class="mandatory"	style="color: red;">*</span></label>
				  <input type="text" style="width: 70%; " class="form-control input-sm "  name="circularno"  id="circularNo" <%if(CirEditDetails!=null && CirEditDetails.getCircularNo()!=null){%>value="<%=CirEditDetails.getCircularNo()%>" <%}%>   maxlength="20"  required="required" >
				</div>
			</div>
			
	     <div class="col-md-2" style="margin-left: 200px;" >
			   <div class="form-group">
				  <label><b>Subject</b><span class="mandatory"	style="color: red;">*</span></label>
				   <textarea id="cirsubject" name="cirSubject" rows="2" cols="50" style="border-bottom-color: gray;" required="required" ><%if(CirEditDetails!=null && CirEditDetails.getCirSubject()!=null){%><%=CirEditDetails.getCirSubject()%><%}%></textarea> 
				</div>
			</div>
     
	      
			
	  </div>
	  
	 <div class="row" align="center">  
	     
	     
	      <div class="col-md-3" style="margin-left: 20px;">
			   <div class="form-group">
				  <label><b>Circular Date</b><span class="mandatory"	style="color: red;">*</span></label>
				  <input type="text" style="width: 70%; " class="form-control input-sm " value="" name="circularDate"  id="circulardate"   required="required"  >
				</div>
			</div>
				
	       
			
			  <div class="col-md-3" style="margin-left: 200px;">
			  <div class="form-group">
					<label><b>Upload File</b> <span class="mandatory"	style="color: red;">*</span></label>
			<%if(CirEditDetails!=null ){%>
			<%System.out.println("filepath"+CirEditDetails.getCircularPath());%>
			<input type="file" name="FileAttach"   accept="application/pdf"  class="form-control input-sm "  value="<%=CirEditDetails.getCircularPath()%>" required="required"  id="file"  >
			<%}else{ %>
			<input type="file" name="FileAttach"   accept="application/pdf"  class="form-control input-sm "  value="" required="required"  id="file"  >
			<%} %>			 
			<%if(CirEditDetails!=null && CirEditDetails.getCircularPath()!=null){%>	
			<button type="submit" formnovalidate="formnovalidate" class="btn btn-sm" style="height: 10%; width: 4%;  margin-top: 2.5%;" 
			name="path" value="<%=CirEditDetails.getCircularPath()%>//<%=CirEditDetails.getCirFileName()%>" 
			formaction="download-CirFile-attachment" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download"
			> <i style="color: #019267" class="fa-solid fa-download fa-2x"></i></button>
			<%} %>		
							
			</div>
		  </div>
		  
		  
		
	 </div> 
	 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	  
	
	 <br>
	      <div class="row" >
		   <div class="col-12" align="center">
		   
		    <%if(CirEditDetails!=null ){%>
		    <input type="hidden" name="circularIdSel" value="<%if(CirEditDetails!=null){%><%=CirEditDetails.getCircularId()%><%}%>">
		    <button type="submit" class="btn btn-sm submit-btn " id="editCircular"  onclick="return confirm('Are You Sure To Submit?');" >UPDATE</button>
		    <%}else{ %>
		     <button type="submit"   class="btn btn-sm submit-btn"  id="addCircular" onclick="return confirm('Are You Sure To Submit?');"  >SUBMIT</button>
		     <%} %>  
		    	</div>
		    </div> 
				
			
						
		<%if(CirEditDetails!=null){%> 
		</form>
		<%}else{%>
	    </form>
	  <%}%>
	 
	  
	  </div>
	
	
	</div>
  </div>
 </div>
		


<script type="text/javascript">

$('#circulardate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,

	<% if(CirEditDetails!=null){%>
	 "startDate" : new Date('<%=CirEditDetails.getCircularDate()%>'), 
	<%}%>
	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>



















</body>
</html>