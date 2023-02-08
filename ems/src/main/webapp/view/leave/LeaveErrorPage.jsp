<!DOCTYPE html >
<%@ page language="java"%>
<%@ page isErrorPage="true"%> 
<html>
<head>

<title>Insert title here</title>
</head>
<body>
   <div class="row" align="center" style="margin-top:10px;">
	             <div class="col-md-4 col-md-offset-4">
                 <div class="alert alert-danger  "><p class="text-center">Some Problem Occur ! Please Try Again Later</p></div>
                
                
                 <% String empid=(String)session.getAttribute("empid");
                  if(empid==null){%>
                <% response.sendRedirect("error");} %>
                
                </div>
                </div>




</body>
</html>