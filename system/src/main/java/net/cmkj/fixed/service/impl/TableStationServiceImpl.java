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
import net.cmkj.fixed.domain.TableStation;
import net.cmkj.fixed.mapper.TableStationMapper;
import net.cmkj.fixed.service.ITableStationService;
import net.cmkj.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.List;
/**
 * 预算填报项目【场站】Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-04
 */
@Service
public class TableStationServiceImpl extends ServiceImpl<TableStationMapper, TableStation> implements ITableStationService
{

    @Autowired
    private IReportingLogService reportingLogService;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询预算填报项目【场站】
     *
     * @param id 预算填报项目【场站】主键
     * @return 预算填报项目【场站】
     */
    @Override
    public TableStation selectTableStationById(Long id)
    {
        return baseMapper.selectTableStationById(id);
    }

    /**
     * 查询预算填报项目【场站】列表
     *
     * @param tableStation 预算填报项目【场站】
     * @return 预算填报项目【场站】
     */
    @Override
    public List<TableStation> selectTableStationList(TableStation tableStation)
    {
        return baseMapper.selectTableStationList(tableStation);
    }

    @Override
    public List<TableStation> selectTableStationCountList(TableStation tableStation)
    {
        return baseMapper.selectTableStationCountList(tableStation);
    }

    /**
     * 新增预算填报项目【场站】
     *
     * @param tableStation 预算填报项目【场站】
     * @return 结果
     */
    @Override
    public int insertTableStation(TableStation tableStation)
    {
        tableStation.setCreateBy(SecurityUtils.getUsername());
        tableStation.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableStation(tableStation);
    }

    /**
     * 修改预算填报项目【场站】
     *
     * @param tableStation 预算填报项目【场站】
     * @return 结果
     */
    @Override
    public int updateTableStation(TableStation tableStation)
    {
        tableStation.setUpdateBy(SecurityUtils.getUsername());
        tableStation.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateTableStation(tableStation);
    }

    /**
     * 批量删除预算填报项目【场站】
     * 
     * @param ids 需要删除的预算填报项目【场站】主键
     * @return 结果
     */
    @Override
    public int deleteTableStationByIds(Long[] ids)
    {
        return baseMapper.deleteTableStationByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报项目【场站】信息
     * 
     * @param id 预算填报项目【场站】主键
     * @return 结果
     */
    @Override
    public int deleteTableStationById(Long id)
    {
        return baseMapper.deleteTableStationById(id, SecurityUtils.getUsername());
    }

    @Override
    @Transactional
    public AjaxResult updateStatus(FixedVo vo) {
        try {
            for (String id : vo.getIds().split(",")) {
                TableStation query = baseMapper.selectTableStationById(Long.parseLong(id));
                Integer status = query.getStatus();
                if(query!=null){
                    query.setStatus(vo.getStatus());
                    query.setUpdateTime(DateUtils.getNowDate());
                    query.setUpdateBy(SecurityUtils.getUsername());
                    if(baseMapper.updateTableStation(query)>0){
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
    public List<TableStation> budgetSummaryList(TableStation station) {
        if(station.getSelectType()!=null && station.getSelectType()==2) {
            return baseMapper.budgetSummaryList2(station);
        }else if(station.getSelectType()!=null && station.getSelectType()==3){
            List<TableStation> tableStations = baseMapper.budgetSummaryList3(station);
            // 通过id获取当前用户
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
                for (SysRole role : roles) {
                    if (role.getRoleKey().equals("financialManagement")) {
                        SysUser queryUser = userMapper.selectUserById(SecurityUtils.getUserId());
                        String companyIds = queryUser.getCompanyIds();
                        List<String> companyIdsList = Arrays.asList(companyIds.split(","));
                        tableStations = tableStations.stream()
                                .filter(dept -> companyIdsList.contains(String.valueOf(dept.getParentId()))).toList();
                    }
                }
            }
            return tableStations;
        }else{
            return baseMapper.budgetSummaryList(station);
        }
    }
}
