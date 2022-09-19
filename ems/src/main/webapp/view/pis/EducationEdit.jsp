<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
    <%@page import="java.time.LocalDate"%>
    <%@page import="java.util.List " %>
    <%@page import="com.vts.ems.pis.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Education ADD</title>
</head>
<body>
<%

Qualification quali = (Qualification)request.getAttribute("QualificationDetails");
Object[] empdata    = (Object[])request.getAttribute("Empdata");
List<Object[]> QualificationList = (List<Object[]>)request.getAttribute("QualificationList");
List<Object[]> DisciplineList = (List<Object[]>)request.getAttribute("DisciplineList");

%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Education Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)
						</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="EducationList.htm?empid=<%=empdata[2]%>">Education List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Education Edit</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="EditEducation.htm" method="POST" enctype="multipart/form-data" autocomplete="off">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" > 
		<div class="card-header">
		<h5>Fill Qualification Details</h5>
		</div>
			<div class="card-body"  >
			  <!-- Qualification & Sponsored,Discipline,HindiProf -->
              <div class="row">
				      <div class="col-md-4">
		                <div class="form-group">
		                    <label>Qualification:<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Qualification" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                       <%if(QualificationList!=null && QualificationList.size()>0){for(Object[] obj :QualificationList){ %>
		                        <option value="<%=obj[0]%>" <%if(quali!=null && quali.getQuali_id()!=0 && quali.getQuali_id() == Integer.valueOf( obj[0].toString())){%> selected="selected"<%}%>><%=obj[1]%></option>
		                        <%}} %>              
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-2">
		                 <div class="form-group">
                            <label>Sponsored:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Sponsored" required  data-live-search="true">
		                        <option value="Y" <%if(quali!=null && quali.getSponsored()!=null && "Y".equalsIgnoreCase(quali.getSponsored())){%> selected="selected"<%}%>>YES</option>
		                        <option value="N" <%if(quali!=null && quali.getSponsored()!=null && "N".equalsIgnoreCase(quali.getSponsored())){%> selected="selected"<%}%>>NO</option>              
		                    </select>
                        </div>
		                
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                    <label>Discipline:<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Discipline" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                       <%if(DisciplineList!=null && DisciplineList.size()>0){for(Object[] obj :DisciplineList){ %>
		                        <option value="<%=obj[0]%>" <%if(quali!=null && quali.getDisci_id()!=0 && quali.getDisci_id() ==Integer.valueOf( obj[0].toString())){%> selected="selected"<%}%> ><%=obj[1]%></option>
		                        <%}} %>              
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-2">
		                 <div class="form-group">
                            <label>HindiProf:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="HindiProf" required  data-live-search="true">
		                          <option value="Y" <%if(quali!=null && quali.getHindi_prof()!=null && "Y".equalsIgnoreCase(quali.getHindi_prof())){%> selected="selected"<%}%>>YES</option>
		                          <option value="N" <%if(quali!=null && quali.getHindi_prof()!=null && "N".equalsIgnoreCase(quali.getHindi_prof())){%> selected="selected"<%}%>>NO</option>                       
		                    </select>
                        </div>
		              </div>
		       </div>	

                <!-- //University,Division,Specialization,Honours -->
                 <div class="row">
				       <div class="col-md-4">
		                <div class="form-group">
		                	<label>University:<span class="mandatory">*</span></label>
		                    <input  id="university" maxlength="255" <%if(quali!=null && quali.getUniversity()!=null){%> value="<%=quali.getUniversity()%>" <%}%> type="text" name="University" class="form-control input-sm" required="required"  placeholder="Enter University" > 
		                </div>
		              </div>
		              
		              <div class="col-md-3">
		                <div class="form-group">
		                	<label>Division:<span class="mandatory">*</span></label>
		                    <input  id="division" maxlength="25" <%if(quali!=null && quali.getDivision()!=null){%> value="<%=quali.getDivision()%>" <%}%>  type="text" name="Division" class="form-control input-sm" required="required"  placeholder="Enter Division" >
		                </div>
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                     <label>Specialization:<span class="mandatory">*</span></label>
		                    <input  id="specialization" maxlength="255" <%if(quali!=null && quali.getSpecialization()!=null){%> value="<%=quali.getSpecialization()%>" <%}%>  type="text" name="Specialization" class="form-control input-sm"    required="required"  placeholder="Enter Specialization" onclick="checkLength()"> 
		                </div>
		               </div> 
		                 <div class="col-md-2">
		                 <div class="form-group">
                            <label>Honours:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Honours" required  data-live-search="true">
		                         <option value="Y" <%if(quali!=null && quali.getHonours()!=null && "Y".equalsIgnoreCase(quali.getHonours())){%> selected="selected"<%}%>>YES</option>
		                          <option value="N" <%if(quali!=null && quali.getHonours()!=null && "N".equalsIgnoreCase(quali.getHonours())){%> selected="selected"<%}%>>NO</option>                                 
		                    </select>
                        </div>
		              </div>
		                 		        	       
				</div>	
             
                <div class="row">
                      <div class="col-md-3">
		                 <div class="form-group">
                            <label>Acquired:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Acquired" required  data-live-search="true">
		                        <option value="B" <%if(quali!=null && quali.getAcq_bef_aft()!=null && "B".equalsIgnoreCase(quali.getAcq_bef_aft())){%> selected="selected"<%}%>>Before Join</option>
		                        <option value="A" <%if(quali!=null && quali.getAcq_bef_aft()!=null && "A".equalsIgnoreCase(quali.getAcq_bef_aft())){%> selected="selected"<%}%>>After Join</option>              
		                    </select>
                        </div>
                        </div>
                      
                 <div class="col-3">
                        <div class="form-group">
                            <label>Year Of Passing<span class="mandatory">*</span></label>
                            <input class="form-control input-sm mydate"  data-date-format="yyyy-mm-dd" id="datepicker1" name="yearofpassing"  required="required"  >
                        </div>
                 </div>
                
                <div class="col-3">
                        <div class="form-group">
		                	<label>CGPA:<span class="mandatory">*</span></label>
		                    <input  id="cgpa" <%if(quali!=null && quali.getCgpa()!=null){%> value="<%=quali.getCgpa()%>" <%}%>  type="text" name="CGPA" class="form-control input-sm" required="required"  placeholder="Enter Division" >
		                </div>
                </div>
                 
                  
                </div>
               <!-- //Acquired,Year Of Passing,CGPA -->

						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<input type="hidden" name="empid" <%if(empdata!=null){%> value="<%= empdata[2]%>" <%}%>>
							<input type="hidden" name="qualificationid" <%if(quali!=null && quali.getQualification_id()!=0){%> value="<%=quali.getQualification_id()%>" <%}%>>
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return CommentsModel();"
									name="action" value="submit">SUBMIT</button>
									</div>
							</div>
						</div>
						</div></div>
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
		  <%if(quali!=null && quali.getYearofpassing()!=null){%> 
		  document.getElementById("datepicker1").value =<%=quali.getYearofpassing()%> 
		  <%}else{%>
		  document.getElementById("datepicker1").value =new Date().getFullYear()
		  <%}%>
		</script>
		<script type="text/javascript">
		function CommentsModel()
		{
			var university = $("#university").val();
			var Division = $("#division").val();
			var Specialization = $("#specialization").val();
			var CGPA = $("#cgpa").val();
			
			if(university==null || university=='' || university=="null" ){
				alert('Enter the University!');
				return false;
			}else if(Division==null || Division=='' || Division=="null" ){
				alert('Enter the Division!');
				return false;
			}else if(Specialization==null || Specialization=='' || Specialization=="null" ){
				alert('Enter the Specialization!');
				return false;
			}else if(CGPA==null || CGPA=='' || CGPA=="null" ){
				alert('Enter the CGPA!');
				return false;
			}else{
				$('#myModal').modal('show');
			}
				 
		}
		</script>
</body>
</html>