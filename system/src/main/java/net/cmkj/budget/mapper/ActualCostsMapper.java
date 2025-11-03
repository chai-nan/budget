package net.cmkj.budget.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.budget.domain.ActualCosts;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 实际费用Mapper接口
 *
 * @author cmkj
 * @date 2024-08-22
 */
public interface ActualCostsMapper extends BaseMapper<ActualCosts>
{
    /**
     * 查询实际费用
     *
     * @param id 实际费用主键
     * @return 实际费用
     */
    public ActualCosts selectActualCostsById(Long id);

    /**
     * 查询实际费用列表
     *
     * @param actualCosts 实际费用
     * @return 实际费用集合
     */
    public List<ActualCosts> selectActualCostsList(ActualCosts actualCosts);

    /**
     * 新增实际费用
     *
     * @param actualCosts 实际费用
     * @return 结果
     */
    public int insertActualCosts(ActualCosts actualCosts);

    /**
     * 修改实际费用
     *
     * @param actualCosts 实际费用
     * @return 结果
     */
    public int updateActualCosts(ActualCosts actualCosts);

    /**
     * 删除实际费用
     *
     * @param id 实际费用主键
     * @return 结果
     */
    public int deleteActualCostsById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除实际费用
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActualCostsByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    ActualCosts selectActualCostsByOne(@Param("year")Integer year, @Param("itemId")Long itemId, @Param("deptId")Long deptId);

    ActualCosts selectLast(ActualCosts queryActualCosts);

    List<ActualCosts> selectActualCostsCountList(ActualCosts queryActualCosts);

    List<Map<String, Object>> selectTotals(Integer budgetYear);

    int deleteActualCostsByFileId(@Param("fileId")Long fileId, @Param("userName")String userName);
}
