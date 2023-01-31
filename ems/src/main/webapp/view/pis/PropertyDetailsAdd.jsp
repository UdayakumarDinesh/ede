<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ page import=" java.time.LocalDate" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Add Property Details  </title>
</head>
<body>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
			      <h5> Add Property Details </h5>
			</div>
			   <div class="col-md-7 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li  class="breadcrumb-item "><a href="PropertyReport.htm"> Property Details List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Add  Property Details   </li>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="page card dashboard-card" style="min-height: 650px;max-height: 660px;overflow: hidden">
     <div class="card-body" >  
     <div style="margin-left: 15%;margin-top: 0px; font-weight: bold;">FORM FOR STATEMENT OF IMMOVABLE PROPERTY FOR THE YEAR ENDING 31<sup style="font-size: 8px;">ST</sup> DECEMBER &nbsp;<%=LocalDate.now().minusYears(1).getYear() %> </div>  
 <form autocomplete="off">	 
		<div class="form-group">
			<div class="table-responsive">
				<table class="table table-bordered table-hover table-striped table-condensed " style="width: 63%; margin-left: 15%; margin-top:5px;">					
						<tr>
                            <th> <label > Description<span class="mandatory"	style="color: red;">*</span></label> </th>
                             <td><textarea name="description" id="description" maxlength="255" rows="2" cols="2"  class="form-control form-control" placeholder="Enter Description" required="required"></textarea></td>
                        </tr> 
                        <tr>
                            <th> <label> Address<span class="mandatory" style="color: red;">*</span></label> </th>
                             <td><textarea name="Address" rows="2" cols="2" id="Address" maxlength="500" class="form-control form-control" placeholder="Enter Address" required="required"></textarea></td>
                        </tr> 
                         <tr>
                              <th> <label>Present Property value <span class="mandatory"	style="color: red;">*</span> </label></th>
                              <td><input type="number" max="100000000000000" id="propertyvalue"  min="1" onblur="checknegative(this) " name="propertyvalue" class=" form-control form-control " placeholder="Enter Property value" required="required"></td>
                         </tr>
                            <tr>
                            <th >
                            <label>If Property is not Wholly owned,<br> Mention Partner Name and Relationship </label></th>
                          <td>  <textarea name="PartnernameAndRelationship" maxlength="200" onclick="return trim(this)" onchange="return trim(this)" rows="2" cols="1" class=" form-control form-control"  placeholder="Enter ParterName and Relationship" id="Partner"></textarea></td>
                            </tr>
                            <tr>
                            <th>
                            <label> Mode of Property Owned <span class="mandatory"	style="color: red;">*</span> </label></th>
                          <td>  <select class=" form-control form-control " name="propertymode" required="required">
                          <option value="" selected disabled>Select Mode of Property</option>
                            <option >Purchased </option>
                            <option> Lease</option>
                            <option> Inherited</option>
                            <option>Mortgage</option>
                            <option>Gift</option>
                            </select></td>
                            </tr>
                             <tr>                            
                            <th> <label>Annual Income from Property<span class="mandatory"	style="color: red;">*</span> </label></th>
                            <td> <input type="number" max="100000000000000" min="0" name="AnnualIncome" required="required" id="Annualincome" onblur="checknegative(this) " class=" form-control form-control" placeholder="Enter Annual Income from pproperty"></td>
                             </tr>
                             
                             <tr>
                            <th>  <label>Remarks</label></th>                                                                                    
                             <td><textarea cols="2" rows="1" name="remarks" maxlength="1000" class=" form-control"placeholder="Enter Remarks"></textarea></td>
                             
                             </tr>
                             
               </table>
               <button id="btn"type="submit" class="btn btn-sm submit-btn" formaction="AddPropertyReoprt.htm" formmethod="get" onclick="return vadidate()" style="margin-left: 40%;margin-top: 0px;">SUBMIT</button>
            </div>  
         </div>
     
</form>

</div>
</div>
	
<script>
	function vadidate(){
		  var description =$("#description").val();
		  var Address=$("#Address").val();
		  if(description==null || description.trim()==="" ){
				alert('Enter the description!');
				return false;	
			}else if(Address==null||Address.trim()==="" ){
				alert('Enter the Address!');
				return false;	
			}
			else{
				var x=confirm('Are You Sure Want To Submit');
				if(x)
					{
					return true;
					}else
						{
						return false;
						}
			}
	}
</script>
	
</body>
</html>
