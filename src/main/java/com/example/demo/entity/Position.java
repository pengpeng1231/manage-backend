package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "positions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Position extends Base {
    private String name;
    private String code;
    private String type;
    private String level;
    private int status;
    private long depId;
    private long parentId;
}
