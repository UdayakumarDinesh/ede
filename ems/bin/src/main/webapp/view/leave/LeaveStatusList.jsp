<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style>
.table thead tr th{vertical-align:middle; text-align: center;  }
.table tbody tr td{vertical-align:middle; text-align: center; }

.label-sanc{
  background-color:#006400;
}
</style>
</head>
<body >
<%SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy"); %>
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>LEAVE LIST</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Leave Status List</li>
					</ol>
				</div>
			</div>
	</div>	

   

                  <form action="leave-appl-status.htm" method="post"  class="navbar-form navbar-right"  style="margin-top:8px;">
                       <div  class="row" style="">
                       <div class="form-group col-sm-3"  ></div>
                         <div class="form-group col-sm-4"  >
                        <select class="form-control input-sm selectpicker" name="employeeId" required="required" data-live-search="true">

                         </select>
                        </div>   
                      
                           <div class="form-group col-sm-1" style="text-align: right;margin-top: 4px;"> 
							 <label>From:</label>
							 </div>
						<div class="form-group col-sm-1" style="padding-right: 2px;padding-left: 2px;">
							<input type="text" class="form-control input-sm currentdate" name="ChooseFromDate"   required="required">
						</div>
					<div class="form-group col-sm-1" style="text-align: right;margin-top: 4px;"> 
							 <label>To:</label>
							 </div>
				         <div class="form-group col-sm-1" style="padding-right: 2px;padding-left: 2px;">
							<input type="text"  name="ChooseToDate" class="form-control input-sm currentdate"     required="required">						
						</div>
						<div class="col-sm-1" style="margin-top: 3px;text-align: left;">
						
					   <button type="submit" class="btn  btn-success btn-sm" name="ChooseDate" value="ChooseDate">submit</button>
                       </div>
                 </div>
                 </form>







  <div class="card">
            
               <div class="card-body">
                 <table class="table table-hover  table-striped table-condensed table-bordered">
				<thead>
					<tr>
					<th class="text-nowrap"> Name  &nbsp; Designation</th>
						<th class="text-nowrap">From &nbsp; To</th>
                        <th>Leave Type</th>
                        <th>Applied On</th>
                        <th>Purpose</th>
                        <th>Status</th>
                  	</tr>
				</thead>
				<tbody>
				        <% 
				        List<Object[]> StatusList=(List<Object[]>)request.getAttribute("StatusList");
				         if(StatusList!=null&&StatusList.size()>0){
                         for(Object[] ls:StatusList ){ 
                        %> 
                        
                      
                           <tr>
                           <td> <%=ls[1].toString()%> <br/> <%=ls[2].toString() %></td>
                                <td><%=sdf.format(ls[4])%> <br/><%=sdf.format(ls[5])%></td>
                                <td>
                                <form action="leaveprint.htm" method="post">
	                           <button class="btn btn-primary btn-sm btn-outline"  name="applId" value="<%=ls[9]%>" formtarget="_blank">
	                           <%=ls[3]%> <%if("F".equalsIgnoreCase(ls[10].toString())){out.println("(FN)");}else if ("A".equalsIgnoreCase(ls[10].toString())){out.println("(AN)");} %>
	                            </button>
	                            <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
                                    </form>
                                <td><%=sdf.format(ls[8])%><br/> for <%=ls[11]%> Day(s)</td>
                                <td><%=ls[7]%></td>
                                <td>
                                 <form action="leavestatus.htm" method="post">
	                           <button class="btn btn-link btn-sm btn-outline"  name="applId" value="<%=ls[9]%>" >
	                             <%=ls[6]%> 
	                            </button>
	                            <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
                                    </form>
                                </td>
                               
                                
                            </tr> 
                            <%}}else{%>
                            <tr><td  colspan="7"><span class="label label-warning">No Record Found</span></td></tr>
                            <%}%>
                        
			    </tbody>
					</table>
                </div>
           
          
          </div>


	


<script>

$('.currentdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" :true,
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>

</body>
</html>