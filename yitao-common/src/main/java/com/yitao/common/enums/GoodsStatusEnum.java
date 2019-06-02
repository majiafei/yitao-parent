package com.yitao.common.enums;

/**
 * <p>
 *     商品状态枚举
 * </p>
 * @Package: com.yitao.common.enums
 * @ClassName: GoodsStatusEnum
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/2 10:43
 */
public enum  GoodsStatusEnum {
    GOODS_UNDERCARRIAGE_STATUS(0, "下架"),
    GOODS_SHELF_STATUS(1, "上架")
    ;

    private int code;

    private String message;

    GoodsStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
