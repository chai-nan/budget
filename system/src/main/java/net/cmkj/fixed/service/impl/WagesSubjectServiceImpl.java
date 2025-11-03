package net.cmkj.fixed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import net.cmkj.fixed.mapper.WagesSubjectMapper;
import net.cmkj.fixed.domain.WagesSubject;
import net.cmkj.fixed.service.IWagesSubjectService;

import java.util.List;
/**
 * 工资字段类型Service业务层处理
 *
 * @author cmkj
 * @date 2024-09-23
 */
@Service
public class WagesSubjectServiceImpl extends ServiceImpl<WagesSubjectMapper, WagesSubject> implements IWagesSubjectService
{

    /**
     * 查询工资字段类型
     *
     * @param id 工资字段类型主键
     * @return 工资字段类型
     */
    @Override
    public WagesSubject selectWagesSubjectById(Integer id)
    {
        return baseMapper.selectWagesSubjectById(id);
    }

    /**
     * 查询工资字段类型列表
     *
     * @param wagesSubject 工资字段类型
     * @return 工资字段类型
     */
    @Override
    public List<WagesSubject> selectWagesSubjectList(WagesSubject wagesSubject)
    {
        return baseMapper.selectWagesSubjectList(wagesSubject);
    }

    /**
     * 新增工资字段类型
     *
     * @param wagesSubject 工资字段类型
     * @return 结果
     */
    @Override
    public int insertWagesSubject(WagesSubject wagesSubject)
    {
        return baseMapper.insertWagesSubject(wagesSubject);
    }

    /**
     * 修改工资字段类型
     *
     * @param wagesSubject 工资字段类型
     * @return 结果
     */
    @Override
    public int updateWagesSubject(WagesSubject wagesSubject)
    {
        return baseMapper.updateWagesSubject(wagesSubject);
    }

    /**
     * 批量删除工资字段类型
     *
     * @param ids 需要删除的工资字段类型主键
     * @return 结果
     */
    @Override
    public int deleteWagesSubjectByIds(Integer[] ids)
    {
        return baseMapper.deleteWagesSubjectByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除工资字段类型信息
     *
     * @param id 工资字段类型主键
     * @return 结果
     */
    @Override
    public int deleteWagesSubjectById(Integer id)
    {
        return baseMapper.deleteWagesSubjectById(id, SecurityUtils.getUsername());
    }
}
