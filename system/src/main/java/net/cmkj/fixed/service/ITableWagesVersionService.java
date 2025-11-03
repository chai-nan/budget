package net.cmkj.fixed.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.fixed.domain.TableWages;
import net.cmkj.fixed.domain.TableWagesVersion;

/**
 * 预算填报订制【工资福利】版本数据Service接口
 * 
 * @author cmkj
 * @date 2024-09-26
 */
public interface ITableWagesVersionService extends IService<TableWagesVersion>
{
    /**
     * 查询预算填报订制【工资福利】版本数据
     * 
     * @param id 预算填报订制【工资福利】版本数据主键
     * @return 预算填报订制【工资福利】版本数据
     */
    public TableWagesVersion selectTableWagesVersionById(Long id);

    /**
     * 查询预算填报订制【工资福利】版本数据列表
     * 
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 预算填报订制【工资福利】版本数据集合
     */
    public List<TableWagesVersion> selectTableWagesVersionList(TableWagesVersion tableWagesVersion);

    /**
     * 新增预算填报订制【工资福利】版本数据
     * 
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 结果
     */
    public int insertTableWagesVersion(TableWagesVersion tableWagesVersion);

    /**
     * 修改预算填报订制【工资福利】版本数据
     * 
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 结果
     */
    public int updateTableWagesVersion(TableWagesVersion tableWagesVersion);

    /**
     * 批量删除预算填报订制【工资福利】版本数据
     * 
     * @param ids 需要删除的预算填报订制【工资福利】版本数据主键集合
     * @return 结果
     */
    public int deleteTableWagesVersionByIds(Long[] ids);

    /**
     * 删除预算填报订制【工资福利】版本数据信息
     * 
     * @param id 预算填报订制【工资福利】版本数据主键
     * @return 结果
     */
    public int deleteTableWagesVersionById(Long id);

    List<Map<String, Object>> selectSummaryList(TableWagesVersion wages);

    List<Map<String, Object>> budgetSummary2(TableWagesVersion wages);
}
