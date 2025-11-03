package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.budget.mapper.ReportedSubjectsMapper;
import net.cmkj.budget.domain.ReportedSubjects;
import net.cmkj.budget.service.IReportedSubjectsService;

import java.util.List;
/**
 * 上报科目Service业务层处理
 *
 * @author cmkj
 * @date 2025-01-09
 */
@Service
public class ReportedSubjectsServiceImpl extends ServiceImpl<ReportedSubjectsMapper, ReportedSubjects> implements IReportedSubjectsService
{

    /**
     * 查询上报科目
     *
     * @param id 上报科目主键
     * @return 上报科目
     */
    @Override
    public ReportedSubjects selectReportedSubjectsById(Long id)
    {
        return baseMapper.selectReportedSubjectsById(id);
    }

    /**
     * 查询上报科目列表
     *
     * @param reportedSubjects 上报科目
     * @return 上报科目
     */
    @Override
    public List<ReportedSubjects> selectReportedSubjectsList(ReportedSubjects reportedSubjects)
    {
        return baseMapper.selectReportedSubjectsList(reportedSubjects);
    }

    /**
     * 新增上报科目
     *
     * @param reportedSubjects 上报科目
     * @return 结果
     */
    @Override
    public int insertReportedSubjects(ReportedSubjects reportedSubjects)
    {
        reportedSubjects.setCreateTime(DateUtils.getNowDate());
        reportedSubjects.setCreateBy(SecurityUtils.getUsername());
        return baseMapper.insertReportedSubjects(reportedSubjects);
    }

    /**
     * 修改上报科目
     *
     * @param reportedSubjects 上报科目
     * @return 结果
     */
    @Override
    public int updateReportedSubjects(ReportedSubjects reportedSubjects)
    {
        reportedSubjects.setUpdateBy(SecurityUtils.getUsername());
        reportedSubjects.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateReportedSubjects(reportedSubjects);
    }

    /**
     * 批量删除上报科目
     * 
     * @param ids 需要删除的上报科目主键
     * @return 结果
     */
    @Override
    public int deleteReportedSubjectsByIds(Long[] ids)
    {
        return baseMapper.deleteReportedSubjectsByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除上报科目信息
     * 
     * @param id 上报科目主键
     * @return 结果
     */
    @Override
    public int deleteReportedSubjectsById(Long id)
    {
        return baseMapper.deleteReportedSubjectsById(id, SecurityUtils.getUsername());
    }
}
