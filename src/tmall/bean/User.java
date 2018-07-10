package tmall.bean;

/**
 * Created by Edward on 2018/7/3
 */

/**
 * 用户表
 * 记录用户信息
 */
public class User {

    private int id;             // 唯一识别 ID
    private String name;        // 用户名
    private String password;    // 密码

    // Get、Set

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 获取该用户的匿名名称，在评价时显示用户名使用
    public String getAnonymousName() {
        // 如果名称为空，则返回 null
        if(name==null) return null;
        // 如果名称长度小于 1，则返回 *
        if(name.length()<=1) return "*";
        // 如果名称长度等于 2，则返回 x*
        if(name.length()==2) return name.substring(0,1)+"*";
        // 将名称字符串化为字符串数组
        char[] cs = name.toCharArray();
        for(int i = 1; i < cs.length - 1; i++) {
            // 除了头尾，全变为 *
            cs[i] = '*';
        }
        return new String(cs);
    }
}
