package com.itheima.sfbx.instance.hystrix;

import com.itheima.sfbx.framework.commons.dto.report.CategoryReportVO;
import com.itheima.sfbx.instance.feign.CategoryFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description：保险分类响应接口
 */
public class CategoryHystrix implements CategoryFeign {


    @Override
    public List<CategoryReportVO> categoryList(CategoryReportVO categoryVO) {
        return null;
    }
}
