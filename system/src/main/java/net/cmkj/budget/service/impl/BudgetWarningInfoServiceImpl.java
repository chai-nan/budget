package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.budget.mapper.BudgetWarningInfoMapper;
import net.cmkj.budget.domain.BudgetWarningInfo;
import net.cmkj.budget.service.IBudgetWarningInfoService;

import java.util.List;
/**
 * 预警详情Service业务层处理
 *
 * @author cmkj
 * @date 2024-11-27
 */
@Service
public class BudgetWarningInfoServiceImpl extends ServiceImpl<BudgetWarningInfoMapper, BudgetWarningInfo> implements IBudgetWarningInfoService
{

    /**
     * 查询预警详情
     *
     * @param id 预警详情主键
     * @return 预警详情
     */
    @Override
    public BudgetWarningInfo selectBudgetWarningInfoById(Long id)
    {
        return baseMapper.selectBudgetWarningInfoById(id);
    }

    /**
     * 查询预警详情列表
     *
     * @param budgetWarningInfo 预警详情
     * @return 预警详情
     */
    @Override
    public List<BudgetWarningInfo> selectBudgetWarningInfoList(BudgetWarningInfo budgetWarningInfo)
    {
        return baseMapper.selectBudgetWarningInfoList(budgetWarningInfo);
    }

    /**
     * 新增预警详情
     *
     * @param budgetWarningInfo 预警详情
     * @return 结果
     */
    @Override
    public int insertBudgetWarningInfo(BudgetWarningInfo budgetWarningInfo)
    {
        return baseMapper.insertBudgetWarningInfo(budgetWarningInfo);
    }

    /**
     * 修改预警详情
     *
     * @param budgetWarningInfo 预警详情
     * @return 结果
     */
    @Override
    public int updateBudgetWarningInfo(BudgetWarningInfo budgetWarningInfo)
    {
        return baseMapper.updateBudgetWarningInfo(budgetWarningInfo);
    }

    /**
     * 批量删除预警详情
     * 
     * @param ids 需要删除的预警详情主键
     * @return 结果
     */
    @Override
    public int deleteBudgetWarningInfoByIds(Long[] ids)
    {
        return baseMapper.deleteBudgetWarningInfoByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预警详情信息
     * 
     * @param id 预警详情主键
     * @return 结果
     */
    @Override
    public int deleteBudgetWarningInfoById(Long id)
    {
        return baseMapper.deleteBudgetWarningInfoById(id, SecurityUtils.getUsername());
    }
}
