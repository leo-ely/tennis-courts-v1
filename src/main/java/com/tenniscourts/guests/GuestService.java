package com.tenniscourts.guests;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    private final GuestMapper guestMapper;

    private final GuestRepository guestRepository;

    public GuestService(GuestMapper guestMapper, GuestRepository guestRepository) {
        this.guestMapper = guestMapper;
        this.guestRepository = guestRepository;
    }

    public List<GuestDTO> findAll(Pageable pageable) {
        return guestMapper.map(guestRepository.findAll(pageable).getContent());
    }

    public GuestDTO findGuestById(Long id) {
        return guestMapper.map(guestRepository.findById(id).get());
    }

    public Optional<Guest> findById(Long id) {
        return guestRepository.findById(id);
    }

    public List<GuestDTO> findByName(String name) {
        return guestMapper.map(guestRepository.findByName(name));
    }

    @Transactional(rollbackFor = Exception.class)
    public GuestDTO insert(GuestDTO dto) {
        return guestMapper.map(guestRepository.save(guestMapper.map(dto)));
    }

    public void delete(Long id) {
        guestRepository.deleteById(id);
    }

}
