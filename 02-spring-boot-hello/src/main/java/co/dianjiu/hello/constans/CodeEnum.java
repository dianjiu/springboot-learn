package co.dianjiu.hello.constans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CodeEnum {

    OK("200", "成功"),
    VALUE_NULL("300", "值为空"),
    PARAM_NULL("301", "参数为空，处理异常"),
    SIGN_ERROR("400", "签名错误"),
    NO_LOGIN("401", "未登录"),
    SYS_ERROR("500", "系统异常");

    private String code;
    private String msg;

    // 根据code返回msg信息
    public String getMsgByCode(String code) {
        for (CodeEnum entry : CodeEnum.values()) {
            if (Objects.equals(entry.getCode(), code)) {
                return entry.getMsg();
            }
        }

        return "";
    }

}

