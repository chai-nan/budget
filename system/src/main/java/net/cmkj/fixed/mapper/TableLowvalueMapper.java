package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableLowvalue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算填报项目【低值易耗品】Mapper接口
 *
 * @author cmkj
 * @date 2024-08-28
 */
public interface TableLowvalueMapper extends BaseMapper<TableLowvalue>
{
    /**
     * 查询预算填报项目【低值易耗品】
     *
     * @param id 预算填报项目【低值易耗品】主键
     * @return 预算填报项目【低值易耗品】
     */
    public TableLowvalue selectTableLowvalueById(Long id);

    /**
     * 查询预算填报项目【低值易耗品】列表
     *
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 预算填报项目【低值易耗品】集合
     */
    public List<TableLowvalue> selectTableLowvalueList(TableLowvalue tableLowvalue);

    public List<TableLowvalue> selectTableLowvalueCountList(TableLowvalue tableLowvalue);

    /**
     * 新增预算填报项目【低值易耗品】
     *
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 结果
     */
    public int insertTableLowvalue(TableLowvalue tableLowvalue);

    /**
     * 修改预算填报项目【低值易耗品】
     *
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 结果
     */
    public int updateTableLowvalue(TableLowvalue tableLowvalue);

    /**
     * 删除预算填报项目【低值易耗品】
     *
     * @param id 预算填报项目【低值易耗品】主键
     * @return 结果
     */
    public int deleteTableLowvalueById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报项目【低值易耗品】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableLowvalueByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    public List<TableLowvalue> budgetSummaryList(TableLowvalue lowvalue);

    List<TableLowvalue> budgetSummaryList2(TableLowvalue lowvalue);

    List<TableLowvalue> budgetSummaryList3(TableLowvalue lowvalue);
}
