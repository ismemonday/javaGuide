package org.mgd.fsm.statePattern;

/**
 * @Author maoguidong
 * @Date 2024/6/13
 * @Des 状态模式的示例
 * 对象:电梯
 * 状态：1.开门 2.关门 3.运行 4.停止
 * action: 开门 关门
 */
public class StatePatternDemo {
    public static void main(String[] args) {
        //业务层不用去设置状态变化
        LiftContext liftContext = new LiftContext();
        liftContext.close();//关门成功
        liftContext.close();//已经关门无法继续关门
        liftContext.open();//开门成功
        liftContext.close();//关门成功
    }
}

class LiftContext {
    private LiftState liftState=new StopState();

    public void setLiftState(LiftState liftState) {
        this.liftState = liftState;
    }

    public void open() {
        liftState.open(this);
    }

    public void close() {
        liftState.close(this);
    }
}

abstract class LiftState {
    abstract void open(LiftContext liftContext);

    abstract void close(LiftContext liftContext);
}

class OpenState extends LiftState {
    @Override
    public void open(LiftContext liftContext) {
        System.out.println("电梯已经开门，不支持重复操作");
    }

    @Override
    public void close(LiftContext liftContext) {
        System.out.println("电梯即将关门");
        //改变当前状态为关门状态
        liftContext.setLiftState(new CloseState());
    }
}

class CloseState extends LiftState {

    @Override
    void open(LiftContext liftContext) {
        System.out.println("电梯即将开门");
        liftContext.setLiftState(new OpenState());
    }

    @Override
    void close(LiftContext liftContext) {
        System.out.println("电梯已经关门，不支持重复操作");
    }
}

class RunState extends LiftState {

    @Override
    void open(LiftContext liftContext) {
        System.out.println("电梯在运行不支持开门");
    }

    @Override
    void close(LiftContext liftContext) {
        System.out.println("电梯在运行不支持关门");
    }
}

class StopState extends LiftState {

    @Override
    void open(LiftContext liftContext) {
        System.out.println("电梯即将开门");
        liftContext.setLiftState(new OpenState());
    }

    @Override
    void close(LiftContext liftContext) {
        System.out.println("电梯即将关门");
        liftContext.setLiftState(new CloseState());
    }
}
