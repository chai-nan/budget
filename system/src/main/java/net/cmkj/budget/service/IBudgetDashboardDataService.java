package net.cmkj.budget.service;

import net.cmkj.budget.domain.dashboard.QueryTableDetailDTO;
import net.cmkj.budget.domain.dashboard.Data;
import net.cmkj.budget.domain.dashboard.TableDetailVO;
import net.cmkj.budget.domain.dashboard.Ygcbzdp;
import net.cmkj.common.core.domain.AjaxResult;

import java.util.List;

/**
 * @FileName IBudgetDashboardDataService
 * @Description 大屏数据服务层
 * @Author @hanjianghui
 * @Date 2025/6/13 14:27
 **/
public interface IBudgetDashboardDataService {

    Data getData(Long taskId, Long deptId);

    AjaxResult getCompanyAndTask();

    Ygcbzdp getZdpData(Long taskId, Long deptId, Long type);

    List<TableDetailVO> getTableDetailInfo(QueryTableDetailDTO queryTableDetailDTO);
}
