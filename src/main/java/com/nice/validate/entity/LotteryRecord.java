package com.nice.validate.entity;

import com.nice.validate.annotations.NotEmpty;
import com.nice.validate.constant.ValidatorConditionType;
import com.nice.validate.validator.BaseAbstractParameter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 抽奖次数记录
 * </p>
 *
 * @author yinxicheng
 * @since 2018-01-26
 */

@Getter
@Setter
@ToString
@ApiModel("抽奖记录-ModelAndView控制")
public class LotteryRecord extends BaseAbstractParameter {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @NotEmpty(when = {ValidatorConditionType.READ,ValidatorConditionType.UPDATE,ValidatorConditionType.DELETE})
	private Long id;
    /**
     * 用户的openId
     */
    @ApiModelProperty("用户的openId")
    private String openId;
    /**
     * 获奖人手机号
     */
    private String mobile;
    /**
     * 初始化次数
     */
    @ApiModelProperty("活动id")
	private Integer initNum;
    /**
     * 增加抽奖次数(抽奖次数)
     */
    @ApiModelProperty("增加抽奖次数(抽奖次数)")
    private Integer addNum;
    /**
     * 总数抽奖次数 每用一次 减一
     */
    @ApiModelProperty("总数抽奖次数")
    private Integer amount;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
	private Long createdBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
	private Long createdTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
	private Long updatedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
	private Long updatedTime;
    /**
     * 删除标记(1:在线2:删除)
     */
    @ApiModelProperty("删除标记(1:在线2:删除)")
	private Integer deleted;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
	private String remark;
}
