package net.cmkj.budget.service;


import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.model.LoginUser;

public interface IIndexStatisticsService {

    AjaxResult queryBudget(Long taskId, LoginUser loginUser);

    AjaxResult queryStatus(Integer year, LoginUser loginUser);

    AjaxResult queryMessages(LoginUser loginUser);

    AjaxResult queryDaiban(LoginUser loginUser);
}
