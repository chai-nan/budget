package net.cmkj.fixed.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.fixed.domain.TableResearchVersion;

/**
 * 预算填报订制【科研计划申报】版本数据Service接口
 * 
 * @author cmkj
 * @date 2024-09-26
 */
public interface ITableResearchVersionService extends IService<TableResearchVersion>
{
    /**
     * 查询预算填报订制【科研计划申报】版本数据
     * 
     * @param id 预算填报订制【科研计划申报】版本数据主键
     * @return 预算填报订制【科研计划申报】版本数据
     */
    public TableResearchVersion selectTableResearchVersionById(Long id);

    /**
     * 查询预算填报订制【科研计划申报】版本数据列表
     * 
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 预算填报订制【科研计划申报】版本数据集合
     */
    public List<TableResearchVersion> selectTableResearchVersionList(TableResearchVersion tableResearchVersion);

    /**
     * 新增预算填报订制【科研计划申报】版本数据
     * 
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 结果
     */
    public int insertTableResearchVersion(TableResearchVersion tableResearchVersion);

    /**
     * 修改预算填报订制【科研计划申报】版本数据
     * 
     * @param tableResearchVersion 预算填报订制【科研计划申报】版本数据
     * @return 结果
     */
    public int updateTableResearchVersion(TableResearchVersion tableResearchVersion);

    /**
     * 批量删除预算填报订制【科研计划申报】版本数据
     * 
     * @param ids 需要删除的预算填报订制【科研计划申报】版本数据主键集合
     * @return 结果
     */
    public int deleteTableResearchVersionByIds(Long[] ids);

    /**
     * 删除预算填报订制【科研计划申报】版本数据信息
     * 
     * @param id 预算填报订制【科研计划申报】版本数据主键
     * @return 结果
     */
    public int deleteTableResearchVersionById(Long id);
}
