package net.cmkj.web.controller.budget;

import net.cmkj.budget.domain.dashboard.QueryTableDetailDTO;
import net.cmkj.budget.service.IBudgetDashboardDataService;
import net.cmkj.common.core.domain.AjaxResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName BudgetDashboardDataController
 * @Description 大屏数据控制层
 * @Author @hanjianghui
 * @Date 2025/6/13 14:26
 **/
@RestController
//@RequestMapping("/budget/dashboard")
@RequestMapping("/")
public class BudgetDashboardDataController {

    @Autowired
    private IBudgetDashboardDataService budgetDashboardDataService;

    @GetMapping("/getDatas")
    public AjaxResult getData(@Param("taskId") Long taskId, @Param(value = "companyId") Long companyId) {
        return AjaxResult.success(budgetDashboardDataService.getData(taskId, companyId));
    }

    @GetMapping("/getParams")
    public AjaxResult getCompanyAndTask() {
        return budgetDashboardDataService.getCompanyAndTask();
    }

    @GetMapping("/getSubDatas")
    public AjaxResult getZdpData(@Param("taskId") Long taskId, @Param(value = "companyId") Long companyId,
                                 @Param(value = "type") Long type) {
        return AjaxResult.success(budgetDashboardDataService.getZdpData(taskId, companyId, type));
    }

    @GetMapping("/getTableDetailInfo")
    public AjaxResult getTableDetailInfo(QueryTableDetailDTO queryTableDetailDTO) {
        return AjaxResult.success(budgetDashboardDataService.getTableDetailInfo(queryTableDetailDTO));
    }
}
