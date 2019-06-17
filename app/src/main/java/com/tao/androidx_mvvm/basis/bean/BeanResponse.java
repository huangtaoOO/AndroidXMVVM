package com.tao.androidx_mvvm.basis.bean;

/**
 * @author: tao
 * @time: 2019/1/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 网络请求Response基类
 */
public class BeanResponse<T>{

    /**
     * status : 200
     * message : 成功
     * data : T
     */

    private int status;
    private String message;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    /**
     * 请求是否成功
     * @return
     */
    public boolean isSuccess(){
        return status==200;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
