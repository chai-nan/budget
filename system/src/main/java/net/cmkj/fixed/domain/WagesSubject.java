package net.cmkj.fixed.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Map;
import net.cmkj.common.annotation.Excel;
import lombok.Data;

import java.io.Serial;

/**
 * 工资字段类型对象 t_wages_subject
 * 
 * @author cmkj
 * @date 2024-09-23
 */
@Data
@TableName("t_wages_subject")
public class WagesSubject implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 字段名 */
    @Excel(name = "字段名")
    private String name;

    /** 字段 */
    @Excel(name = "字段")
    private String field;

    /** 费用科目 */
    private Long type;

    /** 费用科目 */
    @Excel(name = "费用科目")
    private String typeName;

    @TableField(exist = false)
    private Integer subjectType;

    @TableField(exist = false)
    private Long subjectId;


}
