package com.Utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.Tests.App;

import org.apache.poi.ss.usermodel.Sheet;

 

public class ReadExcel  {
    //It was final earlier
    private static String cell = null;
    public  void  readExcel(int rowcounter, int colnum, String filename) throws IOException{
           File file =    new File(filename);
           FileInputStream inputStream = new FileInputStream(file);
        Workbook srcBook = null;
        String fileExtensionName = filename.substring(filename.indexOf("."));
        if(fileExtensionName.equals(".xlsx")){
       srcBook = new XSSFWorkbook(inputStream);
        }
        else if(fileExtensionName.equals(".xls")){
            srcBook = new HSSFWorkbook(inputStream);
        }
         Sheet sourceSheet = srcBook.getSheetAt(0);
        int rownum=rowcounter;
        Row sourceRow = sourceSheet.getRow(rownum);
        Cell orderref = sourceRow.getCell(colnum);
        App.logger.info(orderref);
}
    public  String readExcel2(int rowcounter, int colnum, String filename) throws IOException{
        File file =    new File(filename);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook srcBook = null;
        String fileExtensionName = filename.substring(filename.indexOf("."));
        if(fileExtensionName.equals(".xlsx")){
            srcBook = new XSSFWorkbook(inputStream);
        }
         else if(fileExtensionName.equals(".xls")){
            srcBook = new HSSFWorkbook(inputStream);
        }
        Sheet sourceSheet = srcBook.getSheetAt(0);
        int rownum=rowcounter;
        Row sourceRow = sourceSheet.getRow(rownum);
         String orderrefbeforetrim = sourceRow.getCell(colnum).toString();
         String orderref = orderrefbeforetrim.trim();
         App.logger.info(orderref);
         return (String) orderref;
}
public void readExcel1(int rowcounter, String filename) throws IOException{
        FileInputStream fsIP= new FileInputStream(new File(filename));
        XSSFWorkbook srcBook = new XSSFWorkbook(filename);     
        XSSFSheet sourceSheet = srcBook.getSheetAt(0);
        int rownum=rowcounter;
        XSSFRow sourceRow = sourceSheet.getRow(rownum);
        XSSFCell cell1=sourceRow.getCell(0);
        XSSFCell cell2=sourceRow.getCell(1);
        XSSFCell cell3=sourceRow.getCell(2);
        App.logger.info(cell1);
        App.logger.info(cell2);
        App.logger.info(cell3);
}
    public void readXLSXFile(String fileName) {
        InputStream xlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        try {
            xlsxFileToRead = new FileInputStream(fileName);
            workbook = new XSSFWorkbook(xlsxFileToRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheet("ORDERS");
        XSSFRow row;
        XSSFCell cell;
        Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();
                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    System.out.print(cell.getStringCellValue() + " ");
                } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue() + " ");
                } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue() + " ");
                } else { 
                }
            }
            App.logger.info("done");
            try {
                xlsxFileToRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
