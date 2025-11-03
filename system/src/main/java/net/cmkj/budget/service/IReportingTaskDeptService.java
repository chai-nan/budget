package net.cmkj.budget.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.ReportingTaskDept;

/**
 * 部门任务填报情况Service接口
 * 
 * @author cmkj
 * @date 2024-11-05
 */
public interface IReportingTaskDeptService extends IService<ReportingTaskDept>
{
    /**
     * 查询部门任务填报情况
     * 
     * @param id 部门任务填报情况主键
     * @return 部门任务填报情况
     */
    public ReportingTaskDept selectReportingTaskDeptById(Long id);

    /**
     * 查询部门任务填报情况列表
     * 
     * @param reportingTaskDept 部门任务填报情况
     * @return 部门任务填报情况集合
     */
    public List<ReportingTaskDept> selectReportingTaskDeptList(ReportingTaskDept reportingTaskDept);

    /**
     * 新增部门任务填报情况
     * 
     * @param reportingTaskDept 部门任务填报情况
     * @return 结果
     */
    public int insertReportingTaskDept(ReportingTaskDept reportingTaskDept);

    /**
     * 修改部门任务填报情况
     * 
     * @param reportingTaskDept 部门任务填报情况
     * @return 结果
     */
    public int updateReportingTaskDept(ReportingTaskDept reportingTaskDept);

    /**
     * 批量删除部门任务填报情况
     * 
     * @param ids 需要删除的部门任务填报情况主键集合
     * @return 结果
     */
    public int deleteReportingTaskDeptByIds(Long[] ids);

    /**
     * 删除部门任务填报情况信息
     * 
     * @param id 部门任务填报情况主键
     * @return 结果
     */
    public int deleteReportingTaskDeptById(Long id);
}
