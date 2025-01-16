package cn.px.tea.modules.ycb.service.table.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.px.tea.modules.ycb.mapper.SysEmpMapper;
import cn.px.tea.modules.ycb.model.SysEmp;
import cn.px.tea.modules.ycb.service.table.intel.SysEmpService;
import cn.px.tea.security.define.SystemDefine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysEmpServiceImpl extends ServiceImpl<SysEmpMapper, SysEmp> implements SysEmpService {

    @Override
    public List<SysEmp> listByEmp(List<Long> listEmpId, String select, Integer status) {
        LambdaQueryWrapper<SysEmp> queryWrapper = new LambdaQueryWrapper<>();
        if (listEmpId != null && listEmpId.size() > 0) {
            queryWrapper.in(SysEmp::getEmpId, listEmpId);
        }
        if (StringUtils.isNotBlank(select)) {
            queryWrapper.and(wrapper -> wrapper.like(SysEmp::getEmpNo, select).or().like(SysEmp::getPhone, select).or().like(SysEmp::getCardName, select));
        }
//        if (status != null) {
//            queryWrapper.eq(SysEmp::getStatus, status);
//        }
        queryWrapper.ne(SysEmp::getEmpId, SystemDefine.getRoleId());
        return list(queryWrapper);
    }

    @Override
    public SysEmp getByEmp(Long empId) {
        LambdaQueryWrapper<SysEmp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmp::getEmpId, empId);
        return getOne(queryWrapper);
    }

    @Override
    public List<SysEmp> listByEmp(List<Long> listEmpId) {
        LambdaQueryWrapper<SysEmp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysEmp::getEmpId, listEmpId);
        return list(queryWrapper);
    }

    @Override
    public SysEmp getByPhone(String phone) {
        LambdaQueryWrapper<SysEmp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmp::getPhone, phone);
        return getOne(queryWrapper);
    }

    @Override
    public SysEmp getByEmpNo(String empNo) {
        LambdaQueryWrapper<SysEmp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmp::getEmpNo, empNo);
        return getOne(queryWrapper);
    }

    @Override
    public SysEmp getByCardId(String cardId) {
        LambdaQueryWrapper<SysEmp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmp::getCardId, cardId);
        return getOne(queryWrapper);
    }
}




