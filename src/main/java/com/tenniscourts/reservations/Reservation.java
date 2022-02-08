package com.tenniscourts.reservations;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity<Long> {

    @OneToOne @NotNull private Guest guest;
    @ManyToOne @NotNull private Schedule schedule;
    @NotNull private BigDecimal value;
    @NotNull @Enumerated(EnumType.STRING) private ReservationStatus reservationStatus;
    private BigDecimal refundValue;
}
