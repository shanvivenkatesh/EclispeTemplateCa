package com.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.Tests.App;


public class UpdateExcels {
    public void updateExcels(String filename) throws IOException {
        FileInputStream fsIP = new FileInputStream(new File(filename));
        HSSFWorkbook wb = new HSSFWorkbook(fsIP);
        HSSFSheet worksheet = wb.getSheetAt(0);
        int rowcount = worksheet.getPhysicalNumberOfRows();
        App.logger.info("row count --- " + rowcount);
        Cell cell = null;
        for (int j = 1; j < rowcount; j++) {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
            cell = worksheet.getRow(j).getCell(2);
            String dategenerated = ft.format(dNow);
            dategenerated = dategenerated.replace(":", "");
            cell.setCellValue("Orderref" + j + dategenerated);
        }
        fsIP.close();
        FileOutputStream outputfile = new FileOutputStream(new File(filename));
        wb.write(outputfile);
        outputfile.close();
    }
    public void updateColumn(String filename, int row , int cell, String value) throws IOException {
        
        File src = new File(filename);
        FileInputStream fis = new FileInputStream(src);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet1 = wb.getSheetAt(0);
        sheet1.getRow(row).createCell(cell).setCellValue(value);
        FileOutputStream fout = new FileOutputStream(src);
        wb.write(fout);
        fout.close();
        fis.close();
    }
    public void updateExcelsall(String filePath, String fileName) throws IOException {
        App.logger.info("Excel file is --" + filePath + fileName);
        File file = new File(filePath + "\\" + fileName);
        App.logger.info("Excel file is --" + file);
        FileInputStream fsIP = new FileInputStream(file);
        Workbook srcBook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        App.logger.info("fileExtensionName --" + fileExtensionName);
                if (fileExtensionName.equals(".xlsx")) {
            App.logger.info("Excel fomat is -- xlsx--" + fileName);
            try {
                srcBook = new XSSFWorkbook(fsIP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (fileExtensionName.equals(".xls")) {
            srcBook = new HSSFWorkbook(fsIP);
            App.logger.info("Excel fomat is -- xls--" + fileName);
        }
        Sheet sheet = srcBook.getSheetAt(0);
        int rowcount = sheet.getPhysicalNumberOfRows();
        App.logger.info("row count --- " + rowcount);
        Cell cell = null;
        for (int j = 1; j < rowcount; j++) {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
            cell = sheet.getRow(j).getCell(2);
            String dategenerated = ft.format(dNow);
            dategenerated = dategenerated.replace(":", "");
            cell.setCellValue("Orderref" + j + dategenerated);
        }
        fsIP.close();
        FileOutputStream outputFile = new FileOutputStream(file);
        App.logger.info("File saved  --- " + file);
        srcBook.write(outputFile);
        outputFile.close();
    }
    
    public void excelprogress(String filePath, String filename, String testname, int startend) throws IOException {
        App.logger.info("Excel file is --" + filePath + filename);
        File file = new File(filePath + "\\" + filename);
        App.logger.info("Excel file is --" + file);
        FileInputStream fsIP = new FileInputStream(file);
        Workbook srcBook = null;
        String fileExtensionName = filename.substring(filename.indexOf("."));
        App.logger.info("fileExtensionName --" + fileExtensionName);
        if (fileExtensionName.equals(".xlsx")) {
            App.logger.info("Excel fomat is -- xlsx--" + filename);
            try {
                srcBook = new XSSFWorkbook(fsIP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (fileExtensionName.equals(".xls")) {
            srcBook = new HSSFWorkbook(fsIP);
            App.logger.info("Excel fomat is -- xls--" + filename);
        }
        Sheet sheet = srcBook.getSheetAt(0);
        int rownum = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    if (cell.getRichStringCellValue().getString().trim().equals(testname)) {
                        rownum = row.getRowNum();
                        App.logger.info(rownum);
                    }
                }
            }
        }
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dategenerated = ft.format(dNow);
        App.logger.info("Row number is - " + rownum + "-line 214");
        sheet.getRow(rownum).getCell(6).setCellValue(testname + "--- started");
        sheet.getRow(rownum).getCell(startend).setCellValue(testname + "---" + dategenerated);
        App.logger.info(testname + "---" + dategenerated);
        fsIP.close();
        FileOutputStream outputfile = new FileOutputStream(file);
        App.logger.info("File saved  --- " + file);
        srcBook.write(outputfile);
        outputfile.close();
    }

 

    public void excelprogresstracking(String teststart, String filePath, String filename, String testname, int started, int pass, int fail) throws IOException, InterruptedException {
        App.logger.info("Excel file is --" + filePath + filename);
        File file = new File(filePath + "\\" + filename);
        App.logger.info("Excel file is --" + file);
        FileInputStream fsIP = new FileInputStream(file);
        Workbook srcBook = null;
        String fileExtensionName = filename.substring(filename.indexOf("."));
        App.logger.info("fileExtensionName --" + fileExtensionName);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        App.logger.info(dtf.format(now));
        LocalDate localDate = LocalDate.now();
        String today = (DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate));
        App.logger.info("Today's date is - " + today);
        if (fileExtensionName.equals(".xlsx")) {
            App.logger.info("Excel fomat is -- xlsx--" + filename);
            try {
                srcBook = new XSSFWorkbook(fsIP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (fileExtensionName.equals(".xls")) {
            srcBook = new HSSFWorkbook(fsIP);
            App.logger.info("Excel fomat is -- xls--" + filename);
        }
        Sheet sheet = srcBook.getSheet(testname);
        int rownum = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    if (cell.getRichStringCellValue().getString().trim().equals(today)) {

 

                        rownum = row.getRowNum();
                    } else {
                        App.logger.info("Date not found");
                    }
                }
            }
        }
        int testnamerownum = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    if (cell.getRichStringCellValue().getString().trim().equals(testname)) {
                        testnamerownum = row.getRowNum();
                        App.logger.info(" testnamerownum ---- " + testnamerownum);
                        int testnamecolnum = row.getRowNum();
                        App.logger.info(" testnamecolnum ---- " + testnamecolnum);
                    }
                }
            }
        }
        if (rownum == 0) {
            App.logger.info(" row number is 0 - will inser cell");
            rownum = 0;
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        if (cell.getRichStringCellValue().getString().trim().equals("Do not delete")) {
                            rownum = row.getRowNum();
                            App.logger.info("Do not delete  location is---- " + rownum);
                        }
                    }
                }
            }
            App.logger.info(" inserting row");
            Row row = sheet.getRow(6);
            Cell cell = row.getCell(3);
            cell.setCellValue(started);
            cell = row.getCell(4);
            cell.setCellValue(pass);
            cell = row.getCell(0);
            cell.setCellValue(testname);
            cell = row.getCell(5);
            cell.setCellValue(fail);
            App.logger.info(" row created and added value --" + teststart);
            Thread.sleep(5000);
        }
        else {
            App.logger.info(" row number is not 0--" + rownum);
            Row row = sheet.getRow(rownum);
            Cell cell = row.createCell(pass);
            cell.setCellValue(fail);
        }
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dategenerated = ft.format(dNow);
        App.logger.info("Row number is - " + rownum + "-line 214");
        Thread.sleep(5000);
        App.logger.info(testname + "---" + dategenerated);
        fsIP.close(); 
        FileOutputStream outputFile = new FileOutputStream(file);
        App.logger.info("File saved  --- " + file);
        srcBook.write(outputFile);
        outputFile.close();
    }
    public void excelprogresstrackingaddnewrow1(String filePath, String filename, String testname, int startend, int passfail) throws IOException, InterruptedException {
        App.logger.info("Excel file is --" + filePath + filename);
        File file = new File(filePath + "\\" + filename);
        App.logger.info("Excel file is --" + file);
        FileInputStream fsIP = new FileInputStream(file); 
        Workbook srcBook = null;
        String fileExtensionName = filename.substring(filename.indexOf("."));
        App.logger.info("fileExtensionName --" + fileExtensionName);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        App.logger.info(dtf.format(now));
        LocalDate localDate = LocalDate.now();
        String today = (DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate));
        App.logger.info("Today's date is - " + today);
        if (fileExtensionName.equals(".xlsx")) {
            App.logger.info("Excel fomat is -- xlsx--" + filename);
            try {
                srcBook = new XSSFWorkbook(fsIP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

 

        else if (fileExtensionName.equals(".xls")) {
            srcBook = new HSSFWorkbook(fsIP);
            App.logger.info("Excel fomat is -- xls--" + filename);
        }
        Sheet sheet = srcBook.getSheetAt(0);
        App.logger.info(" inserting row");
        sheet.createRow(5);
        Row row = sheet.getRow(7);
        isRowEmpty(row);
        Cell cell = row.createCell(2);
        cell.setCellValue(today);
        cell = row.createCell(startend);
        cell.setCellValue(passfail);
        App.logger.info(" row created and added value --" + today);
        Thread.sleep(5000);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dategenerated = ft.format(dNow);
        Thread.sleep(5000);
        App.logger.info(testname + "---" + dategenerated);
        fsIP.close();
        FileOutputStream outputFile = new FileOutputStream(file); 
        App.logger.info("File saved  --- " + file);
        srcBook.write(outputFile); 
        outputFile.close();
    }

 

    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                App.logger.info("Row is not empty");
            return false;
        }
        App.logger.info("Row is empty");
        return true;
    }
    public void excelprogresstrackingaddnewrow(String teststart, String filePath, String filename, String testname, int started, int pass, int fail)
            throws EncryptedDocumentException, InvalidFormatException, IOException {
        File file = new File(filePath + "\\" + filename);
        InputStream inp = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheet(testname);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dategenerated = ft.format(dNow);
        App.logger.info("adding data in sheet ---" + testname);
        int lastrow = sheet.getLastRowNum() + 1;
        App.logger.info("Last row was ---" + lastrow);
        sheet.shiftRows(6, lastrow, 1, true, true);
        Row row = sheet.createRow((short) 6);
        row.createCell(0).setCellValue(testname);
        row.createCell(1).setCellFormula("DATEVALUE(C7)");
        row.createCell(2).setCellValue(teststart);
        row.createCell(3).setCellValue(started);
        row.createCell(4).setCellValue(pass);
        row.createCell(5).setCellValue(fail);
        sheet.getRow(4).createCell(3).setCellFormula("SUMIFS(D6:D1000005,B6:B1000005,\">=\"&Summary!$I$2,B6:B1000005,\"<=\"&Summary!$K$2)");
        sheet.getRow(4).createCell(4).setCellFormula("SUMIFS(E6:E1000005,B6:B1000005,\">=\"&Summary!$I$2,B6:B1000005,\"<=\"&Summary!$K$2)");
        FileOutputStream fileOut = new FileOutputStream(file);
        wb.write(fileOut);
        fileOut.close();
    }

 

    public void excelprogresstestend(String filePath, String filename, String testname, int startend) throws IOException {
        App.logger.info("Excel file is --" + filePath + filename);
        File file = new File(filePath + "\\" + filename);
        App.logger.info("Excel file is --" + file);
        FileInputStream fsIP = new FileInputStream(file); 
        Workbook srcBook = null;
        String fileExtensionName = filename.substring(filename.indexOf("."));
        App.logger.info("fileExtensionName --" + fileExtensionName);
        if (fileExtensionName.equals(".xlsx")) {
            App.logger.info("Excel fomat is -- xlsx--" + filename);
            try {
                srcBook = new XSSFWorkbook(fsIP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (fileExtensionName.equals(".xls")) {
            srcBook = new HSSFWorkbook(fsIP);
            App.logger.info("Excel fomat is -- xls--" + filename);
        }
        Sheet sheet = srcBook.getSheetAt(0);
        int rownum = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    if (cell.getRichStringCellValue().getString().trim().equals(testname)) {
                        rownum = row.getRowNum();
                        App.logger.info(rownum);
                    }
                }
            }
        }
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dategenerated = ft.format(dNow);
        sheet.getRow(rownum).getCell(startend).setCellValue(testname + "---" + dategenerated);
        App.logger.info(testname + "---" + dategenerated);
        fsIP.close();
        FileOutputStream outputFile = new FileOutputStream(file);
        App.logger.info("File saved  --- " + file);
        srcBook.write(outputFile);
        outputFile.close();
    }
    public void updatetoexcelemptydata(String filePath, String fileName, String sheetName, String testName) throws IOException {
        int rownum;
        int colnum;
        FileInputStream fis = new FileInputStream(new File(filePath + fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        for (rownum = 0; rownum <= 10; rownum++) {
            for (colnum = 0; colnum <= 10; colnum++) {
                XSSFRow row1 = sheet.createRow(rownum);
                XSSFCell r1c1 = row1.createCell(colnum);
                r1c1.setCellValue("Emd Id");
                App.logger.info(rownum + "---" + colnum + "---" + "Emd Id");
            }
        }
        fis.close();
        FileOutputStream fos = new FileOutputStream(new File(filePath + fileName));
        workbook.write(fos);
        fos.close();
        App.logger.info("Done");
    }

 

    public void updatetoexcelemptydatabackup(String filePath, String fileName, String testName) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath + fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row1 = sheet.createRow(0);
        XSSFCell r1c1 = row1.createCell(0);
        r1c1.setCellValue("Emd Id");
        XSSFCell r1c2 = row1.createCell(1);
        r1c2.setCellValue("NAME");
        XSSFCell r1c3 = row1.createCell(2);
        r1c3.setCellValue("AGE");
        XSSFRow row2 = sheet.createRow(1);
        XSSFCell r2c1 = row2.createCell(0);
        r2c1.setCellValue("1");
        XSSFCell r2c2 = row2.createCell(1);
        r2c2.setCellValue("Ram");
        XSSFCell r2c3 = row2.createCell(2);
        r2c3.setCellValue("20");
        XSSFRow row3 = sheet.createRow(2);
        XSSFCell r3c1 = row3.createCell(0);
        r3c1.setCellValue("2");
        XSSFCell r3c2 = row3.createCell(1);
        r3c2.setCellValue("Shyam");
        XSSFCell r3c3 = row3.createCell(2);
        r3c3.setCellValue("25");
        fis.close();
        FileOutputStream fos = new FileOutputStream(new File(filePath + fileName));
        workbook.write(fos);
        fos.close();
        App.logger.info("Done");
    }
    public void updatetoexcelemptydata2(String filePath, String fileName, String sheetName) throws IOException {
        App.logger.info("Excel file is --" + filePath + fileName + "----259 --");
        File file = new File(filePath + "\\" + fileName);
        App.logger.info("Excel file is --" + file + "----259 --");
        FileInputStream fsIP = new FileInputStream(file);
        Workbook srcBook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        App.logger.info("fileExtensionName --" + fileExtensionName);
        if (fileExtensionName.equals(".xlsx")) {
            App.logger.info("Excel fomat is -- xlsx--" + fileName);
            try {
                srcBook = new XSSFWorkbook(fsIP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (fileExtensionName.equals(".xls")) {
            srcBook = new HSSFWorkbook(fsIP);
            App.logger.info("Excel fomat is -- xls--" + fileName);
        }
        Sheet sheet = srcBook.getSheet(sheetName);
        App.logger.info("Sheet is ----" + sheet.getSheetName());
        int rownum = 0;
        int colnum = 0;
        for (rownum = 0; rownum <= 15; rownum++) {
            for (colnum = 0; colnum <= 15; colnum++) {
                App.logger.info("row ---" + rownum + "col ---" + colnum);
                sheet.setColumnWidth(0, 1000);
                sheet.createRow(rownum).createCell(colnum).setCellValue("updated" + rownum + colnum);
                App.logger.info("--- Setting data to null ---");
            }
            fsIP.close();
            FileOutputStream outputFile = new FileOutputStream(file); 
            App.logger.info("File saved  --- " + file);
            srcBook.write(outputFile);
            outputFile.close();
        }
    }

 

    public void updatetoexcelClientdelivery1(String filePath, String fileName, String sheetName, String data, int rowNuw, int colnum) throws IOException {
        App.logger.info("Excel file is --" + filePath + fileName + "----259 --");
        File file = new File(filePath + "\\" + fileName);
        App.logger.info("Excel file is --" + file + "----259 --");
        FileInputStream fsIP = new FileInputStream(file);
        Workbook srcBook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        App.logger.info("fileExtensionName --" + fileExtensionName);
        if (fileExtensionName.equals(".xlsx")) {
            App.logger.info("Excel fomat is -- xlsx--" + fsIP);
            try {
                srcBook = new XSSFWorkbook(fsIP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (fileExtensionName.equals(".xls")) {
            srcBook = new HSSFWorkbook(fsIP);
            App.logger.info("Excel fomat is -- xls--" + fsIP);
        }
        Sheet sheet = srcBook.getSheetAt(0);
        App.logger.info("sheet name is ----" + sheet);
        Row row = sheet.getRow(1);
        Cell column = row.getCell(1);
        String updatename = column.getStringCellValue();
        updatename = "Lala";
        App.logger.info(updatename);
        column.setCellValue(updatename);
        fsIP.close();
        FileOutputStream out = new FileOutputStream(file);
        srcBook.write(out);
        out.close();
    }

 

    public void updatetoexcelClientdelivery(String filePath, String fileName, String sheetName, String data, int row, int col) throws IOException {
        App.logger.info("Excel file is --" + filePath + fileName + "----259 --");
        File file = new File(filePath + "\\" + fileName);
        App.logger.info("Excel file is --" + file + "----259 --");
        FileInputStream fsIP = new FileInputStream(file);
        Workbook srcBook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        App.logger.info("fileExtensionName --" + fileExtensionName);
            if (fileExtensionName.equals(".xlsx")) {
            App.logger.info("Excel fomat is -- xlsx--" + fileName);
            try {
                srcBook = new XSSFWorkbook(fsIP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (fileExtensionName.equals(".xls")) {
            srcBook = new HSSFWorkbook(fsIP);
            App.logger.info("Excel fomat is -- xls--" + fileName);
        }
        Sheet sheet = srcBook.getSheet(sheetName);
        App.logger.info("Sheet is ----" + sheet.getSheetName());
        App.logger.info("row ---" + row + "----col ---" + col);
        sheet.setColumnWidth(0, 2000);
        sheet.getRow(row).getCell(col).setCellValue(data);
        App.logger.info("---" + data);
        fsIP.close();
        FileOutputStream outputFile = new FileOutputStream(file);
        App.logger.info("File saved  --- " + file);
        srcBook.write(outputFile);
        outputFile.close();
    }
}