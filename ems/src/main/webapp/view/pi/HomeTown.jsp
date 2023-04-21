<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Town</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 
</head>
<style type="text/css">
table{
	align: left;
	width: 100% !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	margin-left:10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
	
}
input{
border-width: 0 0 1px 0;
width:80%;
}
input:focus {
  outline: none;
}

</style>
<body>
<%
String LabLogo = (String)request.getAttribute("LabLogo");
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>Intimation For Home Town </h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				    <li class="breadcrumb-item active" aria-current="page">Home Town</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	
	<div class="page card dashboard-card">
		 <div class="card-body" align="center">
			<div align="center">
					<%String ses=(String)request.getParameter("result"); 
					String ses1=(String)request.getParameter("resultfail");
					if(ses1!=null){ %>
						<div class="alert alert-danger" role="alert"  style="margin-top: 5px;">
							<%=ses1 %>
						</div>
					<%}if(ses!=null){ %>
						<div class="alert alert-success" role="alert"  style="margin-top: 5px;">
							<%=ses %>
						</div>
					<%} %>
				</div>
			 <div class="card-body" >
			 <div class="card" style="padding-top:0px;margin-top: -15px;width:80%;">
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"align="center">
			        <table style="border: 0px; width: 100%">
						<tr>
						  <td style="width: 20%; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
						  <td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h4> DECLARATION OF HOME TOWN</h4> </td>
						  <td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;"> &nbsp;<span class="text-blue"></span></span> </td>
						</tr>						
					</table> 
					
					<table style="margin-top: 5%;border:0;">	
					  <tbody>
						<tr> <td style="border: 0;font-weight:bold;font-size:16px;">To,</td> </tr>	
						<tr> <td style="border: 0;font-weight:bold;font-size:16px;">The Chief Executive Officer</td> </tr>	
						<tr> <td style="border: 0;font-size:16px;">STARC</td> </tr>	
						<tr> <td style="border: 0;font-size:16px;">Bangalore</td> </tr>	
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;text-decoration:underline;">Kind Attn: Incharge - P&A</td> </tr>
						<tr> <td style="border: 0;"> I here by declare my hometown as under for entering the same in personal / official records.</td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;">NAME &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;:</td> </tr>
						<tr> <td style="border: 0;">EMP. NO. &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td> </tr>
						<tr> <td style="border: 0;">DESIGNATION &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;:</td> </tr>
						<tr> <td style="border: 0;">HOMETOWN &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;:</td> </tr>
						<tr> <td style="border: 0;">STATE &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;:</td> </tr>
						<tr> <td style="border: 0;">NEAREST RAILWAYSTATION &emsp;:</td> </tr>
					    <tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"> Employee Signature</td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;">Date:</td> </tr>
					  </tbody>
					</table>
				 </div>
			 </div>
			</div> 
    </div>
</div>					
</body>
</html>