package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TableStationVersionMapper;
import net.cmkj.fixed.domain.TableStationVersion;
import net.cmkj.fixed.service.ITableStationVersionService;

import java.util.List;
/**
 * 预算填报订制【场站】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TableStationVersionServiceImpl extends ServiceImpl<TableStationVersionMapper, TableStationVersion> implements ITableStationVersionService
{

    /**
     * 查询预算填报订制【场站】版本数据
     *
     * @param id 预算填报订制【场站】版本数据主键
     * @return 预算填报订制【场站】版本数据
     */
    @Override
    public TableStationVersion selectTableStationVersionById(Long id)
    {
        return baseMapper.selectTableStationVersionById(id);
    }

    /**
     * 查询预算填报订制【场站】版本数据列表
     *
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 预算填报订制【场站】版本数据
     */
    @Override
    public List<TableStationVersion> selectTableStationVersionList(TableStationVersion tableStationVersion)
    {
        return baseMapper.selectTableStationVersionList(tableStationVersion);
    }

    /**
     * 新增预算填报订制【场站】版本数据
     *
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 结果
     */
    @Override
    public int insertTableStationVersion(TableStationVersion tableStationVersion)
    {
        tableStationVersion.setCreateBy(SecurityUtils.getUsername());
        tableStationVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableStationVersion(tableStationVersion);
    }

    /**
     * 修改预算填报订制【场站】版本数据
     *
     * @param tableStationVersion 预算填报订制【场站】版本数据
     * @return 结果
     */
    @Override
    public int updateTableStationVersion(TableStationVersion tableStationVersion)
    {
        return baseMapper.updateTableStationVersion(tableStationVersion);
    }

    /**
     * 批量删除预算填报订制【场站】版本数据
     * 
     * @param ids 需要删除的预算填报订制【场站】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableStationVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTableStationVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【场站】版本数据信息
     * 
     * @param id 预算填报订制【场站】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableStationVersionById(Long id)
    {
        return baseMapper.deleteTableStationVersionById(id, SecurityUtils.getUsername());
    }
}
