package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TableCharityVersionMapper;
import net.cmkj.fixed.domain.TableCharityVersion;
import net.cmkj.fixed.service.ITableCharityVersionService;

import java.util.List;
/**
 * 预算填报订制【慈善公益】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TableCharityVersionServiceImpl extends ServiceImpl<TableCharityVersionMapper, TableCharityVersion> implements ITableCharityVersionService
{

    /**
     * 查询预算填报订制【慈善公益】版本数据
     *
     * @param id 预算填报订制【慈善公益】版本数据主键
     * @return 预算填报订制【慈善公益】版本数据
     */
    @Override
    public TableCharityVersion selectTableCharityVersionById(Long id)
    {
        return baseMapper.selectTableCharityVersionById(id);
    }

    /**
     * 查询预算填报订制【慈善公益】版本数据列表
     *
     * @param tableCharityVersion 预算填报订制【慈善公益】版本数据
     * @return 预算填报订制【慈善公益】版本数据
     */
    @Override
    public List<TableCharityVersion> selectTableCharityVersionList(TableCharityVersion tableCharityVersion)
    {
        return baseMapper.selectTableCharityVersionList(tableCharityVersion);
    }

    /**
     * 新增预算填报订制【慈善公益】版本数据
     *
     * @param tableCharityVersion 预算填报订制【慈善公益】版本数据
     * @return 结果
     */
    @Override
    public int insertTableCharityVersion(TableCharityVersion tableCharityVersion)
    {
        tableCharityVersion.setCreateBy(SecurityUtils.getUsername());
        tableCharityVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableCharityVersion(tableCharityVersion);
    }

    /**
     * 修改预算填报订制【慈善公益】版本数据
     *
     * @param tableCharityVersion 预算填报订制【慈善公益】版本数据
     * @return 结果
     */
    @Override
    public int updateTableCharityVersion(TableCharityVersion tableCharityVersion)
    {
        return baseMapper.updateTableCharityVersion(tableCharityVersion);
    }

    /**
     * 批量删除预算填报订制【慈善公益】版本数据
     * 
     * @param ids 需要删除的预算填报订制【慈善公益】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableCharityVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTableCharityVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【慈善公益】版本数据信息
     * 
     * @param id 预算填报订制【慈善公益】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableCharityVersionById(Long id)
    {
        return baseMapper.deleteTableCharityVersionById(id, SecurityUtils.getUsername());
    }
}
