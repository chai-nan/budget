package net.cmkj.budget.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.budget.domain.BudgetSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算科目Mapper接口
 *
 * @author cmkj
 * @date 2024-07-15
 */
@Mapper
public interface BudgetSubjectMapper extends BaseMapper<BudgetSubject>
{
    /**
     * 查询预算科目
     *
     * @param id 预算科目主键
     * @return 预算科目
     */
    public BudgetSubject selectBudgetSubjectById(Long id);

    /**
     * 查询预算科目列表
     *
     * @param budgetSubject 预算科目
     * @return 预算科目集合
     */
    public List<BudgetSubject> selectBudgetSubjectList(BudgetSubject budgetSubject);

    /**
     * 新增预算科目
     *
     * @param budgetSubject 预算科目
     * @return 结果
     */
    public int insertBudgetSubject(BudgetSubject budgetSubject);

    /**
     * 修改预算科目
     *
     * @param budgetSubject 预算科目
     * @return 结果
     */
    public int updateBudgetSubject(BudgetSubject budgetSubject);

    /**
     * 删除预算科目
     *
     * @param id 预算科目主键
     * @return 结果
     */
    public int deleteBudgetSubjectById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算科目
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBudgetSubjectByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    BudgetSubject selectByNameAndFinancelSubject(@Param("name")String name, @Param("financelSubject")String financelSubject);

    List<BudgetSubject> selectBudgetSubjectListByXs(BudgetSubject querySubject);

    List<BudgetSubject> selectBudgetSubjectListByGl(BudgetSubject querySubject);

    List<BudgetSubject> selectBudgetSubjectListByYf(BudgetSubject querySubject);

    List<BudgetSubject> selectBudgetSubjectListByZz(BudgetSubject querySubject);

    List<BudgetSubject> selectChildrenByParentId(Long parentId);

    List<BudgetSubject> listByDept(Long deptId);
}
