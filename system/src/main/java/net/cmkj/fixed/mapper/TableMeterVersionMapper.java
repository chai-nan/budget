package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TableMeterVersion;

/**
 * 预算填报订制【计量器】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TableMeterVersionMapper extends BaseMapper<TableMeterVersion>
{
    /**
     * 查询预算填报订制【计量器】版本数据
     *
     * @param id 预算填报订制【计量器】版本数据主键
     * @return 预算填报订制【计量器】版本数据
     */
    public TableMeterVersion selectTableMeterVersionById(Long id);

    /**
     * 查询预算填报订制【计量器】版本数据列表
     *
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 预算填报订制【计量器】版本数据集合
     */
    public List<TableMeterVersion> selectTableMeterVersionList(TableMeterVersion tableMeterVersion);

    /**
     * 新增预算填报订制【计量器】版本数据
     *
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 结果
     */
    public int insertTableMeterVersion(TableMeterVersion tableMeterVersion);

    /**
     * 修改预算填报订制【计量器】版本数据
     *
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 结果
     */
    public int updateTableMeterVersion(TableMeterVersion tableMeterVersion);

    /**
     * 删除预算填报订制【计量器】版本数据
     *
     * @param id 预算填报订制【计量器】版本数据主键
     * @return 结果
     */
    public int deleteTableMeterVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【计量器】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableMeterVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
