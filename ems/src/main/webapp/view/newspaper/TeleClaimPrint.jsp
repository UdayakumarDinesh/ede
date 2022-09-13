

<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.lang.Math"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Re-imbursemrnt bill</title>
<style type="text/css">
 @media print {
  #printPageButton {
    display: none;
  }
}
  </style> 
<style>
/* css buttom border */
h1 {
    border-bottom: 2px solid black;
    width:52%;
}
h2 {
    border-bottom: 2px solid black;
    width:26%;
}
.alignleft {
	float: left;
}

table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
</style>
</head>
<body>
<%
AmountWordConveration nw=new AmountWordConveration();
List<Object[]> TelephoneUserPrintSingleData=(List)request.getAttribute("TelephoneUserPrintSingleData");
List<Object[]> TelephoneUserPrintMultiData=(List)request.getAttribute("TelephoneUserPrintMultiData");

String name="Not Available";
String designation="Not Available";
String SbiAcc="Not Available";
String OtherAcc="Not Available";
String GPFNO="Not Available";

if(request.getAttribute("TelephoneUserPrintSingleData")!=null){
    for(Object ls[]:TelephoneUserPrintSingleData){
    	name=ls[0].toString();
    	designation=ls[3].toString();
    	SbiAcc=ls[1].toString();
    	if(ls[12]!=null){
   	    OtherAcc=ls[12].toString();
    	}
   	    GPFNO=ls[2].toString();
    	
     break;  
    }}

%>
<center><input type="button"  class="btn btn-info btn-xs center-block"  id="printPageButton" value="Print" onClick="window.print()"></center>
<center><h2><font size="+1">दूरभाष  बिल क़ी प्रतिपूति॔ </font></h2></center>

<center><h1><font size="+1">RE-IMBURSEMENT OF TELEPHONE BILL</font></h1></center>
 स्थापना / Estt.: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
वायु वाहित प्रणाली केंद्र,बंगलोर / CABS, Bangalore 
<br><br>
 नाम / Name:<%=name %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;पदनाम / Designation:<%=designation%>
<br><br>
स्टेटबैंक / SBI A/c No.:<%=SbiAcc%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other A/c No.:<%=OtherAcc %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;जीपीएफ  GPF / PRAN NO.:<%=GPFNO%>
<br><br>
<table>
  <tr>
    <th rowspan="3">&nbsp;क्रमसं/<br>&nbsp;S.NO.</th>
    <th colspan="9">विवरण / Details</th>
    <th rowspan="3">देय राशि /<br>Amt Payable<br>₹</th>
    </tr>
    
    <tr>
      <td colspan="3">अवधि / Period</td>
      <td rowspan="2">दूरभाष /मोबाइलसं <br>Land Line/Mob<br>No/Broadband No.</td>
      <td rowspan="2">बिल /प्राप्तिसंख्या<br>Bill/Inv No.</td>
      <td rowspan="2">बिल तारीख<br>Bill Date</td>
       <td rowspan="2">Device<br>Bill<br>Amount</td>
      <td rowspan="2">कुल बिल <br>राशि<br>Total Bill<br>Amount</td>
      <td rowspan="2">स्वीकाय॔ राशि <br>कर सहित<br> Amount <br>admissible<br>including tax</td>
   
    </tr>
     <tr>
     <td>Broad<br>Band</td>
     <td>से<br>From</td>  
     <td>तक<br>To</td>
     
     
     </tr>
    
                    <%int count=1;
                     double TotalPayable=0.00;
                      
                     if(TelephoneUserPrintSingleData!=null&&TelephoneUserPrintSingleData.size()>0){ 
            		 for(Object ls[]:TelephoneUserPrintSingleData){
            		 String TeleId=ls[4].toString();
            		 TotalPayable=TotalPayable+Double.parseDouble(ls[9].toString());
            		 %>            		
                                   
                         <tr>
                         <td colspan="10" style="text-align:center;">Month: <%=ls[5]%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Year: <%=ls[6]%><td>
                         <tr>
                          
                          <tr>
                           <td>
                             <%for(Object ls0[]:TelephoneUserPrintMultiData){ 
                             if(TeleId.equalsIgnoreCase(ls0[0].toString())){%>
                             <%=count%><br>
                            <%count++;}}%>
                          </td>
                          
                          <td><%if("Y".equalsIgnoreCase(ls[11].toString())){%><%="Yes"%><%}else{%><%="No"%><%} %></td>
                          
                            <td>
                            <%for(Object ls1[]:TelephoneUserPrintMultiData){ 
                            if(TeleId.equalsIgnoreCase(ls1[0].toString())){%>
                            <%=DateTimeFormatUtil.fromDatabaseToActual_inNumericShortForm(ls1[1].toString())%><br>
                            <%}}%>
                            </td>
                          
                          
                          
                         
                             <td ><%for(Object ls2[]:TelephoneUserPrintMultiData){ 
                             if(TeleId.equalsIgnoreCase(ls2[0].toString())){%>
                             <%=DateTimeFormatUtil.fromDatabaseToActual_inNumericShortForm(ls2[2].toString())%><br>
                             <%}}%>
                             </td>
                         
                          <td>
                             <%for(Object ls3[]:TelephoneUserPrintMultiData){ 
                             if(TeleId.equalsIgnoreCase(ls3[0].toString())){%>
                             <%=ls3[7]%><br>
                            <%}}%>
                          </td>
                           
                         
                           <td>
                             <%for(Object ls4[]:TelephoneUserPrintMultiData){ 
                             if(TeleId.equalsIgnoreCase(ls4[0].toString())){%>
                             <%=ls4[5]%><br>
                            <%}}%>
                          </td>
                          
                            <td ><%for(Object ls5[]:TelephoneUserPrintMultiData){ 
                             if(TeleId.equalsIgnoreCase(ls5[0].toString())){%>
                             <%=DateTimeFormatUtil.fromDatabaseToActual_inNumericShortForm(ls5[6].toString())%><br>
                             <%}}%>
                             </td>
                        
                         
                           <td style="text-align:right;">
                             <%for(Object ls6[]:TelephoneUserPrintMultiData){ 
                             if(TeleId.equalsIgnoreCase(ls6[0].toString())){%>
                             <%=ls6[3]%><br>
                            <%}}%>
                            
                          </td>
                          <td style="text-align:right;"><%=ls[7]%> </td>
                          <td style="text-align:right;"><%=ls[8]%> </td>
                          <td style="text-align:right;"><%=ls[9]%> </td>
                          </tr>
    
           <%}}%>
          <%
             String total=String.valueOf(TotalPayable);
     	    
     	    String RupeeInNonFormat=String.valueOf( Math.round(Float.parseFloat(total)));     
     	    String RUPEES=IndianRupeeFormat.rupeeFormat(RupeeInNonFormat);
     	    String RupeeInWords  = nw.convert1(Integer.parseInt(RupeeInNonFormat));
                    
          %> 
           
      <tr>
      
     <td colspan="11" style="text-align:right;">Total Amount:<%=RUPEES%></td>
     
     </tr>
</table>
<br>
<b>Rupees In Words: <%=RupeeInWords %> only</b><br>
एक माह हेतु अधिकतम स्वीकाय॔ राशि ₹ 3000/- + 18% कर अथा॔त  ₹ 3540/- अथवा वास्तबिक बिल राशि जो भी कम हो ।<br><br>
Maximum admissible for reimbursement for one month is ₹ 3000/- + 18% Tax i.e ₹ 3540/- or the actual bill amount is less.<br>
मे निवेदन करता हूँ  कि प्रतिपुति॔ शीघ्र कि जाये । I request that , I may be reimbursement the admissible amount at the earliest.
<br><br><br><br>
<center>
हस्ताक्षर/Signature:<br>
नाम/Name:<br>
आंतरिक दूरभाष संख्या Internal Phone No.:
</center>
<br>
<b>प्रतिहस्ताक्षरित/COUNTERSIGNED BY</b>
</body>
</html>