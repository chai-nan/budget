package net.cmkj.budget.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.BudgetSubject;
import net.cmkj.common.core.domain.AjaxResult;

import java.util.List;

/**
 * 预算科目Service接口
 * 
 * @author cmkj
 * @date 2024-07-15
 */
public interface IBudgetSubjectService extends IService<BudgetSubject>
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
    public AjaxResult insertBudgetSubject(BudgetSubject budgetSubject);

    /**
     * 修改预算科目
     * 
     * @param budgetSubject 预算科目
     * @return 结果
     */
    public AjaxResult updateBudgetSubject(BudgetSubject budgetSubject);

    /**
     * 批量删除预算科目
     * 
     * @param ids 需要删除的预算科目主键集合
     * @return 结果
     */
    public int deleteBudgetSubjectByIds(Long[] ids);

    /**
     * 删除预算科目信息
     * 
     * @param id 预算科目主键
     * @return 结果
     */
    public int deleteBudgetSubjectById(Long id);

    List<BudgetSubject> selectBudgetSubjectListByXs(BudgetSubject querySubject);

    List<BudgetSubject> selectBudgetSubjectListByGl(BudgetSubject querySubject);

    List<BudgetSubject> selectBudgetSubjectListByYf(BudgetSubject querySubject);

    List<BudgetSubject> selectBudgetSubjectListByZz(BudgetSubject querySubject);

    List<BudgetSubject> selectChildrenByParentId(Long parentId);

    List<BudgetSubject> listByDept(Long deptId);
}
