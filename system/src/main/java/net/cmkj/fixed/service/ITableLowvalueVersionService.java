package net.cmkj.fixed.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.fixed.domain.TableLowvalueVersion;

/**
 * 预算填报订制【低值易耗品】版本数据Service接口
 * 
 * @author cmkj
 * @date 2024-09-26
 */
public interface ITableLowvalueVersionService extends IService<TableLowvalueVersion>
{
    /**
     * 查询预算填报订制【低值易耗品】版本数据
     * 
     * @param id 预算填报订制【低值易耗品】版本数据主键
     * @return 预算填报订制【低值易耗品】版本数据
     */
    public TableLowvalueVersion selectTableLowvalueVersionById(Long id);

    /**
     * 查询预算填报订制【低值易耗品】版本数据列表
     * 
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 预算填报订制【低值易耗品】版本数据集合
     */
    public List<TableLowvalueVersion> selectTableLowvalueVersionList(TableLowvalueVersion tableLowvalueVersion);

    /**
     * 新增预算填报订制【低值易耗品】版本数据
     * 
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 结果
     */
    public int insertTableLowvalueVersion(TableLowvalueVersion tableLowvalueVersion);

    /**
     * 修改预算填报订制【低值易耗品】版本数据
     * 
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 结果
     */
    public int updateTableLowvalueVersion(TableLowvalueVersion tableLowvalueVersion);

    /**
     * 批量删除预算填报订制【低值易耗品】版本数据
     * 
     * @param ids 需要删除的预算填报订制【低值易耗品】版本数据主键集合
     * @return 结果
     */
    public int deleteTableLowvalueVersionByIds(Long[] ids);

    /**
     * 删除预算填报订制【低值易耗品】版本数据信息
     * 
     * @param id 预算填报订制【低值易耗品】版本数据主键
     * @return 结果
     */
    public int deleteTableLowvalueVersionById(Long id);
}
