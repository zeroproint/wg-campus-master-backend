package com.wg.order.service.impl;

import com.wg.order.domain.po.Tags;
import com.wg.order.mapper.TagsMapper;
import com.wg.order.service.ITagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * tag表 服务实现类
 * </p>
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

}
