<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
    <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quarterly Strength Report</title>
<style type="text/css">
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
  
}
table  thead tr{
background-color: #0e6fb6;
color: white;
}
@page { size: landscape; }

</style>
</head>
<body>
<%
List<Object[]> EmployeeList=(List<Object[]>)request.getAttribute("EmployeeList"); 
Object[] labdetails = (Object[])request.getAttribute("labdetails");
%>
		
	<div class="card"  >
			<div class="card-head">
				<div align="center">
			<%for(Object[] list:EmployeeList){%>
			 <%if(labdetails!=null){%><b style="font-size: 20px;"><%=labdetails[2] %> &nbsp;(<%=labdetails[1]%>)</b><%} %><br>
			QUARTERLY STRENGTH RETURN AS ON: <%=DateTimeFormatUtil.SqlToRegularDate(list[3].toString())%>,&nbsp; Letter No :<%=list[2]%>
				 
		      <%break;}%>
			</div>
		</div>	
				<div class="card-body" style="margin-top: 10px;">
						<div class="table-responsive" >
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
								   <tr>            
	                                   <th>Name of Post</th>
	                                   <th>RE</th>
	                                   <th>Corporate Pool</th>
	                                   <th>Authorised Transaction<br>Trans In</th>
	                                   <th>Authorised Transaction<br>Trans Out</th>
	                                   <th>Total RE</th>
	                                   <th>SC</th>
	                                   <th>ST</th>
	                                   <th>OBC</th> 
	                                   <th>EXSM</th>
	                                   <th>HEL PHY HND</th>
	                                   <th>SPRT</th>
	                                   <th>OTH</th>
	                                   <th>Total Held</th>
	                                   <th>VAC</th>
	                                   <th>Male</th>
	                                   <th>Female</th>
                                  </tr>
                               </thead>
                             <tbody>
			             <%for(Object[] list:EmployeeList){    
                  if(list[8]!=null&&list[9]!=null||list[10]!=null||list[11]!=null&&list[13]!=null&&list[14]!=null&&list[15]!=null&&list[16]!=null&&list[17]!=null&&list[18]!=null&&list[19]!=null){
                               	int value =  Integer.parseInt(list[8].toString());
                                int value2 = Integer.parseInt(list[9].toString());
                                int value3 =Integer.parseInt(list[10].toString());
                                int value4 = Integer.parseInt(list[11].toString());
                                int TotalRE = value+value2+value3-value4;
                               	int data1 =  Integer.parseInt(list[13].toString());
                               	int data2 =  Integer.parseInt(list[14].toString());
                               	int data3 =  Integer.parseInt(list[15].toString());
                               	int data4 =  Integer.parseInt(list[16].toString());
                               	int data5 =  Integer.parseInt(list[17].toString());
                            	int data6 =  Integer.parseInt(list[18].toString());
                            	int data7 =  Integer.parseInt(list[19].toString());
                            	int TotalHeld = data1+data2+data3+data4+data5+data6+data7;
                            	int VAC = TotalRE-TotalHeld;
                            %>       
	                    <tr> 
	                                 <td > <%=list[5]%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[8]!=null){out.println(list[8]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[9]!=null){out.println(list[9]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[10]!=null){out.println(list[10]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[11]!=null){out.println(list[11]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%=TotalRE%> </td> 
                                     <td align="center"> <%if(list!=null){%><%if(list[13]!=null){out.println(list[13]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[14]!=null){out.println(list[14]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%=list[15]%> </td>
                                     <td align="center"> <%=list[16]%> </td>
                                     <td align="center"> <%=list[17]%> </td>
                                     <td align="center"> <%=list[18]%> </td>
                                     <td align="center"> <%=list[19]%> </td>
                                     <td align="center"> <%=TotalHeld%> </td> 
                                     <td align="center"> <%=VAC%> </td> 
                                     <td align="center"> <%=list[20]%> </td>
                                     <td align="center"> <%=list[21]%> </td>
                                  
                              <% }} %>
                            </tr>  
                                </tbody> 
							</table>
		   </div>
		 </div>
		</div>	
  	

</body>
</html>