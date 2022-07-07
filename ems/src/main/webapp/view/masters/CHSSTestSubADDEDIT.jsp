<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@page import="java.util.List"%>
       <%@page import="com.vts.ems.chss.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>CHSS </title>
</head>
<body>


<%
List<Object[]> main =(List<Object[]>)request.getAttribute("TestMain");
CHSSTestSub list = (CHSSTestSub)request.getAttribute("subdata");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(list!=null){ %>
				<h5>CHSS Test Edit</h5>
				<%}else{ %>
				<h5>CHSS Test Add</h5>
				<%}%>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<!-- <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> -->
						<li class="breadcrumb-item "><a href="TestSub.htm"> CHSS Test List </a></li>
						<%if(list!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Test Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Test Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
	
	 <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body main-card  " align="center" >
					<%if(list!=null){ %>
					<form name="myfrm" action="ChssTestSubEdit.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data" >
					<%}else{ %>	
					<form name="myfrm" action="ChssTestSub.htm" method="POST" id="myfrm1" autocomplete="off">
					<%}%>	
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									
										<tr>
											<th><label>Main <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2" name="Main" id="Main" data-container="body" data-live-search="true"  style="font-size: 5px;" required>
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(main!=null&&main.size()>0){for(Object[] O:main){ %>
												<option value="<%=O[0]%>" <%if(list!=null){if(list.getTestMainId()==Long.parseLong(O[0].toString())){%> selected <%}}%>> <%=O[1]%></option>
												<%}}%>
											</select></td>
										</tr>
										<tr>
											<th><label>Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Name" type="text" name="Name" value="<%if(list!=null){ %><%=list.getTestName()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px; text-transform:capitalize;"
												id="Name" ></td>
										</tr>
										
	                                     <tr>	
											<th><label> Code   <span class="mandatory" style="color: red;"> *</span>
											</label></th>
											<td><input class="form-control form-control"
												placeholder="Enter Code" type="text" id="TestCode" name="TestCode" value="<%if(list!=null){%><%=list.getTestCode()%><%}%>"
												required="required" maxlength="10" style="font-size: 15px;"
												id="Rate" onblur="return checkCode();"></td>
										</tr>
									
										<tr>
											<th><label> Rate  <i class="fa fa-inr" aria-hidden="true"></i> <span class="mandatory" style="color: red;"> *</span>
											</label></th>
											<td><input class="form-control form-control"
												placeholder="Enter Rate" type="text" id="RateValue" name="Rate" value="<%if(list!=null){%><%=list.getTestRate()%><%}%>"
												required="required" maxlength="10" style="font-size: 15px;"
												id="Rate" ></td>
										</tr>
	
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(list!=null){ %>
							<input type="hidden" name="SubId" value="<%=list.getTestSubId()%>">
								<button type="submit" class="btn btn-sm submit-btn "
									onclick="return checkDuplicate1();"
									name="action"  value="EDITTEST">SUBMIT</button>
									<%}else{ %>
									<button type="submit" class="btn btn-sm submit-btn"
									onclick="return checkDuplicate();"
									name="action"  value="ADDTEST">SUBMIT</button>
									
									<%} %>
							</div>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						
			<%if(list!=null){ %>
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="EDITTEST" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
			<!----------------------------- container Close ---------------------------->	
						<%}%>
						<%if(list!=null){%></form><%}else{ %></form><%} %>
				  
				</div>
	   </div>
	</div>

</div>
<script type="text/javascript">
setPatternFilter($("#RateValue"), /^-?\d*$/);
setPatternFilter($("#TestCode"), /^-?\d*$/);
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
</script>

<script type="text/javascript">
function checkDuplicate()
{
	var $name = $("#Name").val();	
	var $mainid =$("#Main").val();
	var $rate = $("#RateValue").val();
	var $code = $("#TestCode").val();
	
	var retValue = false;
		$.ajax({
			type : "GET",
			url : "DuplicateTest.htm",	
			datatype : 'json',
			data : {
				testName : $name,				
			},
			success :  function(result){
				 var rr=result;
                 var a = parseInt(rr) ;
				
				if(a > 0){					
					alert("Test Name Already Exist!");
					retValue = false;
				}else if(confirm("Are you sure to Submit!")){
					var $testname = $("#Name").val();
					
					if ($mainid =="null" ||  $mainid==null|| $mainid == "" || $mainid == " " ) {
						alert("Select Test Main !");
						retValue = false;
					}else if($testname=="null" || $testname==null ||  $testname== "" || $testname== " " ){
						alert("Enter Test Name !");
						retValue = false;
					}else  if ($code=="null" || $code==null || $code == "" || $code == " "){ 
						alert("Enter Code !");
						retValue = false;
					}else if ($rate=="null" || $rate==null || $rate == "" || $rate == " " ) {
						alert("Enter Rate !");
						retValue = false;
					}else{
						retValue = true;
						document.getElementById("myfrm1").submit();
					}
		
			    }
		   }
	 });	
		if(retValue==true){
			
			document.getElementById("myfrm1").submit();
		}else{
			return retValue;
		}
}
</script>

<script type="text/javascript">

function checkDuplicate1()
{
	var $name = $("#Name").val();	
	var $mainid = $("#Main").val();
	var $rate = $("#RateValue").val();
	var $code = $("#TestCode").val();
	
	var retValue = false;
		$.ajax({
			type : "GET",
			url : "DuplicateTest.htm",	
			datatype : 'json',
			data : {
				testName : $name,				
			},
			success :  function(result){
				 var rr=result;
                 var a = parseInt(rr) ;
				
				if(a > 1){					
					alert("Test Name Already Exist!");
					retValue = false;
				}else{
					var $testname = $("#Name").val();
					
					if ($mainid =="null" ||  $mainid==null|| $mainid == "" || $mainid == " " ) {
						alert("Select Test Main !");
						retValue = false;
					}else if($testname=="null" || $testname==null ||  $testname== "" || $testname== " " ){
						alert("Enter Test Name !");
						retValue = false;
					}else  if ($code=="null" || $code==null || $code == "" || $code == " "){ 
						alert("Enter Code !");
						retValue = false;
					}else if ($rate=="null" || $rate==null || $rate == "" || $rate == " " ) {
						alert("Enter Rate !");
						retValue = false;
					}else{
						retValue = true;
						$('#myModal').modal('show');
					}
		
			    }
		   }
	 });	
		if(retValue==true){
			
			document.getElementById("myfrm1").submit();
		}else{
			return retValue;
		}
}
</script>
<script type="text/javascript">

function checkCode()
{
	var $code = $("#TestCode").val();	

	var retValue = false;
		$.ajax({
			type : "GET",
			url : "DuplicateTestCode.htm",	
			datatype : 'json',
			data : {
				TestCode : $code,				
			},
			success :  function(result){
				 var rr=result;
                 var a = parseInt(rr) ;				
				if(a >= 1){					
						alert("Test Code Already Exist!");
						$("#TestCode").val('');
					    retValue = false;
					
				     }else {
						   retValue = true;					
					  }	
		    }  
	 });	
		if(retValue==true){
			
			return true;
		}else{
			return retValue;
		}
}

</script>

</body>
</html>