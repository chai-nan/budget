package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.ReportingVersion;
import net.cmkj.budget.mapper.ReportingVersionMapper;
import net.cmkj.budget.service.IReportingVersionService;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 版本控制Service业务层处理
 * 
 * @author cmkj
 * @date 2024-07-17
 */
@Service
public class ReportingVersionServiceImpl extends ServiceImpl<ReportingVersionMapper, ReportingVersion> implements IReportingVersionService
{

    /**
     * 查询版本控制
     * 
     * @param id 版本控制主键
     * @return 版本控制
     */
    @Override
    public ReportingVersion selectReportingVersionById(Long id)
    {
        return baseMapper.selectReportingVersionById(id);
    }

    /**
     * 查询版本控制列表
     * 
     * @param reportingVersion 版本控制
     * @return 版本控制
     */
    @Override
    public List<ReportingVersion> selectReportingVersionList(ReportingVersion reportingVersion)
    {
        return baseMapper.selectReportingVersionList(reportingVersion);
    }

    /**
     * 新增版本控制
     * 
     * @param reportingVersion 版本控制
     * @return 结果
     */
    @Override
    public int insertReportingVersion(ReportingVersion reportingVersion)
    {
        reportingVersion.setCreateBy(SecurityUtils.getUsername());
        reportingVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insert(reportingVersion);
    }

    /**
     * 修改版本控制
     * 
     * @param reportingVersion 版本控制
     * @return 结果
     */
    @Override
    public int updateReportingVersion(ReportingVersion reportingVersion)
    {
        return baseMapper.updateById(reportingVersion);
    }

    /**
     * 批量删除版本控制
     * 
     * @param ids 需要删除的版本控制主键
     * @return 结果
     */
    @Override
    public int deleteReportingVersionByIds(Long[] ids)
    {
        return baseMapper.deleteByIds(Arrays.asList(ids));
    }

    /**
     * 删除版本控制信息
     * 
     * @param id 版本控制主键
     * @return 结果
     */
    @Override
    public int deleteReportingVersionById(Long id)
    {
        return baseMapper.deleteById(id);
    }

    @Override
    public List<ReportingVersion> lastList(ReportingVersion reportingVersion) {
        return baseMapper.lastList(reportingVersion);
    }

    @Override
    public ReportingVersion selectLastVersion() {
        return baseMapper.selectLastVersion();
    }
}
