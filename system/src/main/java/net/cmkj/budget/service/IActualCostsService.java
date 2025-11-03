package net.cmkj.budget.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.cmkj.budget.domain.ActualCosts;
import net.cmkj.budget.domain.ActualCostsFile;
import net.cmkj.common.core.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 实际费用Service接口
 * 
 * @author cmkj
 * @date 2024-08-22
 */
public interface IActualCostsService extends IService<ActualCosts>
{
    /**
     * 查询实际费用
     * 
     * @param id 实际费用主键
     * @return 实际费用
     */
    public ActualCosts selectActualCostsById(Long id);

    /**
     * 查询实际费用列表
     * 
     * @param actualCosts 实际费用
     * @return 实际费用集合
     */
    public List<ActualCosts> selectActualCostsList(ActualCosts actualCosts);

    /**
     * 新增实际费用
     * 
     * @param actualCosts 实际费用
     * @return 结果
     */
    public int insertActualCosts(ActualCosts actualCosts);

    /**
     * 修改实际费用
     * 
     * @param actualCosts 实际费用
     * @return 结果
     */
    public int updateActualCosts(ActualCosts actualCosts);

    /**
     * 批量删除实际费用
     * 
     * @param ids 需要删除的实际费用主键集合
     * @return 结果
     */
    public int deleteActualCostsByIds(Long[] ids);

    /**
     * 删除实际费用信息
     * 
     * @param id 实际费用主键
     * @return 结果
     */
    public int deleteActualCostsById(Long id);

    String importActualCosts(List<ActualCosts> actualCostsList, Boolean isUpdateSupport, String operName, ActualCostsFile actualCostsFile);

    ActualCosts selectLast(ActualCosts queryActualCosts);

    List<ActualCosts> selectActualCostsCountList(ActualCosts queryActualCosts);

    List<Map<String, Object>> selectTotals(Integer budgetYear);

    AjaxResult importActualCostsByFile(MultipartFile file, boolean updateSupport, String username, ActualCostsFile actualCostsFile);
}
