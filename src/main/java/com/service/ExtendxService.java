package com.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Extendx;
@Service("extendxService") // 自动注册到Spring容器，不需要再在xml文件定义bean
public interface ExtendxService {

	// 插入配件消耗表数据 调用extendxDAO里的insertExtendx配置
	public int insertExtendx(Extendx extendx);

	// 更新配件消耗表数据 调用extendxDAO里的updateExtendx配置
	public int updateExtendx(Extendx extendx);

	// 按主键删除配件消耗表数据 调用extendxDAO里的deleteExtendx配置
	public int deleteExtendx(String extendxid);

	// 批量删除配件消耗表数据 调用mapper包extendx.xml里的deleteExtendxByIds配置 返回值0(失败),大于0(成功)
	public int deleteExtendxByIds(String[] ids);

	// 查询全部数据 调用extendxDAO里的getAllExtendx配置
	public List<Extendx> getAllExtendx();

	// 按照Extendx类里面的字段名称精确查询 调用extendxDAO里的getExtendxByCond配置
	public List<Extendx> getExtendxByCond(Extendx extendx);

	// 按照Extendx类里面的字段名称模糊查询 调用extendxDAO里的getExtendxByLike配置
	public List<Extendx> getExtendxByLike(Extendx extendx);

	// 按主键查询表返回单一的Extendx实例 调用extendxDAO里的getExtendxById配置
	public Extendx getExtendxById(String extendxid);

}

