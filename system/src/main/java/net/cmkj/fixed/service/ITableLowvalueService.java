package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableLowvalue;

import java.util.List;

/**
 * 预算填报项目【低值易耗品】Service接口
 * 
 * @author cmkj
 * @date 2024-08-28
 */
public interface ITableLowvalueService extends IService<TableLowvalue>
{
    /**
     * 查询预算填报项目【低值易耗品】
     * 
     * @param id 预算填报项目【低值易耗品】主键
     * @return 预算填报项目【低值易耗品】
     */
    public TableLowvalue selectTableLowvalueById(Long id);

    /**
     * 查询预算填报项目【低值易耗品】列表
     * 
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 预算填报项目【低值易耗品】集合
     */
    public List<TableLowvalue> selectTableLowvalueList(TableLowvalue tableLowvalue);

    public List<TableLowvalue> selectTableLowvalueCountList(TableLowvalue tableLowvalue);

    /**
     * 新增预算填报项目【低值易耗品】
     * 
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 结果
     */
    public int insertTableLowvalue(TableLowvalue tableLowvalue);

    /**
     * 修改预算填报项目【低值易耗品】
     * 
     * @param tableLowvalue 预算填报项目【低值易耗品】
     * @return 结果
     */
    public int updateTableLowvalue(TableLowvalue tableLowvalue);

    /**
     * 批量删除预算填报项目【低值易耗品】
     * 
     * @param ids 需要删除的预算填报项目【低值易耗品】主键集合
     * @return 结果
     */
    public int deleteTableLowvalueByIds(Long[] ids);

    /**
     * 删除预算填报项目【低值易耗品】信息
     * 
     * @param id 预算填报项目【低值易耗品】主键
     * @return 结果
     */
    public int deleteTableLowvalueById(Long id);

    AjaxResult updateStatus(FixedVo vo);

    public List<TableLowvalue> budgetSummaryList(TableLowvalue lowvalue);
}
