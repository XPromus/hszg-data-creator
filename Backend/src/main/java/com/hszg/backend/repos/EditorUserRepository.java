package com.hszg.backend.repos;

import com.hszg.backend.data.model.EditorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EditorUserRepository extends JpaRepository<EditorUser, Long> {

    @Query("SELECT u FROM EditorUser u WHERE u.email = ?1")
    Optional<EditorUser> findByEmail(String email);

}
