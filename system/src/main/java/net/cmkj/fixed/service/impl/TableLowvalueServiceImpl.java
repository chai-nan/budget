package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.ReportingLog;
import net.cmkj.budget.service.IReportingLogService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysRole;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableLowvalue;
import net.cmkj.fixed.mapper.TableLowvalueMapper;
import net.cmkj.fixed.service.ITableLowvalueService;
import net.cmkj.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.List;
/**
 * 预算填报项目【低值易耗品】Service业务层处理
 *
 * @author cmkj
 * @date 2024-08-28
 */
@Service
public class TableLowvalueServiceImpl extends ServiceImpl<TableLowvalueMapper, TableLowvalue> implements ITableLowvalueService
{

    @Autowired
    private IReportingLogService reportingLogService;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询预算填报项目【低值易耗品】
     *
     * @param id 预算填报项目【低值易耗品】主键
     * @return 预算填报项目【低值易耗品】
     */
    @Override
    public TableLowvalue selectTableLowvalueById(Long id)
    {
        return baseMapper.selectTableLowvalueById(id);
    }

    /**
     * 查询预算填报项目【低值易耗品】列表
     *
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 预算填报项目【低值易耗品】
     */
    @Override
    public List<TableLowvalue> selectTableLowvalueList(TableLowvalue tableLowvalue)
    {
        return baseMapper.selectTableLowvalueList(tableLowvalue);
    }

    @Override
    public List<TableLowvalue> selectTableLowvalueCountList(TableLowvalue tableLowvalue)
    {
        return baseMapper.selectTableLowvalueCountList(tableLowvalue);
    }

    /**
     * 新增预算填报项目【低值易耗品】
     *
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 结果
     */
    @Override
    public int insertTableLowvalue(TableLowvalue tableLowvalue)
    {
        tableLowvalue.setCreateBy(SecurityUtils.getUsername());
        tableLowvalue.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableLowvalue(tableLowvalue);
    }

    /**
     * 修改预算填报项目【低值易耗品】
     *
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 结果
     */
    @Override
    public int updateTableLowvalue(TableLowvalue tableLowvalue)
    {
        tableLowvalue.setUpdateBy(SecurityUtils.getUsername());
        tableLowvalue.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateTableLowvalue(tableLowvalue);
    }

    /**
     * 批量删除预算填报项目【低值易耗品】
     * 
     * @param ids 需要删除的预算填报项目【低值易耗品】主键
     * @return 结果
     */
    @Override
    public int deleteTableLowvalueByIds(Long[] ids)
    {
        return baseMapper.deleteTableLowvalueByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报项目【低值易耗品】信息
     * 
     * @param id 预算填报项目【低值易耗品】主键
     * @return 结果
     */
    @Override
    public int deleteTableLowvalueById(Long id)
    {
        return baseMapper.deleteTableLowvalueById(id, SecurityUtils.getUsername());
    }

    @Override
    @Transactional
    public AjaxResult updateStatus(FixedVo vo) {
        try {
            for (String id : vo.getIds().split(",")) {
                TableLowvalue query = baseMapper.selectTableLowvalueById(Long.parseLong(id));
                Integer status = query.getStatus();
                if(query!=null){
                    query.setStatus(vo.getStatus());
                    query.setUpdateTime(DateUtils.getNowDate());
                    query.setUpdateBy(SecurityUtils.getUsername());
                    if(baseMapper.updateTableLowvalue(query)>0){
                        ReportingLog reportingLog = new ReportingLog();
                        reportingLog.setItemId(vo.getItemId());
                        reportingLog.setBudgetId(id);
                        reportingLog.setUserId(SecurityUtils.getUserId());
                        reportingLog.setDeptId(SecurityUtils.getDeptId());
                        reportingLog.setRemark(vo.getRemark());
                        reportingLog.setTime(DateUtils.getNowDate());
                        if(vo.getStatus()==2){
                            if(status!=null && status==3){
                                reportingLog.setStatus(5);//提交撤回
                                reportingLog.setRemark("撤回审批通过操作");
                            }else {
                                ReportingLog queryLog = new ReportingLog();
                                queryLog.setBudgetId(id);
                                queryLog.setItemId(reportingLog.getItemId());
                                int size = reportingLogService.selectReportingLogList(queryLog).size();
                                if (size > 0) {
                                    reportingLog.setStatus(4);
                                    reportingLog.setRemark("重新提交预算申请");
                                } else {
                                    reportingLog.setStatus(1);
                                    reportingLog.setRemark("提交预算申请");
                                }
                            }
                        }else if(vo.getStatus()==3 || vo.getStatus()==5){
                            reportingLog.setStatus(2);
                        }else if(vo.getStatus()==4 || vo.getStatus()==6){
                            reportingLog.setStatus(3);
                        }else if(vo.getStatus()==1){
                            reportingLog.setStatus(5);//提交撤回
                            reportingLog.setRemark("撤回提交审批申请");
                        }
                        if(reportingLogService.insertReportingLog(reportingLog)<=0){
                            throw new RuntimeException();
                        }
                    }else{
                        throw new RuntimeException();
                    }
                }
            }
            return AjaxResult.success();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("操作失败");
        }
    }

    @Override
    public List<TableLowvalue> budgetSummaryList(TableLowvalue lowvalue) {
        if(lowvalue.getSelectType()!=null && lowvalue.getSelectType()==2) {
            return baseMapper.budgetSummaryList2(lowvalue);
        }else if(lowvalue.getSelectType()!=null && lowvalue.getSelectType()==3){
            List<TableLowvalue> tableLowvalues = baseMapper.budgetSummaryList3(lowvalue);
            // 通过id获取当前用户
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
                for (SysRole role : roles) {
                    if (role.getRoleKey().equals("financialManagement")) {
                        SysUser queryUser = userMapper.selectUserById(SecurityUtils.getUserId());
                        String companyIds = queryUser.getCompanyIds();
                        List<String> companyIdsList = Arrays.asList(companyIds.split(","));
                        tableLowvalues = tableLowvalues.stream()
                                .filter(dept -> companyIdsList.contains(String.valueOf(dept.getParentId()))).toList();
                    }
                }
            }
            return tableLowvalues;
        }else{
            return baseMapper.budgetSummaryList(lowvalue);
        }

    }
}
