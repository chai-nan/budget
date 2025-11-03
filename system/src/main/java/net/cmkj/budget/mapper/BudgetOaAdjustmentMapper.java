package net.cmkj.budget.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.budget.domain.BudgetOaAdjustment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OA预算填报调整Mapper接口
 *
 * @author cmkj
 * @date 2025-03-28
 */
public interface BudgetOaAdjustmentMapper extends BaseMapper<BudgetOaAdjustment>
{
    /**
     * 查询OA预算填报调整
     *
     * @param id OA预算填报调整主键
     * @return OA预算填报调整
     */
    public BudgetOaAdjustment selectBudgetOaAdjustmentById(Long id);

    /**
     * 查询OA预算填报调整列表
     *
     * @param budgetOaAdjustment OA预算填报调整
     * @return OA预算填报调整集合
     */
    public List<BudgetOaAdjustment> selectBudgetOaAdjustmentList(BudgetOaAdjustment budgetOaAdjustment);

    /**
     * 新增OA预算填报调整
     *
     * @param budgetOaAdjustment OA预算填报调整
     * @return 结果
     */
    public int insertBudgetOaAdjustment(BudgetOaAdjustment budgetOaAdjustment);

    /**
     * 修改OA预算填报调整
     *
     * @param budgetOaAdjustment OA预算填报调整
     * @return 结果
     */
    public int updateBudgetOaAdjustment(BudgetOaAdjustment budgetOaAdjustment);

    /**
     * 删除OA预算填报调整
     *
     * @param id OA预算填报调整主键
     * @return 结果
     */
    public int deleteBudgetOaAdjustmentById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除OA预算填报调整
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBudgetOaAdjustmentByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
