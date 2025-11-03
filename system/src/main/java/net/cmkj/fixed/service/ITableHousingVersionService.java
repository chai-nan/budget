package net.cmkj.fixed.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.fixed.domain.TableHousingVersion;

/**
 * 预算填报订制【房屋建设、装修】版本数据Service接口
 * 
 * @author cmkj
 * @date 2024-09-26
 */
public interface ITableHousingVersionService extends IService<TableHousingVersion>
{
    /**
     * 查询预算填报订制【房屋建设、装修】版本数据
     * 
     * @param id 预算填报订制【房屋建设、装修】版本数据主键
     * @return 预算填报订制【房屋建设、装修】版本数据
     */
    public TableHousingVersion selectTableHousingVersionById(Long id);

    /**
     * 查询预算填报订制【房屋建设、装修】版本数据列表
     * 
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 预算填报订制【房屋建设、装修】版本数据集合
     */
    public List<TableHousingVersion> selectTableHousingVersionList(TableHousingVersion tableHousingVersion);

    /**
     * 新增预算填报订制【房屋建设、装修】版本数据
     * 
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 结果
     */
    public int insertTableHousingVersion(TableHousingVersion tableHousingVersion);

    /**
     * 修改预算填报订制【房屋建设、装修】版本数据
     * 
     * @param tableHousingVersion 预算填报订制【房屋建设、装修】版本数据
     * @return 结果
     */
    public int updateTableHousingVersion(TableHousingVersion tableHousingVersion);

    /**
     * 批量删除预算填报订制【房屋建设、装修】版本数据
     * 
     * @param ids 需要删除的预算填报订制【房屋建设、装修】版本数据主键集合
     * @return 结果
     */
    public int deleteTableHousingVersionByIds(Long[] ids);

    /**
     * 删除预算填报订制【房屋建设、装修】版本数据信息
     * 
     * @param id 预算填报订制【房屋建设、装修】版本数据主键
     * @return 结果
     */
    public int deleteTableHousingVersionById(Long id);
}
