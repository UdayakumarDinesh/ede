<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="java.util.List"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Residential Address</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%
List<Object[]> States = (List<Object[]>)request.getAttribute("States");
Object[] empdata = (Object[])request.getAttribute("Empdata");
%>
<div class="col page card"> 
				<div class="card-header page-top">
					<div class="row">
						<div class="col-md-5">
						<%if(null!=null){ %>
							<h5> Residential Address Edit<small><b>&nbsp;&nbsp; <%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%> 
									</b></small></h5><%}else{ %>
									<h5>Residential Address Add<small><b>&nbsp;&nbsp; <%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%> 
									</b></small></h5><%}%>
						</div>
						   <div class="col-md-7">
								<ol class="breadcrumb ">
									<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
									<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
									<li class="breadcrumb-item active " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
									<li class="breadcrumb-item active " aria-current="page">Residential Address </li>
								</ol>
							</div>
					</div>
				</div>
	
				
				<div class="card-body">
		
		<div class="row">
		<div class="col-3"></div>
		<form action="AddAddressDetails.htm" method="POST">
		 <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%>  <%}%>"> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card"  > 
		<div class="card-header">
		<h5>Fill Address Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				        
				        <div class="col-md-6">
				        <div class="form-group">
		                     <label>Permanent Address:<span class="mandatory">*</span></label>
		                     <input type="text" value="" class="form-control input-sm" maxlength="4000" name="perAdd" required="required" placeholder="Enter Permanent Address" onclick="return trim(this)" onchange="return trim(this)"> 
		                </div>
		                </div>
		                
	         
		         
		                <div class="col-md-3">
                        <div class="form-group">
                              <label>State:<span class="mandatory">*</span></label>
                              <select  name="state" class="form-control input-sm selectpicker" data-live-search="true">
                                      <%if(States!=null){ 
					                        for(Object[] O:States){%>
					                        
					                        <option value="<%=O[1]%>" ><%=O[1]%></option>
					                        <%}}%>
                              </select>
                       </div>
                       </div>	
                                           
			</div>
            
                
         	<div class="row">
         	
         	        <div class="col-md-4">
                    <div class="form-group">
                            <label>City:<span class="mandatory">*</span></label>
                            <input type="text"  name="city" class="form-control input-sm" maxlength="49"  value="" placeholder="Enter City."   required="required" onclick="return trim(this)" onchange="return trim(this)">
                    </div>
                    </div> 
                         
                             
         	        <div class="col-md-4">
                    <div class="form-group">
                            <label>City PIN:<span class="mandatory">*</span></label>
                            <input id="CityPinTextBox" type="text" class="form-control input-sm "  value="" name="cityPin"  required="required" maxlength="6"  placeholder="Enter PIN" onblur="checknegative(this)">
                    </div>
                    </div>
                
                     
                     <div class="col-md-4">
                     <div class="form-group">
                            <label>Mobile No.<span class="mandatory">*</span></label></label>
                            <input id="MobileTextBox" type="text" value="" class="form-control input-sm " name="mobile" required="required" maxlength="10"  placeholder="Enter MobileNo." onblur="checknegative(this)">  
                     </div>
                     </div>                   

         	</div>
				
				
				<div class="row">
				      <div class="col-md-4">
                      <div class="form-group">
                            <label> Alt Mobile No.</label>
                            <input  id="AltMobileTextBox"  type="text" value="" class="form-control input-sm " name="altMobile"  maxlength="10"    placeholder="Enter AltMobileNo."  onblur="checknegative(this)"/>
                       </div>
                       </div>
                       
                       
				       <div class="col-md-4">
                       <div class="form-group">
                              <label>Landline No.:</label>
                              <input  id="LandLineTextBox" type="text" value="" class="form-control input-sm " name="landineNo"  maxlength="10"  placeholder="Enter LandlineNo"  onblur="checknegative(this)">  
                       </div>
                       </div> 
                         
                       <div class="col-md-3">
                       <div class="form-group">
                             <label>From Date: </label>
                    
                       	<input type="text" class="form-control input-sm mydate" value="" name="fromPer" readonly="readonly" required="required" placeholder="Enter Date" />
                       
                       </div>
                       </div>
                         
				</div>
		</div>
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							
				<button type="submit" class="btn btn-sm submit-btn"	onclick="return confirm('Are You Sure To Submit?');" name="Action" value="ADD">SUBMIT</button>
								
							 </div>
							</div>
						 </div>
						 
								
			</form>
		</div>
		</div>
	
	
	
</div>
</body>
</html>