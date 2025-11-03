package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TableResearchVersion;

/**
 * 预算填报订制【科研计划申报】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TableResearchVersionMapper extends BaseMapper<TableResearchVersion>
{
    /**
     * 查询预算填报订制【科研计划申报】版本数据
     *
     * @param id 预算填报订制【科研计划申报】版本数据主键
     * @return 预算填报订制【科研计划申报】版本数据
     */
    public TableResearchVersion selectTableResearchVersionById(Long id);

    /**
     * 查询预算填报订制【科研计划申报】版本数据列表
     *
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 预算填报订制【科研计划申报】版本数据集合
     */
    public List<TableResearchVersion> selectTableResearchVersionList(TableResearchVersion tableResearchVersion);

    /**
     * 新增预算填报订制【科研计划申报】版本数据
     *
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 结果
     */
    public int insertTableResearchVersion(TableResearchVersion tableResearchVersion);

    /**
     * 修改预算填报订制【科研计划申报】版本数据
     *
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 结果
     */
    public int updateTableResearchVersion(TableResearchVersion tableResearchVersion);

    /**
     * 删除预算填报订制【科研计划申报】版本数据
     *
     * @param id 预算填报订制【科研计划申报】版本数据主键
     * @return 结果
     */
    public int deleteTableResearchVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【科研计划申报】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableResearchVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
