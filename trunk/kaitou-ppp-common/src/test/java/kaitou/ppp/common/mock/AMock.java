package kaitou.ppp.common.mock;

/**
 * 桩A.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 12:37
 */
public class AMock {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public AMock setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AMock setName(String name) {
        this.name = name;
        return this;
    }
}
