package com.fallensouls.messageservice.controller;

import com.fallensouls.messageservice.MessageServiceApplicationTests;
import com.fallensouls.messageservice.domain.MessageRequest;
import com.fallensouls.messageservice.enums.MessageTypeEnum;
import com.fallensouls.messageservice.enums.TemplateCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MessageControllerTest extends MessageServiceApplicationTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
    }

    @Test
    public void sendMessageTest() throws Exception {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setTo("122584482@qq.com");
        messageRequest.setSubject("Test");
        messageRequest.setContent("测试controller");
        HashMap<String, String>contentmap = new HashMap<>();
        contentmap.put("username","HatsuneMiku");
        contentmap.put("id","Fallensouls");
        messageRequest.setContentmap(contentmap);
        messageRequest.setType(MessageTypeEnum.HTML_MAIL);
        messageRequest.setTemplatecode(TemplateCodeEnum.TEST);

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(MockMvcRequestBuilders.post("/message/send")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(messageRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
//                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));
    }

}