package co.dianjiu.excal.demo;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 不带表头的Excal的导出导入
 */
public class PoiExcalDemo01 {
    public static void importSimpleExcalByJxl() throws Exception {
        File xlsFile = new File("poi.xls");
        // 获得工作簿
        Workbook workbook = WorkbookFactory.create(xlsFile);
        // 获得工作表个数
        int sheetCount = workbook.getNumberOfSheets();
        // 遍历工作表
        for (int i = 0; i < sheetCount; i++)
        {
            Sheet sheet = workbook.getSheetAt(i);
            // 获得行数
            int rows = sheet.getLastRowNum() + 1;
            // 获得列数，先获得一行，在得到改行列数
            Row tmp = sheet.getRow(0);
            if (tmp == null)
            {
                continue;
            }
            int cols = tmp.getPhysicalNumberOfCells();
            // 读取数据
            for (int row = 0; row < rows; row++)
            {
                Row r = sheet.getRow(row);
                for (int col = 0; col < cols; col++)
                {
                    System.out.printf("%10s", r.getCell(col).getStringCellValue());
                }
                System.out.println();
            }
        }
    }

    public static void exportSimpleExcalByJxl() throws Exception {
        //创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet = workbook.createSheet();
        //十行十列
        for (int row = 0; row < 10; row++)
        {
            //先创建行
            HSSFRow rows = sheet.createRow(row);
            for (int col = 0; col < 10; col++)
            {
                // 再创建列，向工作表中添加数据
                rows.createCell(col).setCellValue("data" + row + col);
            }
        }
        //输出文件
        File xlsFile = new File("poi.xls");
        FileOutputStream xlsStream = new FileOutputStream(xlsFile);
        workbook.write(xlsStream);
    }

    public static void main(String[] args) throws Exception {
        exportSimpleExcalByJxl();
        importSimpleExcalByJxl();
    }
}
