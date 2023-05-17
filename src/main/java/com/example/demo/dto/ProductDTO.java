package com.example.demo.dto;

import com.example.demo.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {
    private String id;
    private String title;
    private String maker;
    private String color;
    private String userId;

    public ProductDTO(final ProductEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.maker = entity.getMaker();
        this.color = entity.getColor();
        this.userId = entity.getUserId();
    }

    public static ProductEntity toEntity(ProductDTO dto) {
        return ProductEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .maker(dto.getMaker())
                .color(dto.getColor())
                .userId(dto.getUserId())
                .build();
    }
}
