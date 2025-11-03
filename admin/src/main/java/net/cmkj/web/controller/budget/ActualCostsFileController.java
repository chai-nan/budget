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
import net.cmkj.budget.domain.ActualCostsFile;
import net.cmkj.budget.service.IActualCostsFileService;
import net.cmkj.common.utils.poi.ExcelUtil;
import net.cmkj.common.core.page.TableDataInfo;

/**
 * 实际费用文件Controller
 * 
 * @author cmkj
 * @date 2025-01-21
 */
@RestController
@RequestMapping("/budget/actualCostsFile")
public class ActualCostsFileController extends BaseController
{
    @Autowired
    private IActualCostsFileService actualCostsFileService;

    /**
     * 查询实际费用文件列表
     */
    @SaCheckPermission("budget:actualCostsFile:list")
    @GetMapping("/list")
    public TableDataInfo list(ActualCostsFile actualCostsFile)
    {
        startPage();
        List<ActualCostsFile> list = actualCostsFileService.selectActualCostsFileList(actualCostsFile);
        return getDataTable(list);
    }

    /**
     * 导出实际费用文件列表
     */
    @SaCheckPermission("budget:actualCostsFile:export")
    @Log(title = "实际费用文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActualCostsFile actualCostsFile)
    {
        List<ActualCostsFile> list = actualCostsFileService.selectActualCostsFileList(actualCostsFile);
        ExcelUtil<ActualCostsFile> util = new ExcelUtil<ActualCostsFile>(ActualCostsFile.class);
        util.exportExcel(response, list, "实际费用文件数据");
    }

    /**
     * 获取实际费用文件详细信息
     */
    @SaCheckPermission("budget:actualCostsFile:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(actualCostsFileService.selectActualCostsFileById(id));
    }

    /**
     * 新增实际费用文件
     */
    @SaCheckPermission("budget:actualCostsFile:add")
    @Log(title = "实际费用文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActualCostsFile actualCostsFile)
    {
        return toAjax(actualCostsFileService.insertActualCostsFile(actualCostsFile));
    }

    /**
     * 修改实际费用文件
     */
    @SaCheckPermission("budget:actualCostsFile:edit")
    @Log(title = "实际费用文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActualCostsFile actualCostsFile)
    {
        return toAjax(actualCostsFileService.updateActualCostsFile(actualCostsFile));
    }

    /**
     * 删除实际费用文件
     */
    //@SaCheckPermission("budget:actualCostsFile:remove")
    @Log(title = "实际费用文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(actualCostsFileService.deleteActualCostsFileById(id));
    }
}
