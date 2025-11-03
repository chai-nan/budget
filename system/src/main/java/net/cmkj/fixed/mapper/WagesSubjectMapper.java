package net.cmkj.fixed.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.fixed.domain.WagesSubject;

/**
 * 工资字段类型Mapper接口
 *
 * @author cmkj
 * @date 2024-09-23
 */
public interface WagesSubjectMapper extends BaseMapper<WagesSubject>
{
    /**
     * 查询工资字段类型
     *
     * @param id 工资字段类型主键
     * @return 工资字段类型
     */
    public WagesSubject selectWagesSubjectById(Integer id);

    /**
     * 查询工资字段类型列表
     *
     * @param wagesSubject 工资字段类型
     * @return 工资字段类型集合
     */
    public List<WagesSubject> selectWagesSubjectList(WagesSubject wagesSubject);

    /**
     * 新增工资字段类型
     *
     * @param wagesSubject 工资字段类型
     * @return 结果
     */
    public int insertWagesSubject(WagesSubject wagesSubject);

    /**
     * 修改工资字段类型
     *
     * @param wagesSubject 工资字段类型
     * @return 结果
     */
    public int updateWagesSubject(WagesSubject wagesSubject);

    /**
     * 删除工资字段类型
     *
     * @param id 工资字段类型主键
     * @return 结果
     */
    public int deleteWagesSubjectById(@Param("id")Integer id, @Param("userName")String userName);

    /**
     * 批量删除工资字段类型
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWagesSubjectByIds(@Param("ids")Integer[] ids, @Param("userName")String userName);
}
