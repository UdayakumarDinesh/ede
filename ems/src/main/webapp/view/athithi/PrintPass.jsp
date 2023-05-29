<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
    .break
	{
		page-break-after: always;
	} 
    
@page {             
          size: 790px 1120px;
          margin-top: 49px;
          margin-left: 39px;
          margin-right: 39px;
          margin-buttom: 49px; 	
          border: 1px solid black;   
          @bottom-right {          		
             content: "Page " counter(page) " of " counter(pages);
             margin-bottom: 30px;
             margin-right: 10px;
            
          }
 }
 
 
 .vertical-text {
    position: fixed;
    -webkit-transform: rotate(90deg);
    -webkit-transform-origin: center bottom auto;
    top: 10%;
    right: 0;
}
</style>
<%
List<Object[]>  data=(List<Object[]>)request.getAttribute("passDetails");
List<Object[]>  visitorList=(List<Object[]>)request.getAttribute("visitorList");
Object[] lab =(Object[])request.getAttribute("LabDetails");
SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
String LabLogo =(String)request.getAttribute("LabLogo");

%>
</head>
<body>

 <div class="container"  >
            <table class="headTable" style="margin-left: 1.5rem;">
                <tr>
                    <td>
                  <img style="width: 2cm; height: 2cm"  src="data:image/jpeg;base64,<%=LabLogo%>"  >
                    </td>
                     <td style="padding-left:2rem;">
                    <center><p style="font-weight: bold; font-size: large; "><%=lab[1]%>, <%=lab[0]%></p></center>
                    <center><p style="font-weight: bold; font-size: large;  margin-top: -12px;"><%=lab[2] %>, <%=lab[3] %>-<%=lab[4] %></p></center>
                
                    </td> 
                    
                </tr>
            </table>
           
        </div>
        <hr>
        <div>
            <table class="DateTable">
                <tr>
                   
                    <td>Pass No:</td>
                    <td><b><%=data.get(0)[3] %></b></td>

                    <td style="padding-left:6.5rem;">Intimation No:</td>
                    <td><b><%=data.get(0)[1] %></b></td>
                    
                   <td style="padding-left:6.5rem;">Time :</td>
                     <td><b><%=data.get(0)[7].toString().subSequence(11, 16)%></b></td>
                </tr>
                <tr>
                    <td>Date :</td>
                    <td><b><%=sdf1.format(data.get(0)[2])%></b></td>

                    <td style="padding-left:6.5rem;">Date :</td>
                    <td><b><%=sdf1.format(data.get(0)[0])%></b></td>
                    
                     <td style="padding-left:6.5rem;">Duration :</td>
                     <td><b><%=data.get(0)[12]%>&nbsp;hours</b></td>
                </tr>
                
     
            </table>
            
            
        </div>
        <div align="center">
         <table style="border: 1px solid black;">
                <tr  >
                  <td style="width:17rem; text-align: left;"> Receiving Officer : <td>
                     <td style="width:10rem; text-align: left"><b><%=data.get(0)[4]%>&nbsp;(<%=data.get(0)[5]%>)</b> <td>                  
                <td style="width:5rem;text-align: left">Group : <td>
                     <td style="width:10rem;text-align: left"><b><%=data.get(0)[6]%></b> <td>        
                </tr>
                <tr  >
                     <td style="width:17rem; text-align: left;"> Intimated By : <td>
                     <td style="width:10rem; text-align: left"><b><%=data.get(0)[13]%></b> <td>     
                      <td style="width:17rem; text-align: left;"> Visit Expected Time : <td>
                     <td style="width:10rem; text-align: left"><b><%=data.get(0)[14]%></b> <td>                  
                </tr>
                 <tr  >
                  <td style="width:17rem; text-align: left;"> Company: <td>
                     <td style="width:29rem; text-align: left"><b><%=data.get(0)[10]%>&nbsp;(<%=data.get(0)[11]%>)</b> <td>                  
                </tr>
                 <tr  >
                  <td style="width:17rem; text-align: left;"> Purpose of Visit : <td>
                  <td style="width:29rem; text-align: left"><b><%=data.get(0)[8]%></b> <td>                  
                </tr>
                <%if(data.get(0)[8].toString().length()>0){ %>
                 <tr  >
                  <td style="width:17rem; text-align: left;"> Special Permission: <td>
                  <td style="width:29rem; text-align: left"><b><%=data.get(0)[9]%></b> <td>                  
                </tr>
                <%} %>
         
            </table>
            
        </div>
        <div align="center">

		<table >
		<%for(Object[] visitor:visitorList ){ %>
			<tr>
				<td style="width:35rem; text-align: left; border: 1px solid black;">
					<table>
						<tr>
						   <td>Name :</td>
						   <th style=" text-align: left;"><%=visitor[0]%>&nbsp;(<%=visitor[1]%>)</th>
						</tr>
						<tr>
						   <td>Mobile :</td>
						 <th style=" text-align: left;"><%=visitor[2]%> </th>
						</tr>
					</table>
				</td>
				<td style="border: 1px solid black; width:15rem;">
				<%if(visitor[3]!=null){
					byte[] fileContent = FileUtils.readFileToByteArray(new File("D:\\visitorPhoto\\"+visitor[3]));
					String encodedString = Base64.getEncoder().encodeToString(fileContent);
					
					%>
			  <img style="width: 1.5cm; height: 1.5cm"  src="data:image/jpeg;base64,<%=encodedString%>" >
			<%}else{ %>
			<p>Photo is not Available !!</p>
			<%} %>
				</td>
			</tr>
		<%}%>
			
		</table>
	</div>
	<div style="margin-top: 45px;">
	<table >
	<tr>
	<th>Visitor`s Sign</th>
	<th style="padding-left:8rem;">Officer`s Sign And Out Time</th>
	<th style="padding-left:5rem;">Sec. Asst./Receptionist Sign.</th>
	<tr>
	</table>
	</div>

</body>
</html>