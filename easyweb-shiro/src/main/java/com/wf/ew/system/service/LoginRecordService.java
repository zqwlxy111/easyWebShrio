package com.wf.ew.system.service;

import com.wf.ew.common.PageParam;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.model.LoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
public interface LoginRecordService extends IService<LoginRecord> {

    PageResult<LoginRecord> listFull(PageParam page);

}
