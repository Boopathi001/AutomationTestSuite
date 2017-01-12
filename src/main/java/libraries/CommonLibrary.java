package libraries;

import com.relevantcodes.extentreports.LogStatus;
import features.ExcelSheet;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.time.Clock;
import java.util.*;

import static objectProperties.SblCreateAccountPageProperties.*;
import static objectProperties.SiebelAccountMaintenancePageObject.*;

/**
* Created by 23319 on 28-12-2016.
*/
public class CommonLibrary {
    public static String newBrowser="";
    public static String oldBrowser="";

    public static String stringHelperToGenerateUniqueLastName = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random rand = new Random();
    // consider using a Map<String,Boolean> to say whether the identifier is being used or not
    public static Set<String> identifiers = new HashSet<String>();


    public static HashMap settingsSheetInfo;

    /*public CommonLibrary(){

    }*/
    public static CommonLibrary commonInstance;

    CommonLibrary()
    {
        settingsSheetInfo = getSettingsSheetInfo();
    }

    public static HashMap<String, String> getSettingsSheetInfo()
    {
        HashMap<String,String> settingsSheetData = new HashMap<String,String>();
        try
        {
            DataFormatter formatter = new DataFormatter();
            FileInputStream file = new FileInputStream(new File(ReportLibrary.getPath()
                    +"\\testdata\\TestScenarios_Selector.xls"));
            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheet("Settings");
            int noOfRows = sheet.getLastRowNum();
            //System.out.println("no of rows:" + noOfRows);

            int i = 0;
            Row rowWithColumnNames = sheet.getRow(0);
            int noOfColumns = rowWithColumnNames.getPhysicalNumberOfCells();
            //System.out.println(noOfColumns);
            String settingsKey ="";
            String settingsValue = "";

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            for(int m=0;m<=noOfRows;m++)
            {
                //System.out.println("Ieration number : " + m);
                Row rowCurrent = rowIterator.next();
                if(m<=0){
                    continue;
                }
                settingsKey = String.valueOf(rowCurrent.getCell(1));
                settingsValue = String.valueOf(rowCurrent.getCell(2));
                settingsSheetData.put(settingsKey,settingsValue);
            }
            file.close();
            return settingsSheetData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public static Map getTestCasesTestData(String locOfFile)
    {
        HashMap<String,String> rowData = new HashMap<String,String>();
        try
        {
            DataFormatter formatter = new DataFormatter();
            FileInputStream file = new FileInputStream(new File(locOfFile));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            int noOfRows = sheet.getLastRowNum();
            //System.out.println("no of rows:" + noOfRows);

            int i = 0;
            Row rowWithColumnNames = sheet.getRow(0);
            int noOfColumns = rowWithColumnNames.getPhysicalNumberOfCells();
            //System.out.println(noOfColumns);
            String testCaseName ="";
            String columnNamesAndValuesOfOneRow = "";

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            // System.out.println(rowIterator

            for(int m=0;m<=noOfRows;m++)
            {
                //System.out.println("Ieration number : " + m);
                Row rowCurrent = rowIterator.next();
                if(m==0){
                    continue;
                }
                testCaseName = String.valueOf(rowCurrent.getCell(0));
                //     System.out.println("test case name " + testCaseName);

                for (int p = 0; p < noOfColumns; p++) {
                    //formatter.formatCellValue(rowWithColumnNames.getCell(p))=="" &&
                    //Igonre the columns without any column name in test case excel file
                    if(formatter.formatCellValue(rowWithColumnNames.getCell(p))=="")
                    {
                        continue;
                    }
                    columnNamesAndValuesOfOneRow = columnNamesAndValuesOfOneRow+formatter.formatCellValue((rowWithColumnNames.getCell(p))).trim()+
                            ":"+formatter.formatCellValue((rowCurrent.getCell(p))).trim()+";";

                }
                rowData.put(testCaseName,columnNamesAndValuesOfOneRow);
                columnNamesAndValuesOfOneRow="";

            }
            file.close();

            //Sorting the test case ids which are present in Hashmap(allTestCasesDataBeforeSort)
            Map<String, String> allTestCasesData = new TreeMap<String, String>(rowData);

            //System.out.println("After Sorting:");
            Iterator iterator = allTestCasesData.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry me = (Map.Entry)iterator.next();
            }

            return allTestCasesData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    public static HashMap<String,String> dELEMEgetRunningTestCaseData(HashMap allTestCasesData)
    {
        //Defining variables to save data for each row
        String testCaseName = "";
        String colNamesAndValuesInfoString = "";
        String colNamesAndValuesInfoArray [];
        String keyName="";
        String keyValue="";

        //Creating a map object to keep each test case data
        HashMap<String,String> eachTestCaseData = new HashMap<String, String>(allTestCasesData);
        Iterator iterator1 = allTestCasesData.entrySet().iterator();

        while(iterator1.hasNext()) {
            Map.Entry me1 = (Map.Entry) iterator1.next();
            testCaseName = me1.getKey().toString();
            //this string holds all the columnn name and vlaue perticular syntax(Example - "PlazaId:099;PltRead:997;
            colNamesAndValuesInfoString = me1.getValue().toString();
            //this array holds column names and values for a single test case(row)
            colNamesAndValuesInfoArray = colNamesAndValuesInfoString.split(";");
            //column name and value are keeping here in a Map(eachTestCaseData)
            for (int i = 0; i <= colNamesAndValuesInfoArray.length - 1; i++) {
                keyName = colNamesAndValuesInfoArray[i].split(":")[0].toString();//keyname(column name)
                if (colNamesAndValuesInfoArray[i].split(":").length == 2) {
                    //key  value(actual value to be passed in xml)
                    keyValue = colNamesAndValuesInfoArray[i].split(":")[1].toString();
                } else {
                    keyValue = "";
                }
                eachTestCaseData.put(keyName, keyValue);
            }
        }
        return eachTestCaseData;
    }
public static HashMap getEachTestCaseData(ExcelSheet ex, String sheetName, int currentRowNumber) {

    DataFormatter formatter = new DataFormatter();
    XSSFRow rowWithColumnNames = null;
    try {
        rowWithColumnNames = ex.getRow(sheetName, 0);
    } catch (IOException e) {
        e.printStackTrace();
    }
    HashMap<String, String> eachTestCaseData = new HashMap();

    XSSFRow rowCurrent = null;
    try {
        rowCurrent = ex.getRow(sheetName, currentRowNumber);
    } catch (IOException e) {
        e.printStackTrace();
    }

    for (int p = 0; p < rowWithColumnNames.getLastCellNum(); p++) {
        //Igonre the columns without any column name in test case excel file
        if (formatter.formatCellValue(rowWithColumnNames.getCell(p)) == "") {
            continue;
        }
        eachTestCaseData.put(formatter.formatCellValue((rowWithColumnNames.getCell(p))).trim(), formatter.formatCellValue((rowCurrent.getCell(p))).trim());
    }

    return  eachTestCaseData;

}

    public static String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(stringHelperToGenerateUniqueLastName.charAt(rand.nextInt(stringHelperToGenerateUniqueLastName.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public static void loginSiebelApp(HashMap<String,String>dataObj) {
        String Desc;
        try {
            //Launching Browser
            CommonLibrary.launchBrowser(CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString(),
                    "Launching siebel app", dataObj.get("Browser Type"));
            FunctionLibrary.Wait_For_Object = new WebDriverWait(FunctionLibrary.ObjDriver, 60);
            FunctionLibrary.Wait_For_Object.until(ExpectedConditions.visibilityOfElementLocated(By.id("s_swepi_1")));
        } catch (Exception e) {
            System.out.println("Problem in Home Page");
        }

        //Enter User Name
        Desc = "Entering UserName on UserName textbox";
        FunctionLibrary.setText(loginUsernameTxtBox, CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME"), Desc);
        //Enter Password
        Desc = "Entering Password into password field";
        FunctionLibrary.setText(loginPasswordTxtBox, CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD"), Desc);
        //Click on Sign-in Button
        Desc = "Clicking on Sign in Button";
        FunctionLibrary.clickObject(signInBtn, "", "Click sign in button");
        WebDriverWait wait = new WebDriverWait(FunctionLibrary.ObjDriver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//div[@class='Welcome'])[1]")));

        //Thread.sleep(3000);
        //if(LoginMessage != null)
        Desc = "Verify Account Opening element";
        if (!FunctionLibrary.verifyWebElementExist(siebelHomePageVerificationTxt, Desc)) {
            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed",
                    LogStatus.FAIL, true);
        }

    }

    public static void launchBrowser(String url,String desc,String browserName)
    {
        newBrowser = browserName;
        if(!oldBrowser.equals(newBrowser))
        {
            try{
                FunctionLibrary.ObjDriver.quit();
                

            }
            catch (Exception e)
            {
                System.out.println("Webdriver is not yet initiated");
            }
            System.out.println(browserName);
            if(browserName.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver",".\\src\\browserDrivers\\geckodriver.exe");
                DesiredCapabilities capabilities=DesiredCapabilities.firefox();
                capabilities.setCapability("marionette", true);
                FunctionLibrary.ObjDriver = new FirefoxDriver(capabilities);

            }else if(browserName.equalsIgnoreCase("chrome")) {

                System.setProperty("webdriver.chrome.driver", ".\\src\\browserDrivers\\chromedriver.exe");

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                FunctionLibrary.ObjDriver = new ChromeDriver( );

            }else if(browserName.equalsIgnoreCase("iexplore")) {

                System.setProperty("webdriver.ie.driver",".\\src\\browserDrivers\\IEDriverServer.exe");
                FunctionLibrary.ObjDriver = new InternetExplorerDriver();
                //Get Browser name and version.
              //Get OS name.
            }else {
                System.out.println(FunctionLibrary.ObjDriver + " is not a supported browser");
            }

        }
        


       


        FunctionLibrary.ObjDriver.manage().window().maximize();
        Capabilities caps = ((RemoteWebDriver) FunctionLibrary.ObjDriver).getCapabilities();
        String browserName1 = caps.getBrowserName();
        String browserVersion = caps.getVersion();

        String os = System.getProperty("os.name").toLowerCase();
        //System.out.println("operating system: " + os);
        ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Test environment: Browser '" + browserName1 + "' of version '"
                + browserVersion + "' on OS '"+os+"'",LogStatus.INFO,false);
        FunctionLibrary.ObjDriver.navigate().to(url);
        oldBrowser=newBrowser;

    }
public static void serachForAccount(String accountNumber)
{

    FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts tab");
    FunctionLibrary.setText(accountNumberTxtBox, accountNumber, "Entering the account number");
    ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Search for account number: 326054780",LogStatus.INFO,false);
    FunctionLibrary.clickObject(goBtn, "", "Clicking go button");
}

public static void tcAccountMaintenanceAC0014(HashMap<String, String> dataObj)
{
    String executionStatus = dataObj.get("ExecutionStatus");
    String newAccountName = "";
    String oldAccountName="";

    if (executionStatus.equalsIgnoreCase("Yes")) {

        ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "<b>" + dataObj.get("TestCaseId")
                + "</b>" + ": Test Case Execution is started....................... <br>"
                + "Test case description: " + dataObj.get("TestCaseDesc"), LogStatus.INFO, false);

        CommonLibrary.loginSiebelApp(dataObj);
        CommonLibrary.serachForAccount(dataObj.get("AccountNo"));
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");

        oldAccountName = FunctionLibrary.ObjDriver.findElement(By.xpath(accountCompanyNameTxtBox)).getText().toString();
        newAccountName = oldAccountName + " new1";
        FunctionLibrary.setText("xpath=//*[contains(@aria-labelledby, 'Company Name')]", newAccountName, "Enter new account name");
        new Actions(FunctionLibrary.ObjDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
        //Thread.sleep(3000);

        CommonLibrary.serachForAccount("326054780");
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        //Desc="Verify the new account  name";
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


    public static void tcAccountMaintenanceACMO180(HashMap<String, String> dataObj) throws InterruptedException {


        String executionStatus = dataObj.get("ExecutionStatus");
        String newAccountName = "";
        String oldAccountName="";

        String vehiclesNewButtonPlate ="xpath=//button[@id='s_2_1_16_0_Ctrl']";
        String vehiclesCountyPlate="xpath=//*[@id='1_Plate_Country']";
        String vehiclePlateCountryList="xpath=(//*[contains(@aria-labelledby, '_Plate_Country s_') and contains(@id,'_Plate_Country')])[1]";
        String vehicleYearList="xpath=(//*[contains(@aria-labelledby, '_Year_of_Vehicle s') and contains(@id,'_Year_of_Vehicle')])[1]";
        String vehicleSaveList="xpath=.//*[@title='Vehicle List:Save']";

        if (executionStatus.equalsIgnoreCase("Yes")) {

            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "<b>" + dataObj.get("TestCaseId")
                    + "</b>" + ": Test Case Execution is started....................... <br>"
                    + "Test case description: " + dataObj.get("TestCaseDesc"), LogStatus.INFO, false);

            CommonLibrary.serachForAccount(dataObj.get("AccountNo"));

            Thread.sleep(8000);
            FunctionLibrary.ObjDriver.findElement(By.linkText(dataObj.get("AccountNo"))).click();
            String Desc="";
            //HashMap<String,String> eachTestCaseData =new HashMap();

            Desc="Clicking vehicles tab";
            FunctionLibrary.clickObject(vehiclesTab,"",Desc);

            String vehiclesToBeAdded = dataObj.get("VehiclesInfo");
            vehiclesToBeAdded = vehiclesToBeAdded.replace("\n","");

            String []vehiclesItems = vehiclesToBeAdded.split(",");
            String [] eachVehicleInfo;
            System.out.println(vehiclesItems.length);
            String []plateNumber=new String[vehiclesItems.length];
            String plateState;
            String plateType;
            String plateCountry;
            String vehicleType;
            String year;
            String make;
            String model;

            for (int j =0;j<=vehiclesItems.length-1;j++) {
                eachVehicleInfo = vehiclesItems[j].split(":");
                //plateNumber[j] = eachVehicleInfo[0]+ DateTime.now().getMillisOfSecond();
                plateNumber[j] = "VK" + DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
                plateState = eachVehicleInfo[1];
                plateType = eachVehicleInfo[2];
                plateCountry = eachVehicleInfo[3];

                System.out.print("country:+++++++++++++++++++"+plateCountry);
                vehicleType = eachVehicleInfo[4];
                year = eachVehicleInfo[5];
                make = eachVehicleInfo[6];
                model = eachVehicleInfo[7];
                Thread.sleep(3000);
                //Clicks on vehicles new button
                Desc="Clicking vehicles new button";
                FunctionLibrary.clickObject(vehiclesNewButtonPlate,"",Desc);
                ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Adding plate number: " + plateNumber[0],LogStatus.INFO,false);
                //Enter PLate Number
                Desc="Entering plate number";
                FunctionLibrary.setText(vehiclesDetailsPlateNumberTxtBox,plateNumber[j],Desc);
                //Clicks on plate state field
                Desc="Clicking plate state field";
                FunctionLibrary.clickObject(vehicleDetailsPlateStateElement,"",Desc);
                //Enter PLate Number
                Desc="Entering plate number";
                FunctionLibrary.setText(vehicleDetailsPlateStateTxtBox,plateState,Desc);
                //Clicks plate type
                Desc="Clicking plate type field";
               // FunctionLibrary.clickObject(vehicleDetailsPlateTypeElement,"",Desc);
                //Enter Plate Type
                Desc="Enter plate type";
                FunctionLibrary.setText(vehicleDetailsPlateTypeTxtBox,plateType,Desc);
                //Enter vehicle type
                Desc="Enter vehicle type";
                FunctionLibrary.setText(vehicleDetailsVehicleTypeTxtBox,vehicleType,Desc);


                //Clicking plate country
                Desc="Clicking plate country";
                FunctionLibrary.clickObject(vehiclePlateCountryList,"",Desc);
                //Enter plate country name
                Desc="Enter plate country name";
                FunctionLibrary.setText(vehiclesCountyPlate,plateCountry,Desc);
                /*//Clicking vehicle type
                Desc="Clicking vehicle type";
                FunctionLibrary.clickObject(vehicleDetailsVehicleTypeElement,"",Desc);
                */
                //Clicking Year of vehicle
                Desc="Clicking year of vechicle";
                FunctionLibrary.clickObject(vehicleYearList,"",Desc);
                //we can select drop down value for year
                Desc="Entering year of vehicle";
                FunctionLibrary.setText(vehicleDetailsVehicleYearTxtBox,year,Desc);
                //Click on Vechicle Make
               // Desc="Click on vehicle make";
                //FunctionLibrary.clickObject(vehicleDetailsVehicleMakeElement,"",Desc);
                //Enter Vehcile Make
                Desc="Entering vehicle make";
                FunctionLibrary.setText(vehicleDetailsVehicleMakeTxtBox,make,Desc);
                //Clicking vehicle model
              //  Desc="Clicking vehicle model";
               // FunctionLibrary.clickObject(vehicleDetailsVehicleModelElement,"",Desc);
                //Enter vehcile modle
                Desc="Enter vehcile modle";
                FunctionLibrary.setText(vehicleDetailsVehicleModelTxtBox,model,Desc);
                //Clicking vehicle save buttton
                Desc="Clicking vehicle save buttton";
                FunctionLibrary.clickObject(vehicleSaveList,"",Desc);
                ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Vechicle is added",LogStatus.INFO,false);
                Thread.sleep(3000);
               System.out.print("Saved Successfully");

               FunctionLibrary.clickObject("xpath=//a[contains(text(),'Vehicle History')]","","Click Vehicles history");
                String xpathOfVechiclesTable = "xpath=.//*[@summary=' Account Vehicle History List Applet']";
               int rowNumberWithVehicleNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfVechiclesTable,plateNumber[0],"get the row number");
                System.out.println(rowNumberWithVehicleNumber);
                FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,1,"ADD","Verifying status");
                FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,2,"0","Verifying version");
               // FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,3,"VK72538752","Verifying cell");
                FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,4, plateNumber[0],"Verifying plate number");
                FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,5,plateState,"Verifying plate state");
                FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,6,plateCountry,"Verifying plate country");
                FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,12,year,"Verifying plate year");
                FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,13,make,"Verifying plate make");
                FunctionLibrary.verifyWebTableCellData(xpathOfVechiclesTable,rowNumberWithVehicleNumber,14,model,"Verifying plate model");
                System.out.println("hello");
                //Wait till Vehicles Details Getting Saved
                //Calling Webdriver Wait function
                //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 15, sectionTwoRowCounter);
               // FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,"Verify row is added or not for vechiles");
            }
        }
        else
        {

            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Expected account name: " + newAccountName+
                    ". <br> Actual account  name:"+ newAccountName,LogStatus.FAIL,true);

        }


    }



    public static void tcAccountMaintenanceACMO181(HashMap<String, String> dataObj) throws InterruptedException {


        String executionStatus = dataObj.get("ExecutionStatus");
        String newAccountName = "";
        String oldAccountName="";

        String vehiclesNewButtonPlate ="xpath=//button[@id='s_2_1_16_0_Ctrl']";
        String vehiclesCountyPlate="xpath=//*[@id='1_Plate_Country']";
        String vehiclePlateCountryList="xpath=(//*[contains(@aria-labelledby, '_Plate_Country s_') and contains(@id,'_Plate_Country')])[1]";
        String vehicleYearList="xpath=(//*[contains(@aria-labelledby, '_Year_of_Vehicle s') and contains(@id,'_Year_of_Vehicle')])[1]";
        String vehicleSaveList="xpath=.//*[@title='Vehicle List:Save']";

        if (executionStatus.equalsIgnoreCase("Yes")) {

            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "<b>" + dataObj.get("TestCaseId")
                    + "</b>" + ": Test Case Execution is started....................... <br>"
                    + "Test case description: " + dataObj.get("TestCaseDesc"), LogStatus.INFO, false);

            CommonLibrary.loginSiebelApp(dataObj);
            CommonLibrary.serachForAccount(dataObj.get("AccountNo"));

            Thread.sleep(8000);
            FunctionLibrary.ObjDriver.findElement(By.linkText(dataObj.get("AccountNo"))).click();
            String Desc="";
            //HashMap<String,String> eachTestCaseData =new HashMap();

            Desc="Clicking vehicles tab";
            FunctionLibrary.clickObject(vehiclesTab,"",Desc);

            String vehiclesToBeAdded = dataObj.get("VehiclesInfo");
            vehiclesToBeAdded = vehiclesToBeAdded.replace("\n","");

            String []vehiclesItems = vehiclesToBeAdded.split(",");
            String [] eachVehicleInfo;
            System.out.println(vehiclesItems.length);
            String []plateNumber=new String[vehiclesItems.length];
            String plateState;
            String plateType;
            String plateCountry;
            String vehicleType;
            String year;
            String make;
            String model;

            for (int j =0;j<=vehiclesItems.length-1;j++) {
                eachVehicleInfo = vehiclesItems[j].split(":");
                //plateNumber[j] = eachVehicleInfo[0]+ DateTime.now().getMillisOfSecond();
                plateNumber[j] = "VK" + DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
                plateState = eachVehicleInfo[1];
                plateType = eachVehicleInfo[2];
                plateCountry = eachVehicleInfo[3];

                System.out.print("country:+++++++++++++++++++"+plateCountry);
                vehicleType = eachVehicleInfo[4];
                year = eachVehicleInfo[5];
                make = eachVehicleInfo[6];
                model = eachVehicleInfo[7];
                Thread.sleep(3000);
                //Clicks on vehicles new button
                Desc="Clicking vehicles new button";
                FunctionLibrary.clickObject(vehiclesNewButtonPlate,"",Desc);
                //Enter PLate Number
                Desc="Entering plate number";
                FunctionLibrary.setText(vehiclesDetailsPlateNumberTxtBox,plateNumber[j],Desc);
                //Clicks on plate state field
                Desc="Clicking plate state field";
                FunctionLibrary.clickObject(vehicleDetailsPlateStateElement,"",Desc);
                //Enter PLate Number
                Desc="Entering plate number";
                FunctionLibrary.setText(vehicleDetailsPlateStateTxtBox,plateState,Desc);
                //Clicks plate type
                Desc="Clicking plate type field";
                // FunctionLibrary.clickObject(vehicleDetailsPlateTypeElement,"",Desc);
                //Enter Plate Type
                Desc="Enter plate type";
                FunctionLibrary.setText(vehicleDetailsPlateTypeTxtBox,plateType,Desc);
                //Enter vehicle type
                Desc="Enter vehicle type";
                FunctionLibrary.setText(vehicleDetailsVehicleTypeTxtBox,vehicleType,Desc);


                //Clicking plate country
                Desc="Clicking plate country";
                FunctionLibrary.clickObject(vehiclePlateCountryList,"",Desc);
                //Enter plate country name
                Desc="Enter plate country name";
                FunctionLibrary.setText(vehiclesCountyPlate,plateCountry,Desc);
                /*//Clicking vehicle type
                Desc="Clicking vehicle type";
                FunctionLibrary.clickObject(vehicleDetailsVehicleTypeElement,"",Desc);
                */
                //Clicking Year of vehicle
                Desc="Clicking year of vechicle";
                FunctionLibrary.clickObject(vehicleYearList,"",Desc);
                //we can select drop down value for year
                Desc="Entering year of vehicle";
                FunctionLibrary.setText(vehicleDetailsVehicleYearTxtBox,year,Desc);
                //Click on Vechicle Make
                // Desc="Click on vehicle make";
                //FunctionLibrary.clickObject(vehicleDetailsVehicleMakeElement,"",Desc);
                //Enter Vehcile Make
                Desc="Entering vehicle make";
                FunctionLibrary.setText(vehicleDetailsVehicleMakeTxtBox,make,Desc);
                //Clicking vehicle model
                //  Desc="Clicking vehicle model";
                // FunctionLibrary.clickObject(vehicleDetailsVehicleModelElement,"",Desc);
                //Enter vehcile modle
                Desc="Enter vehcile modle";
                FunctionLibrary.setText(vehicleDetailsVehicleModelTxtBox,model,Desc);
                //Clicking vehicle save buttton
                Desc="Clicking vehicle save buttton";
                FunctionLibrary.clickObject(vehicleSaveList,"",Desc);
                Thread.sleep(3000);
                System.out.print("Saved Successfully");


                //Wait till Vehicles Details Getting Saved
                //Calling Webdriver Wait function
                //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 15, sectionTwoRowCounter);
                 FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,"Verify row is added or not for vechiles");
            }
        }
        else
        {

            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Expected account name: " + newAccountName+
                    ". <br> Actual account  name:"+ newAccountName,LogStatus.FAIL,true);

        }


    }

    public static void tcAccountMaintenanceACMO004(HashMap<String, String> dataObj) throws InterruptedException {


        String executionStatus = dataObj.get("ExecutionStatus");
        String newAccountName = "";
        String oldAccountName="";

        String vehiclesNewButtonPlate ="xpath=//button[@id='s_2_1_16_0_Ctrl']";
        String vehiclesCountyPlate="xpath=//*[@id='1_Plate_Country']";
        String vehiclePlateCountryList="xpath=(//*[contains(@aria-labelledby, '_Plate_Country s_') and contains(@id,'_Plate_Country')])[1]";
        String vehicleYearList="xpath=(//*[contains(@aria-labelledby, '_Year_of_Vehicle s') and contains(@id,'_Year_of_Vehicle')])[1]";
        String vehicleSaveList="xpath=.//*[@title='Vehicle List:Save']";

        if (executionStatus.equalsIgnoreCase("Yes")) {

            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "<b>" + dataObj.get("TestCaseId")
                    + "</b>" + ": Test Case Execution is started....................... <br>"
                    + "Test case description: " + dataObj.get("TestCaseDesc"), LogStatus.INFO, false);

            CommonLibrary.loginSiebelApp(dataObj);
            CommonLibrary.serachForAccount(dataObj.get("AccountNo"));

            Thread.sleep(8000);
            FunctionLibrary.ObjDriver.findElement(By.linkText(dataObj.get("AccountNo"))).click();
            String Desc="";
            //HashMap<String,String> eachTestCaseData =new HashMap();

            Desc="Clicking vehicles tab";
            FunctionLibrary.clickObject(vehiclesTab,"",Desc);

            String vehiclesToBeAdded = dataObj.get("VehiclesInfo");
            vehiclesToBeAdded = vehiclesToBeAdded.replace("\n","");

            String []vehiclesItems = vehiclesToBeAdded.split(",");
            String [] eachVehicleInfo;
            System.out.println(vehiclesItems.length);
            String []plateNumber=new String[vehiclesItems.length];
            String plateState;
            String plateType;
            String plateCountry;
            String vehicleType;
            String year;
            String make;
            String model;

            for (int j =0;j<=vehiclesItems.length-1;j++) {
                eachVehicleInfo = vehiclesItems[j].split(":");
                //plateNumber[j] = eachVehicleInfo[0]+ DateTime.now().getMillisOfSecond();
                plateNumber[j] = "VK" + DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
                plateState = eachVehicleInfo[1];
                plateType = eachVehicleInfo[2];
                plateCountry = eachVehicleInfo[3];

                System.out.print("country:+++++++++++++++++++"+plateCountry);
                vehicleType = eachVehicleInfo[4];
                year = eachVehicleInfo[5];
                make = eachVehicleInfo[6];
                model = eachVehicleInfo[7];
                Thread.sleep(3000);
                //Clicks on vehicles new button
                Desc="Clicking vehicles new button";
                FunctionLibrary.clickObject(vehiclesNewButtonPlate,"",Desc);
                //Enter PLate Number
                Desc="Entering plate number";
                FunctionLibrary.setText(vehiclesDetailsPlateNumberTxtBox,plateNumber[j],Desc);
                //Clicks on plate state field
                Desc="Clicking plate state field";
                FunctionLibrary.clickObject(vehicleDetailsPlateStateElement,"",Desc);
                //Enter PLate Number
                Desc="Entering plate number";
                FunctionLibrary.setText(vehicleDetailsPlateStateTxtBox,plateState,Desc);
                //Clicks plate type
                Desc="Clicking plate type field";
                // FunctionLibrary.clickObject(vehicleDetailsPlateTypeElement,"",Desc);
                //Enter Plate Type
                Desc="Enter plate type";
                FunctionLibrary.setText(vehicleDetailsPlateTypeTxtBox,plateType,Desc);
                //Enter vehicle type
                Desc="Enter vehicle type";
                FunctionLibrary.setText(vehicleDetailsVehicleTypeTxtBox,vehicleType,Desc);


                //Clicking plate country
                Desc="Clicking plate country";
                FunctionLibrary.clickObject(vehiclePlateCountryList,"",Desc);
                //Enter plate country name
                Desc="Enter plate country name";
                FunctionLibrary.setText(vehiclesCountyPlate,plateCountry,Desc);
                /*//Clicking vehicle type
                Desc="Clicking vehicle type";
                FunctionLibrary.clickObject(vehicleDetailsVehicleTypeElement,"",Desc);
                */
                //Clicking Year of vehicle
                Desc="Clicking year of vechicle";
                FunctionLibrary.clickObject(vehicleYearList,"",Desc);
                //we can select drop down value for year
                Desc="Entering year of vehicle";
                FunctionLibrary.setText(vehicleDetailsVehicleYearTxtBox,year,Desc);
                //Click on Vechicle Make
                // Desc="Click on vehicle make";
                //FunctionLibrary.clickObject(vehicleDetailsVehicleMakeElement,"",Desc);
                //Enter Vehcile Make
                Desc="Entering vehicle make";
                FunctionLibrary.setText(vehicleDetailsVehicleMakeTxtBox,make,Desc);
                //Clicking vehicle model
                //  Desc="Clicking vehicle model";
               // FunctionLibrary.clickObject(vehicleDetailsVehicleModelElement,"",Desc);
                //Enter vehcile modle
                Desc="Enter vehcile modle";
                FunctionLibrary.setText(vehicleDetailsVehicleModelTxtBox,model,Desc);
                //Clicking vehicle save buttton
                Desc="Clicking vehicle save buttton";
                FunctionLibrary.clickObject(vehicleSaveList,"",Desc);
                Thread.sleep(4000);
                System.out.print("Saved Successfully");


                //Wait till Vehicles Details Getting Saved
                //Calling Webdriver Wait function
                //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 15, sectionTwoRowCounter);
                // FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,"Verify row is added or not for vechiles");
            }
        }
        else
        {

            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Expected account name: " + newAccountName+
                    ". <br> Actual account  name:"+ newAccountName,LogStatus.FAIL,true);

        }


    }

}

