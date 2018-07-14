package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

/**
 * Created by Edward on 2018/7/13
 */
public class ProductSaleCountComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount()-p1.getSaleCount();
    }

}
