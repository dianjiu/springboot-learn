package co.dianjiu.idempotent.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelMap<T> {
    private int code;
    private String msg;
    private T data;

    public ModelMap() {
    }

    public ModelMap(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
