package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.*;
import net.cmkj.budget.mapper.BudgetAdjustmentInfoMapper;
import net.cmkj.budget.mapper.BudgetItemDeptMapper;
import net.cmkj.budget.service.IBudgetItemService;
import net.cmkj.budget.service.IBudgetSubjectService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.cmkj.budget.mapper.BudgetAdjustmentMapper;
import net.cmkj.budget.service.IBudgetAdjustmentService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
/**
 * 预算调整（OA填报）Service业务层处理
 *
 * @author cmkj
 * @date 2024-10-16
 */
@Service
public class BudgetAdjustmentServiceImpl extends ServiceImpl<BudgetAdjustmentMapper, BudgetAdjustment> implements IBudgetAdjustmentService
{

    @Autowired
    private IBudgetSubjectService subjectService;

    @Autowired
    private IBudgetItemService budgetItemService;

    @Autowired
    private BudgetItemDeptMapper budgetItemDeptMapper;

    @Autowired
    private BudgetAdjustmentInfoMapper infoMapper;

    /**
     * 查询预算调整（OA填报）
     *
     * @param id 预算调整（OA填报）主键
     * @return 预算调整（OA填报）
     */
    @Override
    public BudgetAdjustment selectBudgetAdjustmentById(Long id)
    {
        BudgetAdjustment budgetAdjustment = baseMapper.selectBudgetAdjustmentById(id);
        if(budgetAdjustment!=null){
            budgetAdjustment.setItems(infoMapper.selectInfoByAdjustment(budgetAdjustment.getId()));
        }
        return budgetAdjustment;
    }

    /**
     * 查询预算调整（OA填报）列表
     *
     * @param budgetAdjustment 预算调整（OA填报）
     * @return 预算调整（OA填报）
     */
    @Override
    public List<BudgetAdjustment> selectBudgetAdjustmentList(BudgetAdjustment budgetAdjustment)
    {
        return baseMapper.selectBudgetAdjustmentList(budgetAdjustment);
    }

    /**
     * 新增预算调整（OA填报）
     *
     * @param budgetAdjustment 预算调整（OA填报）
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertBudgetAdjustment(BudgetAdjustment budgetAdjustment)
    {
        int index = 0;
        if(budgetAdjustment.getId()==null){
            budgetAdjustment.setCreateBy(SecurityUtils.getUsername());
            budgetAdjustment.setCreateTime(DateUtils.getNowDate());
            index = baseMapper.insertBudgetAdjustment(budgetAdjustment);
        }else{
            budgetAdjustment.setUpdateBy(SecurityUtils.getUsername());
            budgetAdjustment.setUpdateTime(DateUtils.getNowDate());
            index = baseMapper.updateBudgetAdjustment(budgetAdjustment);
        }
        if(index>0){
            List<BudgetAdjustmentInfo> infos = budgetAdjustment.getItems();
            String msg = "明细信息保存失败！";
            try {
                for (BudgetAdjustmentInfo info : infos) {
                    BudgetSubject budgetSubject = subjectService.selectBudgetSubjectById(info.getSubjectId());
                    if(budgetSubject==null){
                        msg = "预算科目不存在！";
                        throw new Exception();
                    }
                    BudgetItem item = new BudgetItem();
                    item.setSubjectId(budgetSubject.getId());
                    List<BudgetItem> items = budgetItemService.selectBudgetItemList(item);
                    boolean out =  false;
                    boolean in =  false;
                    for (BudgetItem budgetItem : items) {
                        List<BudgetItemDept> budgetItemDepts = budgetItemDeptMapper.selectBudgetItemDeptListByItemId(budgetItem.getId());
                        for (BudgetItemDept budgetItemDept : budgetItemDepts) {
                            if(budgetItemDept.getDeptId().longValue()==info.getOutDept().longValue()){
                                out = true;
                            }
                            if(budgetItemDept.getDeptId().longValue()==info.getInDept().longValue()){
                                in = true;
                            }
                        }
                        if(out && in){
                            info.setAdjustmentId(budgetAdjustment.getId());
                            if(info.getId()!=null){
                                if(infoMapper.updateBudgetAdjustmentInfo(info)<0){
                                    throw new Exception();
                                }
                            }else{
                                if(infoMapper.insertBudgetAdjustmentInfo(info)<0){
                                    throw new Exception();
                                }
                            }
                        }else{
                            msg = "部门暂无相同预算表信息！";
                            throw new Exception();
                        }
                    }

                }
                return AjaxResult.success();
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AjaxResult.error(msg);
            }
        }
        return AjaxResult.error("操作失败！");
    }

    /**
     * 修改预算调整（OA填报）
     *
     * @param budgetAdjustment 预算调整（OA填报）
     * @return 结果
     */
    @Override
    public AjaxResult updateBudgetAdjustment(BudgetAdjustment budgetAdjustment)
    {
        budgetAdjustment.setUpdateBy(SecurityUtils.getUsername());
        budgetAdjustment.setUpdateTime(DateUtils.getNowDate());
        BudgetSubject budgetSubject = subjectService.selectBudgetSubjectById(budgetAdjustment.getSubjectId());
        if(budgetSubject==null){
            return AjaxResult.error("预算科目不存在");
        }
        BudgetItem item = new BudgetItem();
        item.setSubjectId(budgetSubject.getId());
        List<BudgetItem> items = budgetItemService.selectBudgetItemList(item);
        boolean out =  false;
        boolean in =  false;
        for (BudgetItem budgetItem : items) {
            List<BudgetItemDept> budgetItemDepts = budgetItemDeptMapper.selectBudgetItemDeptListByItemId(budgetItem.getId());
            for (BudgetItemDept budgetItemDept : budgetItemDepts) {
                if(budgetItemDept.getDeptId().longValue()==budgetAdjustment.getOutDept().longValue()){
                    out = true;
                }
                if(budgetItemDept.getDeptId().longValue()==budgetAdjustment.getInDept().longValue()){
                    in = true;
                }
            }
            if(out && in){
                budgetAdjustment.setItemId(budgetItem.getId());
                return baseMapper.updateBudgetAdjustment(budgetAdjustment)>0?AjaxResult.success():AjaxResult.error();
            }
        }
        return AjaxResult.error("部门暂无相同预算表信息");

    }

    /**
     * 批量删除预算调整（OA填报）
     * 
     * @param ids 需要删除的预算调整（OA填报）主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteBudgetAdjustmentByIds(Long[] ids)
    {
        for (Long id : ids) {
            infoMapper.deleteBudgetAdjustmentInfoByAdjustment(id);
        }
        return baseMapper.deleteBudgetAdjustmentByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算调整（OA填报）信息
     * 
     * @param id 预算调整（OA填报）主键
     * @return 结果
     */
    @Override
    public int deleteBudgetAdjustmentById(Long id)
    {
        return baseMapper.deleteBudgetAdjustmentById(id, SecurityUtils.getUsername());
    }

    @Override
    public int deleteBudgetAdjustmentinfo(Long infoId) {
        return infoMapper.deleteBudgetAdjustmentInfoById(infoId);
    }
}
