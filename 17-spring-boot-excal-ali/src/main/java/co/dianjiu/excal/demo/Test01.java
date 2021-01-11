package co.dianjiu.excal.demo;


import co.dianjiu.excal.listener.OracleDTOListener;
import co.dianjiu.excal.pojo.dto.OrderDTO;
import co.dianjiu.excal.utils.FileUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.util.ArrayList;

public class Test01 {
    public static void simpleWrite() {
        // 写法1
        File file = FileUtils.mkDir("c://abcd");
        String fileName = file+"/"+"simpleWrite"+".xlsx";
        OrderDTO orderDTO = new OrderDTO(1,"001","002");
        ArrayList<OrderDTO> list = new ArrayList<>();
        list.add(orderDTO);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        //EasyExcel.write(fileName, OrderDTO.class).sheet("模板").doWrite(list);

        // 写法2
        //fileName = "simpleWrite.xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(fileName, OrderDTO.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        excelWriter.write(list, writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    public static void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1
        //File file = FileUtils.mkDir("c://abcd");
        String fileName = "c://abcd"+"/"+"simpleWrite"+".xlsx";
        // 这里需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        //EasyExcel.read(fileName, OrderDTO.class, new OracleDTOListener()).sheet().doRead();

        // 写法2
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, OrderDTO.class, new OracleDTOListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    public static void main(String[] args) {
//        simpleWrite();
        System.out.println(FileUtils.isFileExist("c:/abcd/simpleWrite.xlsx"));
        simpleRead();
    }
}
