package team.ElectricityPatrolSys.service;

import java.util.Date;
import java.util.List;

import team.ElectricityPatrolSys.entity.BeHeTask;
import team.ElectricityPatrolSys.entity.BeheTaskUser;
import team.ElectricityPatrolSys.entity.Bug;

/**
 * 
 * 项目名称：<br>
 * 类名称：DoDeElimTaskService <br>
 * 类描述： 消缺任务的制定于分配 <br>
 * 创建人：郭亚飞 创建时间：2015-1-19 下午10:02:08 <br>
 * 修改人： 修改时间： <br>
 * 修改备注：
 * 
 * @version V1.0
 */
public interface DoDeElimTaskService {

	/**
	 * 提交审核
	 * 创建时间：2015-1-23 下午3:44:38 <br>
	 * @param be
	 * @return
	 */
	public int updateShen(BeHeTask be);
	
	/**
	 * 修改消缺任务，顺带bug表，和用户任务中间表
	 * 创建时间：2015-1-23 上午11:37:18 <br>
	 * @return
	 */
	public boolean updateBeHeTaskByIdForUpdate(BeHeTask be,String xiao_ids,String bug_ids);
	
	
	/**
	 * 点击按钮quxiao任务 创建时间：2015-1-21 下午9:59:51 <br>
	 * 
	 * @return
	 */
	public int updateCancelTask(String task_id);

	/**
	 * 添加缺陷任务表 创建时间：2015-1-21 下午8:55:27 <br>
	 * 
	 * @param be
	 * @param bug_ids
	 * @param xiao_ids
	 * @return
	 */
	public boolean addBeHeTaskInfo(BeHeTask be, String bug_ids, String xiao_ids);

	/**
	 * 查询条数
	 * 
	 * @return
	 */
	public int getBeHeTaskWeiCoung();

	/**
	 * 查询所有的未分配消缺任务的缺陷
	 * 
	 * @return
	 */
	public List<Bug> getBeHeTaskWei();

	/**
	 * 根据消缺id，查询所有的消缺员
	 * 
	 * @param t_id
	 * @return
	 */
	public List<BeheTaskUser> getBeheUser(String t_id);

	/**
	 * 根据消缺任务id，查询下属所有的缺陷 创建时间：2015-1-20 下午6:46:58 <br>
	 * 
	 * @param b_id
	 * @return
	 */
	public List<Bug> getBugById(String b_id);

	/**
	 * 根据任务id，查询任务的详细信息 创建时间：2015-1-20 下午5:30:35 <br>
	 * 
	 * @param b_id
	 * @return
	 */
	public BeHeTask getBeHeTaskById(String b_id);

	/**
	 * 查询消缺任务，并进行分页和按条件查询 创建时间：2015-1-20 上午11:14:18 <br>
	 * 
	 * @param start
	 * @param end
	 * @param be
	 * @return
	 */
	public List<BeHeTask> getBeheTask(int start, int end, BeHeTask be,
			String user_name, Date endTime, Date startTime);

	/**
	 * 查询条数 创建时间：2015-1-20 上午11:30:51 <br>
	 * 
	 * @param map
	 * @return
	 */
	public int getBeheTaskCount(BeHeTask be, String user_name, Date endTime,
			Date startTime);
}
