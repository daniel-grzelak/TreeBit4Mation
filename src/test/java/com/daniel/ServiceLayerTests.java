package com.daniel;

import com.daniel.dao.NodeDao;
import com.daniel.domain.MyMapper;
import com.daniel.domain.Node;
import com.daniel.dto.NodeDto;
import com.daniel.service.MyServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Those tests are to test the business logic of the application - the service layer.
 */

@RunWith(SpringRunner.class)
public class ServiceLayerTests {

    @Autowired
    private MyServiceImpl myService;
    @MockBean
    private NodeDao nodeDao;

    /**
     * This method exists to teach Mockito how to act whenever I do something with the mocked Service.
     */
    @Before
    public void beforeTest() {

        Node node1 = Node.builder().id(1L).number(5).build();
        Node node2 = Node.builder().id(2L).number(10).build();
        Node node3 = Node.builder().id(3L).number(15).build();

        List<Node> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        Mockito
                .when(nodeDao.findById(node1.getId()))
                .thenReturn(java.util.Optional.ofNullable(node1));

        Mockito
                .when(nodeDao.findById(node2.getId()))
                .thenReturn(java.util.Optional.ofNullable(node2));


        Mockito
                .when(nodeDao.findAll())
                .thenReturn(nodes);

        Mockito
                .when(nodeDao.save(node1))
                .thenReturn(node1);


    }

    @Test
    public void deleteTest() {
        NodeDto nodeDto = new NodeDto();
        nodeDto.setId(1L);
        myService.deleteNode(nodeDto);
        System.out.println(myService.getAllNodeDtos());
    }

    @Test
    public void getAllTest() {
        List<NodeDto> nodes = myService.getAllNodeDtos();
        Assertions.assertThat(nodes).isEqualTo(myService.getAllNodeDtos());
    }

    @TestConfiguration
    public static class MyServiceTestContextConfiguration {


        @Bean
        public MyMapper myMapper() {
            return new MyMapper();
        }


        @Bean
        public MyServiceImpl myService() {
            return new MyServiceImpl();
        }
    }


}
