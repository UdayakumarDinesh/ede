<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.time.LocalDate"%>
    <%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Property Add</title>
</head>
<body>
<%
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> pisawardslist = (List<Object[]>)request.getAttribute("PisAwardsList");


%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Property Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PropertyList.htm?empid=<%=empdata[2]%>">Property List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Property Add</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="AddProperty.htm" method="POST" autocomplete="off" >
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
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                        <option value="Y">Movable</option>
		                        <option value="N">UnMovable</option>
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-3">
		                 <div class="form-group">
                            <label>Acquired Type<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Acquired" required  data-live-search="true">
		                        <option disabled="disabled" value="" selected="selected"> Select</option>
		                          <option value="PURCHASE">PURCHASE</option>
		                          <option value="GIFT">GIFT</option>       
		                          <option value="INHERITANCE">INHERITANCE</option>               
		                    </select>
                        </div>
		                
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                    <label>Details<span class="mandatory">*</span></label>
		                      <input  id="details" maxlength="100"  type="text" name="Details" class="form-control input-sm" required="required"  placeholder="Enter Deatils" > 
		                </div>
		               </div> 
		               
		                 <div class="col-md-3">
		                 <div class="form-group">
                            <label>Remark/Permission<span class="mandatory">*</span></label>
		                      <input  id="remark" maxlength="100" type="text" name="Remark" class="form-control input-sm" required="required"  placeholder="Enter Remark" > 
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
		                      <input  id="value" maxlength="10" type="text" name="Value" class="form-control input-sm" required="required"  placeholder="Enter Value" > 
		                </div>
		               </div>
                  
                </div>

						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<input type="hidden" name="empid" <%if(empdata!=null){%> value="<%=empdata[2]%>" <%}%>> 
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
									name="action" value="submit">SUBMIT</button>
									</div>
							</div>
						</div>
						</div></div>
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
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>

</body>
</html>