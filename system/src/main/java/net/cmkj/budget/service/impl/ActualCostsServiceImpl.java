package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.validation.Validator;
import net.cmkj.budget.domain.ActualCosts;
import net.cmkj.budget.domain.ActualCostsFile;
import net.cmkj.budget.domain.BudgetSubjectFinance;
import net.cmkj.budget.mapper.ActualCostsMapper;
import net.cmkj.budget.mapper.BudgetItemMapper;
import net.cmkj.budget.mapper.BudgetSubjectFinanceMapper;
import net.cmkj.budget.service.IActualCostsFileService;
import net.cmkj.budget.service.IActualCostsService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.exception.ServiceException;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.StringUtils;
import net.cmkj.common.utils.bean.BeanValidators;
import net.cmkj.system.mapper.SysDeptMapper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 实际费用Service业务层处理
 * 
 * @author cmkj
 * @date 2024-08-22
 */
@Service
public class ActualCostsServiceImpl extends ServiceImpl<ActualCostsMapper, ActualCosts> implements IActualCostsService
{

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private BudgetItemMapper itemMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private BudgetSubjectFinanceMapper subjectFinanceMapper;

    @Autowired
    private IActualCostsFileService actualCostsFileService;
    /**
     * 查询实际费用
     * 
     * @param id 实际费用主键
     * @return 实际费用
     */
    @Override
    public ActualCosts selectActualCostsById(Long id)
    {
        return baseMapper.selectById(id);
    }

    /**
     * 查询实际费用列表
     * 
     * @param actualCosts 实际费用
     * @return 实际费用
     */
    @Override
    public List<ActualCosts> selectActualCostsList(ActualCosts actualCosts)
    {
        return baseMapper.selectActualCostsList(actualCosts);
    }

    /**
     * 新增实际费用
     * 
     * @param actualCosts 实际费用
     * @return 结果
     */
    @Override
    public int insertActualCosts(ActualCosts actualCosts)
    {
        return baseMapper.insert(actualCosts);
    }

    /**
     * 修改实际费用
     * 
     * @param actualCosts 实际费用
     * @return 结果
     */
    @Override
    public int updateActualCosts(ActualCosts actualCosts)
    {
        return baseMapper.updateById(actualCosts);
    }

    /**
     * 批量删除实际费用
     * 
     * @param ids 需要删除的实际费用主键
     * @return 结果
     */
    @Override
    public int deleteActualCostsByIds(Long[] ids)
    {
        return baseMapper.deleteActualCostsByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除实际费用信息
     * 
     * @param id 实际费用主键
     * @return 结果
     */
    @Override
    public int deleteActualCostsById(Long id)
    {
        return baseMapper.deleteActualCostsById(id, SecurityUtils.getUsername());
    }

    @Override
    @Transactional
    public String importActualCosts(List<ActualCosts> actualCostsList, Boolean isUpdateSupport, String operName, ActualCostsFile actualCostsFile) {
        if (StringUtils.isNull(actualCostsList) || actualCostsList.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int index = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (ActualCosts actualCosts : actualCostsList) {
            index++;
            try
            {
                if(StringUtils.isEmpty(actualCosts.getZy()) || actualCosts.getZy().trim().equals("本期小计") || actualCosts.getZy().trim().equals("期间合计")){
                    continue;
                }
                if(actualCosts.getYear()==null || StringUtils.isEmpty(actualCosts.getDeptParentName().trim()) || StringUtils.isEmpty(actualCosts.getKjkmsm().trim())){
                    failureMsg.append("<br/>【年份："+actualCosts.getYear()+"】【所属公司："+actualCosts.getDeptParentName().trim()+"】【会计科目说明："+actualCosts.getKjkmsm().trim()+"】  必填数据不能为空！");
                    throw new RuntimeException();
                }
                if(actualCosts.getYear().toString().length()!=4 ){
                    failureMsg.append("<br/>【年份："+actualCosts.getYear()+"】 年份数据有误！");
                    throw new RuntimeException();
                }
                List<SysDept> parentDepts = deptMapper.selectPrentDeptByName(actualCosts.getDeptParentName().trim());
                if(parentDepts==null || parentDepts.size()!=1){
                    failureMsg.append("<br/>【所属公司："+actualCosts.getDeptParentName().trim()+"】 数据有误,关联公司信息失败！");
                    throw new RuntimeException();
                }
                Long deptId = null;
                if(StringUtils.isNotEmpty(actualCosts.getDeptName().trim())){
                    SysDept queryDept = new SysDept();
                    queryDept.setDeptName(actualCosts.getDeptName().trim());
                    queryDept.setParentId(parentDepts.get(0).getDeptId());
                    queryDept.setLevel(2);
                    List<SysDept> depts = deptMapper.selectDeptList(queryDept);
                    if(depts==null || depts.size()!=1){
                        failureMsg.append("<br/>【所属部门："+actualCosts.getDeptName().trim()+"】 数据有误，关联部门信息失败！");
                        throw new RuntimeException();
                    }
                    deptId = depts.get(0).getDeptId();
                }else{
                    Long parentDeptId = parentDepts.get(0).getDeptId();
                    if(parentDeptId.longValue()==100L){//郑州股份
                        deptId = 102L;//机关总部
                    }else if(parentDeptId.longValue()==101L){//郑州供气分
                        deptId = 111L;//管网运行部
                    }else if(parentDeptId.longValue()==116L){//车用燃气
                        deptId = 117L; //综合管理部
                    }else if(parentDeptId.longValue()==120L){//新郑加气站
                        deptId = 121L; //新郑加气站
                    }else if(parentDeptId.longValue()==122L){//润物能源
                        deptId = 123L; //综合管理部
                    }else if(parentDeptId.longValue()==126L){//登封
                        deptId = 127L; //安全综合部
                    }else if(parentDeptId.longValue()==133L){//港区
                        deptId = 134L; //综合管理部
                    }else if(parentDeptId.longValue()==139L){//东部
                        deptId = 140L; //市场综合部
                    }else if(parentDeptId.longValue()==144L){//中牟分
                        deptId = 145L; //综合部
                    }else if(parentDeptId.longValue()==146L){//新郑
                        deptId = 149L; //综合服务部
                    }else if(parentDeptId.longValue()==152L){//华鑫
                        deptId = 153L; //安全综合部
                    }else if(parentDeptId.longValue()==156L){//润然检测
                        deptId = 157L; //安全综合部
                    }else if(parentDeptId.longValue()==158L){//通许公司
                        deptId = 159L; //安全综合部
                    }else if(parentDeptId.longValue()==162L){//洛宁
                        deptId = 163L; //安全综合部
                    }else if(parentDeptId.longValue()==166L){//夏邑
                        deptId = 167L; //安全综合部
                    }else if(parentDeptId.longValue()==170L){//睢县
                        deptId = 171L; //安全综合部
                    }else if(parentDeptId.longValue()==174L){ //润控
                        deptId = 175L; //市场综合部
                    }else if(parentDeptId.longValue()==186L){ //金象
                        deptId = 186L;//金象
                    }
                }
                String Kjkmsm = actualCosts.getKjkmsm().trim().replaceAll("-+$", "");
                actualCosts.setKjkmsm(Kjkmsm);
                List<BudgetSubjectFinance> budgetSubjectFinances = subjectFinanceMapper.selectByFinancialName(Kjkmsm);
//                if(budgetSubjectFinances == null || budgetSubjectFinances.size()<1){
//                    failureMsg.append("<br/>【会计科目说明："+actualCosts.getKjkmsm().trim()+"】 数据有误，未查询到该财务科目关联的科目信息！");
//                    throw new RuntimeException();
//                }

                if(budgetSubjectFinances.size()>1){
                    failureMsg.append("<br/>【会计科目说明："+Kjkmsm+"】 数据有误，该财务科目已关联多个科目信息！");
                    throw new RuntimeException();
                }
                BeanValidators.validateWithException(validator, actualCosts);
                actualCosts.setSubjectId(budgetSubjectFinances.size()!=1?null:budgetSubjectFinances.get(0).getSubjectId());
                //actualCosts.setItemId(item.getId());
                actualCosts.setDeptId(deptId);
                actualCosts.setCreateBy(operName);
                actualCosts.setCreateTime(new Date());
                actualCosts.setFileId(actualCostsFile.getId());
                baseMapper.insertActualCosts(actualCosts);
                successNum++;
            }
            catch (Exception e)
            {
                log.error(e.getMessage(), e);
                failureMsg.insert(0, "很抱歉，导入失败！第"+index+"条数据格式不正确，错误如下：");
                throw new ServiceException(failureMsg.toString());
            }
        }
        actualCostsFile.setInfoNum(successNum);
        actualCostsFile.setDelFlag("0");
        actualCostsFileService.updateActualCostsFile(actualCostsFile);
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条;");
        return successMsg.toString();
    }

    /**
     * 导入数据
     * @param file
     * @param updateSupport
     * @param username
     * @param actualCostsFile
     * @return
     */
    @Override
    @Transactional
    public AjaxResult importActualCostsByFile(MultipartFile file, boolean updateSupport, String username, ActualCostsFile actualCostsFile) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }
        // 检查Excel文件头
        try (InputStream headerStream = file.getInputStream()) {
            byte[] header = new byte[4];
            int readBytes = headerStream.read(header);
            if (readBytes < 4 || (!isOLE2(header) && !isOOXML(header))) {
                throw new IllegalArgumentException("文件非有效的Excel格式");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("无法读取文件头", e);
        }
        String fileName = file.getOriginalFilename();
        int index = 0;
        int successNum = 0;
        int ignoreNum = 0;
        StringBuilder failureMsg = new StringBuilder();
        try (InputStream inputStream = file.getInputStream()) {
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            Row firstRow = sheet.getRow(0);
            SysDept parentDept = null;
            if (firstRow != null) {
                Cell companyCell = firstRow.getCell(0); // 公司名称单元格
                String companyName = formatter.formatCellValue(companyCell);
                List<SysDept> sysDepts = deptMapper.selectPrentDeptByName(companyName.trim());
                if(sysDepts==null || sysDepts.size()!=1){
                    return AjaxResult.error("【"+companyName+"】公司名称匹配失败，请查正后重试！");
                }
                parentDept = sysDepts.get(0);
            }else{
                return AjaxResult.error("获取公司名称失败！");
            }

            //财务科目绑定预算科目
            List<BudgetSubjectFinance> budgetSubjectFinances = subjectFinanceMapper.selectBudgetSubjectFinanceList(new BudgetSubjectFinance());

            int startRowIndex = 7;
            for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++) {
                index=i+1;
                Row row = sheet.getRow(i);
                if (isEmptyRow(row)) break; // 空行退出

                String zy = formatter.formatCellValue(row.getCell(7));
                if(StringUtils.isEmpty(zy) || zy.trim().equals("本期小计") || zy.trim().equals("期间合计")){
                    ignoreNum++;
                    continue;
                }

                /**
                 * 日期和财务科目说明为空同时跳过
                 */
                Cell cell1 = row.getCell(1); //日期
                Cell cell3 = row.getCell(3); //会计科目说明
                if (isCellEmpty(cell1) && isCellEmpty(cell3)) {
                    ignoreNum++;
                    continue;
                }

                ActualCosts costs = new ActualCosts();
                costs.setFileId(actualCostsFile.getId());
                try {
                    Date dateCellValue = row.getCell(1).getDateCellValue();
                    costs.setZzrq(dateCellValue);
                    Integer year = dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
                    costs.setYear(year);
                }catch (Exception e){
                    failureMsg.append("<br/>【总账日期：").append(formatter.formatCellValue(row.getCell(1))).append("】 数据有误，请查正后重试！");
                    throw new RuntimeException();
                }
                costs.setKjkm(formatter.formatCellValue(row.getCell(2)));
                String Kjkmsm = formatter.formatCellValue(row.getCell(3));
                if(StringUtils.isEmpty(Kjkmsm)){
                    failureMsg.append("<br/>【会计科目说明：").append(Kjkmsm).append("】 数据有误，请查正后重试！");
                    throw new RuntimeException();
                }else{
                    costs.setKjkmsm(Kjkmsm);
                    String[] split = Kjkmsm.split("\\.");
                    if(split.length!=2){
                        failureMsg.append("<br/>【会计科目说明：").append(Kjkmsm).append("】 截取部门信息失败，请查正后重试！");
                        throw new RuntimeException();
                    }

                    String deptName = split[0];
                    SysDept queryDept = new SysDept();
                    queryDept.setParentId(parentDept.getDeptId());
                    queryDept.setLevel(2);
                    List<SysDept> childrenDepts = deptMapper.selectDeptList(queryDept);

                    String matchDeptName = matchDeptName(deptName, parentDept);
                    if(StringUtils.isNotEmpty(matchDeptName)){
                        deptName = matchDeptName;
                    }

                    //规则匹配部门信息
                    List<SysDept> depts = getDept(childrenDepts, deptName);

                    if(depts.size() != 1){
                        failureMsg.append("<br/>【会计科目说明-部门：").append(deptName).append("】 信息匹配失败，请查正后重试！");
                        throw new RuntimeException();
                    }

                    costs.setDeptId(depts.get(0).getDeptId());
                    String financialName = split[1];
                    financialName = financialName.trim().replaceAll("-+$", "");

                    //过滤无用科目
                    if(financialName.startsWith("财务费用")){
                        ignoreNum++;
                        continue;
                    }

                    if (fileName != null && fileName.contains("费用")) {
                        if(financialName.contains("安全生产费")){
                            financialName = "安全生产费-安全生产费计提额";
                        }
                    }else{
                        if(financialName.contains("安全生产费-安全生产费补提额") || financialName.contains("安全生产费-安全生产费计提额")){
                            ignoreNum++;
                            continue;
                        }
                    }

                    if(!financialName.startsWith("研发")){
                        financialName = financialName.substring(financialName.indexOf('-') + 1);
                    }

                    //List<BudgetSubjectFinance> budgetSubjectFinances = subjectFinanceMapper.selectByFinancialName(financialName);
                    //规则匹配
                    List<BudgetSubjectFinance> budgetSubject = getBudgetSubject(budgetSubjectFinances, financialName);
                    if(budgetSubject.size()!=1){
                        failureMsg.append("<br/>【会计科目说明-财务科目：").append(financialName).append("】 信息匹配失败，请查正后重试！");
                        throw new RuntimeException();
                    }
                    costs.setSubjectId(budgetSubject.get(0).getSubjectId());
                }

                costs.setZy(zy);
                costs.setPzbh(formatter.formatCellValue(row.getCell(4)));
                costs.setLy(formatter.formatCellValue(row.getCell(5)));
                costs.setRjztsm(formatter.formatCellValue(row.getCell(6)));
                costs.setBz(formatter.formatCellValue(row.getCell(8)));
                costs.setActualIncurred(parseBigDecimal(formatter.formatCellValue(row.getCell(9))));
                costs.setBwbdf(parseBigDecimal(formatter.formatCellValue(row.getCell(10))));
                costs.setSlzj(parseBigDecimal(formatter.formatCellValue(row.getCell(11))));
                costs.setSljs(parseBigDecimal(formatter.formatCellValue(row.getCell(12))));
                costs.setFx(formatter.formatCellValue(row.getCell(13)));
                costs.setEstimatedIncurred(parseBigDecimal(formatter.formatCellValue(row.getCell(14))));
                costs.setSlye(parseBigDecimal(formatter.formatCellValue(row.getCell(15))));
                costs.setCreateBy(username);
                costs.setCreateTime(new Date());
                baseMapper.insertActualCosts(costs);
                successNum++;
            }
            actualCostsFile.setInfoNum(successNum);
            actualCostsFile.setDelFlag("0");
            actualCostsFileService.updateActualCostsFile(actualCostsFile);
            return AjaxResult.success("成功导入" + successNum + "条数据，忽略"+ignoreNum+"条数据");
        }catch (Exception e)
        {
            log.error("导入失败："+e.getMessage(), e);
            failureMsg.insert(0, "很抱歉，导入失败！第"+index+"条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
    }

    private List<BudgetSubjectFinance> getBudgetSubject(List<BudgetSubjectFinance> budgetSubjectFinances, String financialName) {
        String currentName = financialName;
        while (true) {
            List<BudgetSubjectFinance> result = new ArrayList<>();
            for (BudgetSubjectFinance bsf : budgetSubjectFinances) {
                if (currentName.equals(bsf.getFinancelSubjectName())) {
                    result.add(bsf);
                }
            }
            if (!result.isEmpty()){
                return result;
            }
            // 截取最后"-"
            int lastDashIndex = currentName.lastIndexOf("-");
            if (lastDashIndex == -1) {
                // 大类拼接"-其他"匹配
                String otherName = currentName + "-其他";
                for (BudgetSubjectFinance bsf : budgetSubjectFinances) {
                    if (otherName.equals(bsf.getFinancelSubjectName())) {
                        result.add(bsf);
                    }
                }
                return result;
            }
            currentName = currentName.substring(0, lastDashIndex);
        }
    }


    /**
     * 依次截取匹配部门
     * @param depts
     * @param deptName
     * @return
     */
    private List<SysDept> getDept(List<SysDept> depts, String deptName) {
        String currentName = deptName;
        while (true) {
            List<SysDept> result = new ArrayList<>();
            for (SysDept dept : depts) {
                if (currentName.equals(dept.getDeptName())) {
                    result.add(dept);
                }
            }
            if (!result.isEmpty()){
                return result;
            }

            int lastDashIndex = currentName.lastIndexOf("-");
            if (lastDashIndex == -1) {
                return result;
            }
            currentName = currentName.substring(0, lastDashIndex);
        }
    }
    /**
     * 将字符串转换为BigDecimal
     * @param numStr
     * @return
     */
    private BigDecimal parseBigDecimal(String numStr) {
        if (StringUtils.isBlank(numStr)) return null;
        return new BigDecimal(numStr.replace(",", ""));
    }

    /**
     * 判断某一行是否为空行
     * @return true表示该行为空，false表示该行包含数据
     */
    private static boolean isEmptyRow(Row row) {
        if (row == null) {
            return true; // 如果行本身为null，认为是空行
        }
        // 遍历该行的所有单元格
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null && !isCellEmpty(cell)) {
                return false; // 只要有一个单元格非空，则该行不为空
            }
        }
        return true; // 所有单元格均为空
    }

    /**
     * 判断某个单元格是否为空
     * @return true表示单元格为空，false表示单元格包含数据
     */
    private static boolean isCellEmpty(Cell cell) {
        if (cell == null) {
            return true;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim().isEmpty(); // 字符串类型，判断是否为空白
            case NUMERIC:
                return false; // 数值类型，认为非空
            case BOOLEAN:
                return false; // 布尔类型，认为非空
            case FORMULA:
                return false; // 公式类型，认为非空
            case BLANK:
                return true; // 空白类型，认为空白
            case ERROR:
                return false; // 错误类型，认为非空
            default:
                return true; // 其他情况，默认为空
        }
    }

    private String matchDeptName(String name, SysDept parentDept) {
        String deptName = "";
        if (StringUtils.isBlank(name.trim())) return deptName;
        Long deptId = parentDept.getDeptId();
        if(deptId == 100){//郑州股份
            if(name.equals("默认")|| name.equals("外派")|| name.equals("研发部")|| name.equals("维修中心")|| name.equals("管网部机关")|| name.equals("客户服务部机关")|| name.equals("郑州审计中心") || name.equals("总部纪委派驻郑州纪检组")){
                deptName = "机关总部";
            }else if(name.equals("郑州华润燃气技术创新研究院")){
                deptName = "技术创新研究院";
            }else if(name.equals("物资仓库")){
                deptName = "物资供应部";
            }else if(name.equals("客户服务部增值服务中心")){
                deptName = "增值中心";
            }
        }else if(deptId == 101){ //郑州供气分
            if (name.equals("客户服务部增值服务中心")|| name.equals("客户服务部机关")|| name.equals("客户服务部-安全管理室")){
                deptName = "客户服务部";
            }else if (name.equals("调度中心调度室")|| name.equals("调度中心机关")){
                deptName = "调度中心";
            }else if (name.equals("管网部机关")|| name.equals("默认")|| name.equals("管网运行部-安技管理室")){
                deptName = "管网运行部";
            }else if (name.equals("检定室")){
                deptName = "检测中心";
            }
        }
        return deptName;
    }

    private boolean isOLE2(byte[] header) {
        return header[0] == (byte)0xD0 && header[1] == (byte)0xCF &&
                header[2] == (byte)0x11 && header[3] == (byte)0xE0;
    }

    private boolean isOOXML(byte[] header) {
        return header[0] == (byte)0x50 && header[1] == (byte)0x4B &&
                header[2] == (byte)0x03 && header[3] == (byte)0x04;
    }

    @Override
    public ActualCosts selectLast(ActualCosts queryActualCosts) {
        return baseMapper.selectLast(queryActualCosts);
    }

    @Override
    public List<ActualCosts> selectActualCostsCountList(ActualCosts queryActualCosts) {
        return baseMapper.selectActualCostsCountList(queryActualCosts);
    }

    @Override
    public List<Map<String, Object>> selectTotals(Integer budgetYear) {
        return baseMapper.selectTotals(budgetYear);
    }


}
