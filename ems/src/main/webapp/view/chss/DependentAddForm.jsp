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

</style>
<meta charset="ISO-8859-1">
<title>CHSS FORM - 2</title>
</head>

<body >
	<div id="pageborder" align="center" style="max-width:100%;margin-top: -5px;margin-left: 10px;" >
		<div align="right">
			<h3 style="text-decoration: underline;">CHSS FORM - 2</h3>
		</div>
		<div align="center">
			<h3>Contributory Health Service Scheme of SITAR Society</h3>
			<span style="text-decoration: underline; font-size: 15px;"><b>Application for addition of names of beneficiaries</b></span>
		</div>
		<div align="left">
			<h3>UNIT : STARC</h3>
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
					<td><br><br></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<!-- <td></td>
					<td></td> -->
				</tr>
			</table>
			Residential Address :
			<br>
			<br><br><br>
		</div>
		<div align="left">
			<b style="text-decoration: underline; ">Particulars of family members</b><br>
			<table>
				<tr>
					<th style="width:5% " >SN</th>
					<th style="width:20% " >Name</th>
					<th style="width:12% " >Relationship With the Employee</th>
					<th style="width: 10% " >Date of Birth</th>
					<th style="width: 15% " >Occupation if Any</th>
					<th style="width: 15% " >Income, if any, per month (Rs) </th>
				</tr>
				<%for(int i=0;i<4;i++){ %>
				<tr>
					<td><br></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
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
			<div align="center" ><b style="text-decoration: underline; text-align: center;" >Certificate in respect of dependant </b> </div> 
			<p style="text-indent: 50px;">I certify that the family members whose names are mentioned above are mainly dependant on and
				residing with me.
			</p>
			<div align="right"> 
				<table style="float: right;">
					<tr>
						<td style="border: 0"><b>Signature ..........................................</b></td>
					</tr>
					<tr>
						<td style="border: 0"> Date : ..........................................</td>
					</tr>
					<tr>
						<td style="border: 0; text-align: right;"> <br>P.T.O</td>
					</tr>
					
				</table>
			</div>
			<h1 class="break"></h1>
			
			<div align="left">
			<div align="center"> <h4 style="text-decoration: underline; ">Instructions for the employee</h4></div>
				<ol>
					<li>
						<p><b> The term Parents for the purpose of CHSS benefits does not include 'Step Parents'. Parent
						should have actually resided at least for 60 days with the employee before they are proposed for
						inclusion under the CHSS, and should continue to reside with the employee and be mainlyY
						dependant on him / her.</b> If the total income of the parents from all sources does not exceed thE ay
						of the employee, subject to the maximum income of the parents being Rs. 18,000/- per month, such
						parents may be treated as mainly dependant on the employee. Income from land holdings, houses,
						fixed deposits, dividends, securities, deposits, employment, pension etc., should be taken into account
						for the purpose of total income of both the parents. If the parents of an employee move out for more
						than 60 days continuously, the Unit should be notified by the employee for suspension / cancellation
						of CHSS facilities.</p>
					</li>
					<li>
						<p>
							Married / divorced or legally separated daughters of employees, though dependant on the
							employee, are not eligible for medical benefits under CHSS. In the case of adopted children, only
							legally adopted sons and daughters are eligible for the benefits of the CHSS.
						</p>
					</li>
					<li>
						<p>
							If any of the family members / dependants for whom the registration is sought is eligible to
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

