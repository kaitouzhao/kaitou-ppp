package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 远程工程师服务.
 * User: 赵立伟
 * Date: 2015/4/5
 * Time: 15:32
 */
public interface RemoteEngineerService extends Remote {
    /**
     * 保存工程师基本信息
     *
     * @param engineers 工程师列表
     */
    public void saveEngineers(List<Engineer> engineers) throws RemoteException;

    /**
     * 删除工程师基本信息
     *
     * @param engineer 工程师基本信息
     */
    public void deleteEngineer(Object... engineer) throws RemoteException;

    /**
     * 保存工程师发展信息
     *
     * @param engineerTrainings 工程师发展信息列表
     */
    public void saveEngineerTrainings(List<EngineerTraining> engineerTrainings) throws RemoteException;

    /**
     * 删除工程师发展信息
     *
     * @param engineerTraining 工程师发展信息
     */
    public void deleteEngineerTrainings(Object... engineerTraining) throws RemoteException;
}
