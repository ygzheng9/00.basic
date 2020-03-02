package pickup.pattern.command;

/**
 * Created by YongGang on 2017/4/25.
 */
public class CommandChain {

  public static void main(String[] args) {
    BaseHandler projectManager = new ProjectManager(3);
    BaseHandler departmentManager = new DepartmentManager(5);
    BaseHandler generalManager = new GeneralManager(15);

    //创建职责链
    projectManager.setNextHandler(departmentManager);
    departmentManager.setNextHandler(generalManager);

    //发起一次请求
    projectManager.handleRequest(10);

    // 和 strategy 相比，command chain 上的节点可以先处理，然后再 pass 给下一个节点；
    // strategy 处理完，不能再 pass 给下一个 strategy 处理；
    // TODO: command chain 的这个 chain 能否根据配置，动态构建出来？ 如果可以的话，就像相当于单线的审批流；
  }
}
