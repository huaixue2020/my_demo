package com.demo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
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
public class StudentRead {

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
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date birthday;

    /**
     * 复杂表头：| 地址 | | 省 | 市 | 区 |
     */
    // @ExcelProperty({"地址", "省"})
    // private String province;
    // @ExcelProperty({"地址", "市"})
    // private String city;
    // @ExcelProperty({"地址", "区"})
    // private String area;

    /**
     * 电话
     */
    // @ExcelIgnore
    // private String phone;
}
