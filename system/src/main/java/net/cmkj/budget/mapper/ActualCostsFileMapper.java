package net.cmkj.budget.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.budget.domain.ActualCostsFile;

/**
 * 实际费用文件Mapper接口
 *
 * @author cmkj
 * @date 2025-01-21
 */
public interface ActualCostsFileMapper extends BaseMapper<ActualCostsFile>
{
    /**
     * 查询实际费用文件
     *
     * @param id 实际费用文件主键
     * @return 实际费用文件
     */
    public ActualCostsFile selectActualCostsFileById(Long id);

    /**
     * 查询实际费用文件列表
     *
     * @param actualCostsFile 实际费用文件
     * @return 实际费用文件集合
     */
    public List<ActualCostsFile> selectActualCostsFileList(ActualCostsFile actualCostsFile);

    /**
     * 新增实际费用文件
     *
     * @param actualCostsFile 实际费用文件
     * @return 结果
     */
    public int insertActualCostsFile(ActualCostsFile actualCostsFile);

    /**
     * 修改实际费用文件
     *
     * @param actualCostsFile 实际费用文件
     * @return 结果
     */
    public int updateActualCostsFile(ActualCostsFile actualCostsFile);

    /**
     * 删除实际费用文件
     *
     * @param id 实际费用文件主键
     * @return 结果
     */
    public int deleteActualCostsFileById(@Param("id")Long id, @Param("userName")String userName);

    /**
     * 批量删除实际费用文件
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActualCostsFileByIds(@Param("ids")Long[] ids, @Param("userName")String userName);
}
