package com.t251.springbootcrm.service;

import com.t251.springbootcrm.entity.BasDict;
import com.t251.springbootcrm.entity.SalChance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface SalChanceService {
public List<SalChance>getAllSalChance();
public void addSal(SalChance salChance);
public void editSal(SalChance salChance);
public void delSal(Long id);
public Page<SalChance>getSalChanceByPage(String custName, String title, Pageable pageable);
public Page<SalChance>getDevPlanByPage(String custName,String linkman,String title, Pageable pageable);
public SalChance getSalChanceById(Long id);
public List<BasDict>getBasDictByType(String type);
public void assignChance(Integer dueId,String dueName, Date dueDate,Integer status,Long id);
}
