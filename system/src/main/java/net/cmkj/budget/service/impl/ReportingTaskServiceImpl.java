package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.cmkj.budget.domain.*;
import net.cmkj.budget.mapper.ReportingTaskMapper;
import net.cmkj.budget.service.*;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.core.domain.model.LoginUser;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.StringUtils;
import net.cmkj.common.utils.uuid.IdUtils;
import net.cmkj.fixed.domain.*;
import net.cmkj.fixed.service.*;
import net.cmkj.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

/**
 * 预算任务Service业务层处理
 * 
 * @author cmkj
 */
@Service
@Slf4j
public class ReportingTaskServiceImpl extends ServiceImpl<ReportingTaskMapper, ReportingTask> implements IReportingTaskService
{

    @Autowired
    private IBudgetSubjectService budgetSubjectService;

    @Autowired
    private IBudgetItemService itemService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IDynamicSqlService dynamicSqlService;

    @Autowired
    private IWarningService warningService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private ITableModelService tableModelService;

    @Autowired
    private ITablePipelineService pipelineService;

    @Autowired
    private ITableStationService stationService;

    @Autowired
    private ITableHousingService housingService;

    @Autowired
    private ITableInformationSystemService informationSystemService;

    @Autowired
    private ITableResearchService researchService;

    @Autowired
    private ITableMeterService meterService;

    @Autowired
    private ITableCharityService charityService;

    @Autowired
    private ITableLowvalueService lowvalueService;


    /**
     * 查询预算任务
     * 
     * @param id 预算任务主键
     * @return 预算任务
     */
    @Override
    public ReportingTask selectReportingTaskById(Long id)
    {
        return baseMapper.selectReportingTaskById(id);
    }

    /**
     * 查询预算任务列表
     * 
     * @param reportingTask 预算任务
     * @return 预算任务
     */
    @Override
    public List<ReportingTask> selectReportingTaskList(ReportingTask reportingTask)
    {
        return baseMapper.selectReportingTaskList(reportingTask);
    }

    /**
     * 新增预算任务
     * 
     * @param reportingTask 预算任务
     * @return 结果
     */
    @Override
    public int insertReportingTask(ReportingTask reportingTask) {
        reportingTask.setCreateTime(DateUtils.getNowDate());
        reportingTask.setCreateBy(SecurityUtils.getUsername());
        reportingTask.setAuditStatus("1");
        //reportingTask.setCompletionStatus("1");
        //reportingTask.setEndTime(setEndTimeToLastSecondOfDay(reportingTask.getEndTime()));
        LocalDate currentDate = LocalDate.now();
        reportingTask.setYear(currentDate.getYear());
        return baseMapper.insert(reportingTask);
    }

    @Override
    public AjaxResult relaunch(ReportingTask task, LoginUser loginUser) {
        ReportingTask lastTask = baseMapper.selectReportingTaskById(task.getLastTask());
        if (lastTask == null) {
            return AjaxResult.error("关联任务失败！");
        }
        SysUser sysUser = loginUser.getUser();
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        List<BudgetItem> items = itemService.selectBudgetItemList(budgetItem);
        return transactionTemplate.execute(new TransactionCallback<AjaxResult>() {
            @Override
            public AjaxResult doInTransaction(TransactionStatus status) {
                try {
                    task.setCreateTime(DateUtils.getNowDate());
                    task.setCreateBy(SecurityUtils.getUsername());
                    task.setAuditStatus("1");
                    LocalDate currentDate = LocalDate.now();
                    task.setYear(currentDate.getYear());
                    baseMapper.insert(task);

                    for (BudgetItem item : items) {
                        String tableName = item.getTableName();
                        if (StringUtil.isNotEmpty(tableName)) {
                            if(item.getReportingType().equals("1")){
                                StringBuilder sql = new StringBuilder();
                                sql.append("select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t.actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t.review_reason, t.create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag ");
                                List<TableModel> fieldList = tableModelService.selectTableModelListByItem(item.getId());
                                for (TableModel field : fieldList) {
                                    sql.append(", ").append(" e.").append(field.getFieldName());
                                }
                                sql.append(" from t_reporting_table_").append(tableName).append(" t  LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID ").append(" where t.task_id = ").append(lastTask.getId()).append(" and t.del_flag = '0'    and t.`status` = 5  ");
                                List<Map<String, Object>> dates;
                                try {
                                    dates = dynamicSqlService.dynamicSelectListSqlException(sql.toString());
                                } catch (Exception e) {
                                    throw new RuntimeException("动态查询失败: " + e.getMessage(), e);
                                }
                                if (dates != null && dates.size() > 0) {
                                    for (Map<String, Object> map : dates) {
                                        String uuid = IdUtils.simpleUUID();
                                        StringBuilder tableSql = new StringBuilder();
                                        tableSql.append("insert into t_reporting_table_").append(tableName).append(" ( id, task_id, `year`, dept_id, user_id, budget, budget_year, actual_incurred, estimated_incurred, cost_description, `status`, review_budget, review_reason, create_by, create_time, remark, del_flag )  values ( '").append(uuid).append("', ")
                                                .append(task.getId()).append(", ").append(task.getBudgetYear()).append(", ").append(map.get("dept_id") == null ? null : map.get("dept_id")).append(", ").append(sysUser.getUserId()).append(", ").append(map.get("budget")==null ? null : map.get("budget")).append(", ")
                                                .append(map.get("budget_year") == null ? null : map.get("budget_year")).append(", ").append(map.get("actual_incurred") ==null ? null : map.get("actual_incurred")).append(", ").append(map.get("estimated_incurred")==null ? null : map.get("estimated_incurred").toString()).append(", ")
                                                .append(map.get("cost_description") == null ? null : "'" + map.get("cost_description") + "'").append(", ").append(5).append(", ").append(map.get("review_budget") == null ? null : map.get("review_budget")).append(", ").append(map.get("review_reason") == null ? null : "'" + map.get("review_reason") + "'").append(", '").
                                                append(sysUser.getUserName()).append("', sysdate() , ").append(map.get("remark") ==null ? null : "'" + map.get("remark") + "'").append(", '0' )");
                                        if (dynamicSqlService.dynamicInsertSql(tableSql.toString()) < 0) {
                                            throw new RuntimeException();
                                        }
                                        StringBuilder extensionSql = new StringBuilder();
                                        extensionSql.append("insert into t_reporting_table_").append(tableName).append("_extension ( id ");
                                        for (TableModel field : fieldList) {
                                            extensionSql.append(", ").append(field.getFieldName());
                                        }
                                        extensionSql.append(" ) values ( '").append(uuid).append("'");
                                        for (TableModel field : fieldList) {
                                            Object date = map.get(field.getFieldName());
                                            extensionSql.append(", ").append( date == null ? null : "'" + date + "'");
                                        }
                                        extensionSql.append(" ) ");
                                        if (dynamicSqlService.dynamicInsertSql(extensionSql.toString()) <= 0) {
                                            throw new RuntimeException();
                                        }
                                    }
                                }
                            }else if(item.getReportingType().equals("2")){
                                if("pipeline".equals(tableName)){
                                    TablePipeline pipeline = new TablePipeline();
                                    pipeline.setTaskId(lastTask.getId());
                                    List<TablePipeline> dates = pipelineService.selectTablePipelineList(pipeline);
                                    for (TablePipeline date : dates) {
                                        date.setId(null);
                                        date.setTaskId(task.getId());
                                        date.setStatus(5);
                                        date.setCreateTime(new Date());
                                        date.setCreateBy(sysUser.getUserName());
                                        date.setUpdateTime(null);
                                        date.setUpdateBy(null);
                                    }
                                    pipelineService.saveBatch(dates);
                                }else if("station".equals(tableName)){ //场站工程
                                    TableStation station = new TableStation();
                                    station.setTaskId(lastTask.getId());
                                    List<TableStation> dates = stationService.selectTableStationList(station);
                                    for (TableStation date : dates) {
                                        date.setId(null);
                                        date.setTaskId(task.getId());
                                        date.setStatus(5);
                                        date.setCreateTime(new Date());
                                        date.setCreateBy(sysUser.getUserName());
                                        date.setUpdateTime(null);
                                        date.setUpdateBy(null);
                                    }
                                    stationService.saveBatch(dates);
                                }else if("housing".equals(tableName)){ //房屋建设、装修
                                    TableHousing housing = new TableHousing();
                                    housing.setTaskId(lastTask.getId());
                                    List<TableHousing> dates = housingService.selectTableHousingList(housing);
                                    for (TableHousing date : dates) {
                                        date.setId(null);
                                        date.setTaskId(task.getId());
                                        date.setStatus(5);
                                        date.setCreateTime(new Date());
                                        date.setCreateBy(sysUser.getUserName());
                                        date.setUpdateTime(null);
                                        date.setUpdateBy(null);
                                    }
                                    housingService.saveBatch(dates);
                                }else if("information_system".equals(tableName)){ //信息系统建设
                                    TableInformationSystem informationSystem = new TableInformationSystem();
                                    informationSystem.setTaskId(lastTask.getId());
                                    List<TableInformationSystem> dates = informationSystemService.selectTableInformationSystemList(informationSystem);
                                    for (TableInformationSystem date : dates) {
                                        date.setId(null);
                                        date.setTaskId(task.getId());
                                        date.setStatus(5);
                                        date.setCreateTime(new Date());
                                        date.setCreateBy(sysUser.getUserName());
                                        date.setUpdateTime(null);
                                        date.setUpdateBy(null);
                                    }
                                    informationSystemService.saveBatch(dates);
                                }else if("research".equals(tableName)){ //科研计划
                                    TableResearch research = new TableResearch();
                                    research.setTaskId(lastTask.getId());
                                    List<TableResearch> dates = researchService.selectTableResearchList(research);
                                    for (TableResearch date : dates) {
                                        date.setId(null);
                                        date.setTaskId(task.getId());
                                        date.setStatus(5);
                                        date.setCreateTime(new Date());
                                        date.setCreateBy(sysUser.getUserName());
                                        date.setUpdateTime(null);
                                        date.setUpdateBy(null);
                                    }
                                    researchService.saveBatch(dates);
                                }else if("meter".equals(tableName)){ //计量器
                                    TableMeter meter = new TableMeter();
                                    meter.setTaskId(lastTask.getId());
                                    List<TableMeter> dates = meterService.selectTableMeterList(meter);
                                    for (TableMeter date : dates) {
                                        date.setId(null);
                                        date.setTaskId(task.getId());
                                        date.setStatus(5);
                                        date.setCreateTime(new Date());
                                        date.setCreateBy(sysUser.getUserName());
                                        date.setUpdateTime(null);
                                        date.setUpdateBy(null);
                                    }
                                    meterService.saveBatch(dates);
                                }else if("charity".equals(tableName)){ //慈善公益
                                    TableCharity charity = new TableCharity();
                                    charity.setTaskId(lastTask.getId());
                                    List<TableCharity> dates = charityService.selectTableCharityList(charity);
                                    for (TableCharity date : dates) {
                                        date.setId(null);
                                        date.setTaskId(task.getId());
                                        date.setStatus(5);
                                        date.setCreateTime(new Date());
                                        date.setCreateBy(sysUser.getUserName());
                                        date.setUpdateTime(null);
                                        date.setUpdateBy(null);
                                    }
                                    charityService.saveBatch(dates);
                                }else if("lowvalue".equals(tableName)){ //低值易耗
                                    TableLowvalue lowvalue = new TableLowvalue();
                                    lowvalue.setTaskId(lastTask.getId());
                                    List<TableLowvalue> dates = lowvalueService.selectTableLowvalueList(lowvalue);
                                    for (TableLowvalue date : dates) {
                                        date.setId(null);
                                        date.setTaskId(task.getId());
                                        date.setStatus(5);
                                        date.setCreateTime(new Date());
                                        date.setCreateBy(sysUser.getUserName());
                                        date.setUpdateTime(null);
                                        date.setUpdateBy(null);
                                    }
                                    lowvalueService.saveBatch(dates);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("预算填报数据恢复异常: {}", e.getMessage(), e);
                    return AjaxResult.error("预算填报数据恢复异常！");
                }
                return AjaxResult.success();
            }
        });
    }
    /**
     * 修改预算任务
     * 
     * @param reportingTask 预算任务
     * @return 结果
     */
    @Override
    public int updateReportingTask(ReportingTask reportingTask)
    {
        reportingTask.setUpdateTime(DateUtils.getNowDate());
        reportingTask.setUpdateBy(SecurityUtils.getUsername());
        //reportingTask.setEndTime(setEndTimeToLastSecondOfDay(reportingTask.getEndTime()));
        return baseMapper.updateById(reportingTask);
    }

    public Date setEndTimeToLastSecondOfDay(Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime); // 设置Calendar的时间为endTime
        // 设置时分秒为23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0); // 清除毫秒
        return calendar.getTime(); // 更新endTime
    }
    /**
     * 批量删除预算任务
     * 
     * @param ids 需要删除的预算任务主键
     * @return 结果
     */
    @Override
    public int deleteReportingTaskByIds(Long[] ids)
    {
        return baseMapper.deleteReportingTaskByIds(ids,SecurityUtils.getUsername());
    }

    @Override
    public int updateTips(Long taskId) {
        return baseMapper.updateTips(taskId,SecurityUtils.getUserId());
    }

    @Override
    public Map<String,Object> selectTips(Long taskId) {
        Map<String,Object> map = new HashMap<>();
        map.put("isTips",baseMapper.selectTips(taskId, SecurityUtils.getUserId()));
        ReportingTask task = baseMapper.selectById(taskId);
        map.put("reportingExplain",task==null?"":task.getReportingExplain());
        return map;
    }

    @Override
    public List<ReportingTask> listAll(ReportingTask reportingTask) {
        return baseMapper.selectReportingTaskList(reportingTask);
    }

    @Override
    public int updateExpireTask() {
        return baseMapper.updateExpireTask();
    }

    //预警（实际费用不超过预算费用，每月固定日期执行一次）
    @Override
    public void earlyWarning() {
        BudgetSubject budgetSubject = new BudgetSubject();
        budgetSubject.setDelFlag("0");
        List<BudgetSubject> budgetSubjects = budgetSubjectService.selectBudgetSubjectList(budgetSubject);
        for (BudgetSubject subject : budgetSubjects) {
            Long subjectId = subject.getId();
            BudgetItem budgetItem = new BudgetItem();
            budgetItem.setReportingType("1");//动态表
            budgetItem.setDelFlag("0");
            budgetItem.setSubjectId(subjectId);
            List<BudgetItem> dItems = itemService.selectBudgetItemList(budgetItem);
            budgetItem.setReportingType("2");//固定表
            budgetItem.setDelFlag("0");
            budgetItem.setSubjectId(subjectId);
            List<BudgetItem> gItems = itemService.selectBudgetItemList(budgetItem);
            SysDept dept = new SysDept();
            dept.setLevel(2);
            dept.setDelFlag("0");
            dept.setStatus("0");
            List<SysDept> depts = deptService.selectDeptListAll(dept);
            ReportingTask reportingTask = new ReportingTask();
            reportingTask.setDelFlag("0");
            List<ReportingTask> reportingTasks = baseMapper.selectReportingTaskList(reportingTask);
            for (ReportingTask task : reportingTasks) {
                Integer year = task.getYear();
                Long taskId = task.getId();
                String taskName = task.getName();
                for (SysDept sysDept : depts) {
                    Long deptId = sysDept.getDeptId();
                    BigDecimal ysMoney = BigDecimal.ZERO;//预算费用
                    for (BudgetItem dItem : dItems) {//动态表
                       String sql = "SELECT IFNULL(SUM( t.budget ),0) AS budgetTotal FROM t_reporting_table_"+dItem.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                        ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                    }
                    for (BudgetItem item : gItems) {//固定表
                        if(item.getTableName().equals("pipeline")){
                            String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值

                        }else if (item.getTableName().equals("station")){
                            String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId ;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }else if (item.getTableName().equals("research")){
                            String sql = "SELECT IFNULL( SUM(COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0)), 0 ) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId ;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }else if (item.getTableName().equals("meter")){
                            String sql = "SELECT IFNULL(SUM(budget),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }else if (item.getTableName().equals("charity")){
                            String sql = "SELECT IFNULL(SUM(budget),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }
                        else if (item.getTableName().equals("lowvalue")){
                            String sql = "SELECT IFNULL(ROUND(SUM(budget),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }
                        else if (item.getTableName().equals("information_system")){
                            String sql = "SELECT IFNULL( SUM( COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0) ), 0 ) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }else if (item.getTableName().equals("housing")){
                            String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }
                    }

                    if(dItems.size()!=0 || gItems.size() != 0) {
                        BigDecimal sjMoney = BigDecimal.ZERO;//实际费用
                        String sql2 =" SELECT IFNULL(SUM(t.actual_incurred),0) as actualTotal from t_actual_costs t where t.subject_id = "+subjectId+" and t.dept_id = "+ deptId+" and t.year = " + year;
                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql2);
                        sjMoney = new BigDecimal(info.get("actualTotal").toString());
                        if(ysMoney.compareTo(new BigDecimal("0")) > 0) {
                            if (sjMoney.compareTo(ysMoney) <= 0) {//实际费用小于等于预算费用
                                SysDept sysDept1 = deptService.selectDeptById(sysDept.getParentId());//区域公司
                                String content = "";
                                if(sjMoney.compareTo(BigDecimal.ZERO) == 0){
                                    content = taskName +"："+ sysDept1.getDeptName() + "："+ sysDept.getDeptName() + "："+ subject.getName() + "的实际费用是" + sjMoney + "元，预算费用是" + ysMoney + "元，未发生实际费用。";
                                }else {
                                    if(!(ysMoney.compareTo(BigDecimal.ZERO) == 0)) {
                                        // 计算百分比
                                        BigDecimal percentage = sjMoney.divide(ysMoney, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                                        if(percentage.compareTo(new BigDecimal("80")) <= 0 ){
                                            content = taskName + "：" + sysDept1.getDeptName() + "：" + sysDept.getDeptName() + "：" + subject.getName() + "的实际费用是" + sjMoney + "元，预算费用是" + ysMoney + "元，实际费用达到预算费用的"+percentage+"%。";
                                        }
                                    }
                                }
                                if(StringUtils.isNoneBlank(content)) {
                                    Warning warning = new Warning();
                                    warning.setCreatedTime(new Date());
                                    warning.setDeptId(deptId);
                                    warning.setDeptName(sysDept.getDeptName());
                                    warning.setActualMoney(sjMoney);
                                    warning.setBudgetMoney(ysMoney);
                                    warning.setTaskId(taskId);
                                    warning.setTaskName(taskName);
                                    warning.setMessage(content);
                                    warning.setMessageType(1);//1代表费用预警
                                    warning.setYear(year);
                                    warning.setMoneyType(1);//未超出
                                    warning.setSubjectId(subjectId);
                                    warning.setSubjectName(subject.getName());
                                    warningService.insertWarning(warning);
                                }
                            }
                        }
                    }
                  }
            }
        }
    }

    //预警（实际费用超过预算费用，每天执行一次）
    @Override
    public void exceedWarning() {
        BudgetSubject budgetSubject = new BudgetSubject();
        budgetSubject.setDelFlag("0");
        List<BudgetSubject> budgetSubjects = budgetSubjectService.selectBudgetSubjectList(budgetSubject);
        for (BudgetSubject subject : budgetSubjects) {
            Long subjectId = subject.getId();
            BudgetItem budgetItem = new BudgetItem();
            budgetItem.setReportingType("1");//动态表
            budgetItem.setDelFlag("0");
            budgetItem.setSubjectId(subjectId);
            List<BudgetItem> dItems = itemService.selectBudgetItemList(budgetItem);
            budgetItem.setReportingType("2");//固定表
            budgetItem.setDelFlag("0");
            budgetItem.setSubjectId(subjectId);
            List<BudgetItem> gItems = itemService.selectBudgetItemList(budgetItem);
            SysDept dept = new SysDept();
            dept.setLevel(2);
            dept.setDelFlag("0");
            dept.setStatus("0");
            List<SysDept> depts = deptService.selectDeptListAll(dept);
            ReportingTask reportingTask = new ReportingTask();
            reportingTask.setDelFlag("0");
            List<ReportingTask> reportingTasks = baseMapper.selectReportingTaskList(reportingTask);
            for (ReportingTask task : reportingTasks) {
                Integer year = task.getYear();
                Long taskId = task.getId();
                String taskName = task.getName();
                for (SysDept sysDept : depts) {
                    Long deptId = sysDept.getDeptId();
                    BigDecimal ysMoney = BigDecimal.ZERO;//预算费用
                    for (BudgetItem dItem : dItems) {//动态表
                        String sql = "SELECT IFNULL(SUM( t.budget ),0) AS budgetTotal FROM t_reporting_table_"+dItem.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                        ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                    }
                    for (BudgetItem item : gItems) {//固定表
                        if(item.getTableName().equals("pipeline")){
                            String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值

                        }else if (item.getTableName().equals("station")){
                            String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId ;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }else if (item.getTableName().equals("research")){
                            String sql = "SELECT IFNULL( SUM(COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0)), 0 ) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId ;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }else if (item.getTableName().equals("meter")){
                            String sql = "SELECT IFNULL(SUM(budget),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }else if (item.getTableName().equals("charity")){
                            String sql = "SELECT IFNULL(SUM(budget),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }
                        else if (item.getTableName().equals("lowvalue")){
                            String sql = "SELECT IFNULL(ROUND(SUM(budget),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }
                        else if (item.getTableName().equals("information_system")){
                            String sql = "SELECT IFNULL( SUM( COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0) ), 0 ) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }else if (item.getTableName().equals("housing")){
                            String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM t_reporting_table_"+item.getTableName()+" t  where t.task_id = "+taskId+" and t.dept_id  = "+deptId;
                            Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                            BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                            ysMoney = ysMoney.add(budgetTotal); // 更新 ysMoney 的值
                        }
                    }

                    if(dItems.size()!=0 || gItems.size() != 0) {
                        BigDecimal sjMoney = BigDecimal.ZERO;//实际费用
                        String sql2 =" SELECT IFNULL(SUM(t.actual_incurred),0) as actualTotal from t_actual_costs t where t.subject_id = "+subjectId+" and t.dept_id = "+ deptId+" and t.year = " + year;
                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql2);
                        sjMoney = new BigDecimal(info.get("actualTotal").toString());
                        if(ysMoney.compareTo(new BigDecimal("0")) > 0) {
                            if (sjMoney.compareTo(ysMoney) > 0) {//实际费用大于预算费用
                                SysDept sysDept1 = deptService.selectDeptById(sysDept.getParentId());//区域公司
                                String content = "";
                                if(sjMoney.compareTo(BigDecimal.ZERO) == 0){
                                    content = taskName +"："+ sysDept1.getDeptName() + "："+ sysDept.getDeptName() + "："+ subject.getName() + "的实际费用是" + sjMoney + "元，预算费用是" + ysMoney + "元，未发生实际费用。";
                                }else {
                                    if(!(ysMoney.compareTo(BigDecimal.ZERO) == 0)) {
                                        // 计算百分比
                                        BigDecimal percentage = sjMoney.divide(ysMoney, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                                        if(percentage.compareTo(new BigDecimal("80")) > 0 ){
                                            content = taskName + "：" + sysDept1.getDeptName() + "：" + sysDept.getDeptName() + "：" + subject.getName() + "的实际费用是" + sjMoney + "元，预算费用是" + ysMoney + "元，实际费用已达到预算费用的"+percentage+"%。";
                                        }
                                    }
                                }
                                if(StringUtils.isNoneBlank(content)) {
                                    Warning warning = new Warning();
                                    warning.setCreatedTime(new Date());
                                    warning.setDeptId(deptId);
                                    warning.setDeptName(sysDept.getDeptName());
                                    warning.setActualMoney(sjMoney);
                                    warning.setBudgetMoney(ysMoney);
                                    warning.setTaskId(taskId);
                                    warning.setTaskName(taskName);
                                    warning.setMessage(content);
                                    warning.setMessageType(1);//1代表费用预警
                                    warning.setYear(year);
                                    warning.setMoneyType(2);//已超出
                                    warning.setSubjectId(subjectId);
                                    warning.setSubjectName(subject.getName());
                                    warningService.insertWarning(warning);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ReportingTask getLast() {
        return baseMapper.getLast();
    }

}
