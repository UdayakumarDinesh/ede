<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.List"%>
  <%@page import="com.vts.ems.pis.model.AddressPer"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
	<%
	List<Object[]> States = (List<Object[]>)request.getAttribute("States");
	AddressPer peraddress =(AddressPer)request.getAttribute("peraddress");
	Object[] empData=(Object[])request.getAttribute("EmpData");
	%>
	
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
			<%if(peraddress!=null){ %>
				<h5>Permanent Address Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5><%}else{ %>
						<h5>Permanent Address Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5><%}%>
			</div>
			   <div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				                    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				                    <!-- <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Address</a> </li> -->
						<li class="breadcrumb-item active " aria-current="page">Permanent Address </li>
					</ol>
				</div>
		</div>
	</div>

<div class="page card dashboard-card"> 
	

	<div class="card-body">

		
		<div class="row">
		<div class="col-1"></div>
		<%if(peraddress!=null){ %>
		<form action="PermanentAddressEdit.htm" method="POST" autocomplete="off" id="myform">
		<%}else{%>
		<form action="PermanentAddressAdd.htm" method="POST" autocomplete="off" id="myform">
		<%}%>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" style="width: 140%;" > 
		<div class="card-header">
		<h5>Fill Address Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				        
				        <div class="col-md-8">
				        <div class="form-group">
		                     <label>Permanent Address:<span class="mandatory">*</span></label>
		                     <input type="text" value="<%if(peraddress!=null&&peraddress.getPer_addr()!=null){%><%=peraddress.getPer_addr()%><%}%>" class="form-control input-sm" maxlength="255" id="perAdd" name="perAdd" required="required" placeholder="Enter Permanent Address" onclick="return trim(this)" onchange="return trim(this)"> 
		                </div>
		                </div>
		                
	         
		         
		                <div class="col-md-4">
                        <div class="form-group">
                              <label>State:<span class="mandatory">*</span></label>
                              <select id="state" name="state" class="form-control input-sm selectpicker" data-live-search="true">
                                      <%if(States!=null){ 
					                        for(Object[] O:States){%>
					                        <%if(peraddress!=null){%>
					                        <option value="<%=O[1]%>"<%if(peraddress.getState().equalsIgnoreCase(O[1].toString())){ %>selected  <%} %> ><%=O[1]%></option>				                        
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
                            <input type="text"  id="city"  name="city" class="form-control input-sm" maxlength="49"  value="<%if(peraddress!=null&&peraddress.getCity()!=null){%><%=peraddress.getCity()%><%}%>" placeholder="Enter City."   required="required" onclick="return trim(this)" onchange="return trim(this)">
                    </div>
                    </div> 
                         
                             
         	        <div class="col-md-4">
                    <div class="form-group">
                            <label>City PIN:<span class="mandatory">*</span></label>
                            <input id="CityPinTextBox" type="text" class="form-control input-sm "  value="<%if(peraddress!=null&&peraddress.getPin()!=null){%><%=peraddress.getPin()%><%}%>" name="cityPin"  required="required" maxlength="6"  placeholder="Enter PIN" onblur="checknegative(this)">
                    </div>
                    </div>
                
                     
                     <div class="col-md-4">
                     <div class="form-group">
                            <label>Mobile No.<span class="mandatory">*</span></label>
                            <input id="MobileTextBox" type="text" value="<%if(peraddress!=null&&peraddress.getMobile()!=null){%><%=peraddress.getMobile()%><%}%>" class="form-control input-sm " name="mobile" required="required" maxlength="10"  placeholder="Enter MobileNo." onblur="checknegative(this)">  
                     </div>
                     </div>                   

         	</div>
					
				<div class="row">
				      <div class="col-md-4">
                      <div class="form-group">
                            <label> Alt Mobile No.</label>
                            <input  id="AltMobileTextBox"  type="text" value="<%if(peraddress!=null&&peraddress.getAlt_mobile()!=null){%><%=peraddress.getAlt_mobile()%><%}%>" class="form-control input-sm " name="altMobile"  maxlength="10"    placeholder="Enter Alt MobileNo."  onblur="checknegative(this)"/>
                       </div>
                       </div>
                       
                       
				       <div class="col-md-4">
                       <div class="form-group">
                              <label>Landline No.:</label>
                              <input  id="LandLineTextBox" type="text" value="<%if(peraddress!=null&&peraddress.getLandline()!=null){%><%=peraddress.getLandline()%><%}%>" class="form-control input-sm " name="landineNo"  maxlength="10"  placeholder="Enter Landline No"  onblur="checknegative(this)">  
                       </div>
                       </div> 
                         
                       <div class="col-md-3">
                       <div class="form-group">
                             <label>From Date: </label>
                             <%if(peraddress!=null&&peraddress.getFrom_per_addr()!=null){%>
                             <input type="text" class="form-control input-sm mydate1" value="<%if(peraddress!=null&&peraddress.getFrom_per_addr()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(peraddress.getFrom_per_addr().toString()) %><%}%>" name="fromPer" readonly="readonly" required="required" placeholder="Enter Date" />
                       	<%}else{%>
                       	<input type="text" class="form-control input-sm mydate" value="" name="fromPer" readonly="readonly" required="required" placeholder="Enter Date" />
                       	<%}%>
                       </div>
                       </div>
                         
				</div>
				
		</div>
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<%if(peraddress!=null){ %>
							<input type="hidden" name="addressId" value="<%=peraddress.getAddress_per_id()%>">
				<button type="submit" class="btn btn-sm submit-btn AddItem"	 name="Action" value="EDIT" onclick="return CommentsModel();">SUBMIT</button>
									<%}else{%>
				<button type="submit" class="btn btn-sm submit-btn"	onclick="return CommentsModel();" name="Action" value="ADD">SUBMIT</button>
									<%}%>						
							 </div>
							</div>
						 </div>			 
						</div>								
			<%if(peraddress!=null){ %>
			</form>
			<%}else{ %>
			</form>
			<%}%>	
			
		</div>
		</div>				
		</div>
</body>
<script type="text/javascript">
function CommentsModel()
{
	  var PerAdd =$("#perAdd").val();
	  var State =$("#state").val();
	  var City =$("#city").val();
	  var CityPin= $("#CityPinTextBox").val();
	  var Mobile = $("#MobileTextBox").val();
	  var AltMobile = $("#AltMobileTextBox").val();
	  var LandLine = $("#LandLineTextBox").val();
	  
	  if(confirm('Are You Sure to Submit?')){
	  
	  if(PerAdd==null || PerAdd=='' || PerAdd=="null" ){
			alert('Enter the Permanent Address!');
			return false;
		}else if(State==null || State=='' || State=="null" ){
			alert('Please Select the State!');
			return false;
		}else if(City==null || City=='' || City=="null" ){
			alert('Enter the City Name!');
			return false;
		}else if(CityPin==null || CityPin=='' || CityPin=="null" ){
			alert('Enter the City Pin!');
			return false;
		}else if(Mobile==null || Mobile=='' || Mobile=="null" ){
			alert('Enter the Mobile Number!');
			return false;
		}/*else if(AltMobile==null || AltMobile=='' || AltMobile=="null" ){
			alert('Enter the Alt Mobile Number!');
			return false;
		}else if(LandLine==null || LandLine=='' || LandLine=="null" ){
			alert('Enter the LandLine Number!');
			return false;
		} else{
			$('#myModal').modal('show');
		} */else{
			$('#myform').submit();
			return true;
		}
	  event.preventDefault;
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
</html>