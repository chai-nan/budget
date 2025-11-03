package net.cmkj.web.controller.fixed;

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
import net.cmkj.fixed.domain.WagesSubject;
import net.cmkj.fixed.service.IWagesSubjectService;
import net.cmkj.common.utils.poi.ExcelUtil;
import net.cmkj.common.core.page.TableDataInfo;

/**
 * 工资字段类型Controller
 * 
 * @author cmkj
 * @date 2024-09-23
 */
@RestController
@RequestMapping("/fixed/wagesSubject")
public class WagesSubjectController extends BaseController
{
    @Autowired
    private IWagesSubjectService wagesSubjectService;

    /**
     * 查询工资字段类型列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WagesSubject wagesSubject)
    {
        startPage();
        List<WagesSubject> list = wagesSubjectService.selectWagesSubjectList(wagesSubject);
        return getDataTable(list);
    }

    /**
     * 导出工资字段类型列表
     */
//    @SaCheckPermission("fixed:wagesSubject:export")
//    @Log(title = "工资字段类型", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, WagesSubject wagesSubject)
//    {
//        List<WagesSubject> list = wagesSubjectService.selectWagesSubjectList(wagesSubject);
//        ExcelUtil<WagesSubject> util = new ExcelUtil<WagesSubject>(WagesSubject.class);
//        util.exportExcel(response, list, "工资字段类型数据");
//    }

    /**
     * 获取工资字段类型详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(wagesSubjectService.selectWagesSubjectById(id));
    }

    /**
     * 新增工资字段类型
     */
//    @SaCheckPermission("fixed:wagesSubject:add")
//    @Log(title = "工资字段类型", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody WagesSubject wagesSubject)
//    {
//        return toAjax(wagesSubjectService.insertWagesSubject(wagesSubject));
//    }

    /**
     * 修改工资字段类型
     */
    @Log(title = "工资字段类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WagesSubject wagesSubject)
    {
        return toAjax(wagesSubjectService.updateWagesSubject(wagesSubject));
    }

//    /**
//     * 删除工资字段类型
//     */
//    @SaCheckPermission("fixed:wagesSubject:remove")
//    @Log(title = "工资字段类型", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Integer[] ids)
//    {
//        return toAjax(wagesSubjectService.deleteWagesSubjectByIds(ids));
//    }
}
