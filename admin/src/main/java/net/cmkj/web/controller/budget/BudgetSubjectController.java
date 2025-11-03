package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.BudgetSubject;
import net.cmkj.budget.service.IBudgetSubjectService;
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
 * 预算科目Controller
 * 
 * @author cmkj
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/budget/subject")
public class BudgetSubjectController extends BaseController
{
    @Autowired
    private IBudgetSubjectService budgetSubjectService;

    /**
     * 查询预算科目列表
     */
    @SaCheckPermission("budget:subject:list")
    @GetMapping("/listPage")
    public TableDataInfo listPage(BudgetSubject budgetSubject)
    {
        startPage();
        List<BudgetSubject> list = budgetSubjectService.selectBudgetSubjectList(budgetSubject);
        return getDataTable(list);
    }

    @SaCheckPermission("budget:subject:list")
    @GetMapping("/list")
    public AjaxResult list(BudgetSubject budgetSubject)
    {
        List<BudgetSubject> list = budgetSubjectService.selectBudgetSubjectList(budgetSubject);
        return success(list);
    }

    /**
     * 导出预算科目列表
     */
    @SaCheckPermission("budget:subject:export")
    @Log(title = "预算科目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BudgetSubject budgetSubject)
    {
        List<BudgetSubject> list = budgetSubjectService.selectBudgetSubjectList(budgetSubject);
        ExcelUtil<BudgetSubject> util = new ExcelUtil<BudgetSubject>(BudgetSubject.class);
        util.exportExcel(response, list, "预算科目数据");
    }

    /**
     * 获取预算科目详细信息
     */
    @SaCheckPermission("budget:subject:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(budgetSubjectService.selectBudgetSubjectById(id));
    }

    /**
     * 新增预算科目
     */
    @SaCheckPermission("budget:subject:add")
    @Log(title = "预算科目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BudgetSubject budgetSubject)
    {
        return budgetSubjectService.insertBudgetSubject(budgetSubject);
    }

    /**
     * 修改预算科目
     */
    @SaCheckPermission("budget:subject:edit")
    @Log(title = "预算科目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BudgetSubject budgetSubject)
    {
        return budgetSubjectService.updateBudgetSubject(budgetSubject);
    }

    /**
     * 删除预算科目
     */
    @SaCheckPermission("budget:subject:remove")
    @Log(title = "预算科目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            if(budgetSubjectService.selectChildrenByParentId(id).size()>0){
                return AjaxResult.warn("该科目有子科目，请先删除子科目");
            }
        }
        return toAjax(budgetSubjectService.deleteBudgetSubjectByIds(ids));
    }


    /**
     * 不分页查询（下拉接口使用）
     * @param budgetSubject
     * @return
     */
    @GetMapping("/listAll")
    public List<BudgetSubject> listAll(BudgetSubject budgetSubject)
    {
        return budgetSubjectService.selectBudgetSubjectList(budgetSubject);
    }

    /**
     * 根据部门查询关联科目
     * @param deptId
     * @return
     */
    @GetMapping("/listByDept")
    public List<BudgetSubject> listByDept(Long deptId)
    {
        return budgetSubjectService.listByDept(deptId);
    }
}
