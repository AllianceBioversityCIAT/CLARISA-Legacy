package org.cgiar.clarisa;

import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource({"classpath:config/clarisa-${spring.profiles.active:local}.properties"})
class ClarisaApplicationTests {


  @Autowired
  UserManager userManager;


  @Test
  void contextLoads() {
    System.out.println("context Load");
  }


  @Test
  public void findUser() {
    User user = userManager.findById(1082L).orElse(null);
    System.out.println("Testing----- user Manager");
    assertTrue(user != null);
  }


}
