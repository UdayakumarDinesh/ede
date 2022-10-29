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
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

<%
	List<Object[]> chssclaimlist=(List<Object[]>)request.getAttribute("chssclaimlist");
	
	String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
	String claims_type = (String)request.getAttribute("claims_type");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	long itemstotal=0,totalremamount=0;

%>
 
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Generate Contingent Bill</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>						
						<li class="breadcrumb-item "><a href="ContingentApprovals.htm">Pending Contingent Bills</a></li>
						<li class="breadcrumb-item active " aria-current="page">Generate Contingent Bill</li>
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
			
				<div class="card-header">
					<form action="CHSSBatchList.htm" method="post" >
					
					<div class="row justify-content-end">
		
						
							<%-- <div class="col-sm-1half">	
								<h6 class="control-label" style="color: #145374;"> From : </h6>
							</div>		
								
							<div class="col-md-2">			
								<input type="text" class="form-control fromdate"  name="fromdate" id="fromdate" value="" required="required" readonly="readonly"   > 
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								
							</div> --%>
							
							<div class="col-sm-1half">	
								<h6 class="control-label"  style="color: #145374; margin-bottom: 10px;"> Till Date : </h6> 						
							</div>
							
							<div class="col-md-2">	
	
									<input type="text" class="form-control todate" name="todate" id="todate" value="<%=DateTimeFormatUtil.SqlToRegularDate(todate) %>" required="required" readonly="readonly" onchange="this.form.submit();"  > 
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<input type="hidden" name="claims_type" value="<%=claims_type %>" />	
						
							</div>
							<div class="col-md-1">	
								
							</div>
							
						</div>
					
					
					
					</form>	
				</div>
			
				<div class="card-body main-card " >
											
					<form action="CHSSContingentGenerate.htm" method="post" id="ClaimForm">
						
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
										<th style="padding-top:5px; padding-bottom: 5px;text-align: center ;">Applied Date</th>
										<th style="padding-top:5px; padding-bottom: 5px;width:10%;text-align: right;">Claim Amount (&#8377;)</th>
										<th style="padding-top:5px; padding-bottom: 5px;width:12%;text-align: right;">Admitted Amount (&#8377;)</th>
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
											<td style="padding-top:5px; padding-bottom: 5px;">
												<%if(obj[14]!=null && !obj[14].toString().equalsIgnoreCase("Self")){ %>
													<%=obj[12] %> (<%=obj[14] %>)
												<%}else{ %>
													<%=obj[14] %>
												<%} %>
											</td>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
											<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=Math.round(Double.parseDouble(obj[26].toString())) %></td>
											<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=Math.round(Double.parseDouble(obj[27].toString())) %></td>
											
											<td style="padding-top:5px; padding-bottom: 5px;">
												
												<%if(obj[6].toString().equals("OPD")){ %>
													<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
														<i class="fa-solid fa-eye"></i>
													</button>	
															
													<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEmpDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
														<i style="color: #019267" class="fa-solid fa-download"></i>
													</button>
												<%}else if(obj[6].toString().equals("IPD")){ %>
													<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
														<i class="fa-solid fa-eye"></i>
													</button>	
															
													<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
														<i style="color: #019267" class="fa-solid fa-download"></i>
													</button>
												<%}%>
												
												<input type="hidden" name="view_mode" value="A">
											</td>
										</tr>
									<%
									itemstotal += Math.round(Double.parseDouble(obj[26].toString()));
									totalremamount += Math.round(Double.parseDouble(obj[27].toString()));
									} %>
									
									<%if(chssclaimlist.size()>0){ %>
										<tr>
											<td colspan="5" style="text-align: right;">
													<b>Total</b>
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
									<%if(chssclaimlist.size()==0){ %>
										<tr>
											<td colspan="8" style="text-align: center;">
												No Record Found
											</td>
										</tr>
									<%} %>
								</tbody>
							</table>
							<input type="hidden" name="isapproval" value="N">
						</div>
						
						
					<%if(chssclaimlist.size()>0){ %>		
						<div class="row">
							<div class="col-12">
								Content :
								<textarea class="w-100 form-control" rows="8" cols="100" id="billcontent" name="billcontent" maxlength="3000">According to note no. STARC/P&A/302-457/18 dated 31-07-2018 all the medical claims irrespective of amount will be reimbursed, after the approval of CEO.

It is requested to approve the reimbursement of medical expenses as above and for payment to the above employees.

Put up for approval.</textarea>
							</div>						
						</div>			 
						<div class="col-md-12" align="center" style="margin-top: 5px;">
							<button type="submit" class="btn btn-sm submit-btn" name="claimaction" value="F" onclick="return checklength('F'); " data-toggle="tooltip" data-placement="top" title="Generate New Contingent Bill">Generate</button>
							<input type="hidden" name="genTilldate" value="<%=todate%>">
							
						</div>
					<%} %>
					 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					 	<input type="hidden" name="claims_type" value="<%=claims_type %>" />
					 </form>
					
				</div>
			</div>		
			
		</div>
	
	 </div>
						
<script type="text/javascript">
					
					
	<%-- $('#fromdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"maxDate" :new Date(), 
		"startDate" : new Date('<%=fromdate%>'),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	 --%>
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		
		
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
	
				
					
</script>
	
	 
<script type="text/javascript">


function checklength()
{
	if($('input:checked').length > 0){
		/* if(confirm('Are you sure to Forward ? '))
		{
			$('#ClaimForm').submit();
		} */
		return confirm('Are you sure to Generate ? ');
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