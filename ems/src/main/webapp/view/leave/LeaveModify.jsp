<%@page import="com.vts.userdefinedclasses.GetDateAndTime"%>
<%@ page language="java" %>
<!DOCTYPE html >
<%@page import="java.util.*,java.text.SimpleDateFormat"%>
<html>
<head>


<style type="text/css">

.table thead tr th{vertical-align:middle; text-align: center;  font-size: 12px; white-space: nowrap;}
.table tbody tr td{vertical-align:middle; text-align: center; font-size: 12px;}

</style>





</head>
<body class="hold-transition skin-blue sidebar-mini">



<%
Object[] leav=(Object[])request.getAttribute("detailleave");
Object[] modifyleavedetail=(Object[])request.getAttribute("modifyleavedata");
List<Object[]> holidayList=(List<Object[]>)request.getAttribute("holidayList");
List<Object[]> upcomingHolidayList=(List<Object[]>)request.getAttribute("upcomingHolidayList");
%>

<%-- 
EMPID::<%=modifyleavedetail[0]%><BR>
PUR LEAVE::<%=modifyleavedetail[1]%><BR>
LEAVE ADDRESS::<%=editleavedetail[2]%><BR>
START DATE::<%=editleavedetail[3]%><BR>
END DATE::<%=editleavedetail[4]%><BR>
TOTALDAYS::<%=editleavedetail[5]%><BR>
DATE APPLIED::<%=editleavedetail[6]%><BR>
LEAVE CODE::<%=editleavedetail[7]%><BR>
STATUS::<%=editleavedetail[8]%><BR>
FNAN::<%=editleavedetail[9]%><BR>
APPLID::<%=editleavedetail[10]%><BR>
TYPE OF LEAVE::<%=editleavedetail[11]%><BR>
REMARK::<%=editleavedetail[12]%><BR>
LTC::<%=editleavedetail[13]%>--%>

<div class="wrapper">
         <%@ include file="/WEB-INF/pages/Static/MainHeader.jsp"%>
        
        
        <div class="content-wrapper">
       
          <section class="content-header">
			<h1>Leave  Modify</h1>
			<ol class="breadcrumb">
				<li><a href="DashBoard.htm"><i class="fa fa-home"></i> Home</a></li>
				<li><a href="LeaveDashBoard.htm"><i class="fa fa-dashboard"></i>Leave DashBoard</a></li>
				<li class="active">Leave  Modify</li>
			</ol>
		  </section> 
		
<section class ="content">
<div class="row" style="margin-left: 20px;">	
	
	<div class="row br-f7"><br/>
	    <div class="col-md-1"></div>
	    <div class="col-md-4 col-xs-12">
	        <div id="clb" class="panel panel-default">
	            <div class="panel-heading">
	                <span class="h4">Current Leave Balance</span>
	            </div>
	            <div class="panel-body p-015">
	               <table class=" table table-bordered table-hover table-striped table-condensed">
	                    <thead>
	                        <tr>
	                            <th>CL</th>
	                            <th>EL</th>
	                            <th>HPL/CML</th>
	                            <th>RH</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <%if(leav!=null){%>
	                            <%int hpl=Integer.parseInt(leav[16].toString());
	                             int  cml=hpl/2;
	                             int  el=Integer.parseInt(leav[15].toString());
	                             int  elToBeLapse=Integer.parseInt(leav[21].toString());
	                            %>
	                         <tr>
	                            <td><%=leav[14] %></td>
	                            <td><%=el+elToBeLapse%></td>
	                            <td><%=hpl%>/<%=cml%></td>
	                            <td><%=leav[17] %></td>    
	                        </tr>
	                       <%}%>
	                    </tbody>
	                </table>
	            </div>
	        </div><!-- / current-leave-balance-->
	        
	        
	        
	        
	        <!--Holidays-->
	        <div class="panel panel-default">
	            <div class="panel-heading">
	                <span class="h4">Important Dates</span>
	            </div>
	            <div class="panel-body p-015">
	                <div class="">
	                    <ul class="nav nav-tabs text-center">
	                        <li class="active"><a data-toggle="tab" href="#upcoming">Upcoming</a></li>
	                        <li><a data-toggle="tab" href="#general">General</a></li>
	                        <li><a data-toggle="tab" href="#restricted">Restricted</a></li>
	                        <!-- <li><a data-toggle="tab" href="#working">Working</a></li> -->
	                    </ul>
	
	                    <div class="tab-content">
	                   <!-- upcoming holidays -->
	                    <div id="upcoming" class="tab-pane fade in active  pre-scrollable">
	                        <table class="table table-bordered table-hover table-striped table-condensed tabText">
	                            <thead>
	                            <tr>
	                                  <th>Date</th>
	                                  <th  style=" text-align: left;" >Event</th> 
	                                    
	                            </tr>
	                            </thead>
	                            <tbody>
	                         <%for(Object[] ls:upcomingHolidayList){
	                         %>
	                          <tr>
	                          <td><%=ls[0]%></td>
	                          <td  style=" text-align: left;"><%=ls[1]%></td>
	                          </tr>
	                          <%}%>
	                        </tbody>
	                        </table>
	                    </div>
	                   
	                   <!-- general holidays -->
	                    <div id="general" class="tab-pane fade pre-scrollable">
	                     <table class="table table-bordered table-hover table-striped table-condensed tabText">
	                            <thead>
	                            <tr>
	                                  <th>Date</th>
	                                  <th  style=" text-align: left;">Event</th> 
	                                    
	                            </tr>
	                            </thead>
	                            <tbody>
	                         <%for(Object[] ls:holidayList){
	                         if("G".equalsIgnoreCase(ls[2].toString())){
	                         %>
	                          <tr>
	                          <td><%if(ls[0]!=null){out.println(GetDateAndTime.fromDatabaseToActual_inNumericShortForm(ls[0].toString()));}%></td>
	                          <td  style=" text-align: left;"><%=ls[1]%></td>
	                          </tr>
	                          <%}%>
	                          <%}%>
	                        </tbody>
	                        </table>
	                     </div>
	                    
	                    <!-- restricted holidays -->
	                    <div id="restricted" class="tab-pane fade pre-scrollable">
	                    <table class="table table-bordered table-hover table-striped table-condensed tabText">
	                            <thead>
	                            <tr>
	                                  <th>Date</th>
	                                  <th  style=" text-align: left;">Event</th> 
	                                    
	                            </tr>
	                            </thead>
	                            <tbody>
	                         <%for(Object[] ls:holidayList){
	                         if("R".equalsIgnoreCase(ls[2].toString())){ 
	                         %>
	                          <tr>
	                          <td><%if(ls[0]!=null){out.println(GetDateAndTime.fromDatabaseToActual_inNumericShortForm(ls[0].toString()));}%></td>
	                          <td  style=" text-align: left;"><%=ls[1]%></td>
	                          </tr>
	                          <%}%>
	                          <%}%>
	                        </tbody>
	                        </table>
	                    </div>
	                    
	                    
	                    
	                 </div><!-- <div id="working" class="tab-pane fade in active"></div> -->
	                </div>    
	            </div>
	        </div>
	        <!--Holidays-->
	    </div><!-- / .left-side-panel-->
	    
	    
	    
	    <!-- display message -->
	    <span id="sp"></span>
	    
	    
	    
	 
	    <!-- Leave Application-->
	    <div class="col-md-6 col-xs-12">
	        <div class="panel panel-default">
	            <div class="panel-heading">
	                <span class="h4">Modify Leave Application</span>
	            </div>
	            <div class="panel-body">
	                <form action="apply-leave-modify.htm" method="post">
	                    
	                    
	                    <!-- Leave Type-->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-xs-4"><label for="leaveType">Leave Type:</label></div>
	                            
	                             <div class="col-xs-4">
	                                <select id="leaveType"  name="leavetypecode" class="form-control input-sm" onChange="leavecheck()">
	                                 
	                                
	                               <option value="<%=modifyleavedetail[7]%>"><%=modifyleavedetail[11]%> </option>  
	                                
	                                 
	                                </select>
	                                </div>
	                            
	                            
	                            
	                            <div id="fullhalfdiv"  class="col-xs-4">
	                                <select  id="halforfull" name="fullhalf" class="form-control input-sm" onChange="halffull()">
	                                    
	                                    <%String anfnandx=(String)modifyleavedetail[9]; 
	                                    if("A".equalsIgnoreCase(anfnandx)||"F".equalsIgnoreCase(anfnandx)){%>
	                                    <option value="H" >Half Day</option>
	                                    <% }else{%>
	                                    
	                                     <option value="X" >Full Day</option>
	                                    <%} %>  
	                                   
	                                     <option value="X" >Full Day</option>
	                                     <option value="H" >Half Day</option>
	                                </select>
	                            </div>
	                       
	                           
	                       
	                       
	                       
	                       
	                       
	                        </div>
	                    </div>
	                    <!-- / .leaveType-->
	                    
	                    
	                    
	                    
	                    
	                    <!-- EL Encashment -->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-xs-4">
	                                <label for="from">LTC Leave</label>
	                            </div>
	                            <div class="col-xs-4">
	                                <select id="Elcash" name="elcash" class="form-control input-sm">
	                                    <%String elcash=(String)modifyleavedetail[13] ;
	                                      String elcashYES="Y",LTCApplied="A";
	                                    if(elcashYES.equalsIgnoreCase(elcash)||LTCApplied.equalsIgnoreCase(elcash)){%>
	                                    <option value="Y">Yes</option>
	                                    
	                                    
	                                  <% }else{%>  
	                                     <option value="N">No</option>
	                                    
	                                    <% }%>
	                                    
	                                    
	                                </select>
	                               </div>   
	                       
	                       
	                       
	                       
	                        <div  id="fnandiv" class="col-xs-4">
	                                <select  id="anorfn" name="anfn"  class="form-control input-sm" onChange="halffull()">
	                                   <%  String fn="F";
	                                       String an="A";
	                                      if(fn.equalsIgnoreCase(anfnandx)){%>
	                                    <option value="F">FN</option>
	                                   
	                                    <%}if(an.equalsIgnoreCase(anfnandx)){ %>
	                                    <option value="A">AN</option>
	                                    <%} %>
	                                   
	                                    <option value="F">FN</option>
	                                    <option value="A">AN</option>
	                                    
	                                </select>
	                            </div>
	                       
	                       </div>
	                       
	                    </div>
	                    <!--ElEncashment  --> 
	                    
	                   
	                     <%  
	                        String Pfromdate= GetDateAndTime.fromDatabaseToActual_inNumericFormOnly(modifyleavedetail[3].toString()); 
	                         String Ptodate=GetDateAndTime.fromDatabaseToActual_inNumericFormOnly(modifyleavedetail[4].toString());%>
	                   
	                   
	                    
	                    <!-- date from to -->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-xs-4">
	                                <label for="fromApplyDate"> Date:</label>
	                            </div>
	                            
	                               
	                            
	                           <div id="fromdiv" class="col-xs-4">
	                            <b><span id="spanfrom"></span></b>  <input id="fromApplyDate" type="text" class="form-control input-sm from" placeholder="from" onChange="fromDatefun()" name="startdate"  value="<%=Pfromdate%>"  maxlength="10">
	                            </div>
	                            <div  id="todiv" class="col-xs-4">
	                           <b><span id="spanto"></span></b>  <input id="toApplyDate"  type="text" class="form-control input-sm"  onchange="toDatefun()" name="enddate"   value="<%=Ptodate%>"  maxlength="10">
	                            </div>
	                           
	                        
	                        
	                        
	                        </div>
	                    </div>
	                     <!--/  date from to  -->
	                  
	                  
	                  
	                  <!-- Purpose of leave -->
	                    <div   class="form-group">
	                        <div class="row">
	                            <div class="col-xs-4">
	                                <label for="from">Purpose:</label>
	                            </div>
	                            <div class="col-xs-8">
	                                <select class="form-control input-sm" name="leavepurpose">
	                                   
	                                   <option value="<%=modifyleavedetail[1]%>"><%=modifyleavedetail[1]%></option> 
	                                   
	                                </select>    
	                            </div>
	                        </div>    
	                    </div>
	                    <!-- /. Purpose of leave:-->
	                    
	                    
	                    
	                    <!-- Leave address -->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-xs-4">
	                                <label for="from">Leave address:</label>
	                            </div> 
	                            <div class="col-xs-8">
	                                <textarea class="form-control"   name="leaveadd" required><%=modifyleavedetail[2]%></textarea>
	                            </div>
	                        </div>
	                    </div>
	                    <!-- / .leaveAddress-->
	                    
	                    <!-- Remarks -->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-xs-4">
	                                <label for="from">Remarks:</label>
	                            </div>
	                            <div class="col-xs-8">
	                                <input id="from" class="form-control input-sm" type="text" value="<%=modifyleavedetail[12]%>" name="remark" >
	                            </div>
	                        </div>
	                    </div>
	                    <!-- / Remarks -->
	              
	              
	               
	                  
	                          <!-- hidden applid -->
	                        <input type="hidden"  id="modifyApplidCheck" name="applidModifyTime" value="<%=modifyleavedetail[10]%>">  
	                          
	                          <!-- /hidden applid -->
	                    
	                    
	                    
	                    <!--   apply and check button -->
	                    <div class="form-group">
	                    	<div class="row">
	                    		
	                    		<div class="col-xs-4">
	                    			 <label for="from">Action:</label>
	                    		</div>
	                    		
	                    		<div id="submit" class="col-xs-8">
	                    			<button  type="submit" class="btn btn-success btn-block" name="ModifyLeaveSave" value="ModifyLeaveSave">Apply leave</button>
	                    		</div>
	                    		
	                    		<div id="check" class="col-xs-8">
	                    			<button  type="button" class="btn btn-primary btn-block" onclick="show()">Check leave</button>
	                    		</div>
	                    		
	                    	</div>
	                    </div> 
	                <!-- //  apply and check button -->


</form>
</div>

</div>
</div>
<div class="col-md-1"></div>
</div>

<marquee><b style="color:brown;">Don't Click Apply Leave Button , If You Are Not Changing Anything</b></marquee>

</div>
</section>	
</div><!-- content-wrapper  -->

</div><!--  wrapper-->

	

<script src="${pageContext.request.contextPath}/stresource/dist/js/app.min.js"></script>
<script type="text/javascript">


var anfn="X";


$(document).ready(function(){
	$("#submit").hide();
	
	
	
	var spanfrom=document.getElementById("spanfrom");
	var spanto=document.getElementById("spanto");
	spanfrom.innerHTML ="From";
	spanto.innerHTML ="To";
	
	
	
	var leavetype=document.getElementById("leaveType").value;
	 
	 if(leavetype!="0001")
	 { 
		 $("#fullhalfdiv").hide();
         $("#fnandiv").hide();
      
	 }
	
	 var halforfull=document.getElementById("halforfull").value; 
	   
	 if("X"==halforfull)
		 {
		 $("#fnandiv").children().prop('disabled',true); // fn/an  dropdown disabled
		 }
	 
	 
	 if(halforfull=="H"&&leavetype=="0001")
		 {
		
		 $("#fnandiv").children().prop('disabled',false);// fn/an dropdown enabled
			$("#todiv").hide();
			 var spanfrom=document.getElementById("spanfrom");
			 spanfrom.innerHTML ="On";
			 
			
			var anorfn=document.getElementById("anorfn").value;
			
			if(anorfn=="F")
				{
				anfn="F";
				}
			else{anfn="A";}	
	    	
	    	
		 }
	 
});










function fromDatefun()
{
	
	$("#submit").hide(); //hide submit button (after check not allow to submit if some changes)
	$("#check").show();
	
    var sp=document.getElementById("sp");
    sp.innerHTML="     ";
} 



function toDatefun()
{
	$("#submit").hide(); //hide submit button (after check not allow to submit if some changes)
	$("#check").show();
	
    var sp=document.getElementById("sp");
    sp.innerHTML="     ";


}





function halffull()
{
	$("#submit").hide(); //hide submit button (after check not allow to submit if some changes)
	$("#check").show();
	
	
	 var sp=document.getElementById("sp");
     sp.innerHTML="     ";
	 
	 
	 
	 var halforfull=document.getElementById("halforfull").value;
	 var leavetype=document.getElementById("leaveType").value;
	 
	 if(halforfull=="X")
	{
	$("#fnandiv").children().prop('disabled',true); //fn/an dropdown disabled
	$("#todiv").show();//fromd date enabled
	
	 var spanfrom=document.getElementById("spanfrom");
	 spanfrom.innerHTML ="From";
	 
	anfn="X";
	
	}
	
	 if(halforfull=="H"&&leavetype=="0001")
	{
		$("#fnandiv").children().prop('disabled',false);// fn/an dropdown enabled
		$("#todiv").hide();//fromd date disabled
		 var spanfrom=document.getElementById("spanfrom");
		 spanfrom.innerHTML ="On";
		 
		
		var anorfn=document.getElementById("anorfn").value;
		
		if(anorfn=="F")
			{
			anfn="F";
			}
		else{anfn="A";}	
			
			
	}
	
}





var ajaxreq=new XMLHttpRequest();
function show()
{
	var leavetype=document.getElementById("leaveType").value;
	//var anfn=document.getElementById("anfn").value;
	var ELCash=document.getElementById("Elcash").value;
	var fdate=document.getElementById("fromApplyDate").value;
	var tdate=document.getElementById("toApplyDate").value;
	var modifyApplidCheck=document.getElementById("modifyApplidCheck").value;
	
	$("#submit").hide();
	var sp=document.getElementById("sp");
    sp.innerHTML="     ";
	
	ajaxreq.onreadystatechange=process;
	//ajaxreq.open("get","LeaveController?lv="+leavetype+"&fd="+fdate+"&td="+tdate+"&anfn="+anfn+"&Elcash="+ELCash+"&modifyApplidCheck="+modifyApplidCheck,true);
	//ajaxreq.send(null);

	
	
	ajaxreq.open("post", "check-leave.htm", true);
	ajaxreq.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    ajaxreq.send("lv="+leavetype+"&fd="+fdate+"&td="+tdate+"&anfn="+anfn+"&Elcash="+ELCash+"&modifyApplidCheck="+modifyApplidCheck);
	

	
}


function process()
{
	if(ajaxreq.readyState==4)
		{
		 var sp=document.getElementById("sp");
		 var result=ajaxreq.responseText;
		 sp.innerHTML=result;
				
			
				 var applymessege="You Can Apply";
			     var n = result.search(applymessege);
			 
			     if(n>=0){$("#submit").show(); $("#check").hide();}
			
		}
	
	
}

</script>		
<script src="vtsfolder/js/leave.js"></script>

</body>
</html>