package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.BudgetItem;
import net.cmkj.budget.domain.BudgetItemDept;
import net.cmkj.budget.domain.BudgetSubject;
import net.cmkj.budget.mapper.BudgetItemDeptMapper;
import net.cmkj.budget.mapper.BudgetItemMapper;
import net.cmkj.budget.mapper.BudgetSubjectMapper;
import net.cmkj.budget.service.IBudgetItemService;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.StringUtils;
import net.cmkj.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 预算配置Service业务层处理
 * 
 * @author cmkj
 * @date 2024-07-15
 */
@Service
public class BudgetItemServiceImpl extends ServiceImpl<BudgetItemMapper, BudgetItem> implements IBudgetItemService
{

    @Autowired
    private BudgetItemDeptMapper budgetItemDeptMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private BudgetSubjectMapper subjectMapper;
    /**
     * 查询预算配置
     * 
     * @param id 预算配置主键
     * @return 预算配置
     */
    @Override
    public BudgetItem selectBudgetItemById(Long id)
    {
        BudgetItem budgetItem = baseMapper.selectById(id);
        if(budgetItem != null){
            budgetItem.setDeptIds(budgetItemDeptMapper.selectDeptIdsByItem(budgetItem.getId()));
        }
        return budgetItem;
    }

    /**
     * 查询预算配置列表
     * 
     * @param budgetItem 预算配置
     * @return 预算配置
     */
    @Override
    public List<BudgetItem> selectBudgetItemList(BudgetItem budgetItem)
    {
        return baseMapper.selectBudgetItemList(budgetItem);
    }

    /**
     * 新增预算配置
     * 
     * @param budgetItem 预算配置
     * @return 结果
     */
    @Override
    @Transactional
    public int insertBudgetItem(BudgetItem budgetItem)
    {
        budgetItem.setCreateTime(DateUtils.getNowDate());
        budgetItem.setCreateBy(SecurityUtils.getUsername());
        return baseMapper.insert(budgetItem);
    }


    /**
     * 修改预算配置
     * 
     * @param budgetItem 预算配置
     * @return 结果
     */
    @Override
    @Transactional
    public int updateBudgetItem(BudgetItem budgetItem)
    {
        budgetItem.setUpdateTime(DateUtils.getNowDate());
        budgetItem.setUpdateBy(SecurityUtils.getUsername());
        budgetItemDeptMapper.deleteBudgetItemDeptByItemId(budgetItem.getId());
        insertBudgetItemDept(budgetItem);
        return baseMapper.updateById(budgetItem);
    }

    private void insertBudgetItemDept(BudgetItem budgetItem) {
        Long[] deptIds = budgetItem.getDeptIds();
        Long itemId = budgetItem.getId();
        if (StringUtils.isNotEmpty(deptIds) && deptIds.length > 0 && itemId != null)
        {
            List<BudgetItemDept> list = new ArrayList<BudgetItemDept>(deptIds.length);
            for (Long deptId : deptIds){
                SysDept dept = deptMapper.selectDeptById(deptId);
                if(dept!=null && dept.getLevel()==2){
                    BudgetItemDept bid = new BudgetItemDept();
                    bid.setDeptId(deptId);
                    bid.setItemId(itemId);
                    list.add(bid);
                }
            }
            budgetItemDeptMapper.batchItemDept(list);
        }
    }

    /**
     * 批量删除预算配置
     * 
     * @return 结果
     */
    @Override
    public int updateStatus(BudgetItem budgetItem)
    {
        budgetItem.setUpdateTime(DateUtils.getNowDate());
        budgetItem.setUpdateBy(SecurityUtils.getUsername());
        return baseMapper.updateById(budgetItem);
    }
}
