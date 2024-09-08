package com.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Operlog;
@Service("operlogService") // 自动注册到Spring容器，不需要再在xml文件定义bean
public interface OperlogService {

	// 插入操作日志表数据 调用operlogDAO里的insertOperlog配置
	public int insertOperlog(Operlog operlog);

	// 更新操作日志表数据 调用operlogDAO里的updateOperlog配置
	public int updateOperlog(Operlog operlog);

	// 按主键删除操作日志表数据 调用operlogDAO里的deleteOperlog配置
	public int deleteOperlog(String operlogid);

	// 批量删除操作日志表数据 调用mapper包operlog.xml里的deleteOperlogByIds配置 返回值0(失败),大于0(成功)
	public int deleteOperlogByIds(String[] ids);

	// 查询全部数据 调用operlogDAO里的getAllOperlog配置
	public List<Operlog> getAllOperlog();

	// 按照Operlog类里面的字段名称精确查询 调用operlogDAO里的getOperlogByCond配置
	public List<Operlog> getOperlogByCond(Operlog operlog);

	// 按照Operlog类里面的字段名称模糊查询 调用operlogDAO里的getOperlogByLike配置
	public List<Operlog> getOperlogByLike(Operlog operlog);

	// 按主键查询表返回单一的Operlog实例 调用operlogDAO里的getOperlogById配置
	public Operlog getOperlogById(String operlogid);

}

