package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cmkj.common.annotation.Excel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实际费用对象 t_actual_costs
 * 
 * @author cmkj
 * @date 2024-10-04
 */
@Data
@TableName("t_actual_costs")
public class ActualCosts implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long fileId;

    /** 年份 */
    @Excel(name = "年份")
    private Integer year;

    @Excel(name = "所属公司")
    @TableField(exist = false)
    private String deptParentName;

    /** 部门 */
    private Long deptId;

    /** 科目ID */
    private Long subjectId;

    @Excel(name = "所属部门")
    @TableField(exist = false)
    private String deptName;

    /** 总账日期 */
    @Excel(name = "总账日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date zzrq;

    /** 会计科目 */
    @Excel(name = "会计科目")
    private String kjkm;

    /** 会计科目说明 */
    @Excel(name = "会计科目说明")
    private String kjkmsm;

    /** 凭证编号 */
    @Excel(name = "凭证编号")
    private String pzbh;

    /** 来源 */
    @Excel(name = "来源")
    private String ly;

    /** 日记账头说明 */
    @Excel(name = "日记账头说明")
    private String rjztsm;

    /** 摘要 */
    @Excel(name = "摘要")
    private String zy;

    /** 币种 */
    @Excel(name = "币种")
    private String bz;

    /** 本年实际发生额（元）（本位币借方） */
    @Excel(name = "本位币借方")
    private BigDecimal actualIncurred;

    /** 本位币贷方 */
    @Excel(name = "本位币贷方")
    private BigDecimal bwbdf;

    // 本位币借方 - 本位币贷方
    @TableField(exist = false)
    private BigDecimal total;

    /** 数量增加 */
    @Excel(name = "数量增加")
    private BigDecimal slzj;

    /** 数量减少 */
    @Excel(name = "数量减少")
    private BigDecimal sljs;

    /** 方向 */
    @Excel(name = "方向")
    private String fx;

    /** 全年预计发生费用（元）（余额） */
    @Excel(name = "余额")
    private BigDecimal estimatedIncurred;

    /** 数量余额 */
    @Excel(name = "数量余额")
    private BigDecimal slye;

    /** 填报目录 */
    private Long itemId;

    @TableField(exist = false)
    private String itemName;


    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}
