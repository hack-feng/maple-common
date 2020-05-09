package com.maple.program.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 日志信息-系统日志表
 * </p>
 *
 * @author ZhangFZ
 * @since 2020-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ma_system_log")
@ApiModel(value="SystemLog对象", description="日志信息-系统日志表")
public class SystemLog extends Model<SystemLog> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "服务名称")
    private String appName;

    @ApiModelProperty(value = "日志参数")
    private String params;

    @ApiModelProperty(value = "返回结果")
    private String results;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "请求ip")
    private String requestIp;

    @ApiModelProperty(value = "请求路径")
    private String allMethodName;

    @ApiModelProperty(value = "请求方法名称")
    private String methodName;

    @ApiModelProperty(value = "操作方式")
    private String operationType;

    @ApiModelProperty(value = "是否成功（SUCCESS：是 FALSE：否）")
    private String success;

    @ApiModelProperty(value = "响应时间")
    private String respTime;

    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    @ApiModelProperty(value = "操作人id")
    private String operateId;

    @ApiModelProperty(value = "操作人姓名")
    private String operateName;

    @ApiModelProperty(value = "日志类型")
    private String logType;

    @ApiModelProperty(value = "日志描述")
    private String logDesc;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
