package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.TableStation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算填报项目【场站】Mapper接口
 *
 * @author cmkj
 * @date 2024-09-04
 */
public interface TableStationMapper extends BaseMapper<TableStation>
{
    /**
     * 查询预算填报项目【场站】
     *
     * @param id 预算填报项目【场站】主键
     * @return 预算填报项目【场站】
     */
    public TableStation selectTableStationById(Long id);

    /**
     * 查询预算填报项目【场站】列表
     *
     * @param tableStation 预算填报项目【场站】
     * @return 预算填报项目【场站】集合
     */
    public List<TableStation> selectTableStationList(TableStation tableStation);

    public List<TableStation> selectTableStationCountList(TableStation tableStation);

    /**
     * 新增预算填报项目【场站】
     *
     * @param tableStation 预算填报项目【场站】
     * @return 结果
     */
    public int insertTableStation(TableStation tableStation);

    /**
     * 修改预算填报项目【场站】
     *
     * @param tableStation 预算填报项目【场站】
     * @return 结果
     */
    public int updateTableStation(TableStation tableStation);

    /**
     * 删除预算填报项目【场站】
     *
     * @param id 预算填报项目【场站】主键
     * @return 结果
     */
    public int deleteTableStationById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报项目【场站】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableStationByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    List<TableStation> budgetSummaryList(TableStation station);

    List<TableStation> budgetSummaryList2(TableStation station);

    List<TableStation> budgetSummaryList3(TableStation station);
}
