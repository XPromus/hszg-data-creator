package com.hszg.backend.repos;

import com.hszg.backend.data.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT s FROM Image s WHERE s.object.id = ?1")
    List<Image> findImagesByObjectId(Long id);

}
