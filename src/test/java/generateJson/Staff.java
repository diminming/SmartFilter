package generateJson;

import java.util.Date;

/**
 * Created by Matt Di on 2017/2/24.
 */
public class Staff {

    private long sNo;
    private String name;
    private float salary;
    private Date onboardDate;

    public long getsNo() {
        return sNo;
    }

    public void setsNo(long sNo) {
        this.sNo = sNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getOnboardDate() {
        return onboardDate;
    }

    public void setOnboardDate(Date onboardDate) {
        this.onboardDate = onboardDate;
    }

}
