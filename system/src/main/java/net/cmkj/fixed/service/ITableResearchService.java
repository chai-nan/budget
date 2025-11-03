package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableResearch;

import java.util.List;

/**
 * 预算填报项目【科研计划申报】Service接口
 * 
 * @author cmkj
 * @date 2024-08-27
 */
public interface ITableResearchService extends IService<TableResearch>
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
     * 批量删除预算填报项目【科研计划申报】
     * 
     * @param ids 需要删除的预算填报项目【科研计划申报】主键集合
     * @return 结果
     */
    public int deleteTableResearchByIds(Long[] ids);

    /**
     * 删除预算填报项目【科研计划申报】信息
     * 
     * @param id 预算填报项目【科研计划申报】主键
     * @return 结果
     */
    public int deleteTableResearchById(Long id);

    AjaxResult updateStatus(FixedVo vo);

    public List<TableResearch> budgetSummaryList(TableResearch research);
}
