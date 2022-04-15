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
	



</style>


</head>
<body>

	
<%
	
	HashMap<Long, ArrayList<Object[]>> ContingentList = (HashMap<Long, ArrayList<Object[]>>)request.getAttribute("ContingentList");
	Object[]  contingentdata = (Object[])request.getAttribute("contingentdata");
	
	String logintype = (String)request.getAttribute("logintype");
	int billstatus = Integer.parseInt(contingentdata[5].toString());

	IndianRupeeFormat nfc=new IndianRupeeFormat();
	AmountWordConveration awc = new AmountWordConveration();
	
%>
 
 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Contingent Bill</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>						
						<li class="breadcrumb-item "><a href="ContingentApprovals.htm">CHSS Contingent List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Contingent Bill</li>
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

					<div align="center">
						
						<div style="text-align: left;margin: 5px 5px 5px 10px;">
							<span style="font-size: 20px; font-weight:600; ">SITAR</span> <span style="float: right;">Dt.&nbsp;<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[2].toString()) %></span><br>
							<span style="font-size: 15px; font-weight:600; ">Ref: <%=contingentdata[1] %></span><br>
							<p>
								The medical claim recieved upto <%=DateTimeFormatUtil.SqlToRegularDate(LocalDate.now().withDayOfMonth(20).toString()) %> during the month of 
								<%=" "+LocalDate.now().getMonth() %> - <%=" "+LocalDate.now().getYear() %> for reimbrusement from the following
								employees have been processed and admitted at CHSS rates.
							</p>
						
						</div>
						
						<table>
							<tr>
								<th style="text-align: center;" >SN</th>
								<th style="text-align: center;">Emp. No.</th>
								<th style="text-align: center;">Name</th>
								<th style="text-align: center;">Relation</th>
								<th style="text-align: center;">No. of Bills</th>
								<th class="right" style="width: 15%;">Amount Claimed (&#8377;)</th>
								<th class="right" style="width: 15%;">Amount Admitted (&#8377;)</th>
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
									
									<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%=i %></td>
									<%if(k==0){ %>
										<td rowspan="<%=arrlist.size() %>" style="padding-top:5px; padding-bottom: 5px;"><%=obj[21] %></td>
									
										<td rowspan="<%=arrlist.size() %>"  style="padding-top:5px; padding-bottom: 5px;"><%=obj[19] %></td>
									<%} %>
									<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[14] %></td>
									<td class="center" style="padding-top:5px; padding-bottom: 5px;"><%=obj[22] %></td>
									<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=obj[27] %></td>
									<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=obj[28] %></td>
																
								</tr>
							<%	k++;
								claimamt += Integer.parseInt(obj[27].toString());
								allowedamt +=Integer.parseInt(obj[28].toString());
								billscount += Integer.parseInt(obj[22].toString());
								} 
							}%>
							
									<tr>
									<td colspan="4" class="right">Total</td>
									<td class="center"><%=billscount %></td>
									<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(claimamt)) %></td>
									<td class="right">
										
										&#8377; <%=nfc.rupeeFormat(String.valueOf(allowedamt)) %>
																	
									</td>	
								</tr>
						</table>
						
						<div>
							<p>
								<%=contingentdata[8] %>
							</p>
						</div>
						
						
						<div class="col-md-12" align="left">
								Remarks : <br>
								<textarea class="w-100 form-control" rows="4" cols="100" id="remarks" name="remarks" maxlength="500"></textarea>
						</div>
					</div>
					
					<form action="CHSSContingentApprove.htm" method="post">
						<div class="row">
							<div class="col-12" align="center">
								<%if(billstatus==1  && logintype.equalsIgnoreCase("K")){ %>
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="F"  onclick="return confirm('Are You Sure To Forward?');"  >Forward</button>
								<%}else if( billstatus==9 && logintype.equalsIgnoreCase("K")){ %>
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="F" onclick="return remarkRequired('F','Are You Sure To Forward')" >Forward</button>
								<%}else if((billstatus==8 || billstatus==11) && logintype.equalsIgnoreCase("V")){ %>
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="F" >Recommend</button>
									<button type="submit" class="btn btn-sm delete-btn" name="action" value="R" onclick="return remarkRequired('F')" >Return</button>
								<%}else if((billstatus==10 || billstatus==13) && logintype.equalsIgnoreCase("W")){ %>
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="F" >Authorize</button>
									<button type="submit" class="btn btn-sm delete-btn" name="action" value="R" onclick="return remarkRequired('F')" >Return</button>
								<%}else if(billstatus==12  && logintype.equalsIgnoreCase("Z")){ %>
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="F" >Approve</button>
									<button type="submit" class="btn btn-sm delete-btn" name="action" value="R" onclick="return remarkRequired('F')" >Return</button>
								<%} %>
							</div>	
						</div>
						<input type="hidden" name="contingentid" value="<%=contingentdata[0]%>">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
										

				</div>
			</div>		
			
	</div>
	
 </div>

<script type="text/javascript">



function remarkRequired(action)
{
	if(action === 'R'){
		$('#remarks').attr('required', true);
		if($('#remarks').val().trim()===''){
			alert('Please Fill Remarks to Return! ');
			return false;
		}else{
				return confirm('Are You Sure To Return?');
		}
		
	}else{
		$('#remarks').attr('required', false);
		return confirm('Are You Sure To Submit?');
	}
	
}


</script>	 

</body>

</html>