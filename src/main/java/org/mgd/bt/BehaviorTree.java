//package org.mgd.bt;
//
//import java.util.List;
//
//// 抽象的行为树节点基类
//abstract class BehaviorTreeNode {
//    abstract BehaviorState tick(Agent agent); // 执行节点逻辑并返回状态
//}
//
//// 叶子节点示例 - 一个简单的移动行为
//class MoveToPlayerAction extends BehaviorTreeNode {
//    @Override
//    BehaviorState tick(Agent agent) {
//        if (agent.moveTo(agent.getTarget())) { // 假设有个 moveTo 方法完成移动逻辑
//            return BehaviorState.SUCCESS; // 移动成功
//        } else if (agent.isMoving()) {
//            return BehaviorState.RUNNING; // 还在移动过程中
//        } else {
//            return BehaviorState.FAILURE; // 移动失败
//        }
//    }
//}
//
//// 控制节点示例 - 序列节点
//class SequenceNode extends BehaviorTreeNode {
//    private List<BehaviorTreeNode> children;
//    private int currentChildIndex = 0;
//
//    public SequenceNode(List<BehaviorTreeNode> children) {
//        this.children = children;
//    }
//
//    @Override
//    BehaviorState tick(Agent agent) {
//        if (currentChildIndex >= children.size()) {
//            return BehaviorState.FAILURE; // 已经执行完所有子节点
//        }
//
//        BehaviorState childResult = children.get(currentChildIndex).tick(agent);
//        if (childResult == BehaviorState.SUCCESS) {
//            currentChildIndex++;
//            if (currentChildIndex < children.size()) {
//                return BehaviorState.RUNNING; // 继续执行下一个节点
//            } else {
//                return BehaviorState.SUCCESS; // 全部节点成功执行完毕
//            }
//        } else if (childResult == BehaviorState.RUNNING) {
//            return BehaviorState.RUNNING; // 子节点还在运行，整个序列也在运行
//        } else {
//            return BehaviorState.FAILURE; // 子节点失败，整个序列立即失败
//        }
//    }
//}
//
//// 简化的枚举定义行为树的状态
//enum BehaviorState {
//    SUCCESS,
//    FAILURE,
//    RUNNING
//}
//
//public class BehaviorTree {
//
//}
