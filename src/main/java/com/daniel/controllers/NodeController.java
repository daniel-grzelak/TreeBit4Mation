package com.daniel.controllers;

import com.daniel.dto.NodeDto;
import com.daniel.service.MyServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * This simple controller is taking care of four tasks:
 * 1. Updating the view of the site by inserting data into it;
 * 2. Adding the node;
 * 3. Deleting the node;
 * 4. Modifying the node.
 */
@Controller
public class NodeController {

    private MyServiceImpl myService;

    public NodeController(MyServiceImpl myService) {
        this.myService = myService;
    }

    @GetMapping("/")
    public String formGet(Model m) {
        NodeDto nodeModel = new NodeDto();
        m.addAttribute("node", nodeModel);
        List<NodeDto> nodeDtos = myService.getAllNodeDtos();
        m.addAttribute("nodeDtos", nodeDtos);
        NodeDto nodeDto = nodeDtos.get(0);
        nodeDto.setChildren(myService.childrenRecursion(nodeDto));
        m.addAttribute("nodeDto", nodeDto);
        return "index";
    }

    @PostMapping("/add")
    public String formPost(@ModelAttribute NodeDto nodeDto1, Model m) {


        myService.addNode(nodeDto1);

        NodeDto nodeModel = new NodeDto();
        m.addAttribute("node", nodeModel);
        List<NodeDto> nodeDtos = myService.getAllNodeDtos();
        m.addAttribute("nodeDtos", nodeDtos);
        NodeDto nodeDto = nodeDtos.get(0);
        nodeDto.setChildren(myService.childrenRecursion(nodeDto));
        m.addAttribute("nodeDto", nodeDto);

        return "index";
    }

    @PostMapping("/delete")
    public String formDelete(@ModelAttribute NodeDto nodeDto1, Model m) {


        myService.deleteNode(nodeDto1);

        NodeDto nodeModel = new NodeDto();
        m.addAttribute("node", nodeModel);
        List<NodeDto> nodeDtos = myService.getAllNodeDtos();
        m.addAttribute("nodeDtos", nodeDtos);
        NodeDto nodeDto = nodeDtos.get(0);
        nodeDto.setChildren(myService.childrenRecursion(nodeDto));
        m.addAttribute("nodeDto", nodeDto);
        return "index";
    }

    @PostMapping("/modify")
    public String formModify(@ModelAttribute NodeDto nodeDto1, Model m) {


        myService.modifyNode(nodeDto1);


        NodeDto nodeModel = new NodeDto();
        m.addAttribute("node", nodeModel);
        List<NodeDto> nodeDtos = myService.getAllNodeDtos();
        m.addAttribute("nodeDtos", nodeDtos);
        NodeDto nodeDto = nodeDtos.get(0);
        nodeDto.setChildren(myService.childrenRecursion(nodeDto));
        m.addAttribute("nodeDto", nodeDto);


        return "index";
    }
}
