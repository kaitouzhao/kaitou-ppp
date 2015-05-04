package kaitou.ppp.service.rmi;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.manager.listener.EngineerUpdateListener;
import kaitou.ppp.rmi.service.RemoteEngineerService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * 远程工程师服务实现.
 * User: 赵立伟
 * Date: 2015/4/5
 * Time: 15:37
 */
public class RemoteEngineerServiceImpl extends UnicastRemoteObject implements RemoteEngineerService {

    private EngineerManager engineerManager;
    private EngineerTrainingManager engineerTrainingManager;
    private List<EngineerUpdateListener> engineerUpdateListeners;

    public void setEngineerUpdateListeners(List<EngineerUpdateListener> engineerUpdateListeners) {
        this.engineerUpdateListeners = engineerUpdateListeners;
    }

    public void setEngineerManager(EngineerManager engineerManager) {
        this.engineerManager = engineerManager;
    }

    public void setEngineerTrainingManager(EngineerTrainingManager engineerTrainingManager) {
        this.engineerTrainingManager = engineerTrainingManager;
    }

    public RemoteEngineerServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void saveEngineers(final List<Engineer> engineers) throws RemoteException {
        engineerManager.save(engineers);
        if (CollectionUtil.isEmpty(engineerUpdateListeners)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (EngineerUpdateListener listener : engineerUpdateListeners) {
                    listener.updateEngineerEvent(CollectionUtil.toArray(engineers, Engineer.class));
                }
            }
        }).start();
    }

    @Override
    public void deleteEngineer(Object... engineer) throws RemoteException {
        engineerManager.delete(engineer);
    }

    @Override
    public void saveEngineerTrainings(List<EngineerTraining> engineerTrainings) throws RemoteException {
        engineerTrainingManager.save(engineerTrainings);
    }

    @Override
    public void deleteEngineerTrainings(Object... engineerTraining) throws RemoteException {
        engineerTrainingManager.delete(engineerTraining);
    }
}
