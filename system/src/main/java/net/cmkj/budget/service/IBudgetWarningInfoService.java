package net.cmkj.budget.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.BudgetWarningInfo;

/**
 * 预警详情Service接口
 * 
 * @author cmkj
 * @date 2024-11-27
 */
public interface IBudgetWarningInfoService extends IService<BudgetWarningInfo>
{
    /**
     * 查询预警详情
     * 
     * @param id 预警详情主键
     * @return 预警详情
     */
    public BudgetWarningInfo selectBudgetWarningInfoById(Long id);

    /**
     * 查询预警详情列表
     * 
     * @param budgetWarningInfo 预警详情
     * @return 预警详情集合
     */
    public List<BudgetWarningInfo> selectBudgetWarningInfoList(BudgetWarningInfo budgetWarningInfo);

    /**
     * 新增预警详情
     * 
     * @param budgetWarningInfo 预警详情
     * @return 结果
     */
    public int insertBudgetWarningInfo(BudgetWarningInfo budgetWarningInfo);

    /**
     * 修改预警详情
     * 
     * @param budgetWarningInfo 预警详情
     * @return 结果
     */
    public int updateBudgetWarningInfo(BudgetWarningInfo budgetWarningInfo);

    /**
     * 批量删除预警详情
     * 
     * @param ids 需要删除的预警详情主键集合
     * @return 结果
     */
    public int deleteBudgetWarningInfoByIds(Long[] ids);

    /**
     * 删除预警详情信息
     * 
     * @param id 预警详情主键
     * @return 结果
     */
    public int deleteBudgetWarningInfoById(Long id);
}
