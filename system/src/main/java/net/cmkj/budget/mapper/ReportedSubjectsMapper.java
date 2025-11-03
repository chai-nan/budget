package net.cmkj.budget.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.budget.domain.ReportedSubjects;

/**
 * 上报科目Mapper接口
 *
 * @author cmkj
 * @date 2025-01-09
 */
public interface ReportedSubjectsMapper extends BaseMapper<ReportedSubjects>
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
     * 删除上报科目
     *
     * @param id 上报科目主键
     * @return 结果
     */
    public int deleteReportedSubjectsById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除上报科目
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReportedSubjectsByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
