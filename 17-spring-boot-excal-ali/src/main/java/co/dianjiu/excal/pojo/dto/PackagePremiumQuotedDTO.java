package co.dianjiu.excal.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class PackagePremiumQuotedDTO {
    @ExcelProperty("套餐代码")
    private String packageCode;
    @ExcelProperty("套餐名称")
    private String packageName;
    @ExcelProperty("顶级渠道代码")
    private String mainChannelCode;
    @ExcelProperty("顶级渠道名称")
    private String mainChannelName;
    @ExcelProperty("渠道代码")
    private String channelCode;
    @ExcelProperty("渠道名称")
    private String channelName;
    @ExcelProperty("网点代码")
    private String branchCode;
    @ExcelProperty("网点名称")
    private String branchName;
    @ExcelProperty("险种编码")
    private String riskCode;
    @ExcelProperty("责任编码")
    private String dytyCode;
    @ExcelProperty("试算费率")
    private Double quotedRate;

    public PackagePremiumQuotedDTO() {
    }

    public PackagePremiumQuotedDTO(String packageCode, String packageName, String mainChannelCode, String mainChannelName, String channelCode, String channelName, String branchCode, String branchName, String riskCode, String dytyCode, Double quotedRate) {
        this.packageCode = packageCode;
        this.packageName = packageName;
        this.mainChannelCode = mainChannelCode;
        this.mainChannelName = mainChannelName;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.riskCode = riskCode;
        this.dytyCode = dytyCode;
        this.quotedRate = quotedRate;
    }
}
