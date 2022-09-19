<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@page import="java.time.LocalDate"%>
       <%@page import="java.util.List" %>
       <%@page import="com.vts.ems.pis.model.*" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Awards Edit</title>
</head>
<body>
<%
Awards app = (Awards)request.getAttribute("AwardssDetails");
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> pisawardslist = (List<Object[]>)request.getAttribute("PisAwardsList");

%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Awards Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)
						</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="AwardsList.htm?empid=<%=empdata[2]%>">Awards List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Awards Edit</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">

	    <form action="EditAwards.htm" method="POST" enctype="multipart/form-data" autocomplete="off">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" > 
		<div class="card-header">
		<h5>Fill Awards Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
			    
				      <div class="col-md-4">
		                <div class="form-group">
		                    <label>Award Name<span class="mandatory">*</span></label>
		                	 <select class="form-control input-sm select2" name="AwardName" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                       <%if(pisawardslist!=null && pisawardslist.size()>0){for(Object[] obj :pisawardslist){ %>
		                        <option value="<%=obj[0]%>" <%if(app!=null && app.getAwardListId()!=0 && app.getAwardListId() ==Integer.valueOf( obj[0].toString())){%> selected="selected"<%}%> ><%=obj[1]%></option>
		                        <%}} %>              
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-2">
		                 <div class="form-group">
                            <label>Certificate<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="certificate" required  data-live-search="true">
		                       <option value="Y" <%if(app!=null && app.getCertificate()!=null && "Y".equalsIgnoreCase(app.getCertificate())){%> selected="selected"<%}%>>YES</option>
		                        <option value="N" <%if(app!=null && app.getCertificate()!=null && "N".equalsIgnoreCase(app.getCertificate())){%> selected="selected"<%}%>>NO</option>       
		                    </select>
                        </div>
		                
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                    <label>Award From<span class="mandatory">*</span></label>
		                      <input  id="awardfrom" maxlength="25" type="text" <%if(app!=null && app.getAward_by()!=null){%> value="<%=app.getAward_by()%>" <%}%> name="AwardFrom" class="form-control input-sm" required="required"  placeholder="Enter Award From" > 
		                </div>
		               </div> 
		               
		                 <div class="col-md-2">
		                 <div class="form-group">
                            <label>Citation<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Citation" required  data-live-search="true">
		                       <option value="Y" <%if(app!=null && app.getCitation()!=null && "Y".equalsIgnoreCase(app.getCitation())){%> selected="selected"<%}%>>YES</option>
		                        <option value="N" <%if(app!=null && app.getCitation()!=null && "N".equalsIgnoreCase(app.getCitation())){%> selected="selected"<%}%>>NO</option>    
		                    </select>
                        </div>
		              </div>
		               
		       </div>	

                
                 <div class="row">
				       <div class="col-md-4">
		                <div class="form-group">
		                	<label>Award Details<span class="mandatory">*</span></label>
		                    <input  id="awarddetails" maxlength="50" <%if(app!=null && app.getDetails()!=null){%> value="<%=app.getDetails()%>" <%}%>  type="text" name="AwardDetails" class="form-control input-sm" required="required"  placeholder="Enter Award Details" > 
		                </div>
		              </div>
		              
		               <div class="col-md-2">
		                 <div class="form-group">
                            <label>Cash<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Cash" required  data-live-search="true">
		                       <option value="Y" <%if(app!=null && app.getCash()!=null && "Y".equalsIgnoreCase(app.getCash())){%> selected="selected"<%}%>>YES</option>
		                        <option value="N" <%if(app!=null && app.getCash()!=null && "N".equalsIgnoreCase(app.getCash())){%> selected="selected"<%}%>>NO</option>            
		                    </select>
                        </div>
		              </div>
		              
		                <div class="col-md-4">
		                <div class="form-group">
		                    <label>Award Category<span class="mandatory">*</span></label>
		                    <select class="form-control input-sm select2" name="AwardCategory" required  data-live-search="true">
		                        <option value="Indivicual" <%if(app!=null && app.getAward_cat()!=null && "Indivicual".equalsIgnoreCase(app.getAward_cat())){%> selected="selected"<%}%>>Individual</option>
		                        <option value="Group" <%if(app!=null && app.getAward_cat()!=null && "Group".equalsIgnoreCase(app.getAward_cat())){%> selected="selected"<%}%>>Group</option>       
		                        <option value="Institutional" <%if(app!=null && app.getAward_cat()!=null && "Institutional".equalsIgnoreCase(app.getAward_cat())){%> selected="selected"<%}%>>Institutional</option>       
		                        <option value="Others" <%if(app!=null && app.getAward_cat()!=null && "Others".equalsIgnoreCase(app.getAward_cat())){%> selected="selected"<%}%>>Others</option>              
		                    </select>
		                </div>
		               </div>         
		               
		                <div class="col-2">
                        <div class="form-group">
                            <label>Award Year<span class="mandatory">*</span></label>
                            <input class="form-control input-sm mydate" data-date-format="yyyy-mm-dd" id="datepicker1" name="AwardYear"  required="required"  >
                        </div>
                        </div>  		        	       
				</div>	
             
                <div class="row">
                      <div class="col-md-5">
		                 <div class="form-group">
                            <label>Cash Amount<span class="mandatory">*</span></label>
                            <input  id="cashamount" maxlength="6" <%if(app!=null && app.getCash_amt()!=null){%> value="<%=app.getCash_amt()%>" <%}%>  type="text" name="CashAmount" class="form-control input-sm" required="required"  placeholder="Enter Cash Amount" > 
		                </div>
                        </div>
                      
                    <div class="col-md-2">
		                 <div class="form-group">
                            <label>Medallion<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Medallion" required  data-live-search="true">
		                         <option value="Y" <%if(app!=null && app.getMedallion()!=null && "Y".equalsIgnoreCase(app.getMedallion())){%> selected="selected"<%}%>>YES</option>
		                        <option value="N" <%if(app!=null && app.getMedallion()!=null && "N".equalsIgnoreCase(app.getMedallion())){%> selected="selected"<%}%>>NO</option>                        
		                    </select>
                        </div>
		              </div>
                 
                     <div class="col-md-3">
		                <div class="form-group">
		                	<label>Award Date<span class="mandatory">*</span></label>
		                    <input type="text"  class="form-control input-sm mydate" readonly="readonly"   id="awarddate" name="AwardDate"  required="required">                   
		                </div>
		              </div>
                  
                </div>

						<div class="row">
							<div class="col-12" align="center">
								 <div class="form-group">
								    <input type="hidden" name="empid" <%if(empdata!=null){%> value="<%= empdata[2]%>" <%}%>>
								    <input type="hidden" name="awardsid" <%if(app!=null && app.getAwards_id()!=0){%> value="<%=app.getAwards_id()%>" <%}%>>
									<button type="submit" class="btn btn-sm submit-btn"
									onclick="return CommentsModel();"
									name="action" value="submit">SUBMIT</button>
								 </div>
							</div>
						</div>
						</div>
						</div>
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
						               <input type="file" class=" form-control w-100"   id="file" name="selectedFile" > 
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick="return confirm('Are You Sure To Submit!');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
			</form>
			</div>
		</div>
		</div>
		</div>
		
		<script type="text/javascript">
		  $("#datepicker1").datepicker({
		    	
		    	autoclose: true,
		    	 format: 'yyyy',
		    		 viewMode: "years", 
		    		    minViewMode: "years"
		    });
		  <%if(app!=null && app.getAward_year()!=null){%>
			  document.getElementById("datepicker1").value ="<%=app.getAward_year()%>"
		  <%}else{%>
		  document.getElementById("datepicker1").value =new Date().getFullYear()
		  <%}%>
		 
		</script>

<script type="text/javascript">

setPatternFilter($("#cgpa"), /^[0-9]*(\.[0-9]{0,2})?$/);
setPatternFilter($("#cashamount"), /^-?\d*$/);
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
$('#awarddate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	 <%if(app!=null && app.getAward_date()!=null){%>
	"startDate" : new Date("<%=app.getAward_date()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

	
</script>
<script type="text/javascript">
		function CommentsModel()
		{
			var awardfrom = $("#awardfrom").val();
			var awarddetails = $("#awarddetails").val();
			var cashamount = $("#cashamount").val();
			var Awardyear = $("#datepicker1").val();
			
			if(awardfrom==null || awardfrom=='' || awardfrom=="null" ){
				alert('Enter the Award From!');
				return false;
			}else if(awarddetails==null || awarddetails=='' || awarddetails=="null" ){
				alert('Enter the Award Details!');
				return false;
			}else if(cashamount==null || cashamount=='' || cashamount=="null" ){
				alert('Enter the Cash Amount!');
				return false;
			}else if(Awardyear==null || Awardyear=='' || Awardyear=="null" ){
				alert('Enter the Award Year!');
				return false;
			}else{
				$('#myModal').modal('show');
			}
				 
		}
		</script>
</body>
</html>