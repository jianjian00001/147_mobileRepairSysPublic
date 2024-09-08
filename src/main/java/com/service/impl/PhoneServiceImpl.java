package com.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.PhoneDAO;
import com.entity.Phone;
import com.service.PhoneService;

@Service("phoneService") //
public class PhoneServiceImpl implements PhoneService { // PhoneServiceImpl 实现了 PhoneService接口

	@Autowired // 它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作
	private PhoneDAO phoneDAO; // 实现Spring的AOP依赖注入 自动注入AdminDAO接口

	@Override // 继承接口的新增手机表数据 返回值0(失败),1(成功)
	public int insertPhone(Phone phone) {
		return this.phoneDAO.insertPhone(phone);
	}

	@Override // 继承接口的更新手机表数据 返回值0(失败),1(成功)
	public int updatePhone(Phone phone) {
		return this.phoneDAO.updatePhone(phone);
	}

	@Override // 继承接口的按主键删除手机表数据 返回值0(失败),1(成功)
	public int deletePhone(String phoneid) {
		return this.phoneDAO.deletePhone(phoneid);
	}

	@Override // 继承接口的批量删除手机表数据 返回值0(失败),大于0(成功)
	public int deletePhoneByIds(String[] ids) {
		return this.phoneDAO.deletePhoneByIds(ids);
	}

	@Override // 继承接口的查询手机表全部数据
	public List<Phone> getAllPhone() {
		return this.phoneDAO.getAllPhone();
	}

	@Override // 继承接口的按条件精确查询手机表数据
	public List<Phone> getPhoneByCond(Phone phone) {
		return this.phoneDAO.getPhoneByCond(phone);
	}

	@Override // 继承接口的按条件模糊查询手机表数据
	public List<Phone> getPhoneByLike(Phone phone) {
		return this.phoneDAO.getPhoneByLike(phone);
	}

	@Override // 继承接口的按主键查询手机表数据 返回Entity实例
	public Phone getPhoneById(String phoneid) {
		return this.phoneDAO.getPhoneById(phoneid);
	}

}

