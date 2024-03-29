<%@page import="java.util.Map"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
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

<style type="text/css">

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

input[type=number]{
    -moz-appearance: textfield;
}


p {
	text-align: justify;
	text-justify: inter-word;
}


table{
	align: left;
	width: 100% !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	margin-left:10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
}

.center{

	text-align: center;
}

.right
{
	text-align: right;
}
	
.text-blue
{
	color: blue;
}

.text-green
{
	color: #4E944F;
}


</style>


</head>
<body>

	
<%
	
	HashMap<Long, ArrayList<Object[]>> ContingentList = (HashMap<Long, ArrayList<Object[]>>)request.getAttribute("ContingentList");
	Object[]  contingentdata = (Object[])request.getAttribute("contingentdata");
	Object[] labdata = (Object[])request.getAttribute("labdata");
	List<Object[]> contingentremarks = (List<Object[]>)request.getAttribute("contingentremarks");
	String view_mode = (String) request.getAttribute("view_mode");
	String logintype = (String)request.getAttribute("logintype");
	int billstatus = Integer.parseInt(contingentdata[5].toString());
	if(contingentdata[2]==null){
		contingentdata[2]=LocalDate.now().toString();
	}
	
	
	IndianRupeeFormat nfc=new IndianRupeeFormat();
	AmountWordConveration awc = new AmountWordConveration();
	String LabLogo = (String)request.getAttribute("LabLogo");
	String onlyview = (String)request.getAttribute("onlyview");
	String isapproval = (String)request.getAttribute("isapproval");
	
%>
 
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Contingent Bill</h5>
			</div>
				<div class="col-md-9" >
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>	
						<%if(isapproval!=null && isapproval.equalsIgnoreCase("Y")){ %>					
							<li class="breadcrumb-item "><a href="ContingentApprovals.htm">Pending Contingent Bills</a></li>
						<%}else{ %>
							<li class="breadcrumb-item "><a href="ContingentBillsList.htm">Contingent Bills</a></li>
						<%} %>
						<li class="breadcrumb-item active " aria-current="page">Contingent Bill</li>
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
					<%if(billstatus>=7  && contingentdata[6]!=null && !contingentdata[6].toString().trim().equalsIgnoreCase("")){ %>
						<div class="col-md-12">
							<span style="font-weight: 600;"> Remark : </span>
							<%if(billstatus == 9 || billstatus == 11 || billstatus == 13  ){ %>
								<span style="font-weight: 600; color: #D82148;"> <%=contingentdata[6] %> </span>					
							<%}else{ %>    
								<span style="font-weight: 600; color: #035397;"> <%=contingentdata[6] %> </span>			
							<%} %>
						</div>
						<%} %>
					<form action="#" method="post" id="view-form">
						<input type="hidden" name="isapproval" value="Y">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div align="center">
						
						<div style="text-align: left;margin: 5px 5px 5px 10px;">
							<img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>">
							<div style="padding-left: 5px;">
								<br><br>
								<span style="font-size: 20px; font-weight:600; "><%=labdata[0] %></span> 
								<span style="float: right;vertical-align: bottom;">
									Dt.&nbsp;<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[2].toString()) %> 
									<%if(contingentdata[9]!=null){ %>
									<br>	Approved On:<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[9].toString())%>
									<% } %>
								
								</span>
									<br>
								<span style="font-size: 15px; font-weight:600; ">Ref: <%=contingentdata[1] %></span><br><br>
							</div>
							<p>
								The medical claims received up to <%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[10].toString()) %> during the month of 
								<%=" "+LocalDate.parse(contingentdata[10].toString()).getMonth() %> - <%=" "+LocalDate.parse(contingentdata[10].toString()).getYear() %> for reimbursement from the following
								employees have been processed and admitted at CHSS rates.
							</p>
						</div>
						
						<table>
							<tr>
								<%if((  billstatus==1 || billstatus==9 || billstatus==11 || billstatus==13) && logintype.equalsIgnoreCase("K") && view_mode.equalsIgnoreCase("E")){ %>
								<th style="text-align: center;" >Select</th>
								<%} %>
								<th style="text-align: center;" >SN</th>
								<th style="text-align: center;">Emp. No.</th>
								<th style="text-align: center;">Name</th>
								<th style="text-align: center;">Relation</th>
								<th style="text-align: center;">Claim No</th>
								<th style="text-align: center;">No. of Bills</th>
								<th class="right" style="width: 15%;">Amount Claimed (&#8377;)</th>
								<th class="right" style="width: 15%;">Amount Admitted (&#8377;)</th>
								<th  style="width: 10%" >Form</th>								
							</tr>
							
							<%long allowedamt=0,claimamt=0,billscount=0;
							int i=0;
							for (Map.Entry mapEle : ContingentList.entrySet()) 
							{
								int k=0;
								ArrayList<Object[]> arrlist = (ArrayList<Object[]>)mapEle.getValue();
					          	for(Object[] obj :arrlist )
					          	{
									i++; %>
								<tr>
									<%if((  billstatus==1 || billstatus==9 || billstatus==11 || billstatus==13) && logintype.equalsIgnoreCase("K") && view_mode.equalsIgnoreCase("E")){ %>
									<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><input type="checkbox" name="chssapplyid" value="<%=obj[0] %>" > </td>
									<%} %>
									<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%=i %></td>
									<%if(k==0){ %>
										<td rowspan="<%=arrlist.size() %>" style="padding-top:5px; padding-bottom: 5px;"><%=obj[24] %></td>
									
										<td rowspan="<%=arrlist.size() %>"  style="padding-top:5px; padding-bottom: 5px;"><%=obj[22] %></td>
									<%} %>
									<td style="padding-top:5px; padding-bottom: 5px;">
									<%if(obj[17]!=null && !obj[17].toString().equalsIgnoreCase("Self")){ %>
										<%=obj[15] %> (<%=obj[17] %>)
									<%}else{ %>
										<%=obj[17] %>
									<%} %>
									
									</td>
									<td class="center" style="padding-top:5px; padding-bottom: 5px;"><%=obj[19] %></td>
									<td class="center" style="padding-top:5px; padding-bottom: 5px;"><%=obj[25] %></td>
									<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[1].toString())) )) %></td>
									<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[2].toString())) )) %></td>
									<td >
									<%if(obj[9].toString().equals("OPD")){ %>
										<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEdit.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
											<i class="fa-solid fa-eye"></i>
										</button>	
												
										<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEmpDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
											<i style="color: #019267" class="fa-solid fa-download"></i>
										</button>
									<%}else if(obj[9].toString().equals("IPD")){ %>
										<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDFormEdit.htm"  formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
											<i class="fa-solid fa-eye"></i>
										</button>	
												
										<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
											<i style="color: #019267" class="fa-solid fa-download"></i>
										</button>
									<%}%>
									
									</td>
								</tr>
							<%	k++;
								claimamt += Math.round(Double.parseDouble(obj[1].toString()));
								allowedamt +=Math.round(Double.parseDouble(obj[2].toString()));
								billscount += Integer.parseInt(obj[25].toString());
								} 
							}%>
								<tr>
								<%if((  billstatus==1 || billstatus==9 || billstatus==11 || billstatus==13) && logintype.equalsIgnoreCase("K") && view_mode.equalsIgnoreCase("E")){ %>
									<td colspan="6" class="right">Total</td>
								<%}else{ %>
									<td colspan="5" class="right">Total</td>
								<%} %>
									<td class="center"><%=billscount %></td>
									<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(claimamt)) %></td>
									<td class="right">
										
										&#8377; <%=nfc.rupeeFormat(String.valueOf(allowedamt)) %>
																	
									</td>	
								</tr>
						</table>
						
						<%if(logintype!=null && !logintype.equalsIgnoreCase("K")){ %>
							<div>
								<p>
									<%=contingentdata[8] %>
								</p>
							</div>
						<%} %>
					</div>
					<input type="hidden" name="view_mode" value="<%=view_mode%>">
					<input type="hidden" name="contingentid" value="<%=contingentdata[0]%>">
					</form>	
					<%if(billscount>0 && (logintype==null || !logintype.equals("Y"))){ %>
					<form action="CHSSContingentApprove.htm" method="post">
					
						<div class="row">
							
							<%if( logintype.equalsIgnoreCase("K") ){ %>
								<div class="col-12">
									Content :
									<textarea class="w-100 form-control" rows="8" cols="100" id="billcontent" name="billcontent" maxlength="3000"><%=contingentdata[8] %></textarea>
								</div>	
							<%} %>
							
							<div class="col-md-12" align="left">
								<%if(contingentremarks.size()>0){ %>
								<table style="border: 1px solid black">
									<tr>
										<td style="border:none;">
											<h4 style="text-decoration: underline;">Remarks :</h4> 
										</td>
										
									</tr>
									<%for(Object[] obj : contingentremarks){%>
									<tr>
										<td style="border:none;width: 20%;">
											<%=obj[3] %>&nbsp; :
											<%=obj[4] %>
										</td>
										
									</tr>
									<%} %>
								</table>
								<%} %>
							</div>
							<%if(billstatus<15 ){%>
								<div class="col-md-12" align="left">
									Remarks : <br>
									<textarea class="w-100 form-control" rows="4" cols="100" id="remarks" name="remarks" maxlength="500"></textarea><br>
								</div>
							<%} %>
							
								<div class="col-12" align="center">
									<%if(view_mode.equalsIgnoreCase("E")){ %>
										<%if(billstatus==1  && logintype.equalsIgnoreCase("K") ){ %>
											<button type="submit" class="btn btn-sm submit-btn" name="action" id="fwd-btn" value="F"  onclick="return confirm('Are You Sure To Forward?');"  >Forward</button>
											<button type="submit" class="btn btn-sm submit-btn" style="background-color: #D2001A" formaction="ContingentClaimDrop.htm" form="view-form" onclick="return checklength();"  >Drop From Bill</button>
										<%}else if(( billstatus==9 || billstatus==11 || billstatus==13) && logintype.equalsIgnoreCase("K") ){ %>
											<button type="submit" class="btn btn-sm submit-btn" name="action" id="fwd-btn" value="F" onclick="return remarkRequired('R')" >Forward</button>
											<button type="submit" class="btn btn-sm submit-btn" style="background-color: #D2001A" formaction="ContingentClaimDrop.htm" form="view-form" onclick="return checklength();"  >Drop From Bill</button>
										<%}else if((billstatus==8 ) && logintype.equalsIgnoreCase("V")){ %>
											<button type="submit" class="btn btn-sm submit-btn" name="action" id="fwd-btn" value="F" onclick="return confirm('Are You Sure To Forward?');"  >Forward</button>
											<button type="submit" class="btn btn-sm delete-btn" name="action" value="R" onclick="return remarkRequired('R')" >Return</button>
										<%}else if((billstatus==10) && logintype.equalsIgnoreCase("W")){ %>
											<button type="submit" class="btn btn-sm submit-btn" name="action" id="fwd-btn" value="F" onclick="return confirm('Are You Sure To Recommend?');"  >Recommend</button>
											<button type="submit" class="btn btn-sm delete-btn" name="action" value="R" onclick="return remarkRequired('R')" >Return</button>
										<%}else if(billstatus==12  && logintype.equalsIgnoreCase("Z")){ %>
											<button type="submit" class="btn btn-sm submit-btn" name="action" id="fwd-btn" value="F" onclick="return confirm('Are You Sure To Approve?');" >Approve</button>
											<button type="submit" class="btn btn-sm delete-btn" name="action" value="R" onclick="return remarkRequired('R')" >Return</button>
										<%}%>
									<%} %>
									<%if(billstatus==14  && new ArrayList<String>( Arrays.asList("B", "K", "V", "W","Z")).contains(logintype)){ %>
										<button type="submit" class="btn btn-sm submit-btn" name="action" id="fwd-btn" value="F" onclick="return remarkRequired('R');" >Bank Transfer Successful</button>								
									<%} %>
								</div>	
							
							
							
						</div>
						<input type="hidden" name="contingentid" value="<%=contingentdata[0]%>">
						<input type="hidden" name="isapproval" value="Y">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
					<%} %>					
					
					<%if(i==0){ %>
						<form method="post" action="CHSSContingentDelete.htm">
							<div class="col-12" align="center">
								<input type="hidden" name="contingentid" value="<%=contingentdata[0]%>">
								<input type="hidden" name="isapproval" value="Y">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<button type="submit" class="btn btn-sm delete-btn" name="action" formaction="CHSSContingentDelete.htm" onclick="return confirm('Are You Sure To delete?');" >Delete</button>
							</div>
						</form>
					<%} %>	

				</div>
			</div>		
		
	</div>
	
 </div>

<script type="text/javascript">


function checklength()
{
	if($('input:checked').length > 0){
	
		return confirm('Are You Sure to Drop the Claims from the Bill(s) ? ');
	}else
	{
		alert('Please Select Atleast One Claim!');
		return false;
	}
}


$('#fwd-btn').click(function(){
	$('#remarks').attr('required', false);
	
}); 

function remarkRequired(action)
{
	if(action === 'R'){
		$('#remarks').attr('required', true);
		if($('#remarks').val().trim()===''){
			alert('Please Fill Remarks to Submit! ');
			return false;
		}else{
			return confirm('Are You Sure To Submit?');
		}
		
	}else{
		$('#remarks').attr('required', false);
		return confirm('Are You Sure To Submit 111?');
	}
	
}


</script>	 

</body>

</html>