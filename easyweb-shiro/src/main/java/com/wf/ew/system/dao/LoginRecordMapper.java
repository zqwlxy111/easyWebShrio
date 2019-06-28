package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wf.ew.common.PageParam;
import com.wf.ew.system.model.LoginRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
public interface LoginRecordMapper extends BaseMapper<LoginRecord> {

    List<LoginRecord> listFull(@Param("page") PageParam page);

}
