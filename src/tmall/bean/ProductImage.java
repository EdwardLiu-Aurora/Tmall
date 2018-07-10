package tmall.bean;

/**
 * Created by Edward on 2018/7/3
 */

/**
 * 产品图片表
 */

public class ProductImage {

    // 唯一 ID
    private int id;
    // 图片的类型
    private String type;
    // 管理和产品的多对一关系
    private Product product;

    // Get，Set

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
