package com.pharmacy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BranchInfoPrinter implements CommandLineRunner {

    @Value("${app.branch-name}")
    private String branchName;

    @Value("${app.hotline}")
    private String hotline;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Override
    public void run(String... args) {
        System.out.println("==========================================");
        System.out.println(" Chi nhanh : " + branchName);
        System.out.println(" Hotline   : " + hotline);
        System.out.println(" Database  : " + datasourceUrl);
        System.out.println("==========================================");
    }
}
