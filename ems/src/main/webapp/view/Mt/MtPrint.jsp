<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="java.time.LocalDate ,java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Insert title here</title>
</head>
<body>
<% 
String comp=(String)request.getAttribute("comp"); 
SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
List<Object[]> plist=(List<Object[]>)request.getAttribute("printlist");
System.out.println(plist.size());
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>MT Print</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Print</li>
					</ol>
				</div>
		</div>
</div>	
    <%String ses=(String)request.getParameter("result"); 
	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){%>
	<div align="center"> <div class="alert alert-danger" role="alert"><%=ses1 %>  </div> </div>
	<%}if(ses!=null){%>
	<div align="center"> <div class="alert alert-success" role="alert" ><%=ses %> </div> </div>
    <%}%>

<div class="container-fluid">	
<div class="nav navbar auditnavbar" style="background-color: white;margin-top: 18px" align="left">

		<div class="row"></div>
		<form name="myfrm" action="MtPrint.htm" method="GET" >
			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					<label style="margin-right: 10px; margin-top: 5px; font-weight: bold;">Month: <span class="mandatory" style="color: red;">*</span></label>
					<input class="input-lg" id="month" name="month" required="required" style="margin-top: 3px; width: 100px; height: 30px; margin-right:30px;" /> 
					<input type="submit" class="btn btn-sm submit-btn" style="margin-top: -3px; " />	
	    </form> 
</div>
	
	



<div class="card" style="margin-top: 10px;">
				<div class="card-body main-card"  >
						     <div class="card-body">
    <%if(plist!=null&&plist.size()!=0) {%>
    <form  action="MtPrintReport.htm" method="GET" name="frm">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="form-group">
  
  <div class="table-responsive">
		 <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
	  <thead>
			  <tr>
					  <th >Select</th>
					  <th >Trip#</th>
					  <th >Indentor</th>
					  <th >From</th>
					  <th >To</th>
					  <th >Start Date</th>
					  <th >Start Time</th>
					  <th >Driver</th>
					  <th >Vehicle</th>
			  </tr>
	  </thead>
	   <tbody>
			  <%for(Object[] list: plist){%>
			  <tr>
					  <td align="center"><input type="checkbox" name="printdata" value="<%=list[1]%>&<%=list[7]%>" ></td> 
					  <td> <%=list[0]%> </td>
					  <td> <%=list[6]%> </td>
					  <td> <%=list[3]%> </td>
					  <td> <%=list[4]%> </td>
					  <td> <%=sdf.format(list[2])%> </td>
					  <td> <%=format.format(list[5])%> </td>  
					  <td> <%=list[8]%> </td>
					  <td> <%=list[9]%> </td>
			</tr>  
			 <%}%>
	 </tbody>
   </table>
    </div>
    </div>
    <div align="center"><button type="submit" value="print" name="sub" class="btn btn-primary btn-sm" onclick="Edit(myfrm)"  formtarget="_blank">PRINT</button>
    <button type="submit" value="delete" name="sub" class="btn btn-danger btn-sm"  onclick="Delete(frm)" style="margin-left: 10px;" >DELETE</button>
    </div>
    </form>
    <%}else{ %>
   <div align="center"> <span class="badge badge-warning">No Data Found</span></div> 
    <%} %>
</div>
</div>
</div>
	</div>					
<script type="text/javascript">
$('#month').datepicker({
	format: "mm-yyyy",
	    viewMode: "months", 
	    minViewMode: "months",
	    autoclose: true,
	    todayHighlight: true
});
$('#month').datepicker("setDate", new Date());


function Edit(myfrm) {
	var fields = $("input[name='printdata']").serializeArray();
	if (fields.length === 0) {
		alert("Please Select Atleast One ");
		event.preventDefault();
		return false;
	}
	return true;
}

function Delete(myfrm){ 
	var fields = $("input[name='printdata']").serializeArray();
	if (fields.length === 0){
		alert("Please Select Atleast One ");
		event.preventDefault();
		return false;
	}	
	var cnf = confirm("Are You Sure To Delete!");
	if(cnf){		
		document.getElementById("empForm").submit();
		return true;
	}else{
		event.preventDefault();
		return false;
	}
}
</script>
</body>
</html>