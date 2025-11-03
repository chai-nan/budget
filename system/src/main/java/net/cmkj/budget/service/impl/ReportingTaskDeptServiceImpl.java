package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.budget.mapper.ReportingTaskDeptMapper;
import net.cmkj.budget.domain.ReportingTaskDept;
import net.cmkj.budget.service.IReportingTaskDeptService;

import java.util.List;
/**
 * 部门任务填报情况Service业务层处理
 *
 * @author cmkj
 * @date 2024-11-05
 */
@Service
public class ReportingTaskDeptServiceImpl extends ServiceImpl<ReportingTaskDeptMapper, ReportingTaskDept> implements IReportingTaskDeptService
{

    /**
     * 查询部门任务填报情况
     *
     * @param id 部门任务填报情况主键
     * @return 部门任务填报情况
     */
    @Override
    public ReportingTaskDept selectReportingTaskDeptById(Long id)
    {
        return baseMapper.selectReportingTaskDeptById(id);
    }

    /**
     * 查询部门任务填报情况列表
     *
     * @param reportingTaskDept 部门任务填报情况
     * @return 部门任务填报情况
     */
    @Override
    public List<ReportingTaskDept> selectReportingTaskDeptList(ReportingTaskDept reportingTaskDept)
    {
        return baseMapper.selectReportingTaskDeptList(reportingTaskDept);
    }

    /**
     * 新增部门任务填报情况
     *
     * @param reportingTaskDept 部门任务填报情况
     * @return 结果
     */
    @Override
    public int insertReportingTaskDept(ReportingTaskDept reportingTaskDept)
    {
        reportingTaskDept.setCreateBy(SecurityUtils.getUsername());
        reportingTaskDept.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertReportingTaskDept(reportingTaskDept);
    }

    /**
     * 修改部门任务填报情况
     *
     * @param reportingTaskDept 部门任务填报情况
     * @return 结果
     */
    @Override
    public int updateReportingTaskDept(ReportingTaskDept reportingTaskDept)
    {
        return baseMapper.updateReportingTaskDept(reportingTaskDept);
    }

    /**
     * 批量删除部门任务填报情况
     * 
     * @param ids 需要删除的部门任务填报情况主键
     * @return 结果
     */
    @Override
    public int deleteReportingTaskDeptByIds(Long[] ids)
    {
        return baseMapper.deleteReportingTaskDeptByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除部门任务填报情况信息
     * 
     * @param id 部门任务填报情况主键
     * @return 结果
     */
    @Override
    public int deleteReportingTaskDeptById(Long id)
    {
        return baseMapper.deleteReportingTaskDeptById(id, SecurityUtils.getUsername());
    }
}
