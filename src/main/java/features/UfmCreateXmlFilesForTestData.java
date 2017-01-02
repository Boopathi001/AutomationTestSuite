package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.ReportLibrary;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class UfmCreateXmlFilesForTestData {
    public static String locOfInputUfmExcelFile = "";
    public static String locOfFolderInWhichXmlToBeStored = "";

    public static void UfmCreateXmlFilesForTestData()
    {
        //this function will get the values for the variables.
        getUfmLocations();
        System.out.println(locOfInputUfmExcelFile);
       // System.out.println(ReportLibrary.getPath());
        //Getting test data into a has]hmap
        HashMap<String,String> allTestCasesDataBeforeSort = getUfmTestData(locOfInputUfmExcelFile);

        //Sorting the test case ids which are present in Hashmap(allTestCasesDataBeforeSort)
        Map<String, String> allTestCasesData = new TreeMap<String, String>(allTestCasesDataBeforeSort);

        //System.out.println("After Sorting:");
        Iterator iterator = allTestCasesData.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
        }
        //Defining variables to save data for each row
        String testCaseName = "";
        String colNamesAndValuesInfoString = "";
        String colNamesAndValuesInfoArray [];
        String keyName="";
        String keyValue="";

        //Creating a map object to keep each test case data
        Map<String,String> eachTestCaseData = new TreeMap<String, String>(allTestCasesData);
        Iterator iterator1 = allTestCasesData.entrySet().iterator();

        while(iterator1.hasNext()) {
            Map.Entry me1 = (Map.Entry)iterator1.next();
            testCaseName = me1.getKey().toString();
            //this string holds all the columnn name and vlaue perticular syntax(Example - "PlazaId:099;PltRead:997;
            colNamesAndValuesInfoString = me1.getValue().toString();
            //this array holds column names and values for a single test case(row)
            colNamesAndValuesInfoArray = colNamesAndValuesInfoString.split(";");
            //column name and value are keeping here in a Map(eachTestCaseData)
            for(int i =0;i<=colNamesAndValuesInfoArray.length-1;i++)
            {
                keyName=colNamesAndValuesInfoArray[i].split(":")[0].toString();//keyname(column name)
                if(colNamesAndValuesInfoArray[i].split(":").length==2)
                {
                    //key  value(actual value to be passed in xml)
                    keyValue=colNamesAndValuesInfoArray[i].split(":")[1].toString();
                }
                else
                {
                    keyValue = "";
                }
                eachTestCaseData.put(keyName,keyValue);
            }
            //Erasing array after each iteration
            colNamesAndValuesInfoArray=null;
            createXmlWithTestData(testCaseName,eachTestCaseData);

            ReportLibrary.End_Report();
            ReportLibrary.End_Test();
        }

    }
    /**
     * Returns Element object
     * @param doc: document object
     * @param  parentElement: parent element
     * @param  tagName: tag to be created with the name
     * @param value: tag value to keep
     */
    public static Element createChildElement(Document doc,Element parentElement,String tagName,String value)
    {
        try
        {
            Element e = doc.createElement(tagName);
            e.appendChild(doc.createTextNode(value));
            parentElement.appendChild(e);
            return e;
        }
        catch (Exception e)
        {
            return null;
        }

    }
    /**
     * Read the test data from testData Map object and create the xml file for the mentioned @param testCaseName.
     * @param testData: Map object with key(testcase rownum), value(columnnames and vlaues of perticular row).
     * @param testCaseName: for with test case, xml should be created.
     */
    public static void createXmlWithTestData(String testCaseName,Map testData)
    {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root element
            Document doc = docBuilder.newDocument();
            Element soapEnvelope = doc.createElement("soapenv:Envelope");
            doc.appendChild(soapEnvelope);

            // adding attributes
// adding attributes
            soapEnvelope.setAttribute("xmlns:soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
            soapEnvelope.setAttribute("xmlns:del", "http://deliver.outbound.ufm.sodh.fte.com/");

            Element soapHeader = createChildElement(doc,soapEnvelope,"soapenv:Header","");
            Element soapBody = createChildElement(doc,soapEnvelope,"soapenv:Body","");
            Element delProcUFM = createChildElement(doc,soapBody,"del:procUFM","");

            createChildElement(doc,delProcUFM,"AwayAgencyIAGCode",testData.get("Away Agency ID").toString());
            createChildElement(doc,delProcUFM,"SubmitTime",testData.get("TxnTm").toString());

            // ufm elements
            Element ufm = createChildElement(doc,delProcUFM,"UFM","");

            // ufm elements
            createChildElement(doc,ufm,"UFMID",testData.get("UFM ID").toString());
            createChildElement(doc,ufm,"TipUfmSequence",testData.get("TipUfmSequence").toString());
            createChildElement(doc,ufm,"LaneUFMType",testData.get("Lane UFM Type").toString());
            createChildElement(doc,ufm,"CalClass",testData.get("CalClass").toString());
            createChildElement(doc,ufm,"TimeStamp",testData.get("TxnTm").toString());
            createChildElement(doc,ufm,"PlazaID",testData.get("PlazaId").toString());
            createChildElement(doc,ufm,"LaneID",testData.get("LaneId").toString());
            createChildElement(doc,ufm,"PayMethod",testData.get("PayMethod").toString());
            createChildElement(doc,ufm,"LaneMode",testData.get("Lane Mode").toString());
            createChildElement(doc,ufm,"LaneStatusState",testData.get("Lane Status").toString());
            createChildElement(doc,ufm,"ExceptionsCodes",testData.get("Exception Code").toString());

            Element previousInfo= createChildElement(doc,ufm,"PreviousInfo","");
            createChildElement(doc,previousInfo,"PreviousPlazaID",testData.get("PrevPlzId").toString());
            createChildElement(doc,previousInfo,"PreviousLaneID",testData.get("PrevLane").toString());
            createChildElement(doc,previousInfo,"PreviousTimeStamp",testData.get("PrevTm").toString());

            Element transponderInfo= createChildElement(doc,ufm,"TransponderInfo","");
            createChildElement(doc,transponderInfo,"TransponderStatus",testData.get("TpdrStat").toString());
            createChildElement(doc,transponderInfo,"StateRegionCode",testData.get("Region").toString());
            createChildElement(doc,transponderInfo,"AgencyID",testData.get("Agency").toString());
            createChildElement(doc,transponderInfo,"TransponderID",testData.get("TpdrId").toString());
            createChildElement(doc,transponderInfo,"InternalNumber",testData.get("Internal #").toString());
            createChildElement(doc,transponderInfo,"PasBak",testData.get("PasBak").toString());
            createChildElement(doc,transponderInfo,"PrepaidPassType",testData.get("Prepaid Pass Type").toString());

            Element vehicleInfo= createChildElement(doc,ufm,"VehicleInfo","");
            createChildElement(doc,vehicleInfo,"LicensePlateCountry",testData.get("License Plate Country").toString());
            createChildElement(doc,vehicleInfo,"Jurisdiction",testData.get("Juris").toString());
            createChildElement(doc,vehicleInfo,"PlateType",testData.get("PlateType").toString());
            createChildElement(doc,vehicleInfo,"PlateRead",testData.get("PltRead").toString());
            createChildElement(doc,vehicleInfo,"HorizontalPosition",testData.get("Horizontal Position").toString());
            createChildElement(doc,vehicleInfo,"VerticalPosition",testData.get("Vertical Position").toString());
            createChildElement(doc,vehicleInfo,"PlateReadConfidence",testData.get("Plate Read Confidence").toString());
            createChildElement(doc,vehicleInfo,"JurisdictionReadConfidence",testData.get("Jurisdiction Read Confidence").toString());
            createChildElement(doc,ufm,"FullFareRevenue",testData.get("FulFareRev").toString());
            createChildElement(doc,ufm,"EtcFareRevenue",testData.get("ETCRev").toString());
            createChildElement(doc,ufm,"IndicatedRevenue",testData.get("IndRev").toString());
            createChildElement(doc,ufm,"ActualRevenueCollected",testData.get("ActRev").toString());
            createChildElement(doc,ufm,"AccountID",testData.get("Account ID").toString());
            createChildElement(doc,ufm,"TipAdjusted",testData.get("TIP Adjusted").toString());

            Element tripInfo= createChildElement(doc,ufm,"TripInfo","");

            createChildElement(doc,tripInfo,"TripId",testData.get("Trip ID").toString());
            createChildElement(doc,tripInfo,"TripAssociationType",testData.get("TripAssociationType").toString());
            createChildElement(doc,ufm,"RevenueType",testData.get("Revenue Type").toString());

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            ReportLibrary.checkCreateDirectory(locOfFolderInWhichXmlToBeStored+"\\ufmXmlFiles");

            StreamResult result = new StreamResult(new File(locOfFolderInWhichXmlToBeStored+"\\ufmXmlFiles\\"+
                    testCaseName+".xml"));
            transformer.transform(source, result);

            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"File saved. " + testCaseName + ".xml",
                    LogStatus.PASS,false);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Failed for test case " + testCaseName +".",
                    LogStatus.FAIL,false);
        } catch (TransformerException tfe) {
            ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Failed for test case " + testCaseName +".",
                    LogStatus.FAIL,false);
            tfe.printStackTrace();
        }

    }
    /**
     * Read the test data in Excel data file and convert data to HashMap and return as output.
     * @param locOfFile: location of test data file(Excel file with UFM test cases information).
     * argument is a specifier that is relative to the url argument.
     */
    public static HashMap getUfmTestData(String locOfFile)
    {
        HashMap<String,String> rowData = new HashMap<String,String>();
        try
        {
            DataFormatter formatter = new DataFormatter();
            FileInputStream file = new FileInputStream(new File(locOfInputUfmExcelFile));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            int noOfRows = sheet.getLastRowNum();
            //System.out.println("no of rows:" + noOfRows);

            int i = 0;
            Row rowWithColumnNames = sheet.getRow(2);
            int noOfColumns = rowWithColumnNames.getPhysicalNumberOfCells();
            //System.out.println(noOfColumns);
            String testCaseName ="";
            String columnNamesAndValuesOfOneRow = "";

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            // System.out.println(rowIterator

            for(int m=0;m<noOfRows;m++)
            {
                //System.out.println("Ieration number : " + m);
                Row rowCurrent = rowIterator.next();
                if(m<=3){
                    continue;
                }
                testCaseName = String.valueOf(rowCurrent.getCell(0));
                //     System.out.println("test case name " + testCaseName);

                for (int p = 0; p < 84; p++) {
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
            return rowData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Method to read the Ufm input excel file location and the folder location where need store the xml file
     */
    public static void getUfmLocations() {
        HashMap<String, String> rowData = new HashMap<String, String>();
        try {
            DataFormatter formatter = new DataFormatter();
            FileInputStream file = new FileInputStream(new File(ReportLibrary.getPath()
                    +"\\testdata\\Ufm_InputData.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            int noOfRows = sheet.getLastRowNum();

            Row rowWithColumnNames = sheet.getRow(1);
            locOfInputUfmExcelFile = sheet.getRow(1).getCell(1).toString();
            locOfFolderInWhichXmlToBeStored = sheet.getRow(1).getCell(2).toString();
        }
        catch (Exception e)
        {

        }
    }
}