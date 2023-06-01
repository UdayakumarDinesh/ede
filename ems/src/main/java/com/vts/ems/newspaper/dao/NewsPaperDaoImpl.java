package com.vts.ems.newspaper.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.newspaper.model.Newspaper;
import com.vts.ems.newspaper.model.NewspaperApplyTransaction;
import com.vts.ems.newspaper.model.NewspaperBill;
import com.vts.ems.newspaper.model.NewspaperContingent;
import com.vts.ems.newspaper.model.NewspaperContingentTrans;
import com.vts.ems.newspaper.model.Telephone;
import com.vts.ems.newspaper.model.TelephoneBill;
import com.vts.ems.newspaper.model.TelephoneDetails;
import com.vts.ems.newspaper.model.TelephoneForward;
import com.vts.ems.newspaper.model.TelephoneUsers;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;

@Repository
@Transactional
public class NewsPaperDaoImpl implements NewsPaperDao {

	private static final Logger logger = LogManager.getLogger(NewsPaperDaoImpl.class);

	@PersistenceContext
	private EntityManager manager;

	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();


	private static final String GETNEWSPAPERCLAIMLIST ="SELECT a.NewspaperId, a.ClaimMonth,ClaimYear,a.ClaimAmount,a.RestrictedAmount,a.PayableAmount,a.NewspaperBillId,a.SubmitBy, a.NewsAppliedDate, a.NewspaperStatusCode, b.NewsPaperStatus, b.StatusColor FROM pis_newspaper a, pis_newspaper_status b WHERE empno=:empno AND IsActive='1' AND a.NewspaperStatusCode=b.NewsPaperStatusCode ORDER BY NewsAppliedDate DESC;" ;
	@Override
	public List<Object[]> getNewspaperClaimList(String empno)throws Exception
	{
		List<Object[]> NewspaperClaimList = new ArrayList<Object[]>();
		try 
		{
			Query q = manager.createNativeQuery(GETNEWSPAPERCLAIMLIST);
			q.setParameter("empno", empno);
			NewspaperClaimList = (List<Object[]>) q.getResultList();
		} 
		catch (Exception e) 
		{
			logger.error(new Date() +"Inside DAO getNewspaperClaimList "+e);
			e.printStackTrace();
		}
		return NewspaperClaimList;
	}

	
	private static final String GETPAYLEVELANDNEWSRECTRICTAMT = "SELECT a.empName,ed.PayLevelId,b.PayLevel,b.NewsRestrictAmt FROM employee a,pis_pay_level b,employee_details ed WHERE  ed.empno=a.empno AND ed.PayLevelId=b.PayLevelId AND  a.empno=:empno ;";
	
	@Override
	public Object[] getPayLevelAndNewsRectrictAmt(String empno) throws Exception
	{
		Object[] PayLevelAndNewsRectrictAmt = null;
		try {
			Query q = manager.createNativeQuery(GETPAYLEVELANDNEWSRECTRICTAMT);
			q.setParameter("empno", empno);
			PayLevelAndNewsRectrictAmt = (Object[]) q.getSingleResult();

		} catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		catch (Exception e) {
			logger.error(new Date() +"Inside DAO getPayLevelAndNewsRectrictAmt " +e);
			e.printStackTrace();
			PayLevelAndNewsRectrictAmt = null;
		}

		return (PayLevelAndNewsRectrictAmt);

	}

//	@Override
//	public long AddNewspaperClaim(String empno, String ClaimMonth, String ClaimYear, double ClaimAmount,double RestrictedAmount, double PayableAmount, String PayLevelId)throws Exception
//	{
//		long result = 0;
//
//		try {
//
//			Newspaper n = new Newspaper();
//
//			Date d = new Date();
//
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//			String newsAppliedDate = sdf.format(d);
//			java.sql.Date sqlnewsAppliedDate = DateTimeFormatUtil.dateConversionSql(newsAppliedDate);
//
//			
//			String CreateDate = sdtf.format(d);
//			
//			n.setEmpNo(empno);
//			n.setClaimMonth(ClaimMonth);
//			n.setClaimYear(ClaimYear);
//			n.setClaimAmount(ClaimAmount);
//			n.setRestrictedAmount(RestrictedAmount);
//			n.setPayableAmount(PayableAmount);
//			n.setPayLevelId(Integer.parseInt(PayLevelId));
//			n.setNewsAppliedDate(sqlnewsAppliedDate);
//			n.setIsActive(1);
//			n.setCreatedBy(empno);
//			n.setCreatedDate(CreateDate);
//			manager.persist(n);
//			manager.flush();	
//			result = n.getNewspaperId();
//
//		} catch (Exception e) {
//
//			logger.error(new Date() +"Inside DAO AddNewspaperClaim "+e);
//			e.printStackTrace();
//		}
//
//		return (result);
//
//	}
	
	@Override
	public long addNewspaper(Newspaper newspaper) throws Exception{
		long l=0;
		try {
			manager.persist(newspaper);
			manager.flush();	
			return newspaper.getNewspaperId();
		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO addNewspaper "+e);
			e.printStackTrace();
			return 0;
		}
	}

	public long addNewspaperTrans(NewspaperApplyTransaction newspaperTrans) throws Exception{
		long l=0;
		try {
			manager.persist(newspaperTrans);
			manager.flush();	
			return newspaperTrans.getNewspaperTransactionId();
		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO addNewspaperTrans "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long editNewspaper(Newspaper newspaper) throws Exception{
		long l=0;
		try {
			manager.merge(newspaper);
			manager.flush();	
			return newspaper.getNewspaperId();
		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO addNewspaper "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long editNewspaperContingent(NewspaperContingent newspaperContingent) throws Exception{
		long l=0;
		try {
			manager.merge(newspaperContingent);
			manager.flush();	
			return newspaperContingent.getContingentId();
		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO editNewspaperContingent "+e);
			e.printStackTrace();
			return 0;
		}
	}
  
	
	private static final String GETCHECKPERIODOFNEWSALREADYPRESENTORNOT = "SELECT  NewspaperId,NewspaperBillId from pis_newspaper   WHERE IsActive='1' and ClaimMonth=:ClaimMonth   and ClaimYear=:ClaimYear and empno=:empno";
	
	@Override
	public Object[] getCheckPeriodOfNewsAlreadyPresentOrNot(String empno, String ClaimMonth, String ClaimYear) throws Exception
	{
		Object[] getCheckPeriodAlreadyPresentOrNot = null;

		try {

			Query q = manager.createNativeQuery(GETCHECKPERIODOFNEWSALREADYPRESENTORNOT);
			q.setParameter("ClaimMonth", ClaimMonth);
			q.setParameter("ClaimYear", ClaimYear);
			q.setParameter("empno", empno);
			getCheckPeriodAlreadyPresentOrNot = (Object[]) q.getSingleResult();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception ");
		}	
		 catch (Exception e) {

			 logger.error(new Date() +"Inside DAO getCheckPeriodOfNewsAlreadyPresentOrNot "+e);
			e.printStackTrace();
			getCheckPeriodAlreadyPresentOrNot = null;
		}

		return (getCheckPeriodAlreadyPresentOrNot);

	}
	
	private static final String GETCHECKNEWSPAPERAPPROVEORNOT = "SELECT  NewspaperId,NewspaperBillId from pis_newspaper   WHERE NewspaperBillId='0' and  SubmitBy IS NULL  and NewspaperId=:NewspaperId";

	@Override
	public Object[] getCheckNewspaperApproveOrNot(String NewspaperId) throws Exception
	{
		Object[] CheckNewspaperApproveOrNot = null;
		try {
			Query q = manager.createNativeQuery(GETCHECKNEWSPAPERAPPROVEORNOT);
			q.setParameter("NewspaperId", Integer.parseInt(NewspaperId));
			CheckNewspaperApproveOrNot = (Object[]) q.getSingleResult();
		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		catch (Exception e) {
			logger.error(new Date() +"Inside DAO getCheckNewspaperApproveOrNot "+ e );
			e.printStackTrace();
			CheckNewspaperApproveOrNot = null;
		}

		return (CheckNewspaperApproveOrNot);
	}
	
	private static final String GETNEWSPAPEREDITDETAILS = "SELECT a.NewspaperId,a.ClaimMonth,a.ClaimYear,a.ClaimAmount,a.RestrictedAmount,b.PayLevel FROM pis_newspaper a,pis_pay_level b  where a.PayLevelId=b.PayLevelId and  a.NewspaperId=:NewspaperId";
	
	@Override
	public Object[] getNewspaperEditDetails(String NewspaperId) throws Exception
	{
		Object[] NewspaperEditDetails = null;

		try {

			Query q = manager.createNativeQuery(GETNEWSPAPEREDITDETAILS);
			q.setParameter("NewspaperId", Integer.parseInt(NewspaperId));
			NewspaperEditDetails = (Object[]) q.getSingleResult();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		catch (Exception e) 
		{
			logger.error(new Date() +"Inside DAO getNewspaperEditDetails "+e);
			e.printStackTrace();
		}

		return (NewspaperEditDetails);

	}

	private static final String  EDITNEWSPAPERCLAIM = "update pis_newspaper set ClaimAmount=:ClaimAmount,PayableAmount=:PayableAmount,ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate   where NewspaperId=:NewspaperId and empno=:empno";
	
	@Override
	public int EditNewspaperClaim(String empno, double ClaimAmount, String NewspaperId, double PayableAmount)throws Exception
	{
		int EditNewspaperClaim = 0;

		try {

			Query q = manager.createNativeQuery(EDITNEWSPAPERCLAIM);

			Date d = new Date();
			SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ModifiedDate = sdtf.format(d);

			q.setParameter("NewspaperId", Integer.parseInt(NewspaperId));
			q.setParameter("PayableAmount", PayableAmount);
			q.setParameter("ClaimAmount", ClaimAmount);
			q.setParameter("ModifiedBy", empno);
			q.setParameter("ModifiedDate", ModifiedDate);
			q.setParameter("empno", empno);
			EditNewspaperClaim = q.executeUpdate();

		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO EditNewspaperClaim "+e);
			e.printStackTrace();
		}

		return (EditNewspaperClaim);

	}


//	private static final String GETNEWSPAPERUSERPRINTDATA = "SELECT a.ClaimMonth,a.ClaimYear,a.ClaimAmount,a.RestrictedAmount,a.PayableAmount,b.PayLevel,b.PayGrade,a.NewspaperId, a.EmpNo, em.EmpName, des.Designation, a.NewspaperStatusCode FROM pis_newspaper a,pis_pay_level b, employee em, employee_desig des WHERE  a.PayLevelId=b.PayLevelId AND a.EmpNo=em.EmpNo AND em.DesigId=des.DesigId AND  a.NewspaperId=:NewspaperId";
	private static final String GETNEWSPAPERUSERPRINTDATA = "CALL Newspaper_byId(:NewspaperId)";
	@Override
	public Object[] getNewspaperUserPrintData(String NewspaperId)throws Exception
	{
		Object[] NewspaperUserPrintData = null;

		try {

			Query q = manager.createNativeQuery( GETNEWSPAPERUSERPRINTDATA);
			q.setParameter("NewspaperId", Integer.parseInt(NewspaperId));
			NewspaperUserPrintData = (Object[]) q.getSingleResult();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		 catch (Exception e) {

			 logger.error(new Date() +"Inside DAO getNewspaperUserPrintData "+ e );
			e.printStackTrace();
		}

		return (NewspaperUserPrintData);

	}

	private static final String GETNEWSPAPERCLAIMAPPROVEDLIST = "select NewspaperBillId,SubmitDate,FromDate,ToDate,TotalAmount FROM pis_newspaper_bill ORDER BY submitdate DESC; ";

	@Override
	public List<Object[]> getNewspaperClaimApprovedList()throws Exception
	{
		List<Object[]> NewspaperClaimApprovedList = new ArrayList<>();
		try {

			Query q = manager.createNativeQuery(GETNEWSPAPERCLAIMAPPROVEDLIST);
			
			NewspaperClaimApprovedList = (List<Object[]>) q.getResultList();

		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO getNewspaperClaimApprovedList "+e);
			e.printStackTrace();

		}

		return (NewspaperClaimApprovedList);

	}

	private static final String GETNEWSPAPERAPPROVALLIST = "SELECT a.EmpName,b.Designation,c.NewspaperId,c.ClaimMonth,c.ClaimYear,c.ClaimAmount,c.RestrictedAmount,c.PayableAmount,c.submitdate FROM employee a,employee_desig b,pis_newspaper c WHERE  a.desigid=b.DesigId    AND  a.empno=c.empno AND    c.IsActive='1' AND c.SubmitBy IS NULL AND c.SubmitDate IS NULL AND c.NewspaperBillId='0' ORDER BY submitdate ASC;"; 
	@Override
	public List<Object[]> getNewspaperApprovalList() throws Exception 
	{
		List<Object[]> NewspaperApprovalList = new ArrayList<>();
		try {

			Query q = manager.createNativeQuery(GETNEWSPAPERAPPROVALLIST);
			NewspaperApprovalList = (List<Object[]>) q.getResultList();

		} 
		catch (Exception e) 
		{
			logger.error(new Date() +"Inside DAO getNewspaperApprovalList "+e);
			e.printStackTrace();
		}

		return (NewspaperApprovalList);

	}
	

	private static final String NEWSPAPERAPPROVE_ADDNEWSPAPERBILL = "update pis_newspaper  set NewspaperBillId=:MaxNewspaperBillId,Remark=:remark,SubmitBy=:SubmitBy,SubmitDate=:sqlSubmitDate  where   NewspaperId=:NewspaperId ";
	
	@Override
	public int NewspaperApprove_AddNewspaperBill(String empno, double totalAmount, String FromDate, String ToDate,Map<String, String> map) throws Exception
	{
		int result = 0;

		try {

			NewspaperBill nb = new NewspaperBill();
			nb.setTotalAmount(totalAmount);
			nb.setFromDate(DateTimeFormatUtil.dateConversionSql(FromDate));
			nb.setToDate(DateTimeFormatUtil.dateConversionSql(ToDate));
			nb.setSubmitBy(empno);
			nb.setSubmitDate(LocalDate.now().toString());
			nb.setCreatedBy(empno);
			nb.setCreatedDate(sdtf.format(new Date()));
			manager.persist(nb);

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String NewspaperId = entry.getKey();
				String comments = entry.getValue();

				String Comment;

				if ("::".equalsIgnoreCase(comments)) {
					Comment = " ";
				} else {
					Comment = comments.substring(2);
				}

				Query query = manager.createNativeQuery(NEWSPAPERAPPROVE_ADDNEWSPAPERBILL);
				query.setParameter("NewspaperId", Integer.parseInt(NewspaperId));
				query.setParameter("MaxNewspaperBillId", nb.getNewspaperBillId());
				query.setParameter("remark", Comment);
				query.setParameter("SubmitBy", empno);
				query.setParameter("sqlSubmitDate", LocalDate.now().toString());
				result = query.executeUpdate();

			} // for loop closed

			result = result + 1;
		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO NewspaperApprove_AddNewspaperBill "+e);
			e.printStackTrace();
		}

		return (result);

	}
	
	
	private static final String GETMAXNEWSPAPERBILLID = "select max(NewspaperBillId) from pis_newspaper_bill";

	@Override
	public int getMaxNewspaperBillId() throws Exception
	{
		int MaxNewspaperBillId = 0;
		try {
			Query q = manager.createNativeQuery(GETMAXNEWSPAPERBILLID);
			MaxNewspaperBillId = (int) q.getSingleResult();
		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO getMaxNewspaperBillId "+ e);
			e.printStackTrace();
		} 
		return (MaxNewspaperBillId);

	}
	
	private static final String NEWSPAPERAPPROVE_UPDATENEWSPAPER =	"update pis_newspaper  set NewspaperBillId=:MaxNewspaperBillId,Remark=:remark,SubmitBy=:SubmitBy,SubmitDate=:sqlSubmitDate  where   NewspaperId=:NewspaperId ";

	@Override
	public int NewspaperApprove_UpdateNewspaper(Map<String, String> map, String empno, int MaxNewspaperBillId) throws Exception
	{
		int NewspaperApprove_UpdateNewspaper = 0;
		try {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String NewspaperId = entry.getKey();
				String comments = entry.getValue();

				String Comment;

				if ("::".equalsIgnoreCase(comments)) {
					Comment = " ";
				} else {
					Comment = comments.substring(2);
				}

				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String SubmitDate = sdf.format(d);
				java.sql.Date sqlSubmitDate = DateTimeFormatUtil.dateConversionSql(SubmitDate);

				Query query = manager.createNativeQuery(NEWSPAPERAPPROVE_UPDATENEWSPAPER);
				query.setParameter("NewspaperId", Integer.parseInt(NewspaperId));
				query.setParameter("MaxNewspaperBillId", MaxNewspaperBillId);
				query.setParameter("remark", Comment);
				query.setParameter("SubmitBy", empno);
				query.setParameter("sqlSubmitDate", sqlSubmitDate);
				NewspaperApprove_UpdateNewspaper = query.executeUpdate();

			} // for loop closed

		} // try closed
		catch (Exception e) {

			logger.error(new Date() +"Inside DAO NewspaperApprove_UpdateNewspaper "+e);
			e.printStackTrace();
			NewspaperApprove_UpdateNewspaper = 0;

		} 
		return (NewspaperApprove_UpdateNewspaper);
	}

	
	private static final String  GETNEWSPAPERAPPROVALPERIODEDITDETAILS ="SELECT   NewspaperBillId,FromDate,ToDate   FROM pis_newspaper_bill where  NewspaperBillId=:NewspaperBillId ";
	
	@Override
	public Object[] getNewspaperApprovalPeriodEditDetails(String NewspaperBillId)throws Exception
	{
		Object[] NewspaperApprovalPeriodEditDetails = null;

		try {

			Query q = manager.createNativeQuery(GETNEWSPAPERAPPROVALPERIODEDITDETAILS);
			q.setParameter("NewspaperBillId", Integer.parseInt(NewspaperBillId));
			NewspaperApprovalPeriodEditDetails = (Object[]) q.getSingleResult();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		 catch (Exception e) {

			 logger.error(new Date() +"Inside DAO getNewspaperApprovalPeriodEditDetails "+e);
			e.printStackTrace();
		}

		return (NewspaperApprovalPeriodEditDetails);

	}


	private static final  String UPDATENEWSPERIOD = "update pis_newspaper_bill set FromDate=:sqlFromDate,ToDate=:sqlToDate,ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate   where NewspaperBillId=:NewspaperBillId";
	@Override
	public int UpdateNewsPeriod(String empno, String NewspaperBillId, String FromDate, String ToDate)throws Exception
	{
		int UpdateNewsPeriod = 0;

		try {

			Query q = manager.createNativeQuery(UPDATENEWSPERIOD);

			Date d = new Date();
			String ModifiedDate = sdtf.format(d);

			java.sql.Date sqlFromDate = DateTimeFormatUtil.dateConversionSql(FromDate);
			java.sql.Date sqlToDate = DateTimeFormatUtil.dateConversionSql(ToDate);

			q.setParameter("NewspaperBillId", Integer.parseInt(NewspaperBillId));
			q.setParameter("sqlFromDate", sqlFromDate);
			q.setParameter("sqlToDate", sqlToDate);
			q.setParameter("ModifiedBy", empno);
			q.setParameter("ModifiedDate", ModifiedDate);

			UpdateNewsPeriod = q.executeUpdate();

		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO UpdateNewsPeriod "+ e);
			e.printStackTrace();
			UpdateNewsPeriod = 0;
		}

		return (UpdateNewsPeriod);

	}

	
	private static final String GETNEWSPAPERREPORTPRINTDATA=  "SELECT a.EmpName,b.Designation,c.ClaimMonth,c.ClaimYear,c.ClaimAmount,c.PayableAmount,c.Remark,d.FromDate,d.ToDate,d.TotalAmount,e.NewsRestrictAmt,c.RestrictedAmount  FROM employee a,employee_desig b,pis_newspaper c,pis_newspaper_bill d,pis_pay_level e  WHERE a.empno=c.empno AND a.desigid=b.desigid  AND c.PayLevelId=e.PayLevelId AND c.NewspaperBillId=d.NewspaperBillId   AND d.NewspaperBillId=:NewspaperBillId ORDER BY a.SrNo;  ";
	
	@Override
	public List<Object[]> getNewspaperReportPrintData(String NewspaperBillId) throws Exception
	{
		List<Object[]> NewspaperReportPrintData = new ArrayList<>();
		try {
			
			Query q = manager.createNativeQuery(GETNEWSPAPERREPORTPRINTDATA);
			q.setParameter("NewspaperBillId", Integer.parseInt(NewspaperBillId));
			NewspaperReportPrintData = (List<Object[]>) q.getResultList();

		} catch (Exception e) {

			logger.error(new Date() +"Inside DAO getNewspaperReportPrintData "+ e);
			e.printStackTrace();
		}
		return (NewspaperReportPrintData);
	}

	
	
	
	private static final String GETNEWSPAPERCONTINGENTBILLPRINTDATA = "SELECT FromDate,ToDate,TotalAmount FROM pis_newspaper_bill   WHERE  NewspaperBillId=:NewspaperBillId";
	
	@Override
	public Object[] getNewspaperContingentBillPrintData(String NewspaperBillId) throws Exception
	{
		Object[] NewspaperContingentBillPrintData = null;

		try {

			Query q = manager.createNativeQuery(GETNEWSPAPERCONTINGENTBILLPRINTDATA );
			q.setParameter("NewspaperBillId", Integer.parseInt(NewspaperBillId));
			NewspaperContingentBillPrintData = (Object[]) q.getSingleResult();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		
		catch (Exception e) 
		{
			logger.error(new Date() +"Inside DAO getNewspaperContingentBillPrintData "+ e);
			e.printStackTrace();
		}

		return (NewspaperContingentBillPrintData);

	}
	
	
	private static final String GETLABDETAILS = " From LabMaster";
	
	@Override
	public LabMaster getLabDetails() throws Exception
	{
		LabMaster LabDetails = null;
		try {

			Query q = manager.createQuery(GETLABDETAILS );
			LabDetails = (LabMaster) q.getSingleResult();

		} 
		catch (Exception e) 
		{
			logger.error(new Date() +"Inside DAO getLabDetails "+e);
			e.printStackTrace();
		}

		return LabDetails;

	}
	
	private static final String NEWSPAPERALLAPPROVEDORNOT="SELECT a.ClaimMonth,a.ClaimYear,a.ClaimAmount,a.RestrictedAmount,a.PayableAmount,b.PayLevel,b.PayGrade, em.EmpName, a.EmpNo\r\n"
			+ "FROM pis_newspaper a, pis_pay_level b, employee em  WHERE  a.PayLevelId=b.PayLevelId AND a.EmpNo=em.EmpNo AND a.ClaimMonth=:claimMonth AND a.Claimyear=:claimYear";
	
	@Override
	public List<Object[]> NewspaperAllApprovedOrNot(String claimMonth, String claimYear)throws Exception
	{
		List<Object[]> NewspaperUserPrintData = null;

		try {

			Query q = manager.createNativeQuery( NEWSPAPERALLAPPROVEDORNOT);
			q.setParameter("claimMonth", claimMonth);
			q.setParameter("claimYear", claimYear);
			NewspaperUserPrintData =  q.getResultList();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		 catch (Exception e) {

			 logger.error(new Date() +"Inside DAO NewspaperAllApprovedOrNot "+e);
			e.printStackTrace();
		}

		return (NewspaperUserPrintData);

	}
	
	private static final String NEWSPAPERSBYCONTINGENTBILL="SELECT a.ClaimMonth,a.ClaimYear,a.ClaimAmount,a.RestrictedAmount,a.PayableAmount,b.PayLevel,b.PayGrade, em.EmpName, a.EmpNo \r\n"
			+ "FROM pis_newspaper a, pis_pay_level b, employee em  \r\n"
			+ "WHERE  a.PayLevelId=b.PayLevelId AND a.EmpNo=em.EmpNo AND a.ContingentId=:ContingentId";
	
	@Override
	public List<Object[]> newspapersByContingentBill(Long ContingentId)throws Exception
	{
		List<Object[]> NewspaperPrintData = null;

		try {

			Query q = manager.createNativeQuery( NEWSPAPERSBYCONTINGENTBILL);
			q.setParameter("ContingentId", ContingentId);
			NewspaperPrintData =  q.getResultList();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		 catch (Exception e) {

			 logger.error(new Date() +"Inside DAO newspapersByContingentBill "+e);
			e.printStackTrace();
		}

		return (NewspaperPrintData);

	}
	
	private static final String NEWSPAPERCONTNINGENTAPPRTRANSBYID="SELECT tr.StatusCode, tr.ActionBy,em.empName, des.Designation, tr.ActionDate FROM  pis_newspaper_contingent_trans tr, employee em,employee_desig des WHERE  tr.ActionBy=em.empno AND em.DesigId=des.DesigId \r\n"
			+ "AND tr.ContinTransactionId IN ((SELECT MAX(ContinTransactionId) FROM pis_newspaper_contingent_trans WHERE StatusCode='CGT' AND ContingentId=:ContingentId), (SELECT MAX(ContinTransactionId) FROM pis_newspaper_contingent_trans WHERE StatusCode='ABP' AND ContingentId=:ContingentId),\r\n"
			+ "(SELECT MAX(ContinTransactionId) FROM pis_newspaper_contingent_trans WHERE StatusCode='ABV' AND ContingentId=:ContingentId), (SELECT MAX(ContinTransactionId) FROM pis_newspaper_contingent_trans WHERE StatusCode='ABA' AND ContingentId=:ContingentId), (SELECT MAX(ContinTransactionId) FROM pis_newspaper_contingent_trans WHERE StatusCode='ABD' AND ContingentId=:ContingentId))";
	
	@Override
	public List<Object[]> newspaperContningentApprTransById(Long ContingentId)throws Exception
	{
		List<Object[]> ContingentTransData = null;

		try {

			Query q = manager.createNativeQuery( NEWSPAPERCONTNINGENTAPPRTRANSBYID);
			q.setParameter("ContingentId", ContingentId);
			ContingentTransData =  q.getResultList();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		 catch (Exception e) {

			 logger.error(new Date() +"Inside DAO newspaperContningentApprTransById "+e);
			e.printStackTrace();
		}

		return (ContingentTransData);

	}
	
	private static final String TELEPHONEALLAPPROVEDORNOT="SELECT a.ClaimMonth,a.ClaimYear,a.GrossTotal,a.RestrictedAmt,a.PayableAmount,b.PayLevel,b.PayGrade, em.EmpName, a.Empid\r\n"
			+ "FROM pis_tele a, pis_pay_level b, employee em  WHERE  a.PayLevelId=b.PayLevelId AND a.EmpId=em.EmpNo AND a.ClaimMonth=:claimMonth AND a.Claimyear=:claimYear ";
	
	@Override
	public List<Object[]> TelePhoneAllApprovedOrNot(String claimMonth, String claimYear)throws Exception
	{
		List<Object[]>  TelePhoneUserPrintData = null;

		try {

			Query q = manager.createNativeQuery( TELEPHONEALLAPPROVEDORNOT);
			q.setParameter("claimMonth", claimMonth);
			q.setParameter("claimYear", claimYear);
			TelePhoneUserPrintData =  q.getResultList();

		}
		catch (NoResultException e)
		{
			System.err.println("No Result found Exception");
		}	
		 catch (Exception e) {

			 logger.error(new Date() +"Inside DAO TelePhoneAllApprovedOrNot "+e);
			e.printStackTrace();
		}

		return ( TelePhoneUserPrintData);

	}
	
	private static final String GETEMPDGMEMPNO  ="SELECT dgmempno FROM dgm_master";
	
	@Override
	public List<String> GetEmpDGMEmpNo() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPDGMEMPNO);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list;
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpDHEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String EMPONLOGINTYPE  ="SELECT e.empno,e.empname,ed.desigid, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.desigid = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType";

	@Override
	public Object[] empOnLogintype(String Logintype) throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(EMPONLOGINTYPE);
			query.setParameter("loginType", Logintype);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO empOnLogintype " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Object[]> getNewspaperContingentList(String logintype,String fromdate,String todate) throws Exception
	{
		
		try {
			
			Query query= manager.createNativeQuery("call newspaper_contingent_bills_list(:logintype,:fromdate,:todate)");
			query.setParameter("logintype", logintype);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getNewspaperContingentList " + e); 
			return new ArrayList<Object[]>();
		}
	}
	
	private static final String GETDGMEMPNO  ="SELECT dgm.dgmempno FROM employee e, division_master dm,dgm_master dgm WHERE e.divisionid=dm.divisionid AND dm.dgmid=dgm.dgmid AND e.empno=:empno";

	@Override
	
	public String GetDGMEmpNo(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETDGMEMPNO);
			query.setParameter("empno", empno);
			return (String) query.getSingleResult();
						
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetDGMEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	@Override
	public Newspaper findNewspaperApply(long newspaperApplyId) throws Exception{
		try {
			return manager.find(Newspaper.class, newspaperApplyId);
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO findNewspaperApply " + e);
			e.printStackTrace();
			return null;
		}	
	}
	
	private static final String FINDEMPBYEMPNO  ="FROM Employee WHERE EmpNo=:EmpNo";
	
	@Override
	public Employee findEmpByEmpNo(String empNo) throws Exception{
		Employee employee=null;
		try {			
			Query query= manager.createQuery(FINDEMPBYEMPNO);
			query.setParameter("EmpNo", empNo);
			employee= (Employee) query.getSingleResult();

		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO findEmpByEmpNo " + e);
			e.printStackTrace();

		}		
		return employee;
	}
	
	@Override
	public NewspaperContingent findNewspaperContingent(String ContingentId) throws Exception{
		NewspaperContingent Contingent=null;
		try {			
			Contingent= manager.find(NewspaperContingent.class,Long.parseLong(ContingentId));

		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO findNewspaperContingent " + e);
			e.printStackTrace();

		}		
		return Contingent;
	}
	
	@Override
	public long addNotification(EMSNotification notification) throws Exception{
		try {
			manager.persist(notification);
			manager.flush();

			return notification.getNotificationId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO addNotification "+e);
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public long addNewspaperContingent(NewspaperContingent newspaperContingent) throws Exception{
		try {
			manager.persist(newspaperContingent);
			manager.flush();

			return newspaperContingent.getContingentId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO addNewspaperContingent "+e);
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public long addNewspaperContingentTrans(NewspaperContingentTrans newspaperContingentTrans) throws Exception {
		try {
			manager.persist(newspaperContingentTrans);
			manager.flush();

			return newspaperContingentTrans.getContingentId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO addNewspaperContingentTrans "+e);
			e.printStackTrace();
		}
		return 0;
	}
	
	private static final String DGMALLBANK="SELECT a.NewspaperId, a.EmpNo, em.EmpName, des.Designation, a.ClaimMonth, a.ClaimYear, a.NewspaperStatusCode, c.NewsPaperStatus, c.StatusColor FROM pis_newspaper a, employee em, employee_desig des, division_master b, pis_newspaper_status c ,dgm_master d WHERE a.IsActive=1 AND a.EmpNo=em.EmpNo AND em.DesigId=des.DesigId AND a.NewspaperStatusCode='FWD' AND em.DivisionId=b.DivisionId AND b.DGMId=d.DGMId AND d.DGMEmpNo=:empNo AND a.NewspaperStatusCode=c.NewsPaperStatusCode UNION SELECT a.NewspaperId, a.EmpNo, em.EmpName, des.Designation, a.ClaimMonth, a.ClaimYear, a.NewspaperStatusCode, b.NewsPaperStatus, b.StatusColor FROM pis_newspaper a, employee em, employee_desig des,  pis_newspaper_status b WHERE a.IsActive=1 AND a.EmpNo=em.EmpNo AND em.DesigId=des.DesigId AND a.NewspaperStatusCode='VDG' AND a.NewspaperStatusCode=b.NewsPaperStatusCode AND :LoginType='K'";

	public List<Object[]> findDGMNewspaperList(String empNo, String LoginType) throws Exception{
		List<Object[]> newspaperList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(DGMALLBANK);
			q.setParameter("empNo", empNo);
			q.setParameter("LoginType", LoginType);
			newspaperList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findDGMNewspaperList "+e);
			e.printStackTrace();
		}
		return newspaperList;
	}
	
	private static final String NEWSPAPERREMARKSHISTORY  ="SELECT cat.NewspaperApplyId,cat.Remark,cs.NewsPaperStatusCode,e.EmpName,ed.Designation FROM pis_newspaper_status cs,pis_newspaperapply_trans cat,pis_newspaper ca,employee e,employee_desig ed WHERE cat.ActionBy = e.EmpNo AND e.DesigId = ed.DesigId AND cs.NewsPaperStatusCode = cat.NewspapertatusCode AND ca.NewspaperId = cat.NewspaperApplyId AND TRIM(cat.Remark)<>'' AND ca.NewspaperId=:NewspaperId ORDER BY cat.ActionDate ASC";

	@Override
	public List<Object[]> newsPaperRemarksHistory(String NewspaperId) throws Exception {
		
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(NEWSPAPERREMARKSHISTORY);
			query.setParameter("NewspaperId", NewspaperId);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO newsPaperRemarksHistory " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String POALLBANK="SELECT a.NewspaperId, a.EmpNo, em.EmpName, des.Designation, a.ClaimMonth, a.ClaimYear, a.NewspaperStatusCode, b.NewsPaperStatus, b.StatusColor FROM pis_newspaper a, employee em, employee_desig des,  pis_newspaper_status b WHERE a.IsActive=1 AND a.EmpNo=em.EmpNo AND em.DesigId=des.DesigId AND a.NewspaperStatusCode='VDG' AND a.NewspaperStatusCode=b.NewsPaperStatusCode";

	@Override
	public List<Object[]> findPONewspaperList() throws Exception{
		List<Object[]> newspaperList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(POALLBANK);
			newspaperList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findPONewspaperList "+e );
			e.printStackTrace();
		}
		return newspaperList;
	}
	
	private static final String AOALLBANK="SELECT a.NewspaperId, a.EmpNo, em.EmpName, des.Designation, a.ClaimMonth, a.ClaimYear, a.NewspaperStatusCode, b.NewsPaperStatus, b.StatusColor FROM pis_newspaper a, employee em, employee_desig des,  pis_newspaper_status b WHERE a.IsActive=1 AND a.EmpNo=em.EmpNo AND em.DesigId=des.DesigId AND a.NewspaperStatusCode='VBP' AND a.NewspaperStatusCode=b.NewsPaperStatusCode";

	@Override
	public List<Object[]> findAONewspaperList() throws Exception {
		List<Object[]> newspaperList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(AOALLBANK);
			newspaperList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findAONewspaperList "+e );
			e.printStackTrace();
		}
		return newspaperList;
	}
	
	private static final String APPROVEDTOBILL="SELECT a.NewspaperId, a.EmpNo, em.EmpName, des.Designation, a.ClaimMonth,  a.ClaimYear, a.ClaimAmount, a.RestrictedAmount, a.PayableAmount, a.NewspaperStatusCode, b.NewsPaperStatus, b.StatusColor, a.NewsAppliedDate FROM pis_newspaper a, employee em, employee_desig des,  pis_newspaper_status b WHERE a.IsActive=1 AND a.EmpNo=em.EmpNo AND em.DesigId=des.DesigId AND a.NewspaperStatusCode='VBA' AND a.NewspaperStatusCode=b.NewsPaperStatusCode AND a.NewsAppliedDate <=:todate AND a.ContingentId=0";

	@Override
	public List<Object[]> findApprovedNewspaperList (String todate) throws Exception {
		List<Object[]> newspaperList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(APPROVEDTOBILL);
			q.setParameter("todate", todate);
			newspaperList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findApprovedNewspaperList "+e );
			e.printStackTrace();
		}
		return newspaperList;
	}
	
	private static final String NEWSPAPERCLAIMLIST="SELECT a.NewspaperId, a.ClaimAmount,a.RestrictedAmount, a.NewspaperStatusCode,  a.isactive, a.NewsAppliedDate, a.ContingentId, a.empno, em.empname, b.ClaimsCount, b.ContingentBillNo, b.ContingentStatusCode \r\n"
			+ "FROM pis_newspaper a, pis_newspaper_contingent b, employee em\r\n"
			+ "WHERE a.ContingentId=b.ContingentId AND b.ContingentId=:contingentid AND a.empno=em.empNo";

	@Override
	public List<Object[]> NewspaperContingentClaimList(String contingentid) throws Exception {
		List<Object[]> newspaperList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(NEWSPAPERCLAIMLIST);
			q.setParameter("contingentid", contingentid);
			newspaperList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO NewspaperContingentClaimList"+e );
			e.printStackTrace();
		}
		return newspaperList;
	}
	
	private static final String NEWSPAPERTRANSABYID="SELECT tra.NewspaperTransactionId,emp.empno,emp.empname,des.designation,tra.ActionDate,tra.Remark,sta.NewsPaperStatus,sta.StatusColor  \r\n"
			+ "FROM pis_newspaperapply_trans tra, pis_newspaper_status sta,employee emp,employee_desig des,pis_newspaper n WHERE n.NewspaperId = tra.NewspaperApplyId AND tra.NewspapertatusCode = sta.NewsPaperStatusCode AND tra.ActionBy=emp.empno AND emp.desigid=des.desigid AND n.NewspaperId =:NewspaperId ORDER BY actiondate";
	
	@Override
	public List<Object[]> newspaperTransaById(long NewspaperId) throws Exception
	{
		List<Object[]> bankDetailList=new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery(NEWSPAPERTRANSABYID);
			query.setParameter("NewspaperId", NewspaperId);
			bankDetailList= query.getResultList();
		}catch(Exception e) {
			logger.error(new Date() +"Inside DAO newspaperTransaById "+e);
			e.printStackTrace();
		}
		return bankDetailList;
	}
	
	private static final String NEWSPAPERCONTINGENTDATA  ="SELECT cc.contingentid, cc.ContingentBillNo,cc.ContingentDate,ClaimsCount,cc.ContingentStatusCode ,cc.Remarks ,cs.chssstatus, cc.ApprovalDate,cc.gentilldate \r\n"
			+ "FROM pis_newspaper_contingent cc , chss_contingent_status cs \r\n"
			+ "WHERE  cc.ContingentStatusCode = cs.CHSSStatusCode AND cc.ContingentId=:contingentid";
	@Override
	public Object[] newspaperContingentData(String contingentid) throws Exception
	
	{
		
		try {
			Query query= manager.createNativeQuery(NEWSPAPERCONTINGENTDATA);
			query.setParameter("contingentid", contingentid);
			return (Object[])query.getResultList().get(0);
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO newspaperContingentData " + e);
			return null;
		}
	}
	
///////////////////////////////////////// Telephone code ///////////////////////////////////////////////
	
		private static final String GETDEVICELIST = "SELECT a.DeviceId,a.DeviceName from pis_tele_device a WHERE a.DeviceId NOT IN (SELECT DeviceId from pis_tele_users WHERE EmpId=:empid  AND DeviceId='LL' AND IsActive='1') ";
			
		@Override
		public List<Object[]> getDeviceList(String empid) throws Exception
		{
			List<Object[]> DeviceList = new ArrayList<>();
			try {
		
				Query query = manager.createNativeQuery(GETDEVICELIST);
				query.setParameter("empid", Integer.parseInt(empid));
				DeviceList = (List<Object[]>) query.getResultList();
		
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getDeviceList "+e);
				e.printStackTrace();
				
			} 
			
			return (DeviceList);
		}


		private static final String GETTELEDEVICELIST = "SELECT  a.TeleUsersId,b.DeviceName,a.DeviceNo,b.DeviceId from pis_tele_users a ,pis_tele_device b  WHERE EmpId=:empid and a.DeviceId=b.DeviceId and IsActive='1' " ;
		
		@Override
		public List<Object[]> getTeleDeviceList(String empid) throws Exception
		{
			List<Object[]> TeleDeviceList=new ArrayList<>();
			try
			{
				Query query = manager.createNativeQuery(GETTELEDEVICELIST);
				query.setParameter("empid", Integer.parseInt(empid));
				TeleDeviceList = (List<Object[]>) query.getResultList();
			}
			catch(Exception e)
			{
				logger.error(new Date() +"Inside DAO getTeleDeviceList "+e);
				e.printStackTrace();
			}
			return TeleDeviceList;
		}



		@Override
		public int AddTeleUsers(String Empid,String deviceId,String deviceNo) throws Exception
		{
			try
			{
				TelephoneUsers tu=new  TelephoneUsers();
				 
				tu.setEmpId(Empid);
				tu.setDeviceId(deviceId);
				tu.setDeviceNo(deviceNo);
				tu.setIsActive(1);
				tu.setCreatedBy(Empid);
				tu.setCreatedDate(sdtf.format(new Date()));
				manager.persist(tu);
				
				return 1;
			
			}
			catch(Exception e)
			{
				logger.error(new Date() +"Inside DAO AddTeleUsers "+e);
				e.printStackTrace();
				return 0;
			}
		}



		private static final String GETTELEDEVICEEDITDETAILS = "SELECT  a.TeleUsersId,b.DeviceName,a.DeviceNo from pis_tele_users a ,pis_tele_device b    WHERE a.EmpId=:empid and a.DeviceId=b.DeviceId and a.TeleUsersId=:TeleUsersId";
		
		@Override
		public Object[] getTeleDeviceEditDetails(String empid, String TeleUsersId) 
		{
			Object[] TeleDeviceEditDetails = null;
			try 
			{
				Query query = manager.createNativeQuery(GETTELEDEVICEEDITDETAILS);
				query.setParameter("empid", empid);
				query.setParameter("TeleUsersId", Integer.parseInt(TeleUsersId));
				TeleDeviceEditDetails = (Object[]) query.getSingleResult();

			}
			catch (NoResultException e)
			{
				System.err.println("No Result found Exception");
			}	
			catch (Exception e) 
			{
				logger.error(new Date() +"Inside DAO getTeleDeviceEditDetails "+e);
				e.printStackTrace();
			}

			return TeleDeviceEditDetails;

		}


		private static final String UPDATETELEUSER = "update pis_tele_users set DeviceNo=:deviceNo,ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate   where TeleUsersId=:TeleUsersId and EmpId=:Empid";
		
		@Override
		public int UpdateTeleUser(String Empid,String TeleUsersId,String deviceNo)
		{
			int UpdateTeleUser = 0;

			try {

				Query query = manager.createNativeQuery(UPDATETELEUSER);

				query.setParameter("TeleUsersId", Integer.parseInt(TeleUsersId));
				query.setParameter("deviceNo", deviceNo);
				query.setParameter("ModifiedBy", Empid);
				query.setParameter("ModifiedDate", sdtf.format(new Date()));
				query.setParameter("Empid", Empid);
				UpdateTeleUser = query.executeUpdate();
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO UpdateTeleUser "+e);
				e.printStackTrace();
			}

			return (UpdateTeleUser);

		}


		private static final String DELETETELEUSER =  "update pis_tele_users  set IsActive='0'  where TeleUsersId=:TeleUsersId and EmpId=:Empid ";
		@Override
		public int DeleteTeleUser(String Empid,String TeleUsersId)
		{
			int DeleteTeleUser = 0;
			try {

				Query query = manager.createNativeQuery(DELETETELEUSER);
				query.setParameter("TeleUsersId", Integer.parseInt(TeleUsersId));
				query.setParameter("Empid", Empid);
				DeleteTeleUser = query.executeUpdate();
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO DeleteTeleUser "+ e);
				e.printStackTrace();
			} 
		
			return (DeleteTeleUser);
		}




		private static final String GETTELECLAIMLIST = "SELECT a.TeleId,a.TeleAppliedDate,a.ClaimMonth,a.ClaimYear,a.GrossTotal,a.RestrictedAmt,a.PayableAmount,a.TeleBillId,a.SubmitBy,a.TeleForwardId,a.Status,a.ApprovalRemark  FROM pis_tele a where a.EmpId=:Empid and  a.IsActive='1' ";
		
		@Override
		public List<Object[]> getTeleClaimList(String Empid) 
		{
			List<Object[]> TeleClaimList = new ArrayList<>();
			try {

				Query query = manager.createNativeQuery(GETTELECLAIMLIST);
				query.setParameter("Empid", Empid);
				TeleClaimList = (List<Object[]>) query.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTeleClaimList "+ e);
				e.printStackTrace();
			} 
			return (TeleClaimList);
		}



		private static final String GETPAYLEVELANDTELERECTRICTAMT = "SELECT e.empName,a.SBIACCNO,a.SBIACCNO AS 'OTHER_ACC_NO' ,a.GPFNO,a.PayLevelId,b.PayLevel,b.TeleRestrictAmt FROM employee_details a,pis_pay_level b , employee e WHERE  a.PayLevelId=b.PayLevelId AND a.EMPno=:Empid AND e.empno = a.empno";
		@Override
		public Object[] getPayLevelAndTeleRectrictAmt(String Empid)
		{
			Object[] PayLevelAndTeleRectrictAmt = null;
			try {
	
				Query q = manager.createNativeQuery(GETPAYLEVELANDTELERECTRICTAMT);
				q.setParameter("Empid", Empid);
				PayLevelAndTeleRectrictAmt = (Object[]) q.getSingleResult();
	
			} 
			catch (NoResultException e)
			{
				System.err.println("No Result found Exception");
			}	
			catch (Exception e) {
				logger.error(new Date() +"Inside DAO getPayLevelAndTeleRectrictAmt "+ e);
				e.printStackTrace();
			} 
			return (PayLevelAndTeleRectrictAmt);
	
		}
		
		private static final String GETTELESPECIALPERMISSION="SELECT a.Empno,a.TeleSpecialPermission FROM Employee_details a WHERE  a.TeleSpecialPermission='Y' AND a.Empno=:Empid";
		@Override
		public Object[] getTeleSpecialpermission(String Empid) 
		{
			Object[] TeleSpecialpermission = null;
			try {

				Query q = manager.createNativeQuery(GETTELESPECIALPERMISSION);
				q.setParameter("Empid", Empid);
				TeleSpecialpermission = (Object[]) q.getSingleResult();

			}catch (NoResultException e)
			{
				System.err.println("No Result found Exception");
			}			
			catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTeleSpecialpermission "+ e);
				e.printStackTrace();
			} 
			return (TeleSpecialpermission);

		}


		@Override
		public int addTeleClaim(String Empid, String TotalBasic, String TotalTax, double GrossTotal,
				double RestrictedAmount, double PayableAmount, String PayLevelId, String ClaimMonth, String ClaimYear,
				String IsBroadBand, String[] TeleFromDate, String[] TeleToDate, String[] TeleUsersId,
				String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount,
				String[] TotalAmount) 
		{
			int result = 0;
			try {

				Telephone t = new Telephone();

				t.setEmpId(Empid);
				t.setClaimMonth(ClaimMonth);
				t.setClaimYear(ClaimYear);
				t.setTotalBasic(Double.parseDouble(TotalBasic));
				t.setTotalTax(Double.parseDouble(TotalTax));
				t.setGrossTotal(GrossTotal);
				t.setRestrictedAmt(RestrictedAmount);
				t.setPayableAmount(PayableAmount);
				t.setTeleAppliedDate(DateTimeFormatUtil.TodayDateInSqlFormat());
				t.setIsBroadBand(IsBroadBand);
				t.setPayLevelId(Integer.parseInt(PayLevelId));
				t.setIsActive(1);
				t.setCreatedBy(Empid);
				t.setCreatedDate(sdtf.format(new Date()));
				manager.persist(t);
				
				
				for (int i = 0; i < TeleUsersId.length; i++) 
				{

					TelephoneDetails td = new TelephoneDetails();
					td.setTeleId(t.getTeleId());
					td.setTeleFromDate(DateTimeFormatUtil.dateConversionSql(TeleFromDate[i]));
					td.setTeleToDate(DateTimeFormatUtil.dateConversionSql(TeleToDate[i]));
					td.setTeleUsersId(Integer.parseInt(TeleUsersId[i]));
					td.setTeleBillNo(TeleBillNo[i]);
					td.setTeleBillDate(DateTimeFormatUtil.dateConversionSql(TeleBillDate[i]));
					td.setBasicAmount(Double.parseDouble(BasicAmount[i]));
					td.setTaxAmount(Double.parseDouble(TaxAmount[i]));
					td.setTotalAmount(Double.parseDouble(TotalAmount[i]));
					td.setCreatedBy(Empid);
					td.setCreatedDate(sdtf.format(new Date()));
					manager.persist(td);
				}

				result = 1;

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO addTeleClaim "+ e);
				e.printStackTrace();
			} 

			return (result);
		}

		
		private static final String GETMAXTELEID = "select max(TeleId) from pis_tele" ;
		
		@Override
		public int getMaxTeleId()
		{
			int MaxTeleId = 0;
			try {
		
				Query q = manager.createNativeQuery(GETMAXTELEID);
				MaxTeleId = (int) q.getSingleResult();
		
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getMaxTeleId "+ e);
				e.printStackTrace();
			} 
			return (MaxTeleId);
		
		}



		private static final String GETCHECKPERIODOFTELEALREADYPRESENTORNOT = "SELECT  TeleId,TeleBillId from pis_tele   WHERE IsActive='1' and ClaimMonth=:ClaimMonth   and ClaimYear=:ClaimYear and EmpId=:Empid";

		@Override
		public Object[] getCheckPeriodOfTeleAlreadyPresentOrNot(String Empid,String  ClaimMonth,String ClaimYear)
		{
			Object[] getCheckPeriodAlreadyPresentOrNot = null;
			try {
		
				Query q = manager.createNativeQuery(GETCHECKPERIODOFTELEALREADYPRESENTORNOT);
				q.setParameter("ClaimMonth", ClaimMonth);
				q.setParameter("ClaimYear", ClaimYear);
				q.setParameter("Empid", Empid);
				getCheckPeriodAlreadyPresentOrNot = (Object[]) q.getSingleResult();
		
			} 
			catch (NoResultException e)
			{
				System.err.println("No Result found Exception");
			}	
			catch (Exception e) {
				logger.error(new Date() +"Inside DAO getCheckPeriodOfTeleAlreadyPresentOrNot "+ e);
				e.printStackTrace();
			} 
			return (getCheckPeriodAlreadyPresentOrNot);
		
		}


		private static final String GETTELECLAIMEDITDETAILS = "SELECT a.TeleId,a.TotalBasic,a.TotalTax,a.GrossTotal,a.RestrictedAmt,b.TeleDId,b.TeleFromDate,b.TeleToDate,a.ClaimMonth,a.ClaimYear,b.TeleUsersId,b.TeleBillNo,b.TeleBillDate,b.BasicAmount,b.TaxAmount,b.TotalAmount,c.DeviceNo,d.DeviceId,em.EmpName,f.designation,a.PayableAmount,a.IsBroadBand,e.SBIACCNO,e.SBIACCNO AS 'otheraccno',e.GPFNO,g.PayLevel,a.TeleForwardId FROM pis_tele a ,pis_tele_d b,pis_tele_users c ,pis_tele_device d ,employee_details e,employee em ,employee_desig f,pis_pay_level g WHERE  a.TeleId=b.TeleId AND  b.TeleUsersId=c.TeleUsersId  AND c.DeviceId=d.DeviceId AND e.empno= em.empno AND a.Empid=e.EMPno AND em.Desigid=f.desigid AND a.PayLevelId=g.PayLevelId  AND a.TeleId=:TeleId" ;
		@Override
		public List<Object[]> getTeleClaimEditDetails(String TeleId) 
		{
			List<Object[]> TeleClaimEditDetails = new ArrayList<Object[]>();
			try {

				Query q = manager.createNativeQuery(GETTELECLAIMEDITDETAILS);
				q.setParameter("TeleId", Integer.parseInt(TeleId));
				TeleClaimEditDetails = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTeleClaimEditDetails "+ e);
				e.printStackTrace();
			}
			return (TeleClaimEditDetails);

		}

		private static final String UPDATETELE =  "update pis_tele set TotalBasic=:TotalBasic,TotalTax=:TotalTax,GrossTotal=:GrossTotal,PayableAmount=:PayableAmount,IsBroadBand=:IsBroadBand,ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate,Status=:status   where TeleId=:TeleId and EmpId=:Empid" ;
		private static final String UPDATETELEDETAILS =  "update pis_tele_d set TeleFromDate=:sqlFromDate,TeleToDate=:sqlTeleToDate,TeleBillNo=:TeleBillNo,TeleBillDate=:sqlTeleBillDate,BasicAmount=:BasicAmount,TaxAmount=:TaxAmount,TotalAmount=:TotalAmount,ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate   where TeleDId=:TeleDId ";
		@Override
		public int  updateTele(String Empid,String TeleId,String TotalBasic,String TotalTax,double GrossTotal,double PayableAmount,String IsBroadBand,String[] TeleDId,String[] TeleFromDate,String[] TeleToDate,String[] TeleBillNo,String[] TeleBillDate,String[] BasicAmount,String[] TaxAmount,String[] TotalAmount,String UserRemark,int fid)
		{
			int updateTele = 0;
			int updateTeleD = 0;
			int updateTeleF = 0;
			int result = 0;
			try {
		
				Query q = manager.createNativeQuery(UPDATETELE);
				q.setParameter("TeleId", Integer.parseInt(TeleId));
				q.setParameter("TotalBasic", Double.parseDouble(TotalBasic));
				q.setParameter("TotalTax", Double.parseDouble(TotalTax));
				q.setParameter("GrossTotal", GrossTotal);
				q.setParameter("PayableAmount", PayableAmount);
				q.setParameter("IsBroadBand", IsBroadBand);
				q.setParameter("status", "A");
				q.setParameter("ModifiedBy", Empid);
				q.setParameter("ModifiedDate", sdtf.format(new Date()));
				q.setParameter("Empid", Empid);
				updateTele = q.executeUpdate();
		
				for (int i = 0; i < TeleDId.length; i++) {
		
					Query q1 = manager.createNativeQuery(UPDATETELEDETAILS);
					q1.setParameter("TeleDId", Integer.parseInt(TeleDId[i]));
					q1.setParameter("sqlFromDate", DateTimeFormatUtil.dateConversionSql(TeleFromDate[i]));
					q1.setParameter("sqlTeleToDate", DateTimeFormatUtil.dateConversionSql(TeleToDate[i]));
					q1.setParameter("TeleBillNo", TeleBillNo[i]);
					q1.setParameter("sqlTeleBillDate", DateTimeFormatUtil.dateConversionSql(TeleBillDate[i]));
					q1.setParameter("BasicAmount", Double.parseDouble(BasicAmount[i]));
					q1.setParameter("TaxAmount", Double.parseDouble(TaxAmount[i]));
					q1.setParameter("TotalAmount", Double.parseDouble(TotalAmount[i]));
					q1.setParameter("ModifiedBy", Empid);
					q1.setParameter("ModifiedDate", sdtf.format(new Date()));
					updateTeleD = q1.executeUpdate();
		
				}
		
				if (fid > 0) {
					Query q2 = manager.createNativeQuery(
							"update pis_tele_forward set SendBack=:SendBack,UserRemark=:UserRemark where TeleForwardId=:TeleForwardId ");
					q2.setParameter("TeleForwardId", fid);
					q2.setParameter("SendBack", "A");
					q2.setParameter("UserRemark", UserRemark);
		
					updateTeleF = q2.executeUpdate();
				}
		
				result = updateTele + updateTeleD;
		
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO updateTele "+ e);
				e.printStackTrace();
			}
		
			return (result);
		
		}



		private static final String DELETETELEPHONE = "update pis_tele  set IsActive='0',ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate  where TeleId=:TeleId  ";

		@Override
		public int DeleteTelephone(String TeleId, String sessionEmpid) 
		{
			int DeleteTelephone = 0;

			try {

				Query q = manager.createNativeQuery(DELETETELEPHONE );
				q.setParameter("TeleId", Integer.parseInt(TeleId));
				q.setParameter("ModifiedBy", sessionEmpid);
				q.setParameter("ModifiedDate", sdtf.format(new Date()));
				DeleteTelephone = q.executeUpdate();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO DeleteTelephone "+ e);
				e.printStackTrace();
			}

			return (DeleteTelephone);

		}
		
		@Override
		public int InsertTeleUserFlagAndGenerateTeleForwardId(String Empid) 
		{
			int result = 0;
			try {

				TelephoneForward tf = new TelephoneForward();

				tf.setTeleUserFlag("F");
				tf.setCreatedBy(Empid);
				tf.setCreatedDate(sdtf.format(new Date()));
				manager.persist(tf);

				result = 1;

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO InsertTeleUserFlagAndGenerateTeleForwardId "+ e);
				e.printStackTrace();
			}

			return (result);

		}
 
		private static final String GETMAXTELEFORWARDID = "select max(TeleForwardId) from pis_teleForward";
		@Override
		public int getMaxTeleForwardId() 
		{
			int MaxTeleForwardId = 0;
			try {
				Query q = manager.createNativeQuery(GETMAXTELEFORWARDID);
				MaxTeleForwardId = (int) q.getSingleResult();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getMaxTeleForwardId"+e);
				e.printStackTrace();
			}
			return (MaxTeleForwardId);

		}


		private static final String UPDATETELEBYTELEFORWARDID ="update pis_tele  set TeleForwardId=:MaxTeleForwardId,Status=:status  where   TeleId=:TeleId ";
		@Override
		public int UpdateTeleByTeleForwardId(String Empid, String[] TeleIdArray) 
		{
			int result = 0;
			try {

				TelephoneForward tf = new TelephoneForward();
				tf.setTeleUserFlag("F");
				tf.setSendBack("N");
				tf.setCreatedBy(Empid);
				tf.setCreatedDate(sdtf.format(new Date()));
				manager.persist(tf);

				for (int i = 0; i < TeleIdArray.length; i++) {

					Query query = manager.createNativeQuery(UPDATETELEBYTELEFORWARDID);
					query.setParameter("TeleId", Integer.parseInt(TeleIdArray[i]));
					query.setParameter("MaxTeleForwardId", tf.getTeleForwardId());
					query.setParameter("status", "F");
					result = query.executeUpdate();

				} // for loop closed

				result = result + 1;
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO UpdateTeleByTeleForwardId "+ e);
				e.printStackTrace();
			}
			return (result);
		}
		
		private static final String GETTELEPHONEUSERPRINTSINGLEDATA = "SELECT em.EmpName,a.SBIACCNO,a.GPFNO,b.designation,c.TeleId,c.ClaimMonth,c.ClaimYear,c.GrossTotal,c.RestrictedAmt,c.PayableAmount,c.TeleForwardId,c.IsBroadBand,a.SBIACCNO AS 'OTHER_ACC_NO', em.EmpNo, d.PayLevel FROM employee_details a, employee_desig b, pis_tele c, employee em, pis_pay_level d WHERE em.desigid=b.desigid AND em.empno=a.empno  AND  a.EMPno=c.Empid AND TeleForwardId=:TeleForwardId AND a.PayLevelId=d.PayLevelId";
		@Override
		public List<Object[]> getTelephoneUserPrintSingleData(String TeleForwardId) 
		{
			List<Object[]> TelephoneUserPrintSingleData = new ArrayList<>();
			try {

				Query q = manager.createNativeQuery(GETTELEPHONEUSERPRINTSINGLEDATA);
				q.setParameter("TeleForwardId", Integer.parseInt(TeleForwardId));
				TelephoneUserPrintSingleData = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephoneUserPrintSingleData "+ e);
				e.printStackTrace();
			}
			return (TelephoneUserPrintSingleData);

		}
	
		// me added
		
		 
		  private static final String GETTELEPHONEUSERPRINTSINGLEDATABYMONTH = "SELECT em.EmpName,a.SBIACCNO,a.GPFNO,b.designation,c.TeleId,c.ClaimMonth,c.ClaimYear,c.GrossTotal,c.RestrictedAmt,c.PayableAmount,c.TeleForwardId,c.IsBroadBand,a.SBIACCNO AS 'OTHER_ACC_NO', em.EmpNo, d.PayLevel,d.PayGrade, (SELECT member_name FROM pis_emp_family_details e WHERE e.empid=em.empid  AND relation_id IN (5,8)) AS 'member name' FROM employee_details a, employee_desig b, pis_tele c, employee em, pis_pay_level d WHERE em.desigid=b.desigid AND em.empno=a.empno  AND  a.EMPno=c.Empid AND TeleForwardId=:TeleForwardId AND TeleId=:teleId AND a.PayLevelId=d.PayLevelId ";
		@Override
		public List<Object[]> getTelephoneUserPrintSingleDataByMonth(String TeleForwardId,String teleId) 
		{
			List<Object[]> TelephoneUserPrintSingleData = new ArrayList<>();
			try {

				Query q = manager.createNativeQuery(GETTELEPHONEUSERPRINTSINGLEDATABYMONTH);
				q.setParameter("TeleForwardId", Integer.parseInt(TeleForwardId));
				q.setParameter("teleId", Integer.parseInt(teleId));
				TelephoneUserPrintSingleData = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephoneUserPrintSingleData "+ e);
				e.printStackTrace();
			}
			return (TelephoneUserPrintSingleData);

		}


		private static final String GETTELEPHONEUSERPRINTMULTIDATA =  "SELECT a.TeleId,b.TeleFromDate,b.TeleToDate,b.TotalAmount,b.TeleDId,b.TeleBillNo,b.TeleBillDate,c.DeviceNo,a.TeleForwardId, d.DeviceName, b.TaxAmount from pis_tele a ,pis_tele_d b,pis_tele_users c, pis_tele_device d WHERE a.TeleId=b.TeleId AND b.TeleUsersId=c.TeleUsersId AND a.TeleForwardId=:TeleForwardId AND c.DeviceId=d.DeviceId";
		@Override
		public List<Object[]> getTelephoneUserPrintMultiData(String TeleForwardId) 											
		{
			List<Object[]> TelephoneUserPrintMultiData = new ArrayList<>();
			try {

				Query q = manager.createNativeQuery(GETTELEPHONEUSERPRINTMULTIDATA);
				q.setParameter("TeleForwardId", Integer.parseInt(TeleForwardId));
				TelephoneUserPrintMultiData = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephoneUserPrintMultiData "+ e);
				e.printStackTrace();
			}
			return (TelephoneUserPrintMultiData);

		}
		 

////////////////////////////////Approvals///////////////////////////////////////

		private static final String GETTELEPHONECLAIMAPPROVEDLIST = "select TeleBillId,SubmitDate,FromDate,ToDate,FinalAmount from pis_tele_Bill";
		@Override
		public List<Object[]> getTelephoneClaimApprovedList() 
		{
			List<Object[]> TelephoneClaimApprovedList = new ArrayList<>();
			try {

				Query q = manager.createNativeQuery(GETTELEPHONECLAIMAPPROVEDLIST);
				TelephoneClaimApprovedList = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephoneClaimApprovedList "+ e);
				e.printStackTrace();
			}
			return (TelephoneClaimApprovedList);

		}
		
		private static final String GETTELEPHONEAPPROVALLIST = "SELECT em.EmpName, b.designation, SUM(c.GrossTotal), SUM(c.RestrictedAmt), SUM(c.PayableAmount), c.TeleAppliedDate,d.TeleForwardId,d.SendBack,d.UserRemark,d.AdminRemark  FROM employee_details a,employee_Desig b, pis_tele c, pis_Tele_Forward d, employee em WHERE  em.Desigid=b.desigid   AND  a.EMPno=c.EmpId AND a.empno=em.empno AND  c.TeleForwardId=d.TeleForwardId AND c.IsActive='1' AND c.SubmitBy IS NULL AND c.SubmitDate IS NULL AND c.TeleBillId='0' AND d.sendback <> 'B' AND d.TeleUserFlag='F' GROUP BY d.TeleForwardId ;" ;
		
		@Override
		public List<Object[]> getTelephoneApprovalList() 
		{
			List<Object[]> TelephoneApprovalList = new ArrayList<>();
			try {

				Query q = manager.createNativeQuery(GETTELEPHONEAPPROVALLIST);
				TelephoneApprovalList = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephoneApprovalList "+ e);
				e.printStackTrace();
			}
			return (TelephoneApprovalList);

		}
		
		private static final String  TELEPHONEAPPROVE ="update pis_tele  set TeleBillId=:MaxTeleBillId,Remarks=:remark,SubmitBy=:SubmitBy,SubmitDate=:sqlSubmitDate  where   TeleForwardId=:TeleForwardId ";
		private static final String ADDTELEPHONEBILL = "update pis_tele_forward  set TeleUserFlag='S',ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate  where   TeleForwardId=:TeleForwardId ";

		@Override
		public int TelephoneApprove_AddTelephoneBill( String Empid, double FinalAmount, String FromDate, String ToDate, Map<String, String> map ) 
		{
			int result = 0;

			try {

				TelephoneBill tb = new TelephoneBill();
				tb.setFinalAmount(FinalAmount);
				tb.setFromDate(DateTimeFormatUtil.dateConversionSql(FromDate));
				tb.setToDate(DateTimeFormatUtil.dateConversionSql(ToDate));
				tb.setSubmitBy(Empid);
				tb.setSubmitDate(DateTimeFormatUtil.TodayDateInSqlFormat());
				tb.setCreatedBy(Empid);
				tb.setCreatedDate(sdtf.format(new Date()));
				manager.persist(tb);

				for (Map.Entry<String, String> entry : map.entrySet()) {
					String TeleForwardId = entry.getKey();
					String comments = entry.getValue();

					String Comment;

					if ("::".equalsIgnoreCase(comments)) {
						Comment = " ";
					} else {
						Comment = comments.substring(2);
					}

					Query query = manager.createNativeQuery(TELEPHONEAPPROVE);
					query.setParameter("TeleForwardId", Integer.parseInt(TeleForwardId));
					query.setParameter("MaxTeleBillId", tb.getTeleBillId());
					query.setParameter("remark", Comment);
					query.setParameter("SubmitBy", Empid);
					query.setParameter("sqlSubmitDate", DateTimeFormatUtil.TodayDateInSqlFormat());
					query.executeUpdate();

					Query query1 = manager.createNativeQuery(ADDTELEPHONEBILL);
					query1.setParameter("TeleForwardId", Integer.parseInt(TeleForwardId));
					query1.setParameter("ModifiedBy", Empid);
					query1.setParameter("ModifiedDate", sdtf.format(new Date()));
					query1.executeUpdate();

				} // for loop closed

				result = 2;
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO TelephoneApprove_AddTelephoneBill "+ e);
				e.printStackTrace();
			}

			return (result);

		}

		private static final String TELEPHONESENDBACK = "update pis_tele_forward  set sendback='B', adminremark=:remark ,modifiedby=:ModifiedBy,modifieddate=:ModifiedDate  where   teleforwardid=:TeleForwardId ";
		private static final String TELEPHONECLAIMSSENDBACK = "UPDATE pis_tele SET teleforwardid = 0 , STATUS='S' WHERE teleforwardid = :teleforwardid AND isactive=1 ;";
		@Override
		public int TelephoneSendback(String Empid, double FinalAmount, String FromDate, String ToDate,	Map<String, String> map) 
		{
			int result = 0;
			try {
				
				for (Map.Entry<String, String> entry : map.entrySet()) 
				{
					long count=0;
					String TeleForwardId = entry.getKey();
					String comments = entry.getValue();

					String Comment = comments;

					Query query1 = manager.createNativeQuery(TELEPHONESENDBACK);
					
					query1.setParameter("TeleForwardId", Integer.parseInt(TeleForwardId));
					query1.setParameter("ModifiedBy", Empid);
					query1.setParameter("remark", Comment);
					query1.setParameter("ModifiedDate", sdtf.format(new Date()));
					count = query1.executeUpdate();
					
					if(count > 0) 
					{
						Query query = manager.createNativeQuery(TELEPHONECLAIMSSENDBACK);
						query.setParameter("teleforwardid", Integer.parseInt(TeleForwardId));
						query.executeUpdate();
					}

				} // for loop closed
				
				result = 2;
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO TelephoneSendback "+ e);
				e.printStackTrace();
			}

			return (result);

		}

		private static final String GETCHECKTELEAPPROVEORNOT = "SELECT  TeleId,TeleBillId from pis_tele   WHERE   SubmitBy IS NULL  and TeleId=:TeleId";
		
		@Override
		public Object[] getCheckTeleApproveOrNot(String TeleId) 
		{
			Object[] CheckTeleApproveOrNot = null;

			try {

				Query q = manager.createNativeQuery(GETCHECKTELEAPPROVEORNOT);
				q.setParameter("TeleId", Integer.parseInt(TeleId));
				CheckTeleApproveOrNot = (Object[]) q.getSingleResult();

			} 
			catch (NoResultException e)
			{
				System.err.println("No Result found Exception");
			}	
			catch (Exception e) {
				logger.error(new Date() +"Inside DAO getCheckTeleApproveOrNot "+ e);
				e.printStackTrace();
			}
			return (CheckTeleApproveOrNot);

		}

		private static final String GETCHECKTELEAPPROVEFORWARDORNOT = "SELECT  TeleId,TeleBillId from pis_tele   WHERE TeleBillId='0' and  SubmitBy IS NULL  and TeleForwardId='0' and TeleId=:TeleId";
		
		@Override
		public Object[] getCheckTeleApproveForwardOrNot(String TeleId) 
		{
			Object[] CheckTeleApproveForwardOrNot = null;
			try {
				Query q = manager.createNativeQuery(GETCHECKTELEAPPROVEFORWARDORNOT);
				q.setParameter("TeleId", Integer.parseInt(TeleId));
				CheckTeleApproveForwardOrNot = (Object[]) q.getSingleResult();
			} 
			catch (NoResultException e)
			{
				System.err.println("No Result found Exception");
			}	
			catch (Exception e) 
			{
				logger.error(new Date() +"Inside DAO getCheckTeleApproveForwardOrNot "+ e);
				e.printStackTrace();
			}
			return (CheckTeleApproveForwardOrNot);

		}

		private static final String GETTELEPHONEAPPROVALPERIODEDITDETAILS = "SELECT   TeleBillId,FromDate,ToDate   from pis_tele_bill where  TeleBillId=:TeleBillId ";
		
		@Override
		public Object[] getTelephoneApprovalPeriodEditDetails(String TeleBillId) 
		{
			Object[] TelephoneApprovalPeriodEditDetails = null;

			try {

				Query q = manager.createNativeQuery( GETTELEPHONEAPPROVALPERIODEDITDETAILS);
				q.setParameter("TeleBillId", Integer.parseInt(TeleBillId));
				TelephoneApprovalPeriodEditDetails = (Object[]) q.getSingleResult();

			} 
			catch (NoResultException e)
			{
				System.err.println("No Result found Exception");
			}	
			catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephoneApprovalPeriodEditDetails "+ e);
				e.printStackTrace();
			}
			return (TelephoneApprovalPeriodEditDetails);

		}
		
		private static final String UPDATETELEPERIOD ="update pis_tele_bill set FromDate=:sqlFromDate,ToDate=:sqlToDate,ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate   where TeleBillId=:TeleBillId" ;

		@Override
		public int UpdateTelePeriod(String Empid, String TeleBillId, String FromDate, String ToDate) 
		{
			int UpdateTelePeriod = 0;

			try {
				Query q = manager.createNativeQuery(UPDATETELEPERIOD );

				Date d = new Date();
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String ModifiedDate = sdtf.format(d);

				java.sql.Date sqlFromDate = DateTimeFormatUtil.dateConversionSql(FromDate);
				java.sql.Date sqlToDate = DateTimeFormatUtil.dateConversionSql(ToDate);

				q.setParameter("TeleBillId", Integer.parseInt(TeleBillId));
				q.setParameter("sqlFromDate", sqlFromDate);
				q.setParameter("sqlToDate", sqlToDate);
				q.setParameter("ModifiedBy", Empid);
				q.setParameter("ModifiedDate", ModifiedDate);

				UpdateTelePeriod = q.executeUpdate();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO UpdateTelePeriod "+ e);
				e.printStackTrace();
			}

			return (UpdateTelePeriod);

		}

		
		private static final String GETTELEPHONECONTINGENTBILLPRINTDATA ="SELECT FromDate,ToDate,FinalAmount from pis_tele_bill   WHERE  TeleBillId=:TeleBillId";
		
		@Override
		public Object[] getTelephoneContingentBillPrintData(String TeleBillId) 
		{
			Object[] TelephoneContingentBillPrintData = null;

			try {

				Query q = manager.createNativeQuery( GETTELEPHONECONTINGENTBILLPRINTDATA	);
				q.setParameter("TeleBillId", Integer.parseInt(TeleBillId));
				TelephoneContingentBillPrintData = (Object[]) q.getSingleResult();

			} 
			catch (NoResultException e)
			{
				System.err.println("No Result found Exception");
			}	
			catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephoneContingentBillPrintData "+ e);
				e.printStackTrace();
			}
			return (TelephoneContingentBillPrintData);

		}
		
		private static final String GETTELEPHONEPRINTREPORTSINGLEDATA = "SELECT em.EmpName,a.GPFNO,a.SBIACCNO,a.SBIACCNO AS 'OTHER_ACC_NO',b.designation,c.TeleId,c.IsBroadBand,c.GrossTotal,c.PayableAmount,c.Remarks,d.FromDate,d.ToDate,d.FinalAmount,c.ClaimMonth,c.ClaimYear,c.RestrictedAmt FROM employee_details a ,employee_Desig b ,pis_tele c,pis_tele_bill d,employee em  WHERE em.DesigId=b.desigid AND a.EMPno=c.EmpId AND em.empno=a.empno AND c.TeleBillId=d.TeleBillId  AND c.TeleBillId=:TeleBillId ORDER BY em.SRNO ";
		
		@Override
		public List<Object[]> getTelephonePrintReportSingleData(String TeleBillId) 
		{
			List<Object[]> TelephonePrintReportSingleData = new ArrayList<>();
			try {

				Query q = manager.createNativeQuery(GETTELEPHONEPRINTREPORTSINGLEDATA);
				q.setParameter("TeleBillId", Integer.parseInt(TeleBillId));
				TelephonePrintReportSingleData = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephonePrintReportSingleData "+ e);
				e.printStackTrace();
			}
			return (TelephonePrintReportSingleData);

		}
		
		private static final String GETTELEPHONEPRINTREPORTMULTIDATA = "SELECT a.TeleId,b.TeleFromDate,b.TeleToDate,b.BasicAmount,b.TaxAmount,b.TotalAmount,b.TeleDId,c.DeviceNo from pis_tele a ,pis_tele_d b,pis_tele_users c WHERE a.TeleId=b.TeleId AND  b.TeleUsersId=c.TeleUsersId AND a.TeleBillId=:TeleBillId";

		@Override
		public List<Object[]> getTelephonePrintReportMultiData(String TeleBillId)
		{
			List<Object[]> TelephonePrintReportMultiData = new ArrayList<>();
			try {

				Query q = manager.createNativeQuery(GETTELEPHONEPRINTREPORTMULTIDATA);
				q.setParameter("TeleBillId", Integer.parseInt(TeleBillId));
				TelephonePrintReportMultiData = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephonePrintReportMultiData "+ e);
				e.printStackTrace();
			}
			return (TelephonePrintReportMultiData);

		}
		
		private static final String GETTELEPHONESENDBACKDATA ="SELECT a.teleid,a.submitby,a.teleforwardid,b.adminremark  FROM pis_tele a,pis_tele_forward b WHERE a.teleforwardid=b.teleforwardid AND a.empid=:empid AND  b.sendback='B' ";
		@Override
		public List<Object[]> getTelephoneSendbackData(String Empid) 
		{
			List<Object[]> TelephonePrintReportMultiData = new ArrayList<>();
			try {

				Query q = manager.createNativeQuery(GETTELEPHONESENDBACKDATA);
				q.setParameter("empid", Empid);
				TelephonePrintReportMultiData = (List<Object[]>) q.getResultList();

			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getTelephoneSendbackData "+ e);
				e.printStackTrace();
			}
			return (TelephonePrintReportMultiData);

		}

			//////////////////////*Telephone*////////////////////////////
	
	

}
