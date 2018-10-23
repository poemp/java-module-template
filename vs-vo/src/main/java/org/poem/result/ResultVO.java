package org.poem.result;

/**
 * @author poem
 */
public class ResultVO<T> {

    private Integer status;

    private String message;

    private T data;

    public ResultVO() {
        this.status = 0;
        this.message = "";
    }

    public ResultVO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultVO(Integer status, T data, String message) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
