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
						<div class="col-md-12" >
							<form action="#" method="POST" id="empForm" autocomplete="off">
						
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
													<td><input type="text"  class="form-control " name="<%=ItemName%>" id="<%=ItemName%>" maxlength="100" value="<%=obj[1]%>"> </td>
													<td align="center">
														<button type="submit" class="btn btn-sm "  name="itemid" value="<%=obj[0]%>" formaction="OtherItemAddEdit.htm" formmethod="POST" onclick ="return checkDuplicate('<%=ItemName%>')" data-toggle="tooltip" data-placement="top" title="Update">											
														<i class="fa-solid fa-pen-to-square " style="color: #E45826" ></i>
														</button>
													</td>
												</tr>
											 <%}}%> 
										</tbody>
									</table>
									<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
								</div>
							
									
								<div class="row text-center">
									<div class="col-md-12">
															
										<button type="button" class="btn btn-sm add-btn AddItem" name="action" value="ADD"  >ADD</button>
										<button type="submit" class="btn btn-sm add-btn" style="background-color:#67349e;" formaction="OtherItemAmount.htm" formmethod="GET" name="action"  >ITEM AMOUNT</button>
								    
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
      <form action="OtherItemAddEdit.htm" method="post" id="addform">
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
               <input type="text" class=" form-control w-100" maxlength="100"  id="ItemName"  name="ItemName" required="required" > 
      		</div>
      		</div>
        </div>
       <input type="hidden" name="action" value="ADDITEM"> 
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <!-- Modal footer -->
        <div class="modal-footer" >
        	<button type="submit"  class="btn btn-sm submit-btn" onclick ="return checkDuplicate()">SUBMIT</button>
        </div>
        </form>
      </div>
    </div>
  </div>
  
</div>
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
					return false;
				}else if(confirm("Are you sure to Submit!")){
					var $ItemName = $("#ItemName").val();
					
					if($ItemName=="null" || $ItemName==null ){
						alert("Enter Data Properly!");
						return false;
					}else{
						document.getElementById("addform").submit();
					}		
			   }
		  }
	});	
}
</script>

<script type="text/javascript">

function checkDuplicate(value)
{
	var $name = document.getElementById(value).value;	
	console.log($name);
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
					return false;
				}else if(confirm("Are you sure to Submit!")){
					var $ItemName =document.getElementById(value).value;	
					
					if($ItemName=="null" || $ItemName==null ){
						alert("Enter Data Properly!");
						return false;
					}else{
						document.getElementById("addform").submit();
					}		
			   }
		  }
	});	
}
</script>
</body>
</html>



