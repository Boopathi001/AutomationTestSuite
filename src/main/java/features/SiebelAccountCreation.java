package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import org.joda.time.DateTime;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.HashMap;

import static objectProperties.SblCreateAccountPageProperties.*;

public  class SiebelAccountCreation
{
                                
   //FunctionLibrary Flib=new FunctionLibrary();
               // ExcelSheet exl=new ExcelSheet();
  /**
   * The {@code Logger} to use in cooperation with all test instances.
* @return 
 * @throws Exception 
   */
  //private static final Logger LOG = LoggerFactory.getLogger(SiebelAccountCreation.class);
   public static WebDriver browser= FunctionLibrary.ObjDriver;
    public static String previousTestCaseBrowser;
  @SuppressWarnings("unchecked")
public static void SiebelAccountCreation() throws IOException, Exception {


      //Read input excel sheet for test data
      ExcelSheet exl=new ExcelSheet();

      int counter=0;
      String LoginMessage="NotSuccess";
      //int Row=0;
      String Desc="";

      //Map variable which hold the once test case data
      HashMap<String,String> eachTestCaseData =new HashMap();

      //no of rows in the excel sheet(no of test cases)
      int noOfTestCases=exl.totalrows("TestData_siebel","siebel");

      String applicationUrl;

     //    Row=exl.totalrows("TestData_siebel","siebel");
      String Execution_Status;
      String AccountType;
      String ModeType;
      String PaymentMode;
      String currentTcBrowser;

          for(int i=1;i<=noOfTestCases;i++)
         {
        	  eachTestCaseData= CommonLibrary.getEachTestCaseData(exl,"siebel",i);



             Execution_Status=eachTestCaseData.get("Execution Status");
             AccountType =eachTestCaseData.get("Account Type");
             ModeType =eachTestCaseData.get("Mode Type");
             PaymentMode =eachTestCaseData.get("Rebill Pay Type");
             currentTcBrowser =eachTestCaseData.get("Browser Type");
              applicationUrl = CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString();
              
        	  //Execute Test case only the status mentioned Yes
                if(Execution_Status.equalsIgnoreCase("Yes"))
                   {
                       ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"<b>"+eachTestCaseData.get("TestCaseId")+"</b>"+": Test Case Execution is started....................... <br>"
                               + "Test case description: " + eachTestCaseData.get("TestDesc"), LogStatus.INFO, false);

                       //Counter variable is to make sure if browser opened or not
             	      counter=+1;
                                         
                      try{
                    	  try{
                      		//Launching Browser
                      		FunctionLibrary.launchBrowser(applicationUrl.toString(), "Launching siebel app",currentTcBrowser);
                              FunctionLibrary.Wait_For_Object = new WebDriverWait(FunctionLibrary.ObjDriver,60);
                              FunctionLibrary.Wait_For_Object.until(ExpectedConditions.visibilityOfElementLocated(By.id("s_swepi_1")));
                             }catch(Exception e){System.out.println("Problem in Home Page");}
                    	 // ReportLibrary.Add_Step(ModeType, "**********Started Executing**********", LogStatus.PASS, false);
                    	  ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Creating account of -- Type: "+AccountType+", Payment mode: "+PaymentMode+", Logon mode:"+ModeType, LogStatus.INFO, false);

                           // Thread.sleep(10000);
                           // WebDriverWait wait = new WebDriverWait(browser,20);
                           // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginUsernameTxtBox)));
                          //FunctionLibrary.setText(loginUsernameTxtBox,exl.readexcel("siebel", i, 4), Desc);
                          //Enter User Name
                    	  Desc="Entering UserName on UserName textbox";
                          FunctionLibrary.setText(loginUsernameTxtBox,eachTestCaseData.get("UserId"), Desc);
                          //Enter Password
                          Desc="Entering Password into password field";
                          FunctionLibrary.setText(loginPasswordTxtBox,eachTestCaseData.get("Password"), Desc);
                          //Click on Sign-in Button
                          Desc="Clicking on Sign in Button";
                          FunctionLibrary.clickObject(signInBtn, "", "Click sign in button");
                    	    //FunctionLibrary.setText(loginUsernameTxtBox,"Boopathi", "Entering username");
                            //FunctionLibrary.setText(loginPasswordTxtBox,"", "Entering password");
                          //FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                             WebDriverWait wait = new WebDriverWait(FunctionLibrary.ObjDriver,90);
                             wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//div[@class='Welcome'])[1]")));

                            //  Thread.sleep(3000);
                            //if(LoginMessage != null)
                            Desc="Verify Account Opening element";
                            if(FunctionLibrary.verifyWebElementExist(homePageVerificationTxt,Desc))
                            {
                            	LoginMessage="Success";
                            	//Type of Account creation adding to Report
                            	//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Creating :"+""+AccountType+""+" Account For Sieble CRM"+"By Using ["+PaymentMode+" And "+ModeType+"]", LogStatus.PASS, true);
                             //******************Changing Mode******************
                                //Function call for Change Logon mode
                            	//changeLogonMode(ModeType); //Calling ChangeLogonMode Function

                             //****************code for commercial/Business account ***************************
                             if(AccountType.equalsIgnoreCase("Business")|| AccountType.equalsIgnoreCase("Commercial"))
                             {
                               FunctionLibrary.clickObject(siteMapImage, "", "Click on SiteMap image");
                               FunctionLibrary.clickObject(administrativeUserLink, "", "Click on AdministratorUser link");
                              if( FunctionLibrary.verifyWebElementExist(employeeLink, "Verify Employee Link is displayed")){
                                 FunctionLibrary.clickObject(employeeLink, "", "Click on Employee link");
                              }
                               FunctionLibrary.clickObject(employeeSearchComboBoxIcon, "", "Click on comboboxIcon");
                               FunctionLibrary.clickObject(selectLoginName, "", "select Login Name");
                               //Set User Name on the Login Text Box
                               FunctionLibrary.setText(loginnameTextBox,eachTestCaseData.get("UserId"), "Enter Login Name");
                               FunctionLibrary.clickObject(selectGo, "", "Click on GO");
                               //Thread.sleep(2000);
                                //****Radio Button selection for Commercial flow
                                 if(AccountType.equalsIgnoreCase("Business") && FunctionLibrary.Verify_WebElement_Enabled(commercialAccountSelect," ", "Checking for enabled or disabled")){
                                  FunctionLibrary.clickObject(commercialAccountSelect, "", "Click on commercial Account Radiobutton for make it enabled");
                                 }
                                //*****Radio Button selection for Business flow
                                 if(AccountType.equalsIgnoreCase("Commercial") &&!FunctionLibrary.Verify_WebElement_Enabled(commercialAccountSelect," ", "Checking for enabled or disabled")){
                                  FunctionLibrary.clickObject(commercialAccountSelect, "", "Click on commercial Account Radiobutton for make it enabled");
                                 }

                              FunctionLibrary.clickObject(clickOnSaveCommercialAccount, "", "Click on Save");
                              FunctionLibrary.clickObject(accountOpeningBtn,"","Clicking Account opening Button");
                              FunctionLibrary.clickObject(clickOnBusinessCommercialAccount, "", "Click on Business/Commercial Account");
                              FunctionLibrary.clickObject(clickOnNewButton, "", "Click on new Button for Business/Commercial");
                              //Thread.sleep(2000);
                             }
                           //---------------------------------------------------------------------------------------------------


                             if(AccountType.equalsIgnoreCase("private")){
                              //********Clicking on Account opening tab*************
                            	 try{

                  	                 FunctionLibrary.clickObject(accountOpeningBtn,"","Clicking on Account opening button");
                                     FunctionLibrary.clickObject(newAccountOpeningBtn,"","Clicking new button");


                                    }catch(Exception e){System.out.println("Problem in Home Page");}

                             }

                             //Handling pop up code
                            // FunctionLibrary.clickObject("xpath=.//*[@id='btn-accept']","","Clicking Ok buttn");

                             //**************Fields will be applicable only for Business/Commercial************

                             if(AccountType.equalsIgnoreCase("Business")|| AccountType.equalsIgnoreCase("Commercial"))
                             {
                             //Fields applicable for Business or Commercial only
                             //Wait till accountNameTextBox button to be visible
                                 // //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver,5,accountNameTextBox);

                                // WebDriverWait wait = new WebDriverWait(FunctionLibrary.ObjDriver,90);
                                 Thread.sleep(8000);
                                 wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@aria-label='Account Name']")));
                             //FunctionLibrary.setText(accountNameTextBox,eachTestCaseData.get("Account Name"),"Enter Account name");
                                 String accountName = eachTestCaseData.get("Account Name");
                                 FunctionLibrary.setText(accountNameTextBox,accountName ,"Enter Account name");
                                 ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Account  Name: " + accountName,LogStatus.INFO,false);
                             FunctionLibrary.setText(dbaNameTextBox,eachTestCaseData.get("DBA Name"),"Enter DBA name");
                             //String ExternalResource=exl.readexcel("siebel", i, 42);
                             //String ExternalResource1=ExternalResource.substring(0, ExternalResource.length()-2);
                            // System.out.println(ExternalResource);
                             FunctionLibrary.setText(externalTextBox,eachTestCaseData.get("External Reference"),"Enter External reference number");
                             //String Fein=exl.readexcel("siebel", i, 41);
                             //String Fein1=Fein.substring(0, Fein.length()-2);
                             //System.out.println(ExternalResource);
                             FunctionLibrary.setText(FEINTextBox,eachTestCaseData.get("FEIN"),"Enter Fien number");
                             }
                             //*********************Fields will be applicable for common to Business/Commercial/private**************************
                             //entering agency
                             //System.out.println(exl.readexcel("siebel", i, 6));
                             //clicking Security question drop down icon
                             Desc="Clicking Security question drop down";
                             FunctionLibrary.clickObject(clicksecurityQuestionDropdown,"","Clicking Security question drop down");
                             //Selecting the security question
                             //System.out.println(exl.readexcel("siebel", i, 8));
                             //Provide the input value instead of "Security Question"
                             Desc="Selecting Security question from drop down";
                             FunctionLibrary.clickObject("xpath=(//*[contains(text(),'"+eachTestCaseData.get("Challenge Question")+"')])[1]","",Desc);
                             //entering security answer
                             //String SecurityAnswer=exl.readexcel("siebel", i, 9);
                             //String SecurityAnswer1=SecurityAnswer.substring(0, SecurityAnswer.length()-2);
                             Desc="Enter Security Answer";
                             FunctionLibrary.setText(securityAnswerTxtBox,eachTestCaseData.get("Challenge Answer"),Desc);
                             //entering email id
                             Desc="Enter email on Email field";
                             FunctionLibrary.setText(accountEmailIDTxtBox,eachTestCaseData.get("Email Address"),Desc);
                             //Handling pop up code after entering email
                             Desc="Clicking Ok buttn on Alert";
                             FunctionLibrary.clickObject("xpath=.//*[@id='btn-accept']","",Desc);
                             //FunctionLibrary.clickObject("xpath=//span[@class='siebui-icon-dropdown]", "", "click on plan type icon");
                             //entering statement type
                             Desc="Enter Statement type";
                             FunctionLibrary.setText(statementTypeTxtBox,eachTestCaseData.get("Statement Frequency"),Desc);
                             //entering pin
                             //String pin=exl.readexcel("siebel", i, 7);
                             //String pin1=pin.substring(0, pin.length()-2);
                             //System.out.println(pin1);
                             Desc="Enter Pin";
                             FunctionLibrary.setText(accountPINTxtBox, eachTestCaseData.get("PIN"),Desc);
                            //entering preferred language
                             Desc="Select language preference";
                             FunctionLibrary.setText(preferredLanguageTxtBox,eachTestCaseData.get("LanguagePref"),Desc);
                            //save the personal details for Private account
                             if(AccountType.equalsIgnoreCase("private")){
                            	 Desc="Click on Save Button";
                             FunctionLibrary.clickObject(accountDetailsSaveBtn,"",Desc);
                             }
                             //save the personal details for Business or Commercial account
                             if(AccountType.equalsIgnoreCase("Business")|| AccountType.equalsIgnoreCase("Commercial")){
                            	 Desc="Click on Save Button";
                            	 Thread.sleep(5000);
                             	FunctionLibrary.clickObject(businessOrCommAcoDetailsSaveBtn,"",Desc);

                             }

                                Thread.sleep(4000);
                             //Wait till Contact New button is clickable
                            // //FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.ObjDriver, 5, contactDetailsNewBtn);
                             //clicking the New button in contacts section
                             Desc="Clicks Contact new button";
                             FunctionLibrary.clickObject(contactDetailsNewBtn,"",Desc);

                             //Clicks Last Name Field
                             Desc="Clicks Last Name Field";
                             FunctionLibrary.clickObject(contactDetailsLstNameElement,"",Desc);
                             //Enter Last Name
                             Desc="Enter Last Name";
                             String lname = eachTestCaseData.get("Last Name");
                             FunctionLibrary.setText(contactDetailsLstNameTxtBox,lname,Desc);

                             //Clicks on first Name Field
                             Desc="Clicks on First Name Field";
                             FunctionLibrary.clickObject(contactDetailsFrstNameElement,"",Desc);
                             String fname = eachTestCaseData.get("First Name");
                             //Enter First Name
                                //eachTestCaseData.get("First Name"): instead of value from excel, we are using method to generate a dynamic name
                             Desc="Entering first name: " + fname;
                             FunctionLibrary.setText(contactDetailsFrstNameTxtBox,fname,Desc);
                                ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Customer fname: "+ " and lname: "+lname,LogStatus.INFO,false);
                             //clicks on  phone number field
                             Desc="Clicks on phone number filed";
                             FunctionLibrary.clickObject(contactDetailsPhnNoElement,"",Desc);
                             //String cellphoneno=exl.readexcel("siebel", i, 15);
                             //String cellphoneno1=cellphoneno.substring(0, cellphoneno.length()-2);
                             //System.out.println(cellphoneno1);
                             //Enter Phone number
                             Desc="Enter phone number";
                             FunctionLibrary.setText(contactDetailsPhnNoTxtBox,"8880739228",Desc);

                             //handle the phone number format pop-up alert
                             try {
                                          //FunctionLibrary.clickObject("xpath=./[@id='btn-accept']","","Clicking Ok buttn");
                              WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.ObjDriver,15);
                               wait2.until(ExpectedConditions.alertIsPresent());
                               Alert alert = FunctionLibrary.ObjDriver.switchTo().alert();
                               System.out.println(alert.getText());
                               alert.accept();
                                       /*    Robot robot = new Robot();
                                           robot.delay(250);
                                           robot.keyPress(KeyEvent.VK_ENTER);
                                           robot.keyRelease(KeyEvent.VK_ENTER);
                                           //Click on Contact Details Save button
                                 Thread.sleep(4000);*/
                                           Desc="Click on Contact Detail Save button";
                                           Thread.sleep(4000);
                                           FunctionLibrary.clickObject(contactDetailsSaveBtn,"",Desc);

                             } catch (Exception e) {
                               //exception handling
                             }
                               /* //handle the phone number format pop-up alert
                                try {
                                    WebDriverWait wait1 = new WebDriverWait(browser,30);
                                    wait1.until(ExpectedConditions.alertIsPresent());
                                    Alert alert = FunctionLibrary.ObjDriver.switchTo().alert();
                                    alert.accept();
                                } catch (Exception e) {
                                    //exception handling
                                }
*/
                                //Wait till Contact details row getting added
                             //Calling webdriver wait function
                             //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 5, sectionTwoRowCounter);
                             //Verify Row added
                             Desc="Verify row is added or not for contacts";
                             ///////////////////FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,Desc);
                             //Javascript Executor method
/*
                             JavascriptExecutor js = (JavascriptExecutor)FunctionLibrary.ObjDriver;
                             js.executeScript("return document.readyState").toString().equals("complete");
*/                          Thread.sleep(5000);

                                //FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                             //Wait till Address Details getting Loaded
                             //Calling webdriver wait function
                             //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 10, addressDetailsNewBtn);
                             // click new button for Address section, enter required fields and then click save button
                             Desc="Click Address new button";

                                String addressesToBeAdded = eachTestCaseData.get("AddressType");
                                addressesToBeAdded = addressesToBeAdded.replace("\n","");

                                String []addressItems = addressesToBeAdded.split(",");
                                String [] eachAddressInfo;
                                System.out.println(addressItems.length);
                                String addressType;
                                String addressLine1;
                                String addressLine2;
                                String zipCode;
                                String zipPlus4;
                                String city;
                                String state;
                                String country;

                                for (int j =0;j<=addressItems.length-1;j++)
                                {
                                    eachAddressInfo=addressItems[j].split(":");
                                    addressType = eachAddressInfo[0];
                                    addressLine1 = eachAddressInfo[1];
                                    addressLine2 = eachAddressInfo[2];
                                    zipCode = eachAddressInfo[3];
                                    city = eachAddressInfo[4];
                                    state = eachAddressInfo[5];
                                    country = eachAddressInfo[6];
                                    FunctionLibrary.clickObject(addressDetailsNewBtn,"",Desc);
                                    //Click on Address Type Drop down
                                    Desc="Clicking Address type drop down";
                                    FunctionLibrary.clickObject(addressDetailsAddressTypeBtn,"",Desc);
                                    //change the value of "AddressType"
                                    Desc="Select Address Type";
                                    FunctionLibrary.clickObject("xpath=(//*[contains(text(),'"+addressType+"')])[1]","",Desc);
                                    //Click on
                                    Desc="Clicking street address1";
                                    FunctionLibrary.clickObject(addressDetailsAddress1Element,"",Desc);
                                    Desc="Entering street address1";
                                    FunctionLibrary.setText(addressDetailsAddress1TxtBox,addressLine1,Desc);

                                    //Entering City
                                    //FunctionLibrary.clickObject(addressDetailsCityElement,"","Clicking city field");
                                    //FunctionLibrary.setText(addressDetailsCityTxtBox,exl.readexcel("siebel", i, 19),"Enter city");
                                    //Click on Zip Code
                                    Desc="Click on postal/Zip code filed";
                                    FunctionLibrary.clickObject(addressDetailsPostalCodeELement,"",Desc);
                                    //Click on Zip Code
                                    Desc="Click on postal/Zip code filed";
                                    FunctionLibrary.setText(addressDetailsPostalCodeTxtBox,zipCode,"Entering postal code");

                                    //Entering State
                                    //FunctionLibrary.clickObject(addressDetailsStateElement,"","Clicking state field");
                                    //FunctionLibrary.setText(addressDetailsStateTxtBox,exl.readexcel("siebel", i, 20),"Entering state value");

                                    // FunctionLibrary.clickObject(addressDetailsCountryElement,"","Clicking country field");
                                    //FunctionLibrary.setText(addressDetailsCountryTxtBox,exl.readexcel("siebel", i, 21),"Entering country field");
                                    //Click on Address Save button
                                    Desc="Clicking address save button";
                                    FunctionLibrary.clickObject(addressDetailsSaveBtn,"",Desc);
                                    Thread.sleep(5000);
                                }

                             //Wait till Address Details getting addded
                             //Calling Webdriver wait functioin
                             //FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.ObjDriver, 3, sectionThreeRowCounter);
                             //FunctionLibrary.verifyWebElementExist(sectionThreeRowCounter,"Verify row is added or not for address");
                             
                             FunctionLibrary.clickObject(replenishmentTab,"","Clicking Replenishment button");
                             //Wait till replenishmentTab page getting Loaded
                             //Calling Webdriver wait function
                             //FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.ObjDriver, 5, replenishmentDetailsNewBtn);
                             //Click on Replenishment New button
                             Desc="Clicking Replenishment new button";
                             FunctionLibrary.clickObject(replenishmentDetailsNewBtn,"",Desc);
                             //Check Primary checkbox
                             Desc="Select isPrimary checkbox";
                             FunctionLibrary.setCheckBox(replenushmentDetailsPrimaryChkBox,eachTestCaseData.get("Is Primary ?"),Desc);
                             //Select Rebill payment Type from Drop down
                             Desc="Select list box Payment type";
                             FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,eachTestCaseData.get("Rebill Pay Type"),Desc);
                              //Rebill pay type
                             if(eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("SAVINGS") || eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("CHECKING"))
                             {
                                //Enter Bank Account no:
                                //FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,exl.readexcel("siebel", i, 36),"Enter bank number");
                            	 Desc="Enter bank number";
                                FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,eachTestCaseData.get("BankAccount"),Desc);
                                // FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,exl.readexcel("siebel", i, 37),"Enter routing number");
                                Desc="Enter routing number";
                                FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,eachTestCaseData.get("BankRoutine"),Desc);
                             }
                             //Click on Replenishment Save button
                             Desc="Clicks on Replenishment save button";
                             FunctionLibrary.clickObject(replenishmentDetailsSaveBtn,"",Desc);
                             //Wait till Replenishment Details getting added
                             //Calling Webdriver wait function
                             //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 10, sectionTwoRowCounter);
                             Desc="Verify row is added or not for replenishment";
                             FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,Desc);
                             //Clicks on Vehicles tab
                             Desc="Clicking vehicles tab";
                             FunctionLibrary.clickObject(vehiclesTab,"",Desc);

                                String vehiclesToBeAdded = eachTestCaseData.get("VehiclesInfo");
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
                                    plateNumber[j] = "VK" +DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
                                    plateState = eachVehicleInfo[1];
                                    plateType = eachVehicleInfo[2];
                                    plateCountry = eachVehicleInfo[3];
                                    vehicleType = eachVehicleInfo[4];
                                    year = eachVehicleInfo[5];
                                    make = eachVehicleInfo[6];
                                    model = eachVehicleInfo[7];

                                    //Clicks on vehicles new button
                                    Desc="Clicking vehicles new button";
                                    FunctionLibrary.clickObject(vehiclesDetailsNewBtn,"",Desc);
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
                                    FunctionLibrary.clickObject(vehicleDetailsPlateTypeElement,"",Desc);
                                    //Enter Plate Type
                                    Desc="Enter plate type";
                                    FunctionLibrary.setText(vehicleDetailsPlateTypeTxtBox,plateType,Desc);
                                    //Clicking plate country
                                    Desc="Clicking plate country";
                                    FunctionLibrary.clickObject(vehicleDetailsPlateCountryElement,"",Desc);
                                    //Enter plate country name
                                    Desc="Enter plate country name";
                                    FunctionLibrary.setText(vehicleDetailsPlateCountryTxtBox,plateCountry,Desc);
                                    //Clicking vehicle type
                                    Desc="Clicking vehicle type";
                                    FunctionLibrary.clickObject(vehicleDetailsVehicleTypeElement,"",Desc);
                                    //Enter vehicle type
                                    Desc="Enter vehicle type";
                                    FunctionLibrary.setText(vehicleDetailsVehicleTypeTxtBox,vehicleType,Desc);
                                    //Clicking Year of vehicle
                                    Desc="Clicking year of vechicle";
                                    FunctionLibrary.clickObject(vehicleDetailsVehicleYearElement,"",Desc);
                                    //we can select drop down value for year
                                    Desc="Entering year of vehicle";
                                    FunctionLibrary.setText(vehicleDetailsVehicleYearTxtBox,year,Desc);
                                    //Click on Vechicle Make
                                    Desc="Click on vehicle make";
                                    FunctionLibrary.clickObject(vehicleDetailsVehicleMakeElement,"",Desc);
                                    //Enter Vehcile Make
                                    Desc="Entering vehicle make";
                                    FunctionLibrary.setText(vehicleDetailsVehicleMakeTxtBox,make,Desc);
                                    //Clicking vehicle model
                                    Desc="Clicking vehicle model";
                                    FunctionLibrary.clickObject(vehicleDetailsVehicleModelElement,"",Desc);
                                    //Enter vehcile modle
                                    Desc="Enter vehcile modle";
                                    FunctionLibrary.setText(vehicleDetailsVehicleModelTxtBox,model,Desc);
                                    //Clicking vehicle save buttton
                                    Desc="Clicking vehicle save buttton";
                                    FunctionLibrary.clickObject(vehicleDetailsSaveBtn,"",Desc);
                                    Thread.sleep(3000);

                                    //Wait till Vehicles Details Getting Saved
                                    //Calling Webdriver Wait function
                                    //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 15, sectionTwoRowCounter);
                                    FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,"Verify row is added or not for vechiles");
                                }

                             //Clicking Device Request tab
                             Desc="Clicking Device Request tab";
                             FunctionLibrary.clickObject(deviceRequestTab,"",Desc);


                                String devicesToBeAdded = eachTestCaseData.get("DevicesInfo");
                                devicesToBeAdded = devicesToBeAdded.replace("\n","");

                                String [] devicesItems = devicesToBeAdded.split(",");
                                String [] eachDeviceInfo;
                                //System.out.println(addressItems.length);
                                String deviceDescription;
                                String devicesQuantity;

                                for (int j =0;j<=devicesItems.length-1;j++) {
                                    eachDeviceInfo = devicesItems[j].split(":");
                                    deviceDescription = eachDeviceInfo[0];
                                    devicesQuantity = eachDeviceInfo[1];

                                    //Clicking Device Requests new button
                                    Desc = "Clicking Device Requests new button";
                                    FunctionLibrary.clickObject(deviceDetailsNewBtn, "", Desc);
                                    //Calling Webdriver wait
                                    //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 5, deviceDetailsSmartIssueIdTxtBox);
                                    //FunctionLibrary.clickObject("xpath=.//*[@id='1_s_1_l_Vehicle_Model']","","Clicking vehicle model");
                                    //Enter smart issue id
                                    Desc = "Enter smart issue id";
                                    FunctionLibrary.setText(deviceDetailsSmartIssueIdTxtBox, deviceDescription, Desc);

                                    Desc = "Click the plate number field";
                                    FunctionLibrary.clickObject(deviceDetailsPlateNumberElement,"",Desc);

                                    Desc = "Enter the plate number in text box";
                                    FunctionLibrary.ObjDriver.findElement(By.xpath(".//*[@name='Plate_Number']")).sendKeys(plateNumber[j].toString());
                                   //FunctionLibrary.setText(deviceDetailsPlateNumberTxtBox,plateNumber[j],Desc);

                                    System.out.println(plateNumber[j]);
                                    //"Clicing Quantity field
                                    Desc = "Clicing Quantity field";
                                    FunctionLibrary.clickObject(deviceDetailsQuantityElement, "", Desc);
                                    //Entering quantity of tags
                                    Desc = "Entering quantity of tags";
                                    FunctionLibrary.setText(deviceDetailsQuantityTxtBox, devicesQuantity, Desc);
                                    //Clicking Device Requests Save button
                                    Desc = "Clicking Device Requests Save button";
                                    FunctionLibrary.clickObject(deviceDetailsSaveBtn, "", Desc);
                                    //Calling Webdriver Wait function to wait till Device details saved
                                    //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 40, sectionTwoRowCounter);
                                    //Verify row is added or not for device request
                                    Thread.sleep(3000);
                                }

                                Desc="Verify row is added or not for device request";
                             ////////////////FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,Desc);
                             //Click Plans tab
                             //Calling Webdriver Wait function to wait till plans tab visible
                             //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 5, plansTab);
                             //Clicking the Plans tab

                                Desc="Clicking the Plans tab";
                             FunctionLibrary.clickObject(plansTab,"",Desc);

                                String plansToBeAdded = eachTestCaseData.get("PlansInfo");
                                plansToBeAdded = plansToBeAdded.replace("\n","");
                                String [] plansItems = plansToBeAdded.split(",");
                                String [] eachPlanInfo;
                                System.out.println(plansItems.length);
                                String planName;
                                String existingPlan;
                                Thread.sleep(3000);
                                for (int j =0;j<=plansItems.length-1;j++) {
                                    eachPlanInfo = plansItems[j].split(":");
                                    planName = eachPlanInfo[0];

                                    existingPlan= FunctionLibrary.ObjDriver.findElement(By.xpath(planAlreadyExisted)).getAttribute("title");
                                    if(!existingPlan.equals(planName))
                                    {
                                        Desc = "Clicking new buttton";
                                        FunctionLibrary.clickObject(plansNewButton,"",Desc);
                                        Desc = "Clicking plantype drop down button";
                                        FunctionLibrary.clickObject(planTypeDropDownBtn,"", Desc);
                                        Desc = "Select paln name: " + planName;
                                        FunctionLibrary.clickObject("xpath=(//*[contains(text(),'"+planName+"')])[1]","",Desc);
                                        Thread.sleep(3000);
                                    }

                                }

                             //Click on Pay tab
                             Desc="Click on Pay tab";
                             FunctionLibrary.clickObject(plansTabPayBtn,"",Desc);
                             //Calling Webdriver Wait function to wait till plans Details New button visible
                             //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 5, paymentDetailsNewBtn);
                             //Clicking plans new button
                             Desc="Clicking plans new button";
                             FunctionLibrary.clickObject(paymentDetailsNewBtn,"",Desc);
                             Thread.sleep(3000);
                             //Clicking on Save Button[PaymentDetails Save button]
                             Desc="Clicks on Save Button[PaymentDetails Save button]";
                             FunctionLibrary.clickObject(paymentDetailsInfoSaveBtn,"",Desc);
                             //handle the "Do you want to updated the credit card details as primary payment method....." format pop-up alert
                             //todo: 2 popup: write their  information here

                             try {
                               WebDriverWait  wai= new WebDriverWait(FunctionLibrary.ObjDriver,10);
                               wai.until(ExpectedConditions.alertIsPresent());
                               Alert alert1 = FunctionLibrary.ObjDriver.switchTo().alert();
                               System.out.println("Popup is displayed with text" + alert1.getText());
                               alert1.accept();
                              } catch (Exception e){
                               //System.out.println("Account is not created - Test is failed");
                               }
                             //Clicking on Save Button[{ayment List Save button]
                             FunctionLibrary.clickObject(paymentListSaveBtn,"","Clicking Save button");
                             //Clickin on Process Pay button
                             FunctionLibrary.clickObject(processPayBtn,"","Clicking Process Pay button");
                            //Handling POp up for Savings Payment Type
                             if(replenishmentDetailsRebillPayTypeDropdown.equalsIgnoreCase("CASH")){
                             //Handling Pop up code
                            	try{ 
                              FunctionLibrary.clickObject("xpath=.//*[@id='btn-accept']","","Clicking Ok buttn");
                            	}catch(Exception e){}
                             }
                            
                             //handle the "Account created confirmation" pop-up alert
                             try {
                               WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.ObjDriver,10);
                               wait8.until(ExpectedConditions.alertIsPresent());
                               Alert alert = FunctionLibrary.ObjDriver.switchTo().alert();
                               System.out.println("Popup is displayed with text" + alert.getText());
                               alert.accept();
                               ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Creating :"+""+AccountType+""+" Account For Sieble CRM"+"By Using-->["+PaymentMode+" And "+ModeType+"]", LogStatus.PASS, true);
                              } catch (Exception e) {
                               //exception handling
                               System.out.println("Account is not created - Test is failed");
                             }
                             //After Account no generated
                             Desc="Verify Account  number field";
                             FunctionLibrary.verifyWebElementExist(accountNumberFieldTxt,Desc);
                             int AccountNumber =0;

                             AccountNumber = Integer.parseInt(FunctionLibrary.getText(accountNumberFieldTxt,"","Get Account number"));
                              //AccountNumber = Integer.parseInt(FunctionLibrary.getText(accountNumberFieldTxt,"","Get Account number"));
                             assert (AccountNumber!=0);
                             System.out.println("Created account number: "+ AccountNumber +".");
                             System.out.println("Siebel Account Creation test is completed.");
                             previousTestCaseBrowser=currentTcBrowser;
                             //Once account no created then writing in Excel sheet
                             ExcelSheet.writeExcel("TestData_siebel","siebel", 1, 44, +AccountNumber++,true);
                             if(FunctionLibrary.Get_Element(logoutSettingImage).isDisplayed())
                             {
                            	 //Checking for BrowserName if different browser for next test case
                            	// if(!exl.readexcel("siebel", i, 1).equals(exl.readexcel("siebel", i+1, 1)))
                            	 if(currentTcBrowser!=previousTestCaseBrowser)
                                 {
                            		 FunctionLibrary.Close_All_Active_Browser();
                               	    
                               	  //FunctionLibrary.launchBrowser(siebelURL, "Launching siebel app",exl.readexcel("siebel", i+1, 1));
                                 	//FunctionLibrary.launchBrowser(CommonLibrary.settingsSheetInfo.get("URL_QA").toString(), "Launching siebel app",eachTestCaseData2.get("Browser Type"));
                                 }
                              FunctionLibrary.clickObject(logoutSettingImage, "", "Click on Logout Image");
                              FunctionLibrary.clickObject(logoutButton, "", "Click on Logout Image");
                              
                             }
                          
                          }//End of Inner IF
     
               }//End of Try
                  catch(Exception e)
                 
                  {e.printStackTrace();
                 String Errormsg=e.getMessage();
                  System.out.println("Test Failed SCREENSHOT TAKING"); 
                 // ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Expected:" +Desc, LogStatus.FAIL, false);
                  //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,ReportLibrary.desc ,LogStatus.FAIL, false);
                   ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+"<br>"+"Actual: Execution Failed due to ********"+Errormsg, LogStatus.FAIL, true);
                  //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, ReportLibrary.desc, LogStatus.FAIL, true);
                  if(LoginMessage.equalsIgnoreCase("Success"))
                   {
                     if(FunctionLibrary.Get_Element(logoutSettingImage).isDisplayed())
                       {
                    	 if(FunctionLibrary.Get_Element(logoutSettingImage).isDisplayed())
                    	 {
                          FunctionLibrary.clickObject(logoutSettingImage, "", "Click on Logout Image");
                          FunctionLibrary.clickObject(logoutButton, "", "Click on Logout Image");
                    	 }

                           if (previousTestCaseBrowser!=currentTcBrowser)
                           {
                              FunctionLibrary.Close_All_Active_Browser();
                           }

                      /*//if(!exl.readexcel("siebel", i, 1).equals(exl.readexcel("siebel", i+1, 1)))
                    	 if(!eachTestCaseData.get("Browser Type").equals(eachTestCaseData2.get("Browser Type")))
                       {FunctionLibrary.Close_All_Active_Browser();

                      	  //FunctionLibrary.launchBrowser(siebelURL, "Launching siebel app",exl.readexcel("siebel", i+1, 1));
                  		//FunctionLibrary.launchBrowser(CommonLibrary.settingsSheetInfo.get("URL_QA").toString(), "Launching siebel app",eachTestCaseData2.get("Browser Type"));
                        }*/
                       }
                   }
                  else{  
                	  //if(!exl.readexcel("siebel", i, 1).equals(exl.readexcel("siebel", i+1, 1)))
            
                	  if(previousTestCaseBrowser!=currentTcBrowser)
                      {
                 	 FunctionLibrary.Close_All_Active_Browser();
                     	  //FunctionLibrary.launchBrowser(siebelURL, "Launching siebel app",exl.readexcel("siebel", i+1, 1));
                 		//FunctionLibrary.launchBrowser(CommonLibrary.settingsSheetInfo.get("URL_QA").toString(), "Launching siebel app",eachTestCaseData2.get("Browser Type"));
                       }	 
                     
                   }
                  
       		           //FunctionLibrary.NavigateTo(siebelURL, "Navigating URL");
                }//End of Catch
                         
       }//End of main outer IF
                  //Closing Browser
           		      
                       if(!(counter==0)){
           		     
           		       
           	           //FunctionLibrary.Close_All_Active_Browser(); 
           		       }   
                         //FunctionLibrary.Close_All_Active_Browser();
         }//End Of outer for loop

}//End of Account opening Method

        public static void changeLogonMode(String logonMode) throws Exception
        {
            //code for changing Logon mode
            FunctionLibrary.clickObject(clickOnTool, "", "Click on Tool");
            FunctionLibrary.clickObject(clickOnUserPreference, "", "Click on UserPreference");
            FunctionLibrary.clickObject(clickOnLogOnModeIcon, "", "Click on Logon Mode icon");
            FunctionLibrary.clickObject("xpath=//li[@data-lovtype='Vector User Profile TXDOT Form Applet:Pager Type' "
                    + "and text()='"+logonMode+"']", "", "Clicking logon mode");
            FunctionLibrary.clickObject(clickOnSave, "", "Click on Save");

        }


}//End Of Class


