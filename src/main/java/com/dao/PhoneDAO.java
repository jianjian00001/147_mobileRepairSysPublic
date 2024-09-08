package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.Phone;

@Repository("phoneDAO") // Repository标签定义数据库连接的访问 Spring中直接扫描加载
@Mapper // 不需要在spring配置中设置扫描地址 spring将动态的生成Bean后注入到PhoneServiceImpl中
public interface PhoneDAO {


	/**
	* PhoneDAO 接口 可以按名称直接调用phone.xml配置文件的SQL语句
	*/


	// 插入手机表数据 调用mapper包phone.xml里的insertPhone配置 返回值0(失败),1(成功)
	public int insertPhone(Phone phone);

	// 更新手机表数据 调用mapper包phone.xml里的updatePhone配置 返回值0(失败),1(成功)
	public int updatePhone(Phone phone);

	// 按主键删除手机表数据 调用mapper包phone.xml里的deletePhone配置 返回值0(失败),1(成功)
	public int deletePhone(String phoneid);

	// 批量删除手机表数据 调用mapper包phone.xml里的deletePhoneByIds配置 返回值0(失败),大于0(成功)
	public int deletePhoneByIds(String[] ids);

	// 查询手机表全部数据 调用mapper包phone.xml里的getAllPhone配置 返回List<Phone>类型的数据
	public List<Phone> getAllPhone();

	// 按照Phone类里面的值精确查询 调用mapper包phone.xml里的getPhoneByCond配置 返回List<Phone>类型的数据
	public List<Phone> getPhoneByCond(Phone phone);

	// 按照Phone类里面的值模糊查询 调用mapper包phone.xml里的getPhoneByLike配置 返回List<Phone>类型的数据
	public List<Phone> getPhoneByLike(Phone phone);

	// 按主键查询手机表返回单一的Phone实例 调用mapper包phone.xml里的getPhoneById配置
	public Phone getPhoneById(String phoneid);

}


