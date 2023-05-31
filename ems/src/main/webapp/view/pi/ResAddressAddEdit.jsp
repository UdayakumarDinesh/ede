<%@page import="com.vts.ems.pis.model.AddressRes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="java.util.List"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<script type="text/javascript">
 function trim (el) {
	    el.value = el.value.
	       replace (/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
	       replace (/[ ]{2,}/gi," ").       // replaces multiple spaces with one space 
	       replace (/\n +/,"\n");           // Removes spaces after newlines
	    return;
	}
 </script>
 <style type="text/css">

 </style>
</head>
<body>
<%
List<Object[]> States = (List<Object[]>)request.getAttribute("States");
Object[] empData=(Object[])request.getAttribute("EmpData");
AddressRes addres = (AddressRes)request.getAttribute("addres"); 
%>

<div class="card-header page-top">
					<div class="row">
						<div class="col-md-6">
						<%if(addres!=null){ %>
							<h5>Residential Address Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5><%}else{ %>
						<h5>Residential Address Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5><%}%>
						</div>
						   <div class="col-md-6">
								<ol class="breadcrumb ">
									<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				                    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				                    <!-- <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Address</a> </li> -->
									<li class="breadcrumb-item active " aria-current="page">Residential Address </li>
								</ol>
							</div>
					</div>
				</div>

<div class=" page card dashboard-card"> 

				<div class="card-body" align="center">
		
		<div class="row">
		<div class="col-2"></div>
		<%if(addres!=null){ %>
				<form action="ResidentialAddressEdit.htm" method="POST" autocomplete="off" id="myform">
		<%}else{ %>
				<form action="ResidentialAddressAdd.htm" method="POST" autocomplete="off" id="myform">
		<%}%>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card"  style="width: 80%;" > 
		<div class="card-header" align="left">
		<h5>Fill Address Details</h5>
		</div>
			<div class="card-body" align="left">
			 
          <div class="row">   
                     <div class="col-md-6">
		                <div class="form-group">
		                	<label>Residential  Address <span class="mandatory">*</span></label>
		                    <input type="text"  value="<%if(addres!=null&&addres.getRes_addr()!=null){%> <%=addres.getRes_addr()%><%}%>" class="form-control input-sm" maxlength="4000" id="resAdd" name="resAdd" required="required" placeholder="Enter Residential  Address" onclick="return trim(this)" onchange="return trim(this)">
		                </div>
		                </div>
         
                   <div class="col-md-3">
                        <div class="form-group">
                          <label>State<span class="mandatory">*</span></label>
                           <select id="state" name="state" class="form-control input-sm selectpicker" data-live-search="true" required>
                                <option disabled="disabled" value="" selected="selected"> Select</option>
								<%if(States!=null){ 
								for (Object[] O : States) {%>
								<%if(addres!=null){ %>
								<option value="<%=O[1]%>"   <%if(addres.getState()!=null && addres.getState().equalsIgnoreCase(O[1].toString())){%> selected<%}%>><%=O[1]%></option>
								<%}else{%>
								<option value="<%=O[1]%>"   ><%=O[1]%></option>
								<%}}}%>
							</select>
                       </div>
                    </div>	 
                               
		            <div class="col-md-3">
                        <div class="form-group">
                            <label>City<span class="mandatory">*</span></label>
                            <input type="text"  id="city" name="city" class="form-control input-sm" maxlength="49"  value="<%if(addres!=null&&addres.getCity()!=null){%> <%=addres.getCity()%> <%}%>"    required="required" onclick="return trim(this)" onchange="return trim(this)">
                        </div>
                      </div>
                      
               </div>
            
                
         	  <div class="row">
                         <div class="col-md-2">
                        <div class="form-group">
                            <label>City PIN <span class="mandatory">*</span></label>
                            <input id="CityPinTextBox" type="text" class="form-control input-sm "  value="<%if(addres!=null&&addres.getPin()!=null){%> <%=addres.getPin()%> <%}%>" name="cityPin"  required="required" maxlength="6"  placeholder="Enter PIN" onblur="checknegative(this)">
                        </div>
                    </div>
                        
                         <div class="col-md-2">
                        <div class="form-group">
                         <label>Mobile No.<span class="mandatory">*</span></label>
                           <input id="MobileTextBox" type="text" value="<%if(addres!=null&&addres.getMobile()!=null){%> <%=addres.getMobile()%> <%}%>" class="form-control input-sm " name="mobile"  maxlength="10"  placeholder="Enter Mobile No. " onblur="checknegative(this)" >  
                        </div>
                        </div>
                         
                        
                           <div class="col-md-2">
                           <div class="form-group">
                            <label> Alt Mobile No.</label>
                            <input id="AltMobileTextBox" type="text" value="<%if(addres!=null&&addres.getAlt_mobile()!=null){%> <%=addres.getAlt_mobile()%> <%}%>" class="form-control input-sm " name="altMobile"  maxlength="10"   placeholder="Enter Alternate Mobile No." onblur="checknegative(this)"/>
                           </div>
                        </div>
                   
                          <div class="col-md-2">
                        <div class="form-group">
                         <label>Landline No.</label>
                           <input   id="LandLineTextBox" type="text" value="<%if(addres!=null&&addres.getLandline()!=null){%> <%=addres.getLandline()%> <%}%>"  class="form-control input-sm " name="landineNo" maxlength="10"  placeholder="Enter Landline No"  onblur="checknegative(this)">  
                        </div>
                         </div>
                         
                        
                        
                        <div class="col-md-2">
                        <div class="form-group">
                            <label>From Res Add<span class="mandatory">*</span></label>
                            
                            <input type="text"  value="<%if(addres!=null && addres.getFrom_res_addr()!=null){%> <%=DateTimeFormatUtil.SqlToRegularDate(addres.getFrom_res_addr().toString())%> <%}%>" class="form-control input-sm " id="formRes" name="fromRes" required="required" placeholder="Enter Discipline" readonly>   
                        </div>
                       </div>
                
                
                     <div class="col-md-2">
                           <div class="form-group">
                            <label>Ext No.:</label>
                            <input id="EXTTextBox" type="text" value="<%if(addres!=null&&addres.getExt()!=null){%> <%=addres.getExt()%> <%}%>"  class="form-control input-sm " name="extNo"    maxlength="4"  placeholder="Enter Ext No" onblur="checknegative(this)"/>
                           </div>
                        </div>

                </div>
				
				
				<div class="row">
				  
                        <div class="col-md-6">
                           <div class="form-group">
                            <label>Quarter Details</label>
                            <input type="text" value="<%if(addres!=null&&addres.getQtrDetails()!=null){%> <%=addres.getQtrDetails()%> <%}%>" class="form-control input-sm " id="qtrDetail" name="qtrDetail"   maxlength="90"  placeholder="Enter Quarter Details" onclick="return trim(this)" onchange="return trim(this)">
                           </div>
                        </div>
                       
                       
                       
                        <div class="col-md-3">
                           <div class="form-group">
                            <label>Quarter No</label>
                            <input type="text" value="<%if(addres!=null&&addres.getQtrNo()!=null){%> <%=addres.getQtrNo()%> <%}%>" class="form-control input-sm " id="qtrNo" name="qtrNo"   maxlength="90"  placeholder="Enter Quarter No" onclick="return trim(this)" onchange="return trim(this)">
                           </div>
                        </div>
                   
                       <div class="col-md-3">
                           <div class="form-group">
                            <label>Quarter Type</label>
                            <input type="text" value="<%if(addres!=null&&addres.getQtrType()!=null){%> <%=addres.getQtrType()%> <%}%>" class="form-control input-sm " id="qtrType" name="qtrType"   maxlength="90"  placeholder="Enter Quarter Type" onclick="return trim(this)" onchange="return trim(this)">
                           </div>
                        </div>
        
				</div>
					<div class="row">
					   
                        <div class="col-md-3">
                        <div class="form-group">
                         <label>Email Drona</label>
                           <input type="email" value="<%if(addres!=null&&addres.getEmailDrona()!=null){%> <%=addres.getEmailDrona()%> <%}%>"  class="form-control input-sm " id="eDrona" name="eDrona" maxlength="90"    placeholder=" Enter Email Drona" onclick="return trim(this)" onchange="return trim(this)" >  
                        </div>
                         </div>
                         
                        
                           <div class="col-md-3">
                           <div class="form-group">
                            <label>Email Official</label>
                            <input type="email" value="<%if(addres!=null&&addres.getEmailOfficial()!=null){%> <%=addres.getEmailOfficial()%> <%}%>" class="form-control input-sm " id="eOfficial" name="eOfficial"  maxlength="90"   placeholder="Enter Email Official" onclick="return trim(this)" onchange="return trim(this)" >
                           </div>
                        </div>
                   
                          <div class="col-md-3">
                        <div class="form-group">
                         <label>Email Personal</label>
                           <input type="email" value="<%if(addres!=null&&addres.getEmailPersonal()!=null){%> <%=addres.getEmailPersonal()%> <%}%>" class="form-control input-sm " id="ePersonal" name="ePersonal" maxlength="90"   placeholder="Enter Email Personal"  onclick="return trim(this)" onchange="return trim(this)" >  
                        </div>
                         </div>
                         
                        
                           <div class="col-md-3">
                           <div class="form-group">
                            <label>Email Outlook</label>
                            <input type="email" value="<%if(addres!=null&&addres.getEmailOutlook()!=null){%> <%=addres.getEmailOutlook()%> <%}%>" class="form-control input-sm " id="eOutlook" name="eOutlook"   maxlength="90"   placeholder="Enter Email Outlook" onclick="return trim(this)" onchange="return trim(this)" >
                           </div>
                        </div>
                   
					</div>				
	
		
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<%if(addres!=null){ %>
							<input type="hidden"  name="addressresid" value="<%=addres.getAddress_res_id()%>">
				              <button type="submit" class="btn btn-sm submit-btn" name="Action" value="EDIT" onclick="return CommentsModel();" >SUBMIT</button>
								<%}else{%>
			                   <button type="submit" class="btn btn-sm submit-btn"	onclick="return CommentsModel();" name="Action" value="ADD">SUBMIT</button>
								<%}%>
		                     
							 </div>
							</div>
						   </div> 
					      </div>							
						 </div>	
					<input type="hidden" name="intimation" value="intimation">
					<%if(addres!=null){ %> </form>
					 <%}else{%></form><%}%>
				         </div>
				        </div>	
		               </div>
</body>
<script type="text/javascript">
function CommentsModel()
{
	
	  var ResAdd =$("#resAdd").val();
	  var City =$("#city").val();
	  var State =$("#state").val();
	  var CityPin= $("#CityPinTextBox").val();
	  var Mobile = $("#MobileTextBox").val();
	  var AltMobile = $("#AltMobileTextBox").val();
	  var LandLine = $("#LandLineTextBox").val();
	  var QtrDetail =$("#qtrDetail").val();
	  var QtrNo= $("#qtrNo").val();
	  var QtrType = $("#qtrType").val();
	  var EDrona = $("#eDrona").val();
	  var EOfficial = $("#eOfficial").val();
	  var EPersonal = $("#ePersonal").val();
	  var EOutlook = $("#eOutlook").val();
	  var fromRes=$("#fromRes").val();
	  var currdate=new Date();
	  
	  if(confirm('Are You Sure to Submit?')){
	  if(ResAdd==null || ResAdd=='' || ResAdd=="null" ){
			alert('Enter the Residential Address!');
			return false;
		}else if(State==null || State=='' || State=="null" ){
			alert('Please Select State!');
			return false;
		}else if(City==null || City=='' || City=="null" ){
			alert('Enter the City Name!');
			return false;
		}else if(CityPin==null || CityPin=='' || CityPin=="null" ){
			alert('Enter the City Pin!');
			return false;
		}else if(fromRes==currdate){
			alert('please select fromResDate')
			return false;
		}
	   
	   else if(Mobile==null || Mobile=='' || Mobile=="null" ){
			alert('Enter the Mobile Number!');
			return false;
		} /*else if(AltMobile==null || AltMobile=='' || AltMobile=="null" ){
			alert('Enter the Alt Mobile Number!');
			return false;
		}else if(LandLine==null || LandLine=='' || LandLine=="null" ){
			alert('Enter the LandLine Number!');
			return false;
		}else if(QtrDetail==null || QtrDetail=='' || QtrDetail=="null" ){
			alert('Enter the Quarter Details!');
			return false;
		}else if(QtrNo==null || QtrNo=='' || QtrNo=="null" ){
			alert('Enter the Quarter No!');
			return false;
		}else if(QtrType==null || QtrType=='' || QtrType=="null" ){
			alert('Enter the Quarter Type!');
			return false;
		}else if(EDrona==null || EDrona=='' || EDrona=="null" ){
			alert('Enter the Alt Email Drona!');
			return false;
		}else if(EOfficial==null || EOfficial=='' || EOfficial=="null" ){
			alert('Enter the Email Official!');
			return false;
		}else if(EPersonal==null || EPersonal=='' || EPersonal=="null" ){
			alert('Enter the Alt Email Personal!');
			return false;
		}else if(EOutlook==null || EOutlook=='' || EOutlook=="null" ){
			alert('Enter the Email Outlook!');
			return false;
		}  */ else{
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
$('#formRes').daterangepicker({
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
setPatternFilter($("#EXTTextBox"), /^-?\d*$/);

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