package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.ExcelSheet;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import org.joda.time.DateTime;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;

import static objectProperties.SblCreateAccountPageProperties.*;

public  class SiebelAccountCreation
{
	
    public static WebDriver browser= FunctionLibrary.ObjDriver;
    public static String previousTestCaseBrowser;
    public static String LoginMessage="NotSuccess";
 @SuppressWarnings("unchecked")
    public static void SiebelAccountCreation() throws IOException, Exception {
	
      //Read input excel sheet for test data
      ExcelSheet excelSheetObj=new ExcelSheet();
     // int counter=0;
      //int Row=0;
      String Desc="";
      //Map variable which hold the once test case data
      HashMap<String,String> eachTestCaseData =new HashMap();
      
      //get no of rows fromm the test data excel sheet(no of test cases)
      int noOfTestCases=excelSheetObj.totalrows("TestData_siebel","siebel");
      String applicationUrl;
      String exectuionStatus;
      String accountType;
      
      String modeType;
      String paymentMode;
      String currentTcBrowser;
          //Script will iterate based on the row count
          for(int iterator=1;iterator<=noOfTestCases;iterator++)
         {
        	  eachTestCaseData= CommonLibrary.getEachTestCaseData(excelSheetObj,"siebel",iterator);
        	  exectuionStatus=eachTestCaseData.get("Execution Status");
              accountType =eachTestCaseData.get("Account Type");
              modeType =eachTestCaseData.get("Mode Type");
              paymentMode =eachTestCaseData.get("Rebill Pay Type");
              currentTcBrowser =eachTestCaseData.get("Browser Type");
              applicationUrl = CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString();
              
        	  //Execute Test case only the for the exectuionStatus mentioned Yes
                if(exectuionStatus.equalsIgnoreCase("Yes"))
                   {
                       ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"<b>"+eachTestCaseData.get("TestCaseId")+"</b>"+
                               ": Test Case Execution is started....................... <br>"
                               + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);
                                        
                      try{
                      		  //Launching Browser
                      		  CommonLibrary.launchBrowser(applicationUrl.toString(), "Launching siebel app",currentTcBrowser);
                              FunctionLibrary.Wait_For_Object = new WebDriverWait(FunctionLibrary.ObjDriver,60);
                              FunctionLibrary.Wait_For_Object.until(ExpectedConditions.visibilityOfElementLocated(By.id("s_swepi_1")));
                              ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Creating account of -- Type: "+accountType+", Payment mode: "+paymentMode+", Logon mode:"+modeType, LogStatus.INFO, false);
                    	      //Enter User Name
                    	      Desc="Entering UserName on UserName textbox";
                    	      FunctionLibrary.setText(loginUsernameTxtBox,eachTestCaseData.get("UserId"), Desc);
                    	      //Enter Password
                    	      Desc="Entering Password into password field";
                    	      FunctionLibrary.setText(loginPasswordTxtBox,eachTestCaseData.get("Password"), Desc);
                    	      //Click on Sign-in Button
                    	      Desc="Clicking on Sign in Button";
                    	      FunctionLibrary.clickObject(signInBtn, "", "Click sign in button");
                    	      WebDriverWait wait = new WebDriverWait(FunctionLibrary.ObjDriver,90);
                    	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//div[@class='Welcome'])[1]")));
                    	      //if(LoginMessage != null)
                    	      Desc="Verify Account Opening element";
                    	        if(FunctionLibrary.verifyWebElementExist(homePageVerificationTxt,Desc))
                    	          {
                            	   LoginMessage="Success";
                            	  //Type of Account creation adding to Report
                            	   ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Creating :"+""+accountType+""+" Account For Sieble CRM"+"By Using ["+paymentMode+" And "+modeType+"]", LogStatus.PASS, true);
                            	   //select logon mode                                      
                            	   changeLogonMode(modeType); 
                            	   // choose type of account commercial/Business account
                            	   if(accountType.equalsIgnoreCase("Business")|| accountType.equalsIgnoreCase("Commercial")) 
                            	   	{	  
                            		   FunctionLibrary.clickObject(siteMapImage, "", "Click on SiteMap image");
                                       
                            		   //wait until page to released
                            		   JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.ObjDriver;
                            		   js.executeScript("return document.readyState").toString().equals("complete");

                            		   FunctionLibrary.clickObject(administrativeUserLink, "", "Click on AdministratorUser link");

                            		   if( FunctionLibrary.verifyWebElementExist(employeeLink, "Verify Employee Link is displayed")) {
                            			   FunctionLibrary.clickObject(employeeLink, "", "Click on Employee link");
                            		   }

                            		   FunctionLibrary.clickObject(employeeSearchComboBoxIcon, "", "Click on comboboxIcon");
                            		   js.executeScript("return document.readyState").toString().equals("complete");
                            		   
                            		   FunctionLibrary.clickObject(selectLoginName, "", "select Login Name");
                            		   //Set User Name on the Login Text Box
                            		   FunctionLibrary.setText(loginnameTextBox,eachTestCaseData.get("UserId"), "Enter Login Name");
                            		   FunctionLibrary.clickObject(selectGo, "", "Click on GO");
                            		   js.executeScript("return document.readyState").toString().equals("complete");
                            		   
                            		   String CommercialAccValue=FunctionLibrary.Get_Element(commercialAccountSelect).getAttribute("aria-checked");
                            		   //choose for Commercial flow
                            		   if(accountType.equalsIgnoreCase("Commercial") && CommercialAccValue.equalsIgnoreCase("false")){
                            			   Desc="Click on commercial Account Radiobutton";

                            			   FunctionLibrary.clickObject("xpath=.//input[@type='checkbox' and @aria-labelledby='Commercial_Account_Open_Label']//following::span[1]", "",Desc);
                            		   }

                            		   //choose for Business flow
                            		   if(accountType.equalsIgnoreCase("Business") && CommercialAccValue.equalsIgnoreCase("true")){
                                        Desc="Click on Business Account Radiobutton";
                                        FunctionLibrary.clickObject("xpath=.//input[@type='checkbox' and @aria-labelledby='Commercial_Account_Open_Label']//following::span[1]", "",Desc);
                                    }
                            		   FunctionLibrary.clickObject(clickOnSaveCommercialAccount, "", "Click on Save");
                            		   FunctionLibrary.clickObject(accountOpeningBtn,"","Clicking Account opening Button");
                            		   FunctionLibrary.clickObject(clickOnBusinessCommercialAccount, "", "Click on Business/Commercial Account");
                            		   FunctionLibrary.clickObject(clickOnNewButton, "", "Click on new Button for Business/Commercial");
                            		   //Thread.sleep(2000);
                           }    

                            //-------------------------------Site map flow Ends-------------


                            	   	if(accountType.equalsIgnoreCase("private")){
                            	   		//********Clicking on Account opening tab*************
                            	   		try{

                            	   			FunctionLibrary.clickObject(accountOpeningBtn,"","Clicking on Account opening button");
                            	   			FunctionLibrary.clickObject(newAccountOpeningBtn,"","Clicking new button");


                            	   		}catch(Exception e){System.out.println("Problem in Home Page");}

                            	   	}

                            	   	//Handling pop up code
                            	   	// FunctionLibrary.clickObject("xpath=.//*[@id='btn-accept']","","Clicking Ok buttn");

                            	   	//**************Fields will be applicable only for Business/Commercial************

                            	   	if(accountType.equalsIgnoreCase("Business")|| accountType.equalsIgnoreCase("Commercial"))
                            	   	{
                            	   		//Fields applicable for Business or Commercial only
                            	   		//Wait till accountNameTextBox button to be visible
                            	   		//Desc="Wait till Account Name Textbox to be visible";
                            	   		//FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver,10,accountNameTextBox);

                            	   		// WebDriverWait wait = new WebDriverWait(FunctionLibrary.ObjDriver,90);
                            	   		Thread.sleep(4000);
                            	   		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@aria-label='Account Name']")));
                            	   		//FunctionLibrary.setText(accountNameTextBox,eachTestCaseData.get("Account Name"),"Enter Account name");
                            	   		String accountName = eachTestCaseData.get("Account Name");
                            	   		FunctionLibrary.setText(accountNameTextBox,accountName ,"Enter Account name");
                            	   		ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Account  Name: " + accountName,LogStatus.INFO,false);
                            	   		FunctionLibrary.setText(dbaNameTextBox,eachTestCaseData.get("DBA Name"),"Enter DBA name");
                            	   		//String ExternalResource=excelSheetObj.readexcel("siebel", iterator, 42);
                            	   		//String ExternalResource1=ExternalResource.substring(0, ExternalResource.length()-2);
                            	   		// System.out.println(ExternalResource);
                            	   		FunctionLibrary.setText(externalTextBox,eachTestCaseData.get("External Reference"),"Enter External reference number");
                            	   		//String Fein=excelSheetObj.readexcel("siebel", iterator, 41);
                            	   		//String Fein1=Fein.substring(0, Fein.length()-2);
                            	   		//System.out.println(ExternalResource);
                            	   		FunctionLibrary.setText(FEINTextBox,eachTestCaseData.get("FEIN"),"Enter Fien number");
                            	   	}
                            	   	//*********************Fields will be applicable for common to Business/Commercial/private*************************
                            	   	//clicking Security question drop down icon
                             Desc="Clicking Security question drop down";
                             FunctionLibrary.clickObject(clicksecurityQuestionDropdown,"","Clicking Security question drop down");
                             //Selecting the security question
                             //System.out.println(excelSheetObj.readexcel("siebel", iterator, 8));
                             //Provide the input value instead of "Security Question"
                             Desc="Selecting Security question from drop down";
                             FunctionLibrary.clickObject("xpath=(//*[contains(text(),'"+eachTestCaseData.get("Challenge Question")+"')])[1]","",Desc);
                             //entering security answer
                             //String SecurityAnswer=excelSheetObj.readexcel("siebel", iterator, 9);
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
                             
                             Desc="Enter Pin";
                             FunctionLibrary.setText(accountPINTxtBox, eachTestCaseData.get("PIN"),Desc);
                            //entering preferred language
                             Desc="Select language preference";
                             FunctionLibrary.setText(preferredLanguageTxtBox,eachTestCaseData.get("LanguagePref"),Desc);
                            //save the personal details for Private account
                             if(accountType.equalsIgnoreCase("private")){
                            	 Desc="Click on Save Button";
                             FunctionLibrary.clickObject(accountDetailsSaveBtn,"",Desc);
                             }
                             //save the personal details for Business or Commercial account
                             if(accountType.equalsIgnoreCase("Business")|| accountType.equalsIgnoreCase("Commercial")){
                            	 Desc="Click on Save Button";
                            	 
                            	//Wait till accountNameTextBox button to be visible
                                FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver,3,businessOrCommAcoDetailsSaveBtn);
                             	FunctionLibrary.clickObject(businessOrCommAcoDetailsSaveBtn,"",Desc);

                             }
                                JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.ObjDriver;
                                js.executeScript("return document.readyState").toString().equals("complete");

                                Thread.sleep(4000);
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
                             //Enter Phone number
                             Desc="Enter phone number";
                             FunctionLibrary.setText(contactDetailsPhnNoTxtBox,"8880739228",Desc);

                             //handle the phone number format pop-up alert
                             try {
                                          //FunctionLibrary.clickObject("xpath=./[@id='btn-accept']","","Clicking Ok buttn");
                              WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.ObjDriver,8);
                               wait2.until(ExpectedConditions.alertIsPresent());
                               Alert alert = FunctionLibrary.ObjDriver.switchTo().alert();
                               //System.out.println(alert.getText());
                               alert.accept();
                                       /*    Robot robot = new Robot();
                                           robot.delay(250);
                                           robot.keyPress(KeyEvent.VK_ENTER);
                                           robot.keyRelease(KeyEvent.VK_ENTER);
                                           //Click on Contact Details Save button
                                 Thread.sleep(4000);*/
                                           Desc="Click on Contact Detail Save button";
                                           Thread.sleep(3000);

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
                                FunctionLibrary.clickObject(contactDetailsSaveBtn,"",Desc);

/*
                             JavascriptExecutor js = (JavascriptExecutor)FunctionLibrary.ObjDriver;
                             js.executeScript("return document.readyState").toString().equals("complete");
*/                          Thread.sleep(3000);

                                //FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                             //Wait till Address Details getting Loaded
                             //Calling webdriver wait function
                               // Desc="Wait till Address Details New button To be visible";
                             //FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.ObjDriver, 10, addressDetailsNewBtn);
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

                                for (int iterator1 =0;iterator1<=addressItems.length-1;iterator1++)
                                {
                                    eachAddressInfo=addressItems[iterator1].split(":");
                                    addressType = eachAddressInfo[0];
                                    addressLine1 = eachAddressInfo[1];
                                    addressLine2 = eachAddressInfo[2];
                                    zipCode = eachAddressInfo[3];
                                    city = eachAddressInfo[4];
                                    state = eachAddressInfo[5];
                                    country = eachAddressInfo[6];

                                    FunctionLibrary.clickObject(addressDetailsNewBtn,"",Desc);
                                    Thread.sleep(3000);
                                    //todo: web driver wait implementation for addresstype drop down displays
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
/*
                                           Robot robot = new Robot();
                                           robot.delay(250);

                                           robot.keyPress(KeyEvent.VK_ENTER);
                                           robot.keyRelease(KeyEvent.VK_ENTER);
*/                                           
                                    //Click on Zip Code
                                    Desc="Click on postal/Zip code filed";
                                    FunctionLibrary.clickObject(addressDetailsPostalCodeELement,"",Desc);
                                    //Click on Zip Code
                                    Desc="Click on postal/Zip code filed";
                                    FunctionLibrary.setText(addressDetailsPostalCodeTxtBox,zipCode,"Entering postal code");
                                    //Entering State
                                    //FunctionLibrary.clickObject(addressDetailsStateElement,"","Clicking state field");
                                    //FunctionLibrary.setText(addressDetailsStateTxtBox,excelSheetObj.readexcel("siebel", iterator, 20),"Entering state value");

                                    // FunctionLibrary.clickObject(addressDetailsCountryElement,"","Clicking country field");
                                    //FunctionLibrary.setText(addressDetailsCountryTxtBox,excelSheetObj.readexcel("siebel", iterator, 21),"Entering country field");
                                    //Click on Address Save button
                                    Desc="Clicking address save button";
                                    FunctionLibrary.ObjDriver.findElement(By.xpath(".//*[@title='Addresses:Save']")).click();
                                    //Thread.sleep(2000);
                                    //FunctionLibrary.clickObject(addressDetailsSaveBtn,"",Desc);
                                    try {
                                        //FunctionLibrary.clickObject("xpath=./[@id='btn-accept']","","Clicking Ok buttn");
                                        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.ObjDriver,10);
                                        wait2.until(ExpectedConditions.alertIsPresent());
                                        Alert alert = FunctionLibrary.ObjDriver.switchTo().alert();
                                        alert.accept();
                                        Thread.sleep(3000);
                                        System.out.println("address alert is handled");
                                    } catch (Exception e) {
                                        //exception handling
                                        System.out.println("address alert is not present");
/*

                                        Robot robot = new Robot();
                                        robot.delay(250);
                                        robot.keyPress(KeyEvent.VK_ENTER);
                                        robot.keyRelease(KeyEvent.VK_ENTER);
                                        //Click on Contact Details Save button
                                        Thread.sleep(4000);
*/
                                        //exception handling
                                    }
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
                              //Rebill pay type Savings or Checking Account
                             if(eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("SAVINGS") || eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("CHECKING"))
                             {
                                //Enter Bank Account no:
                                //FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,excelSheetObj.readexcel("siebel", iterator, 36),"Enter bank number");
                            	 Desc="Enter bank number";
                                FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,eachTestCaseData.get("BankAccount"),Desc);
                                // FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,excelSheetObj.readexcel("siebel", iterator, 37),"Enter routing number");
                                Desc="Enter routing number";
                                FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,eachTestCaseData.get("BankRoutine"),Desc);
                             }
                             //Rebill Pay Type with Creditcard
                             if(eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("MASTERCARD") || eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("AMEX") || eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("DISCOVER")|| eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("VISA"))
                             {
                                
                                 //Enter Credit card #
                            	 Desc="Enter Credit Card number";
                                 FunctionLibrary.setText(creditCardNoField,eachTestCaseData.get("CreditCardNo"),Desc);
                                 //Credit card Expiration Month
                                 Desc="Select Expiration Month";
                                 FunctionLibrary.selectDropDownListByValue(creditCardExpMpnth,eachTestCaseData.get("ExpMonth"),Desc);
                                 //Credit card Expiration Year
                                 Desc="Select Expiration Year";
                                 FunctionLibrary.selectDropDownListByValue(creditCardExpYear,eachTestCaseData.get("ExpYear"),Desc);
                                
                             }
                             //Click on Replenishment Save button
                             Desc="Clicks on Replenishment save button";
                             FunctionLibrary.clickObject(replenishmentDetailsSaveBtn,"",Desc);
                             //Wait till Replenishment Details getting added
                             //Calling Webdriver wait function
                             Desc="Wait till SectionTwo Row Counter present";
                             FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 10, sectionTwoRowCounter);
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

                                for (int iterator1 =0;iterator1<=vehiclesItems.length-1;iterator1++) {
                                	
                            	 
                                	String pp = CommonLibrary.randomIdentifier(); 
                                	String RandomChar=pp.substring(0,2);
                                    eachVehicleInfo = vehiclesItems[iterator1].split(":");
                                    //plateNumber[iterator1] = eachVehicleInfo[0]+ DateTime.now().getMillisOfSecond();
                                    plateNumber[iterator1] = RandomChar+DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
                                    System.out.println(plateNumber[iterator1]);
                                    plateState = eachVehicleInfo[1];
                                    plateType = eachVehicleInfo[2];
                                    plateCountry = eachVehicleInfo[3];
                                    vehicleType = eachVehicleInfo[4];
                                    year = eachVehicleInfo[5];
                                    make = eachVehicleInfo[6];
                                    model = eachVehicleInfo[7];
                                    Thread.sleep(3000);
                                    //Clicks on vehicles new button
                                    Desc="Clicking vehicles new button";
                                    FunctionLibrary.clickObject(vehiclesDetailsNewBtn,"",Desc);
                                    //Enter PLate Number
                                    Desc="Entering plate number";
                                    FunctionLibrary.setText(vehiclesDetailsPlateNumberTxtBox,plateNumber[iterator1],Desc);
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

                                for (int iterator1 =0;iterator1<=devicesItems.length-1;iterator1++) {
                                    eachDeviceInfo = devicesItems[iterator1].split(":");
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
                                    FunctionLibrary.ObjDriver.findElement(By.xpath(".//*[@name='Plate_Number']")).sendKeys(plateNumber[iterator1].toString());
                                   //FunctionLibrary.setText(deviceDetailsPlateNumberTxtBox,plateNumber[iterator1],Desc);

                                    System.out.println(plateNumber[iterator1]);
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

                                //Desc="Verify row is added or not for device request";
                                ////////////////FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,Desc);
                                //Click Plans tab
                                Desc="Wait till plans tab to be visible";
                                //Calling Webdriver Wait function to wait till plans tab visible
                                FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 5, plansTab);
                                //Clicking the Plans tab
                                Desc="Clicking the Plans tab";
                                FunctionLibrary.clickObject(plansTab,"",Desc);
                                String plansToBeAdded = eachTestCaseData.get("PlansInfo");
                                plansToBeAdded = plansToBeAdded.replace("\n","");
                                String [] plansItems = plansToBeAdded.split(",");
                                String [] eachPlanInfo;
                                System.out.println("Plan Item Lenght: "+plansItems.length);
                            
                                String planName;
                                String existingPlan;
                                Thread.sleep(3000);
                                for (int iterator1 =0;iterator1<=plansItems.length-1;iterator1++) 
                                {
                                    eachPlanInfo = plansItems[iterator1].split(":");
                                    planName = eachPlanInfo[0];

                                    existingPlan= FunctionLibrary.ObjDriver.findElement(By.xpath(planAlreadyExisted)).getAttribute("title");
                                    if(!existingPlan.equals(planName))
                                    {
                                    	if(eachTestCaseData.get("accountType").equals("PRIVATE"))                                    	
                                    	{
                                        Desc = "Clicking new buttton";
                                        FunctionLibrary.clickObject(plansNewButton,"",Desc);
                                        Desc = "Clicking plantype drop down button";
                                        FunctionLibrary.clickObject(planTypeDropDownBtn,"", Desc);
                                        Desc = "Select paln name: " + planName;
                                        FunctionLibrary.clickObject("xpath=(//*[contains(text(),'"+planName+"')])[1]","",Desc);
                                        Thread.sleep(3000);
                                    	}
                                    	else
                                    	{
                                    		//if it is NRV
                                        	if(planName.equals("NRV"))
                                        	{
                                            Desc = "Clicking new buttton";
                                            FunctionLibrary.clickObject(plansNewButton,"",Desc);
                                            Desc = "Clicking plantype drop down button";
                                            FunctionLibrary.clickObject(planTypeDropDownBtn,"", Desc);
                                            Desc = "Select paln name: " + planName;
                                            FunctionLibrary.clickObject("xpath=(//*[contains(text(),'"+planName+"')])[1]","",Desc);
                                            Thread.sleep(3000);
                                        	}
                                            else
                                            {

                                            	if(eachTestCaseData.get("accountType").equals("BUSINESS"))                                    	
                                            	{
                                            		 FunctionLibrary.clickObject("xpath=(//*[contains(text(),'FLEETPREE')])[1]","",Desc);
                                            		//code to delete fleet free or post
                                                 	Desc = "Delete the eixsting fleet plan";
                                            	}
                                            	else
                                            	{
                                            		 FunctionLibrary.clickObject("xpath=(//*[contains(text(),'FLEETPOST')])[1]","",Desc);
                                            		//code to delete fleet free or post
                                                  	Desc = "Delete the eixsting fleet plan";
                                            	}
                                            	
                                            	 //---------Clarity-->FunctionLibrary.clickObject("xpath=(//*[contains(text(),'FLEETPREE')])[1]","",Desc);
                                            	
                                            	 //Click delete button
                                            	Desc = "Clicking new buttton";
                                                 FunctionLibrary.clickObject(plansNewButton,"",Desc);
                                                 Desc = "Clicking plantype drop down button";
                                                 FunctionLibrary.clickObject(planTypeDropDownBtn,"", Desc);
                                                 Desc = "Select paln name: " + planName;
                                                 FunctionLibrary.clickObject("xpath=(//*[contains(text(),'"+planName+"')])[1]","",Desc);
                                                 Thread.sleep(3000);
                                            	

    										}
                                    	}
                                    	
                                 }
                                }
                             
                             //Click on Pay tab
                             Desc="Click on Pay tab";
                             FunctionLibrary.clickObject(plansTabPayBtn,"",Desc);
                             //Calling Webdriver Wait function to wait till plans Details New button visible
                             Desc="Wait till PaymentDetails New button to be visible";
                             FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.ObjDriver, 5, paymentDetailsNewBtn);
                             //Clicking plans new button
                             Desc="Clicking plans new button";
                             FunctionLibrary.clickObject(paymentDetailsNewBtn,"",Desc);
                             Thread.sleep(3000);
                             //Clicking on Save Button[PaymentDetails Save button]
                             Desc="Clicks on Save Button[PaymentDetails Save button]";
                             FunctionLibrary.clickObject(paymentDetailsInfoSaveBtn,"",Desc);
                             //handle the "Do you want to updated the credit card details as primary payment method....." format pop-up alert

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
                             try{
                            	 Thread.sleep(5000);
                            	 
                               WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.ObjDriver,10);
                               wait8.until(ExpectedConditions.alertIsPresent());
                               Thread.sleep(5000);
                               Alert alert = FunctionLibrary.ObjDriver.switchTo().alert();
                               //System.out.println("Popup is displayed with text" + alert.getText());
                               alert.accept();
                               //After Account no generated
                               Desc="Verify Account  number field";
                              int AccountNumber =0;
                              
                              if (FunctionLibrary.ObjDriver.findElement(By.xpath("//input[@aria-label='Acct #']")).isDisplayed()) 
                              {
                              	AccountNumber = Integer.parseInt(FunctionLibrary.getText(accountNumberFieldTxt, "", "Get Account number"));
                                                              //AccountNumber = Integer.parseInt(FunctionLibrary.getText(accountNumberFieldTxt,"","Get Account number"));
                              	assert (AccountNumber != 0);
                              	ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Expected: Account should be created <br>" +
                                                                      "Actual: Account is created "+AccountNumber,LogStatus.PASS,true);
                              	System.out.println("Created account number: " + AccountNumber + ".");
                              	System.out.println("Siebel Account Creation test is completed.");
                              	previousTestCaseBrowser = currentTcBrowser;

                                   //Once account no created then writing in Excel sheet
                                   ExcelSheet.writeExcel("TestData_siebel", "siebel", iterator, 35, +AccountNumber++, true);
                                   if (FunctionLibrary.Get_Element(logoutSettingImage).isDisplayed()) {
                                       
                                       FunctionLibrary.clickObject(logoutSettingImage, "", "Click on Logout Image");
                                       FunctionLibrary.clickObject(logoutButton, "", "Click on Logout Image");

                                   }
                               }
                               else
                               {

                                  ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Expected: Account number field should be displayed <br>"
                                          +"Actual: Acccount number filed is not displaying",LogStatus.FAIL,true);
                               }
                               
                             } catch (Exception e) {
                               //exception handling
                               System.out.println("Account is not created - Test is failed");
                             }
                            
             
                        }//End of Inner IF
     
               }//End of Try
                  catch(Exception e)
                {
                	  try{
                		  String Errormsg=e.getMessage();
                		  System.out.println("Test Failed SCREENSHOT TAKING");
                		  ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+"<br>"+"Actual: Execution Failed due to: <br>"+Errormsg, LogStatus.FAIL, true);
                		  //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+"<br>"+"Actual: Execution Failed due to:+"+"Cyber Source Error", LogStatus.FAIL, true);
                          WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.ObjDriver,10);
                          wait8.until(ExpectedConditions.alertIsPresent());
                          Thread.sleep(5000);
                          Alert alert = FunctionLibrary.ObjDriver.switchTo().alert();
                          //System.out.println("Popup is displayed with text" + alert.getText());
                          alert.accept();
                	  }catch(Exception exp){};
                	  e.printStackTrace();
                  if (FunctionLibrary.ObjDriver.findElement(By.xpath("//*[@id='btn-accept']")).isDisplayed()) 
                  {
                	  
                	  FunctionLibrary.ObjDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();
                	  
                	  //Method for Logout and Closing browser
                	  logoutAndCloseBrowsers();
                	  if (FunctionLibrary.ObjDriver.findElement(By.xpath("//*[@id='btn-accept']")).isDisplayed()) 
                	  {
                    	  //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+"<br>"+"Actual: Execution Failed due to:"+"Unexpected Pop up", LogStatus.FAIL, true);
                    	  FunctionLibrary.Close_All_Active_Browser();
                    	  //CommonLibrary.launchBrowser(applicationUrl.toString(), "Launching siebel app",currentTcBrowser);
                	  }
                  }
                  else
                  {
                	  
                    String Errormsg=e.getMessage();
                    
                    //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Expected:" +Desc, LogStatus.FAIL, false);
                    //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,ReportLibrary.desc ,LogStatus.FAIL, false);
                    ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+"<br>"+"Actual: Execution Failed due to: <br>"+Errormsg, LogStatus.FAIL, true);
                    //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, ReportLibrary.desc, LogStatus.FAIL, true);
                    //Method for Logout and Closing browser
                    try{
              		  //Handling unexpected Pop up
              		  Robot robot = new Robot();
                        robot.delay(250);
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);  
              	  }catch(Exception e2){};
                    logoutAndCloseBrowsers();
                   }
             }//End of Catch
                         
       }//End of main outer IF
                //Reseting Test Number
                ReportLibrary.Test_Step_Number=1;
                  
         }//End Of outer for loop
          

}//End of Account opening Method

    /**
     * Logout and close all browsers
     */
    private static void logoutAndCloseBrowsers() {
	    
			if(LoginMessage.equalsIgnoreCase("Success"))
            {
                if(FunctionLibrary.Get_Element(logoutSettingImage).isDisplayed())
                {
             	 
             	 
                   FunctionLibrary.clickObject(logoutSettingImage, "", "Click on Logout Image");
                   FunctionLibrary.clickObject(logoutButton, "", "Click on Logout Image");
                   try{
               		  //Handling unexpected Pop up
               		  Robot robot = new Robot();
                         robot.delay(250);
                         robot.keyPress(KeyEvent.VK_ENTER);
                         robot.keyRelease(KeyEvent.VK_ENTER);  
               	  }catch(Exception e2){};
                   
             	 }
             	 else
                  {
                      FunctionLibrary.Close_All_Active_Browser();
                  }

                    
             }
             else
              {
                  System.out.println("Login not success");
              }
            
       }//End of LogoutandCloseBrowserMethod

    /**
     * change the logon mode
     * @param  logonMode give the logon mode. Example: WALK-IN,MAIL-IN.....
     */
    public static void changeLogonMode(String logonMode) throws Exception
        {
            //code for changing Logon mode
            FunctionLibrary.clickObject(clickOnTool, "", "Click on Tool");
            FunctionLibrary.clickObject(clickOnUserPreference, "", "Click on UserPreference");
            FunctionLibrary.clickObject(clickOnLogOnModeIcon, "", "Click on Logon Mode icon");
            FunctionLibrary.clickObject("xpath=//li[@data-lovtype='Vector User Profile TXDOT Form Applet:Pager Type' "
                    + "and text()='"+logonMode+"']", "", "Clicking logon mode");

            JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.ObjDriver;
            js.executeScript("return document.readyState").toString().equals("complete");

            FunctionLibrary.clickObject(clickOnSave, "", "Click on Save");

            //JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.ObjDriver;
            js.executeScript("return document.readyState").toString().equals("complete");



        }

}//End Of Class


