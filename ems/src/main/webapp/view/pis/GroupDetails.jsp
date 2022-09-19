<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Group Details</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
.custom-navbar{
	border-top-left-radius: 7px;
	border-top-right-radius: 7px;
	background-color: white !important;
}
</style>
</head>
<body>

<% 
List<Object[]> stusOrDesigOrGrpOrGenList=(List<Object[]>)request.getAttribute("stusOrDesigOrGrpOrGenList");
String GenderChoice=(String)request.getAttribute("GenderChoice");
String choice=(String)request.getAttribute("choice");
List<Object[]> ResultedList=(List<Object[]>)request.getAttribute("ResultedList");
String DropDownOption1 =(String)request.getAttribute("DropDownOption1");
String empname = (String)request.getAttribute("empname");
System.out.println("jsp"+choice);
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Group Details <small> <%if(empname!=null){%> <%=empname%> <%}%> </small> </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item"><a	href="PIS.htm"> PIS</a></li>
						<li class="breadcrumb-item active " aria-current="page">Group Details</li>	
					</ol>
				</div>
			</div>
</div>

<div class="container-fluid">	
<form   action="GroupDetails.htm" method="post" id="form1">

    <div class="row" style="margin-top: 20px;">
					 
					 <div class="col-md-2"></div>	
						
						<label style="margin-top: 10px;"><b>Select Menu Type :</b> &nbsp;</label>
						<div class="form-group" id="firstList"  style="width:200px;  margin-top: 10px;">			
			 				<select class="form-control input-sm select2" name="Mainlist"   id="mainlist1"  onchange="this.form.submit();">
			 					 <option value="" disabled="disabled" selected="selected">Select</option>
			 				     <option value="Employee Status Wise" <%if(choice!=null && "Employee Status Wise".equalsIgnoreCase(choice)){%> selected="selected" <%}%>>Employee Status Wise</option>
					             <option value="Group/Division Wise" <%if(choice!=null && "Group/Division Wise".equalsIgnoreCase(choice)){%> selected="selected" <%}%>>Group/Division Wise</option>
             					 <option value="Gender Wise" <%if(choice!=null && "Gender Wise".equalsIgnoreCase(choice)){%> selected="selected" <%}%>>Gender Wise</option>
             					 <option value="Designation Wise" <%if(choice!=null && "Designation Wise".equalsIgnoreCase(choice)){%> selected="selected" <%}%>>Designation Wise</option>
             				</select>
						</div>
		  		        <div class="col-md-1"></div>
		  		       
		  		         <%if(stusOrDesigOrGrpOrGenList!=null||GenderChoice!=null){%>
		  		         <div class="form-group"  style="width:200px; margin-top: 10px;">
		  		        	<select class="form-control input-sm select2" name="SubList"  data-live-search="true" onchange="disableOption();">
			 					 
			 					 <option value="All_All">All</option>
			 					 
			 					 <%if(stusOrDesigOrGrpOrGenList!=null){
			 					 for(Object[] ls:stusOrDesigOrGrpOrGenList){%>
			 					 <option value="<%=ls[0]%>_<%=ls[1]%>" ><%=ls[1]%></option>
			 					 <%}}%>
             				
             				     <%if(GenderChoice!=null){%>
             				      <option value="M_Male"   >Male</option>
             				      <option value="F_Female" >Female</option>
             				     <%}%>
             				</select>
						  </div>
      				   
      				   <div class="form-group"  style="width:200px; margin-top: 10px;">
      				   <button  type="submit" class="btn btn-sm submit-btn" style="margin-left: 40px;margin-top: 2px; padding-top: 4px;" >Submit</button>
      				  </div>
      				  <%}%>
      				   </div>
      				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      				</form>
</div>

                                  <!--   Display name of user and selected dates -->
	                                <%if(choice!=null){%><div class="well well-sm text-center" ><span class="h5"><b><%=choice%> <span> ::</span> </b></span>
	                                    
	                                     <span class="h5"><b><%if(DropDownOption1!=null){%><%=DropDownOption1%><%}%></b></span></div><%}%>
                                   <!--  /  Display name of user and selected dates -->
               
<div class="card" style="margin-top: 10px;">
				<div class="card-body main-card"  >
						     <div class="card-body">

 
  <div class="form-group"> 
  <div class="table-responsive">
		 <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
		  	           <thead> 
                             <tr>     
	                             <th>SRNO</th>
	                             <th>Name</th>
	                             <th>Designation</th>
                            </tr>
                       </thead>
                       <tbody>
                        <%if(ResultedList!=null && ResultedList.size()>0){ for(Object ls[]: ResultedList){%>
		                      <tr> 
				                  <td align="center"><%=ls[0]%></td>
			                      <td style='text-align:left;'><%=ls[1]%></td>
			                      <td><%=ls[2]%></td>
	                          </tr>
                       <%}}%>
	                   </tbody>
    			</table>
    </div>
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">

 function submitForm()
 {
	 event.preventDefault()
	 $("#form1").submit(); 
 }

 function disableOption()
{
	 //disable First List Which is main List
		$("#firstList").hide(); 
}

</script>
</html>