import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
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
 * @date 2022/8/25
 */
public class StudentTest {
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
            data.setProvince("四川省");
            data.setCity("成都市");
            data.setArea("武侯区");
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
        File file = new File(filePath, "student.xlsx");
        // 整个xlsx
        EasyExcel.write(file, Student.class)
            // 写入到表一中
            .sheet("表一")
            // .excludeColumnFiledNames(List<String>>)：集合中的列不写入
            // .includeColumnFiledNames(List<String>>)：集合中的列写入
            .doWrite(initData());
    }

    /**
     * 多次/重复插入表格
     */
    @Test
    public void t2() {
        // 文件生成地址
        File file = new File(filePath, "students.xlsx");
        // 生成xlsx文件对象
        try (ExcelWriter build = EasyExcel.write(file, Student.class).build()) {
            // 1. 插入同一sheet中
            // WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            for (int i = 0; i < 5; i++) {
                // 1. 不同sheet；每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                List<Student> data = initData();
                build.write(data, writeSheet);
            }
        }

        // String fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        /*        try (ExcelWriter excelWriter = EasyExcel.write(file, Student.class).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
            for (int i = 0; i < 5; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoData> data = data();
                excelWriter.write(data, writeSheet);
            }
        }*/
    }
}
