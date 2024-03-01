package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.service.ClipboarderUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ClipboarderUserController {
    private final ClipboarderUserService clipboarderUserService;

}
