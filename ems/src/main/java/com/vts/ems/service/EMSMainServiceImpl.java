package com.vts.ems.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vts.ems.dao.EmsDao;
import com.vts.ems.model.AuditStamping;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;
@Service
public class EMSMainServiceImpl implements EMSMainService 
{
	private static final Logger logger=LogManager.getLogger(EMSMainServiceImpl.class);
	
	private SimpleDateFormat sdf1=DateTimeFormatUtil.getSqlDateAndTimeFormat();
//	private  SimpleDateFormat sdf=util.getRegularDateFormat();
//	private SimpleDateFormat sdf2=util.getSqlDateFormat();
	
	@Autowired
	EmsDao dao;

	@Autowired
	BCryptPasswordEncoder encoder;
	 
	@Autowired 	
	private JavaMailSender javaMailSender;
	
	
	@Override
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception {		
		return dao.LoginStampingInsert(Stamping);
	}

	@Override
	public int LoginStampingUpdate(String Logid, String LogoutType) throws Exception {
		AuditStamping stamping=new AuditStamping();
        stamping.setAuditStampingId(dao.LastLoginStampingId(Logid));
        stamping.setLogOutType(LogoutType);
        stamping.setLogOutDateTime(sdf1.format(new Date()));
		return dao.LoginStampingUpdate(stamping);
	}

	@Override
	public Employee EmployeeInfo(long EmpId)throws Exception
	{
		return dao.EmployeeInfo(EmpId);
	}
	
	@Override
	public Object[] EmployeeData(String EmpId)throws Exception
	{
		return dao.EmployeeData(EmpId);
	}
	
	@Override
	public List<EMSNotification> NotificationList(long EmpId)throws Exception
	{
		return dao.NotificationList(EmpId);
	}
	
	@Override
	public int NotificationUpdate(String NotificationId) throws Exception
	{
		return dao.NotificationUpdate(NotificationId);
	}
	
	@Override
	public Object[] LoginExistCheck(String username) throws Exception
	{
		return dao.LoginExistCheck(username);
	}
	
	
	public static String getAlphaNumericString(int n)
    {
  
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }
	
	
	@Override
	public String getPasswordResetOTP(String loginid) throws Exception
	{
		String otp="";
		Object[] otpdata = dao.getResetOtp(loginid);
		if(otpdata!=null && otpdata[0]!=null && !otpdata[0].toString().trim().equals("") )
		{
			otp=otpdata[0].toString().trim();
		}else 
		{
			otp = getAlphaNumericString(4);
			int count=dao.UpdateResetOtp(loginid, otp);
			SendOtpMail(loginid);
		}
		return otp;
	}
	

	
	@Override
	public int userResetPassword(String loginid,String password) throws Exception
	{
		return dao.userResetPassword(loginid, encoder.encode(password));
	}
	
	@Override
	public Object[] LoginEmpInfo(String loginid) throws Exception
	{
		return dao.LoginEmpInfo(loginid);
	}
	
	@Override
	public int SendOtpMail(String loginid) throws Exception
	{
		Object[] userinfo = dao.LoginEmpInfo(loginid);
		
		String email= "";
		
		if(userinfo[4]!=null && !userinfo[4].toString().trim().equals("")) 
		{
			email=userinfo[4].toString().trim();
		}
		
		if (!email.trim().equalsIgnoreCase("")) 
		{
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(email);
			helper.setSubject("CHSS Account Password Reset OTP");
			helper.setText("\""+ userinfo[5].toString()+ "\" is the OTP to Reset Password of your CHSS account" , true);				
			try
			{
				javaMailSender.send(msg);
				return 1;
			}catch (Exception e) {
				logger.error(new Date() +" EMAIL CREDENTIALS NOT AUTHORIZED ", e);
				e.printStackTrace();
				return 0;
			}
			
		}
		
		return 0;
		
	}
	
	
	@Override
	public String reSendResetOTP(String loginid) throws Exception
	{
		String otp="";		
		
		otp = getAlphaNumericString(4);
		int count=dao.UpdateResetOtp(loginid, otp);
		SendOtpMail(loginid);
		
		return otp;
	}
	
	@Override
	public List<Object[]> EmpHandOverLoginTypeList(String empid) throws Exception
	{
		return dao.EmpHandOverLoginTypeList(empid);
	}
	
}
