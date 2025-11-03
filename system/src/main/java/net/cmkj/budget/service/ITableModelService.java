package net.cmkj.budget.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.TableModel;
import net.cmkj.common.core.domain.AjaxResult;

import java.util.List;

/**
 * 填报模块Service接口
 * 
 * @author cmkj
 * @date 2024-07-15
 */
public interface ITableModelService extends IService<TableModel>
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
    public AjaxResult insertTableModel(TableModel tableModel);

    /**
     * 修改填报模块
     * 
     * @param tableModel 填报模块
     * @return 结果
     */
    public AjaxResult updateTableModel(TableModel tableModel);

    /**
     * 批量删除填报模块
     * 
     * @param ids 需要删除的填报模块主键集合
     * @return 结果
     */
    public int deleteTableModelByIds(Long[] ids);

    public List<TableModel> selectTableModelListByItem(Long itemId);

    public int deleteTableModelById(Long id);
}
