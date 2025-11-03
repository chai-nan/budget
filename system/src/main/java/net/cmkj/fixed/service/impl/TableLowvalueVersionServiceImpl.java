package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TableLowvalueVersionMapper;
import net.cmkj.fixed.domain.TableLowvalueVersion;
import net.cmkj.fixed.service.ITableLowvalueVersionService;

import java.util.List;
/**
 * 预算填报订制【低值易耗品】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TableLowvalueVersionServiceImpl extends ServiceImpl<TableLowvalueVersionMapper, TableLowvalueVersion> implements ITableLowvalueVersionService
{

    /**
     * 查询预算填报订制【低值易耗品】版本数据
     *
     * @param id 预算填报订制【低值易耗品】版本数据主键
     * @return 预算填报订制【低值易耗品】版本数据
     */
    @Override
    public TableLowvalueVersion selectTableLowvalueVersionById(Long id)
    {
        return baseMapper.selectTableLowvalueVersionById(id);
    }

    /**
     * 查询预算填报订制【低值易耗品】版本数据列表
     *
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 预算填报订制【低值易耗品】版本数据
     */
    @Override
    public List<TableLowvalueVersion> selectTableLowvalueVersionList(TableLowvalueVersion tableLowvalueVersion)
    {
        return baseMapper.selectTableLowvalueVersionList(tableLowvalueVersion);
    }

    /**
     * 新增预算填报订制【低值易耗品】版本数据
     *
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 结果
     */
    @Override
    public int insertTableLowvalueVersion(TableLowvalueVersion tableLowvalueVersion)
    {
        tableLowvalueVersion.setCreateBy(SecurityUtils.getUsername());
        tableLowvalueVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableLowvalueVersion(tableLowvalueVersion);
    }

    /**
     * 修改预算填报订制【低值易耗品】版本数据
     *
     * @param tableLowvalueVersion 预算填报订制【低值易耗品】版本数据
     * @return 结果
     */
    @Override
    public int updateTableLowvalueVersion(TableLowvalueVersion tableLowvalueVersion)
    {
        return baseMapper.updateTableLowvalueVersion(tableLowvalueVersion);
    }

    /**
     * 批量删除预算填报订制【低值易耗品】版本数据
     * 
     * @param ids 需要删除的预算填报订制【低值易耗品】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableLowvalueVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTableLowvalueVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【低值易耗品】版本数据信息
     * 
     * @param id 预算填报订制【低值易耗品】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableLowvalueVersionById(Long id)
    {
        return baseMapper.deleteTableLowvalueVersionById(id, SecurityUtils.getUsername());
    }
}
