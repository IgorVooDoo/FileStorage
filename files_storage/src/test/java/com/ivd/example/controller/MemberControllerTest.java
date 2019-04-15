package com.ivd.example.controller;

import com.ivd.example.service.AccessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberControllerTest {
    @Autowired
    private AccessService accessService;

    @Test
    public void addMemberRead() {
    }

    @Test
    public void addMemberFromQuery() {
    }

    @Test
    public void queryRead() {
    }

    @Test
    public void addMemberLoad() {
    }

    @Test
    public void queryLoad() {
    }

    @Test
    public void delMemberRead() {
    }
}