package com.basedocker.infrastructure.orm.repository;

import com.basedocker.infrastructure.orm.model.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaEmailConfirmationRepository extends JpaRepository<EmailConfirmation, Integer> {

    @Query("FROM EmailConfirmation ec LEFT JOIN FETCH ec.credential WHERE ec.hash = :hash")
    Optional<EmailConfirmation> findByHash(@Param("hash") String hash);
}
