## 自定义异常
- ResultBean 接口统一返回格式
- CodeEnum 返回编码和信息
- BusinessException 自定义的业务异常类
- GlobalExceptionHandler 全局异常捕获
- DemoController 测试自定义异常捕获接口
- DemoServiceImpl 中抛出自定义业务性异常

## 整体业务逻辑
自定义异常类 

--> 在service层向上抛出自定义异常类 

--> 在controller层把自定义异常类继续向上抛 

--> 用自定义的全局异常处理类进行统一拦截捕获并处理

## 自定义异常的好处
- 异常信息明确，能够快速定位问题
- 统一代码业务性问题处理规范
- 方便错误数据的封装记录，后台统一异常拦截
