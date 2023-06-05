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
/* PisImmovableProperty imm = (PisImmovableProperty)request.getAttribute("ImmProperty"); */
List<Object[]> States = (List<Object[]>)request.getAttribute("States");
Object[] empData=(Object[])request.getAttribute("EmpData");
%>

<div class="card-header page-top">
		<div class="row">
				<div class="col-md-7">
				<h5>Immovable Property Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			   </div>
			<div class="col-md-5">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="PropertyDashBoard.htm">Property</a></li>
					<li class="breadcrumb-item "><a href="AcquiringDisposing.htm">Acquiring / Disposing </a></li>
					<li class="breadcrumb-item active " aria-current="page">Immovable Property</li>
				</ol>
			</div>	
		</div>
</div>	
<div class="page card dashboard-card"> 
  <div class="card-body">
	<div class="row">
	  <div class="col-1"></div>	
		<form action="ImmovablePropAdd.htm" method="POST" autocomplete="off" id="myform1">
		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			 <div class="card-body"  >			 
                 <div class="row">
				   <div class="col-md-2">
				      <label>Purpose:<span class="mandatory">*</span></label> 
				       <select name="purpose" class="form-control input-sm select2" required>
			               <option value="I">Intimation</option>
			               <option value="P">Permission</option>
			           </select>
				   </div>     
				   <div class="col-md-2">
				       <label>Transaction State:<span class="mandatory">*</span></label>
				       <select name="transState" id="transState" class="form-control input-sm select2"  required>
			               <option value="A" >Acquired to be</option>
			               <option value="D" >Disposed to be</option>
			           </select>
				   </div>
				    <div class="col-md-2">
				       <label>Transaction Date:<span class="mandatory">*</span></label>
				       <input class="form-control input-sm " type="text" name="transDate"  id="transDate" placeholder="Enter Discipline" required="required" readonly>
				   </div> 
				   <div class="col-md-2">
				       <label>Mode:<span class="mandatory">*</span></label>
				       <select name="mode" id="mode" class="form-control input-sm select2" required>
			               <option value="Purchase/Sale">Purchase/Sale</option>
			               <option value="Gift" >Gift</option>
			               <option value="Mortgage" >Mortgage</option>
			               <option value="Lease" >Lease</option>
			               <option value="Others" >Others</option>
			           </select>
				   </div>
				   <div class="col-md-4">
				       <label>Description:</label>
				       <input class="form-control" type="text" name="description" maxlength="500" placeholder="Enter Incase of Cultivable or Irrigated land">
				    </div>                
			     </div>
			     <br>
			     <div class="row">
			      <div class="col-md-4">
				       <label>Location:<span class="mandatory">*</span></label>
				       <input class="form-control" type="text" name="location" id="location" maxlength="225" placeholder="Municipal No, Village, Taluk" required>
				     </div> 			       
			        <div class="col-md-2">
			          <label>District:<span class="mandatory">*</span></label>
			          <input class="form-control" type="text" name="district" id="district" placeholder="Enter District" required>
			        </div>
			        <div class="col-md-2">
                        <div class="form-group">
                            <label>Pincode:<span class="mandatory">*</span></label>
                            <input type="text" class="form-control input-sm"  name="pincode" id="CityPinTextBox" maxlength="6" placeholder="Enter PIN" onblur="checknegative(this)">
                        </div>
                    </div>
			         <div class="col-md-4">
                        <div class="form-group">
                          <label>State:<span class="mandatory">*</span></label>
                           <select id="state" name="state" class="form-control input-sm selectpicker" data-live-search="true" required>
                                <option disabled="disabled" value="" selected="selected"> Select</option>
								<%if(States!=null){ 
								for (Object[] O : States) {%>								
								<option value="<%=O[1]%>"   ><%=O[1]%></option>
								<%}}%>
							</select>
                       </div>
                    </div>	 			        
			        
                   
				  
			     </div>
			     <div class="row">
			        <div class="col-md-2">			         
			            <label>Property Price:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="price" id="price" maxlength="225" placeholder="Enter price" onblur="checknegative(this)" required>
			        </div>
			        <div class="col-md-2">
				       <label>Ownership:<span class="mandatory">*</span></label>
				        <select name="ownership" class="form-control input-sm select2" required>
			               <option value="F" >Freehold</option>
			               <option value="L" >LeaseHold</option>
			           </select>
				    </div>
			        <div class="col-md-4">			         
			            <label>Ownership Particulars:</label>
			           <input class="form-control" type="text" name="osParticulars" id="osParticulars" maxlength="500" placeholder="Enter only if transaction is  not exclusive" >
			        </div>
			        <div class="col-md-4">			         
			            <label>Share of each member:</label>
			           <input class="form-control" type="text" name="osShare" id="osShare" maxlength="225" placeholder="Enter only if transaction is not exclusive" >
			        </div>
			     </div>
			      <br>
			     
			     <div class="row">     
			        <div class="col-md-2">
			            <label>Is Party Related?<span class="mandatory" >*</span></label>
			            <select name="partyRealted" id="partyRealted" class="form-control input-sm select2" required>
			               <option value="N" >No</option>
			               <option value="Y" >Yes</option>
			            </select>
			        </div>
			        <div class="col-md-2" id="relation">			         
			            <label>Relationship:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="relationship" id="relationship" maxlength="225" placeholder="Enter relationship" required>
			        </div>
			        <div class="col-md-2">
			            <label>Any dealings w.party?<span class="mandatory" >*</span></label>
			            <select name="futureDealings" id="futureDealings" class="form-control input-sm select2" required>
			               <option value="N"  >No</option>
			               <option value="Y" >Yes</option>
			            </select>
			        </div>
			        <div id="party" class="col-md-2" >			         
			            <label>Party's Name:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="partyName" id="partyName" maxlength="225" placeholder="Enter Party Name" required>
			        </div>
			        <div class="col-md-4">			         
			            <label>Party's Address:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="partyAddress" id="partyAddress" maxlength="500" placeholder="Enter Party Address" required>
			        </div>		
			     </div>
			     <br>
			     
			     <div class="row">
			        <div class="col-md-4">			         
			            <label>Transaction Arrangement:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="transArrangement" id="transArrangement" maxlength="500" placeholder="Enter how transaction arranged or to be arrange" required>
			        </div>
			         <div class="col-md-4" id="dealing" >			         
			            <label>Nature of dealing:</label>
			            <input class="form-control" type="text" name="dealingNature" id="dealingNature" maxlength="225" placeholder="Enter Dealing Nature" required>
			        </div>	 
			        
			        <div class="col-md-4">			         
			            <label>Any relevant facts:</label>
			            <input class="form-control" type="text" name="relavantFacts" maxlength="225" placeholder="Enter Relavant facts">
			        </div>	
			     </div>	
			     <br>
                 <div class="row">
			        <div class="col-md-2" id="interest">
			           <label>Applicant Interest :<span class="mandatory" >*</span></label>
			           <select  name="applicantInterest" id="applicantInterest" class="form-control input-sm select2" onchange="showcents()" required>
			               <option value="F" >Full Property</option>
			               <option value="P" >Part In Property</option>
			           </select>
			        </div>
			        <div class="col-md-2" id="extent" >			         
			            <label>Extent :<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="partialInterest" id="partialInterest" maxlength="225">
			        </div>
			        <div class="col-md-2" id="extentinname" >			         
			            <label>Extent in name of?:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="extentInNameOf" id="extentInNameOf" maxlength="225">
			        </div>
			        
			        
			        <div class="col-md-2" id="finance">
			           <label>Source for finance:<span class="mandatory" >*</span></label>
			           <select name="financeSource" id="financeSource" class="form-control input-sm select2" required>
			               <option value="Personal savings" >Personal savings</option>
			               <option value="Home loan" >Home loan</option>
			               <option value="Land loan" >Land loan</option>
			               <option value="Other sources" >Other sources</option>
			           </select>
			        </div>
			        <div class="col-md-2" id="others" >			         
			            <label>Other Sources:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="otherSource" id="otherSource" maxlength="225" placeholder="Enter Source Details" required>
			        </div>
			        <div class="col-md-2" id="sanction">
			           <label>Requisite Sanctioned?<span class="mandatory" >*</span></label>
			           <select name="requisite" id="requisite" class="form-control input-sm select2" required>
			               <option value="N" >No</option>
			               <option value="Y" >Yes</option>
			           </select>
			        </div>
			        <div class="col-md-2" id="sitarSanction">			         
			            <label>Sanction ( SITAR ):<span class="mandatory" >*</span></label>
			            <select name="sanctionRequired" id="sanctionRequired" class="form-control input-sm select2" required>
			               <option value="N" >Not Required</option>
			               <option value="Y">Required</option>
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
	var location = $('#location').val();
	var district = $('#district').val();
	var state = $('#state').val();
	var pincode = $('#CityPinTextBox').val();
	var applicantInterest = $('#applicantInterest').val();
	var partialInterest = $('#partialInterest').val();
	var extentInNameOf = $('#extentInNameOf').val();
	var price = $('#price').val();
	var financeSource = $('#financeSource').val();
	var otherSource = $('#otherSource').val();
	var partyRealted = $('#partyRealted').val();
	var relationship = $('#relationship').val();
	var partyName = $('#partyName').val();	
	var partyAddress = $('#partyAddress').val();	
	var transArrangement = $('#transArrangement').val();	
	/* var dealingNature = $('#dealingNature').val(); */	
	var osParticulars = $('#osParticulars').val().trim();	
	var osShare = $('#osShare').val().trim();	
	
	if(location==null || location=="" || location=="null"){
		alert('Enter Location Details!');
		return false;
	}else if(district==null || district=="" || district=="null" ){
		alert('Enter District Details!');
		return false;
	}else if(state==null || state=="" || state=="null" ){
		alert('Select State Details!');
		return false;
	}else if(pincode==null || pincode=="" || pincode=="null" ){
		alert('Enter Pincode Details!');
		return false;
	}else if(osParticulars.length>0 && osShare.length==0){
		alert("Enter Share Of Each Member too!");	
		return false;
	}else if(osParticulars.length==0 && osShare.length>0){
		alert("Enter Ownership Particulars too!");	
		return false;
	}else if(applicantInterest=="P" && (partialInterest==null || partialInterest=="" || partialInterest=="null") ){
		alert('Enter Extent Details!');
		return false;
	}
	else if(applicantInterest=="P" && (extentInNameOf==null || extentInNameOf=="" || extentInNameOf=="null") ){
		alert('Enter Extent In the Name Of Details!');
		return false;
	}else if(price==null || price=="" || price=="null" ){
		alert('Enter Property Price!');
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
	} */else{
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

/* function showcents(){
	var x=$('#applicantInterest').val();
	if(x=="P"){
		$('#extent').html('<label>Extent <span class="mandatory">*</span></label><input class="form-control" type="text" name="partialInterest" id="partialInterest" maxlength="225" required>')
	}else{
		
		$('#extent').html('<label>Extent </label><input class="form-control" type="text" name="partialInterest" id="partialInterest" maxlength="225" readonly>');
		/* $('#partialInterest').val(''); 
	}
	}  */

	$( document ).ready(function() {
		$('#sanction').hide();
		$('#others').hide(); 
		$('#extent').hide(); 
		$('#extentinname').hide();
		$('#relation').hide();	
	    $("#party").addClass("col-md-4");  
		/* $("#dealing").addClass("col-md-4"); */
		/* $("#interest").addClass("col-md-4"); */
		$('#sitarSanction').hide();
	});

	  $("#applicantInterest").change(function(){
		var interest = $('#applicantInterest').val();
		  if(interest=="P"){
			$('#extent').show();
			$('#extentinname').show();
			}
		  else{
			$('#extent').hide();
			$('#extentinname').hide();
			}
	});

	$("#transState").change(function(){
		var acquire = $('#transState').val();
		var otherSource = $('#financeSource').val();
		var mode = $('#mode').val();
		  if(acquire=="A")
		  {
			$('#finance').show();
			$('#sanction').hide();
			
		    if(otherSource=="Other sources")
		    {
			   $('#others').show();
		       /* $("#party").removeClass("col-md-4"); */
			}
		    if(mode=="Gift" && acquire=="A")
		    {
			  $('#sitarSanction').show();
		    }
		  }
		  else{
			$('#finance').hide();
			$('#sanction').show();
			 $('#others').hide(); 
			 /* $("#party").addClass("col-md-4"); */
			 $('#sitarSanction').hide();
			}
		});
		
	$("#financeSource").change(function(){
		var otherSource = $('#financeSource').val();

		  if(otherSource=="Other sources"){
			$('#others').show();
			/* $("#party").removeClass("col-md-4"); */
			}
		  else{
			$('#others').hide();			
			/* $("#party").addClass("col-md-4"); */
			}
	});


	$("#partyRealted").change(function(){
		var relation = $('#partyRealted').val();

		  if(relation=="Y"){
			$('#relation').show();
			$("#party").removeClass("col-md-4");
			/* $("#dealing").removeClass("col-md-4"); */
			}
		  else{
			$('#relation').hide();
			$("#party").addClass("col-md-4");
			/* $("#dealing").addClass("col-md-4"); */
			}
	});

	$("#mode").change(function(){
		var mode = $('#mode').val();
		var acquire = $('#transState').val();
		  if(mode=="Gift" && acquire=="A"){
			$('#sitarSanction').show();
			}
		  else{
			$('#sitarSanction').hide();			
			}
	});

	/* function showFinanceSource(){
	  var acquire = $('#transState').val();
		
	  if(acquire=="A"){
	     $('#finance').css("display","block");
		$('#sanction').css("display","none");
	 
		}else{
			$('#finance').css("display","none");
			$('#sanction').css("display","block");
		}
	} */

</script> 
</body>
</html>