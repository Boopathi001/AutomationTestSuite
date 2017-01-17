package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static libraries.CommonLibrary.getEachTestCaseData;

/**
* Created by 23319 on 28-12-2016.
*/
public class SiebelAccountMaintenance {

    public static void SiebelAccountMaintenance() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();

        String LoginMessage = "NotSuccess";
        int Row = 0;
        String Desc = "";

        int noOfRows = exl.totalrows("TestData_maintenance","Account Maintenance");

        HashMap<String, String> eachTestCaseData = new HashMap();

        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Account Maintenance", i);
            libraries.CommonLibrary.dataObj=eachTestCaseData;
            testCaseName = "tcAccountMaintenance"+eachTestCaseData.get("TestCaseId").replace("-","");
/*

            if(i==1)
            {
                CommonLibrary.loginSiebelApp(eachTestCaseData);
            }
*/


                if (eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes"))
                {

                   // CommonLibrary.tcAccountMaintenanceACMO180(eachTestCaseData);
                  //  CommonLibrary.tcAccountMaintenanceACMO181(eachTestCaseData);
                 //  CommonLibrary.tcAccountMaintenanceACMO004(eachTestCaseData);

                    try {
                      // System.out.println(testCaseName);

                        Class<?> c = Class.forName("libraries.CommonLibrary");
                        Method m = c.getMethod(testCaseName);
                        TestCaseLibrary.Get_TestCase_Instance().Execute_TC(m);
                       // m.invoke(eachTestCaseData,"s");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Test is not found", LogStatus.FAIL,false);
                    }
                    libraries.CommonLibrary.dataObj.clear();
                    //System.out.println("hello");
                }
        }


    }


    public static void tcAccountMaintenanceACMO089(HashMap<String, String> dataObj)
    {
        System.out.println("I am running test case " + dataObj.get("TestCaseId"));

    }


}
