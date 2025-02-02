package com.tenniscourts.reservations;

import com.tenniscourts.schedules.ScheduleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;
    private ScheduleDTO schedule;
    private String reservationStatus;
    private ReservationDTO previousReservation;
    private BigDecimal refundValue;
    private BigDecimal value;
    @NotNull private Long scheduledId;
    @NotNull private Long guestId;
}
