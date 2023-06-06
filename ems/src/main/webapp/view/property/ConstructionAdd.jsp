<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List,com.vts.ems.property.model.PisImmovableProperty"%>
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
List<Object[]> States = (List<Object[]>)request.getAttribute("States");
Object[] empData=(Object[])request.getAttribute("EmpData");
%>

<div class="card-header page-top">
		<div class="row">
				<div class="col-md-7">
				<h5>Construction Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			   </div>
			<div class="col-md-5">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="PropertyDashBoard.htm">Property</a></li>
					<li class="breadcrumb-item "><a href="ConstructionRenovation.htm">Construction</a></li>
					<li class="breadcrumb-item active " aria-current="page">Construction Add</li>
				</ol>
			</div>	
		</div>
</div>	
<div class="page card dashboard-card"> 
  <div class="card-body">
	<div class="row">
	  <div class="col-1"></div>	
		<form action="MovablePropAdd.htm" method="POST" autocomplete="off" id="myform1">
		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			 <div class="card-body"  >			 
                 <div class="row">
				   <div class="col-md-4">
				      <label>Permission for:<span class="mandatory">*</span></label> 
				      <select name="transaction" class="form-control input-sm select2" required>
			               <option value="C">Construction of house</option>
			               <option value="A">Addition of house</option>
			               <option value="R">Renovation of an existing house</option>
			           </select>
				   </div>
				   <div class="col-md-2">
				      <label>Estimated Cost:<span class="mandatory">*</span></label>
				      <input class="form-control" type="text" name="estimatedCost" placeholder="Enter Estimated Cost">
				   </div>
				   <div class="col-md-2">
				       <label>Supervised By:<span class="mandatory">*</span></label>
				       <select name="transaction" class="form-control input-sm select2" required>
			               <option value="Myself">Myself</option>
			               <option value="Others">Others</option>
			           </select>
				   </div>
				   <div class="col-md-2">
				       <label>Others:<span class="mandatory">*</span></label>
			           <input class="form-control" type="text" name="others" placeholder="Enter Supervised By">
				   </div> 
				    <div class="col-md-4">
				       <label>Official dealings with Contractor:<span class="mandatory">*</span></label>
				       <select name="transaction" class="form-control input-sm select2" required>
			               <option value="N">No</option>
			               <option value="Y">Yes</option>
			           </select>
				   </div>     
				  </div>
			     <br>
                  <br>
		     	  <div class="row">
					 <div class="col-md-12" align="center">
						<div class="form-group">							
				            <button type="button" class="btn btn-sm submit-btn"	onclick="return CommentsModel();" name="Action" value="ADD">SUBMIT</button>										
					    </div>
					 </div>
				 </div>			 
		   </div>								
		 </form>			
		</div>
	</div>				
</div>
<script type="text/javascript">
function CommentsModel(){
	
	var transState = $('#transState').val();
	var description = $('#description').val();
	var makeAndModel = $('#makeAndModel').val();
	var price = $('#price').val();
	var financeSource = $('#financeSource').val();
	var otherSource = $('#otherSource').val();
	var partyRealted = $('#partyRealted').val();
	var relationship = $('#relationship').val();
	var partyName = $('#partyName').val();	
	var partyAddress = $('#partyAddress').val();	
	var transArrangement = $('#transArrangement').val();	
	/* var dealingNature = $('#dealingNature').val();	 */
	
    if( (description=="Four Wheeler" || description=="Two Wheeler") && (makeAndModel==null || makeAndModel=="" || makeAndModel=="null") ){
		alert('Enter Registration Details!');
		return false;
	}else if(financeSource=="Other sources" && (otherSource==null || otherSource=="" || otherSource=="null") ){
		alert('Enter Other Source Details!');
		return false;
	}else if(partyRealted=="Y" && (relationship==null || relationship=="" || relationship=="null") ){
		alert('Enter Relationship Details!');
		return false;
	}else if(partyName==null || partyName=="" || partyName=="null" ){
		alert('Enter Party Name!');
		return false;
	}else if(partyAddress==null || partyAddress=="" || partyAddress=="null" ){
		alert('Enter Party Address Details!');
		return false;
	}else if(transArrangement==null || transArrangement=="" || transArrangement=="null" ){
		alert('Enter Transaction Arrangement Details!');
		return false;
	}/* else if(dealingNature==null || dealingNature=="" || dealingNature=="null" ){
		alert('Enter Nature of dealing Details!');
		return false;
	} */else if(price==null || price=="" || price=="null" ){
		alert('Enter Property Price!');
		return false;
	}else{
		if(confirm('Are You Sure to submit')){
			$('#myform1').submit();
			return true;
		}else{
			return false;
		}
		
	}
	
}
</script>

<script type="text/javascript">
$('#transDate').daterangepicker({
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
setPatternFilter($("#price"), /^-?\d*$/);

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
</body>
</html>