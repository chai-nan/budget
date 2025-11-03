package net.cmkj.fixed.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.fixed.domain.TableInformationSystemVersion;

/**
 * 预算填报订制【信息系统】版本数据Service接口
 * 
 * @author cmkj
 * @date 2024-09-26
 */
public interface ITableInformationSystemVersionService extends IService<TableInformationSystemVersion>
{
    /**
     * 查询预算填报订制【信息系统】版本数据
     * 
     * @param id 预算填报订制【信息系统】版本数据主键
     * @return 预算填报订制【信息系统】版本数据
     */
    public TableInformationSystemVersion selectTableInformationSystemVersionById(Long id);

    /**
     * 查询预算填报订制【信息系统】版本数据列表
     * 
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 预算填报订制【信息系统】版本数据集合
     */
    public List<TableInformationSystemVersion> selectTableInformationSystemVersionList(TableInformationSystemVersion tableInformationSystemVersion);

    /**
     * 新增预算填报订制【信息系统】版本数据
     * 
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 结果
     */
    public int insertTableInformationSystemVersion(TableInformationSystemVersion tableInformationSystemVersion);

    /**
     * 修改预算填报订制【信息系统】版本数据
     * 
     * @param tableInformationSystemVersion 预算填报订制【信息系统】版本数据
     * @return 结果
     */
    public int updateTableInformationSystemVersion(TableInformationSystemVersion tableInformationSystemVersion);

    /**
     * 批量删除预算填报订制【信息系统】版本数据
     * 
     * @param ids 需要删除的预算填报订制【信息系统】版本数据主键集合
     * @return 结果
     */
    public int deleteTableInformationSystemVersionByIds(Long[] ids);

    /**
     * 删除预算填报订制【信息系统】版本数据信息
     * 
     * @param id 预算填报订制【信息系统】版本数据主键
     * @return 结果
     */
    public int deleteTableInformationSystemVersionById(Long id);
}
