package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TableCharityVersion;

/**
 * 预算填报订制【慈善公益】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TableCharityVersionMapper extends BaseMapper<TableCharityVersion>
{
    /**
     * 查询预算填报订制【慈善公益】版本数据
     *
     * @param id 预算填报订制【慈善公益】版本数据主键
     * @return 预算填报订制【慈善公益】版本数据
     */
    public TableCharityVersion selectTableCharityVersionById(Long id);

    /**
     * 查询预算填报订制【慈善公益】版本数据列表
     *
     * @param tableCharityVersion 预算填报订制【慈善公益】版本数据
     * @return 预算填报订制【慈善公益】版本数据集合
     */
    public List<TableCharityVersion> selectTableCharityVersionList(TableCharityVersion tableCharityVersion);

    /**
     * 新增预算填报订制【慈善公益】版本数据
     *
     * @param tableCharityVersion 预算填报订制【慈善公益】版本数据
     * @return 结果
     */
    public int insertTableCharityVersion(TableCharityVersion tableCharityVersion);

    /**
     * 修改预算填报订制【慈善公益】版本数据
     *
     * @param tableCharityVersion 预算填报订制【慈善公益】版本数据
     * @return 结果
     */
    public int updateTableCharityVersion(TableCharityVersion tableCharityVersion);

    /**
     * 删除预算填报订制【慈善公益】版本数据
     *
     * @param id 预算填报订制【慈善公益】版本数据主键
     * @return 结果
     */
    public int deleteTableCharityVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【慈善公益】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableCharityVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
