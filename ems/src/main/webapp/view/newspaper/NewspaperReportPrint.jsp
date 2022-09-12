
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.text.DecimalFormat"%>

<%@page import="java.util.List"%>
<%@ page language="java"%>
<!DOCTYPE html >
<html>
<head>
<style type="text/css">
 

@media print {
.page-break{display:block; page-break-before: always;}
}

</style>


<!--  Bootstrap -->
<jsp:include page="../static/dependancy.jsp"></jsp:include>
 <style type="text/css">
 @media print {
  #printPageButton {
    display: none;
  }
  
  #btnexcel
  {
    display: none;
  }
}
 
 
 </style>   

<title>Insert title here</title>
</head>
<body>
<%
AmountWordConveration nw = new AmountWordConveration();
List<Object[]> NewspaperReportPrintData=(List)request.getAttribute("NewspaperReportPrintData");
String FromDate="Not Available";
String ToDate="Not Available";
String TotalAmountInRupee="Not Available";
String TotalAmountInPaisa="Not Available";
String TotalAmountInRupeeWords="Not Available";
if(NewspaperReportPrintData!=null&&NewspaperReportPrintData.size()>0){ 
for(Object ls[]:NewspaperReportPrintData){
FromDate=DateTimeFormatUtil.SqlToRegularDate(ls[7].toString());
ToDate=DateTimeFormatUtil.SqlToRegularDate(ls[8].toString());

String TotalAmountRsPaisA[]=new IndianRupeeFormat().getRupeePaisaSplit(ls[9].toString());
TotalAmountInRupee=IndianRupeeFormat.rupeeFormat(TotalAmountRsPaisA[0]);
TotalAmountInPaisa=TotalAmountRsPaisA[1];

TotalAmountInRupeeWords=nw.convert1(Integer.parseInt(TotalAmountRsPaisA[0].toString()));

break;
}}%>


       

       
       
        <table id="excelId" class="table table-hover table-striped  table-condensed  table-bordered ">
                              <tbody > 
                              <tr>
                              <th class="text-center" colspan="10">DEFENCE RESEARCH & DEVELOPMENT ESTABLISHMENT</th>
                              </tr>   
                              
                               <tr>
                                 <th class="text-center" colspan="10">RE-IMBURSEMENT OF INDIAN NEWS PAPERS BILL FOR THE PERIOD</th>
                                 </tr>
                                 
                                 <tr>
                                 <th class="text-center" colspan="10">FROM  <%=FromDate %> TO <%=ToDate %> IN R/O SERVICE/CIVILIAN OFFICERS OF THS ESTABLISHMENT</th>
                                 </tr>
                                
                                 <tr>
	                                 <th class="text-center">SN</th>
	                                 <th class="text-center">Name</th>
	                                 <th class="text-center">Designation</th>
	                                 <th class="text-center">Claim Month</th>
	                                 <th class="text-center">Claim Year</th>
	                                 <th class="text-center">Claim Amount</th>
	                                 <th class="text-center">Entitlement<br>(Admissible Amount)</th>
	                                 <th class="text-center">Payable Amount</th>
	                                 <th class="text-center">Remarks</th>
                                 </tr>
                                
                                  <% int count=1; 
                                     if(NewspaperReportPrintData!=null&&NewspaperReportPrintData.size()>0){ 
		                             for(Object ls[]:NewspaperReportPrintData){%>
                                 <tr>
	                                 <td class="text-center"><%=count%></td>
	                                 <td><%=ls[0]%></td>
	                                 <td><%=ls[1]%></td>
	                                 <td class="text-center"><%=ls[2]%></td>
	                                 <td class="text-center"><%=ls[3]%></td>
	                                 <td class="text-right"><%=ls[4]%></td>
	                                 <td class="text-right"><%=ls[10]%> X 6=<%=ls[11]%></td>
	                                 <td class="text-right"><%=ls[5]%></td>
	                                 <td><%=ls[6]%></td>
                                 </tr>
                                 
                               <%count++; }}%>
                                  <tr>
                                  <th colspan="7"></th>
                                  	<th style="text-align:right;" >Total Amount :&nbsp;<%=TotalAmountInRupee%>.<%=TotalAmountInPaisa%></th>
                                  	<th ></th>
                                  </tr>
                                
                                
                                </tbody>
                              
                          </table>
                     
  
    
					<div align="center">
						                       
						<input type="button" class="btn btn-sm print-btn"  id="printPageButton" value="Print PDF" onClick="window.print()">
						<input type="button" id="btnexcel" class="btn btn-sm delete-btn"  onclick="tableToExcel('excelId', 'NewsPaperReport')" value="Export to Excel">
					</div>

          
         
         <div class="page-break">
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