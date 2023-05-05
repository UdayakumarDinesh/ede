<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.List"%>
  <%@page import="com.vts.ems.pi.model.PisHometown"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hometown</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
	<%
	List<Object[]> States = (List<Object[]>)request.getAttribute("States");
	PisHometown hometown =(PisHometown)request.getAttribute("Hometown");
	 Object[] empdata = (Object[])request.getAttribute("Empdata");
	%>
	
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
			<%if(hometown!=null){ %>
				<h5>Hometown Edit</h5><%}else{ %>
						<h5>Hometown Add</h5><%}%>
			</div>
			   <div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				        <li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="Hometown.htm?empid=<%=empdata[2] %> ">Hometown List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Hometown</li>
					</ol>
				</div>
		</div>
	</div>

<div class="page card dashboard-card"> 
   <div class="card-body" >
	 <div class="row">
       <div class="col-1"></div>
		<%if(hometown!=null){ %>
		<form action="EditHomeTown.htm" method="POST" autocomplete="off" id="myform" enctype="multipart/form-data">
		<%}else{%>
		<form action="AddHomeTown.htm" method="POST" autocomplete="off" id="myform">
		<%}%>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
		<div class="card" style="width: 140%;" > 
		<div class="card-header">
		<h5>Fill Hometown Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				        
				        <div class="col-md-5">
				        <div class="form-group">
		                     <label>Hometown:<span class="mandatory">*</span></label>
		                     <input type="text" value="<%if(hometown!=null && hometown.getHometown()!=null) {%> <%=hometown.getHometown() %> <%} %> " class="form-control input-sm" maxlength="250" id="hometown" name="hometown" required="required" placeholder="Enter HomeTown" onclick="return trim(this)" onchange="return trim(this)"> 
		                </div>
		                </div>
		                
	             <div class="col-md-4">
                    <div class="form-group">
                            <label>Nearest Railway Station:<span class="mandatory">*</span></label>
                            <input type="text" value="<%if(hometown!=null && hometown.getNearestRailwayStation()!=null) {%> <%=hometown.getNearestRailwayStation() %> <%} %>" id="nearestRailwayStation"  name="nearestRailwayStation" class="form-control input-sm" maxlength="250"  placeholder="Enter Nearest Railway Station"   required="required" onclick="return trim(this)" onchange="return trim(this)">
                    </div>
                    </div> 
		         
		                <div class="col-md-3">
                        <div class="form-group">
                              <label>State:<span class="mandatory">*</span></label>
                              <select id="state" name="state" class="form-control input-sm selectpicker" data-live-search="true" required>
                                      <%if(States!=null){ 
					                        for(Object[] O:States){%>
					                        <%if(hometown!=null){%>
					                        <option value="<%=O[1]%>" <%if(hometown!=null){if( hometown.getState().equalsIgnoreCase(O[1].toString())  ){%> selected   <%}}%> ><%=O[1]%></option>				                        
					                        <%}else{ %>
					                        <option value="<%=O[1]%>" ><%=O[1]%></option>
					                        <%}}}%>
                              </select>
                       </div>
                       </div>	                    
			        </div> 		
	         	  </div>
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							 <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2].toString()%><%}%>"> 
		                      <input type="hidden" name="EmpNo" value="<%if(empdata!=null){%><%=empdata[3].toString()%><%}%>"> 
							<%if(hometown!=null){ %>
								
							<input type="hidden" name="hometownId" value="<%=hometown.getHometownId() %>">
			                  <button type="submit" class="btn btn-sm submit-btn AddItem"	 name="Action" value="EDIT" onclick="return CommentsModelEdit();">SUBMIT</button>
							<%}else{%>
				              <button type="submit" class="btn btn-sm submit-btn"	onclick="return CommentsModelAdd();" name="Action" value="ADD">SUBMIT</button>
							<%}%>						
							 </div>
							</div>
					     </div>	
					 </div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						<%if(hometown!=null){ %>
                <!--------------------------- container ------------------------->
			    <div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">The Reason For Edit</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					        <div class="modal-body">
					             <div class="form-inline">
					        	 <div class="form-group "  >
					               <label>File : &nbsp;&nbsp;&nbsp;</label> 
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile" > 
					      		 </div>
					      		 </div>
					        	
					        	<div class="form-inline">
					        	<div class="form-group w-100">
					               <label>Comments : &nbsp;&nbsp;&nbsp;</label> 
					              <textarea  class=" form-control w-100" maxlength="1000" style="text-transform:capitalize;"  id="comments"  name="comments" required="required" ></textarea> 
					      		</div>
					      		</div>
					      </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="Action" value="EDIT" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
			<!----------------------------- container Close ---------------------------->	
						<%}%>		 											
			<%if(hometown!=null){ %>
			</form>
			<%}else{ %>
			</form>
			<%}%>		
	</div>
  </div>				
</div>
</body>
<script type="text/javascript">
function CommentsModelAdd()
{
	  var NearestRailwayStation =$("#nearestRailwayStation").val();
	  var State =$("#state").val();
	  var Hometown =$("#hometown").val();
	  
	  if(confirm('Are You Sure to Submit?')){
	  
	  if(Hometown==null || Hometown=='' || Hometown=="null" ){
			alert('Enter the Hometown!');
			return false;
		}else if(State==null || State=='' || State=="null" ){
			alert('Please Select the State!');
			return false;
		}else if(NearestRailwayStation==null || NearestRailwayStation=='' || NearestRailwayStation=="null" ){
			alert('Enter the Nearest Railway Station!');
			return false;
		}else{
			$('#myform').submit();
			return true;
		}
	  event.preventDefault;
	  }else{
		  return false;
	  }
}
</script>

<script type="text/javascript">
function CommentsModelEdit()
{
	  var NearestRailwayStation =$("#nearestRailwayStation").val();
	  var State =$("#state").val();
	  var Hometown =$("#hometown").val();
	  
	  if(confirm('Are You Sure to Submit?')){
	  
	  if(Hometown==null || Hometown=='' || Hometown=="null" ){
			alert('Enter the Hometown!');
			return false;
		}else if(State==null || State=='' || State=="null" ){
			alert('Please Select the State!');
			return false;
		}else if(NearestRailwayStation==null || NearestRailwayStation=='' || NearestRailwayStation=="null" ){
			alert('Enter the Nearest Railway Station!');
			return false;
		}else{
			$('#myModal').modal('show');
			return true;
		}
	  event.preventDefault;
	  }else{
		  return false;
	  }
}
</script>

</html>