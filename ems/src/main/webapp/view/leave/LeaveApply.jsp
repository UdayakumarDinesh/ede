<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<style type="text/css">
 #dis{
 text-align: center;
 }
 
.btn-group1 button {
  background-color: #344b8a; /* Green background */
  color: white; /* White text */
  padding: 10px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  float: left; /* Float the buttons side by side */
}

/* Clear floats (clearfix hack) */
.btn-group1:after {
  content: "";
  clear: both;
  display: table;
}

.btn-group1 button:not(:last-child) {
  border-right:thin;
}

/* Add a background color on hover */
.btn-group1 button:hover {
  background-color: #3e8e41;
}
.card-body{
padding: 0.25rem 0.25rem 0.25rem 0.25rem !important;
}

.navbar{
padding: 0.05rem 0rem 0.05rem 0rem !important;
}

</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>LEAVE APPLY</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Leave Apply</li>
					</ol>
				</div>
			</div>
	</div>	
<%String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%><center>
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></center>
	<%}if(ses!=null){ %>
	<center>
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div></center>
                    <%} %>
 <%
 
    List<Object[]> officerdetails=(List<Object[]>)request.getAttribute("officerdetails");
    List<Employee> emplist=(List<Employee>)request.getAttribute("EmpList");
    String empNo=(String)request.getAttribute("empNo");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>
<div class="page card dashboard-card" style="margin-top: 1px;">

			 
   <div class="card-body" align="center" >
     <div class="row">
			<div class="col-md-3">
			 <div  class="card">
	            <div class="card-header">
	                <span class="h5">Current Leave Balance</span>
	            </div>
	            <div class="card-body">
	            <div class="table-responsive">
	                <table class="table table-bordered table-hover table-striped table-condensed">
	                    <thead>
	                        <tr>
	                            <th>CL</th>
	                            <th>EL</th>
	                            <th>HPL/CML</th>
	                            <th>RH</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                           
	                         <tr>
	                            <td><%=0 %></td>
	                            <td><%=0 %></td>
	                            <td><%=0 %></td>
	                            <td><%=0 %></td>    
	                        </tr>
	                        
	                        
	                        
	                      
	                    </tbody>
	                </table>
	                </div>
	            </div>
	        </div><!-- / current-leave-balance-->
	        
	         <!--Holidays-->
	        <div  class="card" style="margin-top:7px;">
	            <div class="card-header">
	                <span class="h5">Important Dates</span>
	            </div>
	           
	                    <ul class="nav nav-tabs">
	                        <li class="nav-item"><a class="nav-link  active" id="upcoming" href="#" onclick="getHoliday('U','upcoming')">Upcoming</a></li>
	                        <li class="nav-item"><a class="nav-link" id="gen" href="#" onclick="getHoliday('G','gen')">Gen</a></li>
	                        <li class="nav-item"><a class="nav-link" id="rh" href="#" onclick="getHoliday('R','rh')">RH</a></li>
	                        <li class="nav-item"><a class="nav-link" id="wor" href="#" onclick="getHoliday('W','wor')">Wor</a></li>
	                        <li class="nav-item"><a class="nav-link" id="hol" href="#" onclick="getHoliday('H','hol')">Hol</a></li>
	                    </ul> 
	                    
	                    
	                    
	            <div class="card-body">
	                 
	                   <div class="table-responsive">
	                        <table class="table table-bordered table-hover table-striped table-condensed">
	                            <thead>
	                            <tr>
	                                  <th>Date</th>
	                                  <th style=" text-align: left;">Event</th> 
	                                    
	                            </tr>
	                            </thead>
	                            <tbody id="other-list-table">
	                       
	                          </tbody>
	                        </table>
	              
	                    
	                    
	                    
	                 </div>   
	                
	            </div>
	        </div>
	        <!--Holidays-->
			</div>
			<!--Leave Apply  -->
			
			<%
			List<Object[]> leaveTypeDropdown=(List<Object[]>)request.getAttribute("leaveTypeDropdown");
			%>
			<div class="col-md-6">
			      <div  class="card">
	                    <div class="card-header">
	                     <form action="LeaveApply.htm" method="post">
	                    <div class="input-group  custom-search-form">
                              
                               <select class="form-control selectpicker" name="EmpNo" data-live-search="true">
                                 
                                 <%if(emplist!=null&&emplist.size()>0){
                                  for(Employee emp:emplist){ %> 
                                   <option value="<%=emp.getEmpNo()%>" <%if(1==emp.getEmpId()){ %> selected="selected" <%} %>><%=emp.getEmpName()%></option> 
                                 <%}} %> 
                                </select>
                                
                                  <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
                                  <button class="btn btn-success" type="submit">
                                  <i class="fa fa-paper-plane" aria-hidden="true"></i>
                                  </button>

                                
                              </div>
                                </form>
                                
                                  <!-- Recc -->    <%if(officerdetails!=null){ 
                       for(Object[] obj:officerdetails){ 
                       %>
	                       <div style="margin-top:10px; text-align: right; color: green;"><span class="h4">Leave Application For</span> <b class="h5"><%=obj[1] %></b>, <small><%=obj[2] %> </small> </div>             
                               <hr style="margin: 0rem 0rem 0.45rem 0rem  !important">
                                <div class="row" style="margin-top:0px;" >
	                            <div class="col-sm-2" style="text-align: right;" align="right">
	                                <b  style="font-size:small;color:#4E4C4C ; ">Recc Officer:</b>
	                            </div>
	                            <div class="col-sm-4" style="text-align: left;" align="left">
	                               <b style="font-size:small ; color:#575656  ;"><%=obj[3] %></b>
	                            </div>
	                            <div class="col-sm-2"  	style="text-align: right;">
	                                <b style="font-size:small ;color:#4E4C4C ;">Sanc Officer :</b>
	                            </div>
	                            <div class="col-sm-4" style="text-align: left;">
	                               <b style="font-size:small;  color:#575656  ;"><%=obj[4] %></b>
	                               </div>
	                       
	                           </div>
	                  <%}} %>
	                    <!-- / Recc -->
	                  <!-- // reset apply and check button -->
	                    </div>
	                  <div class="card-body">
                           <form action="apply-leave-add.htm" method="post">
	                    
	                    
	                    <!-- Leave Type-->
	                    <div class="form-group">
	                        <div class="row" style="margin-top:5px; ">
	                            <div class="col-sm-3"><label for="leaveType">Leave Type:</label></div>
	                            
	                              <div class="col-sm-4">
	                                <select id="leaveType"  name="leavetypecode" class="form-control selectpicker" onChange="leavecheck(); cmlMessage();" required="required" title="Leave Type">
	                                  <% if(leaveTypeDropdown!=null&&leaveTypeDropdown.size()>0){
	                                       for(Object[] l:leaveTypeDropdown){%>
	                                       <option value="<%=l[0]%>"><%=l[1]%> </option>
	                                       <%}}%>
	                                </select>
	                              </div>
	                              <div id="fullhalfdiv"  class="col-sm-3">
	                                <select  id="halforfull" name="fullhalf" class="form-control selectpicker" onChange="halffull()" required="required">
	                                    <option value="X" >Full</option>
	                                     <option value="H" >Half</option>
	                                     <option value="T" >Hours</option>
	                                </select>
	                            </div>
	                            <div id="hours"  class="col-sm-2">
	                                <select  id="hours" name="hours" class="form-control selectpicker" required="required" title="Hours">
	                                    <option value="1" >1</option>
	                                     <option value="2" >2</option>
	                                     <option value="3" >3</option>
	                                     <option value="4" >4</option>
	                                     <option value="5" >5</option>
	                                     <option value="6" >6</option>
	                                </select>
	                            </div>
	                              
	                              </div>
	                              </div>
	                              </form>
	                               
                         
                        <br>
                        </div>
                        </div>
                        
                       
			</div>
			<!--Leave Apply End  -->
			<!--Applied   -->
			<div class="col-md-3">
			<div class="card">
	            <div class="card-header">
	                <span class="h5">Recent Applied</span>
	            </div>
	            <div class="card-body">
	                <ul class="list-group">
	                <li class="list-group-item list-group-item-warning"><b><i>Recent Applied  Leave Is Not Present For Edit &amp; Delete </i></b></li>
	                </ul>

	            </div>
	            </div>
	            <br>
	            <div class="card">
	            <div class="card-header">
	                <span class="h5">Recent Leaves</span>
	            </div>
	            <div class="card-body">
	                <ul class="list-group">
	               
	                <li class="list-group-item list-group-item-warning"><b><i>Recent Sanctioned Leave Is Not Present For Modify &amp; Cancel</i></b></li>
	                
	                
	                </ul>
	            </div>
	            </div>
	            
			</div>
			
			
	</div>		





	   </div>
	   </div>






<script type="text/javascript">
  $( document ).ready(function() {
	  getHoliday('U','upcoming');
    });

	function getHoliday(type,id){
		$(".nav-link").removeClass("active");
		$('#'+id).addClass('active');
		
		$.ajax({
			
			type : "GET",
			url : "GetHolidays.htm",
			data : {
				type:type
				
			},
			datatype : 'json',
			success : function(result) {
			var result = JSON.parse(result);
			var consultVals= Object.keys(result).map(function(e){
			return result[e]
			})
			

			
			var otherHTMLStr = '';
			for(var c=0;c<consultVals.length;c++)
			{
				var other = consultVals[c];
			
				otherHTMLStr +=	'<tr> ';
				otherHTMLStr +=	'	<td  style="text-align: center;" >'+ other[0] +'</td> ';
				otherHTMLStr +=	'	<td  style="text-align: left;" >'+ other[1] +' </td> ';
				otherHTMLStr +=	'</tr> ';

				
				
			}
			
			if(consultVals.length>0){
				$('.downloadtable').css('display','block');
			}
			
			$('#other-list-table').html(otherHTMLStr);

				
			}
		});
		
		
		
	}


function Edit(myfrm){
	
	 var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	}
	 
	var cnf=confirm("Are U Sure To Edit!");
	  if(cnf){
	
		 
	
		  return true;
	  }
	  
	  
	  else{
		  event.preventDefault();
			return false;
			}
			
	}
function Delete(myfrm){
	

	var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	
	}
	  var cnf=confirm("Are U Sure To Cancel!");
	  if(cnf){
	
	return true;
	
	}
	  else{
		  event.preventDefault();
			return false;
			}
	
	}


</script>
</body>
</html>