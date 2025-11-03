package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableHousing;

import java.util.List;

/**
 * 预算填报项目【房屋建设、装修】Service接口
 * 
 * @author cmkj
 * @date 2024-08-27
 */
public interface ITableHousingService extends IService<TableHousing>
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

    public List<TableHousing> selectTableHousingCountList(TableHousing housing);
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
     * 批量删除预算填报项目【房屋建设、装修】
     * 
     * @param ids 需要删除的预算填报项目【房屋建设、装修】主键集合
     * @return 结果
     */
    public int deleteTableHousingByIds(Long[] ids);

    /**
     * 删除预算填报项目【房屋建设、装修】信息
     * 
     * @param id 预算填报项目【房屋建设、装修】主键
     * @return 结果
     */
    public int deleteTableHousingById(Long id);

    public AjaxResult updateStatus(FixedVo vo);

    public List<TableHousing> budgetSummaryList(TableHousing housing);
}
