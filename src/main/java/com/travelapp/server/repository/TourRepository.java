package com.travelapp.server.repository;

import com.travelapp.server.entity.Role;
import com.travelapp.server.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
}
