package com.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Partx;
@Service("partxService") // 自动注册到Spring容器，不需要再在xml文件定义bean
public interface PartxService {

	// 插入手机配件表数据 调用partxDAO里的insertPartx配置
	public int insertPartx(Partx partx);

	// 更新手机配件表数据 调用partxDAO里的updatePartx配置
	public int updatePartx(Partx partx);

	// 按主键删除手机配件表数据 调用partxDAO里的deletePartx配置
	public int deletePartx(String partxid);

	// 批量删除手机配件表数据 调用mapper包partx.xml里的deletePartxByIds配置 返回值0(失败),大于0(成功)
	public int deletePartxByIds(String[] ids);

	// 查询全部数据 调用partxDAO里的getAllPartx配置
	public List<Partx> getAllPartx();

	// 按照Partx类里面的字段名称精确查询 调用partxDAO里的getPartxByCond配置
	public List<Partx> getPartxByCond(Partx partx);

	// 按照Partx类里面的字段名称模糊查询 调用partxDAO里的getPartxByLike配置
	public List<Partx> getPartxByLike(Partx partx);

	// 按主键查询表返回单一的Partx实例 调用partxDAO里的getPartxById配置
	public Partx getPartxById(String partxid);

}

