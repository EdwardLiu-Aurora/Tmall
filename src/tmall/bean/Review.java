package tmall.bean;

import java.util.Date;

/**
 * Created by Edward on 2018/7/3
 */

/**
 * 评价表
 */
public class Review {

    private int id;
    // 评价内容
    private String content;
    // 创建日期
    private Date createDate;
    // 评价用户
    private User user;
    // 评价产品
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
