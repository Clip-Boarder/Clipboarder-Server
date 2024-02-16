package com.clipboarder.clipboarder.repository;

import com.clipboarder.clipboarder.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByUser_Email(String email);
}
