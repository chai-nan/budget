package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.ReportingLog;
import net.cmkj.budget.service.IReportingLogService;
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
 * 填报记录Controller
 * 
 * @author cmkj
 * @date 2024-07-22
 */
@RestController
@RequestMapping("/budget/log")
public class ReportingLogController extends BaseController
{
    @Autowired
    private IReportingLogService reportingLogService;

    /**
     * 查询填报记录列表
     */
    @SaCheckPermission("budget:log:list")
    @GetMapping("/list")
    public TableDataInfo list(ReportingLog reportingLog)
    {
        startPage();
        List<ReportingLog> list = reportingLogService.selectReportingLogList(reportingLog);
        return getDataTable(list);
    }

    /**
     * 导出填报记录列表
     */
    @SaCheckPermission("budget:log:export")
    @Log(title = "填报记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReportingLog reportingLog)
    {
        List<ReportingLog> list = reportingLogService.selectReportingLogList(reportingLog);
        ExcelUtil<ReportingLog> util = new ExcelUtil<ReportingLog>(ReportingLog.class);
        util.exportExcel(response, list, "填报记录数据");
    }

    /**
     * 获取填报记录详细信息
     */
    @SaCheckPermission("budget:log:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(reportingLogService.selectReportingLogById(id));
    }

    /**
     * 新增填报记录
     */
    @SaCheckPermission("budget:log:add")
    @Log(title = "填报记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReportingLog reportingLog)
    {
        return toAjax(reportingLogService.insertReportingLog(reportingLog));
    }

    /**
     * 修改填报记录
     */
    @SaCheckPermission("budget:log:edit")
    @Log(title = "填报记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReportingLog reportingLog)
    {
        return toAjax(reportingLogService.updateReportingLog(reportingLog));
    }

    /**
     * 删除填报记录
     */
    @SaCheckPermission("budget:log:remove")
    @Log(title = "填报记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(reportingLogService.deleteReportingLogByIds(ids));
    }
}
