<%@page import="java.time.LocalDate, java.util.Date, java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@ page language="java" %>
<!DOCTYPE html >
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>

<%
String Paylevel="Not Available";
String name =(String)request.getAttribute("name");
String designation=(String)request.getAttribute("desig");
Object[] PayLevelAndNewsRectrictAmt=(Object[])request.getAttribute("PayLevelAndNewsRectrictAmt");
Object[] NewspaperEditDetails=(Object[])request.getAttribute("NewspaperEditDetails");

String LabCode =(String)request.getAttribute("LabCode");
String NewsClaimHeader =(String)request.getAttribute("NewsClaimHeader");

int todaymonth=(int)request.getAttribute("todaymonth");
int TodayDate=(int)request.getAttribute("TodayDate");
/*  int todaymonth=8;
int TodayDate=16;  */
LabMaster LabDetails=(LabMaster)request.getAttribute("LabDetails");
Date today = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Newspaper Claim</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm"> Newspaper </a></li>
						<li class="breadcrumb-item "><a href="NewspaperList.htm"> Newspaper List </a></li>
						<li class="breadcrumb-item active " aria-current="page">Claim</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 <div class="page card dashboard-card">
		
			<div class="card-body"  >
				<div >		 
					 <form action="#" method="post"> 		
						 <div class="col-sm-12 text-center" align="center" >
				           <%	if(NewsClaimHeader!=null && !NewsClaimHeader.trim().equalsIgnoreCase("")){
				        	   String[] headerStr=NewsClaimHeader.split("/");
				           
				           	for(int i=0 ; i<headerStr.length ; i++){ %>
				             	<b> <%=headerStr[i] %> </b><br>
				             <% } }%>
				            <%--  <b><%=LabDetails.getLabName() %></b> --%>
				           </div>
			         
			         
							                 
					     <div class="col-sm-12">
					       <br><br>
					        <div class="col-sm-2"></div>
					        
					        <div class="col-sm-8">
					        <table>
					        <tbody>
					            <tr>
					             <td>Name Of The Applicant:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					             <td><%=name%></td>
					            </tr>
					       
					            <tr>
					              <td>Designation:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					              <td><%=designation%></td>
					           </tr>
					       
					          <!--  <tr>
					             <td>Department:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					             <td>Defence Research & Development Establishment</td>
					           </tr> -->
					       
					           <tr>
					             <td>Pay Level:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					              <%if(NewspaperEditDetails!=null){
					            	  Paylevel=NewspaperEditDetails[5].toString();%>
					               <td><%=Paylevel%></td>
					              <%}if(PayLevelAndNewsRectrictAmt!=null){
					                 Paylevel=PayLevelAndNewsRectrictAmt[2].toString();%>
					              <td><%=Paylevel%></td>
					              <%}%> 
					             
					           </tr>
					          
					       
					        </tbody>
					       
					       </table>
					       </div>
					    <div class="col-sm-2"></div><%=sdf.format(today)%>
					  </div>
					         <div class="col-sm-12">
					     <br><br>
					    
					     <span class="mandatory">*</span>I Certify That I have Spent Rs.<input type="text" id="AmountTextBox"   value="<%if(NewspaperEditDetails!=null){%><%=NewspaperEditDetails[3]%><%}%>" class="form-control input-sm" style="width: 100px; margin-top:-30px; margin-left:200px;" name="ClaimAmount" maxlength="6" required="required">
					      Towards Purchase Of Newspaper(s) for The Period Of:&nbsp;&nbsp;<select class="form-control " style="width:110px; margin-top:-30px; margin-left:360px;" name="ClaimMonth">
					         <%if(NewspaperEditDetails!=null){%>
					              <option><%=NewspaperEditDetails[1]%></option>
					         <%}else{ %>
					         	
					         	<option <% if( todaymonth>7 || (TodayDate>15 && todaymonth==7) || (TodayDate<=15 && todaymonth==1)) {%>disabled <%} %>>JAN-JUN</option>
					         	
					            <option <% if( (todaymonth<7 && todaymonth>1) || (TodayDate<=15 && todaymonth==7) || (TodayDate>15 && todaymonth==1)) {%>disabled <%} %>>JUL-DEC</option>
					         <%}%>
					         </select>
					
										                                     
					 		<%if(NewspaperEditDetails!=null){%>
								<input type="text" id="selectYear" required="required" class="form-control input-sm" style="width:70px;margin-left:475px;margin-top: -35px;" name="ClaimYear" value= "<%=NewspaperEditDetails[2]%>"  readonly="readonly"/>
					        <%}else{%>
					        	<input type="text" id="selectYear" required="required" class="form-control input-sm" style="width:70px;margin-left:475px;margin-top: -35px;" name="ClaimYear" value="<%=LocalDate.now().getYear() %>" readonly="readonly"  placeholder="Year" />
					        <% }%>
					                                                        
					     </div> 
	     
	    
					     <div class="col-sm-12">
						     <br><br>
						      
						      <%
						      double Admissibleamt = 0;
						      if(NewspaperEditDetails!=null){%>
						       Admissible Amount: <b>  Rs <%=NewspaperEditDetails[4]%>/-</b> 
						        <input type="hidden" name="NewspaperId" value="<%=NewspaperEditDetails[0]%>">
						        <input type="hidden" name="RestrictedAmount" value="<%=NewspaperEditDetails[4]%>">
						      <%
						      	Admissibleamt  =  Double.parseDouble(NewspaperEditDetails[4].toString());
						      
						      }else{ %>
						      Admissible Amount: <b> Rs <%=(Double.parseDouble(PayLevelAndNewsRectrictAmt[3].toString())*6)%>  </b>
						      <input type="hidden" name="RestrictedAmount" value="<%=(Double.parseDouble(PayLevelAndNewsRectrictAmt[3].toString())*6)%>">
						      <input type="hidden" name="PayLevelId" value="<%=PayLevelAndNewsRectrictAmt[1]%>">
						      <%
						      Admissibleamt  = Double.parseDouble(PayLevelAndNewsRectrictAmt[3].toString())*6;
						      } %>
					     </div>
					    
					    <%if(Admissibleamt>0){ %>
					     <div class="col-sm-12">
						     <br><br>
						      <%if(NewspaperEditDetails!=null){%>
						      <button type="submit" class="btn btn-sm update-btn" style="  margin-left:450px;" name="EditNewspaperSave" value="EditNewspaperSave" formaction="NewspaperEdit.htm" >Update</button>
						      <%}else{%>
						      <button type="submit" class="btn btn-sm submit-btn" style="  margin-left:450px;" name="AddNewspaperSave" value="AddNewspaperSave" formaction="NewspaperAdd.htm" >Submit</button>
						      <%}%>
					         
					     </div>
						<%} %>
					     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>	         
	    
			         
			    </div> 
			         
		         
		</div>
		    
		    
	</div>
<script type="text/javascript">

setPatternFilter($("#AmountTextBox"),/^-?\d*\.?\d*$/);

function setPatternFilter(obj, pattern) {
	  setInputFilter(obj, function(value) { return pattern.test(value); });
	}

function setInputFilter(obj, inputFilter) {
	  obj.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
	    if (inputFilter(this.value)) {
	      this.oldValue = this.value;
	      this.oldSelectionStart = this.selectionStart;
	      this.oldSelectionEnd = this.selectionEnd;
	    } else if (this.hasOwnProperty("oldValue")) {
	      this.value = this.oldValue;
	      this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	    }
	  });
	}


$("#selectYear").datepicker({
	autoclose: true,
    format: "yyyy",
    viewMode: "years", 
    minViewMode: "years",
    endDate: new Date(),
    setDate : new Date(),
});

</script>
		 
		 
		 
</body>
</html>