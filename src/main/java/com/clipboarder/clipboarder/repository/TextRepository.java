package com.clipboarder.clipboarder.repository;

import com.clipboarder.clipboarder.entity.Text;
import com.clipboarder.clipboarder.entity.dto.TextDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TextRepository extends JpaRepository<Text, Long> {
    List<Text> findAllByUserEmail(String email);
}
