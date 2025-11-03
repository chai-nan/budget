package net.cmkj.budget.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.BudgetAdjustment;
import net.cmkj.common.core.domain.AjaxResult;

/**
 * 预算调整（OA填报）Service接口
 * 
 * @author cmkj
 * @date 2024-10-16
 */
public interface IBudgetAdjustmentService extends IService<BudgetAdjustment>
{
    /**
     * 查询预算调整（OA填报）
     * 
     * @param id 预算调整（OA填报）主键
     * @return 预算调整（OA填报）
     */
    public BudgetAdjustment selectBudgetAdjustmentById(Long id);

    /**
     * 查询预算调整（OA填报）列表
     * 
     * @param budgetAdjustment 预算调整（OA填报）
     * @return 预算调整（OA填报）集合
     */
    public List<BudgetAdjustment> selectBudgetAdjustmentList(BudgetAdjustment budgetAdjustment);

    /**
     * 新增预算调整（OA填报）
     * 
     * @param budgetAdjustment 预算调整（OA填报）
     * @return 结果
     */
    public AjaxResult insertBudgetAdjustment(BudgetAdjustment budgetAdjustment);

    /**
     * 修改预算调整（OA填报）
     * 
     * @param budgetAdjustment 预算调整（OA填报）
     * @return 结果
     */
    public AjaxResult updateBudgetAdjustment(BudgetAdjustment budgetAdjustment);

    /**
     * 批量删除预算调整（OA填报）
     * 
     * @param ids 需要删除的预算调整（OA填报）主键集合
     * @return 结果
     */
    public int deleteBudgetAdjustmentByIds(Long[] ids);

    /**
     * 删除预算调整（OA填报）信息
     * 
     * @param id 预算调整（OA填报）主键
     * @return 结果
     */
    public int deleteBudgetAdjustmentById(Long id);

    public int deleteBudgetAdjustmentinfo(Long infoId);
}
