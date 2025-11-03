package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TableHousingVersionMapper;
import net.cmkj.fixed.domain.TableHousingVersion;
import net.cmkj.fixed.service.ITableHousingVersionService;

import java.util.List;
/**
 * 预算填报订制【房屋建设、装修】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TableHousingVersionServiceImpl extends ServiceImpl<TableHousingVersionMapper, TableHousingVersion> implements ITableHousingVersionService
{

    /**
     * 查询预算填报订制【房屋建设、装修】版本数据
     *
     * @param id 预算填报订制【房屋建设、装修】版本数据主键
     * @return 预算填报订制【房屋建设、装修】版本数据
     */
    @Override
    public TableHousingVersion selectTableHousingVersionById(Long id)
    {
        return baseMapper.selectTableHousingVersionById(id);
    }

    /**
     * 查询预算填报订制【房屋建设、装修】版本数据列表
     *
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 预算填报订制【房屋建设、装修】版本数据
     */
    @Override
    public List<TableHousingVersion> selectTableHousingVersionList(TableHousingVersion tableHousingVersion)
    {
        return baseMapper.selectTableHousingVersionList(tableHousingVersion);
    }

    /**
     * 新增预算填报订制【房屋建设、装修】版本数据
     *
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 结果
     */
    @Override
    public int insertTableHousingVersion(TableHousingVersion tableHousingVersion)
    {
        tableHousingVersion.setCreateBy(SecurityUtils.getUsername());
        tableHousingVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableHousingVersion(tableHousingVersion);
    }

    /**
     * 修改预算填报订制【房屋建设、装修】版本数据
     *
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 结果
     */
    @Override
    public int updateTableHousingVersion(TableHousingVersion tableHousingVersion)
    {
        return baseMapper.updateTableHousingVersion(tableHousingVersion);
    }

    /**
     * 批量删除预算填报订制【房屋建设、装修】版本数据
     * 
     * @param ids 需要删除的预算填报订制【房屋建设、装修】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableHousingVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTableHousingVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【房屋建设、装修】版本数据信息
     * 
     * @param id 预算填报订制【房屋建设、装修】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableHousingVersionById(Long id)
    {
        return baseMapper.deleteTableHousingVersionById(id, SecurityUtils.getUsername());
    }
}
