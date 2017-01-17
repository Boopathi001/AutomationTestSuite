package  features;

import java.sql.*;
public class test{
    public static void main(String args[]){
        try{

//step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

//step2 create  the connection object
            Connection con=DriverManager.getConnection(
                    "jdbc:oracle:thin:@10.36.96.2:1521:flvecqa1","sumitb","burnwal");

//step3 create the statement object
            Statement stmt=con.createStatement();

//step4 execute query
            ResultSet rs=stmt.executeQuery("Select OU_NUM,OU_TYPE_CD,CUST_STAT_CD from siebel.S_ORG_EXT Where OU_TYPE_CD='PRIVATE'");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

//step5 close the connection object
            con.close();

        }catch(Exception e){ System.out.println(e);}

    }
}