package generateJson;

import java.util.List;

/**
 * Created by Matt Di on 2017/2/24.
 */
public class Department {

    private Long id;
    private String name;
    private List<Staff> staffList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

}
