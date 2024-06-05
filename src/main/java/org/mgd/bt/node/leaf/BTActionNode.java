package org.mgd.bt.node.leaf;

public class BTActionNode extends BTLeafNode{
    @Override
    protected BTNodeState tick() {
        return BTNodeState.FAILURE;
    }
}
