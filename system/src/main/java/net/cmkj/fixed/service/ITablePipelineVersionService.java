package net.cmkj.fixed.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.fixed.domain.TablePipelineVersion;

/**
 * 预算填报订制【天然气管线】版本数据Service接口
 * 
 * @author cmkj
 * @date 2024-09-26
 */
public interface ITablePipelineVersionService extends IService<TablePipelineVersion>
{
    /**
     * 查询预算填报订制【天然气管线】版本数据
     * 
     * @param id 预算填报订制【天然气管线】版本数据主键
     * @return 预算填报订制【天然气管线】版本数据
     */
    public TablePipelineVersion selectTablePipelineVersionById(Long id);

    /**
     * 查询预算填报订制【天然气管线】版本数据列表
     * 
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 预算填报订制【天然气管线】版本数据集合
     */
    public List<TablePipelineVersion> selectTablePipelineVersionList(TablePipelineVersion tablePipelineVersion);

    /**
     * 新增预算填报订制【天然气管线】版本数据
     * 
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 结果
     */
    public int insertTablePipelineVersion(TablePipelineVersion tablePipelineVersion);

    /**
     * 修改预算填报订制【天然气管线】版本数据
     * 
     * @param tablePipelineVersion 预算填报订制【天然气管线】版本数据
     * @return 结果
     */
    public int updateTablePipelineVersion(TablePipelineVersion tablePipelineVersion);

    /**
     * 批量删除预算填报订制【天然气管线】版本数据
     * 
     * @param ids 需要删除的预算填报订制【天然气管线】版本数据主键集合
     * @return 结果
     */
    public int deleteTablePipelineVersionByIds(Long[] ids);

    /**
     * 删除预算填报订制【天然气管线】版本数据信息
     * 
     * @param id 预算填报订制【天然气管线】版本数据主键
     * @return 结果
     */
    public int deleteTablePipelineVersionById(Long id);
}
