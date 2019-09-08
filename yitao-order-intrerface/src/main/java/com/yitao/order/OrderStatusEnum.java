package com.yitao.order;

/**
 * @ProjectName: yitao
 * @Package: com.yitao.order
 * @ClassName: OrderStatusEnum
 * @Author: majiafei
 * @Description:
 * @Date: 2019/9/8 21:50
 */
public enum OrderStatusEnum {
    INIT(1, "未支付");
    ;

    private int code;
    private String name;

    OrderStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
