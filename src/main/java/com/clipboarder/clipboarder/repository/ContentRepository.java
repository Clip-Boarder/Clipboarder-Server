package com.clipboarder.clipboarder.repository;

import com.clipboarder.clipboarder.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    public List<Content> findAllByUser_Email(String email);
}
