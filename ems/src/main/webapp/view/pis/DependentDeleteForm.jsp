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
<title>CHSS FORM - 3</title>
</head>

<body >
	<div id="pageborder" align="center" style="max-width:100%;margin-top: -5px;margin-left: 10px;" >
		<div align="right">
			<h3 style="text-decoration: underline;">CHSS FORM - 3</h3>
		</div>
		<div align="center">
			<h3>Contributory Health Service Scheme of SITAR Society</h3>
			<span style="text-decoration: underline; font-size: 15px;"><b>Application for exclusion of names of beneficiaries</b></span>
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
					<th style="width: 15% " >Effective Date of Exclusion</th>
					<th style="width: 15% " >Reason for Exclusion </th>
				</tr>
				<%for(int i=0;i<4;i++){ %>
				<tr>
					<td><br></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<%} %>
			</table>
			<div > 
				<br><br><br><br>
				<table style="float: right; " >
					<tr>
						<td style="border: 0"><b>Signature ..........................................</b></td>
					</tr>
					<tr>
						<td style="border: 0"> Date : ..........................................</td>
					</tr>
				</table>
			</div>
		</div>
		<br><br><br><br>
		<div>
			<b>-------------------------------------------------------------------------------------------------------------------------------</b>
			<div align="center"> <h4 style="text-decoration: underline; ">FOR USE IN P&A DEPARTMENT</h4>		</div>
			<div align="left">
				<table style="width:100%; ">
					<tr>
						<td style="border: 0">Excluded with effect from______________________<br><br></td>
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

