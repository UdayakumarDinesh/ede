<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.util.Map"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
</style>
</head>
<body>
<%
String name="Not Available";
String designation="Not Available";
String SbiAcc="Not Available";
String OtherAcc="Not Available";
String GPFNO="Not Available";
String PayLevel="Not Available";
String BroadBand="Not Available";
String ClaimMonth="Not Available";
String ClaimYear="Not Available";
int id=0;

List<Object[]> TeleClaimEditDetails=(List<Object[]>)request.getAttribute("TeleClaimEditDetails");

if(request.getAttribute("TeleClaimEditDetails")!=null){
for(Object ls[]:TeleClaimEditDetails){

	 name=ls[18].toString();
	 designation=ls[19].toString();
	 SbiAcc=ls[22].toString();
	 if(ls[23]!=null){
	 OtherAcc=ls[23].toString();
	 }
	 id=Integer.parseInt(ls[26].toString());
	 GPFNO=ls[24].toString();
	 PayLevel=ls[25].toString();
	 BroadBand=ls[21].toString();
	 ClaimMonth=ls[8].toString();
	 ClaimYear=ls[9].toString();
	 break;
}}

%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Telephone Claim Edit</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="TelephoneDashBoard.htm">Telephone</a></li>
					<li class="breadcrumb-item "><a href="TelephoneList.htm">Telephone List</a></li>
					<li class="breadcrumb-item active " aria-current="page">Telephone Claim Edit</li>
				</ol>
			</div>
		</div>
	</div>

	<div align="center">
		<%
		String ses = (String) request.getParameter("result");
		String ses1 = (String) request.getParameter("resultfail");
		if (ses1 != null) {
		%>
		<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
			<%=ses1%>
		</div>

		<%
		}
		if (ses != null) {
		%>

		<div class="alert alert-success" role="alert" style="margin-top: 5px;">
			<%=ses%>
		</div>
		<%
		}
		%>
	</div>


	<div class="page card dashboard-card">

		<div class="card-body" >
		<form action="TeleAddEditClaimSave.htm" method="post">
		<div class="row">
             <div class="col-sm-12 text-center" >
             <br>
             <b>RE-IMBURSEMENT OF TELEPHONE BILL</b><br>
             <b>CABS,Banglore</b>
             </div>
     
     
       <div class="col-sm-12">
                  <div class="row">
	              
	               <div class="col-md-3">
                     <b>SBI A/c No:</b> <%=SbiAcc%>
                   </div>
	              
	              
	               <div class="col-md-3">
	                <b>Other A/c No:</b><%if(OtherAcc!=null){ %> <%=OtherAcc%> <%} %>
                    </div>
	               
	               <div class="col-md-3">
                     <b>GPF/PRAN No:</b><%=GPFNO%> 
                   </div>
	              
	              
	               <div class="col-md-3">
	               <b>PayLevel:</b><%=PayLevel%>
                    </div>
                    
                  </div>
	       </div>
     
    
     
                     
                     <div class="col-sm-12" >
                      <br>      
                      <table  class="table table-hover table-striped  table-condensed  table-bordered "  style="border-color: green;">
                              <thead > 
                              <tr>
                              <th style="text-align:center;" colspan="10">Details</th>
                              </tr>
                              </thead>
                                 
                                 <tbody > 
                                 <tr>
                                 <th ></th>
                                 <th colspan="2" class="text-center">Period</th>
                                 <th rowspan="2">Device</th>
                                 <th rowspan="2" class="text-center">LandLine/Mob/<br>Broadband No.</th>
                                 <th rowspan="2" class="text-center">Bill/Inv No.</th>
                                 <th rowspan="2" class="text-center">Bill Date</th>
                                 <th rowspan="2" class="text-center">BillAmount<br>(Without Tax)</th>
                                 <th rowspan="2" class="text-center">Tax<br> Amount</th>
                                 <th rowspan="2" class="text-center">Total<br> Amount</th>
                                </tr>
                                  
                                   <tr>    
                                   <th class="text-center">S.No</th>        
                                   <th class="text-center">From</th>
                                   <th class="text-center">To</th>
                                   </tr>
                                   
                                   <%int count=1;
                                      String Restricted="No Data";
                                      String TotalAmount="No Data";
                                      String TotalTax="No Data";
                                      String GrossTotal="No Data";
                                      String TeleId="0";
                                     if(request.getAttribute("TeleClaimEditDetails")!=null){
                                    	 for(Object ls[]:TeleClaimEditDetails){
                                    		
                                    		 TotalAmount=ls[1].toString();
                                    		 TotalTax=ls[2].toString();
                                    		 GrossTotal=ls[3].toString();
                                    		 Restricted=ls[4].toString();
                                    		 TeleId=ls[0].toString();
                                   
                                    		 String FromDate=DateTimeFormatUtil.fromDatabaseToActual_inNumericFormOnly(ls[6].toString());	 
                                    		 String ToDate=DateTimeFormatUtil.fromDatabaseToActual_inNumericFormOnly(ls[7].toString());
                                    		 String BillDate=DateTimeFormatUtil.fromDatabaseToActual_inNumericFormOnly(ls[12].toString());
                                    %>
                                    <tr > 
                                    
                                    <td class="text-center"><%=count%>
                                    <input type="hidden" name="TeleDId" value="<%=ls[5]%>">
                                    </td>
                                   
                                    <td class="text-center">
                                    <input type="text" name="FromDate" value="<%=FromDate%>" class="form-control input-sm currentdate" style="width: 100%;" required="required">
                                    </td>
                                    
                                    <td>
                                    <input type="text" name="ToDate" value="<%=ToDate%>" class="form-control input-sm currentdate" style="width: 100%;" required="required">
                                    </td>
                                    
                                    <td>
                                     <input type="text" value="<%=ls[17]%>" class="form-control input-sm" style="width:45px;" readonly="readonly">
                                    </td>
                                     
                                     
                                    <td>
                                    <input type="text"  value="<%=ls[16]%>" class="form-control input-sm" style="width:100px;" readonly="readonly">
                                    </td>
                                    
                                    <td>
                                    <input type="text" name="BillNo" value="<%=ls[11]%>" class="form-control input-sm " style="width:100px;" required="required" maxlength="15">
                                    </td>
                                    
                                    <td>
                                    <input type="text" name="BillDate" value="<%=BillDate%>" class="form-control input-sm currentdate" style="width: 100%;" required="required">
                                    </td>
                                    
                                    <td><input type="text" name="BasicAmount" value="<%=ls[13]%>" class="form-control input-sm amount" style="width:120px;" maxlength="6" required="required" onkeydown="crossCheck();" onclick="crossCheck();" onfocus="crossCheck();" ></td>
                                    <td><input type="text" name="TaxAmount" value="<%=ls[14]%>" class="form-control input-sm  tax" style="width:120px;" required="required" readonly="readonly" ></td>
                                    <td><input type="text" name="TotalAmount" value="<%=ls[15]%>" class="form-control input-sm total" style="width:120px;" required="required" readonly="readonly" ></td>
                                    </tr> 
                                   <%count++;}}%>
                                </tbody>
                              
                          </table>
                          
                          <div class="col-sm-12 text-right"><b>Admissible Amount:</b><%=Restricted%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Total Bill Amount:</b> <input type="text" value="<%=TotalAmount %>" name="TotalBasic" id="totalAmount" readonly="readonly" style="width:100px;" required="required">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Total Tax Amount:</b> <input type="text" name="TotalTax" value="<%=TotalTax%>" id="totalTax" readonly="readonly" style="width:100px;" required="required">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Gross Total:</b> <input type="text"   name="GrossTotal"  value="<%=GrossTotal%>"  id="grossTotal" readonly="readonly" style="width:100px;" required="required"></div>
                          
                         </div>
                         <%if(id>0){ %>
                          <div align="left" class="col-sm-12 " style="margin-top: 10px;"><b>User Remark:</b>
                           <input type="text" name="UserRemark"  class="form-control input-sm " style="margin-left:100px;width:160px;margin-top:-20px;margin-bottom: 5px; " required="required"  maxlength="255">
                         </div>
                         <%} %>
                          <input type="hidden" name="TeleId" value="<%=TeleId%>">
                          <input type="hidden" name="ForwardId" value="<%=id%>">
                         <input type="hidden" name="RestrictedAmount" value="<%=Restricted%>">
                        
                        <button  id="submit" class="btn btn-success" style="margin-left:20px;" name="EditTeleClaimSave" value="EditTeleClaimSave">submit</button>
                        <button id="check" type="button" class="btn btn-primary"  style="margin-left:20px;" onclick="myFunction()">calculate</button> &nbsp;&nbsp;
                        <button class="btn btn-info" formaction="TelephoneList.htm"  formnovalidate="formnovalidate">Back</button>
                         
                        <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
                   
      
    </div>
    </form> 
   </div>
 </div>
 


                     




<script>

$('.currentdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


</script>

<script>
$(document).ready(function(){

	$("#submit").hide();
	
});



function crossCheck()
{
	 $("#submit").hide(); $("#check").show();
	 var sp=document.getElementById("sp");
	 sp.innerHTML=" ";
}


function myFunction() {
    
    var TotalAmount=0.0;
    var TotalTax=0.0;
    var GrossTotal=0.0;
    var taxjs=[];
    
    var Amount =(document.getElementsByClassName("amount"));
    var tax=document.getElementsByClassName("tax");
    var total=document.getElementsByClassName("total");
     
    
    for(var i=0;i<Amount.length;i++)
    {
      taxjs[i]=parseFloat((Amount[i].value)*18/100);
      TotalAmount+=parseFloat(Amount[i].value);
    }
    
    
    
    for(var j=0;j<taxjs.length;j++)
    {
    tax[j].value=taxjs[j];
    TotalTax+=taxjs[j];
    }
  
   
   
    for(var k=0;k<Amount.length;k++)
    {
    total[k].value=(parseFloat(Amount[k].value)+taxjs[k]);
    }
   
    GrossTotal =TotalAmount+TotalTax;
    
   document.getElementById("totalAmount").value =TotalAmount;
   document.getElementById("totalTax").value=TotalTax;
   document.getElementById("grossTotal").value=GrossTotal;


   if(isNaN(TotalAmount)||isNaN(TotalTax)||isNaN(GrossTotal))
	   {

	     var sp=document.getElementById("sp");
		 sp.innerHTML="Please Enter Bill Amount Correctly"
		 alert("Please Enter Bill Amount Correctly");
	     $("#submit").hide(); $("#check").show();
	   }
   else{
	   $("#submit").show(); $("#check").hide();
	   }


}
</script>

</body>
</html>