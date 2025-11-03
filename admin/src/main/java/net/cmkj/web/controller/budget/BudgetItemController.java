package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.BudgetItem;
import net.cmkj.budget.service.IBudgetItemService;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预算配置Controller
 * 
 * @author cmkj
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/budget/item")
public class BudgetItemController extends BaseController
{
    @Autowired
    private IBudgetItemService budgetItemService;

    /**
     * 查询预算配置列表
     */
    @SaCheckPermission("budget:item:list")
    @GetMapping("/list")
    public TableDataInfo list(BudgetItem budgetItem)
    {
        startPage();
        List<BudgetItem> list = budgetItemService.selectBudgetItemList(budgetItem);
        return getDataTable(list);
    }

    @GetMapping("/listAll")
    public AjaxResult listAll(BudgetItem budgetItem)
    {
        return success(budgetItemService.selectBudgetItemList(budgetItem));
    }


    @SaCheckPermission("budget:item:listByUser")
    @GetMapping("/listByUser")
    public TableDataInfo listByUser(BudgetItem budgetItem)
    {
        if(!SecurityUtils.isAdmin(getUserId())){
            budgetItem.setUserId(getUserId());
        }
        startPage();
        List<BudgetItem> list = budgetItemService.selectBudgetItemList(budgetItem);
        return getDataTable(list);
    }

    /**
     * 导出预算配置列表
     */
    @SaCheckPermission("budget:item:export")
    @Log(title = "预算配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BudgetItem budgetItem)
    {
        List<BudgetItem> list = budgetItemService.selectBudgetItemList(budgetItem);
        ExcelUtil<BudgetItem> util = new ExcelUtil<BudgetItem>(BudgetItem.class);
        util.exportExcel(response, list, "预算配置数据");
    }

    /**
     * 获取预算配置详细信息
     */
    @SaCheckPermission("budget:item:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(budgetItemService.selectBudgetItemById(id));
    }

    /**
     * 新增预算配置
     */
    @SaCheckPermission("budget:item:add")
    @Log(title = "预算配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BudgetItem budgetItem)
    {
        return toAjax(budgetItemService.insertBudgetItem(budgetItem));
    }

    /**
     * 修改预算配置
     */
    @SaCheckPermission("budget:item:edit")
    @Log(title = "预算配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BudgetItem budgetItem)
    {
        return toAjax(budgetItemService.updateBudgetItem(budgetItem));
    }

    /**
     * 修改预算配置状态
     */
    @SaCheckPermission("budget:item:edit")
    @Log(title = "预算配置", businessType = BusinessType.UPDATE)
	@PostMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody BudgetItem budgetItem)
    {
        return toAjax(budgetItemService.updateStatus(budgetItem));
    }
}
