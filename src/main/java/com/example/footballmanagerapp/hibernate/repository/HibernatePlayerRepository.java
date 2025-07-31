package com.example.footballmanagerapp.hibernate.repository;

import com.example.footballmanagerapp.common.repositroy.PlayerRepository;
import com.example.footballmanagerapp.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernatePlayerRepository implements PlayerRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Player save(Player player) {
        if (player.getId() == null) {
            em.persist(player);
            return player;
        } else {
            return em.merge(player);
        }
    }

    @Override
    public Optional<Player> findById(Long id) {
        return Optional.ofNullable(em.find(Player.class, id));
    }

    @Override
    public List<Player> findAll() {
        return em.createQuery("SELECT p FROM Player p", Player.class).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Player player = em.find(Player.class, id);
        if (player != null) {
            em.remove(player);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String query = "SELECT COUNT(p) FROM Player p WHERE p.id = :id";
        Long count = em.createQuery(query, Long.class).setParameter("id", id).getSingleResult();
        return count > 0;
    }
}
