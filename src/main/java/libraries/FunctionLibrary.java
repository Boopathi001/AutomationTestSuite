package libraries;


import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.testng.junit.IJUnitTestRunner;


@SuppressWarnings({ "unused", "restriction" })
public class FunctionLibrary
{

	public static WebDriver ObjDriver;
	public static WebElement Browser_Element;
	public static List<WebElement> Browser_Elements;
	public static List<WebElement> Browser_Elements_For_Div;
	public static Actions Element_Action;
	public static WebDriverWait Wait_For_Object;
	public static WebDriverWait Wait_For_Ajax;
	public static File Screen_Shot;
	 public static String browser="firefox";
	 //public static String browser="chrome";
	 //public static String browser="iexplore";
	//@@@@@@@@@@@Singleton Implementation@@@@@@@@@@@@@@@@@
	static FunctionLibrary Function_Library_Instance;
	FunctionLibrary()
	{

	}

	public static FunctionLibrary Get_Function_Instance()
	{
		if (Function_Library_Instance == null)
		{
			return Function_Library_Instance = new FunctionLibrary();

		}
		else
		{
			return Function_Library_Instance;
		}
	}

	//@@@@@@@@@@@````````````````````````@@@@@@@@@@@@@@@@@@
	//WebDriver Wait by Xpath for Element to be visible by Xpath
	public static void webdrvwaitByVisiblityofElementLocByXpath(WebDriver ObjDriver,int secs,String elemnt ){
		 WebDriverWait wait1 = new WebDriverWait(ObjDriver,secs);
         wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elemnt)));
	}
	//WebDriver Wait by Xpath for Element to be Clickable by Xpath
	public static void webdrvwaitByVisiblityofElementClickableByXpath(WebDriver ObjDriver,int secs,String elemnt ){
		 WebDriverWait wait1 = new WebDriverWait(ObjDriver,secs);
        wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(elemnt)));
	}
	
	public static void Activate_Latest_Browser(String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Activate_Browsers();
			Element_Action = new Actions(ObjDriver);
			if(Desc!="")
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
			}
		}
	}

	public static void Activate_Browsers()
	{
		Set<String> BrowserList = null;
		Iterator<String> IteratorList = null;
		int Browser_Cnt = 0;
		int i;
		BrowserList = ObjDriver.getWindowHandles();
		Browser_Cnt = BrowserList.size();
		IteratorList = BrowserList.iterator();
		String[] WinList = new String[Browser_Cnt];
		for(i = 0 ; i <Browser_Cnt ; i++)
		{
			WinList[i] = IteratorList.next();
		}
		FunctionLibrary.ObjDriver.switchTo().window(WinList[i-1]).manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public static void Close_All_Active_Browser()
	{
		Set<String> BrowserList = null;
		Iterator<String> IteratorList = null;
		int Browser_Cnt = 0;
		int i;
		BrowserList = ObjDriver.getWindowHandles();
		Browser_Cnt = BrowserList.size();
		IteratorList = BrowserList.iterator();
		String[] WinList = new String[Browser_Cnt];
		for(i = 0 ; i <Browser_Cnt ; i++)
		{
			WinList[i] = IteratorList.next();

		}
		for(i = Browser_Cnt-1 ; i >=0 ; i--)
		{
			ObjDriver.switchTo().window(WinList[i]).manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			ObjDriver.quit();
		}
	}

	public static int Active_Browsers_Cnt()
	{
		Set<String> BrowserList = null;
		Iterator<String> IteratorList = null;
		int Browser_Cnt = 0;
		BrowserList = ObjDriver.getWindowHandles();
		Browser_Cnt = BrowserList.size();
		return Browser_Cnt;
	}

	public static void Close_Latest_Active_Browser()
	{
		Activate_Browsers();
		ObjDriver.quit();
		if( Active_Browsers_Cnt()>0)
		{
			Activate_Browsers();
		}

	}

	//public static void launchBrowser(String URL,String Desc,String browserName)
	public static void launchBrowser(String URL,String Desc,String browserName)
	{
		if(!URL.matches(""))
		
		{
		//***********************************************************************************************	
			/*System.setProperty("webdriver.chrome.driver", ".\\src\\browserDrivers\\chromedriver.exe");

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			FunctionLibrary.ObjDriver = new ChromeDriver( );
			//t.navigate().to(URL);

			FunctionLibrary.ObjDriver.manage().deleteAllCookies();
			FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			FunctionLibrary.ObjDriver.navigate().to(URL);

			//FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			FunctionLibrary.ObjDriver.manage().window().maximize();*/
	//********************************************************************************************
			if(browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",".\\src\\browserDrivers\\geckodriver.exe");
				DesiredCapabilities capabilities=DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);
				ObjDriver = new FirefoxDriver(capabilities);
				//ObjDriver = new FirefoxDriver();
				ObjDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
				ObjDriver.navigate().to(URL);
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
				//FunctionLibrary.ObjDriver = new InternetExplorerDriver();
			}else {
				System.out.println(FunctionLibrary.ObjDriver + " is not a supported browser");
			}
			
			
			

			////ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + ": "+URL, LogStatus.PASS, false);

		}
	}

	public static void NavigateTo(String URL,String Desc) throws Exception
	{
		if(!URL.matches("NA"))
		{
			ObjDriver.navigate().to(URL);
			//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + ": "+URL, LogStatus.PASS, false);
		}
	}

	public static Boolean Is_Browser_Exist()
	{
		Boolean Is_Exist = false;
		if(ObjDriver == null)
		{
			return Is_Exist;
		}
		else
		{
			return Is_Exist = true;
		}
	}

	public static WebElement Get_Element(String Str_Property)
	{
		if(!Is_Browser_Exist())
		{
			//launchBrowser("Launch Browser","yes");
		}
		Get_Locator(Str_Property);
		return Browser_Element;
	}

	public static List<WebElement> Get_Elements(String Str_Property) throws Exception
	{
		if(!Is_Browser_Exist())
		{
			//launchBrowser("Launch Browser","yes");
		}
		Get_Locators(Str_Property);
		return Browser_Elements;
	}

	public static List<WebElement> Get_Elements_For_Div(String Str_Property) throws Exception
	{
		if(!Is_Browser_Exist())
		{
			//launchBrowser("Launch Browser","yes");
		}
		Get_Locators_For_Div(Str_Property);
		return Browser_Elements_For_Div;
	}

	public static String Get_WebElement_Value(WebElement Element)
	{
		return Element.getText();

	}

	public static void Get_Set_WebElement_Value(WebElement Element,String Event,String Desc)
	{
		String Ret;
		Ret = Get_WebElement_Value(Element);

	}


	public static WebElement Get_Locator_For_Exist(String Str_Property)
	{
		WebElement Browser_Element1 = null;
		String Actual_Property;

		//Wait_For_Div();
		if(Str_Property.startsWith("Attribute="))
		{
			Actual_Property = Str_Property.substring(10);
			String[] Actual_Property_Arr = Actual_Property.split(";");
			String TagName = Actual_Property_Arr[0];
			String Attribute = Actual_Property_Arr[1];
			String Attribute_Value = Actual_Property_Arr[2];
			Browser_Elements = ObjDriver.findElements(By.tagName(TagName));
			for(int i=0;i<=Browser_Elements.size()-1;i++)
			{
				if(Browser_Elements.get(i).getAttribute(Attribute).matches(Attribute_Value))
				{
					Browser_Element1 = Browser_Elements.get(i);
					break;
				}
			}
		}
		if(Str_Property.startsWith("id="))
		{
			Actual_Property = Str_Property.substring(3);
			Browser_Element1 = ObjDriver.findElement(By.id(Actual_Property));
			
			////Wait_For_Object.until(ExpectedConditions. visibilityOfElementLocated(By.id(Actual_Property)));
			int i = 10;
			for(i=0;i<=15;i++)
			{
				if(Browser_Element1.isDisplayed())
				{
					//Element_Action.moveToElement(Browser_Element1).perform();
					highLightElement(ObjDriver,Browser_Element1);
					break;
				}
			}
		}
		if(Str_Property.startsWith("name="))
		{
			Actual_Property = Str_Property.substring(5);
			Browser_Element1 = ObjDriver.findElement(By.name(Actual_Property));
			//Wait_For_Object.until(ExpectedConditions. visibilityOfElementLocated(By.name(Actual_Property)));
			int i = 10;
			for(i=0;i<=15;i++)
			{
				if(Browser_Element1.isDisplayed())
				{
					//Element_Action.moveToElement(Browser_Element1).perform();
					highLightElement(ObjDriver,Browser_Element1);
					break;
				}
				//Thread.sleep(100);
			}
		}
		if(Str_Property.startsWith("xpath="))
		{
			Actual_Property = Str_Property.substring(6);
			Browser_Element1 = ObjDriver.findElement(By.xpath(Actual_Property));
			//Wait_For_Object.until(ExpectedConditions. visibilityOfElementLocated(By.xpath(Actual_Property)));
			int i;
			for(i=0;i<=15;i++)
			{
				if(Browser_Element1.isEnabled())
				{
					//Element_Action.moveToElement(Browser_Element1).perform();
					highLightElement(ObjDriver,Browser_Element1);
					Browser_Element1 = ObjDriver.findElement(By.xpath(Actual_Property));
					break;
				}
				else
				{
					return Browser_Element1;
				}

			}
			Browser_Element1 = ObjDriver.findElement(By.xpath(Actual_Property));
		}
		if(Str_Property.startsWith("link="))
		{
			Actual_Property = Str_Property.substring(5);
			Browser_Element1 = ObjDriver.findElement(By.linkText(Actual_Property));
			//Wait_For_Object.until(ExpectedConditions. visibilityOfElementLocated(By.linkText(Actual_Property)));
			int i = 10;
			for(i=0;i<=15;i++)
			{
				if(Browser_Element1.isDisplayed())
				{
					Element_Action.moveToElement(Browser_Element1).perform();
					highLightElement(ObjDriver,Browser_Element1);
					break;
				}
				//Thread.sleep(100);
			}
		}
		if(Str_Property.startsWith("class="))
		{
			Actual_Property = Str_Property.substring(6);
			Browser_Element1 = ObjDriver.findElement(By.className(Actual_Property));
			//Wait_For_Object.until(ExpectedConditions. presenceOfElementLocated(By.className(Actual_Property)));
			int i = 10;
			for(i=0;i<=15;i++)
			{
				if(Browser_Element1.isDisplayed())
				{
					Element_Action.moveToElement(Browser_Element1).perform();
					highLightElement(ObjDriver,Browser_Element1);
					break;
				}
				//Thread.sleep(100);
			}
		}
		if(Str_Property.startsWith("partiallink="))
		{
			Actual_Property = Str_Property.substring(12);
			Browser_Element1 = ObjDriver.findElement(By.partialLinkText(Actual_Property));
			//Wait_For_Object.until(ExpectedConditions. presenceOfElementLocated(By.partialLinkText(Actual_Property)));
			int i = 10;
			for(i=0;i<=15;i++)
			{
				if(Browser_Element1.isDisplayed())
				{
					Element_Action.moveToElement(Browser_Element1).perform();
					highLightElement(ObjDriver,Browser_Element1);
					ReportLibrary.Error_Flag = true;
					break;
				}
				//Thread.sleep(100);
			}
		}
		Wait_For_Div();
		return Browser_Element1;
	}

	public static void Get_Locator(String Str_Property)
	{
		try
		{
			Browser_Element = Get_Locator_For_Exist(Str_Property);
		}
		catch(InvalidSelectorException excp1)
		{
			//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, excp1.toString(), LogStatus.FAIL, false);Exception_Content();
		}
		catch(StaleElementReferenceException excp2)
		{
			ObjDriver.navigate().refresh();
			Browser_Element = Get_Locator_For_Exist(Str_Property);
		}
		catch(InvalidElementStateException excp3)
		{
			//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, excp3.toString(), LogStatus.FAIL, false);Exception_Content();
		}
	}


	public static void Get_Locators(String Str_Property) throws Exception
	{
		Browser_Elements = null;
		String Actual_Property;
		Wait_For_Div();
		if(Str_Property.startsWith("id="))
		{
			try
			{
				Actual_Property = Str_Property.substring(3);

				Browser_Elements = ObjDriver.findElements(By.id(Actual_Property));
				//Wait_For_Object.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(Actual_Property)));
				//Wait_For_Object.until(ExpectedConditions.visibilityOfAllElements(Browser_Elements));

				highLightElement(ObjDriver,Browser_Elements.get(0));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("name="))
		{
			try
			{
				Actual_Property = Str_Property.substring(5);

				Browser_Elements = ObjDriver.findElements(By.name(Actual_Property));
				////Wait_For_Object.until(ExpectedConditions.visibilityOfAllElements(Browser_Elements));
				//Wait_For_Object.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(Actual_Property)));
				int cnt = Browser_Elements.size() - 1;
				Element_Action.moveToElement(Browser_Elements.get(cnt)).perform();
				highLightElement(ObjDriver,Browser_Elements.get(cnt));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("xpath="))
		{
			try
			{
				Actual_Property = Str_Property.substring(6);

				Browser_Elements = ObjDriver.findElements(By.xpath(Actual_Property));
				//Wait_For_Object.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(Actual_Property)));
				//Wait_For_Object.until(ExpectedConditions.visibilityOfAllElements(Browser_Elements));

				int cnt = Browser_Elements.size() - 1;
				Element_Action.moveToElement(Browser_Elements.get(cnt)).perform();
				highLightElement(ObjDriver,Browser_Elements.get(cnt));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("link="))
		{
			try
			{
				Actual_Property = Str_Property.substring(5);

				Browser_Elements = ObjDriver.findElements(By.linkText(Actual_Property));
				//Wait_For_Object.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(Actual_Property)));
				//Wait_For_Object.until(ExpectedConditions.visibilityOfAllElements(Browser_Elements));

				int cnt = Browser_Elements.size() - 1;
				Element_Action.moveToElement(Browser_Elements.get(cnt)).perform();
				highLightElement(ObjDriver,Browser_Elements.get(cnt));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("class="))
		{
			try
			{

				Actual_Property = Str_Property.substring(6);

				Browser_Elements = ObjDriver.findElements(By.className(Actual_Property));
				//Wait_For_Object.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(Actual_Property)));
				//Wait_For_Object.until(ExpectedConditions.visibilityOfAllElements(Browser_Elements));

				int cnt = Browser_Elements.size() - 1;
				Element_Action.moveToElement(Browser_Elements.get(cnt)).perform();
				highLightElement(ObjDriver,Browser_Elements.get(cnt));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("partiallink="))
		{
			try
			{
				Actual_Property = Str_Property.substring(12);
				//Wait_For_Object.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(Actual_Property)));
				Browser_Elements = ObjDriver.findElements(By.partialLinkText(Actual_Property));
				//Wait_For_Object.until(ExpectedConditions.visibilityOfAllElements(Browser_Elements));

				int cnt = Browser_Elements.size() - 1;
				Element_Action.moveToElement(Browser_Elements.get(cnt)).perform();
				highLightElement(ObjDriver,Browser_Elements.get(cnt));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("tagName="))
		{
			try
			{
				Actual_Property = Str_Property.substring(8);

				Browser_Elements = ObjDriver.findElements(By.tagName(Actual_Property));
				//Wait_For_Object.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(Actual_Property)));
				int cnt = Browser_Elements.size() - 1;
				Element_Action.moveToElement(Browser_Elements.get(cnt)).perform();
				highLightElement(ObjDriver,Browser_Elements.get(cnt));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		Wait_For_Div();
	}
	public static void Get_Locators_For_Div(String Str_Property) throws Exception
	{
		Browser_Elements_For_Div = null;
		String Actual_Property;
		if(Str_Property.startsWith("id="))
		{
			try
			{
				Actual_Property = Str_Property.substring(3);
				Browser_Elements_For_Div = ObjDriver.findElements(By.id(Actual_Property));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("name="))
		{
			try
			{
				Actual_Property = Str_Property.substring(5);
				Browser_Elements_For_Div = ObjDriver.findElements(By.name(Actual_Property));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("xpath="))
		{
			try
			{
				Actual_Property = Str_Property.substring(6);
				Browser_Elements_For_Div = ObjDriver.findElements(By.xpath(Actual_Property));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("link="))
		{
			try
			{
				Actual_Property = Str_Property.substring(5);
				Browser_Elements_For_Div = ObjDriver.findElements(By.linkText(Actual_Property));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("class="))
		{
			try
			{

				Actual_Property = Str_Property.substring(6);
				Browser_Elements_For_Div = ObjDriver.findElements(By.className(Actual_Property));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("partiallink="))
		{
			try
			{
				Actual_Property = Str_Property.substring(12);
				Browser_Elements_For_Div = ObjDriver.findElements(By.partialLinkText(Actual_Property));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}
		if(Str_Property.startsWith("tagName="))
		{
			try
			{
				Actual_Property = Str_Property.substring(8);
				Browser_Elements_For_Div = ObjDriver.findElements(By.tagName(Actual_Property));
			}
			catch(Exception e)
			{
				//ConfigurationLibrary.Sound_Beep();
				e.printStackTrace();
				Exception_Content();
			}
		}

	}

	public static void Ajax_Wait() throws InterruptedException
	{
//    try
//    {  Thread.sleep(500);
//       int i;
//       WebElement Ajax = ObjDriver.findElement(By.xpath("xpath=//*[contains(@id,'loadingPanelContentTable')]"));
//       if(!(Ajax==null))
//       {
//          for(i=0;i<=40;i++)
//          {
//             if(!Ajax.isDisplayed())
//             {
//                break;
//             }
//             Thread.sleep(500);
//          }
//       }
//    }
//    catch(Exception e)
//    {
//       e.printStackTrace();
//    }
		//By loader = By.xpath("xpath=//*[contains(@id,'loadingPanelContentTable')]");



//    Thread.sleep(500);
//    By loader = By.className("rich-modalpanel Loading");
//    WebDriverWait wait = new WebDriverWait(ObjDriver, 1500);
//    wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));


		ObjDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	public static void waitForAjax2(int s)
	{
		JavascriptExecutor js = (JavascriptExecutor)ObjDriver;
		//Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete"))
		{
			System.out.println("Page Is loaded.");
			return;
		}
		//This loop will rotate for 25 times to check If page Is ready after every 1 second.
		//You can replace your value with 25 If you wants to Increase or decrease wait time.
		for (int i=0; i<25; i++)
		{
			try
			{
				Thread.sleep(1000);

			}
			catch (InterruptedException e) {}
			//To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete"))
			{
				break;
			}
		}
	}

	public static void waitForAjax3(int timeoutInSeconds) throws InterruptedException {
		System.out.println("Checking active ajax calls by calling jquery.active");
		try {
			if (ObjDriver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor)ObjDriver;
				for (int i = 0; i< timeoutInSeconds; i++)
				{
					Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
					// return should be a number
					if (numberOfAjaxConnections instanceof Long) {
						Long n = (Long)numberOfAjaxConnections;
						System.out.println("Number of active jquery ajax calls: " + n);
						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(1000);
				}
			}
			else {
				System.out.println("Web driver: " + ObjDriver + " cannot execute javascript");
			}
		}
		catch (InterruptedException e) {
			//ConfigurationLibrary.Sound_Beep();
			System.out.println(e);
		}
	}

	public static void WaitForModalPanel_Disapear() throws InterruptedException
	{
		Thread.sleep(500);
		int Wait_Iterate;
		for(Wait_Iterate = 0;Wait_Iterate<16;Wait_Iterate++)
		{

			//Boolean present = //Wait_For_Object.until(ExpectedConditions.not(ExpectedConditions.
			// visibilityOfElementLocated(By.xpath("//*[contains(@id,'loadingPanelContainer')]"))));
			if(!ObjDriver.findElement(By.xpath("//*[@id='menu_form_id:loadingPanelDiv']")).isDisplayed())
			{
				break;
			}
			else
			{
				Thread.sleep(1000);
			}
		}
	}

	public static void Exception_Content()
	{
		Browser_Element = null;
		ReportLibrary.Error_Flag = true;
		//Function_Library.Close_All_Active_Browser();
		//Report_Library.End_Test();
	}

	public static Boolean MouseOver_Element(String Text_Property) throws Exception
	{
		Boolean ret;
		ret = false;
//       WebElement Element = null;
//       Element = Get_Element(Text_Property);
//       if(Element != null)
//       {
//          Element_Action.moveToElement(Element).build().perform();
//          ret = true;
//       }
		return ret;
	}

	public static void MouseOver(String Element_Property,String Event,String Description) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Boolean ret;
			ret = false;
			WebElement Element = null;
			String[] Element_PropertyArray = Element_Property.split(";");
			Thread.sleep(1000);
			for(String Pro:Element_PropertyArray)
			{
				Element = Get_Element(Pro);
				if(Element != null)
				{
					if(Description!="")
					{
						Element_Action.moveToElement(Element).build().perform();
					}
					ret = true;
				}
				else
				{
					if(Description!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Description, LogStatus.PASS, false);
					}
					break;
				}
			}
			if(ret)
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Description, LogStatus.PASS, false);
			}
		}
	}

	public static void setText(String Text_Property,String Text_Val,String Desc)
	{
		if(!Text_Property.matches(""))
		{
			//Thread.sleep(500);
			Wait_For_Div();

			WebElement TextBox = null;

			TextBox = Get_Locator_For_Exist(Text_Property);

			if(TextBox == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + ": " +Text_Val, LogStatus.FAIL, true);Exception_Content();

				}
			}
			else
			{
				try
				{

					TextBox.clear();
					//TextBox.sendKeys("");
					TextBox.sendKeys(Text_Val);
					TextBox.sendKeys(Keys.TAB);
					//WaitForModalPanel_Disapear();
					//Ajax_Wait();
					//waitForAjax(1500);
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ": " +Text_Val, LogStatus.PASS, false);
					}
				}
				catch(InvalidElementStateException ee)
				{
					//ConfigurationLibrary.Sound_Beep();
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ": " +Text_Val, LogStatus.FAIL, true);
					ee.printStackTrace();
					Exception_Content();
				}
			}
		}
	}

	//Function used to get the value and store in Excel sheet
	public static String getText(String Text_Property,String Event,String Desc) {
		String Text_Val ="";
		if (!Event.matches("NA")) {
			//Thread.sleep(500);

			WebElement TextBox = null;
			TextBox = Get_Element(Text_Property);
			if (TextBox == null) {
				if (Desc!="") {
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);
					Exception_Content();
				}
			} else {
				Text_Val = TextBox.getAttribute("value");
				//ConfigurationLibrary.Set_Active_Cell_Value_overwrite(1,Text_Val); // Note : The cell below the function should be blank
				if (Desc!="") {
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + ": " + Text_Val, LogStatus.PASS, true);
				}
			}


		}
		return Text_Val;
	}

	public static String getText_Update(String Text_Property,String Event,String Desc) throws Exception
	{
		String Text_Val = null;
		if(!Event.matches("NA"))
		{
			Thread.sleep(500);

			WebElement TextBox = null;
			TextBox = Get_Element(Text_Property);
			if(TextBox == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc , LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Text_Val = TextBox.getText();
				// Note : The cell below the function will be over write if something is there before
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ": " +Text_Val, LogStatus.PASS, false);
				}

			}
		}
		return Text_Val;
	}

	public static void Verify_TextBox_Value(String Text_Property,String Expected_Value,String Desc) throws Exception
	{
		if(!Expected_Value.matches("NA"))
		{
			String Actual_Text_Val;
			WebElement TextBox = null;
			TextBox = Get_Element(Text_Property);
			if(TextBox == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc , LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Actual_Text_Val = TextBox.getAttribute("value");
				if(Actual_Text_Val.equalsIgnoreCase(Expected_Value))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ": Actual Value :" + Actual_Text_Val + ", Expected Value : " + Expected_Value, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ": Actual Value :" + Actual_Text_Val + ", Expected Value : " + Expected_Value, LogStatus.FAIL, true);Exception_Content();
					}
				}

			}
		}
	}

	public static void Verify_TextBox_Contains_Data(String Text_Property,String Event1,String Desc) throws Exception
	{
		if(!Event1.matches("NA"))
		{
			String Actual_Text_Val;
			WebElement TextBox = null;
			TextBox = Get_Element(Text_Property);
			if(TextBox == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc , LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Actual_Text_Val = TextBox.getAttribute("value");
				if(Actual_Text_Val!="")
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ": Actual Value :" + Actual_Text_Val , LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ": Actual Value :" + Actual_Text_Val , LogStatus.FAIL, true);Exception_Content();
					}
				}

			}
		}
	}

	public static void Select_DropDownList(String DropDownList_Property,String DropDownList_Val,String Desc) throws Exception
	{
		if(!DropDownList_Val.matches("NA"))
		{
			Thread.sleep(500);
			WebElement Element = null;
			Element = Get_Element(DropDownList_Property);
			if(Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+DropDownList_Val, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Select DropDownList = new Select(Element);
				DropDownList.selectByVisibleText(DropDownList_Val) ;
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": "+DropDownList_Val, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void Select_Last_DropDownList(String DropDownList_Property,String DropDownList_Val,String Desc) throws Exception
	{
		if(!DropDownList_Val.matches("NA"))
		{
			Thread.sleep(1000);
			List<WebElement> Objects = null;
			WebElement Object = null;
			Objects = Get_Elements(DropDownList_Property);
			if(Objects == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : " + DropDownList_Val, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				int Element_Cnt = Objects.size();
				Object = Objects.get(Element_Cnt-1);
				Select DropDownList = new Select(Object);
				DropDownList.selectByVisibleText(DropDownList_Val);
				//Ajax_Wait();
				//waitForAjax(1500);
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : " + DropDownList_Val, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void selectDropDownListByValue(String DropDownList_Property,String DropDownList_Val,String Desc)
	{
		if(!DropDownList_Val.matches("NA"))
		{
			WebElement Element = null;
			Element = Get_Element(DropDownList_Property);
			if(Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+DropDownList_Val, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Select DropDownList = new Select(Element);
				DropDownList.selectByValue(DropDownList_Val);
				//Ajax_Wait();
				//waitForAjax(1500);
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": "+DropDownList_Val, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void Select_DropDownList_ByIndex(String DropDownList_Property,String DropDownList_Option_Index,String Desc) throws Exception
	{

		if(! DropDownList_Option_Index.matches("NA"))
		{
			WebElement Element = null;
			Element = Get_Element(DropDownList_Property);
			if(Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+DropDownList_Option_Index, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Select DropDownList = new Select(Element);
				DropDownList.selectByIndex(Integer.parseInt(DropDownList_Option_Index));
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": "+DropDownList_Option_Index, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void Verify_DropDownList_Selected_Option(String DropDownList_Property,String Expected_Option,String Desc) throws Exception
	{
		if(!Expected_Option.matches("NA"))
		{
			String Actual_Option;
			WebElement Element = null;
			Element = Get_Element(DropDownList_Property);
			if(Element == null)
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+Expected_Option, LogStatus.FAIL, true);Exception_Content();
			}
			else
			{
				Select DropDownList = new Select(Element);
				Actual_Option = DropDownList.getFirstSelectedOption().getText();
				if(Actual_Option.equalsIgnoreCase(Expected_Option))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": Expected Option:"+Expected_Option+", Actual Option: "+Actual_Option, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": Expected Option:"+Expected_Option+", Actual Option: "+Actual_Option, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
		}
	}

	public static void Verify_DropDownList_Options(String DropDownList_Property,String Expected_Options,String Desc) throws Exception
	{
		if(!Expected_Options.matches("NA"))
		{
			Thread.sleep(500);
			String Final_OutPut = "";
			List <WebElement> Actual_Option_List;
			String[] Expected_Option_List;
			WebElement Element = null;
			Element = Get_Element(DropDownList_Property);
			if(Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+Expected_Options, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Select DropDownList = new Select(Element);
				Actual_Option_List = DropDownList.getOptions();
				Expected_Option_List = Expected_Options.split(";");
				int i;
				int j;
				int Exist;
				for(i=0;i<Expected_Option_List.length;i++)
				{
					Exist = 0;
					for(j=0;j<Actual_Option_List.size();j++)
					{
						if(Expected_Option_List[i].toString().equalsIgnoreCase(Actual_Option_List.get(j).getText()))
						{
							Exist = Exist +1;
							break;
						}
					}
					if(Exist>0)
					{
						Final_OutPut = Final_OutPut + Expected_Option_List[i].toString()+ ": Exist ,";
					}
					else
					{
						Final_OutPut = Final_OutPut + Expected_Option_List[i].toString()+ ": Not Exist ,";
					}
				}
				if(!Final_OutPut.contains("Not Exist"))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": Expected Option:"+Final_OutPut, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": Expected Option:"+Final_OutPut, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
		}
	}

	public static void Verify_DropDownList_Options_Not_Exist(String DropDownList_Property,String Expected_Options,String Desc) throws Exception
	{
		if(!Expected_Options.matches("NA"))
		{
			Thread.sleep(500);
			String Final_OutPut = "";
			List <WebElement> Actual_Option_List;
			String[] Expected_Option_List;
			WebElement Element = null;
			Element = Get_Element(DropDownList_Property);
			if(Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+Expected_Options, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Select DropDownList = new Select(Element);
				Actual_Option_List = DropDownList.getOptions();
				Expected_Option_List = Expected_Options.split(";");
				int i;
				int j;
				int Exist;
				for(i=0;i<Expected_Option_List.length;i++)
				{
					Exist = 0;
					for(j=0;j<Actual_Option_List.size();j++)
					{
						if(Expected_Option_List[i].toString().equalsIgnoreCase(Actual_Option_List.get(j).getText()))
						{
							Exist = Exist +1;
							break;
						}
					}
					if(Exist>0)
					{
						Final_OutPut = Final_OutPut + Expected_Option_List[i].toString()+ ": Exist ,";
					}
					else
					{
						Final_OutPut = Final_OutPut + Expected_Option_List[i].toString()+ ": Not Exist ,";
					}
				}
				if(Final_OutPut.contains("Not Exist"))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": Expected Option:"+Final_OutPut, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": Expected Option:"+Final_OutPut, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
		}
	}

	public static void setCheckBox(String CheckBox_Pro,String CheckBox_Val,String Desc)
	{

		if(!CheckBox_Val.toString().matches("NA"))
		{
			String IsChecked;
			WebElement CheckBox = null;
			CheckBox = Get_Element(CheckBox_Pro);

			Wait_For_Object.until(ExpectedConditions.visibilityOf(CheckBox));
			Wait_For_Object.until(ExpectedConditions.elementToBeClickable(CheckBox));
			if(CheckBox == null)
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
			}
			else
			{
				IsChecked = CheckBox.getAttribute("value");
				if(CheckBox_Val.equals("Yes"))
				{

					if(IsChecked!="true" )
					{
						CheckBox.click() ;
					}
				}
				else if(CheckBox_Val.matches("No"))
				{
					if(IsChecked!="true")
					{
						//// Element_Action.click(CheckBox).build().perform();
						CheckBox.click() ;
					}
				}

				else if(CheckBox_Val.matches("Yes"))
				{
					if(IsChecked==null)
					{
						CheckBox.click() ;
					}
				}

			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void Click_RadioButton(String RadioButton_Pro,int ButtonPosition,Boolean RadioButton_Val,String Desc) throws Exception
	{
		if(!RadioButton_Val.toString().matches("NA"))
		{
			List <WebElement> RadioButton = null;
			RadioButton = (List<WebElement>) Get_Element(RadioButton_Pro);
			if(RadioButton == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				RadioButton.get(ButtonPosition).click() ;
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void Click_RadioButton_Edited(String RadioButton_Pro,String ButtonPosition,String Desc) throws Exception
	{
		if(ButtonPosition.matches("NA"))
		{
			List <WebElement> RadioButton = null;
			RadioButton = (List<WebElement>) Get_Element(RadioButton_Pro);
			if(RadioButton == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				RadioButton.get(Integer.parseInt(ButtonPosition)).click() ;
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void clickObject(String Object_Name_Pro,String Event,String Desc)
	{
		if(!Event.matches("NA"))
		{
			WebElement Object = null;
			try
			{

				Object = Get_Element(Object_Name_Pro);
				Wait_For_Object.until(ExpectedConditions.visibilityOf(Object));
				Wait_For_Object.until(ExpectedConditions.elementToBeClickable(Object));

				JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.ObjDriver;
				//js.executeScript("return document.readyState").toString().equals("complete");

				////Wait_For_Object.until(ExpectedConditions.stalenessOf(Object));

				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
			highLightElement(ObjDriver,Object);

			try
			{
				Object.click();
			}
			catch (WebDriverException e)
			{

				//JavascriptExecutor js = (JavascriptExecutor)FunctionLibrary.ObjDriver;
				//js.executeScript("return document.readyState").toString().equals("complete");
				//System.out.println(js.executeScript("return document.readyState").toString());
				new Actions(FunctionLibrary.ObjDriver).moveToElement(Object).perform();
				new Actions(FunctionLibrary.ObjDriver).click().perform();

			}
					Wait_For_Div();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}
			catch (StaleElementReferenceException elementHasDisappeared) {
				//ConfigurationLibrary.Sound_Beep();
				Object = Get_Element(Object_Name_Pro);
				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Expected:" +Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					Object.click();
					Wait_For_Div();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}

		}
	}

	public static void clickObject_With_Wait(String Object_Name_Pro,String Wait_Time,String Desc)
	{
		if(!Wait_Time.matches("NA"))
		{
			//Wait_For_Div();
			WebElement Object = null;
			try
			{
				//Thread.sleep(1000);
				Object = Get_Element(Object_Name_Pro);
				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					Object.click();
					//Thread.sleep(2000);

					//Thread.sleep(Integer.parseInt(Wait_Time));
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}

			catch(InvalidSelectorException excp1)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +"\n"+excp1, LogStatus.FAIL, true);Exception_Content();
				}
			}

			catch (StaleElementReferenceException elementHasDisappeared)
			{
				Object = Get_Element(Object_Name_Pro);
				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					Object.click();
					//Thread.sleep(Integer.parseInt(Wait_Time));
					Wait_For_Div();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}
		}
	}

	public static void DblclickObject(String Object_Name_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			WebElement Object = null;
			Object = Get_Element(Object_Name_Pro);
			if(Object == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Element_Action.doubleClick(Object).build().perform();
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void Click_Tab(String Object_Name_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			if(Desc.matches("Click on Hierarchie Tab"))
			{
				WebElement Object = null;
			}
			WebElement Object = null;
			try
			{
				Thread.sleep(500);
				//Function_Library.Activate_Latest_Browser("yes","Activating New Browser");
				Activate_Browsers();
				Object = Get_Element(Object_Name_Pro);
				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					if(Object.isDisplayed())
					{
						Object.click();
						if(Desc!="")
						{
							//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
						}
					}

				}
			}
			catch (StaleElementReferenceException elementHasDisappeared)
			{
				//ConfigurationLibrary.Sound_Beep();
				Object = Get_Element(Object_Name_Pro);
				if(Object.isDisplayed())
				{
					Object.click();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}

		}
	}

	public static String Capture_Screen_Shot(String ScreenShot_Location)
	{
		Screen_Shot = ((TakesScreenshot)ObjDriver).getScreenshotAs(OutputType.FILE);
		Screen_Shot.renameTo(new File (ScreenShot_Location));

		return ScreenShot_Location;
	}

	public String Get_TimeStamp()
	{
		Long l = Calendar.getInstance().getTimeInMillis();
		return l.toString();
	}

	public static int Get_WebTable_Row_Count(WebElement Table_Element) throws Exception
	{
		return Table_Element.findElements(By.tagName("tr")).size();
	}

	public static int Get_WebTable_Column_Count(WebElement Table_Element) throws Exception
	{
		return Table_Element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).size();
	}


	public static void Verify_WebTable_Data(String WebElement_Pro,String ExpectedText,String Desc) throws Exception
	{
		if(!ExpectedText.matches("NA"))
		{
			int Row;
			int Column;
			int iterate;
			int iterate1;
			Boolean ExistFlg = false;
			WebElement Element = null;
			String text = repalceStrings(ExpectedText);
			Element = Get_Element(WebElement_Pro);
			Row = Element.findElements(By.tagName("tr")).size();
			Column = Element.findElements(By.tagName("th")).size();// Element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).size();
			for(iterate = 0; iterate< Row;iterate++)
			{
				for(iterate1 = 0; iterate1< Column;iterate1++)
				{
					if(text.equalsIgnoreCase(Element.findElements(By.tagName("td")).get(iterate1).getText()))
					{
						highLightElement(ObjDriver,Element.findElements(By.tagName("td")).get(iterate1));
						ExistFlg = true;
						break;
					}
				}
				if(ExistFlg)
				{
					break;
				}
			}

			if(ExistFlg)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText, LogStatus.PASS, true);
				}
			}
			else
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}
	}

	public static void Verify_WebTable_Data_Not_Exist(String WebElement_Pro,String ExpectedText,String Desc) throws Exception
	{
		if(!ExpectedText.matches("NA"))
		{
			int Row;
			int Column;
			int iterate;
			int iterate1;
			Boolean ExistFlg = false;
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);
			Row = Element.findElements(By.tagName("tr")).size();
			Column = Element.findElements(By.tagName("th")).size();// Element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).size();
			for(iterate = 0; iterate< Row;iterate++)
			{
				for(iterate1 = 0; iterate1< Column;iterate1++)
				{
					if(ExpectedText.equalsIgnoreCase(Element.findElements(By.tagName("td")).get(iterate1).getText()))
					{
						highLightElement(ObjDriver,Element.findElements(By.tagName("td")).get(iterate1));
						ExistFlg = true;
						break;
					}
				}
				if(ExistFlg)
				{
					break;
				}
			}

			if(!ExistFlg)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText, LogStatus.PASS, true);
				}
			}
			else
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}
	}

	public static void Verify_WebTable_Headers(String WebElement_Pro,String WebTable_Headers,String Desc) throws Exception
	{
		if(!WebTable_Headers.matches("NA"))
		{
			int Row;
			int Column;
			int iterate = 0;
			String Actual_Val;
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);
			List<WebElement> AllHeaders = Element.findElements(By.tagName("th"));
			String[] WebTable_Headers_arr = WebTable_Headers.split(";");
			for(WebElement Header : AllHeaders)
			{
				Actual_Val = WebTable_Headers_arr[iterate];
				if(Header.getText().equalsIgnoreCase(Actual_Val))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Header " + Header.getText() + " is present in the table", LogStatus.PASS, false);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Header " + Header.getText() + " is not present in the table", LogStatus.FAIL, true);Exception_Content();
					}
				}
				iterate++;
			}
		}
	}

	public static void Verify_TextPresent(String Verfy_TextValue,String Desc) throws Exception
	{
		if(!Verfy_TextValue.matches("NA"))
		{
			Thread.sleep(2000);
			Wait_For_Div();
			Thread.sleep(2000);
			Activate_Browsers();
			//ObjDriver.getPageSource()
			String verfytextval2 = repalceStrings(Verfy_TextValue);
			//System.out.println(verfytextval2);
			if(ObjDriver.findElement(By.tagName("body")).getText().contains(verfytextval2))
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" : Expected Value : "+Verfy_TextValue, LogStatus.PASS, true);
				}
			}
			else if(ObjDriver.getPageSource().contains(verfytextval2))
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" : Expected Value : "+Verfy_TextValue, LogStatus.PASS, true);
				}
			}
			else
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" : Expected Value : "+Verfy_TextValue, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}
	}

	public static void Verify_Excel_Cell_Data(String Expected_Value,String Desc) throws Exception
	{
		if(!Expected_Value.matches("NA"))
		{
			//ConfigurationLibrary.Verify_Excel_Contains_Data(Expected_Value,Desc);
		}
	}

	public static void Get_WebElement_Text(String WebElement_Pro,String Event,String Desc) throws Exception
	{
		String Ret;
		Ret = null;
		if(!Event.matches("NA"))
		{
			Thread.sleep(1000);
			List<WebElement> Elements = null;
			WebElement Element = null;
			String Actual_Property = WebElement_Pro.substring(6);
			Elements = Get_Elements(WebElement_Pro);// Browser_Elements = ObjDriver.findElements(By.className(Actual_Property));
			if(Elements == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" Value : "+Ret, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				int i;
				int Element_Cnt = Elements.size();
				for(i=0;i<=Element_Cnt;i++)
				{
					Element = Elements.get(i);
					if(Element.isDisplayed())
					{
						Ret = Element.getText();
						//ConfigurationLibrary.Set_Active_Cell_Value(1,Ret);
						if(Desc!="")
						{
							//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" Value : "+Ret, LogStatus.PASS, true);
						}
					}
					else
					{
						if(Desc!="")
						{
							//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" Value : "+Ret, LogStatus.FAIL, true);Exception_Content();
						}
					}
					break;
				}
			}
		}
	}

	public static void Verify_WebElement_Actual(String WebElement_Pro,String Expected_Value,String Desc) throws Exception
	{
		if(!Expected_Value.matches("NA"))
		{
			Thread.sleep(1000);
			List<WebElement> Elements = null;
			WebElement Element = null;
			String Actual_Property = WebElement_Pro.substring(6);
			Elements = Get_Elements(WebElement_Pro);// Browser_Elements = ObjDriver.findElements(By.className(Actual_Property));
			if(Elements == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+"Expected Value : "+Expected_Value, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				int i;
				int Element_Cnt = Elements.size();
				for(i=0;i<=Element_Cnt;i++)
				{
					Element = Elements.get(i);
					if(Element.isDisplayed())
					{
						int x = Element.getText().compareTo(Expected_Value);
						if(x == 0)
						{
							if(Desc!="")
							{
								//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+"Expected Value : "+Expected_Value, LogStatus.PASS, true);
							}
						}
						else
						{
							if(Desc!="")
							{
								//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+"Expected Value : "+Expected_Value, LogStatus.FAIL, true);Exception_Content();
							}
						}
						break;
					}
				}
			}
		}
	}

	public static boolean verifyWebElementExist(String WebElement_Pro,String Desc)
	{
		boolean isExist = false;
		if(!WebElement_Pro.matches("NA"))
		{
			//Thread.sleep(1000);
			List<WebElement> Elements = null;
			WebElement Element = null;
			String Actual_Property = WebElement_Pro.substring(6);
			if(Get_Locator_For_Exist(WebElement_Pro) == null)
			{
				if(Desc!="")
				{

					////ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+"Elememnt is not present: " + WebElement_Pro, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				if(Get_Locator_For_Exist(WebElement_Pro).isDisplayed())
				{
					isExist=true;
					////ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+"Element : "+WebElement_Pro, LogStatus.PASS, true);
				}
			}

		}
		return  isExist;
	}



	public static void Verify_WebElement_Actual_Contains(String WebElement_Pro,String Expected_Value,String Desc) throws Exception
	{
		if(!Expected_Value.matches("NA"))
		{
			Thread.sleep(1000);
			List<WebElement> Elements = null;
			WebElement Element = null;
			String Actual_Property = WebElement_Pro.substring(6);
			Elements = Get_Elements(WebElement_Pro);// Browser_Elements = ObjDriver.findElements(By.className(Actual_Property));
			if(Elements == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+"Expected Value : "+Expected_Value, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				int i;
				int Element_Cnt = Elements.size();
				for(i=0;i<=Element_Cnt;i++)
				{
					Element = Elements.get(i);
					if(Element.isDisplayed())
					{
						int a= Expected_Value.length();
						int b= Element.getText().length();
						Boolean x;
						if(a>=b)
						{
							x = Expected_Value.contains(Element.getText())  ;
						}
						else
						{
							x = Element.getText().contains(Expected_Value);
						}

						if(x)
						{
							if(Desc!="")
							{
								//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+"Expected Value : "+Expected_Value, LogStatus.PASS, true);
							}
						}
						else
						{
							if(Desc!="")
							{
								//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+"Expected Value : "+Expected_Value, LogStatus.FAIL, true);Exception_Content();
							}
						}
						break;
					}
				}
			}
		}
	}
	public static boolean Verify_WebElement_Enabled(String WebElement_Pro,String Event,String Desc)
	{
		if(!Event.matches("NA"))
		{
			try {
				Thread.sleep(500);

			}
			catch(Exception e)
			{

			}

			Wait_For_Div();
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);
			if(Element == null)
			{
				if(Desc!="")
				{
					////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				if(Element.isEnabled())
				{
					if(Desc!="")
					{
						return true;
						////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						return true;
						////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
		}
		return false;
	}

	public static void Verify_WebElement_Disabled(String WebElement_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			WebElement Element = null;
			Thread.sleep(2000);
			Element = Get_Element(WebElement_Pro);
			if(Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				if(!Element.isEnabled())
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
		}
	}

	public static void Verify_WebElement_Not_Editable(String WebElement_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);
			if(Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				String Attribute  = Element.getAttribute("disabled");
				if(Attribute!="")
				{
					if(Attribute.matches("true"))
					{
						if(Desc!="")
						{
							//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
						}
					}
					else
					{
						if(Desc!="")
						{
							//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
						}
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
		}
	}

	public static void verifyWebElementExist_XPATH(String WebElement_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Thread.sleep(500);
			WebElement Element = null;
			WebElement_Pro = WebElement_Pro.substring(6);
			if(ObjDriver.findElements(By.xpath(WebElement_Pro)).size()>0)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
				}
			}
//       if(Element == null)
//       {
//          if(Desc!="")
//          {
//             Report_Library.Add_Step(Report_Library.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
//          }
//       }
			else
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}
	}

	public static void Verify_WebElement_Not_Exist_XPATH(String WebElement_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Thread.sleep(500);
			WebElement Element = null;
			WebElement_Pro = WebElement_Pro.substring(6);
			if(ObjDriver.findElements(By.xpath(WebElement_Pro)).size()>0)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
				}
			}
		}
	}

	/*public static void Verify_WebElement_Enabled(String WebElement_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Thread.sleep(500);
			Wait_For_Div();
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);
			if(Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				if(Element.isEnabled())
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
		}
	}
*/
	public static void Verify_WebElement_ReadOnly(String WebElement_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Thread.sleep(500);
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);
			if(Element == null)
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
			}
			else
			{
				if(Element.getAttribute("readOnly").equals("true")||Element.getAttribute("readonly").equalsIgnoreCase(""))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
		}
	}

	public static void Verify_Cell_Value_Order_Ascending(String Value_Order,String Desc) throws Exception
	{
		if(!Value_Order.matches("NA"))
		{

			List<String> value_Order_List = new ArrayList<String>(Arrays.asList(Value_Order.split(";")));
			if(value_Order_List.size()== 1)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "First Value :"+ value_Order_List.get(0).toString(), LogStatus.PASS, true);
				}
			}
			if(value_Order_List.size() > 1)
			{
				List<String> value_Order_List_Temp = new ArrayList<String>(value_Order_List);
				Collections.sort(value_Order_List_Temp);
				if(value_Order_List_Temp.equals(value_Order_List))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " :"+ Value_Order, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " :"+ Value_Order, LogStatus.FAIL, true);Exception_Content();
					}
				}

			}
		}
	}

	// Element highlighter code
	public static void highLightElement(WebDriver driver, WebElement element)
	{
		try
		{
			JavascriptExecutor js=(JavascriptExecutor)driver;

			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				//ConfigurationLibrary.Sound_Beep();
				System.out.println(e.getMessage());
			}

			js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element);
		}
		catch(WebDriverException e)
		{
			//ConfigurationLibrary.Sound_Beep();
		}
	}

	public static void Soft_Wait(Integer MS,String Desc) throws Exception
	{
		if(!MS.toString().matches("NA"))
		{
			Thread.sleep(MS);
			if(Desc!="")
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
			}
		}
	}

	public static void Soft_Wait(String MS,String Desc) throws Exception
	{
		if(!MS.matches("NA"))
		{
			Thread.sleep(Integer.parseInt(MS));
			if(Desc!="")
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
			}
		}
	}

	public static void Click_Last_Pluss_Button(String Object_Name_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Thread.sleep(800);
			Wait_For_Div();
			WebElement Object = null;
			String Actual_Property = Object_Name_Pro.substring(6);
			Browser_Elements = ObjDriver.findElements(By.className(Actual_Property));
			////Wait_For_Object.until(ExpectedConditions.visibilityOfAllElements(Browser_Elements));
			if(Browser_Elements == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				int Element_Cnt = Browser_Elements.size();

				Object = Browser_Elements.get(Element_Cnt-1);
				if(Object.isDisplayed())
				{
					Object.click();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}
		}
	}

	public static void Click_Pluss_Button_ByIndex(String Object_Name_Pro,String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Wait_For_Div();
			List<WebElement> Objects = null;
			WebElement Object = null;
			String Actual_Property = Object_Name_Pro.substring(6);
			Browser_Elements = ObjDriver.findElements(By.className(Actual_Property));
			if(Browser_Elements == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{

				int Element_Cnt = Browser_Elements.size();

				Object = Browser_Elements.get(Element_Cnt);
				if(Object.isDisplayed())
				{
					Object.click();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}
		}
	}

	public static void Click_WebTable_Data(String WebElement_Pro,String ExpectedText,String Desc) throws Exception
	{
		if(!ExpectedText.matches("NA"))
		{
			int Row;
			int Column;
			int iterate;
			int iterate1;
			WebElement Element = null;
			WebElement Actual_Element = null;
			Element = Get_Element(WebElement_Pro);
			List<WebElement> element2 = Element.findElements(By.tagName("tr"));
			Row=element2.size();
			System.out.println(Row);
			//Row = Element.findElements(By.tagName("tr")).size();
			Column = Element.findElements(By.tagName("td")).size();
			System.out.println(Row);
			// Element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).size();
         /*List<WebElement> rows=Element.findElements(By.tagName("tr"));

         for(int rnum=0;rnum<rows.size();rnum++)
         {
         List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
         System.out.println("Number of columns:"+columns.size());
         for(int cnum=0;cnum<columns.size();cnum++)
         {
         System.out.println(columns.get(cnum).getText());
         }
         } */


			for(iterate = 0; iterate< Row;iterate++)
			{
				for(iterate1 = 0; iterate1< Column;iterate1++)
				{
					Actual_Element = Element.findElements(By.tagName("td")).get(iterate1);
					if(Actual_Element.isEnabled())
					{
						if(ExpectedText.equalsIgnoreCase(Element.findElements(By.tagName("td")).get(iterate1).getText()))
						{
							highLightElement(ObjDriver,Element.findElements(By.tagName("td")).get(iterate1));

							Actual_Element.click();
							break;
						}
					}
				}
			}
			Thread.sleep(2000);
		}
	}

	public static void Click_Table_Row(String Table_Pro,String Row,String Desc) throws Exception
	{
		if(!Row.matches("NA"))
		{
			int Row1;
			WebElement Table_Element = null;
			WebElement Table_Row = null;
			try
			{
				Table_Element = Get_Element(Table_Pro);
			}
			catch(StaleElementReferenceException ee)
			{
				Table_Element = Get_Element(Table_Pro);
			}
			catch(Exception e)
			{
				Table_Element = Get_Element(Table_Pro);
			}
			if(Table_Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				Table_Row = Table_Element.findElement(By.xpath("//tbody/tr[@id='row"+ Row + "'" + "]"));
				highLightElement(ObjDriver,Table_Row);
				Element_Action.click(Table_Row).build().perform();
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void Dbl_Click_Table_Cell_By_RowCol(String Table_Pro,String RowCol,String Desc) throws Exception
	{
		if(!RowCol.matches("NA"))
		{
			int Row;
			int Column;
			WebElement Table_Element = null;
			WebElement Table_Row = null;
			WebElement Cell_We_Need = null;

			Table_Element = Get_Element(Table_Pro);

			if(Table_Element == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				String[] RowCol_Val = RowCol.split(":");
				Row = Integer.parseInt(RowCol_Val[0]);
				Column = Integer.parseInt(RowCol_Val[1]);

				Table_Row = Table_Element.findElement(By.xpath("//tbody/tr[@id='row"+ Row + "'" + "]"));
				//System.out.println(Table_Row.getText());
				highLightElement(ObjDriver,Table_Row);
				Cell_We_Need = Table_Row.findElement(By.xpath("//td[" + Column + "]"));
				//System.out.println(Cell_We_Need.getText());
				//highLightElement(ObjDriver,Cell_We_Need);
				Element_Action.doubleClick(Table_Row).build().perform();
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void Click_Image_By_Index(String Image_Property,String Image_Index,String Desc) throws Exception
	{
		if(!Image_Index.matches("NA"))
		{
			Thread.sleep(800);
			List<WebElement> Images = null;
			WebElement Image = null;
			Images = Get_Elements(Image_Property);
			if(Images == null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
				}
			}
			else
			{
				int Element_Cnt = Images.size();
				Image = Images.get(Integer.parseInt(Image_Index));
				Element_Action.moveToElement(Image).perform();
				Image.click();
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
				}
			}
		}
	}

	public static void Wait_For_Div()
	{
		try
		{
			//Thread.sleep(1000);

			for(int i=0;i<=60;i++)
			{
				Activate_Browsers();
				List<WebElement> Div_Objects = null;
				WebElement Div_Object = null;
				Div_Objects = Get_Elements_For_Div("tagName=div");

				int Div_Element_Cnt = Div_Objects.size();
				Boolean Flg;
				Flg = false;
				Div_Object = Div_Objects.get(Div_Element_Cnt-1);
				if((!Div_Object.getAttribute("id").contains("loadingPanelCursorDiv")))
				{
					break;
				}
				Thread.sleep(700);
			}

		}
		catch(StaleElementReferenceException e)
		{
			//ConfigurationLibrary.Sound_Beep();
			e.printStackTrace();
			Activate_Browsers();
		}
		catch(Exception e)
		{
			//ConfigurationLibrary.Sound_Beep();
			e.printStackTrace();
			Activate_Browsers();
		}
	}


	public static void Get_Element_By_Attribute(String Event,String Desc)
	{

	}

	public static void Scroll_Down(String Event,String Desc)
	{
		if(!Event.matches("NA"))
		{
			((JavascriptExecutor) ObjDriver).executeScript("scroll(0,250);");
		}

	}

	public static void Scroll_Up(String Event,String Desc)
	{
		if(!Event.matches("NA"))
		{
			JavascriptExecutor JsE = (JavascriptExecutor)ObjDriver;
			JsE.executeScript("window.scrollBy(0,-250)", "");
		}
	}


	public static void FluentWait_Div()
	{
//    Wait<WebDriver> Wait_For_Div = new FluentWait<WebDriver>(ObjDriver)
//    //Wait for the condition
//        .withTimeout(1, TimeUnit.SECONDS)
//          // which to check for the condition with interval of 5 seconds.
//        .pollingEvery(1, TimeUnit.SECONDS)
//      //Which will ignore the NoSuchElementException
//        .ignoring(NoSuchElementException.class);
//    Wait_For_Div.until(ExpectedConditions.invisibilityOfElementLocated(ObjDriver.findElements(By.xpath("//div[contains(@id,'loadingPanelCursorDiv')]")).size() > 0));

	}

	public static void Click_Window_Object(String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Robot Window_Object = new Robot();
			Window_Object.keyPress(KeyEvent.VK_ENTER);
			Window_Object.keyRelease(KeyEvent.VK_ENTER);
			//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
		}
	}

	public static void Click_Window_ALT_S_Key(String Event,String Desc) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Robot Window_Object = new Robot();
			Window_Object.keyPress(KeyEvent.VK_ALT);
			Window_Object.keyPress(KeyEvent.VK_S);
			Window_Object.keyRelease(KeyEvent.VK_S);
			Window_Object.keyRelease(KeyEvent.VK_ALT);
			if(Desc!="")
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);

			}
		}
	}

	public static void Click_Window_ALT_S_Key(String Event) throws Exception
	{
		if(!Event.matches("NA"))
		{
			Robot Window_Object = new Robot();
			Window_Object.keyPress(KeyEvent.VK_ALT);
			Window_Object.keyPress(KeyEvent.VK_S);
			Window_Object.keyRelease(KeyEvent.VK_S);
			Window_Object.keyRelease(KeyEvent.VK_ALT);
		}
	}

	public static void Click_Window_ALT_Down_Key(String occurrence,String Desc) throws Exception
	{
		if(!occurrence.matches("NA"))
		{
			Robot Window_Object = new Robot();
			for(int i=0;i<=Integer.parseInt(occurrence);i++ )
			{
				Window_Object.keyPress(KeyEvent.VK_DOWN);
				Window_Object.keyRelease(KeyEvent.VK_DOWN);
			}
			if(Desc!="")
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
			}
		}
	}

	public static void Click_WebElement_Down_Key(String WebElement_Pro,String occurrence,String Desc) throws Exception
	{
		if(!occurrence.matches("NA"))
		{
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);
			Element.sendKeys(Keys.TAB);
			Robot Window_Object = new Robot();
			for(int i=0;i<=Integer.parseInt(occurrence);i++ )
			{
				Window_Object.keyPress(KeyEvent.VK_DOWN);
				Window_Object.keyRelease(KeyEvent.VK_DOWN);
			}
		}
	}
	public static void Type_WebElement_Key(String WebElement_Pro,String KeyVal,String Desc) throws Exception
	{
		if(!KeyVal.matches("NA"))
		{
			if(KeyVal.matches("Current Date"))
			{
				KeyVal = Get_Current_Date();
			}
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);

			Element.click();
			Thread.sleep(1000);
			Element.clear();
			writeKeyboard(KeyVal);
			Element.sendKeys(Keys.TAB);
			//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : " + KeyVal, LogStatus.PASS, false);
		}
	}
	public static void Click_Window_ALT_Down_Key(String occurrence) throws Exception
	{
		if(!occurrence.matches("NA"))
		{
			Robot Window_Object = new Robot();
			for(int i=0;i<=Integer.parseInt(occurrence);i++ )
			{
				Window_Object.keyPress(KeyEvent.VK_DOWN);
				Window_Object.keyRelease(KeyEvent.VK_DOWN);
			}
		}
	}

	public static void writeKeyboard(String st) throws AWTException{
		Robot Window_Object = new Robot();
		char[] arr = st.toCharArray();
		int i = arr.length;
		int j = 0;
		int keycode;
		while (j<i) {
			keycode = arr[j];
			Window_Object.keyPress(keycode);
			Window_Object.keyRelease(keycode);
			j++;
		}
	}

	public static String Get_Current_Date()
	{
		DateTime currentDate = new DateTime();
		String D = String.valueOf(currentDate.getDayOfMonth());
		String M = String.valueOf(currentDate.getMonthOfYear());
		String Y = String.valueOf(currentDate.getYear());
		String Date123 = D +"."+M+"."+Y;
		return Date123;
	}

	public static void Login(String Credentials,String Desc) throws Exception
	{
		String[] Credentials_Details = Credentials.split(";");
		String[] Locator_User = Credentials_Details[1].split("=");
		String[] Locator_Pwd = Credentials_Details[2].split("=");
		//@@@@@@@@@@@@@@@@@@@@@@Variable Declaration@@@@@@@@@@@@@@@@@@@@@@@@@

		//FunctionLibrary.launchBrowser("yes", "Launch Browser");
		FunctionLibrary.NavigateTo(Credentials_Details[0],"Navigate to URL");
		FunctionLibrary.Activate_Latest_Browser("yes","");

		FunctionLibrary.setText("id="+Locator_User[0], Locator_User[1],"Enter the user id");
		FunctionLibrary.setText("id="+Locator_Pwd[0], Locator_Pwd[1],"Enter the password");
		FunctionLibrary.clickObject("id="+Credentials_Details[3],"yes","Click Login button");
	}


	public static void Date_Select(String Date,String Desc) throws Exception
	{
		if(!Date.matches("NA"))
		{
			String[] Date_Select = Date.split(";");  ////*[starts-with(@id,'upmstart1:MnyLndg_wB_date_') and contains(@class,'rich-calendar-input inputFieldNotSelected')]
			String[] Date_Locator = Date_Select[0].split("="); //12.12.2016
			//String[] Calendar_Locator = Date_Select[1].split("=");12.12.2016
			FunctionLibrary.Type_WebElement_Key("xpath="+Date_Locator[0], Date_Locator[1],"Enter the date");
			//Function_Library.setText("xpath="+Date_Locator[0], Date_Locator[1],"Enter the date");
			FunctionLibrary.clickObject("xpath="+Date_Select[1],"yes","Click Calendar select button"); ////*[starts-with(@id,'upmstart1:MnyLndg_wB_date_') and contains(@class,'rich-calendar-button')]
			Thread.sleep(1000);

			FunctionLibrary.clickObject("xpath="+Date_Select[2],"yes",Desc);
		}

	}

	public static void View_WebElement_Down_Key(String WebElement_Pro,String occurrence,String Desc) throws Exception
	{
		if(!occurrence.matches("NA"))
		{
			WebElement Element = null;
			Element = Get_Element(WebElement_Pro);
			JavascriptExecutor js = (JavascriptExecutor)ObjDriver;
			js.executeScript("arguments[0].scrollIntoView();",Element);
		}
		else
		{
			if(Desc!="")
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" :The element not found", LogStatus.FAIL, true);Exception_Content();
			}
		}
	}

	public static int Get_WebTable_Row_Count_desc(String Table_Element,String Event,String Webtablename) throws Exception
	{
		int row_cnt=0;
		if(!Event.matches("NA"))
		{
			WebElement Element = Get_Element(Table_Element);
			row_cnt=Element.findElements(By.tagName("tr")).size();
			if(row_cnt>0)
			{
				//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "the row count for the webtable "+ Webtablename + " is : "+ row_cnt, LogStatus.PASS, true);
			}

		}
		return row_cnt;
	}


	public static String repalceStrings(String s1)
	{
		//return s1.replaceAll("�", "").replaceAll(" ", "");
		return s1.replaceAll("�", " ");
	}

	public static void Verify_TextPresent_WebElement(String WebElement_Pro,String Verfy_TextValue,String Desc) throws Exception
	{
		if(!Verfy_TextValue.matches("NA"))
		{

			try
			{
				View_WebElement_Down_Key(WebElement_Pro,"yes","Scroll to the elemenet");

				Thread.sleep(2000);
				Wait_For_Div();
				Activate_Browsers();

				String Actual_Pro = WebElement_Pro.substring(6);
				String ele = ObjDriver.findElement(By.xpath(Actual_Pro)).getText();
				String value = ObjDriver.findElement(By.xpath(Actual_Pro)).getAttribute("value");

      /* char[] first  = Verfy_TextValue.toLowerCase().toCharArray();
         char[] second =  ele.toLowerCase().toCharArray();

         int minValue = Math.min(first.length, second.length);

         for(int i = 0; i < minValue; i++)
         {
                 if (first[i] != second[i])
                 {
                    System.out.println(i);
         System.out.println("First string ->"+ first[i] );
         System.out.println("Second String ->"+ second [i] );

                }
         }
       */

				String Verfy_TextValue1=repalceStrings(Verfy_TextValue);
				String ele1 = repalceStrings(ele);


				if(Verfy_TextValue1.equalsIgnoreCase(ele1)||Verfy_TextValue1.equalsIgnoreCase(value))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" : Expected Value : "+Verfy_TextValue, LogStatus.PASS, true);
					}
				}
				else
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" : Expected Value : "+Verfy_TextValue, LogStatus.FAIL, true);Exception_Content();
					}
				}
			}
			catch(InvalidSelectorException excp1)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +"\n"+excp1, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}
	}

	public static void Submit_Object_With_Wait(String Object_Name_Pro,String Wait_Time,String Desc) throws Exception
	{
		if(!Wait_Time.matches("NA"))
		{
			Wait_For_Div();
			WebElement Object = null;
			try
			{
				Thread.sleep(1500);
				Object = Get_Element(Object_Name_Pro);
				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					if(Object_Name_Pro.startsWith("xpath"))
					{
						String actualProperty=Object_Name_Pro.substring(6);
						ObjDriver.findElement(By.xpath(actualProperty)).submit();
						Thread.sleep(Integer.parseInt(Wait_Time));
						if(Desc!="")
						{
							//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
						}
					}
				}
			}
			catch (StaleElementReferenceException elementHasDisappeared)
			{
				Object = Get_Element(Object_Name_Pro);
				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					Object.submit();
					Thread.sleep(Integer.parseInt(Wait_Time));
					Wait_For_Div();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}
		}
	}

	public static void Double_clickObject_With_Wait(String Object_Name_Pro,String Wait_Time,String Desc) throws Exception
	{
		if(!Wait_Time.matches("NA"))
		{
			WebElement Object = null;
			try
			{
				Thread.sleep(1500);
				Object = Get_Element(Object_Name_Pro);
				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					Element_Action=new Actions(ObjDriver);
					Element_Action.doubleClick(Object);
					Object.click();
					Thread.sleep(Integer.parseInt(Wait_Time));
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}

			catch(InvalidSelectorException excp1)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +"\n"+excp1, LogStatus.FAIL, true);Exception_Content();
				}
			}
			catch (StaleElementReferenceException elementHasDisappeared)
			{
				Object = Get_Element(Object_Name_Pro);
				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					Object.submit();
					Thread.sleep(Integer.parseInt(Wait_Time));
					Wait_For_Div();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}
		}
	}

	//Direct click without using of return webelement.
	public static void Direct_clickObject_With_Wait(String Object_Name_Pro,String Wait_Time,String Desc) throws Exception
	{
		if(!Wait_Time.matches("NA"))
		{
			Wait_For_Div();
			//Thread.sleep(1000);

			WebDriverWait Wait_For_Object = new WebDriverWait(ObjDriver,20);
			try
			{

				WebElement Browser_Element1;
				// Object = Get_Element(Object_Name_Pro);

				if(Object_Name_Pro.startsWith("xpath="))
				{
					String Actual_Property = Object_Name_Pro.substring(6);
					Browser_Element1 = ObjDriver.findElement(By.xpath(Actual_Property));
					for(int i=0;i<=10;i++)
					{
						if(Browser_Element1.isDisplayed())
						{
							View_WebElement_Down_Key(Object_Name_Pro,"yes",Desc);
							Element_Action.moveToElement(Browser_Element1).perform();
							highLightElement(ObjDriver,Browser_Element1);
							break;
						}
						Thread.sleep(100);
					}
					Browser_Element1 = ObjDriver.findElement(By.xpath(Actual_Property));
					if(Browser_Element1 != null)
					{
						if(Desc!="")
						{
							Element_Action.moveToElement(ObjDriver.findElement(By.xpath(Actual_Property))).click().build().perform();
						}
					}


					////Wait_For_Object.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Actual_Property)));
					//ObjDriver.findElement(By.xpath(Actual_Property)).click();


					if(Browser_Element1 == null)
					{
						if(Desc!="")
						{
							//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
						}
					}
					else
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}

			catch(InvalidSelectorException excp1)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +"\n"+excp1, LogStatus.FAIL, true);Exception_Content();
				}
			}
			catch (StaleElementReferenceException elementHasDisappeared)
			{
				WebElement Object = Get_Element(Object_Name_Pro);

				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					String Actual_Property = Object_Name_Pro.substring(6);
					//Wait_For_Object.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Actual_Property)));
					ObjDriver.findElement(By.xpath(Actual_Property)).click();
					Thread.sleep(Integer.parseInt(Wait_Time));
					Wait_For_Div();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
					}
				}
			}
			catch(Exception e)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +"\n"+e, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}
	}

	public static void Verify_TextPresent_Select(String WebElement_Pro,String Verfy_TextValue,String Desc) throws Exception
	{
		if(!Verfy_TextValue.matches("NA"))
		{
			View_WebElement_Down_Key(WebElement_Pro,"yes","Scroll to the elemenet");

			Thread.sleep(2000);
			Wait_For_Div();
			Activate_Browsers();
			Boolean a=false;
			String Actual_Pro=null;

			Actual_Pro = WebElement_Pro.substring(6);
			String Actual_option = Actual_Pro+"/option";
			List<WebElement> Actual_options = ObjDriver.findElements(By.xpath(Actual_option));

			for(WebElement ele:Actual_options)
			{
				if(ele.getText().equalsIgnoreCase(Verfy_TextValue))
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" : Expected Value : "+Verfy_TextValue, LogStatus.PASS, true);
					}
					a=true;
				}
			}
			if(a==false && Actual_Pro==null)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+" : Expected Value : "+Verfy_TextValue, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}
	}

	public static void Verify_TreeStructureData_WebTable_Data(String ExpectedText,String Desc) throws Exception
	{
		if(!ExpectedText.matches("NA"))
		{
			Wait_For_Div();
			int Row;
			int Column;
			int iterate;
			int iterate1;
			Boolean ExistFlg = false;
			List<WebElement> Element = null;
			String text = repalceStrings(ExpectedText);


			Element = ObjDriver.findElements(By.xpath("//span[contains(@id,'col1')]"));

			for(WebElement ele:Element)
			{
				if(text.equalsIgnoreCase(ele.getText()))
				{
					highLightElement(ObjDriver,ele);
					ExistFlg = true;
					break;
				}
			}

			if(ExistFlg)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText, LogStatus.PASS, true);
				}
			}
			else
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}
	}

	public static void Direct_Click_Screenshot_With_Wait(String Object_Name_Pro,String Wait_Time,String Desc) throws Exception
	{
		if(!Wait_Time.matches("NA"))
		{
			Wait_For_Div();
			Thread.sleep(1000);

			try
			{

				WebElement Browser_Element1;
				// Object = Get_Element(Object_Name_Pro);

				if(Object_Name_Pro.startsWith("xpath="))
				{
					String Actual_Property = Object_Name_Pro.substring(6);
					Browser_Element1 = ObjDriver.findElement(By.xpath(Actual_Property));
					for(int i=0;i<=10;i++)
					{
						if(Browser_Element1.isDisplayed())
						{
							View_WebElement_Down_Key(Object_Name_Pro,"yes",Desc);
							Element_Action.moveToElement(Browser_Element1).perform();
							highLightElement(ObjDriver,Browser_Element1);
							break;
						}
						Thread.sleep(100);
					}
					Browser_Element1 = ObjDriver.findElement(By.xpath(Actual_Property));
					if(Browser_Element1!= null)
					{
						if(Desc!="")
						{
							Element_Action.moveToElement(ObjDriver.findElement(By.xpath(Actual_Property))).click().build().perform();
							Wait_For_Div();
						}
					}


					////Wait_For_Object.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Actual_Property)));
					//ObjDriver.findElement(By.xpath(Actual_Property)).click();


					if(Browser_Element1 == null)
					{
						if(Desc!="")
						{
							//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
						}
					}
					else
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
					}
				}
			}

			catch(InvalidSelectorException excp1)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +"\n"+excp1, LogStatus.FAIL, true);Exception_Content();
				}
			}
			catch (StaleElementReferenceException elementHasDisappeared)
			{
				WebElement Object = Get_Element(Object_Name_Pro);

				if(Object == null)
				{
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
					}
				}
				else
				{
					String Actual_Property = Object_Name_Pro.substring(6);
					//Wait_For_Object.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Actual_Property)));
					ObjDriver.findElement(By.xpath(Actual_Property)).click();
					Wait_For_Div();
					Thread.sleep(Integer.parseInt(Wait_Time));
					Wait_For_Div();
					if(Desc!="")
					{
						//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
					}
				}
			}
			catch(Exception e)
			{
				if(Desc!="")
				{
					//ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +"\n"+e, LogStatus.FAIL, true);Exception_Content();
				}
			}
		}

	}




}

