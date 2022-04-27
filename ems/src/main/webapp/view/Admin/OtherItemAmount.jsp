<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Other Item Amount</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>

<body>

<%
List<Object[]> itemlist = (List<Object[]>)request.getAttribute("itemlist");
List<Object[]> list = (List<Object[]>)request.getAttribute("list");
String tratementid = (String)request.getAttribute("tratementid");
int slno=0;
%>
<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Medicine List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>	
						<li class="breadcrumb-item active " aria-current="page">CHSS Medicine List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 <div class="card-body">
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
			 <div class="card">
				 <div class="card-body">
						<div class="row">
						<div class="col-4"></div>
						<div class="col-2" align="left" style="margin-right: -8%;"> <h5>Item Name :</h5></div>
						<form action="OtherItemAmount.htm" id="myform">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div class="col-2" align="left">
							
								<select class="form-control select2" name="tratementid" data-container="body" data-live-search="true"  onchange="this.form.submit();" style="width: 200px; align-items: center; font-size: 5px;">
							          
									<%if(itemlist!=null&&itemlist.size()>0){for(Object[] Obj:itemlist){%>
										<option value="<%=Obj[0]%>" <%if(tratementid.equalsIgnoreCase(Obj[0].toString())){%>selected="selected" <%}%>> <%=Obj[1]%></option>
												<%}}%>
						         </select>
							</div>
						</form>
						</div>
						<br>
						<form action="##" method="GET"> 
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div class="row">					
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;"  >SNo.          </th>
											<th style="width:20%;" >Basic From 	  </th>
											<th style="width:20%;" >Basic To      </th>
											<th style="width:20%;" >Permit Amount </th>
											<th style="width:5%;"  ><button type="button" class="btn tbl-row-add" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
									<%if(list!=null&&list.size()>0){  
										Long basicfrom =0L;
										for(Object[] obj:list){
											++slno;						    
											basicfrom =Long.parseLong(String.valueOf(obj[1]));	
											
											String admAmt1 ="admAmt1"+String.valueOf(obj[3]);  %>
									<tr>
									        <td><span class="sno" id="sno"><%=slno %></span></td>
											<td><input type="text" class="form-control items" name="basicfrom1" id="basicfrom1" value="<%=obj[0] %>" readonly="readonly"  maxlength="10" required="required"></td>
											<td><input type="text" class="form-control items" name="basicto1" id="basicto1" value="<%=obj[1] %>"  readonly="readonly"  maxlength="10" required="required"></td>
											<td><input type="text" class="form-control admAmt" name="<%=admAmt1%>" id="admAmt1" value="<%=obj[2] %>"      maxlength="10"  required="required"></td>
											<td><button type="submit" class="btn btn-sm" name="chssOtheramtid" value="<%=obj[3] %>" formaction="EDITOtherAmt.htm" formmethod="POST"   data-toggle="tooltip" data-placement="top" title="Edit">
												<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i></button></td>
									</tr>							
									<%}%> 
									<tr class="tr_clone" >
											<td><span class="sno" id="sno"><%=++slno%></span></td>
											<td><input type="text" class="form-control items" name="basicfrom" id="basicfrom" value="<%=++basicfrom %>" readonly  maxlength="10" ></td>
											<td><input type="text" class="form-control items" name="basicto" id="basicto" value=""    maxlength="100" ></td>
											<td><input type="text" class="form-control admAmt" name="admAmt" id="admAmt" value=""     maxlength="100"  ></td>
											<td><button type="button" class="btn tbl-row-rem"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button></td>
									</tr>
									
									<%}else {  Long basicfrom =0L;%>
										<tr class="tr_clone" >
											<td><span class="sno" id="sno"><%=++slno%></span></td>
											<td><input type="text" class="form-control items" name="basicfrom" id="basicfrom" value="<%=basicfrom%>" readonly="readonly"  maxlength="10" ></td>
											<td><input type="text" class="form-control items" name="basicto" id="basicto" value=""    maxlength="10" ></td>
											<td><input type="text" class="form-control admAmt" name="admAmt" id="admAmt" value=""     maxlength="10"  ></td>
											<td><button type="button" class="btn tbl-row-rem"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button></td>
										</tr>
										<%}%>
									</tbody>															
								</table>
						   </div>
						   <div class="row">
						   		<div class="col-12" align="center">
						   		<input type="hidden" name="Action" value="ADDAMT">
						   		<input type="hidden" name="treateid" value="<%=tratementid%>" > 
						   		  <button type="submit" class="btn btn-sm submit-btn" formaction="AddOtherItemAmount.htm" onclick="return AddAmt();">SUBMIT</button>	
						   		</div>
						   </div>
					  </form>						
				 </div>
			 </div>
		 </div>
</div>
</body>

<script type="text/javascript">

    var count="<%=slno%>";
  
	$("table").on('click','.tbl-row-add' ,function() 
	{
	   	var $tr = $('.tr_clone').last('.tr_clone');
	   	var $clone = $tr.clone();
	   	$tr.after($clone);
	   	$clone.find(".items").val("").end();
	
		++count;
		
		$clone.find(".sno").html(count).end(); 
		 
	  $('[data-toggle="tooltip"]').tooltip({
			 trigger : 'hover',
		});
	  
	});

	$("table").on('click','.tbl-row-rem' ,function(){
	var cl=$('.tr_clone').length;
		if(cl>1){
		          
		   var $tr = $(this).closest('.tr_clone');
		   var $clone = $tr.remove();
		   $tr.after($clone);
		}
	});

	

</script>
<script type="text/javascript">
setPatternFilter($("#basicfrom"), /^-?\d*$/);
setPatternFilter($("#basicto"), /^-?\d*$/);
setPatternFilter($("#admAmt"), /^-?\d*$/);
setPatternFilter($("#admAmt1"), /^-?\d*$/);
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

$(function () {
	/*    $( "#basicfrom" ).change(function() {
	      var max = parseInt($(this).attr('max'));
	      var min = parseInt($(this).attr('min'));
	      if ($(this).val() > max)
	      {
	          $(this).val(max);
	      }
	      else if ($(this).val() < min)
	      {
	          $(this).val(min);
	      }       
	    }); */ 
	   
	   $("#basicto").change(function(){
		   var from = parseInt($("#basicfrom").val());
		   var to = parseInt($("#basicto").val());
		   
		   if(to <= from){
			   alert("Basic-To Amount Should be Greater Than Basic-from Amount");
			   $("#basicto").val("");
		   }
	   });
	});
	
 function AddAmt()
{
	  var admAmt1 = $("#admAmt").val();
	  var basicto = $("#basicto").val();
	  console.log(admAmt1);
	  console.log(basicto);
	  if(basicto==null||basicto==""||basicto=="null" ||admAmt1==""||admAmt1==null||admAmt1=="null")
	  {
		   alert("Enter the Data Properly!");
		   return false;
	  }else{
		  if(confirm("Are You Sure To Submit!")){
			  return true;
		  }else{
			  return false;
		  }
	  }
	  
	
}
	
</script>
</html>