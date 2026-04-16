package com.whxy.campusbooktrade2.entity;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class Book {
    private Long id;
    private String name;
    private String author;
    private BigDecimal price;
    private String oldLevel;
    private String category;
    private Long userId;
    private Integer status;
    private Date createTime;
}