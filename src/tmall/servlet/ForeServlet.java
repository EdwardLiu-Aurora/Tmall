package tmall.servlet;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.util.HtmlUtils;
import tmall.bean.*;
import tmall.comparator.*;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Edward on 2018/7/12
 */

public class ForeServlet extends BaseForeServlet {

    // 主页内容填充方法
    public String home(HttpServletRequest request, HttpServletResponse response, Page page){
        List<Category> cs = new CategoryDAO().list();
        new ProductDAO().fill(cs);
        new ProductDAO().fillByRow(cs);
        request.setAttribute("cs",cs);
        return "home.jsp";
    }

    // 注册方法
    public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);
        System.out.println(name);
        boolean exist = userDAO.isExist(name);

        if(exist){
            request.setAttribute("msg", "用户名已经被使用,不能使用");
            return "register.jsp";
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        userDAO.add(user);

        return "@registerSuccess.jsp";
    }

    // 登录方法
    public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        name = HtmlUtils.htmlEscape(name);
        String password = request.getParameter("password");

        User user = userDAO.get(name,password);

        if(null==user){
            request.setAttribute("msg", "账号密码错误");
            return "login.jsp";
        }
        request.getSession().setAttribute("user", user);
        return "@forehome";
    }

    // 登出方法
    public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
        request.getSession().removeAttribute("user");
        return "@forehome";
    }

    // 填充产品页的方法
    public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productDAO.get(pid);

        List<ProductImage> productSingleImages = productImageDAO.list(p, ProductImageDAO.type_single);
        List<ProductImage> productDetailImages = productImageDAO.list(p, ProductImageDAO.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);

        List<PropertyValue> pvs = propertyValueDAO.list(p.getId());

        List<Review> reviews = reviewDAO.list(p.getId());

        productDAO.setSaleAndReviewNumber(p);

        request.setAttribute("reviews", reviews);

        request.setAttribute("p", p);
        request.setAttribute("pvs", pvs);
        return "product.jsp";
    }

    // 判断是否已经登录的方法
    public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user =(User) request.getSession().getAttribute("user");
        if(null!=user)
            return "%success";
        return "%fail";
    }

    // 使用 ajax 登录的方法
    public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = userDAO.get(name,password);

        if(null==user){
            return "%fail";
        }
        request.getSession().setAttribute("user", user);
        return "%success";
    }

    // 用来对分类进行排序的方法
    public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));

        Category c = new CategoryDAO().get(cid);
        new ProductDAO().fill(c);
        new ProductDAO().setSaleAndReviewNumber(c.getProducts());

        String sort = request.getParameter("sort");
        if(null!=sort){
            switch(sort){
                case "review":
                    Collections.sort(c.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(c.getProducts(),new ProductDateComparator());
                    break;

                case "saleCount" :
                    Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(c.getProducts(),new ProductAllComparator());
                    break;
            }
        }

        request.setAttribute("c", c);
        return "category.jsp";
    }

    // 模糊搜索方法
    public String search(HttpServletRequest request, HttpServletResponse response, Page page){
        String keyword = request.getParameter("keyword");
        List<Product> ps= new ProductDAO().search(keyword,0,20);
        productDAO.setSaleAndReviewNumber(ps);
        request.setAttribute("ps",ps);
        return "searchResult.jsp";
    }

    // 立刻购买
    public String buyone(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int num = Integer.parseInt(request.getParameter("num"));
        Product p = productDAO.get(pid);
        int oiid = 0;

        User user =(User) request.getSession().getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId()==p.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemDAO.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setNumber(num);
            oi.setProduct(p);
            orderItemDAO.add(oi);
            oiid = oi.getId();
            System.out.println("订单项是"+oiid);
        }
        return "@forebuy?oiid="+oiid;
    }

    // 结算页
    public String buy(HttpServletRequest request, HttpServletResponse response, Page page){
        String[] oiids=request.getParameterValues("oiid");
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;

        for (String strid : oiids) {
            int oiid = Integer.parseInt(strid);
            OrderItem oi= orderItemDAO.get(oiid);
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
            ois.add(oi);
        }

        request.getSession().setAttribute("ois", ois);
        request.setAttribute("total", total);
        return "buy.jsp";
    }

    // 加入购物车
    public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productDAO.get(pid);
        int num = Integer.parseInt(request.getParameter("num"));

        User user =(User) request.getSession().getAttribute("user");
        boolean found = false;

        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId()==p.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemDAO.update(oi);
                found = true;
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setNumber(num);
            oi.setProduct(p);
            orderItemDAO.add(oi);
        }
        return "%success";
    }

    // 购物车
    public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user =(User) request.getSession().getAttribute("user");
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        request.setAttribute("ois", ois);
        return "cart.jsp";
    }

    // 调整订单数量
    public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user =(User) request.getSession().getAttribute("user");
        if(null==user)
            return "%fail";

        int pid = Integer.parseInt(request.getParameter("pid"));
        int number = Integer.parseInt(request.getParameter("number"));
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId()==pid){
                oi.setNumber(number);
                orderItemDAO.update(oi);
                break;
            }

        }
        return "%success";
    }

    // 删除订单项目
    public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page){
        User user =(User) request.getSession().getAttribute("user");
        if(null==user)
            return "%fail";
        int oiid = Integer.parseInt(request.getParameter("oiid"));
        orderItemDAO.delete(oiid);
        return "%success";
    }

    // 创建订单
    public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page){
        User user =(User) request.getSession().getAttribute("user");

        List<OrderItem> ois= (List<OrderItem>) request.getSession().getAttribute("ois");
        if(ois.isEmpty())
            return "@login.jsp";

        String address = request.getParameter("address");
        String post = request.getParameter("post");
        String receiver = request.getParameter("receiver");
        String mobile = request.getParameter("mobile");
        String userMessage = request.getParameter("userMessage");

        Order order = new Order();
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +RandomUtils.nextInt(10000);

        order.setOrderCode(orderCode);
        order.setAddress(address);
        order.setPost(post);
        order.setReceiver(receiver);
        order.setMobile(mobile);
        order.setUserMessage(userMessage);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderDAO.waitPay);

        orderDAO.add(order);
        float total =0;
        for (OrderItem oi: ois) {
            oi.setOrder(order);
            orderItemDAO.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }

        return "@forealipay?oid="+order.getId() +"&total="+total;
    }

    // 支付方法
    public String alipay(HttpServletRequest request, HttpServletResponse response, Page page){
        return "alipay.jsp";
    }

    // 已支付
    public String payed(HttpServletRequest request, HttpServletResponse response, Page page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order order = orderDAO.get(oid);
        order.setStatus(OrderDAO.waitDelivery);
        order.setPayDate(new Date());
        new OrderDAO().update(order);
        request.setAttribute("o", order);
        return "payed.jsp";
    }

    // 已购商品
    public String bought(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user =(User) request.getSession().getAttribute("user");
        List<Order> os= orderDAO.list(user.getId(),OrderDAO.delete);

        orderItemDAO.fill(os);

        request.setAttribute("os", os);

        return "bought.jsp";
    }

    // 确认支付
    public String confirmPay(HttpServletRequest request, HttpServletResponse response, Page page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderDAO.get(oid);
        orderItemDAO.fill(o);
        request.setAttribute("o", o);
        return "confirmPay.jsp";
    }

    // 确认收货
    public String orderConfirmed(HttpServletRequest request, HttpServletResponse response, Page page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderDAO.get(oid);
        o.setStatus(OrderDAO.waitReview);
        o.setConfirmDate(new Date());
        orderDAO.update(o);
        return "orderConfirmed.jsp";
    }

    // 删除订单
    public String deleteOrder(HttpServletRequest request, HttpServletResponse response, Page page){
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderDAO.get(oid);
        o.setStatus(OrderDAO.delete);
        orderDAO.update(o);
        return "%success";
    }

    // 评价方法
    public String review(HttpServletRequest request, HttpServletResponse response, Page page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderDAO.get(oid);
        orderItemDAO.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewDAO.list(p.getId());
        productDAO.setSaleAndReviewNumber(p);
        request.setAttribute("p", p);
        request.setAttribute("o", o);
        request.setAttribute("reviews", reviews);
        return "review.jsp";
    }

    // 提交评价
    public String doreview(HttpServletRequest request, HttpServletResponse response, Page page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderDAO.get(oid);
        o.setStatus(OrderDAO.finish);
        orderDAO.update(o);
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productDAO.get(pid);

        String content = request.getParameter("content");

        content = HtmlUtils.htmlEscape(content);

        User user =(User) request.getSession().getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setProduct(p);
        review.setCreateDate(new Date());
        review.setUser(user);
        reviewDAO.add(review);

        return "@forereview?oid="+oid+"&showonly=true";
    }
}
