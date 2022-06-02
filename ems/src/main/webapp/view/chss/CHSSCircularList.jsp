<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<%@page import="java.util.List"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>CHSS Circular List</title>
<jsp:include page="../static/dependancy.jsp"></jsp:include>

<style type="text/css">
.group
{
    text-align: center;
    color: #3498DB;
    text-shadow: 0px 0px 1px #3A3B3C;
    text-decoration: underline;
}

</style>

</head>
<body>
<%
	List<Object[]> circulatlist = (List<Object[]> )request.getAttribute("circulatlist") ;
%>

<h3 class="group">CIRCULAR LIST</h3>
		<div class="table-responsive" align="center">
		<table
			class="table table-bordered table-hover table-striped table-condensed"
			id="myTable" style="width: 80%;">
			<thead>
				<tr>

					<th>SN</th>
					<th>Description</th>
					<th>Action</th>

					<!-- <th>Action</th> -->
				</tr>
			</thead>
			<tbody>
				<%	long slno=0;
									for(Object[] obj : circulatlist){ 
									slno++;%>
				<tr>
					<td style="text-align: center; width: 5%;"><%= slno%>.</td>
					<td style="text-align: justify; width: 80%;"><%=obj[1] %></td>
					<td style="text-align: center; width: 5%;">
						<button type="submit" class="btn btn-sm" name="path"
							value="<%=obj[2] %>" formaction="CircularDownload.htm"
							formtarget="_blank" formmethod="post" data-toggle="tooltip"
							data-placement="top" title="Download">
							<i style="color: #019267" class="fa-solid fa-download"></i>
						</button>
					</td>
				</tr>
				<%} %>
			</tbody>
		</table>
	</div>


</body>



</html>