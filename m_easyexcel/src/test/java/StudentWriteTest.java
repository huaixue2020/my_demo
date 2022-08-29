import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
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
 * @date 2022/8/25
 */
public class StudentWriteTest {
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
//            data.setProvince("四川省");
//            data.setCity("成都市");
//            data.setArea("武侯区");
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
        // 工作簿对象
        try (ExcelWriter build = EasyExcel.write(file, Student.class).build()) {
            // 1. 其中的表格
            // WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            for (int i = 0; i < 5; i++) {
                // 1. 不同表格；每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                List<Student> data = initData();
                build.write(data, writeSheet);
            }
        }
    }

    /**
     * 合并单元格
     */
    @Test
    public void t3() {
        // 文件生成地址
        File file = new File(filePath, "student-merge.xlsx");
        // 工作簿对象
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        EasyExcel.write(file, Student.class).registerWriteHandler(loopMergeStrategy).sheet("模板").doWrite(initData());
    }

    @Test
    public void t4(){
        // 文件生成地址
        File file = new File(filePath, "student.xlsx");
        List<Demo> demos = new ArrayList<>();
        demos.add(new Demo(1,"{name}",19));
        EasyExcel.write(file, Demo.class).sheet().doWrite(demos);
    }
}
