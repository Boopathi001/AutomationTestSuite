package features;

import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by 23319 on 29-12-2016.
 */
public class test
{

    public static String oldBrowser="";
    public static String newBrowser="";
    public static void main(String args[]) throws Exception {
        test t = new test();
        CommonLibrary.launchBrowser("http://google.com","hi","chrome");
        //System.out.println(CommonLibrary.randomIdentifier());
        /*t.launchBrowser1("http://www.google.com","firefox");
        t.launchBrowser1("http://www.google.com","chrome");
      t.launchBrowser1("http://www.google.com","iexplore");

        t.launchBrowser1("http://www.google.com","firefox");

        t.launchBrowser1("http://www.google.com","firefox");
        t.launchBrowser1("http://www.google.com","chrome");
        t.launchBrowser1("http://www.google.com","iexplore");*/
    }
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";

    final java.util.Random rand = new java.util.Random();

    // consider using a Map<String,Boolean> to say whether the identifier is being used or not
    final Set<String> identifiers = new HashSet<String>();

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }
    public void launchBrowserDeletteMe(String URL, String browserName)
{
    System.out.println(browserName);
    if(browserName.equalsIgnoreCase("firefox")) {
        System.setProperty("webdriver.gecko.driver",".\\src\\browserDrivers\\geckodriver.exe");
        DesiredCapabilities capabilities=DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        FunctionLibrary.ObjDriver = new FirefoxDriver(capabilities);
        //ObjDriver = new FirefoxDriver();
        FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        FunctionLibrary.ObjDriver.navigate().to(URL);
    }else if(browserName.equalsIgnoreCase("chrome")) {

        System.setProperty("webdriver.chrome.driver", ".\\src\\browserDrivers\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        FunctionLibrary.ObjDriver = new ChromeDriver( );
        //t.navigate().to(URL);

        FunctionLibrary.ObjDriver.manage().deleteAllCookies();
        FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        FunctionLibrary.ObjDriver.navigate().to(URL);

        //FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        FunctionLibrary.ObjDriver.manage().window().maximize();
        ////ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
        //Wait_For_Object = new WebDriverWait(ObjDriver,60);
    }else if(browserName.equalsIgnoreCase("iexplore")) {

        System.setProperty("webdriver.ie.driver",".\\src\\browserDrivers\\IEDriverServer.exe");
        FunctionLibrary.ObjDriver = new InternetExplorerDriver();
        //Get Browser name and version.
        Capabilities caps = ((RemoteWebDriver) FunctionLibrary.ObjDriver).getCapabilities();
        String browserName1 = caps.getBrowserName();
        String browserVersion = caps.getVersion();
        System.out.println("BrowserName: "+browserName1);
        System.out.println("Browser version: "+browserVersion);

//Get OS name.
        String os = System.getProperty("os.name").toLowerCase();

        FunctionLibrary.ObjDriver.navigate().to(URL);
    }else {
        System.out.println(FunctionLibrary.ObjDriver + " is not a supported browser");
    }

}


}
