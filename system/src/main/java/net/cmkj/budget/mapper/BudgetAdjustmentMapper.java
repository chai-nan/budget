package net.cmkj.budget.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.budget.domain.BudgetAdjustment;

/**
 * 预算调整（OA填报）Mapper接口
 *
 * @author cmkj
 * @date 2024-10-16
 */
public interface BudgetAdjustmentMapper extends BaseMapper<BudgetAdjustment>
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
    public int insertBudgetAdjustment(BudgetAdjustment budgetAdjustment);

    /**
     * 修改预算调整（OA填报）
     *
     * @param budgetAdjustment 预算调整（OA填报）
     * @return 结果
     */
    public int updateBudgetAdjustment(BudgetAdjustment budgetAdjustment);

    /**
     * 删除预算调整（OA填报）
     *
     * @param id 预算调整（OA填报）主键
     * @return 结果
     */
    public int deleteBudgetAdjustmentById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算调整（OA填报）
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBudgetAdjustmentByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
