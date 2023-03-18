package com.travelapp.server.repository;

import com.travelapp.server.entity.Hotel;
import com.travelapp.server.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
