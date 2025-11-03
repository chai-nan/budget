package net.cmkj.budget.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.budget.domain.ReportingTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算任务Mapper接口
 *
 * @author cmkj
 * @date 2024-07-16
 */
public interface ReportingTaskMapper extends BaseMapper<ReportingTask>
{
    /**
     * 查询预算任务
     *
     * @param id 预算任务主键
     * @return 预算任务
     */
    public ReportingTask selectReportingTaskById(Long id);

    /**
     * 查询预算任务列表
     *
     * @param reportingTask 预算任务
     * @return 预算任务集合
     */
    public List<ReportingTask> selectReportingTaskList(ReportingTask reportingTask);

    /**
     * 新增预算任务
     *
     * @param reportingTask 预算任务
     * @return 结果
     */
    public int insertReportingTask(ReportingTask reportingTask);

    /**
     * 修改预算任务
     *
     * @param reportingTask 预算任务
     * @return 结果
     */
    public int updateReportingTask(ReportingTask reportingTask);

    /**
     * 批量删除预算任务
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReportingTaskByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    int updateTips(@Param("taskId")Long taskId, @Param("userId")Long userId);

    int selectTips(@Param("taskId")Long taskId, @Param("userId")Long userId);

    int updateExpireTask();

    ReportingTask getLast();
}
