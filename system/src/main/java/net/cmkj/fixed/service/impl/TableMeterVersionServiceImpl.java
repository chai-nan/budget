package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TableMeterVersionMapper;
import net.cmkj.fixed.domain.TableMeterVersion;
import net.cmkj.fixed.service.ITableMeterVersionService;

import java.util.List;
/**
 * 预算填报订制【计量器】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TableMeterVersionServiceImpl extends ServiceImpl<TableMeterVersionMapper, TableMeterVersion> implements ITableMeterVersionService
{

    /**
     * 查询预算填报订制【计量器】版本数据
     *
     * @param id 预算填报订制【计量器】版本数据主键
     * @return 预算填报订制【计量器】版本数据
     */
    @Override
    public TableMeterVersion selectTableMeterVersionById(Long id)
    {
        return baseMapper.selectTableMeterVersionById(id);
    }

    /**
     * 查询预算填报订制【计量器】版本数据列表
     *
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 预算填报订制【计量器】版本数据
     */
    @Override
    public List<TableMeterVersion> selectTableMeterVersionList(TableMeterVersion tableMeterVersion)
    {
        return baseMapper.selectTableMeterVersionList(tableMeterVersion);
    }

    /**
     * 新增预算填报订制【计量器】版本数据
     *
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 结果
     */
    @Override
    public int insertTableMeterVersion(TableMeterVersion tableMeterVersion)
    {
        tableMeterVersion.setCreateBy(SecurityUtils.getUsername());
        tableMeterVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableMeterVersion(tableMeterVersion);
    }

    /**
     * 修改预算填报订制【计量器】版本数据
     *
     * @param tableMeterVersion 预算填报订制【计量器】版本数据
     * @return 结果
     */
    @Override
    public int updateTableMeterVersion(TableMeterVersion tableMeterVersion)
    {
        return baseMapper.updateTableMeterVersion(tableMeterVersion);
    }

    /**
     * 批量删除预算填报订制【计量器】版本数据
     * 
     * @param ids 需要删除的预算填报订制【计量器】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableMeterVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTableMeterVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【计量器】版本数据信息
     * 
     * @param id 预算填报订制【计量器】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableMeterVersionById(Long id)
    {
        return baseMapper.deleteTableMeterVersionById(id, SecurityUtils.getUsername());
    }
}
