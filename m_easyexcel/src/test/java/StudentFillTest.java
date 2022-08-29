import com.alibaba.excel.EasyExcel;
import com.demo.Demo;
import com.demo.Student;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.RenHongYang
 * @description :
 * @date 2022/8/29
 */
public class StudentFillTest {
    private String filePath;

    private static List<Student> initData() {
        ArrayList<Student> students = new ArrayList<Student>();
        Student data = new Student();
        for (int i = 0; i < 10; i++) {
            data.setName("姓名" + i);
            data.setBirthday(new Date());
            data.setAge((int)(Math.random() * (100 - 18) + 18));
            data.setPhone("110");
            // 复杂表头地址的数据
            // data.setProvince("四川省");
            // data.setCity("成都市");
            // data.setArea("武侯区");
            students.add(data);
        }
        return students;
    }
    private static List<Demo> initDemo() {
        ArrayList<Demo> students = new ArrayList<Demo>();
        for (int i = 0; i < 10; i++) {
            Demo data = new Demo();
            data.setName("姓名" + i);
            data.setId(i);
            Integer random = (int)(Math.random() * 82) + 18;
            System.out.println(random);
            data.setAge(random);
            // 复杂表头地址的数据
            // data.setProvince("四川省");
            // data.setCity("成都市");
            // data.setArea("武侯区");
            students.add(data);
        }
        return students;
    }



    /**
     * 配置 文件写入地址
     *
     * @throws IOException ioexception
     */
    @Before
    public void before() throws IOException {
        File file = new File("");
        filePath = file.getCanonicalPath() + "\\src\\main\\resources\\";
    }

    /**
     * 常规表格生成
     */
    @Test
    public void t1() {
        File templateFileName  = new File(filePath, "student.xlsx");
        File fileFill  = new File(filePath, "student_fill.xlsx");
        // 整个xlsx
        EasyExcel.write(fileFill)
                // 写入到表一中
                .withTemplate(templateFileName)
                .sheet()
                // .excludeColumnFiledNames(List<String>>)：集合中的列不写入
                // .includeColumnFiledNames(List<String>>)：集合中的列写入
                .doFill(initDemo());
    }
}
