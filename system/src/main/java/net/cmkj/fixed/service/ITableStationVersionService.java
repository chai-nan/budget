package net.cmkj.fixed.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.fixed.domain.TableStationVersion;

/**
 * 预算填报订制【场站】版本数据Service接口
 * 
 * @author cmkj
 * @date 2024-09-26
 */
public interface ITableStationVersionService extends IService<TableStationVersion>
{
    /**
     * 查询预算填报订制【场站】版本数据
     * 
     * @param id 预算填报订制【场站】版本数据主键
     * @return 预算填报订制【场站】版本数据
     */
    public TableStationVersion selectTableStationVersionById(Long id);

    /**
     * 查询预算填报订制【场站】版本数据列表
     * 
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 预算填报订制【场站】版本数据集合
     */
    public List<TableStationVersion> selectTableStationVersionList(TableStationVersion tableStationVersion);

    /**
     * 新增预算填报订制【场站】版本数据
     * 
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 结果
     */
    public int insertTableStationVersion(TableStationVersion tableStationVersion);

    /**
     * 修改预算填报订制【场站】版本数据
     * 
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 结果
     */
    public int updateTableStationVersion(TableStationVersion tableStationVersion);

    /**
     * 批量删除预算填报订制【场站】版本数据
     * 
     * @param ids 需要删除的预算填报订制【场站】版本数据主键集合
     * @return 结果
     */
    public int deleteTableStationVersionByIds(Long[] ids);

    /**
     * 删除预算填报订制【场站】版本数据信息
     * 
     * @param id 预算填报订制【场站】版本数据主键
     * @return 结果
     */
    public int deleteTableStationVersionById(Long id);
}
