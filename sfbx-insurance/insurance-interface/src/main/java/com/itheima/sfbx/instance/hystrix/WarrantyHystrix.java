package com.itheima.sfbx.instance.hystrix;

import com.itheima.sfbx.framework.commons.basic.ResponseResult;
import com.itheima.sfbx.framework.commons.dto.analysis.AnalysisCustomerInsuranceDTO;
import com.itheima.sfbx.framework.commons.dto.analysis.AnalysisCustomerSexDTO;
import com.itheima.sfbx.framework.commons.dto.analysis.AnalysisInsuranceTypeDTO;
import com.itheima.sfbx.instance.feign.AnalysisBusinessFeign;
import com.itheima.sfbx.instance.feign.WarrantyFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WarrantyFeign
 */
@Component
public class WarrantyHystrix implements WarrantyFeign {


    @Override
    public Boolean cleanWarranty(String warrantyNo) {
        return null;
    }

    @Override
    public Boolean periodicPay() {
        return null;
    }

    @Override
    public Boolean syncPayment(String orderNo, String tradeState) {
        return null;
    }


}
