package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.cmkj.budget.domain.*;
import net.cmkj.budget.mapper.BudgetItemDeptMapper;
import net.cmkj.budget.mapper.BudgetItemMapper;
import net.cmkj.budget.mapper.ReportingTaskDeptMapper;
import net.cmkj.budget.service.*;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.core.domain.entity.SysRole;
import net.cmkj.common.core.domain.model.LoginUser;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.system.mapper.SysDeptMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class IndexStatisticsServiceImpl implements IIndexStatisticsService {
    @Autowired
    private BudgetItemMapper budgetItemMapper;

    @Autowired
    private IDynamicSqlService dynamicSqlService;

    @Autowired
    private IReportingTaskService reportingTaskService;

    @Autowired
    private IReportingTaskDeptService reportingTaskDeptService;

    @Autowired
    private BudgetItemDeptMapper budgetItemDeptMapper;

    @Autowired
    private ReportingTaskDeptMapper taskDeptMapper;

    @Autowired
    private IBudgetWarningInfoService warningInfoService;

    @Autowired
    private IBudgetWarningService budgetWarningService;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private IBudgetReportingService reportingService;

    @Override
    public AjaxResult queryBudget(Long taskId, LoginUser loginUser) {
        Map<String,Object> data =  new HashMap<>();
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        List<SysRole> roles = loginUser.getUser().getRoles();

        // 如果填报人员的部门为公司，即level = 1 则查看该公司下的所有部门
        // 根据部门id获取部门信息
        SysDept dept = sysDeptMapper.selectDeptById(loginUser.getDeptId());

        String sqlDept = "";
        //填报人员只查看自己部门
        if(roles.size() == 1 && roles.get(0).getRoleKey().equals("businessFilling")){
            if(dept.getLevel() == 1){
                sqlDept = " and dept_id in (SELECT dept_id from sys_dept WHERE parent_id = "+ loginUser.getDeptId() +" ) ";
            }else{
                sqlDept = " and dept_id = "+loginUser.getDeptId();
            }
        }
        Long zndsp = 0L;
        Long znytg = 0L;
        Long znybh = 0L;
        Long ycdsp = 0L;
        Long ycytg = 0L;
        Long ycybh = 0L;
        List<BudgetItem> items =  budgetItemMapper.selectBudgetItemList(budgetItem);
        for (BudgetItem item : items) {
            if(item.getTableName().equals("wages")){
                continue;
            }
            String sql = "select `status`, count(*) as count from t_reporting_table_"+item.getTableName()+"  where task_id = "+taskId+" and  del_flag = 0 "+sqlDept+" GROUP BY `status`";
            System.out.println("String sql" + sql);
            List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
            if(maps.size()>0){
                for (Map<String, Object> map : maps) {
                    Integer type = Integer.parseInt(map.get("status").toString()); // 1：新增2：提交待审核 3：智能已通过 4：智能已驳回 5：业财已通过 6：业财已驳回
                    Long count = Long.parseLong(map.get("count").toString());
                    if(type==2){
                        zndsp+=count;
                    }else if(type==3){
                        znytg+=count;
                        ycdsp+=count;
                    }else if(type==4){
                        znybh+=count;
                    }else if(type==5){
                        ycytg+=count;
                        znytg+=count;
                    }else if(type==6){
                        ycybh+=count;
                        zndsp+=count;
                    }
                }
            }
        }
        data.put("已填报数量",znytg);
        data.put("职能待审批",zndsp);
        data.put("职能已通过",znytg);
        data.put("职能已驳回",znybh);
        data.put("业财待审批",ycdsp);
        data.put("业财已通过",ycytg);
        data.put("业财已驳回",ycybh);
        return AjaxResult.success(data);
    }
    public AjaxResult queryBudget_back(Long taskId, LoginUser loginUser) {
        Map<String,Object> map = new HashMap<>();
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        List<BudgetItem> dList = budgetItemMapper.selectBudgetItemList(budgetItem);
        Long ytb = 0L;
        Long zndsp = 0L;
        Long znytg = 0L;
        Long znybh = 0L;
        Long ycdsp = 0L;
        Long ycytg = 0L;
        Long ycybh = 0L;
        String roleNames = "";//获取角色名称，一个用户可能有多个角色
        List<SysRole> roles = loginUser.getUser().getRoles();
        for (SysRole role : roles) {
            roleNames += role.getRoleName() + ";";
        }
        if(roleNames.contains("超级管理员")){//超级管理员
            for (BudgetItem item : dList) {
                String sql = "select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 2  and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 3  and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 4  and  t.del_flag = '0' " +
                        " UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 5  and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 6  and  t.del_flag = '0'";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                Long count2 = (Long) maps.get(0).get("count");
                Long count3 = (Long) maps.get(1).get("count");
                Long count4 = (Long) maps.get(2).get("count");
                Long count5 = (Long) maps.get(3).get("count");
                Long count6 = (Long) maps.get(4).get("count");
                zndsp+=count2;
                znytg+=count3;
                ycdsp+=count3;
                znybh+=count4;
                ycytg+=count5;
                ycybh+=count6;
            }
        }else if(roleNames.contains("职能审核岗")){
            Long userId = loginUser.getUserId();
            Long deptId = loginUser.getDeptId();
            budgetItem.setUserId(userId);
            List<BudgetItem> uList = budgetItemMapper.selectBudgetItemList(budgetItem);
            for (BudgetItem item : uList) {
                String sql = "select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 2 and t.user_id = "+userId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 3 and t.user_id = "+userId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 4 and t.user_id = "+userId+" and  t.del_flag = '0' " +
                        " UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 5 and t.user_id = "+userId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 6 and t.user_id = "+userId+" and  t.del_flag = '0'";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                Long count2 = (Long) maps.get(0).get("count");
                Long count3 = (Long) maps.get(1).get("count");
                Long count4 = (Long) maps.get(2).get("count");
                Long count5 = (Long) maps.get(3).get("count");
                Long count6 = (Long) maps.get(4).get("count");
                zndsp+=count2;
                znytg+=count3;
                ycdsp+=count3;
                znybh+=count4;
            }
            List<BudgetItem> dListCopy = new ArrayList<>(dList); // 创建 dList 的副本
            dListCopy.removeAll(uList);
            for (BudgetItem item : dListCopy) {
                String sql = "select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 2 and t.dept_id = "+deptId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 3 and t.dept_id = "+deptId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 4 and t.dept_id = "+deptId+" and  t.del_flag = '0' " +
                        " UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 5 and t.dept_id = "+deptId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 6 and t.dept_id = "+deptId+" and  t.del_flag = '0'";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                Long count2 = (Long) maps.get(0).get("count");
                Long count3 = (Long) maps.get(1).get("count");
                Long count4 = (Long) maps.get(2).get("count");
                Long count5 = (Long) maps.get(3).get("count");
                Long count6 = (Long) maps.get(4).get("count");
                zndsp+=count2;
                znytg+=count3;
                ycdsp+=count3;
                znybh+=count4;
            }
        }else if(roleNames.contains("业财管理岗")){
            Long userId = loginUser.getUserId();
            Long deptId = loginUser.getDeptId();
            budgetItem.setUserId(userId);
            List<BudgetItem> uList = budgetItemMapper.selectBudgetItemList(budgetItem);
            for (BudgetItem item : uList) {
                String sql = "select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 2 and t.user_id = "+userId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 3 and t.user_id = "+userId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 4 and t.user_id = "+userId+" and  t.del_flag = '0' " +
                        " UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 5  and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 6 and   t.del_flag = '0'";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                Long count2 = (Long) maps.get(0).get("count");
                Long count3 = (Long) maps.get(1).get("count");
                Long count4 = (Long) maps.get(2).get("count");
                Long count5 = (Long) maps.get(3).get("count");
                Long count6 = (Long) maps.get(4).get("count");
                ycytg+=count5;
                ycybh+=count6;
            }
            List<BudgetItem> dListCopy = new ArrayList<>(dList); // 创建 dList 的副本
            dListCopy.removeAll(uList);
            for (BudgetItem item : dListCopy) {
                String sql = "select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 2 and t.dept_id = "+deptId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 3 and t.dept_id = "+deptId+" and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 4 and t.dept_id = "+deptId+" and  t.del_flag = '0' " +
                        " UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 5  and  t.del_flag = '0' UNION ALL select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+taskId+" and t.status = 6  and  t.del_flag = '0'";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                Long count2 = (Long) maps.get(0).get("count");
                Long count3 = (Long) maps.get(1).get("count");
                Long count4 = (Long) maps.get(2).get("count");
                Long count5 = (Long) maps.get(3).get("count");
                Long count6 = (Long) maps.get(4).get("count");
                ycytg+=count5;
                ycybh+=count6;
            }
        }
        ytb = zndsp + znytg + znybh;
        map.put("已填报数量",ytb);
        map.put("职能待审批",zndsp);
        map.put("职能已通过",znytg);
        map.put("职能已驳回",znybh);
        map.put("业财待审批",ycdsp);
        map.put("业财已通过",ycytg);
        map.put("业财已驳回",ycybh);
        return AjaxResult.success(map);
    }

    @Override
    public AjaxResult queryStatus(Integer year, LoginUser loginUser) {
        return AjaxResult.success();
    }

    @Override
    public AjaxResult queryMessages(LoginUser loginUser) {
        BudgetWarningInfo queryWarning = new BudgetWarningInfo();
        queryWarning.setQueryDays(7);
        if(SecurityUtils.isAdmin(loginUser.getUserId())){
            return AjaxResult.success(new ArrayList<>());
        }else{
            SysDept dept = loginUser.getUser().getDept();
            if(dept.getLevel() == 1){
                queryWarning.setParentId(dept.getDeptId());
            }else{
                queryWarning.setDeptId(dept.getDeptId());
            }
            return AjaxResult.success(warningInfoService.selectBudgetWarningInfoList(queryWarning));
        }
    }

    @Override
    public AjaxResult queryDaiban(LoginUser loginUser) {

        Map<String, Object> map = new HashMap<>();

        Long deptId = loginUser.getUser().getDeptId();
        List<ReportingTask> datas =  new ArrayList<>();
        ReportingTask queryTask = new ReportingTask();
        queryTask.setDelFlag("0");
        queryTask.setCompletionStatus("1");
        // 查询待办任务 没被删除并且状态是进行中
        List<ReportingTask> taskList = reportingTaskService.selectReportingTaskList(queryTask);
        BudgetItem queryItem = new BudgetItem();
        queryItem.setDelFlag("0");
        List<BudgetItem> itemAllList =  budgetItemMapper.selectBudgetItemList(queryItem);
        queryItem.setIds(budgetItemDeptMapper.selectItemByDepts(new Long[]{deptId}));
        List<BudgetItem> itemDeptList =  budgetItemMapper.selectBudgetItemList(queryItem);
        List<SysRole> roles = loginUser.getUser().getRoles();

        // 新增未填报预警代码片段
        ReportingTask queryTask1 = new ReportingTask();
        queryTask1.setDelFlag("0");
        // 查询 没被删除的待办任务
        List<ReportingTask> taskList1 = reportingTaskService.selectReportingTaskList(queryTask1);
//        String sqlDept = "";
//        // 填报人员只看到自己的部门
//        if(roles.size() == 1 && roles.get(0).getRoleKey().equals("businessFilling")){
//            sqlDept = " and t.dept_id = "+deptId;
//        }
//
        // 未及时审核的预警列表
        List<UnauditedWarningVO> unauditedWarningVOList = new ArrayList<>();
        // 去年填报 今年未填报预警列表
        List<BudgetWarningInfo> reportWarningList = new ArrayList<>();

        // 查询未及时审核预警的天数
        BudgetWarning budgetWarning = new BudgetWarning();
        budgetWarning.setType(5);
        budgetWarning.setStatus(1);
        List<BudgetWarning> unauditedWarningList = budgetWarningService.selectBudgetWarningList(budgetWarning);

        for (ReportingTask task : taskList1) {
            // 任务名称
            String taskName = task.getName();
            Long taskId = task.getId();

            BudgetItem budgetItemQuery = new BudgetItem();
            budgetItemQuery.setTaskId(taskId);
            budgetItemQuery.setSelectType(2);
            List<BudgetItem> budgetItems = reportingService.itemList(budgetItemQuery, SecurityUtils.getUserId());
            for (SysRole role : roles) {
                // 如果是职能审核角色
                if(role.getRoleKey().equals("functionalAudit")){
                    for (BudgetItem item : budgetItems) {
                        List<SysDept> itemDepts = budgetItemDeptMapper.selectDeptListByItemId(item.getId());
                        // 表的名称
                        String tabletName = item.getTableDisplayName();
                        // 新增代码
                        List<Map<String, Object>> maps = new ArrayList<>();
                        for (BudgetWarning warning : unauditedWarningList){
                            if (taskId.equals(warning.getTaskId())){
                                String checkSql = "SELECT dept_id as deptId FROM t_reporting_table_" + item.getTableName() +
                                        " " +
                                        "where task_id = " + taskId + " and del_flag = '0' and (status = 2 or status = 6) " +
                                        " AND DATE_ADD(update_time, INTERVAL " + warning.getPendingReviewDays() + " " +
                                        "DAY) < NOW() " +
                                        "GROUP BY " +
                                        "dept_id";
                                maps = dynamicSqlService.dynamicSelectListSql(checkSql);
                                break;
                            }
                        }
                        // 如果maps中的deptId不为空
                        for (SysDept dept : itemDepts) {
                            for (Map<String, Object> deptMap : maps) {
                                if (dept.getDeptId().equals(deptMap.get("deptId"))) {
                                    UnauditedWarningVO unauditedWarningVO = new UnauditedWarningVO();
                                    unauditedWarningVO.setTaskName(taskName);
                                    unauditedWarningVO.setCompanyName(dept.getParentName());
                                    unauditedWarningVO.setDeptName(dept.getDeptName());
                                    unauditedWarningVO.setTableDisplayName(tabletName);

                                    unauditedWarningVOList.add(unauditedWarningVO);
                                }
                            }
                        }
                    }
                }
                // 如果是填报人员
                if(role.getRoleKey().equals("businessFilling")){

                    // 填报预警
                    BudgetWarningInfo queryWarning = new BudgetWarningInfo();
                    queryWarning.setType(1);
                    if(SecurityUtils.isAdmin(loginUser.getUserId())){
                        reportWarningList = new ArrayList<>();
                    }else{
                        SysDept dept = loginUser.getUser().getDept();
                        if(dept.getLevel() == 1){
                            queryWarning.setParentId(dept.getDeptId());
                        }else{
                            queryWarning.setDeptId(dept.getDeptId());
                        }
                        reportWarningList = warningInfoService.selectBudgetWarningInfoList(queryWarning);
                    }

                    String sqlDept = "";
                    if(loginUser.getUser().getDept().getLevel() == 1){
                        sqlDept = " and dept_id in (SELECT dept_id from sys_dept WHERE parent_id = "+ deptId +" ) ";
                    }else {
                        sqlDept = " and dept_id = " + deptId;
                    }
                    BudgetItem budgetItem = new BudgetItem();
                    budgetItem.setDelFlag("0");
                    List<BudgetItem> items =  budgetItemMapper.selectBudgetItemList(budgetItem);
                    for (BudgetItem item : items) {
//                        if(item.getTableName().equals("wages")){
//                            continue;
//                        }
                        // 表名称
                        String tableName = item.getTableDisplayName();
                        List<SysDept> itemDepts = budgetItemDeptMapper.selectDeptListByItemId(item.getId());

                        // 新增代码
                        List<Map<String, Object>> maps = new ArrayList<>();
                        for (BudgetWarning warning : unauditedWarningList){
                            if (taskId.equals(warning.getTaskId())){
                                String sql = "SELECT dept_id as deptId FROM t_reporting_table_" + item.getTableName() +
                                        " " +
                                        "where task_id = " + taskId + sqlDept +" and del_flag = '0' and (status = 2 or status = " +
                                        "6 ) " +
                                        " AND DATE_ADD(update_time, INTERVAL " + warning.getPendingReviewDays() + " " +
                                        "DAY) < NOW() " +
                                        "GROUP BY " +
                                        "dept_id";
                                maps = dynamicSqlService.dynamicSelectListSql(sql);
                                break;
                            }
                        }
                        for (SysDept dept : itemDepts) {
                            for (Map<String, Object> deptMap : maps) {
                                if (dept.getDeptId().equals(deptMap.get("deptId"))) {
                                    UnauditedWarningVO unauditedWarningVO = new UnauditedWarningVO();
                                    unauditedWarningVO.setTaskName(taskName);
                                    unauditedWarningVO.setCompanyName(dept.getParentName());
                                    unauditedWarningVO.setDeptName(dept.getDeptName());
                                    unauditedWarningVO.setTableDisplayName(tableName);

                                    unauditedWarningVOList.add(unauditedWarningVO);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (ReportingTask task : taskList) {
            Long taskId = task.getId();
            for (SysRole role : roles) {
                if(role.getRoleKey().equals("businessFilling")){ //业务填报
                    ReportingTaskDept queryTaskDept =  new ReportingTaskDept();
                    queryTaskDept.setTaskId(task.getId());
                    queryTaskDept.setDeptId(deptId);
                    List<ReportingTaskDept> taskDept = taskDeptMapper.selectReportingTaskDeptList(queryTaskDept);
                    if(taskDept.size()==0){
                        for (BudgetItem item : itemDeptList) {
                            // 新增代码
                            String checkSql = "SELECT dept_id as deptId FROM t_reporting_table_" + item.getTableName() +
                                    " " +
                                    "where task_id = " + taskId + "  and del_flag = '0' and `status` > 1 and `status` != 3 " +
                                    "GROUP BY " +
                                    "dept_id";
                            List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(checkSql);
                            System.out.println("checkSql: " + checkSql);
                            System.out.println(maps);

                            String sql = "select  count(*) as count from t_reporting_table_"+item.getTableName()+"  where task_id = "+ taskId +" and del_flag = '0' and `status` > 1 and `status` != 3 ";
                            Map<String, Object> data = dynamicSqlService.dynamicSelectInfoSql(sql);
                            Long count = (Long) data.get("count");
                            if(count == 0){
                                ReportingTask newTask =  new ReportingTask();
                                BeanUtils.copyProperties(task,newTask);
                                newTask.setStatusName("待填报");
                                datas.add(newTask);
                                break;
                            }
                        }
                    }
                }else if(role.getRoleKey().equals("functionalAudit")){ //职能审核
                    for (BudgetItem item : itemAllList) {
                        // 新增代码
                        String checkSql = "SELECT dept_id as deptId FROM t_reporting_table_" + item.getTableName() +
                                " " +
                                "where task_id = " + taskId + " and del_flag = '0' and (status = 2 or status = 6) " +
                                "GROUP BY " +
                                "dept_id";
                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(checkSql);
                        System.out.println("checkSql: " + checkSql);
                        System.out.println(maps);

                        String sql = "select  count(*) as count from t_reporting_table_"+item.getTableName()+"  where task_id = "+ taskId +" and del_flag = '0' and (`status` = 2 or `status` = 6) ";
                        Map<String, Object> data = dynamicSqlService.dynamicSelectInfoSql(sql);
                        Long count = (Long) data.get("count");
                        if(count != 0){
                            ReportingTask newTask =  new ReportingTask();
                            BeanUtils.copyProperties(task,newTask);
                            newTask.setStatusName("待职能岗审核");
                            datas.add(newTask);
                            break;
                        }
                    }
                }else if(role.getRoleKey().equals("financialManagement")){ //业财管理
                    for (BudgetItem item : itemAllList) {
                        String sql = "select  count(*) as count from t_reporting_table_"+item.getTableName()+"  where task_id = "+ taskId +" and del_flag = '0' and `status` = 3 ";
                        Map<String, Object> data = dynamicSqlService.dynamicSelectInfoSql(sql);
                        Long count = (Long) data.get("count");
                        if(count != 0){
                            ReportingTask newTask =  new ReportingTask();
                            BeanUtils.copyProperties(task,newTask);
                            newTask.setStatusName("待业财岗审核");
                            datas.add(newTask);
                            break;
                        }
                    }
                }
            }
        }
        map.put("datas",datas);
        map.put("unauditedWarningVOList",unauditedWarningVOList);
        map.put("reportWarningList",reportWarningList);
        return AjaxResult.success(map);
    }
    public AjaxResult queryDaiban_back(LoginUser loginUser) {
        QueryWrapper<ReportingTask> qw = new QueryWrapper<>();
        qw.eq("del_flag","0");
        List<ReportingTask> list = reportingTaskService.list(qw);
        List<ReportingTask> list2 = new ArrayList<>();
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        List<BudgetItem> dList = budgetItemMapper.selectBudgetItemList(budgetItem);
        String roleNames = "";//获取角色名称，一个用户可能有多个角色
        List<SysRole> roles = loginUser.getUser().getRoles();
        for (SysRole role : roles) {
            roleNames += role.getRoleName() + ";";
        }
        if(roleNames.contains("超级管理员")){//填报+审核(职能、业财)
            for (ReportingTask reportingTask : list) {
                Long taskId = reportingTask.getId();
                for (BudgetItem item : dList) {
                    String sql = "select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+ taskId;
                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                    Long count = (Long) stringObjectMap.get("count");
                    if(count == 0){
                        ReportingTask reportingTask2 = new ReportingTask();
                        BeanUtils.copyProperties(reportingTask,reportingTask2);
                        reportingTask2.setStatusName("待填报");
                        list2.add(reportingTask2);
                        break;//如果有待填报的数据，退出循环
                    }
                }
                for (BudgetItem item : dList) {
                    String sql = "select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+ taskId+" and t.status = 2 ";
                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                    Long count = (Long) stringObjectMap.get("count");
                    if(count == 0){
                        ReportingTask reportingTask2 = new ReportingTask();
                        BeanUtils.copyProperties(reportingTask,reportingTask2);
                        reportingTask2.setStatusName("待职能审核");
                        list2.add(reportingTask2);
                        break;
                    }
                }
                for (BudgetItem item : dList) {
                    String sql = "select count(*) as count from t_reporting_table_"+item.getTableName()+" t where t.task_id = "+ taskId+" and  t.status = 3 ";
                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                    Long count = (Long) stringObjectMap.get("count");
                    if(count == 0){
                        ReportingTask reportingTask2 = new ReportingTask();
                        BeanUtils.copyProperties(reportingTask,reportingTask2);
                        reportingTask2.setStatusName("待业财审核");
                        list2.add(reportingTask2);
                        break;
                    }
                }

                String sql = "select count(*) as count from t_reporting_table_wages t where t.task_id = " + taskId + " and  t.del_flag = '0'";
                Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                Long count = (Long) stringObjectMap.get("count");
                if(count == 0){
                    ReportingTask reportingTask2 = new ReportingTask();
                    BeanUtils.copyProperties(reportingTask,reportingTask2);
                    reportingTask2.setStatusName("工资待填报");
                    list2.add(reportingTask2);
                }
            }
        }else if(roleNames.contains("业务填报岗")){//填报
            for (ReportingTask reportingTask : list) {
                Long taskId = reportingTask.getId();
                Long userId = loginUser.getUserId();
                Long deptId = loginUser.getDeptId();
               // budgetItem.setUserId(userId);
                String sql3 = " select t.item_id as itemId from t_budget_item_dept t where t.dept_id = "+deptId;
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                Long [] ids = new Long[maps.size()];
                int index = 0;
                for (Map<String, Object> map : maps) {
                    Long itemId = (Long) map.get("itemId");
                    ids[index] = itemId; // 将 itemId 添加到 ids 数组中
                    index++; // 增加索引
                }
                budgetItem.setIds(ids);
                List<BudgetItem> uList = budgetItemMapper.selectBudgetItemList(budgetItem);
                for (BudgetItem item : uList) {
                    String sql = "select count(*) as count from t_reporting_table_" + item.getTableName() + " t where t.task_id = " + taskId + " and  t.del_flag = '0'";
                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                    Long count = (Long) stringObjectMap.get("count");
                    if(count == 0){
                        ReportingTaskDept reportingTaskDept = new ReportingTaskDept();
                        reportingTaskDept.setDeptId(deptId);
                        reportingTaskDept.setTaskId(taskId);
                        List<ReportingTaskDept> list1 = reportingTaskDeptService.selectReportingTaskDeptList(reportingTaskDept);
                        if(list1.size()==0){
                            ReportingTask reportingTask2 = new ReportingTask();
                            BeanUtils.copyProperties(reportingTask,reportingTask2);
                            reportingTask2.setStatusName("待填报");
                            list2.add(reportingTask2);
                            break;
                        }

                    }
                }
            }
        }else if(roleNames.contains("职能审核岗")){//职能审核
            for (ReportingTask reportingTask : list) {
                Long taskId = reportingTask.getId();
                Long userId = loginUser.getUserId();
                Long deptId = loginUser.getDeptId();
                budgetItem.setUserId(userId);
                List<BudgetItem> uList = budgetItemMapper.selectBudgetItemList(budgetItem);
                for (BudgetItem item : uList) {
                    String sql = "select count(*) as count from t_reporting_table_" + item.getTableName() + " t where t.task_id = " + taskId + " and t.status = 2 and t.user_id = " + userId + " and  t.del_flag = '0'";
                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                    Long count = (Long) stringObjectMap.get("count");
                    if(count == 0){
                        ReportingTask reportingTask2 = new ReportingTask();
                        BeanUtils.copyProperties(reportingTask,reportingTask2);
                        reportingTask2.setStatusName("待职能审核");
                        list2.add(reportingTask2);
                        break;
                    }
                }
                List<BudgetItem> dListCopy = new ArrayList<>(dList); // 创建 dList 的副本
                dListCopy.removeAll(uList);
                for (BudgetItem item : dListCopy) {
                    String sql = "select count(*) as count from t_reporting_table_" + item.getTableName() + " t where t.task_id = " + taskId + " and t.status = 2 and t.dept_id = " + deptId + " and  t.del_flag = '0'";
                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                    Long count = (Long) stringObjectMap.get("count");
                    if(count == 0){
                        ReportingTask reportingTask2 = new ReportingTask();
                        BeanUtils.copyProperties(reportingTask,reportingTask2);
                        reportingTask2.setStatusName("待职能审核");
                        list2.add(reportingTask2);
                        break;
                    }
                }
            }
        }else if(roleNames.contains("业财管理岗")){//业财审核
            for (ReportingTask reportingTask : list) {
                Long taskId = reportingTask.getId();
                Long userId = loginUser.getUserId();
                Long deptId = loginUser.getDeptId();
                String sql3 = " select t.item_id as itemId from t_budget_item_dept t where t.dept_id = "+deptId;
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                Long [] ids = new Long[maps.size()];
                int index = 0;
                for (Map<String, Object> map : maps) {
                    Long itemId = (Long) map.get("itemId");
                    ids[index] = itemId; // 将 itemId 添加到 ids 数组中
                    index++; // 增加索引
                }
                budgetItem.setIds(ids);
                List<BudgetItem> uList = budgetItemMapper.selectBudgetItemList(budgetItem);
                for (BudgetItem item : uList) {
                    String sql = "select count(*) as count from t_reporting_table_" + item.getTableName() + " t where t.task_id = " + taskId + " and t.status = 3 and  t.del_flag = '0'";
                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                    Long count = (Long) stringObjectMap.get("count");
                    if(count == 0){
                        ReportingTask reportingTask2 = new ReportingTask();
                        BeanUtils.copyProperties(reportingTask,reportingTask2);
                        reportingTask2.setStatusName("待业财审核");
                        list2.add(reportingTask2);
                        break;
                    }
                }
//                budgetItem.setUserId(userId);
//                List<BudgetItem> uList = budgetItemMapper.selectBudgetItemList(budgetItem);
//                for (BudgetItem item : uList) {
//                    String sql = "select count(*) as count from t_reporting_table_" + item.getTableName() + " t where t.task_id = " + taskId + " and t.status = 3 and t.user_id = " + userId + " and  t.del_flag = '0'";
//                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
//                    Long count = (Long) stringObjectMap.get("count");
//                    if(count == 0){
//                        ReportingTask reportingTask2 = new ReportingTask();
//                        BeanUtils.copyProperties(reportingTask,reportingTask2);
//                        reportingTask2.setStatusName("待业财审核");
//                        list2.add(reportingTask2);
//                        break;
//                    }
//                }
//                List<BudgetItem> dListCopy = new ArrayList<>(dList); // 创建 dList 的副本
//                dListCopy.removeAll(uList);
//                for (BudgetItem item : dListCopy) {
//                    String sql = "select count(*) as count from t_reporting_table_" + item.getTableName() + " t where t.task_id = " + taskId + " and t.status = 3 and t.dept_id = " + deptId + " and  t.del_flag = '0'";
//                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
//                    Long count = (Long) stringObjectMap.get("count");
//                    if(count == 0){
//                        ReportingTask reportingTask2 = new ReportingTask();
//                        BeanUtils.copyProperties(reportingTask,reportingTask2);
//                        reportingTask2.setStatusName("待业财审核");
//                        list2.add(reportingTask2);
//                        break;
//                    }
//                }
            }
        }else if(roleNames.contains("公司查询岗")){//待办为空
         //此角色不做数据处理
        }else if(roleNames.contains("预算配置员")){//待办为空
         //此角色不做数据处理
        }else if(roleNames.contains("系统管理员")){//待办为空
         //此角色不做数据处理
        }else if(roleNames.contains("工资预算填报岗")){//填报
            for (ReportingTask reportingTask : list) {
                Long taskId = reportingTask.getId();
                Long userId = loginUser.getUserId();
                Long deptId = loginUser.getDeptId();

                String sql = "select count(*) as count from t_reporting_table_wages t where t.task_id = " + taskId + " and  t.del_flag = '0'";
                Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
                Long count = (Long) stringObjectMap.get("count");
                if(count == 0){
                    ReportingTask reportingTask2 = new ReportingTask();
                    BeanUtils.copyProperties(reportingTask,reportingTask2);
                    reportingTask2.setStatusName("工资待填报");
                    list2.add(reportingTask2);
                }

//                budgetItem.setUserId(userId);
//                List<BudgetItem> uList = budgetItemMapper.selectBudgetItemList(budgetItem);
//                for (BudgetItem item : uList) {
//                    String sql = "select count(*) as count from t_reporting_table_" + item.getTableName() + " t where t.task_id = " + taskId + " and t.user_id = " + userId + " and  t.del_flag = '0'";
//                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
//                    Long count = (Long) stringObjectMap.get("count");
//                    if(count == 0){
//                        ReportingTask reportingTask2 = new ReportingTask();
//                        BeanUtils.copyProperties(reportingTask,reportingTask2);
//                        reportingTask2.setStatusName("待填报");
//                        list2.add(reportingTask2);
//                        break;
//                    }
//                }
//                List<BudgetItem> dListCopy = new ArrayList<>(dList); // 创建 dList 的副本
//                dListCopy.removeAll(uList);
//                for (BudgetItem item : dListCopy) {
//                    String sql = "select count(*) as count from t_reporting_table_" + item.getTableName() + " t where t.task_id = " + taskId + " and t.dept_id = " + deptId + " and  t.del_flag = '0'";
//                    Map<String, Object> stringObjectMap = dynamicSqlService.dynamicSelectInfoSql(sql);
//                    Long count = (Long) stringObjectMap.get("count");
//                    if(count == 0){
//                        ReportingTask reportingTask2 = new ReportingTask();
//                        BeanUtils.copyProperties(reportingTask,reportingTask2);
//                        reportingTask2.setStatusName("待填报");
//                        list2.add(reportingTask2);
//                        break;
//                    }
//                }
            }
        }
        return AjaxResult.success(list2);
    }
}
