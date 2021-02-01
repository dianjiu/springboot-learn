package co.dianjiu.aspect.exception;

import co.dianjiu.aspect.constans.CodeEnum;
import co.dianjiu.aspect.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = IdempotentException.class)
    public ResultBean BusinessExceptionHandler(IdempotentException e) {
        ResultBean res = ResultBean.builder().build();
        res.setMsg(e.getMessage());
        log.error(String.valueOf(res));
        return res;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResultBean ExceptionHandler(Exception e) {
        ResultBean res = ResultBean.builder().build();
        res.setCode(CodeEnum.SYS_ERROR.getCode());
        res.setMsg(CodeEnum.SYS_ERROR.getMsg());
        return res;
    }
}

