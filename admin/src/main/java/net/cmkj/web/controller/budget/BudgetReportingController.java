package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.*;
import net.cmkj.budget.mapper.BudgetItemDeptMapper;
import net.cmkj.budget.service.*;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.ExcelUtilNew;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.bean.BeanUtils;
import net.cmkj.common.utils.uuid.ExportSheetUtil;
import net.cmkj.fixed.domain.*;
import net.cmkj.fixed.service.*;
import net.cmkj.system.mapper.SysDeptMapper;
import net.cmkj.system.mapper.SysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预算填报Controller
 * 
 * @author cmkj
 */
@RestController
@RequestMapping("/budget/reporting")
public class BudgetReportingController extends BaseController {

    @Autowired
    private IBudgetReportingService reportingService;

    @Autowired
    private IBudgetItemService budgetItemService;


    @Autowired
    private IReportingTaskService taskService;

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

    @Autowired
    private IBudgetItemService itemService;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private BudgetItemDeptMapper budgetItemDeptMapper;

    @Autowired
    private ITableModelService tableModelService;

    @Autowired
    private IDynamicSqlService dynamicSqlService;

    @Autowired
    private IBudgetWarningService budgetWarningService;


    /**
     * 左侧填报项目列表
     */
    @GetMapping("/itemList")
    public AjaxResult itemList(BudgetItem budgetItem) {
        return AjaxResult.success(reportingService.itemList(budgetItem,getUserId()));
    }

    /**
     * 填报项目统计信息
     */
    @GetMapping("/itemInfo")
    public AjaxResult itemInfo(Budget budget) {
        return AjaxResult.success(reportingService.itemInfo(budget));
    }

    /**
     * 填报项目部门信息统计
     * @param budget
     * @return
     */
    @GetMapping("/itemSubmissionDept")
    public AjaxResult itemSubmissionDept(Budget budget) {
        return AjaxResult.success(reportingService.itemSubmissionDept(budget));
    }

    /**
     * 填报表头字段列表
     */
    @GetMapping("/itemFieldList")
    public AjaxResult itemFieldList(Long itemId, Long taskId) {
        return AjaxResult.success(reportingService.itemFieldList(itemId,taskId));
    }

    /**
     * 填报列部门信息
     */
    @GetMapping("/itemFieldDetp")
    public AjaxResult itemFieldDept(Budget query) {
        return AjaxResult.success(reportingService.itemFieldDept(query,getUserId()));
    }

    /**
     * 填报编辑页字段列表
     */
    @GetMapping("/itemEditField")
    public AjaxResult itemEditField(Budget query) {
        return AjaxResult.success(reportingService.itemEditField(query));
    }

    /**
     * 根据部门获取预计和实际费用
     * @param query
     * @return
     */
    @GetMapping("/itemCostByDept")
    public AjaxResult itemCostByDept(Budget query) {
        return AjaxResult.success(reportingService.itemCostByDept(query));
    }


    /**
     * 填报审核页字段列表
     */
    @GetMapping("/itemAuditField")
    public AjaxResult itemAuditField(Budget query) {
        return AjaxResult.success(reportingService.itemAuditField(query));
    }

    /**
     * 审核汇总列表
     * @param query
     * @return
     */
    @GetMapping("/budgetSummaryList")
    public TableDataInfo budgetSummaryList(Budget query) {
        return reportingService.budgetSummaryList(query);
    }

    /**
     * 填报项目数据列表
     */
    @GetMapping("/budgetList")
    public TableDataInfo budgetList(Budget query) {
        return getDataTable(reportingService.budgetList(query,getUserId()));
    }

    @GetMapping("/budgetAddList")
    public AjaxResult budgetAddList(Budget query) {
        return AjaxResult.success(reportingService.budgetAddList(query,getUserId()));
    }

    @Log(title = "填报数据", businessType = BusinessType.EXPORT)
    @SaCheckPermission("budget:business:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Budget query){
        reportingService.export(response, query,getUserId());
    }

    @GetMapping("/getNotReportedList")
    public AjaxResult getNotReportedList(Budget budget) {
        return AjaxResult.success(budgetWarningService.getNotReportedList(budget));
    }

    /**
     * 添加固定字段
     */
    public void addFixedField(List<TableModel> fieldList,boolean flag) {
        TableModel field =  new TableModel();
        field.setFieldName("dept_name");
        field.setFieldDisplayName("填报部门");
        field.setFieldType("2");
        field.setFieldDisplay("1");
        field.setFieldRequired("0");
        field.setFieldQuery("1");
        field.setSourceTable("other");
        fieldList.add(field);
        if(flag){
            field = new TableModel();
            field.setFieldName("budget");
            field.setFieldDisplayName("费用预算（元）");
            field.setFieldType("3");
            field.setFieldLength(10);
            field.setFieldDisplay("0");
            field.setFieldRequired("0");
            field.setFieldQuery("1");
            field.setSourceTable("main");
            fieldList.add(field);

            field = new TableModel();
            field.setFieldName("budget_year");
            field.setFieldDisplayName("本年预算（元）");
            field.setFieldType("3");
            field.setFieldLength(10);
            field.setFieldDisplay("0");
            field.setFieldRequired("1");
            field.setFieldQuery("1");
            field.setSourceTable("main");
            fieldList.add(field);

            field = new TableModel();
            field.setFieldName("actual_incurred");
            field.setFieldDisplayName("本年实际发生额（元）");
            field.setFieldType("3");
            field.setFieldLength(10);
            field.setFieldDisplay("1");
            field.setFieldRequired("1");
            field.setFieldQuery("1");
            field.setSourceTable("main");
            fieldList.add(field);

            field = new TableModel();
            field.setFieldName("estimated_incurred");
            field.setFieldDisplayName("全年预计发生费用（元）");
            field.setFieldType("3");
            field.setFieldLength(10);
            field.setFieldDisplay("1");
            field.setFieldRequired("0");
            field.setFieldQuery("1");
            field.setSourceTable("main");
            fieldList.add(field);
        }

        field = new TableModel();
        field.setFieldName("status_name");
        field.setFieldDisplayName("填报状态");
        field.setFieldType("2");
        field.setFieldDisplay("1");
        field.setFieldRequired("0");
        field.setFieldQuery("1");
        field.setSourceTable("other");
        fieldList.add(field);
    }

    @Log(title = "所有表导出", businessType = BusinessType.EXPORT)
    @PostMapping("/exportTotal")
    public void exportTotal(HttpServletResponse response,  FixedVo vo) throws IOException {
        List<Map<String, Object>> lmap = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook();
        ReportingTask task =taskService.selectReportingTaskById(vo.getTaskId());
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setReportingType("1");//动态表
        budgetItem.setDelFlag("0");
        List<BudgetItem> dList = budgetItemService.selectBudgetItemList(budgetItem);
        for (int i = 0; i < dList.size(); i++) {
            Map<String, Object> result = new HashMap<>();
            BudgetItem item = dList.get(i);
            Budget query = new Budget();
            query.setItemId(item.getId());
            query.setTaskId(vo.getTaskId());
            List<Map<String, Object>> titles = new ArrayList<>();
            List<TableModel> tableModels = new ArrayList<>();
            addFixedField(tableModels,false);
            TableModel queryModel = new TableModel();
            queryModel.setItemId(item.getId());
            List<TableModel> queryModels  = tableModelService.selectTableModelList(queryModel);
            queryModels.removeIf(model -> model.getFieldName().equals("status") && model.getType()==1);
            tableModels.addAll(queryModels);
            if(task!=null && task.getYear()!=null){
                changeYearName(tableModels,task.getYear());
            }
            List<Map<String, Object>> datas = budgetExportList(query, getUserId());
            for (TableModel tableModel : tableModels) {
                Map<String, Object> map2 = new HashMap<>();
                String fieldName = tableModel.getFieldName();
                String fieldDisplayName = "";
                if(fieldName.equals("budget")){
                    fieldDisplayName = (task.getYear()+1)+"年预算";
                }else if(fieldName.equals("budget_year")){
                    fieldDisplayName = task.getYear()+"年预算";
                }else if(fieldName.equals("actual_incurred")){
                    fieldDisplayName = task.getYear()+"年实际发生额";
                }else if(fieldName.equals("estimated_incurred")){
                    fieldDisplayName = task.getYear()+"年预计发生费用";
                }else {
                    fieldDisplayName = tableModel.getFieldDisplayName();
                }
                if(tableModel.getFieldType().equals("4")&& tableModel.getSourceTable().equals("extension")){
                    map2.put("name", fieldDisplayName);
                    map2.put("key", tableModel.getFieldName()+"Name");
                }else{
                    map2.put("name", fieldDisplayName);
                    map2.put("key", tableModel.getFieldName());
                }
                titles.add(map2);
            }

            result.put("datas",datas);
            result.put("titles",titles);
            result.put("sheetName",item.getTableDisplayName());
            lmap.add(result);
        }
        ExportSheetUtil.exportSingleHeaderExcel(workbook,lmap);

        budgetItem.setReportingType("2");//固定表
        List<BudgetItem> gList = budgetItemService.selectBudgetItemList(budgetItem);
        for (BudgetItem item : gList) {
            vo.setItemId(item.getId());
            List<SysDept> depts = itemFieldDept2(vo.getItemId(), getUserId());
            List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
            vo.setDeptIds(deptIds);
            //vo.setSelectType(3);
            if("pipeline".equals(item.getTableName())){ //天然气管线
                TablePipeline pipeline = new TablePipeline();
                BeanUtils.copyBeanProp(pipeline, vo);
                pipeline.setDeptIds(deptIds);
                List<TablePipeline> list = pipelineService.selectTablePipelineList(pipeline);
                ExcelUtilNew<TablePipeline> util = new ExcelUtilNew<TablePipeline>(TablePipeline.class);
                List<TablePipeline> dats = new ArrayList<>();
                /*
                List<TablePipeline> parents = pipelineService.budgetSummaryList(pipeline);
                for(TablePipeline parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TablePipeline child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                /*
                //按表合计
                TablePipeline total = new TablePipeline();
                BigDecimal expectedSettlement = new BigDecimal(0);
                for (TablePipeline tablePipeline : list) {
                    dats.add(tablePipeline);
                    expectedSettlement = expectedSettlement.add(tablePipeline.getExpectedSettlement()==null?new BigDecimal(0):tablePipeline.getExpectedSettlement());
                }
                total.setParentName("合计");
                total.setExpectedSettlement(expectedSettlement);
                dats.add(total);
                */
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }
            else if("station".equals(item.getTableName())){ //场站工程
                TableStation station = new TableStation();
                BeanUtils.copyBeanProp(station, vo);
                List<TableStation> list = stationService.selectTableStationList(station);
                ExcelUtilNew<TableStation> util = new ExcelUtilNew<TableStation>(TableStation.class);
                /*
                List<TableStation> dats = new ArrayList<>();
                List<TableStation> parents = stationService.budgetSummaryList(station);
                for(TableStation parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableStation child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                /*
                //按表合计
                TableStation total = new TableStation();
                BigDecimal expectedSettlement = new BigDecimal(0);
                for (TableStation tableStation : list) {
                    dats.add(tableStation);
                    expectedSettlement = expectedSettlement.add(tableStation.getExpectedSettlement()==null?new BigDecimal(0):tableStation.getExpectedSettlement());
                }
                total.setParentName("合计");
                total.setExpectedSettlement(expectedSettlement);
                dats.add(total);
                */
                util.exportExcel(workbook,list,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("housing".equals(item.getTableName())){ //房屋建设、装修
                TableHousing housing = new TableHousing();
                BeanUtils.copyBeanProp(housing, vo);
                List<TableHousing> list = housingService.selectTableHousingList(housing);
                ExcelUtilNew<TableHousing> util = new ExcelUtilNew<TableHousing>(TableHousing.class);
                /*
                List<TableHousing> dats = new ArrayList<>();
                List<TableHousing> parents = housingService.budgetSummaryList(housing);
                for(TableHousing parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableHousing child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                /*
                //按表合计
                TableHousing total = new TableHousing();
                BigDecimal expectedSettlement = new BigDecimal(0);
                for (TableHousing tableHousing : list) {
                    dats.add(tableHousing);
                    expectedSettlement = expectedSettlement.add(tableHousing.getExpectedSettlement()==null?new BigDecimal(0):tableHousing.getExpectedSettlement());
                }
                total.setParentName("合计");
                total.setExpectedSettlement(expectedSettlement);
                dats.add(total);
                */
                util.exportExcel(workbook,list,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("information_system".equals(item.getTableName())){ //信息系统建设
                TableInformationSystem informationSystem = new TableInformationSystem();
                BeanUtils.copyBeanProp(informationSystem, vo);
                List<TableInformationSystem> list = informationSystemService.selectTableInformationSystemList(informationSystem);
                ExcelUtilNew<TableInformationSystem> util = new ExcelUtilNew<TableInformationSystem>(TableInformationSystem.class);
                /*
                List<TableInformationSystem> dats = new ArrayList<>();
                List<TableInformationSystem> parents = informationSystemService.budgetSummaryList(informationSystem);
                for(TableInformationSystem parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableInformationSystem child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                /*
                //按表合计
                TableInformationSystem total = new TableInformationSystem();
                BigDecimal intangibleAssets = new BigDecimal(0);
                BigDecimal fixedAssets = new BigDecimal(0);
                for (TableInformationSystem tableInformationSystem : list) {
                    dats.add(tableInformationSystem);
                    intangibleAssets = intangibleAssets.add(tableInformationSystem.getIntangibleAssets()==null?new BigDecimal(0):tableInformationSystem.getIntangibleAssets());
                    fixedAssets = fixedAssets.add(tableInformationSystem.getFixedAssets()==null?new BigDecimal(0):tableInformationSystem.getFixedAssets());
                }
                total.setParentName("合计");
                total.setIntangibleAssets(intangibleAssets);
                total.setFixedAssets(fixedAssets);
                dats.add(total);
                */
                util.exportExcel(workbook,list,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("research".equals(item.getTableName())){ //科研计划
                TableResearch research = new TableResearch();
                BeanUtils.copyBeanProp(research, vo);
                List<TableResearch> list = researchService.selectTableResearchList(research);
                ExcelUtilNew<TableResearch> util = new ExcelUtilNew<TableResearch>(TableResearch.class);
                /*
                List<TableResearch> dats = new ArrayList<>();
                List<TableResearch> parents = researchService.budgetSummaryList(research);
                for(TableResearch parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableResearch child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                /*
                //按表合计
                TableResearch total = new TableResearch();
                BigDecimal intangibleAssets = new BigDecimal(0);
                BigDecimal fixedAssets = new BigDecimal(0);
                BigDecimal subtotalCosts = new BigDecimal(0);
                for (TableResearch tableResearch : list) {
                    dats.add(tableResearch);
                    intangibleAssets = intangibleAssets.add(tableResearch.getIntangibleAssets()==null?new BigDecimal(0):tableResearch.getIntangibleAssets());
                    fixedAssets = fixedAssets.add(tableResearch.getFixedAssets()==null?new BigDecimal(0):tableResearch.getFixedAssets());
                    subtotalCosts = subtotalCosts.add(tableResearch.getSubtotalCosts()==null?new BigDecimal(0):tableResearch.getSubtotalCosts());
                }
                total.setParentName("合计");
                total.setIntangibleAssets(intangibleAssets);
                total.setFixedAssets(fixedAssets);
                total.setSubtotalCosts(subtotalCosts);
                dats.add(total);
                */
                util.exportExcel(workbook,list,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("meter".equals(item.getTableName())){ //计量器
                TableMeter meter = new TableMeter();
                BeanUtils.copyBeanProp(meter, vo);
                List<TableMeter> list = meterService.selectTableMeterList(meter);
                ExcelUtilNew<TableMeter> util = new ExcelUtilNew<TableMeter>(TableMeter.class);
                /*
                List<TableMeter> dats = new ArrayList<>();
                List<TableMeter> parents = meterService.budgetSummaryList(meter);
                for(TableMeter parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableMeter child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                /*
                //按表合计
                TableMeter total = new TableMeter();
                BigDecimal budget = new BigDecimal(0);
                for (TableMeter tableMeter : list) {
                    dats.add(tableMeter);
                    budget = budget.add(tableMeter.getBudget()==null?new BigDecimal(0):tableMeter.getBudget());
                }
                total.setParentName("合计");
                total.setBudget(budget);
                dats.add(total);
                */
                util.exportExcel(workbook,list,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("charity".equals(item.getTableName())){ //慈善公益
                TableCharity charity = new TableCharity();
                BeanUtils.copyBeanProp(charity, vo);
                List<TableCharity> list = charityService.selectTableCharityList(charity);
                ExcelUtilNew<TableCharity> util = new ExcelUtilNew<TableCharity>(TableCharity.class);
                /*
                List<TableCharity> dats = new ArrayList<>();
                List<TableCharity> parents = charityService.budgetSummaryList(charity);
                for(TableCharity parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableCharity child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                /*
                //按表合计
                TableCharity total = new TableCharity();
                BigDecimal budget = new BigDecimal(0);
                for (TableCharity tableCharity : list) {
                    dats.add(tableCharity);
                    budget = budget.add(tableCharity.getBudget()==null?new BigDecimal(0):tableCharity.getBudget());
                }
                total.setParentName("合计");
                total.setBudget(budget);
                dats.add(total);
                */
                util.exportExcel(workbook,list,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("lowvalue".equals(item.getTableName())){ //低值易耗
                TableLowvalue lowvalue = new TableLowvalue();
                BeanUtils.copyBeanProp(lowvalue, vo);
                List<TableLowvalue> list = lowvalueService.selectTableLowvalueList(lowvalue);
                ExcelUtilNew<TableLowvalue> util = new ExcelUtilNew<TableLowvalue>(TableLowvalue.class);
                /*
                List<TableLowvalue> dats = new ArrayList<>();
                List<TableLowvalue> parents = lowvalueService.budgetSummaryList(lowvalue);
                for(TableLowvalue parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableLowvalue child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                /*
                //按表合计
                TableLowvalue total = new TableLowvalue();
                BigDecimal budget = new BigDecimal(0);
                for (TableLowvalue tableLowvalue : list) {
                    dats.add(tableLowvalue);
                    budget = budget.add(tableLowvalue.getBudget()==null?new BigDecimal(0):tableLowvalue.getBudget());
                }
                total.setParentName("合计");
                total.setBudget(budget);
                dats.add(total);
                */
                util.exportExcel(workbook,list,item.getTableDisplayName(),task==null?null:task.getYear());
            }
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=" +currentDate+ ".xlsx");

        // 写入数据并关闭工作簿
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            response.getOutputStream().write(bos.toByteArray());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, Object>> budgetExportList(Budget query, Long userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if(budgetItem==null || StringUtil.isEmpty(budgetItem.getTableName())){
            return list;
        }
        ReportingTask task = taskService.selectReportingTaskById(query.getTaskId());
        if(task == null ){
            return list;
        }
        List<TableModel> fieldList = tableModelService.selectTableModelListByItem(query.getItemId());
        String tableName = budgetItem.getTableName();
        if(query.getExportType()!=null && query.getExportType()==2){
            //只查询当前填报部门数据
            String deptSql = " AND t.dept_id in (SELECT dept_id from t_budget_item_dept WHERE item_id = "+budgetItem.getId()+" ) ";
            String parentSql = "";
            if(query.getSelectType()!=null && query.getSelectType()==2){
                parentSql = "SELECT pd.dept_name as parentName,pd.dept_id as parentId,SUM(budget) as budget,SUM(budget_year) as budget_year,SUM(actual_incurred) as actual_incurred,SUM(estimated_incurred) as estimated_incurred FROM t_reporting_table_"+budgetItem.getTableName()+" t LEFT JOIN sys_dept d on t.dept_id = d.dept_id LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id where t.del_flag = '0' and t.task_id = "+task.getId()+" and t.status != 1 and  t.status != 4 "+deptSql+" GROUP BY d.parent_id";
            }else if(query.getSelectType()!=null && query.getSelectType()==3){
                parentSql = "SELECT pd.dept_name as parentName,pd.dept_id as parentId,SUM(budget) as budget,SUM(budget_year) as budget_year,SUM(actual_incurred) as actual_incurred,SUM(estimated_incurred) as estimated_incurred FROM t_reporting_table_"+budgetItem.getTableName()+" t LEFT JOIN sys_dept d on t.dept_id = d.dept_id LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id where t.del_flag = '0' and t.task_id = "+task.getId()+" and (t.status = 3 or  t.status = 5) "+deptSql+" GROUP BY d.parent_id";
            }else{
                return list;
            }
            List<Map<String, Object>> parentList = dynamicSqlService.dynamicSelectListSql(parentSql);
            if(parentList!=null && !parentList.isEmpty()){
                for (Map<String, Object> parent : parentList) {
                    list.add(parent);
                    StringBuilder sql = new StringBuilder();
                    sql.append("select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t.actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t.review_reason, t.create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag, u.user_name, CONCAT(d2.dept_name, '-', d.dept_name) AS dept_name, dd.dict_label as status_name");
                    for (TableModel field : fieldList) {
                        if(field.getFieldType().equals("4")){
                            sql.append(", ").append(" e.").append(field.getFieldName()).append(", ").append(field.getFieldName()+".dict_label as ").append(field.getFieldName()+"Name");
                        }else{
                            sql.append(", ").append(" e.").append(field.getFieldName());
                        }
                    }
                    sql.append(" FROM t_reporting_table_").append(tableName)
                            .append(" t LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID")
                            .append(" LEFT JOIN sys_user u ON u.user_id = t.user_id")
                            .append(" LEFT JOIN sys_dept d ON d.dept_id = t.dept_id")
                            .append(" LEFT JOIN sys_dept d2 on d.parent_id = d2.dept_id")
                            .append(" LEFT JOIN sys_dict_data dd ON t.`status` = dd.dict_value AND dd.dict_type = 'budget_status'");
                    for (TableModel field : fieldList) {
                        if(field.getFieldType().equals("4")){
                            String dictType = net.cmkj.common.utils.StringUtils.isEmpty(field.getDictType()) ? " abc " : field.getDictType();
                            sql.append(" LEFT JOIN sys_dict_data "+field.getFieldName()+" ON e."+field.getFieldName()+" = "+field.getFieldName()+".dict_value AND "+field.getFieldName()+".dict_type = '"+dictType+"'");
                        }
                    }
                    sql.append(" WHERE t.task_id = ").append(query.getTaskId()).append(" AND t.del_flag = '0'");
                    if(query.getSelectType()!=null && query.getSelectType()==2){//职能岗导出
                        sql.append(" and (t.`status` = 2 or t.`status` = 3 or t.`status` = 5 or t.`status` = 6  ) ");
                    }else if(query.getSelectType()!=null && query.getSelectType()==3){//业财岗导出
                        sql.append(" and (t.`status` = 3 or t.`status` = 5  ) ");
                    }
                    sql.append(" AND d.parent_id = ").append(parent.get("parentId"));
                    //只查询当前填报部门数据
                    sql.append(" AND t.dept_id in (SELECT dept_id from t_budget_item_dept WHERE item_id = "+budgetItem.getId()+" ) ");
                    sql.append("order by t.dept_id");
                    List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(sql.toString());
                    if(!dates.isEmpty()){
                        list.addAll(dates);
                    }
                }
            }else {
                return list;
            }
        }else{
            List<SysDept> depts = reportingService.itemFieldDept(query, userId);
            List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
            StringBuilder selectSql = new StringBuilder();
            StringBuilder countSql = new StringBuilder();
            StringBuilder fromSql = new StringBuilder();
            StringBuilder whereSql = new StringBuilder();
            selectSql.append("( select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t.actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t.review_reason, t.create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag, u.user_name, CONCAT(d2.dept_name, '-', d.dept_name) AS dept_name, dd.dict_label as status_name");
            countSql.append("( SELECT NULL AS id,NULL AS task_id, NULL AS `year`,NULL AS dept_id,NULL AS user_id,SUM(t.budget) AS budget,NULL AS budget_year,NULL AS actual_incurred,NULL AS estimated_incurred,NULL AS cost_description,NULL AS `status`,NULL AS review_budget,NULL AS review_reason,NULL AS create_by,NULL AS create_time,NULL AS update_by,NULL AS update_time,NULL AS remark,NULL AS del_flag,NULL AS user_name,'总计' AS dept_name,NULL AS status_name ");
            for (TableModel field : fieldList) {
                if(field.getFieldType().equals("4")){
                    selectSql.append(", ").append(" e.").append(field.getFieldName()).append(", ").append(field.getFieldName()+".dict_label as ").append(field.getFieldName()+"Name");
                    countSql.append(", ").append(" NULL AS ").append(field.getFieldName()).append(", ").append(field.getFieldName()+".dict_label as ").append(field.getFieldName()+"Name");
                }else{
                    selectSql.append(", ").append(" e.").append(field.getFieldName());
                    countSql.append(", ").append(" NULL AS ").append(field.getFieldName());
                }
            }
            countSql.append(", ").append(" 1 AS sort_order");
            selectSql.append(", ").append(" 0 AS sort_order");
            fromSql.append(" FROM t_reporting_table_").append(tableName)
                    .append(" t LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID")
                    .append(" LEFT JOIN sys_user u ON u.user_id = t.user_id")
                    .append(" LEFT JOIN sys_dept d ON d.dept_id = t.dept_id")
                    .append(" LEFT JOIN sys_dept d2 on d.parent_id = d2.dept_id")
                    .append(" LEFT JOIN sys_dict_data dd ON t.`status` = dd.dict_value AND dd.dict_type = 'budget_status'");
            for (TableModel field : fieldList) {
                if(field.getFieldType().equals("4")){
                    String dictType = net.cmkj.common.utils.StringUtils.isEmpty(field.getDictType()) ? " abc " : field.getDictType();
                    fromSql.append(" LEFT JOIN sys_dict_data "+field.getFieldName()+" ON e."+field.getFieldName()+" = "+field.getFieldName()+".dict_value AND "+field.getFieldName()+".dict_type = '"+dictType+"'");
                }
            }
            whereSql.append(" WHERE t.task_id = ").append(query.getTaskId()).append(" AND t.del_flag = '0' and (t.status = 3 or t.status = 5 ) )");
            // 获取当前用户的部门
            SysUser queryUser = userMapper.selectUserById(SecurityUtils.getUserId());
            String companyIds = queryUser.getCompanyIds();
            if (companyIds != null && !companyIds.trim().isEmpty()){
                // 正确处理companyIds字符串，确保它是有效的ID列表
                String[] companyIdArray = companyIds.split(",");
                StringBuilder inClause = new StringBuilder();
                for (int i = 0; i < companyIdArray.length; i++) {
                    if (i > 0) {
                        inClause.append(",");
                    }
                    inClause.append("'").append(companyIdArray[i].trim()).append("'");
                }
                whereSql.append(" AND t.dept_id in (SELECT dept_id from sys_dept WHERE parent_id in ( "+ inClause.toString() +
                        " ) ) ");
            }
            selectSql.append(fromSql).append(whereSql).append(" UNION ALL  ").append(countSql).append(fromSql).append(whereSql).append("  ORDER BY sort_order, dept_id ");
            List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(selectSql.toString());
            if(!dates.isEmpty()){
                list.addAll(dates);
            }
        }
        return list;
    }

    public List<SysDept> itemFieldDept2(Long itemId, Long userId) {
        List<SysDept> depts = new ArrayList<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(itemId);
        if(budgetItem==null || StringUtil.isEmpty(budgetItem.getTableName())){
            return depts;
        }
        SysUser user = userMapper.selectUserById(userId);
        if(user==null || user.getDept()==null){
            return depts;
        }
        SysDept dept = user.getDept();
        List<SysDept> itemDepts = budgetItemDeptMapper.selectDeptListByItemId(budgetItem.getId());
        if(!SecurityUtils.isAdmin(userId)){
            if(dept.getLevel() == 1){
                SysDept queryDept = new SysDept();
                queryDept.setParentId(dept.getDeptId());
                List<SysDept> userDepts = deptMapper.selectDeptList(queryDept);
                List<SysDept> commonDepts = userDepts.stream()
                        .filter(userDept -> itemDepts.stream()
                                .anyMatch(itemDept -> itemDept.getDeptId().longValue() == userDept.getDeptId().longValue()))
                        .toList();
                depts.addAll(commonDepts);
            }else if(dept.getLevel() == 2){
                if (itemDepts.stream().anyMatch(item -> item.getDeptId().longValue() == dept.getDeptId().longValue())) {
                    depts.add(dept);
                }
            }
        }else{
            depts = itemDepts;
        }
        return depts;
    }

    private void changeYearName(List<TableModel> fieldList, Integer year){
        for (TableModel tableModel : fieldList) {
            if(tableModel.getFieldDisplayName().equals("后年")){
                tableModel.setFieldDisplayName((year+2)+"年");
            }else if(tableModel.getFieldDisplayName().equals("大后年")){
                tableModel.setFieldDisplayName((year+3)+"年");
            }else if(tableModel.getFieldDisplayName().equals("大大后年")){
                tableModel.setFieldDisplayName((year+4)+"年");
            }else if(tableModel.getFieldDisplayName().equals("大大大后年")){
                tableModel.setFieldDisplayName((year+5)+"年");
            }
        }
    }

    @Log(title = "所有表所有数据导出", businessType = BusinessType.EXPORT)
    @PostMapping("/exportAllTotal")
    public void exportAllTotal(HttpServletResponse response,  FixedVo vo) throws IOException {
        List<Map<String, Object>> lmap = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook();
        BudgetItem budgetItem = new BudgetItem();
        ReportingTask task =taskService.selectReportingTaskById(vo.getTaskId());
        budgetItem.setReportingType("1");//动态表
        budgetItem.setDelFlag("0");
        List<BudgetItem> dList = budgetItemService.selectBudgetItemList(budgetItem);
        for (int i = 0; i < dList.size(); i++) {
            Map<String, Object> result = new HashMap<>();
            BudgetItem item = dList.get(i);
            Budget query = new Budget();
            query.setItemId(item.getId());
            query.setTaskId(vo.getTaskId());
            List<Map<String, Object>> titles = new ArrayList<>();
            List<TableModel> tableModels = new ArrayList<>();
            addFixedField(tableModels,false);
            TableModel queryModel = new TableModel();
            queryModel.setItemId(item.getId());
            queryModel.setDelFlag("0");
            List<TableModel> queryModels  = tableModelService.selectTableModelList(queryModel);
            queryModels.removeIf(model -> model.getFieldName().equals("status") && model.getType()==1);
            tableModels.addAll(queryModels);
            if(task!=null && task.getYear()!=null){
                changeYearName(tableModels,task.getYear());
            }
            List<Map<String, Object>> datas = budgetExportList3(query, getUserId());//去掉根据人员查询的功能，查出来所有数据
            for (TableModel tableModel : tableModels) {
                Map<String, Object> map2 = new HashMap<>();
                String fieldName = tableModel.getFieldName();
                String fieldDisplayName = "";
                if(fieldName.equals("budget")){
                    fieldDisplayName = (task.getYear()+1)+"年预算";
                }else if(fieldName.equals("budget_year")){
                    fieldDisplayName = task.getYear()+"年预算";
                }else if(fieldName.equals("actual_incurred")){
                    fieldDisplayName = task.getYear()+"年实际发生额";
                }else if(fieldName.equals("estimated_incurred")){
                    fieldDisplayName = task.getYear()+"年预计发生费用";
                }else {
                    fieldDisplayName = tableModel.getFieldDisplayName();
                }
                if(tableModel.getFieldType().equals("4")&& tableModel.getSourceTable().equals("extension")){
                    map2.put("name", fieldDisplayName);
                    map2.put("key", tableModel.getFieldName()+"Name");
                }else{
                    map2.put("name", fieldDisplayName);
                    map2.put("key", tableModel.getFieldName());
                }
                titles.add(map2);
            }
            result.put("datas",datas);
            result.put("titles",titles);
            result.put("sheetName",item.getTableDisplayName());
            lmap.add(result);
        }
        ExportSheetUtil.exportSingleHeaderExcel(workbook,lmap);

        budgetItem.setReportingType("2");//固定表
        List<BudgetItem> gList = budgetItemService.selectBudgetItemList(budgetItem);
        for (BudgetItem item : gList) {
            vo.setItemId(item.getId());
            vo.setSelectType(4);
            if("pipeline".equals(item.getTableName())){ //天然气管线
                TablePipeline pipeline = new TablePipeline();
                BeanUtils.copyBeanProp(pipeline, vo);
                List<TablePipeline> list = pipelineService.selectTablePipelineList(pipeline);
                ExcelUtilNew<TablePipeline> util = new ExcelUtilNew<TablePipeline>(TablePipeline.class);
                List<TablePipeline> dats = new ArrayList<>();
                /*
                List<TablePipeline> parents = pipelineService.budgetSummaryList(pipeline);
                for(TablePipeline parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TablePipeline child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                //按表合计
                TablePipeline total = new TablePipeline();
                BigDecimal expectedSettlement = new BigDecimal(0);
                for (TablePipeline tablePipeline : list) {
                    dats.add(tablePipeline);
                    expectedSettlement = expectedSettlement.add(tablePipeline.getExpectedSettlement()==null?new BigDecimal(0):tablePipeline.getExpectedSettlement());
                }
                total.setParentName("合计");
                total.setExpectedSettlement(expectedSettlement);
                dats.add(total);
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }
            else if("station".equals(item.getTableName())){ //场站工程
                TableStation station = new TableStation();
                BeanUtils.copyBeanProp(station, vo);
                List<TableStation> list = stationService.selectTableStationList(station);
                ExcelUtilNew<TableStation> util = new ExcelUtilNew<TableStation>(TableStation.class);
                List<TableStation> dats = new ArrayList<>();
                /*
                List<TableStation> parents = stationService.budgetSummaryList(station);
                for(TableStation parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableStation child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                 */
                //按表合计
                TableStation total = new TableStation();
                BigDecimal expectedSettlement = new BigDecimal(0);
                for (TableStation tableStation : list) {
                    dats.add(tableStation);
                    expectedSettlement = expectedSettlement.add(tableStation.getExpectedSettlement()==null?new BigDecimal(0):tableStation.getExpectedSettlement());
                }
                total.setParentName("合计");
                total.setExpectedSettlement(expectedSettlement);
                dats.add(total);
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("housing".equals(item.getTableName())){ //房屋建设、装修
                TableHousing housing = new TableHousing();
                BeanUtils.copyBeanProp(housing, vo);
                List<TableHousing> list = housingService.selectTableHousingList(housing);
                ExcelUtilNew<TableHousing> util = new ExcelUtilNew<TableHousing>(TableHousing.class);
                List<TableHousing> dats = new ArrayList<>();
                /*
                List<TableHousing> parents = housingService.budgetSummaryList(housing);
                for(TableHousing parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableHousing child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                //按表合计
                TableHousing total = new TableHousing();
                BigDecimal expectedSettlement = new BigDecimal(0);
                for (TableHousing tableHousing : list) {
                    dats.add(tableHousing);
                    expectedSettlement = expectedSettlement.add(tableHousing.getExpectedSettlement()==null?new BigDecimal(0):tableHousing.getExpectedSettlement());
                }
                total.setParentName("合计");
                total.setExpectedSettlement(expectedSettlement);
                dats.add(total);
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("information_system".equals(item.getTableName())){ //信息系统建设
                TableInformationSystem informationSystem = new TableInformationSystem();
                BeanUtils.copyBeanProp(informationSystem, vo);
                List<TableInformationSystem> list = informationSystemService.selectTableInformationSystemList(informationSystem);
                ExcelUtilNew<TableInformationSystem> util = new ExcelUtilNew<TableInformationSystem>(TableInformationSystem.class);
                List<TableInformationSystem> dats = new ArrayList<>();
                /*
                List<TableInformationSystem> parents = informationSystemService.budgetSummaryList(informationSystem);
                for(TableInformationSystem parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableInformationSystem child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                //按表合计
                TableInformationSystem total = new TableInformationSystem();
                BigDecimal intangibleAssets = new BigDecimal(0);
                BigDecimal fixedAssets = new BigDecimal(0);
                for (TableInformationSystem tableInformationSystem : list) {
                    dats.add(tableInformationSystem);
                    intangibleAssets = intangibleAssets.add(tableInformationSystem.getIntangibleAssets()==null?new BigDecimal(0):tableInformationSystem.getIntangibleAssets());
                    fixedAssets = fixedAssets.add(tableInformationSystem.getFixedAssets()==null?new BigDecimal(0):tableInformationSystem.getFixedAssets());
                }
                total.setParentName("合计");
                total.setIntangibleAssets(intangibleAssets);
                total.setFixedAssets(fixedAssets);
                dats.add(total);
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("research".equals(item.getTableName())){ //科研计划
                TableResearch research = new TableResearch();
                BeanUtils.copyBeanProp(research, vo);
                List<TableResearch> list = researchService.selectTableResearchList(research);
                ExcelUtilNew<TableResearch> util = new ExcelUtilNew<TableResearch>(TableResearch.class);
                List<TableResearch> dats = new ArrayList<>();
                /*
                List<TableResearch> parents = researchService.budgetSummaryList(research);
                for(TableResearch parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableResearch child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                //按表合计
                TableResearch total = new TableResearch();
                BigDecimal intangibleAssets = new BigDecimal(0);
                BigDecimal fixedAssets = new BigDecimal(0);
                BigDecimal subtotalCosts = new BigDecimal(0);
                for (TableResearch tableResearch : list) {
                    dats.add(tableResearch);
                    intangibleAssets = intangibleAssets.add(tableResearch.getIntangibleAssets()==null?new BigDecimal(0):tableResearch.getIntangibleAssets());
                    fixedAssets = fixedAssets.add(tableResearch.getFixedAssets()==null?new BigDecimal(0):tableResearch.getFixedAssets());
                    subtotalCosts = subtotalCosts.add(tableResearch.getSubtotalCosts()==null?new BigDecimal(0):tableResearch.getSubtotalCosts());
                }
                total.setParentName("合计");
                total.setIntangibleAssets(intangibleAssets);
                total.setFixedAssets(fixedAssets);
                total.setSubtotalCosts(subtotalCosts);
                dats.add(total);
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("meter".equals(item.getTableName())){ //计量器
                TableMeter meter = new TableMeter();
                BeanUtils.copyBeanProp(meter, vo);
                List<TableMeter> list = meterService.selectTableMeterList(meter);
                ExcelUtilNew<TableMeter> util = new ExcelUtilNew<TableMeter>(TableMeter.class);
                List<TableMeter> dats = new ArrayList<>();
                /*
                List<TableMeter> parents = meterService.budgetSummaryList(meter);
                for(TableMeter parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableMeter child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                //按表合计
                TableMeter total = new TableMeter();
                BigDecimal budget = new BigDecimal(0);
                for (TableMeter tableMeter : list) {
                    dats.add(tableMeter);
                    budget = budget.add(tableMeter.getBudget()==null?new BigDecimal(0):tableMeter.getBudget());
                }
                total.setParentName("合计");
                total.setBudget(budget);
                dats.add(total);
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("charity".equals(item.getTableName())){ //慈善公益
                TableCharity charity = new TableCharity();
                BeanUtils.copyBeanProp(charity, vo);
                List<TableCharity> list = charityService.selectTableCharityList(charity);
                ExcelUtilNew<TableCharity> util = new ExcelUtilNew<TableCharity>(TableCharity.class);
                List<TableCharity> dats = new ArrayList<>();
                /*
                List<TableCharity> parents = charityService.budgetSummaryList(charity);
                for(TableCharity parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableCharity child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                //按表合计
                TableCharity total = new TableCharity();
                BigDecimal budget = new BigDecimal(0);
                for (TableCharity tableCharity : list) {
                    dats.add(tableCharity);
                    budget = budget.add(tableCharity.getBudget()==null?new BigDecimal(0):tableCharity.getBudget());
                }
                total.setParentName("合计");
                total.setBudget(budget);
                dats.add(total);
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }else if("lowvalue".equals(item.getTableName())){ //低值易耗
                TableLowvalue lowvalue = new TableLowvalue();
                BeanUtils.copyBeanProp(lowvalue, vo);
                List<TableLowvalue> list = lowvalueService.selectTableLowvalueList(lowvalue);
                ExcelUtilNew<TableLowvalue> util = new ExcelUtilNew<TableLowvalue>(TableLowvalue.class);
                List<TableLowvalue> dats = new ArrayList<>();
                /*
                List<TableLowvalue> parents = lowvalueService.budgetSummaryList(lowvalue);
                for(TableLowvalue parent : parents){
                    parent.setParentName(parent.getDeptName());
                    parent.setDeptName("");
                    dats.add(parent);
                    for(TableLowvalue child : list){
                        if(parent.getDeptId().longValue()==child.getParentId().longValue()){
                            dats.add(child);
                        }
                    }
                }
                */
                //按表合计
                TableLowvalue total = new TableLowvalue();
                BigDecimal budget = new BigDecimal(0);
                for (TableLowvalue tableLowvalue : list) {
                    dats.add(tableLowvalue);
                    budget = budget.add(tableLowvalue.getBudget()==null?new BigDecimal(0):tableLowvalue.getBudget());
                }
                total.setParentName("合计");
                total.setBudget(budget);
                dats.add(total);
                util.exportExcel(workbook,dats,item.getTableDisplayName(),task==null?null:task.getYear());
            }
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=" +currentDate+ ".xlsx");

        // 写入数据并关闭工作簿
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            response.getOutputStream().write(bos.toByteArray());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<Map<String, Object>> budgetExportList3(Budget query, Long userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if(budgetItem==null || StringUtil.isEmpty(budgetItem.getTableName())){
            return list;
        }
        ReportingTask task = taskService.selectReportingTaskById(query.getTaskId());
        if(task == null ){
            return list;
        }
        List<TableModel> fieldList = tableModelService.selectTableModelListByItem(query.getItemId());
        String tableName = budgetItem.getTableName();
        if(query.getExportType()!=null && query.getExportType()==2){
            //只查询当前填报部门数据
            String deptSql = " AND t.dept_id in (SELECT dept_id from t_budget_item_dept WHERE item_id = "+budgetItem.getId()+" ) ";
            String parentSql = "";
            if(query.getSelectType()!=null && query.getSelectType()==2){
                parentSql = "SELECT pd.dept_name as parentName,pd.dept_id as parentId,SUM(budget) as budget,SUM(budget_year) as budget_year,SUM(actual_incurred) as actual_incurred,SUM(estimated_incurred) as estimated_incurred FROM t_reporting_table_"+budgetItem.getTableName()+" t LEFT JOIN sys_dept d on t.dept_id = d.dept_id LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id where t.del_flag = '0' and t.task_id = "+task.getId()+" and t.status != 1 and  t.status != 4 "+deptSql+" GROUP BY d.parent_id";
            }else if(query.getSelectType()!=null && query.getSelectType()==3){
                parentSql = "SELECT pd.dept_name as parentName,pd.dept_id as parentId,SUM(budget) as budget,SUM(budget_year) as budget_year,SUM(actual_incurred) as actual_incurred,SUM(estimated_incurred) as estimated_incurred FROM t_reporting_table_"+budgetItem.getTableName()+" t LEFT JOIN sys_dept d on t.dept_id = d.dept_id LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id where t.del_flag = '0' and t.task_id = "+task.getId()+" and (t.status = 3 or  t.status = 5) "+deptSql+" GROUP BY d.parent_id";
            }else{
                return list;
            }
            List<Map<String, Object>> parentList = dynamicSqlService.dynamicSelectListSql(parentSql);
            if(parentList!=null && !parentList.isEmpty()){
                for (Map<String, Object> parent : parentList) {
                    list.add(parent);
                    StringBuilder sql = new StringBuilder();
                    sql.append("select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t.actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t.review_reason, t.create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag, u.user_name, CONCAT(d2.dept_name, '-', d.dept_name) AS dept_name, dd.dict_label as status_name");
                    for (TableModel field : fieldList) {
                        if(field.getFieldType().equals("4")){
                            sql.append(", ").append(" e.").append(field.getFieldName()).append(", ").append(field.getFieldName()+".dict_label as ").append(field.getFieldName()+"Name");
                        }else{
                            sql.append(", ").append(" e.").append(field.getFieldName());
                        }
                    }
                    sql.append(" FROM t_reporting_table_").append(tableName)
                            .append(" t LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID")
                            .append(" LEFT JOIN sys_user u ON u.user_id = t.user_id")
                            .append(" LEFT JOIN sys_dept d ON d.dept_id = t.dept_id")
                            .append(" LEFT JOIN sys_dept d2 on d.parent_id = d2.dept_id")
                            .append(" LEFT JOIN sys_dict_data dd ON t.`status` = dd.dict_value AND dd.dict_type = 'budget_status'");
                    for (TableModel field : fieldList) {
                        if(field.getFieldType().equals("4")){
                            String dictType = net.cmkj.common.utils.StringUtils.isEmpty(field.getDictType()) ? " abc " : field.getDictType();
                            sql.append(" LEFT JOIN sys_dict_data "+field.getFieldName()+" ON e."+field.getFieldName()+" = "+field.getFieldName()+".dict_value AND "+field.getFieldName()+".dict_type = '"+dictType+"'");
                        }
                    }
                    sql.append(" WHERE t.task_id = ").append(query.getTaskId()).append(" AND t.del_flag = '0'");
                    if(query.getSelectType()!=null && query.getSelectType()==2){//职能岗导出
                        sql.append(" and (t.`status` = 2 or t.`status` = 3 or t.`status` = 5 or t.`status` = 6  ) ");
                    }else if(query.getSelectType()!=null && query.getSelectType()==3){//业财岗导出
                        sql.append(" and (t.`status` = 3 or t.`status` = 5  ) ");
                    }
                    sql.append(" AND d.parent_id = ").append(parent.get("parentId"));
                    //只查询当前填报部门数据
                    sql.append(" AND t.dept_id in (SELECT dept_id from t_budget_item_dept WHERE item_id = "+budgetItem.getId()+" ) ");
                    sql.append("order by t.dept_id");
                    List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(sql.toString());
                    if(!dates.isEmpty()){
                        list.addAll(dates);
                    }
                }
            }else {
                return list;
            }
        }
        else{
            StringBuilder selectSql = new StringBuilder();
            StringBuilder countSql = new StringBuilder();
            StringBuilder fromSql = new StringBuilder();
            StringBuilder whereSql = new StringBuilder();
            selectSql.append("( select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t.actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t.review_reason, t.create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag, u.user_name, CONCAT(d2.dept_name, '-', d.dept_name) AS dept_name, dd.dict_label as status_name");
            countSql.append("( SELECT NULL AS id,NULL AS task_id, NULL AS `year`,NULL AS dept_id,NULL AS user_id,SUM(t.budget) AS budget,NULL AS budget_year,NULL AS actual_incurred,NULL AS estimated_incurred,NULL AS cost_description,NULL AS `status`,NULL AS review_budget,NULL AS review_reason,NULL AS create_by,NULL AS create_time,NULL AS update_by,NULL AS update_time,NULL AS remark,NULL AS del_flag,NULL AS user_name,'总计' AS dept_name,NULL AS status_name ");
            for (TableModel field : fieldList) {
                if(field.getFieldType().equals("4")){
                    selectSql.append(", ").append(" e.").append(field.getFieldName()).append(", ").append(field.getFieldName()+".dict_label as ").append(field.getFieldName()+"Name");
                    countSql.append(", ").append(" NULL AS ").append(field.getFieldName()).append(", ").append(field.getFieldName()+".dict_label as ").append(field.getFieldName()+"Name");
                }else{
                    selectSql.append(", ").append(" e.").append(field.getFieldName());
                    countSql.append(", ").append(" NULL AS ").append(field.getFieldName());
                }
            }
            countSql.append(", ").append(" 1 AS sort_order");
            selectSql.append(", ").append(" 0 AS sort_order");

            fromSql.append(" FROM t_reporting_table_").append(tableName)
                    .append(" t LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID")
                    .append(" LEFT JOIN sys_user u ON u.user_id = t.user_id")
                    .append(" LEFT JOIN sys_dept d ON d.dept_id = t.dept_id")
                    .append(" LEFT JOIN sys_dept d2 on d.parent_id = d2.dept_id")
                    .append(" LEFT JOIN sys_dict_data dd ON t.`status` = dd.dict_value AND dd.dict_type = 'budget_status'");
            for (TableModel field : fieldList) {
                if(field.getFieldType().equals("4")){
                    String dictType = net.cmkj.common.utils.StringUtils.isEmpty(field.getDictType()) ? " abc " : field.getDictType();
                    fromSql.append(" LEFT JOIN sys_dict_data "+field.getFieldName()+" ON e."+field.getFieldName()+" = "+field.getFieldName()+".dict_value AND "+field.getFieldName()+".dict_type = '"+dictType+"'");
                }
            }
            whereSql.append(" WHERE t.task_id = ").append(query.getTaskId()).append(" AND t.del_flag = '0' and (t.status = 3 or t.status = 5 ) )");
            // 获取当前用户的部门
            SysUser queryUser = userMapper.selectUserById(SecurityUtils.getUserId());
            String companyIds = queryUser.getCompanyIds();
            if (companyIds != null){
                // 去除StringBuilder类型的whereSql末尾的括号
                whereSql.deleteCharAt(whereSql.length() - 1);
                whereSql.append(" AND t.dept_id in (SELECT dept_id from sys_dept WHERE parent_id in ( "+ companyIds +
                        " ) ) ) ");
            }
            selectSql.append(fromSql).append(whereSql).append(" UNION ALL  ").append(countSql).append(fromSql).append(whereSql).append("  ORDER BY sort_order, dept_id ");
            List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(selectSql.toString());
            if(!dates.isEmpty()){
                list.addAll(dates);
            }
        }
        return list;
    }

    /**
     * 填报项目数据保存
     */
    @Log(title = "填报数据", businessType = BusinessType.INSERT)
    @SaCheckPermission("budget:business:add")
    @PostMapping("/budgetSave")
    public AjaxResult budgetSave(@RequestBody Map<String, String> map) {
        if (StringUtils.isEmpty(map.get("id"))) {
            return reportingService.add(map);
        }else{
            return reportingService.update(map);
        }
    }

    /**
     * 填报项目数据删除
     */
    @Log(title = "填报数据", businessType = BusinessType.DELETE)
    @SaCheckPermission("budget:business:delete")
    @PostMapping("/budgetDelete")
    public AjaxResult budgetDelete(@RequestBody Budget budget) {
        return reportingService.delete(budget);
    }


    /**
     * 填报项目数据状态变更
     */
    @Log(title = "填报数据状态变更", businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody Budget budget) {
        return reportingService.updateStatus(budget);
    }

    /**
     * 填报流程
     * @param budget
     * @return
     */
    @GetMapping("/reportingLog")
    public AjaxResult reportingLog(Budget budget) {
        return reportingService.reportingLog(budget);
    }

    /**
     * 填报项目数据版本保存
     */
    @SaCheckPermission("budget:financial:version")
    @PostMapping("/saveVersion")
    public AjaxResult saveVersion(@RequestBody ReportingVersion reportingVersion) {
        return reportingService.saveVersion(reportingVersion);
    }

    /**
     * 一键回填上次填报数据
     * @param budget
     * @return
     */
    @PostMapping("/backfill")
    public AjaxResult backfill(@RequestBody Budget budget) {
        return reportingService.backfill(budget,getUserId());
    }

    /**
     * 查询任务完成状态
     * @return
     */
    @GetMapping("/checkTaskStatus")
    public AjaxResult checkTaskStatus() {
        return reportingService.checkStatus();
    }

    /**
     * 修改任务完成状态
     * @return
     */
    @PostMapping("/updateTaskStatus")
    public AjaxResult updateTaskStatus(@RequestBody Budget budget) {
        return reportingService.updateTaskStatus(budget);
    }

    /**
     * 一键审核
     * @param budget
     * @return
     */
    @PostMapping("/clickAudit")
    public AjaxResult clickAudit(@RequestBody Budget budget) {
        return reportingService.clickAudit(budget);
    }

    @PostMapping("/rejectByItem")
    public AjaxResult rejectByItem(@RequestBody Budget budget) {
        return reportingService.rejectByItem(budget,getUserId());
    }
}
