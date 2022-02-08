package com.tenniscourts.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ReservationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReservationController reservationController;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void testGETIndexReservation() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPOSTIndexReservationsMustNotReceivePostWithoutParameters() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reservations"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void shouldCreateAReservation() throws Exception {
        CreateReservationRequestDTO dto = CreateReservationRequestDTO.builder().guestId(1L).scheduleId(1L).build();
        mockMvc.perform(post("/reservations").contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateMultipleAReservation() throws Exception {
        CreateReservationRequestDTO dto1 = CreateReservationRequestDTO.builder().guestId(1L).scheduleId(1L).build();
        mockMvc.perform(post("/reservations").contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isCreated());

        CreateReservationRequestDTO dto2 = CreateReservationRequestDTO.builder().guestId(1L).scheduleId(2L).build();
        mockMvc.perform(post("/reservations").contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto2)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldRescheduleAReservation() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/reservations/1/reschedule/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}