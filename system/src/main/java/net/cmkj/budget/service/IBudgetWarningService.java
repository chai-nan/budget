package net.cmkj.budget.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.Budget;
import net.cmkj.budget.domain.BudgetWarning;
import net.cmkj.common.core.domain.AjaxResult;

import java.util.List;
import java.util.Map;

/**
 * 预警配置Service接口
 * 
 * @author cmkj
 * @date 2024-11-27
 */
public interface IBudgetWarningService extends IService<BudgetWarning>
{
    /**
     * 查询预警配置
     * 
     * @param id 预警配置主键
     * @return 预警配置
     */
    public BudgetWarning selectBudgetWarningById(Long id);

    /**
     * 查询预警配置列表
     * 
     * @param budgetWarning 预警配置
     * @return 预警配置集合
     */
    public List<BudgetWarning> selectBudgetWarningList(BudgetWarning budgetWarning);

    /**
     * 新增预警配置
     * 
     * @param budgetWarning 预警配置
     * @return 结果
     */
    public int insertBudgetWarning(BudgetWarning budgetWarning);

    /**
     * 修改预警配置
     * 
     * @param budgetWarning 预警配置
     * @return 结果
     */
    public int updateBudgetWarning(BudgetWarning budgetWarning);

    /**
     * 批量删除预警配置
     * 
     * @param ids 需要删除的预警配置主键集合
     * @return 结果
     */
    public int deleteBudgetWarningByIds(Long[] ids);

    /**
     * 删除预警配置信息
     * 
     * @param id 预警配置主键
     * @return 结果
     */
    public int deleteBudgetWarningById(Long id);

    void warningTask();

    void reportWarning();

    void exceedWarning();

    void costWarning();

    void economicMattersWarning();

    Map<String, Object> getNotReportedList(Budget budget);
}
