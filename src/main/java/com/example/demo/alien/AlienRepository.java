package com.example.demo.alien;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlienRepository extends JpaRepository<Alien, Long> {
}
