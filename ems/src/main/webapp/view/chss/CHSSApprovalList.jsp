<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>

<%
	
	
	List<Object[]> chssclaimlist=(List<Object[]>)request.getAttribute("chssclaimlist");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
%>
 
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Pending Approvals List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Approval List</li>
					</ol>
				</div>
			</div>
	</div>	
	
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
				<div class="card-body main-card " >
					
					<form action="CHSSApprovalForward.htm" method="post" id="ClaimForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<br>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<td style="text-align: center;padding-top:5px; padding-bottom: 5px;">
											SN
											<!-- <input type="checkbox" class="" name="allcheck" value="000" id="select_all" checked > -->
										</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Claim No</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Employee</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Patient Name</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Claim Date</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Action</td>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
									for(Object[] obj : chssclaimlist){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" >
												<%=slno %>
												<%-- <input type="checkbox" class="checkbox" name="chssapplyidcb" value="<%=obj[0] %>" checked> --%>
											</td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[19] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[12] %></td>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
											<td >
											
												 <button class="btn btn-sm btn-link w-100 " formaction="Chss-Status-details.htm" name="chssapplyid" value="<%=obj[0]%>" formtarget="_blank" 
													 data-toggle="tooltip" data-placement="top" title="Transaction History"
														<%if("1".equals(obj[9].toString()) || "2".equals(obj[9].toString()) ){%>  
														    style=" color:#2155CD; font-weight: 600;"				
															<%}else if("3".equals(obj[9].toString())||"5".equals(obj[9].toString()) ||"7".equals(obj[9].toString())||"9".equals(obj[9].toString()) || "11".equals(obj[9].toString())||"13".equals(obj[9].toString())){%>
															 style="  color:#B20600; font-weight: 600;"     
															<%}else  if("4".equals(obj[9].toString())||"6".equals(obj[9].toString())||"8".equals(obj[9].toString())|| "10".equals(obj[9].toString()) || "12".equals(obj[9].toString())){%>
																style=" color:#F66B0E; font-weight: 600;"
															<%}else if("14".equals(obj[9].toString())){ %>
															style=" color:#125C13; font-weight: 600;"
															<%}else{ %>
															 style=" color:#4700D8; font-weight: 600;"
															<%} %>
													>  &nbsp;<%=obj[18] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
											</td>
											
											
											<td style="padding-top:5px; padding-bottom: 5px;">
												
												<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
													<i class="fa-solid fa-eye"></i>
												</button>	
												
												<input type="hidden" name="isapproval" value="Y">
												<input type="hidden" name="show-edit" value="Y">
																
											</td>
										</tr>
									<%} %>
									
								</tbody>
							</table>
							<input type="hidden" name="isapproval" value="Y">
						</div>

					</form>
					
					
				</div>
			</div>		
			
		</div>
	
	 </div>
	 <form action="CHSSUserForward.htm" method="post" id="form2">
	 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	 	<input type="hidden" name="claimaction" value="F">
	 	<input type="hidden" id="form2-chssapplyid" name="chssapplyid" value="">
	 </form>
	 
<script type="text/javascript">

function checklength()
{
	if($('input:checked').length > 0){
		/* if(confirm('Are you sure to Forward ? '))
		{
			$('#ClaimForm').submit();
		} */
		return confirm('Are you sure to Forward ? ');
	}else
	{
		alert('Please Select Atleast One Claim!');
		return false;
	}
}

$(document).ready(function(){
    $('#select_all').on('click',function(){
        if(this.checked){
            $('.checkbox').each(function(){
                this.checked = true;
            });
        }else{
             $('.checkbox').each(function(){
                this.checked = false;
            });
        }
    });
    
    $('.checkbox').on('click',function(){
        if($('.checkbox:checked').length == $('.checkbox').length){
            $('#select_all').prop('checked',true);
        }else{
            $('#select_all').prop('checked',false);
        }
    });
})

</script>


</body>
</html>