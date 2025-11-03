package net.cmkj.fixed.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TableWagesVersion;

/**
 * 预算填报订制【工资福利】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TableWagesVersionMapper extends BaseMapper<TableWagesVersion>
{
    /**
     * 查询预算填报订制【工资福利】版本数据
     *
     * @param id 预算填报订制【工资福利】版本数据主键
     * @return 预算填报订制【工资福利】版本数据
     */
    public TableWagesVersion selectTableWagesVersionById(Long id);

    /**
     * 查询预算填报订制【工资福利】版本数据列表
     *
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 预算填报订制【工资福利】版本数据集合
     */
    public List<TableWagesVersion> selectTableWagesVersionList(TableWagesVersion tableWagesVersion);

    /**
     * 新增预算填报订制【工资福利】版本数据
     *
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 结果
     */
    public int insertTableWagesVersion(TableWagesVersion tableWagesVersion);

    /**
     * 修改预算填报订制【工资福利】版本数据
     *
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 结果
     */
    public int updateTableWagesVersion(TableWagesVersion tableWagesVersion);

    /**
     * 删除预算填报订制【工资福利】版本数据
     *
     * @param id 预算填报订制【工资福利】版本数据主键
     * @return 结果
     */
    public int deleteTableWagesVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【工资福利】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableWagesVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    List<Map<String, Object>> selectSummaryList(TableWagesVersion wages);

    List<Map<String, Object>> budgetSummary2(TableWagesVersion wages);
}
