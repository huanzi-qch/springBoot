package cn.huanzi.qch.springbootmybatis.pojo;


public class Result {

    private String message;

    private Integer status;

    private Object data;

    public Result() {
    }

    public Result(String message, Integer status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }

    public static Result build(Integer status,String message,Object data){
            return new Result(message,status,data);
    }
}
