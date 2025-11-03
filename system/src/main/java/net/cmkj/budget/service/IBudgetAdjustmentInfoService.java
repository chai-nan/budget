package net.cmkj.budget.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.BudgetAdjustmentInfo;

/**
 * 预算调整明细（OA填报）Service接口
 * 
 * @author cmkj
 * @date 2024-10-28
 */
public interface IBudgetAdjustmentInfoService extends IService<BudgetAdjustmentInfo>
{
    /**
     * 查询预算调整明细（OA填报）
     * 
     * @param id 预算调整明细（OA填报）主键
     * @return 预算调整明细（OA填报）
     */
    public BudgetAdjustmentInfo selectBudgetAdjustmentInfoById(Long id);

    /**
     * 查询预算调整明细（OA填报）列表
     * 
     * @param budgetAdjustmentInfo 预算调整明细（OA填报）
     * @return 预算调整明细（OA填报）集合
     */
    public List<BudgetAdjustmentInfo> selectBudgetAdjustmentInfoList(BudgetAdjustmentInfo budgetAdjustmentInfo);

    /**
     * 新增预算调整明细（OA填报）
     * 
     * @param budgetAdjustmentInfo 预算调整明细（OA填报）
     * @return 结果
     */
    public int insertBudgetAdjustmentInfo(BudgetAdjustmentInfo budgetAdjustmentInfo);

    /**
     * 修改预算调整明细（OA填报）
     * 
     * @param budgetAdjustmentInfo 预算调整明细（OA填报）
     * @return 结果
     */
    public int updateBudgetAdjustmentInfo(BudgetAdjustmentInfo budgetAdjustmentInfo);

    /**
     * 批量删除预算调整明细（OA填报）
     * 
     * @return 结果
     */
    public int deleteBudgetAdjustmentInfoByAdjustment(Long adjustmentId);

    /**
     * 删除预算调整明细（OA填报）信息
     * 
     * @param id 预算调整明细（OA填报）主键
     * @return 结果
     */
    public int deleteBudgetAdjustmentInfoById(Long id);
}
