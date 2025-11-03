package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TableStationVersion;

/**
 * 预算填报订制【场站】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TableStationVersionMapper extends BaseMapper<TableStationVersion>
{
    /**
     * 查询预算填报订制【场站】版本数据
     *
     * @param id 预算填报订制【场站】版本数据主键
     * @return 预算填报订制【场站】版本数据
     */
    public TableStationVersion selectTableStationVersionById(Long id);

    /**
     * 查询预算填报订制【场站】版本数据列表
     *
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 预算填报订制【场站】版本数据集合
     */
    public List<TableStationVersion> selectTableStationVersionList(TableStationVersion tableStationVersion);

    /**
     * 新增预算填报订制【场站】版本数据
     *
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 结果
     */
    public int insertTableStationVersion(TableStationVersion tableStationVersion);

    /**
     * 修改预算填报订制【场站】版本数据
     *
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 结果
     */
    public int updateTableStationVersion(TableStationVersion tableStationVersion);

    /**
     * 删除预算填报订制【场站】版本数据
     *
     * @param id 预算填报订制【场站】版本数据主键
     * @return 结果
     */
    public int deleteTableStationVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【场站】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableStationVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
