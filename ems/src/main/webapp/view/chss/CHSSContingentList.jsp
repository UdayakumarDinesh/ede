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
	long itemstotal=0,totalremamount=0; %>
 
 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Approval List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Contingent List</li>
					</ol>
				</div>
			</div>
	</div>	
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
				<div class="card-body " >
					<div class="row" >
						
							<div class="col-12"  align="right" >
							<form action="CHSSContingentList.htm" method="get">
								<button class="btn btn-sm" style="background-color: #FFD36E " >Contingent Lists</button>
								</form>
							</div>
						
					</div>
					
					
					<form action="CHSSClaimsApprove.htm" method="post" id="ClaimForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<br>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id=""> 
								<thead>
									<tr>
										<th style="text-align: center;padding-top:5px; padding-bottom: 5px;">
											<input type="checkbox" class="" name="allcheck" value="000" id="select_all" checked >
										</th>
										<th style="padding-top:5px; padding-bottom: 5px;">Claim No</th>
										<th style="padding-top:5px; padding-bottom: 5px;">Employee</th>
										<th style="padding-top:5px; padding-bottom: 5px;">Patient Name</th>
										<th style="padding-top:5px; padding-bottom: 5px;">Applied Date</th>
										<th style="padding-top:5px; padding-bottom: 5px;width:10%;">Claim Amount (&#8377;)</th>
										<th style="padding-top:5px; padding-bottom: 5px;width:10%;">Admissible Amount (&#8377;)</th>
										<th style="padding-top:5px; padding-bottom: 5px;width:10%;">Action</th>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
									for(Object[] obj : chssclaimlist){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" >
												<input type="checkbox" class="checkbox" name="chssapplyidcb" value="<%=obj[0] %>" checked>
											</td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[19] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[12] %></td>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
											<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=obj[25] %></td>
											<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=obj[26] %></td>
											
											<td style="padding-top:5px; padding-bottom: 5px;">
												
												<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
													<i class="fa-solid fa-eye"></i>
												</button>	
												
												<%-- 
												<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEmpDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
													<i style="color: #019267" class="fa-solid fa-download"></i>
												</button>
												
												<input type="hidden" name="isapproval" value="Y">	
												 --%>					
											</td>
										</tr>
									<%
									itemstotal += Integer.parseInt(obj[25].toString());
									totalremamount +=Integer.parseInt(obj[26].toString());
									} %>
									
									<%if(chssclaimlist.size()>0){ %>
										<tr>
											<td colspan="5" style="text-align: center;">
												
											</td>
											<td style="text-align: right ;">
												<%=itemstotal %>
											</td>
											<td style="text-align: right;">
												<%=totalremamount %>
											</td>
											<td></td>
										</tr>
									<%} %>
								</tbody>
							</table>
							<input type="hidden" name="isapproval" value="N">
						</div>
						
					<%if(chssclaimlist.size()>0){ %>
					 	<!-- <div class="row">
							<div class="col-md-12">
								Remarks : <br>
								<textarea class="w-100 form-control" rows="4" cols="100" id="remarks" name="remarks" maxlength="500" ></textarea>
							</div>
						</div> -->
						<div class="col-md-12" align="center" style="margin-top: 5px;">
							<button type="submit" class="btn btn-sm submit-btn" name="claimaction" value="F" onclick="return checklength('F'); " >Approve</button>
							<!-- <button type="submit" class="btn btn-sm delete-btn" name="claimaction" value="R" onclick="return remarkRequired('R'); " >Return</button> -->
						</div>
					<%} %>
					 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					 	<input type="hidden" id="form2-chssapplyid" name="chssapplyid" value="">
					 </form>
					
				</div>
			</div>		
			
		</div>
	
	 </div>
	
	 
<script type="text/javascript">


function checklength()
{
	if($('input:checked').length > 0){
		/* if(confirm('Are you sure to Forward ? '))
		{
			$('#ClaimForm').submit();
		} */
		return confirm('Are you sure to Approve ? ');
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

/* $("#checkAll").click(function(){
    $('input:checkbox').not(this).prop('checked', this.checked);
}); */
</script>


</body>
</html>