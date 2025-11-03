package net.cmkj.budget.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.budget.domain.BudgetItem;
import net.cmkj.budget.domain.BudgetItemDept;
import net.cmkj.common.core.domain.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 填报科目部门关联Mapper接口
 *
 * @author cmkj
 * @date 2024-07-31
 */
@Mapper
public interface BudgetItemDeptMapper extends BaseMapper<BudgetItemDept>
{
    /**
     * 查询填报科目部门关联列表
     *
     * @param itemId 填报科目部门关联
     * @return 填报科目部门关联集合
     */
    public List<BudgetItemDept> selectBudgetItemDeptListByItemId(Long itemId);


    public List<BudgetItem> selectBudgetItemDeptListByDeptId(Long DeptId);

    public List<SysDept> selectDeptListByItemId(Long itemId);
    /**
     * 新增填报科目部门关联
     *
     * @param budgetItemDept 填报科目部门关联
     * @return 结果
     */
    public int insertBudgetItemDept(BudgetItemDept budgetItemDept);

    /**
     * 删除填报科目部门关联
     *
     * @param itemId 填报科目部门关联主键
     * @return 结果
     */
    public int deleteBudgetItemDeptByItemId(Long itemId);

    public int batchItemDept(List<BudgetItemDept> list);

    Long[] selectDeptIdsByItem(Long id);

    Long[] selectItemByDepts(Long[] depts);

}
