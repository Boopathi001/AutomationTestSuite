package libraries;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ConfigurationLibrary {
	static boolean Create_New_Instance = false;
	static HSSFWorkbook Obj_Workbook;
	static HSSFSheet objSheet;
	static HSSFCell objCell;
	static HSSFCell objCell1;
	static HSSFRow row;
	public static FileInputStream fileInputStream;
	int iterator;
	public static int Driver_Itrator;
	static String cellValue;
	static File Obj_directory;
	public static int Screen_Number;
	public static String WorkBook_Path;
	//@@@@@@@@@@@Singleton Implementation@@@@@@@@@@@@@@@@@@
	public static ConfigurationLibrary configInstance;
	ConfigurationLibrary()
	{
	}
	public static ConfigurationLibrary Get_configInstance()
	{
		if(configInstance == null)
		{
			return configInstance = new ConfigurationLibrary();
		}
		else
		{
			return configInstance;
		}
	}
	//@@@@@@@@@@@````````````````````````@@@@@@@@@@@@@@@@@@
	
	public static int getSheetCount(String WorkBook_Name) throws Exception
	{
		if (Create_New_Instance)
		{
			Obj_Workbook = getActiveWorkBook(WorkBook_Name);
		}
		return Obj_Workbook.getNumberOfSheets();
	}
	
	public static String getSheetNameByHeader(String WorkBook_Name,String Header) throws Exception
	{
		String ret = null;
		try
		{
		int i,j;
		
		ConfigurationLibrary.Create_New_Instance = true;
		int x = getSheetCount(WorkBook_Name);
		ConfigurationLibrary.Create_New_Instance = false;
		for (i = 0;i<=x;i++)
		{
			objSheet = getActiveSheetByIndex(WorkBook_Name,i);
			cellValue = null;
			row = objSheet.getRow(0);
			int col_cnt = row.getPhysicalNumberOfCells();
			for(j=0;j<col_cnt;j++)
			{
				objCell = row.getCell(j);
				cellValue = objCell.getStringCellValue();
				if(Header.compareTo(cellValue)==0)
				{
					ret = objSheet.getSheetName();
					return ret;
				}
			}
		}
		return ret;
		}
		catch(NullPointerException e)
		{
			soundBeep();
			e.printStackTrace();
			FunctionLibrary.Exception_Content();
		}
		return ret;
	}
	
	public static int getColumnIndexByHeader(String WorkBook_Name,String Sheet_Name,String Header) throws Exception
	{
		@SuppressWarnings("unused")
		int i,j;
		int ret = 0;
		objSheet = getActiveSheet(WorkBook_Name,Sheet_Name);
		cellValue = null;
		row = objSheet.getRow(0);
		int col_cnt = row.getPhysicalNumberOfCells();
		for(j=0;j<col_cnt;j++)
		{
			objCell = row.getCell(j);
			cellValue = objCell.getStringCellValue();
			if(Header.compareTo(cellValue)==0)
			{
				ret = j;
				return ret;
			}
		}
		return ret;
	}
	
	public static int getSheetRowCount(String WorkBook_Name,String Sheet_Name) throws Exception 
	{
		objSheet = getActiveSheet(WorkBook_Name,Sheet_Name);
		return objSheet.getLastRowNum();
	}
	
	public static HSSFWorkbook getActiveWorkBook(String WorkBook_Name) throws Exception
	{
		if(Create_New_Instance)
		{
		String Str_Basepath = GetPath();
		@SuppressWarnings("unused")
		int Count = 0;
		WorkBook_Path = Str_Basepath + "\\testdata\\" + WorkBook_Name;
		fileInputStream = new FileInputStream(WorkBook_Path );
		POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
		Obj_Workbook = new HSSFWorkbook((fsFileSystem));
		}
		return Obj_Workbook;
	}
	
	public static HSSFSheet getActiveSheet(String WorkBook_Name,String Sheet_Name) throws Exception
	{
		Obj_Workbook = getActiveWorkBook(WorkBook_Name);
		objSheet = Obj_Workbook.getSheet(Sheet_Name);
		return objSheet;
	}
	
	public static HSSFSheet getActiveSheetByIndex(String WorkBook_Name,Integer Sheet_Index) throws Exception
	{
		Obj_Workbook = getActiveWorkBook(WorkBook_Name);
		objSheet = Obj_Workbook.getSheetAt(Sheet_Index);
		return objSheet;
	}
	
	public static String getCellValue(String WorkBook_Name,String Sheet_Name,int RowNumber, int CellNumber) throws Exception 
	{
		cellValue = null;
		objSheet = getActiveSheet(WorkBook_Name,Sheet_Name);
		row = objSheet.getRow(RowNumber);
		objCell = row.getCell(CellNumber);
		int x = objCell.getCellType();
		if(x == 0)
		{
			cellValue = String.valueOf((int)objCell.getNumericCellValue()) ;
		}
		if(x == 1)
		{
			cellValue = objCell.getStringCellValue();
		}
		if(x == 3)
		{
			cellValue = "";
		}
		return cellValue;
	}
	
	public static void setCellValue(String WorkBook_Name,String Sheet_Name,int RowNumber, int CellNumber,String Set_Val) throws Exception 
	{
		cellValue = null;
		objSheet = getActiveSheet(WorkBook_Name,Sheet_Name);
		row = objSheet.getRow(RowNumber);
		objCell = row.getCell(CellNumber);
		objCell.setCellValue(Set_Val);
	}
	
	public static void setActiveCellValue(int Incremental_Row_Number,String Set_Val) throws Exception 
	{
		int RowNumber = objCell.getRowIndex();
		int ColNumber = objCell.getColumnIndex();
		RowNumber = RowNumber+Incremental_Row_Number;
		String Cellvalue;
		Cellvalue = Set_Val;
		row = objSheet.getRow(RowNumber);
		objCell = row.getCell(ColNumber);
		if (objCell.getStringCellValue()!="")
		{
			//objCell = row.createCell(ColNumber);  
			objCell.setCellType(1);
			objCell.setCellValue(Cellvalue.toString());
		}
		else
		{
			Cellvalue = objCell.getStringCellValue()+";" + Cellvalue;
			objCell.setCellValue(Cellvalue.toString());
		}
		fileInputStream.close();
		
		FileOutputStream outFile =new FileOutputStream(new File(WorkBook_Path));
		Obj_Workbook.write(outFile);
		outFile.close();
	}
	
	public static void setActiveCellValueOverwrite(int Incremental_Row_Number,String Set_Val) throws Exception 
	{
		int RowNumber = objCell.getRowIndex();
		int ColNumber = objCell.getColumnIndex();
		RowNumber = RowNumber+Incremental_Row_Number;
		String Cellvalue;
		Cellvalue = Set_Val;
		row = objSheet.getRow(RowNumber);
		objCell = row.getCell(ColNumber);
		
		if(objCell.getStringCellValue()!="")
		{	
			objCell.setCellValue(Cellvalue.toString());
		}
		fileInputStream.close();
		
		FileOutputStream outFile =new FileOutputStream(new File(WorkBook_Path));
		Obj_Workbook.write(outFile);
		outFile.close();
	}
	
	public static String getHeaderValueBySheeetIndex(String WorkBook_Name,Integer Sheet_Index, int CellNumber) throws Exception 
	{
		cellValue = null;
		objSheet = getActiveSheetByIndex(WorkBook_Name,Sheet_Index);
		row = objSheet.getRow(0);
		int col_cnt = row.getPhysicalNumberOfCells();
		int i=0;
		for (i=1;i<=col_cnt;i++)
		{
		//objCell =  // getCell(CellNumber);
		cellValue = objCell.getStringCellValue();
		}
		return cellValue;
	}
	
	public static String GetPath() throws Exception {
		String Bpath;
		Obj_directory = new File(".");
		Bpath = Obj_directory.getCanonicalPath();
		return Bpath;
	}
	
	public static String GetLocator(String WorkBook_Name,String Sheet_Name,String Col_Name_Row) throws Exception 
	{
		int i = 0;
		int iterator = 0;
		int UsedCellCount = 0;
		
		try 
		{
			UsedCellCount = getSheetRowCount(WorkBook_Name, Sheet_Name);
			
			for (iterator = 1; iterator <= (UsedCellCount); iterator++) {
				row = objSheet.getRow(iterator);
				objCell = row.getCell(0);
				objCell1 = row.getCell(1);
				if (objCell.getStringCellValue().equals(Col_Name_Row)) 
				{
					objCell1 = row.getCell(1);
					i = 1;
					break;
				}
			}
			if (i == 0) 
			{
				System.out.println("Excel Value : " + Col_Name_Row + "Not Found");
				throw new Exception();
			}
		} 
		catch (Exception e) 
		{
			soundBeep();
			System.out.println("Excel Value : " + Col_Name_Row);
			throw new Exception(e);
		}
		return objCell1.getStringCellValue();
	}
	
	public static String getLatestXclFromDownloadLocation() throws IOException
	{
		 File dir = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Downloads");
		    File[] files = dir.listFiles();
		    if (files == null || files.length == 0) {
		        return null;
		    }

		    File lastModifiedFile = files[0];
		    for (int i = 1; i < files.length; i++) {
		       if (lastModifiedFile.lastModified() < files[i].lastModified()) 
		       {
		           lastModifiedFile = files[i];
		       }
		    }
		    return lastModifiedFile.getCanonicalPath();
	}
	
	public static void verifyExcelContainsData(String Expected_Text,String Desc) throws Exception
	{
		HSSFWorkbook Obj_Workbook1;
		HSSFSheet objSheet1;
		HSSFCell objCell1;
		HSSFRow row1;
		String WorkBook_Path1 ;
		int iterator = 0;
		int iterator1 = 0;
		int Row_Cnt = 0;
		int Col_Cnt = 0;
		Boolean Exist_Flg;
		Exist_Flg = false;
		WorkBook_Path1 = getLatestXclFromDownloadLocation();
		fileInputStream = new FileInputStream(WorkBook_Path1 );
		POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
		Obj_Workbook1 = new HSSFWorkbook((fsFileSystem));
		objSheet1 = Obj_Workbook1.getSheet("Sheet1");
		Row_Cnt = objSheet1.getLastRowNum();
		
		for (iterator = 1; iterator <= (Row_Cnt); iterator++) 
		{
			
			row1 = objSheet1.getRow(iterator);
			if(!(row1==null))
			{
			Col_Cnt = row1.getPhysicalNumberOfCells();
			for(iterator1 = 0; iterator1 <= (Col_Cnt); iterator1++)
			{
				objCell1 = row1.getCell(iterator1);
				if(!(objCell1==null))
				{
				if(objCell1.getStringCellValue().equals(Expected_Text))
				{
					Exist_Flg = true;
					break;
				}
				}
			}
			if(Exist_Flg)
			{
				break;
			}
			}
		}
			if(Exist_Flg)
			{
				if(Desc!="")
				{
				ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ", Expected Value : " + Expected_Text, LogStatus.PASS, true);
				}
			}
			else
			{
				if(Desc!="")
				{
				ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ", Expected Value : " + Expected_Text, LogStatus.FAIL, true);
				}
			}
	}
	public static String getVal(String WorkBook_Name,String Sheet_Name,String Col_Name_Row) throws Exception 
	{
		int i = 0;
		int iterator = 0;
		int UsedCellCount = 0;
		String ret = null;
		try 
		{
			UsedCellCount = getSheetRowCount(WorkBook_Name, Sheet_Name);
			
			for (iterator = 1; iterator <= (UsedCellCount); iterator++) {
				row = objSheet.getRow(iterator);
				objCell = row.getCell(0);
				objCell1 = row.getCell(1);
				if (objCell.getStringCellValue().equals(Col_Name_Row)) 
				{
					objCell1 = row.getCell(1);
					i = 1;
					break;
				}
			}
			if (i == 0) 
			{
				System.out.println("Excel Value : " + Col_Name_Row + "Not Found");
				throw new Exception();
			}
		} 
		catch (Exception e) 
		{
			soundBeep();
			System.out.println("Excel Value : " + Col_Name_Row + " :");
			throw new Exception(e);
		}
		int x = objCell1.getCellType();
		if(x == 0)
		{
			ret = String.valueOf((int)objCell1.getNumericCellValue()) ;
		}
		if(x == 1)
		{
			ret = objCell1.getStringCellValue();
		}
		return ret;
	}
	
	//UPDATION REQUIRED
	public static void setVal(String WorkBook_Name,String Sheet_Name,String Col_Name_Row) throws Exception 
	{
		int i = 0;
		int iterator = 0;
		int UsedCellCount = 0;
		try 
		{
			UsedCellCount = getSheetRowCount(WorkBook_Name, Sheet_Name);
			
			for (iterator = 1; iterator <= (UsedCellCount); iterator++) {
				row = objSheet.getRow(iterator);
				objCell = row.getCell(0);
				objCell1 = row.getCell(1);
				if (objCell.getStringCellValue().equals(Col_Name_Row)) 
				{
					objCell1 = row.getCell(1);
					i = 1;
					break;
				}
			}
			if (i == 0) 
			{
				System.out.println("Excel Value : " + Col_Name_Row + "Not Found");
				throw new Exception();
			}
		} 
		catch (Exception e) 
		{
			soundBeep();
			System.out.println("Excel Value : " + Col_Name_Row + " :");
			throw new Exception(e);
		}
		int x = objCell1.getCellType();
		if(x == 0)
		{
		}
		if(x == 1)
		{
		}
	}
	
	public static void Check_Create_Directory(String Pth)
	{
		File f = new File(Pth);
		if(!f.exists())
		{
			f.mkdir();
		}
	}
	
	public static void getIterator(int Local_Iteratator)
	{
		Driver_Itrator = Local_Iteratator;
	}
	
	public static int setIterator()
	{
		return Driver_Itrator;
	}
	
	public static void soundBeep() throws InterruptedException
	{
		for(int i=0;i<=100;i++)
		{
			java.awt.Toolkit.getDefaultToolkit().beep();
			Thread.sleep(100);
		}
	}
}
