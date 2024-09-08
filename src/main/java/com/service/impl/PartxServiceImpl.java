package com.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.PartxDAO;
import com.entity.Partx;
import com.service.PartxService;

@Service("partxService") //
public class PartxServiceImpl implements PartxService { // PartxServiceImpl 实现了 PartxService接口

	@Autowired // 它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作
	private PartxDAO partxDAO; // 实现Spring的AOP依赖注入 自动注入AdminDAO接口

	@Override // 继承接口的新增手机配件表数据 返回值0(失败),1(成功)
	public int insertPartx(Partx partx) {
		return this.partxDAO.insertPartx(partx);
	}

	@Override // 继承接口的更新手机配件表数据 返回值0(失败),1(成功)
	public int updatePartx(Partx partx) {
		return this.partxDAO.updatePartx(partx);
	}

	@Override // 继承接口的按主键删除手机配件表数据 返回值0(失败),1(成功)
	public int deletePartx(String partxid) {
		return this.partxDAO.deletePartx(partxid);
	}

	@Override // 继承接口的批量删除手机配件表数据 返回值0(失败),大于0(成功)
	public int deletePartxByIds(String[] ids) {
		return this.partxDAO.deletePartxByIds(ids);
	}

	@Override // 继承接口的查询手机配件表全部数据
	public List<Partx> getAllPartx() {
		return this.partxDAO.getAllPartx();
	}

	@Override // 继承接口的按条件精确查询手机配件表数据
	public List<Partx> getPartxByCond(Partx partx) {
		return this.partxDAO.getPartxByCond(partx);
	}

	@Override // 继承接口的按条件模糊查询手机配件表数据
	public List<Partx> getPartxByLike(Partx partx) {
		return this.partxDAO.getPartxByLike(partx);
	}

	@Override // 继承接口的按主键查询手机配件表数据 返回Entity实例
	public Partx getPartxById(String partxid) {
		return this.partxDAO.getPartxById(partxid);
	}

}

