package net.cmkj.web.controller.fixed;

import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.Budget;
import net.cmkj.budget.domain.BudgetItem;
import net.cmkj.budget.domain.ReportingTask;
import net.cmkj.budget.mapper.BudgetItemDeptMapper;
import net.cmkj.budget.service.IBudgetItemService;
import net.cmkj.budget.service.IReportingTaskService;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.bean.BeanUtils;
import net.cmkj.common.utils.poi.ExcelUtil;
import net.cmkj.fixed.domain.*;
import net.cmkj.fixed.service.*;
import net.cmkj.system.mapper.SysDeptMapper;
import net.cmkj.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预算填报固定表Controller
 *
 * @author cmkj
 * @date 2024-08-26
 */
@RestController
@RequestMapping("/fixed/tables")
public class FixedTableController extends BaseController {
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

    @GetMapping("/list")
    public TableDataInfo list(FixedVo vo) {
        List<SysDept> depts = itemFieldDept(vo.getItemId(), getUserId());
        List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        vo.setDeptIds(deptIds);
        if("pipeline".equals(vo.getTableName())){ //天然气管线
            TablePipeline pipeline = new TablePipeline();
            BeanUtils.copyBeanProp(pipeline, vo);
            startPage();
            return getDataTable(pipelineService.selectTablePipelineList(pipeline));
        }else if("station".equals(vo.getTableName())){ //场站工程
            TableStation station = new TableStation();
            BeanUtils.copyBeanProp(station, vo);
            startPage();
            return getDataTable(stationService.selectTableStationList(station));
        }else if("housing".equals(vo.getTableName())){ //房屋建设、装修
            TableHousing housing = new TableHousing();
            BeanUtils.copyBeanProp(housing, vo);
            startPage();
            return getDataTable(housingService.selectTableHousingList(housing));
        }else if("information_system".equals(vo.getTableName())){ //信息系统建设
            TableInformationSystem informationSystem = new TableInformationSystem();
            BeanUtils.copyBeanProp(informationSystem, vo);
            startPage();
            return getDataTable(informationSystemService.selectTableInformationSystemList(informationSystem));
        }else if("research".equals(vo.getTableName())){ //科研计划
            TableResearch research = new TableResearch();
            BeanUtils.copyBeanProp(research, vo);
            startPage();
            return getDataTable(researchService.selectTableResearchList(research));
        }else if("meter".equals(vo.getTableName())){//计量器
            TableMeter meter = new TableMeter();
            BeanUtils.copyBeanProp(meter, vo);
            startPage();
            return getDataTable(meterService.selectTableMeterList(meter));
        }else if("charity".equals(vo.getTableName())){ //慈善公益
            TableCharity charity = new TableCharity();
            BeanUtils.copyBeanProp(charity, vo);
            startPage();
            return getDataTable(charityService.selectTableCharityList(charity));
        }else if("lowvalue".equals(vo.getTableName())){ //低值易耗
            TableLowvalue lowvalue = new TableLowvalue();
            BeanUtils.copyBeanProp(lowvalue, vo);
            startPage();
            return getDataTable(lowvalueService.selectTableLowvalueList(lowvalue));
        }else {
            return getDataTable(new ArrayList<>());
        }
    }

    @GetMapping("/listAll")
    public AjaxResult listAll(FixedVo vo) {
        List<SysDept> depts = itemFieldDept(vo.getItemId(), getUserId());
        List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        vo.setDeptIds(deptIds);
        if("pipeline".equals(vo.getTableName())){ //天然气管线
            TablePipeline pipeline = new TablePipeline();
            BeanUtils.copyBeanProp(pipeline, vo);
            if(pipeline.getSelectType()!=null && (pipeline.getSelectType()==2 || pipeline.getSelectType()==3)){
                return AjaxResult.success(pipelineService.selectTablePipelineCountList(pipeline));
            }else{
                return AjaxResult.success(pipelineService.selectTablePipelineList(pipeline));
            }
        }else if("station".equals(vo.getTableName())){ //场站工程
            TableStation station = new TableStation();
            BeanUtils.copyBeanProp(station, vo);
            if(station.getSelectType()!=null && (station.getSelectType()==2 || station.getSelectType()==3)){
                return AjaxResult.success(stationService.selectTableStationCountList(station));
            }else{
                return AjaxResult.success(stationService.selectTableStationList(station));
            }
        }else if("housing".equals(vo.getTableName())){ //房屋建设、装修
            TableHousing housing = new TableHousing();
            BeanUtils.copyBeanProp(housing, vo);
            if(housing.getSelectType()!=null && (housing.getSelectType()==2 || housing.getSelectType()==3)){
                return AjaxResult.success(housingService.selectTableHousingCountList(housing));
            }else{
                return AjaxResult.success(housingService.selectTableHousingList(housing));
            }
        }else if("information_system".equals(vo.getTableName())){ //信息系统建设
            TableInformationSystem informationSystem = new TableInformationSystem();
            BeanUtils.copyBeanProp(informationSystem, vo);
            if(informationSystem.getSelectType()!=null && (informationSystem.getSelectType()==2 || informationSystem.getSelectType()==3)){
                return AjaxResult.success(informationSystemService.selectTableInformationSystemCountList(informationSystem));
            }else{
                return AjaxResult.success(informationSystemService.selectTableInformationSystemList(informationSystem));
            }
        }else if("research".equals(vo.getTableName())){ //科研计划
            TableResearch research = new TableResearch();
            BeanUtils.copyBeanProp(research, vo);
            if(research.getSelectType()!=null && (research.getSelectType()==2 || research.getSelectType()==3)){
                return AjaxResult.success(researchService.selectTableResearchCountList(research));
            }else {
                return AjaxResult.success(researchService.selectTableResearchList(research));
            }
        }else if("meter".equals(vo.getTableName())){ //计量器
            TableMeter meter = new TableMeter();
            BeanUtils.copyBeanProp(meter, vo);
            if(meter.getSelectType()!=null && (meter.getSelectType()==2 || meter.getSelectType()==3)){
                return AjaxResult.success(meterService.selectTableMeterCountList(meter));
            }else{
                return AjaxResult.success(meterService.selectTableMeterList(meter));
            }
        }else if("charity".equals(vo.getTableName())){ //慈善公益
            TableCharity charity = new TableCharity();
            BeanUtils.copyBeanProp(charity, vo);
            if (charity.getSelectType()!=null && (charity.getSelectType()==2 || charity.getSelectType()==3)){
                return AjaxResult.success(charityService.selectTableCharityCountList(charity));
            }else {
                return AjaxResult.success(charityService.selectTableCharityList(charity));
            }
        }else if("lowvalue".equals(vo.getTableName())){ //低值易耗
            TableLowvalue lowvalue = new TableLowvalue();
            BeanUtils.copyBeanProp(lowvalue, vo);
            if(lowvalue.getSelectType()!=null && (lowvalue.getSelectType()==2 || lowvalue.getSelectType()==3)){
                return AjaxResult.success(lowvalueService.selectTableLowvalueCountList(lowvalue));
            }else{
                return AjaxResult.success(lowvalueService.selectTableLowvalueList(lowvalue));
            }
        }else{
            return AjaxResult.success(new ArrayList<>());
        }
    }

    /**
     * 新增预算填报项目【定制表】
     */
    @Log(title = "预算填报【定制表】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FixedVo vo) {
        if(vo.getDeptId()==null){
            return AjaxResult.error("部门信息有误！");
        }
        if("pipeline".equals(vo.getTableName())){ //天然气管线
            TablePipeline pipeline = new TablePipeline();
            BeanUtils.copyBeanProp(pipeline, vo);
            if(pipeline.getId()!=null){
                return toAjax(pipelineService.updateTablePipeline(pipeline));
            }else{
                return toAjax(pipelineService.insertTablePipeline(pipeline));
            }
        }else if("station".equals(vo.getTableName())){ //场站工程
            TableStation station = new TableStation();
            BeanUtils.copyBeanProp(station, vo);
            if(station.getId()!=null){
                return toAjax(stationService.updateTableStation(station));
            }else{
                return toAjax(stationService.insertTableStation(station));
            }
        }else if("housing".equals(vo.getTableName())){ //房屋建设、装修
            TableHousing housing = new TableHousing();
            BeanUtils.copyBeanProp(housing, vo);
            if(housing.getId()!=null){
                return toAjax(housingService.updateTableHousing(housing));
            }else{
                return toAjax(housingService.insertTableHousing(housing));
            }
        }else if("information_system".equals(vo.getTableName())){ //信息系统建设
            TableInformationSystem informationSystem = new TableInformationSystem();
            BeanUtils.copyBeanProp(informationSystem, vo);
            if(informationSystem.getId()!=null){
                return toAjax(informationSystemService.updateTableInformationSystem(informationSystem));
            }else{
                return toAjax(informationSystemService.insertTableInformationSystem(informationSystem));
            }
        }else if("research".equals(vo.getTableName())){ //科研计划
            TableResearch research = new TableResearch();
            BeanUtils.copyBeanProp(research, vo);
            if(research.getId()!=null){
                return toAjax(researchService.updateTableResearch(research));
            }else{
                return toAjax(researchService.insertTableResearch(research));
            }
        }else if("meter".equals(vo.getTableName())){ //计量器
            TableMeter meter = new TableMeter();
            BeanUtils.copyBeanProp(meter, vo);
            if(meter.getId()!=null){
                return toAjax(meterService.updateTableMeter(meter));
            }else{
                return toAjax(meterService.insertTableMeter(meter));
            }
        }else if("charity".equals(vo.getTableName())){ //慈善公益
            TableCharity charity = new TableCharity();
            BeanUtils.copyBeanProp(charity, vo);
            if(charity.getId()!=null){
                return toAjax(charityService.updateTableCharity(charity));
            }else{
                return toAjax(charityService.insertTableCharity(charity));
            }
        }else if("lowvalue".equals(vo.getTableName())){ //低值易耗
            TableLowvalue lowvalue = new TableLowvalue();
            BeanUtils.copyBeanProp(lowvalue, vo);
            if(lowvalue.getId()!=null){
                return toAjax(lowvalueService.updateTableLowvalue(lowvalue));
            }else{
                return toAjax(lowvalueService.insertTableLowvalue(lowvalue));
            }
        }else{
            return AjaxResult.error("填报数据有误");
        }
    }

    /**
     * 修改预算填报状态【定制表】
     */
    @Log(title = "预算填报【定制表】", businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody FixedVo vo) {
        if(vo.getIds()==null || vo.getIds().isEmpty()){
            return AjaxResult.error("选中数据有误！");
        }
        if("pipeline".equals(vo.getTableName())){ //天然气管线
            return pipelineService.updateStatus(vo);
        }else if("station".equals(vo.getTableName())){ //场站工程
            return stationService.updateStatus(vo);
        }else if("housing".equals(vo.getTableName())){ //房屋建设、装修
            return housingService.updateStatus(vo);
        }else if("information_system".equals(vo.getTableName())){ //信息系统建设
            return informationSystemService.updateStatus(vo);
        }else if("research".equals(vo.getTableName())){ //科研计划
            return researchService.updateStatus(vo);
        }else if("meter".equals(vo.getTableName())){ //计量器
            return meterService.updateStatus(vo);
        }else if("charity".equals(vo.getTableName())){ //慈善公益
            return charityService.updateStatus(vo);
        }else if("lowvalue".equals(vo.getTableName())){ //低值易耗
            return lowvalueService.updateStatus(vo);
        }else {
            return AjaxResult.error("填报数据有误");
        }
    }

    @Log(title = "预算填报【定制表】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FixedVo vo) {
        List<SysDept> depts = itemFieldDept(vo.getItemId(), getUserId());
        List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        vo.setDeptIds(deptIds);
        ReportingTask task =taskService.selectReportingTaskById(vo.getTaskId());
        if("pipeline".equals(vo.getTableName())){ //天然气管线
            TablePipeline pipeline = new TablePipeline();
            BeanUtils.copyBeanProp(pipeline, vo);
            List<TablePipeline> list = pipelineService.selectTablePipelineList(pipeline);
            ExcelUtil<TablePipeline> util = new ExcelUtil<TablePipeline>(TablePipeline.class);
            List<TablePipeline> dats = new ArrayList<>();
            if(vo.getExportType()!=null && vo.getExportType()==2){
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
            }else{
                dats = list;
            }
            util.exportExcel(response, dats, "预算填报【天然气管线】数据",task==null?null:task.getYear());
        }else if("station".equals(vo.getTableName())){ //场站工程
            TableStation station = new TableStation();
            BeanUtils.copyBeanProp(station, vo);
            List<TableStation> list = stationService.selectTableStationList(station);
            ExcelUtil<TableStation> util = new ExcelUtil<TableStation>(TableStation.class);
            List<TableStation> dats = new ArrayList<>();
            if(vo.getExportType()!=null && vo.getExportType()==2){
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
            }else{
                dats = list;
            }
            util.exportExcel(response, dats, "预算填报【场站工程】数据",task==null?null:task.getYear());
        }else if("housing".equals(vo.getTableName())){ //房屋建设、装修
            TableHousing housing = new TableHousing();
            BeanUtils.copyBeanProp(housing, vo);
            List<TableHousing> list = housingService.selectTableHousingList(housing);
            ExcelUtil<TableHousing> util = new ExcelUtil<TableHousing>(TableHousing.class);
            List<TableHousing> dats = new ArrayList<>();
            if(vo.getExportType()!=null && vo.getExportType()==2){
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
            }else{
                dats = list;
            }
            util.exportExcel(response, dats, "预算填报【房屋建设、装修】数据",task==null?null:task.getYear());
        }else if("information_system".equals(vo.getTableName())){ //信息系统建设
            TableInformationSystem informationSystem = new TableInformationSystem();
            BeanUtils.copyBeanProp(informationSystem, vo);
            List<TableInformationSystem> list = informationSystemService.selectTableInformationSystemList(informationSystem);
            ExcelUtil<TableInformationSystem> util = new ExcelUtil<TableInformationSystem>(TableInformationSystem.class);
            List<TableInformationSystem> dats = new ArrayList<>();
            if(vo.getExportType()!=null && vo.getExportType()==2){
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
            }else{
                dats = list;
            }
            util.exportExcel(response, dats, "预算填报【信息系统建设】数据",task==null?null:task.getYear());
        }else if("research".equals(vo.getTableName())){ //科研计划
            TableResearch research = new TableResearch();
            BeanUtils.copyBeanProp(research, vo);
            List<TableResearch> list = researchService.selectTableResearchList(research);
            ExcelUtil<TableResearch> util = new ExcelUtil<TableResearch>(TableResearch.class);
            List<TableResearch> dats = new ArrayList<>();
            if(vo.getExportType()!=null && vo.getExportType()==2){
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
            }else{
                dats = list;
            }
            util.exportExcel(response, dats, "预算填报【科研计划申报表】数据",task==null?null:task.getYear());
        }else if("meter".equals(vo.getTableName())){ //计量器
            TableMeter meter = new TableMeter();
            BeanUtils.copyBeanProp(meter, vo);
            List<TableMeter> list = meterService.selectTableMeterList(meter);
            ExcelUtil<TableMeter> util = new ExcelUtil<TableMeter>(TableMeter.class);
            List<TableMeter> dats = new ArrayList<>();
            if(vo.getExportType()!=null && vo.getExportType()==2){
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
            }else{
                dats = list;
            }
            util.exportExcel(response, dats, "预算填报【计量器具】数据",task==null?null:task.getYear());
        }else if("charity".equals(vo.getTableName())){ //慈善公益
            TableCharity charity = new TableCharity();
            BeanUtils.copyBeanProp(charity, vo);
            List<TableCharity> list = charityService.selectTableCharityList(charity);
            ExcelUtil<TableCharity> util = new ExcelUtil<TableCharity>(TableCharity.class);
            List<TableCharity> dats = new ArrayList<>();
            if(vo.getExportType()!=null && vo.getExportType()==2){
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
            }else{
                dats = list;
            }
            util.exportExcel(response, dats, "预算填报【慈善公益费用专项费用】数据",task==null?null:task.getYear());
        }else if("lowvalue".equals(vo.getTableName())){ //低值易耗
            TableLowvalue lowvalue = new TableLowvalue();
            BeanUtils.copyBeanProp(lowvalue, vo);
            List<TableLowvalue> list = lowvalueService.selectTableLowvalueList(lowvalue);
            ExcelUtil<TableLowvalue> util = new ExcelUtil<TableLowvalue>(TableLowvalue.class);
            List<TableLowvalue> dats = new ArrayList<>();
            if(vo.getExportType()!=null && vo.getExportType()==2){
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
            }else{
                dats = list;
            }
            util.exportExcel(response, dats, "预算填报【低值易耗品】数据",task==null?null:task.getYear());
        }else {
            logger.error("excel导出失败，导出数据有误");
        }
    }

    /**
     * 删除预算填报项目【定制表】
     */
    @Log(title = "预算填报【定制表】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody FixedVo vo) {
        if("pipeline".equals(vo.getTableName())){ //天然气管线
            return toAjax(pipelineService.deleteTablePipelineById(vo.getId()));
        }else if("station".equals(vo.getTableName())) { //场站工程
            return toAjax(stationService.deleteTableStationById(vo.getId()));
        }else if("housing".equals(vo.getTableName())){ //房屋建设、装修
            return toAjax(housingService.deleteTableHousingById(vo.getId()));
        }else if("information_system".equals(vo.getTableName())){ //信息系统建设
            return toAjax(informationSystemService.deleteTableInformationSystemById(vo.getId()));
        }else if("research".equals(vo.getTableName())){ //科研计划
            return toAjax(researchService.deleteTableResearchById(vo.getId()));
        }else if("meter".equals(vo.getTableName())){ //计量器
            return toAjax(meterService.deleteTableMeterById(vo.getId()));
        }else if("charity".equals(vo.getTableName())){ //慈善公益
            return toAjax(charityService.deleteTableCharityById(vo.getId()));
        }else if("lowvalue".equals(vo.getTableName())){ //低值易耗
            return toAjax(lowvalueService.deleteTableLowvalueById(vo.getId()));
        }else{
            return AjaxResult.error("填报数据有误");
        }
    }

    @GetMapping("/budgetSummaryList")
    public TableDataInfo budgetSummaryList(FixedVo vo) {
        if("pipeline".equals(vo.getTableName())){
            TablePipeline pipeline = new TablePipeline();
            BeanUtils.copyBeanProp(pipeline, vo);
            startPage();
            return getDataTable(pipelineService.budgetSummaryList(pipeline));
        }else if("station".equals(vo.getTableName())){ //场站工程
            TableStation station = new TableStation();
            BeanUtils.copyBeanProp(station, vo);
            startPage();
            return getDataTable(stationService.budgetSummaryList(station));
        }else if("housing".equals(vo.getTableName())){ //房屋建设、装修
            TableHousing housing = new TableHousing();
            BeanUtils.copyBeanProp(housing, vo);
            startPage();
            return getDataTable(housingService.budgetSummaryList(housing));
        }else if("information_system".equals(vo.getTableName())){ //信息系统建设
            TableInformationSystem informationSystem = new TableInformationSystem();
            BeanUtils.copyBeanProp(informationSystem, vo);
            startPage();
            return getDataTable(informationSystemService.budgetSummaryList(informationSystem));
        }else if("research".equals(vo.getTableName())){ //科研计划
            TableResearch research = new TableResearch();
            BeanUtils.copyBeanProp(research, vo);
            startPage();
            return getDataTable(researchService.budgetSummaryList(research));
        }else if("meter".equals(vo.getTableName())){ //计量器
            TableMeter meter = new TableMeter();
            BeanUtils.copyBeanProp(meter, vo);
            startPage();
            return getDataTable(meterService.budgetSummaryList(meter));
        }else if("charity".equals(vo.getTableName())){ //慈善公益
            TableCharity charity = new TableCharity();
            BeanUtils.copyBeanProp(charity, vo);
            startPage();
            return getDataTable(charityService.budgetSummaryList(charity));
        }else if("lowvalue".equals(vo.getTableName())){ //低值易耗
            TableLowvalue lowvalue = new TableLowvalue();
            BeanUtils.copyBeanProp(lowvalue, vo);
            startPage();
            return getDataTable(lowvalueService.budgetSummaryList(lowvalue));
        }else{
            return getDataTable(new ArrayList<>());
        }
    }


    public List<SysDept> itemFieldDept(Long itemId, Long userId) {
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
}
