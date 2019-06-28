package com.wf.ew.system.service.impl;

import com.wf.ew.common.PageParam;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.model.LoginRecord;
import com.wf.ew.system.dao.LoginRecordMapper;
import com.wf.ew.system.service.LoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
@Service
public class LoginRecordServiceImpl extends ServiceImpl<LoginRecordMapper, LoginRecord> implements LoginRecordService {

    @Override
    public PageResult<LoginRecord> listFull(PageParam pageParam) {
        List<LoginRecord> records = baseMapper.listFull(pageParam);
        return new PageResult<>(records, pageParam.getTotal());
    }
}
