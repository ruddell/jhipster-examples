package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MonoApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link ClientForwardController} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonoApp.class)
public class ClientForwardControllerIT {

    private MockMvc restMockMvc;

    @Before
    public void setup() {
        ClientForwardController clientForwardController = new ClientForwardController();
        LogsResource logsResource = new LogsResource();
        this.restMockMvc = MockMvcBuilders
            .standaloneSetup(clientForwardController, logsResource)
            .build();
    }

    @Test
    public void getBackendEndpoint() throws Exception {
        restMockMvc.perform(get("/management/logs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getClientEndpoint() throws Exception {
        ResultActions perform = restMockMvc.perform(get("/non-existant-mapping"));
        perform
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/"));
    }

    @Test
    public void getNestedClientEndpoint() throws Exception {
        restMockMvc.perform(get("/admin/user-management"))
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/"));
    }
}
