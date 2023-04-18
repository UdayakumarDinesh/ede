<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" %>
<!DOCTYPE html >

<html>
<head>
<!--  Bootstrap -->
	<link rel="stylesheet" href="vtsfolder/bower_components/bootstrap/dist/css/bootstrap.min.css"/>
 
<style type="text/css">
 @media print {
  #printPageButton {
    display: none;
  }
}
 </style>  
    
<title>Newspaper Claim Print</title>
</head>
<body>


<%
String name =(String)request.getAttribute("name");
String designation=(String)request.getAttribute("desig");
Object[] NewspaperUserPrintData=(Object[])request.getAttribute("NewspaperUserPrintData");
LabMaster LabDetails=(LabMaster)request.getAttribute("LabDetails");
String PayableRupee="Not Available";
String AdmissibleRupee="Not Available";
String ClaimRupee="Not Available";

String NewsClaimHeader =(String)request.getAttribute("NewsClaimHeader");

if(NewspaperUserPrintData!=null)
{
	 String ClaimAmountRsPaisA[]=IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[2].toString());
	 ClaimRupee=IndianRupeeFormat.rupeeFormat(ClaimAmountRsPaisA[0]);
	
	 String PayableAmountRsPaisA[]=IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[4].toString());
	 PayableRupee=IndianRupeeFormat.rupeeFormat(PayableAmountRsPaisA[0]);
	
	 String AdmissibleAmountRsPaisA[]=IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[3].toString());
	 AdmissibleRupee=IndianRupeeFormat.rupeeFormat(AdmissibleAmountRsPaisA[0]);
	 
}

%>

<div align="center"> 

<div style="width: 790px !important; "align="left">

<div class="container-fluid">
<div class="row">

           <div class="col-sm-12 text-center" align="center" >
           <%	if(NewsClaimHeader!=null && !NewsClaimHeader.trim().equalsIgnoreCase("")){
				        	   String[] headerStr=NewsClaimHeader.split("/");
				           
				           	for(int i=0 ; i<headerStr.length ; i++){ %>
				             	<b> <%=headerStr[i] %> </b><br>
				             <% } }%>
            <%--  <b><%=LabDetails.getLabName() %></b> --%>
           </div>
     <div class="col-sm-12">
       <br><br>
        <div class="col-sm-2"></div>
        
        <div class="col-sm-8">
        <table>
        <tbody>
            <tr>
             <td>Name Of The Applicant:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
             <td><%=name%></td>
            </tr>
       
            <tr>
              <td>Designation:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
              <td><%=designation%></td>
           </tr>
       
           <!-- <tr>
             <td>Department:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
             <td>Defence Research & Development Establishment</td>
           </tr> -->
       
           <tr>
             <td>Pay Level:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
             <td><%if(NewspaperUserPrintData!=null){%><%=NewspaperUserPrintData[5]%><%}%></td>
           </tr>
       
       
        </tbody>
       
       </table>
       </div>
    <div class="col-sm-2"></div>
  </div>
    
    
    
    <div class="col-sm-12">
    <br>
    I Certify That I have Spent Rs<b> <%=ClaimRupee%> /-</b> Towards Purchase Of Newspaper(s)<br>
    for The Period Of:<b><%if(NewspaperUserPrintData!=null){out.println(NewspaperUserPrintData[0]);}%> <%if(NewspaperUserPrintData!=null){out.println(NewspaperUserPrintData[1]);}%></b>
    </div>
    
    
     <div class="col-sm-12">
     <br><br>
      Admissible Amount <b>Rs <%=AdmissibleRupee%> /-</b><br>
      Payable  Amount <b>Rs <%=PayableRupee%> /-</b>
     </div>
    
    
        
     <div class="col-sm-12">
     <br><br>
     I Further Declare That:
     i)The NewsPaper(s) In Respect of Which Reimbursement Is Claimed, Is/Are Purchased By Me.
     ii)The Amount For Which Reimbursement Is Being Claimed Has Actually Been Paid By Me Has Not/Will Not Be Claimed By Any Other Source.
     </div>


  
            <div class="col-sm-12">
            <br><br><br>
            <div class="col-sm-6 text-left">Date:</div><div class="col-sm-6 text-right">Name and Signature<br></div>
            </div>
     
        
</div>
</div>

</div>

 <input type="button"  class="btn btn-sm print-btn"  id="printPageButton" value="Print" onClick="window.print()">
</div>
  
</body>
</html>