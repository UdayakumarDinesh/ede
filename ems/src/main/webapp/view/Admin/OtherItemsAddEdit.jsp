<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page  import="com.vts.ems.chss.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>Other Items</title>
</head>
<body>



<%CHSSOtherItems item=(CHSSOtherItems)request.getAttribute("Item"); %>

<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(item!=null){ %>
				<h5>OtherItems Edit</h5>
				<%}else{ %>
				<h5>OtherItems Add</h5>
				<%}%>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="OtherItems.htm"> Other Items List </a></li>
						<%if(item!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Other Items Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Other Items Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
	</div>
	
	
		<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<form name="myfrm" action="OtherItemAddEdit.htm" method="POST">
						<div class="form-group">
							<div class="table-responsive">
								<table
									class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									<thead>
																		
										<tr>
											<th><label>Item Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Item Name" type="text" name="ItemName" value="<%if(item!=null){ %><%=item.getOtherItemName()%><%}%>"
												required="required"  style="font-size: 15px;"
												id="Name" ></td>
										</tr>
										<tr>
											<th><label>PayLevel-1 <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter PayLevel-1" type="text" name="level1" value="<%if(item!=null){ %><%=item.getPayLevel1()%><%}%>"
												required="required"  maxlength="12" style="font-size: 15px;"
												id="PayLevel" ></td>
										</tr>
										<tr>
											<th><label>PayLevel-2 <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter PayLevel-2" type="text" name="level2" value="<%if(item!=null){ %><%=item.getPayLevel2()%><%}%>"
												required="required" maxlength="12" style="font-size: 15px;"
												id="PayLeve2" ></td>
										</tr>
										<tr>
											<th><label>PayLevel-3 <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter PayLevel-3" type="text" name="level3" value="<%if(item!=null){ %><%=item.getPayLevel3()%><%}%>"
												required="required" maxlength="12" style="font-size: 15px;"
												id="PayLeve3" ></td>
										</tr>
										<tr>
											<th><label>PatLevel-4 <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter PayLevel-4" type="text" name="level4" value="<%if(item!=null){ %><%=item.getPayLevel4()%><%}%>"
												required="required"  maxlength="12" style="font-size: 15px;"
												id="PayLeve4" ></td>
										</tr>
										
	
									</thead>
								</table>
							</div>
						</div>
							<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(item!=null){ %>
						 	<input type="hidden" name="itemid" value="<%=item.getOtherItemId()%>"> 
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
									name="action"  value="EDITITEM">SUBMIT</button>
									<%}else{ %>
									<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
									name="action"  value="ADDITEM">SUBMIT</button>
									
									<%} %>
							</div>

						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					</form>
				</div>
	   </div>
	</div>	
	<script type="text/javascript">
setPatternFilter($("#PayLevel"), /^-?\d*$/);
setPatternFilter($("#PayLeve2"), /^-?\d*$/);
setPatternFilter($("#PayLeve3"), /^-?\d*$/);
setPatternFilter($("#PayLeve4"), /^-?\d*$/);
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
</body>
</html>