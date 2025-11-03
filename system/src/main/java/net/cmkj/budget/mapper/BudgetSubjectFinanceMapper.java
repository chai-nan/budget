package net.cmkj.budget.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import net.cmkj.budget.domain.BudgetSubjectFinance;

/**
 * 预算科目财务科目Mapper接口
 *
 * @author cmkj
 * @date 2024-10-14
 */
public interface BudgetSubjectFinanceMapper extends BaseMapper<BudgetSubjectFinance>
{

    /**
     * 查询预算科目财务科目列表
     *
     * @param budgetSubjectFinance 预算科目财务科目
     * @return 预算科目财务科目集合
     */
    public List<BudgetSubjectFinance> selectBudgetSubjectFinanceList(BudgetSubjectFinance budgetSubjectFinance);

    /**
     * 新增预算科目财务科目
     *
     * @param budgetSubjectFinance 预算科目财务科目
     * @return 结果
     */
    public int insertBudgetSubjectFinance(BudgetSubjectFinance budgetSubjectFinance);


    /**
     * 删除预算科目财务科目
     *
     * @param subjectId 预算科目财务科目主键
     * @return 结果
     */
    public int deleteBudgetSubjectFinanceBySubjectId(Long subjectId);

    int insertList(List<BudgetSubjectFinance> datas);

    List<BudgetSubjectFinance> selectByFinancialName(String kjkmsm);
}
