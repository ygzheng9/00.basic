package pickup.fastjson;

import com.alibaba.fastjson.JSON;


/**
 * Created by yonggang on 7/11/16.
 */
public class JsonTest {
    public static void main(String [] args) {

        Group group = new Group();
        group.setId(0L);
        group.setName("admin");

        User guestUser = new User();
        guestUser.setId(2L);
        guestUser.setName("guest");

        User rootUser = new User();
        rootUser.setId(3L);
        rootUser.setName("root");

        group.addUser(guestUser);
        group.addUser(rootUser);

        String jsonString = JSON.toJSONString(group);

        System.out.println(jsonString);
        // {"id":0,"name":"admin","users":[{"id":2,"name":"guest"},{"id":3,"name":"root"}]}

        // Group group = JSON.parseObject(jsonString, Group.class);
    }
}
