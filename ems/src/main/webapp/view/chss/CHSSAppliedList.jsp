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
<style type="text/css">
.a{
	background-color: #d6e0f0;
}
	.font{
		  font-family:'Muli', sans-serif !important;
		  font-style: normal;
		  font-size: 13px;
		  font-weight: 400 !important;
	}
</style>
</head>
<body>

<%
	
	
	List<Object[]> empchsslist=(List<Object[]>)request.getAttribute("empchsslist");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
%>
 
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS List</li>
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
					
					<form action="#" method="post" id="ClaimForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<td style="padding-top:5px; padding-bottom: 5px;">SNo</td>
										<td style="padding-top:5px; padding-bottom: 5px;" >Claim No</td>
										<td style="padding-top:5px; padding-bottom: 5px;" >Patient Name</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Ailment</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Claim Date</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Action</td>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
									for(Object[] obj : empchsslist){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[12] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;" ><%=obj[17] %> <%=obj[9] %></td>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
											<td 
											<%if("7".equals(obj[9].toString())||"9".equals(obj[9].toString()) ||"11".equals(obj[9].toString())||"13".equals(obj[9].toString())){%>  
										    style="padding-top:5px; padding-bottom:5px; color:#d1312c;"				
											<%}else if("1".equals(obj[9].toString())){%>
											 style="padding-top:5px; padding-bottom:5px;  color:#2db714;"     
											<%}else if("3".equals(obj[9].toString())||"12".equals(obj[9].toString())||"6".equals(obj[9].toString())){%>
											style="padding-top:5px; padding-bottom:5px;  color:#83bd62;"
											<%}else if("10".equals(obj[9].toString())||"4".equals(obj[9].toString())||"2".equals(obj[9].toString())){%>
												style="padding-top:5px; padding-bottom:5px;  color:#149694;"
											<%}else{%>
											style="padding-top:5px; padding-bottom: 5px;  color:#0b4980;"
											<%}%>
											 class="editable-click"> <a class="font" href="Chss-Status-details.htm?chssapplyid=<%=obj[0]%>"   title="Click for Details." ><%=obj[18] %> </a></td>
											
											
											<td style="padding-top:5px; padding-bottom: 5px;">
												<%if(Integer.parseInt(obj[9].toString())==1 || Integer.parseInt(obj[9].toString())==3 || Integer.parseInt(obj[9].toString())==7){ %>
													<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSConsultMainData.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Edit">
														<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
													</button>	
												<%} %>
												
												<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSForm.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
													<i class="fa-solid fa-eye"></i>
												</button>	
												
												<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEmpDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
													<i style="color: #019267" class="fa-solid fa-download"></i>
												</button>
												<%-- <button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>"  formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Status" 	onclick="window.open('Chss-Status-details.htm?chssapplyid=<%=obj[0]%>')" title="Click for Details.">
													<i class='fas fa-stream'> </i>
												</button> --%>
												<%-- <%if(Integer.parseInt(obj[9].toString())==1 || Integer.parseInt(obj[9].toString())==3 && false){ %>
													<button type="button" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>"  onclick="return CheckClaimAmount(<%=obj[0] %>)"  data-toggle="tooltip" data-placement="top" title="Forward">
														<i class="fa-solid fa-forward" style="color: #A63EC5"></i>
													</button>
												<%} %>		 --%>	
												<input type="hidden" name="isapproval" value="N">							
											</td>
										</tr>
									<%} %>
								</tbody>
							</table>
						</div>
					</form>
					
					
				</div>
			</div>		
			
		</div>
	
	 </div>
	 <form action="CHSSUserForward.htm" method="post" id="form2">
	 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	 	<input type="hidden" name="claimaction" value="F">
	 	<input type="hidden" id="form2-chssapplyid" name="chssapplyid" value="">
	 </form>
	 
<script type="text/javascript">

function CheckClaimAmount($chssapplyid)
{
	$.ajax({

		type : "GET",
		url : "CHSSClaimFwdApproveAjax.htm",
		data : {
				
			chssapplyid : $chssapplyid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
						
			if(result===1){
				if(confirm('Are You Sure To Forward?'))
				{
					$('#form2-chssapplyid').val($chssapplyid);
					$('#form2').submit();
				}
			}else
			{
				alert('Please Add Bill and Items To Forward ');
				return false;	
			}
		
		}
	});
	
}

</script>


</body>
</html>