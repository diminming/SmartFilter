package generateJson;

import cc.smartform.smartfilter.SmartFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by Matt Di on 2017/2/24.
 */
public class GenerateJSON {


    @Test
    public void generateJson() throws IOException, CannotCompileException, InstantiationException, NotFoundException, IllegalAccessException, ClassNotFoundException {

        String json = "{\"id\":1234567,\"name\":\"Development\",\"staffList\":[{\"sNo\":23456,\"name\":\"Matt Di\",\"salary\":1.23,\"onboardDate\":\"2017-2-24 17:25\"},{\"sNo\":23456,\"name\":\"Matt Di\",\"salary\":1.23,\"onboardDate\":\"2017-2-23 17:25\"},{\"sNo\":23456,\"name\":\"Matt Di\",\"salary\":1.23,\"onboardDate\":\"2017-2-22 17:25\"}]}";

        ObjectMapper mapper = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));

        Department department = mapper.readValue(json, Department.class);

        System.out.println(mapper.writeValueAsString(new SmartFilter().getProxyObject(department, new HashMap<String, String[]>(){{
            put(Department.class.getName(), new String[]{"getId"});
            put(Staff.class.getName(), new String[]{"getSalary"});
        }})));


    }

}
