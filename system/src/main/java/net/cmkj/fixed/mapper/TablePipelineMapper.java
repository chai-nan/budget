package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.TablePipeline;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算填报项目【天然气管线】Mapper接口
 *
 * @author cmkj
 * @date 2024-09-03
 */
public interface TablePipelineMapper extends BaseMapper<TablePipeline>
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
     * 删除预算填报项目【天然气管线】
     *
     * @param id 预算填报项目【天然气管线】主键
     * @return 结果
     */
    public int deleteTablePipelineById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报项目【天然气管线】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTablePipelineByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    List<TablePipeline> budgetSummaryList(TablePipeline pipeline);

    List<TablePipeline> budgetSummaryList2(TablePipeline pipeline);

    List<TablePipeline> budgetSummaryList3(TablePipeline pipeline);
}
