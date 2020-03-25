package com.t251.springbootcrm.service.impl;

import com.t251.springbootcrm.entity.Right;
import com.t251.springbootcrm.entity.Role;
import com.t251.springbootcrm.entity.User;
import com.t251.springbootcrm.repository.RightRepository;
import com.t251.springbootcrm.repository.RoleRepository;
import com.t251.springbootcrm.repository.UserRepository;
import com.t251.springbootcrm.service.IUserService;
import com.t251.springbootcrm.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 亚力克
 */
@Service("userService")
//配置缓存：当我们使用到缓存的地方越来越多
//你可以使用@CacheConfig(cacheNames = {"myCache"})注解来统一指定value的值
//这时课省略value
//如果你在你的方法上写了value，那么依然以方法的value为准
//@CacheConfig(cacheNames = "hhh")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RightRepository rightRepository;
    @Override
    public User login(String usrName, String usrPassword) {
        return userRepository.findByUsrNameAndUsrPassword(usrName,usrPassword);
    }

    @Override
    public void saveUser(User user) {
        user.setUsrPassword(SecurityUtils.md5Hex("salt"+user.getUsrPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long usrId) {
        userRepository.deleteById(usrId);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
        //配置缓存：注解会先查询是否有缓存，有会使用缓存，没有则会执行方法并缓存
    //此处的value是必须的，它指定的你的缓存放在哪块命名空间 如果配置了@CacheConfig的cacheNames属性就可不写value了
    //keyGenerator：缓存数据时key生成策略
    @Override
   // @Cacheable(value = "getUser",keyGenerator = "keyGenerator")
    public User getUser(Long usrId) {
        return userRepository.findByUsrId(usrId);
    }

    @Override
    public User getUser(String usrName) {
        return userRepository.findByUsrName(usrName);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findUsers(String usrName, Long roleId, Pageable pageable) {
        Specification<User>specification=new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>predicates=new ArrayList<>();
                if (usrName!=null&&!usrName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("usrName"),"%"+usrName+"%"));
                }
                if (roleId!=null&&roleId.longValue()!=0){
                    Role role=new Role();
                    role.setRoleId(roleId);
                    predicates.add(criteriaBuilder.equal(root.get("role"),role));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
       return userRepository.findAll(specification,pageable);
    }

    @Override
    public Long getUsrIdByUsrName(String usrName) {
        return userRepository.findUsrIdByUsrName(usrName);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByUser(User user) {

        return roleRepository.findRoleByUsers(user);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> findRoles(String roleName) {
        List<Role>list=null;
        if (roleName!=null) {
            list=roleRepository.findRolesByRoleNameLike("%"+roleName+"%");
        }else {
            list=roleRepository.findAll();
        }
        return list;
    }

    @Override
    public List<Right> findAllRights() {
        return rightRepository.findAll();
    }

    @Override
    public List<Right> findRightsByRole(Role role) {
        return rightRepository.findRightsByRoles(role);
    }
}
