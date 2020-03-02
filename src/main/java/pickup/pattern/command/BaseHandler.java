package pickup.pattern.command;

/**
 * Created by YongGang on 2017/4/25.
 */

/**
 * 抽象处理者
 */
public abstract class BaseHandler {

  private BaseHandler nextHandler;

  // 当前领导能审批通过的最多天数
  public int maxDay;

  protected BaseHandler(int maxDay) {
    this.maxDay = maxDay;
  }

  //设置责任链中下一个处理请求的对象
  public void setNextHandler(BaseHandler handler) {
    nextHandler = handler;
  }

  protected void handleRequest(int day) {
    if (day <= maxDay) {
      this.process(day);
    } else {
      if (nextHandler != null) {
        System.out.println("审批权限不够，需要上报.");

        //审批权限不够，继续上报
        nextHandler.handleRequest(day);
      } else {
        System.out.print("没有更高的领导审批了");
      }
    }
  }

  protected abstract void process(int day);
}

/**
 * 项目经理
 */
class ProjectManager extends BaseHandler {

  public ProjectManager(int day) {
    super(day);
  }

  @Override
  protected void process(int day) {
    System.out.print(day + "天请假，项目经理直接审批通过");
  }
}

/**
 * 部门经理
 */
class DepartmentManager extends BaseHandler {

  public DepartmentManager(int day) {
    super(day);
  }

  @Override
  protected void process(int day) {
    System.out.print(day + "天请假，部门经理审批通过");
  }
}

/**
 * 总经理
 */
class GeneralManager extends BaseHandler {

  public GeneralManager(int day) {
    super(day);
  }

  @Override
  protected void process(int day) {
    System.out.print(day + "天请假，总经理审批通过");
  }
}

