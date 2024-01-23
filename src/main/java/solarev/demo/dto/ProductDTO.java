package solarev.demo.dto;

import solarev.demo.entity.Product;
import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String title;
    private String description;
    private int price;
    private Long vendorId;

    public Product toEntity() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }
}
