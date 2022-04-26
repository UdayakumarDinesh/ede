<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
  <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Handing Over</title>

<jsp:include page="../static/header.jsp"></jsp:include>
<style type="text/css">
#card
{
	box-shadow: 0 4px 6px -2px gray;
}
.text-nowrap{
	font-weight: 600;
}
</style>
</head>
<body>
<%List<Object[]> emplist = (List<Object[]>)request.getAttribute("emplist");%>


<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>ADD Handing Over </h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item " ><a href="HandingOver.htm">  Handing Over List </a></li>
						<li class="breadcrumb-item active " aria-current="page">ADD Handing  Over </li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 
		 
		<div class="card-body" align="center">
			 <div align="center">
					<%String ses=(String)request.getAttribute("result"); 
					String ses1=(String)request.getAttribute("resultfail");
					if(ses1!=null){ %>
						<div class="alert alert-danger" role="alert">
							<%=ses1 %>
						</div>
			
		              <%}if(ses!=null){ %>
			
						<div class="alert alert-success" role="alert">
							<%=ses %>
						</div>
		             <%} %>
        	</div>
		
			<div class="card" style="width: 60%;" id="card" >
				<div class="card-header"  >
				<h4 align="left">Fill Details </h4>
				</div>
				<div class="card-body">
				<form action="HandingOver.htm" method="GET">
						<div class="form-group">
						<div class="row">
						
						<div class="col-4">
			                <label class="text-nowrap  ">Handing Over Officer:<span class="mandatory">*</span></label>
			                <select name="fromemp" id="fromemp" class="form-control select2" sty>
								<%for( Object[] obj: emplist){ %>
									<option value="<%=obj[0]%>"><%=obj[1]%></option>
								<%} %>
			                </select>
			            </div>
			            
			            <div class="col-4">
			                <label class="text-nowrap  ">Handing Over To:<span class="mandatory">*</span></label>
			                <select name="toemp" id="toemp" class="form-control select2">
								<%for( Object[] obj: emplist){ %>
									<option value="<%=obj[0]%>"><%=obj[1]%></option>
								<%} %>
			                </select>
			            </div>
			            
			            <div class="col-2">
			             <label class="text-nowrap  ">From Date:<span class="mandatory">*</span></label>
			             <input type="text"  style="width: 115%;" class="form-control input-sm mydate" readonly="readonly" value="" placeholder=""  id="fromdate" name="fromdate"  required="required"  > 
						 <label class="input-group-addon btn" for="testdate"></label>
			            </div>
			            
			            <div class="col-2">
			             <label class="text-nowrap  ">To Date:<span class="mandatory">*</span></label>
			             <input type="text" style="width: 115%;" class="form-control input-sm mydate" readonly="readonly" value="" placeholder=""  id="todate" name="todate"  required="required"  > 
						 <label class="input-group-addon btn" for="testdate"></label>
			            </div>
						</div>
						</div>
						<div class="row">
						<div class="col-12" align="center">
							<button type="submit" class="btn btn-sm add-btn" name="Action" onclick="return checkEmp();" value="ADDHANDING"   >ADD </button>
							<a href="Address.htm?empid="   class="btn btn-sm  btn-info">BACK</a>
						</div>
						</div>
						</form>
				</div>
			</div>
		</div> 
 
</div>
</body>
<script type="text/javascript">

$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
$('#todate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
</script>
<script type="text/javascript">
function checkEmp()
{
	var $name  = $("#fromemp").val();	
	var $name1 = $("#toemp").val();	
	
	if($name === $name1)
	{
		alert("Both Employee Can Not Be Same!");
		return false;
	}else{
		return true;
	}
}

</script>
</html>