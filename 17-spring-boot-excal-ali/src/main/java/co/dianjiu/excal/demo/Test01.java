package co.dianjiu.excal.demo;


import co.dianjiu.core.util.FileUtils;
import co.dianjiu.excal.pojo.dto.OrderDTO;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.util.ArrayList;

public class Test01 {
    public static void simpleWrite() {
        // 写法1
        File file = FileUtils.mkDir("d://abcd");
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

    public static void main(String[] args) {
        simpleWrite();
        System.out.println(FileUtils.isFileExist("d:/abcd/simpleWrite.xlsx"));
    }
}
