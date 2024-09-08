package com.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Phone;
@Service("phoneService") // 自动注册到Spring容器，不需要再在xml文件定义bean
public interface PhoneService {

	// 插入手机表数据 调用phoneDAO里的insertPhone配置
	public int insertPhone(Phone phone);

	// 更新手机表数据 调用phoneDAO里的updatePhone配置
	public int updatePhone(Phone phone);

	// 按主键删除手机表数据 调用phoneDAO里的deletePhone配置
	public int deletePhone(String phoneid);

	// 批量删除手机表数据 调用mapper包phone.xml里的deletePhoneByIds配置 返回值0(失败),大于0(成功)
	public int deletePhoneByIds(String[] ids);

	// 查询全部数据 调用phoneDAO里的getAllPhone配置
	public List<Phone> getAllPhone();

	// 按照Phone类里面的字段名称精确查询 调用phoneDAO里的getPhoneByCond配置
	public List<Phone> getPhoneByCond(Phone phone);

	// 按照Phone类里面的字段名称模糊查询 调用phoneDAO里的getPhoneByLike配置
	public List<Phone> getPhoneByLike(Phone phone);

	// 按主键查询表返回单一的Phone实例 调用phoneDAO里的getPhoneById配置
	public Phone getPhoneById(String phoneid);

}

