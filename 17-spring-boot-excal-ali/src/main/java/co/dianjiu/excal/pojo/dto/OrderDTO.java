package co.dianjiu.excal.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class OrderDTO {
    @ExcelProperty("序号")
    private Integer id;
    @ExcelProperty("订单ID")
    private String orderId;
    @ExcelProperty("司机ID")
    private String driverId;
    /*@ExcelProperty("车牌号")
    private String license;
    @ExcelProperty("核定载客数")
    private String approvedPassenger;
    @ExcelProperty("投保座位数")
    private String policySeats;
    @ExcelProperty("用车时间")
    private Date useTime;
    @ExcelProperty("接单时间")
    private Date orderTakingTime;
    @ExcelProperty("上车时间")
    private Date startTime;
    @ExcelProperty("下车时间")
    private Date endTime;
    @ExcelProperty("起始地址")
    private String startAddress;
    @ExcelProperty("结束地址")
    private String endAddress;*/

    public OrderDTO() {
    }

    public OrderDTO(Integer id, String orderId, String driverId) {
        this.id = id;
        this.orderId = orderId;
        this.driverId = driverId;
    }
}
