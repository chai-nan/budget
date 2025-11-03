package net.cmkj.budget.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.cmkj.budget.mapper.DynamicSqlMapper;
import net.cmkj.budget.service.IDynamicSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态SQL Service业务层处理
 *
 * @author cmkj
 * @date 2024-07-15
 */
@Service
@Slf4j
public class DynamicSqlServiceImpl implements IDynamicSqlService {

    @Autowired
    private DynamicSqlMapper dynamicSqlMapper;

    public List<Map<String,Object>> dynamicSelectListSql (String sql){
        List<Map<String,Object>> list = null;
        try {
            list = dynamicSqlMapper.dynamicSelectListSql(sql);
        }catch (Exception e){
            log.error("\n【动态Sql】：{}\n【执行异常】：{}", sql, e.getCause().getMessage(), e);
        }
        return list == null ? new ArrayList<>() : list;
    }
    public List<Map<String,Object>> dynamicSelectListSqlException (String sql) throws Exception{
        return dynamicSqlMapper.dynamicSelectListSql(sql);
    }

    public Map<String,Object> dynamicSelectInfoSql (String sql){
        Map<String,Object> map = null;
        try {
            map = dynamicSqlMapper.dynamicSelectInfoSql(sql);
        }catch (Exception e){
            log.error("\n【动态Sql】：{}\n【执行异常】：{}", sql, e.getCause().getMessage(), e);
        }
        return map == null ? new HashMap<>() : map;
    }

    public int dynamicInsertSql(String sql){
        return dynamicSqlMapper.dynamicInsertSql(sql);
    }

    public int dynamicUpdateSql(String sql){
        return dynamicSqlMapper.dynamicUpdateSql(sql);
    }
}
