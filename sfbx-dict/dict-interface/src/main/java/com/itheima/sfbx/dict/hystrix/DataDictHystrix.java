package com.itheima.sfbx.dict.hystrix;

import com.itheima.sfbx.dict.feign.DataDictFeign;
import com.itheima.sfbx.framework.commons.dto.dict.DataDictVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DataDictHystrix.java
 * @Description DataDictFeign的Hystrix处理
 */
@Component
public class DataDictHystrix implements DataDictFeign {


    @Override
    public List<DataDictVO> findDataDictVOByParentKey(String parentKey) {
        return null;
    }

    @Override
    public DataDictVO findDataDictVOByDataKey(String dataKey) {
        return null;
    }

    @Override
    public List<DataDictVO> findValueByDataKeys(ArrayList<String> dataKeys) {
        return null;
    }
}
