package com.daniel.service;

import com.daniel.dao.NodeDao;
import com.daniel.domain.MyMapper;
import com.daniel.domain.Node;
import com.daniel.dto.NodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MyServiceImpl {

    @Autowired
    private NodeDao nodeDao;
    @Autowired
    private MyMapper myMapper;

    public List<NodeDto> getAllNodeDtos() {
        return nodeDao.findAll().stream().map(myMapper::fromNodeToNodeDto).collect(Collectors.toList());
    }

    public NodeDto addNode(NodeDto nodeDto) {
        Node node = myMapper.fromNodeDtoToNode(nodeDto);
        Node parentNode = nodeDao.getOne(nodeDto.getNodeDto());
        node.setNode(parentNode);
        return myMapper.fromNodeToNodeDto(nodeDao.save(node));
    }

    public NodeDto deleteNode(NodeDto nodeDto) {
        nodeDao.deleteById(nodeDto.getId());
        return nodeDto;
    }

    public NodeDto modifyNode(NodeDto nodeDto) {
        Node nodeFromDb = nodeDao.getOne(nodeDto.getId());
        Node node = myMapper.fromNodeDtoToNode(nodeDto);
        node.setChildren(nodeFromDb.getChildren());
        Node parentNode = nodeDao.getOne(nodeDto.getNodeDto());
        node.setNode(parentNode);
        parentNode.getChildren().add(node);
        return myMapper.fromNodeToNodeDto(nodeDao.save(node));
    }


    /**
     * @param nodeDto NodeDTO we are willing to get the children for.
     * @return Returns the list of all the children of a node.
     * This method returns all the children of the node. When reaching the final node (the leaf), it replaces the value of the leaf with the sum of all nodes BEFORE it. @see leafSumCounter
     */
    public List<NodeDto> childrenRecursion(NodeDto nodeDto) {
        Node nodeFromDb = nodeDao.findById(nodeDto.getId()).orElseThrow(NullPointerException::new);
        List<NodeDto> children = new ArrayList<>();

        if (nodeFromDb.getChildren() != null && !nodeFromDb.getChildren().isEmpty()) {
            for (Node n : nodeFromDb.getChildren()) {
                NodeDto n1 = myMapper.fromNodeToNodeDto(n);
                n1.setChildren(childrenRecursion(n1));
                children.add(n1);
            }
        } else {
            nodeDto.setNumber(leafSumCounter(nodeDto));
        }

        return children;

    }

    /**
     * @param nodeDto NodeDTO we want to act as a leaf.
     * @return Returns the sum of all the nodes BEFORE the leaf.
     */
    public int leafSumCounter(NodeDto nodeDto) {

        int sum = 0;


        if (nodeDao.getOne(nodeDto.getId()).getNode() != null) {
            NodeDto nodeDtoFromDb = myMapper.fromNodeToNodeDto(nodeDao.getOne(nodeDto.getId()).getNode());
            sum += nodeDtoFromDb.getNumber();
            sum += leafSumCounter(nodeDtoFromDb);


        }
        return sum;
    }
}
