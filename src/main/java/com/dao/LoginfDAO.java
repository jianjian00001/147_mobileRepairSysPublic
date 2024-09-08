package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.Loginf;

@Repository("loginfDAO") // Repository标签定义数据库连接的访问 Spring中直接扫描加载
@Mapper // 不需要在spring配置中设置扫描地址 spring将动态的生成Bean后注入到LoginfServiceImpl中
public interface LoginfDAO {


	/**
	* LoginfDAO 接口 可以按名称直接调用loginf.xml配置文件的SQL语句
	*/


	// 插入登录日志表数据 调用mapper包loginf.xml里的insertLoginf配置 返回值0(失败),1(成功)
	public int insertLoginf(Loginf loginf);

	// 更新登录日志表数据 调用mapper包loginf.xml里的updateLoginf配置 返回值0(失败),1(成功)
	public int updateLoginf(Loginf loginf);

	// 按主键删除登录日志表数据 调用mapper包loginf.xml里的deleteLoginf配置 返回值0(失败),1(成功)
	public int deleteLoginf(String loginfid);

	// 批量删除登录日志表数据 调用mapper包loginf.xml里的deleteLoginfByIds配置 返回值0(失败),大于0(成功)
	public int deleteLoginfByIds(String[] ids);

	// 查询登录日志表全部数据 调用mapper包loginf.xml里的getAllLoginf配置 返回List<Loginf>类型的数据
	public List<Loginf> getAllLoginf();

	// 按照Loginf类里面的值精确查询 调用mapper包loginf.xml里的getLoginfByCond配置 返回List<Loginf>类型的数据
	public List<Loginf> getLoginfByCond(Loginf loginf);

	// 按照Loginf类里面的值模糊查询 调用mapper包loginf.xml里的getLoginfByLike配置 返回List<Loginf>类型的数据
	public List<Loginf> getLoginfByLike(Loginf loginf);

	// 按主键查询登录日志表返回单一的Loginf实例 调用mapper包loginf.xml里的getLoginfById配置
	public Loginf getLoginfById(String loginfid);

}


