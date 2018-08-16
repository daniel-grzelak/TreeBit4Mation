package com.daniel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class ensures better handling of a nodes. By working with this Data-Transfer Object, one does not need to worry about messing up the entities.
 * In bigger application it also would allow to be less expensive in calls, since a single DTO contains only the required data for the application.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeDto {

    private Long id;
    private int number;
    private List<NodeDto> children;
    private Long nodeDto;


}
