package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.BudgetOaAdjustment;
import net.cmkj.budget.service.IBudgetOaAdjustmentService;
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
 * OA预算填报调整Controller
 * 
 * @author cmkj
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/budget/adjustment")
public class BudgetOaAdjustmentController extends BaseController
{
    @Autowired
    private IBudgetOaAdjustmentService budgetOaAdjustmentService;

    /**
     * 查询OA预算填报调整列表
     */
    @SaCheckPermission("budget:adjustment:list")
    @GetMapping("/list")
    public TableDataInfo list(BudgetOaAdjustment budgetOaAdjustment)
    {
        startPage();
        List<BudgetOaAdjustment> list = budgetOaAdjustmentService.selectBudgetOaAdjustmentList(budgetOaAdjustment);
        return getDataTable(list);
    }

    /**
     * 导出OA预算填报调整列表
     */
    @SaCheckPermission("budget:adjustment:export")
    @Log(title = "OA预算填报调整", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BudgetOaAdjustment budgetOaAdjustment)
    {
        List<BudgetOaAdjustment> list = budgetOaAdjustmentService.selectBudgetOaAdjustmentList(budgetOaAdjustment);
        ExcelUtil<BudgetOaAdjustment> util = new ExcelUtil<BudgetOaAdjustment>(BudgetOaAdjustment.class);
        util.exportExcel(response, list, "OA预算填报调整数据");
    }

    /**
     * 获取OA预算填报调整详细信息
     */
    @SaCheckPermission("budget:adjustment:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(budgetOaAdjustmentService.selectBudgetOaAdjustmentById(id));
    }

    /**
     * 新增OA预算填报调整
     */
    @SaCheckPermission("budget:adjustment:add")
    @Log(title = "OA预算填报调整", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BudgetOaAdjustment budgetOaAdjustment)
    {
        return toAjax(budgetOaAdjustmentService.insertBudgetOaAdjustment(budgetOaAdjustment));
    }

    /**
     * 修改OA预算填报调整
     */
    @SaCheckPermission("budget:adjustment:edit")
    @Log(title = "OA预算填报调整", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BudgetOaAdjustment budgetOaAdjustment)
    {
        return toAjax(budgetOaAdjustmentService.updateBudgetOaAdjustment(budgetOaAdjustment));
    }

    /**
     * 删除OA预算填报调整
     */
    @SaCheckPermission("budget:adjustment:remove")
    @Log(title = "OA预算填报调整", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(budgetOaAdjustmentService.deleteBudgetOaAdjustmentByIds(ids));
    }
}
