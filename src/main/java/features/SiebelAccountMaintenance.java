package features;

import libraries.CommonLibrary;

import java.io.IOException;
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


        for (int i = 1; 1 <= noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Account Maintenance", i);

            if(i==1)
            {
                CommonLibrary.loginSiebelApp(eachTestCaseData);
            }

                if (eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) {

                    CommonLibrary.tcAccountMaintenanceACMO180(eachTestCaseData);
                  //  CommonLibrary.tcAccountMaintenanceACMO181(eachTestCaseData);
                 //  CommonLibrary.tcAccountMaintenanceACMO004(eachTestCaseData);
                }
        }


    }


}
