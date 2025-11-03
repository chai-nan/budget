package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.TableModel;
import net.cmkj.budget.service.ITableModelService;
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
 * 填报模块Controller
 * 
 * @author cmkj
 */
@RestController
@RequestMapping("/budget/model")
public class TableModelController extends BaseController
{
    @Autowired
    private ITableModelService tableModelService;

    /**
     * 查询填报模块列表
     */
    @SaCheckPermission("budget:model:list")
    @GetMapping("/list")
    public TableDataInfo list(TableModel tableModel)
    {
        startPage();
        List<TableModel> list = tableModelService.selectTableModelList(tableModel);
        return getDataTable(list);
    }

    /**
     * 导出填报模块列表
     */
    @SaCheckPermission("budget:model:export")
    @Log(title = "填报模块", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TableModel tableModel)
    {
        List<TableModel> list = tableModelService.selectTableModelList(tableModel);
        ExcelUtil<TableModel> util = new ExcelUtil<TableModel>(TableModel.class);
        util.exportExcel(response, list, "填报模块数据");
    }

    /**
     * 获取填报模块详细信息
     */
    @SaCheckPermission("budget:model:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tableModelService.selectTableModelById(id));
    }

    /**
     * 新增填报模块
     */
    @SaCheckPermission("budget:model:add")
    @Log(title = "填报模块", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TableModel tableModel)
    {
        return tableModelService.insertTableModel(tableModel);
    }

    /**
     * 修改填报模块
     */
    @SaCheckPermission("budget:model:edit")
    @Log(title = "填报模块", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TableModel tableModel)
    {
        return tableModelService.updateTableModel(tableModel);
    }

    /**
     * 删除填报模块
     */
    @SaCheckPermission("budget:model:remove")
    @Log(title = "填报模块", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(tableModelService.deleteTableModelById(id));
    }
}
