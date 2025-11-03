package net.cmkj.fixed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.fixed.domain.FixedVo;
import net.cmkj.fixed.domain.TableCharity;

import java.util.List;

/**
 * 预算填报项目【慈善公益】Service接口
 * 
 * @author cmkj
 * @date 2024-08-28
 */
public interface ITableCharityService extends IService<TableCharity>
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
     * 批量删除预算填报项目【慈善公益】
     * 
     * @param ids 需要删除的预算填报项目【慈善公益】主键集合
     * @return 结果
     */
    public int deleteTableCharityByIds(Long[] ids);

    /**
     * 删除预算填报项目【慈善公益】信息
     * 
     * @param id 预算填报项目【慈善公益】主键
     * @return 结果
     */
    public int deleteTableCharityById(Long id);

    AjaxResult updateStatus(FixedVo vo);

    public List<TableCharity> budgetSummaryList(TableCharity charity);
}
