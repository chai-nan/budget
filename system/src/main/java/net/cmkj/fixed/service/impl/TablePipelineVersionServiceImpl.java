package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TablePipelineVersionMapper;
import net.cmkj.fixed.domain.TablePipelineVersion;
import net.cmkj.fixed.service.ITablePipelineVersionService;

import java.util.List;
/**
 * 预算填报订制【天然气管线】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TablePipelineVersionServiceImpl extends ServiceImpl<TablePipelineVersionMapper, TablePipelineVersion> implements ITablePipelineVersionService
{

    /**
     * 查询预算填报订制【天然气管线】版本数据
     *
     * @param id 预算填报订制【天然气管线】版本数据主键
     * @return 预算填报订制【天然气管线】版本数据
     */
    @Override
    public TablePipelineVersion selectTablePipelineVersionById(Long id)
    {
        return baseMapper.selectTablePipelineVersionById(id);
    }

    /**
     * 查询预算填报订制【天然气管线】版本数据列表
     *
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 预算填报订制【天然气管线】版本数据
     */
    @Override
    public List<TablePipelineVersion> selectTablePipelineVersionList(TablePipelineVersion tablePipelineVersion)
    {
        return baseMapper.selectTablePipelineVersionList(tablePipelineVersion);
    }

    /**
     * 新增预算填报订制【天然气管线】版本数据
     *
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 结果
     */
    @Override
    public int insertTablePipelineVersion(TablePipelineVersion tablePipelineVersion)
    {
        tablePipelineVersion.setCreateBy(SecurityUtils.getUsername());
        tablePipelineVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTablePipelineVersion(tablePipelineVersion);
    }

    /**
     * 修改预算填报订制【天然气管线】版本数据
     *
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 结果
     */
    @Override
    public int updateTablePipelineVersion(TablePipelineVersion tablePipelineVersion)
    {
        return baseMapper.updateTablePipelineVersion(tablePipelineVersion);
    }

    /**
     * 批量删除预算填报订制【天然气管线】版本数据
     * 
     * @param ids 需要删除的预算填报订制【天然气管线】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTablePipelineVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTablePipelineVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【天然气管线】版本数据信息
     * 
     * @param id 预算填报订制【天然气管线】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTablePipelineVersionById(Long id)
    {
        return baseMapper.deleteTablePipelineVersionById(id, SecurityUtils.getUsername());
    }
}
