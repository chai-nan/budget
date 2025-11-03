package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.fixed.domain.TableWages;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.TableWagesVersionMapper;
import net.cmkj.fixed.domain.TableWagesVersion;
import net.cmkj.fixed.service.ITableWagesVersionService;

import java.util.List;
import java.util.Map;

/**
 * 预算填报订制【工资福利】版本数据Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-26
 */
@Service
public class TableWagesVersionServiceImpl extends ServiceImpl<TableWagesVersionMapper, TableWagesVersion> implements ITableWagesVersionService
{

    /**
     * 查询预算填报订制【工资福利】版本数据
     *
     * @param id 预算填报订制【工资福利】版本数据主键
     * @return 预算填报订制【工资福利】版本数据
     */
    @Override
    public TableWagesVersion selectTableWagesVersionById(Long id)
    {
        return baseMapper.selectTableWagesVersionById(id);
    }

    /**
     * 查询预算填报订制【工资福利】版本数据列表
     *
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 预算填报订制【工资福利】版本数据
     */
    @Override
    public List<TableWagesVersion> selectTableWagesVersionList(TableWagesVersion tableWagesVersion)
    {
        return baseMapper.selectTableWagesVersionList(tableWagesVersion);
    }

    /**
     * 新增预算填报订制【工资福利】版本数据
     *
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 结果
     */
    @Override
    public int insertTableWagesVersion(TableWagesVersion tableWagesVersion)
    {
        tableWagesVersion.setCreateBy(SecurityUtils.getUsername());
        tableWagesVersion.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertTableWagesVersion(tableWagesVersion);
    }

    /**
     * 修改预算填报订制【工资福利】版本数据
     *
     * @param tableWagesVersion 预算填报订制【工资福利】版本数据
     * @return 结果
     */
    @Override
    public int updateTableWagesVersion(TableWagesVersion tableWagesVersion)
    {
        return baseMapper.updateTableWagesVersion(tableWagesVersion);
    }

    /**
     * 批量删除预算填报订制【工资福利】版本数据
     * 
     * @param ids 需要删除的预算填报订制【工资福利】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableWagesVersionByIds(Long[] ids)
    {
        return baseMapper.deleteTableWagesVersionByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预算填报订制【工资福利】版本数据信息
     * 
     * @param id 预算填报订制【工资福利】版本数据主键
     * @return 结果
     */
    @Override
    public int deleteTableWagesVersionById(Long id)
    {
        return baseMapper.deleteTableWagesVersionById(id, SecurityUtils.getUsername());
    }

    @Override
    public List<Map<String, Object>> selectSummaryList(TableWagesVersion wages) {
        return baseMapper.selectSummaryList(wages);
    }

    @Override
    public List<Map<String, Object>>  budgetSummary2(TableWagesVersion wages) {
        return baseMapper.budgetSummary2(wages);
    }
}
