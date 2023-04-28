<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.itinventory.model.ITInventoryConfigured"%> 
<%-- <%@page import="com.vts.ems.itinventory.model.ITInventory"%>  --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 

</head>
<body>

  <%
  
  %>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-4">
			
			
				<h5>Passport Add</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="Passport.htm">Passport List</a></li>
					<li class="breadcrumb-item active " aria-current="page">Passport Add</li>
					
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
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
		<%} %>
	
	
	<div class=" page card dashboard-card">
   <!--  <div class="card-body" > -->
		<div class="card" >
			<div class="card-body" >
			
				<form action="" method="post" autocomplete="off" >
				
				
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			         
			
			
			            <div class="col-md-3">
			                <label>Name</label> 
			            	<input type="text" name="Name"  maxlength="100" value="" class="form-control input-sm"  >
			            </div>			
			            	
			            <div class="col-md-2">
			                <label>Designation</label>
			                <input type="text" id="" name="Designation"   maxlength="100" value=""
			                    class=" form-control input-sm " 
			                    >
			            </div>
			            <div class="col-md-2">
			                <label> Department</label>
			                <input type="text" id="" name="Department"   maxlength="100" value=""
			                    class=" form-control input-sm " 
			                     >
			            </div>
			             <div class="col-md-3">
			                <label>Present  Address </label>
			                <input type="text" id="" name="Present"    maxlength="100" value=""
			                    class=" form-control input-sm " 
			                    >
			            </div>
				  </div>
			    </div>
			
			    <div class="form-group">
			        <div class="row">
			          <div class="col-md-3">
			                <label> Permanent Address </label>
			                <input type="text" id="" name="Permanent"   maxlength="100" value=""
			                    class=" form-control input-sm "
			                    >
			            </div>
			            
			             <div class="col-md-2">
			                <label>Details Of<span class="mandatory">*</span></label>
			                <select name="Details Of" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="F">Father</option>
			                    <option value="H" >Husband</option>
			                    <option value="G" >Guardian</option>
			                </select>
			            </div> 
			            
			              <div class="col-md-2">
			                <label>Relation Name <span class=" mandatory ">*</span></label>
			                <input type="text" id="" name="RelationName"    maxlength="100" value=""
			                   class=" form-control input-sm "  required="required" >
			                    
			            </div>
			            <div class="col-md-2">
			                <label>Relation Occupation <span class=" mandatory ">*</span></label>
			                <input type="text" id="" name="RelationOccupation"   maxlength="100" value=""
			                    class=" form-control input-sm "  required="required">
			                   
			            </div>
			            
			             <div class="col-md-3">
			                <label>Relation Address <span class=" mandatory ">*</span></label>
			                <input type="text" id="" name="RelationAddress"   maxlength="100"  value=""  required="required"
			                    class=" form-control input-sm " >
			             
			            </div>
			            
			            <!-- <div class="col-md-2">
			                <label>Office <span class=" mandatory ">*</span></label>
			                <input type="text" id="" name="Office"    maxlength="100" value=""
			                   class=" form-control input-sm " placeholder="Office "  required="required"
			                   >
			            </div>
			           <div class="col-md-2">
			                <label>OS<span class="mandatory">*</span></label>
			                <select name="OS" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="W">Windows</option>
			                    <option value="L" >Linux</option>
			                </select>
			            </div>  -->
			       </div>
			     </div>
			   
			    <div class="form-group">
			        <div class="row">
			            
			
			             <div class="col-md-6">
			                <label> Details of blood /close relations working in foreign embassy / firms in India / Abroad 
                              <span class=" mandatory ">*</span></label>
                             <div class="col-md-11" style="margin-left:-16px;">  
			                <input type="text" name=""    maxlength="" value=" "
			                    class=" form-control input-sm "  required="required"  >
			                   
			                    </div>
			              </div>
			              
			              
			             <div class="col-md-6">
			                <label> Details of employment during last ten years 
                              <span class=" mandatory ">*</span></label>
                             <div class="col-md-11" style="margin-left:-16px;">  
			                <input type="text" name=""    maxlength="" value=" "
			                    class=" form-control input-sm "  required="required"  >
			                   
			                    </div>
			              </div>
			              
			          </div>
			    </div> 
			    <div class="form-group">
			        <div class="row">
			          <div class="col-md-2">
			                <label> Passport Type </label>
			                <input type="text" id="" name="Permanent"   maxlength="100" value=""
			                    class=" form-control input-sm "
			                    >
			            </div>
			            
			             <div class="col-md-2">
			                <label> Passport No. <span class="mandatory">*</span></label>
			                <input type="text" id="" name="Permanent"   maxlength="100" value=""
			                    class=" form-control input-sm "
			                    >
			            </div> 
			            
			              <div class="col-md-2">
			                <label>Date of Issue  <span class=" mandatory ">*</span></label>
			                <input type="text" id="" name="RelationName"    maxlength="100" value=""
			                   class=" form-control input-sm "  required="required" >
			                    
			            </div>
			            <div class="col-md-2">
			                <label>Validity<span class=" mandatory ">*</span></label>
			                <input type="text" id="" name="RelationOccupation"   maxlength="100" value=""
			                    class=" form-control input-sm "  required="required">
			                   
			            </div>
			            
			             <div class="col-md-3">
			                <label> Details of passport lost, if any<span class=" mandatory ">*</span></label>
			                <input type="text" id="" name="RelationAddress"   maxlength="100"  value=""  required="required"
			                    class=" form-control input-sm " >
			             
			            </div>
			          
			           
			            
			       </div>
			     </div>
			     
			     <div class="form-group">
			        <div class="row">
			        
			        <div class="col-md-2">
			                <label> Passport Type Required<span class=" mandatory ">*</span></label>
			                <select name="Details Of" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="Ordinary">Ordinary</option>
			                    <option value="Official" >Official</option>
			                    <option value="Diplomatic" >Diplomatic</option>
			                </select>
			             
			            </div>
			        
			        </div>
			         </div>
			    
			    
			<div class="col-12" align="center">
			       
			    	<button type="submit" class="btn btn-sm submit-btn"  name="action" value="submit" onclick="return confirm('Are You Sure To Submit')">Submit</button>
			    	
			    	
			    	
			   </div>
			    
			   
			    
			 </form>
			</div>
		</div>
	<!-- </div> -->
	
	
      </div>



	


</body>
</html>