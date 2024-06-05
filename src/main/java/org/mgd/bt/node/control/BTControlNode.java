package org.mgd.bt.node.control;

import org.mgd.bt.node.BTNode;

import java.util.ArrayList;
import java.util.List;

public abstract class BTControlNode extends BTNode {
    /**
     * 子节点集合
     */
    List<BTNode> childNodes = new ArrayList<>();
}
