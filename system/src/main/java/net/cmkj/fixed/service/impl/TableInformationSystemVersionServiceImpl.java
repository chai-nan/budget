package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TableInformationSystemVersionMapper;
import net.cmkj.fixed.domain.TableInformationSystemVersion;
import net.cmkj.fixed.service.ITableInformationSystemVersionService;

import java.util.List;
/**
 * 预算填报订制【信息系统】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TableInformationSystemVersionServiceImpl extends ServiceImpl<TableInformationSystemVersionMapper, TableInformationSystemVersion> implements ITableInformationSystemVersionService
{

    /**
     * 查询预算填报订制【信息系统】版本数据
     *
     * @param id 预算填报订制【信息系统】版本数据主键
     * @return 预算填报订制【信息系统】版本数据
     */
    @Override
    public TableInformationSystemVersion selectTableInformationSystemVersionById(Long id)
    {
        return baseMapper.selectTableInformationSystemVersionById(id);
    }

    /**
     * 查询预算填报订制【信息系统】版本数据列表
     *
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 预算填报订制【信息系统】版本数据
     */
    @Override
    public List<TableInformationSystemVersion> selectTableInformationSystemVersionList(TableInformationSystemVersion tableInformationSystemVersion)
    {
        return baseMapper.selectTableInformationSystemVersionList(tableInformationSystemVersion);
    }

    /**
     * 新增预算填报订制【信息系统】版本数据
     *
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 结果
     */
    @Override
    public int insertTableInformationSystemVersion(TableInformationSystemVersion tableInformationSystemVersion)
    {
        tableInformationSystemVersion.setCreateBy(SecurityUtils.getUsername());
        tableInformationSystemVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableInformationSystemVersion(tableInformationSystemVersion);
    }

    /**
     * 修改预算填报订制【信息系统】版本数据
     *
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 结果
     */
    @Override
    public int updateTableInformationSystemVersion(TableInformationSystemVersion tableInformationSystemVersion)
    {
        return baseMapper.updateTableInformationSystemVersion(tableInformationSystemVersion);
    }

    /**
     * 批量删除预算填报订制【信息系统】版本数据
     * 
     * @param ids 需要删除的预算填报订制【信息系统】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableInformationSystemVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTableInformationSystemVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【信息系统】版本数据信息
     * 
     * @param id 预算填报订制【信息系统】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableInformationSystemVersionById(Long id)
    {
        return baseMapper.deleteTableInformationSystemVersionById(id, SecurityUtils.getUsername());
    }
}
