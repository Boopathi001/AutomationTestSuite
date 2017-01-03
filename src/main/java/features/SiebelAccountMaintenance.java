package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.HashMap;

import static libraries.CommonLibrary.getEachTestCaseData;
import static objectProperties.SblCreateAccountPageProperties.accountCompanyNameTxtBox;
import static objectProperties.SiebelAccountMaintenancePageObject.*;

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

            String executionStatus = eachTestCaseData.get("ExecutionStatus");
            String newAccountName = "";
            String oldAccountName="";

            if (executionStatus.equalsIgnoreCase("Yes")) {

                ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "<b>" + eachTestCaseData.get("TestCaseId")
                        + "</b>" + ": Test Case Execution is started....................... <br>"
                        + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);

                CommonLibrary.loginSiebelApp(eachTestCaseData);
                CommonLibrary.serachForAccount("326054780");
                FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");

                oldAccountName = FunctionLibrary.ObjDriver.findElement(By.xpath(accountCompanyNameTxtBox)).getText().toString();
                newAccountName = oldAccountName + " new1";
                FunctionLibrary.setText("xpath=//*[contains(@aria-labelledby, 'Company Name')]", newAccountName, "Enter new account name");
                new Actions(FunctionLibrary.ObjDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
                Thread.sleep(3000);

                CommonLibrary.serachForAccount("326054780");
                FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
                Desc="Verify the new account  name";
                if(FunctionLibrary.ObjDriver.findElement(By.xpath(accountCompanyNameTxtBox)).getText().equals(oldAccountName))

                    ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Expected account name: " + newAccountName+
                            ". <br> Actual account  name:"+ newAccountName,LogStatus.PASS,true);
                }
                else
                {

                    ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Expected account name: " + newAccountName+
                            ". <br> Actual account  name:"+ newAccountName,LogStatus.FAIL,true);

                }


            }


    }
}
