<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
    <%@page import="com.vts.ems.master.model.*" %>
<!DOCTYPE html>
<html>
<head>
 <!--BootStrap JS  -->
  
 
   <script src="./webjars/bootstrap/4.0.0/js/*.js"></script>
  <link rel="stylesheet" href="./webjars/bootstrap/4.0.0/css/bootstrap.min.css" />

<meta http-equiv="Content-Type" content="text/html charset=ISO-8859-1">
<style type="text/css">
body {
  border: 1px solid black;
  zoom: 70%;
}

hr {
  border: 0;
  clear:both;
  display:block;
  width: 96%;               
  background-color:black;
  height: 1px;
}
 
/* span{
color:blue;
} */
</style>
</head>
<body>
<%List<List<Object[]>> printdata1=(List<List<Object[]>>)request.getAttribute("printdata"); 
 List<LabMaster> labdetails = (List<LabMaster>)request.getAttribute("labdetails"); 
 LabMaster englabdetails=null;
 LabMaster hindilabdetails=null;
if(labdetails!=null && labdetails.size()>0){
 englabdetails=labdetails.get(0);
 hindilabdetails=labdetails.get(1);
} 
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
for(List<Object[]> printdata:printdata1){
for(Object[] obj:printdata){



	 String time1 = obj[5].toString();
	String time2 = obj[6].toString();

	SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
	Date date1 = format1.parse(time1);
	Date date2 = format1.parse(time2);
	long difference = date2.getTime() - date1.getTime(); 
	String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(difference),
            TimeUnit.MILLISECONDS.toMinutes(difference) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)),
            TimeUnit.MILLISECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));
%>

<center><p ><b><%if(labdetails!=null && hindilabdetails!=null){%><%=hindilabdetails.getLabName()%> (<%=hindilabdetails.getLabCode()%>),  <%=hindilabdetails.getLabAddress()%>,<%=hindilabdetails.getLabCity()%>-<%=hindilabdetails.getLabPin()%> <%}%></b></p></center>
<center><p ><b><%if(labdetails!=null && englabdetails!=null){%><%=englabdetails.getLabName()%> (<%=englabdetails.getLabCode()%>),  <%=englabdetails.getLabAddress()%>,<%=englabdetails.getLabCity()%>-<%=englabdetails.getLabPin()%> <%}%></b></p></center>
<center><p >टेलिफोन<b >/Tele:28381155/ 0388/ 2402/ 6807</b></p></center>
<center>सरकारी परिवहन के लिए मांगपत्र/<b>REQUISITION FOR GOVT TRANSPORT</b></b></center>

<span style="margin-left: 10px;">एकक या मागनेवाले अधिकारी का नाम/Name of Unit / Section Demanding:&nbsp; &nbsp; &nbsp;<b><%=obj[2] %></b> </span><br><br>

<div class="row" style="margin-bottom: 10px;">
<div class="col-md-4">
<span style="margin-left: 10px;">रैंक/Rank: &nbsp; &nbsp; &nbsp;<b><%=obj[1] %></b></span>
</div>
<div class="col-md-4">
<span style="margin-left: 20px;">नाम/Name:  &nbsp; &nbsp; &nbsp;<b><%=obj[0] %></b></span>
</div>

<div class="col-md-4">
<span style="margin-left: 20px;">  पदनाम/Desgn: &nbsp; &nbsp; &nbsp;<b><%=obj[1] %></b> </span>
</div>
</div>

  
  
 <div class="row" style="margin-bottom: 10px;">
<div class="col-md-4" style="margin-right: -1px;">
<span style="margin-left: 10px;">वाहन का प्रकार/Type of Vehicle: </span> <b><%=obj[3] %></b>
</div>
<div class="col-md-4">
<span style="margin-left: 0px;">आरम्भ दिनांक 
तथा समय/Start Date and Time :&nbsp;<b><%=sdf.format(  format.parse (obj[4].toString())) %>,  <%=obj[5] %>   </b> </span>
</div>

<div class="col-md-4">
<span style="margin-left: 0px;">
समाप्त दिनांक 
तथा समय/End Date and Time:&nbsp;<b><%=sdf.format(  format.parse (obj[25].toString()))%>, <%=obj[6] %></b></span>
</div>
</div> 
               
  
  <div class="row" style="margin-bottom: 10px;">
<div class="col-md-12">
<span style="margin-left: 10px;">कहाँ अपेक्षित है (आरंभिक स्थान) ।/ Where required ( Starting Place ):&nbsp; &nbsp; &nbsp;<b><%=obj[7] %></b></span>
</div>

</div> 
  
  <div class="row" style="margin-bottom: 10px;">
<div class="col-md-12">
<span style="margin-left: 10px;">गंतव्य/Destination:&nbsp; &nbsp; &nbsp; <b><%=obj[8] %></b></span>
</div>

</div> 
  
 <div class="row" style="margin-bottom: 10px;">
<div class="col-md-8">
<span style="margin-left: 10px;">एक तरफ संचार की दूरी /One way distance :&nbsp; &nbsp; &nbsp;<b><%=obj[9] %></b></span>
</div>


<div class="col-md-4">
<span style="margin-left: 20px;">  अवधि/ Duration:&nbsp; &nbsp; &nbsp;<b><%if(obj[26]!=null){%><%=obj[26].toString() %><%}else{ %>0<%} %> Days, <%=obj[27].toString() %> Hours, <%=obj[28].toString() %> Minutes </b></span>
</div>
</div> 

  <div class="row" style="margin-bottom: 10px;">
<div class="col-md-12">
<span style="margin-left: 10px;">बजट / Budget:&nbsp; &nbsp; &nbsp; <b><%=obj[24] %></b></span>
</div>

</div> 

<div class="row" >
<div class="col-md-12"><span style="margin-left: 10px;"> 
किस कारण के लिए परिवहन की आवश्यकता है। /Nature of duty for which transport required :&nbsp; &nbsp; &nbsp;<b><%=obj[10] %></b></span>
</div>

</div>
<div class="row" >
<div class="col-md-12"><span style="margin-left: 10px;"> 
Reason :&nbsp; &nbsp; &nbsp;<b><%if(obj[11]!=null){ %><%=obj[11] %><%}else{ %><b>--</b><%} %></b></span>
</div>

</div>

<div class="row" >
<div class="col-md-12"><span style="margin-left: 10px;"> 
Traveler Name and Phone Number :&nbsp; &nbsp; &nbsp;<b><%if(obj[12]!=null){ %><%=obj[12] %><%}else{ %><b>--</b><%} %></b></span>
</div>

</div>
<div class="row" style=" margin-bottom: 50px;">
<div class="col-md-12"><span style="margin-left: 10px;"> 
Mto Comments(if any) :&nbsp; &nbsp; &nbsp;<b><%if(obj[14]!=null){ %><%=obj[14] %><%}else{ %><b>--</b><%} %></b></span>
</div>

</div>



<div class="row" >
<div class="col-md-4"></div>
<div class="col-md-4"><span style="margin-left: 10px;"> 
<b><%=obj[0] %></b><br><b><%=obj[15] %></b></span>
</div>
<div class="col-md-4"><span style="margin-left: 10px;"> 
<b><%if(obj[16]!=null){ %> <%=obj[16] %><%}else{ %><b>--</b><%} %></b><br><b><%if(obj[17]!=null){ %><%=obj[17] %><%=obj[17] %><%}else{ %><b>--</b><%} %></b></span>
</div>
</div>


<div class="row" style="margin-bottom: 10px;">
<div class="col-md-4">
<span style="margin-left: 10px;">बेंगलूरु/Bangalore-13</span>
</div>
<div class="col-md-4">
<span style="margin-left: 20px;">हस्ताक्षर/ Signature :Indenter</span>
</div>

<div class="col-md-4">
<span style="margin-left: 20px;"> समुह मुख्यस्थ/Group Head</span>
</div>
</div> 

<div class="row" style="margin-bottom: 30px;">
<div class="col-md-4">
<span style="margin-left: 10px;">दिनांक/Date :&nbsp; &nbsp; &nbsp;<b><%=sdf.format(new Date()) %></b></span>
</div>
<div class="col-md-4">
<span style="margin-left: 20px;">दूरभाष/ Tel : (Int)&nbsp; &nbsp; &nbsp;<b><%=obj[21] %></b></span>
</div>

<div class="col-md-4">
<span style="margin-left: 20px;"> दूरभाष/ Tel : (Int)&nbsp; &nbsp; &nbsp;</span>
</div>
</div> 


<div class="row" style="margin-bottom: 10px;">
<div class="col-md-4"></div>
<div class="col-md-6">
<span style="margin-left: 10px;">स्वीकृत/अस्वीकृत/SANCTIONED/NOT SANCTIONED</span>
</div>

</div>

<div class="row" style="margin-bottom: 10px;">
<div class="col-md-2"></div>
<div class="col-md-10">
<span style="margin-left: 10px;">वाहन का नाम/Vehicle Name: &nbsp; &nbsp; &nbsp;<b><%=obj[3] %></b>  &nbsp; &nbsp; &nbsp;                 वाहन का नंबर/Vehicle Number:&nbsp; &nbsp; &nbsp;<b><%=obj[22] %></b>
</span>
</div>

</div>

<div class="row" style="margin-bottom: 10px;">
<div class="col-md-2"></div>
<div class="col-md-10"><span style="margin-left: 10px;">
वाहन परिचालक/Vehicle Operator:&nbsp; &nbsp; &nbsp;<b><%=obj[23] %></b></span>
</div>

</div>

<div class="row" >
<div class="col-md-8"></div>
<div class="col-md-4"><span style="margin-left: 10px;"> 
<b><%=obj[18] %></b><br><b><%=obj[19] %></b></span>
</div>

</div>



<div class="row" style="margin-bottom: 10px;">

<div class="col-md-6"></div>
<div class="col-md-6"><span style="margin-left: 10px;">
अनुबंधित अधिकारी का हस्ताक्षर /Signature of approving authority

</span>
</div>

</div>




<div class="row" style="margin-bottom: 10px;">
<div class="col-md-4"><span style="margin-left: 10px;">Trip #:&nbsp;&nbsp;<b><%=obj[20] %></b></span></div>


</div>

<div class="row" style="margin-bottom: 10px;">
<div class="col-md-4"><span style="margin-left: 10px;">स्पीडो (बाहर)/Speedo (Out)</span><br>
<span style="margin-left: 10px;">बाहर जाने का समय/Time out :	</span></div>
<div class="col-md-4"></div>
<div class="col-md-4">स्पीडो (अंदर))/Speedo (In)<br>
<span style="margin-left: 10px;">अंदर आने का समय/Time In	</span>

</div>


</div>

<div class="row" style="margin-bottom: 10px;">
<div class="col-md-4"><span style="margin-left: 10px;">कुल समय/Total Time : </span></div>


</div>


<%}}%>


</body>
</html>