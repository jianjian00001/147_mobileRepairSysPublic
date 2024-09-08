package com.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.TopicDAO;
import com.entity.Topic;
import com.service.TopicService;

@Service("topicService") //
public class TopicServiceImpl implements TopicService { // TopicServiceImpl 实现了 TopicService接口

	@Autowired // 它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作
	private TopicDAO topicDAO; // 实现Spring的AOP依赖注入 自动注入AdminDAO接口

	@Override // 继承接口的新增用户评价表数据 返回值0(失败),1(成功)
	public int insertTopic(Topic topic) {
		return this.topicDAO.insertTopic(topic);
	}

	@Override // 继承接口的更新用户评价表数据 返回值0(失败),1(成功)
	public int updateTopic(Topic topic) {
		return this.topicDAO.updateTopic(topic);
	}

	@Override // 继承接口的按主键删除用户评价表数据 返回值0(失败),1(成功)
	public int deleteTopic(String topicid) {
		return this.topicDAO.deleteTopic(topicid);
	}

	@Override // 继承接口的批量删除用户评价表数据 返回值0(失败),大于0(成功)
	public int deleteTopicByIds(String[] ids) {
		return this.topicDAO.deleteTopicByIds(ids);
	}

	@Override // 继承接口的查询用户评价表全部数据
	public List<Topic> getAllTopic() {
		return this.topicDAO.getAllTopic();
	}

	@Override // 继承接口的按条件精确查询用户评价表数据
	public List<Topic> getTopicByCond(Topic topic) {
		return this.topicDAO.getTopicByCond(topic);
	}

	@Override // 继承接口的按条件模糊查询用户评价表数据
	public List<Topic> getTopicByLike(Topic topic) {
		return this.topicDAO.getTopicByLike(topic);
	}

	@Override // 继承接口的按主键查询用户评价表数据 返回Entity实例
	public Topic getTopicById(String topicid) {
		return this.topicDAO.getTopicById(topicid);
	}

}

