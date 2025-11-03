package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.TableInformationSystemVersion;

/**
 * 预算填报订制【信息系统】版本数据Mapper接口
 *
 * @author cmkj
 * @date 2024-09-26
 */
public interface TableInformationSystemVersionMapper extends BaseMapper<TableInformationSystemVersion>
{
    /**
     * 查询预算填报订制【信息系统】版本数据
     *
     * @param id 预算填报订制【信息系统】版本数据主键
     * @return 预算填报订制【信息系统】版本数据
     */
    public TableInformationSystemVersion selectTableInformationSystemVersionById(Long id);

    /**
     * 查询预算填报订制【信息系统】版本数据列表
     *
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 预算填报订制【信息系统】版本数据集合
     */
    public List<TableInformationSystemVersion> selectTableInformationSystemVersionList(TableInformationSystemVersion tableInformationSystemVersion);

    /**
     * 新增预算填报订制【信息系统】版本数据
     *
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 结果
     */
    public int insertTableInformationSystemVersion(TableInformationSystemVersion tableInformationSystemVersion);

    /**
     * 修改预算填报订制【信息系统】版本数据
     *
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 结果
     */
    public int updateTableInformationSystemVersion(TableInformationSystemVersion tableInformationSystemVersion);

    /**
     * 删除预算填报订制【信息系统】版本数据
     *
     * @param id 预算填报订制【信息系统】版本数据主键
     * @return 结果
     */
    public int deleteTableInformationSystemVersionById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报订制【信息系统】版本数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableInformationSystemVersionByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
