package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.budget.mapper.WarningMapper;
import net.cmkj.budget.domain.Warning;
import net.cmkj.budget.service.IWarningService;

import java.util.List;
/**
 * 预警Service业务层处理
 *
 * @author cmkj
 * @date 2024-11-07
 */
@Service
public class WarningServiceImpl extends ServiceImpl<WarningMapper, Warning> implements IWarningService
{

    /**
     * 查询预警
     *
     * @param id 预警主键
     * @return 预警
     */
    @Override
    public Warning selectWarningById(Long id)
    {
        return baseMapper.selectWarningById(id);
    }

    /**
     * 查询预警列表
     *
     * @param warning 预警
     * @return 预警
     */
    @Override
    public List<Warning> selectWarningList(Warning warning)
    {
        return baseMapper.selectWarningList(warning);
    }

    /**
     * 新增预警
     *
     * @param warning 预警
     * @return 结果
     */
    @Override
    public int insertWarning(Warning warning)
    {
        return baseMapper.insertWarning(warning);
    }

    /**
     * 修改预警
     *
     * @param warning 预警
     * @return 结果
     */
    @Override
    public int updateWarning(Warning warning)
    {
        return baseMapper.updateWarning(warning);
    }

    /**
     * 批量删除预警
     * 
     * @param ids 需要删除的预警主键
     * @return 结果
     */
    @Override
    public int deleteWarningByIds(Long[] ids)
    {
        return baseMapper.deleteWarningByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预警信息
     * 
     * @param id 预警主键
     * @return 结果
     */
    @Override
    public int deleteWarningById(Long id)
    {
        return baseMapper.deleteWarningById(id, SecurityUtils.getUsername());
    }
}
