package org.mgd.bt.node;

public abstract class BTNode {
    private String name;
    /**
     * 父节点
     */
    private BTNode parentNode;
    /**
     * 节点状态
     */
    private BTNodeState nodeState;

    public enum BTNodeState {
        RUNNING, SUCCESS, FAILURE;
    }

    protected abstract BTNodeState tick();

}
