package com.maple.program.controller;

import com.alibaba.fastjson.JSONObject;
import com.maple.common.excel.ExportExcelBean;
import com.maple.common.bean.R;
import com.maple.common.excel.ExportExcelTheme;
import com.maple.common.excel.ExportExcelUtil;
import com.maple.program.utils.ExportExcelTitle;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangFZ
 * @date 2020/5/7 13:52
 **/
@RestController
@Slf4j
public class ExampleController {

    @GetMapping("/exportSimpleExcel")
    @ApiOperation(value = "导出简单的excel")
    public R exportSimpleExcel(HttpServletRequest request, HttpServletResponse response){

        List<Map<String, Object>> list = new ArrayList<>();
        int num = 10;
        for (int i = 0; i < num; i++) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("code", "数据名称" + i);
            map.put("name", "数据编码" + i);
            map.put("brand", "数据品牌" + i);
            list.add(map);
        }

        try {
            String exportFileName = "简单的excel ";
            ExportExcelUtil.updateNameUnicode(request, response, exportFileName);
            OutputStream out = response.getOutputStream();
            //保存导出的excel的名称
            ExportExcelBean table = ExportExcelTitle.table2.getValue();
            table.setDataList(list);
            log.info("导出的数据JSON格式：{}", JSONObject.toJSONString(table));
            ExportExcelUtil.exportExcel(table, out);

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/exportExcelMoreSheetMoreTable")
    @ApiOperation(value = "导出多sheet，单sheet拥有多表格的excel")
    public R exportExcelMoreSheetMoreTable(HttpServletRequest request, HttpServletResponse response){

        List<ExportExcelBean> excelBean = new ArrayList<>();

        // 模拟数据
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("name", "名称" + i);
            map.put("name1", "名称" + i);
            map.put("code", "2020051400" + i);
            list.add(map);
        }
        List<Map<String, Object>> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("name", "数据名称" + i);
            map.put("code", "数据名称" + 0);
            map.put("brand", "数据品牌" + i);
            list2.add(map);
        }
        try {
            String exportFileName = "测试数据";
            ExportExcelUtil.updateNameUnicode(request, response, exportFileName);
            OutputStream out = response.getOutputStream();

            // 表格1
            ExportExcelBean table1 = ExportExcelTitle.table1.getValue();
            table1.setDataList(list);

            // 表格2
            ExportExcelBean table2 = ExportExcelTitle.table2.getValue();
            table2.setDataList(list2);

            // 表格3
            ExportExcelBean table3 = ExportExcelTitle.table2.getValue();
            table3.setTheme(ExportExcelTheme.GREEN);
            table3.setDataList(list2);

            // 表格4
            ExportExcelBean table4 = ExportExcelTitle.table2.getValue();
            table4.setTheme(ExportExcelTheme.DEFAULT);
            table4.setDataList(list2);

            // sheet1
            ExportExcelBean sheet1 = new ExportExcelBean();

            // sheet1 - 多表格
            List<ExportExcelBean> tables1 = new ArrayList<>();
            tables1.add(table1);
            tables1.add(table2);
            tables1.add(table3);
            tables1.add(table4);

            sheet1.setSheetName("测试sheet");
            sheet1.setList(tables1);
            // 设置sheet的密码，仅作用于当前sheet页
            sheet1.setProtectSheet("123456");
            excelBean.add(sheet1);

            // sheet2
            ExportExcelBean sheet2 = new ExportExcelBean();

            // sheet2 - 多表格
            List<ExportExcelBean> tables2 = new ArrayList<>();
            tables2.add(table2);
            tables2.add(table1);

            sheet2.setSheetName("测试sheet2");
            sheet2.setList(tables2);
            excelBean.add(sheet2);

            log.info("导出的数据JSON格式：{}", JSONObject.toJSONString(excelBean));
            ExportExcelUtil.exportExcelMoreSheetMoreTable(excelBean, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
