package net.cmkj.budget.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.budget.domain.BudgetWarningInfo;

/**
 * 预警详情Mapper接口
 *
 * @author cmkj
 * @date 2024-11-27
 */
public interface BudgetWarningInfoMapper extends BaseMapper<BudgetWarningInfo>
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
     * 删除预警详情
     *
     * @param id 预警详情主键
     * @return 结果
     */
    public int deleteBudgetWarningInfoById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预警详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBudgetWarningInfoByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
