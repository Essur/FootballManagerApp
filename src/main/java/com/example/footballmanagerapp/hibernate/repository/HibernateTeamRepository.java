package com.example.footballmanagerapp.hibernate.repository;

import com.example.footballmanagerapp.common.repositroy.TeamRepository;
import com.example.footballmanagerapp.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateTeamRepository implements TeamRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Team save(Team team) {
        if (team.getId() == null) {
            em.persist(team);
            return team;
        } else {
            return em.merge(team);
        }
    }

    @Override
    public Optional<Team> findById(Long id) {
        return Optional.ofNullable(em.find(Team.class, id));
    }

    @Override
    public List<Team> findAll() {
        return em.createQuery("SELECT t FROM Team t", Team.class).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Team team = em.find(Team.class, id);
        if (team != null) {
            em.remove(team);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String query = "SELECT COUNT(t) FROM Team t WHERE t.id = :id";
        Long count = em.createQuery(query, Long.class).setParameter("id", id).getSingleResult();
        return count > 0;
    }

    @Override
    public boolean existsByName(String name) {
        String query = "SELECT COUNT(t) FROM Team t WHERE t.name = :name";
        Long count = em.createQuery(query, Long.class)
                .setParameter("name", name)
                .getSingleResult();
        return count > 0;
    }
}