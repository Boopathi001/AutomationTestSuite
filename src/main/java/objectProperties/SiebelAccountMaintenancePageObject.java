package objectProperties;

/**
 * Created by 23319 on 28-12-2016.
 */
public class SiebelAccountMaintenancePageObject {

    //**************************************************************
    public static String siebelURL="http://10.36.20.4/callcenter_enu/start.swe?";
    public static String siebelLoginUsernameTxtBox="id=s_swepi_1";
    public static String siebelLoginPasswordTxtBox="id=s_swepi_2";

    public static String siebelSignInBtn="id=s_swepi_22";
    public static String siebelHomePageVerificationTxt="xpath=(.//div[@class='Welcome'])[1]";
    //Properties for Logout
    public static String logoutSettingImage="xpath=//li[@id='tb_0']";
    public static String logoutButton="xpath=//span[@class='siebui-icon-logout']";



    public static String accountsTab="xpath=//*[contains(text(),'Accounts') and @class='ui-tabs-anchor']";

    public static String accountNumberTxtBox="xpath=//input[@aria-label='Account Number' and @aria-labelledby='CSN_Label']";
    public static String goBtn="xpath=.//*[@title='Search:Go']";
    public static String accountInfoTab="xpath=//*[contains(text(),'Account Info') and @class='ui-tabs-anchor']";

    public static String accountInfoPINTxt="xpath=//input[@aria-labelledby='Challenge_Answer_Label' and @aria-label='Challenge Answer' and contains(@name,'s_1')]";
    public static String accountInfoMiscellaniousPINTxtBox="xpath=//input[@aria-labelledby='Challenge_Answer_Label' and @aria-label='Challenge Answer' and contains(@name,'s_2')]";


}
