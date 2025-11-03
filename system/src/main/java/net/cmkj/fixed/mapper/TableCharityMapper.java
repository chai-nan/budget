package net.cmkj.fixed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableCharity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预算填报项目【慈善公益】Mapper接口
 *
 * @author cmkj
 * @date 2024-08-28
 */
public interface TableCharityMapper extends BaseMapper<TableCharity>
{
    /**
     * 查询预算填报项目【慈善公益】
     *
     * @param id 预算填报项目【慈善公益】主键
     * @return 预算填报项目【慈善公益】
     */
    public TableCharity selectTableCharityById(Long id);

    /**
     * 查询预算填报项目【慈善公益】列表
     *
     * @param tableCharity 预算填报项目【慈善公益】
     * @return 预算填报项目【慈善公益】集合
     */
    public List<TableCharity> selectTableCharityList(TableCharity tableCharity);

    public List<TableCharity> selectTableCharityCountList(TableCharity tableCharity);

    /**
     * 新增预算填报项目【慈善公益】
     *
     * @param tableCharity 预算填报项目【慈善公益】
     * @return 结果
     */
    public int insertTableCharity(TableCharity tableCharity);

    /**
     * 修改预算填报项目【慈善公益】
     *
     * @param tableCharity 预算填报项目【慈善公益】
     * @return 结果
     */
    public int updateTableCharity(TableCharity tableCharity);

    /**
     * 删除预算填报项目【慈善公益】
     *
     * @param id 预算填报项目【慈善公益】主键
     * @return 结果
     */
    public int deleteTableCharityById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预算填报项目【慈善公益】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableCharityByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    public List<TableCharity> budgetSummaryList(TableCharity charity);

    List<TableCharity> budgetSummaryList2(TableCharity charity);

    List<TableCharity> budgetSummaryList3(TableCharity charity);
}
