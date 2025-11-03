package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableMeter;

import java.util.List;

/**
 * 预算填报项目【计量器】Service接口
 * 
 * @author cmkj
 * @date 2024-08-28
 */
public interface ITableMeterService extends IService<TableMeter>
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
     * 批量删除预算填报项目【计量器】
     * 
     * @param ids 需要删除的预算填报项目【计量器】主键集合
     * @return 结果
     */
    public int deleteTableMeterByIds(Long[] ids);

    /**
     * 删除预算填报项目【计量器】信息
     * 
     * @param id 预算填报项目【计量器】主键
     * @return 结果
     */
    public int deleteTableMeterById(Long id);

    AjaxResult updateStatus(FixedVo vo);

    public List<TableMeter> budgetSummaryList(TableMeter meter);
}
