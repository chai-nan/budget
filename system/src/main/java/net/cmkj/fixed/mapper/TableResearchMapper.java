package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableResearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算填报项目【科研计划申报】Mapper接口
 *
 * @author cmkj
 * @date 2024-08-28
 */
public interface TableResearchMapper extends BaseMapper<TableResearch>
{
    /**
     * 查询预算填报项目【科研计划申报】
     *
     * @param id 预算填报项目【科研计划申报】主键
     * @return 预算填报项目【科研计划申报】
     */
    public TableResearch selectTableResearchById(Long id);

    /**
     * 查询预算填报项目【科研计划申报】列表
     *
     * @param tableResearch 预算填报项目【科研计划申报】
     * @return 预算填报项目【科研计划申报】集合
     */
    public List<TableResearch> selectTableResearchList(TableResearch tableResearch);

    public List<TableResearch> selectTableResearchCountList(TableResearch tableResearch);

    /**
     * 新增预算填报项目【科研计划申报】
     *
     * @param tableResearch 预算填报项目【科研计划申报】
     * @return 结果
     */
    public int insertTableResearch(TableResearch tableResearch);

    /**
     * 修改预算填报项目【科研计划申报】
     *
     * @param tableResearch 预算填报项目【科研计划申报】
     * @return 结果
     */
    public int updateTableResearch(TableResearch tableResearch);

    /**
     * 删除预算填报项目【科研计划申报】
     *
     * @param id 预算填报项目【科研计划申报】主键
     * @return 结果
     */
    public int deleteTableResearchById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报项目【科研计划申报】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableResearchByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    public List<TableResearch> budgetSummaryList(TableResearch research);

    List<TableResearch> budgetSummaryList2(TableResearch research);

    List<TableResearch> budgetSummaryList3(TableResearch research);
}
