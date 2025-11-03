package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableStation;

import java.util.List;

/**
 * 预算填报项目【场站】Service接口
 * 
 * @author cmkj
 * @date 2024-09-04
 */
public interface ITableStationService extends IService<TableStation>
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

    public List<TableStation> selectTableStationCountList(TableStation station);
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
     * 批量删除预算填报项目【场站】
     * 
     * @param ids 需要删除的预算填报项目【场站】主键集合
     * @return 结果
     */
    public int deleteTableStationByIds(Long[] ids);

    /**
     * 删除预算填报项目【场站】信息
     * 
     * @param id 预算填报项目【场站】主键
     * @return 结果
     */
    public int deleteTableStationById(Long id);

    AjaxResult updateStatus(FixedVo vo);

    public List<TableStation> budgetSummaryList(TableStation station);
}
