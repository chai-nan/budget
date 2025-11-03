package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TableResearchVersionMapper;
import net.cmkj.fixed.domain.TableResearchVersion;
import net.cmkj.fixed.service.ITableResearchVersionService;

import java.util.List;
/**
 * 预算填报订制【科研计划申报】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TableResearchVersionServiceImpl extends ServiceImpl<TableResearchVersionMapper, TableResearchVersion> implements ITableResearchVersionService
{

    /**
     * 查询预算填报订制【科研计划申报】版本数据
     *
     * @param id 预算填报订制【科研计划申报】版本数据主键
     * @return 预算填报订制【科研计划申报】版本数据
     */
    @Override
    public TableResearchVersion selectTableResearchVersionById(Long id)
    {
        return baseMapper.selectTableResearchVersionById(id);
    }

    /**
     * 查询预算填报订制【科研计划申报】版本数据列表
     *
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 预算填报订制【科研计划申报】版本数据
     */
    @Override
    public List<TableResearchVersion> selectTableResearchVersionList(TableResearchVersion tableResearchVersion)
    {
        return baseMapper.selectTableResearchVersionList(tableResearchVersion);
    }

    /**
     * 新增预算填报订制【科研计划申报】版本数据
     *
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 结果
     */
    @Override
    public int insertTableResearchVersion(TableResearchVersion tableResearchVersion)
    {
        tableResearchVersion.setCreateBy(SecurityUtils.getUsername());
        tableResearchVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableResearchVersion(tableResearchVersion);
    }

    /**
     * 修改预算填报订制【科研计划申报】版本数据
     *
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 结果
     */
    @Override
    public int updateTableResearchVersion(TableResearchVersion tableResearchVersion)
    {
        return baseMapper.updateTableResearchVersion(tableResearchVersion);
    }

    /**
     * 批量删除预算填报订制【科研计划申报】版本数据
     * 
     * @param ids 需要删除的预算填报订制【科研计划申报】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableResearchVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTableResearchVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【科研计划申报】版本数据信息
     * 
     * @param id 预算填报订制【科研计划申报】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableResearchVersionById(Long id)
    {
        return baseMapper.deleteTableResearchVersionById(id, SecurityUtils.getUsername());
    }
}
