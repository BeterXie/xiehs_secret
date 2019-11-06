package com.secret.bussiness.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiehs
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 昵称
     */
    private String nikeName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别 0男1女
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifiedDate;

    /**
     * 状态 0启用 1停用
     */
    private String isDelete;


    public static final String USER_ID = "user_id";

    public static final String NIKE_NAME = "nike_name";

    public static final String REAL_NAME = "real_name";

    public static final String SEX = "sex";

    public static final String AGE = "age";

    public static final String LAST_LOGIN_DATE = "last_login_date";

    public static final String CREATED_DATE = "created_date";

    public static final String MODIFIED_DATE = "modified_date";

    public static final String IS_DELETE = "is_delete";

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
