package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.cmkj.budget.domain.ActualCostsFile;
import net.cmkj.budget.mapper.ActualCostsFileMapper;
import net.cmkj.budget.mapper.ActualCostsMapper;
import net.cmkj.budget.service.IActualCostsFileService;
import net.cmkj.common.config.CmkjConfig;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
/**
 * 实际费用文件Service业务层处理
 *
 * @author cmkj
 * @date 2025-01-21
 */
@Service
public class ActualCostsFileServiceImpl extends ServiceImpl<ActualCostsFileMapper, ActualCostsFile> implements IActualCostsFileService
{

    @Autowired
    private ActualCostsMapper actualCostsMapper;

    /**
     * 查询实际费用文件
     *
     * @param id 实际费用文件主键
     * @return 实际费用文件
     */
    @Override
    public ActualCostsFile selectActualCostsFileById(Long id)
    {
        return baseMapper.selectActualCostsFileById(id);
    }

    /**
     * 查询实际费用文件列表
     *
     * @param actualCostsFile 实际费用文件
     * @return 实际费用文件
     */
    @Override
    public List<ActualCostsFile> selectActualCostsFileList(ActualCostsFile actualCostsFile)
    {
        return baseMapper.selectActualCostsFileList(actualCostsFile);
    }

    /**
     * 新增实际费用文件
     *
     * @param actualCostsFile 实际费用文件
     * @return 结果
     */
    @Override
    public int insertActualCostsFile(ActualCostsFile actualCostsFile)
    {
        actualCostsFile.setCreateBy(SecurityUtils.getUsername());
        actualCostsFile.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertActualCostsFile(actualCostsFile);
    }

    /**
     * 修改实际费用文件
     *
     * @param actualCostsFile 实际费用文件
     * @return 结果
     */
    @Override
    public int updateActualCostsFile(ActualCostsFile actualCostsFile)
    {
        return baseMapper.updateActualCostsFile(actualCostsFile);
    }

    /**
     * 批量删除实际费用文件
     * 
     * @param ids 需要删除的实际费用文件主键
     * @return 结果
     */
    @Override
    public int deleteActualCostsFileByIds(Long[] ids)
    {

        return baseMapper.deleteActualCostsFileByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除实际费用文件信息
     * 
     * @param id 实际费用文件主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteActualCostsFileById(Long id)
    {
        String path = CmkjConfig.getUploadPath();
        ActualCostsFile actualCostsFile = baseMapper.selectActualCostsFileById(id);
        String filePath = actualCostsFile.getFilePath().replace("/profile/upload", path);
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        actualCostsMapper.deleteActualCostsByFileId(id, SecurityUtils.getUsername());
        return baseMapper.deleteActualCostsFileById(id, SecurityUtils.getUsername());
    }
}
