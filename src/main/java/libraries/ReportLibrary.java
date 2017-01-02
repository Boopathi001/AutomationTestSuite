package libraries;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportLibrary {
	public static ExtentReports ExtendReport_Instance;
	public static ExtentTest ExtTest_Instance;
	public static int Test_Step_Number;
	public static String Report_Base_Path;
	public static String Report_Suite_Path;
	public static String Report_Suite_HTML_Path;
	public static String Report_Suite_HTMLs_Path;
	public static String Report_Suite_SS_Path;
	public static String Report_Suite_SS_F_Path;
	public static ReportLibrary Report_Library_Instance;
	public static String SS_Folder = null;
	public static Boolean Error_Flag;
	public static File objDirecotry;
	public static String desc;
	String path;
	ReportLibrary()
	{

	}

	public static ReportLibrary Get_Report_Library_Instance()
	{

		if(Report_Library_Instance == null)
		{
			return Report_Library_Instance = new ReportLibrary();
		}
		else
		{
			return Report_Library_Instance;
		}
	}

	@SuppressWarnings("deprecation")
	public static ExtentReports Get_Extends(String Test_Name)
	{
		try {
			if (ExtendReport_Instance == null) {

				Report_Base_Path = ReportLibrary.getPath() + "\\Report";
				ReportLibrary.checkCreateDirectory(Report_Base_Path);
				Report_Suite_Path = Report_Base_Path + "\\Suite";
				ReportLibrary.checkCreateDirectory(Report_Suite_Path);
				Report_Suite_HTML_Path = Report_Suite_Path + "\\Suite_HTML";
				ReportLibrary.checkCreateDirectory(Report_Suite_HTML_Path);
				Report_Suite_HTMLs_Path = Report_Suite_HTML_Path +"\\"+Test_Name + "_" + FunctionLibrary.Get_Function_Instance().Get_TimeStamp();
				Report_Suite_SS_F_Path = Report_Suite_Path + "\\Suite_SS\\Screenshots\\";
				ReportLibrary.checkCreateDirectory(Report_Suite_SS_F_Path);
				ExtendReport_Instance = new ExtentReports(Report_Suite_HTMLs_Path + ".html", true);
				ExtendReport_Instance.addSystemInfo("Chrome", "54.0.0").
						addSystemInfo("Selenium Version", "3.0.1");
				ExtendReport_Instance.assignProject("Author:Boopathi");
				ExtendReport_Instance.config().documentTitle("TEST AUTOMATION REPORT").reportName("UNIFIED PARTNER").reportHeadline("MANAGEMENT TEST AUTOMATION ANALYSIS");
				Error_Flag = false;
			} else {
				return ExtendReport_Instance;
			}

		}
		catch(Exception e)
		{

		}
		return ExtendReport_Instance;
	}



	public static ExtentTest Start_Report(String Test_Name,String Test_Description)
	{
		//if (ExtTest_Instance == null)
		//{
		//SS_Folder = Report_Suite_SS_Path+"/" + Function_Library.Get_Function_Instance()
		//.Get_TimeStamp();
		Test_Step_Number = 1;
		ExtTest_Instance = Get_Extends(Test_Name).startTest(Test_Name, Test_Description);
		//	}
		return ExtTest_Instance;
	}

	public static ExtentTest Get_Report()
	{
		return ReportLibrary.ExtTest_Instance;
	}

	public static void Add_Step(int Step, String Description, LogStatus Status, boolean ScreenShot)
	{
		desc=Description;
		String StepName = "Step " + Step ;
		if(ScreenShot)
		{
			//For KNE Execution-Delete after
			//String path = Report_Suite_SS_F_Path + "//Suite_SS_" +Function_Library.Get_Function_Instance().Get_TimeStamp()+".png";
			System.out.println(Report_Suite_SS_F_Path);
			String path=Report_Suite_SS_F_Path+ FunctionLibrary.Get_Function_Instance().Get_TimeStamp()+".png";
			System.out.println(path);
			FunctionLibrary.Capture_Screen_Shot(path);
			System.out.println(Description);
			Get_Report().log(Status, StepName,Description + Get_Report().addScreenCapture(path));

		}
		else
		{	System.out.println(Description);
			Get_Report().log(Status, StepName, Description);
		}
		Test_Step_Number = Test_Step_Number + 1;
	}
	//OverLoading Add_Step method with 1st parameter as String 
	public static void Add_Step(String Step, String Description, LogStatus Status, boolean ScreenShot)
	{
		desc=Description;
		String StepName = Step;
		if(ScreenShot)
		{
			//For KNE Execution-Delete after
			//String path = Report_Suite_SS_F_Path + "//Suite_SS_" +Function_Library.Get_Function_Instance().Get_TimeStamp()+".png";
			System.out.println(Report_Suite_SS_F_Path);
			String path=Report_Suite_SS_F_Path+ FunctionLibrary.Get_Function_Instance().Get_TimeStamp()+".png";
			System.out.println(path);
			FunctionLibrary.Capture_Screen_Shot(path);
			System.out.println(Description);
			Get_Report().log(Status, StepName,Description + Get_Report().addScreenCapture(path));

		}
		else
		{	System.out.println(Description);
			Get_Report().log(Status, StepName, Description);
		}
		
	}

	public static void End_Test()
	{
	/*	if(Report_Library.Error_Flag)
		{
			Report_Library.Add_Step(Report_Library.Test_Step_Number, "Fail" , LogStatus.FAIL,false)	;
		}*/

		Test_Step_Number = 0;
		ExtendReport_Instance.endTest(ExtTest_Instance);

	}

	public static void End_Report()
	{

		ExtendReport_Instance.flush();

		try {



			File gb_objDirecotry = new File(".");
			String Project_path = gb_objDirecotry.getCanonicalPath();
			FileWriter writer = new FileWriter(Project_path+"/Result_path.txt");
			writer.write("");
			String path=Report_Suite_HTMLs_Path.toString();
			String path2;
			path2=path.substring(62);
			System.out.println(path2);
			writer.write(path2);
			writer.close();



		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void checkCreateDirectory(String Pth)
	{
		File f = new File(Pth);
		if(!f.exists())
		{
			f.mkdir();
		}
	}

	public static String getPath() {
		String Bpath="";
		try {

			objDirecotry = new File(".");
			Bpath = objDirecotry.getCanonicalPath();
		}
		catch (Exception e)
		{

		}
		return Bpath;
	}
}
