package com.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Loginf;
@Service("loginfService") // 自动注册到Spring容器，不需要再在xml文件定义bean
public interface LoginfService {

	// 插入登录日志表数据 调用loginfDAO里的insertLoginf配置
	public int insertLoginf(Loginf loginf);

	// 更新登录日志表数据 调用loginfDAO里的updateLoginf配置
	public int updateLoginf(Loginf loginf);

	// 按主键删除登录日志表数据 调用loginfDAO里的deleteLoginf配置
	public int deleteLoginf(String loginfid);

	// 批量删除登录日志表数据 调用mapper包loginf.xml里的deleteLoginfByIds配置 返回值0(失败),大于0(成功)
	public int deleteLoginfByIds(String[] ids);

	// 查询全部数据 调用loginfDAO里的getAllLoginf配置
	public List<Loginf> getAllLoginf();

	// 按照Loginf类里面的字段名称精确查询 调用loginfDAO里的getLoginfByCond配置
	public List<Loginf> getLoginfByCond(Loginf loginf);

	// 按照Loginf类里面的字段名称模糊查询 调用loginfDAO里的getLoginfByLike配置
	public List<Loginf> getLoginfByLike(Loginf loginf);

	// 按主键查询表返回单一的Loginf实例 调用loginfDAO里的getLoginfById配置
	public Loginf getLoginfById(String loginfid);

}

