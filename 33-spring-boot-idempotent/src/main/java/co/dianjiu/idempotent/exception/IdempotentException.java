package co.dianjiu.idempotent.exception;

/**
 * 自定义幂等注解异常处理类
 */
public class IdempotentException extends RuntimeException {

    private static final long serialVersionUID = 17721020985L;

    public IdempotentException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
