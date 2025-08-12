package com.itheima.sfbx.framework.commons.dto.sms;

import com.itheima.sfbx.framework.commons.dto.basic.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description：黑名单表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SmsBlacklistVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @Builder
    public SmsBlacklistVO(Long id, String dataState, String mobile,String companyNo) {
        super(id, dataState);
        this.mobile = mobile;
        this.companyNo = companyNo;
    }

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "企业编号")
    private String companyNo;
}
