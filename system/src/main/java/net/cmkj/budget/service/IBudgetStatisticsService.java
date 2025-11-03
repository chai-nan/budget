package net.cmkj.budget.service;

import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.common.core.domain.model.LoginUser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 预算统计Service接口
 * 
 * @author cmkj
 */
public interface IBudgetStatisticsService {

    Map<String, Object> budgetSummary(Integer type,Long taskId,Long deptId);

    Map<String, Object> budgetSummaryOA(Integer type,Long taskId,Long deptId,Long versionId);

    Map<String, Object> budgetVersionSummary(Integer type, Long taskId,Long deptId,Long versionId);

    Map<String, Object>  reportSummary(Long taskId, LoginUser user);

    void reportSummaryExport(HttpServletResponse response, Long taskId, LoginUser loginUser);

    void budgetExport(HttpServletResponse response, Long taskId, Integer type,Long deptId,Long versionId)throws IOException;

    // Map<String, Object> itemSummary(Long taskId);
    Map<String, Object> itemSummary(Long taskId);

    Map<String, Object> itemAreaSummary(Long taskId);

    void exportTotal(HttpServletResponse response, Long taskId);

    List<Map<String, Object>> statisticsAnalysis(Long deptId,Long versionId,int type);

    void exportStatisticsAnalysis(HttpServletResponse response, Long deptId, Long versionId);

    List<Map<String, Object>> budgetVersionWarningSummary(Long taskId, Long versionId);
}
