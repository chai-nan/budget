package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.ReportingLog;
import net.cmkj.budget.mapper.ReportingLogMapper;
import net.cmkj.budget.service.IReportingLogService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 填报记录Service业务层处理
 * 
 * @author cmkj
 * @date 2024-07-22
 */
@Service
public class ReportingLogServiceImpl extends ServiceImpl<ReportingLogMapper, ReportingLog> implements IReportingLogService
{

    /**
     * 查询填报记录
     * 
     * @param id 填报记录主键
     * @return 填报记录
     */
    @Override
    public ReportingLog selectReportingLogById(Long id)
    {
        return baseMapper.selectById(id);
    }

    /**
     * 查询填报记录列表
     * 
     * @param reportingLog 填报记录
     * @return 填报记录
     */
    @Override
    public List<ReportingLog> selectReportingLogList(ReportingLog reportingLog)
    {
        return baseMapper.selectReportingLogList(reportingLog);
    }

    /**
     * 新增填报记录
     * 
     * @param reportingLog 填报记录
     * @return 结果
     */
    @Override
    public int insertReportingLog(ReportingLog reportingLog)
    {
        return baseMapper.insert(reportingLog);
    }

    /**
     * 修改填报记录
     * 
     * @param reportingLog 填报记录
     * @return 结果
     */
    @Override
    public int updateReportingLog(ReportingLog reportingLog)
    {
        return baseMapper.updateById(reportingLog);
    }

    /**
     * 批量删除填报记录
     * 
     * @param ids 需要删除的填报记录主键
     * @return 结果
     */
    @Override
    public int deleteReportingLogByIds(Long[] ids)
    {
        return baseMapper.deleteByIds(Arrays.asList(ids));
    }

    /**
     * 删除填报记录信息
     * 
     * @param id 填报记录主键
     * @return 结果
     */
    @Override
    public int deleteReportingLogById(Long id)
    {
        return baseMapper.deleteById(id);
    }
}
