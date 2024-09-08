package com.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.OperlogDAO;
import com.entity.Operlog;
import com.service.OperlogService;

@Service("operlogService") //
public class OperlogServiceImpl implements OperlogService { // OperlogServiceImpl 实现了 OperlogService接口

	@Autowired // 它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作
	private OperlogDAO operlogDAO; // 实现Spring的AOP依赖注入 自动注入AdminDAO接口

	@Override // 继承接口的新增操作日志表数据 返回值0(失败),1(成功)
	public int insertOperlog(Operlog operlog) {
		return this.operlogDAO.insertOperlog(operlog);
	}

	@Override // 继承接口的更新操作日志表数据 返回值0(失败),1(成功)
	public int updateOperlog(Operlog operlog) {
		return this.operlogDAO.updateOperlog(operlog);
	}

	@Override // 继承接口的按主键删除操作日志表数据 返回值0(失败),1(成功)
	public int deleteOperlog(String operlogid) {
		return this.operlogDAO.deleteOperlog(operlogid);
	}

	@Override // 继承接口的批量删除操作日志表数据 返回值0(失败),大于0(成功)
	public int deleteOperlogByIds(String[] ids) {
		return this.operlogDAO.deleteOperlogByIds(ids);
	}

	@Override // 继承接口的查询操作日志表全部数据
	public List<Operlog> getAllOperlog() {
		return this.operlogDAO.getAllOperlog();
	}

	@Override // 继承接口的按条件精确查询操作日志表数据
	public List<Operlog> getOperlogByCond(Operlog operlog) {
		return this.operlogDAO.getOperlogByCond(operlog);
	}

	@Override // 继承接口的按条件模糊查询操作日志表数据
	public List<Operlog> getOperlogByLike(Operlog operlog) {
		return this.operlogDAO.getOperlogByLike(operlog);
	}

	@Override // 继承接口的按主键查询操作日志表数据 返回Entity实例
	public Operlog getOperlogById(String operlogid) {
		return this.operlogDAO.getOperlogById(operlogid);
	}

}

