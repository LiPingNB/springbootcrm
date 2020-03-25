package com.t251.springbootcrm.service.impl;

import com.t251.springbootcrm.entity.BasDict;
import com.t251.springbootcrm.entity.SalChance;
import com.t251.springbootcrm.repository.BasDictRepository;
import com.t251.springbootcrm.repository.SalChanceRepository;
import com.t251.springbootcrm.repository.UserRepository;
import com.t251.springbootcrm.service.SalChanceService;
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
import java.util.Date;
import java.util.List;

/**
 * @author world
 */
@Service
public class SalChanceServiceImpl implements SalChanceService {
    @Resource
    private SalChanceRepository salChanceRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private BasDictRepository basDictRepository;
    @Override
    public List<SalChance> getAllSalChance() {
        return salChanceRepository.findAll();
    }

    @Override
    public void addSal(SalChance salChance) {
        salChanceRepository.save(salChance);
    }

    @Override
    public void editSal(SalChance salChance) {
        salChanceRepository.save(salChance);
    }

    @Override
    public void delSal(Long id) {
        salChanceRepository.deleteById(id);
    }

    @Override
    public Page<SalChance> getSalChanceByPage(String custName, String title, Pageable pageable) {
        Specification<SalChance> specification=new Specification<SalChance>() {
            @Override
            public Predicate toPredicate(Root<SalChance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>predicates=new ArrayList<>();
                if (custName!=null&&!custName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custName"),"%"+custName+"%"));
                }
                if (title!=null&&!title.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("title"),"%"+title+"%"));
                }
                  //  predicates.add(criteriaBuilder.equal(root.get("status"),"未分配"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return salChanceRepository.findAll(specification,pageable);
    }

    @Override
    public Page<SalChance> getDevPlanByPage(String custName, String linkman, String title, Pageable pageable) {
        Specification<SalChance> specification=new Specification<SalChance>() {
            @Override
            public Predicate toPredicate(Root<SalChance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>predicates=new ArrayList<>();
                if (custName!=null&&!custName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custName"),"%"+custName+"%"));
                }
                if (linkman!=null&&!linkman.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("linkman"),"%"+linkman+"%"));
                }
                if (title!=null&&!title.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("title"),"%"+title+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return salChanceRepository.findAll(specification,pageable);
    }

    @Override
    public SalChance getSalChanceById(Long id) {
        return salChanceRepository.findSalChanceById(id);
    }

    @Override
    public List<BasDict> getBasDictByType(String type) {
        return basDictRepository.findBasDictByType(type);
    }

    @Override
    public void assignChance(Integer dueId,String dueName, Date dueDate,Integer status,Long id) {
        salChanceRepository.updateSalChanceById(dueId,dueName,dueDate,status,id);
    }


}
