package net.cmkj.web.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.poi.ExcelUtil;
import net.cmkj.system.domain.SysOperLog;
import net.cmkj.system.service.ISysOperLogService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录
 * 
 * @author Yuan
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController
{
    @Autowired
    private ISysOperLogService operLogService;

    @SaCheckPermission("monitor:operlog:list")
    @GetMapping("/list")
    public TableDataInfo list(SysOperLog operLog)
    {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @SaCheckPermission("monitor:operlog:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLog operLog)
    {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        util.exportExcel(response, list, "操作日志");
    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("monitor:operlog:remove")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds)
    {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @SaCheckPermission("monitor:operlog:remove")
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        operLogService.cleanOperLog();
        return success();
    }
}
