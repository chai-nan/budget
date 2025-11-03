package net.cmkj.budget.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.ReportingLog;

import java.util.List;

/**
 * 填报记录Service接口
 * 
 * @author cmkj
 * @date 2024-07-22
 */
public interface IReportingLogService extends IService<ReportingLog>
{
    /**
     * 查询填报记录
     * 
     * @param id 填报记录主键
     * @return 填报记录
     */
    public ReportingLog selectReportingLogById(Long id);

    /**
     * 查询填报记录列表
     * 
     * @param reportingLog 填报记录
     * @return 填报记录集合
     */
    public List<ReportingLog> selectReportingLogList(ReportingLog reportingLog);

    /**
     * 新增填报记录
     * 
     * @param reportingLog 填报记录
     * @return 结果
     */
    public int insertReportingLog(ReportingLog reportingLog);

    /**
     * 修改填报记录
     * 
     * @param reportingLog 填报记录
     * @return 结果
     */
    public int updateReportingLog(ReportingLog reportingLog);

    /**
     * 批量删除填报记录
     * 
     * @param ids 需要删除的填报记录主键集合
     * @return 结果
     */
    public int deleteReportingLogByIds(Long[] ids);

    /**
     * 删除填报记录信息
     * 
     * @param id 填报记录主键
     * @return 结果
     */
    public int deleteReportingLogById(Long id);
}
