package com.yitao.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ProjectName: house
 * @Package: com.yitao.domain
 * @ClassName: User
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 10:45
 */

@Data
@Table(name = "tb_user")
public class User {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "created")
    private Date created;

    @Column(name = "salt")
    private String salt;

}
