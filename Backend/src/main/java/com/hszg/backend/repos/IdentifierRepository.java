package com.hszg.backend.repos;

import com.hszg.backend.data.model.Identifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentifierRepository extends JpaRepository<Identifier, Long> {

    @Query("SELECT i FROM Identifier i WHERE i.filename = ?1")
    Optional<Identifier> findByIdentifierName(String name);

}
