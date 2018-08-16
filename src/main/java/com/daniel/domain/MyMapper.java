package com.daniel.domain;


import com.daniel.dto.NodeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Class mapping the Node entity to Node Data Transfer Object.
 */

@Component
public class MyMapper {


    public NodeDto fromNodeToNodeDto(Node node) {

        return node == null ? null : NodeDto.builder()
                .id(node.getId())
                .number(node.getNumber())
                .children(new ArrayList<>())
                .build();
    }

    public Node fromNodeDtoToNode(NodeDto nodeDto) {
        return nodeDto == null ? null : Node.builder()
                .id(nodeDto.getId())
                .number(nodeDto.getNumber())
                .children(new ArrayList<>())
                .build();
    }
}
