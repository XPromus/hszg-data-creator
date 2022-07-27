package com.hszg.backend.repos;

import com.hszg.backend.data.model.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectRepository extends JpaRepository<Object, Long> {

    @Query("SELECT o FROM Object o WHERE o.identifierId = ?1")
    List<Object> findObjectsByIdentifierId(Long id);

}
