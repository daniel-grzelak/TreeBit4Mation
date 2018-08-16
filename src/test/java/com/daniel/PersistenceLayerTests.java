package com.daniel;

import com.daniel.dao.NodeDao;
import com.daniel.domain.Node;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is sorely for the purpose of testing the persistence layer. In this case it's NodeDao repository.
 */

@RunWith(SpringRunner.class)
@DataJpaTest

public class PersistenceLayerTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private NodeDao nodeDao;

    @Test
    public void idTest() {

        Node node = Node.builder().id(1L).number(10).build();
        testEntityManager.merge(node);
        testEntityManager.flush();

        Node node2 = nodeDao.findById(node.getId()).get();
        Assertions.assertThat(node2.getId()).isEqualTo(node.getId());

    }

    @Test
    public void numberTest() {

        Node node = Node.builder().id(1L).number(10).build();
        testEntityManager.merge(node);
        testEntityManager.flush();

        Node node2 = nodeDao.findById(node.getId()).get();
        Assertions.assertThat(node2.getNumber()).isEqualTo(node.getNumber());

    }

    @Test
    public void childrenTest() {

        List<Node> children = new ArrayList<>();
        children.add(Node.builder().id(2L).number(4).build());
        Node node = Node.builder().id(1L).number(10).children(children).build();
        testEntityManager.merge(node);
        testEntityManager.flush();

        Node node2 = nodeDao.findById(node.getId()).get();
        System.out.println(node2);
        Assertions.assertThat(node2.getChildren()).isEqualTo(node.getChildren()); //throws error because Equals compares by reference,
        //while the objects are the same they are in different parts of memory, a fix would be to change Equals to look
        //at values

    }

    @Test
    public void parentTest() {


        Node parentNode = Node.builder().id(1L).number(3).build();
        testEntityManager.merge(parentNode);
        Node node = Node.builder().id(2L).number(10).node(parentNode).build();
        testEntityManager.merge(node);
        testEntityManager.flush();
        Node node2 = nodeDao.findById(node.getId()).get();
        System.out.println(node2);
        Assertions.assertThat(node2.getNode()).isEqualTo(node.getNode());

    }


}
