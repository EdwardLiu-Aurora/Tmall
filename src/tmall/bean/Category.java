package tmall.bean;

import java.util.List;

/**
 * Created by Edward on 2018/7/3
 */

/**
 * 分类表
 * 首页的竖状导航栏显示所有分类信息
 */

public class Category {

    // 目录 ID
    private int id;
    // 栏目的名称
    private String name;
    // 产品的列表
    private List<Product> products;
    // 产品列表的列表（每一行）
    private List<List<Product>> productsByRow;

    // get，set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    // 重写 toString 方法，用于测试
    @Override
    public String toString() {
        return "Category [name=" + name + "]";
    }
}
