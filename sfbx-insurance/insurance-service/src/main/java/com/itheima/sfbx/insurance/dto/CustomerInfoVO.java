package com.itheima.sfbx.insurance.dto;

import com.itheima.sfbx.framework.commons.dto.basic.BaseVO;
import com.itheima.sfbx.framework.commons.dto.file.FileVO;
import com.itheima.sfbx.insurance.validator.anno.IdentityCard;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
/**
 * @Description：客户信息表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="CustomerInfo对象", description="客户信息表")
public class CustomerInfoVO extends BaseVO {

    @Builder
    public CustomerInfoVO(String identityCard,Long id,String dataState,List<FileVO> fileVOs,String customerId,String remark,String cityName,BigDecimal income,BigDecimal liabilities,String realNameAuthentication,String bindingCard,String name){
        super(id, dataState);
        this.customerId=customerId;
        this.remark=remark;
        this.cityName=cityName;
        this.income=income;
        this.liabilities=liabilities;
        this.realNameAuthentication=realNameAuthentication;
        this.bindingCard=bindingCard;
        this.fileVOs=fileVOs;
        this.identityCard = identityCard;
        this.name = name;
    }

    @ApiModelProperty(value = "系统账号编号")
    private String customerId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "常驻城市")
    private String cityName;

    @ApiModelProperty(value = "收入")
    private BigDecimal income;

    @ApiModelProperty(value = "负债")
    private BigDecimal liabilities;

    @ApiModelProperty(value = "实名认证（0是 1否）")
    private String realNameAuthentication;

    @ApiModelProperty(value = "绑卡（0是 1否）")
    private String bindingCard;

    @ApiModelProperty(value = "文件VO对象")
    private List<FileVO> fileVOs;

    @ApiModelProperty(value = "批量操作：主键ID")
    private String[] checkedIds;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    @IdentityCard(message = "身份证号格式错误")
    private String identityCard;

}
