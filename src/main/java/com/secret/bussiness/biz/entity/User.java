package com.secret.bussiness.biz.entity;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiehs
 * @since 2019-10-10
 */
@Data
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

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
    @TableLogic
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
