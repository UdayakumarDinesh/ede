<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PropertiesDetailsReport</title>
<style>
body
{
	width:1020px !important;
	padding:5px;
	font-size: 16px;
}
@page {
  size: landscape;
}
th,td{
border:1px solid black;
padding:5px;
text-align:left
}

</style>
</head>
<body>
<%

List<Object[]> lablist= (List<Object[]>)request.getAttribute("lablist");
List<Object[]> list= (List<Object[]>)request.getAttribute("list");
List<Object[]> emplist=(List<Object[]>)request.getAttribute("emplist");
int year=(int)request.getAttribute("year"); %> 

<div align="center"> 
   <table  style="table-layout: fixed; margin:0px 0px 0px 10px;width:1020px;">
       <tr>
             <th style="text-align:right ; border:0px;">APR FORM NO-01</th>
       </tr>
       <%for(Object[] obj:lablist) {%>
       <tr>
             <th style="border:0px;text-align:center;text-decoration: underline;"><%=obj[0].toString().toUpperCase() %></th>
       </tr>
        <tr>
             <th  style="border:0px;text-align:center"><%= obj[1].toString().toUpperCase()%>,<%=obj[2].toString().toUpperCase() %>,<%=obj[3] %></th>
        </tr>
             <%} %>
        <tr>
        
             <th style=" border:0px;text-align:center">FORM FOR STATEMENT OF IMMOVABLE PROPERTY FOR THE YEAR ENDING 31<sup style="font-size: 8px;">ST</sup> DECEMBER-<%=year%></th>
           
       </tr>
  </table>
<br>
<table  style="table-layout: fixed; margin:0px 0px 0px 10px;width:1020px;border:1px solid black; border-collapse: collapse;">
   <thead>
              <%for(Object[] emp:emplist) {
             %>
        <tr>
              <td colspan="6"  ><span style="font-weight: bold;">NAME IN FULL(IN CAPITAL)&nbsp; : &nbsp;</span> <%=emp[0].toString().toUpperCase()%></td>
              <td  colspan="2"  ><span style="font-weight: bold;"> EMP.No :&nbsp;</span> <%=emp[1] %></td>
              <td  colspan="2"  ><span style="font-weight: bold;"> GRADE :&nbsp;</span><%=emp[3] %></td>
              <td  colspan="2"  ><span style="font-weight: bold;"> DEPT :&nbsp;</span><%=emp[5] %></td>
        </tr>
        <tr>
              <td colspan="3"   ><span style="font-weight: bold;"> DATE OF JOINING :&nbsp;</span><%=emp[7] %></td>
              <td  colspan="3"  ><span style="font-weight: bold;"> DESIGNATION :&nbsp;</span><%=emp[8] %></td>
              <td  colspan="2"  ><span style="font-weight: bold;"> LEVEL IN THE PAY MATRIX :&nbsp;</span><%=emp[4] %></td>
              <td  colspan="2"  ><span style="font-weight: bold;"> BASIC PAY :&nbsp;</span><%=emp[9] %></td>
              <td  colspan="2"  ><span style="font-weight: bold;"> INTERCOM No :&nbsp;</span><%=emp[2] %></td>
        </tr>
             <%} %>

    </thead>
</table>
<br>
    <table  style="table-layout: fixed; margin:0px 0px 0px 10px;width:1020px;border:1px solid black; border-collapse: collapse;">
        <thead>
             <tr >
                   <th colspan="3" style="text-align:center ;"> Details/ Description of Property and its Location (i.e.,House/ Building/ land Number, Village, Taluk, Sub division and District) </th>
                   <th colspan="2"  style="text-align:center;"> Present value *</th>
                   <th colspan="2"  style="text-align:center;"> If the Property is not wholly owned,state in whose name held and his/her relationship to the employee.</th>
                   <th colspan="2"  style="text-align:center;"> How acquired,Whether by purchase,lease, mortgage, inheritance, gift or otherwise with details of person/persons from whom acquired.</th>
                   <th colspan="2" style="text-align:center;"> Annual income from the property</th>
                   <th colspan="1"  style="text-align:center;"> Remarks @</th>
               </tr>
        </thead>
                <tbody>
                <%if(list.size()>0){ %>
                <%for(Object[] ob:list) {
                	
                %>
                       <tr>
                       
                            <td colspan="3" style="padding-bottom: 20px;"><%=ob[2] %>, <%= ob[3]%></td>
                            <td colspan="2" style="padding-bottom: 20px; text-align: right;"><%=ob[4] %></td>
                            <td colspan="2" style="padding-bottom: 20px;"><%=ob[5] %></td>
                            <td colspan="2" style="padding-bottom: 20px;"><%=ob[6] %></td>
                            <td colspan="2" style="padding-bottom: 20px;text-align: right;"><%=ob[7] %></td>
                            <td colspan="1" style="padding-bottom: 20px;"><%=ob[8] %></td>
                            
                      </tr>
                      <%}%>
                      <%}else{ %>
                      	<tr><td colspan="12" style="text-align: center;"> Nil</td></tr>
                      <%} %>
              </tbody>
              
    </table>
   
</div>
   <div class="break"></div>
          <div align="left" style="margin-left:13px;">
             <p> * In case where it is not possible to assess the value accurately, the approximate value in relation to present condition may be indicated.<br>
                 @ whether acquisition of the said property has been intimated to the Competent Authority, if so date of intimation may be mentioned.<br>
                <span style="font-weight: bold;">    Separate Sheet may be enclosed, if space is not sufficient.<br><br>
                Date: <span style=" margin-left: 650px;"> Signature:</span></span></p>
         </div>

</body>
</html>