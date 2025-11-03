package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.BudgetWarning;
import net.cmkj.budget.service.IBudgetWarningService;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预警配置Controller
 * 
 * @author cmkj
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/budget/budgetWarning")
public class BudgetWarningController extends BaseController
{
    @Autowired
    private IBudgetWarningService budgetWarningService;

    /**
     * 查询预警配置列表
     */
    @SaCheckPermission("budget:budgetWarning:list")
    @GetMapping("/list")
    public TableDataInfo list(BudgetWarning budgetWarning)
    {
        startPage();
        List<BudgetWarning> list = budgetWarningService.selectBudgetWarningList(budgetWarning);
        return getDataTable(list);
    }

    /**
     * 导出预警配置列表
     */
    @SaCheckPermission("budget:budgetWarning:export")
    @Log(title = "预警配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BudgetWarning budgetWarning)
    {
        List<BudgetWarning> list = budgetWarningService.selectBudgetWarningList(budgetWarning);
        ExcelUtil<BudgetWarning> util = new ExcelUtil<BudgetWarning>(BudgetWarning.class);
        util.exportExcel(response, list, "预警配置数据");
    }

    /**
     * 获取预警配置详细信息
     */
    @SaCheckPermission("budget:budgetWarning:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(budgetWarningService.selectBudgetWarningById(id));
    }

    /**
     * 新增预警配置
     */
    @SaCheckPermission("budget:budgetWarning:add")
    @Log(title = "预警配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BudgetWarning budgetWarning)
    {
        return toAjax(budgetWarningService.insertBudgetWarning(budgetWarning));
    }

    /**
     * 修改预警配置
     */
    @SaCheckPermission("budget:budgetWarning:edit")
    @Log(title = "预警配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BudgetWarning budgetWarning)
    {
        budgetWarning.setLastTime(null);
        return toAjax(budgetWarningService.updateBudgetWarning(budgetWarning));
    }

    /**
     * 删除预警配置
     */
    @SaCheckPermission("budget:budgetWarning:remove")
    @Log(title = "预警配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(budgetWarningService.deleteBudgetWarningByIds(ids));
    }
}
