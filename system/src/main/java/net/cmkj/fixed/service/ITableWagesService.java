package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.TableWages;

import java.util.List;
import java.util.Map;

/**
 * 预算填报项目表【工资福利】Service接口
 * 
 * @author cmkj
 * @date 2024-08-26
 */
public interface ITableWagesService extends IService<TableWages>
{
    /**
     * 查询预算填报项目【工资福利】
     * 
     * @param id 预算填报项目【工资福利】主键
     * @return 预算填报项目【工资福利】
     */
    public TableWages selectTableWagesById(Long id);

    /**
     * 查询预算填报项目【工资福利】列表
     * 
     * @param tableWages 预算填报项目【工资福利】
     * @return 预算填报项目【工资福利】集合
     */
    public List<TableWages> selectTableWagesList(TableWages tableWages);

    /**
     * 新增预算填报项目【工资福利】
     * 
     * @param tableWages 预算填报项目【工资福利】
     * @return 结果
     */
    public AjaxResult insertTableWages(TableWages tableWages);

    /**
     * 修改预算填报项目【工资福利】
     * 
     * @param tableWages 预算填报项目【工资福利】
     * @return 结果
     */
    public int updateTableWages(TableWages tableWages);

    /**
     * 批量删除预算填报项目【工资福利】
     * 
     * @param ids 需要删除的预算填报项目【工资福利】主键集合
     * @return 结果
     */
    public int deleteTableWagesByIds(Long[] ids);

    /**
     * 删除预算填报项目【工资福利】信息
     * 
     * @param id 预算填报项目【工资福利】主键
     * @return 结果
     */
    public int deleteTableWagesById(Long id);

    AjaxResult updateStatus(TableWages tableWages);

    AjaxResult backfill(TableWages tableWages);

    List<TableWages> parentList(Integer selectType,Long taskId);

    List<Map<String, Object>> selectSummaryList(TableWages wages);

    List<Map<String, Object>> budgetSummary1(TableWages wages);

    List<Map<String, Object>>  budgetSummary2(TableWages wages);

    Map<String, Object> budgetSummary3(TableWages wages);

    List<TableWages> selectExportList(TableWages tableWages);
}
