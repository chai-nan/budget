package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableMeter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算填报项目【计量器】Mapper接口
 *
 * @author cmkj
 * @date 2024-08-28
 */
public interface TableMeterMapper extends BaseMapper<TableMeter>
{
    /**
     * 查询预算填报项目【计量器】
     *
     * @param id 预算填报项目【计量器】主键
     * @return 预算填报项目【计量器】
     */
    public TableMeter selectTableMeterById(Long id);

    /**
     * 查询预算填报项目【计量器】列表
     *
     * @param tableMeter 预算填报项目【计量器】
     * @return 预算填报项目【计量器】集合
     */
    public List<TableMeter> selectTableMeterList(TableMeter tableMeter);

    public List<TableMeter> selectTableMeterCountList(TableMeter tableMeter);

    /**
     * 新增预算填报项目【计量器】
     *
     * @param tableMeter 预算填报项目【计量器】
     * @return 结果
     */
    public int insertTableMeter(TableMeter tableMeter);

    /**
     * 修改预算填报项目【计量器】
     *
     * @param tableMeter 预算填报项目【计量器】
     * @return 结果
     */
    public int updateTableMeter(TableMeter tableMeter);

    /**
     * 删除预算填报项目【计量器】
     *
     * @param id 预算填报项目【计量器】主键
     * @return 结果
     */
    public int deleteTableMeterById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报项目【计量器】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableMeterByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    public List<TableMeter> budgetSummaryList(TableMeter meter);

    List<TableMeter> budgetSummaryList2(TableMeter meter);

    List<TableMeter> budgetSummaryList3(TableMeter meter);
}
