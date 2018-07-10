package tmall.bean;

/**
 * Created by Edward on 2018/7/3
 */

/**
 * 属性表
 * 商品详情标签下的产品属性
 * 不同的商品可能有相同的属性，如能效等级
 */

public class Property {

    // 属性的唯一识别的 id
    private int id;
    // 属性的名称
    private String name;
    // 和分类表的多对一关系
    private Category category;

    // Get, Set
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
