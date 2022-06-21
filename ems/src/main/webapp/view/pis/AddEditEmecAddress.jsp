<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
  <%@page import="com.vts.ems.pis.model.AddressEmec"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Emergency Address </title>
<jsp:include page="../static/header.jsp"></jsp:include>


</head>
<body>
	<%
	List<Object[]> States = (List<Object[]>)request.getAttribute("States");
	Object[] empdata = (Object[])request.getAttribute("Empdata");
	AddressEmec EmecAddress =(AddressEmec)request.getAttribute("emecaddress");
	%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
			<%if(EmecAddress!=null){ %>
			         	<h5>Emergency  Address Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%>
						</b></small></h5><%}else{ %>
						<h5>Emergency  Address Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%>
						</b></small></h5><%}%>
			</div>
			   <div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Emergency Address </li>
					</ol>
				</div>
		</div>
	</div>


<div class=" page card dashboard-card"> 
	

	<div class="card-body">
		
		<div class="row">
		<div class="col-2"></div>
		
		<%if(EmecAddress!=null){ %>
		<form action="EmecEditAddressDetails.htm" method="POST" id="MyTable" autocomplete="off" enctype="multipart/form-data">
		<%}else{ %>
		<form action="EmecAddAddressDetails.htm" method="POST" id="MyTable" autocomplete="off">
		<%}%>
		<input type="hidden" id="EmerId" name="empid" value="<%=empdata[2]%>">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card"  style="width: 140%;"> 
		<div class="card-header">
		<h5>Fill Address Details</h5>
		</div>
			<div class="card-body" >
			    <div class="row">
			      <div class="col-md-12">
                 <input type="checkbox" id="EmerAddress" name="EmerAddress" value="Address" style= "transform: scale(1.5);">
		  <label style= "margin-left: 0px;">&nbsp;Check the Box if your Emergency Address is Same as Permanent Address *<span class=""></span></label>
		  </div>
                </div>
              <div class="row">
				        
				        <div class="col-md-8">
				        <div class="form-group">
		                     <label>Emergency Address:<span class="mandatory">*</span></label>
		                     <input type="text" id="emergencyadd" value="<%if(EmecAddress!=null&&EmecAddress.getEmer_addr()!=null){%><%=EmecAddress.getEmer_addr()%><%}%>" class="form-control input-sm" maxlength="4000" name="EmecAdd" required="required" placeholder="Enter Emergency Address" onclick="return trim(this)" onchange="return trim(this)"> 
		                </div>
		                </div>
		                
	         
		         
		                <div class="col-md-4">
                        <div class="form-group">
                              <label>State:<span class="mandatory">*</span></label>
                              <select  id="ddlRelationship" name="state" class="form-control input-sm " data-live-search="true">
                                      <%if(States!=null){ 
					                        for(Object[] O:States){%>
					                        <%if(EmecAddress!=null){%>
					                        <option value="<%=O[1]%>"<%if(EmecAddress.getState().equalsIgnoreCase(O[1].toString())){ %>selected  <%} %> ><%=O[1]%></option>				                        
					                        <%}else{ %>
					                        <option value="<%=O[1]%>" ><%=O[1]%></option>
					                        <%}}}%>
                              </select>
                       </div>
                       </div>	                    
			</div>
            
             
         	<div class="row">
         	
         	        <div class="col-md-4">
                    <div class="form-group">
                            <label>City:<span class="mandatory">*</span></label>
                            <input type="text" id="cityname"  name="city" class="form-control input-sm" maxlength="49"  value="<%if(EmecAddress!=null&&EmecAddress.getCity()!=null){%><%=EmecAddress.getCity()%><%}%>" placeholder="Enter City."   required="required" onclick="return trim(this)" onchange="return trim(this)">
                    </div>
                    </div> 
                         
                             
         	        <div class="col-md-4">
                    <div class="form-group">
                            <label>City PIN:<span class="mandatory">*</span></label>
                            <input id="CityPinTextBox" type="text" class="form-control input-sm "  value="<%if(EmecAddress!=null&&EmecAddress.getPin()!=null){%><%=EmecAddress.getPin()%><%}%>" name="cityPin"  required="required" maxlength="6"  placeholder="Enter PIN" onblur="checknegative(this)">
                    </div>
                    </div>
                
                     
                     <div class="col-md-4">
                     <div class="form-group">
                            <label>Mobile No.<span class="mandatory">*</span></label></label>
                            <input id="MobileTextBox" type="text" value="<%if(EmecAddress!=null&&EmecAddress.getMobile()!=null){%><%=EmecAddress.getMobile()%><%}%>" class="form-control input-sm " name="mobile" required="required" maxlength="10"  placeholder="Enter MobileNo." onblur="checknegative(this)">  
                     </div>
                     </div>                   

         	</div>
				
				
				<div class="row">
				      <div class="col-md-4">
                      <div class="form-group">
                            <label> Alt Mobile No.</label>
                            <input  id="AltMobileTextBox"  type="text" value="<%if(EmecAddress!=null&&EmecAddress.getAlt_mobile()!=null){%><%=EmecAddress.getAlt_mobile()%><%}%>" class="form-control input-sm " name="altMobile"  maxlength="10"    placeholder="Enter AltMobileNo."  onblur="checknegative(this)"/>
                       </div>
                       </div>
                       
                       
				       <div class="col-md-4">
                       <div class="form-group">
                              <label>Landline No.:</label>
                              <input  id="LandLineTextBox" type="text" value="<%if(EmecAddress!=null&&EmecAddress.getLandline()!=null){%><%=EmecAddress.getLandline()%><%}%>" class="form-control input-sm " name="landineNo"  maxlength="10"  placeholder="Enter LandlineNo"  onblur="checknegative(this)">  
                       </div>
                       </div> 
                         
                       <div class="col-md-3">
                       <div class="form-group">
                             <label>From Date: </label>
                             <%if(EmecAddress!=null&&EmecAddress.getFrom_per_addr()!=null){%>
                             <input type="text" class="form-control input-sm mydate1" value="<%if(EmecAddress!=null&&EmecAddress.getFrom_per_addr()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(EmecAddress.getFrom_per_addr().toString()) %><%}%>" name="fromPer" readonly="readonly" required="required" placeholder="Enter Date" />
                       	<%}else{%>
                       	<input type="text" class="form-control input-sm mydate" value="" name="fromPer" id = "fromdate" readonly="readonly" required="required" placeholder="Enter Date" />
                       	<%}%>
                       </div>
                       </div>
                         
				</div>
				
		</div>
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<%if(EmecAddress!=null){ %>
							<input type="hidden" name="addressId" value="<%=EmecAddress.getAddress_emer_id()%>">
				<button type="submit" class="btn btn-sm submit-btn AddItem"	 name="action" value="submit" onclick="return CommentsModel();">SUBMIT</button>
									<%}else{%>
				<button type="submit" class="btn btn-sm submit-btn"	onclick="return confirm('Are You Sure To Submit?');" name="Action" value="ADD">SUBMIT</button>
									<%}%>
									<a href="Address.htm?empid=<%if(empdata!=null){ %><%=empdata[2]%><%}%>"   class="btn btn-sm  btn-info">BACK</a>
							 </div>
							</div>
						 </div>
				</div>
				<%if(EmecAddress!=null){ %>
			<!--------------------------- container ------------------------->
			<div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">The Reason For Edit</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					       <div class="modal-body">
					             <div class="form-inline">
					        	 <div class="form-group "  >
					               <label>File : &nbsp;&nbsp;&nbsp;</label> 
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile" required="required" > 
					      		 </div>
					      		 </div>
					        	
					        	<div class="form-inline">
					        	<div class="form-group w-100">
					               <label>Comments : &nbsp;&nbsp;&nbsp;</label> 
					              <textarea  class=" form-control w-100" maxlength="1000" style="text-transform:capitalize;"  id="comments"  name="comments" required="required" ></textarea> 
					      		</div>
					      		</div>
					        </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Submit?');" name="Action" value="EDIT">SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<%} %>
					<!----------------------------- container Close ---------------------------->
	     <%if(EmecAddress!=null){%></form>
			<%}else{%></form><%}%>
		</div>
		</div>				
		</div>
</body>

<script type="text/javascript">
function CommentsModel()
{
	if(confirm('Are you sure to submit')){
		 $('#myModal').modal('show');
	}else{
		return false;
	}
}
</script>

<script type="text/javascript">
$('.mydate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('.mydate1').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
</script>
<script type="text/javascript">

setPatternFilter($("#CityPinTextBox"), /^-?\d*$/);
setPatternFilter($("#MobileTextBox"), /^-?\d*$/);
setPatternFilter($("#AltMobileTextBox"), /^-?\d*$/);
setPatternFilter($("#LandLineTextBox"), /^-?\d*$/);

function setPatternFilter(obj, pattern) {
	  setInputFilter(obj, function(value) { return pattern.test(value); });
	}

function setInputFilter(obj, inputFilter) {
	  obj.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
	    if (inputFilter(this.value)) {
	      this.oldValue = this.value;
	      this.oldSelectionStart = this.selectionStart;
	      this.oldSelectionEnd = this.selectionEnd;
	    } else if (this.hasOwnProperty("oldValue")) {
	      this.value = this.oldValue;
	      this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	    }
	  });
	}


function checknegative(str) {
    if (parseFloat(document.getElementById(str.id).value) < 0) {
        document.getElementById(str.id).value = "";
        document.getElementById(str.id).focus();
        alert('Negative Values Not allowed');
        return false;
    }
}
</script>

<script type="text/javascript">

$("#EmerAddress").on("change",function(e){
	
	alert("Are your sure You want to check");
	
	var EmpId =$("#EmerId").val();

	
	 var fields = $("input[name='EmerAddress']").serializeArray();
	
	
   
   if(fields.length==0){
	  
	   document.getElementById("MyTable").reset();
   }else{
   $.ajax({
             url:"ReqEmerAddajax.htm",
             type:"GET",
   	         data:{EmerEmpid:EmpId},
             dataType:'json',
       
             success:function(data){
            	
            	 if(data.length>0){
            	
                 $("#emergencyadd").val(data[0][2]);
                 var s =data[0][7];
                 
               
                 $('select[name="state"]').find('option[value='+s+']').attr("selected",true);
                 $("#cityname").val(data[0][8]);
                 $("#CityPinTextBox").val(data[0][9]);
                 $("#MobileTextBox").val(data[0][4]);
                 $("#AltMobileTextBox").val(data[0][5]);
                 $("#LandLineTextBox").val(data[0][6]);
                 $("#fromdate").val(data[0][3].split("-").reverse().join("-"));
            	 }else{
            		 
            		 alert('Please Add Permanent Address First');
            	 }
                 
     }

 });
   }

});
</script>
</html>