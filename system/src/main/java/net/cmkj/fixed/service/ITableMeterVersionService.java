package net.cmkj.fixed.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.fixed.domain.TableMeterVersion;

/**
 * 预算填报订制【计量器】版本数据Service接口
 * 
 * @author cmkj
 * @date 2024-09-26
 */
public interface ITableMeterVersionService extends IService<TableMeterVersion>
{
    /**
     * 查询预算填报订制【计量器】版本数据
     * 
     * @param id 预算填报订制【计量器】版本数据主键
     * @return 预算填报订制【计量器】版本数据
     */
    public TableMeterVersion selectTableMeterVersionById(Long id);

    /**
     * 查询预算填报订制【计量器】版本数据列表
     * 
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 预算填报订制【计量器】版本数据集合
     */
    public List<TableMeterVersion> selectTableMeterVersionList(TableMeterVersion tableMeterVersion);

    /**
     * 新增预算填报订制【计量器】版本数据
     * 
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 结果
     */
    public int insertTableMeterVersion(TableMeterVersion tableMeterVersion);

    /**
     * 修改预算填报订制【计量器】版本数据
     * 
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 结果
     */
    public int updateTableMeterVersion(TableMeterVersion tableMeterVersion);

    /**
     * 批量删除预算填报订制【计量器】版本数据
     * 
     * @param ids 需要删除的预算填报订制【计量器】版本数据主键集合
     * @return 结果
     */
    public int deleteTableMeterVersionByIds(Long[] ids);

    /**
     * 删除预算填报订制【计量器】版本数据信息
     * 
     * @param id 预算填报订制【计量器】版本数据主键
     * @return 结果
     */
    public int deleteTableMeterVersionById(Long id);
}
