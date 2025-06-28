package org.mgd.fsm;


/**
 * @Author maoguidong
 * @Date 2024/6/13
 * @Des 有限状态机的示例还是以电梯为例子
 * 对象:电梯
 * 状态：1.开门 2.关门 3.运行 4.停止
 * action: 开门 关门
 */
public class FsmDemo {
    public static void main(String[] args) {
        LiftStateMachine liftStateMachine = new LiftStateMachine(new LiftContext());
        liftStateMachine.processEvent("open");
        liftStateMachine.processEvent("close");
    }
}

enum LiftStateEnum {
    OPEN, CLOSE, RUN, STOP;
}

class LiftStateMachine {
    private LiftContext context;

    public LiftStateMachine(LiftContext context) {
        this.context = context;
    }

    public void processEvent(String event) {
        switch (context.getCurrentState()) {
            case OPEN:
                if ("open".equals(event)) {
                }
                if ("close".equals(event)) {
                    context.setCurrentState(LiftStateEnum.CLOSE);
                }
                break;
            case CLOSE:
                if ("open".equals(event)) {
                    context.setCurrentState(LiftStateEnum.OPEN);
                }
                if ("close".equals(event)) {

                }
                break;
            case RUN:
                if ("open".equals(event)) {

                }
                if ("close".equals(event)) {

                }
                break;
            case STOP:
                if ("open".equals(event)) {
                    context.setCurrentState(LiftStateEnum.OPEN);
                }
                if ("close".equals(event)) {
                    context.setCurrentState(LiftStateEnum.CLOSE);
                }
            default:
                System.out.println("Invalid event for current state.");
        }
    }
}

class LiftContext {
    private LiftStateEnum currentState;

    public LiftContext() {
        this.currentState = LiftStateEnum.OPEN;
    }

    public void setCurrentState(LiftStateEnum currentState) {
        this.currentState = currentState;
    }

    public LiftStateEnum getCurrentState() {
        return currentState;
    }
}
