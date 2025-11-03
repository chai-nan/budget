package net.cmkj.quartz.task;

import net.cmkj.budget.service.IBudgetWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author Yuan
 */
@Component("task")
public class CmkjTask
{

    @Autowired
    private IBudgetWarningService budgetWarningService;

    /**
     * 填报预警
     */
    public void reportWarning()
    {
        budgetWarningService.reportWarning();
    }

    /**
     * 超出预警
     */
    public void exceedWarning()
    {
        budgetWarningService.exceedWarning();
    }

    /**
     * 费用报销预警
     */
    public void costWarning()
    {
        budgetWarningService.costWarning();
    }

    /**
     * 经济事项预警
     */
    public void economicMattersWarning()
    {
        budgetWarningService.economicMattersWarning();
    }


}
