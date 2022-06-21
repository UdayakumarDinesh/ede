<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Other Items List</title>
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Other Items List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">Other Items List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		<%List<Object[]> list = (List<Object[]>)request.getAttribute("itemlist"); %>
	
	 <div class="page card dashboard-card">
	<div class="card-body" >		
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
		
			<div class="card" >
			
				<div class="card-body" >
					<div class="row">
						<div class="col-md-12">
							<form action="OtherItemAddEdit.htm" method="POST" id="empForm" autocomplete="off">
						
								<div class="table-responsive">
						   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
										<thead>
											<tr>
												<th>SN</th>
												<th>Item Name</th>	
												<th>Update</th>
											</tr>
										</thead>
										<tbody>
											<%if(list!=null){ int slno=0; for(Object[] obj : list){
												String ItemName = "ItemName"+String.valueOf(obj[0]); 
												%>
												<tr>
													<td style="text-align: center;"><%=++slno %>. </td>
													<td><input type="text"  class="form-control " name="<%=ItemName%>" id="<%=ItemName%>" maxlength="100" value="<%=obj[1]%>" style="text-transform:capitalize;"> </td>
													<td align="center">
														<button type="button" class="btn btn-sm "  name="itemid" value="<%=obj[0]%>"  onclick ="return checkDuplicateItem('<%=ItemName%>','<%=obj[0]%>')" data-toggle="tooltip" data-placement="top" title="Update">											
														<i class="fa-solid fa-pen-to-square " style="color: #E45826" ></i>
														</button>
													</td>
												</tr>
											 <%}}%> 
										</tbody>
									</table>
									<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
									<input type="hidden" id="itemid" name="item" value="">
								</div>
							
									
								<div class="row text-center">
									<div class="col-md-12">
															
										<button type="button" class="btn btn-sm add-btn AddItem" name="action" value="ADD"  >ADD</button>
										<button type="submit" class="btn btn-sm misc1-btn" formaction="OtherItemAmount.htm" formmethod="POST" name="action"  >ITEM AMOUNT</button>
								    
								    </div>						 
								</div>
								
							  </form>
						  </div>
		   			</div>				
	        	</div>
           </div>
          </div>
          </div> 

           <!-- container -->
<div class="container">

  <!-- The Modal -->
  <div class="modal" id="myModal">
    <div class="modal-dialog">
      <div class="modal-content">
      <form action="OtherItemAdd.htm" method="post" id="addform" autocomplete="off">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Other Items Add</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <!-- Modal body -->
        <div class="modal-body">
        	<div class="form-inline">
        	<div class="form-group w-100">
               <label>Item Name : &nbsp;&nbsp;&nbsp;</label> 
               <input type="text" class=" form-control w-100" maxlength="100" style="text-transform:capitalize;"  id="ItemName"  name="ItemName" required="required" > 
      		</div>
      		</div>
        </div>
      
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <!-- Modal footer -->
        <div class="modal-footer" >
        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick ="return checkDuplicate()">SUBMIT</button>
        </div>
        </form>
      </div>
    </div>
  </div>
  
</div>
<%if(list!=null){%>
								<!--------------------------- container ------------------------->
			<div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal1">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     	<form action="OtherItemEdit.htm" method="POST" id="empForm" autocomplete="off" enctype="multipart/form-data" >	
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
						               <input type="file" class=" form-control w-100"   id="file" name="selectedFile"  > 
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
					         <input type="hidden" id="value1" name="itemid" value=""/>
					          <input type="hidden" id="value2" name="itemname" value=""/>
					          <input type="hidden" name="" value=""/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       </form>
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
					<%}%>

<script type="text/javascript">
$(".AddItem").click(function(){ 
	
		 $('#myModal').modal('show');
});

</script>
 <script type="text/javascript">
			 function Edit(myfrm) {
			
				var fields = $("input[name='itemid']").serializeArray();
			
				  if (fields.length === 0) {
				        alert("Please Select Atleast One Item ");
				          event.preventDefault();
				        return false;
				    }
				        return true;
				 }
</script>


<script type="text/javascript">
function checkDuplicate()
{
	var $name = $("#ItemName").val();	
	var retValue = false;
		$.ajax({
			type : "GET",
			url : "DuplicateOtherItem.htm",	
			datatype : 'json',
			data : {
				treatmentName : $name,
				
			},
			success :  function(result){
				 var rr=result;
                 var a = parseInt(rr) ;
				
				if(a > 0){					
					alert("Item Already Exist!");
					retValue= false;
				}else if(confirm("Are you sure to Submit!")){
					var $ItemName = $("#ItemName").val();
					
					if($ItemName.trim()=="null" || $ItemName.trim()==null || $ItemName.trim()==''){
						alert("Enter Item Name!");
						retValue= false;
					}else{
						retValue = true;
						document.getElementById("addform").submit();
					}		
			   }
		  }
	});	
		
		if(retValue==true){
			
			document.getElementById("addform").submit();
		}else{
			return retValue;
		}	
}
</script>

<script type="text/javascript">

function checkDuplicateItem(value , itemid)
{
	var $name = document.getElementById(value).value;
	$("#itemid").val(itemid);
	var retValue = false;

		$.ajax({
			type : "GET",
			url : "DuplicateOtherItem.htm",	
			datatype : 'json',
			data : {
				treatmentName : $name,				
			},
			success :  function(result){
				 var rr=result;
                 var a = parseInt(rr) ;
				
				if(a > 0){					
					alert("Item Already Exist!");
					retValue= false;
				}else{
					var $ItemName =document.getElementById(value).value;	
					
					if($ItemName.trim()=="null" || $ItemName.trim()==null || $ItemName.trim()==''){
						alert("Enter Item Name!");
						retValue= false;
					}else{
						$("#value1").val(itemid);
						$("#value2").val($("#"+value).val());
						 $('#myModal1').modal('show');
						 retValue= true;
					}		
			   }
		  }
	});	
		
		if(retValue==true){
			
			document.getElementById("empForm").submit();
		}else{
			return retValue;
		}	
}
</script>
</body>
</html>



