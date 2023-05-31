<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List,com.vts.ems.property.model.PisMovableProperty"%>
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
 PisMovableProperty mov = (PisMovableProperty)request.getAttribute("MovProperty");
List<Object[]> States = (List<Object[]>)request.getAttribute("States");
Object[] empData=(Object[])request.getAttribute("EmpData");
%>

<div class="card-header page-top">
		<div class="row">
				<div class="col-md-7">
				<h5>Movable Property Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			   </div>
			<div class="col-md-5">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="PropertyDashBoard.htm">Property</a></li>
					<li class="breadcrumb-item active " aria-current="page">Movable Property</li>
				</ol>
			</div>	
		</div>
</div>	
<div class="page card dashboard-card"> 
  <div class="card-body">
	<div class="row">
	  <div class="col-1"></div>	
		<form action="MovablePropEdit.htm" method="POST" autocomplete="off" id="myform1">
		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			 <div class="card-body"  >			 
                 <div class="row">
				   <div class="col-md-2">
				      <label>Purpose:<span class="mandatory">*</span></label> 
				       <select name="purpose" class="form-control input-sm select2" required>
			               <option value="I" <%if(mov!=null && mov.getPurpose()!=null && "I".equalsIgnoreCase(mov.getPurpose()) ){ %> selected="selected" <%} %> >Intimation</option>
			               <option value="P" <%if(mov!=null && mov.getPurpose()!=null && "P".equalsIgnoreCase(mov.getPurpose()) ){ %> selected="selected" <%} %>>Permission</option>
			           </select>
				   </div>     
				   <div class="col-md-2">
				       <label>Transaction State:<span class="mandatory">*</span></label>
				       <select name="transState" id="transState" class="form-control input-sm select2"  required>
			                <option value="A" <%if(mov!=null && mov.getTransState()!=null && "A".equalsIgnoreCase(mov.getTransState())) {%> selected="selected" <%} %> >Acquired</option>
			               <option value="D" <%if(mov!=null && mov.getTransState()!=null && "D".equalsIgnoreCase(mov.getTransState())) {%> selected="selected" <%} %> >Disposed off</option>
			           </select>
				   </div>
				    <div class="col-md-2">
				       <label>Transaction Date:<span class="mandatory">*</span></label>
				       <input class="form-control input-sm " type="text" name="transDate"  id="transDate" value="<%if(mov!=null && mov.getTransDate()!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(mov.getTransDate().toString()) %> <%} %>" placeholder="Enter Discipline" required="required" readonly>
				   </div> 
				   <div class="col-md-2">
				       <label>Mode:<span class="mandatory">*</span></label>
				       <select name="mode" id="mode" class="form-control input-sm select2" required>
			               <option value="Purchase/Sale" <%if(mov!=null && mov.getMode()!=null && "Purchase/Sale".equalsIgnoreCase(mov.getMode())) {%> selected="selected" <%} %> >Purchase/Sale</option>
			               <option value="Gift" <%if(mov!=null && mov.getMode()!=null && "Gift".equalsIgnoreCase(mov.getMode())) {%> selected="selected" <%} %> >Gift</option>
			               <option value="Mortgage" <%if(mov!=null && mov.getMode()!=null && "Mortgage".equalsIgnoreCase(mov.getMode())) {%> selected="selected" <%} %> >Mortgage</option>
			               <option value="Lease" <%if(mov!=null && mov.getMode()!=null && "Lease".equalsIgnoreCase(mov.getMode())) {%> selected="selected" <%} %> >Lease</option>
			               <option value="Others" <%if(mov!=null && mov.getMode()!=null && "Others".equalsIgnoreCase(mov.getMode())) {%> selected="selected" <%} %> >Others</option>
			           </select>
				   </div>
				   <div class="col-md-2" id="desc">
				       <label>Description:<span class="mandatory">*</span></label>
				       <select name="description" id="description" class="form-control input-sm select" required>
			               <option value="Four Wheeler" <%if(mov!=null && mov.getDescription()!=null && "Four Wheeler".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Four Wheeler</option>
			               <option value="Two Wheeler" <%if(mov!=null && mov.getDescription()!=null && "Two Wheeler".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Two Wheeler</option>
			               <option value="Refrigerator" <%if(mov!=null && mov.getDescription()!=null && "Refrigerator".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Refrigerator</option>
			               <option value="TV" <%if(mov!=null && mov.getDescription()!=null && "TV".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>TV</option>
			               <option value="PC" <%if(mov!=null && mov.getDescription()!=null && "PC".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>PC</option>
			               <option value="Jewellery" <%if(mov!=null && mov.getDescription()!=null && "Jewellery".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Jewellery</option>
			               <option value="Loans" <%if(mov!=null && mov.getDescription()!=null && "Loans".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Loans</option>
			               <option value="Insurance Policies Premium" <%if(mov!=null && mov.getDescription()!=null && "Insurance Policies Premium".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Insurance Policies Premium</option>
			               <option value="Shares" <%if(mov!=null && mov.getDescription()!=null && "Shares".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>shares</option>
			               <option value="Securities" <%if(mov!=null && mov.getDescription()!=null && "Securities".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Securities</option>
			               <option value="Debentures" <%if(mov!=null && mov.getDescription()!=null && "Debentures".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Debentures</option>
			               <option value="Other Items" <%if(mov!=null && mov.getDescription()!=null && "Other Items".equalsIgnoreCase(mov.getDescription())) {%> selected <%} %>>Other Items</option>
			           </select>
				    </div> 
				    <div class="col-md-2" id="model" >			         
			            <label>Make & Model:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="makeAndModel" id="makeAndModel" value="<%if(mov!=null && mov.getMakeAndModel()!=null){%><%=mov.getMakeAndModel()%><%}%>" maxlength="225" placeholder="Enter Regstr.No" required>
			        </div>	               
			     </div>
			     <br>			    
			     <div class="row">     
			        <div class="col-md-2">
			            <label>Is Party Related?<span class="mandatory" >*</span></label>
			            <select name="partyRealted" id="partyRealted" class="form-control input-sm select2" required>
			              <option value="N" <%if(mov!=null && mov.getPartyRelated()!=null && "N".equalsIgnoreCase(mov.getPartyRelated())) {%> selected <%} %> >No</option>
			               <option value="Y" <%if(mov!=null && mov.getPartyRelated()!=null && "Y".equalsIgnoreCase(mov.getPartyRelated())) {%> selected <%} %> >Yes</option>
			            </select>
			        </div>
			        <div class="col-md-2" id="relation">			         
			            <label>Relationship:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="relationship" id="relationship" value="<%if(mov!=null && mov.getRelationship()!=null){%><%=mov.getRelationship()%><%}%>" maxlength="225" placeholder="Enter relationship" required>
			        </div>
			        <div class="col-md-2">
			            <label>Any dealings w.party?<span class="mandatory" >*</span></label>
			            <select name="futureDealings" id="futureDealings" class="form-control input-sm select2" required>
			               <option value="N" <%if(mov!=null && mov.getFutureDealings()!=null && "N".equalsIgnoreCase(mov.getFutureDealings())) {%>selected <%} %> >No</option>
			               <option value="Y" <%if(mov!=null && mov.getFutureDealings()!=null && "Y".equalsIgnoreCase(mov.getFutureDealings())) {%>selected <%} %>>Yes</option>
			            </select>
			        </div>
			        <div id="party" class="col-md-2" >			         
			            <label>Party's Name:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="partyName" id="partyName" value="<%if(mov!=null && mov.getPartyName()!=null){%><%=mov.getPartyName()%><%}%>" maxlength="225" placeholder="Enter Party Name" required>
			        </div>
			        <div class="col-md-4">			         
			            <label>Party's Address:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="partyAddress" id="partyAddress" value="<%if(mov!=null && mov.getPartyAddress()!=null){%><%=mov.getPartyAddress()%><%}%>" maxlength="500" placeholder="Enter Party Address" required>
			        </div>		
			     </div>
			     <br>
			     
			     <div class="row">
			        <div class="col-md-4">			         
			            <label>Transaction Arrangement:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="transArrangement" id="transArrangement" value="<%if(mov!=null && mov.getTransArrangement()!=null){%><%=mov.getTransArrangement()%><%}%>" maxlength="500" placeholder="Enter how transaction arranged or to be arrange" required>
			        </div>
			         <div class="col-md-4" id="dealing" >			         
			            <label>Nature of dealing:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="dealingNature" id="dealingNature" value="<%if(mov!=null && mov.getDealingNature()!=null){%><%=mov.getDealingNature()%><%}%>" maxlength="225" placeholder="Enter Dealing Nature" required>
			        </div>	 
			        
			        <div class="col-md-4">			         
			            <label>Any relevant facts:</label>
			            <input class="form-control" type="text" name="relavantFacts" value="<%if(mov!=null && mov.getRelavantFacts()!=null){%><%=mov.getRelavantFacts()%><%}%>" maxlength="225" placeholder="Enter Relavant facts">
			        </div>	
			     </div>	
			     <br>
                 <div class="row">	
                    <div class="col-md-2">			         
			            <label>Property Price:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="price" id="price" value="<%if(mov!=null && mov.getPrice()!=null ){%><%=mov.getPrice()%><%}%>" maxlength="225" placeholder="Enter price" onblur="checknegative(this)" required>
			        </div>			       
			        <div class="col-md-2" id="finance">
			           <label>Source for finance:<span class="mandatory" >*</span></label>
			           <select name="financeSource" id="financeSource" class="form-control input-sm select2" required>
			               <option value="Personal savings" <%if(mov!=null && mov.getFinanceSource()!=null && "Personal savings".equalsIgnoreCase(mov.getFinanceSource()) ) {%>selected <%} %> >Personal savings</option>
			               <option value="Other sources" <%if(mov!=null && mov.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(mov.getFinanceSource()) ) {%>selected <%} %> >Other sources</option>
			           </select>
			        </div>
			        <div class="col-md-2" id="others" >			         
			            <label>Other Sources:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="otherSource" id="otherSource" value="<%if(mov!=null && mov.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(mov.getFinanceSource()) && "A".equalsIgnoreCase(mov.getTransState()) ){ %><%=mov.getFinanceSource()%><%}%>" maxlength="225" placeholder="Enter Source Details" required>
			        </div>
			        <div class="col-md-2" id="sanction">
			           <label>Requisite Sanctioned?<span class="mandatory" >*</span></label>
			           <select name="requisite" id="requisite" class="form-control input-sm select2" required>
			               <option value="N" <%if(mov!=null && mov.getRequisiteSanction()!=null && "N".equalsIgnoreCase(mov.getRequisiteSanction())){%> selected<%} %> >No</option>
			               <option value="Y" <%if(mov!=null && mov.getRequisiteSanction()!=null && "Y".equalsIgnoreCase(mov.getRequisiteSanction())){%> selected<%} %> >Yes</option>
			           </select>
			        </div>
			        <div class="col-md-2" id="sitarSanction">			         
			            <label>Sanction ( SITAR ):<span class="mandatory" >*</span></label>
			            <select name="sanctionRequired" id="sanctionRequired" class="form-control input-sm select2" required>
			              <option value="N" <%if(mov!=null && mov.getSanctionRequired()!=null && "N".equalsIgnoreCase(mov.getSanctionRequired())) {%>selected <%} %> >Not Required</option>
			               <option value="Y" <%if(mov!=null && mov.getSanctionRequired()!=null && "Y".equalsIgnoreCase(mov.getSanctionRequired())) {%>selected <%} %>>Required</option>
			            </select>
			        </div>
			       
			     </div>
			     <br>
                  <br>
		     	  <div class="row">
					 <div class="col-md-12" align="center">
						<div class="form-group">
						<input type="hidden" name="movpropertyId" value="<%=mov.getMovPropertyId()%>">							
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
	var dealingNature = $('#dealingNature').val();	
	
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
	}else if(dealingNature==null || dealingNature=="" || dealingNature=="null" ){
		alert('Enter Nature of dealing Details!');
		return false;
	}else if(price==null || price=="" || price=="null" ){
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


	$( document ).ready(function() {
		/* $('#sanction').hide();
		$('#others').hide(); 
		$('#extent').hide(); 
		$('#relation').hide();	
	    $("#party").addClass("col-md-4");  
		$('#sitarSanction').hide(); */
		
		var acquire = $('#transState').val();
		var description = $('#description').val();
		var mode = $('#mode').val();
		var relation = $('#partyRealted').val();
		var financeSource = $('#financeSource').val();
		
		if(description=="Four Wheeler" || description=="Two Wheeler"){
			
			$('#model').show();
			$('#desc').removeClass("col-md-4");	
			$('#desc').addClass("col-md-2");
		}
		else{
			$('#model').hide();
			$('#desc').removeClass("col-md-2");			
			$('#desc').addClass("col-md-4");		
		}
		
		if(acquire=="A" && mode=="Gift"){
			$('#sitarSanction').show();		
		}else{
			 $('#sitarSanction').hide(); 
		}
		
		if(acquire=="A"){
			$('#sanction').hide();
			
		}else{
			$('#sanction').show();
		}
		
		
		if(relation=="Y"){
			$('#relation').show();			
			 $("#party").removeClass("col-md-4");
			$("#party").addClass("col-md-2");
		} else{
			$('#relation').hide();
			$("#party").addClass("col-md-4");
		}
		 
		if(financeSource=="Personal savings"){
		   $('#others').hide(); 
		} else{
			$('#others').show(); 
		}
	});

	
	
	$("#description").change(function(){
		var makeAndModel = $('#description').val();
		if(makeAndModel=="Four Wheeler" || makeAndModel=="Two Wheeler" ){
			$('#model').show();
			$('#desc').removeClass("col-md-4");	
			$('#desc').addClass("col-md-2");
		}
		else{
			$('#model').hide();
			$('#desc').removeClass("col-md-2");			
			$('#desc').addClass("col-md-4");		
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
			 $('#sitarSanction').hide();
			}
		});
		
	$("#financeSource").change(function(){
		var otherSource = $('#financeSource').val();

		  if(otherSource=="Other sources"){
			$('#others').show();
			}
		  else{
			$('#others').hide();			
			}
	});


	$("#partyRealted").change(function(){
		var relation = $('#partyRealted').val();

		  if(relation=="Y"){
			$('#relation').show();
			$("#party").removeClass("col-md-4");
			}
		  else{
			$('#relation').hide();
			$("#party").addClass("col-md-4");
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

</script> 
</body>
</html>