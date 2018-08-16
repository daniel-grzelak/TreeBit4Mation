package com.daniel.dao;


import com.daniel.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The only repository required to persist a node.
 */

@Repository
public interface NodeDao extends JpaRepository<Node, Long> {
}
