package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.BudgetOaAdjustment;
import net.cmkj.budget.mapper.BudgetOaAdjustmentMapper;
import net.cmkj.budget.service.IBudgetOaAdjustmentService;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * OA预算填报调整Service业务层处理
 *
 * @author cmkj
 * @date 2025-03-28
 */
@Service
public class BudgetOaAdjustmentServiceImpl extends ServiceImpl<BudgetOaAdjustmentMapper, BudgetOaAdjustment> implements IBudgetOaAdjustmentService
{

    /**
     * 查询OA预算填报调整
     *
     * @param id OA预算填报调整主键
     * @return OA预算填报调整
     */
    @Override
    public BudgetOaAdjustment selectBudgetOaAdjustmentById(Long id)
    {
        return baseMapper.selectBudgetOaAdjustmentById(id);
    }

    /**
     * 查询OA预算填报调整列表
     *
     * @param budgetOaAdjustment OA预算填报调整
     * @return OA预算填报调整
     */
    @Override
    public List<BudgetOaAdjustment> selectBudgetOaAdjustmentList(BudgetOaAdjustment budgetOaAdjustment)
    {
        return baseMapper.selectBudgetOaAdjustmentList(budgetOaAdjustment);
    }

    /**
     * 新增OA预算填报调整
     *
     * @param budgetOaAdjustment OA预算填报调整
     * @return 结果
     */
    @Override
    public int insertBudgetOaAdjustment(BudgetOaAdjustment budgetOaAdjustment)
    {
        budgetOaAdjustment.setCreateBy(SecurityUtils.getUsername());
        budgetOaAdjustment.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertBudgetOaAdjustment(budgetOaAdjustment);
    }

    /**
     * 修改OA预算填报调整
     *
     * @param budgetOaAdjustment OA预算填报调整
     * @return 结果
     */
    @Override
    public int updateBudgetOaAdjustment(BudgetOaAdjustment budgetOaAdjustment)
    {
        budgetOaAdjustment.setUpdateBy(SecurityUtils.getUsername());
        budgetOaAdjustment.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateBudgetOaAdjustment(budgetOaAdjustment);
    }

    /**
     * 批量删除OA预算填报调整
     * 
     * @param ids 需要删除的OA预算填报调整主键
     * @return 结果
     */
    @Override
    public int deleteBudgetOaAdjustmentByIds(Long[] ids)
    {
        return baseMapper.deleteBudgetOaAdjustmentByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除OA预算填报调整信息
     * 
     * @param id OA预算填报调整主键
     * @return 结果
     */
    @Override
    public int deleteBudgetOaAdjustmentById(Long id)
    {
        return baseMapper.deleteBudgetOaAdjustmentById(id, SecurityUtils.getUsername());
    }
}
