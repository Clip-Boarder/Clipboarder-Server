package com.clipboarder.clipboarder.repository;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.dto.ClipboarderUserDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClipboarderRepository extends JpaRepository<ClipboarderUser, String> {
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "select u from ClipboarderUser u where u.email = :email")
    Optional<ClipboarderUser> findByEmail(String email);
}
