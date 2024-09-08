package com.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Topic;
@Service("topicService") // 自动注册到Spring容器，不需要再在xml文件定义bean
public interface TopicService {

	// 插入用户评价表数据 调用topicDAO里的insertTopic配置
	public int insertTopic(Topic topic);

	// 更新用户评价表数据 调用topicDAO里的updateTopic配置
	public int updateTopic(Topic topic);

	// 按主键删除用户评价表数据 调用topicDAO里的deleteTopic配置
	public int deleteTopic(String topicid);

	// 批量删除用户评价表数据 调用mapper包topic.xml里的deleteTopicByIds配置 返回值0(失败),大于0(成功)
	public int deleteTopicByIds(String[] ids);

	// 查询全部数据 调用topicDAO里的getAllTopic配置
	public List<Topic> getAllTopic();

	// 按照Topic类里面的字段名称精确查询 调用topicDAO里的getTopicByCond配置
	public List<Topic> getTopicByCond(Topic topic);

	// 按照Topic类里面的字段名称模糊查询 调用topicDAO里的getTopicByLike配置
	public List<Topic> getTopicByLike(Topic topic);

	// 按主键查询表返回单一的Topic实例 调用topicDAO里的getTopicById配置
	public Topic getTopicById(String topicid);

}

