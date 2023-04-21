<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.time.LocalDate ,java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 
<title>Insert title here</title>
<style type="text/css">
 .main {
	width: 1130px;
	height:260px;
	white-space: nowrap;
  overflow-x: auto;
/*   margin-top: 20px; */
	padding: 0 15px;
	padding-top: 12px;
} 
 
.right {
	  display: inline-block;
	  width: 300px;
	  margin: 10px ;
	 /*  background-color: rgb(133, 193, 233); */
	  border-radius: 10px;
	  padding: 5px;
	  font-family: 'Lato';
	  color: #005C97;
	  text-transform: capitalize;

	  
	 height: 200px;
	 position: relative;
	 box-shadow: 0px 0px 10px 0px rgb(200 194 194 / 90%);  
	 position: relative;
  z-index: 1;
  text-align: center;
  transition: transform 0.2s;
  
  white-space: nowrap; overflow: hidden;
	
}
.right:hover{
	transform: scale(1.1);
}

 .right::before {
  content: "";
  background-color:rgb(93, 173, 226);
  height: 28%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  border-radius:10px 10px 50% 50%;
  background-clip: padding-box;
  z-index: -1;
} 
/* .right::after {
  content: "";
  background-color: rgb(133, 193, 233);
  height: 14%;
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  border-radius:50% 50%  10px 10px ;
  background-clip: padding-box;
  z-index: -1;
}
 */
.right p{
	font-weight: bolder;
	margin-bottom: 1px;
	margin-top: 1px;
	line-height: 1.5;
}

#button{
	position: absolute;
	bottom: 10px;
	left: 50%;
 	transform: translateX(-50%);
}
	
#td1{ 
 	width: 80px;
 	vertical-align: middle;
}

table {
/* 	table-layout: fixed; */
	max-height: 250px;
	width: 100%;

	overflow-y: scroll;
	/* overflow-x: scroll; */
	display: block;
	border: 1.5px solid white;
 	-ms-overflow-style: none;  /* IE and Edge */
	scrollbar-width: none; /*  Firefox */ 
	

}

	table::-webkit-scrollbar {
	display: none;
}
#OneRow{
	margin: 0 8px;
	margin-top: 20px;
	background-color:rgb(133, 193, 233);
	border-radius: 10px;
	position: relative;
}
input[type="radio"] {
  transform: scale(1.2);
}
tr{
	height: 50px;
	font-size: 20px;
	/* border-right: 1.5px solid white; */
}
/* .rowTable{
	border: 1.5px solid blue;
	position: relative; 
}  */
.comment{
	position: absolute;
 	right: 100px;
 	top: 47%;
 	transform: translateY(-50%);
 	font-weight: bolder;
 	
}
textarea{
	border-radius: 8px;
}
.btnLink2{
	position: absolute;
	right: 30px;
	top: 50%;
 	transform: translateY(-50%);
}

</style>
</head>
<body>
<%String empname = (String)request.getAttribute("empname"); %>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>MT Link Trip <small> <%if(empname!=null){%> <%=empname%> <%}%> </small> </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Link Trip</li>	
					</ol>
				</div>
			</div>
</div>
<% 
String comp=(String)request.getAttribute("comp"); 
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
List<Object[]> appllist=(List<Object[]>)request.getAttribute("AllListApp");
List<Object[]> triplist=(List<Object[]>)request.getAttribute("triplist");

String reqDate = "0";
if(request.getAttribute("reqDate")!=null){
	reqDate=(String)request.getAttribute("reqDate");
}

String mtRequestNo = "0";
if(request.getAttribute("mtRequestNo")!=null){
	mtRequestNo=(String)request.getAttribute("mtRequestNo");
}

String appid = (String)request.getAttribute("appid");
/* if(request.getAttribute("appid")!=null){
	appid=(String)request.getAttribute("appid");
} */


String EmpId = (String)request.getAttribute("EmpId");
/* if(request.getAttribute("EmpId")!=null){
	EmpId=(String)request.getAttribute("EmpId");
} */
%>

<%String ses=(String)request.getParameter("result"); 
	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){%>
	<center><div class="alert alert-danger" role="alert"><%=ses1 %></div></center>
	<%}if(ses!=null){%>
	<center><div class="alert alert-success" role="alert" ><%=ses %> </div></center>
    <%}%>


<%if(comp.equalsIgnoreCase("link")){ %>
   
				<% if(!(request.getParameter("message")==null)){ %>
				<div class="alert alert-success"><strong><%= request.getParameter("message") %></strong> </div>
				 <%} %>

<%if(appllist!=null&&appllist.size()!=0){%>

<div class="main">

 <% for(Object[] list:appllist){ %>
	
		<div class="right" onclick="submitLink('<%=sdf.format(list[4]) %>','<%=list[0]%>','<%=list[12] %>','<%=list[1] %>')" <%if(!mtRequestNo.equalsIgnoreCase((String)list[0]) && !mtRequestNo.equalsIgnoreCase("0")) {%>  style="filter: blur(1.5px);" <%} %> <%if(reqDate.equalsIgnoreCase(sdf.format(list[4]))) {%> style="box-shadow: 0 0 12px 5px rgb(133, 193, 233);" <% }%>>
			<p style="color: white; font-size: 20px;"><%=list[2] %></p>
			<p style="color: white; font-size: 12px; padding-bottom: 15px;">(<%=list[3] %>)</p>
			<div> <!-- style="width: 205px; height: 220px;" --> 
			<p>For Req# <%=list[0]%><%-- - <%=list[2] %>-<%=list[3] %>  Request--%></p>
			<p> From <%=list [7]%> To <%=list[8] %> </p> 
			<p>on <%=sdf.format(list[4]) %> To <%=sdf.format(list[16]) %> </p>
			<p>At <%=list[5] %></p>
			</div>
			<%-- <%if((reqDate.equalsIgnoreCase(obj[0].toString()))) %> --%>
			<!-- <center><button class="btn btn-sm misc2-btn apply-bn"  id="button" name="button" formnovalidate>Link</button></center> -->

		</div>
		
		
		
<%}%>
 </div>

<% }else{%>
   
    	<center><b style="color: red;">No Data Found</b></center>
    	<% } %>
    	
    	
<%if(!reqDate.equals("0")) {%>
<form action="MtTripLink.htm" method="POST">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<input type="hidden" name="appid" value="<%=appid%>">
<input type="hidden" name="EmpId" value="<%=EmpId%>">

		<div class="row" id="OneRow">
			    <div class="rowTable">
				<table class="table" style="width: 820px; margin-bottom: 0rem; ">
					<tbody>
						<%for(Object[] trip:triplist){
							if(sdf.format(trip[5]).equalsIgnoreCase(reqDate)){ %>
							<tr>
								<td id="td1" style=" text-align: center; "><input type="radio" name="tripid" value="<%=trip[1]%>"></td>
								<td><b>Trip#&nbsp; <%=trip[2]%> :&nbsp; <%=sdf.format(trip[5]) %> To <%=sdf.format(trip[9]) %> AT&nbsp; <%=trip[6]%>&nbsp;Vehicle &nbsp;&nbsp;<%=trip[7]%>&nbsp;&nbsp;(<%=trip[8]%>) &nbsp;by&nbsp; <%=trip[0]%>&nbsp;Covering &nbsp;&nbsp;<%=trip[4]%>  </b></td>
							</tr>
							<%}}%>
							
							<tr>
							<td id="td1" style=" text-align: center; "><input type="radio" name="tripid" value="0"></td>
							<td><b>Vehicle not available </b></td>
							</tr>
							<tr>
							<td id="td1" style=" text-align: center; "><input type="radio" name="tripid" value="-1"></td>
							<td><b>CMTD not available </b></td>
							</tr>
							
					</tbody>
				</table>
				</div>
				<div class="comment">
				<center><td>Comment</td></center>
    			<textarea name="comment" cols="" maxlength="255"></textarea>
    			</div>
    			
    			<div class="btnLink2">
    			<button class="btn btn-sm misc2-btn apply-bn" onclick="confirmLink()">Link</button>
    			</div>
    			
    			</div>
</form>    			
    			<%}} %>
    			
  
  			
			

<form action="MTTripLink.htm" id="changeform" >
	<input type="hidden" name="reqDate" id="reqDate" >   
	<input type="hidden" name="mtRequestNo" id="mtRequestNo"> 	
	<input type="hidden" name="appidRet" id="appidRet">
	<input type="hidden" name="EmpIdRet" id="EmpIdRet">
</form>
   	
<script type="text/javascript">

function submitLink(reqDate1,mtRequestNo1,appid,EmpId) {
	$('#reqDate').val(reqDate1);
 	$('#mtRequestNo').val(mtRequestNo1);
 	$('#appidRet').val(appid);
 	$('#EmpIdRet').val(EmpId);
	$('#changeform').submit();
}

function confirmLink() {
	const items = document.querySelectorAll('input[name="tripid"]');
	var selectedItem = null;
	for (let i = 0; i < items.length; i++) {
		if (items[i].checked) {
			selectedItem = items[i].value;
			break;
		}
	}
	if (selectedItem == null) {
		alert("Please select atleast one option");
		event.preventDefault();
	} else {
		if (window.confirm('Are you sure To Link?')) {
			return true;
		} else {
			event.preventDefault();
			return false;
		}
	}
}
</script>

</body>
</html>