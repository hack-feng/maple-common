package com.maple.common.bean;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基础管理-邮件发送内容表
 * </p>
 *
 * @author buzy
 * @since 2019-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "MailContent对象", description = "基础管理-邮件发送内容表")
public class MailContent{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮件内容唯一编号")
    private String id;

    @ApiModelProperty(value = "邮件模板编号")
    private String tempId;

    @ApiModelProperty(value = "索引号")
    private String refId;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "邮件发送者")
    private String sendFrom;

    @ApiModelProperty(value = "邮件接收者")
    private String sendTo;

    @ApiModelProperty(value = "邮件抄送者")
    private String ccTo;

    @ApiModelProperty(value = "邮件内容")
    private String content;

    @ApiModelProperty(value = "邮件类型 0 - internal mail  1 - external mail")
    private Integer type;

    @ApiModelProperty(value = "邮件来自于哪里 0 - Others,其他 1 - Customer inquiry,客户询价单 2 - Customer quote,客户报价单 3 - Sales order,销售订单 4 - Purchase inquiry,供应商询价单 5 - Purchase quote,供应商报价单 6 - Purchase order, 采购合同")
    private String createdFrom;

    @ApiModelProperty(value = "邮件状态 0 - Not sent,未发送 1 - Sent,发送 2 - Sent failed,发送失败")
    private Integer status;

    @ApiModelProperty(value = "邮件创建者")
    private String updatedBy;

    @ApiModelProperty(value = "邮件创建/修改日期")
    private Date updatedDate;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "附件的服务器路径")
    private String attachment;

    @ApiModelProperty(value = "邮件主题")
    private String subject;
}
