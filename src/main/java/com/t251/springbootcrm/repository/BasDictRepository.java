package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.BasDict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasDictRepository extends JpaRepository<BasDict,Long> {
    public List<BasDict>findBasDictByType(String type);
}
