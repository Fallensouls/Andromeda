package com.fallensouls.userservice.controller;

import com.fallensouls.userservice.UserServiceApplicationTests;
import com.fallensouls.userservice.domain.User;
import com.fallensouls.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Transactional
@Rollback
public class UserControllerTest extends UserServiceApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
    }

    @Test
    public void getUserListTest() throws Exception{
//        for(int i = 0; i < 20; i++){
//            addUserTest();
//        }
        mvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getUserByIdTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/user/cc2c04a8-5b79-4061-8608-23f739fe3124"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addUserTest() throws Exception{
        User user = new User();
        user.setUsername("fallensouls");
        user.setPassword("dfjdljfsdfd");
        user.setTelphone("12244545444");
        user.setEmail("ddsfsf@14533.com");
        user.setIslocked(false);
        user.setLastlogin(new Date());
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUserTest() throws Exception{
        User user = userRepository.findById(UUID.fromString("cc2c04a8-5b79-4061-8608-23f739fe3124")).get();
        user.setEmail("aaaaaa@14533.com");
        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(MockMvcRequestBuilders.put("/user/cc2c04a8-5b79-4061-8608-23f739fe3124")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.delete("/user/cc2c04a8-5b79-4061-8608-23f739fe3124"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void lockUserTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.delete("/user/cc2c04a8-5b79-4061-8608-23f739fe3124"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}