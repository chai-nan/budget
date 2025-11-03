package net.cmkj.budget.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.ReportingTask;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.model.LoginUser;

import java.util.List;
import java.util.Map;

/**
 * 预算任务Service接口
 * 
 * @author cmkj
 * @date 2024-07-16
 */
public interface IReportingTaskService extends IService<ReportingTask>
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
     * @param ids 需要删除的预算任务主键集合
     * @return 结果
     */
    public int deleteReportingTaskByIds(Long[] ids);

    int updateTips(Long taskId);

    Map<String,Object> selectTips(Long taskId);

    List<ReportingTask> listAll(ReportingTask reportingTask);

    public int  updateExpireTask();

    ReportingTask getLast();

    void earlyWarning();

    void exceedWarning();

    AjaxResult relaunch(ReportingTask reportingTask, LoginUser loginUser);
}
