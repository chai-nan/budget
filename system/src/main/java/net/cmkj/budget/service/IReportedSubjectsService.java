package net.cmkj.budget.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.ReportedSubjects;

/**
 * 上报科目Service接口
 * 
 * @author cmkj
 * @date 2025-01-09
 */
public interface IReportedSubjectsService extends IService<ReportedSubjects>
{
    /**
     * 查询上报科目
     * 
     * @param id 上报科目主键
     * @return 上报科目
     */
    public ReportedSubjects selectReportedSubjectsById(Long id);

    /**
     * 查询上报科目列表
     * 
     * @param reportedSubjects 上报科目
     * @return 上报科目集合
     */
    public List<ReportedSubjects> selectReportedSubjectsList(ReportedSubjects reportedSubjects);

    /**
     * 新增上报科目
     * 
     * @param reportedSubjects 上报科目
     * @return 结果
     */
    public int insertReportedSubjects(ReportedSubjects reportedSubjects);

    /**
     * 修改上报科目
     * 
     * @param reportedSubjects 上报科目
     * @return 结果
     */
    public int updateReportedSubjects(ReportedSubjects reportedSubjects);

    /**
     * 批量删除上报科目
     * 
     * @param ids 需要删除的上报科目主键集合
     * @return 结果
     */
    public int deleteReportedSubjectsByIds(Long[] ids);

    /**
     * 删除上报科目信息
     * 
     * @param id 上报科目主键
     * @return 结果
     */
    public int deleteReportedSubjectsById(Long id);
}
