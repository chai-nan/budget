package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.cmkj.budget.domain.TableModel;
import net.cmkj.budget.mapper.TableModelMapper;
import net.cmkj.budget.service.ITableModelService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Objects;

/**
 * 填报模块Service业务层处理
 * 
 * @author cmkj
 * @date 2024-07-15
 */
@Service
@Slf4j
public class TableModelServiceImpl extends ServiceImpl<TableModelMapper, TableModel> implements ITableModelService
{

    /**
     * 查询填报模块
     * 
     * @param id 填报模块主键
     * @return 填报模块
     */
    @Override
    public TableModel selectTableModelById(Long id)
    {
        return baseMapper.selectById(id);
    }

    /**
     * 查询填报模块列表
     * 
     * @param tableModel 填报模块
     * @return 填报模块
     */
    @Override
    public List<TableModel> selectTableModelList(TableModel tableModel)
    {
        return baseMapper.selectTableModelList(tableModel);
    }

    /**
     * 新增填报模块
     * 
     * @param tableModel 填报模块
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertTableModel(TableModel tableModel) {
        try {
            if(tableModel==null || StringUtils.isEmpty(tableModel.getTableName()) || StringUtils.isEmpty(tableModel.getFieldType())){
                return AjaxResult.error("新增字段数据有误！");
            }
            int index = baseMapper.selectIndex(tableModel.getTableName())+1;
            tableModel.setType(2);
            tableModel.setFieldDisplay("0");//是否显示
            tableModel.setFieldName("field"+index);
            tableModel.setDelFlag("0");
            tableModel.setCreateTime(DateUtils.getNowDate());
            tableModel.setCreateBy(SecurityUtils.getUsername());
            if(Objects.equals(tableModel.getFieldType(), "3")){
                tableModel.setFieldDecimalLength(2);
            }
            if(baseMapper.insert(tableModel)<=0){
                return AjaxResult.error("新增字段失败！");
            }
            String columnSql = getColumnSql(tableModel);
            baseMapper.dynamicSql("ALTER TABLE t_reporting_table_"+tableModel.getTableName()+"_extension ADD COLUMN "+columnSql);
            baseMapper.dynamicSql("ALTER TABLE t_reporting_table_"+tableModel.getTableName()+"_extension_version ADD COLUMN "+columnSql);
            return AjaxResult.success();
        }catch (Exception e){
            log.error("新增字段异常:{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("新增字段异常！");
        }
    }


    /**
     * 修改填报模块
     * 
     * @param tableModel 填报模块
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateTableModel(TableModel tableModel){
        try {
            TableModel query = baseMapper.selectById(tableModel.getId());
            if(query==null || StringUtils.isEmpty(query.getTableName()) || StringUtils.isEmpty(tableModel.getFieldType()) || StringUtils.isEmpty(tableModel.getFieldName())){
                return AjaxResult.error("新增字段数据有误！");
            }
            if(query.getType()==1){
                query.setOrderNum(tableModel.getOrderNum());
                if(baseMapper.updateTableModel(query)<=0){
                    return AjaxResult.error("修改字段失败！");
                }
            }else{
                if(Objects.equals(tableModel.getFieldType(), "3")){
                    tableModel.setFieldDecimalLength(2);
                }else{
                    tableModel.setFieldDecimalLength(null);
                }
                tableModel.setUpdateTime(DateUtils.getNowDate());
                tableModel.setUpdateBy(SecurityUtils.getUsername());
                if(baseMapper.updateTableModel(tableModel)<=0){
                    return AjaxResult.error("修改字段失败！");
                }
                String columnSql = getColumnSql(tableModel);
                baseMapper.dynamicSql("ALTER TABLE t_reporting_table_"+query.getTableName()+"_extension MODIFY COLUMN "+columnSql);
                baseMapper.dynamicSql("ALTER TABLE t_reporting_table_"+query.getTableName()+"_extension_version MODIFY COLUMN "+columnSql);
            }
            return AjaxResult.success();
        }catch (Exception e){
            log.error("更新字段异常:{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error("更新字段异常！");
        }
    }

    private static String getColumnSql(TableModel tableModel) {
        String notnull = "NULL";
        /*字段非空设置
        if(Objects.equals(tableModel.getFieldRequired(), "0")){
            notnull = "NOT NULL";
        }
        */
        String type = "";
        Integer fieldLength = tableModel.getFieldLength();
        if(Objects.equals(tableModel.getFieldType(), "1")){
            if(fieldLength!=null){
                type = "VARCHAR("+fieldLength+")";
            }else{
                type = "VARCHAR(1024)";
            }
        }else if(Objects.equals(tableModel.getFieldType(), "2")){
            if(fieldLength!=null){
                type = "INT("+fieldLength+")";
            }else{
                type = "INT(11)";
            }
        }else if(Objects.equals(tableModel.getFieldType(), "3")){
            if(fieldLength!=null){
                type = "DECIMAL("+fieldLength+",2)";
            }else{
                type = "DECIMAL(10,2)";
            }
        }else if(Objects.equals(tableModel.getFieldType(), "4")){
            type = "VARCHAR(50)";
        }else if(Objects.equals(tableModel.getFieldType(), "5")){
            type = "text";
        }
        return " `"+ tableModel.getFieldName()+"`  "+type+" "+notnull+" COMMENT '"+tableModel.getFieldDisplayName()+"';";
    }

    /**
     * 批量删除填报模块
     * 
     * @param ids 需要删除的填报模块主键
     * @return 结果
     */
    @Override
    public int deleteTableModelByIds(Long[] ids) {
        for (Long id : ids) {
            TableModel query = baseMapper.selectTableModelById(id);
            if(query.getFieldRequired().equals("0")){
                query.setFieldRequired("1");
                String columnSql = getColumnSql(query);
                baseMapper.dynamicSql("ALTER TABLE t_reporting_table_"+query.getTableName()+"_extension MODIFY COLUMN "+columnSql);
                baseMapper.dynamicSql("ALTER TABLE t_reporting_table_"+query.getTableName()+"_extension_version MODIFY COLUMN "+columnSql);
            }
        };
        return baseMapper.deleteTableModelByIds(ids,SecurityUtils.getUsername());
    }

    @Override
    public List<TableModel> selectTableModelListByItem(Long itemId) {
        return baseMapper.selectTableModelListByItem(itemId);
    }

    @Override
    public int deleteTableModelById(Long id) {
        TableModel query = baseMapper.selectTableModelById(id);
        if(query.getFieldRequired().equals("0")){
            query.setFieldRequired("1");
            String columnSql = getColumnSql(query);
            baseMapper.dynamicSql("ALTER TABLE t_reporting_table_"+query.getTableName()+"_extension MODIFY COLUMN "+columnSql);
            baseMapper.dynamicSql("ALTER TABLE t_reporting_table_"+query.getTableName()+"_extension_version MODIFY COLUMN "+columnSql);
        }
        return baseMapper.deleteTableModelById(id,SecurityUtils.getUsername());
    }

}
