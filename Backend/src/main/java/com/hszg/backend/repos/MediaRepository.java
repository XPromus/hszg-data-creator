package com.hszg.backend.repos;

import com.hszg.backend.data.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    @Query("SELECT m FROM Media m WHERE m.object.id = ?1")
    List<Media> findMediaByObjectId(Long id);

}
