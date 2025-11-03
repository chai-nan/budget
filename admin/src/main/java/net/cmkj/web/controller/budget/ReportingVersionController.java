package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.ReportingVersion;
import net.cmkj.budget.service.IReportingVersionService;
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
 * 版本控制Controller
 * 
 * @author cmkj
 * @date 2024-07-17
 */
@RestController
@RequestMapping("/budget/version")
public class ReportingVersionController extends BaseController {
    @Autowired
    private IReportingVersionService reportingVersionService;

    /**
     * 查询版本控制列表
     */
    @SaCheckPermission("budget:version:list")
    @GetMapping("/list")
    public TableDataInfo list(ReportingVersion reportingVersion)
    {
        startPage();
        List<ReportingVersion> list = reportingVersionService.selectReportingVersionList(reportingVersion);
        return getDataTable(list);
    }

    @GetMapping("/listAll")
    public AjaxResult listAll(ReportingVersion reportingVersion)
    {
        return AjaxResult.success(reportingVersionService.selectReportingVersionList(reportingVersion));
    }

    /**
     * 预算数据导出列表（每个任务最新一条数据）
     * @param reportingVersion
     * @return
     */
    @SaCheckPermission("budget:version:lastList")
    @GetMapping("/lastList")
    public TableDataInfo lastList(ReportingVersion reportingVersion)
    {
        startPage();
        List<ReportingVersion> list = reportingVersionService.lastList(reportingVersion);
        return getDataTable(list);
    }

    /**
     * 导出版本控制列表
     */
    @SaCheckPermission("budget:version:export")
    @Log(title = "版本控制", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReportingVersion reportingVersion)
    {
        List<ReportingVersion> list = reportingVersionService.selectReportingVersionList(reportingVersion);
        ExcelUtil<ReportingVersion> util = new ExcelUtil<ReportingVersion>(ReportingVersion.class);
        util.exportExcel(response, list, "版本控制数据");
    }

    /**
     * 获取版本控制详细信息
     */
    @SaCheckPermission("budget:version:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(reportingVersionService.selectReportingVersionById(id));
    }

    /**
     * 新增版本控制
     */
    @SaCheckPermission("budget:version:add")
    @Log(title = "版本控制", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReportingVersion reportingVersion)
    {
        return toAjax(reportingVersionService.insertReportingVersion(reportingVersion));
    }

    /**
     * 修改版本控制
     */
    @SaCheckPermission("budget:version:edit")
    @Log(title = "版本控制", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReportingVersion reportingVersion)
    {
        return toAjax(reportingVersionService.updateReportingVersion(reportingVersion));
    }

    /**
     * 删除版本控制
     */
    @SaCheckPermission("budget:version:remove")
    @Log(title = "版本控制", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(reportingVersionService.deleteReportingVersionByIds(ids));
    }
}
