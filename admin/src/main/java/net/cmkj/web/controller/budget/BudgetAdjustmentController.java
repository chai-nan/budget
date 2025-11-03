package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.BudgetAdjustment;
import net.cmkj.budget.service.IBudgetAdjustmentService;
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
 * 预算调整（OA填报）Controller
 * 
 * @author cmkj
 * @date 2024-10-16
 */
@RestController
@RequestMapping("/budget/adjustmentCancel")
public class BudgetAdjustmentController extends BaseController
{
    @Autowired
    private IBudgetAdjustmentService budgetAdjustmentService;

    /**
     * 查询预算调整（OA填报）列表
     */
    @SaCheckPermission("budget:adjustment:list")
    @GetMapping("/list")
    public TableDataInfo list(BudgetAdjustment budgetAdjustment)
    {
        startPage();
        List<BudgetAdjustment> list = budgetAdjustmentService.selectBudgetAdjustmentList(budgetAdjustment);
        return getDataTable(list);
    }

    /**
     * 导出预算调整（OA填报）列表
     */
    @SaCheckPermission("budget:adjustment:export")
    @Log(title = "预算调整（OA填报）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BudgetAdjustment budgetAdjustment)
    {
        List<BudgetAdjustment> list = budgetAdjustmentService.selectBudgetAdjustmentList(budgetAdjustment);
        ExcelUtil<BudgetAdjustment> util = new ExcelUtil<BudgetAdjustment>(BudgetAdjustment.class);
        util.exportExcel(response, list, "预算调整（OA填报）数据");
    }

    /**
     * 获取预算调整（OA填报）详细信息
     */
    @SaCheckPermission("budget:adjustment:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(budgetAdjustmentService.selectBudgetAdjustmentById(id));
    }

    /**
     * 新增预算调整（OA填报）
     */
    @SaCheckPermission("budget:adjustment:add")
    @Log(title = "预算调整（OA填报）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BudgetAdjustment budgetAdjustment)
    {
        return budgetAdjustmentService.insertBudgetAdjustment(budgetAdjustment);
    }

    /**
     * 修改预算调整（OA填报）
     */
    @SaCheckPermission("budget:adjustment:edit")
    @Log(title = "预算调整（OA填报）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BudgetAdjustment budgetAdjustment)
    {
        return budgetAdjustmentService.updateBudgetAdjustment(budgetAdjustment);
    }

    /**
     * 删除预算调整（OA填报）
     */
    @SaCheckPermission("budget:adjustment:remove")
    @Log(title = "预算调整（OA填报）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(budgetAdjustmentService.deleteBudgetAdjustmentByIds(ids));
    }

    @SaCheckPermission("budget:adjustment:remove")
    @Log(title = "预算调整（OA填报）", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeInfo/{infoId}")
    public AjaxResult removeInfo(@PathVariable Long infoId)
    {
        return toAjax(budgetAdjustmentService.deleteBudgetAdjustmentinfo(infoId));
    }
}
