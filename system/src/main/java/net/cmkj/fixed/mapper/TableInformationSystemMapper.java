package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.TableInformationSystem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算填报项目【信息系统】Mapper接口
 *
 * @author cmkj
 * @date 2024-09-04
 */
public interface TableInformationSystemMapper extends BaseMapper<TableInformationSystem>
{
    /**
     * 查询预算填报项目【信息系统】
     *
     * @param id 预算填报项目【信息系统】主键
     * @return 预算填报项目【信息系统】
     */
    public TableInformationSystem selectTableInformationSystemById(Long id);

    /**
     * 查询预算填报项目【信息系统】列表
     *
     * @param tableInformationSystem 预算填报项目【信息系统】
     * @return 预算填报项目【信息系统】集合
     */
    public List<TableInformationSystem> selectTableInformationSystemList(TableInformationSystem tableInformationSystem);

    public List<TableInformationSystem> selectTableInformationSystemCountList(TableInformationSystem tableInformationSystem);

    /**
     * 新增预算填报项目【信息系统】
     *
     * @param tableInformationSystem 预算填报项目【信息系统】
     * @return 结果
     */
    public int insertTableInformationSystem(TableInformationSystem tableInformationSystem);

    /**
     * 修改预算填报项目【信息系统】
     *
     * @param tableInformationSystem 预算填报项目【信息系统】
     * @return 结果
     */
    public int updateTableInformationSystem(TableInformationSystem tableInformationSystem);

    /**
     * 删除预算填报项目【信息系统】
     *
     * @param id 预算填报项目【信息系统】主键
     * @return 结果
     */
    public int deleteTableInformationSystemById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报项目【信息系统】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableInformationSystemByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    List<TableInformationSystem> budgetSummaryList(TableInformationSystem informationSystem);

    List<TableInformationSystem> budgetSummaryList2(TableInformationSystem informationSystem);

    List<TableInformationSystem> budgetSummaryList3(TableInformationSystem informationSystem);
}
