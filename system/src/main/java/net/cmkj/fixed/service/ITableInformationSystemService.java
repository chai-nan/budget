package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableInformationSystem;

import java.util.List;

/**
 * 预算填报项目【信息系统】Service接口
 * 
 * @author cmkj
 * @date 2024-09-04
 */
public interface ITableInformationSystemService extends IService<TableInformationSystem>
{
    /**
     * 查询预算填报项目【信息系统】
     * 
     * @param id 预算填报项目【信息系统】主键
     * @return 预算填报项目【信息系统】
     */
    public TableInformationSystem selectTableInformationSystemById(Long id);

    /**
     * 查询预算填报项目【信息系统】列表
     * 
     * @param tableInformationSystem 预算填报项目【信息系统】
     * @return 预算填报项目【信息系统】集合
     */
    public List<TableInformationSystem> selectTableInformationSystemList(TableInformationSystem tableInformationSystem);

    public List<TableInformationSystem> selectTableInformationSystemCountList(TableInformationSystem tableInformationSystem);

    /**
     * 新增预算填报项目【信息系统】
     * 
     * @param tableInformationSystem 预算填报项目【信息系统】
     * @return 结果
     */
    public int insertTableInformationSystem(TableInformationSystem tableInformationSystem);

    /**
     * 修改预算填报项目【信息系统】
     * 
     * @param tableInformationSystem 预算填报项目【信息系统】
     * @return 结果
     */
    public int updateTableInformationSystem(TableInformationSystem tableInformationSystem);

    /**
     * 批量删除预算填报项目【信息系统】
     * 
     * @param ids 需要删除的预算填报项目【信息系统】主键集合
     * @return 结果
     */
    public int deleteTableInformationSystemByIds(Long[] ids);

    /**
     * 删除预算填报项目【信息系统】信息
     * 
     * @param id 预算填报项目【信息系统】主键
     * @return 结果
     */
    public int deleteTableInformationSystemById(Long id);

    AjaxResult updateStatus(FixedVo vo);

    public List<TableInformationSystem> budgetSummaryList(TableInformationSystem informationSystem);
}
