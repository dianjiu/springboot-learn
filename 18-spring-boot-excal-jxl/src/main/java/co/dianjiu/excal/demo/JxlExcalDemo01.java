package co.dianjiu.excal.demo;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.List;

/**
 * 不带表头的Excal的导出导入
 * <p>
 * jxl.jar是通过java操作excel表格的工具类库
 * jxl操作excel包括对象Workbook（工作簿），Sheet（工作表） ，Cell（单元格）。
 * 一个excel就对应一个Workbook对象。
 * 一个Workbook可以有多个Sheet对象。
 * 一个Sheet对象可以有多个Cell对象。
 */
public class JxlExcalDemo01 {
    public static void importSimpleExcalByJxl() throws Exception {
        File xlsFile = new File("jxl.xls");
        // 获得工作簿对象
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        // 获得所有工作表
        Sheet[] sheets = workbook.getSheets();
        // 遍历工作表
        if (sheets != null && sheets.length > 0) {
            for (Sheet sheet : sheets) {
                // 获得行数
                int rows = sheet.getRows();
                // 获得列数
                int cols = sheet.getColumns();
                // 读取数据
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        System.out.printf("%10s", sheet.getCell(col, row).getContents());
                    }
                    System.out.println();
                }
            }
        }
        workbook.close();
    }

    public static void exportSimpleExcalByJxl() throws Exception {
        File xlsFile = new File("jxl.xls");
        //创建一个工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
        //创建一个工作表
        WritableSheet sheet1 = workbook.createSheet("sheet1", 0);
        //十行十列
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                // 向工作表中添加数据
                sheet1.addCell(new Label(col, row, "data" + row + col));
            }
        }
        workbook.write();
        workbook.close();
    }

    public static void main(String[] args) throws Exception {
        exportSimpleExcalByJxl();
        importSimpleExcalByJxl();
    }
}
