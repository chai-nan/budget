package net.cmkj.budget.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.budget.domain.BudgetItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预算配置Mapper接口
 *
 * @author cmkj
 * @date 2024-07-15
 */
@Mapper
public interface BudgetItemMapper extends BaseMapper<BudgetItem>
{
    /**
     * 查询预算配置
     *
     * @param id 预算配置主键
     * @return 预算配置
     */
    public BudgetItem selectBudgetItemById(Long id);

    /**
     * 查询预算配置列表
     *
     * @param budgetItem 预算配置
     * @return 预算配置集合
     */
    public List<BudgetItem> selectBudgetItemList(BudgetItem budgetItem);

    /**
     * 新增预算配置
     *
     * @param budgetItem 预算配置
     * @return 结果
     */
    public int insertBudgetItem(BudgetItem budgetItem);

    /**
     * 修改预算配置
     *
     * @param budgetItem 预算配置
     * @return 结果
     */
    public int updateBudgetItem(BudgetItem budgetItem);

    /**
     * 删除预算配置
     *
     * @param id 预算配置主键
     * @return 结果
     */
    public int deleteBudgetItemById(Long id);

    /**
     * 批量删除预算配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBudgetItemByIds(Long[] ids);

    BudgetItem selectBudgetItemByName(String itemName);

    List<BudgetItem> selectBudgetItemByFinancialName(String kjkmsm);
}
