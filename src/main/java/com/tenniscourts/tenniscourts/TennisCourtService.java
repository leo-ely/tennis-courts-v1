package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TennisCourtService {

    private final TennisCourtMapper tennisCourtMapper;

    private final TennisCourtRepository tennisCourtRepository;
    private final ScheduleService scheduleService;

    @Autowired
    public TennisCourtService(TennisCourtMapper tennisCourtMapper,
                              TennisCourtRepository tennisCourtRepository,
                              ScheduleService scheduleService) {
        this.tennisCourtMapper = tennisCourtMapper;
        this.tennisCourtRepository = tennisCourtRepository;
        this.scheduleService = scheduleService;
    }

    @Transactional(rollbackFor = Exception.class)
    public TennisCourtDTO addTennisCourt(TennisCourtDTO dto) {
        return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(dto)));
    }

    public TennisCourtDTO findTennisCourtById(Long id) {
        Optional<TennisCourt> tennisCourt = tennisCourtRepository.findById(id);
        if (!tennisCourt.isPresent())
            throw new EntityNotFoundException("Tennis Court not found.");

        return tennisCourt.map(tennisCourtMapper::map).get();
    }

    public List<TennisCourtDTO> findAll(Pageable pageable) {
        return tennisCourtMapper.map(tennisCourtRepository.findAll(pageable).getContent());
    }

    public List<TennisCourtDTO> findTennisCourtByName(String name) {
        return tennisCourtMapper.map(tennisCourtRepository.findByName(name));
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long id) {
        TennisCourtDTO dto = findTennisCourtById(id);
        dto.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(id));
        return dto;
    }

}
