package team.ElectricityPatrolSys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import team.ElectricityPatrolSys.dao.DoDeElimTaskDao;
import team.ElectricityPatrolSys.entity.BeHeTask;
import team.ElectricityPatrolSys.entity.BeheTaskUser;
import team.ElectricityPatrolSys.entity.Bug;
import team.ElectricityPatrolSys.entity.SysUser;
import team.ElectricityPatrolSys.service.DoDeElimTaskService;

/**
 * 
 * 项目名称：<br>
 * 类名称：DoDeElimTaskServiceImpl <br>
 * 类描述：消缺任务的制定与分配<br>
 * 创建人：郭亚飞 创建时间：2015-1-19 下午10:05:13 <br>
 * 修改人： 修改时间： <br>
 * 修改备注：
 * 
 * @version V1.0
 */

@Service("doDeElimTaskService")
public class DoDeElimTaskServiceImpl implements DoDeElimTaskService {

	@Autowired
	private DoDeElimTaskDao doDeElimTaskDao;

	
	
	/**
	 * 提交审核
	 * 创建时间：2015-1-23 下午3:44:38 <br>
	 * @param be
	 * @return
	 */
	public int updateShen(BeHeTask be){
		return doDeElimTaskDao.updateShen(be);
	}
	
	
	/**
	 * 修改消缺任务，顺带bug表，和用户任务中间表 创建时间：2015-1-23 上午11:37:18 <br>
	 * 
	 * @return
	 */
	public boolean updateBeHeTaskByIdForUpdate(BeHeTask be, String xiao_ids,
			String bug_ids) {
		int num1 = doDeElimTaskDao.updateBeHeTask(be); // 修改任务
		int num2 = doDeElimTaskDao.updateBugByTid(be.getTask_id()); // 修改为空
		int num3 = doDeElimTaskDao.delBeHeTaskUser(be.getTask_id()); // 删除
		int num4 = 0;
		int num5 = 0;
		// 打破重建
		if (!"".equals(xiao_ids) && xiao_ids != null) {
			String[] xiaos = xiao_ids.split(","); // 分解获得多个消缺员id
			for (int i = 0; i < xiaos.length; i++) {
				BeheTaskUser btu = new BeheTaskUser(); // 循环添加消缺任务表中间表
				btu.setBehe_task_id(be.getTask_id());
				btu.setSys_user_id(xiaos[i]); // 添加属性
				num4 = doDeElimTaskDao.addBeHeTaskUser(btu); // 添加中间表
			}
		}
		if (!"".equals(bug_ids) && bug_ids != null) {
			String[] bugs = bug_ids.split(","); // 分解bug id
			for (int i = 0; i < bugs.length; i++) { // 循环修改
				Bug bug = new Bug();
				bug.setBug_id(bugs[i]);
				bug.setBehe_task_id(be.getTask_id());
				num5 = doDeElimTaskDao.updateBugByID(bug); // 修改bug表
			}
		}

		if (num1 > 0 && num2 > 0 && num3 > 0 ) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 点击按钮quxiao任务 创建时间：2015-1-21 下午9:59:51 <br>
	 * 
	 * @return
	 */
	public int updateCancelTask(String task_id) {
		return doDeElimTaskDao.updateCancelTask(task_id);
	}

	/**
	 * 添加缺陷任务表 创建时间：2015-1-21 下午8:55:27 <br>
	 * 
	 * @param be
	 * @param bug_ids
	 * @param xiao_ids
	 * @return
	 */
	public boolean addBeHeTaskInfo(BeHeTask be, String bug_ids, String xiao_ids) {
		String u_id = ((SysUser) ActionContext.getContext().getSession()
				.get("loginUser")).getUser_id();
		be.setFrom_user_id(u_id); // 添加属性
		be.setTask_status_id(1008); // 默认为已分配
		be.setIstrue(0); // 默认不取消
		int num2 = 0;
		int num3 = 0;
		int num1 = doDeElimTaskDao.addBeheTask(be); // 添加消缺任务
		String[] xiaos = xiao_ids.split(","); // 分解获得多个消缺员id
		for (int i = 0; i < xiaos.length; i++) {
			BeheTaskUser btu = new BeheTaskUser(); // 循环添加消缺任务表中间表
			btu.setBehe_task_id(be.getTask_id());
			btu.setSys_user_id(xiaos[i]); // 添加属性
			num2 = doDeElimTaskDao.addBeHeTaskUser(btu); // 添加中间表
		}
		String[] bugs = bug_ids.split(","); // 分解bug id
		for (int i = 0; i < bugs.length; i++) { // 循环修改
			Bug bug = new Bug();
			bug.setBug_id(bugs[i]);
			bug.setBehe_task_id(be.getTask_id());
			num3 = doDeElimTaskDao.updateBugByID(bug); // 修改bug表
		}

		if (num1 > 0 && num2 > 0 && num3 > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询条数
	 * 
	 * @return
	 */
	public int getBeHeTaskWeiCoung() {
		return doDeElimTaskDao.getBeHeTaskWeiCoung();
	}

	/**
	 * 查询所有的未分配消缺任务的缺陷
	 * 
	 * @return
	 */
	public List<Bug> getBeHeTaskWei() {
		return doDeElimTaskDao.getBeHeTaskWei();
	}

	/**
	 * 根据消缺id，查询所有的消缺员
	 * 
	 * @param t_id
	 * @return
	 */
	public List<BeheTaskUser> getBeheUser(String t_id) {
		return doDeElimTaskDao.getBeheUser(t_id);
	}

	/**
	 * 根据消缺任务id，查询下属所有的缺陷 创建时间：2015-1-20 下午6:46:58 <br>
	 * 
	 * @param b_id
	 * @return
	 */
	public List<Bug> getBugById(String b_id) {
		return doDeElimTaskDao.getBugById(b_id);
	}

	/**
	 * 根据任务id，查询任务的详细信息 创建时间：2015-1-20 下午5:30:35 <br>
	 * 
	 * @param b_id
	 * @return
	 */
	public BeHeTask getBeHeTaskById(String b_id) {
		return doDeElimTaskDao.getBeHeTaskById(b_id);
	}

	/**
	 * 查询消缺任务，并进行分页和按条件查询 创建时间：2015-1-20 上午11:14:18 <br>
	 * 
	 * @param start
	 * @param end
	 * @param be
	 * @return
	 */
	public List<BeHeTask> getBeheTask(int start, int end, BeHeTask be,
			String user_name, Date endTime, Date startTime) {
		Map map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		if (be != null) {
			map.put("task_id", be.getTask_id());
			map.put("task_name", be.getTask_name());

			map.put("status_id", be.getTask_status_id());
		}
		if (user_name != null && user_name != "") {
			map.put("user_name", user_name);
		}
		if (startTime != null) {
			map.put("from_date1", startTime);
		}
		if (startTime != null) {
			map.put("from_date2", endTime);
		}
		return doDeElimTaskDao.getBeheTask(map);
	}

	/**
	 * 查询条数 创建时间：2015-1-20 上午11:30:51 <br>
	 * 
	 * @param map
	 * @return
	 */
	public int getBeheTaskCount(BeHeTask be, String user_name, Date endTime,
			Date startTime) {
		Map map = new HashMap();
		if (be != null) {
			map.put("task_id", be.getTask_id());
			map.put("task_name", be.getTask_name());

			if (be.getStatusComm() != null) {
				map.put("status_id", be.getStatusComm().getStatus_id());
			}
		}
		if (user_name != null && user_name != null) {
			map.put("user_name", user_name);
		}
		if (startTime != null) {
			map.put("from_date1", startTime);
		}
		if (startTime != null) {
			map.put("from_date2", endTime);
		}
		return doDeElimTaskDao.getBeheTaskCount(map);
	}

}
