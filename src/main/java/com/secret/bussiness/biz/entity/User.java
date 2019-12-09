package com.secret.bussiness.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiehs
 * @since 2019-12-09
 */
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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "User{" +
        "userId=" + userId +
        ", nikeName=" + nikeName +
        ", realName=" + realName +
        ", sex=" + sex +
        ", age=" + age +
        ", lastLoginDate=" + lastLoginDate +
        ", createdDate=" + createdDate +
        ", modifiedDate=" + modifiedDate +
        ", isDelete=" + isDelete +
        "}";
    }
}
