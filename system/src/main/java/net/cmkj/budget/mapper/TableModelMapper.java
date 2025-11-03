package net.cmkj.budget.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.cmkj.budget.domain.TableModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 填报模块Mapper接口
 *
 * @author cmkj
 * @date 2024-07-15
 */
public interface TableModelMapper extends BaseMapper<TableModel>
{
    /**
     * 查询填报模块
     *
     * @param id 填报模块主键
     * @return 填报模块
     */
    public TableModel selectTableModelById(Long id);

    /**
     * 查询填报模块列表
     *
     * @param tableModel 填报模块
     * @return 填报模块集合
     */
    public List<TableModel> selectTableModelList(TableModel tableModel);

    /**
     * 新增填报模块
     *
     * @param tableModel 填报模块
     * @return 结果
     */
    public int insertTableModel(TableModel tableModel);

    /**
     * 修改填报模块
     *
     * @param tableModel 填报模块
     * @return 结果
     */
    public int updateTableModel(TableModel tableModel);

    /**
     * 批量删除填报模块
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableModelByIds(@Param("ids")Long[] ids, @Param("userName")String userName);

    public int deleteTableModelById(@Param("id")Long id, @Param("userName")String userName);

    /**查询当前字段数*/
    int selectIndex(String tableName);

    @Update("${sqlStr}")
    public int dynamicSql (@Param("sqlStr") String sql);

    List<TableModel> selectTableModelListByItem(Long itemId);
}
