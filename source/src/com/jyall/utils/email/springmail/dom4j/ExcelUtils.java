package com.jyall.utils.email.springmail.dom4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils
{
    private static XSSFSheet excelWSheet;
    private static XSSFWorkbook excelWBook;
    private static XSSFCell cell;
    private static XSSFRow row;

    public static void setExcelFile(String Path, String SheetName)
            throws Exception
    {
        try
        {
            FileInputStream ExcelFile = new FileInputStream(Path);

            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(SheetName);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static void setCellData(String Result, int RowNum, int ColNum, String filePath, String fileName)
            throws Exception
    {
        try
        {
            row = excelWSheet.getRow(RowNum);
            cell = row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
            if (cell == null)
            {
                cell = row.createCell(ColNum);
                cell.setCellValue(Result);
            }
            FileOutputStream fileOut = new FileOutputStream(filePath + fileName);
            excelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static Object[][] getTableArray(String FilePath, String SheetName)
            throws Exception
    {
        String[][] tabArray = null;
        try
        {
            FileInputStream ExcelFile = new FileInputStream(FilePath);

            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(SheetName);
            if ((excelWBook == null) || (excelWSheet == null)) {
                System.err.println("SheetName不存在，请确认后填写");
            }
            int startRow = 1;
            int startCol = 0;

            int totalRows = excelWSheet.getLastRowNum();

            XSSFRow xssfrow = excelWSheet.getRow(1);

            int totalCols = xssfrow.getPhysicalNumberOfCells();
            tabArray = new String[totalRows][totalCols];
            int ci = 0;
            for (int i = startRow; i < totalRows + 1; ci++)
            {
                int cj = 0;
                for (int j = startCol; j < totalCols; cj++)
                {
                    tabArray[ci][cj] = getCellData(i, j);j++;
                }
                i++;
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found,please check the file path");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.err.println("Could not read the Excel sheet");
            e.printStackTrace();
        }
        return tabArray;
    }

    public static String getCellData(int RowNum, int ColNum)
            throws Exception
    {
        try
        {
            cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            int dataType;
            if (cell == null) {
                dataType = 3;
            } else {
                dataType = cell.getCellType();
            }
            if (dataType == 3) {
                return "";
            }
//            System.out.println(dataType);
//            System.out.println(cell.getCellType());
            String CellData = null;
            if (1 == cell.getCellType()) {
                CellData = cell.getStringCellValue();
            } else if (4 == cell.getCellType()) {
                CellData = String.valueOf(cell.getBooleanCellValue());
            } else if (3 == cell.getCellType()) {
                CellData = null;
            } else if (cell.getCellType() == 0) {
                CellData = String.valueOf(cell.getNumericCellValue());
            } else if (2 == cell.getCellType()) {
                System.out.println("公式型数据什么也不做 ");
            }
            return  CellData;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static String getTestCaseName(String sTestCase)
            throws Exception
    {
        String value = sTestCase;
        try
        {
            int posi = value.indexOf("@");
            value = value.substring(0, posi);
            posi = value.lastIndexOf(".");
            return value.substring(posi + 1);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static int getRowContains(String sTestCaseName, int colNum)
            throws Exception
    {
        int i;
        try
        {
            int rowCount = getRowUsed();
            for ( i = 0; i < rowCount; i++) {
                if (getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
                    break;
                }
            }
            return i;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static int getRowUsed()
            throws Exception
    {
        try
        {
            return excelWSheet.getLastRowNum();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args)
    {
        try
        {
            Object[][] a = getTableArray("src/main/resources/4级模板.xlsx",
                    "TestCase");
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[i].length; j++) {
                    System.out.println(a[i][j] + "\t");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
