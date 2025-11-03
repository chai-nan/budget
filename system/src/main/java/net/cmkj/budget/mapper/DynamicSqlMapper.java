package net.cmkj.budget.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface DynamicSqlMapper {

    @Select("${sqlStr}")
    public List<Map<String,Object>> dynamicSelectListSql (@Param("sqlStr") String sql);

    @Select("${sqlStr}")
    public Map<String,Object> dynamicSelectInfoSql (@Param("sqlStr") String sql);

    @Insert("${sqlStr}")
    public int dynamicInsertSql (@Param("sqlStr") String sql);

    @Update("${sqlStr}")
    public int dynamicUpdateSql (@Param("sqlStr") String sql);

}
