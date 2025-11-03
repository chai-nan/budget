package net.cmkj.budget.domain.dashboard;

import lombok.Data;

import java.util.List;

/**
 * @FileName Tables
 * @Description 子大屏-表格
 * @Author @hanjianghui
 * @Date 2025/6/15 21:26
 **/
@Data
public class Tables {
    private List<String> names;
    //private List<Valus> valus;
    private Object[][] valus;
}
