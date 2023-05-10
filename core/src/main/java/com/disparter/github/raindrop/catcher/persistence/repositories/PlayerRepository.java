package com.disparter.github.raindrop.catcher.persistence.repositories;

import com.disparter.github.raindrop.catcher.persistence.entities.Player;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    List<Player> findTop3ByOrderByScoreDesc();
}
