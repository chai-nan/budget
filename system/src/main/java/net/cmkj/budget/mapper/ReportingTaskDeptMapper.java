package net.cmkj.budget.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.budget.domain.ReportingTaskDept;

/**
 * 部门任务填报情况Mapper接口
 *
 * @author cmkj
 * @date 2024-11-05
 */
@Mapper
public interface ReportingTaskDeptMapper extends BaseMapper<ReportingTaskDept>
{
    /**
     * 查询部门任务填报情况
     *
     * @param id 部门任务填报情况主键
     * @return 部门任务填报情况
     */
    public ReportingTaskDept selectReportingTaskDeptById(Long id);

    /**
     * 查询部门任务填报情况列表
     *
     * @param reportingTaskDept 部门任务填报情况
     * @return 部门任务填报情况集合
     */
    public List<ReportingTaskDept> selectReportingTaskDeptList(ReportingTaskDept reportingTaskDept);

    /**
     * 新增部门任务填报情况
     *
     * @param reportingTaskDept 部门任务填报情况
     * @return 结果
     */
    public int insertReportingTaskDept(ReportingTaskDept reportingTaskDept);

    /**
     * 修改部门任务填报情况
     *
     * @param reportingTaskDept 部门任务填报情况
     * @return 结果
     */
    public int updateReportingTaskDept(ReportingTaskDept reportingTaskDept);

    /**
     * 删除部门任务填报情况
     *
     * @param id 部门任务填报情况主键
     * @return 结果
     */
    public int deleteReportingTaskDeptById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除部门任务填报情况
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReportingTaskDeptByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
