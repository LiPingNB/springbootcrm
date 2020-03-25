package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 亚力克
 * jpa仓库
 */
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
 //public List<User>findByUsrName(String usrName);
 public User findByUsrNameAndUsrPassword(String usrName, String usrPassword);
 public User findByUsrId(Long usrId);
 public User findByUsrName(String usrName);
 public Long countByUsrName(String usrName);
 public List<User> findByUsrNameLike(String usrName);
 @Query("select usrId from User where usrName=:usrName")
 public Long findUsrIdByUsrName(String usrName);
 //@Query("select u from User u where u.usrRoleId=?1")
 //@Query(value = "select * from sys_user where usr_role_id=?1",nativeQuery = true)
// @Query("select u from User u where u.usrRoleId=:roleId")
// public List<User>findByRoleId(@Param("roleId") Long roleId);
//@Query("select u from User u where u.usrRoleId=?1")
// public Page<User>findPageByUsrRoleId(Long roleId,Pageable pageable);
@Query(value = "select * from sys_user",nativeQuery = true)
public Page<User>findUserByPage(Pageable pageable);

}
