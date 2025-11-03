package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TableHousingVersion;

/**
 * 预算填报订制【房屋建设、装修】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TableHousingVersionMapper extends BaseMapper<TableHousingVersion>
{
    /**
     * 查询预算填报订制【房屋建设、装修】版本数据
     *
     * @param id 预算填报订制【房屋建设、装修】版本数据主键
     * @return 预算填报订制【房屋建设、装修】版本数据
     */
    public TableHousingVersion selectTableHousingVersionById(Long id);

    /**
     * 查询预算填报订制【房屋建设、装修】版本数据列表
     *
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 预算填报订制【房屋建设、装修】版本数据集合
     */
    public List<TableHousingVersion> selectTableHousingVersionList(TableHousingVersion tableHousingVersion);

    /**
     * 新增预算填报订制【房屋建设、装修】版本数据
     *
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 结果
     */
    public int insertTableHousingVersion(TableHousingVersion tableHousingVersion);

    /**
     * 修改预算填报订制【房屋建设、装修】版本数据
     *
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 结果
     */
    public int updateTableHousingVersion(TableHousingVersion tableHousingVersion);

    /**
     * 删除预算填报订制【房屋建设、装修】版本数据
     *
     * @param id 预算填报订制【房屋建设、装修】版本数据主键
     * @return 结果
     */
    public int deleteTableHousingVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【房屋建设、装修】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableHousingVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
