package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.BudgetSubject;
import net.cmkj.budget.domain.BudgetSubjectFinance;
import net.cmkj.budget.mapper.BudgetSubjectFinanceMapper;
import net.cmkj.budget.mapper.BudgetSubjectMapper;
import net.cmkj.budget.service.IBudgetSubjectService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预算科目Service业务层处理
 * 
 * @author cmkj
 * @date 2024-07-15
 */
@Service
public class BudgetSubjectServiceImpl extends ServiceImpl<BudgetSubjectMapper, BudgetSubject> implements IBudgetSubjectService
{
    @Autowired
    private BudgetSubjectFinanceMapper subjectFinanceMapper;

    /**
     * 查询预算科目
     * 
     * @param id 预算科目主键
     * @return 预算科目
     */
    @Override
    public BudgetSubject selectBudgetSubjectById(Long id)
    {
        return baseMapper.selectBudgetSubjectById(id);
    }

    /**
     * 查询预算科目列表
     * 
     * @param budgetSubject 预算科目
     * @return 预算科目
     */
    @Override
    public List<BudgetSubject> selectBudgetSubjectList(BudgetSubject budgetSubject)
    {
        List<BudgetSubject> budgetSubjects = baseMapper.selectBudgetSubjectList(budgetSubject);
        for (BudgetSubject subjects : budgetSubjects) {
            if(subjects.getParentId()==0 && subjects.getLevel()==1){
                subjects.setChildren(getChildList(budgetSubjects,subjects));
            }else {
                subjects.setChildren(new ArrayList<>());
            }
        }
        return budgetSubjects;
    }
    private List<BudgetSubject> getChildList(List<BudgetSubject> list, BudgetSubject subject)
    {
        List<BudgetSubject> childList = new ArrayList<>();
        for (BudgetSubject budgetSubject : list) {
            if(budgetSubject.getParentId().longValue()==subject.getId().longValue()){
                childList.add(budgetSubject);
            }
        }
        return childList;
    }

    /**
     * 新增预算科目
     * 
     * @param budgetSubject 预算科目
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertBudgetSubject(BudgetSubject budgetSubject)
    {
        BudgetSubject query = baseMapper.selectByNameAndFinancelSubject(budgetSubject.getName(),budgetSubject.getFinancelSubject());
        if(query!=null){
            return AjaxResult.error("【"+budgetSubject.getName()+"】该科目已存在，请勿重新添加！");
        }
        budgetSubject.setParentId(budgetSubject.getParentId()==null?0:budgetSubject.getParentId());
        budgetSubject.setLevel(budgetSubject.getParentId()==0?1:2);
        budgetSubject.setAncestors(budgetSubject.getParentId()==0?"0":baseMapper.selectById(budgetSubject.getParentId()).getAncestors()+","+budgetSubject.getParentId());
        budgetSubject.setDelFlag("0");
        budgetSubject.setCreateTime(DateUtils.getNowDate());
        budgetSubject.setCreateBy(SecurityUtils.getUsername());
        if(baseMapper.insert(budgetSubject)>0){
            setFinancelSubject(budgetSubject);
        }
        return AjaxResult.success();
    }

    /**
     * 修改预算科目
     * 
     * @param budgetSubject 预算科目
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateBudgetSubject(BudgetSubject budgetSubject)
    {
        BudgetSubject query = baseMapper.selectByNameAndFinancelSubject(budgetSubject.getName(),budgetSubject.getFinancelSubject());
        if(query!=null && query.getId().longValue()!=budgetSubject.getId().longValue()){
            return AjaxResult.error("【"+budgetSubject.getName()+"】该科目已存在！");
        }
        setFinancelSubject(budgetSubject);
        budgetSubject.setLevel(budgetSubject.getParentId()==0?1:2);
        budgetSubject.setAncestors(budgetSubject.getParentId()==0?"0":baseMapper.selectById(budgetSubject.getParentId()).getAncestors()+","+budgetSubject.getParentId());
        budgetSubject.setUpdateTime(new Date());
        budgetSubject.setUpdateBy(SecurityUtils.getUsername());
        return baseMapper.updateBudgetSubject(budgetSubject) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 批量删除预算科目
     * 
     * @param ids 需要删除的预算科目主键
     * @return 结果
     */
    @Override
    public int deleteBudgetSubjectByIds(Long[] ids)
    {
        return baseMapper.deleteBudgetSubjectByIds(ids,SecurityUtils.getUsername());
    }

    /**
     * 删除预算科目信息
     * 
     * @param id 预算科目主键
     * @return 结果
     */
    @Override
    public int deleteBudgetSubjectById(Long id)
    {
        return baseMapper.deleteBudgetSubjectById(id,SecurityUtils.getUsername());
    }

    @Override
    public List<BudgetSubject> selectBudgetSubjectListByXs(BudgetSubject querySubject) {
        return baseMapper.selectBudgetSubjectListByXs(querySubject);
    }

    @Override
    public List<BudgetSubject> selectBudgetSubjectListByGl(BudgetSubject querySubject) {
        return baseMapper.selectBudgetSubjectListByGl(querySubject);
    }

    @Override
    public List<BudgetSubject> selectBudgetSubjectListByYf(BudgetSubject querySubject) {
        return baseMapper.selectBudgetSubjectListByYf(querySubject);
    }

    @Override
    public List<BudgetSubject> selectBudgetSubjectListByZz(BudgetSubject querySubject) {
        return baseMapper.selectBudgetSubjectListByZz(querySubject);
    }

    @Override
    public List<BudgetSubject> selectChildrenByParentId(Long parentId) {
        return baseMapper.selectChildrenByParentId(parentId);
    }

    @Override
    public List<BudgetSubject> listByDept(Long deptId) {
        return baseMapper.listByDept(deptId);
    }

    public void setFinancelSubject(BudgetSubject budgetSubject){
        subjectFinanceMapper.deleteBudgetSubjectFinanceBySubjectId(budgetSubject.getId());
        String[] ids = budgetSubject.getFinancelSubject().split(",");
        List<BudgetSubjectFinance> datas = new ArrayList<>();
        for (String id : ids) {
            BudgetSubjectFinance subjectFinance = new BudgetSubjectFinance();
            subjectFinance.setSubjectId(budgetSubject.getId());
            subjectFinance.setFinancelSubject(id);
            datas.add(subjectFinance);
        }
        subjectFinanceMapper.insertList(datas);
    }
}
