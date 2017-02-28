package simple;

import cc.smartform.smartfilter.SmartFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import generateJson.Staff;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Matt Di on 2017/2/24.
 */
public class SimpleFilter {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void filter() throws CannotCompileException, InstantiationException, NotFoundException, IllegalAccessException, ClassNotFoundException, JsonProcessingException {

        Staff staffA = new Staff();
        staffA.setsNo(12345);
        staffA.setName("Matt Di");
        staffA.setSalary(1.23f);
        staffA.setOnboardDate(new Date());

        System.out.println("---------- original: ----------");
        System.out.println(staffA.getsNo());
        System.out.println(staffA.getName());
        System.out.println(staffA.getSalary());
        System.out.println(staffA.getOnboardDate());

        System.out.println(mapper.writeValueAsString(staffA));

        Staff proxy = new SmartFilter().getProxyObject(staffA, new HashMap<String, String[]>(){{
            put(Staff.class.getName(), new String[]{"getsNo", "getName"});
        }});

        System.out.println("---------- filtered: ----------");
        System.out.println(proxy.getsNo());
        System.out.println(proxy.getName());
        System.out.println(proxy.getSalary());
        System.out.println(proxy.getOnboardDate());

        System.out.println(mapper.writeValueAsString(proxy));

    }

    @Test
    public void gap() throws CannotCompileException, InstantiationException, NotFoundException, IllegalAccessException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++) {
            Staff staffA = new Staff();
            staffA.setsNo(12345);
            staffA.setName("Matt Di");
            staffA.setSalary(1.23f);
            staffA.setOnboardDate(new Date());

            System.out.println(staffA.getsNo());
            System.out.println(staffA.getName());
            System.out.println(staffA.getSalary());
            System.out.println(staffA.getOnboardDate());
        }
        long end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start));

//        long start = System.currentTimeMillis();
//        for(int i = 0; i < 1000000; i++) {
//            Staff staffA = new Staff();
//            staffA.setsNo(12345);
//            staffA.setName("Matt Di");
//            staffA.setSalary(1.23f);
//            staffA.setOnboardDate(new Date());
//
//            Staff proxy = SmartFilter.getProxyObject(staffA, new String[]{"getSalary", "getOnboardDate"});
//
//            System.out.println(proxy.getsNo());
//            System.out.println(proxy.getName());
//            System.out.println(proxy.getSalary());
//            System.out.println(proxy.getOnboardDate());
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("cost: " + (end - start));
    }

}
