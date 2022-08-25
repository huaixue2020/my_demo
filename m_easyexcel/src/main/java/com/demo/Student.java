package com.demo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Mr.RenHongYang
 * @description :
 * @date 2022/8/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(30)
public class Student {
    /**
     * 注解说明：
     * 
     * @ContentRowHeight(20)：标识在类上面，表示内容行高为20
     * @HeadRowHeight(10)：标识在类上面，表示表头行高为10
     *
     * @ExcelIgnore：写出的表格不包含该注解标识的字段
     * @ColumnWidth(10)：设置列宽度为10
     * @ExcelProperty(value = "性别", index = 2)：该列名由sex->性别；排表格第 3 列。默认排列顺序为类中字段顺序，被跳过的列为空白列
     * @DateTimeFormat("yyyy-MM-dd")，日期格式化，参照
     * @NumberFormat("#.##%")，小数格式化；保留2位小数，并转化为 %
     */

    /**
     * 名字
     */
    @ExcelProperty("姓名")
    private String name;
    /**
     * 年龄
     */
    @ExcelProperty("年龄")
    private Integer age;
    /**
     * 生日
     */
    @ExcelProperty("生日")
    @ColumnWidth(50)
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date birthday;

    /**
     * 复杂表头：|     地址     |
     *          | 省 | 市 | 区 |
     */
    @ExcelProperty({"地址", "省"})
    private String province;
    @ExcelProperty({"地址", "市"})
    private String city;
    @ExcelProperty({"地址", "区"})
    private String area;

    /**
     * 电话
     */
    @ExcelIgnore
    private String phone;
}
