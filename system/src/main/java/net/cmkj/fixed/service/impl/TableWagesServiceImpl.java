package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.ReportingLog;
import net.cmkj.budget.domain.ReportingTask;
import net.cmkj.budget.service.IReportingLogService;
import net.cmkj.budget.service.IReportingTaskService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.fixed.domain.TableWages;
import net.cmkj.fixed.mapper.TableWagesMapper;
import net.cmkj.fixed.service.ITableWagesService;
import net.cmkj.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预算填报项目【工资福利】Service业务层处理
 * 
 * @author cmkj
 * @date 2024-08-26
 */
@Service
public class TableWagesServiceImpl extends ServiceImpl<TableWagesMapper, TableWages> implements ITableWagesService
{

    @Autowired
    private IReportingLogService reportingLogService;

    @Autowired
    private IReportingTaskService taskService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询预算填报项目【工资福利】
     * 
     * @param id 预算填报项目【工资福利】主键
     * @return 预算填报项目【工资福利】
     */
    @Override
    public TableWages selectTableWagesById(Long id)
    {
        return baseMapper.selectById(id);
    }

    /**
     * 查询预算填报项目【工资福利】列表
     * 
     * @param tableWages 预算填报项目【工资福利】
     * @return 预算填报项目【工资福利】
     */
    @Override
    public List<TableWages> selectTableWagesList(TableWages tableWages)
    {
        return baseMapper.selectTableWagesList(tableWages);
    }

    /**
     * 新增预算填报项目【工资福利】
     * 
     * @param tableWages 预算填报项目【工资福利】
     * @return 结果
     */
    @Override
    public AjaxResult insertTableWages(TableWages tableWages)
    {
        tableWages.setCreateTime(DateUtils.getNowDate());
        tableWages.setCreateBy(SecurityUtils.getUsername());
        tableWages.setDelFlag("0");
        if(baseMapper.selectTableWagesByOne(tableWages.getTaskId(),tableWages.getDeptId())!=null){
            return AjaxResult.error("该部门数据已存在，请勿重复提交");
        }
        return baseMapper.insert(tableWages)> 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 修改预算填报项目【工资福利】
     * 
     * @param tableWages 预算填报项目【工资福利】
     * @return 结果
     */
    @Override
    public int updateTableWages(TableWages tableWages)
    {
        tableWages.setUpdateTime(DateUtils.getNowDate());
        tableWages.setUpdateBy(SecurityUtils.getUsername());
        return baseMapper.updateById(tableWages);
    }

    /**
     * 批量删除预算填报项目【工资福利】
     * 
     * @param ids 需要删除的预算填报项目【工资福利】主键
     * @return 结果
     */
    @Override
    public int deleteTableWagesByIds(Long[] ids)
    {
        return baseMapper.deleteTableWagesByIds(ids);
    }

    /**
     * 删除预算填报项目【工资福利】信息
     * 
     * @param id 预算填报项目【工资福利】主键
     * @return 结果
     */
    @Override
    public int deleteTableWagesById(Long id)
    {
        return baseMapper.deleteTableWagesById(id);
    }

    @Override
    @Transactional
    public AjaxResult updateStatus(TableWages tableWages) {
        String[] ids = tableWages.getIds().split(",");
        for (String id : ids) {
            TableWages query = baseMapper.selectById(id);
            if(query!=null){
                query.setStatus(tableWages.getStatus());
                query.setUpdateTime(DateUtils.getNowDate());
                query.setUpdateBy(SecurityUtils.getUsername());
                if(baseMapper.updateTableWages(query)>0){
                    ReportingLog reportingLog = new ReportingLog();
                    reportingLog.setItemId(1L);
                    reportingLog.setBudgetId(id);
                    reportingLog.setUserId(SecurityUtils.getUserId());
                    reportingLog.setDeptId(SecurityUtils.getDeptId());
                    reportingLog.setRemark(tableWages.getRemark());
                    reportingLog.setTime(DateUtils.getNowDate());
                    if(tableWages.getStatus()==2){
                        ReportingLog queryLog = new ReportingLog();
                        queryLog.setBudgetId(id);
                        queryLog.setItemId(reportingLog.getItemId());
                        int size = reportingLogService.selectReportingLogList(queryLog).size();
                        if(size>0){
                            reportingLog.setStatus(4);
                            reportingLog.setRemark("修改了预算申请");
                        }else{
                            reportingLog.setStatus(1);
                            reportingLog.setRemark("提交了预算申请");
                        }
                    }else if(tableWages.getStatus()==3 || tableWages.getStatus()==5){
                        reportingLog.setStatus(2);
                    }else if(tableWages.getStatus()==4 || tableWages.getStatus()==6){
                        reportingLog.setStatus(3);
                    }
                    if(reportingLogService.insertReportingLog(reportingLog)<=0){
                        throw new RuntimeException();
                    }
                }else{
                    throw new RuntimeException();
                }
            }
        }
        return AjaxResult.success();
    }

    @Override
    @Transactional
    public AjaxResult backfill(TableWages tableWages) {
        ReportingTask task = taskService.selectReportingTaskById(tableWages.getTaskId());
        if (task == null || task.getLastTask() == null) {
            return AjaxResult.error("任务数据有误！");
        }
        ReportingTask lastTask = taskService.selectReportingTaskById(task.getLastTask());
        if (lastTask == null) {
            return AjaxResult.error("关联任务失败！");
        }
        Long taskId = task.getId();
        Long lastTaskId = lastTask.getId();
        TableWages wages = new TableWages();
        tableWages.setTaskId(taskId);
        List<TableWages> queryList = baseMapper.selectTableWagesList(wages);
        if(queryList.size()>0){
            return AjaxResult.warn("操作取消，已有填报数据！");
        }
        wages = new TableWages();
        tableWages.setTaskId(lastTaskId);
        List<TableWages> querylastList = baseMapper.selectTableWagesList(wages);
        for (TableWages lastWages : querylastList) {
            lastWages.setId(null);
            lastWages.setTaskId(taskId);
            lastWages.setStatus(1);
            lastWages.setCreateTime(new Date());
            lastWages.setCreateBy(SecurityUtils.getUsername());
            lastWages.setUpdateBy(null);
            lastWages.setUpdateTime(null);
        }
        this.saveBatch(querylastList);
        return AjaxResult.success();
    }


    @Override
    public List<TableWages> parentList(Integer selectType,Long taskId) {
        List<TableWages> list = new ArrayList<>();
        SysDept dept = new SysDept();
        dept.setLevel(1);
        List<SysDept> sysDepts = deptService.selectDeptListAll(dept);
        for (SysDept sysDept : sysDepts) {
            TableWages wages = baseMapper.selectTableWagesByParentDept(sysDept.getDeptId(),selectType,taskId);
            if(wages==null){
                wages = new TableWages();
                wages.setDeptId(sysDept.getDeptId());
                wages.setDeptName(sysDept.getDeptName());
            }
            list.add(wages);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> selectSummaryList(TableWages wages) {
        return baseMapper.selectSummaryList(wages);
    }

    @Override
    public List<Map<String, Object>> budgetSummary1(TableWages wages) {
        return baseMapper.budgetSummary1(wages);
    }

    @Override
    public List<Map<String, Object>>  budgetSummary2(TableWages wages) {
        return baseMapper.budgetSummary2(wages);
    }

    @Override
    public Map<String, Object> budgetSummary3(TableWages wages) {
        return baseMapper.budgetSummary3(wages);
    }

    @Override
    public List<TableWages> selectExportList(TableWages wages) {
        return baseMapper.selectExportList(wages);
    }
}
