package libraries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestCaseLibrary extends FunctionLibrary        //UPM_Object_Mapping_Library
{
	
	public static TestCaseLibrary TestCase_Library_Instance;
	TestCaseLibrary()
	{
		
	}
	
	public static TestCaseLibrary Get_TestCase_Instance()
	{
		if(TestCase_Library_Instance == null)
		{
			return TestCase_Library_Instance = new TestCaseLibrary();
		}
		else
		{
			return TestCase_Library_Instance;
		}
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public String TC_N;
	
	public void Execute_TC(Method TC_Name) throws NoSuchMethodException,

	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		try
		{
		//	TC_N = TC_Name;
			//System.out.println(TC_Name);

			//Method Execute = getClass().getMethod(TC_Name,String.class);
			TC_Name.invoke(this);
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	public void Execute_TC(String TC_Name,String Param1,String Param2) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		try
		{
			TC_N = TC_Name;
			Method Execute = getClass().getMethod(TC_Name,String.class,String.class);
			Execute.invoke(this,Param1,Param2);
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	public void Execute_TC(String TC_Name,String Param1,String Param2,String Param3) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		TC_N = TC_Name;
		Method Execute = getClass().getMethod(TC_Name,String.class, String.class, String.class);
		
		
		Execute.invoke(this,Param1,Param2,Param3);
	}	
	public void Execute_TC(String TC_Name,String Param1,String Param2,String Param3,String Param4) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		TC_N = TC_Name;
		Method Execute = getClass().getMethod(TC_Name,String.class, String.class, String.class,String.class);
		
		
		Execute.invoke(this,Param1,Param2,Param3,Param4);
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	//Start Region Test Cases

	public void testCaseRunner(Method methodToBeRun)
	{	try
	{
		methodToBeRun.invoke(this);
	}
		catch (Exception e) {
		}
	}

	
}
