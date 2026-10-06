package com.pharmacy.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BranchController {

    @Value("${app.branch-name}")
    private String branchName;

    @Value("${app.hotline}")
    private String hotline;

    @GetMapping("/branch")
    public Map<String, String> info() {
        return Map.of("branchName", branchName, "hotline", hotline);
    }
}
