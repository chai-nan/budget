package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.cmkj.budget.domain.ReportingLog;
import net.cmkj.budget.service.IReportingLogService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysRole;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TablePipeline;
import net.cmkj.fixed.mapper.TablePipelineMapper;
import net.cmkj.fixed.service.ITablePipelineService;
import net.cmkj.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.List;
/**
 * 预算填报项目【天然气管线】Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-03
 */
@Service
@Slf4j
public class TablePipelineServiceImpl extends ServiceImpl<TablePipelineMapper, TablePipeline> implements ITablePipelineService
{

    @Autowired
    private IReportingLogService reportingLogService;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询预算填报项目【天然气管线】
     *
     * @param id 预算填报项目【天然气管线】主键
     * @return 预算填报项目【天然气管线】
     */
    @Override
    public TablePipeline selectTablePipelineById(Long id)
    {
        return baseMapper.selectTablePipelineById(id);
    }

    /**
     * 查询预算填报项目【天然气管线】列表
     *
     * @param tablePipeline 预算填报项目【天然气管线】
     * @return 预算填报项目【天然气管线】
     */
    @Override
    public List<TablePipeline> selectTablePipelineList(TablePipeline tablePipeline)
    {
        return baseMapper.selectTablePipelineList(tablePipeline);
    }

    @Override
    public List<TablePipeline> selectTablePipelineCountList(TablePipeline tablePipeline)
    {
        return baseMapper.selectTablePipelineCountList(tablePipeline);
    }

    /**
     * 新增预算填报项目【天然气管线】
     *
     * @param tablePipeline 预算填报项目【天然气管线】
     * @return 结果
     */
    @Override
    public int insertTablePipeline(TablePipeline tablePipeline)
    {
        tablePipeline.setCreateBy(SecurityUtils.getUsername());
        tablePipeline.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTablePipeline(tablePipeline);
    }

    /**
     * 修改预算填报项目【天然气管线】
     *
     * @param tablePipeline 预算填报项目【天然气管线】
     * @return 结果
     */
    @Override
    public int updateTablePipeline(TablePipeline tablePipeline)
    {
        tablePipeline.setUpdateBy(SecurityUtils.getUsername());
        tablePipeline.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateTablePipeline(tablePipeline);
    }

    /**
     * 批量删除预算填报项目【天然气管线】
     * 
     * @param ids 需要删除的预算填报项目【天然气管线】主键
     * @return 结果
     */
    @Override
    public int deleteTablePipelineByIds(Long[] ids)
    {
        return baseMapper.deleteTablePipelineByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报项目【天然气管线】信息
     * 
     * @param id 预算填报项目【天然气管线】主键
     * @return 结果
     */
    @Override
    public int deleteTablePipelineById(Long id)
    {
        return baseMapper.deleteTablePipelineById(id, SecurityUtils.getUsername());
    }

    @Override
    @Transactional
    public AjaxResult updateStatus(FixedVo vo) {
        try {
            for (String id : vo.getIds().split(",")) {
                TablePipeline query = baseMapper.selectTablePipelineById(Long.parseLong(id));
                Integer status = query.getStatus();
                if(query!=null){
                    query.setStatus(vo.getStatus());
                    query.setUpdateTime(DateUtils.getNowDate());
                    query.setUpdateBy(SecurityUtils.getUsername());
                    if(baseMapper.updateTablePipeline(query)>0){
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
                            }else{
                                ReportingLog queryLog = new ReportingLog();
                                queryLog.setBudgetId(id);
                                queryLog.setItemId(reportingLog.getItemId());
                                int size = reportingLogService.selectReportingLogList(queryLog).size();
                                if(size>0){
                                    reportingLog.setStatus(4);
                                    reportingLog.setRemark("重新提交预算申请");
                                }else{
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
    public List<TablePipeline> budgetSummaryList(TablePipeline pipeline) {
        if(pipeline.getSelectType()!=null && pipeline.getSelectType()==2) {
            return baseMapper.budgetSummaryList2(pipeline);
        }else if(pipeline.getSelectType()!=null && pipeline.getSelectType()==3){
            List<TablePipeline> tablePipelines = baseMapper.budgetSummaryList3(pipeline);
            // 通过id获取当前用户
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
                for (SysRole role : roles) {
                    if (role.getRoleKey().equals("financialManagement")) {
                        SysUser queryUser = userMapper.selectUserById(SecurityUtils.getUserId());
                        String companyIds = queryUser.getCompanyIds();
                        List<String> companyIdsList = Arrays.asList(companyIds.split(","));
                        tablePipelines = tablePipelines.stream()
                                .filter(dept -> companyIdsList.contains(String.valueOf(dept.getParentId()))).toList();
                    }
                }
            }
            return tablePipelines;
        }else{
            return baseMapper.budgetSummaryList(pipeline);
        }
    }
}
