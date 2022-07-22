<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

.break
{
	page-break-after: always;
} 
	
#pageborder
{
      /* position:fixed; */
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      
}     
@page {             
          size: 790px 1120px;
          margin-top: 40px;
          margin-left: 50px;
          margin-right: 40px;
          margin-buttom: 40px; 	
          border: 1px solid black;  
            
          @bottom-right {          		
             content: "Page " counter(page) " of " counter(pages);
             margin-bottom: 30px;
             margin-right: 10px;
          }
          
          @top-right {
          	
          	 content: "";
            margin-top: 30px;
            margin-right: 10px;
          }
          @top-left {
          	margin-top: 30px;
            margin-left: 10px;
            content: "";
          }            
          
          @top-center { 
          margin-top: 30px;
          content: ""; 
          
          }
           @bottom-center { 
	          margin-bottom: 30px;
	          content: ""; 
          
          }

 }
p
{
	text-align: justify;
  	text-justify: inter-word;
}
p,td,th
{
  word-wrap: break-word;
  word-break: normal ;
}
div
{
	max-width: 680px; 
	margin:0px;
}
th,td
{
	border : 1px solid black;
	padding: 5px;
}
table{
	max-width: 670px;
	border-collapse: collapse;
}

ol > li::marker {
  font-weight: bold;
}

.nobordertd
{
	border: 0px;
	margin: 0px;
	padding : 0px;
}



</style>
<meta charset="ISO-8859-1">
<title>CHSS FORM - 2</title>
</head>
<%

String labcode=(String) session.getAttribute("LabCode");


List<Object[]> FwdMemberDetails = (List<Object[]>)request.getAttribute("FwdMemberDetails");
Object[] empdetails = (Object[])request.getAttribute("empdetails");
Object[] employeeResAddr = (Object[])request.getAttribute("employeeResAddr");
Object[] formdetails = (Object[])request.getAttribute("formdetails");
String LabLogo = (String)request.getAttribute("LabLogo");
%>
<body >
		<div style="width: 100%;">
			<div style="width: 20%; border: 0;float:left;" >
				<img style="width: 80px; height: 90px; margin-left:0px;margin-top: 5px;"   src="data:image/png;base64,<%=LabLogo%>">
			</div>
		</div>	
	<div id="pageborder" align="center" style="max-width:100%;margin-top: -5px;margin-left: 10px;" >
	
		<!-- <div align="right">
			<h3 style="text-decoration: underline;">CHSS FORM - 2</h3>
		</div> -->
		<div align="center">
			<br>
			<h3>Contributory Health Service Scheme of SITAR Society</h3>
			<span style="text-decoration: underline; font-size: 15px;"><b>Application for addition of names of beneficiaries</b></span>
		</div>
		<div align="left">
			<br>
			<h3>UNIT : <%=labcode.toUpperCase() %></h3>
		</div>
		<div align="left">
			<table>
				<tr>
					<th style="width:23% " >Name</th>
					<th style="width:10% " >Emp.No.</th>
					<th style="width:17% " >Designation</th>
					<th style="width: 10% " >Level In Pay Matrix</th>
					<th style="width: 15% " >Basic Pay</th>
					<!-- <th style="width: 12% " >Date of Birth</th>
					<th style="width: 12% " >Date of Appointment</th> -->
				</tr>
				<tr>
					<td><%=empdetails[2] %></td>
					<td><%=empdetails[1] %></td>
					<td><%=empdetails[12] %></td>
					<td><%=empdetails[9] %></td>
					<td><%=empdetails[11] %></td>
					<!-- <td></td>
					<td></td> -->
				</tr>
			</table>
			Residential Address :
				<%if(employeeResAddr!=null){ %>
					<%=employeeResAddr[1] %>
				<%} %>
			<br>
			<br><br><br>
		</div>
		<div align="left">
			<b style="text-decoration: underline; ">Particulars of family members</b><br>
			<table>
				<tr>
					<th style="width:5% " >SN</th>
					<th style="width:20% " >Name</th>
					<th style="width:15% " >Relationship With the Employee</th>
					<th style="width: 15% " >Date of Birth</th>
					<th style="width: 15% " >Occupation if Any</th>
					<th style="width: 15% " >Income, if any, per month (Rs) </th>
				</tr>
				<%for(int i=0;i<FwdMemberDetails.size();i++)
				{ %>
				<tr>
					<td><%=i+1%>     </td>
					<td><%=FwdMemberDetails.get(i)[1] %></td>
					<td><%=FwdMemberDetails.get(i)[2] %></td>
					<td><%=DateTimeFormatUtil.SqlToRegularDate(FwdMemberDetails.get(i)[3].toString()) %></td>
					<td>
						<%if(FwdMemberDetails.get(i)[7]!=null){ %>
							<%=FwdMemberDetails.get(i)[7] %>
						<%}else{ %>
							-
						<%} %>
					</td>
					<td>
						<%if(FwdMemberDetails.get(i)[8]!=null && !FwdMemberDetails.get(i)[8].toString().equals("0")){ %>
						<%=FwdMemberDetails.get(i)[8] %>
						<%}else{ %>
							-
						<%} %>
					</td>
				</tr>
				<%} %>
			</table>
			<p style="text-indent: 50px;">I have read the instructions on the reverse side very carefully and have understood their
				meaning. I certify that the persons mentioned above fulfil the conditions prescribed and that they are
				eligible for registration under CHSS.
			</p>
				
			<p style="text-indent: 50px;">I hereby undertake to declare at the beginning of each calendar year and as soon as necessary
				thereafter about the eligibility or otherwise to the CHSS benefits of myself and my parents/ family
				members whose names are mentioned above. It shall be my responsibility to notify the Unit when any
				person referred to above becomes ineligible to the CHSS benefits. I realise that the onus of proving
				eligibility of the members mentioned above to the benefits of the scheme rests on me.
			</p>
			<div align="center" ><b style="text-decoration: underline; text-align: center;" >Certificate in respect of dependent </b> </div> 
			<p style="text-indent: 50px;">I certify that the family members whose names are mentioned above are mainly dependent on and
				residing with me.
			</p>
				<div  align="left"> 
					<div >
					<br><br><br><br><br>
					<table style="float: left;">
						<tr>
							<td class="nobordertd" ><b style="text-decoration: underline;" >APPLIED BY</b></td>
						</tr>
						<tr>
							<td class="nobordertd"><b><%=empdetails[2] %>,</b></td>
						</tr>
						<tr>
							<td class="nobordertd"> <%=empdetails[12] %></td>
						</tr>
						<tr>
							<td class="nobordertd" >Date: &nbsp; <%if(formdetails!=null && formdetails[4]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(formdetails[4].toString()) %>.<%} %></td>
						</tr>
					</table>
					<%if(formdetails!=null && formdetails[3].toString().equals("A")){ %>
						<table style="float: right;">
							<tr>
							<td class="nobordertd"  ><b style="text-decoration: underline;" >APPROVED BY</b></td>
						</tr>
							<tr>
								<td class="nobordertd" ><b><%=formdetails[8] %>,</b></td>
							</tr>
							<tr>
								<td class="nobordertd"  > <%=formdetails[9] %></td>
							</tr>
							<tr>
							<td class="nobordertd" >Date: &nbsp; <%=DateTimeFormatUtil.SqlToRegularDate(formdetails[6].toString()) %>.</td>
						</tr>
						</table>
					<%} %>
					
					</div>
				</div>
			<h1 class="break"></h1>
			
			<div align="left">
			<div align="center"> <h4 style="text-decoration: underline; ">Instructions for the employee</h4></div>
				<ol>
					<li>
						<p><b> The term Parents for the purpose of CHSS benefits does not include 'Step Parents'. Parent
						should have actually resided at least for 60 days with the employee before they are proposed for
						inclusion under the CHSS, and should continue to reside with the employee and be mainly
						dependent on him / her.</b> If the total income of the parents from all sources does not exceed the pay
						of the employee, subject to the maximum income of the parents being Rs. 18,000/- per month, such
						parents may be treated as mainly dependent on the employee. Income from land holdings, houses,
						fixed deposits, dividends, securities, deposits, employment, pension etc., should be taken into account
						for the purpose of total income of both the parents. If the parents of an employee move out for more
						than 60 days continuously, the Unit should be notified by the employee for suspension / cancellation
						of CHSS facilities.</p>
					</li>
					<li>
						<p>
							Married / divorced or legally separated daughters of employees, though dependent on the
							employee, are not eligible for medical benefits under CHSS. In the case of adopted children, only
							legally adopted sons and daughters are eligible for the benefits of the CHSS.
						</p>
					</li>
					<li>
						<p>
							If any of the family members / dependents for whom the registration is sought is eligible to
							receive medical aid/ facility, cash subsidy, cash allowances or reimbursement for medical care from
							sources other than the CHSS of SITAR Society, particulars of such benefits should be furnished on a
							separate sheet.
						</p>
					</li>
					<li>
						<p>
							If any of the members of the family proposed for registration is engaged in trade/business or is employed outside SITAR on part / full time basis, full particulars of such occupation should be
							furnished on a separate sheet duly supported by documentary evidence so that their eligibility for
							CHSS beneficiaries could be determined.
						</p>
					</li>
					<li>
						<p>
							Employees giving false or misleading information will be liable for disciplinary action.
						</p>
					</li>
				</ol>
				<b>-------------------------------------------------------------------------------------------------------------------------------</b>
			</div>
			<div align="center"> <h4 style="text-decoration: underline; ">FOR USE IN P&A DEPARTMENT</h4>		</div>
			<div>
				<table style="width:100%; ">
					<tr>
						<td style="border: 0">Registered under the CHSS.<br><br></td>
					</tr>
					<tr>
						<td style="border: 0"> Date : </td>
					
						<td style="border: 0; text-align: right;">Incharge - P&A</td>
					</tr>
					
				</table>
			</div>			
		</div>
	</div>
</body>
</html>

