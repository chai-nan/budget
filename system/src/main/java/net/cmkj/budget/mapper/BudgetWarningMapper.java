package net.cmkj.budget.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.budget.domain.BudgetWarning;

/**
 * 预警配置Mapper接口
 *
 * @author cmkj
 * @date 2024-11-27
 */
public interface BudgetWarningMapper extends BaseMapper<BudgetWarning>
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
     * 删除预警配置
     *
     * @param id 预警配置主键
     * @return 结果
     */
    public int deleteBudgetWarningById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预警配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBudgetWarningByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
