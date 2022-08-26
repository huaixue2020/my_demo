import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.demo.Demo;
import com.demo.Student;
import com.demo.StudentRead;
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
public class StudentReadTest {
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
     * 读取表格
     */
    @Test
    public void t1() {
        File file = new File(filePath, "student.xlsx");
        EasyExcel.read(file, StudentRead.class, new AnalysisEventListener<StudentRead>() {

            @Override
            public void invoke(StudentRead studentRead, AnalysisContext analysisContext) {
                System.out.println(studentRead.toString());
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("完了");
            }
        }).sheet("表一").doRead();
    }

    @Test
    public void t2() {
        File file = new File(filePath, "student.xlsx");
        // 单个表
        EasyExcel.read(file, StudentRead.class, new AnalysisEventListener<StudentRead>() {

            @Override
            public void invoke(StudentRead studentRead, AnalysisContext analysisContext) {
                System.out.println(studentRead.toString());
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("完了");
            }
        }).doReadAll();

        // 多个表，且字段格式不同
        try (ExcelReader excelReader = EasyExcel.read(file).build()) {
            ReadSheet one = EasyExcel.readSheet(0).head(StudentRead.class)
                .registerReadListener(new AnalysisEventListener<StudentRead>() {
                    @Override
                    public void invoke(StudentRead studentRead, AnalysisContext analysisContext) {
                        System.out.println(studentRead.toString());
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("完了");
                    }
                }).build();
            ReadSheet two =
                EasyExcel.readSheet(1).head(Demo.class).registerReadListener(new AnalysisEventListener<Demo>() {
                    @Override
                    public void invoke(Demo studentRead, AnalysisContext analysisContext) {
                        System.out.println(studentRead.toString());
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("完了");
                    }
                }).build();
            excelReader.read(one, two);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
