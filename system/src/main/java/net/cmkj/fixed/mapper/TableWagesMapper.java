package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.TableWages;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预算填报项目【工资福利】Mapper接口
 *
 * @author cmkj
 * @date 2024-08-26
 */
public interface TableWagesMapper extends BaseMapper<TableWages>
{
    /**
     * 查询预算填报项目【工资福利】
     *
     * @param id 预算填报项目【工资福利】主键
     * @return 预算填报项目【工资福利】
     */
    public TableWages selectTableWagesById(Long id);

    /**
     * 查询预算填报项目【工资福利】列表
     *
     * @param tableWages 预算填报项目【工资福利】
     * @return 预算填报项目【工资福利】集合
     */
    public List<TableWages> selectTableWagesList(TableWages tableWages);

    /**
     * 新增预算填报项目【工资福利】
     *
     * @param tableWages 预算填报项目【工资福利】
     * @return 结果
     */
    public int insertTableWages(TableWages tableWages);

    /**
     * 修改预算填报项目【工资福利】
     *
     * @param tableWages 预算填报项目【工资福利】
     * @return 结果
     */
    public int updateTableWages(TableWages tableWages);

    /**
     * 删除预算填报项目【工资福利】
     *
     * @param id 预算填报项目【工资福利】主键
     * @return 结果
     */
    public int deleteTableWagesById(Long id);

    /**
     * 批量删除预算填报项目【工资福利】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableWagesByIds(Long[] ids);

    /**
     * 唯一查询 任务、部门唯一
     * @return
     */
    public TableWages selectTableWagesByOne(@Param("taskId")Long taskId, @Param("deptId")Long deptId);

    List<Map<String, Object>> budgetSummary2(TableWages wages);

    TableWages  selectTableWagesByParentDept(Long parentId, Integer selectType,Long taskId);

    List<Map<String, Object>> selectSummaryList(TableWages wages);

    Map<String, Object> budgetSummary3(TableWages wages);

    List<Map<String, Object>> budgetSummary1(TableWages wages);

    List<TableWages> selectExportList(TableWages wages);
}
