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
import net.cmkj.fixed.domain.TableHousing;
import net.cmkj.fixed.mapper.TableHousingMapper;
import net.cmkj.fixed.service.ITableHousingService;
import net.cmkj.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.List;

/**
 * 预算填报项目【房屋建设、装修】Service业务层处理
 * 
 * @author cmkj
 * @date 2024-08-27
 */
@Service
public class TableHousingServiceImpl extends ServiceImpl<TableHousingMapper, TableHousing> implements ITableHousingService
{
    @Autowired
    private IReportingLogService reportingLogService;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询预算填报项目【房屋建设、装修】
     * 
     * @param id 预算填报项目【房屋建设、装修】主键
     * @return 预算填报项目【房屋建设、装修】
     */
    @Override
    public TableHousing selectTableHousingById(Long id)
    {
        return baseMapper.selectById(id);
    }

    /**
     * 查询预算填报项目【房屋建设、装修】列表
     * 
     * @param tableHousing 预算填报项目【房屋建设、装修】
     * @return 预算填报项目【房屋建设、装修】
     */
    @Override
    public List<TableHousing> selectTableHousingList(TableHousing tableHousing)
    {
        return baseMapper.selectTableHousingList(tableHousing);
    }

    @Override
    public List<TableHousing> selectTableHousingCountList(TableHousing tableHousing)
    {
        return baseMapper.selectTableHousingCountList(tableHousing);
    }

    /**
     * 新增预算填报项目【房屋建设、装修】
     * 
     * @param tableHousing 预算填报项目【房屋建设、装修】
     * @return 结果
     */
    @Override
    public int insertTableHousing(TableHousing tableHousing)
    {
        tableHousing.setCreateTime(DateUtils.getNowDate());
        tableHousing.setCreateBy(SecurityUtils.getUsername());
        return baseMapper.insertTableHousing(tableHousing);
    }

    /**
     * 修改预算填报项目【房屋建设、装修】
     * 
     * @param tableHousing 预算填报项目【房屋建设、装修】
     * @return 结果
     */
    @Override
    public int updateTableHousing(TableHousing tableHousing)
    {
        tableHousing.setUpdateTime(DateUtils.getNowDate());
        tableHousing.setUpdateBy(SecurityUtils.getUsername());
        return baseMapper.updateTableHousing(tableHousing);
    }

    /**
     * 批量删除预算填报项目【房屋建设、装修】
     * 
     * @param ids 需要删除的预算填报项目【房屋建设、装修】主键
     * @return 结果
     */
    @Override
    public int deleteTableHousingByIds(Long[] ids)
    {
        return baseMapper.deleteTableHousingByIds(ids,SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报项目【房屋建设、装修】信息
     * 
     * @param id 预算填报项目【房屋建设、装修】主键
     * @return 结果
     */
    @Override
    public int deleteTableHousingById(Long id)
    {
        return baseMapper.deleteTableHousingById(id,SecurityUtils.getUsername());
    }

    @Override
    @Transactional
    public AjaxResult updateStatus(FixedVo vo) {
        try {
            for (String id : vo.getIds().split(",")) {
                TableHousing query = baseMapper.selectTableHousingById(Long.parseLong(id));
                Integer status = query.getStatus();
                if(query!=null){
                    query.setStatus(vo.getStatus());
                    query.setUpdateTime(DateUtils.getNowDate());
                    query.setUpdateBy(SecurityUtils.getUsername());
                    if(baseMapper.updateTableHousing(query)>0){
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
    public List<TableHousing> budgetSummaryList(TableHousing housing) {
        if(housing.getSelectType()!=null && housing.getSelectType()==2) {
            return baseMapper.budgetSummaryList2(housing);
        }else if(housing.getSelectType()!=null && housing.getSelectType()==3){
            List<TableHousing> tableHousings = baseMapper.budgetSummaryList3(housing);
            // 通过id获取当前用户
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
                for (SysRole role : roles) {
                    if (role.getRoleKey().equals("financialManagement")) {
                        SysUser queryUser = userMapper.selectUserById(SecurityUtils.getUserId());
                        String companyIds = queryUser.getCompanyIds();
                        List<String> companyIdsList = Arrays.asList(companyIds.split(","));
                        tableHousings = tableHousings.stream()
                                .filter(dept -> companyIdsList.contains(String.valueOf(dept.getParentId()))).toList();
                    }
                }
            }
            return tableHousings;
        }else{
            return baseMapper.budgetSummaryList(housing);
        }
    }
}
