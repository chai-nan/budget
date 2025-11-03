package net.cmkj.web.controller.fixed;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.poi.ExcelUtil;
import net.cmkj.fixed.domain.TableWages;
import net.cmkj.fixed.service.ITableWagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预算填报项目表【工资福利】Controller
 *
 * @author cmkj
 * @date 2024-08-26
 */
@RestController
@RequestMapping("/fixed/wages")
public class TableWagesController extends BaseController
{
    @Autowired
    private ITableWagesService tableWagesService;

    /**
     * 分页查询预算填报项目【工资福利】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TableWages tableWages)
    {
        startPage();
        List<TableWages> list = tableWagesService.selectTableWagesList(tableWages);
        return getDataTable(list);
    }

    /**
     * 不分页查询预算填报项目【工资福利】列表
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(TableWages tableWages) {
        return AjaxResult.success(tableWagesService.selectTableWagesList(tableWages));
    }

    @GetMapping("/parentList")
    public AjaxResult parentList(Integer selectType, Long taskId) {
        return AjaxResult.success(tableWagesService.parentList(selectType,taskId));
    }


    /**
     * 导出预算填报项目【工资福利】列表
     */
    @SaCheckPermission("fixed:wages:export")
    @Log(title = "预算填报项目【工资福利】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TableWages tableWages)
    {
        List<TableWages> list = tableWagesService.selectExportList(tableWages);
        for (TableWages wages : list) {
            wages.setDeptName(wages.getParentName()+"-"+wages.getDeptName());
        }
        ExcelUtil<TableWages> util = new ExcelUtil<TableWages>(TableWages.class);
        util.exportExcel(response, list, "预算填报【工资福利】数据");
    }

    /**
     * 获取预算填报项目【工资福利】详细信息
     */
    @SaCheckPermission("fixed:wages:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tableWagesService.selectTableWagesById(id));
    }

    /**
     * 新增预算填报项目【工资福利】
     */
    @SaCheckPermission("fixed:wages:add")
    @Log(title = "预算填报项目【工资福利】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TableWages tableWages) {
        if(tableWages.getTaskId()==null || tableWages.getDeptId()==null){
            return AjaxResult.error("参数错误");
        }
        if(tableWages.getId()!=null){
            TableWages query = tableWagesService.selectTableWagesById(tableWages.getId());
            if(query!=null && query.getStatus()!=1 && query.getStatus()!=6){
                return AjaxResult.error("数据已提交，禁止修改");
            }
            return toAjax(tableWagesService.updateTableWages(tableWages));
        }else{
            return tableWagesService.insertTableWages(tableWages);
        }
    }

    /**
     * 修改预算填报项目【工资福利】
     */
    @SaCheckPermission("fixed:wages:update")
    @Log(title = "预算填报项目【工资福利】", businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody TableWages tableWages) {
        return tableWagesService.updateStatus(tableWages);
    }

    /**
     * 删除预算填报项目【工资福利】
     */
    @Log(title = "预算填报项目【工资福利】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tableWagesService.deleteTableWagesByIds(ids));
    }

    @PostMapping("/backfill")
    public AjaxResult backfill(@RequestBody TableWages tableWages) {
        return tableWagesService.backfill(tableWages);
    }
}
