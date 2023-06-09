<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List,java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%> 
<%@page import="com.vts.ems.property.model.PisPropertyConstruction,com.vts.ems.master.model.LabMaster"%>
<%@page import="java.util.List,java.util.ArrayList,java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%>       
<!DOCTYPE html>
<html>
<%
Object[] emp = (Object[])request.getAttribute("EmpData");
%>
<head>
<title>Sanction Order</title>
<meta charset="ISO-8859-1">
<jsp:include page="../static/LetterHead.jsp"></jsp:include>
<style type="text/css">
.break {
	page-break-after: always;
}

#pageborder {
	position: fixed;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	border: 2px solid black;
}

@page {
	size: 790px 1050px;
	margin-top: 49px;
	margin-left: 72px;
	margin-right: 39px;
	margin-buttom: 49px;
	border: 2px solid black; @ bottom-right { counter-increment : page;
	counter-reset: page 2;
	content: "Page " counter(page) " of " counter(pages);
	margin-bottom: 30px;
	margin-right: 10px;
}

@top-right {
	content: "";
	margin-top: 30px;
	margin-right: 10px;
}

@top-left {
	margin-top: 30px;
	margin-left: 10px;
	content: "Emp No: <%=emp[0]%>";
}

@top-center {
	margin-top: 30px;
	content: "";
}

@bottom-center {
	margin-bottom: 30px;
	content: "";
}
}
p {
	text-align: justify;
	text-justify: inter-word;
}
body
{
	font-size:14px !important;
}

table{
	align: left;
	width: 650px !important;
	max-width: 650px !important;
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
	
	 -ms-word-break: break-all;
     word-break: break-all;

     /* Non standard for WebKit */
     word-break: break-word;

-webkit-hyphens: auto;
   -moz-hyphens: auto;
        hyphens: auto;
	
}

</style>

</head>
<body>
<%
PisPropertyConstruction con = (PisPropertyConstruction) request.getAttribute("constructionFormData");
LabMaster lab = (LabMaster)request.getAttribute("Labmaster");

String empNo = (String) session.getAttribute("EmpNo");
List<String> PandAs = (List<String>) request.getAttribute("PandAsEmpNos");
Object[] panda = (Object[])request.getAttribute("PandAData");

List<Object[]> ApprovalEmpData = (List<Object[]>) request.getAttribute("ApprovalEmpData");

List<String> adminRemarks = Arrays.asList("VDG", "VPA", "VSO", "APR");
%>
    
<div class="center">
   <div style="width: 100%;float:left;margin-top: 20px;">
      <div style="width: 100%; height: 75px; border: 0; text-align: center;"><h3 style="text-decoration: underline">
         SANCTION ORDER FOR 
          <%if(con!=null && con.getTransactionState()!=null) {%>
          <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>CONSTRUCTION
          <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>ADDITION
          <%}else{ %>RENOVATION<%} }%>
          </h3></div>
   </div>
 <!--  <br> -->
   <table style="margin-top: 100px;">
      <tr>
        <td style="border: 0;width: 80%;">Ref&nbsp;&nbsp;:&nbsp;&nbsp;<label style="color: blue;"><%if(con!=null && con.getLetterNo()!=null) {%><%=con.getLetterNo() %><%} %></label></td>
        <td style="border: 0;width: 20%;">Date&nbsp;&nbsp;:&nbsp;&nbsp;<label style="color: blue;"><%if(con!=null && con.getLetterDate()!=null) {%><%=DateTimeFormatUtil.SqlToRegularDate(con.getLetterDate())%><%} %></label></td>
      </tr>
      <tr><td style="border: 0;"></td></tr>
      <tr>
        <td style="border: 0;" colspan="2">
        <%if(emp!=null) {
          if(emp[5]!=null){ %>
          <%=emp[5] %>
         <%}if(emp[1]!=null){ %>
         <label style="color: blue;"><%=emp[1] %></label> 
          <%}} %>
        </td>
      </tr>
      <tr>
        <td style="border: 0;" colspan="2">EmpNo&nbsp;&nbsp;:&nbsp;&nbsp;<label style="color: blue;"><%if(emp!=null && emp[0]!=null) {%><%=emp[0] %><%} %></label></td>
      </tr>
      <tr>
        <td style="border: 0;color: blue;" colspan="2"><%if(emp!=null && emp[2]!=null) {%><%=emp[2] %><%} %></td>
      </tr>
      <tr>
        <td style="border: 0;" colspan="2"><%if(lab!=null) {%><%=lab.getLabCode()+", "+lab.getLabCity()%><%} %></td>
      </tr>
       <tr><td style="border: 0;"></td></tr>
        <tr>
        <td style="border: 0;" colspan="2">
          The Competent Authority has granted permission for 
          <%if(con!=null && con.getTransactionState()!=null) {%>
          <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>Construction
          <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>Addition
          <%}else{ %>Renovation<%} }%> of House at a cost of Rs.
          <span style="text-decoration: underline;color: blue;"><%if(con!=null && con.getProposedCost()!=null) {%><%=con.getProposedCost() %><%} %></span> as per your proposal dated
          <span style="text-decoration: underline;color: blue;"><%if(con!=null && con.getConstrCompletedBy()!=null) {%><%=DateTimeFormatUtil.SqlToRegularDate(con.getConstrCompletedBy()+"")%><%} %></span>.         
        </td>
        </tr>
        <tr><td style="border: 0;"></td></tr>
        <tr>
         <td style="border: 0;" colspan="2">
           You are advised to submit Form-62 after completion of
           <%if(con!=null && con.getTransactionState()!=null) {%>
           <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>Construction
           <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>Addition
           <%}else{ %>Renovation<%} }%> of house along with valuation report.
         </td>
        </tr>
        <tr><td style="border: 0;"></td></tr>
        <tr><td style="border: 0;"></td></tr>
        <tr><td style="border: 0;"></td></tr>
        <tr><td style="border: 0;"></td></tr>
        <tr>
          <td style="border: 0;color: blue;" colspan="2">
            <%if(panda!=null && panda[1]!=null){ %>
            <%=panda[1] %>  
           <%}%>
          </td>
        </tr>
          <tr>
            <td style="border: 0;">
             <%if(panda!=null && panda[2]!=null) {%>  
             <%=panda[2] %>
             <%} %>
            </td>
          </tr>
          <tr>
          <td style="border: 0;">For CEO,&nbsp;<%if(lab!=null) {%><%=lab.getLabCode() %><%} %> </td></tr>
   </table>
 	
</div>

</body>
</html>