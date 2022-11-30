
<%@page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>

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
	String LoginType1 = (String)request.getAttribute("LoginType");
	List<Object[]> NoticeList=(List<Object[]>)request.getAttribute("NoticeList");
%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>Today's Notice</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active" aria-current="page">Today's Notice</li>
				</ol>
			</div>

		</div>
		<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
		<div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
		</div>
		<%}if(ses!=null){ %>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
		<%} %>


		<div class="card">
			<div class="card-header" style="height: 4rem">
			</div>
		</div>


		<div class="card-body main-card">

			<form action="#" method="POST" id="NoticeForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="table-responsive">
					<table
						class="table table-hover  table-striped table-condensed table-bordered table-fixed"
						id="myTable">
						<thead>
							<tr>
								<th style="width: 4%">SN</th>
								<th style="width: 10%">Notice No</th>
								<th style="width: 45%">Description</th>
								<th style="width: 11%">Issued Date</th>
								<th style="width: 8%">Download</th>
							</tr>
						</thead>
						<tbody>
							<%
							if (NoticeList != null) {
								int slno = 0;
								for (Object[] ls : NoticeList) {
							%>
							<tr>
								<td style="text-align: center;"><%=NoticeList.indexOf(ls)+1 %></td>
								<td><%=ls[1]%></td>
								<td><%=ls[2]%></td>
								<td style="text-align: center;"><%=DateTimeFormatUtil.SqlToRegularDate(ls[3].toString())%></td>
								<td style="text-align: center;">
									<button type="submit" class="btn btn-sm" name="NoticeId" value="<%=ls[0]%>" formaction="EMSNoticeDownload.htm" formmethod="post" data-toggle="tooltip" data-placement="top"
										title="Download" formtarget="_blank">
										<i class="fa-solid fa-download " style="color: green;"></i>
									</button>
								</td>

							</tr>
							<%
							}
							}
							%>

						</tbody>
					</table>

					
				</div>

				<div class="row text-center">
					<div class="col-md-12">
					</div>
				</div>

			</form>


		</div>

	</div>

</body>
</html>