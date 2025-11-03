package net.cmkj.web.controller.budget;

import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import cn.dev33.satoken.annotation.SaCheckPermission;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.budget.domain.Warning;
import net.cmkj.budget.service.IWarningService;
import net.cmkj.common.utils.poi.ExcelUtil;
import net.cmkj.common.core.page.TableDataInfo;

/**
 * 预警Controller
 * 
 * @author cmkj
 * @date 2024-11-07
 */
@RestController
@RequestMapping("/budget/warning")
public class WarningController extends BaseController
{
    @Autowired
    private IWarningService warningService;

    /**
     * 查询预警列表
     */
    @SaCheckPermission("budget:warning:list")
    @GetMapping("/list")
    public TableDataInfo list(Warning warning)
    {
        startPage();
        if(SecurityUtils.isAdmin(getUserId())) {
            List<Warning> list = warningService.selectWarningList(warning);
            return getDataTable(list);
        }else{
            Long deptId = SecurityUtils.getDeptId();
            warning.setDeptId(deptId);
            List<Warning> list = warningService.selectWarningList(warning);
            return getDataTable(list);
        }
    }

    @GetMapping("/getList")
    public AjaxResult getList()
    {
        Warning warning = new Warning();
        List<Warning> list = new ArrayList<>();
        if(SecurityUtils.isAdmin(getUserId())) {
            list = warningService.selectWarningList(warning);
        }else{
            Long deptId = SecurityUtils.getDeptId();
            warning.setDeptId(deptId);
            list = warningService.selectWarningList(warning);
        }
        return AjaxResult.success(list);
    }

    /**
     * 导出预警列表
     */
    @SaCheckPermission("budget:warning:export")
    @Log(title = "预警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Warning warning)
    {
        List<Warning> list = warningService.selectWarningList(warning);
        ExcelUtil<Warning> util = new ExcelUtil<Warning>(Warning.class);
        util.exportExcel(response, list, "预警数据");
    }

    /**
     * 获取预警详细信息
     */
    @SaCheckPermission("budget:warning:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(warningService.selectWarningById(id));
    }

    /**
     * 新增预警
     */
    @SaCheckPermission("budget:warning:add")
    @Log(title = "预警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Warning warning)
    {
        return toAjax(warningService.insertWarning(warning));
    }

    /**
     * 修改预警
     */
    @SaCheckPermission("budget:warning:edit")
    @Log(title = "预警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Warning warning)
    {
        return toAjax(warningService.updateWarning(warning));
    }

    /**
     * 删除预警
     */
    @SaCheckPermission("budget:warning:remove")
    @Log(title = "预警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(warningService.deleteWarningByIds(ids));
    }
}
