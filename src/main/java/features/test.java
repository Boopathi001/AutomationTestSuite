/*
package  features;
//
import java.sql.*;
import java.util.HashMap;
public class test{
	public static String connStringOracleDbQa1 = "jdbc:oracle:thin:@10.36.96.2:1521:flvecqa1";
	public static String connStringOracleDbQa2 = "jdbc:oracle:thin:@10.36.96.2:1521:flvecqa2";
	public static String userName = "sumitb";
	public static String password = "burnwal";
    public static void main(String args[]){
    	String sql = "select o.OU_NUM,o.PR_REP_ASGN_TYPE,o.CUST_STAT_CD, o.OU_TYPE_CD,o.NAME,o.DEPT_NUM,o.OU_TYPE_CD,p.PAY_TYPE_CD,c.OWNER_LOGIN,s.X_Plan_Name " + 
    			"from siebel.s_org_ext o ,siebel.s_contact c,SIEBEL.CX_ACTPLN_HST s,siebel.s_pty_pay_prfl p where o.PAR_ROW_ID = c.PR_DEPT_OU_ID " + 
    			"and o.CUST_STAT_CD='ACTIVE' and o.DEPT_NUM = 'EMAIL' and o.OU_TYPE_CD = 'PRIVATE' "+
    			"and o.PR_REP_ASGN_TYPE = 'WALK-IN' and  o.ROW_ID = p.PARTY_ID and c.OWNER_LOGIN='SUMITB' and p.PAY_TYPE_CD='CHECK' and o.LOC = s.X_ETC_ACCOUNT_ID";
    	try {
			System.out.println(getAnAccountNumber(sql).getNString("OU_NUM").toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            }
    
    public static HashMap<String,String> getAnAccountNumber(String sql)
    {
    	HashMap <String,String> databaseHashMapObject
    	ResultSet resultSet=null;
    	try{

    		//step1 load the driver class
    		            Class.forName("oracle.jdbc.driver.OracleDriver");

    		//step2 create  the connection object
    		            Connection con=DriverManager.getConnection(
    		            		connStringOracleDbQa1,userName,password);

    		//step3 create the statement object
    		            Statement stmt=con.createStatement();

    		//step4 execute query
    		            resultSet=stmt.executeQuery(sql);
    		            
    		            */
/**//*

    		            
    		            ResultSetMetaData rsmd = resultSet.getMetaData();
    		            int columnCount = rsmd.getColumnCount();

    		            // The column count starts from 1
    		            for (int i = 1; i <= columnCount; i++ ) {
    		              String name = rsmd.getColumnName(i);
    		              System.out.println(name);
    		              // Do stuff with name
    		            }
    		            
    		            
*/
/*    		            while(resultSet.next())
    		                System.out.println(resultSet.getInt(1)+"  "+resultSet.getString(2)+"  "+resultSet.getString(3));
*//*

    		//step5 close the connection object
    		            con.close();

    		        }catch(Exception e){ System.out.println(e);}
		
		return resultSet;


    }
}*/
