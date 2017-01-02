package features;

//package FLCSS.floridaautomationtestsuite.siebelaccountcreation;

import libraries.ReportLibrary;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelSheet {
	
	protected static XSSFSheet sheetClass = null;
	protected static XSSFWorkbook workbookClass = null;
	public static String locOfInputExcel ;
	ExcelSheet rexl;
	//Excel Data reading
    public String readexcel(String testDataFileName, String sheetname, int rownum, int colnum) throws IOException {
        locOfInputExcel = ReportLibrary.getPath()+"\\testdata\\"+testDataFileName+".xlsx";
        FileInputStream fis = new FileInputStream(locOfInputExcel);
        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        XSSFRow r = s.getRow(rownum);
        return r.getCell(colnum).toString();
        //return r.getCell(colnum).getStringCellValue();
       
    }
    

    public int totalrows(String testDataFileName, String sheetname) throws IOException {
        locOfInputExcel = ReportLibrary.getPath()+"\\testdata\\"+testDataFileName+".xlsx";
        FileInputStream fis = new FileInputStream(locOfInputExcel);
        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        return s.getLastRowNum();
    }

    public int totalcolumns(String sheetname, int rownum) throws IOException {
        FileInputStream fis = new FileInputStream(locOfInputExcel);
        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        XSSFRow r = s.getRow(rownum);
        return r.getLastCellNum();
    }
    
    public static XSSFRow getRow(String sheetname, int rownum) throws IOException {
        FileInputStream fis = new FileInputStream(locOfInputExcel);
        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        XSSFRow r = s.getRow(rownum);
        return r;
    }
    public static void writeExcel(String testDataFileName,String sheetname, int rownum, int colnum,int accno,boolean headerRow) throws EncryptedDocumentException, InvalidFormatException, IOException {
    	 locOfInputExcel = ReportLibrary.getPath()+"\\testdata\\"+testDataFileName+".xlsx";
    	FileInputStream fis=new FileInputStream(locOfInputExcel);
    	XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        XSSFRow r = s.getRow(rownum);
    Cell cell=r.createCell(colnum);
    cell.setCellType(cell.CELL_TYPE_STRING);
    cell.setCellValue(accno);
    XSSFCellStyle headerStyle = w.createCellStyle();
	Font headerFont = w.createFont();
	headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	if (headerRow) {
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	} else {
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	}
	headerStyle.setFont(headerFont);
	cell.setCellStyle(headerStyle);
    FileOutputStream fos=new FileOutputStream(locOfInputExcel);
    w.write(fos);
    fos.close();
    //System.out.println(“Excel File Written.”);
    }
   /* public static void writeExcelCell(int columnno, String data, int row, boolean headerRow) throws IOException {
		Row r=getRow("siebel",row);
    	Cell cell = r.createCell(columnno);
		cell.setCellValue(data);
		XSSFCellStyle headerStyle = getWorkbook().createCellStyle();
		Font headerFont = getWorkbook().createFont();
		headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		if (headerRow) {
			headerFont.setColor(IndexedColors.WHITE.getIndex());
			headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		} else {
			headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		headerStyle.setFont(headerFont);
		cell.setCellStyle(headerStyle);
	}*/
    protected static XSSFWorkbook getWorkbook() {
		return workbookClass;
	}


}

