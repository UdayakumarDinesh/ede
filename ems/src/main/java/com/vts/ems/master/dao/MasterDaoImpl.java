package com.vts.ems.master.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.master.model.CHSSDoctorRates;
import com.vts.ems.master.model.CHSSEmpanelledHospital;
import com.vts.ems.master.model.Department;
import com.vts.ems.master.model.DgmMaster;
import com.vts.ems.master.model.DivisionGroup;
import com.vts.ems.master.model.DoctorList;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.master.model.PisAdmins;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.utils.DateTimeFormatUtil;

@Transactional
@Repository
public class MasterDaoImpl implements MasterDao{
	private static final Logger logger = LogManager.getLogger(MasterDaoImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@PersistenceContext
	EntityManager manager;
	

	private static final String OTHERITEM = "SELECT  otheritemid , otheritemname FROM chss_other_items ORDER BY otheritemid DESC";
	@Override
	public List<Object[]> OtherItems() throws Exception {
		Query query = manager.createNativeQuery(OTHERITEM);
		
		List<Object[]> FormModuleList= query.getResultList();
		return FormModuleList;
	}
	
	private static final String TESTMAIN = "SELECT a.testsubid ,a.testname , a.testrate ,b.testmainname ,a.testcode FROM chss_test_sub a , chss_test_main b WHERE isactive='1' AND a.testmainid=b.testmainid ORDER BY a.testsubid DESC";
	@Override
	public List<Object[]> ChssTestSub() throws Exception
	{
		 Query query = manager.createNativeQuery(TESTMAIN);
		 List<Object[]> FormModuleList= query.getResultList();
	return FormModuleList;
    } 
	private static final String TESTSUB="SELECT testmainid ,testmainname ,testmaintype FROM chss_test_main";
	@Override
	public List<Object[]>ChssTestMain () throws Exception
	{
		 Query query = manager.createNativeQuery(TESTSUB);
		 List<Object[]> FormModuleList= query.getResultList();
	return FormModuleList;
    }
	
	@Override
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception
	{
		try {
			manager.persist(TestSub);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO testSub() "+e);
			e.printStackTrace();
		}
		return TestSub.getTestSubId();
	}
	
	private static final String GETTESTSUB="FROM CHSSTestSub WHERE TestSubId=:TestSubId";
	
	@Override
	public CHSSTestSub testSub(String TestSubId)throws Exception
	{
		
		try {
			return manager.find(CHSSTestSub.class, Long.parseLong(TestSubId));		
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO testSub() "+e);
			e.printStackTrace();
			return null;
		}		
		
	}
	
	@Override
	public CHSSTestSub getTestSub(long subid) throws Exception
	{
		try {
			return manager.find(CHSSTestSub.class, subid);
			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO getTestSub() "+e);
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public long EditTestSub(CHSSTestSub test) throws Exception
	{
		try {
			manager.merge(test);
			manager.flush();
			
			return test.getTestSubId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditTestSub() "+e);
			e.printStackTrace();
			return 0;
		}		
	}
	@Override
	public CHSSOtherItems getOtherItem(int itemid) throws Exception
	{
		try {
			return manager.find(CHSSOtherItems.class, itemid);			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO getOtherItem() "+e);
			e.printStackTrace();
			return null;
		}	
	}
	@Override
	public int AddOtherItem(CHSSOtherItems item)throws Exception
	{
		try {
			manager.persist(item);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddOtherItem() "+e);
			e.printStackTrace();
		}
		return item.getOtherItemId();
	}
	@Override
	public int EditItem(CHSSOtherItems item) throws Exception
	{
		try {
			manager.merge(item);
			manager.flush();		
			return item.getOtherItemId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditItem() "+e);
			e.printStackTrace();
			return 0;
		}		
	}
	

	private static final String MEDICINELIST="SELECT a.medicineid , b.treatmentname , a.medicinename,a.medno, isadmissible FROM chss_medicines_list a ,chss_treattype b WHERE a.treattypeid=b.treattypeid AND a.isactive=1 ORDER BY medno DESC";
	@Override
	public List<Object[]>  getMedicineList()throws Exception
	{
		 try {
			 Query query = manager.createNativeQuery(MEDICINELIST);
			 List<Object[]> List= query.getResultList();
			 return List;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getMedicineList() "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String MEDICINELISTBYTREATMENT="SELECT a.medicineid , b.treatmentname , a.medicinename,a.medno, isadmissible FROM chss_medicines_list a ,chss_treattype b WHERE a.treattypeid=b.treattypeid AND a.isactive=1 AND a.treattypeid =:treatmentid ORDER BY medno DESC";
	@Override
	public List<Object[]>  getMedicineListByTreatment(String treatmentname)throws Exception
	{
		 try {
			 Query query = manager.createNativeQuery(MEDICINELISTBYTREATMENT);
			 query.setParameter("treatmentid", treatmentname);
			 List<Object[]> List= query.getResultList();
			 return List;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getMedicineListByTreatment() "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String TREATMENTTYPE="SELECT treattypeid , treatmentname FROM chss_treattype";
	@Override
	public List<Object[]> GetTreatmentType()throws Exception
	{
		 try {
			 Query query = manager.createNativeQuery(TREATMENTTYPE);
			 List<Object[]> List= query.getResultList();
			 return List;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetTreatmentType() "+e);
			e.printStackTrace();
			return null;
		}
	}
	private static final String CHECKMEDICINE="SELECT COUNT(medicineid) FROM chss_medicines_list WHERE medicinename=:medicinename AND treattypeid=:treatid";
	@Override
	public int Checkduplicate(String medicinename ,String treatid)throws Exception
	{
		 try {
			Query query = manager.createNativeQuery(CHECKMEDICINE);
			query.setParameter("medicinename", medicinename);
			query.setParameter("treatid", treatid);
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

				return result;
		  }catch (Exception e){
			  logger.error(new Date() + "Inside DAO Checkduplicate() "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public CHSSMedicinesList getCHSSMedicine(long medicineid) throws Exception {
		CHSSMedicinesList memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<CHSSMedicinesList> cq = cb.createQuery(CHSSMedicinesList.class);
			Root<CHSSMedicinesList> root = cq.from(CHSSMedicinesList.class);
			Predicate p1 = cb.equal(root.get("MedicineId"), medicineid);
			cq = cq.select(root).where(p1);
			TypedQuery<CHSSMedicinesList> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getCHSSMedicine() "+e);
			e.printStackTrace();
			return null;
		}
	
	}
	
	@Override
	public CHSSDoctorRates getCHSSDoctorRates(int DocRateId) throws Exception
	{
		CHSSDoctorRates memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<CHSSDoctorRates> cq = cb.createQuery(CHSSDoctorRates.class);
			Root<CHSSDoctorRates> root = cq.from(CHSSDoctorRates.class);
			Predicate p1 = cb.equal(root.get("DocRateId"), DocRateId);
			cq = cq.select(root).where(p1);
			TypedQuery<CHSSDoctorRates> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getCHSSDoctorRates() "+e);
			e.printStackTrace(); 
			return null;
		}
	
	}
	
	@Override
	public Long AddMedicine(CHSSMedicinesList medicine)throws Exception
	{
		try {
			manager.persist(medicine);
			manager.flush();
			return medicine.getMedicineId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddMedicine() "+e);
			e.printStackTrace();
			return 0l;
		}
		
	}
	
	@Override
	public int AddDocQualification(CHSSDoctorRates  DocRate)throws Exception
	{
		try {
			manager.persist(DocRate);
			manager.flush();
			return DocRate.getDocRateId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddDocQualification() "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public Long EditMedicine(CHSSMedicinesList item) throws Exception
	{
		try {
			manager.merge(item);
			manager.flush();		
			return item.getMedicineId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditMedicine() "+e);
			e.printStackTrace();
			return 0l;
		}		
	}
private static final String DOCTORLIST="SELECT a.docrateid , b.treatmentname , a.docqualification ,a.docrating ,a.consultation_1 ,a.consultation_2  FROM chss_doctor_rates a , chss_treattype b WHERE a.isactive='1' AND a.treattypeid = b.treattypeid order by a.treattypeid asc,a.docrateid desc";
	 
	@Override
	public List<Object[]> GetDoctorList()throws Exception
	{                                           
		try {
			
			Query query= manager.createNativeQuery(DOCTORLIST);			
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetDoctorList() "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public CHSSDoctorRates getCHSSDocRate(long docrateid) throws Exception 
	{
		CHSSDoctorRates memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<CHSSDoctorRates> cq = cb.createQuery(CHSSDoctorRates.class);
			Root<CHSSDoctorRates> root = cq.from(CHSSDoctorRates.class);
			Predicate p1 = cb.equal(root.get("DocRateId"), docrateid);
			cq = cq.select(root).where(p1);
			TypedQuery<CHSSDoctorRates> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getCHSSDocRate() "+e);
			e.printStackTrace();
			return null;
		}
	
	}
	
	@Override
	public int EditDoctorMaster(CHSSDoctorRates Docrate) throws Exception
	{
		try {
			manager.merge(Docrate);
			manager.flush();		
			return Docrate.getDocRateId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditDoctorMaster() "+ e);
			e.printStackTrace();
			return 0;
		}		
	}
	private static final String LABDETAILS="SELECT labmasterid, labcode ,labname ,labunitcode, labaddress, labcity, labpin , labauthorityid FROM lab_master";
	@Override
	public Object[] getLabDetails()throws Exception
	{
		 try {
			 Query query = manager.createNativeQuery(LABDETAILS);
			 
			 List<Object[]> list = (List<Object[]>)query.getResultList();
			 
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO getLabDetails() "+e);
				e.printStackTrace();
				return null;
			}	
	}
	@Override
	public LabMaster GetLabDetailsToEdit(long labid)throws Exception
	{
		LabMaster memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<LabMaster> cq = cb.createQuery(LabMaster.class);
			Root<LabMaster> root = cq.from(LabMaster.class);
			Predicate p1 = cb.equal(root.get("LabMasterId"), labid);
			cq = cq.select(root).where(p1);
			TypedQuery<LabMaster> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetLabDetailsToEdit() "+e);
			e.printStackTrace();
			return null;
		}
	}
	private static final String LABSLIST="SELECT a.labid , a.labname ,a.labcode FROM cluster_lab a ,cluster b WHERE a.clusterid=b.clusterid";
	
	@Override
	public List<Object[]> getLabsList()throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(LABSLIST);			
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO getLabsList() "+e);
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public long EditLabMaster(LabMaster labmatster) throws Exception
	{
		try {
			manager.merge(labmatster);
			manager.flush();		
			return labmatster.getLabMasterId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditLabMaster() "+e);
			e.printStackTrace();
			return 0;
		}		
	}
	
	private static final String UPDATEOTHERITEMAMTWITHBASICTOAMT="UPDATE chss_other_perm_amt SET itempermitamt=:admAmt  ,modifiedby=:modifiedby , modifieddate=:modifieddate , basicto=:basicto WHERE CHSSOtherAmtId=:chssOtheramtid";
	@Override
	public long updateOtherItemAmt(String chssOtheramtid, String admAmt, String UserId ,String basicto)throws Exception
	{
		int count=0;
		try {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Query query= manager.createNativeQuery(UPDATEOTHERITEMAMTWITHBASICTOAMT);				
			query.setParameter("chssOtheramtid", chssOtheramtid);
			query.setParameter("admAmt",admAmt);
			query.setParameter("basicto",basicto);
			query.setParameter("modifiedby", UserId);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			count = query.executeUpdate();
			return count;
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO updateOtherItemAmt() "+e);
				e.printStackTrace();
				return count;
			}
	}
	
private static final String CHECKITEM="SELECT COUNT(otheritemid) FROM chss_other_items WHERE otheritemname=:treatmentname";
	
	@Override
	public int CheckduplicateItem(String treatmentname)throws Exception
	{
		 try {
			Query query = manager.createNativeQuery(CHECKITEM);
			query.setParameter("treatmentname", treatmentname);		
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

				return result;
		  }catch (Exception e){
			  logger.error(new Date() + "Inside DAO CheckduplicateItem() "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	private static final String CHECKTEST = "SELECT COUNT(testname) FROM chss_test_sub WHERE testname=:testname";
	
	@Override
	public int CheckduplicateTest(String testname)throws Exception
	{
		 try {
			Query query = manager.createNativeQuery(CHECKTEST);
			query.setParameter("testname", testname);		
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

			return result;
		  }catch (Exception e){
			  logger.error(new Date() + "Inside DAO CheckduplicateTest() "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String DELETEITEM="UPDATE chss_other_perm_amt SET modifiedby=:modifiedby , modifieddate=:modifieddate , isactive=:isactive WHERE chssotheramtid=:chssOtheramtid";
	
	@Override
	public long DeleteOtherAmt(String chssOtheramtid, String userid)throws Exception
	{
		long count=0;
		try {
			
			Query query= manager.createNativeQuery(DELETEITEM);				
			query.setParameter("chssOtheramtid", chssOtheramtid);
			query.setParameter("isactive","0");
			query.setParameter("modifiedby", userid);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			
			count = query.executeUpdate();
			return count;
			
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteOtherAmt() "+e);
			e.printStackTrace();
			return count;
		}
	}
	
	private static final String DESIGNATION ="SELECT  desigid , desigcode , designation , desiglimit FROM employee_desig order by desigid desc";
	
	@Override
	public List<Object[]> GetDesignation()throws Exception
	{
		try {
				Query query= manager.createNativeQuery(DESIGNATION);			
				List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO GetDesignation() "+e);
				e.printStackTrace();
				return null;
		   }
	}
	
	
	@Override
	public long AddDesignation(EmployeeDesig desig)throws Exception
	{
		try {
			manager.persist(desig);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddDesignation() "+e);
			e.printStackTrace();
		}
		return desig.getDesigId();
	}
	
	@Override
	public EmployeeDesig GetDesignationToEdit(long desigid)throws Exception
	{
		EmployeeDesig memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmployeeDesig> cq = cb.createQuery(EmployeeDesig.class);
			Root<EmployeeDesig> root = cq.from(EmployeeDesig.class);
			Predicate p1 = cb.equal(root.get("DesigId"), desigid);
			cq = cq.select(root).where(p1);
			TypedQuery<EmployeeDesig> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetDesignationToEdit() "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long EditDesignation(EmployeeDesig desig) throws Exception
	{
		try {
			manager.merge(desig);
			manager.flush();		
			return desig.getDesigId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditDesignation() "+e);
			e.printStackTrace();
			return 0;
		}		
	}
	
	private static final String DESIGNATIONCODECHECK="SELECT COUNT(desigcode),'desigcode' FROM employee_desig WHERE desigcode=:desigcode";
	@Override
	public Object[] DesignationCodeCheck(String desigcode)throws Exception
	{
		Query query =manager.createNativeQuery(DESIGNATIONCODECHECK);
		query.setParameter("desigcode", desigcode);
		return (Object[])query.getSingleResult();
	}
	
	private static final String DESIGNATIONCHECK="SELECT COUNT(designation),'designation' FROM employee_desig WHERE designation=:designation";
	@Override
	public Object[] DesignationCheck(String designation)throws Exception
	{
		Query query =manager.createNativeQuery(DESIGNATIONCHECK);
		query.setParameter("designation", designation);
		return (Object[])query.getSingleResult();
	}
	
	private static final String DESIGNATIONCODEEDITCHECK="SELECT COUNT(desigcode),'desigcode' FROM employee_desig WHERE desigcode=:desigcode AND desigid<>:desigid";
	@Override
	public Object[] DesignationCodeEditCheck(String desigcode,String desigid )throws Exception
	{
		Query query =manager.createNativeQuery(DESIGNATIONCODEEDITCHECK);
		query.setParameter("desigcode", desigcode);
		query.setParameter("desigid", desigid);
		return (Object[])query.getSingleResult();
	}
	
	private static final String DESIGNATIONEDITCHECK="SELECT COUNT(designation),'designation' FROM employee_desig WHERE designation=:designation AND desigid<>:desigid";
	@Override
	public Object[] DesignationEditCheck(String designation,String desigid)throws Exception
	{
		Query query =manager.createNativeQuery(DESIGNATIONEDITCHECK);
		query.setParameter("designation", designation);
		query.setParameter("desigid", desigid);
		return (Object[])query.getSingleResult();
	}
	

	@Override
	public DoctorList GetDoctor(Long  doctorid)throws Exception
	{
		DoctorList list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<DoctorList> cq = cb.createQuery(DoctorList.class);
			Root<DoctorList> root = cq.from(DoctorList.class);
			Predicate p1 = cb.equal(root.get("DoctorId"), doctorid);
			cq = cq.select(root).where(p1);
			TypedQuery<DoctorList> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
			return list;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetDoctor() "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long DoctorsAdd(DoctorList doctor)throws Exception
	{
		try {
			manager.persist(doctor);
			manager.flush();
			return (long)doctor.getDoctorId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DoctorsAdd() "+e);
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public long DoctorsEdit(DoctorList doctor)throws Exception
	{
		try {
			manager.merge(doctor);
			manager.flush();
			return (long)doctor.getDoctorId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DoctorsEdit() "+e);
			e.printStackTrace();
			return 0l;
		}
	}
	
	private static final String MAXMEDNO="SELECT MAX(medno) FROM chss_medicines_list WHERE treattypeid=:treattype";
	@Override
	public int GetMaxMedNo(String treatmenttype)throws Exception
	{
		Query query = manager.createNativeQuery(MAXMEDNO);
		query.setParameter("treattype", treatmenttype);
		Integer result = (Integer) query.getSingleResult();
		return result;
	}
	
	
	private static final String CHECKTESTCODE = "SELECT COUNT(testcode) FROM chss_test_sub WHERE testcode=:testcode";
	
	@Override
	public int CheckduplicateTestCode(String testcode)throws Exception
	{
		 try {
			Query query = manager.createNativeQuery(CHECKTESTCODE);
			query.setParameter("testcode", testcode);		
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

			return result;
		  }catch (Exception e){
			  logger.error(new Date() + "Inside DAO CheckduplicateTestCode() "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
private static final String DUPLICATEDOCQUALIFICATION = "SELECT COUNT(docqualification) FROM chss_doctor_rates WHERE treattypeid=:treatment AND docqualification=:qualification";
	
	@Override
	public int DuplicateDocQualification(String treatment,String qualification)throws Exception
	{
		 try {
			Query query = manager.createNativeQuery(DUPLICATEDOCQUALIFICATION);
			query.setParameter("treatment", treatment);	
			query.setParameter("qualification", qualification);	
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

			return result;
		  }catch (Exception e){
			  logger.error(new Date() + "Inside DAO DuplicateDocQualification() "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String GETOTHEREMSLIST="SELECT a.basicfrom , a.basicto , a.itempermitamt , a.CHSSOtherAmtId FROM chss_other_perm_amt a , chss_other_items b WHERE a.otheritemid=b.otheritemid AND a.isactive='1' AND a.otheritemid=:id"; 
	@Override
	public List<Object[]> GetOtherItemAmlountList(String id)throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(GETOTHEREMSLIST);			
			query.setParameter("id", id);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetOtherItemAmlountList() "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public long AddOtherItemAmt(CHSSOtherPermitAmt otheramt)throws Exception
	{
		try {
			manager.persist(otheramt);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddOtherItemAmt() "+ e);
			e.printStackTrace();
		}
		return otheramt.getCHSSOtherAmtId();
	}
	private static final String UPDATEOTHERITEMAMT="UPDATE chss_other_perm_amt SET itempermitamt=:admAmt  ,modifiedby=:modifiedby , modifieddate=:modifieddate WHERE CHSSOtherAmtId=:chssOtheramtid";
	@Override
	public long updateOtherAmt(String chssOtheramtid, String admAmt, String UserId)throws Exception
	{
		int count=0;
		try {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Query query= manager.createNativeQuery(UPDATEOTHERITEMAMT);				
			query.setParameter("chssOtheramtid", chssOtheramtid);
			query.setParameter("admAmt",admAmt);
			query.setParameter("modifiedby", UserId);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			count = query.executeUpdate();
			return count;
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO updateOtherAmt() "+e);
				e.printStackTrace();
				return count;
			}
	}
	
		@Override
		public long EmpanelledHospitalAdd(CHSSEmpanelledHospital hospital)throws Exception
		{
			try {
				manager.persist(hospital);
				manager.flush();
				return (long)hospital.getEmpanelledHospitalId();
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO EmpanelledHospitalAdd() "+e);
				e.printStackTrace();
				return 0l;
			}
		}
		@Override
		public CHSSEmpanelledHospital GetEmpanelled(Long  empanelledid)throws Exception
		{
			CHSSEmpanelledHospital list = null;
			try {
				CriteriaBuilder cb = manager.getCriteriaBuilder();
				CriteriaQuery<CHSSEmpanelledHospital> cq = cb.createQuery(CHSSEmpanelledHospital.class);
				Root<CHSSEmpanelledHospital> root = cq.from(CHSSEmpanelledHospital.class);
				Predicate p1 = cb.equal(root.get("EmpanelledHospitalId"), empanelledid);
				cq = cq.select(root).where(p1);
				TypedQuery<CHSSEmpanelledHospital> allquery = manager.createQuery(cq);
				list = allquery.getResultList().get(0);
				return list;
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO GetEmpanelled() "+e);
				e.printStackTrace();
				return null;
			}
		}
		
		public long EmpanelledHospitalEdit(CHSSEmpanelledHospital hospital)throws Exception
		{
			try {
				manager.merge(hospital);
				manager.flush();		
				return hospital.getEmpanelledHospitalId();
			}catch (Exception e) {
				logger.error(new Date() + "Inside DAO EmpanelledHospitalEdit() "+e);
				e.printStackTrace();
				return 0;
			}	
		}
		
		@Override
		public Long AddMasterEditComments(MasterEdit masteredit)throws Exception
		{
			try {
				manager.persist(masteredit);
				manager.flush();
				return (long)masteredit.getMasterEditId();
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO AddMasterEditComments() "+e);
				e.printStackTrace();
				return 0l;
			}
		}
		
		
		private static final String GETDOCTOREMPANELLEDLIST="SELECT doctorid ,doctorname , qualification, address, phoneno FROM chss_doctor_list ORDER BY doctorid DESC";
		@Override
		public List<Object[]> GetDoctorEmpanelledList()throws Exception
		{
			try {
					Query query =  manager.createNativeQuery(GETDOCTOREMPANELLEDLIST);
					
					return (List<Object[]>)query.getResultList();
			} catch (Exception e) {
				logger.error(new Date() +" Inside DAO GetDoctorEmpanelledList "+ e);
				e.printStackTrace();
				return null;
			}
		}
		
		private static final String EMPANELLEDHOSPITALLIST="SELECT  EmpanelledHospitalId ,HospitalName, hospitaladdress FROM chss_empanelledhospital WHERE isactive='1' order by EmpanelledHospitalId desc";
		@Override
		public List<Object[]> GetEmpanelledHostpitalList()throws Exception
		{
			 try {
					Query query =  manager.createNativeQuery(EMPANELLEDHOSPITALLIST);
					
					return (List<Object[]>)query.getResultList();
			} catch (Exception e) {
				logger.error(new Date() +" Inside DAO GetEmpanelledHostpitalList "+ e);
				e.printStackTrace();
				return null;
			}
		}
         private static final String DEPARTMENTLIST="SELECT a.DivisionId,a.DivisionCode,a.DivisionName,b.EmpName  AS 'DivisionHEAD' ,c.dgmcode AS 'DGM' FROM division_master  a,employee  b  , dgm_master c WHERE b.Empno=a.DivisionHeadId  AND c.dgmid=a.dgmid AND a.isactive=1 AND a.dgmid<>0 UNION SELECT a.DivisionId,a.DivisionCode,a.DivisionName,b.EmpName  AS 'DivisionHEAD' ,'NA' AS 'DGM' FROM division_master  a,employee  b WHERE b.Empno=a.DivisionHeadId  AND a.isactive=1 AND a.dgmid=0";
		@Override
		public List<Object[]> getDepartmentsList() throws Exception {
			List <Object[]>list=null;
			try {
				Query query = manager.createNativeQuery(DEPARTMENTLIST);
				list =(List <Object[]>)query.getResultList();
				manager.flush();
				return list;
			} catch (Exception e) {
				logger.error(new Date() +" Inside DAO getDepartmentsList "+ e);
				e.printStackTrace();
				return null;
			}
			
		}
		
		 private static final String EMPLIST="select EmpId,EmpName , empno from employee where isactive=1";
		 
		 @Override public List<Object[]> getEmpList() throws Exception {
		 List<Object[]> emplist=null;
		 try { 
			 Query query =manager.createNativeQuery(EMPLIST);
		  emplist=(List<Object[]>)query.getResultList();
		  manager.flush();
		  return emplist; 
		  } catch (Exception e) {
			  logger.error(new Date()+" Inside DAO getEmpListList "+ e); 
			  e.printStackTrace(); 
			  return null; 
	    }
		 
		 }

		@Override
		public int DepartmentAdd(DivisionMaster dep) throws Exception {
			try {
				manager.persist(dep);
				manager.flush();
				return (int) dep.getDivisionId();
			} catch (Exception e) {
				 logger.error(new Date()+" Inside DAO DepartmentAdd "+ e);
				e.printStackTrace();
				return 0; 
			}
			
		}
      private static final String DEPTEDIT="select DivisionId,DivisionCode,DivisionName,DivisionHeadId,DGMId from division_master where DivisionId=:deptId";

		@Override
		public Object[] departmentEdit(String deptId) throws Exception {			
			try {
				Query query = manager.createNativeQuery(DEPTEDIT);
				query.setParameter("deptId", deptId);
				Object[] dept=(Object[]) query.getSingleResult();
				return dept;
			} catch (Exception e) {
				 logger.error(new Date()+" Inside DAO departmentEdit "+ e);
					e.printStackTrace();
					return null;
			}
			
		}
		@Override
		public DivisionMaster departmentEdit(long divisionId) throws Exception {
			try {
				
				return manager.find(DivisionMaster.class, divisionId);	
				
			} catch (Exception e) {
				 logger.error(new Date()+" Inside DAO departmentEdit "+ e);
					e.printStackTrace();
					return null;
			}
		}

		@Override
		public int updateDepartment(DivisionMaster department) throws Exception {
			try {
				 manager.merge(department);
				 manager.flush();
				return (int) department.getDivisionId();
			} catch (Exception e) {
				 logger.error(new Date()+" Inside DAO departmentEdit "+ e);
					e.printStackTrace();
					return 0;
			}
			
		}
		private static final String DEPTCODE="select count(DivisionCode) from division_master where DivisionCode=:DeptCode";
		@Override
		public BigInteger DepartmentCodeCheck(String depCode) throws Exception {
		try {		
		Query query = manager.createNativeQuery(DEPTCODE);
		query.setParameter("DeptCode", depCode.trim());
	     return (BigInteger) query.getSingleResult();	
		} catch (Exception e) {
			logger.error(new Date()+" Inside DAO DepartmentCodeCheck "+ e);
			e.printStackTrace();
			return null;
		}	
	}
		@Override
		public Long AddDeptEditComments(MasterEdit masteredit)throws Exception
		{
			try {
				manager.persist(masteredit);
				manager.flush();
				return (long)masteredit.getMasterEditId();
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO AddDeptEditComments "+e);
				e.printStackTrace();
				return 0l;
			}
		}
private static final String DEPTCODEEDITCHECK="select count(DivisionCode) from division_master where DivisionCode=:DeptCode and DivisionId!=:DeptId";
		@Override
		public BigInteger DepartmentEditcheck(String depCode, String deptId) throws Exception {
			try {
				Query query = manager.createNativeQuery(DEPTCODEEDITCHECK);
				query.setParameter("DeptCode", depCode);
				query.setParameter("DeptId", deptId);
				return (BigInteger) query.getSingleResult();
				
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO AddDeptEditComments "+e);
				e.printStackTrace();
				
			}
			return null;
		}
		
		
		
		@Override
		public int divisionGroupAdd(DivisionGroup divisiongroup) throws Exception {
			try {
				manager.persist(divisiongroup);
				manager.flush();
				return divisiongroup.getGroupId();
			}  catch (Exception e) {
				logger.error(new Date() + "Inside DAO divsionGroupAdd() "+e);
				e.printStackTrace();
				return 0;
			}
		}
		
//		public static final String DIVISIONGROUPUPDATE = "UPDATE division_group SET GroupCode=:groupCode, GroupName=:groupName, GroupHeadId=:groupHeadId WHERE GroupId=:groupId";
		@Override
		public int divisionGroupEdit(DivisionGroup divisionGroup) throws Exception {
			
			try {
				manager.merge(divisionGroup);
				manager.flush();		
				return divisionGroup.getGroupId();
			}	catch (Exception e) {
				logger.error(new Date() + "Inside DAO divsionGroupEdit() "+e);
				e.printStackTrace();
				return 0;
			}
		}

		public static final String DIVISIONGROUPFINDBYID = "FROM DivisionGroup WHERE GroupId=:groupId";
		@Override
		public DivisionGroup getDivisionGroupById(int groupId) throws Exception {
			
			try {
				return manager.find(DivisionGroup.class, groupId);	
			}catch (Exception e) {
				logger.error(new Date() + "Inside DAO getDivisionGroupById "+e);
				e.printStackTrace();
				return null;
			}
		}
		
		private static final String DIVISIONGROUPLIST = "SELECT a.GroupId,UPPER (a.GroupCode),a.GroupName, b.EmpName AS DivisionHead, c.Designation , d.divisionname FROM division_group a,employee b,employee_desig c , division_master d  WHERE b.Empno =a.GroupHeadId AND c.DesigId = b.DesigId AND d.divisionid=a.divisionid ORDER BY a.GroupId DESC"; 
		@Override
		public List<Object[]> getDivisionGroupsList() throws Exception {
			List <Object[]>list=null;
			try {
				Query query = manager.createNativeQuery(DIVISIONGROUPLIST);
				list =(List <Object[]>)query.getResultList();
				return list;
			}catch (Exception e) {
				logger.error(new Date() +" Inside DAO getDivisionGroupList "+ e);
				e.printStackTrace();
				return null;
			}
			
		}
		public static final String EMPLOYEELIST = "SELECT EmpId,EmpName,EmpNo FROM employee WHERE IsActive=1 ORDER BY SrNo ASC";
		@Override
		public List<Object[]> getEmployeeList() throws Exception {
			List <Object[]>list=null;
			try {
			Query query = manager.createNativeQuery(EMPLOYEELIST);
			list = (List <Object[]>)query.getResultList();
			return list;
			}catch (Exception e) {
				logger.error(new Date() +" Inside DAO getEmployeeList "+ e);
				e.printStackTrace();
				return null;
			}
		}
		
		public static final String DUPLICATEADD = "SELECT COUNT(GroupCode) FROM division_group WHERE  GroupCode=:groupCode";
		@Override
		public BigInteger getDuplicateCount(String groupCode) throws Exception {
			
		try {
			Query query =manager.createNativeQuery(DUPLICATEADD);
			query.setParameter("groupCode", groupCode);
			return (BigInteger)query.getSingleResult();	
			}catch (Exception e) {
				logger.error(new Date() +" Inside DAO getDuplicateCount "+ e);
				e.printStackTrace();
				return null;
			}
				
		}
		
		public static final String DUPLICATEEDIT = "SELECT COUNT(GroupCode) FROM division_group WHERE GroupId!=:groupId AND GroupCode=:groupCode";
		@Override
		public BigInteger getDuplicateCountEdit(String groupId,String groupCode) throws Exception {
			
			try {
				Query query =manager.createNativeQuery(DUPLICATEEDIT);
				query.setParameter("groupId", groupId);
				query.setParameter("groupCode", groupCode);
				 return (BigInteger)query.getSingleResult();
			}catch (Exception e) {
				logger.error(new Date() +" Inside DAO getDuplicateCountEdit "+ e);
				e.printStackTrace();
				return null;
			}
			
			
	}
		
		public static final String GROUPLIST = "SELECT GroupId,GroupName FROM division_group WHERE IsActive=1;";
		@Override
		public List<Object[]> getGroupList() throws Exception
		{
			List <Object[]>list=null;
			try {
			Query query = manager.createNativeQuery(GROUPLIST);
			list = (List <Object[]>)query.getResultList();
			return list;
			}catch (Exception e) {
				logger.error(new Date() +" Inside DAO getGroupList "+ e);
				e.printStackTrace();
				return null;
			}
		}
private static final String QUALIFICATIONLIST="SELECT quali_id,quali_title FROM pis_quali_code WHERE is_active='1'";
		@Override
		public List<Object[]> getQualificationList() throws Exception {
			List <Object[]> list =null;
			try {
				Query query = manager.createNativeQuery(QUALIFICATIONLIST);
				list =(List <Object[]>) query.getResultList();
			} catch (Exception e) {
				logger.error(new Date() +" Inside DAO getQualificationList "+ e);
				e.printStackTrace();
			}
			
			
			return list;
		}

		private static final String DGMLIST = "SELECT a.DGMId,a.DGMCode,a.DGMName,b.EmpName,a.DGMEmpNo FROM dgm_master a,employee b WHERE a.DGMEmpNo = b.EmpNo AND a.IsActive=1 ORDER BY a.DGMId DESC";
		@Override
		public List<Object[]> getDgmList() throws Exception {
			
			List <Object[]> list =null;
			try {
				Query query = manager.createNativeQuery(DGMLIST);
				list =(List <Object[]>) query.getResultList();
			} catch (Exception e) {
				logger.error(new Date() +" Inside DAO getDgmList "+ e);
				e.printStackTrace();
			}
			
			
			return list;
		}
		
		public static final String DGMFINDBYID = "FROM DgmMaster WHERE DGMId=:DGMId";
		@Override
		public DgmMaster getDgmById(long DGMId) throws Exception {
			
			try {
				return manager.find(DgmMaster.class, DGMId);	
			}catch (Exception e) {
				logger.error(new Date() + "Inside DAO getDgmById "+e);
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		public long dgmAdd(DgmMaster dgmMaster) throws Exception {
			try {
				manager.persist(dgmMaster);
				manager.flush();
				return dgmMaster.getDGMId();
			}  catch (Exception e) {
				logger.error(new Date() + "Inside DAO dgmAdd "+e);
				e.printStackTrace();
				return 0L;
			}
		}
		

		@Override
		public long dgmEdit(DgmMaster dgmMaster) throws Exception {
			
			try {
				manager.merge(dgmMaster);
				manager.flush();		
				return dgmMaster.getDGMId();
			}	catch (Exception e) {
				logger.error(new Date() + "Inside DAO dgmEdit "+e);
				e.printStackTrace();
				return 0L;
			}
		}
		
		public static final String DUPLICATEDGMCODEADD = "SELECT COUNT(DGMCode) FROM dgm_master WHERE DGMCode=:dgmCode";
		@Override
		public BigInteger duplicateDgmCodeCountAdd(String dgmCode) throws Exception {
			try {
				Query query =manager.createNativeQuery(DUPLICATEDGMCODEADD);
				query.setParameter("dgmCode", dgmCode);
				return (BigInteger)query.getSingleResult();
			}catch (Exception e) {
				logger.error(new Date() + "Inside DAO duplicateDgmCodeCountAdd "+e);
				e.printStackTrace();
				return null;
			}
				
		}
		
		public static final String DUPLICATEDGMCODEEDIT = "SELECT COUNT(DGMCode) FROM dgm_master WHERE DGMId!=:dgmId AND DGMCode=:dgmCode";
		@Override
		public BigInteger duplicateDgmCodeCountEdit(String dgmId,String dgmCode) throws Exception {
			try {
				Query query =manager.createNativeQuery(DUPLICATEDGMCODEEDIT);
				query.setParameter("dgmId", dgmId);
				query.setParameter("dgmCode", dgmCode);
				 return (BigInteger)query.getSingleResult();
			}catch (Exception e) {
				logger.error(new Date() + "Inside DAO duplicateDgmCodeCountEdit "+e);
				e.printStackTrace();
				return null;
			}
			
			
	}
		private static final String PANDAFANDAADMINDATA="SELECT a.AdminsId,a.EmpAdmin,a.AdminType,a.AdminFrom,a.AdminTo,b.EmpName,c.Designation FROM pis_admins a,employee b,employee_desig c WHERE a.EmpAdmin=b.EmpNo AND b.DesigId=c.DesigId AND a.IsActive=1 ORDER BY a.AdminsId DESC";

		@Override
		public List<Object[]> PandAFandAAdminData() throws Exception {			
			try {
				Query query = manager.createNativeQuery(PANDAFANDAADMINDATA);
				return (List<Object[]>) query.getResultList();
			} catch (Exception e) {
				 logger.error(new Date()+" Inside DAO PandAFandAAdminData "+ e);
					return null;
			}
		}
	
		@Override
		public PisAdmins getPandAFandAById(long adminsId) throws Exception {
			
			try {
				return manager.find(PisAdmins.class, adminsId);	
			}catch (Exception e) {
				logger.error(new Date() + "Inside DAO getPandAFandAById "+e);
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		public long PandAFandAAdd(PisAdmins admins) throws Exception {
			try {
				manager.persist(admins);
				manager.flush();
				return admins.getAdminsId();
			}  catch (Exception e) {
				logger.error(new Date() + "Inside DAO PandAFandAAdd "+e);
				e.printStackTrace();
				return 0L;
			}
		}

		@Override
		public long PandAFandAEdit(PisAdmins admins) throws Exception {
			
			try {
				manager.merge(admins);
				manager.flush();		
				return admins.getAdminsId();
			}	catch (Exception e) {
				logger.error(new Date() + "Inside DAO PandAFandAEdit "+e);
				e.printStackTrace();
				return 0L;
			}
		}
}
