package com.hszg.backend.repos;

import com.hszg.backend.data.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearRepository extends JpaRepository<Year, Long> {

    @Query("SELECT s FROM Year s WHERE s.object.id = ?1")
    List<Year> findYearsByObjectId(Long id);

}
