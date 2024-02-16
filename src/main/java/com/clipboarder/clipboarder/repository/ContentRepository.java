package com.clipboarder.clipboarder.repository;

import com.clipboarder.clipboarder.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
