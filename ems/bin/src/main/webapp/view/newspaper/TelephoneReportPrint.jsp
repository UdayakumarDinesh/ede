
<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.text.DecimalFormat"%>

<%@page import="java.util.List"%>

<jsp:include page="../static/dependancy.jsp"></jsp:include>
<%@ page language="java"%>
<!DOCTYPE html >
<html>
<head>
<!--  Bootstrap -->
  <style type="text/css">
 @media print {
  #printPageButton {
    display: none;
  }
  
  #btnexcel{
    display: none;
  }
  
  
}
 
 
 </style>   
<title>Telephone Report</title>
</head>
<body>

<%
AmountWordConveration nw=new AmountWordConveration();
String FromDate="Not Available";
String ToDate="Not Available";
double FinalAmount=0.0;
List<Object[]> TelephonePrintReportSingleData=(List)request.getAttribute("TelephonePrintReportSingleData");
List<Object[]> TelephonePrintReportMultiData=(List)request.getAttribute("TelephonePrintReportMultiData");
if(TelephonePrintReportSingleData!=null&&TelephonePrintReportSingleData.size()>0){ 
for(Object ls[]:TelephonePrintReportSingleData){
	 
	FromDate=DateTimeFormatUtil.fromDatabaseToActualInPeriodForm(ls[10].toString());
	ToDate=DateTimeFormatUtil.fromDatabaseToActualInPeriodForm(ls[11].toString());
	
	
	FinalAmount=Double.parseDouble(ls[12].toString());
	break;
}}

 String total=String.valueOf(FinalAmount);

  String RupeeInNonFormat=String.valueOf( Math.round(Float.parseFloat(total)));
 String RUPEES=IndianRupeeFormat.rupeeFormat(RupeeInNonFormat);
 String RupeeInWords  = nw.convert1(Integer.parseInt(RupeeInNonFormat));
int value=0;

LabMaster LabDetails =(LabMaster)request.getAttribute("LabDetails");
%>



        
        <table  id="excelId"  class="table table-hover table-striped  table-condensed  table-bordered ">
         <caption><div class="text-center"><b>CONSOLIDATED LIST FOR RE-IMBURSEMENT OF TELEPHONE/MOBILE PHONES</b></div><div class="text-center"><small><%=LabDetails.getLabCode() %>/Misc/CB- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  / Dated <b> From <%=FromDate%> to <%=ToDate%> </b></small></div></caption><colgroup align="center"></colgroup><colgroup align="left"></colgroup><colgroup span="2" align="center"></colgroup><colgroup span="3" align="center"></colgroup><thead valign="top">
        <thead>
        <tr>
        <th style="text-align:center;width:1% !important;">S.NO.</th>
        <th>Name &amp; Initial GPF/Sr No.</th>
        <th>SBI A/C No.</th>
        <th>Phone/Mob No.</th>
        <th>Broad<br>Band</th>
        <th>Month &amp; Year</th>
        <th>Period of Claim</th>
        <th>Bill<br> Amount</th>
        <th>Claim<br> Amount</th>
        <th>Admissible<br> Amount</th>
        <th>Payable<br> Amount</th>
        <th>Sub <br> Total</th>
        <th >Remark</th>
        </tr>
        </thead>
        <tbody>
        
         <%int count=1;
         int countemp=1;
         int count1=1;
         DecimalFormat df=new DecimalFormat(".00");
         String temp=null;
         double Amount=0.00;
         if(TelephonePrintReportSingleData!=null&&TelephonePrintReportSingleData.size()>0){ 
		 for(Object ls[]:TelephonePrintReportSingleData){
		 String TeleId=ls[5].toString();
		 %>
          <%
          if(temp==null){
          temp=ls[0].toString();
          }
        if(!temp.equalsIgnoreCase(ls[0].toString())){ 
        
        %>
        <tr>
	        <td colspan="11"></td>
	        <td style="text-align:right;"><% value+= Math.round(Amount);%><%=Math.round(Amount)%></b></td>
	        <td></td>
        </tr>
        <%Amount=0.00;temp=ls[0].toString();count1=count;countemp++;}
        Amount+=Double.parseDouble(ls[8].toString());
        %>                     
        <%if(count==count1){
        	%>
        <tr>
        <td style="text-align:center;width:1%;"><%=countemp%></td>
        <td style="text-align:left;width:20%;"><%=ls[0]%>-<%=ls[4]%><br><%=ls[1]%></td>
        <%}else{ %>
        <td style="text-align:center;"> </td>
        <td> </td>
        <%} %>
        <td><%=ls[2]%></td>
        
        <td>
        <%for(Object ls1[]:TelephonePrintReportMultiData){ 
         if(TeleId.equalsIgnoreCase(ls1[0].toString())){%>
        <%=ls1[7]%><br>
        <%}}%>
        </td>
        
        <td><%if("Y".equalsIgnoreCase(ls[6].toString())){%><%="Yes"%><%}else{%><%="No"%><%} %></td>
        
        <td><%=ls[13]%>-<%=ls[14]%></td>
        
         <td style="width:130px;"><%for(Object ls3[]:TelephonePrintReportMultiData){ 
         if(TeleId.equalsIgnoreCase(ls3[0].toString())){%>
         <%=DateTimeFormatUtil.fromDatabaseToActual_inNumericShortForm(ls3[1].toString())%>-<%=DateTimeFormatUtil.fromDatabaseToActual_inNumericShortForm(ls3[2].toString())%><br>
         <%}}%></td>
        
        <td style="text-align:right;"><%for(Object ls4[]:TelephonePrintReportMultiData){ 
         if(TeleId.equalsIgnoreCase(ls4[0].toString())){%>
         <%=ls4[5]%><br>
         <%}}%></td>
        
        <td style="text-align:right;"><%=ls[7]%></td>
        <td style="text-align:right;"><%=ls[15]%></td>
        <td style="text-align:right;"><%=ls[8]%></td>
        <td ></td>
        <td ><%=ls[9]%></td>
        </tr>
        
        <%count++;}}%>
        <tr>
        <td colspan="11"></td>
        <td style="text-align:right;"><% value+= Math.round(Amount);%><%=Math.round(Amount)%></b></td>
        <td></td>
        </tr>
        
        <tr>
        <td colspan="11"></td>
        <td style="text-align:right;"><b>Total<br>=<%=value%></b> </td>
        <td></td>
        </tr>
        
        <tr><td colspan="13"><b>Total:<%=value%> /-</b> </td></tr>
        <tr><td colspan="13"><div><p><b>Net amount (in words): (Rupees <%= nw.convert1(value)%> only)</b></p></div></td></tr>
        </tbody>
        
        	
  
      </table>


<div align="center">
	<input type="button" class="btn btn-sm print-btn"  id="printPageButton" value="Print PDF" onClick="window.print()">
	<input type="button" id="btnexcel" class="btn btn-sm btn-danger"  onclick="tableToExcel('excelId', 'TelephoneReport')" value="Export to Excel">
</div>
	
 
 
<script type="text/javascript">

var tableToExcel = (function() {
	  var uri = 'data:application/vnd.ms-excel;base64,'
	    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
	    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
	  return function(table, name) {
		 
	    if (!table.nodeType) table = document.getElementById(table)
	    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
	    window.location.href = uri + base64(format(template, ctx))
	  } 
	})()

</script>



</body>
</html>
