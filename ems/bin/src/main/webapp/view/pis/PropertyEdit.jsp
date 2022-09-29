<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.time.LocalDate"%>
        <%@page import="java.util.List" %>
         <%@page import="com.vts.ems.pis.model.*" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Property Edit</title>
</head>
<body>
<%
Object[] empdata      = (Object[])request.getAttribute("Empdata");
Property pro = (Property)request.getAttribute("PropertyDetails");

%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Property Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PropertyList.htm?empid=<%=empdata[2]%>">Property List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Property Edit</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="EditProperty.htm" method="POST" autocomplete="off" enctype="multipart/form-data" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" > 
		<div class="card-header">
		<h5>Fill Property Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				      <div class="col-md-3">
		                <div class="form-group">
		                    <label>Movable Name<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Movable" required  data-live-search="true">
		                        <option value="Y" <%if(pro!=null && pro.getMovable()!=null && "Y".equalsIgnoreCase(pro.getMovable())){%> selected="selected"<%}%>> Movable   </option>
		                        <option value="N" <%if(pro!=null && pro.getMovable()!=null && "N".equalsIgnoreCase(pro.getMovable())){%> selected="selected"<%}%>> UnMovable </option>
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-3">
		                 <div class="form-group">
                            <label>Acquired Type<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Acquired" required  data-live-search="true">
		                          <option value="PURCHASE"    <%if(pro!=null && pro.getAcquired_type()!=null && "PURCHASE".equalsIgnoreCase(pro.getAcquired_type())){%> selected="selected"<%}%>>PURCHASE</option>
		                          <option value="GIFT"        <%if(pro!=null && pro.getAcquired_type()!=null && "GIFT".equalsIgnoreCase(pro.getAcquired_type())){%> selected="selected"<%}%>>GIFT</option>       
		                          <option value="INHERITANCE" <%if(pro!=null && pro.getAcquired_type()!=null && "INHERITANCE".equalsIgnoreCase(pro.getAcquired_type())){%> selected="selected"<%}%>>INHERITANCE</option>               
		                    </select>
                        </div>
		                
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                    <label>Details<span class="mandatory">*</span></label>
		                      <input  id="details" maxlength="100" type="text" name="Details" <%if(pro!=null && pro.getDetails()!=null){%> value="<%=pro.getDetails()%>" <%}%> class="form-control input-sm" required="required"  placeholder="Enter Deatils" > 
		                </div>
		               </div> 
		               
		                 <div class="col-md-3">
		                 <div class="form-group">
                            <label>Remark/Permission<span class="mandatory">*</span></label>
		                      <input  id="remark" maxlength="100" type="text" name="Remark" <%if(pro!=null && pro.getRemarks()!=null){%> value="<%=pro.getRemarks()%>" <%}%> class="form-control input-sm" required="required"  placeholder="Enter Remark" > 
		                </div>
                        </div>
		              </div>

                <div class="row">
                     <div class="col-md-3">
		                <div class="form-group">
		                	<label>DOP<span class="mandatory">*</span></label>
		                    <input type="text" class="form-control input-sm mydate" readonly="readonly"   id="dop" name="DOP"  required="required">                   
		                </div>
		              </div>
		              
		                <div class="col-md-3">
		                <div class="form-group">
		                	<label>Noting On<span class="mandatory">*</span></label>
		                    <input type="text" class="form-control input-sm mydate" readonly="readonly"   id="notingon" name="NotingOn"  required="required">                   
		                </div>
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                    <label>Value<span class="mandatory">*</span></label>
		                      <input  id="value" maxlength="10" type="text" name="Value" <%if(pro!=null && pro.getValue()!=null){%> value="<%=pro.getValue()%>" <%}%> class="form-control input-sm" required="required"  placeholder="Enter Value" > 
		                </div>
		               </div>
                  
                </div>

						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<input type="hidden" name="empid" <%if(empdata!=null){%> value="<%=empdata[2]%>" <%}%>> 
							<input type="hidden" name="PropertyId" <%if(pro!=null && pro.getProperty_id()!=0){%> value="<%=pro.getProperty_id()%>" <%}%>> 
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return CommentsModel();"
									name="action" value="submit">SUBMIT</button>
									</div>
							</div>
						</div>
						</div></div>
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick="return confirm('Are You Sure To Submit!');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
						
						
			</form>
			</div>
		</div>
		</div>
		</div>
<script type="text/javascript">

$('#notingon').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	 <%if(pro!=null && pro.getNoting_on()!=null){%>
	"startDate" : new Date("<%=pro.getNoting_on()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
$('#dop').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	 <%if(pro!=null && pro.getDop()!=null){%>
	"startDate" : new Date("<%=pro.getDop()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>
<script type="text/javascript">
		function CommentsModel()
		{
			var details = $("#details").val();
			var remark = $("#remark").val();
			var value = $("#value").val();
			
			
			if(details==null || details=='' || details=="null" ){
				alert('Enter the Details!');
				return false;
			}else if(remark==null || remark=='' || remark=="null" ){
				alert('Enter the Remark!');
				return false;
			}else if(value==null || value=='' || value=="null" ){
				alert('Enter the value!');
				return false;
			}else {
				$('#myModal').modal('show');
			}
				 
		}
</script>
</body>
</html>