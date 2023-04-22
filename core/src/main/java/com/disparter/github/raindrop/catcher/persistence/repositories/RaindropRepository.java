package com.disparter.github.raindrop.catcher.persistence.repositories;

import com.disparter.github.raindrop.catcher.models.Raindrop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RaindropRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RaindropRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final Raindrop raindrop) {
        final String sql = "INSERT INTO raindrops (x, y, size, speed) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, raindrop.getX(), raindrop.getY(), raindrop.getSize(), raindrop.getSpeed());
    }

    public List<Raindrop> findAll() {
        String sql = "SELECT * FROM raindrops";
        return jdbcTemplate.query(sql, new RaindropRow
