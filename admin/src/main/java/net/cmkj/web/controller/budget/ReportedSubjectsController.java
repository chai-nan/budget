package net.cmkj.web.controller.budget;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import cn.dev33.satoken.annotation.SaCheckPermission;
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
import net.cmkj.budget.domain.ReportedSubjects;
import net.cmkj.budget.service.IReportedSubjectsService;
import net.cmkj.common.utils.poi.ExcelUtil;
import net.cmkj.common.core.page.TableDataInfo;

/**
 * 上报科目Controller
 * 
 * @author cmkj
 * @date 2025-01-09
 */
@RestController
@RequestMapping("/budget/subjects")
public class ReportedSubjectsController extends BaseController
{
    @Autowired
    private IReportedSubjectsService reportedSubjectsService;

    /**
     * 查询上报科目列表
     */
    @SaCheckPermission("budget:subjects:list")
    @GetMapping("/list")
    public TableDataInfo list(ReportedSubjects reportedSubjects)
    {
        startPage();
        List<ReportedSubjects> list = reportedSubjectsService.selectReportedSubjectsList(reportedSubjects);
        return getDataTable(list);
    }

    /**
     * 导出上报科目列表
     */
    @SaCheckPermission("budget:subjects:export")
    @Log(title = "上报科目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReportedSubjects reportedSubjects)
    {
        List<ReportedSubjects> list = reportedSubjectsService.selectReportedSubjectsList(reportedSubjects);
        ExcelUtil<ReportedSubjects> util = new ExcelUtil<ReportedSubjects>(ReportedSubjects.class);
        util.exportExcel(response, list, "上报科目数据");
    }

    /**
     * 获取上报科目详细信息
     */
    @SaCheckPermission("budget:subjects:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(reportedSubjectsService.selectReportedSubjectsById(id));
    }

    /**
     * 新增上报科目
     */
    @SaCheckPermission("budget:subjects:add")
    @Log(title = "上报科目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReportedSubjects reportedSubjects)
    {
        return toAjax(reportedSubjectsService.insertReportedSubjects(reportedSubjects));
    }

    /**
     * 修改上报科目
     */
    @SaCheckPermission("budget:subjects:edit")
    @Log(title = "上报科目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReportedSubjects reportedSubjects)
    {
        return toAjax(reportedSubjectsService.updateReportedSubjects(reportedSubjects));
    }

    /**
     * 删除上报科目
     */
    @SaCheckPermission("budget:subjects:remove")
    @Log(title = "上报科目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(reportedSubjectsService.deleteReportedSubjectsByIds(ids));
    }
}
