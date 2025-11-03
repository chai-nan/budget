package net.cmkj.budget.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.BudgetItem;

import java.util.List;

/**
 * 预算配置Service接口
 * 
 * @author cmkj
 * @date 2024-07-15
 */
public interface IBudgetItemService extends IService<BudgetItem>
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
     * 修改预算配置状态
     * 
     */
    public int updateStatus(BudgetItem budgetItem);

}
