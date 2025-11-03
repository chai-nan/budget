package net.cmkj.budget.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface BudgetReportingMapper {

    int updateModuleData(@Param("tableName")String tableName,@Param("tableColumns") Map<String, Object> record);
}
