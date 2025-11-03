package net.cmkj.web.controller.budget;

import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.service.IBudgetStatisticsService;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * 预算统计Controller
 * 
 * @author cmkj
 * @date 2024-07-25
 */
@RestController
@RequestMapping("/budget/statistics")
public class BudgetStatisticsController extends BaseController {

    @Autowired
    private IBudgetStatisticsService budgetSummary;

    /**
     * 财务审核汇总
     * @param type
     * @param taskId
     * @param deptId
     * @return
     */
    @GetMapping("/budgetSummary")
    public AjaxResult budgetSummary(Integer type,Long taskId,Long deptId)
    {
        return AjaxResult.success(budgetSummary.budgetSummary(type,taskId,deptId));
    }

    @GetMapping("/test")
    public AjaxResult budgetSummaryTest(Integer type,Long taskId,Long deptId)
    {
        Map<String, Object> stringObjectMap = budgetSummary.budgetSummary(type, taskId, deptId);


        return AjaxResult.success();
    }

    /**
     * OA调整汇总
     * @param type
     * @param taskId
     * @param deptId
     * @return
     */
    @GetMapping("/budgetSummaryOA")
    public AjaxResult budgetSummaryOA(Integer type,Long taskId,Long deptId,Long versionId)
    {
        return AjaxResult.success(budgetSummary.budgetSummaryOA(type,taskId,deptId,versionId));
    }

    /**
     * 版本审核汇总
     * @param type
     * @param taskId
     * @param deptId
     * @return
     */
    @GetMapping("/budgetVersionSummary")
    public AjaxResult budgetVersionSummary(Integer type,Long taskId,Long deptId,Long versionId)
    {
        return AjaxResult.success(budgetSummary.budgetVersionSummary(type,taskId,deptId,versionId));
    }

    /**
     * 填报汇总
     * @return
     */
    @GetMapping("/reportSummary")
    public AjaxResult reportSummary(Long taskId)
    {
        return AjaxResult.success(budgetSummary.reportSummary(taskId,getLoginUser()));
    }

    /**
     * 填报汇总导出
     */
    @PostMapping("/reportSummaryExport")
    public void reportSummaryExport(HttpServletResponse response,Long taskId){
        budgetSummary.reportSummaryExport(response,taskId,getLoginUser());
    }

    /**
     * 版本汇总导出
     */
    @PostMapping("/budgetExport")
    public void budgetExport(HttpServletResponse response,Long taskId,Integer type,Long deptId,Long versionId)throws IOException {
         budgetSummary.budgetExport(response,taskId,type,deptId,versionId);
    }

    /**
     * 项目汇总
     * @return
     */
    @GetMapping("/itemSummary")
    public AjaxResult itemSummary(Long taskId)
    {
        return AjaxResult.success(budgetSummary.itemSummary(taskId));
    }

    /**
     * 汇总分析
     */
    @GetMapping("/statisticsAnalysis")
    public AjaxResult statisticsAnalysis(Long deptId,Long versionId){
        return AjaxResult.success(budgetSummary.statisticsAnalysis(deptId,versionId,1));
    }

    @PostMapping("/exportStatisticsAnalysis")
    public void exportStatisticsAnalysis(HttpServletResponse response, Long deptId,Long versionId) {
        budgetSummary.exportStatisticsAnalysis(response,deptId,versionId);
    }

    /**
     * 项目区域汇总
     * @return
     */
    @GetMapping("/itemAreaSummary")
    public AjaxResult itemAreaSummary(Long taskId)
    {
        return AjaxResult.success(budgetSummary.itemAreaSummary(taskId));
    }

    @Log(title = "资本性导出", businessType = BusinessType.EXPORT)
//    @SaCheckPermission("budget:business:exportTotal")
    @PostMapping("/exportTotal")
    public void exportTotal(HttpServletResponse response,  Long taskId) throws IOException {
        budgetSummary.exportTotal(response,taskId);
    }

}
