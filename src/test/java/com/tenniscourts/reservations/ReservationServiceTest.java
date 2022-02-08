package com.tenniscourts.reservations;

import com.tenniscourts.schedules.Schedule;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ContextConfiguration(classes = ReservationService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = Schedule.builder().startDateTime(LocalDateTime.now().plusDays(2)).build();
        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder()
                .schedule(schedule)
                .value(BigDecimal.TEN).build()), BigDecimal.TEN);
    }

    @Test
    public void getRefundValue75Refund() {
        Schedule schedule = Schedule.builder().startDateTime(LocalDateTime.now().plusHours(13)).build();
        Assert.assertEquals("" + reservationService.getRefundValue(Reservation.builder()
                .schedule(schedule)
                .value(BigDecimal.TEN).build()), "7.50");
    }

    @Test
    public void getRefundValue50Refund() {
        Schedule schedule = Schedule.builder().startDateTime(LocalDateTime.now().plusHours(8)).build();
        Assert.assertEquals("" + reservationService.getRefundValue(Reservation.builder()
                .schedule(schedule)
                .value(BigDecimal.TEN).build()), "5.0");
    }

    @Test
    public void getRefundValue25Refund() {
        Schedule schedule = Schedule.builder().startDateTime(LocalDateTime.now().plusHours(1)).build();
        Assert.assertEquals("" + reservationService.getRefundValue(Reservation.builder()
                .schedule(schedule)
                .value(BigDecimal.TEN).build()), "2.50");
    }

}