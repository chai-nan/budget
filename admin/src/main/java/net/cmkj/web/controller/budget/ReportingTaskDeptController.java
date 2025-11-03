package net.cmkj.web.controller.budget;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import cn.dev33.satoken.annotation.SaCheckPermission;
import net.cmkj.common.core.domain.entity.SysRole;
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
import net.cmkj.budget.domain.ReportingTaskDept;
import net.cmkj.budget.service.IReportingTaskDeptService;
import net.cmkj.common.utils.poi.ExcelUtil;
import net.cmkj.common.core.page.TableDataInfo;

/**
 * 部门任务填报情况Controller
 * 
 * @author cmkj
 * @date 2024-11-05
 */
@RestController
@RequestMapping("/budget/reportingTaskDept")
public class ReportingTaskDeptController extends BaseController
{
    @Autowired
    private IReportingTaskDeptService reportingTaskDeptService;

    /**
     * 查询部门任务填报情况列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ReportingTaskDept reportingTaskDept)
    {
        startPage();
        List<ReportingTaskDept> list = reportingTaskDeptService.selectReportingTaskDeptList(reportingTaskDept);
        return getDataTable(list);
    }

    /**
     * 导出部门任务填报情况列表
     */
    @Log(title = "部门任务填报情况", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReportingTaskDept reportingTaskDept)
    {
        List<ReportingTaskDept> list = reportingTaskDeptService.selectReportingTaskDeptList(reportingTaskDept);
        ExcelUtil<ReportingTaskDept> util = new ExcelUtil<ReportingTaskDept>(ReportingTaskDept.class);
        util.exportExcel(response, list, "部门任务填报情况数据");
    }

    /**
     * 获取部门任务填报情况详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(reportingTaskDeptService.selectReportingTaskDeptById(id));
    }

    /**
     * 新增部门任务填报情况
     */
    @Log(title = "部门任务填报情况", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReportingTaskDept reportingTaskDept){
        List<SysRole> roles = getLoginUser().getUser().getRoles();
        if (roles.stream().anyMatch(role -> role.getRoleKey().equals("businessFilling"))){
            return toAjax(reportingTaskDeptService.insertReportingTaskDept(reportingTaskDept));
        }
        return toAjax(-1);
    }

    /**
     * 修改部门任务填报情况
     */
    @Log(title = "部门任务填报情况", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReportingTaskDept reportingTaskDept)
    {
        return toAjax(reportingTaskDeptService.updateReportingTaskDept(reportingTaskDept));
    }

    /**
     * 删除部门任务填报情况
     */
    @Log(title = "部门任务填报情况", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(reportingTaskDeptService.deleteReportingTaskDeptByIds(ids));
    }
}
