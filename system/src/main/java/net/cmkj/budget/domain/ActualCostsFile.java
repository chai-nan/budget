package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cmkj.common.annotation.Excel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 实际费用文件对象 t_actual_costs_file
 * 
 * @author cmkj
 * @date 2025-01-21
 */
@Data
@TableName("t_actual_costs_file")
public class ActualCostsFile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**  */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 源文件 */
    @Excel(name = "源文件")
    private String sourceName;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 明细数量 */
    @Excel(name = "明细数量")
    private Integer infoNum;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}
