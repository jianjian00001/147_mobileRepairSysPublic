package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.Topic;

@Repository("topicDAO") // Repository标签定义数据库连接的访问 Spring中直接扫描加载
@Mapper // 不需要在spring配置中设置扫描地址 spring将动态的生成Bean后注入到TopicServiceImpl中
public interface TopicDAO {


	/**
	* TopicDAO 接口 可以按名称直接调用topic.xml配置文件的SQL语句
	*/


	// 插入用户评价表数据 调用mapper包topic.xml里的insertTopic配置 返回值0(失败),1(成功)
	public int insertTopic(Topic topic);

	// 更新用户评价表数据 调用mapper包topic.xml里的updateTopic配置 返回值0(失败),1(成功)
	public int updateTopic(Topic topic);

	// 按主键删除用户评价表数据 调用mapper包topic.xml里的deleteTopic配置 返回值0(失败),1(成功)
	public int deleteTopic(String topicid);

	// 批量删除用户评价表数据 调用mapper包topic.xml里的deleteTopicByIds配置 返回值0(失败),大于0(成功)
	public int deleteTopicByIds(String[] ids);

	// 查询用户评价表全部数据 调用mapper包topic.xml里的getAllTopic配置 返回List<Topic>类型的数据
	public List<Topic> getAllTopic();

	// 按照Topic类里面的值精确查询 调用mapper包topic.xml里的getTopicByCond配置 返回List<Topic>类型的数据
	public List<Topic> getTopicByCond(Topic topic);

	// 按照Topic类里面的值模糊查询 调用mapper包topic.xml里的getTopicByLike配置 返回List<Topic>类型的数据
	public List<Topic> getTopicByLike(Topic topic);

	// 按主键查询用户评价表返回单一的Topic实例 调用mapper包topic.xml里的getTopicById配置
	public Topic getTopicById(String topicid);

}


