package net.cmkj.budget.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.cmkj.budget.domain.*;
import net.cmkj.budget.service.*;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.core.domain.model.LoginUser;
import net.cmkj.common.utils.ExcelUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.StringUtils;
import net.cmkj.common.utils.uuid.ExportSheetUtil;
import net.cmkj.fixed.domain.TableWages;
import net.cmkj.fixed.domain.TableWagesVersion;
import net.cmkj.fixed.domain.WagesSubject;
import net.cmkj.fixed.service.ITableWagesService;
import net.cmkj.fixed.service.ITableWagesVersionService;
import net.cmkj.fixed.service.IWagesSubjectService;
import net.cmkj.system.mapper.SysDeptMapper;
import net.cmkj.system.service.ISysDeptService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 预算统计Service业务层处理
 *
 * @author cmkj
 * @date 2024-07-25
 */
@Service
@Slf4j
public class BudgetStatisticsServiceImpl implements IBudgetStatisticsService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IBudgetItemService itemService;

    @Autowired
    private IDynamicSqlService dynamicSqlService;

    @Autowired
    private IReportingTaskService taskService;

    @Autowired
    private ITableWagesService tableWagesService;

    @Autowired
    private ITableWagesVersionService tableWagesVersionService;

    @Autowired
    private IWagesSubjectService wagesSubjectService;

    @Autowired
    private IBudgetSubjectService budgetSubjectService;

    @Autowired
    private IActualCostsService actualCostsService;

    @Autowired
    private IReportingVersionService reportingVersionService;

    @Autowired
    private IReportedSubjectsService reportedSubjectsService;

    @Autowired
    private IBudgetOaAdjustmentService budgetOaAdjustmentService;

    @Override
    public Map<String, Object> budgetSummary(Integer type,Long taskId,Long deptId) {

        Map<String, Object> map = new HashMap<>();
        if(type==1){
            map =  budgetSummary1(taskId,1,null,false);
        }else if(type==2) {
            map =  budgetSummary2(taskId,1,null,false);
        }else if(type==3){
            map =  budgetSummary3(taskId,1,1,null,null,null);
        }else if(type==4) {
            map =  budgetSummary3(taskId,2,1,null,null,null);
        }else if(type==5){
            map =  budgetSummary3(taskId,3,1,null,null,null);
        }else if(type==6){
            map =  budgetSummary3(taskId,4,1,null,null,null);
        }
        return map;
    }

    public Map<String, Object> budgetSummaryOA(Integer type,Long taskId,Long deptId,Long versionId) {
        Map<String, Object> map = new HashMap<>();
        ReportingVersion version = reportingVersionService.selectReportingVersionById(versionId);
        if(version==null){
            return map;
        }
        if(type==1){
            map =  budgetSummary1(version.getTaskId(),2,versionId,true);
        }else if(type==2) {
            map =  budgetSummary2(version.getTaskId(),2,versionId,true);
        }
        return map;
    }
    @Override
    public Map<String, Object> budgetVersionSummary(Integer type, Long taskId,Long deptId,Long versionId) {
        Map<String, Object> map = new HashMap<>();
        if(type==1){
            map =  budgetSummary1(taskId,2,versionId,false);
        }else if(type==2){
            map =  budgetSummary2(taskId,2,versionId,false);
        }else if(type==3){
            map =  budgetSummary3(taskId,1,2,versionId,null,null);
        }else if(type==4){
            map =  budgetSummary3(taskId,2,2,versionId,null,null);
        }else if(type==5){
            map =  budgetSummary3(taskId,3,2,versionId,null,null);
        }else if(type==6){
            map =  budgetSummary3(taskId,4,2,versionId,null,null);
        }
        return map;
    }

    @Override
    public Map<String, Object> reportSummary(Long taskId, LoginUser user) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> titleDate = new ArrayList<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<SysDept> depts = new ArrayList<>();
        if(!SecurityUtils.isAdmin(user.getUserId())){
            SysDept dept = deptMapper.selectDeptById(user.getDeptId());
            if(dept.getLevel() == 1){
                SysDept queryDept = new SysDept();
                queryDept.setParentId(dept.getDeptId());
                depts = deptMapper.selectDeptList(queryDept);
            }else if(dept.getLevel() == 2){
                depts.add(dept);
            }
        }else{
            SysDept dept = new SysDept();
            dept.setLevel(2);
            depts = deptMapper.selectDeptList(dept);
        }
        List<Map<String, Object>> titleName = reportTatleList(user.getUserId(),depts);
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        List<BudgetItem> items = itemService.selectBudgetItemList(budgetItem);
        for (BudgetItem item : items) {
            Map<String, Object> data = new HashMap<>();
            List<Map<String, Object>> datas = new ArrayList<>();
            if(StringUtils.isEmpty(item.getType()) || item.getType().equals("1")){
                if (item.getTableName().equals("research")){
                    String sql = "SELECT dept_id ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total, SUM(subtotal_costs) as fyxj  FROM t_reporting_table_research WHERE  del_flag = '0' and (`status` = 3 or `status` = 5 or `status` = 6 ) and task_id = "+taskId+" GROUP BY dept_id ";
                    datas = dynamicSqlService.dynamicSelectListSql(sql);
                    createItemDate(item, titleName, data, datas, itemDate);
                }
            }else{
                if(StringUtils.isNotEmpty(item.getTableName())){
                    String sql = "";
                    if(item.getReportingType().equals("1")){ // 动态表
                        if (item.getTableName().equals("dynamic30")){ //表42-1 特殊处理
                            sql = "SELECT  dept_id ,  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field2), 2) as txf,  ROUND(SUM(e.field3), 2) as tcf, ROUND(SUM(e.field4), 2) as rlf, ROUND(SUM(e.field5), 2) as xlf, ROUND(SUM(e.field6), 2) as nsf, ROUND(SUM(COALESCE(e.field7,0)+COALESCE(e.field9,0)), 2) as qtf, ROUND(SUM(e.field8), 2) as bxf FROM `t_reporting_table_dynamic30` t LEFT JOIN t_reporting_table_dynamic30_extension e on  t.id = e.id where t.del_flag = '0' and (t.status = 3 or t.status = 5 or t.status = 6 )  and t.task_id = "+taskId+"  GROUP BY dept_id ";
                        }else if (item.getTableName().equals("dynamic61")){ // 表42-2 特殊处理
                            sql = "SELECT  dept_id, ROUND( SUM( t.budget ), 2 ) AS budget, ROUND( SUM( e.field9 ), 2 ) AS txf, ROUND( SUM( e.field8 ), 2 ) AS tcf, ROUND( SUM( e.field10 ), 2 ) AS rlf, ROUND( SUM( e.field11 ), 2 ) AS xlf, ROUND( SUM( e.field12 ), 2 ) AS nsf, ROUND( SUM( COALESCE(e.field13,0) + COALESCE(e.field15,0) ), 2 ) AS qtf, ROUND( SUM( e.field14 ), 2 ) AS bxf  FROM  `t_reporting_table_dynamic61` t  LEFT JOIN t_reporting_table_dynamic61_extension e ON t.id = e.id WHERE t.del_flag = '0'  and (t.status = 3 or t.status = 5 or t.status = 6 )  and t.task_id = "+taskId+" GROUP BY  dept_id";
                        }else{
                            sql = "SELECT dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_"+item.getTableName()+" WHERE  del_flag = '0'  and ( status = 3 or  status = 5 or  status = 6 )  and task_id = "+taskId+" GROUP BY dept_id ";
                        }
                        datas = dynamicSqlService.dynamicSelectListSql(sql);
                    }else if(item.getReportingType().equals("2")){ //定制表
                        if(item.getTableName().equals("pipeline")){
                            sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_pipeline WHERE  del_flag = '0' and ( status = 3 or  status = 5 or  status = 6 )   and task_id = "+taskId+" GROUP BY dept_id ";
                        }else if (item.getTableName().equals("station")){
                            sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_station WHERE  del_flag = '0' and ( status = 3 or  status = 5 or  status = 6 )  and task_id = "+taskId+" GROUP BY dept_id ";
                        }else if (item.getTableName().equals("research")){ //表9特殊处理
                            //sql = "SELECT dept_id ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total, SUM(subtotal_costs) as fyxj  FROM t_reporting_table_research WHERE  del_flag = '0' and task_id = "+taskId+" GROUP BY dept_id ";
                            continue;
                        }else if (item.getTableName().equals("meter")){ //表11特殊处理
                            sql = "SELECT reporting_type,project_type, dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_meter WHERE  del_flag = '0' and ( status = 3 or  status = 5 or  status = 6 )  and task_id = "+taskId+" GROUP BY reporting_type,project_type, dept_id ";
                        }else if (item.getTableName().equals("charity")){
                            sql = "SELECT dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_charity WHERE  del_flag = '0' and ( status = 3 or  status = 5 or  status = 6 )  and task_id = "+taskId+" GROUP BY dept_id ";
                        }else if (item.getTableName().equals("lowvalue")){
                            sql = "SELECT dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_lowvalue WHERE  del_flag = '0' and ( status = 3 or  status = 5 or  status = 6 )  and task_id = "+taskId+" GROUP BY dept_id ";
                        }else if (item.getTableName().equals("information_system")){
                            sql = "SELECT dept_id , ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2)AS total FROM t_reporting_table_information_system WHERE  del_flag = '0' and ( status = 3 or  status = 5 or  status = 6 )  and task_id = "+taskId+" GROUP BY dept_id ";
                        }else if (item.getTableName().equals("housing")){
                            sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_housing WHERE  del_flag = '0' and ( status = 3 or  status = 5 or  status = 6 )  and  task_id = "+taskId+" GROUP BY dept_id ";
                        }
                        datas = dynamicSqlService.dynamicSelectListSql(sql);
                    }else{ //表10工资表特殊处理
                        TableWages wages = new TableWages();
                        wages.setTaskId(taskId);
                        datas = tableWagesService.budgetSummary1(wages);
                    }
                    createItemDate(item, titleName, data, datas, itemDate);
                }
            }
        }
        createSubjectData(titleName, itemDate, titleDate,1);
        DecimalFormat df = new DecimalFormat("¥ #,##0.00;¥ -#,##0.00");
        for (Map<String, Object> data : titleDate) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (entry.getValue() instanceof BigDecimal) {
                    entry.setValue(df.format(entry.getValue()));
                }
            }
        }
        map.put("titleName", titleName);
        map.put("titleDate", titleDate);
        return map;
    }

    public void reportSummaryExport(HttpServletResponse response, Long taskId, LoginUser user){
        Map<String, Object> map = reportSummary(taskId, user);
        ExcelUtils.exportSingleHeaderExcel(response, (List<Map<String, Object>>) map.get("titleName"), (List<Map<String, Object>>) map.get("titleDate"));
    }

    public List<Map<String, Object>>  reportTatleList(Long userId, List<SysDept> depts){
        List<Map<String, Object>> titleName = new ArrayList<>();
        Map<String, Object> name = new HashMap<>();
        name.put("name","费用类型");
        name.put("key","fylx");
        name.put("value",0);
        titleName.add(name);

        name = new HashMap<>();
        name.put("name","费用名称");
        name.put("key","fymc");
        name.put("value",0);
        titleName.add(name);
        if(!SecurityUtils.isAdmin(userId)){
            depts.forEach(dept -> {
                Map<String, Object> deptName = new HashMap<>();
                deptName.put("name", dept.getDeptName());
                deptName.put("key", "dept"+dept.getDeptId());
                deptName.put("value",dept.getDeptId());
                titleName.add(deptName);
            });
        }else{
            depts.forEach(dept -> {
                Map<String, Object> deptName = new HashMap<>();
                deptName.put("name", dept.getParentName()+"-"+dept.getDeptName());
                deptName.put("key", "dept"+dept.getDeptId());
                deptName.put("value",dept.getDeptId());
                titleName.add(deptName);
            });
        }
        return titleName;
    }

    @Override
    public void budgetExport(HttpServletResponse response, Long taskId, Integer type,Long deptId,Long versionId)throws IOException {
        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String zipFileName = currentDate + ".zip";
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename="+zipFileName);
        String[] type1 = {"股份、供气汇总","区域汇总","其他公司汇总",};
        String[] type2 = {"销售费用预算","管理费用预算","研发费用预算","制造费用预算"};
        String[] type3 = {"股份、供气汇总","区域汇总","销售费用预算","管理费用预算","研发费用预算","制造费用预算"};
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(bos)) {
            int index = 1;
            if(type != null && type == 1){
                for (String name : type1) {
                    String fileName = name+ ".xlsx";
                    Map<String, Object> map = new HashMap<>();
                    if(index == 1){
                        map = budgetSummary1(taskId,2,versionId,false);
                    } else if (index == 2) {
                        map = budgetSummary2(taskId,2,versionId,false);
                    } else if (index == 3) {
                        map = budgetSummaryOrther(taskId,2,versionId);
                    }
                    byte[] excelData = ExcelUtils.generateSingleHeaderExcel((List<Map<String, Object>>) map.get("titleName"), (List<Map<String, Object>>) map.get("titleDate"));
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zos.putNextEntry(zipEntry);
                    zos.write(excelData);
                    zos.closeEntry();
                    index++;
                }
            }else if (type != null && type == 2){
                creatDeptExcel(type2,zos, taskId, versionId);
            }else if (type != null && type == 3){
                for (String name : type1) {
                    String fileName = name+ ".xlsx";
                    Map<String, Object> map = new HashMap<>();
                    if(index == 1){
                        map = budgetSummary1(taskId,2,versionId,false);
                    } else if (index == 2) {
                        map = budgetSummary2(taskId,2,versionId,false);
                    } else if (index == 3) {
                        map = budgetSummaryOrther(taskId,2,versionId);
                    }
                    byte[] excelData = ExcelUtils.generateSingleHeaderExcel((List<Map<String, Object>>) map.get("titleName"), (List<Map<String, Object>>) map.get("titleDate"));
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zos.putNextEntry(zipEntry);
                    zos.write(excelData);
                    zos.closeEntry();
                    index++;
                }
                creatDeptExcel(type2, zos, taskId, versionId);
            }else if (type != null && type == 4){
                creatDeptParentExcel(type2,zos, taskId, versionId);
            }
            zos.finish();
            zos.flush();
            try (OutputStream os = response.getOutputStream()) {
                os.write(bos.toByteArray());
                os.flush();
            }
        }
    }
    public void creatDeptExcel(String[] type,ZipOutputStream zos,Long taskId,Long versionId) throws IOException{
        List<Map<String, Object>> datas = new ArrayList<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<Map<String, Object>> titles =  creatTatleList(99,new ReportingTask());
        ReportingVersion version = reportingVersionService.selectReportingVersionById(versionId);
        if(version!=null && version.getStatus().equals("2")){
            extracted(taskId, 2, versionId, titles, itemDate, datas,false);
        }
        SysDept queryDept = new SysDept();
        queryDept.setLevel(2);
        queryDept.setDelFlag("0");
        List<SysDept> sysDepts = deptMapper.selectDeptList(queryDept);

        List<Map<String, Object>> titleName = new ArrayList<>();
        List<Map<String, Object>> titleData = new ArrayList<>();
        ReportingTask task = taskService.selectReportingTaskById(taskId);
        if(task!=null){
            titleName = creatTatleList(3,task);
        }

        BudgetSubject querySubject = new BudgetSubject();
        querySubject.setType("1");
        List<BudgetSubject> subjects = budgetSubjectService.selectBudgetSubjectList(querySubject);
        List<Long> summary1Ids = subjects.stream().map(BudgetSubject::getId).collect(Collectors.toList());

        querySubject = new BudgetSubject();
        querySubject.setType("2");
        subjects = budgetSubjectService.selectBudgetSubjectList(querySubject);
        List<Long> summary2Ids = subjects.stream().map(BudgetSubject::getId).collect(Collectors.toList());

        querySubject = new BudgetSubject();
        querySubject.setType("3");
        subjects = budgetSubjectService.selectBudgetSubjectList(querySubject);
        List<Long> summary3Ids = subjects.stream().map(BudgetSubject::getId).collect(Collectors.toList());

        querySubject = new BudgetSubject();
        querySubject.setType("4");
        subjects = budgetSubjectService.selectBudgetSubjectList(querySubject);
        List<Long> summary4Ids = subjects.stream().map(BudgetSubject::getId).collect(Collectors.toList());
        for (SysDept dept : sysDepts) {
            List<Map<String, Object>> infos = creatInfosByDept(datas, dept.getDeptId());
            int index = 1;
            for (String name : type) {
                Map<String, Object> map = new HashMap<>();
                String fileName = dept.getParentName()+"-"+dept.getDeptName()+"-"+name+ ".xlsx";
                if(version!=null && version.getStatus().equals("2")){
                    if(index == 1){
                        titleData = new ArrayList<>();
                        creatSubjectDataByDept(1, titleData, infos, dept.getDeptId(), summary1Ids, dept.getType());
                        //titleData = budgetSummary3_new(1,infos,dept.getDeptId(),dept.getType(),summary1Ids);
                    } else if (index == 2) {
                        titleData = new ArrayList<>();
                        creatSubjectDataByDept(2, titleData, infos, dept.getDeptId(), summary2Ids, dept.getType());
                        //titleData = budgetSummary3_new(2,infos,dept.getDeptId(),dept.getType(),summary2Ids);
                    } else if (index == 3) {
                        titleData = new ArrayList<>();
                        creatSubjectDataByDept(3, titleData, infos, dept.getDeptId(), summary3Ids, dept.getType());
                        //titleData = budgetSummary3_new(3,infos,dept.getDeptId(),dept.getType(),summary3Ids);
                    }else if (index == 4) {
                        titleData = new ArrayList<>();
                        creatSubjectDataByDept(4, titleData, infos, dept.getDeptId(), summary4Ids, dept.getType());
                        //titleData = budgetSummary3_new(4,infos,dept.getDeptId(),dept.getType(),summary4Ids);
                    }
                }
                byte[] excelData = ExcelUtils.generateMultiHeaderExcel(titleName, titleData);
                ZipEntry zipEntry = new ZipEntry(fileName);
                zos.putNextEntry(zipEntry);
                zos.write(excelData);
                zos.closeEntry();
                index++;
            }
        }
    }

    public void creatDeptParentExcel(String[] type,ZipOutputStream zos,Long taskId,Long versionId) throws IOException{
        List<Map<String, Object>> datas = new ArrayList<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<Map<String, Object>> titles =  creatTatleList(99,new ReportingTask());
        ReportingVersion version = reportingVersionService.selectReportingVersionById(versionId);
        if(version!=null && version.getStatus().equals("2")){
            extracted(taskId, 2, versionId, titles, itemDate, datas,false);
        }
        SysDept queryDept = new SysDept();
        queryDept.setDelFlag("0");
        List<SysDept> sysDepts = deptMapper.selectDeptList(queryDept);
        sysDepts.removeIf(sysDept -> sysDept.getParentId() == 0);
        List<SysDept> sysDeptsParent = deptService.buildDeptTree(sysDepts);

        List<Map<String, Object>> titleName = new ArrayList<>();
        List<Map<String, Object>> titleData = new ArrayList<>();
        ReportingTask task = taskService.selectReportingTaskById(taskId);
        if(task!=null){
            titleName = creatTatleList(3,task);
        }

        BudgetSubject querySubject = new BudgetSubject();
        querySubject.setType("3");
        List<BudgetSubject>  subjects = budgetSubjectService.selectBudgetSubjectList(querySubject);
        List<Long> summary3Ids = subjects.stream().map(BudgetSubject::getId).collect(Collectors.toList());

        for (SysDept dept : sysDeptsParent) {
            String fileName = dept.getParentName()+"-"+dept.getDeptName()+"-研发费用预算.xlsx";
            titleData = new ArrayList<>();
            if(version!=null && version.getStatus().equals("2")){
                List<SysDept> children = dept.getChildren();
                List<Map<String,Object>> childInfos = new ArrayList<>();
                for (SysDept child : children) {
                    Map<String,Object> childInfo = new HashMap<>();
                    List<Map<String, Object>> info = creatInfosByDeptParent(datas, child.getDeptId());
                    childInfo.put("info", info);
                    childInfo.put("deptId", child.getDeptId());
                    childInfo.put("deptType", child.getType());
                    childInfo.put("dept", child);
                    childInfos.add(childInfo);
                }
                creatSubjectDataByDeptParent(3, titleData, childInfos, summary3Ids, dept);
            }
            byte[] excelData = ExcelUtils.generateMultiHeaderExcel(titleName, titleData);
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);
            zos.write(excelData);
            zos.closeEntry();
        }
    }

    public void creatSubjectDataByDeptParent(int summaryType, List<Map<String, Object>> titleData, List<Map<String,Object>> childInfos,List<Long> summaryIds,SysDept dept) {
        ReportedSubjects query = new ReportedSubjects();
        query.setType(summaryType);
        List<ReportedSubjects> reportedSubjects = reportedSubjectsService.selectReportedSubjectsList(query);
        for (ReportedSubjects reportedSubject : reportedSubjects){
            String subjects = reportedSubject.getSubjects();

            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            BigDecimal allBudgetTotal = BigDecimal.ZERO; //今年合计
            BigDecimal allSnqm = BigDecimal.ZERO; //去年期末
            BigDecimal allQunian1 = BigDecimal.ZERO; //下一年合计
            BigDecimal allQunian2 = BigDecimal.ZERO; //下两年合计
            BigDecimal allQunian3 = BigDecimal.ZERO; //下三年合计
            BigDecimal allQunian4 = BigDecimal.ZERO; //下四年合计

            Map<String, Object> info = new HashMap<>();
            info.put("fylx", reportedSubject.getName());
            List<String> list = new ArrayList<>();
            if(StringUtils.isNotEmpty(subjects)){
                list = Arrays.asList(subjects.split(","));
            }
            List<SysDept> children = dept.getChildren();
            for (SysDept child : children) {
                List<Long> ids = new ArrayList<>();
                for (String sid : list) {
                    Long id = Long.valueOf(sid);
                    Long subjectId = summaryIds.contains(id) ? id : child.getType() == summaryType ? id : 0L;
                    if(subjectId!=null && subjectId.longValue() > 0){
                        ids.add(subjectId);
                    }
                }
                for (Map<String, Object> childInfo : childInfos) {
                    if(childInfo.get("deptId").equals(child.getDeptId())){
                        List<Map<String, Object>> infos = (List<Map<String, Object>>) childInfo.get("info");
                        if(ids!=null && ids.size()>0){
                            for (Map<String, Object> date : infos) {
                                if(ids.contains(date.get("subjectId"))){
                                    BigDecimal budgetTotal = date.get("budgetTotal")==null?BigDecimal.ZERO:(BigDecimal) date.get("budgetTotal");
                                    BigDecimal snqm = date.get("qnqm")==null?BigDecimal.ZERO:(BigDecimal) date.get("qnqm");
                                    BigDecimal growthRate = date.get("growthRate")==null?BigDecimal.ZERO:(BigDecimal) date.get("growthRate");
                                    growthRate = growthRate.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                                    if (budgetTotal.compareTo(BigDecimal.ZERO) >= 0) {
                                        budgetTotal = budgetTotal.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
                                    }
                                    if (snqm.compareTo(BigDecimal.ZERO) >= 0) {
                                        snqm = snqm.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
                                    }
                                    BigDecimal qunian1 = budgetTotal.multiply(BigDecimal.ONE.add(growthRate));
                                    BigDecimal qunian2 = qunian1.multiply(BigDecimal.ONE.add(growthRate));
                                    BigDecimal qunian3 = qunian2.multiply(BigDecimal.ONE.add(growthRate));
                                    BigDecimal qunian4 = qunian3.multiply(BigDecimal.ONE.add(growthRate));
                                    allBudgetTotal = allBudgetTotal.add(budgetTotal);
                                    allSnqm = allSnqm.add(snqm);
                                    allQunian1 = allQunian1.add(qunian1);
                                    allQunian2 = allQunian2.add(qunian2);
                                    allQunian3 = allQunian3.add(qunian3);
                                    allQunian4 = allQunian4.add(qunian4);
                                }
                            }
                        }
                    }
                }
            }
            info.put("snqm", allSnqm);
            info.put("quannian", allBudgetTotal);
            if(allBudgetTotal.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal oneQuarterlyRatio = reportedSubject.getOneQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getOneQuarterlyRatio();
                BigDecimal twoQuarterlyRatio = reportedSubject.getTwoQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getTwoQuarterlyRatio();
                BigDecimal threeQuarterlyRatio = reportedSubject.getThreeQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getThreeQuarterlyRatio();
                BigDecimal fourQuarterlyRatio = reportedSubject.getFourQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getFourQuarterlyRatio();

                if(oneQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && twoQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && threeQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && fourQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0){
                    BigDecimal one = allBudgetTotal.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
                    BigDecimal three = allBudgetTotal.divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP);
                    BigDecimal six = allBudgetTotal.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);

                    for (int i = 1; i <= 12; i++) {
                        info.put(i + "yue", one);
                    }
                    for (int i = 1; i <= 4; i++) {
                        info.put("d"+i+"j", three);
                    }

                    info.put("sbn", six);
                    info.put("xbn", six);
                }else{
                    BigDecimal jd1 = BigDecimal.ZERO;
                    BigDecimal jd2 = BigDecimal.ZERO;
                    BigDecimal jd3 = BigDecimal.ZERO;
                    BigDecimal jd4 = BigDecimal.ZERO;
                    if(oneQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                        jd1 = allBudgetTotal.multiply(new BigDecimal("0.01")).multiply(oneQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                        info.put("d1j", jd1);
                        BigDecimal month = jd1.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                        info.put("1yue", month);
                        info.put("2yue", month);
                        info.put("3yue", month);
                    }
                    if(twoQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                        jd2 = allBudgetTotal.multiply(new BigDecimal("0.01")).multiply(twoQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                        info.put("d2j", jd2);
                        BigDecimal month = jd2.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                        info.put("4yue", month);
                        info.put("5yue", month);
                        info.put("6yue", month);
                    }
                    if(threeQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                        jd3 = allBudgetTotal.multiply(new BigDecimal("0.01")).multiply(threeQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                        info.put("d3j", jd3);
                        BigDecimal month = jd3.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                        info.put("7yue", month);
                        info.put("8yue", month);
                        info.put("9yue", month);
                    }
                    if(fourQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                        jd4 = allBudgetTotal.multiply(new BigDecimal("0.01")).multiply(fourQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                        info.put("d4j", jd4);
                        BigDecimal month = jd4.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                        info.put("10yue", month);
                        info.put("11yue", month);
                        info.put("12yue", month);
                    }
                    info.put("sbn", jd1.add(jd2));
                    info.put("xbn", jd3.add(jd4));
                }

                if(allSnqm.compareTo(BigDecimal.ZERO) > 0){
                    info.put("qntb0", decimalFormat.format((allBudgetTotal.subtract(allSnqm)).divide(allSnqm, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
                }
                info.put("qunian1", allQunian1);
                info.put("qntb1", decimalFormat.format((allQunian1.subtract(allBudgetTotal)).divide(allBudgetTotal, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
                info.put("qunian2", allQunian2);
                info.put("qntb2", decimalFormat.format((allQunian2.subtract(allQunian1)).divide(allQunian1, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
                info.put("qunian3", allQunian3);
                info.put("qntb3", decimalFormat.format((allQunian3.subtract(allQunian2)).divide(allQunian2, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
                info.put("qunian4", allQunian4);
                info.put("qntb4", decimalFormat.format((allQunian4.subtract(allQunian3)).divide(allQunian3, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))) + "%");
            }else{
                info.put("sbn", BigDecimal.ZERO);
                info.put("xbn", BigDecimal.ZERO);
                for (int i = 1; i <= 4; i++) {
                    info.put("d"+i+"j", BigDecimal.ZERO);
                }
            }
            titleData.add(info);
        }
    }

    public Map<String, Object> budgetSummaryOrther(Long taskId, int type,Long versionId){
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> titleDate = new ArrayList<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<Map<String, Object>> titleName =  creatTatleList(0,new ReportingTask());
        ReportingVersion version = reportingVersionService.selectReportingVersionById(versionId);
        if(type==2 && (version==null || !version.getStatus().equals("2"))){
            map.put("titleName", titleName);
            map.put("titleDate", titleDate);
            return map;
        }
        extracted(taskId, type, versionId, titleName, itemDate, titleDate,false);
        map.put("titleName", titleName);
        map.put("titleDate", titleDate);
        return map;
    }


    public Map<String, Object> budgetSummary1(Long taskId, int type,Long versionId, boolean isOA){
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> titleDate = new ArrayList<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<Map<String, Object>> titleName =  creatTatleList(1,new ReportingTask());
        ReportingVersion version = reportingVersionService.selectReportingVersionById(versionId);
        if(type==2 && (version==null || !version.getStatus().equals("2"))){
            map.put("titleName", titleName);
            map.put("titleDate", titleDate);
            return map;
        }
        extracted(taskId, type, versionId, titleName, itemDate, titleDate,isOA);

        // 将titleDate中的BigDecimal值格式化为千分位展示
        DecimalFormat df = new DecimalFormat("¥ #,##0.00;¥ -#,##0.00");
        for (Map<String, Object> data : titleDate) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (entry.getValue() instanceof BigDecimal) {
                    entry.setValue(df.format(entry.getValue()));
                }
            }
        }
        map.put("titleName", titleName);
        map.put("titleDate", titleDate);
        return map;
    }

    private void extracted(Long taskId, int type, Long versionId, List<Map<String, Object>> titleName, List<Map<String, Object>> itemDate, List<Map<String, Object>> titleDate, boolean isOA) {
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        List<BudgetItem> items = itemService.selectBudgetItemList(budgetItem);
        for (BudgetItem item : items) {
            Map<String, Object> data = new HashMap<>();
            List<Map<String, Object>> datas = new ArrayList<>();
            if(StringUtils.isEmpty(item.getType()) || item.getType().equals("1")){  //排除资本性支出表
                if (item.getTableName().equals("research")){
                    String sql;
                    if(type ==1){
                        sql = "SELECT dept_id ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total, SUM(subtotal_costs) as fyxj  FROM t_reporting_table_research WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+ taskId +" GROUP BY dept_id ";
                    }else{
                        sql = "SELECT dept_id ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total ,SUM(subtotal_costs) as fyxj  FROM t_reporting_table_research_version WHERE version_id = "+ versionId +" and task_id = "+ taskId +" GROUP BY dept_id ";
                    }
                    datas = dynamicSqlService.dynamicSelectListSql(sql);
                    createItemDate(item, titleName, data, datas, itemDate);
                }
            }
            else{
                String tableName = item.getTableName();
                if(StringUtils.isNotEmpty(tableName)){
                    String sql = "";
                    if(type ==1){//任务汇总
                        if(item.getReportingType().equals("1")){ // 动态表
                            if (item.getTableName().equals("dynamic30")){ //表42-1 特殊处理
                                sql = "SELECT  dept_id ,  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field2), 2) as txf,  ROUND(SUM(e.field3), 2) as tcf, ROUND(SUM(e.field4), 2) as rlf, ROUND(SUM(e.field5), 2) as xlf, ROUND(SUM(e.field6), 2) as nsf, ROUND(SUM(COALESCE(e.field7,0)+COALESCE(e.field9,0)), 2) as qtf, ROUND(SUM(e.field8), 2) as bxf FROM `t_reporting_table_dynamic30` t LEFT JOIN t_reporting_table_dynamic30_extension e on  t.id = e.id where (t.`status` = 3 or t.`status` = 5 ) and t.del_flag = '0' and t.task_id = "+ taskId +"  GROUP BY dept_id ";
                            }else if (item.getTableName().equals("dynamic61")){ // 表42-2 特殊处理
                                sql = "SELECT  dept_id, ROUND( SUM( t.budget ), 2 ) AS budget, ROUND( SUM( e.field9 ), 2 ) AS txf, ROUND( SUM( e.field8 ), 2 ) AS tcf, ROUND( SUM( e.field10 ), 2 ) AS rlf, ROUND( SUM( e.field11 ), 2 ) AS xlf, ROUND( SUM( e.field12 ), 2 ) AS nsf, ROUND( SUM( COALESCE(e.field13,0) + COALESCE(e.field15,0) ), 2 ) AS qtf, ROUND( SUM( e.field14 ), 2 ) AS bxf  FROM  `t_reporting_table_dynamic61` t  LEFT JOIN t_reporting_table_dynamic61_extension e ON t.id = e.id WHERE (t.`status` = 3 or t.`status` = 5 ) and t.del_flag = '0' and t.task_id = "+ taskId +" GROUP BY  dept_id";
                            }else{
                                sql = "SELECT dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_"+tableName+" WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+ taskId +" GROUP BY dept_id ";
                            }
                            datas = dynamicSqlService.dynamicSelectListSql(sql);
                        }
                        else if(item.getReportingType().equals("2")){ //定制表
                            if(item.getTableName().equals("pipeline")){
                                sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_pipeline WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("station")){
                                sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_station WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("research")){ //表9特殊处理
                                //sql = "SELECT dept_id ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total, SUM(subtotal_costs) as fyxj  FROM t_reporting_table_research WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId+" GROUP BY dept_id ";
                                continue;
                            }else if (item.getTableName().equals("meter")){ //表11特殊处理
                                sql = "SELECT reporting_type,project_type, dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_meter WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+ taskId +" GROUP BY reporting_type,project_type, dept_id ";
                            }else if (item.getTableName().equals("charity")){
                                sql = "SELECT dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_charity WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("lowvalue")){
                                sql = "SELECT dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_lowvalue WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("information_system")){
                                sql = "SELECT dept_id ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total FROM t_reporting_table_information_system WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("housing")){
                                sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_housing WHERE (`status` = 3 or `status` = 5 )	and task_id = "+ taskId +" GROUP BY dept_id ";
                            }
                            datas = dynamicSqlService.dynamicSelectListSql(sql);
                        }
                        else{ //表10工资表特殊处理
                            TableWages wages = new TableWages();
                            wages.setTaskId(taskId);
                            datas = tableWagesService.budgetSummary1(wages);
                        }
                    }else if(type ==2){
                        //版本汇总
                        if(item.getReportingType().equals("1")){ // 动态表
                            if (item.getTableName().equals("dynamic30")){ //表42-1 特殊处理
                                sql = "SELECT  dept_id ,  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field2), 2) as txf,  ROUND(SUM(e.field3), 2) as tcf, ROUND(SUM(e.field4), 2) as rlf, ROUND(SUM(e.field5), 2) as xlf, ROUND(SUM(e.field6), 2) as nsf, ROUND(SUM(COALESCE(e.field7,0)+COALESCE(e.field9,0)), 2) as qtf, ROUND(SUM(e.field8), 2) as bxf FROM `t_reporting_table_dynamic30_version` t LEFT JOIN t_reporting_table_dynamic30_extension_version e on  t.budget_id = e.budget_id  and t.version_id = e.version_id    where t.version_id = "+ versionId +"  GROUP BY t.dept_id ";
                            }else if (item.getTableName().equals("dynamic61")){ // 表42-2 特殊处理
                                sql = "SELECT  dept_id, ROUND(SUM(t.budget),2) AS budget, ROUND( SUM( e.field9 ), 2 ) AS txf, ROUND( SUM( e.field8 ), 2 ) AS tcf, ROUND( SUM( e.field10 ), 2 ) AS rlf, ROUND( SUM( e.field11 ), 2 ) AS xlf, ROUND( SUM( e.field12 ), 2 ) AS nsf, ROUND( SUM( COALESCE(e.field13,0) + COALESCE(e.field15,0) ), 2 ) AS qtf, ROUND( SUM( e.field14 ), 2 ) AS bxf  FROM  `t_reporting_table_dynamic61_version` t  LEFT JOIN t_reporting_table_dynamic61_extension_version e ON t.budget_id = e.budget_id  and t.version_id = e.version_id  WHERE  t.version_id = "+ versionId +" GROUP BY  t.dept_id";
                            }else{
                                sql = "SELECT dept_id , ROUND(SUM(budget), 2) AS total FROM t_reporting_table_"+tableName+"_version WHERE version_id = "+ versionId +" GROUP BY dept_id ";
                            }
                            datas = dynamicSqlService.dynamicSelectListSql(sql);
                        }
                        else if(item.getReportingType().equals("2")){ //定制表
                            if(item.getTableName().equals("pipeline")){
                                sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_pipeline_version WHERE version_id = "+ versionId +" and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("station")){
                                sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_station_version WHERE version_id = "+ versionId +" and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("research")){//表9特殊处理
                                //sql = "SELECT dept_id ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total ,SUM(subtotal_costs) as fyxj  FROM t_reporting_table_research_version WHERE version_id = "+versionId+" and task_id = "+taskId+" GROUP BY dept_id ";
                                continue;
                            }else if (item.getTableName().equals("meter")){ //表11特殊处理
                                sql = "SELECT reporting_type,project_type,  dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_meter_version WHERE version_id = "+ versionId +" and task_id = "+ taskId +" GROUP BY reporting_type,project_type, dept_id ";
                            }else if (item.getTableName().equals("charity")){
                                sql = "SELECT dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_charity_version WHERE version_id = "+ versionId +" and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("lowvalue")){
                                sql = "SELECT dept_id ,  ROUND(SUM(budget), 2) AS total FROM t_reporting_table_lowvalue_version WHERE version_id = "+ versionId +" and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("information_system")){
                                sql = "SELECT dept_id ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total FROM t_reporting_table_information_system_version WHERE version_id = "+ versionId +" and task_id = "+ taskId +" GROUP BY dept_id ";
                            }else if (item.getTableName().equals("housing")){
                                sql = "SELECT dept_id ,  ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_housing_version WHERE version_id = "+ versionId +"	and task_id = "+ taskId +" GROUP BY dept_id ";
                            }
                            datas = dynamicSqlService.dynamicSelectListSql(sql);
                        }
                        else{
                            TableWagesVersion wages = new TableWagesVersion();
                            wages.setVersionId(versionId);
                            wages.setTaskId(taskId);
                            datas = tableWagesVersionService.selectSummaryList(wages);
                        }
                    }
                    createItemDate(item, titleName, data, datas, itemDate);
                }
            }
        }
        createSubjectData(titleName, itemDate, titleDate, versionId ==null?1:2);
        if(isOA){
            createSubjectDataOA(taskId,titleDate);
        }
        createSubjectTotalData(titleName, titleDate);
    }


    public Map<String, Object> budgetSummary2(Long taskId, int type,Long versionId, boolean isOA){
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<Map<String, Object>> titleDate = new ArrayList<>();
        List<Map<String, Object>> titleName = creatTatleList(2, new ReportingTask());// type = 1
        ReportingVersion version = reportingVersionService.selectReportingVersionById(versionId);
        if(type==2 && (version==null || !version.getStatus().equals("2"))){
            map.put("titleName", titleName);
            map.put("titleDate", titleDate);
            return map;
        }
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        List<BudgetItem> items = itemService.selectBudgetItemList(budgetItem);
        for (BudgetItem item : items) {
            Map<String, Object> data = new HashMap<>();
            List<Map<String, Object>> datas = new ArrayList<>();
            if(StringUtils.isEmpty(item.getType()) || item.getType().equals("1")){ // 排除资本性支出表
                //1	表1-1天然气管线
                //1	表1-2场站工程
                //1	表2房屋建设、装修
                //1	表3办公用具购置
                //1	表4车辆购置
                //1	表5信息系统建设
                //1	表6信息设备购置
                //1	表7生产设备购置
                //1	表8计量器具购置
                //1	表9科研计划申报表 research
                if (item.getTableName().equals("research")) {
                    String sql = "";
                    if(type==1) {//任务汇总
                        sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.intangible_assets)+SUM(t.fixed_assets), 2) as total , SUM(t.subtotal_costs) as fyxj FROM t_reporting_table_research t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 )  and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                    }else if(type==2){
                        sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.intangible_assets)+SUM(t.fixed_assets), 2) as total , SUM(t.subtotal_costs) as fyxj FROM t_reporting_table_research_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+" and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                    }
                    datas = dynamicSqlService.dynamicSelectListSql(sql);
                    createItemDate(item, titleName, data, datas, itemDate);
                }
            }else{
                String tableName = item.getTableName();
                if(StringUtils.isNotEmpty(tableName)){
                    String sql = "";
                    if(type==1){
                        //任务汇总
                        if(item.getReportingType().equals("1")){ // 动态表预算金额
                            if (item.getTableName().equals("dynamic30")){ //表42-1 特殊处理
                                sql = "SELECT  d.parent_id as dept_id ,  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field2), 2) as txf,  ROUND(SUM(e.field3), 2) as tcf, ROUND(SUM(e.field4), 2) as rlf, ROUND(SUM(e.field5), 2) as xlf, ROUND(SUM(e.field6), 2) as nsf, ROUND(SUM(COALESCE(e.field7,0)+COALESCE(e.field9,0)), 2) as qtf, ROUND(SUM(e.field8), 2) as bxf FROM `t_reporting_table_dynamic30` t LEFT JOIN sys_dept d on t.dept_id = d.dept_id  LEFT JOIN t_reporting_table_dynamic30_extension e on  t.id = e.id where d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 ) and t.del_flag = '0' and t.task_id = "+taskId+"  GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("dynamic61")){ // 表42-2 特殊处理
                                sql = "SELECT  d.parent_id as dept_id, ROUND( SUM( t.budget ), 2 ) AS budget, ROUND( SUM( e.field9 ), 2 ) AS txf, ROUND( SUM( e.field8 ), 2 ) AS tcf, ROUND( SUM( e.field10 ), 2 ) AS rlf, ROUND( SUM( e.field11 ), 2 ) AS xlf, ROUND( SUM( e.field12 ), 2 ) AS nsf, ROUND( SUM( COALESCE(e.field13,0) + COALESCE(e.field15,0) ), 2 ) AS qtf, ROUND( SUM( e.field14 ), 2 ) AS bxf  FROM  `t_reporting_table_dynamic61` t LEFT JOIN sys_dept d on t.dept_id = d.dept_id   LEFT JOIN t_reporting_table_dynamic61_extension e ON t.id = e.id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 ) and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY  d.parent_id";
                            }else{
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.budget), 2) as total FROM t_reporting_table_"+tableName+" t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 ) and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }
                            datas = dynamicSqlService.dynamicSelectListSql(sql);
                        }
                        else if(item.getReportingType().equals("2")){ // 固定表预算金额
                            if(item.getTableName().equals("pipeline")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.expected_settlement), 2) as total FROM t_reporting_table_pipeline t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 )  and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("station")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.expected_settlement), 2) as total FROM t_reporting_table_station t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 )  and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("research")){ //表9特殊处理
                                continue;
                            }else if (item.getTableName().equals("meter")){ //表11特殊处理
                                sql = "SELECT  t.reporting_type,t.project_type,d.parent_id as dept_id, ROUND(SUM(t.budget), 2) as total FROM t_reporting_table_meter t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 )  and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY t.reporting_type,t.project_type,d.parent_id ";
                            }else if (item.getTableName().equals("charity")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.budget), 2) as total FROM t_reporting_table_charity t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 )  and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("lowvalue")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.budget), 2) as total FROM t_reporting_table_lowvalue t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 )  and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("information_system")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.intangible_assets)+SUM(t.fixed_assets), 2) as total FROM t_reporting_table_information_system t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and  (t.`status` = 3 or t.`status` = 5 )  and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("housing")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.expected_settlement), 2) as total FROM t_reporting_table_housing t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 )  and t.del_flag = '0' and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }
                            datas = dynamicSqlService.dynamicSelectListSql(sql);
                        }
                        else if(item.getReportingType().equals("3")){// 工资
                            TableWages wages = new TableWages();
                            wages.setTaskId(taskId);
                            datas = tableWagesService.budgetSummary2(wages);
                        }
                    }
                    else if(type==2){
                        //版本汇总
                        if(item.getReportingType().equals("1")){
                            if (item.getTableName().equals("dynamic30")){ //表42-1 特殊处理
                                sql = "SELECT  d.parent_id as dept_id ,  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field2), 2) as txf,  ROUND(SUM(e.field3), 2) as tcf, ROUND(SUM(e.field4), 2) as rlf, ROUND(SUM(e.field5), 2) as xlf, ROUND(SUM(e.field6), 2) as nsf, ROUND(SUM(COALESCE(e.field7,0)+COALESCE(e.field9,0)), 2) as qtf, ROUND(SUM(e.field8), 2) as bxf FROM `t_reporting_table_dynamic30_version` t LEFT JOIN sys_dept d on t.dept_id = d.dept_id  LEFT JOIN t_reporting_table_dynamic30_extension_version e on  t.budget_id = e.budget_id  and t.version_id = e.version_id  where d.parent_id != 0  and t.version_id = "+versionId+"  GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("dynamic61")){ // 表42-2 特殊处理
                                sql = "SELECT  d.parent_id as dept_id, ROUND( SUM( t.budget ), 2 ) AS budget, ROUND( SUM( e.field9 ), 2 ) AS txf, ROUND( SUM( e.field8 ), 2 ) AS tcf, ROUND( SUM( e.field10 ), 2 ) AS rlf, ROUND( SUM( e.field11 ), 2 ) AS xlf, ROUND( SUM( e.field12 ), 2 ) AS nsf, ROUND( SUM( COALESCE(e.field13,0) + COALESCE(e.field15,0) ), 2 ) AS qtf, ROUND( SUM( e.field14 ), 2 ) AS bxf  FROM  `t_reporting_table_dynamic61_version` t LEFT JOIN sys_dept d on t.dept_id = d.dept_id   LEFT JOIN t_reporting_table_dynamic61_extension_version e ON  t.budget_id = e.budget_id  and t.version_id = e.version_id  WHERE d.parent_id != 0  and t.version_id = "+versionId+" GROUP BY  d.parent_id";
                            }else{
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.budget), 2) as total FROM t_reporting_table_"+tableName+"_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+"  GROUP BY d.parent_id ";
                            }
                            datas = dynamicSqlService.dynamicSelectListSql(sql);
                        }else if(item.getReportingType().equals("2")){
                            if(item.getTableName().equals("pipeline")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.expected_settlement), 2) as total FROM t_reporting_table_pipeline_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+" and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("station")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.expected_settlement), 2) as total FROM t_reporting_table_station_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+" and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("research")){ //表9特殊处理
                                continue;
                            }else if (item.getTableName().equals("meter")){
                                sql = "SELECT  t.reporting_type,t.project_type,d.parent_id as dept_id, ROUND(SUM(t.budget), 2) as total FROM t_reporting_table_meter_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+" and t.task_id = "+taskId+" GROUP BY t.reporting_type,t.project_type,d.parent_id ";
                            }else if (item.getTableName().equals("charity")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.budget), 2) as total FROM t_reporting_table_charity_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+" and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("lowvalue")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.budget), 2) as total FROM t_reporting_table_lowvalue_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+" and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("information_system")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.intangible_assets)+SUM(t.fixed_assets), 2) as total FROM t_reporting_table_information_system_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+" and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }else if (item.getTableName().equals("housing")){
                                sql = "SELECT  d.parent_id as dept_id, ROUND(SUM(t.expected_settlement), 2) as total FROM t_reporting_table_housing_version t LEFT JOIN sys_dept d on t.dept_id = d.dept_id WHERE d.parent_id != 0 and t.version_id = "+versionId+" and t.task_id = "+taskId+" GROUP BY d.parent_id ";
                            }
                            datas = dynamicSqlService.dynamicSelectListSql(sql);
                        }else if(item.getReportingType().equals("3")){
                            TableWagesVersion wages = new TableWagesVersion();
                            wages.setVersionId(versionId);
                            wages.setTaskId(taskId);
                            datas = tableWagesVersionService.budgetSummary2(wages);
                        }
                    }
                    if(item.getReportingType().equals("1") || item.getReportingType().equals("2")){ // 固定表预算和动态表预算
                        if(item.getTableName().equals("meter")){ //表11计量器
                            long[] subjectIds = {44, 46, 49, 61, 68};
                            if (datas != null && datas.size() > 0){
                                for (long subjectId : subjectIds) {
                                    data = new HashMap<>();
                                    for (Map<String, Object> title : titleName) {
                                        if (title.get("key").equals("fylx")) {
                                            data.put("fylx", "");
                                        } else if (title.get("key").equals("fymc")) {
                                            data.put("fymc", "");
                                            data.put("subjectId", subjectId);
                                        } else {
                                            data.put(title.get("key").toString(), BigDecimal.ZERO);
                                            if (title.get("value")!=null) {
                                                for (Map<String, Object> budgetData : datas) {
                                                    if (budgetData.get("reporting_type") == null || budgetData.get("project_type") == null) {
                                                        continue;
                                                    }
                                                    if(subjectId ==44){
                                                        if(!budgetData.get("reporting_type").toString().equals("2") || !budgetData.get("project_type").toString().equals("2")){
                                                            continue;
                                                        }
                                                    }else if(subjectId ==46){
                                                        if(!budgetData.get("reporting_type").toString().equals("2") || !budgetData.get("project_type").toString().equals("3")){
                                                            continue;
                                                        }
                                                    } else if(subjectId ==49){
                                                        if(!budgetData.get("reporting_type").toString().equals("2") || !budgetData.get("project_type").toString().equals("1")){
                                                            continue;
                                                        }
                                                    }else if(subjectId ==61){
                                                        if(!budgetData.get("reporting_type").toString().equals("1") || budgetData.get("project_type").toString().equals("1")){
                                                            continue;
                                                        }
                                                    }else if(subjectId ==68){
                                                        if(!budgetData.get("reporting_type").toString().equals("1") || !budgetData.get("project_type").toString().equals("1")){
                                                            continue;
                                                        }
                                                    }
                                                    if(budgetData.get("dept_id")!=null && budgetData.get("dept_id").equals(title.get("value"))){
                                                        BigDecimal dateValue = new BigDecimal(data.get(title.get("key"))==null?"0":data.get(title.get("key")).toString());
                                                        BigDecimal budgetValue = new BigDecimal(budgetData.get("total")==null?"0":budgetData.get("total").toString());
                                                        data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    itemDate.add(data);
                                }
                            }
                        }
                        else if(item.getTableName().equals("dynamic30") || item.getTableName().equals("dynamic61")){ //表42-1、42-2
                            long[] subjectIds = {32, 33, 34, 35, 36, 37, 38};
                            if (datas != null && datas.size() > 0){
                                for (long subjectId : subjectIds) {
                                    data = new HashMap<>();
                                    for (Map<String, Object> title : titleName) {
                                        if (title.get("key").equals("fylx")) {
                                            data.put("fylx", "");
                                        } else if (title.get("key").equals("fymc")) {
                                            data.put("fymc", "");
                                            data.put("subjectId", subjectId);
                                        } else {
                                            data.put(title.get("key").toString(), BigDecimal.ZERO);
                                            if (title.get("value")!=null) {
                                                for (Map<String, Object> budgetData : datas) {
                                                    if(budgetData.get("dept_id")!=null && budgetData.get("dept_id").equals(title.get("value"))){
                                                        BigDecimal dateValue = new BigDecimal(data.get(title.get("key"))==null?"0":data.get(title.get("key")).toString());
                                                        if(subjectId ==32){ //通行费
                                                            BigDecimal budgetValue = new BigDecimal(budgetData.get("txf")==null?"0":budgetData.get("txf").toString());
                                                            data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                                        }else if(subjectId ==33){ //车位费
                                                            BigDecimal budgetValue = new BigDecimal(budgetData.get("tcf")==null?"0":budgetData.get("tcf").toString());
                                                            data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                                        } else if(subjectId ==34){//加油（气、电）费
                                                            BigDecimal budgetValue = new BigDecimal(budgetData.get("rlf")==null?"0":budgetData.get("rlf").toString());
                                                            data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                                        }else if(subjectId ==35){//维修费
                                                            BigDecimal budgetValue = new BigDecimal(budgetData.get("xlf")==null?"0":budgetData.get("xlf").toString());
                                                            data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                                        }else if(subjectId ==36){//年审费
                                                            BigDecimal budgetValue = new BigDecimal(budgetData.get("nsf")==null?"0":budgetData.get("nsf").toString());
                                                            data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                                        }else if(subjectId ==37){//保险费
                                                            BigDecimal budgetValue = new BigDecimal(budgetData.get("bxf")==null?"0":budgetData.get("bxf").toString());
                                                            data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                                        }else if(subjectId ==38){//其他
                                                            BigDecimal budgetValue = new BigDecimal(budgetData.get("qtf")==null?"0":budgetData.get("qtf").toString());
                                                            data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    itemDate.add(data);
                                }
                            }
                        }
                        else{
                            for (Map<String, Object> title : titleName) {
                                if (title.get("key").equals("fylx")) {
                                    data.put(title.get("key").toString(), item.getItemType());
                                } else if (title.get("key").equals("fymc")) {
                                    data.put(title.get("key").toString(), item.getSubjectName());
                                    data.put("subjectId", item.getSubjectId());
                                } else {
                                    data.put(title.get("key").toString(), BigDecimal.ZERO);
                                    if (title.get("value")!=null) {
                                        for (Map<String, Object> budgetData : datas) {
                                            if(budgetData.get("dept_id")!=null && budgetData.get("dept_id").equals(title.get("value"))){
                                                BigDecimal dateValue = new BigDecimal(data.get(title.get("key"))==null?"0":data.get(title.get("key")).toString());
                                                BigDecimal budgetValue = new BigDecimal(budgetData.get("total")==null?"0":budgetData.get("total").toString());
                                                data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                            }
                                        }
                                    }
                                }
                            }
                            itemDate.add(data);
                        }
                    }else if(item.getReportingType().equals("3")){  // 工资预算
                        List<WagesSubject> wagesSubjects = wagesSubjectService.selectWagesSubjectList(new WagesSubject());
                        for (WagesSubject wagesSubject : wagesSubjects) {
                            data = new HashMap<>();
                            data.put("fylx", item.getItemType());
                            data.put("fymc", wagesSubject.getTypeName());
                            data.put("subjectId", wagesSubject.getType());
                            for (Map<String, Object> title : titleName) {
                                if (!title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                    data.put(title.get("key").toString(), BigDecimal.ZERO);
                                    if(title.get("value")!=null) {
                                        for (Map<String, Object> tableWages : datas) {
                                            if(tableWages.get("deptParentId").toString().equals(title.get("value").toString())){
                                                data.put(title.get("key").toString(), Optional.ofNullable(tableWages.get(wagesSubject.getField())).orElse(0));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            itemDate.add(data);
                        }
                    }
                }
            }
        }
        for (Map<String, Object> objectMap : itemDate) {
            BigDecimal all = BigDecimal.ZERO;
            for (Map<String, Object> title : titleName) {
                if (!title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                    if (title.get("value") != null) {
                        Object value = title.get("key");
                        BigDecimal key;
                        if (objectMap.get(value) instanceof Number) {
                            key = new BigDecimal(objectMap.get(value).toString());
                        } else {
                            key = BigDecimal.ZERO;
                        }
                        all = all.add(key);
                    }
                }
            }
            objectMap.put("dept100&101", new BigDecimal(String.valueOf(objectMap.get("dept100")==null?0:objectMap.get("dept100"))).add(new BigDecimal(String.valueOf(objectMap.get("dept101")==null?0:objectMap.get("dept101")))));
            objectMap.put("deptAll", all);
        }
        createSubjectData(titleName, itemDate, titleDate, versionId==null?1:2);
        if(isOA){
            createParentDeptSubjectDataOA(taskId,titleDate);
        }
        createSubjectTotalData(titleName,titleDate);

        // 将titleDate中的BigDecimal值格式化为千分位展示
        DecimalFormat df = new DecimalFormat("¥ #,##0.00;¥ -#,##0.00");
        for (Map<String, Object> data : titleDate) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (entry.getValue() instanceof BigDecimal) {
                    entry.setValue(df.format(entry.getValue()));
                }
            }
        }
        map.put("titleName", titleName);
        map.put("titleDate", titleDate);
        return map;
    }


    public Map<String, Object> budgetSummary3(Long taskId,int summaryType, int type,Long versionId,Long parentDeptId,Long deptId) {
        List<Map<String, Object>> datas = new ArrayList<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<Map<String, Object>> titles =  creatTatleList(99,new ReportingTask());
        if(type==2){
            ReportingVersion version = reportingVersionService.selectReportingVersionById(versionId);
            if(version==null || !version.getStatus().equals("2")){
                versionId = 0L;
            }
        }
        extracted(taskId, type, versionId, titles, itemDate, datas,false);

        List<Map<String, Object>> titleName = new ArrayList<>();
        ReportingTask task = taskService.selectReportingTaskById(taskId);
        if(task!=null){
            titleName = creatTatleList(3,task);
        }
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> titleData = new ArrayList<>();

        BudgetSubject querySubject = new BudgetSubject();
        querySubject.setType(summaryType+"");
        List<BudgetSubject> subjects = budgetSubjectService.selectBudgetSubjectList(querySubject);
        List<Long> summaryIds = subjects.stream().map(BudgetSubject::getId).collect(Collectors.toList());

        SysDept queryDept = new SysDept();
        queryDept.setLevel(2);
        queryDept.setType(summaryType);
        queryDept.setDelFlag("0");
        List<SysDept> sysDepts = deptMapper.selectDeptList(queryDept);
        List<Long> deptIds = sysDepts.stream().map(SysDept::getDeptId).collect(Collectors.toList());

        titleData = creatSubjectDataAll(summaryType,datas,summaryIds,deptIds);
        // 将titleDate中的BigDecimal值格式化为千分位展示
        DecimalFormat df = new DecimalFormat("¥ #,##0.00;¥ -#,##0.00");
        for (Map<String, Object> data : titleData) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (entry.getValue() instanceof BigDecimal) {
                    entry.setValue(df.format(entry.getValue()));
                }
            }
        }
        map.put("titleName", titleName);
        map.put("titleDate", titleData);
        return map;
    }


    public void creatSubjectDataByDept(int summaryType, List<Map<String, Object>> titleData, List<Map<String, Object>> datas,Long deptId,List<Long> summaryIds,Integer deptType) {
        ReportedSubjects query = new ReportedSubjects();
        query.setType(summaryType);
        List<ReportedSubjects> reportedSubjects = reportedSubjectsService.selectReportedSubjectsList(query);
        for (ReportedSubjects reportedSubject : reportedSubjects){
            String subjects = reportedSubject.getSubjects();
            Map<String, Object> data = new HashMap<>();
            data.put("fylx", reportedSubject.getName());
            List<String> list = new ArrayList<>();
            if(StringUtils.isNotEmpty(subjects)){
                list = Arrays.asList(subjects.split(","));
            }
            List<Long> ids = new ArrayList<>();
            for (String sid : list) {
                Long id = Long.valueOf(sid);
                Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
                if(subjectId!=null && subjectId.longValue() > 0){
                    ids.add(subjectId);
                }
            }
            getInfo(datas, ids, data,reportedSubject);
            titleData.add(data);
        }
    }

    public List<Map<String, Object>> creatSubjectDataAll(int summaryType, List<Map<String, Object>> datas,List<Long> summaryIds,List<Long>  deptIds) {
        List<Map<String, Object>> titleData = new ArrayList<>();
        ReportedSubjects query = new ReportedSubjects();
        query.setType(summaryType);
        List<ReportedSubjects> reportedSubjects = reportedSubjectsService.selectReportedSubjectsList(query);
        for (ReportedSubjects reportedSubject : reportedSubjects){
            Map<String, Object> data = new HashMap<>();
            String subjects = reportedSubject.getSubjects();
            List<String> list = new ArrayList<>();
            if(StringUtils.isNotEmpty(subjects)){
                list = Arrays.asList(subjects.split(","));
            }
            BigDecimal qnqm = BigDecimal.ZERO; //去年期末
            BigDecimal oneMonth = BigDecimal.ZERO;
            BigDecimal threeMonth = BigDecimal.ZERO;
            BigDecimal sixMonth = BigDecimal.ZERO;
            BigDecimal budgetTotal = BigDecimal.ZERO; //今年合计
            BigDecimal qunian1 = BigDecimal.ZERO; //下一年合计
            BigDecimal qunian2 = BigDecimal.ZERO; //下两年合计
            BigDecimal qunian3 = BigDecimal.ZERO; //下三年合计
            BigDecimal qunian4 = BigDecimal.ZERO; //下四年合计

            for (String sid : list) {
                Long subjectId = Long.valueOf(sid);
                BigDecimal[] result  = new BigDecimal[]{BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO};
                if(summaryIds.contains(subjectId)){
                    //科目属于预算分类，统计所有
                    result = creatInfosAll(subjectId, datas, null);
                }else{
                    //按部门统计
                    result = creatInfosAll(subjectId,datas,deptIds);
                }
                qnqm = qnqm.add(result[0]);
                budgetTotal = budgetTotal.add(result[1]);
                qunian1 = qunian1.add(result[2]);
                qunian2 = qunian2.add(result[3]);
                qunian3 = qunian3.add(result[4]);
                qunian4 = qunian4.add(result[5]);
            }
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            data.put("fylx", reportedSubject.getName());
            data.put("snqm", qnqm);
            data.put("quannian", budgetTotal);
            BigDecimal oneQuarterlyRatio = reportedSubject.getOneQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getOneQuarterlyRatio();
            BigDecimal twoQuarterlyRatio = reportedSubject.getTwoQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getTwoQuarterlyRatio();
            BigDecimal threeQuarterlyRatio = reportedSubject.getThreeQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getThreeQuarterlyRatio();
            BigDecimal fourQuarterlyRatio = reportedSubject.getFourQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getFourQuarterlyRatio();
            if(oneQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && twoQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && threeQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && fourQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0){
                oneMonth = budgetTotal.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
                threeMonth = budgetTotal.divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP);
                sixMonth = budgetTotal.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);

                for (int i = 1; i <= 12; i++) {
                    data.put(i + "yue", oneMonth);
                }
                for (int i = 1; i <= 4; i++) {
                    data.put("d"+i+"j", threeMonth);
                }

                data.put("sbn", sixMonth);
                data.put("xbn", sixMonth);
            }else{
                BigDecimal jd1 = BigDecimal.ZERO;
                BigDecimal jd2 = BigDecimal.ZERO;
                BigDecimal jd3 = BigDecimal.ZERO;
                BigDecimal jd4 = BigDecimal.ZERO;
                if(oneQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                    jd1 = budgetTotal.multiply(new BigDecimal("0.01")).multiply(oneQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                    data.put("d1j", jd1);
                    BigDecimal month = jd1.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                    data.put("1yue", month);
                    data.put("2yue", month);
                    data.put("3yue", month);
                }
                if(twoQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                    jd2 = budgetTotal.multiply(new BigDecimal("0.01")).multiply(twoQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                    data.put("d2j", jd2);
                    BigDecimal month = jd2.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                    data.put("4yue", month);
                    data.put("5yue", month);
                    data.put("6yue", month);
                }
                if(threeQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                    jd3 = budgetTotal.multiply(new BigDecimal("0.01")).multiply(threeQuarterlyRatio);
                    data.put("d3j", jd3);
                    BigDecimal month = jd3.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                    data.put("7yue", month);
                    data.put("8yue", month);
                    data.put("9yue", month);
                }
                if(fourQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                    jd4 = budgetTotal.multiply(new BigDecimal("0.01")).multiply(fourQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                    data.put("d4j", jd4);
                    BigDecimal month = jd4.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                    data.put("10yue", month);
                    data.put("11yue", month);
                    data.put("12yue", month);
                }

                data.put("sbn", jd1.add(jd2));
                data.put("xbn", jd3.add(jd4));
            }

            data.put("qntb0", qnqm.compareTo(BigDecimal.ZERO) == 0?"-" : decimalFormat.format((budgetTotal.subtract(qnqm)).divide(qnqm, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            data.put("qunian1", qunian1);
            data.put("qntb1", qunian1.compareTo(BigDecimal.ZERO) == 0?"-" :decimalFormat.format((qunian1.subtract(budgetTotal)).divide(budgetTotal, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            data.put("qunian2", qunian2);
            data.put("qntb2", qunian2.compareTo(BigDecimal.ZERO) == 0?"-" :decimalFormat.format((qunian2.subtract(qunian1)).divide(qunian1, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            data.put("qunian3", qunian3);
            data.put("qntb3", qunian3.compareTo(BigDecimal.ZERO) == 0?"-" :decimalFormat.format((qunian3.subtract(qunian2)).divide(qunian2, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            data.put("qunian4", qunian4);
            data.put("qntb4", qunian4.compareTo(BigDecimal.ZERO) == 0?"-" :decimalFormat.format((qunian4.subtract(qunian3)).divide(qunian3, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))) + "%");
            titleData.add(data);
        }
        return titleData;
    }


    public List<Map<String, Object>> creatInfosByDept(List<Map<String, Object>> dates,Long deptId) {
        List<Map<String, Object>> infos = new ArrayList<>();
        for (Map<String, Object> date : dates) {
            BigDecimal budgetTotal = BigDecimal.ZERO; //今年合计
            BigDecimal qnqm = BigDecimal.ZERO; //去年期末
            BigDecimal growthRate = BigDecimal.ZERO; //增长率
            BigDecimal budgetRatio = BigDecimal.ZERO; //预算比例
            if (date.get("dept" + deptId) != null) {
                budgetTotal = (BigDecimal) date.get("dept" + deptId);
            }
            if (date.get("subjectId") != null) {
                BudgetSubject subject = budgetSubjectService.selectBudgetSubjectById(Long.parseLong(date.get("subjectId").toString()));
                if (subject != null) {
                    if (subject.getGrowthRate() != null) {
                        growthRate = subject.getGrowthRate();
                    }
                    if (subject.getBudgetRatio() != null) {
                        budgetRatio = subject.getBudgetRatio();
                    }
                }
            }
            if (budgetRatio.compareTo(BigDecimal.ZERO) >= 0) {
                budgetRatio = budgetRatio.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP); // 保留四位小数，避免精度丢失
                qnqm = budgetTotal.multiply(budgetRatio).setScale(2, RoundingMode.HALF_UP); // 最终结果保留两位小数
            }
            Map<String, Object> info = new HashMap<>();
            info.put("deptId", deptId);
            info.put("subjectId", date.get("subjectId"));
            info.put("qnqm", qnqm);
            info.put("budgetTotal", budgetTotal);
            info.put("growthRate", growthRate);
            info.put("budgetRatio", budgetRatio);
            infos.add(info);
        }
        return infos;
    }
    public List<Map<String, Object>> creatInfosByDeptParent(List<Map<String, Object>> dates,Long deptId) {
        List<Map<String, Object>> infos = new ArrayList<>();
        for (Map<String, Object> date : dates) {
            BigDecimal budgetTotal = BigDecimal.ZERO; //今年合计
            BigDecimal qnqm = BigDecimal.ZERO; //去年期末
            BigDecimal growthRate = BigDecimal.ZERO; //增长率
            BigDecimal budgetRatio = BigDecimal.ZERO; //预算比例
            if (date.get("dept" + deptId) != null) {
                budgetTotal = (BigDecimal) date.get("dept" + deptId);
            }
            if (date.get("subjectId") != null) {
                BudgetSubject subject = budgetSubjectService.selectBudgetSubjectById(Long.parseLong(date.get("subjectId").toString()));
                if (subject != null) {
                    if (subject.getGrowthRate() != null) {
                        growthRate = subject.getGrowthRate();
                    }
                    if (subject.getBudgetRatio() != null) {
                        budgetRatio = subject.getBudgetRatio();
                    }
                }
            }
            if (budgetRatio.compareTo(BigDecimal.ZERO) >= 0) {
                budgetRatio = budgetRatio.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP); // 保留四位小数，避免精度丢失
                qnqm = budgetTotal.multiply(budgetRatio).setScale(2, RoundingMode.HALF_UP); // 最终结果保留两位小数
            }
            Map<String, Object> info = new HashMap<>();
            info.put("deptId", deptId);
            info.put("subjectId", date.get("subjectId"));
            info.put("qnqm", qnqm);
            info.put("budgetTotal", budgetTotal);
            info.put("growthRate", growthRate);
            info.put("budgetRatio", budgetRatio);
            infos.add(info);
        }
        return infos;
    }

    public  BigDecimal[] creatInfosAll(Long subjectId, List<Map<String, Object>> dates,List<Long> deptIds) {
        BigDecimal growthRate = BigDecimal.ZERO; //增长率
        BigDecimal budgetRatio = BigDecimal.ZERO; //预算比例
        BigDecimal budgetTotal = BigDecimal.ZERO; //今年合计
        BigDecimal qnqm = BigDecimal.ZERO;
        BigDecimal qunian1 = BigDecimal.ZERO;
        BigDecimal qunian2 = BigDecimal.ZERO;
        BigDecimal qunian3 = BigDecimal.ZERO;
        BigDecimal qunian4 = BigDecimal.ZERO;
        if (subjectId != null) {
            BudgetSubject subject = budgetSubjectService.selectBudgetSubjectById(subjectId);
            if (subject != null) {
                if (subject.getGrowthRate() != null) {
                    growthRate = subject.getGrowthRate();
                    growthRate = growthRate.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                }
                if (subject.getBudgetRatio() != null) {
                    budgetRatio = subject.getBudgetRatio();
                }
            }
        }
        for (Map<String, Object> date : dates) {
            if (date.get("subjectId") != null && date.get("subjectId").equals(subjectId)) {
                if(deptIds != null && deptIds.size() > 0){
                    for (Long deptId : deptIds) {
                        budgetTotal = budgetTotal.add(date.get("dept" + deptId)==null?BigDecimal.ZERO:(BigDecimal) date.get("dept" + deptId));
                    }
                }else{
                    for (String key : date.keySet()) {
                        if (key.startsWith("dept")) {
                            budgetTotal = budgetTotal.add(date.get(key)==null?BigDecimal.ZERO:(BigDecimal) date.get(key));
                        }
                    }
                }
            }
        }
        budgetTotal = budgetTotal.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
        qnqm = budgetTotal.multiply(budgetRatio).setScale(2, RoundingMode.HALF_UP); // 最终结果保留两位小数
        qunian1 = budgetTotal.multiply(BigDecimal.ONE.add(growthRate));
        qunian2 = qunian1.multiply(BigDecimal.ONE.add(growthRate));
        qunian3 = qunian2.multiply(BigDecimal.ONE.add(growthRate));
        qunian4 = qunian3.multiply(BigDecimal.ONE.add(growthRate));
        return new BigDecimal[]{qnqm,budgetTotal,qunian1,qunian2,qunian3,qunian4};
    }

    public void getInfo(List<Map<String, Object>> infos,List<Long> ids,Map<String, Object> info,ReportedSubjects reportedSubject){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        BigDecimal allBudgetTotal = BigDecimal.ZERO; //今年合计
        BigDecimal allSnqm = BigDecimal.ZERO; //去年期末
        BigDecimal allQunian1 = BigDecimal.ZERO; //下一年合计
        BigDecimal allQunian2 = BigDecimal.ZERO; //下两年合计
        BigDecimal allQunian3 = BigDecimal.ZERO; //下三年合计
        BigDecimal allQunian4 = BigDecimal.ZERO; //下四年合计
        if(ids!=null && ids.size()>0){
            for (Map<String, Object> date : infos) {
                if(ids.contains(date.get("subjectId"))){
                    BigDecimal budgetTotal = date.get("budgetTotal")==null?BigDecimal.ZERO:(BigDecimal) date.get("budgetTotal");
                    BigDecimal snqm = date.get("qnqm")==null?BigDecimal.ZERO:(BigDecimal) date.get("qnqm");
                    BigDecimal growthRate = date.get("growthRate")==null?BigDecimal.ZERO:(BigDecimal) date.get("growthRate");
                    growthRate = growthRate.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                    if (budgetTotal.compareTo(BigDecimal.ZERO) >= 0) {
                        budgetTotal = budgetTotal.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
                    }
                    if (snqm.compareTo(BigDecimal.ZERO) >= 0) {
                        snqm = snqm.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
                    }
                    BigDecimal qunian1 = budgetTotal.multiply(BigDecimal.ONE.add(growthRate));
                    BigDecimal qunian2 = qunian1.multiply(BigDecimal.ONE.add(growthRate));
                    BigDecimal qunian3 = qunian2.multiply(BigDecimal.ONE.add(growthRate));
                    BigDecimal qunian4 = qunian3.multiply(BigDecimal.ONE.add(growthRate));
                    allBudgetTotal = allBudgetTotal.add(budgetTotal);
                    allSnqm = allSnqm.add(snqm);
                    allQunian1 = allQunian1.add(qunian1);
                    allQunian2 = allQunian2.add(qunian2);
                    allQunian3 = allQunian3.add(qunian3);
                    allQunian4 = allQunian4.add(qunian4);
                }
            }
        }

        info.put("snqm", allSnqm);
        info.put("quannian", allBudgetTotal);
        if(allBudgetTotal.compareTo(BigDecimal.ZERO) > 0){

            BigDecimal oneQuarterlyRatio = reportedSubject.getOneQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getOneQuarterlyRatio();
            BigDecimal twoQuarterlyRatio = reportedSubject.getTwoQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getTwoQuarterlyRatio();
            BigDecimal threeQuarterlyRatio = reportedSubject.getThreeQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getThreeQuarterlyRatio();
            BigDecimal fourQuarterlyRatio = reportedSubject.getFourQuarterlyRatio()==null?BigDecimal.ZERO:reportedSubject.getFourQuarterlyRatio();

            if(oneQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && twoQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && threeQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0 && fourQuarterlyRatio.compareTo(BigDecimal.ZERO) == 0){
                BigDecimal one = allBudgetTotal.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
                BigDecimal three = allBudgetTotal.divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP);
                BigDecimal six = allBudgetTotal.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);

                for (int i = 1; i <= 12; i++) {
                    info.put(i + "yue", one);
                }
                for (int i = 1; i <= 4; i++) {
                    info.put("d"+i+"j", three);
                }
                info.put("sbn", six);
                info.put("xbn", six);
            }else{
                BigDecimal jd1 = BigDecimal.ZERO;
                BigDecimal jd2 = BigDecimal.ZERO;
                BigDecimal jd3 = BigDecimal.ZERO;
                BigDecimal jd4 = BigDecimal.ZERO;

                if(oneQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                    jd1 = allBudgetTotal.multiply(new BigDecimal("0.01")).multiply(oneQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                    info.put("d1j", jd1);
                    BigDecimal month = jd1.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                    info.put("1yue", month);
                    info.put("2yue", month);
                    info.put("3yue", month);
                }
                if(twoQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                    jd2 = allBudgetTotal.multiply(new BigDecimal("0.01")).multiply(twoQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                    info.put("d2j", jd2);
                    BigDecimal month = jd2.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                    info.put("4yue", month);
                    info.put("5yue", month);
                    info.put("6yue", month);
                }
                if(threeQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                    jd3 = allBudgetTotal.multiply(new BigDecimal("0.01")).multiply(threeQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                    info.put("d3j", jd3);
                    BigDecimal month = jd3.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                    info.put("7yue", month);
                    info.put("8yue", month);
                    info.put("9yue", month);
                }
                if(fourQuarterlyRatio.compareTo(BigDecimal.ZERO) > 0){
                    jd4 = allBudgetTotal.multiply(new BigDecimal("0.01")).multiply(fourQuarterlyRatio).setScale(2, RoundingMode.HALF_UP);
                    info.put("d4j", jd4);
                    BigDecimal month = jd4.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
                    info.put("10yue", month);
                    info.put("11yue", month);
                    info.put("12yue", month);
                }

                info.put("sbn", jd1.add(jd2));
                info.put("xbn", jd3.add(jd4));
            }

            if(allSnqm.compareTo(BigDecimal.ZERO) > 0){
                info.put("qntb0", decimalFormat.format((allBudgetTotal.subtract(allSnqm)).divide(allSnqm, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            }
            info.put("qunian1", allQunian1);
            info.put("qntb1", decimalFormat.format((allQunian1.subtract(allBudgetTotal)).divide(allBudgetTotal, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            info.put("qunian2", allQunian2);
            info.put("qntb2", decimalFormat.format((allQunian2.subtract(allQunian1)).divide(allQunian1, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            info.put("qunian3", allQunian3);
            info.put("qntb3", decimalFormat.format((allQunian3.subtract(allQunian2)).divide(allQunian2, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            info.put("qunian4", allQunian4);
            info.put("qntb4", decimalFormat.format((allQunian4.subtract(allQunian3)).divide(allQunian3, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))) + "%");
        }else{
            info.put("sbn", BigDecimal.ZERO);
            info.put("xbn", BigDecimal.ZERO);
            for (int i = 1; i <= 4; i++) {
                info.put("d"+i+"j", BigDecimal.ZERO);
            }
        }
    }

    public void creatSubjectName(int summaryType, List<Map<String, Object>> titleData, List<Map<String, Object>> datas,Long deptId,List<Long> summaryIds,Integer deptType) {
        Map<String, Object> data = new HashMap<>();
        data.put("fylx", "员工成本");
        data.put("index", 1);
        BigDecimal sum = BigDecimal.ZERO;
        List<Long> list = Arrays.asList(2L, 3L, 4L, 5L, 11L, 12L, 13L, 14L, 15L);
        List<Long> ids = new ArrayList<>();
        for (Long id : list) {
            Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
            if(subjectId!=null && subjectId.longValue() > 0){
                ids.add(subjectId);
            }
        }
        sum = getSum(datas, ids, deptId);
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-工资");
        data.put("index", 2);
        sum = summaryIds.contains(2L)?getSum(datas, Arrays.asList(2L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(2L), deptId):BigDecimal.ZERO;
        creatInfo(data, 2L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-劳务费");
        data.put("index", 3);
        sum =summaryIds.contains(3L)?getSum(datas, Arrays.asList(3L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(3L), deptId):BigDecimal.ZERO;
        creatInfo(data, 3L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-奖金");
        data.put("index", 4);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-津贴及补贴");
        data.put("index", 5);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-福利费");
        data.put("index", 6);
        sum =summaryIds.contains(4L)?getSum(datas, Arrays.asList(4L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(4L), deptId):BigDecimal.ZERO;
        creatInfo(data, 4L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-社会保险费");
        data.put("index", 7);
        sum =summaryIds.contains(5L)?getSum(datas, Arrays.asList(5L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(5L), deptId):BigDecimal.ZERO;
        creatInfo(data, 5L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-职工公积金");
        data.put("index", 8);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-职工强积金");
        data.put("index", 9);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-认购股权费用");
        data.put("index", 10);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-职工住房公积金");
        data.put("index", 11);
        sum =summaryIds.contains(11L)?getSum(datas, Arrays.asList(11L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(11L), deptId):BigDecimal.ZERO;
        creatInfo(data, 11L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-职工教育经费");
        data.put("index", 12);
        sum =summaryIds.contains(12L)?getSum(datas, Arrays.asList(12L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(12L), deptId):BigDecimal.ZERO;
        creatInfo(data, 12L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-工会经费");
        data.put("index", 13);
        sum =summaryIds.contains(13L)?getSum(datas, Arrays.asList(13L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(13L), deptId):BigDecimal.ZERO;
        creatInfo(data, 13L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-培训费");
        data.put("index", 14);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        if(summaryType==2 || summaryType==3){
            data = new HashMap<>();
            data.put("fylx", "    员工成本-董事薪酬");
            data.put("index", 15);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        data = new HashMap<>();
        data.put("fylx", "    员工成本-劳动保护费");
        data.put("index", 16);
        sum =summaryIds.contains(14L)?getSum(datas, Arrays.asList(14L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(14L), deptId):BigDecimal.ZERO;
        creatInfo(data, 14L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-辞退福利");
        data.put("index", 17);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    员工成本-企业年金");
        data.put("index", 18);
        sum =summaryIds.contains(15L)?getSum(datas, Arrays.asList(15L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(15L), deptId):BigDecimal.ZERO;
        creatInfo(data, 15L, sum,BigDecimal.ZERO);
        titleData.add(data);

        if(summaryType==2 || summaryType==3){
            data = new HashMap<>();
            data.put("fylx", "    员工成本-加班费");
            data.put("index", 19);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        if(summaryType==2){
            data = new HashMap<>();
            data.put("fylx", "    员工成本-离退休员工福利");
            data.put("index", 20);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        data = new HashMap<>();
        data.put("fylx", "    员工成本-其他");
        data.put("index", 21);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        if(summaryType==1 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "文具印刷费");
            data.put("index", 22);
            sum =summaryIds.contains(21L)?getSum(datas, Arrays.asList(21L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(21L), deptId):BigDecimal.ZERO;
            creatInfo(data, 21L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        if(summaryType==1 || summaryType==2 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "邮电费");
            data.put("index", 23);
            sum =summaryIds.contains(24L)?getSum(datas, Arrays.asList(24L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(24L), deptId):BigDecimal.ZERO;
            creatInfo(data, 24L, sum,BigDecimal.ZERO);
            titleData.add(data);

            list = Arrays.asList(26L, 27L);
            ids = new ArrayList<>();
            for (Long id : list) {
                Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
                if(subjectId!=null && subjectId.longValue() > 0){
                    ids.add(subjectId);
                }
            }
            data = new HashMap<>();
            data.put("fylx", "差旅费");
            data.put("index", 24);
            sum =getSum(datas, ids, deptId);
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    住宿费");
            data.put("index", 25);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    交通费");
            data.put("index", 26);
            sum =summaryIds.contains(27L)?getSum(datas, Arrays.asList(27L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(27L), deptId):BigDecimal.ZERO;
            creatInfo(data, 27L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    其他");
            data.put("index", 27);
            sum =summaryIds.contains(26L)?getSum(datas, Arrays.asList(26L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(26L), deptId):BigDecimal.ZERO;
            creatInfo(data, 26L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "图书资料费");
            data.put("index", 28);
            sum =summaryIds.contains(22L)?getSum(datas, Arrays.asList(22L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(22L), deptId):BigDecimal.ZERO;
            creatInfo(data, 22L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "会议费");
            data.put("index", 29);
            sum =summaryIds.contains(28L)?getSum(datas, Arrays.asList(28L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(28L), deptId):BigDecimal.ZERO;
            creatInfo(data, 28L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "通讯费");
            data.put("index", 30);
            sum =summaryIds.contains(23L)?getSum(datas, Arrays.asList(23L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(23L), deptId):BigDecimal.ZERO;
            creatInfo(data, 23L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    网络费");
            data.put("index", 31);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    专线费");
            data.put("index", 32);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    手机、电话费");
            data.put("index", 33);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    其他");
            data.put("index", 34);
            sum =summaryIds.contains(23L)?getSum(datas, Arrays.asList(23L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(23L), deptId):BigDecimal.ZERO;
            creatInfo(data, 23L, sum,BigDecimal.ZERO);
            titleData.add(data);

            list = Arrays.asList(29L, 30L, 71L);
            ids = new ArrayList<>();
            for (Long id : list) {
                Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
                if(subjectId!=null && subjectId.longValue() > 0){
                    ids.add(subjectId);
                }
            }
            data = new HashMap<>();
            data.put("fylx", "能源费");
            data.put("index", 35);
            sum =getSum(datas, ids, deptId);
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    水费");
            data.put("index", 36);
            sum =summaryIds.contains(29L)?getSum(datas, Arrays.asList(29L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(29L), deptId):BigDecimal.ZERO;
            creatInfo(data, 29L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    电费");
            data.put("index", 37);
            sum =summaryIds.contains(30L)?getSum(datas, Arrays.asList(30L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(30L), deptId):BigDecimal.ZERO;
            creatInfo(data, 30L, sum,BigDecimal.ZERO);
            titleData.add(data);


            data = new HashMap<>();
            data.put("fylx", "    气费");
            data.put("index", 38);
            sum =summaryIds.contains(71L)?getSum(datas, Arrays.asList(71L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(71L), deptId):BigDecimal.ZERO;
            creatInfo(data, 71L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "租赁费用");
            data.put("index", 39);
            sum =summaryIds.contains(83L)?getSum(datas, Arrays.asList(83L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(83L), deptId):BigDecimal.ZERO;
            creatInfo(data, 83L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "运输费用");
            data.put("index", 40);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            list = Arrays.asList(32L, 33L, 34L, 35L, 36L, 37L, 38L);
            ids = new ArrayList<>();
            for (Long id : list) {
                Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
                if(subjectId!=null && subjectId.longValue() > 0){
                    ids.add(subjectId);
                }
            }
            data = new HashMap<>();
            data.put("fylx", "车辆费用");
            data.put("index", 41);
            sum =getSum(datas, ids, deptId);
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    市内交通费");
            data.put("index", 42);
            sum =summaryIds.contains(33L)?getSum(datas, Arrays.asList(33L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(33L), deptId):BigDecimal.ZERO;
            creatInfo(data, 33L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    路桥费");
            data.put("index", 43);
            sum =summaryIds.contains(32L)?getSum(datas, Arrays.asList(32L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(32L), deptId):BigDecimal.ZERO;
            creatInfo(data, 32L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    汽车保险费");
            data.put("index", 44);
            sum =summaryIds.contains(37L)?getSum(datas, Arrays.asList(37L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(37L), deptId):BigDecimal.ZERO;
            creatInfo(data, 37L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    维修费");
            data.put("index", 45);
            sum =summaryIds.contains(35L)?getSum(datas, Arrays.asList(35L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(35L), deptId):BigDecimal.ZERO;
            creatInfo(data, 35L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    加油（气）费");
            data.put("index", 46);
            sum =summaryIds.contains(34L)?getSum(datas, Arrays.asList(34L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(34L), deptId):BigDecimal.ZERO;
            creatInfo(data, 34L, sum,BigDecimal.ZERO);
            titleData.add(data);

            list = Arrays.asList(36L, 38L);
            ids = new ArrayList<>();
            for (Long id : list) {
                Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
                if(subjectId!=null && subjectId.longValue() > 0){
                    ids.add(subjectId);
                }
            }
            data = new HashMap<>();
            data.put("fylx", "    其他");
            data.put("index", 47);
            sum =getSum(datas, ids, deptId);
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "押运费");
            data.put("index", 48);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "仓储费用");
            data.put("index", 49);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            if(summaryType==1 || summaryType==4){
                data = new HashMap<>();
                data.put("fylx", "装卸驳运费");
                data.put("index", 50);
                sum =summaryIds.contains(72L)?getSum(datas, Arrays.asList(72L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(72L), deptId):BigDecimal.ZERO;
                creatInfo(data, 72L, sum,BigDecimal.ZERO);
                titleData.add(data);
            }

            data = new HashMap<>();
            data.put("fylx", "耗用品费用");
            data.put("index", 51);
            sum =summaryIds.contains(39L)?getSum(datas, Arrays.asList(39L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(39L), deptId):BigDecimal.ZERO;
            creatInfo(data, 39L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "电脑耗材");
            data.put("index", 52);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "软件服务费");
            data.put("index", 53);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "证照费");
            data.put("index", 54);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "业务招待费");
            data.put("index", 55);
            sum =summaryIds.contains(40L)?getSum(datas, Arrays.asList(40L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(40L), deptId):BigDecimal.ZERO;
            creatInfo(data, 40L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "代理费");
            data.put("index", 56);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("index", 57);
            data.put("fylx", "交易手续费");
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("index", 58);
            data.put("fylx", "行政收费");
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "保险费");
            data.put("index", 59);
            sum =summaryIds.contains(41L)?getSum(datas, Arrays.asList(41L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(41L), deptId):BigDecimal.ZERO;
            creatInfo(data, 41L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==3){
            data = new HashMap<>();
            data.put("fylx", "直接投入");
            data.put("index", 60);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    材料费");
            data.put("index", 61);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    加工费");
            data.put("index", 62);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        data = new HashMap<>();
        data.put("fylx", "折旧");
        data.put("index", 63);
        sum =summaryIds.contains(81L)?getSum(datas, Arrays.asList(81L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(81L), deptId):BigDecimal.ZERO;
        creatInfo(data, 81L, sum,BigDecimal.ZERO);
        titleData.add(data);

        if(summaryType==1 || summaryType==3 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "    折旧-管网");
            data.put("index", 64);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    折旧-厂站");
            data.put("index", 65);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    折旧-大型运输设备");
            data.put("index", 66);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    折旧-生产性车辆");
            data.put("index", 67);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==2){
            data = new HashMap<>();
            data.put("fylx", "    折旧-办公楼");
            data.put("index", 68);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        data = new HashMap<>();
        data.put("fylx", "    折旧-其他");
        data.put("index", 69);
        sum =summaryIds.contains(81L)?getSum(datas, Arrays.asList(81L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(81L), deptId):BigDecimal.ZERO;
        creatInfo(data, 81L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "无形资产摊销");
        data.put("index", 70);
        sum =summaryIds.contains(82L)?getSum(datas, Arrays.asList(82L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(82L), deptId):BigDecimal.ZERO;
        creatInfo(data, 82L, sum,BigDecimal.ZERO);
        titleData.add(data);

        if(summaryType==1 || summaryType==3 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "    特许权使用费");
            data.put("index", 71);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    专利权摊销");
            data.put("index", 72);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    商标权摊销");
            data.put("index", 73);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        data = new HashMap<>();
        data.put("fylx", "    土地使用权摊销");
        data.put("index", 74);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    软件摊销");
        data.put("index", 75);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", "    其他摊销");
        data.put("index", 76);
        sum =summaryIds.contains(82L)?getSum(datas, Arrays.asList(82L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(82L), deptId):BigDecimal.ZERO;
        creatInfo(data, 82L, sum,BigDecimal.ZERO);
        titleData.add(data);

        data = new HashMap<>();
        data.put("fylx", summaryType==3?"    长期待摊费用摊销":"长期待摊费用摊销");
        data.put("index", 77);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        if(summaryType==1 || summaryType==2 || summaryType==4){
            if(summaryType==1 || summaryType==4){
                list = Arrays.asList(44L, 46L, 48L, 75L);
            }else if(summaryType==2){
                list = Arrays.asList(44L, 48L, 75L);
            }
            ids = new ArrayList<>();
            for (Long id : list) {
                Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
                if(subjectId!=null && subjectId.longValue() > 0){
                    ids.add(subjectId);
                }
            }
            data = new HashMap<>();
            data.put("fylx", "维修及保养费");
            data.put("index", 78);
            sum =getSum(datas, ids, deptId);
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    建筑物及其附属物");
            data.put("index", 79);
            sum =summaryIds.contains(75L)?getSum(datas, Arrays.asList(75L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(75L), deptId):BigDecimal.ZERO;
            creatInfo(data, 75L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    设备");
            data.put("index", 80);
            sum =summaryIds.contains(44L)?getSum(datas, Arrays.asList(44L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(44L), deptId):BigDecimal.ZERO;
            creatInfo(data, 44L, sum,BigDecimal.ZERO);
            titleData.add(data);

            if(summaryType==1 || summaryType==4){
                data = new HashMap<>();
                data.put("fylx", "    燃气管网");
                data.put("index", 81);
                sum =summaryIds.contains(46L)?getSum(datas, Arrays.asList(46L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(46L), deptId):BigDecimal.ZERO;
                creatInfo(data, 46L, sum,BigDecimal.ZERO);
                titleData.add(data);
            }
            if(summaryType==2){
                data = new HashMap<>();
                data.put("fylx", "    材料");
                data.put("index", 82);
                sum = BigDecimal.ZERO;
                creatInfo(data, null, sum,BigDecimal.ZERO);
                titleData.add(data);
            }
            data = new HashMap<>();
            data.put("fylx", "    燃气流量表");
            data.put("index", 83);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    其他");
            data.put("index", 84);
            sum =summaryIds.contains(48L)?getSum(datas, Arrays.asList(48L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(48L), deptId):BigDecimal.ZERO;
            creatInfo(data, 48L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "试验检验费");
            data.put("index", 85);
            sum =summaryIds.contains(49L)?getSum(datas, Arrays.asList(49L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(49L), deptId):BigDecimal.ZERO;
            creatInfo(data, 49L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "产品责任拨备");
            data.put("index", 86);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==2 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "安全生产费");
            data.put("index", 87);
            sum =summaryIds.contains(60L)?getSum(datas, Arrays.asList(60L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(60L), deptId):BigDecimal.ZERO;
            creatInfo(data, 60L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==2){
            data = new HashMap<>();
            data.put("fylx", "矿山环境恢复治理保证金");
            data.put("index", 88);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "维简费");
            data.put("index", 89);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "煤矿转产发展资金");
            data.put("index", 90);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "污水处理费");
            data.put("index", 91);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==2 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "样品费");
            data.put("index", 92);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "促销费用");
            data.put("index", 93);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==2 || summaryType==4){
            list = Arrays.asList(51L, 52L);
            ids = new ArrayList<>();
            for (Long id : list) {
                Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
                if(subjectId!=null && subjectId.longValue() > 0){
                    ids.add(subjectId);
                }
            }
            data = new HashMap<>();
            data.put("fylx", "市场推广费");
            data.put("index", 94);
            sum =getSum(datas, ids, deptId);
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    市场调研费用");
            data.put("index", 95);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    广告费");
            data.put("index", 96);
            sum =summaryIds.contains(52L)?getSum(datas, Arrays.asList(52L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(52L), deptId):BigDecimal.ZERO;
            creatInfo(data, 52L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    客户服务费用");
            data.put("index", 97);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    市场推广费用");
            data.put("index", 98);
            sum =summaryIds.contains(51L)?getSum(datas, Arrays.asList(51L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(51L), deptId):BigDecimal.ZERO;
            creatInfo(data, 51L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    其他");
            data.put("index", 99);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "佣金支出");
            data.put("index", 100);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "报关费");
            data.put("index", 101);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "特许权使用费");
            data.put("index", 102);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==2 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "物业管理费用");
            data.put("index", 103);
            sum =summaryIds.contains(76L)?getSum(datas, Arrays.asList(76L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(76L), deptId):BigDecimal.ZERO;
            creatInfo(data, 76L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "安保服务费");
            data.put("index", 104);
            sum =summaryIds.contains(53L)?getSum(datas, Arrays.asList(53L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(53L), deptId):BigDecimal.ZERO;
            creatInfo(data, 53L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==2 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "清洁费");
            data.put("index", 105);
            sum =summaryIds.contains(54L)?getSum(datas, Arrays.asList(54L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(54L), deptId):BigDecimal.ZERO;
            creatInfo(data, 54L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "交通费");
            data.put("index", 106);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "环境费");
            data.put("index", 107);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "加工费");
            data.put("index", 108);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "存货损耗");
            data.put("index", 109);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "广告宣传费");
            data.put("index", 110);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==2) {
            data = new HashMap<>();
            data.put("fylx", "存货盘亏及毁损");
            data.put("index", 111);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "上市费用");
            data.put("index", 112);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==2 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "专业机构费");
            data.put("index", 113);
            sum =summaryIds.contains(55L)?getSum(datas, Arrays.asList(55L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(55L), deptId):BigDecimal.ZERO;
            creatInfo(data, 55L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    审计费用");
            data.put("index", 114);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    非审计费用");
            data.put("index", 115);
            sum =summaryIds.contains(55L)?getSum(datas, Arrays.asList(55L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(55L), deptId):BigDecimal.ZERO;
            creatInfo(data, 55L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "销售设施配套建设费");
            data.put("index", 116);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "存货摊销");
            data.put("index", 117);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "渠道费");
            data.put("index", 118);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "招标费");
            data.put("index", 119);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "商检费");
            data.put("index", 120);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==2){
            data = new HashMap<>();
            data.put("fylx", "绿化费");
            data.put("index", 121);
            sum =summaryIds.contains(56L)?getSum(datas, Arrays.asList(56L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(56L), deptId):BigDecimal.ZERO;
            creatInfo(data, 56L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "总部管理费");
            data.put("index", 122);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "开办费");
            data.put("index", 123);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "地塌费");
            data.put("index", 124);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "停工损失");
            data.put("index", 125);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "差饷");
            data.put("index", 126);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "企业评优奖励");
            data.put("index", 127);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "安保服务费");
            data.put("index", 128);
            sum =summaryIds.contains(53L)?getSum(datas, Arrays.asList(53L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(53L), deptId):BigDecimal.ZERO;
            creatInfo(data, 53L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "金融讯息费");
            data.put("index", 129);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "文具印刷费");
            data.put("index", 130);
            sum =summaryIds.contains(21L)?getSum(datas, Arrays.asList(21L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(21L), deptId):BigDecimal.ZERO;
            creatInfo(data, 21L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "车膳费");
            data.put("index", 131);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "节庆费");
            data.put("index", 132);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "电脑费");
            data.put("index", 133);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "文具印刷费");
            data.put("index", 134);
            sum =summaryIds.contains(73L)?getSum(datas, Arrays.asList(73L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(73L), deptId):BigDecimal.ZERO;
            creatInfo(data, 73L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1 || summaryType==2 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "使用权资产折旧（新租赁准则专用）");
            data.put("index", 135);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "短期租赁或低价值租赁（新租赁准则专用）");
            data.put("index", 135);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "可变租赁付款额（新租赁准则专用）");
            data.put("index", 136);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==1){
            data = new HashMap<>();
            data.put("fylx", "配送费");
            data.put("index", 137);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "平台服务费");
            data.put("index", 138);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "污水处理费用");
            data.put("index", 139);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        if(summaryType==3){
            data = new HashMap<>();
            data.put("fylx", "设计检验费");
            data.put("index", 140);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    设计费");
            data.put("index", 141);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    检验费");
            data.put("index", 142);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "委托外部研究开发费用");
            data.put("index", 143);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        if(summaryType==1){
            list = Arrays.asList(58L, 74L, 57L, 25L, 56L, 59L, 73L);
        }else if(summaryType==2){
            list = Arrays.asList(58L, 74L, 57L, 25L, 56L, 59L, 73L);
        }else if(summaryType==3){
            list = Arrays.asList(0L);
        }else if(summaryType==4){
            list = Arrays.asList(58L, 74L, 57L, 25L, 56L, 59L, 73L);
        }
        ids = new ArrayList<>();
        for (Long id : list) {
            Long subjectId = summaryIds.contains(id) ? id : deptType == summaryType ? id : 0L;
            if(subjectId!=null && subjectId.longValue() > 0){
                ids.add(subjectId);
            }
        }
        data = new HashMap<>();
        data.put("fylx", "其他");
        data.put("index", 144);
        sum =getSum(datas, ids, deptId);
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);

        if(summaryType==1 || summaryType==4){
            data = new HashMap<>();
            data.put("fylx", "    取暖费");
            data.put("index", 145);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    机物料消耗");
            data.put("index", 146);
            sum =summaryIds.contains(58L)?getSum(datas, Arrays.asList(58L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(58L), deptId):BigDecimal.ZERO;
            creatInfo(data, 58L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    消防费");
            data.put("index", 147);
            sum =summaryIds.contains(74L)?getSum(datas, Arrays.asList(74L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(74L), deptId):BigDecimal.ZERO;
            creatInfo(data, 74L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    价格调节基金");
            data.put("index", 148);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    售后服务");
            data.put("index", 149);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    党建经费");
            data.put("index", 150);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    钢瓶回购");
            data.put("index", 151);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    税金");
            data.put("index", 152);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    举报窃气行为奖励费");
            data.put("index", 153);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    协会会费");
            data.put("index", 154);
            sum =summaryIds.contains(57L)?getSum(datas, Arrays.asList(57L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(57L), deptId):BigDecimal.ZERO;
            creatInfo(data, 57L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    董事会基金");
            data.put("index", 155);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    劳务点工费");
            data.put("index", 156);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    顾问咨询费");
            data.put("index", 157);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    其他办公费");
            data.put("index", 158);
            sum =summaryIds.contains(25L)?getSum(datas, Arrays.asList(25L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(25L), deptId):BigDecimal.ZERO;
            creatInfo(data, 25L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    购票据");
            data.put("index", 159);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    绿化养护费");
            data.put("index", 160);
            sum =summaryIds.contains(56L)?getSum(datas, Arrays.asList(56L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(56L), deptId):BigDecimal.ZERO;
            creatInfo(data, 56L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    设计制图费");
            data.put("index", 161);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    劳动保护费");
            data.put("index", 162);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    其他");
            data.put("index", 163);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        if(summaryType==2){
            data = new HashMap<>();
            data.put("fylx", "    机物料消耗");
            data.put("index", 163);
            sum =summaryIds.contains(58L)?getSum(datas, Arrays.asList(58L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(58L), deptId):BigDecimal.ZERO;
            creatInfo(data, 58L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    取暖费");
            data.put("index", 164);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    消防费");
            data.put("index", 165);
            sum =summaryIds.contains(74L)?getSum(datas, Arrays.asList(74L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(74L), deptId):BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    特许经营权使用费");
            data.put("index", 166);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    工具用具费");
            data.put("index", 167);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    减免费用");
            data.put("index", 168);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    党建经费");
            data.put("index", 169);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    年会费用");
            data.put("index", 170);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    举报窃气行为奖励费");
            data.put("index", 171);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    协会会费");
            data.put("index", 172);
            sum =summaryIds.contains(57L)?getSum(datas, Arrays.asList(57L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(57L), deptId):BigDecimal.ZERO;
            creatInfo(data, 57L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    董事会基金");
            data.put("index", 173);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    劳务点工费");
            data.put("index", 174);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    保安服务费");
            data.put("index", 175);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    办公费");
            data.put("index", 176);
            sum =summaryIds.contains(25L)?getSum(datas, Arrays.asList(25L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(25L), deptId):BigDecimal.ZERO;
            creatInfo(data, 25L, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    购票据");
            data.put("index", 177);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    设计制图费");
            data.put("index", 178);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    劳动保护费");
            data.put("index", 179);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    其他");
            data.put("index", 180);
            sum =summaryIds.contains(59L)?getSum(datas, Arrays.asList(59L), deptId):deptType==summaryType?getSum(datas, Arrays.asList(59L), deptId):BigDecimal.ZERO;
            creatInfo(data, 59L, sum,BigDecimal.ZERO);
            titleData.add(data);
        }

        if(summaryType==3){
            data = new HashMap<>();
            data.put("fylx", "    办公费");
            data.put("index", 181);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    知识产权费用");
            data.put("index", 182);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    差饷");
            data.put("index", 183);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    咨询服务费");
            data.put("index", 184);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    停工损失");
            data.put("index", 185);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    开办费");
            data.put("index", 186);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    绿化费");
            data.put("index", 187);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    专业机构费");
            data.put("index", 188);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    存货盘亏及毁损");
            data.put("index", 189);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    交通费");
            data.put("index", 190);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    清洁费用");
            data.put("index", 191);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    物业管理费用");
            data.put("index", 192);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    佣金支出");
            data.put("index", 193);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    样品费");
            data.put("index", 194);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    污水处理费");
            data.put("index", 195);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    安全生产费");
            data.put("index", 196);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    协会会费");
            data.put("index", 197);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    维修及保养费");
            data.put("index", 198);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    保险费");
            data.put("index", 199);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    行政收费");
            data.put("index", 200);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    交易手续费");
            data.put("index", 201);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    代理费");
            data.put("index", 202);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    交际应酬费");
            data.put("index", 203);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    证照费");
            data.put("index", 204);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    软件服务费");
            data.put("index", 205);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    电脑耗材");
            data.put("index", 206);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    耗用品费用");
            data.put("index", 207);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    仓储费用");
            data.put("index", 208);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    押运费");
            data.put("index", 209);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    车辆费用");
            data.put("index", 210);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    租赁费用");
            data.put("index", 211);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    能源费");
            data.put("index", 212);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    通讯费");
            data.put("index", 213);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    会议费");
            data.put("index", 214);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    图书资料费");
            data.put("index", 215);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    差旅费");
            data.put("index", 216);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    邮电费");
            data.put("index", 217);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    印刷费");
            data.put("index", 218);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    文具费");
            data.put("index", 219);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);

            data = new HashMap<>();
            data.put("fylx", "    其他");
            data.put("index", 220);
            sum = BigDecimal.ZERO;
            creatInfo(data, null, sum,BigDecimal.ZERO);
            titleData.add(data);
        }
        data = new HashMap<>();
        data.put("fylx", "合计");
        data.put("index", 221);
        sum = BigDecimal.ZERO;
        creatInfo(data, null, sum,BigDecimal.ZERO);
        titleData.add(data);
    }

    public BigDecimal getSum(List<Map<String, Object>> dates,List<Long> ids,Long deptId){
        BigDecimal sum = BigDecimal.ZERO;
        if(ids.size()>0){
            for (Map<String, Object> date : dates) {
                if(ids.contains(date.get("subjectId"))){
                    if (date.get("dept"+deptId) !=null){
                        sum = sum.add((BigDecimal) date.get("dept"+deptId));
                    }
                }
            }
        }
        return sum;
    }

    public BigDecimal getSumByDept(List<Map<String, Object>> dates,List<Long> ids,SysDept dept){
        BigDecimal sum = BigDecimal.ZERO;
        if(ids.size()>0){
            for (Map<String, Object> date : dates) {
                if(ids.contains(date.get("subjectId"))){
                    for (int i = 0; i < dept.getChildren().size(); i++) {
                        SysDept children = dept.getChildren().get(i);
                        if (date.get("dept"+children.getDeptId()) !=null){
                            sum = sum.add((BigDecimal) date.get("dept"+children.getDeptId()));
                        }
                    }
                    if (date.get("dept"+dept.getDeptId()) !=null){
                        sum = sum.add((BigDecimal) date.get("dept"+dept.getDeptId()));
                    }
                }
            }
        }
        return sum;
    }

    public void creatInfo(Map<String, Object> data, Long subjectId, BigDecimal budgetTotal,BigDecimal estimatedIncurredTotal){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        BigDecimal budgetRatio = BigDecimal.ZERO;
        BigDecimal growthRate = null;
        if(subjectId!=null){
            BudgetSubject subject = budgetSubjectService.selectBudgetSubjectById(subjectId);
            if(subject!=null) {
                if(subject.getBudgetRatio()!=null){
                    budgetRatio = subject.getBudgetRatio();
                }
                if(subject.getGrowthRate()!=null){
                    growthRate = subject.getGrowthRate();
                }
            }
            estimatedIncurredTotal= budgetTotal.multiply(budgetRatio);
        }
        //换算千元
        if (estimatedIncurredTotal.compareTo(BigDecimal.ZERO) >= 0) {
            estimatedIncurredTotal = estimatedIncurredTotal.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
        }
        if (budgetTotal.compareTo(BigDecimal.ZERO) >= 0) {
            budgetTotal = budgetTotal.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
        }
        data.put("snqm", estimatedIncurredTotal);
        data.put("quannian", budgetTotal);
        if(budgetTotal.compareTo(BigDecimal.ZERO) > 0){
            BigDecimal one = budgetTotal.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
            BigDecimal three = budgetTotal.divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP);
            BigDecimal six = budgetTotal.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
            for (int i = 1; i <= 12; i++) {
                data.put(i + "yue", one);
            }
            for (int i = 1; i <= 4; i++) {
                data.put("d"+i+"j", three);
            }
            data.put("sbn", six);
            data.put("xbn", six);
            if(estimatedIncurredTotal.compareTo(BigDecimal.ZERO) > 0){
                data.put("qntb0", decimalFormat.format((budgetTotal.subtract(estimatedIncurredTotal)).divide(estimatedIncurredTotal, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
            }
            for (int i = 1; i < 6; i++){
                if(growthRate != null) {
                    growthRate = growthRate.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                    BigDecimal previousYearSales = budgetTotal;
                    budgetTotal = budgetTotal.multiply(BigDecimal.ONE.add(growthRate));
                    BigDecimal tb = (budgetTotal.subtract(previousYearSales))
                            .divide(previousYearSales, 10, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal("100"))
                            .setScale(2, RoundingMode.HALF_UP);
                    data.put("qunian"+i, decimalFormat.format(budgetTotal));
                    data.put("qntb"+i, decimalFormat.format(tb)+"%");
                }else{
                    data.put("qunian"+i, 0);
                    data.put("qntb"+i, 0);
                }
            }
        }else{
            data.put("sbn", BigDecimal.ZERO);
            data.put("xbn", BigDecimal.ZERO);
            for (int i = 1; i <= 4; i++) {
                data.put("d"+i+"j", BigDecimal.ZERO);
            }
        }
    }

    /**
     * 千元单位
     */
    public Map<String, Object> budgetSummary3_special(Long taskId,int summaryType, int type,Long versionId,Long parentDeptId,Long deptId) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> itemDates = new ArrayList<>();
        List<Map<String, Object>> subjectDates = new ArrayList<>();
        List<Map<String, Object>> wagesDates = new ArrayList<>();
        List<Map<String, Object>> titleName = new ArrayList<>();
        List<Map<String, Object>> titleDate = new ArrayList<>();
        ReportingTask task = taskService.selectReportingTaskById(taskId);
        if(task!=null){
            titleName = creatTatleList(3,task);
        }
        ReportingVersion version = reportingVersionService.selectReportingVersionById(versionId);
        if(type==2 && (version==null || !version.getStatus().equals("2"))){
            map.put("titleName", titleName);
            map.put("titleDate", titleDate);
            return map;
        }

        //所属分类部门
        SysDept dept = new SysDept();
        dept.setType(summaryType);
        List<SysDept> depts = deptMapper.selectDeptList(dept);
        List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        String deptIdsStr = deptIds.size()>0? deptIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", " and dept_id in (", ")")): " and dept_id = 0 ";

        // 所属分类科目
        BudgetSubject query = new BudgetSubject();
        query.setType(summaryType+"");
        List<BudgetSubject> subjects = budgetSubjectService.selectBudgetSubjectList(query);

        //表11特殊科目
        List<Long> meterSubjectIds = subjects.stream()
                .filter(subject -> Arrays.asList(68L, 61L, 49L, 44L, 46L).contains(subject.getId()))
                .map(BudgetSubject::getId)
                .collect(Collectors.toList());

        //表9特殊科目
        List<Long> researchSubjectIds = subjects.stream()
                .filter(subject -> Arrays.asList(80L).contains(subject.getId()))
                .map(BudgetSubject::getId)
                .collect(Collectors.toList());

        //表42车辆费用特殊科目
        List<Long> clSubjectIds = subjects.stream()
                .filter(subject -> Arrays.asList(32L, 33L, 34L, 35L, 36L, 37L, 38L).contains(subject.getId()))
                .map(BudgetSubject::getId)
                .collect(Collectors.toList());

        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        List<BudgetItem> items = itemService.selectBudgetItemList(budgetItem);

        String deptSql = "";
        List<Long> allDeptIds = new ArrayList<>();
        if(parentDeptId!=null && parentDeptId!=0){
            allDeptIds = Arrays.stream(deptMapper.selectChildIds(parentDeptId)).toList();
            deptSql = allDeptIds.size()>0? allDeptIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", " and dept_id in (", ")")): " and dept_id = 0 ";
        }

        if(deptId!=null && deptId!=0){
            deptSql = " and dept_id = "+deptId;
        }

        if(type==1){
            //任务汇总
            //表11特殊处理
            long[] meterIds = {68L, 61L, 49L, 44L, 46L};
            for (Long id : meterIds) {
                String sql = "";
                Map<String, Object> subjectDate = new HashMap<>();
                Map<String, Object> date = new HashMap<>();
                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_meter WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId;
                if(id.longValue()==68){
                    sql+="  and reporting_type = 1 and project_type = 1 ";
                }else if(id.longValue()==61){
                    sql+="  and reporting_type = 1 and project_type != 1 ";
                }else if(id.longValue()==49){
                    sql+="  and reporting_type = 2 and project_type = 1 ";
                }else if(id.longValue()==44){
                    sql+="  and reporting_type = 2 and project_type = 2 ";
                }else if(id.longValue()==46){
                    sql+="  and reporting_type = 2 and project_type = 3 ";
                }
                if(!meterSubjectIds.contains(id)){
                    sql+=deptIdsStr;
                }
                date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                subjectDate.put("subjectId",id);
                subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                subjectDates.add(subjectDate);
            }

            long[] researchIds = {80};
            for (Long id : researchIds) {
                String sql = "";
                Map<String, Object> subjectDate = new HashMap<>();
                Map<String, Object> date = new HashMap<>();
                sql = "SELECT ROUND(SUM(subtotal_costs), 2) AS total FROM t_reporting_table_research WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId;
                if(!researchSubjectIds.contains(id)){
                    sql+=deptIdsStr;
                }
                date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                subjectDate.put("subjectId",id);
                subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                subjectDates.add(subjectDate);
            }

            long[] clIds = {32L, 33L, 34L, 35L, 36L, 37L, 38L};
            String[] tableNames = {"dynamic30","dynamic61"};
            for (Long id : clIds) {
                for (String tableName : tableNames) {
                    String sql = "";
                    Map<String, Object> subjectDate = new HashMap<>();
                    Map<String, Object> date = new HashMap<>();
                    if(tableName.equals("dynamic30")){
                        sql = "SELECT  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field2), 2) as txf,  ROUND(SUM(e.field3), 2) as tcf, ROUND(SUM(e.field4), 2) as rlf, ROUND(SUM(e.field5), 2) as xlf, ROUND(SUM(e.field6), 2) as nsf, ROUND( CAST( SUM( COALESCE(e.field7,0)+COALESCE(e.field9,0) ) AS DECIMAL(10, 2) ), 2 ) AS qtf, ROUND(SUM(e.field8), 2) as bxf FROM `t_reporting_table_"+tableName+"` t LEFT JOIN t_reporting_table_"+tableName+"_extension e on  t.id = e.id where (t.`status` = 3 or t.`status` = 5 ) and t.del_flag = '0' and t.task_id = "+taskId;
                    }else{
                        sql = "SELECT  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field9), 2) as txf,  ROUND(SUM(e.field8), 2) as tcf, ROUND(SUM(e.field10), 2) as rlf, ROUND(SUM(e.field11), 2) as xlf, ROUND(SUM(e.field12), 2) as nsf, ROUND( CAST( SUM( COALESCE(e.field13,0)+COALESCE(e.field15,0) ) AS DECIMAL(10, 2) ), 2 ) AS qtf, ROUND(SUM(e.field14), 2) as bxf FROM `t_reporting_table_"+tableName+"` t LEFT JOIN t_reporting_table_"+tableName+"_extension e on  t.id = e.id where (t.`status` = 3 or t.`status` = 5 ) and t.del_flag = '0' and t.task_id = "+taskId;
                    }
                    if(!clSubjectIds.contains(id)){
                        sql+=deptIdsStr;
                    }
                    date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                    subjectDate.put("subjectId",id);
                    subjectDate.put("total", BigDecimal.ZERO);
                    if(id.longValue()==32){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("txf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==33){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("tcf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==34){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("rlf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==35){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("xlf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==36){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("nsf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==37){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("bxf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==38){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("qtf")).orElse(BigDecimal.ZERO));
                    }
                    subjectDates.add(subjectDate);
                }
            }

            for (BudgetItem item : items) {
                if(StringUtils.isEmpty(item.getType()) || item.getType().equals("1")){
                    continue;
                }
                Map<String, Object> itemDate = new HashMap<>();
                String tableName = item.getTableName();
                if(StringUtils.isNotEmpty(tableName)){
                    if(item.getSubjectType() !=null && item.getSubjectType()==summaryType){
                        String sql = "";
                        Map<String, Object> date = new HashMap<>();
                        if(item.getReportingType().equals("1")){ //动态表
                            if(item.getTableName().equals("dynamic30") || item.getTableName().equals("dynamic61")){ //表42-1、42-2特殊处理
                                continue;
                            }
                            sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_"+tableName+" WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId;
                            date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                        }else if(item.getReportingType().equals("2")){ //定制表
                            if(item.getTableName().equals("pipeline")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_pipeline WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId;
                            }else if (item.getTableName().equals("station")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_station WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId;
                            }else if (item.getTableName().equals("research")){ //特殊处理
                                continue;
                            }else if (item.getTableName().equals("meter")){ //特殊处理
                                continue;
                            }else if (item.getTableName().equals("charity")){
                                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_charity WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId;
                            }else if (item.getTableName().equals("lowvalue")){
                                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_lowvalue WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId;
                            }else if (item.getTableName().equals("information_system")){
                                sql = "SELECT ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total FROM t_reporting_table_information_system WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId;
                            }else if (item.getTableName().equals("housing")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_housing WHERE (`status` = 3 or `status` = 5 )	and task_id = "+taskId;
                            }
                            date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                        }else{
                            continue;
                        }
                        itemDate.put("itemId",item.getId());
                        itemDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                    }else{
                        String sql = "";
                        Map<String, Object> date = new HashMap<>();
                        if(item.getReportingType().equals("1")){ //动态表
                            if(item.getTableName().equals("dynamic30") || item.getTableName().equals("dynamic61")){ //表42-1、42-2特殊处理
                                continue;
                            }
                            sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_"+tableName+" WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId+deptIdsStr;
                            date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                        }else if(item.getReportingType().equals("2")){ //定制表
                            if(item.getTableName().equals("pipeline")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_pipeline WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId+deptIdsStr;
                            }else if (item.getTableName().equals("station")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_station WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId+deptIdsStr;
                            }else if (item.getTableName().equals("research")){//特殊处理
                                continue;
                            }else if (item.getTableName().equals("meter")){ //特殊处理
                                continue;
                            }else if (item.getTableName().equals("charity")){
                                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_charity WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId+deptIdsStr;
                            }else if (item.getTableName().equals("lowvalue")){
                                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_lowvalue WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId+deptIdsStr;
                            }else if (item.getTableName().equals("information_system")){
                                sql = "SELECT ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total FROM t_reporting_table_information_system WHERE (`status` = 3 or `status` = 5 ) and del_flag = '0' and task_id = "+taskId+deptIdsStr;
                            }else if (item.getTableName().equals("housing")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_housing WHERE (`status` = 3 or `status` = 5 )	and task_id = "+taskId+deptIdsStr;
                            }
                            date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                        }else{
                            continue;
                        }
                        itemDate.put("itemId",item.getId());
                        itemDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                    }
                }
                itemDates.add(itemDate);
            }
            //工资
            TableWages wages = new TableWages();
            wages.setTaskId(taskId);
            List<Map<String, Object>> wageData = tableWagesService.budgetSummary1(wages);
            List<WagesSubject> wagesSubjects = wagesSubjectService.selectWagesSubjectList(new WagesSubject());
            for (WagesSubject subject : wagesSubjects){
                Map<String, Object> itemDate = new HashMap<>();
                BigDecimal total = BigDecimal.ZERO;
                for (Map<String, Object> wage : wageData) {
                    if(subject.getSubjectType()!=null && subject.getSubjectType()==summaryType){
                        if(parentDeptId!=null && parentDeptId!=0){
                            if(allDeptIds.contains(wage.get("dept_id"))){
                                total = total.add(Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO));
                            }
                        }else if(deptId!=null && deptId!=0) {
                            if (deptId.longValue()==Long.parseLong(wage.get("dept_id").toString())) {
                                total = Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO);
                            }
                        }else{
                            total = total.add(Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO));
                        }
                    }else{
                        if(wage.get("subjectType")!=null && Integer.parseInt(wage.get("subjectType").toString())==summaryType ){
                            if(parentDeptId!=null && parentDeptId!=0){
                                if(allDeptIds.contains(wage.get("dept_id"))){
                                    if(deptIds.contains(wage.get("dept_id"))){
                                        total = total.add(Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO));
                                    }
                                }
                            }else if(deptId!=null && deptId!=0){
                                if(deptId.equals(wage.get("dept_id"))){
                                    total = Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO);
                                }
                            }else{
                                if(deptIds.contains(wage.get("dept_id"))){
                                    total = total.add(Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO));
                                }
                            }
                        }
                    }
                }
                itemDate.put("subjectId",subject.getSubjectId());
                itemDate.put("total",total);
                wagesDates.add(itemDate);
            }
        }else if(type==2){ //版本汇总
            //表11特殊处理
            long[] meterIds = {68L, 61L, 49L, 44L, 46L};
            for (Long id : meterIds) {
                String sql = "";
                Map<String, Object> subjectDate = new HashMap<>();
                Map<String, Object> date = new HashMap<>();
                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_meter_version WHERE version_id = "+versionId;
                if(id.longValue()==68){
                    sql+="  and reporting_type = 1 and project_type = 1 ";
                }else if(id.longValue()==61){
                    sql+="  and reporting_type = 1 and project_type != 1 ";
                }else if(id.longValue()==49){
                    sql+="  and reporting_type = 2 and project_type = 1 ";
                }else if(id.longValue()==44){
                    sql+="  and reporting_type = 2 and project_type = 2 ";
                }else if(id.longValue()==46){
                    sql+="  and reporting_type = 2 and project_type = 3 ";
                }
                if(!meterSubjectIds.contains(id)){
                    sql+=deptIdsStr;
                }
                date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                subjectDate.put("subjectId",id);
                subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                subjectDates.add(subjectDate);
            }

            long[] researchIds = {80};
            for (Long id : researchIds) {
                String sql = "";
                Map<String, Object> subjectDate = new HashMap<>();
                Map<String, Object> date = new HashMap<>();
                sql = "SELECT ROUND(SUM(subtotal_costs), 2) AS total FROM t_reporting_table_research_version WHERE  version_id = "+versionId;
                if(!researchSubjectIds.contains(id)){
                    sql+=deptIdsStr;
                }
                date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                subjectDate.put("subjectId",id);
                subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                subjectDates.add(subjectDate);
            }

            long[] clIds = {32L, 33L, 34L, 35L, 36L, 37L, 38L};
            String[] tableNames = {"dynamic30","dynamic61"};
            for (Long id : clIds) {
                for (String tableName : tableNames) {
                    String sql = "";
                    Map<String, Object> subjectDate = new HashMap<>();
                    Map<String, Object> date = new HashMap<>();
                    if(tableName.equals("dynamic30")){
                        sql = "SELECT  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field2), 2) as txf,  ROUND(SUM(e.field3), 2) as tcf, ROUND(SUM(e.field4), 2) as rlf, ROUND(SUM(e.field5), 2) as xlf, ROUND(SUM(e.field6), 2) as nsf, ROUND( CAST( SUM( COALESCE(e.field7,0)+COALESCE(e.field9,0) ) AS DECIMAL(10, 2) ), 2 ) AS qtf, ROUND(SUM(e.field8), 2) as bxf FROM `t_reporting_table_"+tableName+"_version` t LEFT JOIN t_reporting_table_"+tableName+"_extension_version e on t.budget_id = e.budget_id  and t.version_id = e.version_id  where  t.version_id = "+versionId;
                    }else{
                        sql = "SELECT  ROUND(SUM(t.budget), 2) as budget, ROUND(SUM(e.field9), 2) as txf,  ROUND(SUM(e.field8), 2) as tcf, ROUND(SUM(e.field10), 2) as rlf, ROUND(SUM(e.field11), 2) as xlf, ROUND(SUM(e.field12), 2) as nsf, ROUND( CAST( SUM( COALESCE(e.field13,0)+COALESCE(e.field15,0) ) AS DECIMAL(10, 2) ), 2 ) AS qtf, ROUND(SUM(e.field14), 2) as bxf FROM `t_reporting_table_"+tableName+"_version` t LEFT JOIN t_reporting_table_"+tableName+"_extension_version e on t.budget_id = e.budget_id  and t.version_id = e.version_id  where  t.version_id = "+versionId;
                    }
                    if(!clSubjectIds.contains(id)){
                        sql+=deptIdsStr;
                    }
                    date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                    subjectDate.put("subjectId",id);
                    subjectDate.put("total", BigDecimal.ZERO);
                    if(id.longValue()==32){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("txf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==33){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("tcf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==34){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("rlf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==35){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("xlf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==36){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("nsf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==37){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("bxf")).orElse(BigDecimal.ZERO));
                    }else if(id.longValue()==38){
                        subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("qtf")).orElse(BigDecimal.ZERO));
                    }
                    subjectDates.add(subjectDate);
                }
            }

            for (Long id : meterSubjectIds) {
                Map<String, Object> subjectDate = new HashMap<>();
                Map<String, Object> date = new HashMap<>();
                String sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_meter_version WHERE version_id = "+versionId;
                if(id.longValue()==68){
                    sql+="  and reporting_type = 1 and project_type = 1 ";
                }else if(id.longValue()==61){
                    sql+="  and reporting_type = 1 and project_type != 1 ";
                }else if(id.longValue()==49){
                    sql+="  and reporting_type = 2 and project_type = 1 ";
                }else if(id.longValue()==44){
                    sql+="  and reporting_type = 2 and project_type = 2 ";
                }else if(id.longValue()==46){
                    sql+="  and reporting_type = 2 and project_type = 3 ";
                }
                date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                subjectDate.put("subjectId",id);
                subjectDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                subjectDates.add(subjectDate);
            }
            for (BudgetItem item : items) {
                if(StringUtils.isEmpty(item.getType()) || item.getType().equals("1")){
                    continue;
                }
                Map<String, Object> itemDate = new HashMap<>();
                String tableName = item.getTableName();
                if(StringUtils.isNotEmpty(tableName)){
                    if(item.getSubjectType() !=null && item.getSubjectType()==summaryType){
                        String sql = "";
                        Map<String, Object> date = new HashMap<>();
                        if(item.getReportingType().equals("1")){ //动态表
                            if(item.getTableName().equals("dynamic30") || item.getTableName().equals("dynamic61")){ //表42-1、42-2特殊处理
                                continue;
                            }
                            sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_"+tableName+"_version WHERE version_id = "+versionId;
                            date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                        }else if(item.getReportingType().equals("2")){ //定制表
                            if(item.getTableName().equals("pipeline")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_pipeline_version WHERE version_id = "+versionId;
                            }else if (item.getTableName().equals("station")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_station_version WHERE version_id = "+versionId;
                            }else if (item.getTableName().equals("research")){ //特殊处理
                                continue;
                            }else if (item.getTableName().equals("meter")){ //特殊处理
                                continue;
                            }else if (item.getTableName().equals("charity")){
                                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_charity_version WHERE version_id = "+versionId;
                            }else if (item.getTableName().equals("lowvalue")){
                                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_lowvalue_version WHERE version_id = "+versionId;
                            }else if (item.getTableName().equals("information_system")){
                                sql = "SELECT ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total FROM t_reporting_table_information_system_version WHERE version_id = "+versionId;
                            }else if (item.getTableName().equals("housing")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_housing_version WHERE version_id = "+versionId;
                            }
                            date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                        }else{
                            continue;
                        }
                        itemDate.put("itemId",item.getId());
                        itemDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                    }else{
                        String sql = "";
                        Map<String, Object> date = new HashMap<>();
                        if(item.getReportingType().equals("1")){ //动态表
                            if(item.getTableName().equals("dynamic30") || item.getTableName().equals("dynamic61")){ //表42-1、42-2特殊处理
                                continue;
                            }
                            sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_"+tableName+"_version WHERE version_id = "+versionId+deptIdsStr;
                            date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                        }else if(item.getReportingType().equals("2")){ //定制表
                            if(item.getTableName().equals("pipeline")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_pipeline_version WHERE version_id = "+versionId+deptIdsStr;
                            }else if (item.getTableName().equals("station")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_station_version WHERE version_id = "+versionId+deptIdsStr;
                            }else if (item.getTableName().equals("research")){//特殊处理
                                continue;
                            }else if (item.getTableName().equals("meter")){//特殊处理
                                continue;
                            }else if (item.getTableName().equals("charity")){
                                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_charity_version WHERE version_id = "+versionId+deptIdsStr;
                            }else if (item.getTableName().equals("lowvalue")){
                                sql = "SELECT ROUND(SUM(budget), 2) AS total FROM t_reporting_table_lowvalue_version WHERE version_id = "+versionId+deptIdsStr;
                            }else if (item.getTableName().equals("information_system")){
                                sql = "SELECT ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS total FROM t_reporting_table_information_system_version WHERE version_id = "+versionId+deptIdsStr;
                            }else if (item.getTableName().equals("housing")){
                                sql = "SELECT ROUND(SUM(expected_settlement), 2) AS total FROM t_reporting_table_housing_version WHERE version_id = "+versionId+deptIdsStr;
                            }
                            date = dynamicSqlService.dynamicSelectInfoSql(sql+deptSql);
                        }else{
                            continue;
                        }
                        itemDate.put("itemId",item.getId());
                        itemDate.put("total", Optional.ofNullable((BigDecimal)date.get("total")).orElse(BigDecimal.ZERO));
                    }
                }
                itemDates.add(itemDate);
            }
            //工资
            TableWagesVersion wages = new TableWagesVersion();
            wages.setVersionId(versionId);
            List<Map<String, Object>> wageData = tableWagesVersionService.selectSummaryList(wages);
            List<WagesSubject> wagesSubjects = wagesSubjectService.selectWagesSubjectList(new WagesSubject());
            for (WagesSubject subject : wagesSubjects){
                Map<String, Object> itemDate = new HashMap<>();
                BigDecimal total = BigDecimal.ZERO;
                for (Map<String, Object> wage : wageData) {
                    if(subject.getSubjectType()!=null && subject.getSubjectType()==summaryType){
                        if(parentDeptId!=null && parentDeptId!=0){
                            if(allDeptIds.contains(wage.get("dept_id"))){
                                total = total.add(Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO));
                            }
                        }else if(deptId!=null && deptId!=0) {
                            if (deptId.longValue()==Long.parseLong(wage.get("dept_id").toString())) {
                                total = Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO);
                            }
                        }else{
                            total = total.add(Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO));
                        }
                    }else{
                        if(wage.get("subjectType")!=null && Integer.parseInt(wage.get("subjectType").toString())==summaryType ){
                            if(parentDeptId!=null && parentDeptId!=0){
                                if(allDeptIds.contains(wage.get("dept_id"))){
                                    if(deptIds.contains(wage.get("dept_id"))){
                                        total = total.add(Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO));
                                    }
                                }
                            }else if(deptId!=null && deptId!=0){
                                if(deptId.equals(wage.get("dept_id"))){
                                    total = Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO);
                                }
                            }else{
                                if(deptIds.contains(wage.get("dept_id"))){
                                    total = total.add(Optional.ofNullable((BigDecimal) wage.get(subject.getField())).orElse(BigDecimal.ZERO));
                                }
                            }
                        }
                    }
                }
                itemDate.put("subjectId",subject.getSubjectId());
                itemDate.put("total",total);
                wagesDates.add(itemDate);
            }
        }

        BigDecimal budgetTotal ;
        BigDecimal estimatedIncurredTotal ;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        BudgetSubject querySubject = new BudgetSubject();
        if(versionId!=null){
            if(summaryType==1){ //销售
                querySubject.setXsExport(1);
                subjects = budgetSubjectService.selectBudgetSubjectListByXs(querySubject);
            }else if(summaryType==2){ //管理
                querySubject.setGlExport(1);
                subjects = budgetSubjectService.selectBudgetSubjectListByGl(querySubject);
            }else if(summaryType==3){ //研发
                querySubject.setYfExport(1);
                subjects = budgetSubjectService.selectBudgetSubjectListByYf(querySubject);
            }else if(summaryType==4){ //制造
                querySubject.setZzExport(1);
                subjects = budgetSubjectService.selectBudgetSubjectListByZz(querySubject);
            }
        }else {
            subjects = budgetSubjectService.selectBudgetSubjectList(querySubject);
        }
        for (BudgetSubject subject : subjects) {
            if(subject.getName().equals("无")){
                continue;
            }
            Map<String,Object> data = new HashMap<>();
            budgetTotal = BigDecimal.ZERO;
            for (Map<String, Object> date : wagesDates) {
                if(date.get("subjectId").equals(subject.getId())){
                    budgetTotal = budgetTotal.add((BigDecimal) date.get("total"));
                }
            }
            for (Map<String, Object> date : subjectDates) {
                if(date.get("subjectId").equals(subject.getId())){
                    budgetTotal = budgetTotal.add((BigDecimal) date.get("total"));
                }
            }
            BudgetItem queryItem = new BudgetItem();
            queryItem.setSubjectId(subject.getId());
            queryItem.setDelFlag("0");
            List<BudgetItem> itemsList = itemService.selectBudgetItemList(queryItem);
            List<Long> itemsIds = itemsList.stream().map(BudgetItem::getId).collect(Collectors.toList());
            for (Map<String, Object> date : itemDates) {
                if(itemsIds.contains(date.get("itemId"))){
                    budgetTotal = budgetTotal.add((BigDecimal) date.get("total"));
                }
            }
            estimatedIncurredTotal = BigDecimal.ZERO;
            /*
            ActualCosts actualCosts = new ActualCosts();
            actualCosts.setYear(task.getBudgetYear()-1);
            actualCosts.setSubjectId(subject.getId());
            List<ActualCosts> actualCostsList = actualCostsService.selectActualCostsList(actualCosts);
            estimatedIncurredTotal = actualCostsList.stream().map(ActualCosts::getActualIncurred).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            */
            BigDecimal budgetRatio = BigDecimal.ZERO;
            if(subject!=null && subject.getBudgetRatio()!=null) {
                budgetRatio = subject.getBudgetRatio();
            }
            estimatedIncurredTotal= budgetTotal.multiply(budgetRatio);
            //换算千元
            if (estimatedIncurredTotal.compareTo(BigDecimal.ZERO) >= 0) {
                estimatedIncurredTotal = estimatedIncurredTotal.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
            }
            if (budgetTotal.compareTo(BigDecimal.ZERO) >= 0) {
                budgetTotal = budgetTotal.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
            }
            data.put("fylx", versionId!=null?subject.getReportName():subject.getName());
            data.put("subjectId", subject.getId());
            data.put("snqm", estimatedIncurredTotal);
            data.put("quannian", budgetTotal);
            if(budgetTotal.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal one = budgetTotal.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
                BigDecimal three = budgetTotal.divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP);
                BigDecimal six = budgetTotal.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
                for (int i = 1; i <= 12; i++) {
                    data.put(i + "yue", one);
                }
                for (int i = 1; i <= 4; i++) {
                    data.put("d"+i+"j", three);
                }
                data.put("sbn", six);
                data.put("xbn", six);
                if(estimatedIncurredTotal.compareTo(BigDecimal.ZERO) > 0){
                    data.put("qntb0", decimalFormat.format((budgetTotal.subtract(estimatedIncurredTotal)).divide(estimatedIncurredTotal, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
                }
                for (int i = 1; i < 6; i++){
                    if(subject!=null && subject.getGrowthRate()!=null) {
                        BigDecimal growthRate = subject.getGrowthRate().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                        BigDecimal previousYearSales = budgetTotal;
                        budgetTotal = budgetTotal.multiply(BigDecimal.ONE.add(growthRate));
                        BigDecimal tb = (budgetTotal.subtract(previousYearSales))
                                .divide(previousYearSales, 10, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal("100"))
                                .setScale(2, RoundingMode.HALF_UP);
                        data.put("qunian"+i, decimalFormat.format(budgetTotal));
                        data.put("qntb"+i, decimalFormat.format(tb)+"%");
                    }else{
                        data.put("qunian"+i, 0);
                        data.put("qntb"+i, 0);
                    }
                }
            }else{
                data.put("sbn", BigDecimal.ZERO);
                data.put("xbn", BigDecimal.ZERO);
                for (int i = 1; i <= 4; i++) {
                    data.put("d"+i+"j", BigDecimal.ZERO);
                }
            }
            titleDate.add(data);
        }
        Map<String,Object> allTotal = new HashMap<>();
        allTotal.put("fylx","合计");
        allTotal.put("subjectId", 0L);
        allTotal.put("snqm", BigDecimal.ZERO);
        allTotal.put("quannian", BigDecimal.ZERO);
        for (int i = 1; i <= 12; i++) {
            allTotal.put(i + "yue", BigDecimal.ZERO);
        }
        for (int i = 1; i <= 4; i++) {
            allTotal.put("d"+i+"j", BigDecimal.ZERO);
        }
        allTotal.put("sbn", BigDecimal.ZERO);
        allTotal.put("xbn", BigDecimal.ZERO);
        titleDate.add(allTotal);
        createSubjectTotalDataAlltotal(titleName,titleDate,summaryType);
        createSubjectTotalDataOrther(titleName,titleDate,summaryType);
        createSubjectTotalDataTotal(titleName,titleDate,summaryType);
        map.put("titleName", titleName);
        map.put("titleDate", titleDate);
        return map;
    }

    public List<Map<String, Object>> creatTatleList(int type,ReportingTask task){
        List<Map<String, Object>> titleName = new ArrayList<>();
        if(type ==0 || type ==1 || type ==2 || type ==99){
            Map<String, Object> name = new HashMap<>();
            name.put("name","费用类型");
            name.put("key","fylx");
            name.put("value",0);
            titleName.add(name);
            name = new HashMap<>();
            name.put("name","费用名称");
            name.put("key","fymc");
            name.put("value",0);
            titleName.add(name);
            List<SysDept> depts;
            if(type ==0){
                depts = deptMapper.budgetSummaryDeptOrther();
            }else if(type ==1){
                depts = deptMapper.budgetSummaryDept1();
            }else if(type == 99){
                depts = deptMapper.budgetSummaryDeptAll();
            }else {
                depts = deptMapper.budgetSummaryDept2();
            }
            depts.forEach(dept -> {
                Map<String, Object> deptName = new HashMap<>();
                deptName.put("name", dept.getDeptName() + (type == 1 ? "" : "预算汇总"));
                deptName.put("key", "dept"+dept.getDeptId());
                deptName.put("value",dept.getDeptId());
                titleName.add(deptName);
            });
            if(type == 2){
                Map<String, Object> deptName = new HashMap<>();
                deptName.put("name", "股份供气预算汇总");
                deptName.put("key", "dept100&101");
                deptName.put("value","100&101");
                titleName.add(4,deptName);
                deptName = new HashMap<>();
                deptName.put("name", "郑州区域预算汇总");
                deptName.put("key", "deptAll");
                deptName.put("value","All");
                titleName.add(deptName);
            }
        }else if(type == 3 || type == 4 || type == 5){
            int year = task.getBudgetYear();
            Map<String, Object> name;
            Map<String, Object> children;
            List<Map<String, Object>> childrens ;
            name = new HashMap<>();
            name.put("name","费用类型");
            name.put("key","fylx");
            titleName.add(name);

            childrens = new ArrayList<>();
            children = new HashMap<>();
            children.put("name","上年期末");
            children.put("key","snqm");
            childrens.add(children);
            name = new HashMap<>();
            name.put("name",year+"年");
            name.put("children",childrens);
            titleName.add(name);

            childrens = new ArrayList<>();
            children = new HashMap<>();
            children.put("name","1月");
            children.put("key","1yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","2月");
            children.put("key","2yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","3月");
            children.put("key","3yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","第一季");
            children.put("key","d1j");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","4月");
            children.put("key","4yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","5月");
            children.put("key","5yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","6月");
            children.put("key","6yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","第二季");
            children.put("key","d2j");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","上半年");
            children.put("key","sbn");
            childrens.add(children);
            name = new HashMap<>();
            name.put("name",year+"年");
            name.put("children",childrens);
            titleName.add(name);

            childrens = new ArrayList<>();
            children = new HashMap<>();
            children.put("name","7月");
            children.put("key","7yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","8月");
            children.put("key","8yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","9月");
            children.put("key","9yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","第三季");
            children.put("key","d3j");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","10月");
            children.put("key","10yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","11月");
            children.put("key","11yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","12月");
            children.put("key","12yue");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","第四季");
            children.put("key","d4j");
            childrens.add(children);
            children = new HashMap<>();
            children.put("name","下半年");
            children.put("key","xbn");
            childrens.add(children);
            name = new HashMap<>();
            name.put("name",year+"年");
            name.put("children",childrens);
            titleName.add(name);

            childrens = new ArrayList<>();
            children = new HashMap<>();
            children.put("name","全年");
            children.put("key","quannian");
            childrens.add(children);
            name = new HashMap<>();
            name.put("name",year+"年");
            name.put("children",childrens);
            titleName.add(name);

            childrens = new ArrayList<>();
            children = new HashMap<>();
            children.put("name","%");
            children.put("key","qntb0");
            childrens.add(children);
            name = new HashMap<>();
            name.put("name","与去年同比");
            name.put("children",childrens);
            titleName.add(name);
            for (int i = 1; i < 5; i++){
                childrens = new ArrayList<>();
                children = new HashMap<>();
                children.put("name","12月");
                children.put("key","qunian"+i);
                childrens.add(children);
                name = new HashMap<>();
                name.put("name",(year+i)+"年");
                name.put("children",childrens);
                titleName.add(name);

                childrens = new ArrayList<>();
                children = new HashMap<>();
                children.put("name","%");
                children.put("key","qntb"+i);
                childrens.add(children);
                name = new HashMap<>();
                name.put("name","与去年同比");
                name.put("children",childrens);
                titleName.add(name);
            }
        }
        return titleName;
    }

    /**
     * 统计汇总各科目部门数据
     * @param titleName
     * @param itemDate
     * @param titleDate
     * @param type
     */
    private void createSubjectData(List<Map<String, Object>> titleName, List<Map<String, Object>> itemDate, List<Map<String, Object>> titleDate, int type) {
        BudgetSubject querySubject = new BudgetSubject();
        //querySubject.setHzExport(1);
        List<BudgetSubject> subjects = budgetSubjectService.selectBudgetSubjectList(querySubject);
        for (BudgetSubject subject : subjects){
            if(subject.getName().equals("无")){
                continue;
            }
            Map<String, Object> data = new HashMap<>();
            data.put("fylx", subject.getItemType());
            //data.put("fymc", type==2?subject.getReportName():subject.getName());
            data.put("fymc", subject.getName());
            data.put("subjectId", subject.getId());
            for (Map<String, Object> title : titleName) {
                if (!title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                    data.put(title.get("key").toString(), BigDecimal.ZERO);
                }
            }
            for (Map<String, Object> item : itemDate) {
                if(item.get("subjectId").toString().equals(subject.getId().toString())){
                    for (Map<String, Object> title : titleName) {
                        if (!title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                            BigDecimal dateValue = new BigDecimal(data.get(title.get("key")).toString());
                            BigDecimal itemValue = new BigDecimal(item.get(title.get("key")).toString());
                            data.put(title.get("key").toString(), dateValue.add(itemValue));
                        }
                    }
                }
            }
            titleDate.add(data);
        }
    }

    /**
     * 统计汇总各科目OA调整后部门数据
     */
    private void createSubjectDataOA(Long taskId, List<Map<String, Object>> titleDate){
        BudgetOaAdjustment query = new BudgetOaAdjustment();
        query.setTaskId(taskId);
        List<BudgetOaAdjustment> budgetOaAdjustments = budgetOaAdjustmentService.selectBudgetOaAdjustmentList(query);
        for (BudgetOaAdjustment budgetOaAdjustment : budgetOaAdjustments) {
            Long inSubject = budgetOaAdjustment.getInSubject();
            Long inDept = budgetOaAdjustment.getInDept();
            Long outSubject = budgetOaAdjustment.getOutSubject();
            Long outDept = budgetOaAdjustment.getOutDept();
            BigDecimal amount = budgetOaAdjustment.getAmount();
            for (Map<String, Object> data : titleDate) {
                if(data.get("subjectId")!=null && data.get("subjectId").equals(inSubject)){
                    BigDecimal budget = BigDecimal.ZERO;
                    if(data.get("dept"+inDept)!=null){
                        budget = new BigDecimal(data.get("dept"+inDept).toString());
                    }
                    budget = budget.add(amount);
                    data.put("dept"+inDept,budget);
                }

                if(data.get("subjectId")!=null && data.get("subjectId").equals(outSubject)){
                    BigDecimal budget = BigDecimal.ZERO;
                    if(data.get("dept"+outDept)!=null){
                        budget = new BigDecimal(data.get("dept"+outDept).toString());
                    }
                    budget = budget.subtract(amount);
                    data.put("dept"+outDept,budget);
                }
            }
        }
    }

    /**
     * 统计汇总各科目OA调整后公司数据
     */
    private void createParentDeptSubjectDataOA(Long taskId, List<Map<String, Object>> titleDate){
        BudgetOaAdjustment query = new BudgetOaAdjustment();
        query.setTaskId(taskId);
        List<BudgetOaAdjustment> budgetOaAdjustments = budgetOaAdjustmentService.selectBudgetOaAdjustmentList(query);
        for (BudgetOaAdjustment budgetOaAdjustment : budgetOaAdjustments) {
            Long inSubject = budgetOaAdjustment.getInSubject();
            Long inDept = budgetOaAdjustment.getInParentDept();
            Long outSubject = budgetOaAdjustment.getOutSubject();
            Long outDept = budgetOaAdjustment.getOutParentDept();
            BigDecimal amount = budgetOaAdjustment.getAmount();
            for (Map<String, Object> data : titleDate) {
                if(data.get("subjectId")!=null && data.get("subjectId").equals(inSubject)){
                    BigDecimal budget = BigDecimal.ZERO;
                    if(data.get("dept"+inDept)!=null){
                        budget = new BigDecimal(data.get("dept"+inDept).toString());
                    }
                    budget = budget.add(amount);
                    data.put("dept"+inDept,budget);
                    //公司100或者101，修改股份供气预算汇总
                    if(inDept.equals(100L)||inDept.equals(101L)){
                        BigDecimal sum = BigDecimal.ZERO;
                        if(data.get("dept100&101")!=null){
                            sum = new BigDecimal(data.get("dept100&101").toString());
                        }
                        sum = sum.add(amount);
                        data.put("dept100&101",sum);
                    }
                    //修改总合计
                    BigDecimal deptAll = BigDecimal.ZERO;
                    if(data.get("deptAll")!=null){
                        deptAll = new BigDecimal(data.get("deptAll").toString());
                    }
                    deptAll = deptAll.add(amount);
                    data.put("deptAll",deptAll);
                }

                if(data.get("subjectId")!=null && data.get("subjectId").equals(outSubject)){
                    BigDecimal budget = BigDecimal.ZERO;
                    if(data.get("dept"+outDept)!=null){
                        budget = new BigDecimal(data.get("dept"+outDept).toString());
                    }
                    budget = budget.subtract(amount);
                    data.put("dept"+outDept,budget);
                    //公司100或者101，修改股份供气预算汇总
                    if(outDept.equals(100L)||outDept.equals(101L)){
                        BigDecimal sum = BigDecimal.ZERO;
                        if(data.get("dept100&101")!=null){
                            sum = new BigDecimal(data.get("dept100&101").toString());
                        }
                        sum = sum.subtract(amount);
                        data.put("dept100&101",sum);
                    }
                    //修改总合计
                    BigDecimal deptAll = BigDecimal.ZERO;
                    if(data.get("deptAll")!=null){
                        deptAll = new BigDecimal(data.get("deptAll").toString());
                    }
                    deptAll = deptAll.subtract(amount);
                    data.put("deptAll",deptAll);
                }
            }
        }
    }

    /**
     * 统计汇总父级科目数据
     */
    private void createSubjectTotalData(List<Map<String, Object>> titleName, List<Map<String, Object>> titleDate) {
        List<BudgetSubject> budgetSubjects = budgetSubjectService.selectBudgetSubjectList(new BudgetSubject());
        for (Map<String, Object> map : titleDate) {
            if(map.get("subjectId")!=null){
                Long subjectId = (Long) map.get("subjectId");
                for (BudgetSubject budgetSubject : budgetSubjects) {
                    if(budgetSubject.getLevel()==1 && subjectId.longValue()==budgetSubject.getId().longValue()){
                        List<BudgetSubject> subjectChildren = budgetSubject.getChildren();
                        if(subjectChildren!=null && subjectChildren.size()>0){
                            List<Long> ids = subjectChildren.stream()
                                    .map(BudgetSubject::getId)
                                    .collect(Collectors.toList());
                            for (Long id : ids) {
                                for (Map<String, Object> info : titleDate) {
                                    if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                        for (Map<String, Object> title : titleName) {
                                            if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                                BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                                BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                                map.put(title.get("key").toString(), dateValue.add(itemValue));
                                            }else if (title.get("children")!=null){
                                                List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                                for (Map<String, Object> children : childrenList) {
                                                    BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString());
                                                    BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString());
                                                    map.put(children.get("key").toString(), dateValue.add(itemValue));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void createSubjectTotalDataAlltotal(List<Map<String, Object>> titleName, List<Map<String, Object>> titleDate,int summaryType) {
        //总合计
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (Map<String, Object> map : titleDate) {
            if(map.get("subjectId")!=null && map.get("subjectId").equals(0L)){
                for (Map<String, Object> info : titleDate) {
                    if(info.get("subjectId").equals(0L)){
                        continue;
                    }
                    for (Map<String, Object> title : titleName) {
                        if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                            BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                            BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                            map.put(title.get("key").toString(), dateValue.add(itemValue));
                        }else if (title.get("children")!=null){
                            List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                            for (Map<String, Object> children : childrenList) {
                                BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                map.put(children.get("key").toString(), dateValue.add(itemValue));
                            }
                        }
                    }
                }
                BudgetSubject subject = budgetSubjectService.selectBudgetSubjectById(Long.parseLong(map.get("subjectId").toString()));
                BigDecimal estimatedIncurredTotal = new BigDecimal(map.get("snqm")==null?"0":map.get("snqm").toString());
                BigDecimal budgetTotal = new BigDecimal(map.get("quannian")==null?"0":map.get("quannian").toString());
                if(budgetTotal.compareTo(BigDecimal.ZERO) > 0){
                    if(estimatedIncurredTotal.compareTo(BigDecimal.ZERO) > 0){
                        map.put("qntb0", decimalFormat.format((budgetTotal.subtract(estimatedIncurredTotal)).divide(estimatedIncurredTotal, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
                    }
                    for (int i = 1; i < 6; i++){
                        if(subject!=null && subject.getGrowthRate()!=null) {
                            BigDecimal growthRate = subject.getGrowthRate().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                            BigDecimal previousYearSales = budgetTotal;
                            budgetTotal = budgetTotal.multiply(BigDecimal.ONE.add(growthRate));
                            BigDecimal tb = (budgetTotal.subtract(previousYearSales))
                                    .divide(previousYearSales, 10, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal("100"))
                                    .setScale(2, RoundingMode.HALF_UP);
                            map.put("qunian"+i, decimalFormat.format(budgetTotal));
                            map.put("qntb"+i, decimalFormat.format(tb)+"%");
                        }else{
                            map.put("qunian"+i, 0);
                            map.put("qntb"+i, 0);
                        }
                    }
                }
            }
        }
    }
    private void createSubjectTotalDataOrther(List<Map<String, Object>> titleName, List<Map<String, Object>> titleDate,int summaryType) {
        //其他数据处理
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (Map<String, Object> map : titleDate) {
            if(map.get("subjectId")!=null && map.get("subjectId").equals(162L)){
                Long[] ids = new Long[]{};
                if(summaryType==1){
                    ids = new Long[]{163L, 58L, 74L, 198L, 199L, 167L, 200L, 201L, 169L, 57L, 170L, 171L, 202L, 25L, 174L, 56L, 175L, 59L};
                }else if(summaryType==2){
                    ids = new Long[]{163L, 58L, 74L, 164L, 165L,166L,167L,168L,169L, 57L, 170L, 171L, 172L, 25L, 174L, 175L, 59L};
                }else if(summaryType==3){
                    ids = new Long[]{25L ,211L ,154L ,212L ,153L ,151L ,56L ,52L ,145L ,27L ,54L ,76L ,143L ,138L ,137L ,60L ,57L ,42L ,41L ,123L ,122L ,121L ,40L ,119L ,118L ,47L ,39L ,117L ,116L ,31L ,83L ,114L ,114L ,28L ,22L ,26L ,24L ,21L ,59L};
                }else if(summaryType==4){
                    ids = new Long[]{163L, 58L, 74L, 198L, 199L, 167L, 200L, 201L, 169L, 57L, 170L, 171L, 202L, 25L, 174L, 56L, 175L, 203L, 59L};
                }
                for (Long id : ids) {
                    for (Map<String, Object> info : titleDate) {
                        if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                            for (Map<String, Object> title : titleName) {
                                if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                    BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                    BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                    map.put(title.get("key").toString(), dateValue.add(itemValue));
                                }else if (title.get("children")!=null){
                                    List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                    for (Map<String, Object> children : childrenList) {
                                        BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                        BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                        map.put(children.get("key").toString(), dateValue.add(itemValue));
                                    }
                                }
                            }
                        }
                    }
                }
                BudgetSubject subject = budgetSubjectService.selectBudgetSubjectById(Long.parseLong(map.get("subjectId").toString()));
                BigDecimal estimatedIncurredTotal = new BigDecimal(map.get("snqm")==null?"0":map.get("snqm").toString());
                BigDecimal budgetTotal = new BigDecimal(map.get("quannian")==null?"0":map.get("quannian").toString());
                if(budgetTotal.compareTo(BigDecimal.ZERO) > 0){
                    if(estimatedIncurredTotal.compareTo(BigDecimal.ZERO) > 0){
                        map.put("qntb0", decimalFormat.format((budgetTotal.subtract(estimatedIncurredTotal)).divide(estimatedIncurredTotal, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
                    }
                    for (int i = 1; i < 6; i++){
                        if(subject!=null && subject.getGrowthRate()!=null) {
                            BigDecimal growthRate = subject.getGrowthRate().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                            BigDecimal previousYearSales = budgetTotal;
                            budgetTotal = budgetTotal.multiply(BigDecimal.ONE.add(growthRate));
                            BigDecimal tb = (budgetTotal.subtract(previousYearSales))
                                    .divide(previousYearSales, 10, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal("100"))
                                    .setScale(2, RoundingMode.HALF_UP);
                            map.put("qunian"+i, decimalFormat.format(budgetTotal));
                            map.put("qntb"+i, decimalFormat.format(tb)+"%");
                        }else{
                            map.put("qunian"+i, 0);
                            map.put("qntb"+i, 0);
                        }
                    }
                }
            }
        }
    }
    private void createSubjectTotalDataTotal(List<Map<String, Object>> titleName, List<Map<String, Object>> titleDate,int summaryType) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (Map<String, Object> map : titleDate) {
            if(map.get("subjectId")!=null){
                if(map.get("subjectId").equals(96L)){//员工成本
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{2L, 3L, 97L, 98L, 4L, 5L, 99L, 100L, 101L, 11L, 12L, 13L, 102L, 14L, 104L, 15L, 107L};
                    }else if(summaryType==2){
                        ids = new Long[]{2L, 3L, 97L, 98L, 4L, 5L, 99L, 100L, 101L, 11L, 12L, 13L, 102L, 103L, 14L, 104L, 15L, 105L, 106L, 107L};
                    }else if(summaryType==3){
                        ids = new Long[]{2L, 3L, 97L, 98L, 4L, 5L, 99L, 100L, 101L, 11L, 12L, 13L, 102L, 103L, 14L, 104L, 15L, 105L, 107L};
                    }else if(summaryType==4){
                        ids = new Long[]{2L, 3L, 97L, 98L, 4L, 5L, 99L, 100L, 101L, 11L, 12L, 13L, 102L, 14L, 104L, 15L, 107L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(26L)){//差旅费
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{108L, 109L, 176L};
                    }else if(summaryType==2){
                        ids = new Long[]{108L, 109L, 176L};
                    }else if(summaryType==3){
                        ids = new Long[]{};
                    }else if(summaryType==4){
                        ids = new Long[]{108L, 109L, 176L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(112L)){//通讯费
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{110L, 111L, 23L, 113L};
                    }else if(summaryType==2){
                        ids = new Long[]{110L, 111L, 23L, 113L};
                    }else if(summaryType==3){
                        ids = new Long[]{};
                    }else if(summaryType==4){
                        ids = new Long[]{110L, 111L, 23L, 113L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(114L)){//能源费
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{29L, 30L, 71L};
                    }else if(summaryType==2){
                        ids = new Long[]{29L, 30L, 71L};
                    }else if(summaryType==3){
                        ids = new Long[]{};
                    }else if(summaryType==4){
                        ids = new Long[]{29L, 30L, 71L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(31L)){//车辆费用
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{32L, 34L, 35L, 37L, 38L, 115L};
                    }else if(summaryType==2){
                        ids = new Long[]{32L, 34L, 35L, 37L, 38L, 115L};
                    }else if(summaryType==3){
                        ids = new Long[]{};
                    }else if(summaryType==4){
                        ids = new Long[]{32L, 34L, 35L, 37L, 38L, 115L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(125L)){//折旧
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{81L, 178L, 179L, 180L, 181L};
                    }else if(summaryType==2){
                        ids = new Long[]{81L, 124L};
                    }else if(summaryType==3){
                        ids = new Long[]{81L, 178L, 179L, 180L, 181L};
                    }else if(summaryType==4){
                        ids = new Long[]{81L, 178L, 179L, 180L, 181L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(126L)){//无形资产摊销
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{ 127L, 128L, 129L, 182L, 183L ,184L};
                    }else if(summaryType==2){
                        ids = new Long[]{ 127L, 128L, 129L};
                    }else if(summaryType==3){
                        ids = new Long[]{};
                    }else if(summaryType==4){
                        ids = new Long[]{ 127L, 128L, 129L, 182L, 183L ,184L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(82L)){//摊销费用
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{};
                    }else if(summaryType==2){
                        ids = new Long[]{};
                    }else if(summaryType==3){
                        ids = new Long[]{ 127L, 128L, 129L, 130L, 182L, 183L ,184L};
                    }else if(summaryType==4){
                        ids = new Long[]{};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(42L)){//维修及保养费
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{ 75L, 44L, 50L, 48L, 185L };
                    }else if(summaryType==2){
                        ids = new Long[]{ 75L, 44L, 132L, 50L, 48L};
                    }else if(summaryType==3){
                        ids = new Long[]{ };
                    }else if(summaryType==4){
                        ids = new Long[]{ 75L, 44L, 50L, 48L, 185L };
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(51L)){//市场推广费
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{ 139L, 52L, 140L, 141L , 142L};
                    }else if(summaryType==2){
                        ids = new Long[]{ 139L, 52L, 140L, 141L , 142L};
                    }else if(summaryType==3){
                        ids = new Long[]{ };
                    }else if(summaryType==4){
                        ids = new Long[]{ 139L, 52L, 140L, 141L , 142L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(55L)){//专业机构费
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{ 147L , 148L};
                    }else if(summaryType==2){
                        ids = new Long[]{ 147L , 148L};
                    }else if(summaryType==3){
                        ids = new Long[]{ };
                    }else if(summaryType==4){
                        ids = new Long[]{ 147L , 148L};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(208L)){//直接投入
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{ };
                    }else if(summaryType==2){
                        ids = new Long[]{ };
                    }else if(summaryType==3){
                        ids = new Long[]{ 209L , 210L};
                    }else if(summaryType==4){
                        ids = new Long[]{ };
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (map.get("subjectId").equals(204L)){//设计检验费
                    Long[] ids = new Long[]{};
                    if(summaryType==1){
                        ids = new Long[]{};
                    }else if(summaryType==2){
                        ids = new Long[]{};
                    }else if(summaryType==3){
                        ids = new Long[]{205L, 206L};
                    }else if(summaryType==4){
                        ids = new Long[]{};
                    }
                    for (Long id : ids) {
                        for (Map<String, Object> info : titleDate) {
                            if(info.get("subjectId")!=null && info.get("subjectId").equals(id)){
                                for (Map<String, Object> title : titleName) {
                                    if (title.get("key")!=null && !title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                                        BigDecimal dateValue = new BigDecimal(map.get(title.get("key")).toString());
                                        BigDecimal itemValue = new BigDecimal(info.get(title.get("key")).toString());
                                        map.put(title.get("key").toString(), dateValue.add(itemValue));
                                    }else if (title.get("children")!=null){
                                        List<Map<String, Object>> childrenList = (List<Map<String, Object>>) title.get("children");
                                        for (Map<String, Object> children : childrenList) {
                                            BigDecimal dateValue = new BigDecimal(map.get(children.get("key"))==null?"0":map.get(children.get("key")).toString().replace("%",""));
                                            BigDecimal itemValue = new BigDecimal(info.get(children.get("key"))==null?"0":info.get(children.get("key")).toString().replace("%",""));
                                            map.put(children.get("key").toString(), dateValue.add(itemValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                BudgetSubject subject = budgetSubjectService.selectBudgetSubjectById(Long.parseLong(map.get("subjectId").toString()));
                BigDecimal estimatedIncurredTotal = new BigDecimal(map.get("snqm")==null?"0":map.get("snqm").toString());
                BigDecimal budgetTotal = new BigDecimal(map.get("quannian")==null?"0":map.get("quannian").toString());
                if(budgetTotal.compareTo(BigDecimal.ZERO) > 0){
                    if(estimatedIncurredTotal.compareTo(BigDecimal.ZERO) > 0){
                        map.put("qntb0", decimalFormat.format((budgetTotal.subtract(estimatedIncurredTotal)).divide(estimatedIncurredTotal, 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100")))+"%");
                    }
                    for (int i = 1; i < 6; i++){
                        if(subject!=null && subject.getGrowthRate()!=null) {
                            BigDecimal growthRate  = subject.getGrowthRate().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                            BigDecimal previousYearSales = budgetTotal;
                            budgetTotal = budgetTotal.multiply(BigDecimal.ONE.add(growthRate));
                            BigDecimal tb = (budgetTotal.subtract(previousYearSales))
                                    .divide(previousYearSales, 10, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal("100"))
                                    .setScale(2, RoundingMode.HALF_UP);
                            map.put("qunian"+i, decimalFormat.format(budgetTotal));
                            map.put("qntb"+i, decimalFormat.format(tb)+"%");
                        }else{
                            map.put("qunian"+i, 0);
                            map.put("qntb"+i, 0);
                        }
                    }
                }
            }
        }
    }

    private void createItemDate(BudgetItem item, List<Map<String, Object>> titleName, Map<String, Object> data, List<Map<String, Object>> datas, List<Map<String, Object>> itemDate) {
        if(item.getReportingType().equals("1") || item.getReportingType().equals("2")){
            if(item.getTableName().equals("meter")){ //表11计量器
                long[] subjectIds = {44, 46, 49, 61, 68};
                if (datas != null && datas.size() > 0){
                    for (long subjectId : subjectIds) {
                        data = new HashMap<>();
                        for (Map<String, Object> title : titleName) {
                            if (title.get("key").equals("fylx")) {
                                data.put("fylx", "");
                            } else if (title.get("key").equals("fymc")) {
                                data.put("fymc", "");
                                data.put("subjectId", subjectId);
                            } else {
                                data.put(title.get("key").toString(), BigDecimal.ZERO);
                                if (title.get("value")!=null) {
                                    for (Map<String, Object> budgetData : datas) {
                                        if (budgetData.get("reporting_type") == null || budgetData.get("project_type") == null) {
                                            continue;
                                        }
                                        if(subjectId ==44){// 维修保养费-设备-一般性维修
                                            if(!budgetData.get("reporting_type").toString().equals("2") || !budgetData.get("project_type").toString().equals("2")){
                                                continue;
                                            }
                                        }else if(subjectId ==46){// 维修保养费-庭院管
                                            if(!budgetData.get("reporting_type").toString().equals("2") || !budgetData.get("project_type").toString().equals("3")){
                                                continue;
                                            }
                                        } else if(subjectId ==49){// 维修保养费-试验检验费
                                            if(!budgetData.get("reporting_type").toString().equals("2") || !budgetData.get("project_type").toString().equals("1")){
                                                continue;
                                            }
                                        }else if(subjectId ==61){// 安全生产费-设备安全防护支出类
                                            if(!budgetData.get("reporting_type").toString().equals("1") || budgetData.get("project_type").toString().equals("1")){
                                                continue;
                                            }
                                        }else if(subjectId ==68){// 安全生产费-安全设备检验检测类
                                            if(!budgetData.get("reporting_type").toString().equals("1") || !budgetData.get("project_type").toString().equals("1")){
                                                continue;
                                            }
                                        }
                                        if(budgetData.get("dept_id")!=null && budgetData.get("dept_id").equals(title.get("value"))){
                                            BigDecimal dateValue = new BigDecimal(data.get(title.get("key"))==null?"0":data.get(title.get("key")).toString());
                                            BigDecimal budgetValue = new BigDecimal(budgetData.get("total")==null?"0":budgetData.get("total").toString());
                                            data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                        }
                                    }
                                }
                            }
                        }
                        itemDate.add(data);
                    }
                }
            }
            else if(item.getTableName().equals("research")){ //表9
                if (datas != null && datas.size() > 0){
                    for (Map<String, Object> title : titleName) {
                        if (title.get("key").equals("fylx")) {
                            data.put("fylx", "");
                        } else if (title.get("key").equals("fymc")) {
                            data.put("fymc", "");
                            data.put("subjectId", 80);
                        } else {
                            data.put(title.get("key").toString(), BigDecimal.ZERO);
                            if (title.get("value")!=null) {
                                for (Map<String, Object> budgetData : datas) {
                                    if(budgetData.get("dept_id")!=null && budgetData.get("dept_id").equals(title.get("value"))){
                                        BigDecimal dateValue = new BigDecimal(data.get(title.get("key"))==null?"0":data.get(title.get("key")).toString());
                                        BigDecimal budgetValue = new BigDecimal(budgetData.get("fyxj")==null?"0":budgetData.get("fyxj").toString());
                                        data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                    }
                                }
                            }
                        }
                    }
                    itemDate.add(data);
                }
            }
            else if(item.getTableName().equals("dynamic30") || item.getTableName().equals("dynamic61")){ //表42-1、42-2计量器
                long[] subjectIds = {32, 33, 34, 35, 36, 37, 38};
                if (datas != null && datas.size() > 0){
                    for (long subjectId : subjectIds) {
                        data = new HashMap<>();
                        for (Map<String, Object> title : titleName) {
                            if (title.get("key").equals("fylx")) {
                                data.put("fylx", "");
                            } else if (title.get("key").equals("fymc")) {
                                data.put("fymc", "");
                                data.put("subjectId", subjectId);
                            } else {
                                data.put(title.get("key").toString(), BigDecimal.ZERO);
                                if (title.get("value")!=null) {
                                    for (Map<String, Object> budgetData : datas) {
                                        if(budgetData.get("dept_id")!=null && budgetData.get("dept_id").equals(title.get("value"))){
                                            BigDecimal dateValue = new BigDecimal(data.get(title.get("key"))==null?"0":data.get(title.get("key")).toString());
                                            if(subjectId ==32){ //通行费
                                                BigDecimal budgetValue = new BigDecimal(budgetData.get("txf")==null?"0":budgetData.get("txf").toString());
                                                data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                            }else if(subjectId ==33){ //车位费
                                                BigDecimal budgetValue = new BigDecimal(budgetData.get("tcf")==null?"0":budgetData.get("tcf").toString());
                                                data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                            } else if(subjectId ==34){//加油（气、电）费
                                                BigDecimal budgetValue = new BigDecimal(budgetData.get("rlf")==null?"0":budgetData.get("rlf").toString());
                                                data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                            }else if(subjectId ==35){//维修费
                                                BigDecimal budgetValue = new BigDecimal(budgetData.get("xlf")==null?"0":budgetData.get("xlf").toString());
                                                data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                            }else if(subjectId ==36){//年审费
                                                BigDecimal budgetValue = new BigDecimal(budgetData.get("nsf")==null?"0":budgetData.get("nsf").toString());
                                                data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                            }else if(subjectId ==37){//保险费
                                                BigDecimal budgetValue = new BigDecimal(budgetData.get("bxf")==null?"0":budgetData.get("bxf").toString());
                                                data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                            }else if(subjectId ==38){//其他
                                                BigDecimal budgetValue = new BigDecimal(budgetData.get("qtf")==null?"0":budgetData.get("qtf").toString());
                                                data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        itemDate.add(data);
                    }
                }
            }
            else{
                for (Map<String, Object> title : titleName) {
                    if (title.get("key").equals("fylx")) {
                        data.put(title.get("key").toString(), item.getItemType());
                    } else if (title.get("key").equals("fymc")) {
                        data.put(title.get("key").toString(), item.getSubjectName());
                        data.put("subjectId", item.getSubjectId());
                    } else {
                        data.put(title.get("key").toString(), BigDecimal.ZERO);
                        if (title.get("value")!=null) {
                            for (Map<String, Object> budgetData : datas) {
                                if(budgetData.get("dept_id")!=null && budgetData.get("dept_id").equals(title.get("value"))){
                                    BigDecimal dateValue = new BigDecimal(data.get(title.get("key"))==null?"0":data.get(title.get("key")).toString());
                                    BigDecimal budgetValue = new BigDecimal(budgetData.get("total")==null?"0":budgetData.get("total").toString());
                                    data.put(title.get("key").toString(), dateValue.add(budgetValue));
                                }
                            }
                        }
                    }
                }
                itemDate.add(data);
            }
        }else if (item.getReportingType().equals("3") ){ //表10工资
            List<WagesSubject> wagesSubjects = wagesSubjectService.selectWagesSubjectList(new WagesSubject());
            for (WagesSubject wagesSubject : wagesSubjects) {
                data = new HashMap<>();
                data.put("fylx", item.getItemType());
                data.put("fymc", wagesSubject.getTypeName());
                data.put("subjectId", wagesSubject.getType());
                for (Map<String, Object> title : titleName) {
                    if (!title.get("key").equals("fylx") && !title.get("key").equals("fymc")) {
                        if(title.get("value")!=null) {
                            data.put(title.get("key").toString(), BigDecimal.ZERO);
                            for (Map<String, Object> tableWages : datas) {
                                if(tableWages.get("dept_id").toString().equals(title.get("value").toString())){
                                    data.put(title.get("key").toString(), Optional.ofNullable(tableWages.get(wagesSubject.getField())).orElse(0));
                                    break;
                                }else{
                                    data.put(title.get("key").toString(), BigDecimal.ZERO);
                                }
                            }
                        }
                    }
                }
                itemDate.add(data);
            }
        }
    }

    @Override
    public Map<String, Object> itemSummary(Long taskId) {
        ReportingTask task =taskService.selectReportingTaskById(taskId);
        Integer year = task.getYear();
        List<Map<String, Object>> titles = getTitles(year);
        List<Map<String, Object>> datas = new ArrayList<>();
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setReportingType("1");//动态表
        budgetItem.setType("1");//资本性支出
        List<BudgetItem> dItems = itemService.selectBudgetItemList(budgetItem);
        for (BudgetItem item : dItems) {//动态表（表3，表4，表6，表7，表8）
            if(item.getTableName().equals("dynamic1") || item.getTableName().equals("dynamic2") || item.getTableName().equals("dynamic3") || item.getTableName().equals("dynamic4") || item.getTableName().equals("dynamic5"))
            {
                String sql0 = "select field_name from t_table_model t where t.del_flag = '0' and t.field_display_name = '数量' and t.table_name = '"+item.getTableName()+"'";
                List<Map<String, Object>> maps0 = dynamicSqlService.dynamicSelectListSql(sql0);
                if(maps0.size()>0){
                    Map<String, Object> stringObjectMap = maps0.get(0);
                    String fieldName = (String) stringObjectMap.get("field_name");
                    String sql1 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型', '"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+year+"年实际发生数数量', IFNULL( SUM( t.actual_incurred ), 0 ) AS  '"+year+"年实际发生数金额', IFNULL( SUM( te."+fieldName+"), 0 ) AS '"+(year+1)+"年预算数数量', IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额', '' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算'  FROM t_reporting_table_"+item.getTableName()+" t LEFT JOIN t_reporting_table_"+item.getTableName()+"_extension te on t.id = te.id WHERE t.task_id = "+taskId+" AND t.del_flag = '0' AND ( t.STATUS = 3 OR t.STATUS = 5 )";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql1);
                    datas.addAll(maps);

                }else{
                    String sql = "SELECT '郑州区域' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+year+"年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '"+year+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_"+item.getTableName()+" t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                    datas.addAll(maps);
                }
            }
//            String sql = "SELECT '郑州区域' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+year+"年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '"+year+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_"+item.getTableName()+" t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 )";
//            List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
//            datas.addAll(maps);
        }
        budgetItem.setReportingType("2");//固定表
        budgetItem.setType("1");//资本性支出
        List<BudgetItem> gItems = itemService.selectBudgetItemList(budgetItem);

        for (BudgetItem item : gItems) {//固定表
            if(item.getTableName().equals("pipeline")){
                String sql = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                String sql2 = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型', CASE  WHEN t.project_type = '1' THEN '高压管网' WHEN t.project_type = '2' THEN '中压管网' WHEN t.project_type = '3' THEN '低压管网' WHEN t.project_type = '4' THEN '其他' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.project_type";

//                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                datas.addAll(maps);
                String sql3 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型',  '高压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql4 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型',  '中压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql5 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型',  '低压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql6 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型',  '其他' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                List<Map<String, Object>> maps1 = dynamicSqlService.dynamicSelectListSql(sql4);
                List<Map<String, Object>> maps2 = dynamicSqlService.dynamicSelectListSql(sql5);
                List<Map<String, Object>> maps3 = dynamicSqlService.dynamicSelectListSql(sql6);
                datas.addAll(maps);
                datas.addAll(maps1);
                datas.addAll(maps2);
                datas.addAll(maps3);


                String sql31 = "SELECT '郑州区域' AS '部门名称', '新建'  AS '建设类型',  '高压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql41 = "SELECT '郑州区域' AS '部门名称', '新建'  AS '建设类型',  '中压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql51 = "SELECT '郑州区域' AS '部门名称', '新建'  AS '建设类型',  '低压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql61 = "SELECT '郑州区域' AS '部门名称', '新建'  AS '建设类型',  '其他' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps01 = dynamicSqlService.dynamicSelectListSql(sql31);
                List<Map<String, Object>> maps02 = dynamicSqlService.dynamicSelectListSql(sql41);
                List<Map<String, Object>> maps03 = dynamicSqlService.dynamicSelectListSql(sql51);
                List<Map<String, Object>> maps04 = dynamicSqlService.dynamicSelectListSql(sql61);
                datas.addAll(maps01);
                datas.addAll(maps02);
                datas.addAll(maps03);
                datas.addAll(maps04);

            }else if (item.getTableName().equals("station")){
                String sql = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                String sql2 = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN 'CNG加气站' WHEN t.project_type = '2' THEN 'LNG加气站' WHEN t.project_type = '3' THEN 'L-CNG合建站' WHEN t.project_type = '4' THEN 'LPG加气站' WHEN t.project_type = '5' THEN '门站' WHEN t.project_type = '6' THEN '高中压调压站' WHEN t.project_type = '7' THEN '气源厂|储配站' WHEN t.project_type = '8' THEN '其他一' WHEN t.project_type = '9' THEN '其他二' WHEN t.project_type = '10' THEN '其他三' WHEN t.project_type = '11' THEN '分布式光伏' WHEN t.project_type = '12' THEN '分布式能源' WHEN t.project_type = '13' THEN '交通能源' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.project_type";

//                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                datas.addAll(maps);
                List<String> list = new ArrayList<>();
                list.add("CNG加气站");list.add("LNG加气站");list.add("L-CNG合建站");list.add("LPG加气站");list.add("门站");list.add("高中压调压站");
                list.add("气源厂|储配站");list.add("其他一");list.add("其他二");list.add("其他三");list.add("分布式光伏");list.add("分布式能源");
                list.add("交通能源");
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    String sql3 = "SELECT '郑州区域' AS '部门名称', '续建' AS '建设类型','"+s+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = "+(i+1)+" and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                    datas.addAll(maps);
                }
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    String sql3 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','"+s+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = "+(i+1)+" and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                    datas.addAll(maps);
                }
            }else if (item.getTableName().equals("research")){
                String sql="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.intangible_assets+t.fixed_assets+t.subtotal_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                //String sql2="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.intangible_assets+t.fixed_assets+t.subtotal_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status";
                String sql2="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0)), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status";

                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                datas.addAll(maps);
            }else if (item.getTableName().equals("meter")){
                String sql = "SELECT '郑州区域' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM(t.last_accumulated_expenses), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.projected_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_meter t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                datas.addAll(maps);
            }else if (item.getTableName().equals("charity")){
                String sql = "SELECT '郑州区域' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM(t.last_accumulated_expenses), 0 ) AS '"+(year)+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.projected_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_charity t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                datas.addAll(maps);
            }
            else if (item.getTableName().equals("lowvalue")){
                String sql = "SELECT '郑州区域' AS '部门名称','新建' AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.last_accumulated_expenses ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_lowvalue t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.name";
                String sql2 = "SELECT '郑州区域' AS '部门名称','新建' AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.last_accumulated_expenses ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_lowvalue t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                datas.addAll(maps);
            }
            else if (item.getTableName().equals("information_system")){
                String sql="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.intangible_assets+t.fixed_assets ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_information_system t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                String sql2="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0) ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_information_system t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status";

                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                datas.addAll(maps);
            }else if (item.getTableName().equals("housing")){
                String sql = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                String sql2 = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN '房屋建筑物-办公楼' WHEN t.project_type = '2' THEN '房屋建筑物-仓库等' WHEN t.project_type = '3' THEN '房屋建筑物-房屋装修' WHEN t.project_type = '4' THEN '房屋建筑物-土地' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.project_type";

//                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                datas.addAll(maps);

                String sql3 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-办公楼' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql4 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-仓库等' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql5 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-房屋装修' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql6 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-土地' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                datas.addAll(maps);
                List<Map<String, Object>> maps1 = dynamicSqlService.dynamicSelectListSql(sql4);
                datas.addAll(maps1);
                List<Map<String, Object>> maps2 = dynamicSqlService.dynamicSelectListSql(sql5);
                datas.addAll(maps2);
                List<Map<String, Object>> maps3 = dynamicSqlService.dynamicSelectListSql(sql6);
                datas.addAll(maps3);


                String sql31 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','房屋建筑物-办公楼' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql41 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','房屋建筑物-仓库等' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 2  and (t.status = 3 or t.status = 5 ) ";
                String sql51 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','房屋建筑物-房屋装修' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql61 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','房屋建筑物-土地' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps01 = dynamicSqlService.dynamicSelectListSql(sql31);
                datas.addAll(maps01);
                List<Map<String, Object>> maps02 = dynamicSqlService.dynamicSelectListSql(sql41);
                datas.addAll(maps02);
                List<Map<String, Object>> maps03 = dynamicSqlService.dynamicSelectListSql(sql51);
                datas.addAll(maps03);
                List<Map<String, Object>> maps04 = dynamicSqlService.dynamicSelectListSql(sql61);
                datas.addAll(maps04);
            }
        }
        List<Map<String, Object>> status1Data = new ArrayList<>();//续建
        List<Map<String, Object>> status2Data = new ArrayList<>();//新建
        for (Map<String, Object> data : datas) {
            if (data.get("建设类型") instanceof String) {
                String projectStatus = (String) data.get("建设类型");
                if (projectStatus.equals("续建")) {
                    status1Data.add(data);
                } else if (projectStatus.equals("新建")) {
                    status2Data.add(data);
                }
            }
        }

        BigDecimal y2024c1 = BigDecimal.ZERO;
        BigDecimal y2024m1 = BigDecimal.ZERO;
        BigDecimal y2025c1 = BigDecimal.ZERO;
        BigDecimal y2025m1 = BigDecimal.ZERO;
        BigDecimal y20261 = BigDecimal.ZERO;
        BigDecimal y20271 = BigDecimal.ZERO;
        BigDecimal y20281 = BigDecimal.ZERO;
        BigDecimal y20291 = BigDecimal.ZERO;

        BigDecimal y2024c2 = BigDecimal.ZERO;
        BigDecimal y2024m2 = BigDecimal.ZERO;
        BigDecimal y2025c2 = BigDecimal.ZERO;
        BigDecimal y2025m2 = BigDecimal.ZERO;
        BigDecimal y20262 = BigDecimal.ZERO;
        BigDecimal y20272 = BigDecimal.ZERO;
        BigDecimal y20282 = BigDecimal.ZERO;
        BigDecimal y20292 = BigDecimal.ZERO;

        // 遍历 status1Data 列表并计算小计
        for (Map<String, Object> data : status1Data) {
            y2024c1 = y2024c1.add(new BigDecimal(data.get(year+"年实际发生数数量").toString()));
            y2024m1 = y2024m1.add(new BigDecimal(data.get(year+"年实际发生数金额").toString()));
            y2025c1 = y2025c1.add(new BigDecimal(data.get((year+1)+"年预算数数量").toString()));
            y2025m1 = y2025m1.add(new BigDecimal(data.get((year+1)+"年预算数金额").toString()));
            y20261 = y20261.add(new BigDecimal(data.get((year+2)+"年预算").toString()));
            y20271 = y20271.add(new BigDecimal(data.get((year+3)+"年预算").toString()));
            y20281 = y20281.add(new BigDecimal(data.get((year+4)+"年预算").toString()));
            y20291 = y20291.add(new BigDecimal(data.get((year+5)+"年预算").toString()));
        }

        // 遍历 status2Data 列表并计算小计
        for (Map<String, Object> data : status2Data) {
            y2024c2 = y2024c2.add(new BigDecimal(data.get(year+"年实际发生数数量").toString()));
            y2024m2 = y2024m2.add(new BigDecimal(data.get(year+"年实际发生数金额").toString()));
            y2025c2 = y2025c2.add(new BigDecimal(data.get((year+1)+"年预算数数量").toString()));
            y2025m2 = y2025m2.add(new BigDecimal(data.get((year+1)+"年预算数金额").toString()));
            y20262 = y20262.add(new BigDecimal(data.get((year+2)+"年预算").toString()));
            y20272 = y20272.add(new BigDecimal(data.get((year+3)+"年预算").toString()));
            y20282 = y20282.add(new BigDecimal(data.get((year+4)+"年预算").toString()));
            y20292 = y20292.add(new BigDecimal(data.get((year+5)+"年预算").toString()));
        }

        Map<String,Object> map1 = new HashMap<>();//续建小计
        map1.put("部门名称","郑州区域"); map1.put("建设类型","续建"); map1.put("资本性支出项目","小计"); map1.put((year)+"年实际发生数数量",y2024c1); map1.put((year)+"年实际发生数金额",y2024m1);
        map1.put((year+1)+"年预算数数量",y2025c1);map1.put((year+1)+"年预算数金额",y2025m1); map1.put("增速"," "); map1.put((year+2)+"年预算",y20261); map1.put((year+3)+"年预算",y20271); map1.put((year+4)+"年预算",y20281); map1.put((year+5)+"年预算",y20291);

        Map<String,Object> map2 = new HashMap<>();//新建小计
        map2.put("部门名称","郑州区域"); map2.put("建设类型","新建"); map2.put("资本性支出项目","小计"); map2.put((year)+"年实际发生数数量",y2024c2); map2.put((year)+"年实际发生数金额",y2024m2);
        map2.put((year+1)+"年预算数数量",y2025c2);map2.put((year+1)+"年预算数金额",y2025m2); map2.put("增速"," "); map2.put((year+2)+"年预算",y20262); map2.put((year+3)+"年预算",y20272); map2.put((year+4)+"年预算",y20282); map2.put((year+5)+"年预算",y20292);

        Map<String,Object> map3 = new HashMap<>();//合计
        map3.put("部门名称","郑州区域"); map3.put("建设类型","新建"); map3.put("资本性支出项目","合计"); map3.put((year)+"年实际发生数数量",y2024c1.add(y2024c2)); map3.put((year)+"年实际发生数金额",y2024m1.add(y2024m2));
        map3.put((year+1)+"年预算数数量",y2025c1.add(y2025c2));map3.put((year+1)+"年预算数金额",y2025m1.add(y2025m2)); map3.put("增速"," "); map3.put((year+2)+"年预算",y20261.add(y20262)); map3.put((year+3)+"年预算",y20271.add(y20272)); map3.put((year+4)+"年预算",y20281.add(y20282)); map3.put((year+5)+"年预算",y20291.add(y20292));

        Map<String,Object> map = new HashMap<>();
        map.put("续建集合",status1Data); map.put("续建小计",map1); map.put("新建集合",status2Data); map.put("新建小计",map2); map.put("合计",map3);

        List<Map<String, Object>> datas2 = new ArrayList<>();
        datas2.addAll(status1Data);datas2.add(map1);datas2.addAll(status2Data);datas2.add(map2);
        datas2.add(map3);
        // return map;
        Map<String,Object> mm = new HashMap<>();
        mm.put("titleName",titles);
        mm.put("titleDate",datas2);
        return mm;
    }

    @Override
    public Map<String, Object> itemAreaSummary(Long taskId) {
        ReportingTask task =taskService.selectReportingTaskById(taskId);
        Integer year = task.getYear();
        List<Map<String, Object>> titles = getTitles(year);
        List<Map<String, Object>> datas22 = new ArrayList<>();
       // Map<String,Object> mapp = new HashMap<>();
        SysDept sysDept = new SysDept();
        sysDept.setLevel(1);
        sysDept.setDelFlag("0");
        sysDept.setStatus("0");
        List<SysDept> sysDepts = deptMapper.selectDeptList(sysDept);
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setReportingType("1");//动态表
        budgetItem.setType("1");//资本性支出
        List<BudgetItem> dItems = itemService.selectBudgetItemList(budgetItem);
        budgetItem.setReportingType("2");//固定表
        budgetItem.setType("1");//资本性支出
        List<BudgetItem> gItems = itemService.selectBudgetItemList(budgetItem);
        for (SysDept dept : sysDepts) {
            List<Map<String, Object>> datas = new ArrayList<>();
            for (BudgetItem item : dItems) {//动态表（表3，表4，表6，表7，表8）
                if(item.getTableName().equals("dynamic1") || item.getTableName().equals("dynamic2") || item.getTableName().equals("dynamic3") || item.getTableName().equals("dynamic4") || item.getTableName().equals("dynamic5"))
                {
                    String sql0 = "select field_name from t_table_model t where t.del_flag = '0' and t.field_display_name = '数量' and t.table_name = '" + item.getTableName() + "'";
                    List<Map<String, Object>> maps0 = dynamicSqlService.dynamicSelectListSql(sql0);
                    if (maps0.size() > 0) {
                        Map<String, Object> stringObjectMap = maps0.get(0);
                        String fieldName = (String) stringObjectMap.get("field_name");
                        String sql = "SELECT '" + dept.getDeptName() + "' AS '部门名称','新建' as '建设类型','" + item.getTableDisplayName() + "' AS '资本性支出项目',0 AS '" + year + "年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '" + year + "年实际发生数金额',IFNULL( SUM( te." + fieldName + "), 0 ) AS '" + (year + 1) + "年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '" + (year + 1) + "年预算数金额','' AS '增速',0 AS '" + (year + 2) + "年预算',0 AS '" + (year + 3) + "年预算',0 AS '" + (year + 4) + "年预算',0 AS '" + (year + 5) + "年预算' FROM t_reporting_table_" + item.getTableName() + " t LEFT JOIN t_reporting_table_" + item.getTableName() + "_extension te on t.id = te.id WHERE t.task_id = " + taskId + " and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = " + dept.getDeptId() + ")";
                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                        datas.addAll(maps);
                    } else {
                        String sql = "SELECT '" + dept.getDeptName() + "' AS '部门名称','新建' as '建设类型','" + item.getTableDisplayName() + "' AS '资本性支出项目',0 AS '" + year + "年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '" + year + "年实际发生数金额',0 AS '" + (year + 1) + "年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '" + (year + 1) + "年预算数金额','' AS '增速',0 AS '" + (year + 2) + "年预算',0 AS '" + (year + 3) + "年预算',0 AS '" + (year + 4) + "年预算',0 AS '" + (year + 5) + "年预算' FROM t_reporting_table_" + item.getTableName() + " t WHERE t.task_id = " + taskId + " and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = " + dept.getDeptId() + ")";
                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                        datas.addAll(maps);
                    }
                }
//                String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+year+"年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '"+year+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_"+item.getTableName()+" t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";
//                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
//                datas.addAll(maps);
            }

            for (BudgetItem item : gItems) {//固定表
                if(item.getTableName().equals("pipeline")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    String sql2 = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN '高压管网' WHEN t.project_type = '2' THEN '中压管网' WHEN t.project_type = '3' THEN '低压管网' WHEN t.project_type = '4' THEN '其他' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.project_status";

//                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                    datas.addAll(maps);

                    String sql3 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建' AS '建设类型','高压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql4 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','中压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql5 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','低压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql6 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','其他' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                    datas.addAll(maps);
                    List<Map<String, Object>> maps2 = dynamicSqlService.dynamicSelectListSql(sql4);
                    datas.addAll(maps2);
                    List<Map<String, Object>> maps3 = dynamicSqlService.dynamicSelectListSql(sql5);
                    datas.addAll(maps3);
                    List<Map<String, Object>> maps4 = dynamicSqlService.dynamicSelectListSql(sql6);
                    datas.addAll(maps4);


                    String sql31 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','高压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql41 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','中压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql51 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','低压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql61 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','其他' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    List<Map<String, Object>> maps01 = dynamicSqlService.dynamicSelectListSql(sql31);
                    datas.addAll(maps01);
                    List<Map<String, Object>> maps02 = dynamicSqlService.dynamicSelectListSql(sql41);
                    datas.addAll(maps02);
                    List<Map<String, Object>> maps03 = dynamicSqlService.dynamicSelectListSql(sql51);
                    datas.addAll(maps03);
                    List<Map<String, Object>> maps04 = dynamicSqlService.dynamicSelectListSql(sql61);
                    datas.addAll(maps04);
                }else if (item.getTableName().equals("station")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    String sql2 = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN 'CNG加气站' WHEN t.project_type = '2' THEN 'LNG加气站' WHEN t.project_type = '3' THEN 'L-CNG合建站' WHEN t.project_type = '4' THEN 'LPG加气站' WHEN t.project_type = '5' THEN '门站' WHEN t.project_type = '6' THEN '高中压调压站' WHEN t.project_type = '7' THEN '气源厂|储配站' WHEN t.project_type = '8' THEN '其他一' WHEN t.project_type = '9' THEN '其他二' WHEN t.project_type = '10' THEN '其他三' WHEN t.project_type = '11' THEN '分布式光伏' WHEN t.project_type = '12' THEN '分布式能源' WHEN t.project_type = '13' THEN '交通能源' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.project_type";

//                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                    datas.addAll(maps);
                    List<String> list = new ArrayList<>();
                    list.add("CNG加气站");list.add("LNG加气站");list.add("L-CNG合建站");list.add("LPG加气站");list.add("门站");list.add("高中压调压站");
                    list.add("气源厂|储配站");list.add("其他一");list.add("其他二");list.add("其他三");list.add("分布式光伏");list.add("分布式能源");
                    list.add("交通能源");
                    for (int j = 0; j < list.size(); j++) {
                        String s = list.get(j);
                        String sql3 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','"+s+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_status = 1 and t.project_type = "+(j+1)+" and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";

                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                        datas.addAll(maps);
                    }

                    for (int j = 0; j < list.size(); j++) {
                        String s = list.get(j);
                        String sql3 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','"+s+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_status = 2  and t.project_type = "+(j+1)+" and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";

                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                        datas.addAll(maps);
                    }
                }else if (item.getTableName().equals("research")){
                    String sql="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.intangible_assets+t.fixed_assets+t.subtotal_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    //String sql2="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.intangible_assets+t.fixed_assets+t.subtotal_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status";
                    String sql2="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0)), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status";

                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                    datas.addAll(maps);
                }else if (item.getTableName().equals("meter")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM(t.last_accumulated_expenses), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.projected_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_meter t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                    datas.addAll(maps);
                }else if (item.getTableName().equals("charity")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM(t.last_accumulated_expenses), 0 ) AS '"+(year)+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.projected_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_charity t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                    datas.addAll(maps);
                }
                else if (item.getTableName().equals("lowvalue")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.last_accumulated_expenses ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_lowvalue t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.name";
                    String sql2 = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.last_accumulated_expenses ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_lowvalue t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                    datas.addAll(maps);

                }
                else if (item.getTableName().equals("information_system")){
                    String sql="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.intangible_assets+t.fixed_assets ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_information_system t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    String sql2="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0) ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_information_system t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status";

                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                    datas.addAll(maps);
                }else if (item.getTableName().equals("housing")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    String sql2 = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN '房屋建筑物-办公楼' WHEN t.project_type = '2' THEN '房屋建筑物-仓库等' WHEN t.project_type = '3' THEN '房屋建筑物-房屋装修' WHEN t.project_type = '4' THEN '房屋建筑物-土地' WHEN t.project_type IS NULL THEN ' ' END  AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.project_type";

//                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                    datas.addAll(maps);
                    String sql3 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-办公楼' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql4 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-仓库等' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql5 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-房屋装修' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql6 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-土地' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";

                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                    datas.addAll(maps);
                    List<Map<String, Object>> maps1 = dynamicSqlService.dynamicSelectListSql(sql4);
                    datas.addAll(maps1);
                    List<Map<String, Object>> maps2 = dynamicSqlService.dynamicSelectListSql(sql5);
                    datas.addAll(maps2);
                    List<Map<String, Object>> maps3 = dynamicSqlService.dynamicSelectListSql(sql6);
                    datas.addAll(maps3);


                    String sql31 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建'  AS '建设类型','房屋建筑物-办公楼' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql41 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建'  AS '建设类型','房屋建筑物-仓库等' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql51 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建'  AS '建设类型','房屋建筑物-房屋装修' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql61 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建'  AS '建设类型','房屋建筑物-土地' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";

                    List<Map<String, Object>> maps22 = dynamicSqlService.dynamicSelectListSql(sql31);
                    datas.addAll(maps22);
                    List<Map<String, Object>> maps11 = dynamicSqlService.dynamicSelectListSql(sql41);
                    datas.addAll(maps11);
                    List<Map<String, Object>> maps23 = dynamicSqlService.dynamicSelectListSql(sql51);
                    datas.addAll(maps23);
                    List<Map<String, Object>> maps33 = dynamicSqlService.dynamicSelectListSql(sql61);
                    datas.addAll(maps33);

                }
            }

            List<Map<String, Object>> status1Data = new ArrayList<>();//续建
            List<Map<String, Object>> status2Data = new ArrayList<>();//新建
            for (Map<String, Object> data : datas) {
                if (data.get("建设类型") instanceof String) {
                    String projectStatus = (String) data.get("建设类型");
                    if (projectStatus.equals("续建")) {
                        status1Data.add(data);
                    } else if (projectStatus.equals("新建")) {
                        status2Data.add(data);
                    }
                }
            }

            BigDecimal y2024c1 = BigDecimal.ZERO;
            BigDecimal y2024m1 = BigDecimal.ZERO;
            BigDecimal y2025c1 = BigDecimal.ZERO;
            BigDecimal y2025m1 = BigDecimal.ZERO;
            BigDecimal y20261 = BigDecimal.ZERO;
            BigDecimal y20271 = BigDecimal.ZERO;
            BigDecimal y20281 = BigDecimal.ZERO;
            BigDecimal y20291 = BigDecimal.ZERO;

            BigDecimal y2024c2 = BigDecimal.ZERO;
            BigDecimal y2024m2 = BigDecimal.ZERO;
            BigDecimal y2025c2 = BigDecimal.ZERO;
            BigDecimal y2025m2 = BigDecimal.ZERO;
            BigDecimal y20262 = BigDecimal.ZERO;
            BigDecimal y20272 = BigDecimal.ZERO;
            BigDecimal y20282 = BigDecimal.ZERO;
            BigDecimal y20292 = BigDecimal.ZERO;
            // 遍历 status1Data 列表并计算小计
            for (Map<String, Object> data : status1Data) {
                y2024c1 = y2024c1.add(new BigDecimal(data.get(year+"年实际发生数数量").toString()));
                y2024m1 = y2024m1.add(new BigDecimal(data.get(year+"年实际发生数金额").toString()));
                y2025c1 = y2025c1.add(new BigDecimal(data.get((year+1)+"年预算数数量").toString()));
                y2025m1 = y2025m1.add(new BigDecimal(data.get((year+1)+"年预算数金额").toString()));
                y20261 = y20261.add(new BigDecimal(data.get((year+2)+"年预算").toString()));
                y20271 = y20271.add(new BigDecimal(data.get((year+3)+"年预算").toString()));
                y20281 = y20281.add(new BigDecimal(data.get((year+4)+"年预算").toString()));
                y20291 = y20291.add(new BigDecimal(data.get((year+5)+"年预算").toString()));
            }
            // 遍历 status2Data 列表并计算小计
            for (Map<String, Object> data : status2Data) {
                y2024c2 = y2024c2.add(new BigDecimal(data.get(year+"年实际发生数数量").toString()));
                y2024m2 = y2024m2.add(new BigDecimal(data.get(year+"年实际发生数金额").toString()));
                y2025c2 = y2025c2.add(new BigDecimal(data.get((year+1)+"年预算数数量").toString()));
                y2025m2 = y2025m2.add(new BigDecimal(data.get((year+1)+"年预算数金额").toString()));
                y20262 = y20262.add(new BigDecimal(data.get((year+2)+"年预算").toString()));
                y20272 = y20272.add(new BigDecimal(data.get((year+3)+"年预算").toString()));
                y20282 = y20282.add(new BigDecimal(data.get((year+4)+"年预算").toString()));
                y20292 = y20292.add(new BigDecimal(data.get((year+5)+"年预算").toString()));
            }

            Map<String,Object> map1 = new HashMap<>();//续建小计
            map1.put("部门名称",dept.getDeptName()); map1.put("建设类型","续建"); map1.put("资本性支出项目","小计"); map1.put((year)+"年实际发生数数量",y2024c1); map1.put((year)+"年实际发生数金额",y2024m1);
            map1.put((year+1)+"年预算数数量",y2025c1);map1.put((year+1)+"年预算数金额",y2025m1); map1.put("增速"," "); map1.put((year+2)+"年预算",y20261); map1.put((year+3)+"年预算",y20271); map1.put((year+4)+"年预算",y20281); map1.put((year+5)+"年预算",y20291);

            Map<String,Object> map2 = new HashMap<>();//新建小计
            map2.put("部门名称",dept.getDeptName()); map2.put("建设类型","新建"); map2.put("资本性支出项目","小计"); map2.put((year)+"年实际发生数数量",y2024c2); map2.put((year)+"年实际发生数金额",y2024m2);
            map2.put((year+1)+"年预算数数量",y2025c2);map2.put((year+1)+"年预算数金额",y2025m2); map2.put("增速"," "); map2.put((year+2)+"年预算",y20262); map2.put((year+3)+"年预算",y20272); map2.put((year+4)+"年预算",y20282); map2.put((year+5)+"年预算",y20292);

            Map<String,Object> map3 = new HashMap<>();//合计
            map3.put("部门名称",dept.getDeptName()); map3.put("建设类型","新建"); map3.put("资本性支出项目","合计"); map3.put((year)+"年实际发生数数量",y2024c1.add(y2024c2)); map3.put((year)+"年实际发生数金额",y2024m1.add(y2024m2));
            map3.put((year+1)+"年预算数数量",y2025c1.add(y2025c2));map3.put((year+1)+"年预算数金额",y2025m1.add(y2025m2)); map3.put("增速"," "); map3.put((year+2)+"年预算",y20261.add(y20262)); map3.put((year+3)+"年预算",y20271.add(y20272)); map3.put((year+4)+"年预算",y20281.add(y20282)); map3.put((year+5)+"年预算",y20291.add(y20292));

            Map<String,Object> map = new HashMap<>();
            map.put("续建集合",status1Data); map.put("续建小计",map1); map.put("新建集合",status2Data); map.put("新建小计",map2); map.put("合计",map3);

            List<Map<String, Object>> datas2 = new ArrayList<>();
            datas2.addAll(status1Data);datas2.add(map1);datas2.addAll(status2Data);datas2.add(map2);
            datas2.add(map3);
            //  mapp.put(dept.getDeptName(),datas2);
            datas22.addAll(datas2);
        }
        Map<String,Object> mm = new HashMap<>();
        mm.put("titleName",titles);
        mm.put("titleDate",datas22);
        return mm;
        // return datas22;
    }

    @Override
    public void exportTotal(HttpServletResponse response, Long taskId) {
        ReportingTask task =taskService.selectReportingTaskById(taskId);
        Integer year = task.getYear();
        Workbook workbook = new XSSFWorkbook();
        List<Map<String, Object>> titles = getTitles(year);
        List<Map<String, Object>> datas = new ArrayList<>();
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setReportingType("1");//动态表
        budgetItem.setType("1");//资本性支出(表3，表4，表6，表7，表8)
        List<BudgetItem> dItems = itemService.selectBudgetItemList(budgetItem);
        for (BudgetItem item : dItems) {//动态表
            if(item.getTableName().equals("dynamic1") || item.getTableName().equals("dynamic2") || item.getTableName().equals("dynamic3") || item.getTableName().equals("dynamic4") || item.getTableName().equals("dynamic5"))
            {
                String sql0 = "select field_name from t_table_model t where t.del_flag = '0' and t.field_display_name = '数量' and t.table_name = '"+item.getTableName()+"'";
                List<Map<String, Object>> maps0 = dynamicSqlService.dynamicSelectListSql(sql0);
                if(maps0.size()>0){
                    Map<String, Object> stringObjectMap = maps0.get(0);
                    String fieldName = (String) stringObjectMap.get("field_name");
                    String sql1 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型', '"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+year+"年实际发生数数量', IFNULL( SUM( t.actual_incurred ), 0 ) AS  '"+year+"年实际发生数金额', IFNULL( SUM( te."+fieldName+"), 0 ) AS '"+(year+1)+"年预算数数量', IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额', '' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算'  FROM t_reporting_table_"+item.getTableName()+" t LEFT JOIN t_reporting_table_"+item.getTableName()+"_extension te on t.id = te.id WHERE t.task_id = "+taskId+" AND t.del_flag = '0' AND ( t.STATUS = 3 OR t.STATUS = 5 )";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql1);
                    datas.addAll(maps);

                }else{
                    String sql = "SELECT '郑州区域' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+year+"年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '"+year+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_"+item.getTableName()+" t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                    datas.addAll(maps);

                }
            }
//            String sql = "SELECT '郑州区域' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+year+"年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '"+year+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_"+item.getTableName()+" t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";
//            List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
//            datas.addAll(maps);
        }
        budgetItem.setReportingType("2");//固定表
        budgetItem.setType("1");//资本性支出
        List<BudgetItem> gItems = itemService.selectBudgetItemList(budgetItem);
        for (BudgetItem item : gItems) {//固定表
            if(item.getTableName().equals("pipeline")){
                String sql = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                String sql2 = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型', CASE  WHEN t.project_type = '1' THEN '高压管网' WHEN t.project_type = '2' THEN '中压管网' WHEN t.project_type = '3' THEN '低压管网' WHEN t.project_type = '4' THEN '其他' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.project_type";

//                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                datas.addAll(maps);

                String sql3 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型',  '高压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql4 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型',  '中压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql5 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型',  '低压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql6 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型',  '其他' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                List<Map<String, Object>> maps1 = dynamicSqlService.dynamicSelectListSql(sql4);
                List<Map<String, Object>> maps2 = dynamicSqlService.dynamicSelectListSql(sql5);
                List<Map<String, Object>> maps3 = dynamicSqlService.dynamicSelectListSql(sql6);
                datas.addAll(maps);
                datas.addAll(maps1);
                datas.addAll(maps2);
                datas.addAll(maps3);


                String sql31 = "SELECT '郑州区域' AS '部门名称', '新建'  AS '建设类型',  '高压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql41 = "SELECT '郑州区域' AS '部门名称', '新建'  AS '建设类型',  '中压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql51 = "SELECT '郑州区域' AS '部门名称', '新建'  AS '建设类型',  '低压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql61 = "SELECT '郑州区域' AS '部门名称', '新建'  AS '建设类型',  '其他' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps01 = dynamicSqlService.dynamicSelectListSql(sql31);
                List<Map<String, Object>> maps02 = dynamicSqlService.dynamicSelectListSql(sql41);
                List<Map<String, Object>> maps03 = dynamicSqlService.dynamicSelectListSql(sql51);
                List<Map<String, Object>> maps04 = dynamicSqlService.dynamicSelectListSql(sql61);
                datas.addAll(maps01);
                datas.addAll(maps02);
                datas.addAll(maps03);
                datas.addAll(maps04);


            }else if (item.getTableName().equals("station")){
                String sql = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                String sql2 = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN 'CNG加气站' WHEN t.project_type = '2' THEN 'LNG加气站' WHEN t.project_type = '3' THEN 'L-CNG合建站' WHEN t.project_type = '4' THEN 'LPG加气站' WHEN t.project_type = '5' THEN '门站' WHEN t.project_type = '6' THEN '高中压调压站' WHEN t.project_type = '7' THEN '气源厂|储配站' WHEN t.project_type = '8' THEN '其他一' WHEN t.project_type = '9' THEN '其他二' WHEN t.project_type = '10' THEN '其他三' WHEN t.project_type = '11' THEN '分布式光伏' WHEN t.project_type = '12' THEN '分布式能源' WHEN t.project_type = '13' THEN '交通能源'  WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.project_type";

//                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                datas.addAll(maps);
                List<String> list = new ArrayList<>();
                list.add("CNG加气站");list.add("LNG加气站");list.add("L-CNG合建站");list.add("LPG加气站");list.add("门站");list.add("高中压调压站");
                list.add("气源厂|储配站");list.add("其他一");list.add("其他二");list.add("其他三");list.add("分布式光伏");list.add("分布式能源");
                list.add("交通能源");
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    String sql3 = "SELECT '郑州区域' AS '部门名称', '续建' AS '建设类型','"+s+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = "+(i+1)+" and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                    datas.addAll(maps);
                }
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    String sql3 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','"+s+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = "+(i+1)+" and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                    datas.addAll(maps);
                }


            }else if (item.getTableName().equals("research")){
                String sql="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.intangible_assets+t.fixed_assets+t.subtotal_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
               // String sql2="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.intangible_assets+t.fixed_assets+t.subtotal_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status";
                String sql2="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0)), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status";


                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                datas.addAll(maps);
            }else if (item.getTableName().equals("meter")){
                String sql = "SELECT '郑州区域' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM(t.last_accumulated_expenses), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.projected_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_meter t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                datas.addAll(maps);
            }else if (item.getTableName().equals("charity")){
                String sql = "SELECT '郑州区域' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM(t.last_accumulated_expenses), 0 ) AS '"+(year)+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.projected_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_charity t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                datas.addAll(maps);
            }
            else if (item.getTableName().equals("lowvalue")){
                String sql = "SELECT '郑州区域' AS '部门名称','新建' AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.last_accumulated_expenses ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_lowvalue t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.name";
                String sql2 = "SELECT '郑州区域' AS '部门名称','新建' AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.last_accumulated_expenses ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_lowvalue t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) ";

                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                datas.addAll(maps);
            }
            else if (item.getTableName().equals("information_system")){
                String sql="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.intangible_assets+t.fixed_assets ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_information_system t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                String sql2="SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0) ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_information_system t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status";

                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                datas.addAll(maps);
            }else if (item.getTableName().equals("housing")){
                String sql = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.name";
                String sql2 = "SELECT '郑州区域' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE WHEN t.project_type = '1' THEN '房屋建筑物-办公楼' WHEN t.project_type = '2' THEN '房屋建筑物-仓库等' WHEN t.project_type = '3' THEN '房屋建筑物-房屋装修' WHEN t.project_type = '4' THEN '房屋建筑物-土地' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) GROUP BY t.project_status, t.project_type";

//                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                datas.addAll(maps);

                String sql3 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-办公楼' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql4 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-仓库等' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql5 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-房屋装修' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                String sql6 = "SELECT '郑州区域' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-土地' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                datas.addAll(maps);
                List<Map<String, Object>> maps1 = dynamicSqlService.dynamicSelectListSql(sql4);
                datas.addAll(maps1);
                List<Map<String, Object>> maps2 = dynamicSqlService.dynamicSelectListSql(sql5);
                datas.addAll(maps2);
                List<Map<String, Object>> maps3 = dynamicSqlService.dynamicSelectListSql(sql6);
                datas.addAll(maps3);


                String sql31 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','房屋建筑物-办公楼' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql41 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','房屋建筑物-仓库等' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 2  and (t.status = 3 or t.status = 5 ) ";
                String sql51 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','房屋建筑物-房屋装修' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                String sql61 = "SELECT '郑州区域' AS '部门名称', '新建' AS '建设类型','房屋建筑物-土地' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) ";
                List<Map<String, Object>> maps01 = dynamicSqlService.dynamicSelectListSql(sql31);
                datas.addAll(maps01);
                List<Map<String, Object>> maps02 = dynamicSqlService.dynamicSelectListSql(sql41);
                datas.addAll(maps02);
                List<Map<String, Object>> maps03 = dynamicSqlService.dynamicSelectListSql(sql51);
                datas.addAll(maps03);
                List<Map<String, Object>> maps04 = dynamicSqlService.dynamicSelectListSql(sql61);
                datas.addAll(maps04);

            }
        }
        List<Map<String, Object>> status1Data = new ArrayList<>();//续建
        List<Map<String, Object>> status2Data = new ArrayList<>();//新建
        for (Map<String, Object> data : datas) {
            if (data.get("建设类型") instanceof String) {
                String projectStatus = (String) data.get("建设类型");
                if (projectStatus.equals("续建")) {
                    status1Data.add(data);
                } else if (projectStatus.equals("新建")) {
                    status2Data.add(data);
                }
            }
        }

        BigDecimal y2024c1 = BigDecimal.ZERO;
        BigDecimal y2024m1 = BigDecimal.ZERO;
        BigDecimal y2025c1 = BigDecimal.ZERO;
        BigDecimal y2025m1 = BigDecimal.ZERO;
        BigDecimal y20261 = BigDecimal.ZERO;
        BigDecimal y20271 = BigDecimal.ZERO;
        BigDecimal y20281 = BigDecimal.ZERO;
        BigDecimal y20291 = BigDecimal.ZERO;

        BigDecimal y2024c2 = BigDecimal.ZERO;
        BigDecimal y2024m2 = BigDecimal.ZERO;
        BigDecimal y2025c2 = BigDecimal.ZERO;
        BigDecimal y2025m2 = BigDecimal.ZERO;
        BigDecimal y20262 = BigDecimal.ZERO;
        BigDecimal y20272 = BigDecimal.ZERO;
        BigDecimal y20282 = BigDecimal.ZERO;
        BigDecimal y20292 = BigDecimal.ZERO;
        // 遍历 status1Data 列表并计算小计
        for (Map<String, Object> data : status1Data) {
            y2024c1 = y2024c1.add(new BigDecimal(data.get(year+"年实际发生数数量").toString()));
            y2024m1 = y2024m1.add(new BigDecimal(data.get(year+"年实际发生数金额").toString()));
            y2025c1 = y2025c1.add(new BigDecimal(data.get((year+1)+"年预算数数量").toString()));
            y2025m1 = y2025m1.add(new BigDecimal(data.get((year+1)+"年预算数金额").toString()));
            y20261 = y20261.add(new BigDecimal(data.get((year+2)+"年预算").toString()));
            y20271 = y20271.add(new BigDecimal(data.get((year+3)+"年预算").toString()));
            y20281 = y20281.add(new BigDecimal(data.get((year+4)+"年预算").toString()));
            y20291 = y20291.add(new BigDecimal(data.get((year+5)+"年预算").toString()));
        }
        // 遍历 status2Data 列表并计算小计
        for (Map<String, Object> data : status2Data) {
            y2024c2 = y2024c2.add(new BigDecimal(data.get(year+"年实际发生数数量").toString()));
            y2024m2 = y2024m2.add(new BigDecimal(data.get(year+"年实际发生数金额").toString()));
            y2025c2 = y2025c2.add(new BigDecimal(data.get((year+1)+"年预算数数量").toString()));
            y2025m2 = y2025m2.add(new BigDecimal(data.get((year+1)+"年预算数金额").toString()));
            y20262 = y20262.add(new BigDecimal(data.get((year+2)+"年预算").toString()));
            y20272 = y20272.add(new BigDecimal(data.get((year+3)+"年预算").toString()));
            y20282 = y20282.add(new BigDecimal(data.get((year+4)+"年预算").toString()));
            y20292 = y20292.add(new BigDecimal(data.get((year+5)+"年预算").toString()));
        }

        Map<String,Object> map1 = new HashMap<>();//续建小计
        map1.put("部门名称","郑州区域"); map1.put("建设类型","续建"); map1.put("资本性支出项目","小计"); map1.put((year)+"年实际发生数数量",y2024c1); map1.put((year)+"年实际发生数金额",y2024m1);
        map1.put((year+1)+"年预算数数量",y2025c1);map1.put((year+1)+"年预算数金额",y2025m1); map1.put("增速"," "); map1.put((year+2)+"年预算",y20261); map1.put((year+3)+"年预算",y20271); map1.put((year+4)+"年预算",y20281); map1.put((year+5)+"年预算",y20291);

        Map<String,Object> map2 = new HashMap<>();//新建小计
        map2.put("部门名称","郑州区域"); map2.put("建设类型","新建"); map2.put("资本性支出项目","小计"); map2.put((year)+"年实际发生数数量",y2024c2); map2.put((year)+"年实际发生数金额",y2024m2);
        map2.put((year+1)+"年预算数数量",y2025c2);map2.put((year+1)+"年预算数金额",y2025m2); map2.put("增速"," "); map2.put((year+2)+"年预算",y20262); map2.put((year+3)+"年预算",y20272); map2.put((year+4)+"年预算",y20282); map2.put((year+5)+"年预算",y20292);

        Map<String,Object> map3 = new HashMap<>();//合计
        map3.put("部门名称","郑州区域"); map3.put("建设类型","新建"); map3.put("资本性支出项目","合计"); map3.put((year)+"年实际发生数数量",y2024c1.add(y2024c2)); map3.put((year)+"年实际发生数金额",y2024m1.add(y2024m2));
        map3.put((year+1)+"年预算数数量",y2025c1.add(y2025c2));map3.put((year+1)+"年预算数金额",y2025m1.add(y2025m2)); map3.put("增速"," "); map3.put((year+2)+"年预算",y20261.add(y20262)); map3.put((year+3)+"年预算",y20271.add(y20272)); map3.put((year+4)+"年预算",y20281.add(y20282)); map3.put((year+5)+"年预算",y20291.add(y20292));

        Map<String,Object> map = new HashMap<>();
        map.put("续建集合",status1Data); map.put("续建小计",map1); map.put("新建集合",status2Data); map.put("新建小计",map2); map.put("合计",map3);

        List<Map<String, Object>> datas2 = new ArrayList<>();
        datas2.addAll(status1Data);datas2.add(map1);datas2.addAll(status2Data);datas2.add(map2);
        datas2.add(map3);
        List<Map<String, Object>> lmap = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        result.put("datas0",datas2);
        result.put("titles0",titles);
        result.put("sheetName0","郑州区域");
        lmap.add(result);
        //总体汇总
        ExportSheetUtil.exportSingleHeaderExcel2(workbook,lmap);
        //区域汇总
        areaExport(year,workbook,titles,taskId);
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

    @Override
    public List<Map<String, Object>> statisticsAnalysis(Long deptId,Long versionId,int type) {
        List<Map<String, Object>> datas = new ArrayList<>();
        List<Map<String, Object>> infos = new ArrayList<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<Map<String, Object>> titles =  creatTatleList(99,new ReportingTask());
        List<Map<String, Object>> actualCostsDatas = new ArrayList<>();
        ReportingVersion version = null;
        if(versionId==null){
            version = reportingVersionService.selectLastVersion();
        }else{
            version = reportingVersionService.selectReportingVersionById(versionId);
        }
        SysDept sysDept = deptService.selectDeptById(deptId);
        if(sysDept==null){
            return datas;
        }
        sysDept.setChildren(deptService.selectChildrenDeptById(deptId));
        if(version==null){
            return datas;
        }
        ReportingTask task = taskService.selectReportingTaskById(version.getTaskId());
        if(task!=null && version.getStatus().equals("2") ){
            extracted(task.getId(), 2, version.getId(), titles, itemDate, infos,false);
        }
        if(task != null && task.getBudgetYear()!=null){
            actualCostsDatas = actualCostsService.selectTotals(task.getBudgetYear());
        }
        int index = 1;
        List<Long> subjectIds = Arrays.asList(2L,3L,4L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L,21L,22L,23L,24L,25L,26L,27L,28L,29L,30L,84L,85L,86L,87L,32L,33L,34L,35L,36L,37L,38L,39L,40L,41L,81L,82L,43L,44L,45L,46L,47L,48L,49L,75L,51L,52L,53L,74L,76L,54L,55L,56L,57L,59L,58L,61L,62L,63L,64L,65L,66L,67L,68L,69L,70L,229L,72L,73L);
        Map<String, Object> data = new HashMap<>();
        data.put("name","一、销售、管理费用合计");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        subjectIds = List.of(2L);
        data = new HashMap<>();
        data.put("name","    1、员工成本-工资");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        subjectIds = List.of(3L);
        data = new HashMap<>();
        data.put("name","    2、员工成本-劳务费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(4L);
        data.put("name","    3、员工成本-福利费-其他");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(6L,7L,8L,9L,10L);
        data.put("name","    4、员工成本-社会保险费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(6L);
        data.put("name","        ①员工成本-社会保险费-基本医疗保险");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(7L);
        data.put("name","        ②员工成本-社会保险费-基本养老保险");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(8L);
        data.put("name","        ③员工成本-社会保险费-失业保险");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(9L);
        data.put("name","        ④员工成本-社会保险费-工伤保险");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(10L);
        data.put("name","        ⑤员工成本-社会保险费-生育保险");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(11L);
        data.put("name","    5、员工成本-职工住房公积金");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(12L);
        data.put("name","    6、员工成本-职工教育经费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(13L);
        data.put("name","    7、员工成本-工会经费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(14L);
        data.put("name","    8、员工成本-劳动保护费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(15L);
        data.put("name","    9、员工成本-企业年金");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(21L,22L,23L,24L,25L);
        data.put("name","    10、办公费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(21L);
        data.put("name","        ①文具印刷费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(22L);
        data.put("name","        ②图书资料费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(23L);
        data.put("name","        ③通讯费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(24L);
        data.put("name","        ④邮电费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(25L);
        data.put("name","        ⑤其他（办公费）");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(26L);
        data.put("name","    11、差旅费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(27L);
        data.put("name","    12、交通费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(28L);
        data.put("name","    13、会议费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(29L);
        data.put("name","    14、能源费-水费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(30L);
        data.put("name","    15、能源费-电费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(84L,85L,86L,87L);
        data.put("name","    16、租赁费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(84L);
        data.put("name","        ①房屋租赁费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(86L);
        data.put("name","        ②土地租赁费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(87L);
        data.put("name","        ③设备租赁费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(85L);
        data.put("name","        ④车辆租赁费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of();
        data.put("name","        ⑤其他（租赁费用）");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(32L,33L,34L,35L,36L,37L,38L);
        data.put("name","    17、车辆费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(32L);
        data.put("name","        ①车辆费用-路桥费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(33L);
        data.put("name","        ②车辆费用-车位费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(34L);
        data.put("name","        ③车辆费用-加油（气）费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(35L);
        data.put("name","        ④车辆规费-维修费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(36L);
        data.put("name","        ⑤车辆规费-年审费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(37L);
        data.put("name","        ⑥保险费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(38L);
        data.put("name","        ⑦其他（车辆费用）");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(39L);
        data.put("name","    18、耗用品费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(40L);
        data.put("name","    19、交际应酬费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(41L);
        data.put("name","    20、保险费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(81L);
        data.put("name","    21、折旧费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(82L);
        data.put("name","    22、摊销费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(43L,44L,45L,46L,47L,48L,49L,75L);
        data.put("name","    23、维修及保养费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(43L);
        data.put("name","        ①设备-大中修费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(44L);
        data.put("name","        ②设备-一般性维修");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(75L);
        data.put("name","        ③建筑物及其附属物");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(45L);
        data.put("name","        ④主干管");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(46L);
        data.put("name","        ⑤庭院管");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(47L);
        data.put("name","        ⑥办公维修（电脑耗材）");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(48L);
        data.put("name","        ⑦维修及保养费-其他");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(49L);
        data.put("name","        ⑧试验检验费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(51L);
        data.put("name","    24、市场推广费-市场推广费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(52L);
        data.put("name","    25、市场推广费-广告费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(53L);
        data.put("name","    26、安保服务费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(74L);
        data.put("name","    27、消防费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(76L);
        data.put("name","    28、物业管理费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(54L);
        data.put("name","    29、清洁费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(55L);
        data.put("name","    30、专业机构费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        /*
        data = new HashMap<>();
        subjectIds = Arrays.asList(88L);
        data.put("name","        ①专业机构费-审计类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(89L);
        data.put("name","        ②专业机构费-法律类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(90L);
        data.put("name","        ③专业机构费-信息类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(91L);
        data.put("name","        ④专业机构费-技术咨询类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);
        */

        data = new HashMap<>();
        subjectIds = List.of(56L);
        data.put("name","    31、其他-绿化养护费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(57L);
        data.put("name","    32、其他-协会会费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(59L);
        data.put("name","    33、其他-其他");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(58L);
        data.put("name","    34、其他-机物料消耗");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(61L,62L,63L,64L,65L,66L,67L,68L,69L,70L);
        data.put("name","    35、安全生产费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(61L);
        data.put("name","        ①设备安全防护支出类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(62L);
        data.put("name","        ②应急设备支出演练类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(63L);
        data.put("name","        ③管网维护整改评估类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(64L);
        data.put("name","        ④安全生产检查咨询类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(65L);
        data.put("name","        ⑤人员防护支出类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(66L);
        data.put("name","        ⑥安全宣传教育及培训类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(67L);
        data.put("name","        ⑦安全生产推广应用类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(68L);
        data.put("name","        ⑧安全设备检验检测类");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(69L);
        data.put("name","        ⑨安全生产责任保险支出");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(70L);
        data.put("name","        ⑩其他安全生产费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of();
        data.put("name","        安全生产费余额");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(228L);
        data.put("name","        安全生产费计提额");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(229L);
        data.put("name","    36、能源费-气费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(72L);
        data.put("name","    37、运输费用及装卸驳运费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(73L);
        data.put("name","    38、A组织工作经费");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(77L,78L,79L,80L);
        data.put("name","二、研发费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(77L);
        data.put("name","    1、人工成本");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(78L);
        data.put("name","    2、折旧费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(79L);
        data.put("name","    3、摊销费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = List.of(80L);
        data.put("name","    4、其他-研发费用");
        data.put("index",index++);
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        data = new HashMap<>();
        subjectIds = Arrays.asList(2L,3L,4L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L,21L,22L,23L,24L,25L,26L,27L,28L,29L,30L,84L,85L,86L,87L,32L,33L,34L,35L,36L,37L,38L,39L,40L,41L,81L,82L,43L,44L,45L,46L,47L,48L,49L,75L,51L,52L,53L,74L,76L,54L,55L,56L,57L,59L,58L,61L,62L,63L,64L,65L,66L,67L,68L,69L,70L,229L,72L,73L,77L,78L,79L,80L);
        data.put("name","合计");
        data.put("index",index++);
        /*
        data.put("budget",getSum(infos,subjectIds,deptId));
        getActualCostsInfo(data,actualCostsDatas,subjectIds,deptId);
        total =(BigDecimal) data.get("total");
        budget = (BigDecimal) data.get("budget");
        ratio = BigDecimal.ZERO;
        if (budget != null && budget.compareTo(BigDecimal.ZERO) != 0) {
            ratio = total.multiply(new BigDecimal("100")).divide(budget, 2, RoundingMode.HALF_UP);
        }
        data.put("ratio",ratio+"%");
        */
        creatDate(data,infos,actualCostsDatas,subjectIds,sysDept,type);
        datas.add(data);

        return datas;
    }

    @Override
    public void exportStatisticsAnalysis(HttpServletResponse response, Long deptId, Long versionId) {
        Workbook workbook = new XSSFWorkbook();
        List<Map<String, Object>> datas = new ArrayList<>();
        List<Map<String, Object>> titles = new ArrayList<>();
        SysDept sysDept = deptService.selectDeptById(deptId);
        if(sysDept!=null){
            Map<String, Object> title = new HashMap<>();
            title.put("name", "科目/月份");
            title.put("key", "name");
            titles.add(title);
            title = new HashMap<>();
            title.put("name", "预算");
            title.put("key", "budget");
            titles.add(title);
            title = new HashMap<>();
            title.put("name", "实际累计");
            title.put("key", "total");
            titles.add(title);
            title = new HashMap<>();
            title.put("name", "完成预算比例");
            title.put("key", "ratio");
            titles.add(title);
            for (int i = 1; i <= 12; i++) {
                title = new HashMap<>();
                title.put("name", i+"月");
                title.put("key", "month"+1);
                titles.add(title);
            }
            title = new HashMap<>();
            title.put("name", "累计");
            title.put("key", "total");
            titles.add(title);

            Map<String, Object> data =new HashMap<>();
            data.put("datas",statisticsAnalysis(deptId, versionId,2));
            data.put("titles",titles);
            data.put("sheetName",sysDept.getDeptName());
            datas.add(data);
            List<SysDept> sysDepts = deptService.selectChildrenDeptById(deptId);
            for (SysDept children : sysDepts) {
                data =new HashMap<>();
                data.put("datas",statisticsAnalysis(children.getDeptId(), versionId,2));
                data.put("titles",titles);
                data.put("sheetName",children.getParentName()+"-"+children.getDeptName());
                datas.add(data);
            }
        }
        ExportSheetUtil.exportSingleHeaderExcel(workbook,datas);

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

    @Override
    public List<Map<String, Object>> budgetVersionWarningSummary(Long taskId, Long versionId) {
        List<Map<String, Object>> titleDate = new ArrayList<>();
        List<Map<String, Object>> itemDate = new ArrayList<>();
        List<Map<String, Object>> titleName =  creatTatleList(99,new ReportingTask());
        extracted(taskId, 2, versionId, titleName, itemDate, titleDate,false);
        return titleDate;
    }

    private void creatDate(Map<String, Object> data,List<Map<String, Object>> infos, List<Map<String, Object>> actualCostsDatas,List<Long> subjectIds, SysDept dept,Integer type){
        BigDecimal budget = getSumByDept(infos,subjectIds,dept);
        if(type==1){ //设置千分位
            DecimalFormat df = new DecimalFormat("¥ #,##0.00;¥ -#,##0.00");
            data.put("budget",df.format(budget));
        }else if(type==2){
            data.put("budget",budget);
        }
        getActualCostsInfoByDept(data,actualCostsDatas,subjectIds,dept,type);
        BigDecimal total =(BigDecimal) data.get("totalData");
        BigDecimal ratio = BigDecimal.ZERO;
        if (budget != null && budget.compareTo(BigDecimal.ZERO) != 0) {
            ratio = total.multiply(new BigDecimal("100")).divide(budget, 2, RoundingMode.HALF_UP);
        }
        data.put("ratio",ratio+"%");
    }

    private void getActualCostsInfo(Map<String, Object> data,List<Map<String, Object>> dates, List<Long> ids, Long deptId){
        for (int i = 0; i <= 12; i++) {
            BigDecimal sum = BigDecimal.ZERO;
            for (Map<String, Object> date : dates) {
                if(deptId!=null && deptId.longValue()==Long.parseLong(date.get("dept_id").toString()) && ids.contains(date.get("subject_id"))){
                    if (date.get("total") !=null && date.get("month")!=null && Integer.parseInt(date.get("month").toString())==i){
                        sum = sum.add((BigDecimal) date.get("total"));
                    }
                }
            }
            if(i==0){
                data.put("total",sum);
            }else{
                data.put("month"+i,sum);
            }
        }
    }

    private void getActualCostsInfoByDept(Map<String, Object> data,List<Map<String, Object>> dates, List<Long> ids, SysDept dept,int type){
        DecimalFormat df = new DecimalFormat("¥ #,##0.00;¥ -#,##0.00");
        for (int i = 0; i <= 12; i++) {
            BigDecimal sum = BigDecimal.ZERO;
            for (Map<String, Object> date : dates) {
                if(dept.getDeptId()!=null && dept.getDeptId().longValue() ==Long.parseLong(date.get("dept_id").toString()) && ids.contains(date.get("subject_id"))){
                    if (date.get("total") !=null && date.get("month")!=null && Integer.parseInt(date.get("month").toString())==i){
                        sum = sum.add((BigDecimal) date.get("total"));
                    }
                }
                for (int j = 0; j < dept.getChildren().size(); j++) {
                    SysDept children = dept.getChildren().get(j);
                    if(children.getDeptId()!=null && children.getDeptId().longValue()==Long.parseLong(date.get("dept_id").toString()) && ids.contains(date.get("subject_id"))){
                        if (date.get("total") !=null && date.get("month")!=null && Integer.parseInt(date.get("month").toString())==i){
                            sum = sum.add((BigDecimal) date.get("total"));
                        }
                    }
                }
            }
            if(i==0){
                if(type==1){
                    data.put("total",df.format(sum));
                }else if(type==2){
                    data.put("total",sum);
                }
                data.put("totalData",sum);
            }else{
                if(type==1){
                    data.put("month"+i,df.format(sum));
                }else if(type==2){
                    data.put("month"+i,sum);
                }
                data.put("monthData"+i,sum);
            }
        }
    }

    public void areaExport(int year,Workbook workbook, List<Map<String,Object>> titles, Long taskId){
        List<Map<String, Object>> datas22 = new ArrayList<>();
        SysDept sysDept = new SysDept();
        sysDept.setLevel(1);
        sysDept.setDelFlag("0");
        sysDept.setStatus("0");
        List<SysDept> sysDepts = deptMapper.selectDeptList(sysDept);
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setReportingType("1");//动态表
        budgetItem.setType("1");//资本性支出(表3，表4，表6，表7，表8)
        List<BudgetItem> dItems = itemService.selectBudgetItemList(budgetItem);
        budgetItem.setReportingType("2");//固定表
        budgetItem.setType("1");//资本性支出
        List<BudgetItem> gItems = itemService.selectBudgetItemList(budgetItem);
        for (int i = 0; i <sysDepts.size() ; i++) {
            SysDept dept = sysDepts.get(i);
            List<Map<String, Object>> datas = new ArrayList<>();
            for (BudgetItem item : dItems) {//动态表
                if(item.getTableName().equals("dynamic1") || item.getTableName().equals("dynamic2") || item.getTableName().equals("dynamic3") || item.getTableName().equals("dynamic4") || item.getTableName().equals("dynamic5"))
                {
                    String sql0 = "select field_name from t_table_model t where t.del_flag = '0' and t.field_display_name = '数量' and t.table_name = '" + item.getTableName() + "'";
                    List<Map<String, Object>> maps0 = dynamicSqlService.dynamicSelectListSql(sql0);
                    if (maps0.size() > 0) {
                        Map<String, Object> stringObjectMap = maps0.get(0);
                        String fieldName = (String) stringObjectMap.get("field_name");
                        String sql = "SELECT '" + dept.getDeptName() + "' AS '部门名称','新建' as '建设类型','" + item.getTableDisplayName() + "' AS '资本性支出项目',0 AS '" + year + "年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '" + year + "年实际发生数金额',IFNULL( SUM( te." + fieldName + "), 0 ) AS '" + (year + 1) + "年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '" + (year + 1) + "年预算数金额','' AS '增速',0 AS '" + (year + 2) + "年预算',0 AS '" + (year + 3) + "年预算',0 AS '" + (year + 4) + "年预算',0 AS '" + (year + 5) + "年预算' FROM t_reporting_table_" + item.getTableName() + " t LEFT JOIN t_reporting_table_" + item.getTableName() + "_extension te on t.id = te.id WHERE t.task_id = " + taskId + " and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = " + dept.getDeptId() + ")";
                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                        datas.addAll(maps);
                    } else {
                        String sql = "SELECT '" + dept.getDeptName() + "' AS '部门名称','新建' as '建设类型','" + item.getTableDisplayName() + "' AS '资本性支出项目',0 AS '" + year + "年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '" + year + "年实际发生数金额',0 AS '" + (year + 1) + "年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '" + (year + 1) + "年预算数金额','' AS '增速',0 AS '" + (year + 2) + "年预算',0 AS '" + (year + 3) + "年预算',0 AS '" + (year + 4) + "年预算',0 AS '" + (year + 5) + "年预算' FROM t_reporting_table_" + item.getTableName() + " t WHERE t.task_id = " + taskId + " and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = " + dept.getDeptId() + ")";
                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                        datas.addAll(maps);
                    }
                }
//                String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+year+"年实际发生数数量',IFNULL( SUM(t.actual_incurred), 0 ) AS '"+year+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.budget), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_"+item.getTableName()+" t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";
//                List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
//                datas.addAll(maps);
            }

            for (BudgetItem item : gItems) {//固定表
                if(item.getTableName().equals("pipeline")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    String sql2 = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN '高压管网' WHEN t.project_type = '2' THEN '中压管网' WHEN t.project_type = '3' THEN '低压管网' WHEN t.project_type = '4' THEN '其他' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.project_type";

//                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                    datas.addAll(maps);

                    String sql3 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建' AS '建设类型','高压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql4 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','中压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql5 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','低压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql6 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','其他' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                    datas.addAll(maps);
                    List<Map<String, Object>> maps2 = dynamicSqlService.dynamicSelectListSql(sql4);
                    datas.addAll(maps2);
                    List<Map<String, Object>> maps3 = dynamicSqlService.dynamicSelectListSql(sql5);
                    datas.addAll(maps3);
                    List<Map<String, Object>> maps4 = dynamicSqlService.dynamicSelectListSql(sql6);
                    datas.addAll(maps4);


                    String sql31 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','高压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql41 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','中压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql51 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','低压管网' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql61 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','其他' AS '资本性支出项目',IFNULL( SUM( t.completed_length ), 0 ) AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.expected_length ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_pipeline t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    List<Map<String, Object>> maps01 = dynamicSqlService.dynamicSelectListSql(sql31);
                    datas.addAll(maps01);
                    List<Map<String, Object>> maps02 = dynamicSqlService.dynamicSelectListSql(sql41);
                    datas.addAll(maps02);
                    List<Map<String, Object>> maps03 = dynamicSqlService.dynamicSelectListSql(sql51);
                    datas.addAll(maps03);
                    List<Map<String, Object>> maps04 = dynamicSqlService.dynamicSelectListSql(sql61);
                    datas.addAll(maps04);


                }else if (item.getTableName().equals("station")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    String sql2 = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN 'CNG加气站' WHEN t.project_type = '2' THEN 'LNG加气站' WHEN t.project_type = '3' THEN 'L-CNG合建站' WHEN t.project_type = '4' THEN 'LPG加气站' WHEN t.project_type = '5' THEN '门站' WHEN t.project_type = '6' THEN '高中压调压站' WHEN t.project_type = '7' THEN '气源厂|储配站' WHEN t.project_type = '8' THEN '其他一' WHEN t.project_type = '9' THEN '其他二' WHEN t.project_type = '10' THEN '其他三' WHEN t.project_type = '11' THEN '分布式光伏' WHEN t.project_type = '12' THEN '分布式能源' WHEN t.project_type = '13' THEN '交通能源' WHEN t.project_type IS NULL THEN ' ' END AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.project_type";

//                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                    datas.addAll(maps);

                    List<String> list = new ArrayList<>();
                    list.add("CNG加气站");list.add("LNG加气站");list.add("L-CNG合建站");list.add("LPG加气站");list.add("门站");list.add("高中压调压站");
                    list.add("气源厂|储配站");list.add("其他一");list.add("其他二");list.add("其他三");list.add("分布式光伏");list.add("分布式能源");
                    list.add("交通能源");
                    for (int j = 0; j < list.size(); j++) {
                        String s = list.get(j);
                        String sql3 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','"+s+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_status = 1 and t.project_type = "+(j+1)+" and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";

                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                        datas.addAll(maps);
                    }

                    for (int j = 0; j < list.size(); j++) {
                        String s = list.get(j);
                        String sql3 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建' AS '建设类型','"+s+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_station t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_status = 2  and t.project_type = "+(j+1)+" and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";

                        List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                        datas.addAll(maps);
                    }

                }else if (item.getTableName().equals("research")){
                    String sql="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.intangible_assets+t.fixed_assets+t.subtotal_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                   // String sql2="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.intangible_assets+t.fixed_assets+t.subtotal_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status";
                    String sql2="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',0 AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0)), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_research t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status";

                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                    datas.addAll(maps);
                }else if (item.getTableName().equals("meter")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM(t.last_accumulated_expenses), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.projected_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_meter t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                    datas.addAll(maps);
                }else if (item.getTableName().equals("charity")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' as '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM(t.last_accumulated_expenses), 0 ) AS '"+(year)+"年实际发生数金额',0 AS '"+(year+1)+"年预算数数量',IFNULL( SUM(t.projected_costs), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_charity t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql);
                    datas.addAll(maps);
                }
                else if (item.getTableName().equals("lowvalue")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.last_accumulated_expenses ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_lowvalue t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.name";
                    String sql2 = "SELECT '"+dept.getDeptName()+"' AS '部门名称','新建' AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.last_accumulated_expenses ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.budget ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',0 AS '"+(year+2)+"年预算',0 AS '"+(year+3)+"年预算',0 AS '"+(year+4)+"年预算',0 AS '"+(year+5)+"年预算' FROM t_reporting_table_lowvalue t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                    datas.addAll(maps);
                }
                else if (item.getTableName().equals("information_system")){
                    String sql="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.intangible_assets+t.fixed_assets ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_information_system t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    String sql2="SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型','"+item.getTableDisplayName()+"' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( COALESCE(t.intangible_assets,0)+COALESCE(t.fixed_assets,0) ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_information_system t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status";

                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
                    datas.addAll(maps);
                }else if (item.getTableName().equals("housing")){
                    String sql = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',t.name AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.name";
                    String sql2 = "SELECT '"+dept.getDeptName()+"' AS '部门名称',CASE t.project_status WHEN '1' THEN '续建' WHEN '2' THEN '新建'  END AS '建设类型',CASE  WHEN t.project_type = '1' THEN '房屋建筑物-办公楼' WHEN t.project_type = '2' THEN '房屋建筑物-仓库等' WHEN t.project_type = '3' THEN '房屋建筑物-房屋装修' WHEN t.project_type = '4' THEN '房屋建筑物-土地' WHEN t.project_type IS NULL THEN ' ' END  AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") GROUP BY t.project_status, t.project_type";

//                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql2);
//                    datas.addAll(maps);

                    String sql3 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-办公楼' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql4 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-仓库等' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql5 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-房屋装修' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql6 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '续建'  AS '建设类型','房屋建筑物-土地' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 1 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";

                    List<Map<String, Object>> maps = dynamicSqlService.dynamicSelectListSql(sql3);
                    datas.addAll(maps);
                    List<Map<String, Object>> maps1 = dynamicSqlService.dynamicSelectListSql(sql4);
                    datas.addAll(maps1);
                    List<Map<String, Object>> maps2 = dynamicSqlService.dynamicSelectListSql(sql5);
                    datas.addAll(maps2);
                    List<Map<String, Object>> maps3 = dynamicSqlService.dynamicSelectListSql(sql6);
                    datas.addAll(maps3);


                    String sql31 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建'  AS '建设类型','房屋建筑物-办公楼' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 1 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql41 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建'  AS '建设类型','房屋建筑物-仓库等' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 3 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql51 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建'  AS '建设类型','房屋建筑物-房屋装修' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 2 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+") ";
                    String sql61 = "SELECT '"+dept.getDeptName()+"' AS '部门名称', '新建'  AS '建设类型','房屋建筑物-土地' AS '资本性支出项目',0 AS '"+(year)+"年实际发生数数量',IFNULL( SUM( t.actual_settlement ), 0 ) AS '"+(year)+"年实际发生数金额',IFNULL( SUM( t.quantity ), 0 ) AS '"+(year+1)+"年预算数数量',IFNULL( SUM( t.expected_settlement ), 0 ) AS '"+(year+1)+"年预算数金额','' AS '增速',IFNULL( SUM( t.year1_settlement ), 0 ) AS '"+(year+2)+"年预算',IFNULL( SUM( t.year2_settlement ), 0 ) AS '"+(year+3)+"年预算',IFNULL( SUM( t.year3_settlement ), 0 ) AS '"+(year+4)+"年预算',IFNULL( SUM( t.year4_settlement ), 0 ) AS '"+(year+5)+"年预算' FROM t_reporting_table_housing t WHERE t.task_id = "+taskId+" and t.del_flag = '0' and t.project_type = 4 and t.project_status = 2 and (t.status = 3 or t.status = 5 ) and t.dept_id in (select dept_id from sys_dept t where t.parent_id = "+dept.getDeptId()+")";

                    List<Map<String, Object>> maps22 = dynamicSqlService.dynamicSelectListSql(sql31);
                    datas.addAll(maps22);
                    List<Map<String, Object>> maps11 = dynamicSqlService.dynamicSelectListSql(sql41);
                    datas.addAll(maps11);
                    List<Map<String, Object>> maps23 = dynamicSqlService.dynamicSelectListSql(sql51);
                    datas.addAll(maps23);
                    List<Map<String, Object>> maps33 = dynamicSqlService.dynamicSelectListSql(sql61);
                    datas.addAll(maps33);


                }
            }

            List<Map<String, Object>> status1Data = new ArrayList<>();//续建
            List<Map<String, Object>> status2Data = new ArrayList<>();//新建
            for (Map<String, Object> data : datas) {
                if (data.get("建设类型") instanceof String) {
                    String projectStatus = (String) data.get("建设类型");
                    if (projectStatus.equals("续建")) {
                        status1Data.add(data);
                    } else if (projectStatus.equals("新建")) {
                        status2Data.add(data);
                    }
                }
            }

            BigDecimal y2024c1 = BigDecimal.ZERO;
            BigDecimal y2024m1 = BigDecimal.ZERO;
            BigDecimal y2025c1 = BigDecimal.ZERO;
            BigDecimal y2025m1 = BigDecimal.ZERO;
            BigDecimal y20261 = BigDecimal.ZERO;
            BigDecimal y20271 = BigDecimal.ZERO;
            BigDecimal y20281 = BigDecimal.ZERO;
            BigDecimal y20291 = BigDecimal.ZERO;

            BigDecimal y2024c2 = BigDecimal.ZERO;
            BigDecimal y2024m2 = BigDecimal.ZERO;
            BigDecimal y2025c2 = BigDecimal.ZERO;
            BigDecimal y2025m2 = BigDecimal.ZERO;
            BigDecimal y20262 = BigDecimal.ZERO;
            BigDecimal y20272 = BigDecimal.ZERO;
            BigDecimal y20282 = BigDecimal.ZERO;
            BigDecimal y20292 = BigDecimal.ZERO;
            // 遍历 status1Data 列表并计算小计
            for (Map<String, Object> data : status1Data) {
                y2024c1 = y2024c1.add(new BigDecimal(data.get(year+"年实际发生数数量").toString()));
                y2024m1 = y2024m1.add(new BigDecimal(data.get(year+"年实际发生数金额").toString()));
                y2025c1 = y2025c1.add(new BigDecimal(data.get((year+1)+"年预算数数量").toString()));
                y2025m1 = y2025m1.add(new BigDecimal(data.get((year+1)+"年预算数金额").toString()));
                y20261 = y20261.add(new BigDecimal(data.get((year+2)+"年预算").toString()));
                y20271 = y20271.add(new BigDecimal(data.get((year+3)+"年预算").toString()));
                y20281 = y20281.add(new BigDecimal(data.get((year+4)+"年预算").toString()));
                y20291 = y20291.add(new BigDecimal(data.get((year+5)+"年预算").toString()));
            }
           // 遍历 status2Data 列表并计算小计
            for (Map<String, Object> data : status2Data) {
                y2024c2 = y2024c2.add(new BigDecimal(data.get(year+"年实际发生数数量").toString()));
                y2024m2 = y2024m2.add(new BigDecimal(data.get(year+"年实际发生数金额").toString()));
                y2025c2 = y2025c2.add(new BigDecimal(data.get((year+1)+"年预算数数量").toString()));
                y2025m2 = y2025m2.add(new BigDecimal(data.get((year+1)+"年预算数金额").toString()));
                y20262 = y20262.add(new BigDecimal(data.get((year+2)+"年预算").toString()));
                y20272 = y20272.add(new BigDecimal(data.get((year+3)+"年预算").toString()));
                y20282 = y20282.add(new BigDecimal(data.get((year+4)+"年预算").toString()));
                y20292 = y20292.add(new BigDecimal(data.get((year+5)+"年预算").toString()));
            }

            Map<String,Object> map1 = new HashMap<>();//续建小计
            map1.put("部门名称",dept.getDeptName()); map1.put("建设类型","续建"); map1.put("资本性支出项目","小计"); map1.put((year)+"年实际发生数数量",y2024c1); map1.put((year)+"年实际发生数金额",y2024m1);
            map1.put((year+1)+"年预算数数量",y2025c1);map1.put((year+1)+"年预算数金额",y2025m1); map1.put("增速"," "); map1.put((year+2)+"年预算",y20261); map1.put((year+3)+"年预算",y20271); map1.put((year+4)+"年预算",y20281); map1.put((year+5)+"年预算",y20291);

            Map<String,Object> map2 = new HashMap<>();//新建小计
            map2.put("部门名称",dept.getDeptName()); map2.put("建设类型","新建"); map2.put("资本性支出项目","小计"); map2.put((year)+"年实际发生数数量",y2024c2); map2.put((year)+"年实际发生数金额",y2024m2);
            map2.put((year+1)+"年预算数数量",y2025c2);map2.put((year+1)+"年预算数金额",y2025m2); map2.put("增速"," "); map2.put((year+2)+"年预算",y20262); map2.put((year+3)+"年预算",y20272); map2.put((year+4)+"年预算",y20282); map2.put((year+5)+"年预算",y20292);

            Map<String,Object> map3 = new HashMap<>();//合计
            map3.put("部门名称",dept.getDeptName()); map3.put("建设类型","新建"); map3.put("资本性支出项目","合计"); map3.put((year)+"年实际发生数数量",y2024c1.add(y2024c2)); map3.put((year)+"年实际发生数金额",y2024m1.add(y2024m2));
            map3.put((year+1)+"年预算数数量",y2025c1.add(y2025c2));map3.put((year+1)+"年预算数金额",y2025m1.add(y2025m2)); map3.put("增速"," "); map3.put((year+2)+"年预算",y20261.add(y20262)); map3.put((year+3)+"年预算",y20271.add(y20272)); map3.put((year+4)+"年预算",y20281.add(y20282)); map3.put((year+5)+"年预算",y20291.add(y20292));

            Map<String,Object> map = new HashMap<>();
            map.put("续建集合",status1Data); map.put("续建小计",map1); map.put("新建集合",status2Data); map.put("新建小计",map2); map.put("合计",map3);

            List<Map<String, Object>> datas2 = new ArrayList<>();
            datas2.addAll(status1Data);datas2.add(map1);datas2.addAll(status2Data);datas2.add(map2);
            datas2.add(map3);
            datas22.addAll(datas2);
            List<Map<String, Object>> lmap = new ArrayList<>();
            Map<String, Object> result = new HashMap<>();
            result.put("datas0",datas2);
            result.put("titles0",titles);
            result.put("sheetName0",dept.getDeptName());
            lmap.add(result);
            ExportSheetUtil.exportSingleHeaderExcel2(workbook,lmap);
        }
    }

    public  List<Map<String,Object>>  getTitles(int year){
        List<Map<String,Object>> titles = new ArrayList<>();
        Map<String,Object> m1= new HashMap<>();
        m1.put("key","部门名称");m1.put("name","部门名称");
        Map<String,Object> m2= new HashMap<>();
        m2.put("key","建设类型");m2.put("name","建设类型");
        Map<String,Object> m3= new HashMap<>();
        m3.put("key","资本性支出项目");m3.put("name","资本性支出项目");
        Map<String,Object> m4= new HashMap<>();
        m4.put("key",year+"年实际发生数数量");m4.put("name",year+"年实际发生数数量");
        Map<String,Object> m5= new HashMap<>();
        m5.put("key",year+"年实际发生数金额");m5.put("name",year+"年实际发生数金额");
        Map<String,Object> m6= new HashMap<>();
        m6.put("key",(year+1)+"年预算数数量");m6.put("name",(year+1)+"年预算数数量");
        Map<String,Object> m7= new HashMap<>();
        m7.put("key",(year+1)+"年预算数金额");m7.put("name",(year+1)+"年预算数金额");
        Map<String,Object> m8= new HashMap<>();
        m8.put("key","增速");m8.put("name","增速");
        Map<String,Object> m9= new HashMap<>();
        m9.put("key",(year+2)+"年预算");m9.put("name",(year+2)+"年预算");
        Map<String,Object> m10= new HashMap<>();
        m10.put("key",(year+3)+"年预算");m10.put("name",(year+3)+"年预算");
        Map<String,Object> m11= new HashMap<>();
        m11.put("key",(year+4)+"年预算");m11.put("name",(year+4)+"年预算");
        Map<String,Object> m12= new HashMap<>();
        m12.put("key",(year+5)+"年预算");m12.put("name",(year+5)+"年预算");
        titles.add(m1);
        titles.add(m2);
        titles.add(m3);
        titles.add(m4);
        titles.add(m5);
        titles.add(m6);
        titles.add(m7);
        titles.add(m8);
        titles.add(m9);
        titles.add(m10);
        titles.add(m11);
        titles.add(m12);
        return titles;
    }
}
