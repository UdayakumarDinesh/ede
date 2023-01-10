<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import=" java.time.LocalDate" %> 
      <%@ page import="com.ibm.icu.math.BigDecimal" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Edit Property Details  </title>
</head>
<body>
       <%List<Object[]> list=(List<Object[]>)request.getAttribute("list"); %>
       <%for(Object []obj:list){ 
       
       %>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
			      <h5> Edit Property Details </h5>
			</div>
			   <div class="col-md-7 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li  class="breadcrumb-item "><a href="PropertyReport.htm"> Property Details List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Edit  Property Details   </li>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="page card dashboard-card" style="min-height: 650px;max-height: 660px; overflow: hidden;">
     <div class="card-body" style="overflow: hidden;" >  
     <div style="margin-left: 15%;margin-top: 0px; font-weight: bold;">FORM FOR STATEMENT OF IMMOVABLE PROPERTY FOR THE YEAR ENDING 31<sup style="font-size: 8px;">ST</sup> DECEMBER &nbsp;<%=LocalDate.now().minusYears(1).getYear() %> </div>  
 <form >	 
		<div class="form-group">
			<div class="table-responsive">
			      
				<table class="table table-bordered table-hover table-striped table-condensed " style="width: 65%; margin-left: 15%; margin-top: 0px;">
						
						 <input type="hidden" name="PropertyId" value="<%= obj[0]%>">
						<tr>
                            <th> <label> Description<span class="mandatory"	style="color: red;">*</span></label> </th>
                             <td><textarea name="description" id="description" rows="2" cols="2"  class="form-control form-control" maxlength="255"  placeholder="Enter Description" required="required"><%=obj[1] %></textarea></td>
                        </tr> 
                        <tr>
                            <th> <label> Address<span class="mandatory"	style="color: red;">*</span></label> </th>
                             <td><textarea name="Address" id="Address"rows="2" cols="2"  class="form-control form-control" maxlength="500" placeholder="Enter Address" required="required"><%=obj[2] %></textarea></td>
                        </tr> 
                         <tr>
                         <%BigDecimal bd = new BigDecimal(obj[3].toString());
                        %>
                              <th> <label>Present Property value <span class="mandatory"	style="color: red;">*</span> </label></th>
                              <td><input type="number" max="100000000000000" min="1" id="propertyvalue" onblur="checknegative(this) " value="<%=bd.longValue() %>" name="propertyvalue" 
                              class=" form-control form-control " placeholder="Enter Property value" required="required"></td>
                         </tr>
                         
                            <tr>
                            <th >
                            <label> Mention Partner Name and Relationship </label></th>
                          <td>  <textarea name="PartnernameAndRelationship" maxlength="200" rows="2" cols="1" class=" form-control form-control"  placeholder="Enter ParterName and Relationship"><%=obj[4] %></textarea></td>
                            </tr>
                            <tr>
                            <th>
                            <label> Mode of Property Owned <span class="mandatory"	style="color: red;">*</span> </label></th>
                          <td>  <select class=" form-control select2" name="propertymode" required data-live-search="true" >

                            <option value="Purchased"<%if(("Purchased").equalsIgnoreCase(obj[5].toString())){ %>selected="selected"<%} %>>Purchased </option>
                            <option value="Lease"<%if(("Lease").equalsIgnoreCase(obj[5].toString())){ %>selected="selected"<%} %>> Lease</option>
                            <option value="Inherited"<%if(("Inherited").equalsIgnoreCase(obj[5].toString())){ %>selected="selected"<%} %>> Inherited</option>
                            <option value="Mortgage"<%if(("Mortgage").equalsIgnoreCase(obj[5].toString())){ %>selected="selected"<%} %>>Mortgage</option>
                            <option value="Gift"<%if(("Gift").equalsIgnoreCase(obj[5].toString())){ %>selected="selected"<%} %>>Gift</option>
                            </select></td>
                            </tr>
                            <tr>
                            
                          <!--  <th> <label>Property Acquired From </label></th>
                            <td> <input type="text" name="propertyacqFrom" value=""class=" form-control form-control" placeholder="Enter Property Acquired From"></td>
                            </tr> -->
                            <%BigDecimal bda = new BigDecimal(obj[6].toString());%>
                             <tr>                            
                            <th> <label>Annual Income from Property<span class="mandatory"	style="color: red;">*</span> </label></th>
                            <td> <input type="number" max="100000000000000" min="0" name="AnnualIncome" value="<%=bda.longValue()%>" id="Annualincome" required="required" onblur="checknegative(this) " class=" form-control form-control" placeholder="Enter Annual Income from property"></td>
                             </tr>
                             
                             <tr>
                            <th>  <label>Remarks</label></th>
                             <td><textarea cols="1"rows="1" name="remarks" maxlength="1000" class=" form-control " placeholder="Enter Remarks"><%=obj[7] %></textarea></td>
                             
                             </tr>
                              <%} %>
               </table>
            </div>  
         </div>
    <button type="submit" class="btn btn-sm submit-btn" formaction="UpdatePropertyDetails.htm" formmethod="get" onclick="return vadidate()" style="margin-left: 40%">SUBMIT</button>
</form>

</div>
</div>
<script>
function vadidate(){
	  var description =$("#description").val();
	  var Address=$("#Address").val();
	  if(description==null || description.trim()=="" ){
			alert('Enter the description!');
			return false;	
		}else if(Address==null||Address.trim()=="" ){
			alert('Enter the Address!');
			return false;	
		}else{
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