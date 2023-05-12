<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
  <%@page import="com.vts.ems.pis.model.*"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Passport</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
	<%
	List<Object[]> States = (List<Object[]>)request.getAttribute("States");
	Object[] empdata = (Object[])request.getAttribute("Empdata");
	Passport passport =(Passport)request.getAttribute("passport");
	String NOCpassport =(String)request.getAttribute("NOCpassport");
	%>
	
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
			<%if(passport!=null){ %>
				<h5>Passport Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%>
						</b></small></h5><%}else{ %>
				<h5>Passport Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%>
						</b></small></h5><%}%>
			</div>
			   <div class="col-md-6">
					<ol class="breadcrumb ">
					<%if(NOCpassport!=null){ %>
					
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					    <li class="breadcrumb-item "><a href="Passport.htm">Passport List</a></li>
					    <li class="breadcrumb-item active " aria-current="page">Passport Add</li>
					
					<%}
					else{%>
					
					    <li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Passport Address </li>
					
					<%} %>
					
					</ol>
				</div>
		</div>
	</div>

<div class="page card dashboard-card"> 
	

	<div class="card-body">

		
		<div class="row">
		<div class="col-3"></div>
		<%if(passport!=null){ %>
		<form action="EditPassport.htm" method="POST" autocomplete="off" enctype="multipart/form-data" >
		<%}else{%>
		<form action="AddPassport.htm" method="POST" autocomplete="off">
		<%}%>
		<input type="hidden" name="empid" value="<%if(empdata!=null){ %><%=empdata[2]%> <%}%>">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card"  > 
		<div class="card-header">
		<h5>Fill Passport Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">

		                <div class="col-md-6">
                        <div class="form-group">
                              <label>Passport Type:<span class="mandatory">*</span></label>
                              <select  name="PassportType" class="form-control input-sm selectpicker" data-live-search="true">
                              
                                     		<option value="Official" <%if(passport!=null && "Official".equalsIgnoreCase(passport.getPassportType())){%> selected="selected" <%}%>>Official</option>
					                        <option value="Diplomatic" <%if(passport!=null && "Diplomatic".equalsIgnoreCase(passport.getPassportType())){%> selected="selected" <%}%>>Diplomatic</option>
					                        <option value="Ordinary" <%if(passport!=null && "Ordinary".equalsIgnoreCase(passport.getPassportType())){%> selected="selected" <%}%>>Ordinary</option>				                         
                              </select>
                       </div>
                       </div>	     
                       
                       <div class="col-md-6">
                        <div class="form-group">
                              <label>Status:<span class="mandatory">*</span></label>
                              <select  name="Status" class="form-control input-sm selectpicker" data-live-search="true">
                              
                                     		<option value="Valid" <%if(passport!=null && "Valid".equalsIgnoreCase(passport.getStatus())){%> selected="selected" <%}%>>Valid</option>
					                        <option value="Cancelled" <%if(passport!=null && "Cancelled".equalsIgnoreCase(passport.getStatus())){%> selected="selected" <%}%>>Cancelled</option>
					                        <option value="Surrendered" <%if(passport!=null && "Surrendered".equalsIgnoreCase(passport.getStatus())){%> selected="selected" <%}%>>Surrendered</option>
					                        <option value="Lab Custody" <%if(passport!=null && "Lab Custody".equalsIgnoreCase(passport.getStatus())){%> selected="selected" <%}%>>Lab Custody</option>
					                        <option value="HQ Custody" <%if(passport!=null && "HQ Custody".equalsIgnoreCase(passport.getStatus())){%> selected="selected" <%}%>>HQ Custody</option>				                         
                              </select>
                       </div>
                       </div>               
			</div>
            
                
         	<div class="row">
        
         	        <div class="col-md-4">
	                    <div class="form-group">
	                            <label>Passport No:<span class="mandatory">*</span></label>
	                            <input id="passportno" type="text"  class="form-control input-sm " <%if(passport!=null && passport.getPassportNo()!=null){%> value="<%=passport.getPassportNo()%>" <%}%> name="PassportNo"  required="required" maxlength="6"  placeholder="Enter Passport No" onblur="checknegative(this)">
	                    </div>
                    </div>
                
                    <div class="col-md-4">
                       <div class="form-group">
                             <label> Valid From  </label>
	                       	  <input type="text" class="form-control input-sm validfrom" value="" name="ValidFrom" id="validfrom" readonly="readonly" required="required"  /> 
                       </div>
                     </div>    
                     
                      <div class="col-md-4">
	                       <div class="form-group">
	                             <label>Valid To </label>
	                       	     <input type="text" class="form-control input-sm validto" value="" name="ValidTo" id="validto" readonly="readonly" required="required"  />
	                       </div>
                       </div>              

         	</div>
	        <div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<%if(passport!=null){ %>
							<input type="hidden"  name="passportid"<%if(passport!=null && passport.getPassportId()>0){%> value="<%=passport.getPassportId()%>" <%}%>>
				              <button type="submit" class="btn btn-sm submit-btn AddItem"	 name="action" value="submit" onclick="return CommentsModel();" >SUBMIT</button>
								<%}else if(passport==null && NOCpassport==null){%>
			                   <button type="submit" class="btn btn-sm submit-btn"	onclick="return confirm('Are You Sure To Submit?');" name="Action" value="ADD" >SUBMIT</button>
								<%}
								else if(NOCpassport!=null){%>
								 <button type="submit" class="btn btn-sm submit-btn"	onclick="return confirm('Are You Sure To Submit?');" name="Action" value="<%=NOCpassport%>" >SUBMIT</button>
								<%} %>
		                    <%if(empdata!=null && NOCpassport==null ){%><a href="PassportList.htm?empid=<%=empdata[2]%>"   class="btn btn-sm  btn-info">BACK</a><%}%>
							 </div>
							</div>
						   </div> 
						  </div> 
						 </div>		
						<%if(passport!=null){ %>
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
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile"  > 
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="Action" value="EDIT" onclick="return confirm('Are You Sure To Submit!');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<%} %>
					<!----------------------------- container Close ---------------------------->		
			<%if(passport!=null){ %>
			</form>
			<%}else{ %>
			</form>
			<%}%>	
			
		</div>
		</div>				
		</div>
</body>
<script type="text/javascript">
function CommentsModel()
{
	if(confirm('Are you sure to submit')){
		 $('#myModal').modal('show');
	}else{
		return false;
	}
}
</script>

<script type="text/javascript">
$('#validfrom').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	<%if(passport!=null && passport.getValidFrom()!=null){ %>
	"startDate" : new Date("<%=passport.getValidFrom()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#validto').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#validfrom').val(),    
	<%if(passport!=null && passport.getValidTo()!=null){ %>
	"startDate" : new Date("<%=passport.getValidTo()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});




$("#validfrom").change( function(){
	var validdate = $("#validfrom").val();
	
	$('#validto').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : validdate,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	
	
});						

</script>
<script type="text/javascript">
setPatternFilter($("#passportno"), /^-?\d*$/);
function setPatternFilter(obj, pattern) {
	  setInputFilter(obj, function(value) { return pattern.test(value); });
	}

function setInputFilter(obj, inputFilter) {
	  obj.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
	    if (inputFilter(this.value)) {
	      this.oldValue = this.value;
	      this.oldSelectionStart = this.selectionStart;
	      this.oldSelectionEnd = this.selectionEnd;
	    } else if (this.hasOwnProperty("oldValue")) {
	      this.value = this.oldValue;
	      this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	    }
	  });
	}


function checknegative(str) {
    if (parseFloat(document.getElementById(str.id).value) < 0) {
        document.getElementById(str.id).value = "";
        document.getElementById(str.id).focus();
        alert('Negative Values Not allowed');
        return false;
    }
}
</script>
</html>