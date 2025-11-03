package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.ActualCosts;
import net.cmkj.budget.domain.ActualCostsFile;
import net.cmkj.budget.service.IActualCostsFileService;
import net.cmkj.budget.service.IActualCostsService;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.config.CmkjConfig;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.file.FileUploadUtils;
import net.cmkj.common.utils.file.FileUtils;
import net.cmkj.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 实际费用Controller
 * 
 * @author cmkj
 * @date 2024-08-22
 */
@RestController
@RequestMapping("/budget/actualCosts")
public class ActualCostsController extends BaseController
{
    @Autowired
    private IActualCostsService actualCostsService;

    @Autowired
    private IActualCostsFileService actualCostsFileService;

    /**
     * 查询实际费用列表
     */
    @SaCheckPermission("budget:actualCosts:list")
    @GetMapping("/list")
    public TableDataInfo list(ActualCosts actualCosts)
    {
        startPage();
        List<ActualCosts> list = actualCostsService.selectActualCostsList(actualCosts);
        return getDataTable(list);
    }

    /**
     * 导出实际费用列表
     */
    @SaCheckPermission("budget:actualCosts:export")
    @Log(title = "实际费用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActualCosts actualCosts)
    {
        List<ActualCosts> list = actualCostsService.selectActualCostsList(actualCosts);
        ExcelUtil<ActualCosts> util = new ExcelUtil<ActualCosts>(ActualCosts.class);
        util.exportExcel(response, list, "实际费用数据");
    }

    @Log(title = "实际费用", businessType = BusinessType.IMPORT)
    @SaCheckPermission("budget:actualCosts:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ActualCostsFile actualCostsFile = new ActualCostsFile();
        if(uploadFile(file,actualCostsFile) && actualCostsFile.getId() != null){
            return actualCostsService.importActualCostsByFile(file, updateSupport, getUsername(),actualCostsFile);
        }else{
            return error("导入失败，文件保存异常");
        }
    }
    public AjaxResult importData_back(MultipartFile file, boolean updateSupport) throws Exception
    {
        ActualCostsFile actualCostsFile = new ActualCostsFile();
        if(uploadFile(file,actualCostsFile) && actualCostsFile.getId() != null){
            ExcelUtil<ActualCosts> util = new ExcelUtil<ActualCosts>(ActualCosts.class);
            List<ActualCosts> actualCostsList = util.importExcel(file.getInputStream());
            String operName = getUsername();
            String message = actualCostsService.importActualCosts(actualCostsList, updateSupport, operName,actualCostsFile);
            return success(message);
        }else{
            return error("导入失败，文件保存异常");
        }
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<ActualCosts> util = new ExcelUtil<ActualCosts>(ActualCosts.class);
        util.importTemplateExcel(response, "实际费用数据");
    }

    /**
     * 获取实际费用详细信息
     */
    @SaCheckPermission("budget:actualCosts:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(actualCostsService.selectActualCostsById(id));
    }

    /**
     * 新增实际费用
     */
    @SaCheckPermission("budget:actualCosts:add")
    @Log(title = "实际费用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActualCosts actualCosts)
    {
        return toAjax(actualCostsService.insertActualCosts(actualCosts));
    }

    /**
     * 修改实际费用
     */
    @SaCheckPermission("budget:actualCosts:edit")
    @Log(title = "实际费用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActualCosts actualCosts)
    {
        return toAjax(actualCostsService.updateActualCosts(actualCosts));
    }

    /**
     * 删除实际费用
     */
    @SaCheckPermission("budget:actualCosts:remove")
    @Log(title = "实际费用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(actualCostsService.deleteActualCostsByIds(ids));
    }


    public boolean uploadFile(MultipartFile file, ActualCostsFile actualCostsFile) {
        try {
            // 上传文件路径
            String filePath = CmkjConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            actualCostsFile.setSourceName(file.getOriginalFilename());
            actualCostsFile.setFileName(FileUtils.getName(fileName));
            actualCostsFile.setFilePath(fileName);
            actualCostsFile.setInfoNum(0);
            actualCostsFile.setDelFlag("1");
            actualCostsFileService.insertActualCostsFile(actualCostsFile);
            return true;
        } catch (Exception e){
            logger.error("实际费用文件上传异常",e.getMessage(),e);
            return false;
        }
    }
}
