package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TablePipeline;

import java.util.List;

/**
 * 预算填报项目【天然气管线】Service接口
 * 
 * @author cmkj
 * @date 2024-09-03
 */
public interface ITablePipelineService extends IService<TablePipeline>
{
    /**
     * 查询预算填报项目【天然气管线】
     * 
     * @param id 预算填报项目【天然气管线】主键
     * @return 预算填报项目【天然气管线】
     */
    public TablePipeline selectTablePipelineById(Long id);

    /**
     * 查询预算填报项目【天然气管线】列表
     * 
     * @param tablePipeline 预算填报项目【天然气管线】
     * @return 预算填报项目【天然气管线】集合
     */
    public List<TablePipeline> selectTablePipelineList(TablePipeline tablePipeline);

    public List<TablePipeline> selectTablePipelineCountList(TablePipeline tablePipeline);

    /**
     * 新增预算填报项目【天然气管线】
     * 
     * @param tablePipeline 预算填报项目【天然气管线】
     * @return 结果
     */
    public int insertTablePipeline(TablePipeline tablePipeline);

    /**
     * 修改预算填报项目【天然气管线】
     * 
     * @param tablePipeline 预算填报项目【天然气管线】
     * @return 结果
     */
    public int updateTablePipeline(TablePipeline tablePipeline);

    /**
     * 批量删除预算填报项目【天然气管线】
     * 
     * @param ids 需要删除的预算填报项目【天然气管线】主键集合
     * @return 结果
     */
    public int deleteTablePipelineByIds(Long[] ids);

    /**
     * 删除预算填报项目【天然气管线】信息
     * 
     * @param id 预算填报项目【天然气管线】主键
     * @return 结果
     */
    public int deleteTablePipelineById(Long id);

    public AjaxResult updateStatus(FixedVo vo);

    public List<TablePipeline> budgetSummaryList(TablePipeline pipeline);
}
