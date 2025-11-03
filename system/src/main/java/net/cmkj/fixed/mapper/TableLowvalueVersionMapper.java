package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TableLowvalueVersion;

/**
 * 预算填报订制【低值易耗品】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TableLowvalueVersionMapper extends BaseMapper<TableLowvalueVersion>
{
    /**
     * 查询预算填报订制【低值易耗品】版本数据
     *
     * @param id 预算填报订制【低值易耗品】版本数据主键
     * @return 预算填报订制【低值易耗品】版本数据
     */
    public TableLowvalueVersion selectTableLowvalueVersionById(Long id);

    /**
     * 查询预算填报订制【低值易耗品】版本数据列表
     *
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 预算填报订制【低值易耗品】版本数据集合
     */
    public List<TableLowvalueVersion> selectTableLowvalueVersionList(TableLowvalueVersion tableLowvalueVersion);

    /**
     * 新增预算填报订制【低值易耗品】版本数据
     *
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 结果
     */
    public int insertTableLowvalueVersion(TableLowvalueVersion tableLowvalueVersion);

    /**
     * 修改预算填报订制【低值易耗品】版本数据
     *
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 结果
     */
    public int updateTableLowvalueVersion(TableLowvalueVersion tableLowvalueVersion);

    /**
     * 删除预算填报订制【低值易耗品】版本数据
     *
     * @param id 预算填报订制【低值易耗品】版本数据主键
     * @return 结果
     */
    public int deleteTableLowvalueVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【低值易耗品】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableLowvalueVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
