package pickup.lambda;

/**
 * Created by YongGang on 2016/10/30.
 */
public class Author {
    private Integer sn;
    private Integer count;

    public Author(Integer sn, Integer count) {
        this.sn = sn;
        this.count = count;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Author{" +
            "sn=" + sn +
            ", count=" + count +
            '}';
    }
}
