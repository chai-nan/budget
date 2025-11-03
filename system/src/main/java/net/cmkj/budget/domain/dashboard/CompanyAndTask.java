package net.cmkj.budget.domain.dashboard;

import java.util.List;

/**
 * @FileName CompanyAndTask
 * @Description 公司和任务树
 * @Author @hanjianghui
 * @Date 2025/6/15 17:38
 **/
@lombok.Data
public class CompanyAndTask {
    private List<Company> companys;
    private List<Task> tasks;
}
