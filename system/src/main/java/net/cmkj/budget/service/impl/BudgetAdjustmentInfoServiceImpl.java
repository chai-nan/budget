package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.budget.mapper.BudgetAdjustmentInfoMapper;
import net.cmkj.budget.domain.BudgetAdjustmentInfo;
import net.cmkj.budget.service.IBudgetAdjustmentInfoService;

import java.util.List;
/**
 * 预算调整明细（OA填报）Service业务层处理
 *
 * @author cmkj
 * @date 2024-10-28
 */
@Service
public class BudgetAdjustmentInfoServiceImpl extends ServiceImpl<BudgetAdjustmentInfoMapper, BudgetAdjustmentInfo> implements IBudgetAdjustmentInfoService
{

    /**
     * 查询预算调整明细（OA填报）
     *
     * @param id 预算调整明细（OA填报）主键
     * @return 预算调整明细（OA填报）
     */
    @Override
    public BudgetAdjustmentInfo selectBudgetAdjustmentInfoById(Long id)
    {
        return baseMapper.selectBudgetAdjustmentInfoById(id);
    }

    /**
     * 查询预算调整明细（OA填报）列表
     *
     * @param budgetAdjustmentInfo 预算调整明细（OA填报）
     * @return 预算调整明细（OA填报）
     */
    @Override
    public List<BudgetAdjustmentInfo> selectBudgetAdjustmentInfoList(BudgetAdjustmentInfo budgetAdjustmentInfo)
    {
        return baseMapper.selectBudgetAdjustmentInfoList(budgetAdjustmentInfo);
    }

    /**
     * 新增预算调整明细（OA填报）
     *
     * @param budgetAdjustmentInfo 预算调整明细（OA填报）
     * @return 结果
     */
    @Override
    public int insertBudgetAdjustmentInfo(BudgetAdjustmentInfo budgetAdjustmentInfo)
    {
        return baseMapper.insertBudgetAdjustmentInfo(budgetAdjustmentInfo);
    }

    /**
     * 修改预算调整明细（OA填报）
     *
     * @param budgetAdjustmentInfo 预算调整明细（OA填报）
     * @return 结果
     */
    @Override
    public int updateBudgetAdjustmentInfo(BudgetAdjustmentInfo budgetAdjustmentInfo)
    {
        return baseMapper.updateBudgetAdjustmentInfo(budgetAdjustmentInfo);
    }

    /**
     * 批量删除预算调整明细（OA填报）
     * 
     * @return 结果
     */
    @Override
    public int deleteBudgetAdjustmentInfoByAdjustment(Long adjustmentId)
    {
        return baseMapper.deleteBudgetAdjustmentInfoByAdjustment(adjustmentId);
    }

    /**
     * 删除预算调整明细（OA填报）信息
     * 
     * @param id 预算调整明细（OA填报）主键
     * @return 结果
     */
    @Override
    public int deleteBudgetAdjustmentInfoById(Long id)
    {
        return baseMapper.deleteBudgetAdjustmentInfoById(id);
    }
}
