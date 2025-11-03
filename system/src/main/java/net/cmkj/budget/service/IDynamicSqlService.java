package net.cmkj.budget.service;

import java.util.List;
import java.util.Map;

/**
 * 动态SQL Service接口
 * 
 * @author cmkj
 * @date 2024-07-25
 */
public interface IDynamicSqlService {

    public List<Map<String,Object>> dynamicSelectListSql (String sql);

    public List<Map<String,Object>> dynamicSelectListSqlException (String sql) throws Exception;

    public Map<String,Object> dynamicSelectInfoSql (String sql);

    public int dynamicInsertSql (String sql);

    public int dynamicUpdateSql (String sql);
}
