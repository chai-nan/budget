package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cmkj.common.annotation.Excel;
import net.cmkj.common.core.domain.entity.SysDictData;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 填报模块对象 t_table_model
 * 
 * @author cmkj
 * @date 2024-07-15
 */
@Data
@TableName("t_table_model")
public class TableModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 填报项目ID */
    @Excel(name = "填报项目ID")
    private Long itemId;

    /** 库表名称 */
    @Excel(name = "库表名称")
    private String tableName;

    /** 字段名称（规则生成） */
    @Excel(name = "字段名称", readConverterExp = "规=则生成")
    private String fieldName;

    /** 字段显示名称 */
    @Excel(name = "字段显示名称")
    private String fieldDisplayName;

    /** 字段类型（1：系统字段 2：扩展字段） */
    private Integer type;

    /** 字段屬性（1:字符 2：整数 3：小数） */
    @Excel(name = "字段屬性", readConverterExp = "1=:字符,2=：整数,3=：小数 4=：字典")
    private String fieldType;

    /** 字段长度 */
    @Excel(name = "字段长度")
    private Integer fieldLength;

    /** 小数长度 */
    @Excel(name = "小数长度")
    private Integer fieldDecimalLength;

    /** 字典ID*/
    private Long dictId;

    /** 是否必填（1：是 2：否） */
    @Excel(name = "是否必填", readConverterExp = "0=：是,1=：否")
    private String fieldRequired;

    /** 是否显示（1：是 2：否） */
    @Excel(name = "是否显示", readConverterExp = "0=：是,1=：否")
    private String fieldDisplay;

    /** 是否查询（1：是 2：否） */
    @Excel(name = "是否查询", readConverterExp = "0=：是,1=：否")
    private String fieldQuery;

    /** 排序 */
    private Integer orderNum;

    /** 删除标志（0代表正常 2代表删除） */
    private String delFlag;

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

    @TableField(exist = false)
    private String value;

    @TableField(exist = false)
    private String sourceTable;

    @TableField(exist = false)
    private String dictType;

    @TableField(exist = false)
    private List<SysDictData> dictDatas;
}
