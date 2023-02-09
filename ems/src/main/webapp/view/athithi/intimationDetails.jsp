<%@page import="java.io.File"%>
<%@page import="java.util.Base64"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">


</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5></h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
					</ol>
				</div>
			</div>
	</div>	
<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
	<div align="center">
		<div class="alert alert-danger" role="alert">
        	<%=ses1 %>
        </div>
   	</div>
	<%}if(ses!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
        	<%=ses %>
        </div>
    </div>
	<%} %>
   
<%
List<Object[]> intimationDetail =(List<Object[]>)request.getAttribute("intimationDetail");
List<Object[]> intimationVisitor =(List<Object[]>)request.getAttribute("intimationVisitor");

SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

%>
<div class="page card dashboard-card">
   <div class="card-body" >
  		

	
				<div class="card shadow-nohover">
					<div class="card-header">
						<h5>
							  This gate pass intimation has been sent by&nbsp;<%=intimationDetail.get(0)[8]%>(<%=intimationDetail.get(0)[9]%>)&nbsp; for <%=intimationDetail.get(0)[7]%>&nbsp;
					<%if(intimationDetail.get(0)[6].toString().length()!=0){ %>with the special permission <%=intimationDetail.get(0)[6]%> <%}%>
					
					</h5> 
					
					</div>
					<form action="createPassSubmit" id="passForm" method="post" onSubmit="return confirm('Do you want to submit?') ">
					<div class="card-body" style="height:70vh; overflow-y:auto; ">
	
						<table class="table table-bordered table-hover table-striped table-condensed">
							<thead>
								<tr>
								    <th style="width: 4%;">Select</th>
									<th style="width: 20%;">Visitor Name</th>
									<th style="width: 20%;">Designation</th>
									<th style="width: 20%;">Mobile</th>
									<th style="width: 10%;" >Batch No</th>
									<th>Photo</th>
								</tr>
							</thead>
						
							<tbody>
						
							<%for(Object[] visitor:intimationVisitor){ %>
							<tr>
									<td><div class="form-check" ><input type="checkbox" name="visitorEmpId[]" class="form-check-input checkClass" id="checkbox<%=visitor[0]%>" value="<%=visitor[0]%>" onclick="checkPhoto(<%=visitor[0]%>)"></div></td>
									<td><%=visitor[1]%></td>
									<td><%=visitor[2]%></td>
									<td><%=visitor[3]%></td>
									<td><input class= "form-control" type="number" name='batchNumber[]' id="batchId<%=visitor[0]%>" min="10" max="999"></td>
									<td>
									<input  type="hidden" id="visitorId<%=visitor[0]%>" name="visitorId" value="<%=visitor[0]%>">
									<div class="row"  id="photoDiv<%=visitor[0]%>" style="margin-left: 1rem;">
									<%if(visitor[4]==null){ %>
									    <button class="btn btn-primary btn-sm"  style="height: 27px;" onclick="onCamera(<%=visitor[0]%>)">Open Camera</button>
									<%} else{
										byte[] fileContent = FileUtils.readFileToByteArray(new File("D:\\visitorPhoto\\"+visitor[4]));
										String encodedString = Base64.getEncoder().encodeToString(fileContent);
									%>
									<img src="data:image/jpeg;base64,<%=encodedString%>" alt="visitorPhoto<%=visitor[4]%>">
									<%} %>
									<input type="hidden" id="isPhoto<%=visitor[0]%>" value="<%=visitor[4]%>">
										<input type="hidden" name="IntimationId" value="<%=intimationDetail.get(0)[0]%>">
									</div>
									</td>
						          </tr>
								<%} %>				
							</tbody>
						</table>
					</div>
					<div class="card-footer text-muted">
                  <div align="center">
                  	<input type="hidden" name="IntimationId" value="<%=intimationDetail.get(0)[0]%>">
                  <button class="btn btn-success" id="submitButton" type="submit" >Submit </button>
                  
                  </div>
  </div>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	async function onCamera(e){
		event.preventDefault();
		var x="<video autoplay='true' id='video"+e+"'" +"width='110' height='110'></video>"+"<canvas name='photo' id='canvas"+e+"'" +" width='90' height='90' ></canvas>"+ "<button class='btn btn-success btn-sm' style='height: 27px;' id='capture"+e+"'"+">CAPTURE</button>";
		 $("#photoDiv"+e).html(x);
	    try{
	        const videoSrc=await navigator.mediaDevices.getUserMedia({video:true});
	        var video=document.getElementById("video"+e);
	        video.srcObject=videoSrc;
	    }catch(e){
	        console.log(e);
	    }
	    
	    var capture=document.getElementById("capture"+e);
	    var canvas=document.getElementById("canvas"+e);
	    var snapshot = document.getElementById( "snapshot"+e );
	    var context=canvas.getContext('2d');
	    var img = new Image();
	    capture.addEventListener("click",function(){
	    	event.preventDefault();
	    	 $("#video"+e).remove();
	    	 $("#capture"+e).remove();
	        context.drawImage(video,0,0,90,90);
	        var canvas=document.getElementById("canvas"+e);
	        var base64=canvas.toDataURL();
	        base64=base64.replace(/^data:image\/(png|jpg);base64,/, "");
	        var visitor=$('#visitorId'+e).val();
	        $.ajax({
				type : "post",
				url : "uploadPhoto",
				data : {
					project : base64,
					vistoriId:visitor 
				},
				datatype : 'json',
				success : function(result) {
					$('#isPhoto'+e).val(e);
				}
				});	
	    });
	}
	</script>
	<script type="text/javascript">
	function checkPhoto(id){
	var vadil=$('#isPhoto'+id).val();
	if(vadil=='null'){
		alert("please upload photo first");
		$('#checkbox'+id).prop('checked', false);
		return false;
	}
		
		    if($('#checkbox'+id).is(":checked")) {
		    	
		    	$('#batchId'+id).prop('required',true);
		    }else{
		    	
		    	$('#batchId'+id).prop('required',false);
		    }
		}
	</script>
	<script type="text/javascript">
	$( "#submitButton").click(function(){
	
		var visitorIds = [];
		var visitorBatch=[];

		$('input.checkClass:checkbox:checked').each(function () {
			visitorIds.push($(this).val());
			visitorBatch.push($('#batchId'+$(this).val()).val());
		});
		if(visitorIds.length<=0){
			alert("Please select one visitor");
			return false;
		}
		
		$("<input />").attr("type", "hidden")
        .attr("name", "visitorIds[]")
        .attr("value",visitorIds)
        .appendTo("#passForm");
		
		$("<input />").attr("type", "hidden")
        .attr("name", "visitorBatch[]")
        .attr("value",visitorBatch)
        .appendTo("#passForm");
		
	});
	
	</script>
</body>


</html>