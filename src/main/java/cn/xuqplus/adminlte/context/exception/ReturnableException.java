package cn.xuqplus.adminlte.context.exception;

public class ReturnableException extends Exception {

    public ReturnableException(String s) {
        super(s);
    }

    public ReturnableException(Throwable throwable) {
        super(throwable);
    }
}
