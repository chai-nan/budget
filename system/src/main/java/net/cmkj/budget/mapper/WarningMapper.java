package net.cmkj.budget.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.budget.domain.Warning;

/**
 * 预警Mapper接口
 *
 * @author cmkj
 * @date 2024-11-07
 */
public interface WarningMapper extends BaseMapper<Warning>
{
    /**
     * 查询预警
     *
     * @param id 预警主键
     * @return 预警
     */
    public Warning selectWarningById(Long id);

    /**
     * 查询预警列表
     *
     * @param warning 预警
     * @return 预警集合
     */
    public List<Warning> selectWarningList(Warning warning);

    /**
     * 新增预警
     *
     * @param warning 预警
     * @return 结果
     */
    public int insertWarning(Warning warning);

    /**
     * 修改预警
     *
     * @param warning 预警
     * @return 结果
     */
    public int updateWarning(Warning warning);

    /**
     * 删除预警
     *
     * @param id 预警主键
     * @return 结果
     */
    public int deleteWarningById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除预警
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarningByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
