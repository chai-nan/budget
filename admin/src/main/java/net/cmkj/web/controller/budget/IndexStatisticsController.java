package net.cmkj.web.controller.budget;

import net.cmkj.budget.domain.ReportingTask;
import net.cmkj.budget.service.IIndexStatisticsService;
import net.cmkj.budget.service.IReportingTaskService;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.system.domain.SysNotice;
import net.cmkj.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页统计Controller
 * 
 */
@RestController
@RequestMapping("/index/statistics")
public class IndexStatisticsController extends BaseController {

    @Autowired
    private IReportingTaskService reportingTaskService;

    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private IIndexStatisticsService service;

    /**
     * 待办事项
     */
    @GetMapping("/queryTask")
    public AjaxResult queryTask(Integer year) {
        ReportingTask queryTask = new ReportingTask();
        queryTask.setYear(year);
        List<ReportingTask> list = reportingTaskService.selectReportingTaskList(queryTask);
        return AjaxResult.success(list);
    }

    /**
     * 待办事项
     */
    @GetMapping("/queryDaiban")
    public AjaxResult queryDaiban() {
        return service.queryDaiban(getLoginUser());
    }

    /**
     * 预算填报分析
     */
    @GetMapping("/queryBudget")
    public AjaxResult queryBudget(Long taskId) {
        return service.queryBudget(taskId,getLoginUser());
    }

    /**
     * 状态统计
     */
    @GetMapping("/queryStatus")
    public AjaxResult queryStatus(Integer year) {
        return service.queryStatus(year,getLoginUser());
    }

    /**
     * 消息中心
     */
    @GetMapping("/queryMessages")
    public AjaxResult queryMessages() {
        return service.queryMessages(getLoginUser());
    }

    /**
     * 通知公告
     */
    @GetMapping("/queryNotice")
    public AjaxResult list() {
        SysNotice queryNotice = new SysNotice();
        queryNotice.setStatus("0");
        List<SysNotice> list = noticeService.selectNoticeList(queryNotice);
        return AjaxResult.success(list);
    }

    /**
     * 任务下拉框选择
     */
    @GetMapping("/taskList")
    public AjaxResult taskList() {
        ReportingTask queryTask = new ReportingTask();
        queryTask.setDelFlag("0");
        List<ReportingTask> list = reportingTaskService.selectReportingTaskList(queryTask);
        return AjaxResult.success(list);
    }

}
