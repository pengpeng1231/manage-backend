package com.example.demo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuQuery extends PageDTO {
    private String name;
    private String path;
    private Long status;
    private Long parentId;

}
