package com.itheima.sfbx.insurance.pojo;

import com.itheima.sfbx.framework.mybatisplus.basic.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description：评估类目
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tab_health_assessment")
@ApiModel(value="HealthAssessment对象", description="评估类目")
public class HealthAssessment extends BasePojo {

    private static final long serialVersionUID = 1L;

    @Builder
    public HealthAssessment(Long id,String dataState,Long insuranceId,String assessmentKey,String assessmentKeyName,String assessmentVal,Integer sortNo,String remake){
        super(id, dataState);
        this.insuranceId=insuranceId;
        this.assessmentKey=assessmentKey;
        this.assessmentKeyName=assessmentKeyName;
        this.assessmentVal=assessmentVal;
        this.sortNo=sortNo;
        this.remake=remake;
    }

    @ApiModelProperty(value = "保险产品id")
    private Long insuranceId;

    @ApiModelProperty(value = "评估类目key")
    private String assessmentKey;

    @ApiModelProperty(value = "评估类目名称")
    private String assessmentKeyName;

    @ApiModelProperty(value = "评估类目值")
    private String assessmentVal;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "备注")
    private String remake;


}
