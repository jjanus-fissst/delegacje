package com.lbd.projectlbd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbd.projectlbd.dto.DelegationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectLbdApplicationTests {

    @Autowired private MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

//    @Test void delegationAddTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/adddelegation")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(new DelegationDTO(
//                                Timestamp.valueOf("2022-09-01 00:00:00"),
//                                Timestamp.valueOf("2022-09-01 06:00:00"),
//                                "Mirek",
//                                "Anus",
//                                "Sosnowiec",
//                                "PL",
//                                "Daleko"))))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

}
