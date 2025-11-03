package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableHousing;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算填报项目【房屋建设、装修】Mapper接口
 *
 * @author cmkj
 * @date 2024-08-27
 */
public interface TableHousingMapper extends BaseMapper<TableHousing>
{
    /**
     * 查询预算填报项目【房屋建设、装修】
     *
     * @param id 预算填报项目【房屋建设、装修】主键
     * @return 预算填报项目【房屋建设、装修】
     */
    public TableHousing selectTableHousingById(Long id);

    /**
     * 查询预算填报项目【房屋建设、装修】列表
     *
     * @param tableHousing 预算填报项目【房屋建设、装修】
     * @return 预算填报项目【房屋建设、装修】集合
     */
    public List<TableHousing> selectTableHousingList(TableHousing tableHousing);

    public List<TableHousing> selectTableHousingCountList(TableHousing tableHousing);

    /**
     * 新增预算填报项目【房屋建设、装修】
     *
     * @param tableHousing 预算填报项目【房屋建设、装修】
     * @return 结果
     */
    public int insertTableHousing(TableHousing tableHousing);

    /**
     * 修改预算填报项目【房屋建设、装修】
     *
     * @param tableHousing 预算填报项目【房屋建设、装修】
     * @return 结果
     */
    public int updateTableHousing(TableHousing tableHousing);

    /**
     * 删除预算填报项目【房屋建设、装修】
     *
     * @param id 预算填报项目【房屋建设、装修】主键
     * @return 结果
     */
    public int deleteTableHousingById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报项目【房屋建设、装修】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableHousingByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    public List<TableHousing> budgetSummaryList(TableHousing housing);

    List<TableHousing> budgetSummaryList2(TableHousing housing);

    List<TableHousing> budgetSummaryList3(TableHousing housing);
}
