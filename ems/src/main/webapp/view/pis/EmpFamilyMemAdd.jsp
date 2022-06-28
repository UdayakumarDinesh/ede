<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.time.LocalDate"%>
    <%@page import="java.util.List" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> RelativeList = (List<Object[]>)request.getAttribute("FamilyRelation");
List<Object[]> StatusList = (List<Object[]>)request.getAttribute("FamilyStatus");
List<Object[]> famMembersconf = (List<Object[]>) request.getAttribute("famMembersconf");
List<Object[]> famMembersPend = (List<Object[]>) request.getAttribute("famMembersPend");

String empid = (String)request.getAttribute("empid");

%>
	<div class="card-header page-top">
		<div class="row">
			<div class="col-5">
				<h5>
					Family Members Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)
					</b></small>
				</h5>
			</div>
			<div class="col-md-7">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item  " aria-current="page"><a href="EmployeeDetails.htm">Profile</a></li>
					<li class="breadcrumb-item active " aria-current="page">Family Member Add</li>
				</ol>
			</div>
		</div>
	</div>
		
	<div class="page card dashboard-card">
		<div align="center">
				<%String ses=(String)request.getParameter("result"); 
				String ses1=(String)request.getParameter("resultfail");
				if(ses1!=null){ %>
					<div class="alert alert-danger" role="alert">
						<%=ses1 %>
					</div>
				<%}if(ses!=null){ %>
					<div class="alert alert-success" role="alert">
						<%=ses %>
					</div>
				<%} %>
			</div>
	<div class="row">
		<div class="col-md-6">
		<div class=" card" style="padding: 10px;">
		
			<div class="row" align="center" ><div class="col-md-12" ><h5 style="background-color: #FFE7BF; padding: 3px;  border-radius: 5px;" >Confirmed Members</h5></div> </div>
			<table class="table table-striped table-bordered" id="">
				<thead>
				<tr>
					<th style="width: 5%;text-align: center;">SN</th>
					<th >Member Name</th>
					<th >Relation</th>
					<th >Date Of Birth</th>
				</tr>
				</thead>
				<tbody>			
				<%if(famMembersconf!=null){for(Object[] member:famMembersconf){ %>
							
					<tr>
						<td style="text-align: center;" ><%= famMembersconf.indexOf(member)+1 %></td>
						<td><%if(member[0]!=null){%><%=member[0]%><%}else{%>--<%}%></td>
						<td><%if(member[1]!=null){%><%=member[1]%><%}else{%>--<%}%></td>
						<td><%if(member[2]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(member[2].toString())%><%}else{%>--<%}%></td>
					</tr>	
						
				<%}}
				else
				{%>
					<tr><td colspan="4" style="text-align: center;" >No record found</td></tr>
				<%} %>
				</tbody>
			</table>
		</div>	
		</div>
		<div class="col-md-6">		
		<div class=" card" style="padding: 10px;">
			<form action="FamilyMembersForward.htm" method="post">
				<div class="row" align="center" >
					<div class="col-md-12" >
						<h5 style="background-color: #FFE7BF; padding: 3px;  border-radius: 5px;" >New Member(s)
							<span style="float: right;"> 
								<button type="submit" class="btn " style="background-color: transparent;margin-top: -5px;" formaction="DependentAddForm.htm" id="add-form_value" name="empid" value="<%=empid%>">
									<i style="color: #019267;background-color: transparent;" class="fa-solid fa-download"></i>
								</button>
							</span>
						</h5>
					</div>
				</div>
				<table class="table table-striped table-bordered" >
					<thead>
					<tr>
						<th style="width: 5%;text-align: center;"><input type="checkbox" class="" name="allcheck" value="000" id="select_all_add" checked ></th>
						<th style="width: 25%;" >Member Name</th>
						<th style="width: 15%;" >Relation</th>
						<th style="width: 15%;" >Date Of Birth</th>
						<th style="width: 10%;" >Status</th>
						<th style="width: 10%;" >Action</th>
					</tr>
					</thead>
					<tbody>			
					<% int flag=0;
					if(famMembersPend!=null && famMembersPend.size()>0){for(Object[] member:famMembersPend){ %>
						<tr>
							<td style="text-align: center;" >
								<%if(member[6].toString().equals("A")){ %>
									<input type="checkbox" class="add-checkbox" name="familydetailid" value="<%=member[0]%>" checked>
								<%}else{ %>
									<input type="checkbox" class="add-checkbox" name="familydetailid" value="<%=member[0]%>" disabled="disabled">
									<input type="hidden" class="add-checkbox" name="familydetailid" value="<%=member[0]%>" disabled="disabled">
								<%} %>
							</td>
							<td><%if(member[1]!=null){%><%=member[1]%><%}else{%>--<%}%></td>
							<td><%if(member[2]!=null){%><%=member[2]%><%}else{%>--<%}%></td>
							<td><%if(member[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(member[3].toString())%><%}else{%>--<%}%></td>
							<td>
								<%if(member[6].toString().equals("A")){ flag++; %>
									<span style="color: blue"><b>Added</b></span>
								<%}else if(member[6].toString().equals("F")){  %>
									<span style="color: green"><b>Forwarded</b></span>
								<%} %>
							</td>							
							<td>
								<%if(member[6].toString().equals("A")){ %>
									<button type="button" class="btn btn-sm edit-btn" onclick="famMemEdit(<%=member[0]%>)"> Edit</button>
								<%}else{ %>
									<button type="button" class="btn btn-sm edit-btn" onclick="famMemEdit(<%=member[0]%>)" disabled="disabled"> Edit</button>
								<%} %>								
							</td>
						</tr>	
					<%}
					}
					else
					{%>
						<tr> <td colspan="5" style="text-align: center;" >No record found</td></tr>
					<%} %>
					</tbody>
				</table>
				<div class= "row" align="center">
					<div class="col-md-12">
						<button type="button" class="btn btn-sm add-btn" onclick="famMemAdd();" >add</button>
						<%if(famMembersPend!=null && famMembersPend.size()>0 && flag>0){ %>
						<button type="submit" class="btn btn-sm submit-btn" data-toggle="tooltip" onclick="return checklength(); " data-placement="top" title="Forward To Admin for Inclusion" >
							Forward For Approval
						</button>
						<%} %>
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
		</div>	
		</div>
	</div>
	</div>
	
	
<!-- ---------------------------------------- member Add Model -----------------------------------------  -->
	
		<div class="modal fade add-form-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">

			<div class="modal-dialog modal-lg modal-dialog-centered" style="min-width: 65% !important;min-height: 80% !important; ">
				<div class="modal-content" >
					<div class="modal-header" style="background: #0E6FB6 ">
						<h4 style="color: white;font-weight: 600;" id="modal-header">Fill Family Member Details Add</h4>
					</div>
				 	<div class="modal-body" style="min-height: 30rem;">
						<form action="AddFamilyDetails.htm" method="POST" autocomplete="off" id="add_edit_form">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
							<!-- NAME & Date Of Birth -->
				            <div class="row">
								<div class="col-md-8">
							        <div class="form-group">
						    	        <label>Name:<span class="mandatory">*</span></label>
						                <input type="text"  id="mem-name"  class="form-control input-sm" style="text-transform:capitalize"  maxlength="100" name="memberName" required="required" placeholder="Enter name" >
						            </div>
						        </div> 
						        <div class="col-md-4">
								    <div class="form-group">
								        <label>Date Of Birth:<span class="mandatory">*</span></label>
								        <input type="text" class="form-control input-sm " id="mem-dob" readonly="readonly" name="dob" required="required">
						            </div>
							    </div>
						   	</div>	
				            <!-- / .NAME & Date Of Birth --> 
			                 
			                <!-- RELATIONSHIP & BENID-->
			                <div class="row">
								<div class="col-md-5">
							        <div class="form-group">
								        <label>Relation:<span class="mandatory">*</span></label><br>
								        <select class="form-control input-sm select2" id="mem-relation" style="width: 80%;" name="relation" required  data-live-search="true">
									        <option value="" disabled="disabled" selected>Select Relation</option>
									        <% for(Object[] relativeLs:RelativeList){ %> 
									     	   <option value="<%=relativeLs[0]%>"><%=relativeLs[1]%></option>
									        <%} %> 
								        </select>
							        </div>
						        </div>
					              
					            <div class="col-md-3">
					            	<div class="form-group">
					                	<label>Gender:<span class="mandatory">*</span></label><br>
					                	<select class="form-control input-sm select2" id="mem-gender" name="Gender" style="width: 80%;"  required  data-live-search="true">
					                    	<option value="" disabled="disabled" selected>Select Gender</option>
					                        <option value="M">Male</option>
					                       	<option value="F">Female</option>
					                    </select>  
					                </div> 
					            </div>  
					            <div class="col-md-4">
					            	<div class="form-group">
					                	<label>Ben Id:<span class="mandatory">*</span></label><br>
					                    <input  type="text" name="benId" class="form-control input-sm" id="mem-benid"  maxlength="9" required="required"  placeholder="Enter BenID" > 
					                </div>
					            </div> 
							</div>	
							
							
			               	<div class="row">
				                <div class="col-4">
					                <div class="form-group">
						                <label>Status<span class="mandatory">*</span></label><br>
						                <select class="form-control input-sm select2" style="width: 80%;" name="status" id="mem-status" required="required">
							                <option value="" selected disabled >Select Status</option>
							                <% for(Object[] statusLs:StatusList){ %> 
									  	      <option value="<%=statusLs[0]%>"><%=statusLs[1]%></option>
									        <%} %>
						                </select>
					                </div>  
				                </div>
			                      
			                    <div class="col-4">
				                    <div class="form-group">
					                    <label>Status From<span class="mandatory">*</span></label>
					                    <input type="text" class="form-control input-sm mydate" name="statusDate" id="mem-status-from" readonly="readonly" required="required" placeholder="Enter Date" />
				                    </div>
			                    </div>
			                   
			                	<div class="col-2">
			                        <div class="form-group">
			                            <label>BG:<span class="mandatory">*</span></label>
			                            <select class="form-control input-sm " name="bloodgroup" id="mem-bg" required="required" style="width:70px; height: 30px;" >
				                            <option value="A-">A-</option>
				                            <option value="A+">A+</option>
				                            <option value="B-">B-</option>
				                            <option value="B+">B+</option>
				                            <option value="AB-">AB-</option>
				                            <option value="AB+">AB+</option>
				                            <option value="O-">O-</option>
				                            <option value="O+">O+</option>
				                            <option value="Not Available">Not Available</option>
			                            </select>
			                        </div>
			                    </div>
			                
			                
			                	<div class="col-2">
			                       <div class="form-group">
					                	<label>PH:<span class="mandatory">*</span></label>
					                    <select name="PH" class="form-control input-sm" id="mem-ph" style="width: 70px;height: 30px;">
					                    <option value="N">No</option>
					                    <option value="Y">Yes</option>
					                    </select>
					                 </div>
			                    </div>
			                 
			                  
			                </div>
			               	<!-- //Status,Status From,BG,PH -->
			                  
			               	<!-- dependency ,Employed,Married-->
							<div class="row"> 
					                <!-- Medical -->
					        	<div class="col-4">
					            	<label>Med Dependent:<span class="mandatory">*</span></label>
					                <div class="form-group">
					                   	<div class="input-group">
					                       	<span class="input-group-addon" style="padding: 3px 6px;">
						                          	<select name="medicaldep" class="form-control input-sm" id="mem-meddep" style="width: 100%; height: 30px;">
						                         		<option value="N">No</option>
						                         		<option value="Y" >Yes</option>
												</select>
					                       	</span>
					                    	<input type="text" class="form-control input-sm mydate" id="mem-meddep-date" name="medicaldepdate" readonly="readonly" placeholder="Enter Date" required style="width: 85px">
					                 	</div><!-- /input-group -->
					                </div> 
					           	</div><!-- / Medical -->
					                
					              
					           	<div class="col-4">   <!-- LTC Dependent -->
					           		<label>LTC Dependent:<span class="mandatory">*</span></label>
					                <div class="form-group">
					                	<div class="input-group">
					                    	<span class="input-group-addon" style="padding: 3px 6px;">
						                        <select name="ltcdep" class="form-control input-sm" id="mem-ltcdep" style="width: 100%; height: 30px;">
							                        <option value="Y" >Yes</option>
							                        <option value="N">No</option>
						                        </select>
					                        </span>
					                        <input type="text" class="form-control input-sm mydate" id="mem-ltcdep-date" readonly="readonly" value="<%=LocalDate.now() %>" placeholder=""  id="LTC" name="LTC"  required="required"  >
					                	</div><!-- /input-group -->
					            	</div> 
					            </div><!-- LTC Dependent -->
					          
			                   <div class="col-2">
			                       <div class="form-group">
					                	<label>Married:<span class="mandatory">*</span></label>
					                    <select name="married_unmarried" class="form-control input-sm"  id="mem-married" style="width:70px; height: 30px;">
						                    <option value="N">No</option>
						                    <option value="Y">Yes</option>
					                    </select>
					                 </div>
			                    </div>
			                    
			                    
			                    <div class="col-2">
			                    	<div class="form-group">
					                	<label>Employed:<span class="mandatory">*</span></label>
					                    <select name="emp_unemp" class="form-control input-sm" id="mem-employed" style="width:70px; height: 30px;">
						                    <option value="N">No</option>
						                    <option value="Y">Yes</option>       
					                    </select>
					                </div>
			                    </div>
					       	</div>
			               
			                <!--// dependency ,Employed,Married-->
							<div class="row" id = "EmpHide">
								<div class="col-md-3" >
			                    	<div class="form-group">
			                        <label>Employed Status:<span class="mandatory">*</span></label>
			                        	<select class="form-control input-sm" name="EmpStatus" id="mem-employed-status">
				                            <option value="" selected disabled >Select Status</option>
						                    <option value="Private">Private</option>
						                    <option value="Central Goverment">Central Goverment.</option>
						                    <option value="State Goverment">State Goverment</option>
						                    <option value="PSU">PSU</option>
			                            </select>
			                        </div>  
			               		</div>
			               		<div class="col-md-3" >
			                    	<div class="form-group">
			                        <label>Occupation:<span class="mandatory">*</span></label>
			                        	<input type="text" class="form-control" name="memberOccupation" value="" id="mem-occupation" maxlength="100">
			                        </div>  
			               		</div>
			               		
			               		<div class="col-md-3" >
			                    	<div class="form-group">
			                        <label>Income (Rs / Month)<span class="mandatory">*</span></label>
			                        	<input type="number" class="form-control numberonly" name="memberIncome" value="0" id="mem-income" min="0" max="9999999999" >
			                        </div>  
			               		</div>
							</div>
						
							<div class="row">
								<div class="col-12" align="center">
									<div class="form-group">
										<input type="hidden" name="empid" value="<%= empdata[2]%>">
										<button type="submit" class="btn btn-sm submit-btn" id="add-btn" onclick="return confirm('Are You Sure To Submit?');" name="action" value="submit">Submit</button>
										<button type="submit" class="btn btn-sm update-btn" id="update-btn" formaction="EditFamilyDetails.htm" formenctype="multipart/form-data"  onclick="return confirm('Are You Sure To update?');" name="action" value="update">Update</button>
									</div>
								</div>
							</div>
							<input type="hidden" name="incstatus" value="A">
							<input type="hidden" name="familyid" id="familydetailsid" value="">
							<input type="file" name="selectedFile" style="visibility: hidden;">
							<input type="hidden" name="useredit" value="Y">
						</form>
					</div>
						
				</div>
			</div>
		</div>

<!-- ---------------------------------------- member Add Model -----------------------------------------  -->

<%-- 
<%if(detailsids != null && !detailsids.trim().equals("")){ %>

<form action="DependentAddForm.htm" method="get" target="_blank" id="download-form">
	<input type="hidden" name="detailsids" value="<%=detailsids%>">
</form>


<script type="text/javascript">

$(document).ready(function(){
	console.log('udsghfuhbuhsfshfds');
	$('#download-form').submit();		
});

</script>
<%} %> --%>




<script type="text/javascript">


function checklength()
{
	if($('.add-checkbox:checkbox:checked').length > 0)
	{
		return confirm('Are you sure to Forward Selected Member Details ? ');
		
	}else
	{
		alert('Please Select Atleast One Member !');
		return false;
	}
}

	$('.numberonly').keypress(function (e) {    

        var charCode = (e.which) ? e.which : event.keyCode    

        if (String.fromCharCode(charCode).match(/[^0-9]/g))    

            return false;                        

  		});

</script>

<script type="text/javascript">

function famMemAdd()
{
	$('.input-sm').val('');
	$('.select2').val('').trigger('change');
	
	
	$('#add-btn').show();
	$('#update-btn').hide();
	$('#EmpHide').hide();
	$('#mem-employed-status').removeAttr('required');
	$('#mem-occupation').removeAttr('required');
	$('#mem-income').removeAttr('required');
	$('.add-form-modal').modal('show');
	
}

function famMemEdit(famdetailid)
{
	
		$.ajax({
		
			type : "GET",
			url : "FamilyMemDataAjax.htm",
			data : {
					
				familydetailsid : famdetailid,
			},
			datatype : 'json',
			success : function(result) {

			console.log(result);
			
			$('#mem-name').val(result.member_name);
			
			
			$('#mem-dob').daterangepicker({
				"singleDatePicker" : true,
				"linkedCalendars" : false,
				"showCustomRangeLabel" : true,
				"maxDate" :new Date(), 
				"startDate" :new Date(result.dob),
				"cancelClass" : "btn-default",
				showDropdowns : true,
				locale : {
					format : 'DD-MM-YYYY'
				}
			});
			
			
			$('#mem-relation').val(result.relation_id).trigger('change');			
			$('#mem-gender').val(result.gender).trigger('change');
			$('#mem-benid').val(result.cghs_ben_id);
			$('#mem-status').val(result.family_status_id).trigger('change');
			
			$('#mem-status-from').daterangepicker({
				"singleDatePicker" : true,
				"linkedCalendars" : false,
				"showCustomRangeLabel" : true,
				/* "maxDate" :new Date(),  */
				"startDate" :new Date(result.status_from),
				"cancelClass" : "btn-default",
				showDropdowns : true,
				locale : {
					format : 'DD-MM-YYYY'
				}
			});
			
			
			$('#mem-bg').val(result.blood_group);	
			$('#mem-ph').val(result.ph);
			
			$('#mem-meddep').val(result.med_dep);
			$('#mem-meddep-date').daterangepicker({
				"singleDatePicker" : true,
				"linkedCalendars" : false,
				"showCustomRangeLabel" : true,
				/* "maxDate" :new Date(),  */
				"startDate" :new Date(result.med_dep_from),
				"cancelClass" : "btn-default",
				showDropdowns : true,
				locale : {
					format : 'DD-MM-YYYY'
				}
			});
			
			$('#mem-ltcdep').val(result.ltc_dep);
			$('#mem-ltcdep-date').daterangepicker({
				"singleDatePicker" : true,
				"linkedCalendars" : false,
				"showCustomRangeLabel" : true,
				/* "maxDate" :new Date(),  */
				"startDate" :new Date(result.ltc_dep_from),
				"cancelClass" : "btn-default",
				showDropdowns : true,
				locale : {
					format : 'DD-MM-YYYY'
				}
			});
			
			$('#mem-married').val(result.mar_unmarried);
			
			$('#mem-employed').val(result.emp_unemp);
			if(result.emp_unemp === 'Y')
			{
				$('#EmpHide').show();
				$('#mem-employed-status').val(result.empStatus);
				$('#mem-occupation').val(result.memberOccupation);
				$('#mem-income').val(result.memberIncome);
				
				$('#mem-employed-status').attr('required','required');
				$('#mem-occupation').attr('required','required');
				$('#mem-income').attr('required','required');
			}else
			{
				$('#EmpHide').hide();
				$('#mem-employed-status').removeAttr('required');
				$('#mem-occupation').removeAttr('required');
				$('#mem-income').removeAttr('required');
			}
			
			
			/* $('#add_edit_form').attr('action', 'EditFamilyDetails.htm'); */
			/* $('#add_edit_form').attr('enctype', 'multipart/form-data');   */
			
			$('#familydetailsid').val(result.family_details_id);
			
			$('#add-btn').hide();
			$('#update-btn').show();
			
			$('.add-form-modal').modal('show');
			
			}
		});


}
</script>

<script type="text/javascript">

$("#myTable1").DataTable({
    "lengthMenu": [5,10,20,30,50],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});


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

$('#mem-dob').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(), 
	"startDate" :new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$("#mem-employed").on("change",function(e){
	   
	 var EmpValue =$("#mem-employed").val();
	 
	    if(!(EmpValue=="Y")){
			
	    	$("#EmpHide").hide();
	    	$('#mem-employed-status').removeAttr('required');
	    	$('#mem-occupation').removeAttr('required');
			$('#mem-income').removeAttr('required');
	      }else{
	    	  
	    	$("#EmpHide").show();
	    	$('#mem-employed-status').attr('required','required');
	    	$('#mem-occupation').attr('required','required');
			$('#mem-income').attr('required','required');
	      }
});
 

$(document).ready(function(){
    $('#select_all_add').on('click',function(){
        if(this.checked){
            $('.checkbox').each(function(){
                this.checked = true;
            });
        }else{
             $('.checkbox').each(function(){
                this.checked = false;
            });
        }
    });
    
    $('.checkbox').on('click',function(){
        if($('.checkbox:checked').length == $('.checkbox').length){
            $('#select_all_add').prop('checked',true);
        }else{
            $('#select_all_add').prop('checked',false);
        }
    });
})


</script>

<script type="text/javascript">

setPatternFilter($("#mem-benid"), /^-?\d*$/);

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