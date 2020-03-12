package org.jeecg.modules.hrbetc.exam.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: etc_question
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Data
@TableName("etc_question")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="etc_question对象", description="etc_question")
public class EtcQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;
	/**试题题目*/
	@Excel(name = "试题题目", width = 15)
    @ApiModelProperty(value = "试题题目")
    private String qtName;
	/**题目分数*/
	@Excel(name = "题目分数", width = 15)
    @ApiModelProperty(value = "题目分数")
    private String qtScore;
	/**外键引自DIRECTION*/
	@Excel(name = "外键引自DIRECTION", width = 15, dicCode = "etc_exa_dir")
	@Dict(dicCode = "etc_exa_dir")
    @ApiModelProperty(value = "外键引自DIRECTION")
    private String dirId;
	/**试题难度,分别是:1 简单,2 中等,3 困难*/
	@Excel(name = "试题难度,分别是:1 简单,2 中等,3 困难", width = 15)
    @ApiModelProperty(value = "试题难度,分别是:1 简单,2 中等,3 困难")
    private String diff;
	/**引用自QT_TYPE*/
	@Excel(name = "引用自QT_TYPE", width = 15, dicCode = "etc_exa_type")
	@Dict(dicCode = "etc_exa_type")
    @ApiModelProperty(value = "引用自QT_TYPE")
    private String typeId;
	/**引自SUBJECT*/
	@Excel(name = "引自SUBJECT", width = 15, dicCode = "etc_exa_sub")
	@Dict(dicCode = "etc_exa_sub")
    @ApiModelProperty(value = "引自SUBJECT")
    private String subId;
	/**选项A*/
	@Excel(name = "选项A", width = 15)
    @ApiModelProperty(value = "选项A")
    private String opta;
	/**选项B*/
	@Excel(name = "选项B", width = 15)
    @ApiModelProperty(value = "选项B")
    private String optb;
	/**选项C*/
	@Excel(name = "选项C", width = 15)
    @ApiModelProperty(value = "选项C")
    private String optc;
	/**选项D*/
	@Excel(name = "选项D", width = 15)
    @ApiModelProperty(value = "选项D")
    private String optd;
	/**选项E*/
	@Excel(name = "选项E", width = 15)
    @ApiModelProperty(value = "选项E")
    private String opte;
	/**选项F*/
	@Excel(name = "选项F", width = 15)
    @ApiModelProperty(value = "选项F")
    private String optf;
	/**选项G*/
	@Excel(name = "选项G", width = 15)
    @ApiModelProperty(value = "选项G")
    private String optg;
	/**答案*/
	@Excel(name = "答案", width = 15)
    @ApiModelProperty(value = "答案")
    private String answer;
	/**标记,表示当前试题是否被选中*/
	@Excel(name = "标记,表示当前试题是否被选中", width = 15)
    @ApiModelProperty(value = "标记,表示当前试题是否被选中")
    private String mark;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
