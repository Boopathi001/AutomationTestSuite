
package  features;
//
import libraries.FunctionLibrary;

import java.util.HashMap;
public class test{
    public static void main(String args[]){
    	String sql = "select o.OU_NUM,o.PR_REP_ASGN_TYPE,o.CUST_STAT_CD, o.OU_TYPE_CD,o.NAME,o.DEPT_NUM,o.OU_TYPE_CD,p.PAY_TYPE_CD,c.OWNER_LOGIN,s.X_Plan_Name " + 
    			"from siebel.s_org_ext o ,siebel.s_contact c,SIEBEL.CX_ACTPLN_HST s,siebel.s_pty_pay_prfl p where o.PAR_ROW_ID = c.PR_DEPT_OU_ID " + 
    			"and o.CUST_STAT_CD='ACTIVE' and o.DEPT_NUM = 'EMAIL' and o.OU_TYPE_CD = 'PRIVATE' "+
    			"and o.PR_REP_ASGN_TYPE = 'WALK-IN' and  o.ROW_ID = p.PARTY_ID and c.OWNER_LOGIN='SUMITB' and p.PAY_TYPE_CD='CHECK' and o.LOC = s.X_ETC_ACCOUNT_ID and rownum = 1";
    
    	
    	String sql1 = test.makeSqlToGetAccountInfo("WALK-IN", "PRIVATE", "CASH", "GOOD", "ACTIVE", true, true, "AUTOMATION USER", "PRITAMS");
    	HashMap <String,String> databaseDataObj = FunctionLibrary.getAnAccountNumber(sql1);
    	System.out.println(databaseDataObj.get("OU_NUM"));
    	
    	sql1 = test.makeSqlToGetAccountInfo("PHONE-IN", "BUSIESS", "CASH", "GOOD", "ACTIVE", true, true, "", "PRITAMS");
    	databaseDataObj = FunctionLibrary.getAnAccountNumber(sql1);
    	System.out.println(databaseDataObj.get("OU_NUM"));
    	
    	sql1 = test.makeSqlToGetAccountInfo("MAIL-IN", "COMMERCIAL", "VISA", "GOOD", "ACTIVE", true, true, "", "");
    	databaseDataObj = FunctionLibrary.getAnAccountNumber(sql1);
    	System.out.println(databaseDataObj.get("OU_NUM"));
    	
    	sql1 = test.makeSqlToGetAccountInfo("WALK-IN", "PRIVATE", "CASH", "GOOD", "CLOSED", true, true, "", "");
    	databaseDataObj = FunctionLibrary.getAnAccountNumber(sql1);
    	System.out.println(databaseDataObj.get("OU_NUM"));
    	
    }
    
    public static String makeSqlToGetAccountInfo(String logonMode, String accType, String reBillType, String financialStatus, 
    				String accountStatus, boolean isAnonymous, boolean isTaxExempt, String accName, String accCreatedLoginName)
    {
    	String sql;
    	String logonModePart;
    	String accTypePart;
    	String reBillTypePart;
    	String financialStatusPart;
    	String accountStatusPart;
    	String isAnonymousPart;
    	String isTaxExemptPart;
    	String accCreatedLoginNamePart;
    	String accCustomerNamePart;
    	String onlyOneRecordPart;
    	
    	sql = "select  o.CREATED, o.OU_NUM,o.PR_REP_ASGN_TYPE,o.CUST_STAT_CD, o.OU_TYPE_CD,o.NAME,o.DEPT_NUM,o.OU_TYPE_CD,p.PAY_TYPE_CD,c.OWNER_LOGIN,s.X_Plan_Name " + 
    					"from siebel.s_org_ext o ,siebel.s_contact c,SIEBEL.CX_ACTPLN_HST s,siebel.s_pty_pay_prfl p where o.PAR_ROW_ID = c.PR_DEPT_OU_ID " + 
						"and o.LOC = s.X_ETC_ACCOUNT_ID and  o.ROW_ID = p.PARTY_ID ";
    	
    	logonModePart = " and o.PR_REP_ASGN_TYPE = ";
    	accTypePart = " and o.OU_TYPE_CD = ";
    	reBillTypePart = " and p.PAY_TYPE_CD = ";
    	//financialStatusPart = " and o.PR_REP_ASGN_TYPE = ";
    	accountStatusPart = " and o.CUST_STAT_CD= ";
    	isAnonymousPart = " and o.PR_REP_ASGN_TYPE = ";
    	isTaxExemptPart = " and o.PR_REP_ASGN_TYPE = ";
    	accCreatedLoginNamePart = " and c.OWNER_LOGIN = ";
    	accCustomerNamePart = " and o.NAME like ";
    	onlyOneRecordPart = "  and rownum=1  order by o.CREATED desc ";
    	onlyOneRecordPart = " order by o.CREATED desc ";
    	
    	
    	if(logonMode!="")
    	{
    		sql = sql + logonModePart+"\'"+logonMode +"\' ";
    	}
    	
    	if(accType!="")
    	{
    		sql = sql + accTypePart+"\'"+accType +"\' ";
    	}
    	
    	if(reBillType!="")
    	{
    		sql = sql + reBillTypePart+"\'"+reBillType +"\' ";
    	}
    	
    	/*if(financialStatus!="")
    	{
    		sql = sql + financialStatusPart;
    	}*/
   
     	if(accountStatus!="")
    	{
    		sql = sql + accountStatusPart+"\'"+accountStatus +"\' ";
    	}
   
    	if(!isTaxExempt)
    	{
    		sql = sql + isTaxExemptPart+"\'"+isTaxExempt +"\' ";
    	}
    	
    	if(!isAnonymous)
    	{
    		sql = sql + isAnonymousPart+"\'"+isAnonymous +"\' ";
    	}
    	
    	if(accCreatedLoginName!="")
    	{
    		sql = sql + accCreatedLoginNamePart+"\'"+accCreatedLoginName +"\'";
    	}
    	
    	if(accName!="")
    	{
    		sql = sql + accCustomerNamePart+"\'%"+accName +"%\'";
    	}
    	  	
    	sql = sql + onlyOneRecordPart;
   
    	System.out.println(sql);
		return sql;
    	
    }
    
    
}
