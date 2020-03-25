package com.t251.springbootcrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author world
 */
@Entity
@Table(name = "sys_user")
//@Data   不能使用Data注解 会报no session错误
public class User  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long usrId;
    @Column(name = "usr_name")
    private String usrName;
    @Column(name = "usr_password")
    private String usrPassword;
   /* @Column(name = "usr_role_id")
    private Long usrRoleId;*/

    @ManyToOne(targetEntity = Role.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_role_id")
    private Role role;

    @Column(name = "usr_flag")
    //属性级别：忽略该属性的序列化
    @JsonIgnore
    private Integer usrFlag;
    //图片url地址
    @Column(name = "image_pic_path")
    //图片的服务器存储路径
    private String imagePicPath;
    @Column(name = "image_loc_path")
    private String imageLocPath;
    public User(){}
   /* public User( String usrName, String usrPassword, Long usrRoleId, Integer usrFlag) {
        this.usrName = usrName;
        this.usrPassword = usrPassword;
        this.usrRoleId = usrRoleId;
        this.usrFlag = usrFlag;
    }*/
    public User( String usrName, String usrPassword, Integer usrFlag) {
        this.usrName = usrName;
      //  this.usrPassword = usrPassword;

        this.usrFlag = usrFlag;
    }
    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

   /* public Long getUsrRoleId() {
        return usrRoleId;
    }

    public void setUsrRoleId(Long usrRoleId) {
        this.usrRoleId = usrRoleId;
    }*/

    public Integer getUsrFlag() {
        return usrFlag;
    }

    public void setUsrFlag(Integer usrFlag) {
        this.usrFlag = usrFlag;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getImagePicPath() {
        return imagePicPath;
    }

    public void setImagePicPath(String imagePicPath) {
        this.imagePicPath = imagePicPath;
    }

    public String getImageLocPath() {
        return imageLocPath;
    }

    public void setImageLocPath(String imageLocPath) {
        this.imageLocPath = imageLocPath;
    }
}
