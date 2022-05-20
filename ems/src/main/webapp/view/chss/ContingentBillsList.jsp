<%@page import="java.time.LocalDate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>

<%
	
	
	List<Object[]> contingentlist=(List<Object[]>)request.getAttribute("ContingentList");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	String logintype = (String)request.getAttribute("logintype");
%>
 
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-4">
				<h5>CHSS Contingent Approval List</h5>
			</div>
				<div class="col-md-8 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Contingent List</li>
					</ol>
				</div>
			</div>
	</div>	
	
	<div class="page card dashboard-card">
	
	<div class="card-body" >
	
	
	
	<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
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
				
			<div class="card" >
				<div class="card-body " >
				
					<form action="ApprovedBills.htm" method="POST" style="float: right;">
							<button class="btn btn-sm" style="background-color: #94477b ;color: white; margin-bottom: 10px;" >Approved Bills</button>
							<br>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
							
					<form action="#" method="post" id="ClaimForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1"> 
								<thead>
									<tr>
										<td style="padding-top:5px; padding-bottom: 5px;">SNo</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Contingent No</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Date</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
										<td style="padding-top:5px; padding-bottom: 5px;">View</td>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
									for(Object[] obj : contingentlist){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1] %></td>
											<%if(obj[2]!=null){ %>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[2].toString()))%></td>
											<%}else{ %>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(LocalDate.now().toString()))%></td>
											<%} %>
											<td 
												<%if("1".equals(obj[5].toString()) || "2".equals(obj[5].toString()) ){%>  
											    style=" padding-top:5px; padding-bottom: 5px;color:#4700D8; font-weight: 600;"				
												<%}else if("3".equals(obj[5].toString())||"5".equals(obj[5].toString()) ||"7".equals(obj[5].toString())||"9".equals(obj[5].toString()) || "11".equals(obj[5].toString())||"13".equals(obj[5].toString())){%>
												 style="  padding-top:5px; padding-bottom: 5px;color:#B20600; font-weight: 600;"     
												<%}else  if("4".equals(obj[5].toString())||"6".equals(obj[5].toString())||"8".equals(obj[5].toString())|| "10".equals(obj[5].toString()) || "12".equals(obj[5].toString())){%>
													style="padding-top:5px; padding-bottom: 5px; color:#F66B0E; font-weight: 600;"
												<%}else if("14".equals(obj[5].toString())){ %>
												style=" padding-top:5px; padding-bottom: 5px;color:#6BCB77; font-weight: 600;"
												<%}else{ %>
												 style="padding-top:5px; padding-bottom: 5px; color:#4700D8; font-weight: 600;"
												<%} %>	
											>
												<%if("1".equals(obj[5].toString()) ){%>  
												 Bill Generated
												<%}else{ %>
												<%=obj[7] %>
												<%} %>
											</td>
											<td style="padding-top:5px; padding-bottom: 5px;">
												
												<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingentBillData.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Details">
													<i class="fa-solid fa-circle-info" style="color: #03C4A1"></i>
												</button>	
												<button type="submit" class="btn btn-sm view-icon" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBill.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
													<i class="fa-solid fa-eye"></i>
												</button>	
												<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBillDownload.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Download">
													<i style="color: #019267" class="fa-solid fa-download"></i>
												</button>
												
											</td>
										</tr>
									<%} %>
								</tbody>
							</table>
						</div>
					</form>
					<%if(logintype.equalsIgnoreCase("K")){ %>
					<div class="row" style="margin-top: 10px;">
					
						<div class="col-12"  align="center" >
							<form action="CHSSBatchList.htm" method="post">
								<button class="btn btn-sm" style="background-color: #FFD36E " >Generate Contingent Bill</button>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
							
					</div>
					<%} %>
				</div>
			</div>		
			
		</div>
	
	 </div>
	<script type="text/javascript">
	
	$("#myTable1").DataTable({
	    "lengthMenu": [ 10, 25, 50, 75, 100],
	    "pagingType": "simple",
	    "language": {
		      "emptyTable": "No Record Found"
		    }

	});
	
	</script>


</body>
</html>