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
</head>
<body>
<!--CircularDetails cir = (EMSCircular)request.getAttribute("circularDetails");  -->



<div class="card-header page-top">
  <div class="row">
  
     	<div class="col-md-3">
     	<!-- if(CircularDetails==null){ -->
     	<h5>Circular Add</h5>
     		<!-- }else{
				<h5>Circular Edit</h5>
			}  -->
     	</div>
     	
     	<div class="col-md-9">
			<ol class="breadcrumb">
				<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
				<!-- <li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li> -->
				<li class="breadcrumb-item "><a href="CircularList.htm"> Circular List </a></li>
						<!-- if(CircularDetails==null){  -->
                        <li class="breadcrumb-item active " aria-current="page">Circular Add</li>
                         <!--  else
                          <li class="breadcrumb-item active " aria-current="page">Circular Edit</li>
                        }  -->
			</ol>
		</div>  
 </div>
</div>


<div class="page card dashboard-card">
 <div class="card-body" >
	<div class="card" >
	  <div class="card-body main-card  " align="center" >
	
	
	<!-- if(circularDetails==null){  -->
	<form name="myfrm" action="CircularAddSubmit.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data">
	 <!-- }else{ 
	            <form name="myfrm" action="CircularEdit.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data">
				
      } -->
	  <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	  
	  
	   <div class="row" align="center"> 
	   
	        <div class="col-md-3" style="margin-left: 20px;" >
			   <div class="form-group">
				  <label><b>Circular No</b><span class="mandatory"	style="color: red;">*</span></label>
				  <input type="text" style="width: 70%; " class="form-control input-sm " value=""  name="circularno"  id="circularNo" maxlength="20"  required="required" >
				</div>
			</div>
			
	     <div class="col-md-2" style="margin-left: 200px;" >
			   <div class="form-group">
				  <label><b>Subject</b><span class="mandatory"	style="color: red;">*</span></label>
				   <textarea id="cirsubject" name="cirSubject" rows="2" cols="50" style="border-bottom-color: gray;" required="required" ></textarea>
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
						<!-- if(circularDetails!=null ){ -->
						<%--  <input type="file"   class="form-control input-sm "  value=" <%=circular.getPath()%>"   id="file" name="selectedFile" > --%>
							 
						<input type="file" name="FileAttach"   accept="application/pdf"  class="form-control input-sm "  value="" required="required"  id="file"  >
							
			</div>
		  </div>
			
			  
	 
	 </div> 
	 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	  
	
	 <br>
	      <div class="row" >
		   <div class="col-12" align="center">
		   
		    <%-- if(circularDetails==null ){--%>
		      <button type="submit"   class="btn btn-sm submit-btn"  id="addCircular" onclick="return confirm('Are You Sure To Submit?');"  >SUBMIT</button>
		      
		    			<%-- <input type="hidden" name="circular" value="<%if(circular.getCircularId()!=null){%><%=circular.getCircularId()%><%}%>">
		    				<button type="submit" class="btn btn-sm submit-btn "  onclick="return confirm('Are You Sure To Submit?');" name="action" value="CircularEdit" >UPDATE</button>
		    			}else{  --%>
		   
		     
		    	</div>
		    </div> 
				
			
						
					<!-- if(circularDetails!=null){ 
					</form>
					}else{ -->
	
	               </form>
	  
	 
	  
	  </div>
	
	
	</div>
  </div>
 </div>
		


<script type="text/javascript">

$('#circulardate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,

	<%-- if(circular!=null){%>
	 "startDate" : new Date('<%=circular.getCircularDate()%>'), 
	} --%>
	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>



















</body>
</html>