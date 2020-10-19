package co.dianjiu.exception.service.impl;

import co.dianjiu.exception.constans.CodeEnum;
import co.dianjiu.exception.custom.BusinessException;
import co.dianjiu.exception.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public void test() {
        throw new BusinessException(CodeEnum.PARAM_NULL.getCode(), CodeEnum.PARAM_NULL.getMsg());
    }
}
