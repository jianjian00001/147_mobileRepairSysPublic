package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.Operlog;

@Repository("operlogDAO") // Repository标签定义数据库连接的访问 Spring中直接扫描加载
@Mapper // 不需要在spring配置中设置扫描地址 spring将动态的生成Bean后注入到OperlogServiceImpl中
public interface OperlogDAO {


	/**
	* OperlogDAO 接口 可以按名称直接调用operlog.xml配置文件的SQL语句
	*/


	// 插入操作日志表数据 调用mapper包operlog.xml里的insertOperlog配置 返回值0(失败),1(成功)
	public int insertOperlog(Operlog operlog);

	// 更新操作日志表数据 调用mapper包operlog.xml里的updateOperlog配置 返回值0(失败),1(成功)
	public int updateOperlog(Operlog operlog);

	// 按主键删除操作日志表数据 调用mapper包operlog.xml里的deleteOperlog配置 返回值0(失败),1(成功)
	public int deleteOperlog(String operlogid);

	// 批量删除操作日志表数据 调用mapper包operlog.xml里的deleteOperlogByIds配置 返回值0(失败),大于0(成功)
	public int deleteOperlogByIds(String[] ids);

	// 查询操作日志表全部数据 调用mapper包operlog.xml里的getAllOperlog配置 返回List<Operlog>类型的数据
	public List<Operlog> getAllOperlog();

	// 按照Operlog类里面的值精确查询 调用mapper包operlog.xml里的getOperlogByCond配置 返回List<Operlog>类型的数据
	public List<Operlog> getOperlogByCond(Operlog operlog);

	// 按照Operlog类里面的值模糊查询 调用mapper包operlog.xml里的getOperlogByLike配置 返回List<Operlog>类型的数据
	public List<Operlog> getOperlogByLike(Operlog operlog);

	// 按主键查询操作日志表返回单一的Operlog实例 调用mapper包operlog.xml里的getOperlogById配置
	public Operlog getOperlogById(String operlogid);

}


