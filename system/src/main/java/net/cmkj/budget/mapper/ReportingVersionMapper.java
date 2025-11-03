package net.cmkj.budget.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.budget.domain.ReportingVersion;

import java.util.List;

/**
 * 版本控制Mapper接口
 *
 * @author cmkj
 * @date 2024-07-17
 */
public interface ReportingVersionMapper extends BaseMapper<ReportingVersion>
{
    /**
     * 查询版本控制
     *
     * @param id 版本控制主键
     * @return 版本控制
     */
    public ReportingVersion selectReportingVersionById(Long id);

    /**
     * 查询版本控制列表
     *
     * @param reportingVersion 版本控制
     * @return 版本控制集合
     */
    public List<ReportingVersion> selectReportingVersionList(ReportingVersion reportingVersion);

    /**
     * 新增版本控制
     *
     * @param reportingVersion 版本控制
     * @return 结果
     */
    public int insertReportingVersion(ReportingVersion reportingVersion);

    /**
     * 修改版本控制
     *
     * @param reportingVersion 版本控制
     * @return 结果
     */
    public int updateReportingVersion(ReportingVersion reportingVersion);

    /**
     * 删除版本控制
     *
     * @param id 版本控制主键
     * @return 结果
     */
    public int deleteReportingVersionById(Long id);

    /**
     * 批量删除版本控制
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReportingVersionByIds(Long[] ids);

    List<ReportingVersion> lastList(ReportingVersion reportingVersion);

    ReportingVersion selectLastVersion();
}
