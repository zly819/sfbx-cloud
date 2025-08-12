package com.itheima.sfbx.file.hystrix;

import com.itheima.sfbx.file.feign.FileDownLoadFeign;
import com.itheima.sfbx.framework.commons.dto.file.FileVO;
import org.springframework.stereotype.Component;

/**
 * @ClassName FileHystrix.java
 * @Description FileFeign的Hystrix
 */
@Component
public class FileDownLoadHystrix implements FileDownLoadFeign {


    @Override
    public FileVO downLoad(Long fileId) {
        return null;
    }
}
