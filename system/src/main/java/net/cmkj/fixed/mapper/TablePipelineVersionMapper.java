package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TablePipelineVersion;

/**
 * 预算填报订制【天然气管线】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TablePipelineVersionMapper extends BaseMapper<TablePipelineVersion>
{
    /**
     * 查询预算填报订制【天然气管线】版本数据
     *
     * @param id 预算填报订制【天然气管线】版本数据主键
     * @return 预算填报订制【天然气管线】版本数据
     */
    public TablePipelineVersion selectTablePipelineVersionById(Long id);

    /**
     * 查询预算填报订制【天然气管线】版本数据列表
     *
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 预算填报订制【天然气管线】版本数据集合
     */
    public List<TablePipelineVersion> selectTablePipelineVersionList(TablePipelineVersion tablePipelineVersion);

    /**
     * 新增预算填报订制【天然气管线】版本数据
     *
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 结果
     */
    public int insertTablePipelineVersion(TablePipelineVersion tablePipelineVersion);

    /**
     * 修改预算填报订制【天然气管线】版本数据
     *
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 结果
     */
    public int updateTablePipelineVersion(TablePipelineVersion tablePipelineVersion);

    /**
     * 删除预算填报订制【天然气管线】版本数据
     *
     * @param id 预算填报订制【天然气管线】版本数据主键
     * @return 结果
     */
    public int deleteTablePipelineVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【天然气管线】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTablePipelineVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
