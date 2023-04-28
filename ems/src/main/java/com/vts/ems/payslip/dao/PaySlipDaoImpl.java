package com.vts.ems.payslip.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vts.ems.config.MsAccessDBConnectionConfig;

@Component
public class PaySlipDaoImpl implements PaySlipDao {
	
	
	private static final Logger logger = LogManager.getLogger(PaySlipDaoImpl.class);
	@Autowired	
	private MsAccessDBConnectionConfig ConnectionProvider;
		
//	private static final String MONTHWISEPAYSLIPSYNCLIST = "SELECT M_M.Month, M_M.[Emp NO], M_M.[Emp NAME], Emp_M.Level, M_M.[F/M], Emp_M.[PAN NO], Emp_M.Address, Emp_M.Grade, \r\n"
//			+ "Emp_M.[Entry DATE], Emp_M.[ACCOUNT NO], Emp_M.Qtr, Var_MM.A_Days, Var_MM.R_Days_LM, Var_MM.OT_Hrs, \r\n"
//			+ "Emp_M.[Basic Rate], Emp_M.[GP Rate], M_M.[DA Rate], ROUND(([M_M]![Basic Rate]+[M_M]![GP Rate])*0.24,0) AS [HRA Rate], \r\n"
//			+ "ROUND(([Emp_M]![Trans_All]*[Mnths]![DA%]),0)+[Emp_M]![Trans_All] AS Trans_All, M_M.Basic, M_M.GP, M_M.DA, M_M.HRA, \r\n"
//			+ "[M_M]![Trans_All]+[M_M]![DA_TA] AS TA, M_M.SFE, M_M.Med_Reim, M_M.OT, [Var_MM]![PL_Encashmt] AS PL_Encashmt, M_M.Shift_All, \r\n"
//			+ "[M_M]![Fes_Adv_E] AS Fes_Adv_E, M_M.Edu_All, M_M.Bonus, M_M.E1, [Basic]+[GP]+[DA]+[HRA]+[TA]+[SFE]+[Med_Reim]+[OT]+[Shift_All]+[Fes_Adv_E]+[Edu_All]+[Bonus]+[E1]+[PL_Encashmt] AS [Gross Pay], \r\n"
//			+ "M_M.PF, M_M.APF, M_M.GI, M_M.LIC_Rec, M_M.PT, Tax.Tax_Mnth, M_M.Lib, M_M.Inadd_Med, M_M.Fest_Adv, M_M.R_Rec, M_M.PLI, Var_MM.Water_Recovery, Var_MM.Sec_Recovery, M_M.ITI_Scty, M_M.Medi_R, Var_MM.D_2, \r\n"
//			+ "Var_MM.D_3, Var_MM.D_4, Var_MM.D_5, \r\n"
//			+ "[PF]+[APF]+[GI]+[LIC_Rec]+[PT]+[Tax_Mnth]+[Lib]+[Inadd_Med]+[Fest_Adv]+[PLI]+[Water_Recovery]+[Sec_Recovery]+[ITI_Scty]+[Medi_R]+[M_M]![D_2]+[M_M]![D_3]+[R_Rec] AS Gross_Recy, \r\n"
//			+ "\r\n"
//			+ "M_M.Gross_Earnings_A, M_M.HRA_Exmp_A, [M_M]![80_C]+[M_M]![80_D]+[M_M]![80_G]+[M_M]![80_DD]+[M_M]![80_E] AS [Gross Dedn], M_M.[Taxable Income], [Tax]![Tot_Tax]-[Tax]![Cess] AS [I Tax], Tax.Cess, Tax.Tot_Tax, [Tax]![SumOfIT_Paid]+[Tax]![Tax_Mnth] AS SumOfIT_Paid, IIf([Tot_Tax]-[SumOfIT_Paid]<0,0,[Tot_Tax]-[SumOfIT_Paid]) AS Bal_IT, [Gross Pay]-[Gross_Recy] AS [Net Pay]\r\n"
//			+ "FROM Mnths \r\n"
//			+ "INNER JOIN ((((SELECT Var_MM.Month, Emp_M.[Emp NO], Emp_M.[Emp NAME], Emp_M.[F/M], ROUND(([Emp_M]![Basic Rate]*[Var_MM]![A_Days]/[Mnths]![NO of Days])+[Var_MM]![Adj_Basic]+([Emp_M_LM]![Basic Rate_LM]/[Emp_M_LM]![NO of Days]*[Var_MM]![R_Days_LM])-([Emp_M_LM]![Basic Rate_LM]/[Emp_M_LM]![NO of Days]*[Var_MM]![D_Days_LM]),0) AS Basic, ROUND(([Emp_M]![GP Rate]*[Var_MM]![A_Days]/[Mnths]![NO of Days])+[Var_MM]![Adj_GP]+([Emp_M_LM]![GP Rate_LM]/[Emp_M_LM]![NO of Days]*[Var_MM]![R_Days_LM])-([Emp_M_LM]![GP Rate_LM]/[Emp_M_LM]![NO of Days]*[Var_MM]![D_Days_LM]),0) AS GP, ROUND(ROUND(((([Emp_M]![Basic Rate]+[Emp_M]![GP Rate])*[Mnths]![DA%])/[Mnths]![NO of Days]*[Var_MM]![A_Days]),2)+0.0001,0)+[Var_MM]![Adj_DA]+ROUND((([Emp_M_LM]![Basic Rate_LM]+[Emp_M_LM]![GP Rate_LM])*[Emp_M_LM]![DA%_LM])/[Emp_M_LM]![NO of Days]*[Var_MM]![R_Days_LM],0)-ROUND((([Emp_M_LM]![Basic Rate_LM]+[Emp_M_LM]![GP Rate_LM])*[Emp_M_LM]![DA%_LM])/[Emp_M_LM]![NO of Days]*[Var_MM]![D_Days_LM],0) AS DA, Emp_M.[Basic Rate], Emp_M.[GP Rate], ROUND(((([Emp_M]![Basic Rate]+[Emp_M]![GP Rate])*[Mnths]![DA%])+0.0001),0) AS [DA Rate], ROUND(IIf([Emp_M]![HRA_Y/N]=\"Yes\",([Basic]+[GP])*0.24,0)+[Var_MM]![Adj_HRA],0) AS HRA, IIf([Emp_M]![Key_TA]=\"Single\",[Emp_M]![Trans_All],[Emp_M]![Trans_All]*2)+[Var_MM]![Adj_TA] AS Trans_All, ROUND([Trans_All]*[Mnths]![DA%]+[Var_MM]![Adj_DA_TA],0) AS DA_TA, [Emp_M]![SFE] AS SFE, [Var_MM]![Med_Reim] AS Med_Reim, [Var_MM]![Edu_All] AS Edu_All, [Var_MM]![Bonus] AS Bonus, ROUND((([Emp_M_LM]![Basic Rate_LM]+[Emp_M_LM]![GP Rate_LM]+[Emp_M_LM]![DA_LM])/(26*8))*[Var_MM]![OT_Hrs],0)+[Var_MM]![OT] AS OT, [Var_MM]![Shift] AS Shift_All, [Var_MM]![E1] AS E1, Var_MM.Fes_Adv_E, IIf([Emp_M]![HRA_Y/N]=\"No\",([Basic]+[GP]+[DA])*0.15,0) AS [Perk_15%], IIf([Perk_15%]>[Qtr]![Rnt],[Qtr]![Rnt],[Perk_15%])-[L_Fees] AS Qtrs, [Basic]+[GP]+[DA]+[HRA]+[Trans_All]+[DA_TA]+[SFE]+[Med_Reim]+[Edu_All]+[Bonus]+[OT]+[Shift_All]+[E1]+[Fes_Adv_E] AS Gross_E_Sal, [Basic]+[GP]+[DA]+[HRA]+[Trans_All]+[DA_TA]+[SFE]+[Med_Reim]+[Edu_All]+[Bonus]+[OT]+[Shift_All]+[E1] AS [Salary Gross Earnings], ROUND(([Basic]+[GP]+[DA])*0.12,0) AS PF, [Var_MM]![APF] AS APF, INT((IIf([Emp_M]![Med_R]=\"Yes\",([Emp_M]![Basic Rate]+[Emp_M]![GP Rate])*0.01,0)+5)/10)*10+[Var_MM]![Medi_R_Adj] AS Medi_R, [Var_MM]![LIC_Recovery] AS LIC_Rec, [Var_MM]![Rent_Recovery] AS R_Rec, [Var_MM]![Water_Recovery] AS Water_Rec, [Var_MM]![Fest_Adv] AS Fest_Adv, [Var_MM]![ITI_Society] AS ITI_Scty, [Var_MM]![Library_recovery] AS Lib, IIf([Emp_M]![GI]=\"Y\",[GI&TA]![GI amt],0) AS GI, [Var_MM]![PLI] AS PLI, Var_MM.D_2, Var_MM.D_3, [Var_MM]![Inadd_Medi] AS Inadd_Med, [PF]+[APF]+[LIC_Rec]+[R_Rec]+[Water_Rec]+[PLI]+[Fest_Adv]+[ITI_Scty]+[Lib]+[GI]+[D_2]+[D_3] AS Gross_D_Sal, [PF]+[APF]+[LIC_Rec]+[R_Rec]+[Water_Rec]+[PLI]+[Fest_Adv]+[ITI_Scty]+[Lib]+[GI] AS [Salary Gross_Deductions], [Gross_E_Sal]-[Gross_D_Sal] AS Net_Salary, [Salary Gross Earnings]-[Salary Gross_Deductions] AS [Salary Net Earnings], 0.5*([Basic]+[DA]+[GP]+[SFE]) AS [50%_Sal], IIf([Inv_M]![RPaid]-([Basic]+[GP]+[DA]+[SFE])*0.1<0,0,[Inv_M]![RPaid]-([Basic]+[GP]+[DA]+[SFE])*0.1) AS [10%_Sall], (IIf([HRA]<(IIf([RPaid]<[10%_Sall],[RPaid],[10%_Sall])),[HRA],IIf([RPaid]<[10%_Sall],[RPaid],[10%_Sall]))) AS HRA_Exm, IIf([Emp_M]![Key_TA]=\"Single\",IIf([Salary Gross Earnings]<15000,0,200),0) AS PT, [Mnths]![P_Mnth]-[Emp_M]![P_Mth_Adj] AS P_Mnth, [P_Mnth]*[Emp_M]![Basic Rate] AS Basic_P, [P_Mnth]*[Emp_M]![GP Rate] AS GP_P, ROUND((([Basic Rate]+[GP Rate])*[Mnths]![DA%]+0.01),0)*[P_Mnth] AS DA_P, ROUND(IIf([Emp_M]![HRA_Y/N]=\"Yes\",([Basic_P]+[GP_P])*0.24,0),0) AS HRA_P, [P_Mnth]*IIf([Emp_M]![Key_TA]=\"Single\",[Emp_M]![Trans_All],[Emp_M]![Trans_All]*2) AS Trans_All_P, ROUND([Trans_All_P]*[Mnths]![DA%],0) AS DA_TA_P, [SFE]*[P_Mnth] AS SFE_P, [E1]*[P_Mnth] AS E1_P, ROUND(([Basic_P]+[GP_P]+[DA_P])*0.12) AS PF_P, [APF]*[P_Mnth] AS APF_P, ([Medi_R]-[Var_MM]![Medi_R_Adj])*[P_Mnth] AS medi_Rec_P, [LIC_Rec]*[P_Mnth] AS LIC_Rec_P, [P_Mnth]*[PLI] AS PLI_P, [P_Mnth]*[GI] AS GI_P, [P_Mnth]*[HRA_Exm] AS HRA_Exmp_P, [Qtrs]*[P_Mnth] AS Qtrs_P, [PT]*[P_Mnth] AS PT_P, [Basic]+[Basic_P]+[Sum_Act]![SumOfBasic]+[Inv_M]![Basic OS] AS Basic_A, [GP]+[GP_P]+[Sum_Act]![SumOfGP]+[Inv_M]![Grade Pay OS] AS GP_A, [DA]+[DA_P]+[Sum_Act]![SumOfDA]+[Inv_M]![DA OS] AS DA_A, [HRA]+[HRA_P]+[Inv_M]![HRA OS]+[Sum_Act]![SumOfHRA] AS HRA_A, IIf(([Trans_All]+[Trans_All_P]+[Sum_Act]![SumOfTrans_All]+[DA_TA]+[DA_TA_P]+[Sum_Act]![SumOfDA_TA]<[Inv_M]![Transport_Exp]),0,([Trans_All]+[Trans_All_P]+[Sum_Act]![SumOfTrans_All]+[DA_TA]+[DA_TA_P]+[Sum_Act]![SumOfDA_TA]-[Inv_M]![Transport_Exp])) AS Trans_All_A, 0 AS DA_TA_A, [SFE]+[SFE_P]+[Sum_Act]![SumOfSFE] AS SFE_A, [Bonus]+[Inv_M]![Bonus OS]+[Sum_Act]![SumOfBonus] AS Bonus_A, [OT]+[Sum_Act]![SumOfOT] AS OT_A, [Shift_All]+Sum_Act![Shift_All] AS Shift_All_A, [Qtrs]+[Qtrs_P]+[Sum_Act]![Qtrs]+[Inv_M]![Qtr_adj] AS Qtrs_A, ([Med_Reim]-[Inadd_Med]+[Sum_Act]![SumOfMed_Reim]+[Inv_M]![Medi_Reim OS]-[Inv_M]![Medi_Expm]) AS Med_Reim_A, ([Edu_All]+[Sum_Act]![Edu_All])-[Inv_M]![Edu_All_Exp]+[Inv_M]![Edu_All OS] AS Edu_All_A, [E1]+[E1_P]+[Sum_Act]![SumOfE1] AS E1_A, [Inv_M]![PL_Encashmt]+[Var_MM]![PL_Encashmt] AS PL_Encashmt, Inv_M.Others, Inv_M.Arrears, 50000 AS Std_Dedtn, [Basic_A]+[GP_A]+[DA_A]+[HRA_A]+[Trans_All_A]+[DA_TA_A]+[SFE_A]+[Bonus_A]+[OT_A]+[Shift_All_A]+[Qtrs_A]+[Med_Reim_A]+[Edu_All_A]+[PL_Encashmt]+[Others]+[Arrears]+[E1_A]-[Std_Dedtn] AS Gross_Earnings_A, [PF]+[PF_P]+[Sum_Act]![SumOfPF]+[Inv_M]![PF_adj] AS PF_A, [APF]+[APF_P]+[Sum_Act]![SumOfAPF] AS APF_A, [LIC_Rec]+[LIC_Rec_P]+[Sum_Act]![LIC_R]+[Inv_M]![LIC_Premium] AS LIC_Rec_A, [GI]+[GI_P]+[Sum_Act]![SumOfGI] AS GI_A, [PLI]+[PLI_P]+[Sum_Act]![SumOfPLI]+[Inv_M]![PLI] AS PLI_A, [HRA_Exmp_P]+[HRA_Exm]+[Sum_Act]![SumOfHRA_Exm] AS HRA_Exmp_A, [PT_P]+[PT]+[Sum_Act]![PT] AS PT_A, Inv_M.[Int_HL+Others], Inv_M.ULIP, Inv_M.FD_PSU, Inv_M.NSC, Inv_M.Tuition_Fees, Inv_M.Infra_Bonds, Inv_M.HL_RP, Inv_M.[IT_89(1)_R], [Gross_Earnings_A]-[HRA_Exmp_A]-[PT_A] AS Taxable_Income_from_Salaries, [Taxable_Income_from_Salaries]+[Int_HL+Others] AS [Gross Total Income], IIf([PF_A]+[APF_A]+[LIC_Rec_A]+[GI_A]+[PLI_A]+[FD_PSU]+[ULIP]+[NSC]+[HL_RP]+[Infra_Bonds]+[Tuition_Fees]>150000,150000,[PF_A]+[APF_A]+[LIC_Rec_A]+[GI_A]+[PLI_A]+[FD_PSU]+[ULIP]+[NSC]+[HL_RP]+[Infra_Bonds]+[Tuition_Fees]) AS 80_C, [Medi_R]+[medi_Rec_P]+[Sum_Act]![Medi_R] AS 80_D, Inv_M.[80_G], Inv_M.[80_DD], Inv_M.[80_E], [Gross Total Income]-[80_C]-[80_G]-[80_D]-[80_DD]-[80_E] AS [Taxable Income_WRO], INT((([Gross Total Income]-[80_C]-[80_G]-[80_D]-[80_DD]-[80_E])+5)/10)*10 AS [Taxable Income], Sum_Act.SumOfIT_Paid\r\n"
//			+ "FROM ((((((Emp_M INNER JOIN Var_MM ON Emp_M.[Emp NO] = Var_MM.[Emp NO]) INNER JOIN Inv_M ON Emp_M.[Emp NO] = Inv_M.[Emp NO]) INNER JOIN Mnths ON Var_MM.Month = Mnths.Month) INNER JOIN [GI&TA] ON Emp_M.Grade = [GI&TA].Grade) INNER JOIN Sum_Act ON Emp_M.[Emp NO] = Sum_Act.[Emp NO]) INNER JOIN Qtr ON Emp_M.Qtr = Qtr.Qtr) INNER JOIN Emp_M_LM ON Emp_M.[Emp NO] = Emp_M_LM.[Emp NO];\r\n"
//			+ ") AS M_M INNER JOIN Tax ON (M_M.[Emp NAME] = Tax.[Emp NAME]) AND (M_M.[Emp NO] = Tax.[Emp NO])) INNER JOIN Emp_M ON (Tax.[Emp NAME] = Emp_M.[Emp NAME]) AND (Tax.[Emp NO] = Emp_M.[Emp NO]) AND (M_M.[Emp NAME] = Emp_M.[Emp NAME]) AND (M_M.[Emp NO] = Emp_M.[Emp NO])) INNER JOIN Var_MM ON (Emp_M.[Emp NAME] = Var_MM.[Emp NAME]) AND (Emp_M.[Emp NO] = Var_MM.[Emp NO])) ON (M_M.Month = Mnths.Month) AND (Mnths.Month = Var_MM.Month);\r\n"
//			+ "";
	
	private static final String MONTHWISEPAYSLIPSYNCLIST = "SELECT * FROM Payslip";
	@Override
	public ResultSet MonthWisePaySlipSyncList(int month,int year) throws Exception
	{
		try {
			Connection conn = ConnectionProvider.getMSAccessConnection();		
//			ResultSet result = conn.prepareCall(MONTHWISEPAYSLIPSYNCLIST).executeQuery();
	        Statement stmt = conn.createStatement();
	        ResultSet result = stmt.executeQuery(MONTHWISEPAYSLIPSYNCLIST);
	        while(result.next()) {
	        	System.out.println(result.getString(1) +"   "+result.getString(2));
	        }
//	        stmt.close(); 
	        conn.close();	  
	        return result;
	    }catch (Exception e) {
	    	logger.error(new Date() +" Inside DAO MonthWisePaySlipSyncList", e);
			e.printStackTrace();
			return null;
		}
		
	}

}
